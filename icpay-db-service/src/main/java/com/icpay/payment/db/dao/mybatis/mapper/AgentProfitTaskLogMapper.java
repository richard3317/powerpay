package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.AgentProfitTaskLog;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitTaskLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AgentProfitTaskLogMapper {
    /**
     * Database table : tbl_agent_profit_task_log
     *
     * @mbg.generated
     */
    long countByExample(AgentProfitTaskLogExample example);

    /**
     * Database table : tbl_agent_profit_task_log
     *
     * @mbg.generated
     */
    int deleteByExample(AgentProfitTaskLogExample example);

    /**
     * Database table : tbl_agent_profit_task_log
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer seqId);

    /**
     * Database table : tbl_agent_profit_task_log
     *
     * @mbg.generated
     */
    int insert(AgentProfitTaskLog record);

    /**
     * Database table : tbl_agent_profit_task_log
     *
     * @mbg.generated
     */
    int insertSelective(AgentProfitTaskLog record);

    /**
     * Database table : tbl_agent_profit_task_log
     *
     * @mbg.generated
     */
    List<AgentProfitTaskLog> selectByPage(AgentProfitTaskLogExample example);

    /**
     * Database table : tbl_agent_profit_task_log
     *
     * @mbg.generated
     */
    List<AgentProfitTaskLog> selectByExample(AgentProfitTaskLogExample example);

    /**
     * Database table : tbl_agent_profit_task_log
     *
     * @mbg.generated
     */
    AgentProfitTaskLog selectByPrimaryKey(Integer seqId);

    /**
     * Database table : tbl_agent_profit_task_log
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") AgentProfitTaskLog record, @Param("example") AgentProfitTaskLogExample example);

    /**
     * Database table : tbl_agent_profit_task_log
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") AgentProfitTaskLog record, @Param("example") AgentProfitTaskLogExample example);

    /**
     * Database table : tbl_agent_profit_task_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(AgentProfitTaskLog record);

    /**
     * Database table : tbl_agent_profit_task_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(AgentProfitTaskLog record);
}