package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.SettleProfitResultMapper;
import com.icpay.payment.db.dao.mybatis.model.SettleProfitResult;
import com.icpay.payment.db.dao.mybatis.model.SettleProfitResultExample;
import com.icpay.payment.db.dao.mybatis.model.SettleProfitResultExample.Criteria;
import com.icpay.payment.db.service.ISettleProfitResultService;

@Service("settleProfitResultService")
public class SettleProfitResultService extends BaseService implements ISettleProfitResultService {

	@Override
	public Pager<SettleProfitResult> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		SettleProfitResultExample example = this.getQryExample(qryParamMap);
		SettleProfitResultMapper mapper = getMapper();
		Pager<SettleProfitResult> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public SettleProfitResult selectByPrimaryKey(String transSeqId) {
		return this.getMapper().selectByPrimaryKey(transSeqId);
	}

	@Override
	protected SettleProfitResultExample buildQryExample(Map<String, String> qryParamMap) {
		SettleProfitResultExample example = new SettleProfitResultExample();
		Criteria c = example.createCriteria();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			String settleDt = StringUtil.trimStr(qryParamMap.get("settleDt"));
			if (!StringUtil.isBlank(settleDt)) {
				c.andSettleDtEqualTo(settleDt);
			}
			String crtDt = StringUtil.trimStr(qryParamMap.get("crtDt"));
			if (!StringUtil.isBlank(crtDt)) {
				Date dt1 = DateUtil.parseDate8(crtDt);
				Date dt2 = DateUtil.parseDate8(DateUtil.nextDay(dt1));
				c.andRecCrtTsGreaterThanOrEqualTo(dt1);
				c.andRecCrtTsLessThan(dt2);
			}
			String agentCd = StringUtil.trimStr(qryParamMap.get("agentCd"));
			if (!StringUtil.isBlank(agentCd)) {
				c.andAgentCdEqualTo(agentCd);
			}
			String mchntCd = StringUtil.trimStr(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			String termSn = StringUtil.trimStr(qryParamMap.get("termSn"));
			if (!StringUtil.isBlank(termSn)) {
				c.andTermSnEqualTo(termSn);
			}
			String intTransCd = StringUtil.trimStr(qryParamMap.get("intTransCd"));
			if (!StringUtil.isBlank(intTransCd)) {
				c.andIntTransCdEqualTo(intTransCd);
			}
			String orderId = StringUtil.trimStr(qryParamMap.get("orderId"));
			if (!StringUtil.isBlank(orderId)) {
				c.andOrderIdEqualTo(orderId);
			}
		}
		example.setOrderByClause("rec_crt_ts desc");
		return example;
	}
	
	private SettleProfitResultMapper getMapper() {
		return super.getMapper(SettleProfitResultMapper.class);
	}
}
