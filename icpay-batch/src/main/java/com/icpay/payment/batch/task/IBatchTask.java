package com.icpay.payment.batch.task;

import com.icpay.payment.common.enums.BatchEnums;

public interface IBatchTask {

	void exec(String batchDt, BatchEnums.RunningMode runningMode);
	
	void setParam(String jsonString);
	
	void setParam(String key,String value);
	
	boolean isManualTrigger();
	
	void setManualTrigger(Integer timeout);
	
}
