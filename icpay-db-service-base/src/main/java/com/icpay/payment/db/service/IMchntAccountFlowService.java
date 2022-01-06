package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.MerAccountFlow;

public interface IMchntAccountFlowService {

	public List<MerAccountFlow> select(Map<String, String> qryParamMap);
	
	/**
	 * 分页查询
	 */
	public Pager<MerAccountFlow> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 * @param seqId
	 * @return
	 */
	public MerAccountFlow selectByPrimaryKey(long seqId);

	/**
	 * 新增
	 */
	public void add(MerAccountFlow merAccountFlow);
	
}