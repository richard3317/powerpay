package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfoKey;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MerAccountInfoSummary;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MerAccountInfoSummaryAddRealAavailable;

public interface IMchntAccountService {

	public List<MerAccountInfo> select(Map<String, String> qryParamMap);
	
	public List<MerAccountInfo> selectByExample(MerAccountInfoExample example);

	/**
	 * 分页查询会员商户账户信息
	 */
	public Pager<MerAccountInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键获取会员商户账户信息
	 */
	public MerAccountInfo selectByPrimaryKey(MerAccountInfoKey key);

	/**
	 * 新增会员商户账户信息
	 */
	public void add(MerAccountInfo merAccountInfo);
	
	/**
	 * 更新会员商户账户信息
	 */
	public void update(MerAccountInfo merAccountInfo);
	
	/**
	 * 根据主键删除会员商户账户信息
	 */
	public void delete(MerAccountInfoKey key);
	
	/**
	 * 加总收入
	 */
	public String selectSummary(Map<String, String> qryParamMap);
	
	/**
	 * 加总
	 */
	public MerAccountInfoSummaryAddRealAavailable selectInfoSummary(Map<String, String> qryParamMap);
}