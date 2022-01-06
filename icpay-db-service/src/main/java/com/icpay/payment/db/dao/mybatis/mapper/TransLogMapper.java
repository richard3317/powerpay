package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.TransLog;
import com.icpay.payment.db.dao.mybatis.model.TransLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TransLogMapper {
    /**
     * Database table : tbl_trans_log01
     *
     * @mbg.generated
     */
    long countByExample(TransLogExample example);

    /**
     * Database table : tbl_trans_log01
     *
     * @mbg.generated
     */
    int deleteByExample(TransLogExample example);

    /**
     * Database table : tbl_trans_log01
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String transSeqId);

    /**
     * Database table : tbl_trans_log01
     *
     * @mbg.generated
     */
    int insert(TransLog record);

    /**
     * Database table : tbl_trans_log01
     *
     * @mbg.generated
     */
    int insertSelective(TransLog record);

    /**
     * Database table : tbl_trans_log01
     *
     * @mbg.generated
     */
    List<TransLog> selectByPage(TransLogExample example);

    /**
     * Database table : tbl_trans_log01
     *
     * @mbg.generated
     */
    List<TransLog> selectByExample(TransLogExample example);

    /**
     * Database table : tbl_trans_log01
     *
     * @mbg.generated
     */
    TransLog selectByPrimaryKey(String transSeqId);

    /**
     * Database table : tbl_trans_log01
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TransLog record, @Param("example") TransLogExample example);

    /**
     * Database table : tbl_trans_log01
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TransLog record, @Param("example") TransLogExample example);

    /**
     * Database table : tbl_trans_log01
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TransLog record);

    /**
     * Database table : tbl_trans_log01
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TransLog record);
}