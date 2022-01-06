package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.BalanceTransferResult;
import com.icpay.payment.db.dao.mybatis.model.BalanceTransferResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BalanceTransferResultMapper {
    /**
     * Database table : tbl_balance_transfer_result
     *
     * @mbg.generated
     */
    long countByExample(BalanceTransferResultExample example);

    /**
     * Database table : tbl_balance_transfer_result
     *
     * @mbg.generated
     */
    int deleteByExample(BalanceTransferResultExample example);

    /**
     * Database table : tbl_balance_transfer_result
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer seqId);

    /**
     * Database table : tbl_balance_transfer_result
     *
     * @mbg.generated
     */
    int insert(BalanceTransferResult record);

    /**
     * Database table : tbl_balance_transfer_result
     *
     * @mbg.generated
     */
    int insertSelective(BalanceTransferResult record);

    /**
     * Database table : tbl_balance_transfer_result
     *
     * @mbg.generated
     */
    List<BalanceTransferResult> selectByPage(BalanceTransferResultExample example);

    /**
     * Database table : tbl_balance_transfer_result
     *
     * @mbg.generated
     */
    List<BalanceTransferResult> selectByExample(BalanceTransferResultExample example);

    /**
     * Database table : tbl_balance_transfer_result
     *
     * @mbg.generated
     */
    BalanceTransferResult selectByPrimaryKey(Integer seqId);

    /**
     * Database table : tbl_balance_transfer_result
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BalanceTransferResult record, @Param("example") BalanceTransferResultExample example);

    /**
     * Database table : tbl_balance_transfer_result
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BalanceTransferResult record, @Param("example") BalanceTransferResultExample example);

    /**
     * Database table : tbl_balance_transfer_result
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BalanceTransferResult record);

    /**
     * Database table : tbl_balance_transfer_result
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BalanceTransferResult record);
}