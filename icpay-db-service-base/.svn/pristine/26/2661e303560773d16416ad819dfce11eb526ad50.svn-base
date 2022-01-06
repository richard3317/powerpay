package com.icpay.payment.db.service;

import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.RoutInfo;
import com.icpay.payment.db.dao.mybatis.model.RoutInfoKey;

public interface IRoutInfoService {
	
	public Pager<RoutInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public RoutInfo selectByPrimaryKey(RoutInfoKey key);
	
	/**
	 * 新增
	 */
	public void add(RoutInfo routInfo);
	
	/**
	 * 修改
	 */
	public void update(RoutInfo routInfo);
	
	/**
	 * 删除
	 */
	public void delete(RoutInfoKey key);
}
