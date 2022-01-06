package com.icpay.payment.service;

import org.apache.log4j.Logger;

import com.icpay.payment.batch.task.IBatchTask;
import com.icpay.payment.common.enums.AjaxRespEnums;
import com.icpay.payment.common.enums.BatchEnums;
import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;


public class BatchTaskServiceImpl implements BatchTaskService {
	
	private static final Logger logger = Logger.getLogger(BatchTaskServiceImpl.class);

	@Override
	public void executeTask(String batchDt, String taskNm, String executeTimeout, String paramStr) throws Exception {
		try {
			AssertUtil.strIsBlank(batchDt, "batchDt is blank.");
			AssertUtil.strIsBlank(taskNm, "taskNm is blank.");
			
			logger.info("###task started:" + batchDt + "_" + taskNm+" with paramStr["+paramStr+"]");
			
			IBatchTask task = ApplicationContextUtil.getBean(taskNm);
			if (task==null) AssertUtil.objIsNull(task, "task is null");

			if(StringUtil.isNotEmpty(paramStr)){
				task.setParam(paramStr);
			}
			
			if (Utils.isEmpty(executeTimeout))
				executeTimeout = "300";
			task.setManualTrigger(Integer.parseInt(executeTimeout));
			
			task.exec(batchDt, BatchEnums.RunningMode._1);
			
			logger.info("###task ended:" + batchDt + "_" + taskNm);
		} catch (Exception e) {
			logger.error("###task failed:" + batchDt + "_" + taskNm, e);
			throw e;
		}
		

	}

}
