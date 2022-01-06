package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitSettleResult;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitSettleResultKey;


public interface IAgentProfitSettleResultService {

	public Pager<AgentProfitSettleResult> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	public AgentProfitSettleResult selectByPrimaryKey(AgentProfitSettleResultKey key);

	List<AgentProfitSettleResult> select(Map<String, String> qryParamMap);
	
}
