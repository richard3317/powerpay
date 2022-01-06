package com.icpay.payment.batch.quartz.job;

import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.IBatchTask;
import com.icpay.payment.common.enums.BatchEnums;
import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.common.utils.DateUtil;

@Component("D0BalanceTransferJob")
public class D0BalanceTransferJob extends BaseQuartzJob {
	
	@Override
	public void doProcess() {
		String batchDt = DateUtil.fuzzyBatchDayD0(getFuzzyDayCutTimeD0());
		IBatchTask bo = ApplicationContextUtil.getBean("D0BalanceTransferTask");
		bo.exec(batchDt, BatchEnums.RunningMode._0);
	}

	@Override
	protected String getJobDesc() {
		return "D0余额结转任务";
	}
	
}
