package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.AgentAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.AgentAccountInfoKey;

public interface IAgentBankAccsService {
    
    List<AgentAccountInfo> select(Map<String, String> qryParamMap);
    List<AgentAccountInfo> selectByAgent(String agentCd, String... accTypes);
    
	Pager<AgentAccountInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
    AgentAccountInfo selectByPrimaryKey(AgentAccountInfoKey key);

    void add(AgentAccountInfo record);
    void update(AgentAccountInfo record);
    void delete(AgentAccountInfo record);

}
