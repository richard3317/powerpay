package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;

public interface IMchntInfoService {

	public List<MchntInfo> select(Map<String, String> qryParamMap);
	 
	/**
	 * 分页查询会员商户信息
	 */
	public Pager<MchntInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键获取会员商户信息
	 */
	public MchntInfo selectByPrimaryKey(String mchntCd);

	/**
	 * 新增会员商户信息
	 */
	public void add(MchntInfo mchntInfo);
	
	/**
	 * 更新会员商户信息
	 */
	public void update(MchntInfo mchntInfo);
	
	/**
	 * 更新用户登录相关信息
	 * @param mchntInfo
	 */
	public void updateLoginInfo(MchntInfo mchntInfo);
	
	/**
	 * 根据主键删除会员商户信息
	 */
	public void delete(String mchntCd);

	/**
	 * 根据主键更新Google验证的信息
	 * @param mchntInfo
	 */
	public void updateSecretInitState(MchntInfo mchntInfo);
	
	/**
	 * 新增后回传商户号
	 * @param mchntInfo
	 */
	public String addAndReturnMchntCd(MchntInfo mchntInfo);
}