package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.RiskThreshold;

public interface IRiskThresholdService {

	public List<RiskThreshold> select(Map<String, String> qryParamMap);
	
	/**
	 * 分页查询交易笔数金额阀值信息
	 */
	public Pager<RiskThreshold> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键获取交易笔数金额阀值信息
	 */
	public RiskThreshold selectByPrimaryKey(int ruleId);

	/**
	 * 新增交易笔数金额阀值信息
	 */
	public void add(RiskThreshold riskThreshold);
	
	/**
	 * 更新交易笔数金额阀值信息
	 */
	public void update(RiskThreshold riskThreshold);
	
	/**
	 * 根据主键删除交易笔数金额阀值信息
	 */
	public void delete(int ruleId);
}