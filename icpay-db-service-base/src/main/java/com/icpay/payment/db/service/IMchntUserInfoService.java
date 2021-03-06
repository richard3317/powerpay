package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfoKey;

public interface IMchntUserInfoService {
	
    /**
     * Database table : tbl_mchnt_user_info
     *
     * @mbg.generated
     */
    long countByExample(MchntUserInfoExample example);

    /**
     * Database table : tbl_mchnt_user_info
     *
     * @mbg.generated
     */
    int deleteByExample(MchntUserInfoExample example);

    /**
     * Database table : tbl_mchnt_user_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(MchntUserInfoKey key);

    /**
     * Database table : tbl_mchnt_user_info
     *
     * @mbg.generated
     */
    int insert(MchntUserInfo record);

    /**
     * Database table : tbl_mchnt_user_info
     *
     * @mbg.generated
     */
    int insertSelective(MchntUserInfo record);

    /**
     * Database table : tbl_mchnt_user_info
     *
     * @mbg.generated
     */
//    List<MchntUserInfo> selectByPage(MchntUserInfoExample example);
    public Pager<MchntUserInfo> selectByPage(int pageNum, int pageSize,Map<String,String> qryParamMap);

    /**
     * Database table : tbl_mchnt_user_info
     *
     * @mbg.generated
     */
    List<MchntUserInfo> selectByExample(Map<String,String> map);

    /**
     * Database table : tbl_mchnt_user_info
     *
     * @mbg.generated
     */
    MchntUserInfo selectByPrimaryKey(MchntUserInfoKey key);

    /**
     * Database table : tbl_mchnt_user_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(MchntUserInfo record, Map<String,String> map);

    /**
     * Database table : tbl_mchnt_user_info
     *
     * @mbg.generated
     */
    int updateByExample(MchntUserInfo record, MchntUserInfoExample example);

    /**
     * Database table : tbl_mchnt_user_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MchntUserInfo record);

    /**
     * Database table : tbl_mchnt_user_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MchntUserInfo record);	

//	public List<MchntInfo> select(Map<String, String> qryParamMap);
//	 
//	/**
//	 * ??????????????????????????????
//	 */
//	public Pager<MchntInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
//	
//	/**
//	 * ????????????????????????????????????
//	 */
//	public MchntInfo selectByPrimaryKey(String mchntCd, String userId);
//
//	/**
//	 * ????????????????????????
//	 */
//	public void add(MchntInfo mchntInfo);
//	
//	/**
//	 * ????????????????????????
//	 */
//	public void update(MchntInfo mchntInfo);
//	
//	/**
//	 * ??????????????????????????????
//	 * @param mchntInfo
//	 */
//	public void updateLoginInfo(MchntInfo mchntInfo);
//	
//	/**
//	 * ????????????????????????????????????
//	 */
//	public void delete(String mchntCd);
//
//	/**
//	 * ??????????????????Google???????????????
//	 * @param mchntInfo
//	 */
//	public void updateSecretInitState(MchntInfo mchntInfo);
}