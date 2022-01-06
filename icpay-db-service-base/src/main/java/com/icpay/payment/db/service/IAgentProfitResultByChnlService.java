package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitResultByChnl;



public interface IAgentProfitResultByChnlService {

	public Pager<AgentProfitResultByChnl> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	public AgentProfitResultByChnl selectByPrimaryKey(AgentProfitResultByChnl key);

	List<AgentProfitResultByChnl> select(Map<String, String> qryParamMap);
	
}
