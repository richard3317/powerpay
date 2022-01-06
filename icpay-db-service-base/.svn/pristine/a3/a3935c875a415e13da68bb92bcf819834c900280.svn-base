package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.ChnlInfo;
import com.icpay.payment.db.dao.mybatis.model.MerParams;
import com.icpay.payment.db.dao.mybatis.model.MerParamsKey;

public interface IMerParamsService {
	
	//public Pager<MerParams> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public MerParams selectByPrimaryKey(MerParamsKey key);
	
	/**
	 * 新增
	 */
	public void add(MerParams record);
	
	/**
	 * 修改
	 */
	public void update(MerParams record);
	
	/**
	 * 删除
	 */
	public void delete(MerParamsKey key);
	
	/**
	 * 获取所有信息
	 * @return
	 */
	public List<MerParams> getAllMerParams();	
	
//	/**
//	 * 删除key中所有paramId字段
//	 */
//	public void deleteNoParamId(MerParamsKey mpk);//deleteByPrimaryKeyNoParamId
	
	public List<MerParams> select(Map<String, String> qryParamMap);

}
