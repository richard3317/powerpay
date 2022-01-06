package com.icpay.payment.batch.quartz.job;

import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.IBatchTask;
import com.icpay.payment.common.enums.BatchEnums;
import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.common.utils.DateUtil;

@Component("T1BalancePreTransferJob")
public class T1BalancePreTransferJob extends BaseQuartzJob {
	
	@Override
	public void doProcess() {
		String batchDt = DateUtil.fuzzyBatchDayT1(getFuzzyDayCutTimeT1());
		IBatchTask bo = ApplicationContextUtil.getBean("T1BalancePreTransferTask");
		bo.exec(batchDt, BatchEnums.RunningMode._0);
	}

	@Override
	protected String getJobDesc() {
		return "T1余额前置结转任务";
	}
	
}
