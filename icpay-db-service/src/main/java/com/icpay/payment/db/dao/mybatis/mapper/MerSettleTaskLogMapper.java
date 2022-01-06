package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.MerSettleTaskLog;
import com.icpay.payment.db.dao.mybatis.model.MerSettleTaskLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerSettleTaskLogMapper {
    /**
     * Database table : tbl_mer_settle_task_log
     *
     * @mbg.generated
     */
    long countByExample(MerSettleTaskLogExample example);

    /**
     * Database table : tbl_mer_settle_task_log
     *
     * @mbg.generated
     */
    int deleteByExample(MerSettleTaskLogExample example);

    /**
     * Database table : tbl_mer_settle_task_log
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer seqId);

    /**
     * Database table : tbl_mer_settle_task_log
     *
     * @mbg.generated
     */
    int insert(MerSettleTaskLog record);

    /**
     * Database table : tbl_mer_settle_task_log
     *
     * @mbg.generated
     */
    int insertSelective(MerSettleTaskLog record);

    /**
     * Database table : tbl_mer_settle_task_log
     *
     * @mbg.generated
     */
    List<MerSettleTaskLog> selectByPage(MerSettleTaskLogExample example);

    /**
     * Database table : tbl_mer_settle_task_log
     *
     * @mbg.generated
     */
    List<MerSettleTaskLog> selectByExample(MerSettleTaskLogExample example);

    /**
     * Database table : tbl_mer_settle_task_log
     *
     * @mbg.generated
     */
    MerSettleTaskLog selectByPrimaryKey(Integer seqId);

    /**
     * Database table : tbl_mer_settle_task_log
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") MerSettleTaskLog record, @Param("example") MerSettleTaskLogExample example);

    /**
     * Database table : tbl_mer_settle_task_log
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") MerSettleTaskLog record, @Param("example") MerSettleTaskLogExample example);

    /**
     * Database table : tbl_mer_settle_task_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MerSettleTaskLog record);

    /**
     * Database table : tbl_mer_settle_task_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MerSettleTaskLog record);
}