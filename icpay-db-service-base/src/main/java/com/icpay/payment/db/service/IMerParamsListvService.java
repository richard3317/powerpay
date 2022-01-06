package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.MerParamsListv;

public interface IMerParamsListvService {
	
	//public Pager<MerParamsListv> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public MerParamsListv selectByPrimaryKey(Long id);
	
	/**
	 * 新增
	 */
	public void add(MerParamsListv record);
	
	/**
	 * 修改
	 */
	public void update(MerParamsListv record);
	
	/**
	 * 删除
	 */
	public void delete(Long Id);
	
	/**
	 * 获取所有信息
	 * @return
	 */
	public List<MerParamsListv> getAllMerParamsListv();	
	
	
	/**
	 * 获取需要删除的主键
	 * @return
	 */
	public List<Long> getMerParamsListvKey(MerParamsListv mp);//getMerParamsListvKey
	
	
	}
