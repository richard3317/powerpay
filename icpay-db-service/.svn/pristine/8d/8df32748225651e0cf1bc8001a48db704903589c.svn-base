package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.MerAccountFlowMapper;
import com.icpay.payment.db.dao.mybatis.model.MerAccountFlow;
import com.icpay.payment.db.dao.mybatis.model.MerAccountFlowExample;
import com.icpay.payment.db.dao.mybatis.model.MerAccountFlowExample.Criteria;
import com.icpay.payment.db.service.IMchntAccountFlowService;

@Service("mchntAccountFlowService")
public class MchntAccountFlowService extends BaseService implements IMchntAccountFlowService {
	
	@Override
	public List<MerAccountFlow> select(Map<String, String> qryParamMap) {
		MerAccountFlowExample example = this.getQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}

	@Override
	public void add(MerAccountFlow merAccountFlow) {
		AssertUtil.objIsNull(merAccountFlow, "merAccountFlow is null.");
		this.getMapper().insert(merAccountFlow);
	}

	@Override
	public Pager<MerAccountFlow> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		MerAccountFlowExample example = this.getQryExample(qryParamMap);
		MerAccountFlowMapper mapper = getMapper();
		Pager<MerAccountFlow> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public MerAccountFlow selectByPrimaryKey(long seqId) {
		return this.getMapper().selectByPrimaryKey(seqId);
	}
	
	@Override
	protected MerAccountFlowExample buildQryExample(Map<String, String> qryParamMap) {
		MerAccountFlowExample example = new MerAccountFlowExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件：商户代码
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			
			// 查询条件: 操作类型
			String operateTp = StringUtil.trim(qryParamMap.get("operateTp"));
			if (!StringUtil.isBlank(operateTp)) {
				c.andOperateTpEqualTo(operateTp);
			}
			
			// 查询条件: 操作日期
			String operateDt = StringUtil.trim(qryParamMap.get("operateDt"));
			if (!StringUtil.isBlank(operateDt)) {
				Date d1 = DateUtil.parseDate8(operateDt);
				Date d2 = DateUtil.parseDate8(DateUtil.nextDay(d1));
				c.andRecCrtTsGreaterThanOrEqualTo(d1);
				c.andRecCrtTsLessThan(d2);
			}
			
			// 交易序列号
			String transSeqId = StringUtil.trimStr(qryParamMap.get("transSeqId"));
			if (!StringUtil.isBlank(transSeqId)) {
				c.andTransSeqIdEqualTo(transSeqId);
			}
			
			// 交易金额
			String transAt = StringUtil.trimStr(qryParamMap.get("transAt"));
			if (!StringUtil.isBlank(transAt)) {
				c.andTransAtEqualTo(Long.parseLong(transAt));
			}
		}
		// 排序字段
		example.setOrderByClause("seq_id desc");
		return example;
	}

	private MerAccountFlowMapper getMapper() {
		return this.getMapper(MerAccountFlowMapper.class);
	}
}
