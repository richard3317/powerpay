package com.icpay.payment.batch.quartz.job;

import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.IBatchTask;
import com.icpay.payment.common.enums.BatchEnums;
import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.common.utils.DateUtil;

@Component("D0BalanceTransferChnlJob")
public class D0BalanceTransferChnlJob extends BaseQuartzJob {
	
	@Override
	public void doProcess() {
		String batchDt = DateUtil.fuzzyBatchDayD0(getFuzzyDayCutTimeD0());
		IBatchTask bo = ApplicationContextUtil.getBean("D0BalanceTransferChnlTask");
		bo.exec(batchDt, BatchEnums.RunningMode._0);
	}

	@Override
	protected String getJobDesc() {
		return "渠道D0余额结转任务";
	}
	
}
