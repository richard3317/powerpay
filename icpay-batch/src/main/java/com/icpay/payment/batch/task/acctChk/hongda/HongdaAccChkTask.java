package com.icpay.payment.batch.task.acctChk.hongda;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

import com.icpay.payment.batch.cache.BatchConfigCache;
import com.icpay.payment.batch.constants.BatchConstants;
import com.icpay.payment.batch.task.acctChk.ChnlAccChkBaseTask;
import com.icpay.payment.common.constants.Constant.CHANNEL;
import com.icpay.payment.common.constants.Constant.INTER_MSG;
import com.icpay.payment.common.constants.Constant.MSG;
import com.icpay.payment.common.jdbc.bo.ChnlVirMchntSettleInfo;



@Component("HongdaAccChkTask")
public class HongdaAccChkTask extends ChnlAccChkBaseTask {
		
	
	@Override
	protected void doTask() {
		try{
			delteTotalCheckResult(CHANNEL._03);
			delteCheckResult(CHANNEL._03);
			String charSet = BatchConfigCache.getConfig(BatchConstants.CONF_KEY_HONGDA_CHK_FILE_CHARSET);
			Map<String,Map<String,String>> allTranLogs =  loadTransLog();
			Map<String,Map<String,String>> allPendingLogs =  loadPendingTransLog();
			//挂账流水中去掉正常对账交易
			for(String key:allTranLogs.keySet()){
				if(allPendingLogs.containsKey(key)){
					allPendingLogs.remove(key);
				}				
			}
			List<ChnlVirMchntSettleInfo> chnlFiles = queryChnlSettleInfo(CHANNEL._03);
			List<Map<String,String>> checkLogs = new ArrayList<Map<String,String>>();
			List<Map<String,String>> checkPendingLogs = new ArrayList<Map<String,String>>();
			
			for(ChnlVirMchntSettleInfo chnlFile:chnlFiles){
				String filePath = chnlFile.getFile_path();
				logger.info("开始勾兑渠道对账文件["+filePath+"]");
				File f = new File(filePath);
				if(!f.exists()){
					throw new RuntimeException("弘达对账文件不存在["+f+"]");
				}
				else{
					FileInputStream fis=new FileInputStream(filePath);
					HSSFWorkbook workbook = new HSSFWorkbook(fis);
					HSSFSheet sheet=workbook.getSheetAt(0);
					HSSFRow row = sheet.getRow(0);
					List<String> errContent= new ArrayList<String>(); 
					
					//从第二行开始读取，第一行是标题						
					for(int rowInx=0;rowInx<=sheet.getLastRowNum();rowInx++){
						row=sheet.getRow(rowInx);
						if(row!=null){
							HSSFCell cell = row.getCell((short)12);
							String txnStatus=readCellValue(cell);
							if(txnStatus!=null&&txnStatus.equals("00")){
								String key=genChnlTransKey(row);
								
								if(allTranLogs.containsKey(key)){
									checkLogs.add(allTranLogs.remove(key));
									continue;
								}
								if(allPendingLogs.containsKey(key)){
									checkPendingLogs.add(allPendingLogs.remove(key));
									continue;
								}
								logger.warn("could not find key["+key+"] add to errFile");
								String dataLine="";
								for(short cellInx=0;cellInx<row.getLastCellNum();cellInx++){
									HSSFCell errCell = row.getCell(cellInx);
									String cellVal=readCellValue(errCell);
									dataLine+=cellVal+",";										
								}
								dataLine=dataLine.substring(0, dataLine.length()-1);
								errContent.add(dataLine);
							}	
						}
					}
					fis.close();
					
					String reFundFilePath = "";
					String checkRes = "1";
					if(errContent.size()>1){
						reFundFilePath = storeToRefundFile(f.getName(),errContent,CHANNEL._03);
						checkRes = "0";
					}
					insertTotalCheckResult(f.getName(),checkRes,reFundFilePath,CHANNEL._03);
				}
			}
	
			if(!allTranLogs.isEmpty()){
				List<Map<String,String>> temp = new ArrayList<Map<String,String>>();
				temp.addAll(allTranLogs.values());
				batchSavePendingResult(temp,CHANNEL._03);
			}
			
			if(!checkLogs.isEmpty()){
				batchSaveCheckedResult(checkLogs,CHANNEL._03);
			}
			
			if(!checkPendingLogs.isEmpty()){
				batchUpdateCheckedResult(checkPendingLogs);
			}
			
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private Map<String,Map<String,String>> loadTransLog(){
		List<Map<String,String>> allTransLogs =queryAllTransLogs(CHANNEL._03);
		Map<String, Map<String,String>> res = new HashMap<String,Map<String,String>>(allTransLogs.size());
		for(Map<String,String> transLog:allTransLogs){
			String key = /*transLog.get(INTER_MSG.cupsTraceTime).substring(8)
			+"#"+*/transLog.get(MSG.cardNo)
			+"#"+transLog.get(INTER_MSG.cupsQueryId)
			+"#"+transLog.get(INTER_MSG.cupsTraceNo)
			+"#"+transLog.get(MSG.txnAmt);
			
			//System.out.println("xinfu key="+key);
			if(res.containsKey(key)){
				throw new RuntimeException("["+res.get(key)+"]["+transLog.get(MSG.queryId)+"]渠道段关键要素重复，对账无法进行");
			}
			res.put(key, transLog);
		}
		allTransLogs.clear();
		logger.info(batchDt+"日弘达成功交易["+res.size()+"]");
		return res;
	}
	
	private Map<String,Map<String,String>> loadPendingTransLog(){
		List<Map<String,String>> pendingTransLogs =queryPendingTransLogs(CHANNEL._03);
		Map<String, Map<String,String>> res = new HashMap<String,Map<String,String>>(pendingTransLogs.size());
		for(Map<String,String> transLog:pendingTransLogs){
			String key = /*transLog.get(INTER_MSG.cupsTraceTime).substring(8)
					+"#"+*/transLog.get(MSG.cardNo)
					+"#"+transLog.get(INTER_MSG.cupsQueryId)
					+"#"+transLog.get(INTER_MSG.cupsTraceNo)
					+"#"+transLog.get(MSG.txnAmt);
			if(res.containsKey(key)){
				throw new RuntimeException("["+res.get(key)+"]["+transLog.get(MSG.queryId)+"]渠道段关键要素重复，对账无法进行");
			}
			res.put(key, transLog);
		}
		pendingTransLogs.clear();
		logger.info("挂账功交易["+res.size()+"]");
		return res;
	}
	

	@Override
	protected String getTaskNm() {
		return "弘达对账任务";
	}
	
	
	/**
	 * 读取cell的值
	 * @param cell
	 * @return
	 */
	private String readCellValue(HSSFCell cell){
		String cellValue="";
		if(cell.getCellType()==0){
			cellValue=String.valueOf(cell.getNumericCellValue());
		}
		else if(cell.getCellType()==1){
			cellValue=cell.getStringCellValue();
		}
		return cellValue;
	}
	
	/**
	 * 对账文件中每一行生成唯一比对的key
	 * @param row
	 * @return
	 */
	private String genChnlTransKey(HSSFRow row){
		
		HSSFCell cell = row.getCell((short)5);
		String cardNo=readCellValue(cell);
		
		cell = row.getCell((short)8);
		String chnlSerialNO=readCellValue(cell);
		
		cell = row.getCell((short)9);
		String posSerialNO=readCellValue(cell);
		
		cell = row.getCell((short)13);
		String txnAmt=(new BigDecimal(readCellValue(cell)).movePointRight(2)).toString();
		
		String key=/*txnTime+"#"+*/cardNo+"#"+chnlSerialNO+"#"+posSerialNO+"#"+txnAmt;
		return key;
	}
	
}

