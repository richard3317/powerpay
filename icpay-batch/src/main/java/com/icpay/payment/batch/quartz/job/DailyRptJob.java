package com.icpay.payment.batch.quartz.job;

import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.IBatchTask;
import com.icpay.payment.common.enums.BatchEnums;
import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.common.utils.DateUtil;

@Component("dailyRptJob")
public class DailyRptJob extends BaseQuartzJob {

	@Override
	public void doProcess() {
		String batchDt = DateUtil.yesterday8();
		
		// 统计数据抽取
		IBatchTask transStatExtractTask = ApplicationContextUtil.getBean("transStatExtractTask");
		transStatExtractTask.exec(batchDt, BatchEnums.RunningMode._0);
		
		// 商户交易情况统计日报
		IBatchTask mchntTransDailyReport = ApplicationContextUtil.getBean("mchntTransDailyReport");
		mchntTransDailyReport.exec(batchDt, BatchEnums.RunningMode._0);
		
		// 渠道交易情况统计日报
		IBatchTask chnlTransDailyReport = ApplicationContextUtil.getBean("chnlTransDailyReport");
		chnlTransDailyReport.exec(batchDt, BatchEnums.RunningMode._0);
		
		// 渠道商户交易情况统计日报
		IBatchTask chnlMchntTransDailyReport = ApplicationContextUtil.getBean("chnlMchntTransDailyReport");
		chnlMchntTransDailyReport.exec(batchDt, BatchEnums.RunningMode._0);
	}

	@Override
	protected String getJobDesc() {
		return "统计数据抽取及日报生成任务";
	}
	
}
