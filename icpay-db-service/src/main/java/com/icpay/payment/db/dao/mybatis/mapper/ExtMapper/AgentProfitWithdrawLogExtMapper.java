package com.icpay.payment.db.dao.mybatis.mapper.ExtMapper;

import com.icpay.payment.db.dao.mybatis.model.AgentProfitWithdrawLog;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitWithdrawLogExample;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfoExample;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentProfitWithdrawSummary;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MerAccountInfoSummary;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AgentProfitWithdrawLogExtMapper {
    /**
     * Database table : tbl_agent_profit_withdraw_log
     *
     * @mbg.generated
     */
    long countByExample(AgentProfitWithdrawLogExample example);

    /**
     * Database table : tbl_agent_profit_withdraw_log
     *
     * @mbg.generated
     */
    int deleteByExample(AgentProfitWithdrawLogExample example);

    /**
     * Database table : tbl_agent_profit_withdraw_log
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * Database table : tbl_agent_profit_withdraw_log
     *
     * @mbg.generated
     */
    int insert(AgentProfitWithdrawLog record);

    /**
     * Database table : tbl_agent_profit_withdraw_log
     *
     * @mbg.generated
     */
    int insertSelective(AgentProfitWithdrawLog record);

    /**
     * Database table : tbl_agent_profit_withdraw_log
     *
     * @mbg.generated
     */
    List<AgentProfitWithdrawLog> selectByPage(AgentProfitWithdrawLogExample example);

    /**
     * Database table : tbl_agent_profit_withdraw_log
     *
     * @mbg.generated
     */
    List<AgentProfitWithdrawLog> selectByExample(AgentProfitWithdrawLogExample example);

    /**
     * Database table : tbl_agent_profit_withdraw_log
     *
     * @mbg.generated
     */
    AgentProfitWithdrawLog selectByPrimaryKey(Long id);

    /**
     * Database table : tbl_agent_profit_withdraw_log
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") AgentProfitWithdrawLog record, @Param("example") AgentProfitWithdrawLogExample example);

    /**
     * Database table : tbl_agent_profit_withdraw_log
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") AgentProfitWithdrawLog record, @Param("example") AgentProfitWithdrawLogExample example);

    /**
     * Database table : tbl_agent_profit_withdraw_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(AgentProfitWithdrawLog record);

    /**
     * Database table : tbl_agent_profit_withdraw_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(AgentProfitWithdrawLog record);
    
    /**
     * Database table : tbl_agent_profit_withdraw_log
     *
     * @mbg.generated
     */
	public AgentProfitWithdrawSummary selectSummary(AgentProfitWithdrawLogExample qryParamMap);
}