package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicy;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicyKey;

public interface IAgentProfitPolicyService {

	/**
	 * 查询分润策略
	 * @param qryParamMap
	 * @return
	 */
	public List<AgentProfitPolicy> select(Map<String, String> qryParamMap);
	
	Pager<AgentProfitPolicy> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 查询分润策略，模糊查询，允许数据库设置的"*"比对
	 * @param qryParamMap
	 * @return
	 */
	public List<AgentProfitPolicy> selectFuzzyMode(Map<String, String> qryParamMap);
	
	public AgentProfitPolicy selectByPrimaryKey(AgentProfitPolicyKey key);
	
	public AgentProfitPolicy selectForMerTransType(String agentCd, String mchntCd, String intTransCd, String tradeType);
	
	public void add(AgentProfitPolicy entity);

	public void update(AgentProfitPolicy entity);
	
	public void delete(AgentProfitPolicyKey key);
	
}
