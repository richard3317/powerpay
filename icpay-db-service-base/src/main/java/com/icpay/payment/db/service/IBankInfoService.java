package com.icpay.payment.db.service;

import java.util.List;

import com.icpay.payment.db.dao.mybatis.model.BankInfo;

public interface IBankInfoService {

	public List<String> qryBankNmLst();
	
	public List<BankInfo> qryBankListByBankName(String bankName);
	
	public BankInfo qryBankInfoByBankBranchInfo(String bankBranchName, String bankCode);
}
