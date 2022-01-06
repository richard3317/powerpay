package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.RiskTransLog;

public interface IRiskTransLogService {

	public List<RiskTransLog> select(Map<String, String> qryParamMap);
	
	/**
	 * 分页查询风险交易信息
	 */
	public Pager<RiskTransLog> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键获取风险交易信息
	 */
	public RiskTransLog selectByPrimaryKey(String transSeqId);
	
}