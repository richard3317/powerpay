package com.icpay.payment.db.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.SysSettleProfitResultMapper;
import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitResultExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitResult;
import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitResultExample;
import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitResultKey;
import com.icpay.payment.db.service.ISysSettleProfitResultService;

@Service("sysSettleProfitResult")
public class SysSettleProfitResultService extends BaseService implements ISysSettleProfitResultService {

	@Override
	public long countByExample(Map<String,String> qryParamMap) {
		SysSettleProfitResultExample example = this.getQryExample(qryParamMap);
		return dao().countByExample(example);
	}
	
	@Override
	public int deleteByExample(SysSettleProfitResultExample example) {
		return dao().deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(SysSettleProfitResultKey key) {
		return dao().deleteByPrimaryKey(key);
	}
	
	@Override
	public int deleteByExample(Map<String,String> qryParamMap) {
		SysSettleProfitResultExample example = this.getQryExample(qryParamMap);
		return dao().deleteByExample(example);
	}

	@Override
	public int insert(SysSettleProfitResult record) {
		return dao().insert(record);
	}

	@Override
	public int insertSelective(SysSettleProfitResult record) {
		return dao().insertSelective(record);
	}

	@Override
	public List<SysSettleProfitResult> selectByExample(Map<String,String> qryParamMap) {
		SysSettleProfitResultExample example = this.getQryExample(qryParamMap);
		return dao().selectByExample(example);
	}
	
	@Override
	public Pager<SysSettleProfitResult> selectByPage(int pageNum, int pageSize,Map<String,String> qryParamMap) {
		SysSettleProfitResultExample example = this.getQryExample(qryParamMap);
		Pager<SysSettleProfitResult> pager = buildPager(pageNum, pageSize, qryParamMap);
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);

		pager.setTotal(dao().countByExample(example));
		pager.setResultList(dao().selectByPage(example));
		return pager;
	}

	@Override
	public SysSettleProfitResult selectByPrimaryKey(SysSettleProfitResultKey key) {
		return dao().selectByPrimaryKey(key);
	}

	private SysSettleProfitResultMapper dao() {
		return this.getMapper(SysSettleProfitResultMapper.class);
	}	
	
	@Override
	protected SysSettleProfitResultExample buildQryExample(Map<String, String> qryParamMap) {
		SysSettleProfitResultExample example = new SysSettleProfitResultExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:结算日期
			String settleDate = StringUtil.trim(qryParamMap.get("settleDate"));
			if (!StringUtil.isBlank(settleDate)) {
				c.andSettleDateEqualTo(settleDate);
			}
			// 查询条件:前端商户号
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			// 查询条件:渠道编号
			String chnlId = StringUtil.trim(qryParamMap.get("chnlId"));
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}
			// 查询条件:渠道商户号
			String chnlMchntCd = StringUtil.trim(qryParamMap.get("chnlMchntCd"));
			if (!StringUtil.isBlank(chnlMchntCd)) {
				c.andChnlMchntCdEqualTo(chnlMchntCd);
			}
		}
		// 排序字段
		example.setOrderByClause("settle_date desc");
		return example;
	}

}
