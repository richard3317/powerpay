package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.BatchExecLog;
import com.icpay.payment.db.dao.mybatis.model.BatchExecLogDetail;

public interface IBatchLogService {

	public List<BatchExecLog> selectBatchLog(Map<String, String> qryParamMap);
	
	public Pager<BatchExecLog> selectBatchLogByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	public BatchExecLog selectBatchLogByPrimaryKey(String logId);
	
	public void addBatchLog(BatchExecLog batchLog);
	
	public void updateBatchLog(BatchExecLog batchLog);
	
	public List<BatchExecLogDetail> selectBatchLogDetail(Map<String, String> qryParamMap);
	
	public Pager<BatchExecLogDetail> selectBatchLogDetailByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	public BatchExecLogDetail selectBatchLogDetailByPrimaryKey(String logDetailId);
	
	public void addBatchLogDetail(BatchExecLogDetail batchLogDetail);
	
	public void updateBatchLogDetail(BatchExecLogDetail batchLogDetail);
}
