package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.IntKey;
import com.icpay.payment.db.dao.mybatis.model.IntKeyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IntKeyMapper {
    /**
     * Database table : tbl_int_key
     *
     * @mbg.generated
     */
    long countByExample(IntKeyExample example);

    /**
     * Database table : tbl_int_key
     *
     * @mbg.generated
     */
    int deleteByExample(IntKeyExample example);

    /**
     * Database table : tbl_int_key
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String keyTp);

    /**
     * Database table : tbl_int_key
     *
     * @mbg.generated
     */
    int insert(IntKey record);

    /**
     * Database table : tbl_int_key
     *
     * @mbg.generated
     */
    int insertSelective(IntKey record);

    /**
     * Database table : tbl_int_key
     *
     * @mbg.generated
     */
    List<IntKey> selectByPage(IntKeyExample example);

    /**
     * Database table : tbl_int_key
     *
     * @mbg.generated
     */
    List<IntKey> selectByExample(IntKeyExample example);

    /**
     * Database table : tbl_int_key
     *
     * @mbg.generated
     */
    IntKey selectByPrimaryKey(String keyTp);

    /**
     * Database table : tbl_int_key
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") IntKey record, @Param("example") IntKeyExample example);

    /**
     * Database table : tbl_int_key
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") IntKey record, @Param("example") IntKeyExample example);

    /**
     * Database table : tbl_int_key
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(IntKey record);

    /**
     * Database table : tbl_int_key
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(IntKey record);
}