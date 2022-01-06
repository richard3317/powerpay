package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.ChnlMchntMappingInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntMappingInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntMappingInfoExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntMappingInfoKey;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntMappingInfoExample.Criteria;
import com.icpay.payment.db.service.IChnlMchntMappingInfoService;

@Service("chnlMchntMappingInfoService")
public class ChnlMchntMappingInfoService extends BaseService implements IChnlMchntMappingInfoService {

	@Override
	public void add(ChnlMchntMappingInfo entity) {
		AssertUtil.objIsNull(entity, "entity is null");
		AssertUtil.strIsBlank(entity.getChnlId(), "chnlId is blank.");
		AssertUtil.strIsBlank(entity.getMchntCd(), "mchntCd is blank.");
		AssertUtil.strIsBlank(entity.getChnlMchntCd(), "chnlMchntCd is blank.");
		
		ChnlMchntMappingInfoMapper mapper = getMapper();
		ChnlMchntMappingInfo dbEntity = mapper.selectByPrimaryKey(entity);
		i18ObjIsNotNull(dbEntity, this.getClass().getSimpleName(), "该映射关系已存在");

		Date now = new Date();
		entity.setRecCrtTs(now);
		entity.setRecUpdTs(now);
		this.getMapper().insert(entity);
	}

	@Override
	public void delete(ChnlMchntMappingInfoKey key) {
		AssertUtil.objIsNull(key, "entity is null");
		AssertUtil.strIsBlank(key.getChnlId(), "chnlId is blank.");
		AssertUtil.strIsBlank(key.getMchntCd(), "mchntCd is blank.");
		AssertUtil.strIsBlank(key.getChnlMchntCd(), "chnlMchntCd is blank.");
		
		ChnlMchntMappingInfoMapper mapper = getMapper();
		ChnlMchntMappingInfo dbEntity = mapper.selectByPrimaryKey(key);
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "该映射关系不存在");

		mapper.deleteByPrimaryKey(key);
	}
	
	@Override
	public List<ChnlMchntMappingInfo> select(Map<String, String> qryParamMap) {
		ChnlMchntMappingInfoExample example = this.getQryExample(qryParamMap);
		ChnlMchntMappingInfoMapper mapper = getMapper();
		return mapper.selectByExample(example);
	}

	@Override
	public Pager<ChnlMchntMappingInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		ChnlMchntMappingInfoExample example = this.getQryExample(qryParamMap);
		ChnlMchntMappingInfoMapper mapper = getMapper();
		Pager<ChnlMchntMappingInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public ChnlMchntMappingInfo selectByPrimaryKey(ChnlMchntMappingInfoKey key) {
		return this.getMapper().selectByPrimaryKey(key);
	}

	@Override
	protected ChnlMchntMappingInfoExample buildQryExample(Map<String, String> qryParamMap) {
		ChnlMchntMappingInfoExample example = new ChnlMchntMappingInfoExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件: 渠道号
			String chnlId = StringUtil.trim(qryParamMap.get("chnlId"));
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}
			// 查询条件: 前端商户号
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			// 查询条件:渠道商户号
			String chnlMchntCd = StringUtil.trim(qryParamMap.get("chnlMchntCd"));
			if (!StringUtil.isBlank(chnlMchntCd)) {
				c.andChnlMchntCdEqualTo(chnlMchntCd);
			}
		}
		// 排序字段
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}
	
	private ChnlMchntMappingInfoMapper getMapper() {
		return this.getMapper(ChnlMchntMappingInfoMapper.class);
	}

	@Override
	public void cmt(ChnlMchntMappingInfoKey key, String comment, String lastOperId) {
		ChnlMchntMappingInfoMapper mapper = this.getMapper();
		ChnlMchntMappingInfo dbEntity = mapper.selectByPrimaryKey(key);
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "未找到记录");

		dbEntity.setComment(comment);
		dbEntity.setRecUpdTs(new Date());
		dbEntity.setLastOperId(lastOperId);
		mapper.updateByPrimaryKey(dbEntity);
	}
}
