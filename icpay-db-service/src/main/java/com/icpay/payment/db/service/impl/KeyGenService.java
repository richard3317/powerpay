package com.icpay.payment.db.service.impl;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.db.service.IKeyGenService;

@Service("keyGenService")
public class KeyGenService extends BaseService implements IKeyGenService {
	
	@Override
	public synchronized int genRiskKey() {
		return super.genIntKey(CommonEnums.PrimaryKeyTp._01);
	}
	
	@Override
	public synchronized int genMchntCdKey() {
		return super.genIntKey(CommonEnums.PrimaryKeyTp._03);
	}
}
