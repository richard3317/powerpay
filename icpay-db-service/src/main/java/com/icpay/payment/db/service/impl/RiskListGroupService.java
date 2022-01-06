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
import com.icpay.payment.db.dao.mybatis.mapper.RiskListGroupMapper;
import com.icpay.payment.db.dao.mybatis.model.RiskListGroup;
import com.icpay.payment.db.dao.mybatis.model.RiskListGroupExample;
import com.icpay.payment.db.dao.mybatis.model.RiskListGroupExample.Criteria;
import com.icpay.payment.db.service.IRiskListGroupService;

@Service("riskListGroupService")
public class RiskListGroupService extends BaseService implements IRiskListGroupService {

	private static final Logger logger = Logger.getLogger(RiskListGroupService.class);
	
	@Override
	public List<RiskListGroup> select(Map<String, String> qryParamMap) {
		RiskListGroupExample example = this.getQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}

	@Override
	public Pager<RiskListGroup> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		logger.info("分页查询名单组配置信息开始");
		RiskListGroupExample example = this.getQryExample(qryParamMap);
		RiskListGroupMapper mapper = getMapper();
		Pager<RiskListGroup> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		
		logger.info("分页查询名单组配置信息完成");
		return pager;
	}

	@Override
	public RiskListGroup selectByPrimaryKey(int groupId) {
		return getMapper().selectByPrimaryKey(groupId);
	}

	/**
	 * 新增
	 */
	@Override
	public void add(RiskListGroup riskListGroup) {
		logger.info("新增名单组配置信息开始");
		RiskListGroupMapper mapper = this.getMapper();
		i18ObjIsNull(riskListGroup, this.getClass().getSimpleName(), "待新增的记录为null");

		RiskListGroup dbEntity = mapper.selectByPrimaryKey(riskListGroup.getGroupId());
		i18ObjIsNotNull(dbEntity, this.getClass().getSimpleName(), "主键冲突: %s", riskListGroup.getGroupId().toString());

		riskListGroup.setStatus(RiskEnums.Status._0.getCode()); // 新增后，状态置为待启用

		// 风险类型、处理结果、风险级别放空
		riskListGroup.setRiskTp("");
		String listTp = riskListGroup.getListTp();
		// 如果是黑名单，则处理结果填写拒绝
		if (RiskEnums.ListTp._0.getCode().equals(listTp)) {
			riskListGroup.setResult(RiskEnums.Result._0.getCode());
		} else {
		// 如果是白名单，则处理结果填写空
			riskListGroup.setResult("");
		}
		riskListGroup.setRiskLevel(0);
		
		Date now = new Date();
		riskListGroup.setRecCrtTs(now);
		riskListGroup.setRecUpdTs(now);
		this.getMapper().insert(riskListGroup);
		logger.info("新增名单组配置信息完成");
	}
	
	/**
	 * 更新
	 */
	@Override
	public void update(RiskListGroup riskListGroup) {
		logger.info("修改名单组配置信息开始");
	
		i18ObjIsNull(riskListGroup, this.getClass().getSimpleName(), "待修改的记录为null");

		RiskListGroup dbEntity = this.selectByPrimaryKey(riskListGroup.getGroupId());
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "该名单组配置信息不存在");
		BeanUtil.cloneEntity(riskListGroup, dbEntity, new String[] {
				"groupNm", "groupTp", "listTp",
				"status", "expiredTm", "comment", "lastOperId"
		});
		dbEntity.setRecUpdTs(new Date());
		getMapper().updateByPrimaryKey(dbEntity);
		
		logger.info("修改名单组配置信息完成");
	}
	
	/**
	 * 删除
	 */
	@Override
	public void delete(int groupId) {
		logger.info("删除名单组配置信息开始");
		
		RiskListGroup dbRiskListGroup = this.selectByPrimaryKey(groupId);
		i18ObjIsNull(dbRiskListGroup, this.getClass().getSimpleName(), "待删除的记录不存在");

		getMapper().deleteByPrimaryKey(groupId);
		
		logger.info("删除名单组配置信息完成");
	}
	
	@Override
	protected RiskListGroupExample buildQryExample(Map<String, String> qryParamMap) {
		RiskListGroupExample example = new RiskListGroupExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:风险类型
			String riskTp = StringUtil.trim(qryParamMap.get("riskTp"));
			if (!StringUtil.isBlank(riskTp)) {
				c.andRiskTpEqualTo(riskTp);
			}
			// 查询条件:处理结果
			String result = StringUtil.trim(qryParamMap.get("result"));
			if (!StringUtil.isBlank(result)) {
				c.andResultEqualTo(result);
			}
			// 查询条件:名单组名称
			String groupNm = StringUtil.trim(qryParamMap.get("groupNm"));
			if (!StringUtil.isBlank(groupNm)) {
				c.andGroupNmLike("%" + groupNm + "%");
			}
			// 查询条件:名单内容类型
			String groupTp = StringUtil.trim(qryParamMap.get("groupTp"));
			if (!StringUtil.isBlank(groupTp)) {
				c.andGroupTpEqualTo(groupTp);
			}
			// 查询条件:名单组ID
			String groupId = StringUtil.trim(qryParamMap.get("groupId"));
			if (!StringUtil.isBlank(groupId)) {
				c.andGroupIdEqualTo(Integer.valueOf(groupId));
			}
			// 查询条件:名单类型
			String listTp = StringUtil.trim(qryParamMap.get("listTp"));
			if (!StringUtil.isBlank(listTp)) {
				c.andListTpEqualTo(listTp);
			}
			// 查询条件:名单组状态
			String status = StringUtil.trim(qryParamMap.get("status"));
			if (!StringUtil.isBlank(status)) {
				c.andStatusEqualTo(status);
			}
		}
		// 排序字段
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}
	
	private RiskListGroupMapper getMapper() {
		return this.getMapper(RiskListGroupMapper.class);
	}
}
