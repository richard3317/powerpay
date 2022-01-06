package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.RuleInfo;
import com.icpay.payment.db.dao.mybatis.model.RuleInfoExample;
import com.icpay.payment.db.dao.mybatis.model.RuleInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RuleInfoMapper {
    /**
     * Database table : tbl_rule_info
     *
     * @mbg.generated
     */
    long countByExample(RuleInfoExample example);

    /**
     * Database table : tbl_rule_info
     *
     * @mbg.generated
     */
    int deleteByExample(RuleInfoExample example);

    /**
     * Database table : tbl_rule_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(RuleInfoKey key);

    /**
     * Database table : tbl_rule_info
     *
     * @mbg.generated
     */
    int insert(RuleInfo record);

    /**
     * Database table : tbl_rule_info
     *
     * @mbg.generated
     */
    int insertSelective(RuleInfo record);

    /**
     * Database table : tbl_rule_info
     *
     * @mbg.generated
     */
    List<RuleInfo> selectByPage(RuleInfoExample example);

    /**
     * Database table : tbl_rule_info
     *
     * @mbg.generated
     */
    List<RuleInfo> selectByExample(RuleInfoExample example);

    /**
     * Database table : tbl_rule_info
     *
     * @mbg.generated
     */
    RuleInfo selectByPrimaryKey(RuleInfoKey key);

    /**
     * Database table : tbl_rule_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") RuleInfo record, @Param("example") RuleInfoExample example);

    /**
     * Database table : tbl_rule_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") RuleInfo record, @Param("example") RuleInfoExample example);

    /**
     * Database table : tbl_rule_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RuleInfo record);

    /**
     * Database table : tbl_rule_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(RuleInfo record);
}