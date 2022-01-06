package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.AgentInfo;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentProfitQueryBean;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentProfitQuerySummary;

public interface IAgentProfitQueryService {
	
	//代理商分润查询页面
	public Pager<AgentProfitQueryBean> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	//代理商分润数据查询报表
	public List<AgentProfitQueryBean> selectByRpt(Map<String, String> qryParamMap);
	
	//代理商分润数据加总
	public AgentProfitQuerySummary selectSummary(Map<String, String> qryParamMap);
	public List<AgentProfitQuerySummary> selectTotalSummary(Map<String, String> qryParamMap);
}
