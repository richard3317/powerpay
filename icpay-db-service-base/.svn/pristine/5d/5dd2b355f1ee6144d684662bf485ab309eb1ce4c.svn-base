package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntMappingInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntMappingInfoKey;

public interface IChnlMchntMappingInfoService {
	
	public List<ChnlMchntMappingInfo> select(Map<String, String> qryParamMap);
	
	public Pager<ChnlMchntMappingInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public ChnlMchntMappingInfo selectByPrimaryKey(ChnlMchntMappingInfoKey key);
	
	/**
	 * 新增
	 */
	public void add(ChnlMchntMappingInfo entity);
	
	/**
	 * 删除
	 */
	public void delete(ChnlMchntMappingInfoKey key);
	
	/**
	 * 删除
	 */
	public void cmt(ChnlMchntMappingInfoKey key, String comment, String lastOperId);
	
}
