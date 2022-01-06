package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.AgentInfo;

public interface IAgentInfoService {
	
	public Map<String, String> selectValidAgentNameMap();
	
	public List<AgentInfo> selectValidAgentsByType(String agentType);
	
	public List<AgentInfo> select(Map<String, String> qryParamMap);
	
	public Pager<AgentInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	public AgentInfo selectByPrimaryKey(String agentCd);
	
	public void add(AgentInfo agentInfo);

	public void update(AgentInfo agentInfo);
	
	public void delete(String agentCd);
	
}
