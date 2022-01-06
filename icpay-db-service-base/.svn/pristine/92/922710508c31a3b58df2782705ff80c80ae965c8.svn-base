package com.icpay.payment.db.service;

import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.EventLog;

public interface IEventLogService {

	/**
	 * 分页查询取现信息
	 */
	public Pager<EventLog> selectByPage(int pageNum, int pageSize, String mon, Map<String, String> qryParamMap);
	
	/**
	 * 查询
	 */
	public EventLog selectByPrimaryKey(String seqId, String mon);
	
	
}