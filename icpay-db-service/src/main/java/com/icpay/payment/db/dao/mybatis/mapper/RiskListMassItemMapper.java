package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.RiskListMassItem;
import com.icpay.payment.db.dao.mybatis.model.RiskListMassItemExample;
import com.icpay.payment.db.dao.mybatis.model.RiskListMassItemKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RiskListMassItemMapper {
    /**
     * Database table : tbl_risk_list_mass_item
     *
     * @mbg.generated
     */
    long countByExample(RiskListMassItemExample example);

    /**
     * Database table : tbl_risk_list_mass_item
     *
     * @mbg.generated
     */
    int deleteByExample(RiskListMassItemExample example);

    /**
     * Database table : tbl_risk_list_mass_item
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(RiskListMassItemKey key);

    /**
     * Database table : tbl_risk_list_mass_item
     *
     * @mbg.generated
     */
    int insert(RiskListMassItem record);

    /**
     * Database table : tbl_risk_list_mass_item
     *
     * @mbg.generated
     */
    int insertSelective(RiskListMassItem record);

    /**
     * Database table : tbl_risk_list_mass_item
     *
     * @mbg.generated
     */
    List<RiskListMassItem> selectByPage(RiskListMassItemExample example);

    /**
     * Database table : tbl_risk_list_mass_item
     *
     * @mbg.generated
     */
    List<RiskListMassItem> selectByExample(RiskListMassItemExample example);

    /**
     * Database table : tbl_risk_list_mass_item
     *
     * @mbg.generated
     */
    RiskListMassItem selectByPrimaryKey(RiskListMassItemKey key);

    /**
     * Database table : tbl_risk_list_mass_item
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") RiskListMassItem record, @Param("example") RiskListMassItemExample example);

    /**
     * Database table : tbl_risk_list_mass_item
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") RiskListMassItem record, @Param("example") RiskListMassItemExample example);

    /**
     * Database table : tbl_risk_list_mass_item
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RiskListMassItem record);

    /**
     * Database table : tbl_risk_list_mass_item
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(RiskListMassItem record);
}