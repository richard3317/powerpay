package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.AgentFuncInfo;
import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitBank;
import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitBankExample;

public interface ISysSettleProfitBankService {
	
    /**
     * Database table : tbl_sys_settle_profit_result
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String accountNo);

    /**
     * Database table : tbl_sys_settle_profit_bank
     *
     * @mbg.generated
     */
    int insert(SysSettleProfitBank record);

    /**
     * Database table : tbl_sys_settle_profit_bank
     *
     * @mbg.generated
     */
    int insertSelective(SysSettleProfitBank record);
    
    /**
     * Database table : tbl_sys_settle_profit_bank
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SysSettleProfitBank record);

    /**
     * Database table : tbl_sys_settle_profit_bank
     *
     * @mbg.generated
     */
    public Pager<SysSettleProfitBank> selectByPage(int pageNum, int pageSize,Map<String,String> qryParamMap);

    /**
     * Database table : tbl_sys_settle_profit_bank
     *
     * @mbg.generated
     */
    List<SysSettleProfitBank> selectByExample(Map<String,String> map);

    /**
     * Database table : tbl_sys_settle_profit_bank
     *
     * @mbg.generated
     */
    SysSettleProfitBank selectByPrimaryKey(String accountNo);
}