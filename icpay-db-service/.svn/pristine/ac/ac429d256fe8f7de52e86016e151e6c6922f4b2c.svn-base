package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.MerSettlePolicyMapper;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicy;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicyExample;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicyKey;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicyExample.Criteria;
import com.icpay.payment.db.service.IMerSettlePolicyService;

@Service("merSettlePolicyService")
public class MerSettlePolicyService extends BaseService implements IMerSettlePolicyService {

	@Override
	public Pager<MerSettlePolicy> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		MerSettlePolicyExample example = this.getQryExample(qryParamMap);
		MerSettlePolicyMapper mapper = getMapper();
		Pager<MerSettlePolicy> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	@Override
	public void add(MerSettlePolicy entity) {
		AssertUtil.objIsNull(entity, "entity is null.");
		this.getMapper().insert(entity);
	}

	@Override
	public void delete(MerSettlePolicyKey key) {
		AssertUtil.objIsNull(key, "key is null.");
		this.getMapper().deleteByPrimaryKey(key);
	}

	@Override
	public MerSettlePolicy selectByPrimaryKey(MerSettlePolicyKey key) {
		return this.getMapper().selectByPrimaryKey(key);
	}

	@Override
	public void update(MerSettlePolicy entity) {
		i18ObjIsNull(entity, this.getClass().getSimpleName(), "待修改的记录为null");

		MerSettlePolicyKey key = new MerSettlePolicyKey();
		key.setMchntCd(entity.getMchntCd());
		key.setCurrCd(entity.getCurrCd());
		MerSettlePolicy dbEntity = this.selectByPrimaryKey(key);
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "待修改的记录不存在");

		
		// 更新数据库字段
//		BeanUtil.cloneEntity(entity, dbEntity, new String[] {
//				"settleAccount", "settleAccountName", "settleAccountBankName",
//				"comment", "lastOperId", "settleLimit", "settleAccountBankCode",
//				"settleAccountAreaCode", "settleAccountAreaInfo","balanceTransfer","balanceTransferT1","transferDate"
//		});
		
		BeanUtil.cloneEntity(entity, dbEntity, null, new String[] {"recUpdTs"}, true);
		dbEntity.setRecUpdTs(new Date());
		
		// 保存至数据库
		getMapper().updateByPrimaryKey(dbEntity);
	}
	
	@Override
	protected MerSettlePolicyExample buildQryExample(Map<String, String> qryParamMap) {
		MerSettlePolicyExample example = new MerSettlePolicyExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:商户代码
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
//				c.andMchntCdLike("%" + mchntCd + "%");
				c.andMchntCdEqualTo(mchntCd);
			}
			
			// 查询条件:清算周期
			String settlePeriod = StringUtil.trim(qryParamMap.get("settlePeriod"));
			if (!StringUtil.isBlank(settlePeriod)) {
				c.andSettlePeriodEqualTo(settlePeriod);
			}
						
			// 查询条件:D0结转
			String balanceTransfer = StringUtil.trim(qryParamMap.get("balanceTransfer"));
			if (!StringUtil.isBlank(balanceTransfer)) {
				c.andBalanceTransferEqualTo(balanceTransfer);
			}
			
			// 查询条件:T1返还
			String balanceTransferT1 = StringUtil.trim(qryParamMap.get("balanceTransferT1"));
			if (!StringUtil.isBlank(balanceTransferT1)) {
				c.andBalanceTransferT1EqualTo(balanceTransferT1);
			}
			
			// 查询条件:前置T1返还比例
			String preTransferT1Percent = StringUtil.trim(qryParamMap.get("preTransferT1Percent"));
			if (!StringUtil.isBlank(preTransferT1Percent)) {
				c.andPreTransferT1PercentEqualTo(preTransferT1Percent);
			}
			
			// 查询条件：幣別
			String currCd = StringUtil.trim(qryParamMap.get("currCd"));
			if (!StringUtil.isBlank(currCd)) {
				c.andCurrCdEqualTo(currCd);
			}
		}
		// 排序字段
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}

	private MerSettlePolicyMapper getMapper() {
		return this.getMapper(MerSettlePolicyMapper.class);
	}
}
