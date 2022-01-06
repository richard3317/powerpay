package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.BatchInfo;

public interface IBatchInfoService {

	public List<BatchInfo> select(Map<String, String> qryParamMap);
	
	/**
	 * 分页查询批量任务配置信息
	 */
	public Pager<BatchInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键获取批量任务配置信息
	 */
	public BatchInfo selectByPrimaryKey(int batchNo);

	/**
	 * 新增批量任务配置信息
	 */
	public void add(BatchInfo batchInfo);
	
	/**
	 * 更新批量任务配置信息
	 */
	public void update(BatchInfo batchInfo);
	
	/**
	 * 根据主键删除批量任务配置信息
	 */
	public void delete(int batchNo);
}