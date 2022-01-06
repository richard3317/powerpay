package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.TransTypeGroupMapper;
import com.icpay.payment.db.dao.mybatis.model.TransTypeGroup;
import com.icpay.payment.db.dao.mybatis.model.TransTypeGroupExample;
import com.icpay.payment.db.dao.mybatis.model.TransTypeGroupExample.Criteria;
import com.icpay.payment.db.service.ITransTypeGroupService;

@Service("transTypeGroupService")
public class TransTypeGroupService extends BaseService implements ITransTypeGroupService {

	@Override
	public Pager<TransTypeGroup> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		TransTypeGroupExample example = this.getQryExample(qryParamMap);
		TransTypeGroupMapper mapper = getMapper();
		Pager<TransTypeGroup> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	@Override
	public void add(TransTypeGroup entity) {
		AssertUtil.objIsNull(entity, "entity is null.");
		this.getMapper().insert(entity);
	}

	@Override
	public void delete(int seqId) {
		this.getMapper().deleteByPrimaryKey(seqId);
	}
	

	@Override
	public TransTypeGroup selectByPrimaryKey(int seqId) {
		return this.getMapper().selectByPrimaryKey(seqId);
	}

	@Override
	public void update(TransTypeGroup entity) {
		i18ObjIsNull(entity, this.getClass().getSimpleName(), "待修改的记录为null");

		TransTypeGroup dbEntity = this.selectByPrimaryKey(entity.getSeqId());
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "待修改的记录不存在");

		// 更新数据库字段
		BeanUtil.cloneEntity(entity, dbEntity, new String[] {
				"groupNm", "transType", "lastOperId"
		});
		dbEntity.setRecUpdTs(new Date());
		
		// 保存至数据库
		getMapper().updateByPrimaryKey(dbEntity);
	}

	@Override
	public List<TransTypeGroup> select(Map<String, String> qryParamMap) {
		TransTypeGroupExample example = this.getQryExample(qryParamMap);
		return this.getMapper().selectByExample(example);
	}

	@Override
	protected TransTypeGroupExample buildQryExample(Map<String, String> qryParamMap) {
		TransTypeGroupExample example = new TransTypeGroupExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			
			String seqId = StringUtil.trim(qryParamMap.get("seqId"));
			if (!StringUtil.isBlank(seqId)) {
				c.andSeqIdEqualTo(Integer.valueOf(seqId));
			}
			
			String groupNm = StringUtil.trim(qryParamMap.get("groupNm"));
			if (!StringUtil.isBlank(groupNm)) {
				c.andGroupNmLike("%" + groupNm + "%");
			}
		}
		// 排序字段
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}

	private TransTypeGroupMapper getMapper() {
		return this.getMapper(TransTypeGroupMapper.class);
	}
}
