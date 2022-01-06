package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicy;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicyExample;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicyKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AgentProfitPolicyMapper {
    /**
     * Database table : tbl_agent_profit_policy
     *
     * @mbg.generated
     */
    long countByExample(AgentProfitPolicyExample example);

    /**
     * Database table : tbl_agent_profit_policy
     *
     * @mbg.generated
     */
    int deleteByExample(AgentProfitPolicyExample example);

    /**
     * Database table : tbl_agent_profit_policy
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(AgentProfitPolicyKey key);

    /**
     * Database table : tbl_agent_profit_policy
     *
     * @mbg.generated
     */
    int insert(AgentProfitPolicy record);

    /**
     * Database table : tbl_agent_profit_policy
     *
     * @mbg.generated
     */
    int insertSelective(AgentProfitPolicy record);

    /**
     * Database table : tbl_agent_profit_policy
     *
     * @mbg.generated
     */
    List<AgentProfitPolicy> selectByPage(AgentProfitPolicyExample example);

    /**
     * Database table : tbl_agent_profit_policy
     *
     * @mbg.generated
     */
    List<AgentProfitPolicy> selectByExample(AgentProfitPolicyExample example);

    /**
     * Database table : tbl_agent_profit_policy
     *
     * @mbg.generated
     */
    AgentProfitPolicy selectByPrimaryKey(AgentProfitPolicyKey key);

    /**
     * Database table : tbl_agent_profit_policy
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") AgentProfitPolicy record, @Param("example") AgentProfitPolicyExample example);

    /**
     * Database table : tbl_agent_profit_policy
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") AgentProfitPolicy record, @Param("example") AgentProfitPolicyExample example);

    /**
     * Database table : tbl_agent_profit_policy
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(AgentProfitPolicy record);

    /**
     * Database table : tbl_agent_profit_policy
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(AgentProfitPolicy record);
}