package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChnlMchntInfoMapper {
    /**
     * Database table : tbl_chnl_mchnt_info
     *
     * @mbg.generated
     */
    long countByExample(ChnlMchntInfoExample example);

    /**
     * Database table : tbl_chnl_mchnt_info
     *
     * @mbg.generated
     */
    int deleteByExample(ChnlMchntInfoExample example);

    /**
     * Database table : tbl_chnl_mchnt_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(ChnlMchntInfoKey key);

    /**
     * Database table : tbl_chnl_mchnt_info
     *
     * @mbg.generated
     */
    int insert(ChnlMchntInfo record);

    /**
     * Database table : tbl_chnl_mchnt_info
     *
     * @mbg.generated
     */
    int insertSelective(ChnlMchntInfo record);

    /**
     * Database table : tbl_chnl_mchnt_info
     *
     * @mbg.generated
     */
    List<ChnlMchntInfo> selectByPage(ChnlMchntInfoExample example);

    /**
     * Database table : tbl_chnl_mchnt_info
     *
     * @mbg.generated
     */
    List<ChnlMchntInfo> selectByExample(ChnlMchntInfoExample example);

    /**
     * Database table : tbl_chnl_mchnt_info
     *
     * @mbg.generated
     */
    ChnlMchntInfo selectByPrimaryKey(ChnlMchntInfoKey key);

    /**
     * Database table : tbl_chnl_mchnt_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ChnlMchntInfo record, @Param("example") ChnlMchntInfoExample example);

    /**
     * Database table : tbl_chnl_mchnt_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ChnlMchntInfo record, @Param("example") ChnlMchntInfoExample example);

    /**
     * Database table : tbl_chnl_mchnt_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ChnlMchntInfo record);

    /**
     * Database table : tbl_chnl_mchnt_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ChnlMchntInfo record);
}