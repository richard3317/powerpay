package com.icpay.payment.db.service;

import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitInfo;

public interface IAgentProfitInfoService {

	public Pager<AgentProfitInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	public AgentProfitInfo selectByPrimaryKey(String agentCd);
	
	public void add(AgentProfitInfo entity);

	public void update(AgentProfitInfo entity);
	
	public void delete(String agentCd);
	
}
