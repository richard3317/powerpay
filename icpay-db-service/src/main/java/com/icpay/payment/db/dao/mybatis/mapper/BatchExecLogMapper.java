package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.BatchExecLog;
import com.icpay.payment.db.dao.mybatis.model.BatchExecLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BatchExecLogMapper {
    /**
     * Database table : tbl_batch_exec_log
     *
     * @mbg.generated
     */
    long countByExample(BatchExecLogExample example);

    /**
     * Database table : tbl_batch_exec_log
     *
     * @mbg.generated
     */
    int deleteByExample(BatchExecLogExample example);

    /**
     * Database table : tbl_batch_exec_log
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String logId);

    /**
     * Database table : tbl_batch_exec_log
     *
     * @mbg.generated
     */
    int insert(BatchExecLog record);

    /**
     * Database table : tbl_batch_exec_log
     *
     * @mbg.generated
     */
    int insertSelective(BatchExecLog record);

    /**
     * Database table : tbl_batch_exec_log
     *
     * @mbg.generated
     */
    List<BatchExecLog> selectByPage(BatchExecLogExample example);

    /**
     * Database table : tbl_batch_exec_log
     *
     * @mbg.generated
     */
    List<BatchExecLog> selectByExample(BatchExecLogExample example);

    /**
     * Database table : tbl_batch_exec_log
     *
     * @mbg.generated
     */
    BatchExecLog selectByPrimaryKey(String logId);

    /**
     * Database table : tbl_batch_exec_log
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BatchExecLog record, @Param("example") BatchExecLogExample example);

    /**
     * Database table : tbl_batch_exec_log
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BatchExecLog record, @Param("example") BatchExecLogExample example);

    /**
     * Database table : tbl_batch_exec_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BatchExecLog record);

    /**
     * Database table : tbl_batch_exec_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BatchExecLog record);
}