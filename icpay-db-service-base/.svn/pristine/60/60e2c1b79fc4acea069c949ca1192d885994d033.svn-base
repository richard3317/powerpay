package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.BusCheckTask;

public interface IBusCheckTaskService {

	public List<BusCheckTask> select(Map<String, String> qryParamMap);
	
	/**
	 * 分页查询业务审核任务
	 */
	public Pager<BusCheckTask> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键获取业务审核任务
	 */
	public BusCheckTask selectByPrimaryKey(int taskId);

	/**
	 * 新增业务审核任务
	 */
	public int add(BusCheckTask busCheckTask);
	
	/**
	 * 更新业务审核任务
	 */
	public void update(BusCheckTask busCheckTask);
	
}