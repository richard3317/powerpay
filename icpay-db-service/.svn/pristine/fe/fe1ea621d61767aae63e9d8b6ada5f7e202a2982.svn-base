package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.ChnlVirtualMerSettleInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.ChnlVirtualMerSettleInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlVirtualMerSettleInfoExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlVirtualMerSettleInfoExample.Criteria;
import com.icpay.payment.db.service.IChnlVirtualMerSettleInfoService;

@Service("chnlVirtualMerSettleInfoService")
public class ChnlVirtualMerSettleInfoService extends BaseService implements IChnlVirtualMerSettleInfoService {

	@Override
	public Pager<ChnlVirtualMerSettleInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		ChnlVirtualMerSettleInfoExample example = this.getQryExample(qryParamMap);
		ChnlVirtualMerSettleInfoMapper mapper = getMapper();
		Pager<ChnlVirtualMerSettleInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public ChnlVirtualMerSettleInfo selectByPrimaryKey(int seqId) {
		return this.getMapper().selectByPrimaryKey(seqId);
	}

	@Override
	public void update(ChnlVirtualMerSettleInfo entity) {
		i18ArgIsNull(entity, this.getClass().getSimpleName(), "待更新对象为null");

		ChnlVirtualMerSettleInfoMapper mapper = getMapper();
		ChnlVirtualMerSettleInfo dbEntity = mapper.selectByPrimaryKey(entity.getSeqId());
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "待更新记录不存在： %s" , entity.getSeqId().toString());
		// 更新数据库字段
		BeanUtil.cloneEntity(entity, dbEntity, new String[] {"state", "lastOperId", "comment"});
		dbEntity.setRecUpdTs(new Date());
		mapper.updateByPrimaryKey(dbEntity);
	}

	@Override
	protected ChnlVirtualMerSettleInfoExample buildQryExample(Map<String, String> qryParamMap) {
		ChnlVirtualMerSettleInfoExample example = new ChnlVirtualMerSettleInfoExample();
		Criteria c = example.createCriteria();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			String mchntCd = StringUtil.trimStr(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			String chnlId = StringUtil.trimStr(qryParamMap.get("chnlId"));
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}
			String settleDt = StringUtil.trimStr(qryParamMap.get("settleDt"));
			if (!StringUtil.isBlank(settleDt)) {
				c.andSettleDtEqualTo(settleDt);
			}
		}
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}
	
	private ChnlVirtualMerSettleInfoMapper getMapper() {
		return super.getMapper(ChnlVirtualMerSettleInfoMapper.class);
	}
}
