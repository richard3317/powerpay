package com.icpay.payment.batch.quartz.job;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.IBatchTask;
import com.icpay.payment.common.enums.BatchEnums;
import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.common.utils.DateUtil;

@Component("transRptInfoJob")
public class TransRptInfoJob extends BaseQuartzJob{

	@Override
	protected void doProcess() {
		// TODO Auto-generated method stub
		String batchDt = DateUtil.fuzzyBatchDayT1(getFuzzyDayCutTimeT1());
		IBatchTask bo = ApplicationContextUtil.getBean("transRptInfoTask");
		bo.exec(batchDt, BatchEnums.RunningMode._0);	
		
	}

	@Override
	protected String getJobDesc() {
		// TODO Auto-generated method stub
		
		return "折线图显示交易系列";
	}
	
	public static void main(String[] args) {
		//1.先取出当前月份，根据月份查询表格
		Calendar instance = Calendar.getInstance();
		System.out.println(instance);
		//为什么要加一呢，因为月份是从0开始的
		int month = instance.get(Calendar.MONDAY)+1;
	
	}
	
	

}
