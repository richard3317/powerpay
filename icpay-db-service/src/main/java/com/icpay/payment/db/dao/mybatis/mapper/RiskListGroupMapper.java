package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.RiskListGroup;
import com.icpay.payment.db.dao.mybatis.model.RiskListGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RiskListGroupMapper {
    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    long countByExample(RiskListGroupExample example);

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    int deleteByExample(RiskListGroupExample example);

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer groupId);

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    int insert(RiskListGroup record);

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    int insertSelective(RiskListGroup record);

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    List<RiskListGroup> selectByPage(RiskListGroupExample example);

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    List<RiskListGroup> selectByExample(RiskListGroupExample example);

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    RiskListGroup selectByPrimaryKey(Integer groupId);

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") RiskListGroup record, @Param("example") RiskListGroupExample example);

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") RiskListGroup record, @Param("example") RiskListGroupExample example);

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RiskListGroup record);

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(RiskListGroup record);
}