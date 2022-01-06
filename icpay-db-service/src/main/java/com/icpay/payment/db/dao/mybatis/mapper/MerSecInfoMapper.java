package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.MerSecInfo;
import com.icpay.payment.db.dao.mybatis.model.MerSecInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MerSecInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerSecInfoMapper {
    /**
     * Database table : tbl_mer_sec_info
     *
     * @mbg.generated
     */
    long countByExample(MerSecInfoExample example);

    /**
     * Database table : tbl_mer_sec_info
     *
     * @mbg.generated
     */
    int deleteByExample(MerSecInfoExample example);

    /**
     * Database table : tbl_mer_sec_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(MerSecInfoKey key);

    /**
     * Database table : tbl_mer_sec_info
     *
     * @mbg.generated
     */
    int insert(MerSecInfo record);

    /**
     * Database table : tbl_mer_sec_info
     *
     * @mbg.generated
     */
    int insertSelective(MerSecInfo record);

    /**
     * Database table : tbl_mer_sec_info
     *
     * @mbg.generated
     */
    List<MerSecInfo> selectByPage(MerSecInfoExample example);

    /**
     * Database table : tbl_mer_sec_info
     *
     * @mbg.generated
     */
    List<MerSecInfo> selectByExample(MerSecInfoExample example);

    /**
     * Database table : tbl_mer_sec_info
     *
     * @mbg.generated
     */
    MerSecInfo selectByPrimaryKey(MerSecInfoKey key);

    /**
     * Database table : tbl_mer_sec_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") MerSecInfo record, @Param("example") MerSecInfoExample example);

    /**
     * Database table : tbl_mer_sec_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") MerSecInfo record, @Param("example") MerSecInfoExample example);

    /**
     * Database table : tbl_mer_sec_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MerSecInfo record);

    /**
     * Database table : tbl_mer_sec_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MerSecInfo record);
}