package com.icpay.payment.db.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.db.dao.mybatis.mapper.TxnRoutingInfoWdPoolMapper;
import com.icpay.payment.db.dao.mybatis.model.TxnRoutingInfoWdPool;
import com.icpay.payment.db.dao.mybatis.model.TxnRoutingInfoWdPoolExample;
import com.icpay.payment.db.service.ITxnRoutingInfoWdPoolService;

@Service("txnRoutingInfoWdPoolService")
public class TxnRoutingInfoWdPoolService extends BaseService implements ITxnRoutingInfoWdPoolService {
	
	@Override
	public List<TxnRoutingInfoWdPool> select(Map<String, String> qryParamMap) {
		TxnRoutingInfoWdPoolExample example = new TxnRoutingInfoWdPoolExample();
		example.createCriteria()
		.andIntTransCdEqualTo(qryParamMap.get("int_trans_cd"))
		.andMchntCdEqualTo(qryParamMap.get("mchnt_cd"))
		.andCurrCdEqualTo(qryParamMap.get("curr_cd"))
		.andRouteStatusEqualTo(qryParamMap.get("route_status"))
		.andAccountStateEqualTo(qryParamMap.get("account_state"));
		
		return this.getMapper().selectByExample(example);
	}
	
	private TxnRoutingInfoWdPoolMapper getMapper() {
		return this.getMapper(TxnRoutingInfoWdPoolMapper.class);
	}


}
