package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.MerSettleAlgorithm;
import com.icpay.payment.db.dao.mybatis.model.MerSettleAlgorithmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerSettleAlgorithmMapper {
    /**
     * Database table : view_mer_settle_algorithm
     *
     * @mbg.generated
     */
    long countByExample(MerSettleAlgorithmExample example);

    /**
     * Database table : view_mer_settle_algorithm
     *
     * @mbg.generated
     */
    List<MerSettleAlgorithm> selectByPage(MerSettleAlgorithmExample example);

    /**
     * Database table : view_mer_settle_algorithm
     *
     * @mbg.generated
     */
    List<MerSettleAlgorithm> selectByExample(MerSettleAlgorithmExample example);
}