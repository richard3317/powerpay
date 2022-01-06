package com.icpay.payment.db.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.AgentProfitTaskLogMapper;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitTaskLog;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitTaskLogExample;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitTaskLogExample.Criteria;
import com.icpay.payment.db.service.IAgentProfitTaskLogService;

@Service("agentProfitTaskLogService")
public class AgentProfitTaskLogService extends BaseService implements IAgentProfitTaskLogService {

	@Override
	public Pager<AgentProfitTaskLog> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		AgentProfitTaskLogExample example = this.getQryExample(qryParamMap);
		AgentProfitTaskLogMapper mapper = getMapper();
		Pager<AgentProfitTaskLog> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public AgentProfitTaskLog selectByPrimaryKey(int seqId) {
		return this.getMapper().selectByPrimaryKey(seqId);
	}

	@Override
	protected AgentProfitTaskLogExample buildQryExample(Map<String, String> qryParamMap) {
		AgentProfitTaskLogExample example = new AgentProfitTaskLogExample();
		Criteria c = example.createCriteria();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			String agentCd = StringUtil.trimStr(qryParamMap.get("agentCd"));
			if (!StringUtil.isBlank(agentCd)) {
				c.andAgentCdEqualTo(agentCd);
			}
			String profitDt = StringUtil.trimStr(qryParamMap.get("profitDt"));
			if (!StringUtil.isBlank(profitDt)) {
				c.andProfitDtEqualTo(profitDt);
			}
		}
		example.setOrderByClause("rec_crt_ts desc");
		return example;
	}
	
	private AgentProfitTaskLogMapper getMapper() {
		return super.getMapper(AgentProfitTaskLogMapper.class);
	}
	
}
