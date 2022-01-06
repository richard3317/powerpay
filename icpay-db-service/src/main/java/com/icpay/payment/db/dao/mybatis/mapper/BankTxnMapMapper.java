package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.BankTxnMap;
import com.icpay.payment.db.dao.mybatis.model.BankTxnMapExample;
import com.icpay.payment.db.dao.mybatis.model.BankTxnMapKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BankTxnMapMapper {
    /**
     * Database table : tbl_bank_txn_map
     *
     * @mbg.generated
     */
    long countByExample(BankTxnMapExample example);

    /**
     * Database table : tbl_bank_txn_map
     *
     * @mbg.generated
     */
    int deleteByExample(BankTxnMapExample example);

    /**
     * Database table : tbl_bank_txn_map
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(BankTxnMapKey key);

    /**
     * Database table : tbl_bank_txn_map
     *
     * @mbg.generated
     */
    int insert(BankTxnMap record);

    /**
     * Database table : tbl_bank_txn_map
     *
     * @mbg.generated
     */
    int insertSelective(BankTxnMap record);

    /**
     * Database table : tbl_bank_txn_map
     *
     * @mbg.generated
     */
    List<BankTxnMap> selectByPage(BankTxnMapExample example);

    /**
     * Database table : tbl_bank_txn_map
     *
     * @mbg.generated
     */
    List<BankTxnMap> selectByExample(BankTxnMapExample example);

    /**
     * Database table : tbl_bank_txn_map
     *
     * @mbg.generated
     */
    BankTxnMap selectByPrimaryKey(BankTxnMapKey key);

    /**
     * Database table : tbl_bank_txn_map
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BankTxnMap record, @Param("example") BankTxnMapExample example);

    /**
     * Database table : tbl_bank_txn_map
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BankTxnMap record, @Param("example") BankTxnMapExample example);

    /**
     * Database table : tbl_bank_txn_map
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BankTxnMap record);

    /**
     * Database table : tbl_bank_txn_map
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BankTxnMap record);
}