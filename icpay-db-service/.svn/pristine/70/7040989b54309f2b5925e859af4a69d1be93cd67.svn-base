package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.RiskListItemMapper;
import com.icpay.payment.db.dao.mybatis.model.RiskListItem;
import com.icpay.payment.db.dao.mybatis.model.RiskListItemExample;
import com.icpay.payment.db.dao.mybatis.model.RiskListItemKey;
import com.icpay.payment.db.dao.mybatis.model.RiskListItemExample.Criteria;
import com.icpay.payment.db.service.IRiskListItemService;

@Service("riskListItemService")
public class RiskListItemService extends BaseService implements IRiskListItemService {

	private static final Logger logger = Logger.getLogger(RiskListItemService.class);
	
	@Override
	public List<RiskListItem> select(Map<String, String> qryParamMap) {
		RiskListItemExample example = this.getQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}

	@Override
	public Pager<RiskListItem> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		logger.info("分页查询名单组配置信息开始");
		RiskListItemExample example = this.getQryExample(qryParamMap);
		RiskListItemMapper mapper = getMapper();
		Pager<RiskListItem> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		
		logger.info("分页查询名单组配置信息完成");
		return pager;
	}

	@Override
	public RiskListItem selectByPrimaryKey(RiskListItemKey riskListItemKey) {
		return getMapper().selectByPrimaryKey(riskListItemKey);
	}

	/**
	 * 新增
	 */
	@Override
	public void add(RiskListItem riskListItem) {
		logger.info("新增名单组配置信息开始");
		
		i18ObjIsNull(riskListItem, this.getClass().getSimpleName(), "待新增的记录为null");

		RiskListItem dbEntity = this.selectByPrimaryKey(riskListItem);
		i18ObjIsNotNull(dbEntity, this.getClass().getSimpleName(), "主键冲突");

		Date now = new Date();
		riskListItem.setRecCrtTs(now);
		riskListItem.setRecUpdTs(now);
		this.getMapper().insert(riskListItem);
		
		logger.info("新增名单组配置信息完成");
	}
	
	/**
	 * 删除
	 */
	@Override
	public void delete(RiskListItemKey riskListItemKey) {
		logger.info("删除名单组配置信息开始");
		
		RiskListItem dbRiskListItem = this.selectByPrimaryKey(riskListItemKey);
		i18ObjIsNull(dbRiskListItem, this.getClass().getSimpleName(), "待删除的记录不存在");

		getMapper().deleteByPrimaryKey(riskListItemKey);
		
		logger.info("删除名单组配置信息完成");
	}
	
	@Override
	protected RiskListItemExample buildQryExample(Map<String, String> qryParamMap) {
		RiskListItemExample example = new RiskListItemExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:名单组ID
			String groupId = StringUtil.trim(qryParamMap.get("groupId"));
			if (!StringUtil.isBlank(groupId)) {
				c.andGroupIdEqualTo(Integer.valueOf(groupId));
			}
			// 查询条件:名单项
			String item = StringUtil.trim(qryParamMap.get("item"));
			if (!StringUtil.isBlank(item)) {
				c.andItemLike("%" + item + "%");
			}
		}
		// 排序字段
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}
	
	private RiskListItemMapper getMapper() {
		return this.getMapper(RiskListItemMapper.class);
	}
}
