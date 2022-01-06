package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.CashPoolInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.CashPoolInfo;
import com.icpay.payment.db.dao.mybatis.model.CashPoolInfoExample;
import com.icpay.payment.db.dao.mybatis.model.CashPoolInfoExample.Criteria;
import com.icpay.payment.db.service.ICashPoolInfoService;

@Service("cashPoolInfoService")
public class CashPoolInfoService extends BaseService implements ICashPoolInfoService {
	
	@Override
	public Pager<CashPoolInfo> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		CashPoolInfoExample example = this.getQryExample(qryParamMap);
		example.setOrderByClause("rec_upd_ts desc");
		CashPoolInfoMapper mapper = getMapper();
		Pager<CashPoolInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	/**
	 * 获取全部通道信息
	 * @return
	 */
	@Override
	public List<CashPoolInfo> getAllCashPoolInfo() {
		CashPoolInfoExample example = new CashPoolInfoExample();
		example.createCriteria().andStateEqualTo(CommonEnums.ChnlSt._1.getCode());
		example.setOrderByClause("pool_id asc");
		return this.getMapper().selectByExample(example);
	}
	
	@Override
	public CashPoolInfo selectByPrimaryKey(String chnlId) {
		i18StrIsBlank(chnlId, this.getClass().getSimpleName(), "资金池代码为空");
		return getMapper().selectByPrimaryKey(chnlId);
	}
	
	/**
	 * 新增
	 */
	public void add(CashPoolInfo CashPoolInfo) {
		i18ArgIsNull(CashPoolInfo, this.getClass().getSimpleName(), "待保存的资金池信息对象为null");

		String poolId = CashPoolInfo.getPoolId();
		i18StrIsBlank(poolId, this.getClass().getSimpleName(), "资金池码为空");

		CashPoolInfo m = this.selectByPrimaryKey(poolId);
		i18ObjIsNotNull(m, this.getClass().getSimpleName(), "资金池信息信息已存在: %s", poolId);

		Date now = new Date();
		CashPoolInfo.setRecCrtTs(now);
		CashPoolInfo.setRecUpdTs(now);
		
		getMapper().insert(CashPoolInfo);
	}
	
	/**
	 * 修改
	 */
	public void update(CashPoolInfo CashPoolInfo) {
		i18ArgIsNull(CashPoolInfo, this.getClass().getSimpleName(), "待更新的资金池信息对象为null");
		String poolId = CashPoolInfo.getPoolId();
		i18StrIsBlank(poolId, this.getClass().getSimpleName(), "功能码不能为空");
		CashPoolInfo dbEntity = this.selectByPrimaryKey(poolId);
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "资金池信息信息不存在： %s", poolId);

		// 更新数据库字段
		BeanUtil.cloneEntity(CashPoolInfo, dbEntity, new String[] {
				"poolDesc", "state", "lastOperId"});
		dbEntity.setRecUpdTs(new Date());
		
		getMapper().updateByPrimaryKey(dbEntity);
	}
	
	/**
	 * 删除
	 * @param funcCd
	 * @return
	 */
	public void delete(String chnlId) {
		i18ArgIsBlank(chnlId, this.getClass().getSimpleName(), "资金池ID不能为空");
		CashPoolInfo m = this.selectByPrimaryKey(chnlId);
		i18ObjIsNull(m, this.getClass().getSimpleName(), "待删除的资金池信息不存在： %s", chnlId);

		getMapper().deleteByPrimaryKey(chnlId);
	}
	
	private CashPoolInfoMapper getMapper() {
		return this.getMapper(CashPoolInfoMapper.class);
	}

	@Override
	protected CashPoolInfoExample buildQryExample(Map<String, String> qryParamMap) {
		CashPoolInfoExample example = new CashPoolInfoExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();

			// 查询条件：渠道
			String poolId = StringUtil.trim(qryParamMap.get("poolId"));
			if (!StringUtil.isBlank(poolId)) {
				c.andPoolIdEqualTo(poolId);
			}
			
			// 查询条件：
			String state = StringUtil.trim(qryParamMap.get("state"));
			if (!StringUtil.isBlank(state)) {
				c.andStateEqualTo(state);
			}
			
			// 查询条件：名称
			String poolDesc = StringUtil.trim(qryParamMap.get("poolDesc"));
			if (!StringUtil.isBlank(poolDesc)) {
				c.andPoolDescLike("%" + poolDesc + "%");
			}
			
			// 查询条件：名称
			String currCd = StringUtil.trim(qryParamMap.get("currCd"));
			if (!StringUtil.isBlank(currCd)) {
				c.andCurrCdEqualTo(currCd);
			}
		}
		return example;
	}
	
	
	@Override
	public List<CashPoolInfo> getCashPoolInfo() {
		CashPoolInfoExample example = new CashPoolInfoExample();
		example.setOrderByClause("pool_id asc");
		return this.getMapper().selectByExample(example);
	}
}
