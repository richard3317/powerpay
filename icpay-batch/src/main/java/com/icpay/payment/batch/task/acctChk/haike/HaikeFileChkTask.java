package com.icpay.payment.batch.task.acctChk.haike;
import static org.apache.commons.lang.StringUtils.isEmpty;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Component;

import com.icpay.payment.batch.cache.BatchConfigCache;
import com.icpay.payment.batch.constants.BatchConstants;
import com.icpay.payment.batch.task.acctChk.ChnlFileChkBaseTask;
import com.icpay.payment.common.constants.Constant.CHANNEL;
import com.icpay.payment.common.utils.FileLineHandler;
import com.icpay.payment.common.utils.FileUtil;


@Component("HaikeFileChkTask")
public class HaikeFileChkTask extends ChnlFileChkBaseTask {

	
	@Override
	protected void doTask() {
		deleteChnlSettleInfo(CHANNEL._04);
		String basePath = BatchConfigCache.getConfig(BatchConstants.CONF_KEY_HAIKE_CHK_FILE_PATH);
		String charSet = BatchConfigCache.getConfig(BatchConstants.CONF_KEY_HAIKE_CHK_FILE_CHARSET);
		File srcFileDir=new File(basePath+batchDt);
		
		File[] srcFileArr=srcFileDir.listFiles();
		if(srcFileArr==null||srcFileArr.length==0){
			logger.info("haike src_file not exists!batchDt="+batchDt);
			
		}
		for(int f=0;f<srcFileArr.length;f++){
			if(srcFileArr[f].getName().endsWith("settle.csv")){
				logger.info("haike src_file="+srcFileArr[f].getAbsolutePath());
			
				final List<String> content = new ArrayList<String>();
				FileUtil.readFileByLine(srcFileArr[f], charSet, new FileLineHandler() {
					@Override
					public void handleLine(String line) {
						content.add(line);
					}
				});
				int txnNum = 0;
				BigDecimal totalTxnAmt = new BigDecimal(0);
				BigDecimal totalFee = new BigDecimal(0);
				
				
				//从第二行开始读取，第一行是标题
				for (int i = 1; i < content.size();  i ++) {
					String line = content.get(i);
					if(isEmpty(line)){
						continue;
					}
					String[] fields = line.split(",");
					if(fields.length>=10){
						totalTxnAmt=totalTxnAmt.add(new BigDecimal(fields[8]));
						totalFee=totalFee.add(new BigDecimal(fields[9]));
						txnNum++;
					}
				}
				insertChnlSettleInfo(srcFileArr[f].getName(),txnNum,totalTxnAmt.movePointRight(2),totalFee.movePointRight(2),srcFileArr[f].getAbsolutePath(),CHANNEL._04,"HaikeFileChkTask");						
			}
		}
	}
	
	@Override
	protected String getTaskNm() {
		return "下载海科对账文件";
	}

}

