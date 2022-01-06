package com.icpay.payment.db.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.AcctChkResultTotalMapper;
import com.icpay.payment.db.dao.mybatis.model.AcctChkResultTotal;
import com.icpay.payment.db.dao.mybatis.model.AcctChkResultTotalExample;
import com.icpay.payment.db.dao.mybatis.model.AcctChkResultTotalExample.Criteria;
import com.icpay.payment.db.service.IAcctChkResultTotalService;

@Service("acctChkResultTotalService")
public class AcctChkResultTotalService extends BaseService implements IAcctChkResultTotalService {

	@Override
	public Pager<AcctChkResultTotal> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		AcctChkResultTotalExample example = this.getQryExample(qryParamMap);
		AcctChkResultTotalMapper mapper = getMapper();
		Pager<AcctChkResultTotal> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public AcctChkResultTotal selectByPrimaryKey(int seqId) {
		return this.getMapper().selectByPrimaryKey(seqId);
	}

	private AcctChkResultTotalMapper getMapper() {
		return this.getMapper(AcctChkResultTotalMapper.class);
	}

	@Override
	protected AcctChkResultTotalExample buildQryExample(Map<String, String> qryParamMap) {
		AcctChkResultTotalExample example = new AcctChkResultTotalExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件: 对账日期
			String checkDt = StringUtil.trim(qryParamMap.get("checkDt"));
			if (!StringUtil.isBlank(checkDt)) {
				c.andCheckDtEqualTo(checkDt);
			}
			
			// 查询条件: 商户号
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}

			// 查询条件：渠道
			String chnlId = StringUtil.trim(qryParamMap.get("chnlId"));
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}
			
			// 查询条件：对账结果
			String checkResult = StringUtil.trim(qryParamMap.get("checkResult"));
			if (!StringUtil.isBlank(checkResult)) {
				c.andCheckResultEqualTo(checkResult);
			}
		}
		// 排序字段
		example.setOrderByClause("rec_upd_ts asc");
		return example;
	}
}
