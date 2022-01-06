package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.ChnlInfo;
import com.icpay.payment.db.dao.mybatis.model.MerSettleAlgorithm;
import com.icpay.payment.db.dao.mybatis.model.OffPayBank;

public interface IOffPayBankService {
	
	public Pager<OffPayBank> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	public List<OffPayBank> selectByMap(Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public OffPayBank selectByPrimaryKey(String catalog, String accNo);
	
	/**
	 * 新增
	 */
	public void add(OffPayBank offPayBank);
	
	/**
	 * 修改
	 */
	public void update(OffPayBank offPayBank);
	
	/**
	 * 删除
	 */
	public void delete(String catalog, String accNo);
	
	/**
	 * 获取所有信息
	 * @return
	 */
	public List<OffPayBank> getAllOffPayBank();	
	
	public OffPayBank select(String mchntCd);
}
