package com.icpay.payment.db.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.icpay.payment.db.dao.mybatis.mapper.BankNumsMapper;
import com.icpay.payment.db.dao.mybatis.mapper.ExtMapper.BankNumsExtMapper;
import com.icpay.payment.db.dao.mybatis.model.BankNums;
import com.icpay.payment.db.dao.mybatis.model.BankNumsExample;
import com.icpay.payment.db.service.IBankNumsService;

@Service("bankNumsService")
public class BankNumsService extends BaseService implements IBankNumsService {

	@Override
	public List<BankNums> qryBankNumLst() {
		BankNumsExample example = new BankNumsExample();
		example.setOrderByClause("sort_seq asc, bank_num asc");
		return this.getMapper(BankNumsMapper.class).selectByExample(example);
	}
	
	public List<BankNums> qryBankNumLstByChnlsAndCurrCd(List<String> uniqueChnlsId, String currCd) {
		return this.getMapper(BankNumsExtMapper.class).selectByChnlsAndCurrCd(uniqueChnlsId, currCd);
	}

	@Override
	public List<BankNums> qryBankNumLstByCurrCd(String currCd) {
		BankNumsExample example = new BankNumsExample();
		example.or().andCurrCdEqualTo(currCd);
		example.setOrderByClause("sort_seq asc, bank_num asc");
		return this.getMapper(BankNumsMapper.class).selectByExample(example);
	}

}
