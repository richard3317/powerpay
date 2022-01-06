package com.icpay.payment.db.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.AgentAccountInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.AgentAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.AgentAccountInfoExample;
import com.icpay.payment.db.dao.mybatis.model.AgentAccountInfoKey;
import com.icpay.payment.db.dao.mybatis.model.AgentAccountInfoExample.Criteria;
import com.icpay.payment.db.service.IAgentBankAccsService;

@Service("agentBankAccsService")
public class AgentBankAccsService  extends BaseService implements IAgentBankAccsService {
	
	private AgentAccountInfoMapper getMapper() {
		return this.getMapper(AgentAccountInfoMapper.class);
	}
	
	@Override
	protected AgentAccountInfoExample buildQryExample(Map<String, String> qryParamMap) {
		AgentAccountInfoExample example = new AgentAccountInfoExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			String agentCd = qryParamMap.get("agentCd");
			if (!StringUtil.isBlank(agentCd)) {
				c.andAgentCdEqualTo(agentCd);
			}
			String accountType = qryParamMap.get("accountType");
			if (!StringUtil.isBlank(accountType)) {
				c.andAccountTypeEqualTo(accountType);
			}
			
			String accountNo = qryParamMap.get("accountNo");
			if (!StringUtil.isBlank(accountNo)) {
				c.andAccountNoEqualTo(accountNo);
			}
			
			String accountName = qryParamMap.get("accountName");
			if (!StringUtil.isBlank(accountName)) {
				c.andAccountNameEqualTo(accountName);
			}
			String accountBankCode = qryParamMap.get("accountBankCode");
			if (!StringUtil.isBlank(accountBankCode)) {
				c.andAccountBankCodeEqualTo(accountBankCode);
			}
			String comment = qryParamMap.get("comment");
			if (!StringUtil.isBlank(comment)) {
				c.andCommentLike("%"+comment+"%");
			}
		}
		// 排序字段
		example.setOrderByClause("agent_cd,account_type,priority");
		return example;
	}	

	@Override
	public List<AgentAccountInfo> select(Map<String, String> qryParamMap) {
		AgentAccountInfoExample example = this.buildQryExample(qryParamMap);
		return this.getMapper().selectByExample(example);
	}
	
	@Override
    public List<AgentAccountInfo> selectByAgent(String agentCd, String... accTypes){
		AgentAccountInfoExample example = new AgentAccountInfoExample();
		AgentAccountInfoExample.Criteria c = example.createCriteria().andAgentCdEqualTo(agentCd);
		if (accTypes!=null) {
			if (accTypes.length==1) {
				c=c.andAccountTypeEqualTo(accTypes[0]);
			}
			else if (accTypes.length > 1) {
				List<String> actList = new ArrayList<>();
				for (String act: accTypes) actList.add(act);
				c=c.andAccountTypeIn(actList);
			}
		}
		return this.getMapper().selectByExample(example);
    }


	@Override
	public Pager<AgentAccountInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		AgentAccountInfoExample example = this.buildQryExample(qryParamMap);
		AgentAccountInfoMapper mapper=this.getMapper();
		Pager<AgentAccountInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public AgentAccountInfo selectByPrimaryKey(AgentAccountInfoKey key) {
		return this.getMapper().selectByPrimaryKey(key);
	}

	@Override
	public void add(AgentAccountInfo record) {
		this.getMapper().insertSelective(record);
	}

	@Override
	public void update(AgentAccountInfo record) {
		this.getMapper().updateByPrimaryKeySelective(record);
	}

	@Override
	public void delete(AgentAccountInfo record) {
		this.getMapper().deleteByPrimaryKey(record);
	}

}
