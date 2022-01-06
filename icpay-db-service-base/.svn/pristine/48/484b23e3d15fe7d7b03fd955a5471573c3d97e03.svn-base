package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.BatchStepInfo;

public interface IBatchStepInfoService {

	public List<BatchStepInfo> select(Map<String, String> qryParamMap);
	
	/**
	 * 分页查询批量任务配置信息
	 */
	public Pager<BatchStepInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键获取批量任务配置信息
	 */
	public BatchStepInfo selectByPrimaryKey(int batchNo, int jobNo, int stepNo);

	/**
	 * 新增批量任务配置信息
	 */
	public void add(BatchStepInfo batchStepInfo);
	
	/**
	 * 更新批量任务配置信息
	 */
	public void update(BatchStepInfo batchStepInfo);
	
	/**
	 * 根据主键删除批量任务配置信息
	 */
	public void delete(int batchNo, int jobNo, int stepNo);
}