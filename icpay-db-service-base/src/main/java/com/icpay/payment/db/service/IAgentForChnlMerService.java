package com.icpay.payment.db.service;

import java.util.List;

import com.icpay.payment.db.dao.mybatis.model.AgentForChnlMer;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentForChnlMerEx;

public interface IAgentForChnlMerService {
	List<AgentForChnlMer> selectByAgent(String agentCd);
	List<AgentForChnlMerEx> selectByAgentEx(String agentCd);
}
