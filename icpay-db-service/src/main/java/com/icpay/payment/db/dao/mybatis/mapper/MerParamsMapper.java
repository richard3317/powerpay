package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.MerParams;
import com.icpay.payment.db.dao.mybatis.model.MerParamsExample;
import com.icpay.payment.db.dao.mybatis.model.MerParamsKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerParamsMapper {
    /**
     * Database table : tbl_mer_params
     *
     * @mbg.generated
     */
    long countByExample(MerParamsExample example);

    /**
     * Database table : tbl_mer_params
     *
     * @mbg.generated
     */
    int deleteByExample(MerParamsExample example);

    /**
     * Database table : tbl_mer_params
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(MerParamsKey key);

    /**
     * Database table : tbl_mer_params
     *
     * @mbg.generated
     */
    int insert(MerParams record);

    /**
     * Database table : tbl_mer_params
     *
     * @mbg.generated
     */
    int insertSelective(MerParams record);

    /**
     * Database table : tbl_mer_params
     *
     * @mbg.generated
     */
    List<MerParams> selectByPage(MerParamsExample example);

    /**
     * Database table : tbl_mer_params
     *
     * @mbg.generated
     */
    List<MerParams> selectByExample(MerParamsExample example);

    /**
     * Database table : tbl_mer_params
     *
     * @mbg.generated
     */
    MerParams selectByPrimaryKey(MerParamsKey key);

    /**
     * Database table : tbl_mer_params
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") MerParams record, @Param("example") MerParamsExample example);

    /**
     * Database table : tbl_mer_params
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") MerParams record, @Param("example") MerParamsExample example);

    /**
     * Database table : tbl_mer_params
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MerParams record);

    /**
     * Database table : tbl_mer_params
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MerParams record);
}