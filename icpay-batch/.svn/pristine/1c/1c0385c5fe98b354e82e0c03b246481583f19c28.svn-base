package com.icpay.payment.batch.quartz.job;

import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.IBatchTask;
import com.icpay.payment.common.enums.BatchEnums;
import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.common.utils.DateUtil;

@Component("agentProfitQueryCreateJob")
public class AgentProfitQueryCreateJob extends BaseQuartzJob {
	
	@Override
	public void doProcess() {
		String batchDt = DateUtil.now8();
		IBatchTask bo = ApplicationContextUtil.getBean("agentProfitQueryCreateTask");
		bo.exec(batchDt, BatchEnums.RunningMode._0);
	}

	@Override
	protected String getJobDesc() {
		return "代理商分润数据生成任务";
	}
	
}
