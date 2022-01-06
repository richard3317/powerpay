package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.AgentInfo;
import com.icpay.payment.db.dao.mybatis.model.BmUserInfo;
import com.icpay.payment.db.dao.mybatis.model.MerBanks;
import com.icpay.payment.db.dao.mybatis.model.MerBanksExample;
import com.icpay.payment.db.dao.mybatis.model.TelegramMerchatid;
import com.icpay.payment.db.dao.mybatis.model.TelegramMerchatidExample;
import com.icpay.payment.db.dao.mybatis.model.TxnRouting;
import com.icpay.payment.db.dao.mybatis.model.TxnRoutingKey;
import com.icpay.payment.db.dao.mybatis.model.TxnRouting_Mapping;

public interface ITelegramMerchatidService {
	
	
	
	public List<TelegramMerchatid> select(Map<String, String> qryParamMap);
	/**
	 * 新增
	 * @param usrInfo
	 */
	public int add(TelegramMerchatid usrInfo);
	
}
