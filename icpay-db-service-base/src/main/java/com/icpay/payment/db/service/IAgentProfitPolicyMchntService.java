package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicyMchnt;



public interface IAgentProfitPolicyMchntService {

	public Pager<AgentProfitPolicyMchnt> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	public AgentProfitPolicyMchnt selectByPrimaryKey(AgentProfitPolicyMchnt key);

	List<AgentProfitPolicyMchnt> select(Map<String, String> qryParamMap);
	
}
