package com.icpay.payment.db.service;

import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicy;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicyKey;

public interface IMerSettlePolicyService {

	/**
	 * 分页查询
	 */
	public Pager<MerSettlePolicy> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public MerSettlePolicy selectByPrimaryKey(MerSettlePolicyKey key);
	
	/**
	 * 新增
	 */
	public void add(MerSettlePolicy entity);
	
	/**
	 * 修改
	 */
	public void update(MerSettlePolicy entity);
	
	/**
	 * 删除
	 */
	public void delete(MerSettlePolicyKey key);
	
}
