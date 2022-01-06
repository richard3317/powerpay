package com.icpay.payment.batch.quartz.job;

import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.IBatchTask;
import com.icpay.payment.common.enums.BatchEnums;
import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.common.utils.DateUtil;

/**
 * 
 * 每日分潤Job
 * 
 * @author robin
 *
 */
@Component("dailyProfitSettleJob")
public class DailyProfitSettleJob extends BaseQuartzJob {
	
	@Override
	public void doProcess() {
		//String batchDt = DateUtil.fuzzyBatchDayD0(getFuzzyDayCutTimeD0());
		String batchDt = DateUtil.yesterday8();
		IBatchTask bo = ApplicationContextUtil.getBean("dailyProfitSettleTask");
		bo.exec(batchDt, BatchEnums.RunningMode._0);
	}

	@Override
	protected String getJobDesc() {
		return "每日分润结算作业";
	}
	
}
