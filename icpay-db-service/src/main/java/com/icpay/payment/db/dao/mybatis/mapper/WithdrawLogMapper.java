package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.WithdrawLog;
import com.icpay.payment.db.dao.mybatis.model.WithdrawLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WithdrawLogMapper {
    /**
     * Database table : tbl_withdraw_log01
     *
     * @mbg.generated
     */
    long countByExample(WithdrawLogExample example);

    /**
     * Database table : tbl_withdraw_log01
     *
     * @mbg.generated
     */
    int deleteByExample(WithdrawLogExample example);

    /**
     * Database table : tbl_withdraw_log01
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String orderSeqId);

    /**
     * Database table : tbl_withdraw_log01
     *
     * @mbg.generated
     */
    int insert(WithdrawLog record);

    /**
     * Database table : tbl_withdraw_log01
     *
     * @mbg.generated
     */
    int insertSelective(WithdrawLog record);

    /**
     * Database table : tbl_withdraw_log01
     *
     * @mbg.generated
     */
    List<WithdrawLog> selectByPage(WithdrawLogExample example);

    /**
     * Database table : tbl_withdraw_log01
     *
     * @mbg.generated
     */
    List<WithdrawLog> selectByExample(WithdrawLogExample example);

    /**
     * Database table : tbl_withdraw_log01
     *
     * @mbg.generated
     */
    WithdrawLog selectByPrimaryKey(String orderSeqId);

    /**
     * Database table : tbl_withdraw_log01
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") WithdrawLog record, @Param("example") WithdrawLogExample example);

    /**
     * Database table : tbl_withdraw_log01
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") WithdrawLog record, @Param("example") WithdrawLogExample example);

    /**
     * Database table : tbl_withdraw_log01
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(WithdrawLog record);

    /**
     * Database table : tbl_withdraw_log01
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(WithdrawLog record);
}