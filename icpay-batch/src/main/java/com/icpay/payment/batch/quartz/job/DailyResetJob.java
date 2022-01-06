package com.icpay.payment.batch.quartz.job;

import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.IBatchTask;
import com.icpay.payment.common.enums.BatchEnums;
import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.common.utils.DateUtil;

/**
 * 
 * 每日重置的工作
 * 
 * @author robin
 *
 */
@Component("dailyResetJob")
public class DailyResetJob extends BaseQuartzJob {
	
	@Override
	public void doProcess() {
		String batchDt = DateUtil.fuzzyBatchDayD0(getFuzzyDayCutTimeD0());
		IBatchTask bo = ApplicationContextUtil.getBean("dailyResetTask");
		bo.exec(batchDt, BatchEnums.RunningMode._0);
	}

	@Override
	protected String getJobDesc() {
		return "每日归零及重置作业";
	}
	
}
