package com.icpay.payment.batch.web.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.batch.task.IBatchTask;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.enums.AjaxRespEnums;
import com.icpay.payment.common.enums.BatchEnums;
import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;

@Controller
public class RunTaskController {

	private static final Logger logger = Logger.getLogger(RunTaskController.class);
	
	@RequestMapping(value = "/runTask.do")
	public @ResponseBody AjaxResult runJob(String batchDt, String taskNm, String executeTimeout, String paramStr) {
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
			return buildAjaxResp(AjaxRespEnums.SUCC, null);
		} catch (Exception e) {
			logger.error("###task failed:" + batchDt + "_" + taskNm, e);
			return buildAjaxResp(AjaxRespEnums.ERROR, "任务执行异常");
		}
	}
	
	private AjaxResult buildAjaxResp(AjaxRespEnums ajaxResp, String msg) {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(ajaxResp.getRespCode());
		if (StringUtil.isBlank(msg)) {
			rst.setRespMsg(ajaxResp.getRespMsg());
		} else {
			rst.setRespMsg(msg);
		}
		return rst;
	}
}
