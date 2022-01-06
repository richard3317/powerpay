package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.TransRptInfo;

public interface ITransRptInfoService {
	public List<TransRptInfo> select(Map<String, String> qryParamMap);
	 
	/**
	 * 分页查询会员商户信息
	 */
	public Pager<TransRptInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	
	
	
}
