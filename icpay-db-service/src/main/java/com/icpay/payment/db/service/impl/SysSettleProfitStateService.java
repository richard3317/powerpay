package com.icpay.payment.db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.SysSettleProfitStateMapper;
import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitState;
import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitStateExample;
import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitStateExample.Criteria;
import com.icpay.payment.db.service.ISysSettleProfitStateService;

@Service("SysSettleProfitState")
public class SysSettleProfitStateService extends BaseService implements ISysSettleProfitStateService {
	
	@Override
	public int insertSelective(SysSettleProfitState record) {
		return dao().insert(record);
	}

	@Override
	public int updateByPrimaryKeySelective(SysSettleProfitState record) {
		return dao().updateByPrimaryKeySelective(record);
	}

	@Override
	public List<SysSettleProfitState> select(String settleDate, String settleState) {
		Map <String,String> qryParamMap = new HashMap<String,String>();
		qryParamMap.put("settleDate", settleDate);
		qryParamMap.put("settleState", settleState);
		SysSettleProfitStateExample example = this.getQryExample(qryParamMap);
		return dao().selectByExample(example);
	}
	
	@Override
	public List<SysSettleProfitState> selectBySettleDate(String settleDate) {
		Map <String,String> qryParamMap = new HashMap<String,String>();
		qryParamMap.put("settleDate", settleDate);
		SysSettleProfitStateExample example = this.getQryExample(qryParamMap);
		return dao().selectByExample(example);
	}
	
	@Override
	public List<SysSettleProfitState> selectBeforeDate(String beforeDate) {
		Map <String,String> qryParamMap = new HashMap<String,String>();
		qryParamMap.put("beforeDate", beforeDate);
		SysSettleProfitStateExample example = this.getQryExample(qryParamMap);
		return dao().selectByExample(example);
	}
	
	@Override
	public SysSettleProfitState selectByPrimaryKey(String transSeqId) {
		return dao().selectByPrimaryKey(transSeqId);
	}
	
	@Override
    public int deleteByPrimaryKey(String transSeqId) {
		return dao().deleteByPrimaryKey(transSeqId);
	}
	
	private SysSettleProfitStateMapper dao() {
		return this.getMapper(SysSettleProfitStateMapper.class);
	}	
	
	@Override
	protected SysSettleProfitStateExample buildQryExample(Map<String, String> qryParamMap) {
		SysSettleProfitStateExample example = new SysSettleProfitStateExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:结算日期
			String settleDate = StringUtil.trim(qryParamMap.get("settleDate"));
			if (!StringUtil.isBlank(settleDate)) {
				c.andSettleDateEqualTo(settleDate);
			}
			// 查询条件:清算状态
			String settleState = StringUtil.trim(qryParamMap.get("settleState"));
			if (!StringUtil.isBlank(settleState)) {
				c.andSettleStateEqualTo(settleState);
			}
			// 查询条件:小于日期
			String beforeDate = StringUtil.trim(qryParamMap.get("beforeDate"));
			if (!StringUtil.isBlank(beforeDate)) {
				c.andSettleDateLessThanOrEqualTo(beforeDate);
			}
		}
		// 排序字段
		example.setOrderByClause("settle_date desc");
		return example;
	}

}
