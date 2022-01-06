package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfoExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChnlCashPoolInfoMapper {
    /**
     * Database table : tbl_chnl_cash_pool_info
     *
     * @mbg.generated
     */
    long countByExample(ChnlCashPoolInfoExample example);

    /**
     * Database table : tbl_chnl_cash_pool_info
     *
     * @mbg.generated
     */
    int deleteByExample(ChnlCashPoolInfoExample example);

    /**
     * Database table : tbl_chnl_cash_pool_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(ChnlCashPoolInfoKey key);

    /**
     * Database table : tbl_chnl_cash_pool_info
     *
     * @mbg.generated
     */
    int insert(ChnlCashPoolInfo record);

    /**
     * Database table : tbl_chnl_cash_pool_info
     *
     * @mbg.generated
     */
    int insertSelective(ChnlCashPoolInfo record);

    /**
     * Database table : tbl_chnl_cash_pool_info
     *
     * @mbg.generated
     */
    List<ChnlCashPoolInfo> selectByPage(ChnlCashPoolInfoExample example);

    /**
     * Database table : tbl_chnl_cash_pool_info
     *
     * @mbg.generated
     */
    List<ChnlCashPoolInfo> selectByExample(ChnlCashPoolInfoExample example);

    /**
     * Database table : tbl_chnl_cash_pool_info
     *
     * @mbg.generated
     */
    ChnlCashPoolInfo selectByPrimaryKey(ChnlCashPoolInfoKey key);

    /**
     * Database table : tbl_chnl_cash_pool_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ChnlCashPoolInfo record, @Param("example") ChnlCashPoolInfoExample example);

    /**
     * Database table : tbl_chnl_cash_pool_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ChnlCashPoolInfo record, @Param("example") ChnlCashPoolInfoExample example);

    /**
     * Database table : tbl_chnl_cash_pool_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ChnlCashPoolInfo record);

    /**
     * Database table : tbl_chnl_cash_pool_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ChnlCashPoolInfo record);
}