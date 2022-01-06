package com.icpay.payment.db.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.exception.I18nBizException;
import com.icpay.payment.common.exception.I18nMsgSpec;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.mapper.AgentProfitPolicyMapper;
import com.icpay.payment.db.dao.mybatis.mapper.MchntInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicy;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicyExample;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicyExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicyKey;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntInfoExample;
import com.icpay.payment.db.service.IAgentProfitPolicyService;

@Service("agentProfitPolicyService")
public class AgentProfitPolicyService extends BaseService implements IAgentProfitPolicyService {

	@Override
	protected AgentProfitPolicyExample buildQryExample(Map<String, String> qryParamMap) {
		AgentProfitPolicyExample example = new AgentProfitPolicyExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			String strFuzzyMode=qryParamMap.get("fuzzyMode");
			
			boolean fuzzyMode = (strFuzzyMode==null ? false : "true".equalsIgnoreCase(strFuzzyMode));
			
			String agentCd = qryParamMap.get("agentCd");
			if (!StringUtil.isBlank(agentCd)) {
				c.andAgentCdEqualTo(agentCd);
			}
			String intTransCd = qryParamMap.get("intTransCd");
			if (!StringUtil.isBlank(intTransCd)) {
				if (fuzzyMode)
					c.andIntTransCdIn(Utils.newList("*",intTransCd));
				else
					c.andIntTransCdEqualTo(intTransCd);
			}
			String mchntCd = qryParamMap.get("mchntCd");
			if (!StringUtil.isBlank(mchntCd)) {
				if (fuzzyMode)
					c.andMchntCdIn(Utils.newList("*",mchntCd));
				else
					c.andMchntCdEqualTo(mchntCd);
			}
			String chnlId = qryParamMap.get("chnlId");
			if (!StringUtil.isBlank(chnlId)) {
				if (fuzzyMode)
					c.andChnlIdIn(Utils.newList("*",chnlId));
				else
					c.andChnlIdEqualTo(chnlId);
			}
			String chnlMchntCd = qryParamMap.get("chnlMchntCd");
			if (!StringUtil.isBlank(chnlMchntCd)) {
				if (fuzzyMode)
					c.andChnlMchntCdIn(Utils.newList("*",chnlMchntCd));
				else
					c.andChnlMchntCdEqualTo(chnlMchntCd);
			}
			
			String tradeType = qryParamMap.get("tradeType");
			if (!StringUtil.isBlank(tradeType)) {
				if (fuzzyMode)
					c.andTradeTypeIn(Utils.newList("*",tradeType));
				else
					c.andTradeTypeEqualTo(tradeType);
			}
			String rate = qryParamMap.get("rate");
			if (!StringUtil.isBlank(rate)) {
				c.andRateEqualTo(rate);
			}
			String comment = qryParamMap.get("comment");
			if (!StringUtil.isBlank(comment)) {
				c.andCommentLike("%"+comment+"%");
			}
			//String mchntName
			String mchntName = StringUtil.trim(qryParamMap.get("mchntName"));
			if (!StringUtil.isBlank(mchntName)) {
				MchntInfoMapper mdao = getMapper(MchntInfoMapper.class);
				MchntInfoExample mex= new MchntInfoExample();
				MchntInfoExample.Criteria c1 = mex.createCriteria().andMchntCnAbbrLike("%"+mchntName+"%");
				MchntInfoExample.Criteria c2 = mex.or().andMchntCnNmLike("%"+mchntName+"%");
				List<MchntInfo> mers=mdao.selectByExample(mex);
				if ((mers!=null)&& mers.size()>0) {
					List<String> merIds= new ArrayList<>();
					for(MchntInfo m: mers) {
						merIds.add(m.getMchntCd());
					}
					c.andMchntCdIn(merIds);
				}
			}
		}
		// 排序字段
		//example.setOrderByClause("rec_upd_ts desc");
		example.setOrderByClause("agent_cd,priority");
		return example;
	}

	@Override
	public List<AgentProfitPolicy> select(Map<String, String> qryParamMap) {
		AgentProfitPolicyExample example = this.buildQryExample(qryParamMap);
		return this.getMapper().selectByExample(example);
	}
	
	@Override
	public void add(AgentProfitPolicy entity) {
		AssertUtil.objIsNull(entity, "entity is null");
		AssertUtil.strIsBlank(entity.getAgentCd(), "agentCd is blank.");
		AssertUtil.strIsBlank(entity.getIntTransCd(), "intTransCd is blank.");
		
		Date now = new Date();
		entity.setRecCrtTs(now);
		entity.setRecUpdTs(now);
		
		//this.getMapper().insert(entity);
		this.getMapper().insertSelective(entity);
	}

	@Override
	public void delete(AgentProfitPolicyKey key) {
		AssertUtil.objIsNull(key, "key is null");
		AssertUtil.strIsBlank(key.getAgentCd(), "agentCd is blank.");
		AssertUtil.strIsBlank(key.getIntTransCd(), "intTransCd is blank.");
		this.getMapper().deleteByPrimaryKey(key);
	}

	@Override
	public AgentProfitPolicy selectByPrimaryKey(AgentProfitPolicyKey key) {
		AssertUtil.objIsNull(key, "key is null");
		AssertUtil.strIsBlank(key.getAgentCd(), "agentCd is blank.");
		AssertUtil.strIsBlank(key.getIntTransCd(), "intTransCd is blank.");
		return this.getMapper().selectByPrimaryKey(key);
	}

	@Override
	public void update(AgentProfitPolicy entity) {
		AssertUtil.objIsNull(entity, "entity is null");
		AssertUtil.strIsBlank(entity.getAgentCd(), "agentCd is blank.");
		AssertUtil.strIsBlank(entity.getIntTransCd(), "intTransCd is blank.");
		
		AgentProfitPolicyMapper mapper = getMapper();
//		AgentProfitPolicy dbEntity = mapper.selectByPrimaryKey(entity);
//		AssertUtil.objIsNull(dbEntity, "entity not exist:" + entity.getAgentCd() + "-" + entity.getIntTransCd());
//		// 更新数据库字段
//		BeanUtil.cloneEntity(entity, dbEntity, new String[] {
//				"rate", "maxFee", "minFee", "comment", "lastOperId"
//		});
//		dbEntity.setRecUpdTs(new Date());
//		//mapper.updateByPrimaryKey(dbEntity);
//		int r = mapper.updateByPrimaryKeySelective(dbEntity);
		
		int r = mapper.updateByPrimaryKeySelective(entity);
		if (r==0)
			throw new I18nBizException(new I18nMsgSpec("zh_CN", this.getClass().getSimpleName(),null, "數據更新失敗"));
	}

	private AgentProfitPolicyMapper getMapper() {
		return this.getMapper(AgentProfitPolicyMapper.class);
	}
	
	@Override
	public AgentProfitPolicy selectForMerTransType(String agentCd, String mchntCd, String intTransCd, String tradeType) {
		//this.debug("selectForMerTransType(agentCd=%s, mchntCd=%s, intTransCd=%s, tradeType=%s)",agentCd, mchntCd, intTransCd, tradeType);
		AgentProfitPolicyExample example = new AgentProfitPolicyExample();
		example.createCriteria()
			.andAgentCdEqualTo(agentCd)
			.andMchntCdIn(Utils.newList("*",mchntCd))
			.andIntTransCdIn(Utils.newList("*",intTransCd))
			.andTradeTypeIn(Utils.newList("*",tradeType))
			;
		example.setOrderByClause("priority");
		List<AgentProfitPolicy> list = this.getMapper().selectByExample(example);
		//this.debug("Result of selectForMerTransType(agentCd=%s, mchntCd=%s, intTransCd=%s, tradeType=%s): \n%s",agentCd, mchntCd, intTransCd, tradeType, listToString(list));
		
		if (list==null) return null;
		if (list.size()==0) return null;
		return list.get(0);
	}
	
	private String listToString(List<?> list) {
		if (list==null) return "";
		if (list.size()==0) return "";
		StringBuilder buf=new StringBuilder("{").append("\n");
		for(Object item: list) {
			buf.append(item).append(",\n");
		}
		buf.append("\n");
		return buf.toString();
	}

	@Override
	public List<AgentProfitPolicy> selectFuzzyMode(Map<String, String> qryParamMap) {
		qryParamMap.put("fuzzyMode", "true");
		AgentProfitPolicyExample example = this.buildQryExample(qryParamMap);
		return this.getMapper().selectByExample(example);
	}

	@Override
	public Pager<AgentProfitPolicy> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		AgentProfitPolicyMapper mapper = getMapper();
		AgentProfitPolicyExample example = this.buildQryExample(qryParamMap);
		Pager<AgentProfitPolicy> pager = buildPager(pageNum, pageSize, qryParamMap);
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
}
