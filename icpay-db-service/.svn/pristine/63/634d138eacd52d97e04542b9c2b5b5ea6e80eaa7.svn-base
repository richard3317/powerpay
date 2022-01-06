package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.AgentProfitInfoMapper;
import com.icpay.payment.db.dao.mybatis.mapper.ExtMapper.AgentProfitInfoExtMapper;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitInfo;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitInfoExample;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitInfoExample.Criteria;
import com.icpay.payment.db.service.IAgentProfitInfoService;

@Service("agentProfitInfoService")
public class AgentProfitInfoService extends BaseService implements IAgentProfitInfoService {

	@Override
	public Pager<AgentProfitInfo> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		AgentProfitInfoExample example = this.buildQryExample(qryParamMap);
		AgentProfitInfoMapper mapper = getMapper();
		Pager<AgentProfitInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	@Override
	public void add(AgentProfitInfo entity) {
		AssertUtil.objIsNull(entity, "entity is null.");
		AssertUtil.strIsBlank(entity.getAgentCd(), "agentCd is blank.");
		
		Date now = new Date();
		entity.setRecCrtTs(now);
		entity.setRecUpdTs(now);
		
		this.getMapper().insert(entity);
	}

	@Override
	public void delete(String agentCd) {
		AssertUtil.strIsBlank(agentCd, "agentCd is blank.");
		this.getMapper(AgentProfitInfoExtMapper.class).deleteByPrimaryKey(agentCd);
	}

	@Override
	public AgentProfitInfo selectByPrimaryKey(String agentCd) {
		AssertUtil.strIsBlank(agentCd, "agentCd is blank.");
		return this.getMapper(AgentProfitInfoExtMapper.class).selectByPrimaryKey(agentCd);
	}

	@Override
	public void update(AgentProfitInfo entity) {
		AssertUtil.objIsNull(entity, "entity is null.");
		AssertUtil.strIsBlank(entity.getAgentCd(), "agentCd is blank.");
		
		AgentProfitInfoExtMapper mapper = this.getMapper(AgentProfitInfoExtMapper.class);
		AgentProfitInfo dbEntity = mapper.selectByPrimaryKey(entity.getAgentCd());
		AssertUtil.objIsNull(dbEntity, "entity not exist:" + entity.getAgentCd());
		// 更新数据库字段
		BeanUtil.cloneEntity(entity, dbEntity, new String[] {
				"accountAreaCode", "accountAreaInfo", "accountBankName",
				"accountBankCode", "accountNo", "accountName", "comment", "lastOperId"
		});
		dbEntity.setRecUpdTs(new Date());
		mapper.updateByPrimaryKey(dbEntity);
	}

	private AgentProfitInfoMapper getMapper() {
		return this.getMapper(AgentProfitInfoMapper.class);
	}
	
	@Override
	protected AgentProfitInfoExample buildQryExample(Map<String, String> qryParamMap) {
		AgentProfitInfoExample example = new AgentProfitInfoExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			
			String agentCd = qryParamMap.get("agentCd");
			if (!StringUtil.isBlank(agentCd)) {
				c.andAgentCdLike("%" + agentCd + "%");
			}
			
			String profitPeriod = qryParamMap.get("profitPeriod");
			if (!StringUtil.isBlank(profitPeriod)) {
				c.andProfitPeriodEqualTo(profitPeriod);
			}
		}
		// 排序字段
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}
}
