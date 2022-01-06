package com.icpay.payment.batch.quartz.job;

import org.springframework.stereotype.Component;
import com.icpay.payment.batch.task.IBatchTask;
import com.icpay.payment.common.enums.BatchEnums;
import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.common.utils.DateUtil;

@Component("chnlMchntAccountHourJob")
public class ChnlMchntAccountHourJob extends BaseQuartzJob{

	@Override
	protected void doProcess() {
		String batchDt = DateUtil.fuzzyBatchDayT1(getFuzzyDayCutTimeT1());
		IBatchTask bo = ApplicationContextUtil.getBean("chnlMchntAccountHourTask");
		bo.exec(batchDt, BatchEnums.RunningMode._0);	
	}

	@Override
	protected String getJobDesc() {
		return "平台交易量生成任务";
	}
}
