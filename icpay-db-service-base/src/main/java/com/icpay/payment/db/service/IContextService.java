package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.Content;
import com.icpay.payment.db.dao.mybatis.model.TransTypeGroup;

public interface IContextService {
	public List<Content> select(Map<String, String> qryParamMap);
	 
	/**
	 * 分页查询会员商户信息
	 */
	public Pager<Content> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public Content selectByPrimaryKey(int contentId);
	
	/**
	 * 新增
	 */
	public int add(Content entity);
	
	/**
	 * 修改
	 */
	public void update(Content entity);
	
	/**
	 * 删除
	 */
	public void delete(int contentId);
	
	
	
	
	
}
