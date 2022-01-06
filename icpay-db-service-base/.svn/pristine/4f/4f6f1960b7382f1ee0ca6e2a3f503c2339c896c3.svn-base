package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.ComplaintManage;

public interface IComplaintManageService {
	public List<ComplaintManage> select(Map<String, String> qryParamMap);
	 
	/**
	 * 分页查询会员商户信息
	 */
	public Pager<ComplaintManage> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public ComplaintManage selectByPrimaryKey(int comId);
	
	/**
	 * 新增
	 */
	public int add(ComplaintManage entity);
	
	/**
	 * 修改
	 */
	public void update(ComplaintManage entity);
	
	/**
	 * 删除
	 */
	public void delete(int ComplaintManageId);
}
