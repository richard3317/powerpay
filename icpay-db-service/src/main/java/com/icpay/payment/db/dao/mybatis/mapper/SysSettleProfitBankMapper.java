package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitBank;
import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitBankExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysSettleProfitBankMapper {
    /**
     * Database table : tbl_sys_settle_profit_bank
     *
     * @mbg.generated
     */
    long countByExample(SysSettleProfitBankExample example);

    /**
     * Database table : tbl_sys_settle_profit_bank
     *
     * @mbg.generated
     */
    int deleteByExample(SysSettleProfitBankExample example);

    /**
     * Database table : tbl_sys_settle_profit_bank
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
    List<SysSettleProfitBank> selectByPage(SysSettleProfitBankExample example);

    /**
     * Database table : tbl_sys_settle_profit_bank
     *
     * @mbg.generated
     */
    List<SysSettleProfitBank> selectByExample(SysSettleProfitBankExample example);

    /**
     * Database table : tbl_sys_settle_profit_bank
     *
     * @mbg.generated
     */
    SysSettleProfitBank selectByPrimaryKey(String accountNo);

    /**
     * Database table : tbl_sys_settle_profit_bank
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SysSettleProfitBank record, @Param("example") SysSettleProfitBankExample example);

    /**
     * Database table : tbl_sys_settle_profit_bank
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SysSettleProfitBank record, @Param("example") SysSettleProfitBankExample example);

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
    int updateByPrimaryKey(SysSettleProfitBank record);
}