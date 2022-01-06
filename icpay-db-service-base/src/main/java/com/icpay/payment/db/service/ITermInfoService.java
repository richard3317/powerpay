package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.TermInfo;

public interface ITermInfoService {

	/**
	 * 根据查询条件查询
	 * @param qryParamMap
	 * @return
	 */
	public List<TermInfo> select(Map<String, String> qryParamMap);
	
	/**
	 * 分页查询
	 */
	public Pager<TermInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 按主键查找
	 */
	public TermInfo selectByPrimaryKey(int termId);
	
	/**
	 * 按终端序号查询
	 */
	public TermInfo selectByTermSn(String termSn);
	
	/**
	 * 新增
	 */
	public void add(TermInfo termInfo);
	
}