package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.db.dao.mybatis.model.TransProfitReport;

public interface IProfitRptInfoService {

	/**
	 * 查询每日财报
	 */
	public List <TransProfitReport> selectProfit(Map<String,String> map);
}
