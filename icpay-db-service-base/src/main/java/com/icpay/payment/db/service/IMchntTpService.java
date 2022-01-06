package com.icpay.payment.db.service;

import java.util.List;

import com.icpay.payment.db.dao.mybatis.model.MchntTp;

public interface IMchntTpService {

	public List<MchntTp> selectAll();
	
}
