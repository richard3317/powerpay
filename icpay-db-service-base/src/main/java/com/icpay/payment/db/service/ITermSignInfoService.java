package com.icpay.payment.db.service;

import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.TermSignInfo;

public interface ITermSignInfoService {

	/**
	 * 分页查询
	 */
	public Pager<TermSignInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
}