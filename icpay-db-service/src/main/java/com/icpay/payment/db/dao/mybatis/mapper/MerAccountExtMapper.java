package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfoExample;
import com.icpay.payment.db.dao.mybatis.model.modelExt.ChnlAccountInfoSummary;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MerAccountInfoSummaryAddRealAavailable;

public interface MerAccountExtMapper {
	/**
	 * 渠道商户 收入加总
	 * @param qryParamMap
	 * @return
	 */
	public String selectSummaryByChnl(ChnlMchntAccountInfoExample example);
	
	/**
	 * 收入加总
	 * @param qryParamMap
	 * @return
	 */
	public String selectSummary(MerAccountInfoExample example);
	
	/**
	 * 加总
	 * @param qryParamMap
	 * @return
	 */
	public MerAccountInfoSummaryAddRealAavailable selectInfoSummary(MerAccountInfoExample qryParamMap);
	
	/**
	 * 渠道商户 加总
	 * @param qryParamMap
	 * @return
	 */
	public ChnlAccountInfoSummary selectInfoSummaryByChnl(ChnlMchntAccountInfoExample example);
	
}