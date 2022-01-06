package com.icpay.payment.db.dao.mybatis.mapper.ExtMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icpay.payment.db.dao.mybatis.model.MerParamsListv;
import com.icpay.payment.db.dao.mybatis.model.MerParamsListvExample;

public interface MerParamsListvExtMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_mer_params_listv
	 * @mbggenerated  Tue Mar 27 10:23:36 CST 2018
	 */
	int countByExample(MerParamsListvExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_mer_params_listv
	 * @mbggenerated  Tue Mar 27 10:23:36 CST 2018
	 */
	int deleteByExample(MerParamsListvExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_mer_params_listv
	 * @mbggenerated  Tue Mar 27 10:23:36 CST 2018
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_mer_params_listv
	 * @mbggenerated  Tue Mar 27 10:23:36 CST 2018
	 */
	int insert(MerParamsListv record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_mer_params_listv
	 * @mbggenerated  Tue Mar 27 10:23:36 CST 2018
	 */
	int insertSelective(MerParamsListv record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_mer_params_listv
	 * @mbggenerated  Tue Mar 27 10:23:36 CST 2018
	 */
	List<MerParamsListv> selectByExample(MerParamsListvExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_mer_params_listv
	 * @mbggenerated  Tue Mar 27 10:23:36 CST 2018
	 */
	MerParamsListv selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_mer_params_listv
	 * @mbggenerated  Tue Mar 27 10:23:36 CST 2018
	 */
	int updateByExampleSelective(@Param("record") MerParamsListv record,
			@Param("example") MerParamsListvExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_mer_params_listv
	 * @mbggenerated  Tue Mar 27 10:23:36 CST 2018
	 */
	int updateByExample(@Param("record") MerParamsListv record, @Param("example") MerParamsListvExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_mer_params_listv
	 * @mbggenerated  Tue Mar 27 10:23:36 CST 2018
	 */
	int updateByPrimaryKeySelective(MerParamsListv record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_mer_params_listv
	 * @mbggenerated  Tue Mar 27 10:23:36 CST 2018
	 */
	int updateByPrimaryKey(MerParamsListv record);
	
	public List<Long> getMerParamsListvKey(MerParamsListv mp);
}