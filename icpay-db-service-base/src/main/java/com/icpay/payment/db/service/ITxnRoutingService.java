package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.TxnRouting;
import com.icpay.payment.db.dao.mybatis.model.TxnRoutingKey;
import com.icpay.payment.db.dao.mybatis.model.TxnRouting_Mapping;

public interface ITxnRoutingService {
	
	public List<TxnRouting> select(Map<String, String> qryParamMap);
	
	/**
	   * 在路由表上, 符合 交易类型是52带头, 状态是有效, 代入 商户号, 币别, 去找符合的渠道编号
	 */
	public List<TxnRoutingKey> selectByMchntCdAndCurrCd(TxnRoutingKey key);
	
	public Pager<TxnRouting_Mapping> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public TxnRouting selectByPrimaryKey(TxnRoutingKey key);
	
	/**
	 * 新增
	 */
	public void add(TxnRouting routInfo);
	
	/**
	 * 修改
	 */
	public void update(TxnRouting routInfo);
	
	/**
	 * 删除
	 */
	public void delete(TxnRoutingKey key);
	
	/**
	 * 批量修改
	 * @param qryParamMap
	 * @param txnRouting
	 * @return
	 */
	public int batchUpdate(Map<String, String> qryParamMap, TxnRouting txnRouting);
	
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
	int batchAdd(String mchntCds, TxnRouting tr);
}
