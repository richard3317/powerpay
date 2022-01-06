package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.VaccInfo;
import com.icpay.payment.db.dao.mybatis.model.VaccInfoExample;
import com.icpay.payment.db.dao.mybatis.model.VaccInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VaccInfoMapper {
    /**
     * Database table : tbl_vacc_info
     *
     * @mbg.generated
     */
    long countByExample(VaccInfoExample example);

    /**
     * Database table : tbl_vacc_info
     *
     * @mbg.generated
     */
    int deleteByExample(VaccInfoExample example);

    /**
     * Database table : tbl_vacc_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(VaccInfoKey key);

    /**
     * Database table : tbl_vacc_info
     *
     * @mbg.generated
     */
    int insert(VaccInfo record);

    /**
     * Database table : tbl_vacc_info
     *
     * @mbg.generated
     */
    int insertSelective(VaccInfo record);

    /**
     * Database table : tbl_vacc_info
     *
     * @mbg.generated
     */
    List<VaccInfo> selectByPage(VaccInfoExample example);

    /**
     * Database table : tbl_vacc_info
     *
     * @mbg.generated
     */
    List<VaccInfo> selectByExample(VaccInfoExample example);

    /**
     * Database table : tbl_vacc_info
     *
     * @mbg.generated
     */
    VaccInfo selectByPrimaryKey(VaccInfoKey key);

    /**
     * Database table : tbl_vacc_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") VaccInfo record, @Param("example") VaccInfoExample example);

    /**
     * Database table : tbl_vacc_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") VaccInfo record, @Param("example") VaccInfoExample example);

    /**
     * Database table : tbl_vacc_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(VaccInfo record);

    /**
     * Database table : tbl_vacc_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(VaccInfo record);
}