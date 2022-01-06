package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.AgentProfitInfo;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitInfoExample;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AgentProfitInfoMapper {
    /**
     * Database table : tbl_agent_profit_info
     *
     * @mbg.generated
     */
    long countByExample(AgentProfitInfoExample example);

    /**
     * Database table : tbl_agent_profit_info
     *
     * @mbg.generated
     */
    int deleteByExample(AgentProfitInfoExample example);

    /**
     * Database table : tbl_agent_profit_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(AgentProfitInfoKey key);

    /**
     * Database table : tbl_agent_profit_info
     *
     * @mbg.generated
     */
    int insert(AgentProfitInfo record);

    /**
     * Database table : tbl_agent_profit_info
     *
     * @mbg.generated
     */
    int insertSelective(AgentProfitInfo record);

    /**
     * Database table : tbl_agent_profit_info
     *
     * @mbg.generated
     */
    List<AgentProfitInfo> selectByPage(AgentProfitInfoExample example);

    /**
     * Database table : tbl_agent_profit_info
     *
     * @mbg.generated
     */
    List<AgentProfitInfo> selectByExample(AgentProfitInfoExample example);

    /**
     * Database table : tbl_agent_profit_info
     *
     * @mbg.generated
     */
    AgentProfitInfo selectByPrimaryKey(AgentProfitInfoKey key);

    /**
     * Database table : tbl_agent_profit_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") AgentProfitInfo record, @Param("example") AgentProfitInfoExample example);

    /**
     * Database table : tbl_agent_profit_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") AgentProfitInfo record, @Param("example") AgentProfitInfoExample example);

    /**
     * Database table : tbl_agent_profit_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(AgentProfitInfo record);

    /**
     * Database table : tbl_agent_profit_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(AgentProfitInfo record);
}