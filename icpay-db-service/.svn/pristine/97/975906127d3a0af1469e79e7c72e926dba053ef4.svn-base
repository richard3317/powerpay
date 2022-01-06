package com.icpay.payment.db.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.AgentAccCheckFileMapper;
import com.icpay.payment.db.dao.mybatis.model.AgentAccCheckFile;
import com.icpay.payment.db.dao.mybatis.model.AgentAccCheckFileExample;
import com.icpay.payment.db.dao.mybatis.model.AgentAccCheckFileExample.Criteria;
import com.icpay.payment.db.service.IAgentAccCheckFileService;

@Service("agentAccCheckFileService")
public class AgentAccCheckFileService extends BaseService implements IAgentAccCheckFileService {

	@Override
	public Pager<AgentAccCheckFile> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		AgentAccCheckFileExample example = this.getQryExample(qryParamMap);
		AgentAccCheckFileMapper mapper = getMapper();
		Pager<AgentAccCheckFile> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public AgentAccCheckFile selectByPrimaryKey(int seqId) {
		return this.getMapper().selectByPrimaryKey(seqId);
	}

	@Override
	protected AgentAccCheckFileExample buildQryExample(Map<String, String> qryParamMap) {
		AgentAccCheckFileExample example = new AgentAccCheckFileExample();
		Criteria c = example.createCriteria();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			String settleDt = StringUtil.trimStr(qryParamMap.get("settleDt"));
			if (!StringUtil.isBlank(settleDt)) {
				c.andSettleDtEqualTo(settleDt);
			}
			String agentCd = StringUtil.trimStr(qryParamMap.get("agentCd"));
			if (!StringUtil.isBlank(agentCd)) {
				c.andAgentCdEqualTo(agentCd);
			}
		}
		example.setOrderByClause("rec_crt_ts desc");
		return example;
	}
	
	private AgentAccCheckFileMapper getMapper() {
		return super.getMapper(AgentAccCheckFileMapper.class);
	}
}
