package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.CommonEnums.PrimaryKeyTp;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.exception.I18nBizException;
import com.icpay.payment.common.exception.I18nMsgSpec;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.AgentInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.AgentInfo;
import com.icpay.payment.db.dao.mybatis.model.AgentInfoExample;
import com.icpay.payment.db.dao.mybatis.model.AgentInfoExample.Criteria;
import com.icpay.payment.db.service.IAgentInfoService;

@Service("agentInfoService")
public class AgentInfoService extends BaseService implements IAgentInfoService {
	
	private static final Logger logger = Logger.getLogger(AgentInfoService.class);
	
	@Override
	public Pager<AgentInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		AgentInfoExample example = this.buildQryExample(qryParamMap);
		AgentInfoMapper mapper = getMapper();
		Pager<AgentInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	@Override
	public AgentInfo selectByPrimaryKey(String agentCd) {
		AssertUtil.strIsBlank(agentCd, "agentCd is blank.");
		return getMapper().selectByPrimaryKey(agentCd);
	}
	
	@Override
	public void delete(String agentCd) {
		i18ArgIsBlank(agentCd, this.getClass().getSimpleName(), "代理商代码为空");
		AgentInfoMapper mapper = this.getMapper();
		AgentInfo m = mapper.selectByPrimaryKey(agentCd);
		i18ObjIsNull(m, this.getClass().getSimpleName(), "待删除的渠道信息信息不存在： %s", agentCd);

		mapper.deleteByPrimaryKey(agentCd);
	}
	
	@Override
	public void add(AgentInfo agentInfo) {
		i18ArgIsNull(agentInfo, this.getClass().getSimpleName(), "待保存的对象为null");

		AgentInfoMapper mapper = this.getMapper();
		// 商户代码生成规则: DL + 6位地区码 + 7位顺序数
		logger.info("生成代理商代码开始");
		String agentCdPre = "DL" + agentInfo.getAreaCd();
		for (int i = 0; i < 9999999; i ++) {
			int k = this.genIntKey(PrimaryKeyTp._04);
			String agentCdSuf = StringUtil.leftPad(String.valueOf(k), 7, '0');
			String agentCd = agentCdPre + agentCdSuf;
			if (mapper.selectByPrimaryKey(agentCd) == null) {
				logger.info("代理商代码生成成功:" + agentCd);
				agentInfo.setAgentCd(agentCd);
				break;
			}
		}
		i18StrIsBlank(agentInfo.getAgentCd(), this.getClass().getSimpleName(), "代理商代码生成失败");
		Date now = new Date();
		agentInfo.setRecCrtTs(now);
		agentInfo.setRecUpdTs(now);
		
		int r= mapper.insertSelective(agentInfo);
		if (r<=0)
			throw new I18nBizException(new I18nMsgSpec("zh_CN", this.getClass().getSimpleName(),null, "代理商信息更新失敗"));
	}

	@Override
	public List<AgentInfo> select(Map<String, String> qryParamMap) {
		AgentInfoExample example = this.buildQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}
	
	@Override
	public Map<String, String> selectValidAgentNameMap() {
		Map<String, String> m = new LinkedHashMap<String, String>();
		List<AgentInfo> agentLst = this.select(null);
		for (AgentInfo agentInfo : agentLst) {
			if (CommonEnums.AgentSt._1.getCode().equals(agentInfo.getAgentSt())) {
				m.put(agentInfo.getAgentCd(), agentInfo.getAgentCnNm());
			}
		}
		return m;
	}

	public List<AgentInfo> selectValidAgentsByType(String agentType) {
		AgentInfoExample example = new AgentInfoExample();
		example.createCriteria()
			.andAgentTypeEqualTo(agentType)
			.andAgentStEqualTo("1");
		return getMapper().selectByExample(example);
	}

	@Override
	public void update(AgentInfo agentInfo) {
		AgentInfoMapper mapper = this.getMapper();
		i18ObjIsNull(agentInfo, this.getClass().getSimpleName(), "待修改的记录为null");

//		AgentInfo dbEntity = mapper.selectByPrimaryKey(agentInfo.getAgentCd());
//		AssertUtil.objIsNull(dbEntity, "代理商信息不存在");
//		// 更新数据库字段
//		BeanUtil.cloneEntity(agentInfo, dbEntity, new String[] {
//				"agentType", "agentCnNm", "agentEnNm", "agentCnAbbr", "agentEnAbbr",
//				"agentAddr", "contactPerson1", "contactPhone1", "contactMail1",
//				"contactPerson2", "contactPhone2", "contactMail2", "zipCd",
//				"fax", "agentSt", "comment", "lastOperId"
//		});
//		dbEntity.setRecUpdTs(new Date());
//		mapper.updateByPrimaryKey(dbEntity);
		agentInfo.setRecUpdTs(new Date());
		int r= mapper.updateByPrimaryKeySelective(agentInfo);
		if (r<=0)
			throw new I18nBizException(new I18nMsgSpec("zh_CN", this.getClass().getSimpleName(),null, "代理商信息更新失敗"));
	}
	
	@Override
	protected AgentInfoExample buildQryExample(Map<String, String> qryParamMap) {
		AgentInfoExample example = new AgentInfoExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			
			String agentCd = qryParamMap.get("agentCd");
			if (!StringUtil.isBlank(agentCd)) {
				c.andAgentCdLike("%" + agentCd + "%");
			}
			
			String agentType = qryParamMap.get("agentType");
			if (!StringUtil.isBlank(agentType)) {
				c.andAgentTypeEqualTo(agentType);
			}
			
			String agentCnNm = qryParamMap.get("agentCnNm");
			if (!StringUtil.isBlank(agentCnNm)) {
				c.andAgentCnNmLike("%" + agentCnNm + "%");
			}
			
			String fullAgentCnNm = qryParamMap.get("fullAgentCnNm");
			if (!StringUtil.isBlank(fullAgentCnNm)) {
				c.andAgentCnNmEqualTo(fullAgentCnNm);
			}
			
			String agentSt = qryParamMap.get("agentSt");
			if (!StringUtil.isBlank(agentSt)) {
				c.andAgentStEqualTo(agentSt);
			}
		}
		// 排序字段
		example.setOrderByClause("agent_type, agent_cd");
		return example;
	}

	private AgentInfoMapper getMapper() {
		return this.getMapper(AgentInfoMapper.class);
	}
}
