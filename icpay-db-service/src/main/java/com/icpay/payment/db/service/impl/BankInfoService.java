package com.icpay.payment.db.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.db.dao.mybatis.mapper.BankInfoMapper;
import com.icpay.payment.db.dao.mybatis.mapper.ExtMapper.BankInfoExtMapper;
import com.icpay.payment.db.dao.mybatis.model.BankInfo;
import com.icpay.payment.db.dao.mybatis.model.BankInfoExample;
import com.icpay.payment.db.dao.mybatis.model.BankInfoExample.Criteria;
import com.icpay.payment.db.service.IBankInfoService;

@Service("bankInfoService")
public class BankInfoService extends BaseService implements IBankInfoService {

	@Override
	public List<String> qryBankNmLst() {
		return super.getMapper(BankInfoExtMapper.class).selectBankNameLst();
	}
	
	@Override
	public List<BankInfo> qryBankListByBankName(String bankName) {
		AssertUtil.strIsBlank(bankName, "bankName is blank.");
		BankInfoExample example = new BankInfoExample();
		Criteria c = example.createCriteria();
		c.andBankNameEqualTo(bankName);
		return this.getMapper().selectByExample(example);
	}

	private BankInfoMapper getMapper() {
		return super.getMapper(BankInfoMapper.class);
	}

	@Override
	public BankInfo qryBankInfoByBankBranchInfo(String bankBranchName,
			String bankCode) {
		AssertUtil.strIsBlank(bankBranchName, "bankBranchName is blank.");
		AssertUtil.strIsBlank(bankCode, "bankCode is blank.");
		BankInfoExample example = new BankInfoExample();
		Criteria c = example.createCriteria();
		c.andBranchBankNameEqualTo(bankBranchName);
		c.andBankCdEqualTo(bankCode);
		List<BankInfo> lst = this.getMapper().selectByExample(example);
		if (lst == null || lst.size() != 1) {
			return null;
		} else {
			return lst.get(0);
		}
	}
}
