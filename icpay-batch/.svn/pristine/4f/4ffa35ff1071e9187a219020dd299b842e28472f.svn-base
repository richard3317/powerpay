package com.icpay.payment.batch.task.acctChk.qianhai;


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

import com.icpay.payment.batch.task.acctChk.ChnlAccChkBaseTask;
import com.icpay.payment.common.constants.Constant.CHANNEL;
import com.icpay.payment.common.constants.Constant.INTER_MSG;
import com.icpay.payment.common.constants.Constant.MSG;
import com.icpay.payment.common.jdbc.bo.ChnlVirMchntSettleInfo;



@Component("QianhaiWithdrawChkTask")
public class QianhaiWithdrawChkTask extends ChnlAccChkBaseTask {
	
	@Override
	protected void doTask() {
		try{
			delteTotalCheckResult(CHANNEL._11);
			delteCheckResult(CHANNEL._11);
			Map<String,Map<String,String>> allTranLogs =  loadWithdrawLog();
			Map<String,Map<String,String>> allPendingLogs =  loadPendingWithdrawLog();
			//挂账流水中去掉正常对账交易
			for(String key:allTranLogs.keySet()){
				if(allPendingLogs.containsKey(key)){
					allPendingLogs.remove(key);
				}				
			}
			List<ChnlVirMchntSettleInfo> chnlFiles = queryChnlSettleInfo(CHANNEL._11);
			List<Map<String,String>> checkLogs = new ArrayList<Map<String,String>>();
			List<Map<String,String>> checkPendingLogs = new ArrayList<Map<String,String>>();
			
			for(ChnlVirMchntSettleInfo chnlFile:chnlFiles){
				String filePath = chnlFile.getFile_path();
				logger.info("开始勾兑渠道取现对账文件["+filePath+"]");
				File f = new File(filePath);
				if(!f.exists()){
					throw new RuntimeException("取现对账文件不存在["+f+"]");
				}
				else{
					FileInputStream fis=new FileInputStream(filePath);
					HSSFWorkbook workbook = new HSSFWorkbook(fis);
					HSSFSheet sheet=workbook.getSheetAt(0);
					HSSFRow row = sheet.getRow(0);
					List<String> errContent= new ArrayList<String>(); 
					
					//从第二行开始读取，第一行是标题						
					for(int rowInx=1;rowInx<=sheet.getLastRowNum();rowInx++){
						row=sheet.getRow(rowInx);
						if(row!=null){
							HSSFCell cell = row.getCell((short)6);
							String txnStatus=readCellValue(cell);
							if(txnStatus!=null&&txnStatus.equals("成功")){
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
						reFundFilePath = storeToRefundFile(f.getName(),errContent,CHANNEL._11);
						checkRes = "0";
					}
					insertTotalCheckResult(f.getName(),checkRes,reFundFilePath,CHANNEL._11);
				}
			}
	
			if(!allTranLogs.isEmpty()){
				List<Map<String,String>> temp = new ArrayList<Map<String,String>>();
				temp.addAll(allTranLogs.values());
				batchSavePendingResult(temp,CHANNEL._11);
			}
			
			if(!checkLogs.isEmpty()){
				batchSaveCheckedResult(checkLogs,CHANNEL._11);
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
	/**
	 * 
	 * @return
	 */
	private Map<String,Map<String,String>> loadWithdrawLog(){
		List<Map<String,String>> allWithdrawLogs =queryAllWithdrawLogs(CHANNEL._11);
		Map<String, Map<String,String>> res = new HashMap<String,Map<String,String>>(allWithdrawLogs.size());
		for(Map<String,String> withdrawLog:allWithdrawLogs){
			String cardNo=withdrawLog.get(MSG.cardNo);
			String cardNoShort=cardNo.substring(0, 6)+"******"+cardNo.substring(cardNo.length()-4);
			String key = withdrawLog.get(INTER_MSG.cupsTraceNo)//对应渠道订单号
//					+"#"+withdrawLog.get(INTER_MSG.cupsQueryId)//对应渠道交易编号
//					+"#"+cardNoShort
					+"#"+withdrawLog.get(MSG.txnAmt);
			
			if(res.containsKey(key)){
				throw new RuntimeException("["+res.get(key)+"]["+withdrawLog.get(MSG.queryId)+"]渠道段关键要素重复，对账无法进行");
			}
			res.put(key, withdrawLog);
		}
		allWithdrawLogs.clear();
		logger.info(batchDt+"日成功交易["+res.size()+"]");
		return res;
	}
	
	private Map<String,Map<String,String>> loadPendingWithdrawLog(){
		List<Map<String,String>> pendingWithdrawLogs =queryPendingWithdrawLogs(CHANNEL._11);
		Map<String, Map<String,String>> res = new HashMap<String,Map<String,String>>(pendingWithdrawLogs.size());
		for(Map<String,String> withdrawLog:pendingWithdrawLogs){
			String cardNo=withdrawLog.get(MSG.cardNo);
			String cardNoShort=cardNo.substring(0, 6)+"**** **** *"+cardNo.substring(cardNo.length()-4);
			String key = withdrawLog.get(INTER_MSG.cupsTraceNo)//对应渠道订单号
//					+"#"+withdrawLog.get(INTER_MSG.cupsQueryId)//对应渠道交易编号
//					+"#"+cardNoShort
					+"#"+withdrawLog.get(MSG.txnAmt);
			if(res.containsKey(key)){
				throw new RuntimeException("["+res.get(key)+"]["+withdrawLog.get(MSG.queryId)+"]渠道段关键要素重复，对账无法进行");
			}
			res.put(key, withdrawLog);
		}
		pendingWithdrawLogs.clear();
		logger.info("挂账功交易["+res.size()+"]");
		return res;
	}
	

	@Override
	protected String getTaskNm() {
		return "钱海渠道取现对账任务";
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
		
//		HSSFCell cell = row.getCell((short)2);
//		String cardNo=readCellValue(cell);
		
//		cell = row.getCell((short)8);
//		String chnlSerialNO=readCellValue(cell);
		
		HSSFCell cell = row.getCell((short)9);
		String posSerialNO=readCellValue(cell);
		
		cell = row.getCell((short)4);
		String txnAmt=(new BigDecimal(readCellValue(cell)).movePointRight(2)).toString();
		
		String key=posSerialNO+"#"+txnAmt;
		return key;
	}
	
	
}

