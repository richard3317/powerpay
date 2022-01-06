package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.RiskEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.RiskThresholdMapper;
import com.icpay.payment.db.dao.mybatis.model.RiskThreshold;
import com.icpay.payment.db.dao.mybatis.model.RiskThresholdExample;
import com.icpay.payment.db.dao.mybatis.model.RiskThresholdExample.Criteria;
import com.icpay.payment.db.service.IRiskThresholdService;

@Service("riskThresholdService")
public class RiskThresholdService extends BaseService implements IRiskThresholdService {

	private static final Logger logger = Logger.getLogger(RiskThresholdService.class);
	
	@Override
	public List<RiskThreshold> select(Map<String, String> qryParamMap) {
		RiskThresholdExample example = this.getQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}

	@Override
	public Pager<RiskThreshold> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		logger.info("分页查询风控限额配置信息开始");
		RiskThresholdExample example = this.getQryExample(qryParamMap);
		RiskThresholdMapper mapper = getMapper();
		Pager<RiskThreshold> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		
		logger.info("分页查询风控限额配置信息完成");
		return pager;
	}

	@Override
	public RiskThreshold selectByPrimaryKey(int ruleId) {
		return getMapper().selectByPrimaryKey(Long.valueOf(ruleId));
	}

	/**
	 * 新增
	 */
	@Override
	public void add(RiskThreshold riskThreshold) {
		logger.info("新增风控限额配置信息开始");
		i18ObjIsNull(riskThreshold, this.getClass().getSimpleName(), "待新增的记录为null");

		RiskThreshold dbEntity = this.selectByPrimaryKey(Integer.parseInt(""+riskThreshold.getRuleId()));//@Deprecated
		i18ObjIsNotNull(dbEntity, this.getClass().getSimpleName(), "主键冲突:", riskThreshold.getRuleId().toString());

		riskThreshold.setUserId(""); // 监控项-用户ID预留
		riskThreshold.setPayType(""); // 监控项-支付方式预留
		riskThreshold.setPhoneNo("");// 监控项-手机号预留
		riskThreshold.setStatus(RiskEnums.Status._0.getCode()); // 新增后状态默认为待启用
		
		Date now = new Date();
		riskThreshold.setRecCrtTs(now);
		riskThreshold.setRecUpdTs(now);
		this.getMapper().insert(riskThreshold);
		
		logger.info("新增风控限额配置信息完成");
	}
	
	/**
	 * 更新
	 */
	@Override
	public void update(RiskThreshold riskThreshold) {
		logger.info("修改风控限额配置信息开始");
	
		i18ObjIsNull(riskThreshold, this.getClass().getSimpleName(), "待修改的记录为null");

		RiskThreshold dbEntity = this.selectByPrimaryKey(Integer.parseInt(""+riskThreshold.getRuleId()));//@Deprecated
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "该风控限额配置信息不存在");

		BeanUtil.cloneEntity(riskThreshold, dbEntity, new String[] {
				"ruleNm", "riskTp", "period", "threhold", "result",
				"expiredTm", "status", "comment", "lastOperId"
		});
		
		dbEntity.setRecUpdTs(new Date());
		getMapper().updateByPrimaryKey(dbEntity);
		
		logger.info("修改风控限额配置信息完成");
	}
	
	/**
	 * 删除
	 */
	@Override
	public void delete(int ruleId) {
		logger.info("删除风控限额配置信息开始");
		
		RiskThreshold dbEntity = this.selectByPrimaryKey(ruleId);
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "待删除的记录不存在");

		getMapper().deleteByPrimaryKey(Long.valueOf(ruleId));
		
		logger.info("删除风控限额配置信息完成");
	}
	
	@Override
	protected RiskThresholdExample buildQryExample(Map<String, String> qryParamMap) {
		RiskThresholdExample example = new RiskThresholdExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:规则ID
			String ruleId = StringUtil.trim(qryParamMap.get("ruleId"));
			if (!StringUtil.isBlank(ruleId)) {
				c.andRuleIdEqualTo(Long.valueOf(ruleId));
			}
			// 查询条件:规则名称
			String ruleNm = StringUtil.trim(qryParamMap.get("ruleNm"));
			if (!StringUtil.isBlank(ruleNm)) {
				c.andRuleNmLike("%" + ruleNm + "%");
			}
			// 查询条件:风险类型
			String riskTp = StringUtil.trim(qryParamMap.get("riskTp"));
			if (!StringUtil.isBlank(riskTp)) {
				c.andRiskTpEqualTo(riskTp);
			}
			// 查询条件:统计周期
			String period = StringUtil.trim(qryParamMap.get("period"));
			if (!StringUtil.isBlank(period)) {
				c.andPeriodEqualTo(Integer.valueOf(period));
			}
			// 查询条件:处理结果
			String result = StringUtil.trim(qryParamMap.get("result"));
			if (!StringUtil.isBlank(result)) {
				c.andResultEqualTo(result);
			}
			// 查询条件:状态
			String status = StringUtil.trim(qryParamMap.get("status"));
			if (!StringUtil.isBlank(status)) {
				c.andStatusEqualTo(status);
			}
			// 查询条件:规则类型
			String ruleTp = StringUtil.trim(qryParamMap.get("ruleTp"));
			if (!StringUtil.isBlank(ruleTp)) {
				c.andRuleTpEqualTo(ruleTp);
			}
		}
		// 排序字段
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}
	
	private RiskThresholdMapper getMapper() {
		return this.getMapper(RiskThresholdMapper.class);
	}
}
