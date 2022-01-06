package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.CashPoolInfo;

public interface ICashPoolInfoService {
	
	public Pager<CashPoolInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public CashPoolInfo selectByPrimaryKey(String chnlId);
	
	/**
	 * 新增
	 */
	public void add(CashPoolInfo CashPoolInfo);
	
	/**
	 * 修改
	 */
	public void update(CashPoolInfo CashPoolInfo);
	
	/**
	 * 删除
	 */
	public void delete(String chnlId);
	
	/**
	 * 获取所有信息
	 * @return
	 */
	public List<CashPoolInfo> getAllCashPoolInfo();	
	
	/**
	 * 
	 * @return
	 */
	public List<CashPoolInfo> getCashPoolInfo();
}
