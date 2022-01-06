package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.BatchStepInfo;
import com.icpay.payment.db.dao.mybatis.model.BatchStepInfoExample;
import com.icpay.payment.db.dao.mybatis.model.BatchStepInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BatchStepInfoMapper {
    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    long countByExample(BatchStepInfoExample example);

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    int deleteByExample(BatchStepInfoExample example);

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(BatchStepInfoKey key);

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    int insert(BatchStepInfo record);

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    int insertSelective(BatchStepInfo record);

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    List<BatchStepInfo> selectByPage(BatchStepInfoExample example);

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    List<BatchStepInfo> selectByExample(BatchStepInfoExample example);

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    BatchStepInfo selectByPrimaryKey(BatchStepInfoKey key);

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BatchStepInfo record, @Param("example") BatchStepInfoExample example);

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BatchStepInfo record, @Param("example") BatchStepInfoExample example);

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BatchStepInfo record);

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BatchStepInfo record);
}