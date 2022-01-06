package com.icpay.payment.db.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.TelegramUserInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.TelegramUserInfo;
import com.icpay.payment.db.dao.mybatis.model.TelegramUserInfoExample;
import com.icpay.payment.db.dao.mybatis.model.TelegramUserInfoExample.Criteria;
import com.icpay.payment.db.service.ITelegramUserInfoService;

@Service("TelegramUserInfoService")
public class TelegramUserInfoService extends BaseService implements ITelegramUserInfoService {

	private TelegramUserInfoMapper getMapper() {
		return this.getMapper(TelegramUserInfoMapper.class);
	}

	@Override
	public List<TelegramUserInfo> select(Map<String, String> qryParamMap) {
		TelegramUserInfoExample example = this.buildQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}

	@Override
	public int add(TelegramUserInfo usrInfo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected TelegramUserInfoExample buildQryExample(Map<String, String> qryParamMap) {
		TelegramUserInfoExample example = new TelegramUserInfoExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			String mchntCd = qryParamMap.get("mchntCd");
			if (!StringUtil.isBlank(mchntCd)) {
				System.out.println("mchntCd"+mchntCd+"帳號");
				c.andMchntCdEqualTo(mchntCd);
			}

			String mchntPass = qryParamMap.get("mchntPass");
			if (!StringUtil.isBlank(mchntPass)) {
				System.out.println("mchntPass"+mchntPass+"密碼");
				c.andMchntPassEqualTo(mchntPass);
			}

			String state = qryParamMap.get("state");
			if (!StringUtil.isBlank(state)) {
				c.andStateEqualTo(state);
			}

		}
		return example;
	}
}
