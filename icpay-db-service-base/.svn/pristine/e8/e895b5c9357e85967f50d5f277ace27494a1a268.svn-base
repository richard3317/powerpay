package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.TransTypeGroup;

public interface ITransTypeGroupService {

	public List<TransTypeGroup> select(Map<String, String> qryParamMap);
	
	/**
	 * 分页查询
	 */
	public Pager<TransTypeGroup> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public TransTypeGroup selectByPrimaryKey(int seqId);
	
	/**
	 * 新增
	 */
	public void add(TransTypeGroup entity);
	
	/**
	 * 修改
	 */
	public void update(TransTypeGroup entity);
	
	/**
	 * 删除
	 */
	public void delete(int seqId);
	
}
