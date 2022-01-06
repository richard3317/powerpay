package com.icpay.payment.db.service;

import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.BmOperLog;

public interface IBmOperLogService {

	/**
	 * 分页查询操作日志
	 */
	public Pager<BmOperLog> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 按主键查找操作日志
	 * @param logId
	 * @return
	 */
	public BmOperLog selectByPrimaryKey(int logId);
	
	/**
	 * 新增日志记录
	 * @param log
	 */
	public void add(BmOperLog log);
	
}