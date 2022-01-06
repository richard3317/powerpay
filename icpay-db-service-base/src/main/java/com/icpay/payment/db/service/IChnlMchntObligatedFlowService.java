package com.icpay.payment.db.service;

import java.util.Map;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountFlow;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntObligatedFlow;

public interface IChnlMchntObligatedFlowService {

	/**
	 * 分页查询
	 */
	public Pager<ChnlMchntObligatedFlow> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询取现信息
	 */
	public ChnlMchntObligatedFlow selectByPrimaryKey(String seqId);
	
	/**
	 * 新增
	 */
	public void add(ChnlMchntObligatedFlow chnlMchntObligatedFlow);
	
}