package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountInfoExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChnlMchntAccountInfoMapper {
    /**
     * Database table : tbl_chnl_mchnt_account_info
     *
     * @mbg.generated
     */
    long countByExample(ChnlMchntAccountInfoExample example);

    /**
     * Database table : tbl_chnl_mchnt_account_info
     *
     * @mbg.generated
     */
    int deleteByExample(ChnlMchntAccountInfoExample example);

    /**
     * Database table : tbl_chnl_mchnt_account_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(ChnlMchntAccountInfoKey key);

    /**
     * Database table : tbl_chnl_mchnt_account_info
     *
     * @mbg.generated
     */
    int insert(ChnlMchntAccountInfo record);

    /**
     * Database table : tbl_chnl_mchnt_account_info
     *
     * @mbg.generated
     */
    int insertSelective(ChnlMchntAccountInfo record);

    /**
     * Database table : tbl_chnl_mchnt_account_info
     *
     * @mbg.generated
     */
    List<ChnlMchntAccountInfo> selectByPage(ChnlMchntAccountInfoExample example);

    /**
     * Database table : tbl_chnl_mchnt_account_info
     *
     * @mbg.generated
     */
    List<ChnlMchntAccountInfo> selectByExample(ChnlMchntAccountInfoExample example);

    /**
     * Database table : tbl_chnl_mchnt_account_info
     *
     * @mbg.generated
     */
    ChnlMchntAccountInfo selectByPrimaryKey(ChnlMchntAccountInfoKey key);

    /**
     * Database table : tbl_chnl_mchnt_account_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ChnlMchntAccountInfo record, @Param("example") ChnlMchntAccountInfoExample example);

    /**
     * Database table : tbl_chnl_mchnt_account_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ChnlMchntAccountInfo record, @Param("example") ChnlMchntAccountInfoExample example);

    /**
     * Database table : tbl_chnl_mchnt_account_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ChnlMchntAccountInfo record);

    /**
     * Database table : tbl_chnl_mchnt_account_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ChnlMchntAccountInfo record);
}