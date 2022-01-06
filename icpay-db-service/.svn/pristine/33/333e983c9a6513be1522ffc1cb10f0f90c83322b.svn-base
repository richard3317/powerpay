package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.ChnlCashPoolInfoMapper;
import com.icpay.payment.db.dao.mybatis.mapper.ChnlCashPoolInfoViewMapper;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfoExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfoKey;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfoView;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfoViewExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfoViewExample.Criteria;
import com.icpay.payment.db.service.IChnlCashPoolInfoService;

@Service("ChnlCashPoolInfoService")
public class ChnlCashPoolInfoService extends BaseService implements IChnlCashPoolInfoService {
	
	@Override
	public Pager<ChnlCashPoolInfoView> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		ChnlCashPoolInfoViewExample example = this.getQryExample(qryParamMap);
		example.setOrderByClause("rec_upd_ts desc");
		ChnlCashPoolInfoViewMapper mapper = getMapper();
		Pager<ChnlCashPoolInfoView> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	
	@Override
	public ChnlCashPoolInfo selectByPrimaryKey(ChnlCashPoolInfoKey info) {
		return getPoolMapper().selectByPrimaryKey(info);
	}
	
	/**
	 * 新增
	 */
	public void add(ChnlCashPoolInfo info) {
		i18ArgIsNull(info, this.getClass().getSimpleName(), "待保存的资金池信息对象为null");
		String poolId = info.getPoolId();
		String mchntCd = info.getChnlMchntCd();
		i18StrIsBlank(poolId, this.getClass().getSimpleName(), "资金池码为空");
		i18StrIsBlank(mchntCd, this.getClass().getSimpleName(), "商户号为空");

		ChnlCashPoolInfoKey key = new ChnlCashPoolInfoKey();
		key.setChnlMchntCd(mchntCd);
		key.setPoolId(poolId);
		ChnlCashPoolInfo m = this.selectByPrimaryKey(key);
		i18ObjIsNotNull(m, this.getClass().getSimpleName(), "资金池信息已存在: %s", poolId);

		Date now = new Date();
		info.setRecCrtTs(now);
		info.setRecUpdTs(now);
		
		getPoolMapper().insert(info);
	}
	
	/**
	 * 修改
	 */
	public void update(ChnlCashPoolInfo info) {
		i18ArgIsNull(info, this.getClass().getSimpleName(), "待更新的资金池配置信息对象为null");
		info.setRecUpdTs(new Date());
		getPoolMapper().updateByPrimaryKeySelective(info);
	}
	
	/**
	 * 删除
	 * @param funcCd
	 * @return
	 */
	public void delete(ChnlCashPoolInfoKey key) {
		
		i18ArgIsBlank(key.getChnlMchntCd(), this.getClass().getSimpleName(), "商户号不能为空");
		i18ArgIsBlank(key.getPoolId(), this.getClass().getSimpleName(), "资金池id不能为空");
		
		ChnlCashPoolInfo m = this.selectByPrimaryKey(key);
		i18ObjIsNull(m, this.getClass().getSimpleName(), "待删除的资金池信息不存在： %s", key.getPoolId());

		getPoolMapper().deleteByPrimaryKey(key);
	}
	
	private ChnlCashPoolInfoViewMapper getMapper() {
		return this.getMapper(ChnlCashPoolInfoViewMapper.class);
	}
	private ChnlCashPoolInfoMapper getPoolMapper() {
		return this.getMapper(ChnlCashPoolInfoMapper.class);
	}

	@Override
	protected ChnlCashPoolInfoViewExample buildQryExample(Map<String, String> qryParamMap) {
		ChnlCashPoolInfoViewExample example = new ChnlCashPoolInfoViewExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();

			// 查询条件：
			String chnlId = StringUtil.trim(qryParamMap.get("chnlId"));
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}
			
			String chnlMchntCd = StringUtil.trim(qryParamMap.get("chnlMchntCd"));
			if (!StringUtil.isBlank(chnlMchntCd)) {
				c.andChnlMchntCdEqualTo(chnlMchntCd);
			}
			
			// 查询条件：商户名
			String chnlMchntDesc = StringUtil.trim(qryParamMap.get("chnlMchntDesc"));
			if (!StringUtil.isBlank(chnlMchntDesc)) {
				c.andChnlMchntDescLike("%" + chnlMchntDesc + "%");
			}
			
			// 查询条件：资金池
			String poolId = StringUtil.trim(qryParamMap.get("poolId"));
			if (!StringUtil.isBlank(poolId)) {
				c.andPoolIdEqualTo(poolId);
			}
			
			// 查询条件：状态
			String state = StringUtil.trim(qryParamMap.get("state"));
			if (!StringUtil.isBlank(state)) {
				c.andStateEqualTo(state);
			}
			
			// 查询条件：出款状态
			String wdState = StringUtil.trim(qryParamMap.get("wdState"));
			if (!StringUtil.isBlank(wdState)) {
				c.andWdStateEqualTo(wdState);
			}
			
			 //查询条件：幣別
			String currCd = StringUtil.trim(qryParamMap.get("currCd"));
			if (!StringUtil.isBlank(currCd)) {
				c.andCurrCdEqualTo(currCd);
			}
			
		}
		return example;
	}

	protected ChnlCashPoolInfoExample buildExample(Map<String, String> qryParamMap) {
		ChnlCashPoolInfoExample example = new ChnlCashPoolInfoExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfoExample.Criteria c = example.createCriteria();

			// 查询条件：商户号
			String chnlMchntCd = StringUtil.trim(qryParamMap.get("chnlMchntCd"));
			if (!StringUtil.isBlank(chnlMchntCd)) {
				c.andChnlMchntCdEqualTo(chnlMchntCd);
			}
			
			// 查询条件：资金池
			String poolId = StringUtil.trim(qryParamMap.get("poolId"));
			if (!StringUtil.isBlank(poolId)) {
				c.andPoolIdEqualTo(poolId);
			}
			
			// 查询条件：状态
			String state = StringUtil.trim(qryParamMap.get("state"));
			if (!StringUtil.isBlank(state)) {
				c.andStateEqualTo(state);
			}
			
			// 查询条件：出款状态
			String wdState = StringUtil.trim(qryParamMap.get("wdState"));
			if (!StringUtil.isBlank(wdState)) {
				c.andWdStateEqualTo(wdState);
			}
			
			
		}
		return example;
	}

	@Override
	public List<ChnlCashPoolInfo> select(String chnlMchntCd) {
		ChnlCashPoolInfoMapper mapper = getPoolMapper();
		ChnlCashPoolInfoExample example =  new ChnlCashPoolInfoExample();
		example.createCriteria().andChnlMchntCdEqualTo(chnlMchntCd);
		return mapper.selectByPage(example);
	}


	@Override
	public List<ChnlCashPoolInfo> selectByPoolId(String poolId) {
		ChnlCashPoolInfoMapper mapper = getPoolMapper();
		ChnlCashPoolInfoExample example =  new ChnlCashPoolInfoExample();
		example.createCriteria().andPoolIdEqualTo(poolId);
		return mapper.selectByPage(example);
	}

	@Override
	public String selectSummaryByChnl(Map<String, String> qryParamMap) {
		ChnlCashPoolInfoViewExample example = this.getQryExample(qryParamMap);
		String sum = getMapper().selectSummaryByChnl(example);
        return sum;
	}
}
