package com.icpay.payment.db.service;

import java.util.List;

import com.icpay.payment.db.dao.mybatis.model.BankNums;

public interface IBankNumsService {

	/**
	 * 查询交易中银行的联行号
	 */
	public List<BankNums> qryBankNumLst();
	
	/**
	 * 查询商户在代付路由上所配置的渠道, 各渠道所匹配到联行号之集合
	 */
	public List<BankNums> qryBankNumLstByChnlsAndCurrCd(List<String> uniqueChnlsId, String currd);
	
	/**
	 * 藉由币别找银行
	 */
	public List<BankNums> qryBankNumLstByCurrCd(String currCd);
	
}
