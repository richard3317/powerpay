package com.icpay.payment.db.service;

import java.util.List;

import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitState;

public interface ISysSettleProfitStateService {
	
    /**
     * Database table : tbl_sys_settle_profit_state
     *
     * @mbg.generated
     */
    int insertSelective(SysSettleProfitState record);
    
    /**
     * Database table : tbl_sys_settle_profit_state
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SysSettleProfitState record);
    
    /**
     * Database table : tbl_sys_settle_profit_state
     *
     * @mbg.generated
     */
    List<SysSettleProfitState> select(String settleDate, String settleState);
    
    /**
     * Database table : tbl_sys_settle_profit_state
     *
     * @mbg.generated
     */
    SysSettleProfitState selectByPrimaryKey(String transSeqId);
    
    /**
     * Database table : tbl_sys_settle_profit_state
     *
     * @mbg.generated
     */
    List<SysSettleProfitState> selectBySettleDate(String settleDate);
    
    /**
     * Database table : tbl_sys_settle_profit_state
     *
     * @mbg.generated
     */
    List<SysSettleProfitState> selectBeforeDate(String settleDate);
    
    /**
     * Database table : tbl_sys_settle_profit_state
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String transSeqId);

}