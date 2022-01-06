package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySub;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySubExample;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySubKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerSettlePolicySubMapper {
    /**
     * Database table : tbl_mer_settle_policy_sub
     *
     * @mbg.generated
     */
    long countByExample(MerSettlePolicySubExample example);

    /**
     * Database table : tbl_mer_settle_policy_sub
     *
     * @mbg.generated
     */
    int deleteByExample(MerSettlePolicySubExample example);

    /**
     * Database table : tbl_mer_settle_policy_sub
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(MerSettlePolicySubKey key);

    /**
     * Database table : tbl_mer_settle_policy_sub
     *
     * @mbg.generated
     */
    int insert(MerSettlePolicySub record);

    /**
     * Database table : tbl_mer_settle_policy_sub
     *
     * @mbg.generated
     */
    int insertSelective(MerSettlePolicySub record);

    /**
     * Database table : tbl_mer_settle_policy_sub
     *
     * @mbg.generated
     */
    List<MerSettlePolicySub> selectByPage(MerSettlePolicySubExample example);

    /**
     * Database table : tbl_mer_settle_policy_sub
     *
     * @mbg.generated
     */
    List<MerSettlePolicySub> selectByExample(MerSettlePolicySubExample example);

    /**
     * Database table : tbl_mer_settle_policy_sub
     *
     * @mbg.generated
     */
    MerSettlePolicySub selectByPrimaryKey(MerSettlePolicySubKey key);

    /**
     * Database table : tbl_mer_settle_policy_sub
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") MerSettlePolicySub record, @Param("example") MerSettlePolicySubExample example);

    /**
     * Database table : tbl_mer_settle_policy_sub
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") MerSettlePolicySub record, @Param("example") MerSettlePolicySubExample example);

    /**
     * Database table : tbl_mer_settle_policy_sub
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MerSettlePolicySub record);

    /**
     * Database table : tbl_mer_settle_policy_sub
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MerSettlePolicySub record);
}