package com.icpay.payment.batch.quartz.job;

import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.IBatchTask;
import com.icpay.payment.common.enums.BatchEnums;
import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.common.utils.DateUtil;
/**
 * 钱海渠道取现对账定时任务
 */
@Component("QianhaiWithdrawFileChkJob")
public class QianhaiWithdrawFileChkJob extends BaseQuartzJob {
	
	@Override
	public void doProcess() {
		String batchDt = DateUtil.yesterday8();
		IBatchTask bo = ApplicationContextUtil.getBean("QianhaiWithdrawFileChkTask");
		bo.exec(batchDt, BatchEnums.RunningMode._0);
	}

	@Override
	protected String getJobDesc() {
		return "钱海渠道取现对账定时任务";
	}
	
}
