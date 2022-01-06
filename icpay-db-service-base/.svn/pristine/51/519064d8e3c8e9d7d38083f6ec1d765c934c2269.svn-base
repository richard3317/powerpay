package com.icpay.payment.db.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.TransLog;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TransLogMapping;

public interface ITransLogService {

	public List<TransLog> select(String mon, Map<String, String> qryParamMap);
	
	/**
	 * 分页查询交易信息
	 */
	public Pager<TransLog> selectByPage(int pageNum, int pageSize, String mon, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询交易信息
	 */
	public TransLog selectByPrimaryKey(String transSeqId, String mon);
	
	/**
	 * 分页查询交易信息
	 */
	public Pager<TransLogMapping> selectTransLogMappingByPage(int pageNum, int pageSize, String mon, Map<String, String> qryParamMap);
	
	/**
	 * 导出流水
	 */
	public List<TransLogMapping> selectTransLogMapping(String mon, Map<String, String> qryParamMap);
	
	/**
	 * 查询出交易总金额，手续费
	 * @param mon
	 * @return
	 */
	public TransLog countTransAt(String mon, Map<String, String> qryParamMap);
	
	/**
	 * 更新交易流水状态
	 * @param log
	 */
	public void updateTxnState(String mon,TransLog log);
	
	/**
	 * 更新交易状态
	 * @param log
	 */
	public void updateState(String mon,TransLog log);

	int updateTxnTags(String transSeqId, String mon, String tags);

	public BigDecimal checkAmount(Map<String, String> qryParamMap);
	
}