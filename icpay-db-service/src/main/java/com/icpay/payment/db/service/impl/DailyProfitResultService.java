package com.icpay.payment.db.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.DailyProfitResultMapper;
import com.icpay.payment.db.dao.mybatis.model.DailyProfitResult;
import com.icpay.payment.db.dao.mybatis.model.DailyProfitResultExample;
import com.icpay.payment.db.dao.mybatis.model.DailyProfitResultExample.Criteria;
import com.icpay.payment.db.service.IDailyProfitResultService;

@Service("dailyProfitResultService")
public class DailyProfitResultService extends BaseService implements IDailyProfitResultService {
	
	private DailyProfitResultMapper getMapper() {
		return this.getMapper(DailyProfitResultMapper.class);
	}

	@Override
	public Pager<DailyProfitResult> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		DailyProfitResultExample example = new DailyProfitResultExample();
		example = this.buildQryExample(qryParamMap);
		
		Pager<DailyProfitResult> pager = new Pager<DailyProfitResult>();
		pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(this.getMapper().countByExample(example));
		pager.setResultList(this.getMapper().selectByPage(example));
		return pager;
	}
	
	@Override
	public List<DailyProfitResult> select(Map<String, String> qryParamMap) {
		DailyProfitResultExample example = new DailyProfitResultExample();
		example = this.buildQryExample(qryParamMap);
		return this.getMapper().selectByPage(example);
	}
	
	
	@Override
	protected DailyProfitResultExample buildQryExample(Map<String, String> qryParamMap) {
		DailyProfitResultExample example = new DailyProfitResultExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			//结算日期
			String settleDate = StringUtil.trim(qryParamMap.get("settleDate"));
			if (!StringUtil.isBlank(settleDate)) {
				c.andSettleDateEqualTo(settleDate);
			}
			//渠道编号
			String chnlId = StringUtil.trim(qryParamMap.get("chnlId"));
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}
			
			//mchntCnNm
			String mchntCnNm = StringUtil.trim(qryParamMap.get("mchntCnNm"));
			if (!StringUtil.isBlank(mchntCnNm)) {
				c.andMchntCnNmLike("%" + mchntCnNm + "%" );
			}
			
			String siteId = StringUtil.trim(qryParamMap.get("siteId"));
			if (!StringUtil.isBlank(siteId)) {
				c.andSiteIdEqualTo(siteId);
			}
			
		}
		// 排序字段
		example.setOrderByClause("settle_date desc, site_id, chnl_id, mchnt_cn_nm , int_trans_cd ");
		return example;
	}
	
}
