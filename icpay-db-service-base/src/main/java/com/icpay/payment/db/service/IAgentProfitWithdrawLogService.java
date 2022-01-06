package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitWithdrawLog;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentProfitWithdrawSummary;

public interface IAgentProfitWithdrawLogService {

	public List<AgentProfitWithdrawLog> select(Map<String, String> qryParamMap);
	
	Pager<AgentProfitWithdrawLog> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	public AgentProfitWithdrawLog selectByPrimaryKey(Long id);
	
	public void add(AgentProfitWithdrawLog entity);

	public void update(AgentProfitWithdrawLog entity);
	
	public int delete(Long id);
	
	public int enableAll(Map<String, String> qryParamMap, boolean enabled, String lastOperId);
	
	/**
	 * 加总
	 */
	public AgentProfitWithdrawSummary selectSummary(Map<String, String> qryParamMap);
	
}
