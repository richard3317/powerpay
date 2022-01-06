package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.SettleResFileMapper;
import com.icpay.payment.db.dao.mybatis.model.SettleResFile;
import com.icpay.payment.db.dao.mybatis.model.SettleResFileExample;
import com.icpay.payment.db.dao.mybatis.model.SettleResFileExample.Criteria;
import com.icpay.payment.db.service.ISettleResFileService;

@Service("settleResFileService")
public class SettleResFileService extends BaseService implements ISettleResFileService {

	@Override
	public Pager<SettleResFile> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		SettleResFileExample example = this.getQryExample(qryParamMap);
		SettleResFileMapper mapper = getMapper();
		Pager<SettleResFile> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public SettleResFile selectByPrimaryKey(int seqId) {
		return this.getMapper().selectByPrimaryKey(seqId);
	}

	@Override
	public void update(SettleResFile entity) {
		i18ArgIsNull(entity, this.getClass().getSimpleName(), "待更新对象为null");

		SettleResFileMapper mapper = getMapper();
		SettleResFile dbEntity = mapper.selectByPrimaryKey(entity.getSeqId());
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "待更新记录不存在： %s", entity.getSeqId().toString());

		// 更新数据库字段
		BeanUtil.cloneEntity(entity, dbEntity, new String[] {"state", "lastOperId", "comment"});
		dbEntity.setRecUpdTs(new Date());
		mapper.updateByPrimaryKey(dbEntity);
	}

	@Override
	protected SettleResFileExample buildQryExample(Map<String, String> qryParamMap) {
		SettleResFileExample example = new SettleResFileExample();
		Criteria c = example.createCriteria();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			String settleDt = StringUtil.trimStr(qryParamMap.get("settleDt"));
			if (!StringUtil.isBlank(settleDt)) {
				c.andSettleDtEqualTo(settleDt);
			}
			String settleBt = StringUtil.trimStr(qryParamMap.get("settleBt"));
			if (!StringUtil.isBlank(settleBt)) {
				c.andSettleBtEqualTo(Integer.parseInt(settleBt));
			}
			String state = StringUtil.trimStr(qryParamMap.get("state"));
			if (!StringUtil.isBlank(state)) {
				c.andStateEqualTo(state);
			}
		}
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}
	
	private SettleResFileMapper getMapper() {
		return super.getMapper(SettleResFileMapper.class);
	}
}
