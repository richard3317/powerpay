package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.AgentMchntTxn;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentMchntTxnSummary;

public interface IAgentMchntTxnService {
	
	/**
	 * 分页查询交易信息
	 */
	public Pager<AgentMchntTxn> selectByPage(int pageNum, int pageSize, String mon, Map<String, String> qryParamMap);
	
	/**
	 * 查询交易信息
	 */
	public List<AgentMchntTxn> select(String mon, Map<String, String> qryParamMap);	
	
	
	/**
	 * 根据主键查询交易信息
	 */
	public AgentMchntTxn selectByPrimaryKey(String transSeqId, String mon);	
	
	
	/**
	 * 计数
	 */
	public Long count(String mon, Map<String, String> qryParamMap);	
	
	/**
	 * 获取符合条件的加总值
	 * @param mon
	 * @param qryParamMap
	 * @return
	 */
	AgentMchntTxnSummary selectSummary(String mon, Map<String, String> qryParamMap);
	

	
}