package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.VirtualTermInfo;
import com.icpay.payment.db.dao.mybatis.model.VirtualTermInfoKey;

public interface IVirtualTermInfoService {
	
	public List<VirtualTermInfo> select(Map<String, String> qryParamMap);

	/**
	 * 分页查询
	 */
	public Pager<VirtualTermInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public VirtualTermInfo selectByPrimarykey(VirtualTermInfoKey key);
	
	/**
	 * 新增
	 */
	public void add(VirtualTermInfo entity);
	
	/**
	 * 修改
	 */
	public void update(VirtualTermInfo entity);
	
	/**
	 * 删除
	 */
	public void delete(VirtualTermInfoKey key);
	
}
