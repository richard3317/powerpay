package com.icpay.payment.batch.task.acctChk.haike;

import static org.apache.commons.lang.StringUtils.isEmpty;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Component;

import com.icpay.payment.batch.cache.BatchConfigCache;
import com.icpay.payment.batch.constants.BatchConstants;
import com.icpay.payment.batch.task.acctChk.ChnlAccChkBaseTask;
import com.icpay.payment.common.constants.Constant.CHANNEL;
import com.icpay.payment.common.constants.Constant.INTER_MSG;
import com.icpay.payment.common.constants.Constant.MSG;
import com.icpay.payment.common.jdbc.bo.ChnlVirMchntSettleInfo;
import com.icpay.payment.common.utils.FileLineHandler;
import com.icpay.payment.common.utils.FileUtil;



@Component("HaikeAccChkTask")
public class HaikeAccChkTask extends ChnlAccChkBaseTask {
		
	
	@Override
	protected void doTask() {
		delteTotalCheckResult(CHANNEL._04);
		delteCheckResult(CHANNEL._04);
		String charSet = BatchConfigCache.getConfig(BatchConstants.CONF_KEY_HAIKE_CHK_FILE_CHARSET);
		Map<String,Map<String,String>> allTranLogs =  loadTransLog();
		Map<String,Map<String,String>> allPendingLogs =  loadPendingTransLog();
		for(String key:allTranLogs.keySet()){
			if(allPendingLogs.containsKey(key)){				
				allPendingLogs.remove(key);				
			}
		}
		List<ChnlVirMchntSettleInfo> chnlFiles = queryChnlSettleInfo(CHANNEL._04);
		List<Map<String,String>> checkLogs = new ArrayList<Map<String,String>>();
		List<Map<String,String>> checkPendingLogs = new ArrayList<Map<String,String>>();
		if(chnlFiles!=null&&chnlFiles.size()>0){
			for(ChnlVirMchntSettleInfo chnlFile:chnlFiles){
				String filePath = chnlFile.getFile_path();
				logger.info("开始勾兑渠道对账文件["+filePath+"]");
				File f = new File(filePath);
				if(!f.exists()){
					throw new RuntimeException("海科对账文件不存在["+f+"]");
				}
				else{
					final List<String> content = new ArrayList<String>();
					FileUtil.readFileByLine(f, charSet, new FileLineHandler() {
						@Override
						public void handleLine(String line) {
							content.add(line);
						}
					});
					List<String> errContent= new ArrayList<String>(); 
					errContent.add(content.get(0));
					
					//从第二行开始读取，第一行是标题
					for (int i = 1; i < content.size();  i ++) {
						String line = content.get(i);
						if(isEmpty(line)){
							continue;
						}
						
						String key=genChnlTransKey(line);
						if(allTranLogs.containsKey(key)){
							checkLogs.add(allTranLogs.remove(key));
							continue;
						}
						if(allPendingLogs.containsKey(key)){
							checkPendingLogs.add(allPendingLogs.remove(key));
							continue;
						}
						logger.warn("could not find key["+key+"] add to errFile");
						errContent.add(line);
						
					}
					String reFundFilePath = "";
					String checkRes = "1";
					if(errContent.size()>1){
						reFundFilePath = storeToRefundFile(f.getName(),errContent,CHANNEL._04);
						checkRes = "0";
					}
					insertTotalCheckResult(f.getName(),checkRes,reFundFilePath,CHANNEL._04);
				}
			}
			if(!allTranLogs.isEmpty()){
				List<Map<String,String>> temp = new ArrayList<Map<String,String>>();
				temp.addAll(allTranLogs.values());
				batchSavePendingResult(temp,CHANNEL._04);
			}
			
			if(!checkLogs.isEmpty()){
				batchSaveCheckedResult(checkLogs,CHANNEL._04);
			}
			
			if(!checkPendingLogs.isEmpty()){
				batchUpdateCheckedResult(checkPendingLogs);
			}	
		}
	}

	private Map<String,Map<String,String>> loadTransLog(){
		List<Map<String,String>> allTransLogs =queryAllTransLogs(CHANNEL._04);
		Map<String, Map<String,String>> res = new HashMap<String,Map<String,String>>(allTransLogs.size());
		for(Map<String,String> transLog:allTransLogs){
			//海科的卡号中间是固定6个*
			String cardNo=transLog.get(MSG.cardNo);
			String cardNoShort=cardNo.substring(0, 6)+"******"+cardNo.substring(cardNo.length()-4);
			String key = /*transLog.get(INTER_MSG.cupsTraceTime).substring(8)
			+"#"+*/transLog.get(INTER_MSG.cupsQueryId)
			+"#"+transLog.get(INTER_MSG.cupsTraceNo)
			+"#"+cardNoShort
			+"#"+transLog.get(MSG.txnAmt);
			//System.out.println("xinfu key="+key);
			if(res.containsKey(key)){
				throw new RuntimeException("["+res.get(key)+"]["+transLog.get(MSG.queryId)+"]渠道段关键要素重复，对账无法进行");
			}
			res.put(key, transLog);
		}
		allTransLogs.clear();
		logger.info(batchDt+"日海科成功交易["+res.size()+"]");
		return res;
	}
	
	private Map<String,Map<String,String>> loadPendingTransLog(){
		List<Map<String,String>> pendingTransLogs =queryPendingTransLogs(CHANNEL._04);
		Map<String, Map<String,String>> res = new HashMap<String,Map<String,String>>(pendingTransLogs.size());
		for(Map<String,String> transLog:pendingTransLogs){
			//海科的卡号中间是固定6个*
			String cardNo=transLog.get(MSG.cardNo);
			String cardNoShort=cardNo.substring(0, 6)+"******"+cardNo.substring(cardNo.length()-4);
			String key = /*transLog.get(INTER_MSG.cupsTraceTime).substring(8)
			+"#"+*/transLog.get(INTER_MSG.cupsQueryId)
			+"#"+transLog.get(INTER_MSG.cupsTraceNo)
			+"#"+cardNoShort
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
		return "海科对账任务";
	}
	

	
	/**
	 * 对账文件中的记录生成唯一比对key
	 * @param line
	 * @return
	 */
	private String genChnlTransKey(String line){
		String key="";
		String[] fields = line.split(",");
		if(fields.length>=13){
			String txnTime=fields[2].replace(":", "");
			String cardNoShort=fields[6];
			String txnAmt=(new BigDecimal(fields[8]).movePointRight(2)).toString();
			String chnlSerialNO=fields[11];
			String posSerialNO=fields[12].substring(fields[12].length()-6);
			key= /*txnTime								
					+"#"+*/chnlSerialNO
					+"#"+posSerialNO
					+"#"+cardNoShort
					+"#"+txnAmt;
		}
		return key;
	} 
	
}

