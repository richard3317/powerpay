package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.RuleActionInfo;
import com.icpay.payment.db.dao.mybatis.model.RuleActionInfoExample;
import com.icpay.payment.db.dao.mybatis.model.RuleActionInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RuleActionInfoMapper {
    /**
     * Database table : tbl_rule_action_info
     *
     * @mbg.generated
     */
    long countByExample(RuleActionInfoExample example);

    /**
     * Database table : tbl_rule_action_info
     *
     * @mbg.generated
     */
    int deleteByExample(RuleActionInfoExample example);

    /**
     * Database table : tbl_rule_action_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(RuleActionInfoKey key);

    /**
     * Database table : tbl_rule_action_info
     *
     * @mbg.generated
     */
    int insert(RuleActionInfo record);

    /**
     * Database table : tbl_rule_action_info
     *
     * @mbg.generated
     */
    int insertSelective(RuleActionInfo record);

    /**
     * Database table : tbl_rule_action_info
     *
     * @mbg.generated
     */
    List<RuleActionInfo> selectByPage(RuleActionInfoExample example);

    /**
     * Database table : tbl_rule_action_info
     *
     * @mbg.generated
     */
    List<RuleActionInfo> selectByExample(RuleActionInfoExample example);

    /**
     * Database table : tbl_rule_action_info
     *
     * @mbg.generated
     */
    RuleActionInfo selectByPrimaryKey(RuleActionInfoKey key);

    /**
     * Database table : tbl_rule_action_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") RuleActionInfo record, @Param("example") RuleActionInfoExample example);

    /**
     * Database table : tbl_rule_action_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") RuleActionInfo record, @Param("example") RuleActionInfoExample example);

    /**
     * Database table : tbl_rule_action_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RuleActionInfo record);

    /**
     * Database table : tbl_rule_action_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(RuleActionInfo record);
}