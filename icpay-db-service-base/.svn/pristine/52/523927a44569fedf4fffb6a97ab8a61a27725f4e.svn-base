package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.ChnlInfoMapping;
import com.icpay.payment.db.dao.mybatis.model.ChnlInfoMappingKey;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoSub;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoSubKey;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySub;

public interface IChnlMchntInfoSubService {
	
	public Pager<ChnlMchntInfoSub> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public ChnlMchntInfoSub selectByPrimaryKey(ChnlMchntInfoSubKey key);
	
	/**
	 * 新增
	 */
	public void add(ChnlMchntInfoSub record);
	
	/**
	 * 修改
	 */
	public void update(ChnlMchntInfoSub record);
	
	/**
	 * 删除
	 */
	public void delete(ChnlMchntInfoSubKey key);
	
	/**
	 * 获取所有信息
	 * @return
	 */
	public List<ChnlMchntInfoSub> getAllChnlInfo();	
	
	public Pager<ChnlInfoMapping> selectChnlInfoMappingByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	
	/**
	 * 两表联查
	 */
	public ChnlInfoMapping selectMciAndMcisByPrimarykey(ChnlInfoMappingKey key);
	
	/**
	 * 根据主键No  intTransCd  查询所有信息  
	 */
	public List<ChnlMchntInfoSub> queryAllNoIntTransCd(ChnlMchntInfo key);
	
	public List<ChnlMchntInfoSub> select(String chnlId,String chnlMchntCd);
}
