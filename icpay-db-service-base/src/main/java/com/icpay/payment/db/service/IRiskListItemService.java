package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.RiskListItem;
import com.icpay.payment.db.dao.mybatis.model.RiskListItemKey;

public interface IRiskListItemService {

	public List<RiskListItem> select(Map<String, String> qryParamMap);
	
	/**
	 * 分页查询名单组配置信息
	 */
	public Pager<RiskListItem> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键获取名单组配置信息
	 */
	public RiskListItem selectByPrimaryKey(RiskListItemKey riskListItem);

	/**
	 * 新增名单组配置信息
	 */
	public void add(RiskListItem riskListItem);
	
	/**
	 * 根据主键删除名单组配置信息
	 */
	public void delete(RiskListItemKey riskListItem);
}