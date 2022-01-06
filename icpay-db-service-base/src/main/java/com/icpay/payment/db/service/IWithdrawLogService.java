package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.WithdrawLog;
import com.icpay.payment.db.dao.mybatis.model.modelExt.WithdrawLogMapping;

public interface IWithdrawLogService {

	public List<WithdrawLog> select(String mon, Map<String, String> qryParamMap);
	
	/**
	 * 分页查询取现信息
	 */
	public Pager<WithdrawLog> selectByPage(int pageNum, int pageSize, String mon, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询取现信息
	 */
	public WithdrawLog selectByPrimaryKey(String orderSeqId, String mon);
	
	/**
	 * 分页查询取现信息
	 */
	public Pager<WithdrawLogMapping> selectWithdrawLogMappingByPage(int pageNum, int pageSize, String mon, Map<String, String> qryParamMap);
	
	/**
	 * 导出取现流水
	 */
	public List<WithdrawLogMapping> selectWithdrawLog(String mon, Map<String, String> qryParamMap);
	
	public WithdrawLogMapping select(String orderSeqId, String mon);
}