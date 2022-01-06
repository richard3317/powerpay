package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitResult;
import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitResultExample;
import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitResultKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysSettleProfitResultMapper {
    /**
     * Database table : tbl_sys_settle_profit_result
     *
     * @mbg.generated
     */
    long countByExample(SysSettleProfitResultExample example);

    /**
     * Database table : tbl_sys_settle_profit_result
     *
     * @mbg.generated
     */
    int deleteByExample(SysSettleProfitResultExample example);

    /**
     * Database table : tbl_sys_settle_profit_result
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(SysSettleProfitResultKey key);

    /**
     * Database table : tbl_sys_settle_profit_result
     *
     * @mbg.generated
     */
    int insert(SysSettleProfitResult record);

    /**
     * Database table : tbl_sys_settle_profit_result
     *
     * @mbg.generated
     */
    int insertSelective(SysSettleProfitResult record);

    /**
     * Database table : tbl_sys_settle_profit_result
     *
     * @mbg.generated
     */
    List<SysSettleProfitResult> selectByPage(SysSettleProfitResultExample example);

    /**
     * Database table : tbl_sys_settle_profit_result
     *
     * @mbg.generated
     */
    List<SysSettleProfitResult> selectByExample(SysSettleProfitResultExample example);

    /**
     * Database table : tbl_sys_settle_profit_result
     *
     * @mbg.generated
     */
    SysSettleProfitResult selectByPrimaryKey(SysSettleProfitResultKey key);

    /**
     * Database table : tbl_sys_settle_profit_result
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SysSettleProfitResult record, @Param("example") SysSettleProfitResultExample example);

    /**
     * Database table : tbl_sys_settle_profit_result
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SysSettleProfitResult record, @Param("example") SysSettleProfitResultExample example);

    /**
     * Database table : tbl_sys_settle_profit_result
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SysSettleProfitResult record);

    /**
     * Database table : tbl_sys_settle_profit_result
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysSettleProfitResult record);
}