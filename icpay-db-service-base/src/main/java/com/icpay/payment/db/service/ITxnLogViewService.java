package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.TxnLogView;
import com.icpay.payment.db.dao.mybatis.model.modelExt.ChnlMchntDaily;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TxnLogProfitQuery;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TxnLogSummary;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TxnLogSysSettleProfit;

public interface ITxnLogViewService {
	
	/**
	 * 分页查询交易信息
	 */
	public Pager<TxnLogView> selectByPage(int pageNum, int pageSize, String mon, Map<String, String> qryParamMap);
	
	/**
	 * 查询交易信息
	 */
	public List<TxnLogView> select(String mon, Map<String, String> qryParamMap);	
	
	
	/**
	 * 根据主键查询交易信息
	 */
	public TxnLogView selectByPrimaryKey(String transSeqId, String mon);	
	
	
	/**
	 * 计数
	 */
	public Long count(String mon, Map<String, String> qryParamMap);	
	
	/**
	 * 获取符合条件的加总值
	 * @param mon
	 * @param qryParamMap
	 * @return
	 */
	TxnLogSummary selectSummary(String mon, Map<String, String> qryParamMap);
	
	/**
     * 获取成功的利润(新增)
     */
    String sumTransFeeDelta(String mon, Map<String, String> qryParamMap);
    
	/**
	 * 排程捞取资料
	 * @param mon
	 * @param qryParamMap
	 * @return
	 */
	public List<TxnLogProfitQuery> selectForAgentProfitQueryCreateTaske(String mon, Map<String, Object> qryParamMap);
	
	/**
	 * 查询自我利潤清算
	 */
	public List<TxnLogSysSettleProfit> selectSysSettleProfitSummary(String mon, String startDate, String endDate);
	
	/**
	 * 全文檢索
	 */
	public Pager<TxnLogView> selectAllOrderId(int pageNum, int pageSize, String mon, Map<String, String> qryParamMap);
	
	/**
	 * 查詢大小商編交易日額
	 */
	public Pager<ChnlMchntDaily> selectChnlMchntDailyByPage(int pageNum, int pageSize, String mon, Map<String, String> qryParamMap);
	
	/**
	 * 查詢大小商編交易日額
	 */
	public List<ChnlMchntDaily> selectChnlMchntDaily(String mon, Map<String, String> qryParamMap);
}