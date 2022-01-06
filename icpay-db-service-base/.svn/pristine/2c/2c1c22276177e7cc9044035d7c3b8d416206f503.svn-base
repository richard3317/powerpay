package com.icpay.payment.db.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.ExchangeRate;
import com.icpay.payment.db.dao.mybatis.model.ExchangeRateKey;

public interface IExchangeRateService {
	
	public Pager<ExchangeRate> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public ExchangeRate selectByPrimaryKey(ExchangeRateKey key);
	
	List<ExchangeRate> select(Map<String, String> qryParamMap);
	
	/**
	 * 汇率兑换计算
	 * @param cat 分类，目前请设定"BITPAY"
	 * @param intTransCd 交易类
	 * @param txnAmt 交易金额
	 * @param txnUnit 交易金额的单位
	 * @param currency 交易金额的币别
	 * @param currencyTarget 目标币别
	 * @return 回传兑换后的结果
	 */
	ExchangeRate calcExchangeRate(String cat, String intTransCd, 
			BigDecimal txnAmt, BigDecimal txnUnit, String currency, String currencyTarget);
		
}
