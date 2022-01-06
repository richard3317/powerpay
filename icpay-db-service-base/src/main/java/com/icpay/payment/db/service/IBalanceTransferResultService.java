package com.icpay.payment.db.service;

import java.util.Map;

import com.icpay.payment.db.dao.mybatis.model.BalanceTransferResult;
import com.icpay.payment.db.dao.mybatis.model.BalanceTransferResultExample;
import com.icpay.payment.db.dao.mybatis.model.BatchInfo;

public interface IBalanceTransferResultService {
	
	/**
	 * 新增
	 */
	public void add(BalanceTransferResult balanceInfo);
	
	/**
	 * 修改
	 */
	public void update(BalanceTransferResult balanceInfo);
	
	/**
	 * 统计
	 */

	public long count(Map<String,String>qryParamMap);
}
