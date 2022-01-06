package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfoKey;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfoView;

public interface IChnlCashPoolInfoService {
	
	public Pager<ChnlCashPoolInfoView> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public ChnlCashPoolInfo selectByPrimaryKey(ChnlCashPoolInfoKey key);
	
	/**
	 * 新增
	 */
	public void add(ChnlCashPoolInfo ChnlCashPoolInfo);
	
	/**
	 * 修改
	 */
	public void update(ChnlCashPoolInfo ChnlCashPoolInfo);
	
	/**
	 * 删除
	 */
	public void delete(ChnlCashPoolInfoKey key);
	
	/**
	 * 查询是否存在其他资金池
	 * @param mchntCd
	 * @return
	 */
	public List<ChnlCashPoolInfo> select(String mchntCd);
	
	/**
	 * 根据poolid 查询是否有大商户配置
	 * @param poolId
	 * @return
	 */
	public List<ChnlCashPoolInfo> selectByPoolId(String poolId);
	
	/**
	 * 通道余额加总
	 */
	public String selectSummaryByChnl(Map<String, String> qryParamMap);
	
}
