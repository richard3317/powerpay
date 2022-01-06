package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.TransTypeGroup;
import com.icpay.payment.db.dao.mybatis.model.TransTypeGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TransTypeGroupMapper {
    /**
     * Database table : tbl_trans_type_group
     *
     * @mbg.generated
     */
    long countByExample(TransTypeGroupExample example);

    /**
     * Database table : tbl_trans_type_group
     *
     * @mbg.generated
     */
    int deleteByExample(TransTypeGroupExample example);

    /**
     * Database table : tbl_trans_type_group
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer seqId);

    /**
     * Database table : tbl_trans_type_group
     *
     * @mbg.generated
     */
    int insert(TransTypeGroup record);

    /**
     * Database table : tbl_trans_type_group
     *
     * @mbg.generated
     */
    int insertSelective(TransTypeGroup record);

    /**
     * Database table : tbl_trans_type_group
     *
     * @mbg.generated
     */
    List<TransTypeGroup> selectByPage(TransTypeGroupExample example);

    /**
     * Database table : tbl_trans_type_group
     *
     * @mbg.generated
     */
    List<TransTypeGroup> selectByExample(TransTypeGroupExample example);

    /**
     * Database table : tbl_trans_type_group
     *
     * @mbg.generated
     */
    TransTypeGroup selectByPrimaryKey(Integer seqId);

    /**
     * Database table : tbl_trans_type_group
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TransTypeGroup record, @Param("example") TransTypeGroupExample example);

    /**
     * Database table : tbl_trans_type_group
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TransTypeGroup record, @Param("example") TransTypeGroupExample example);

    /**
     * Database table : tbl_trans_type_group
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TransTypeGroup record);

    /**
     * Database table : tbl_trans_type_group
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TransTypeGroup record);
}