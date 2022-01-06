package com.icpay.payment.db.service;

import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitTaskLog;

public interface IAgentProfitTaskLogService {

	public Pager<AgentProfitTaskLog> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	public AgentProfitTaskLog selectByPrimaryKey(int seqId);
}
