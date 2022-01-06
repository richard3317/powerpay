package com.icpay.payment.batch.quartz.job;

import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.IBatchTask;
import com.icpay.payment.common.enums.BatchEnums;
import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.common.utils.DateUtil;

@Component("T1BalanceTransferChnlJob")
public class T1BalanceTransferChnlJob extends BaseQuartzJob {
	
	@Override
	public void doProcess() {
		String batchDt = DateUtil.fuzzyBatchDayT1(getFuzzyDayCutTimeT1());
		IBatchTask bo = ApplicationContextUtil.getBean("T1BalanceTransferChnlTask");
		bo.exec(batchDt, BatchEnums.RunningMode._0);
	}

	@Override
	protected String getJobDesc() {
		return "渠道T1余额结转任务";
	}
	
}
