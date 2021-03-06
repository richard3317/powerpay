package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.MerSettleAlgorithm;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySub;

public interface IMerSettlePolicySubService {

	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param qryParamMap
	 * @return
	 */
//	public List<MerSettlePolicySub> selectByPage(int pageNum, int pageSize,Map<String, String> qryParamMap);
//	public Pager<SettleAlgorithm> selectByPage(int pageNum, int pageSize,Map<String, String> qryParamMap);
	public Pager<MerSettleAlgorithm> selectByPage(int pageNum, int pageSize,Map<String, String> qryParamMap);
	
	public List<MerSettleAlgorithm> selectByMap(Map<String, String> qryParamMap);
	
	
	
	public List<MerSettlePolicySub> select(String mchntCd);
	
	/*
	 * 增加币别条件
	 */
	public List<MerSettlePolicySub> select(String mchntCd, String currCd);
	
	/**
	 * 根据主键查询
	 */
	public MerSettlePolicySub selectByPrimaryKey(String mchntCd, String intTransCd,String currCd);
	
	/**
	 * 新增
	 */
	public void add(MerSettlePolicySub entity);
	
	/**
	 * 修改
	 */
	public int update(MerSettlePolicySub entity);
	
	/**
	 * 修改
	 */
	public int updateByExampleSelective(MerSettlePolicySub entity);
	
	/**
	 * 删除
	 */
	public void delete(String mchntCd, String intTransCd,String currCd);
	/**
	 * 給批量修改用的查詢
	 */
	public Pager<MerSettleAlgorithm> selectByPage4Update(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 批量修改
	 */
	/*public int batchFixRate(Map<String, String> qryParamMap, String newFixRate,String newMaxfee,String newMinfee,String newTxDayMax,
			String newTxAmtMin,String newTxAmtMax,String newTxCardDayMax,String newTxT0Percent,String comment, String lastOperId);*/
	
//	public int batchFixRate(MerSettlePolicySub entity);
}
