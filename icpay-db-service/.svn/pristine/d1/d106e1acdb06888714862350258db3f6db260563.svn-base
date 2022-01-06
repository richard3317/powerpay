package com.icpay.payment.db.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.icpay.payment.db.dao.mybatis.mapper.MchntTpMapper;
import com.icpay.payment.db.dao.mybatis.model.MchntTp;
import com.icpay.payment.db.dao.mybatis.model.MchntTpExample;
import com.icpay.payment.db.service.IMchntTpService;

@Service("mchntTpService")
public class MchntTpService extends BaseService implements IMchntTpService {

	@Override
	public List<MchntTp> selectAll() {
		MchntTpExample example = new MchntTpExample();
		return this.getMapper(MchntTpMapper.class).selectByExample(example);
	}

}
