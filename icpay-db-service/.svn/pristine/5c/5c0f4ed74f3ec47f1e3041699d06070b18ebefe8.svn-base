package com.icpay.payment.db.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.mapper.MerBanksMapper;
import com.icpay.payment.db.dao.mybatis.model.MerBanks;
import com.icpay.payment.db.dao.mybatis.model.MerBanksExample;
import com.icpay.payment.db.service.IMerBanksService;

@Service("merBanksService")
public class MerBanksService extends BaseService implements IMerBanksService {

	@Override
	public List<MerBanks> select(String intTransCd, String mchntCd, String currCd) {
		MerBanksExample example = new MerBanksExample();
		example.createCriteria()
			.andIntTransCdEqualTo(intTransCd)
			.andMchntCdEqualTo(mchntCd)
			.andCurrCdEqualTo(currCd);
		example.setOrderByClause("sort_seq asc");
		MerBanksMapper svc = this.getMapper(MerBanksMapper.class);
		List<MerBanks> list= svc.selectByExample(example);
		return Utils.unduplicate(list);
	}

}
