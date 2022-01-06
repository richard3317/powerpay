package com.icpay.payment.db.service;

import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.BatchTaskExecLog;

public interface IBatchTaskExecLogService {

	public Pager<BatchTaskExecLog> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	public BatchTaskExecLog selectByPrimaryKey(String logId);
	
	public void add(BatchTaskExecLog batchStepInfo);
	
	public void update(BatchTaskExecLog batchStepInfo);
}
