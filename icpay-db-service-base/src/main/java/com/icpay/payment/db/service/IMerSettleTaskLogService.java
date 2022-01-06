package com.icpay.payment.db.service;

import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.MerSettleTaskLog;

public interface IMerSettleTaskLogService {
	
	public Pager<MerSettleTaskLog> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	public MerSettleTaskLog selectByPrimaryKey(int seqId);
	
}
