package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfoView;
import com.icpay.payment.db.dao.mybatis.model.MerCashPoolInfo;
import com.icpay.payment.db.dao.mybatis.model.MerCashPoolInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MerCashPoolInfoKey;
import com.icpay.payment.db.dao.mybatis.model.RptInfo;
import com.icpay.payment.db.dao.mybatis.model.TxnRouting;

public interface IMerCashPoolInfoService {
	
	public Pager<MerCashPoolInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	public List<MerCashPoolInfo> select(MerCashPoolInfoExample qryParamMap);

	
	/**
	 * 根据主键查询
	 */
	public MerCashPoolInfo selectByPrimaryKey(MerCashPoolInfoKey key);
	
	/**
	 * 新增
	 */
	public void add(MerCashPoolInfo MerCashPoolInfo);
	
	/**
	 * 修改
	 */
	public void update(MerCashPoolInfo MerCashPoolInfo,Map<String, String> qryParamMap);
	
	/**
	 * 修改
	 * @param MerCashPoolInfo
	 */
	public void updateByPrimaryKey(MerCashPoolInfo MerCashPoolInfo);
	
	/**
	 * 删除
	 */
	public void delete(MerCashPoolInfoKey key);
	
	/**
	 * 查询是否存在其他资金池
	 * @param mchntCd
	 * @return
	 */
	public List<MerCashPoolInfo> select(String mchntCd);
	
	/**
	 * 根据poolid 查询是否有商户配置
	 * @param poolId
	 * @return
	 */
	public List<MerCashPoolInfo> selectByPoolId(String poolId);
	
	/**
	 * 批量修改
	 * @param qryParamMap
	 * @param txnRouting
	 * @return
	 */
	public int batchUpdate(Map<String, String> qryParamMap, MerCashPoolInfo merCashPoolInfo);
	
	/**
	 * 批量删除
	 * @param qryParamMap
	 * @return
	 */
	public int batchDelete(Map<String, String> qryParamMap);

	/**
	 * 批量新增
	 * @param mchntCds 商户号列表，可以,;或\n区隔
	 * @param tr 待新增的资料
	 * @return
	 */
	int batchAdd(List<String> mchntCdList , MerCashPoolInfo merCashPoolInfo);
}
