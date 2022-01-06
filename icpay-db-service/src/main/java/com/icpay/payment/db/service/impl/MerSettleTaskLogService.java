package com.icpay.payment.db.service.impl;

import java.util.Arrays;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.MerSettleTaskLogMapper;
import com.icpay.payment.db.dao.mybatis.model.MerSettleTaskLog;
import com.icpay.payment.db.dao.mybatis.model.MerSettleTaskLogExample;
import com.icpay.payment.db.dao.mybatis.model.MerSettleTaskLogExample.Criteria;
import com.icpay.payment.db.service.IMerSettleTaskLogService;

@Service("merSettleTaskLogService")
public class MerSettleTaskLogService extends BaseService implements IMerSettleTaskLogService {

	@Override
	public Pager<MerSettleTaskLog> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		MerSettleTaskLogExample example = this.getQryExample(qryParamMap);
		MerSettleTaskLogMapper mapper = getMapper();
		Pager<MerSettleTaskLog> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public MerSettleTaskLog selectByPrimaryKey(int seqId) {
		return this.getMapper().selectByPrimaryKey(seqId);
	}

	@Override
	protected MerSettleTaskLogExample buildQryExample(Map<String, String> qryParamMap) {
		MerSettleTaskLogExample example = new MerSettleTaskLogExample();
		Criteria c = example.createCriteria();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			String mchntCd = StringUtil.trimStr(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			String settleDt = StringUtil.trimStr(qryParamMap.get("settleDt"));
			if (!StringUtil.isBlank(settleDt)) {
				c.andSettleDtEqualTo(settleDt);
			}
			String settleBt = StringUtil.trimStr(qryParamMap.get("settleBt"));
			if (!StringUtil.isBlank(settleBt)) {
				c.andSettleBtEqualTo(Integer.parseInt(settleBt));
			}
			String settlePeriod = StringUtil.trimStr(qryParamMap.get("settlePeriod"));
			if (!StringUtil.isBlank(settlePeriod)) {
				c.andSettlePeriodEqualTo(settlePeriod);
			}
			String state = StringUtil.trimStr(qryParamMap.get("state"));
			if (!StringUtil.isBlank(state)) {
				c.andStateEqualTo(state);
			}
			String stateLst = StringUtil.trimStr(qryParamMap.get("stateLst"));
			if (!StringUtil.isBlank(stateLst)) {
				String[] lst = stateLst.split(",");
				c.andStateIn(Arrays.asList(lst));
			}
		}
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}
	
	private MerSettleTaskLogMapper getMapper() {
		return super.getMapper(MerSettleTaskLogMapper.class);
	}
}
