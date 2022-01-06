package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.AgentFuncInfo;
import com.icpay.payment.db.dao.mybatis.model.AgentFuncInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AgentFuncInfoMapper {
    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    long countByExample(AgentFuncInfoExample example);

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    int deleteByExample(AgentFuncInfoExample example);

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String funcCd);

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    int insert(AgentFuncInfo record);

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    int insertSelective(AgentFuncInfo record);

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    List<AgentFuncInfo> selectByPage(AgentFuncInfoExample example);

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    List<AgentFuncInfo> selectByExample(AgentFuncInfoExample example);

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    AgentFuncInfo selectByPrimaryKey(String funcCd);

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") AgentFuncInfo record, @Param("example") AgentFuncInfoExample example);

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") AgentFuncInfo record, @Param("example") AgentFuncInfoExample example);

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(AgentFuncInfo record);

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(AgentFuncInfo record);
}