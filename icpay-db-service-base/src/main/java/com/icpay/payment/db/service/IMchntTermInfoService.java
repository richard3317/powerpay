package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.MchntTermInfo;

public interface IMchntTermInfoService {

	public List<MchntTermInfo> select(Map<String, String> qryParamMap);
	
	/**
	 * 分页查询
	 */
	public Pager<MchntTermInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 单条查询
	 */
	public MchntTermInfo selectByPrimarykey(String mchntCd, String termSn);
	
	/**
	 * 新增
	 */
	public void add(MchntTermInfo entity);
	
	/**
	 * 删除
	 */
	public void delete(String mchntCd, String termSn);
}