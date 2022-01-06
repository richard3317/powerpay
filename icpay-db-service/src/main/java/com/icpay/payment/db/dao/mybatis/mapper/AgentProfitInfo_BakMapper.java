package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.AgentProfitInfo;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@Deprecated
public interface AgentProfitInfo_BakMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_agent_profit_info
     *
     * @mbggenerated Wed Sep 09 15:11:37 CST 2015
     */
    int countByExample(AgentProfitInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_agent_profit_info
     *
     * @mbggenerated Wed Sep 09 15:11:37 CST 2015
     */
    int deleteByExample(AgentProfitInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_agent_profit_info
     *
     * @mbggenerated Wed Sep 09 15:11:37 CST 2015
     */
    int deleteByPrimaryKey(String agentCd);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_agent_profit_info
     *
     * @mbggenerated Wed Sep 09 15:11:37 CST 2015
     */
    int insert(AgentProfitInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_agent_profit_info
     *
     * @mbggenerated Wed Sep 09 15:11:37 CST 2015
     */
    int insertSelective(AgentProfitInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_agent_profit_info
     *
     * @mbggenerated Wed Sep 09 15:11:37 CST 2015
     */
    List<AgentProfitInfo> selectByExample(AgentProfitInfoExample example);
    
    List<AgentProfitInfo> selectByPage(AgentProfitInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_agent_profit_info
     *
     * @mbggenerated Wed Sep 09 15:11:37 CST 2015
     */
    AgentProfitInfo selectByPrimaryKey(String agentCd);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_agent_profit_info
     *
     * @mbggenerated Wed Sep 09 15:11:37 CST 2015
     */
    int updateByExampleSelective(@Param("record") AgentProfitInfo record, @Param("example") AgentProfitInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_agent_profit_info
     *
     * @mbggenerated Wed Sep 09 15:11:37 CST 2015
     */
    int updateByExample(@Param("record") AgentProfitInfo record, @Param("example") AgentProfitInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_agent_profit_info
     *
     * @mbggenerated Wed Sep 09 15:11:37 CST 2015
     */
    int updateByPrimaryKeySelective(AgentProfitInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_agent_profit_info
     *
     * @mbggenerated Wed Sep 09 15:11:37 CST 2015
     */
    int updateByPrimaryKey(AgentProfitInfo record);
}