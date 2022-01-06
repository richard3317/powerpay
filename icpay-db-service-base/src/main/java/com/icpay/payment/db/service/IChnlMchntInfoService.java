package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoKey;

public interface IChnlMchntInfoService {
	
	public Pager<ChnlMchntInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public ChnlMchntInfo selectByPrimaryKey(ChnlMchntInfoKey key);
	
	/**
	 * 新增
	 */
	public void add(ChnlMchntInfo record);
	
	/**
	 * 修改
	 */
	public void update(ChnlMchntInfo record);
	
	/**
	 * 删除
	 */
	public void delete(ChnlMchntInfoKey key);
	
	/**
	 * 获取所有信息
	 * @return
	 */
	public List<ChnlMchntInfo> getAllChnlInfo();	
}
