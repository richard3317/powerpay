package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountInfoKey;
import com.icpay.payment.db.dao.mybatis.model.modelExt.ChnlAccountInfoSummary;

public interface IChnlMchntAccountInfoService {
	
	public Pager<ChnlMchntAccountInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public ChnlMchntAccountInfo selectByPrimaryKey(ChnlMchntAccountInfoKey key);
	
	/**
	 * 新增
	 */
	public void add(ChnlMchntAccountInfo chnlInfo);
	
	/**
	 * 修改
	 */
	public void update(ChnlMchntAccountInfo chnlInfo);
	
	/**
	 * 删除
	 */
	public void delete(ChnlMchntAccountInfoKey key);
	
	/**
	 * 获取所有信息
	 * @return
	 */
	public List<ChnlMchntAccountInfo> getAllChnlInfo();	
	
	/**
	 * 加总收入
	 */
	public String selectSummaryByChnl(Map<String, String> qryParamMap);
	
	/**
	 * 加总
	 */
	public ChnlAccountInfoSummary selectInfoSummaryByChnl(Map<String, String> qryParamMap);
}
