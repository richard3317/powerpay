package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.RiskListItemMer;
import com.icpay.payment.db.dao.mybatis.model.RiskListItemMerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RiskListItemMerMapper {
    /**
     * Database table : tbl_risk_list_item_mer
     *
     * @mbg.generated
     */
    long countByExample(RiskListItemMerExample example);

    /**
     * Database table : tbl_risk_list_item_mer
     *
     * @mbg.generated
     */
    int deleteByExample(RiskListItemMerExample example);

    /**
     * Database table : tbl_risk_list_item_mer
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * Database table : tbl_risk_list_item_mer
     *
     * @mbg.generated
     */
    int insert(RiskListItemMer record);

    /**
     * Database table : tbl_risk_list_item_mer
     *
     * @mbg.generated
     */
    int insertSelective(RiskListItemMer record);

    /**
     * Database table : tbl_risk_list_item_mer
     *
     * @mbg.generated
     */
    List<RiskListItemMer> selectByPage(RiskListItemMerExample example);

    /**
     * Database table : tbl_risk_list_item_mer
     *
     * @mbg.generated
     */
    List<RiskListItemMer> selectByExample(RiskListItemMerExample example);

    /**
     * Database table : tbl_risk_list_item_mer
     *
     * @mbg.generated
     */
    RiskListItemMer selectByPrimaryKey(Long id);

    /**
     * Database table : tbl_risk_list_item_mer
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") RiskListItemMer record, @Param("example") RiskListItemMerExample example);

    /**
     * Database table : tbl_risk_list_item_mer
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") RiskListItemMer record, @Param("example") RiskListItemMerExample example);

    /**
     * Database table : tbl_risk_list_item_mer
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RiskListItemMer record);

    /**
     * Database table : tbl_risk_list_item_mer
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(RiskListItemMer record);
}