package com.icpay.payment.db.service;

import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.AgentAccCheckFile;

public interface IAgentAccCheckFileService {
	
	public Pager<AgentAccCheckFile> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	public AgentAccCheckFile selectByPrimaryKey(int seqId);

}
