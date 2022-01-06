package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.ChnlInfo;

public interface IChnlInfoService {
	
	public Pager<ChnlInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public ChnlInfo selectByPrimaryKey(String chnlId);
	
	/**
	 * 新增
	 */
	public void add(ChnlInfo chnlInfo);
	
	/**
	 * 修改
	 */
	public void update(ChnlInfo chnlInfo);
	
	/**
	 * 删除
	 */
	public void delete(String chnlId);
	
	/**
	 * 获取所有信息
	 * @return
	 */
	public List<ChnlInfo> getAllChnlInfo();	
}
