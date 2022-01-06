package com.icpay.payment.db.dao.mybatis.mapper.ExtMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icpay.payment.db.dao.mybatis.model.ChnlInfoMapping;
import com.icpay.payment.db.dao.mybatis.model.ChnlInfoMappingExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlInfoMappingKey;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoSub;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoSubExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoSubKey;

public interface ChnlMchntInfoSubExtMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_chnl_mchnt_info_sub
	 * @mbggenerated  Sat Mar 24 10:13:27 CST 2018
	 */
	int countByExample(ChnlMchntInfoSubExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_chnl_mchnt_info_sub
	 * @mbggenerated  Sat Mar 24 10:13:27 CST 2018
	 */
	int deleteByExample(ChnlMchntInfoSubExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_chnl_mchnt_info_sub
	 * @mbggenerated  Sat Mar 24 10:13:27 CST 2018
	 */
	int deleteByPrimaryKey(ChnlMchntInfoSubKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_chnl_mchnt_info_sub
	 * @mbggenerated  Sat Mar 24 10:13:27 CST 2018
	 */
	int insert(ChnlMchntInfoSub record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_chnl_mchnt_info_sub
	 * @mbggenerated  Sat Mar 24 10:13:27 CST 2018
	 */
	int insertSelective(ChnlMchntInfoSub record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_chnl_mchnt_info_sub
	 * @mbggenerated  Sat Mar 24 10:13:27 CST 2018
	 */
	List<ChnlMchntInfoSub> selectByExample(ChnlMchntInfoSubExample example);
	List<ChnlMchntInfoSub> selectByPage(ChnlMchntInfoSubExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_chnl_mchnt_info_sub
	 * @mbggenerated  Sat Mar 24 10:13:27 CST 2018
	 */
	ChnlMchntInfoSub selectByPrimaryKey(ChnlMchntInfoSubKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_chnl_mchnt_info_sub
	 * @mbggenerated  Sat Mar 24 10:13:27 CST 2018
	 */
	int updateByExampleSelective(@Param("record") ChnlMchntInfoSub record,
			@Param("example") ChnlMchntInfoSubExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_chnl_mchnt_info_sub
	 * @mbggenerated  Sat Mar 24 10:13:27 CST 2018
	 */
	int updateByExample(@Param("record") ChnlMchntInfoSub record, @Param("example") ChnlMchntInfoSubExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_chnl_mchnt_info_sub
	 * @mbggenerated  Sat Mar 24 10:13:27 CST 2018
	 */
	int updateByPrimaryKeySelective(ChnlMchntInfoSub record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tbl_chnl_mchnt_info_sub
	 * @mbggenerated  Sat Mar 24 10:13:27 CST 2018
	 */
	int updateByPrimaryKey(ChnlMchntInfoSub record);
	//-----------------------------
	int countByExampleByMapping(ChnlInfoMappingExample example);
	List<ChnlInfoMapping> selectByPageByMapping(ChnlInfoMappingExample example);
	
	
	
	
}