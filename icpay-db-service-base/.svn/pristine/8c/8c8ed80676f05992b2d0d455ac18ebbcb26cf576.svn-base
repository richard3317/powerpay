package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.RiskListGroup;

public interface IRiskListGroupService {

	public List<RiskListGroup> select(Map<String, String> qryParamMap);
	
	/**
	 * 分页查询名单组配置信息
	 */
	public Pager<RiskListGroup> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键获取名单组配置信息
	 */
	public RiskListGroup selectByPrimaryKey(int groupId);

	/**
	 * 新增名单组配置信息
	 */
	public void add(RiskListGroup riskListGroup);
	
	/**
	 * 更新名单组配置信息
	 */
	public void update(RiskListGroup riskListGroup);
	
	/**
	 * 根据主键删除名单组配置信息
	 */
	public void delete(int groupId);
}