package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.BatchJobInfo;
import com.icpay.payment.db.dao.mybatis.model.BatchJobInfoExample;
import com.icpay.payment.db.dao.mybatis.model.BatchJobInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BatchJobInfoMapper {
    /**
     * Database table : tbl_batch_job_info
     *
     * @mbg.generated
     */
    long countByExample(BatchJobInfoExample example);

    /**
     * Database table : tbl_batch_job_info
     *
     * @mbg.generated
     */
    int deleteByExample(BatchJobInfoExample example);

    /**
     * Database table : tbl_batch_job_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(BatchJobInfoKey key);

    /**
     * Database table : tbl_batch_job_info
     *
     * @mbg.generated
     */
    int insert(BatchJobInfo record);

    /**
     * Database table : tbl_batch_job_info
     *
     * @mbg.generated
     */
    int insertSelective(BatchJobInfo record);

    /**
     * Database table : tbl_batch_job_info
     *
     * @mbg.generated
     */
    List<BatchJobInfo> selectByPage(BatchJobInfoExample example);

    /**
     * Database table : tbl_batch_job_info
     *
     * @mbg.generated
     */
    List<BatchJobInfo> selectByExample(BatchJobInfoExample example);

    /**
     * Database table : tbl_batch_job_info
     *
     * @mbg.generated
     */
    BatchJobInfo selectByPrimaryKey(BatchJobInfoKey key);

    /**
     * Database table : tbl_batch_job_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BatchJobInfo record, @Param("example") BatchJobInfoExample example);

    /**
     * Database table : tbl_batch_job_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BatchJobInfo record, @Param("example") BatchJobInfoExample example);

    /**
     * Database table : tbl_batch_job_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BatchJobInfo record);

    /**
     * Database table : tbl_batch_job_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BatchJobInfo record);
}