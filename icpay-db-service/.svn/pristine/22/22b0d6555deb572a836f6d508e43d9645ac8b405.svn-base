package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.AgentProfitQuery;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitQueryExample;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitQueryKey;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentProfitQueryBean;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentProfitQuerySummary;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AgentProfitQueryMapper {
    /**
     * Database table : tbl_agent_profit_query
     *
     * @mbg.generated
     */
    long countByExample(AgentProfitQueryExample example);

    /**
     * Database table : tbl_agent_profit_query
     *
     * @mbg.generated
     */
    int deleteByExample(AgentProfitQueryExample example);

    /**
     * Database table : tbl_agent_profit_query
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(AgentProfitQueryKey key);

    /**
     * Database table : tbl_agent_profit_query
     *
     * @mbg.generated
     */
    int insert(AgentProfitQuery record);

    /**
     * Database table : tbl_agent_profit_query
     *
     * @mbg.generated
     */
    int insertSelective(AgentProfitQuery record);

    /**
     * Database table : tbl_agent_profit_query
     *
     * @mbg.generated
     */
    List<AgentProfitQuery> selectByPage(AgentProfitQueryExample example);

    /**
     * Database table : tbl_agent_profit_query
     *
     * @mbg.generated
     */
    List<AgentProfitQuery> selectByExample(AgentProfitQueryExample example);

    /**
     * Database table : tbl_agent_profit_query
     *
     * @mbg.generated
     */
    AgentProfitQuery selectByPrimaryKey(AgentProfitQueryKey key);

    /**
     * Database table : tbl_agent_profit_query
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") AgentProfitQuery record, @Param("example") AgentProfitQueryExample example);

    /**
     * Database table : tbl_agent_profit_query
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") AgentProfitQuery record, @Param("example") AgentProfitQueryExample example);

    /**
     * Database table : tbl_agent_profit_query
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(AgentProfitQuery record);

    /**
     * Database table : tbl_agent_profit_query
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(AgentProfitQuery record);
    
    //代理商分润查询页面
  	long countAgentProfitQueryByExample(AgentProfitQueryBean example);
  	
  	//代理商分润数据查询报表
  	List<AgentProfitQueryBean> selectAgentProfitQueryByPage(AgentProfitQueryBean example);
  	
  	//代理商分润数据加总
  	AgentProfitQuerySummary selectSummary(AgentProfitQueryBean example);
  	List<AgentProfitQuerySummary> selectTotalSummary(AgentProfitQueryBean example);
}