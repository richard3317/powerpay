package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.OffPayBankMapper;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoExample;
import com.icpay.payment.db.dao.mybatis.model.OffPayBank;
import com.icpay.payment.db.dao.mybatis.model.OffPayBankExample;
import com.icpay.payment.db.dao.mybatis.model.OffPayBankKey;
import com.icpay.payment.db.service.IOffPayBankService;

@Service("offPayBankService")
public class OffPayBankService extends BaseService implements IOffPayBankService {
	
	@Override
	public Pager<OffPayBank> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		OffPayBankExample example = this.getQryExample(qryParamMap);
		OffPayBankMapper mapper = getMapper();
		Pager<OffPayBank> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	@Override
	public List<OffPayBank> selectByMap(Map<String, String> qryParamMap) {
		OffPayBankExample example = this.getQryExample(qryParamMap);
		return this.getMapper().selectByPage(example);
	}
	
	@Override
	protected OffPayBankExample buildQryExample(Map<String, String> qryParamMap) {
		OffPayBankExample example = new OffPayBankExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			com.icpay.payment.db.dao.mybatis.model.OffPayBankExample.Criteria c = example.createCriteria();
			// 查询条件：银行卡号
			String accNo = StringUtil.trim(qryParamMap.get("accNo"));
			if (!StringUtil.isBlank(accNo)) {
				c.andAccNoEqualTo(accNo);
			}
			// 查询条件：账户名称
			String accName = StringUtil.trim(qryParamMap.get("accName"));
			if (!StringUtil.isBlank(accName)) {
				c.andAccNameEqualTo(accName);
			}
			// 查询条件：状态
			String state = StringUtil.trim(qryParamMap.get("state"));
			if (!StringUtil.isBlank(state)) {
				c.andStateEqualTo(state);
			}
			// 查询条件：渠道编号
			String chnlId = StringUtil.trim(qryParamMap.get("chnlId"));
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}
			// 查询条件：渠道商户号
			String chnlMchntCd = StringUtil.trim(qryParamMap.get("chnlMchntCd"));
			if (!StringUtil.isBlank(chnlMchntCd)) {
				c.andChnlMchntCdEqualTo(chnlMchntCd);
			}
		}
		// 排序字段
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}
	
	/**
	 * 获取全部通道信息
	 * @return
	 */
	@Override
	public List<OffPayBank> getAllOffPayBank() {
		OffPayBankExample example = new OffPayBankExample();
		example.setOrderByClause("rec_upd_ts desc");
		return this.getMapper().selectByExample(example);
	}
	
	@Override
	public OffPayBank selectByPrimaryKey(String catalog, String accNo) {
		AssertUtil.strIsBlank(catalog, "catalog is blank.");
		AssertUtil.strIsBlank(accNo, "accNo is blank.");
		OffPayBankKey k = new OffPayBankKey();
		k.setCatalog(catalog);
		k.setAccNo(accNo);
		return this.getMapper().selectByPrimaryKey(k);
	}
	
	/**
	 * 新增
	 */
	public void add(OffPayBank offPayBank) {
		i18ArgIsNull(offPayBank, this.getClass().getSimpleName(), "待保存的银行卡号信息对象为null");

		String accNo = offPayBank.getAccNo();
		i18StrIsBlank(accNo, this.getClass().getSimpleName(), "银行卡号不能为空");
		String catalog = offPayBank.getCatalog();
		OffPayBank dbEntity = this.selectByPrimaryKey(catalog, accNo);
		i18ObjIsNotNull(dbEntity, this.getClass().getSimpleName(), "此银行卡号信息已存在: %s", accNo);

		Date now = new Date();
		offPayBank.setRecUpdTs(now);
		getMapper().insert(offPayBank);
	}
	
	/**
	 * 修改
	 */
	public void update(OffPayBank offPayBank) {
		i18ArgIsNull(offPayBank, this.getClass().getSimpleName(), "待更新的银行卡号信息对象为null");

		String accNo = offPayBank.getAccNo();
		i18StrIsBlank(accNo, this.getClass().getSimpleName(), "银行卡号不能为空");

		String catalog = offPayBank.getCatalog();
		OffPayBank dbEntity = this.selectByPrimaryKey(catalog, accNo);
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "银行卡号信息不存在： %s", accNo);

		// 更新数据库字段
		BeanUtil.cloneEntity(offPayBank, dbEntity, new String[] {
				"catalog", "bankName", "accName", "accountBankName",
				"state", "lastOperId"});
		dbEntity.setRecUpdTs(new Date());
		getMapper().updateByPrimaryKey(dbEntity);
	}
	
	/**
	 * 删除
	 */
	public void delete(String catalog, String accNo) {
		AssertUtil.strIsBlank(accNo, "accNo is blank.");
		if (StringUtil.isBlank(catalog)) {
			catalog = "DEFAULT";
		}
		OffPayBankKey k = new OffPayBankKey();
		k.setCatalog(catalog);
		k.setAccNo(accNo);
		this.getMapper().deleteByPrimaryKey(k);
	}
	
	private OffPayBankMapper getMapper() {
		return this.getMapper(OffPayBankMapper.class);
	}

	@Override
	public OffPayBank select(String mchntCd) {
		return this.getMapper().select(mchntCd);
	}

}
