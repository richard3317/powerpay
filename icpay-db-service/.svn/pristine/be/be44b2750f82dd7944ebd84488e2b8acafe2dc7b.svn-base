package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.ProfitResFileMapper;
import com.icpay.payment.db.dao.mybatis.model.ProfitResFile;
import com.icpay.payment.db.dao.mybatis.model.ProfitResFileExample;
import com.icpay.payment.db.dao.mybatis.model.ProfitResFileExample.Criteria;
import com.icpay.payment.db.service.IProfitResFileService;

@Service("profitResFileService")
public class ProfitResFileService extends BaseService implements IProfitResFileService {

	@Override
	public Pager<ProfitResFile> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		ProfitResFileExample example = this.getQryExample(qryParamMap);
		ProfitResFileMapper mapper = getMapper();
		Pager<ProfitResFile> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public ProfitResFile selectByPrimaryKey(int seqId) {
		return this.getMapper().selectByPrimaryKey(seqId);
	}
	
	@Override
	public void update(ProfitResFile entity) {
		AssertUtil.objIsNull(entity, "entity is null");
		ProfitResFileMapper mapper = this.getMapper();
		ProfitResFile dbEntity = mapper.selectByPrimaryKey(entity.getSeqId());
		i18ObjIsNull(entity, this.getClass().getSimpleName(), "该记录不存在: %s", entity.getSeqId().toString());

		// 目前只允许修改状态和最后操作员
		BeanUtil.cloneEntity(entity, dbEntity, new String[] {
				"state", "lastOperId"
		});
		dbEntity.setRecUpdTs(new Date());
		mapper.updateByPrimaryKey(dbEntity);
	}

	@Override
	protected ProfitResFileExample buildQryExample(Map<String, String> qryParamMap) {
		ProfitResFileExample example = new ProfitResFileExample();
		Criteria c = example.createCriteria();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			String profitDt = qryParamMap.get("profitDt");
			if (!StringUtil.isBlank(profitDt)) {
				c.andProfitDtEqualTo(profitDt);
			}
		}
		example.setOrderByClause("rec_crt_ts desc");
		return example;
	}
	
	private ProfitResFileMapper getMapper() {
		return super.getMapper(ProfitResFileMapper.class);
	}
	
}
