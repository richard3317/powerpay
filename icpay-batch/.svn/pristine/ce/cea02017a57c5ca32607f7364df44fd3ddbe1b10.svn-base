package com.icpay.payment.batch.quartz.job;

import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.IBatchTask;
import com.icpay.payment.common.enums.BatchEnums;
import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.common.utils.DateUtil;

/**
 * 
 * 每日间隔五分钟，BitPay币别汇率查询作业
 * 
 * @author JERRY
 *
 */
@Component("currencyQueryJob")
public class CurrencyQueryJob extends BaseQuartzJob {
	
	@Override
	public void doProcess() {
		//String batchDt = DateUtil.fuzzyBatchDayD0(getFuzzyDayCutTimeD0());
		String batchDt = DateUtil.now8();
		IBatchTask bo = ApplicationContextUtil.getBean("currencyQueryTask");
		bo.exec(batchDt, BatchEnums.RunningMode._0);
	}

	@Override
	protected String getJobDesc() {
		return "BitPay币别汇率查询作业";
	}
	
}
