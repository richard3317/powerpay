package com.icpay.payment.db.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.ChnlMchntSettlePolicyMapper;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntSettlePolicy;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntSettlePolicyExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntSettlePolicyExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntSettlePolicyKey;
import com.icpay.payment.db.service.IChnlMchntSettlePolicyService;

@Service("chnlMchntSettlePolicyService")
public class ChnlMchntSettlePolicyService extends BaseService implements IChnlMchntSettlePolicyService {

	@Override
	public Pager<ChnlMchntSettlePolicy> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		ChnlMchntSettlePolicyExample example = this.getQryExample(qryParamMap);
		example.setOrderByClause("rec_upd_ts desc");
		ChnlMchntSettlePolicyMapper mapper = getMapper();
		Pager<ChnlMchntSettlePolicy> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	
	@Override
	public ChnlMchntSettlePolicy selectByPrimaryKey(ChnlMchntSettlePolicyKey key) {
		return getMapper().selectByPrimaryKey(key);
	}
	
	/**
	 * 新增
	 */
	public void add(ChnlMchntSettlePolicy record) {
		getMapper().insert(record);
	}
	
	/**
	 * 修改
	 */
	public void update(ChnlMchntSettlePolicy record) {
		
		getMapper().updateByPrimaryKey(record);
	}
	
	/**
	 * 删除
	 * @param funcCd
	 * @return
	 */
	public void delete(ChnlMchntSettlePolicyKey key) {
		getMapper().deleteByPrimaryKey(key);
	}
	
	private ChnlMchntSettlePolicyMapper getMapper() {
		return this.getMapper(ChnlMchntSettlePolicyMapper.class);
	}

	@Override
	protected ChnlMchntSettlePolicyExample buildQryExample(Map<String, String> qryParamMap) {
		ChnlMchntSettlePolicyExample example = new ChnlMchntSettlePolicyExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();

			// 查询条件: 商户号
			String chnlMchntCd = StringUtil.trim(qryParamMap.get("chnlMchntCd"));
			if (!StringUtil.isBlank(chnlMchntCd)) {
				c.andChnlMchntCdEqualTo(chnlMchntCd);
			}
			
			// 查询条件：渠道
			String chnlId = StringUtil.trim(qryParamMap.get("chnlId"));
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}

			
//			// 查询条件：商户名称
//			String chnlMchntDesc = StringUtil.trim(qryParamMap.get("chnlMchntDesc"));
//			if (!StringUtil.isBlank(chnlMchntDesc)) {
//				c.andChnlMchntDescLike("%" + chnlMchntDesc + "%");
//			}
			
//			// 查询条件：清算周期
//			String settlePeriod = StringUtil.trim(qryParamMap.get("settlePeriod"));
//			if (!StringUtil.isBlank(settlePeriod)) {
//				c.andSettlePeriodEqualTo(settlePeriod);
//			}
			
			// 查询关联条件: 商户号
			if(qryParamMap.containsKey("chnlMchntCdAssoc")) {
				String chnlMchntCdAssoc = StringUtil.trim(qryParamMap.get("chnlMchntCdAssoc"));
				if (!StringUtil.isBlank(chnlMchntCdAssoc)) {
					//String[] mchnts =  chnlMchntCdAssoc.split(";");
					//List<String> chnmmnList = Arrays.asList(mchnts);
					//c.andChnlMchntCdIn(chnmmnList);
					c.andChnlMchntCdLike(chnlMchntCdAssoc +"%");
				}
			}
		}
		return example;
	}
	
	@Override
	public List<ChnlMchntSettlePolicy> select( String chnlId, String chnlMchntCd) {
		AssertUtil.strIsBlank(chnlId, "chnlId is blank.");
		ChnlMchntSettlePolicyExample example = new ChnlMchntSettlePolicyExample();
		Criteria c = example.createCriteria();
		c.andChnlIdEqualTo(chnlId);
		c.andChnlMchntCdEqualTo(chnlMchntCd);
		return this.getMapper().selectByExample(example);
	}
}
