package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.BatchJobInfo;

public interface IBatchJobInfoService {

	public List<BatchJobInfo> select(Map<String, String> qryParamMap);
	
	/**
	 * 分页查询任务配置信息
	 */
	public Pager<BatchJobInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键获取任务配置信息
	 */
	public BatchJobInfo selectByPrimaryKey(int batchNo, int jobNo);

	/**
	 * 新增任务配置信息
	 */
	public void add(BatchJobInfo batchJobInfo);
	
	/**
	 * 更新任务配置信息
	 */
	public void update(BatchJobInfo batchJobInfo);
	
	/**
	 * 根据主键删除任务配置信息
	 */
	public void delete(int batchNo, int jobNo);
}