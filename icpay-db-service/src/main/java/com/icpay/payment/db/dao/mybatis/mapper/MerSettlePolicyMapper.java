package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicy;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicyExample;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicyKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerSettlePolicyMapper {
    /**
     * Database table : tbl_mer_settle_policy
     *
     * @mbg.generated
     */
    long countByExample(MerSettlePolicyExample example);

    /**
     * Database table : tbl_mer_settle_policy
     *
     * @mbg.generated
     */
    int deleteByExample(MerSettlePolicyExample example);

    /**
     * Database table : tbl_mer_settle_policy
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(MerSettlePolicyKey key);

    /**
     * Database table : tbl_mer_settle_policy
     *
     * @mbg.generated
     */
    int insert(MerSettlePolicy record);

    /**
     * Database table : tbl_mer_settle_policy
     *
     * @mbg.generated
     */
    int insertSelective(MerSettlePolicy record);

    /**
     * Database table : tbl_mer_settle_policy
     *
     * @mbg.generated
     */
    List<MerSettlePolicy> selectByPage(MerSettlePolicyExample example);

    /**
     * Database table : tbl_mer_settle_policy
     *
     * @mbg.generated
     */
    List<MerSettlePolicy> selectByExample(MerSettlePolicyExample example);

    /**
     * Database table : tbl_mer_settle_policy
     *
     * @mbg.generated
     */
    MerSettlePolicy selectByPrimaryKey(MerSettlePolicyKey key);

    /**
     * Database table : tbl_mer_settle_policy
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") MerSettlePolicy record, @Param("example") MerSettlePolicyExample example);

    /**
     * Database table : tbl_mer_settle_policy
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") MerSettlePolicy record, @Param("example") MerSettlePolicyExample example);

    /**
     * Database table : tbl_mer_settle_policy
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MerSettlePolicy record);

    /**
     * Database table : tbl_mer_settle_policy
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MerSettlePolicy record);
}