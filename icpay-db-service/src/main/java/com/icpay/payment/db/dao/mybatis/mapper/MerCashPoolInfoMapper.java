package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.MerCashPoolInfo;
import com.icpay.payment.db.dao.mybatis.model.MerCashPoolInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MerCashPoolInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerCashPoolInfoMapper {
    /**
     * Database table : tbl_mer_cash_pool_info
     *
     * @mbg.generated
     */
    long countByExample(MerCashPoolInfoExample example);

    /**
     * Database table : tbl_mer_cash_pool_info
     *
     * @mbg.generated
     */
    int deleteByExample(MerCashPoolInfoExample example);

    /**
     * Database table : tbl_mer_cash_pool_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(MerCashPoolInfoKey key);

    /**
     * Database table : tbl_mer_cash_pool_info
     *
     * @mbg.generated
     */
    int insert(MerCashPoolInfo record);

    /**
     * Database table : tbl_mer_cash_pool_info
     *
     * @mbg.generated
     */
    int insertSelective(MerCashPoolInfo record);

    /**
     * Database table : tbl_mer_cash_pool_info
     *
     * @mbg.generated
     */
    List<MerCashPoolInfo> selectByPage(MerCashPoolInfoExample example);

    /**
     * Database table : tbl_mer_cash_pool_info
     *
     * @mbg.generated
     */
    List<MerCashPoolInfo> selectByExample(MerCashPoolInfoExample example);

    /**
     * Database table : tbl_mer_cash_pool_info
     *
     * @mbg.generated
     */
    MerCashPoolInfo selectByPrimaryKey(MerCashPoolInfoKey key);

    /**
     * Database table : tbl_mer_cash_pool_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") MerCashPoolInfo record, @Param("example") MerCashPoolInfoExample example);

    /**
     * Database table : tbl_mer_cash_pool_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") MerCashPoolInfo record, @Param("example") MerCashPoolInfoExample example);

    /**
     * Database table : tbl_mer_cash_pool_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MerCashPoolInfo record);

    /**
     * Database table : tbl_mer_cash_pool_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MerCashPoolInfo record);
}