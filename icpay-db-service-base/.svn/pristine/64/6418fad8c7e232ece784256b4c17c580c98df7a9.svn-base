package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.RptInfo;

public interface IRptInfoService {
	

	public List<RptInfo> select(Map<String, String> qryParamMap);
	
	public Pager<RptInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public RptInfo selectByPrimaryKey(int seqId);
	
	/**
	 * 新增
	 */
	public void add(RptInfo rptInfo);
	
}
