package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntSettlePolicy;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntSettlePolicyKey;

public interface IChnlMchntSettlePolicyService {
	
	public Pager<ChnlMchntSettlePolicy> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public ChnlMchntSettlePolicy selectByPrimaryKey(ChnlMchntSettlePolicyKey key);
	
	/**
	 * 新增
	 */
	public void add(ChnlMchntSettlePolicy record);
	
	/**
	 * 修改
	 */
	public void update(ChnlMchntSettlePolicy record);
	
	/**
	 * 删除
	 */
	public void delete(ChnlMchntSettlePolicyKey key);
	
	public List<ChnlMchntSettlePolicy> select(String chnlId,String chnlMchntCd);
	
}
