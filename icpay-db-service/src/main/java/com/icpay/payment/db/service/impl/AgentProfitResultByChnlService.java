package com.icpay.payment.db.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.AgentProfitResultByChnlMapper;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitResultByChnl;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitResultByChnlExample;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitResultByChnlExample.Criteria;
import com.icpay.payment.db.service.IAgentProfitResultByChnlService;


@Service("agentProfitResultByChnlService")
public class AgentProfitResultByChnlService extends BaseService implements IAgentProfitResultByChnlService {

	@Override
	public Pager<AgentProfitResultByChnl> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		AgentProfitResultByChnlExample example = this.getQryExample(qryParamMap);
		AgentProfitResultByChnlMapper mapper = getMapper();
		Pager<AgentProfitResultByChnl> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	@Override
	public List<AgentProfitResultByChnl> select( Map<String, String> qryParamMap) {
		AgentProfitResultByChnlExample example = this.getQryExample(qryParamMap);
		AgentProfitResultByChnlMapper mapper = getMapper();
		List<AgentProfitResultByChnl> list = mapper.selectByExample(example);
		return list;
	}

	@Override
	public AgentProfitResultByChnl selectByPrimaryKey(AgentProfitResultByChnl key) {
		AgentProfitResultByChnlExample example = new AgentProfitResultByChnlExample();
		Criteria c = example.createCriteria();
		c.andSettleDateEqualTo(key.getSettleDate());
		c.andAgentCdEqualTo(key.getAgentCd());
		c.andChnlIdEqualTo(key.getChnlId());
		c.andChnlMchntCdEqualTo(key.getChnlMchntCd());
		List<AgentProfitResultByChnl> list= this.getMapper().selectByExample(example);
		if (list==null) return null;
		AgentProfitResultByChnl res= list.size()>0 ? list.get(0) : null;
		this.debug("[selectByPrimaryKey] Result: %s", res);
		return res;
	}

	@Override
	protected AgentProfitResultByChnlExample buildQryExample(Map<String, String> qryParamMap) {
		AgentProfitResultByChnlExample example = new AgentProfitResultByChnlExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			
			String settleDate = qryParamMap.get("settleDate");
			if (!StringUtil.isBlank(settleDate)) {
				c.andSettleDateEqualTo(settleDate);
			}
			String agentCd = qryParamMap.get("agentCd");
			if (!StringUtil.isBlank(agentCd)) {
				c.andAgentCdEqualTo(agentCd);
			}
			String chnlId = qryParamMap.get("chnlId");
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}
			String chnlMchntCd = qryParamMap.get("chnlMchntCd");
			if (!StringUtil.isBlank(chnlMchntCd)) {
				c.andChnlMchntCdEqualTo(chnlMchntCd);
			}
		}
		// 排序字段
		//example.setOrderByClause("rec_upd_ts desc");
		example.setOrderByClause("settle_date, agent_cd, chnl_id, chnl_mchnt_cd");
		return example;
	}
	
	private AgentProfitResultByChnlMapper getMapper() {
		return super.getMapper(AgentProfitResultByChnlMapper.class);
	}
	
}
