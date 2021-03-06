package com.icpay.payment.db.dao.mybatis.mapper.ExtMapper;

import com.icpay.payment.db.dao.mybatis.model.TxnLogView;
import com.icpay.payment.db.dao.mybatis.model.TxnLogViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.icpay.payment.db.dao.mybatis.model.modelExt.ChnlMchntDaily;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TxnLogProfitQuery;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TxnLogSummary;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TxnLogSysSettleProfit;
public interface TxnLogViewExtMapper {
    /**
     * Database table : view_txn_log_01
     *
     * @mbg.generated
     */
    long countByExample(@Param("example")  TxnLogViewExample example, @Param("mon") String mon);

    /**
     * Database table : view_txn_log_01
     *
     * @mbg.generated
     */
    List<TxnLogView> selectByPage(@Param("example") TxnLogViewExample example, @Param("mon") String mon);

    /**
     * Database table : view_txn_log_01
     *
     * @mbg.generated
     */
    List<TxnLogView> selectByExample(@Param("example") TxnLogViewExample example, @Param("mon") String mon);
    
    /**
     * 获取符合条件的加总值
     * @param example
     * @param mon
     * @return
     */
    TxnLogSummary selectSummary(@Param("example") TxnLogViewExample example, @Param("mon") String mon);
    
    /**
     * 获取成功的利润(新增)
     */
    String selecttransFeeDelta(@Param("example") TxnLogViewExample example, @Param("mon") String mon);
    
    /**
     * 排程捞取资料
     * @param intTransCd
     * @param mchntCd
     * @param chnlId
     * @param chnlMchntCd
     * @param mon
     * @return
     */
	List<TxnLogProfitQuery> selectForAgentProfitQueryCreateTaske(@Param("intTransCd") String intTransCd, @Param("mchntCd") String mchntCd, @Param("chnlId") String chnlId, @Param("chnlMchntCd") String chnlMchntCd, @Param("beginDate") String beginDate, @Param("endDat") String endDat, @Param("mon") String mon);
	
	/**
     * 查询自我利潤清算
     */
	List<TxnLogSysSettleProfit> selectSysSettleProfitSummary(@Param("mon") String mon, @Param("startDate") String startDate, @Param("endDate") String endDate);
	
	/**
     * Database table : view_txn_log_01
     *
     * @mbg.generated
     */
	long countAllOrderId(@Param("example") TxnLogViewExample example, @Param("mon") String mon, @Param("mon1") String mon1, @Param("mon2") String mon2, @Param("allOrderId") String allOrderId);
    
    /**
     * Database table : view_txn_log_01
     *
     * @mbg.generated
     */
    List<TxnLogView> selectAllOrderId(@Param("example") TxnLogViewExample example, @Param("mon") String mon, @Param("mon1") String mon1, @Param("mon2") String mon2, @Param("allOrderId") String allOrderId);
    
    /**
     * 大小商編交易日額
     */
	long countChnlMchntDaily(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("mon") String mon);
    
	/**
	 * 大小商編交易日額
	 */
    List<ChnlMchntDaily> selectChnlMchntDaily(@Param("example") TxnLogViewExample example, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("mon") String mon);
}