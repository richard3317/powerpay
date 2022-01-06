package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.ChnlInfo;
import com.icpay.payment.db.dao.mybatis.model.MerSettleAlgorithm;
import com.icpay.payment.db.dao.mybatis.model.OffPayBank;
import com.icpay.payment.db.dao.mybatis.model.TransProof;
import com.icpay.payment.db.dao.mybatis.model.TxnLogView;

public interface ITransProofService {
	
	/**
	 * 查询
	 */
	public List<TransProof> selectByMap(Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询交易信息
	 */
	public TransProof selectByPrimaryKey(String txnId);
	
	/**
	 * 新增
	 */
	public void add(TransProof transProof);
	
}
