
package com.icpay.payment.db.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icpay.payment.db.dao.mybatis.model.RiskListItemMer;
import com.icpay.payment.db.dao.mybatis.model.RiskListItemMerExample;


@Deprecated
public interface RiskListItemMer_BakMapper {
   
    List<RiskListItemMer> selectByPage(RiskListItemMerExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_risk_list_item_mer
	 * @mbggenerated  Mon Mar 26 16:02:20 CST 2018
	 */
	int countByExample(RiskListItemMerExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_risk_list_item_mer
	 * @mbggenerated  Mon Mar 26 16:02:20 CST 2018
	 */
	int deleteByExample(RiskListItemMerExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_risk_list_item_mer
	 * @mbggenerated  Mon Mar 26 16:02:20 CST 2018
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_risk_list_item_mer
	 * @mbggenerated  Mon Mar 26 16:02:20 CST 2018
	 */
	int insert(RiskListItemMer record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_risk_list_item_mer
	 * @mbggenerated  Mon Mar 26 16:02:20 CST 2018
	 */
	int insertSelective(RiskListItemMer record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_risk_list_item_mer
	 * @mbggenerated  Mon Mar 26 16:02:20 CST 2018
	 */
	List<RiskListItemMer> selectByExample(RiskListItemMerExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_risk_list_item_mer
	 * @mbggenerated  Mon Mar 26 16:02:20 CST 2018
	 */
	RiskListItemMer selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_risk_list_item_mer
	 * @mbggenerated  Mon Mar 26 16:02:20 CST 2018
	 */
	int updateByExampleSelective(@Param("record") RiskListItemMer record,
			@Param("example") RiskListItemMerExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_risk_list_item_mer
	 * @mbggenerated  Mon Mar 26 16:02:20 CST 2018
	 */
	int updateByExample(@Param("record") RiskListItemMer record, @Param("example") RiskListItemMerExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_risk_list_item_mer
	 * @mbggenerated  Mon Mar 26 16:02:20 CST 2018
	 */
	int updateByPrimaryKeySelective(RiskListItemMer record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_risk_list_item_mer
	 * @mbggenerated  Mon Mar 26 16:02:20 CST 2018
	 */
	int updateByPrimaryKey(RiskListItemMer record);

}
