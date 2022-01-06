package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.VirtualTermInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.VirtualTermInfo;
import com.icpay.payment.db.dao.mybatis.model.VirtualTermInfoExample;
import com.icpay.payment.db.dao.mybatis.model.VirtualTermInfoKey;
import com.icpay.payment.db.dao.mybatis.model.VirtualTermInfoExample.Criteria;
import com.icpay.payment.db.service.IVirtualTermInfoService;

@Service("virtualTermInfoService")
public class VirtualTermInfoService extends BaseService implements IVirtualTermInfoService {

	@Override
	public void add(VirtualTermInfo entity) {
		AssertUtil.objIsNull(entity, "entity is null.");
		AssertUtil.strIsBlank(entity.getChnlId(), "chnlId is blank.");
		AssertUtil.strIsBlank(entity.getMchntCd(), "mchntCd is blank.");

		// 通过控制台新增渠道终端信息时，状态为未签到，MAK和PIK字段，均为空，签到后由联机来填充
		entity.setState(TxnEnums.VirtualTermStatus._0.getCode());
		entity.setMak("");
		entity.setPik("");
		Date now = new Date();
		entity.setRecCrtTs(now);
		entity.setRecUpdTs(now);
		
		this.getMapper().insert(entity);
	}

	@Override
	public void delete(VirtualTermInfoKey key) {
		AssertUtil.objIsNull(key, "key is null.");
		AssertUtil.strIsBlank(key.getChnlId(), "chnlId is blank.");
		AssertUtil.strIsBlank(key.getMchntCd(), "mchntCd is blank.");
		
		VirtualTermInfoMapper mapper = this.getMapper();
		VirtualTermInfo dbEntity = mapper.selectByPrimaryKey(key);
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "待删除记录不存在");

		mapper.deleteByPrimaryKey(key);
	}
	
	@Override
	public List<VirtualTermInfo> select(Map<String, String> qryParamMap) {
		VirtualTermInfoExample example = this.getQryExample(qryParamMap);
		return this.getMapper().selectByExample(example);
	}

	@Override
	public Pager<VirtualTermInfo> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		VirtualTermInfoExample example = this.getQryExample(qryParamMap);
		VirtualTermInfoMapper mapper = getMapper();
		Pager<VirtualTermInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public VirtualTermInfo selectByPrimarykey(VirtualTermInfoKey key) {
		AssertUtil.objIsNull(key, "key is null.");
		AssertUtil.strIsBlank(key.getChnlId(), "chnlId is blank.");
		AssertUtil.strIsBlank(key.getMchntCd(), "mchntCd is blank.");
		
		return this.getMapper().selectByPrimaryKey(key);
	}

	@Override
	public void update(VirtualTermInfo entity) {
		AssertUtil.objIsNull(entity, "entity is null.");
		AssertUtil.strIsBlank(entity.getChnlId(), "chnlId is blank.");
		AssertUtil.strIsBlank(entity.getMchntCd(), "mchntCd is blank.");
		
		VirtualTermInfoMapper mapper = this.getMapper();
		VirtualTermInfo dbEntity = mapper.selectByPrimaryKey(entity);
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "待更新记录不存在");
		
		BeanUtil.cloneEntity(entity, dbEntity, new String[] {
				"termId", "lastOperId", "autoSignIn", "chnlMchntNm", 
				"tradeType", "feeRate", "dailyLimit", "virtualFlag","topFee"
		});
		
		dbEntity.setRecUpdTs(new Date());
		
		mapper.updateByPrimaryKey(dbEntity);
	}

	protected VirtualTermInfoExample buildQryExample(Map<String, String> qryParamMap) {
		VirtualTermInfoExample example = new VirtualTermInfoExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件: chnlId
			String chnlId = StringUtil.trim(qryParamMap.get("chnlId"));
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}
			// 查询条件: mchntCd
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			
			/// Robin Modified : {{
			// 查询条件: termId
			String termId = StringUtil.trim(qryParamMap.get("termId"));
			if (!StringUtil.isBlank(termId)) {
				c.andTermIdEqualTo(termId);
			}
			/// }}
			
			// 查询条件: state
			String state = StringUtil.trim(qryParamMap.get("state"));
			if (!StringUtil.isBlank(state)) {
				c.andStateEqualTo(state);
			}
			// 查询条件: autoSignIn
			String autoSignIn = StringUtil.trim(qryParamMap.get("autoSignIn"));
			if (!StringUtil.isBlank(autoSignIn)) {
				c.andAutoSignInEqualTo(autoSignIn);
			}
			// 查询条件: 行业类别
			String tradeType = StringUtil.trim(qryParamMap.get("tradeType"));
			if (!StringUtil.isBlank(tradeType)) {
				c.andTradeTypeEqualTo(tradeType);
			}
			// 查询条件: 是否归入商户吃
			String virtualFlag = StringUtil.trimStr(qryParamMap.get("virtualFlag"));
			if (!StringUtil.isBlank(virtualFlag)) {
				c.andVirtualFlagEqualTo(virtualFlag);
			}
		}
		// 排序字段
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}
	
	private VirtualTermInfoMapper getMapper() {
		return this.getMapper(VirtualTermInfoMapper.class);
	}

}
