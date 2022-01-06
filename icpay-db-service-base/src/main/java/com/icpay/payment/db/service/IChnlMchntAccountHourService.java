package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountHour;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountHourKey;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MerAccountInfoSummary;

public interface IChnlMchntAccountHourService {
	
	public Pager<ChnlMchntAccountHour> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	public List<ChnlMchntAccountHour> selectByDate(Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public ChnlMchntAccountHour selectByPrimaryKey(ChnlMchntAccountHourKey key);
	
	public long count(Map<String, String> qryParamMap);
	
}
