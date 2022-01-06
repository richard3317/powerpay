package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountFlow;

public interface IChnlMchntAccountFlowService {

	public List<ChnlMchntAccountFlow> select(String mon, Map<String, String> qryParamMap);
	
	/**
	 * 分页查询取现信息
	 */
	public Pager<ChnlMchntAccountFlow> selectByPage(int pageNum, int pageSize, String mon, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询取现信息
	 */
	public ChnlMchntAccountFlow selectByPrimaryKey(String seqId, String mon);
	
	/**
	 * 修改
	 */
	public void update(ChnlMchntAccountFlow chnlMchntAccountFlow);
	
	/**
	 * 加總
	 * @param mon
	 * @param qryParamMap
	 * @return
	 */
	public ChnlMchntAccountFlow selectSummary(String mon, Map<String, String> qryParamMap);
	
}