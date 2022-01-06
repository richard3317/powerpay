package com.icpay.payment.db.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.AgentProfitSettleResultMapper;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitSettleResult;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitSettleResultExample;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitSettleResultExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitSettleResultKey;
import com.icpay.payment.db.service.IAgentProfitSettleResultService;


@Service("agentProfitSettleResultService")
public class AgentProfitSettleResultService extends BaseService implements IAgentProfitSettleResultService {

	@Override
	public Pager<AgentProfitSettleResult> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		AgentProfitSettleResultExample example = this.getQryExample(qryParamMap);
		AgentProfitSettleResultMapper mapper = getMapper();
		Pager<AgentProfitSettleResult> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	@Override
	public List<AgentProfitSettleResult> select( Map<String, String> qryParamMap) {
		AgentProfitSettleResultExample example = this.getQryExample(qryParamMap);
		AgentProfitSettleResultMapper mapper = getMapper();
		List<AgentProfitSettleResult> list = mapper.selectByExample(example) ;
		return list;
	}
	

	@Override
	public AgentProfitSettleResult selectByPrimaryKey(AgentProfitSettleResultKey key) {
		return this.getMapper().selectByPrimaryKey(key);
	}

	@Override
	protected AgentProfitSettleResultExample buildQryExample(Map<String, String> qryParamMap) {
		AgentProfitSettleResultExample example = new AgentProfitSettleResultExample();
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
			String intTransCd = qryParamMap.get("intTransCd");
			if (!StringUtil.isBlank(intTransCd)) {
				c.andIntTransCdEqualTo(intTransCd);
			}
			String mchntCd = qryParamMap.get("mchntCd");
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			String chnlId = qryParamMap.get("chnlId");
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}
			String chnlMchntCd = qryParamMap.get("chnlMchntCd");
			if (!StringUtil.isBlank(chnlMchntCd)) {
				c.andChnlMchntCdEqualTo(chnlMchntCd);
			}
			String comment = qryParamMap.get("comment");
			if (!StringUtil.isBlank(comment)) {
				c.andCommentLike("%"+comment+"%");
			}
		}
		// 排序字段
		//example.setOrderByClause("rec_upd_ts desc");
		example.setOrderByClause("settle_date, agent_cd, chnl_id, chnl_mchnt_cd, mchnt_cd");
		return example;
	}
	
	private AgentProfitSettleResultMapper getMapper() {
		return super.getMapper(AgentProfitSettleResultMapper.class);
	}
	
}
