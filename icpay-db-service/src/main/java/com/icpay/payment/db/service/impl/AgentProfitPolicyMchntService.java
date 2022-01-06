package com.icpay.payment.db.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.AgentProfitPolicyMchntMapper;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicyMchnt;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicyMchntExample;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicyMchntExample.Criteria;
import com.icpay.payment.db.service.IAgentProfitPolicyMchntService;


@Service("agentProfitPolicyMchntService")
public class AgentProfitPolicyMchntService extends BaseService implements IAgentProfitPolicyMchntService {

	@Override
	public Pager<AgentProfitPolicyMchnt> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		AgentProfitPolicyMchntExample example = this.getQryExample(qryParamMap);
		AgentProfitPolicyMchntMapper mapper = getMapper();
		Pager<AgentProfitPolicyMchnt> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	@Override
	public List<AgentProfitPolicyMchnt> select( Map<String, String> qryParamMap) {
		AgentProfitPolicyMchntExample example = this.getQryExample(qryParamMap);
		AgentProfitPolicyMchntMapper mapper = getMapper();
		List<AgentProfitPolicyMchnt> list = mapper.selectByExample(example);
		return list;
	}

	@Override
	public AgentProfitPolicyMchnt selectByPrimaryKey(AgentProfitPolicyMchnt key) {
		AgentProfitPolicyMchntExample example = new AgentProfitPolicyMchntExample();
		Criteria c = example.createCriteria();
		c.andMchntCdEqualTo(key.getMchntCd());
		c.andMchntCnNmEqualTo(key.getMchntCnNm());
		c.andMchntStEqualTo(key.getMchntSt());
		c.andTrustDomainEqualTo(key.getTrustDomain());
		List<AgentProfitPolicyMchnt> list= this.getMapper().selectByExample(example);
		if (list==null) return null;
		AgentProfitPolicyMchnt res= list.size()>0 ? list.get(0) : null;
		this.debug("[selectByPrimaryKey] Result: %s", res);
		return res;
	}

	@Override
	protected AgentProfitPolicyMchntExample buildQryExample(Map<String, String> qryParamMap) {
		AgentProfitPolicyMchntExample example = new AgentProfitPolicyMchntExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			
			String agentCd = qryParamMap.get("agentCd");
			if (!StringUtil.isBlank(agentCd)) {
				c.andAgentCdEqualTo(agentCd);
			}
			
			String mchntCd = qryParamMap.get("mchntCd");
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			String mchntCnNm = qryParamMap.get("mchntCnNm");
			if (!StringUtil.isBlank(mchntCnNm)) {
				c.andMchntCnNmEqualTo(mchntCnNm);
			}
			String mchntSt = qryParamMap.get("mchntSt");
			if (!StringUtil.isBlank(mchntSt)) {
				c.andMchntStEqualTo(mchntSt);
			}
			String trustDomain = qryParamMap.get("trustDomain");
			if (!StringUtil.isBlank(trustDomain)) {
				c.andTrustDomainEqualTo(trustDomain);
			}
		}
		// 排序字段
		example.setOrderByClause("rec_crt_ts desc");
//		example.setOrderByClause("agentCd, mchntCd, mchntCnNm, mchntSt, trustDomain");
		return example;
	}
	
	private AgentProfitPolicyMchntMapper getMapper() {
		return super.getMapper(AgentProfitPolicyMchntMapper.class);
	}
	
}
