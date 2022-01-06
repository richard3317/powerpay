package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.MchntTermInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.MchntTermInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntTermInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MchntTermInfoKey;
import com.icpay.payment.db.dao.mybatis.model.MchntTermInfoExample.Criteria;
import com.icpay.payment.db.service.IMchntTermInfoService;

@Service("mchntTermInfoService")
public class MchntTermInfoService extends BaseService implements IMchntTermInfoService {

	@Override
	public void add(MchntTermInfo entity) {
		AssertUtil.objIsNull(entity, "entity is null.");
		AssertUtil.strIsBlank(entity.getMchntCd(), "mchntCd is blank.");
		AssertUtil.strIsBlank(entity.getTermSn(), "mchntCd is termSn.");

		Date now = new Date();
		entity.setRecCrtTs(now);
		entity.setRecUpdTs(now);
		this.getMapper().insert(entity);
	}

	@Override
	public void delete(String mchntCd, String termSn) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		AssertUtil.strIsBlank(termSn, "mchntCd is termSn.");
		
		MchntTermInfoKey k = new MchntTermInfoKey();
		k.setMchntCd(mchntCd);
		k.setTermSn(termSn);
		this.getMapper().deleteByPrimaryKey(k);
	}

	@Override
	public List<MchntTermInfo> select(Map<String, String> qryParamMap) {
		MchntTermInfoExample example = this.getQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}

	@Override
	public Pager<MchntTermInfo> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		MchntTermInfoExample example = this.getQryExample(qryParamMap);
		MchntTermInfoMapper mapper = getMapper();
		Pager<MchntTermInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	@Override
	protected MchntTermInfoExample buildQryExample(Map<String, String> qryParamMap) {
		MchntTermInfoExample example = new MchntTermInfoExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:商户代码
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			// 查询条件: 终端序列号
			String termSn = StringUtil.trim(qryParamMap.get("termSn"));
			if (!StringUtil.isBlank(termSn)) {
				c.andTermSnEqualTo(termSn);
			}
//			// 查询条件: 商户名称(沒在用)
//			String mchntCnNm = StringUtil.trim(qryParamMap.get("mchntCnNm"));
//			if (!StringUtil.isBlank(mchntCnNm)) {
//				c.andMchntCnNmLike("%"+mchntCnNm+"%");
//			}
		}
		// 排序字段
		example.setOrderByClause("mt.rec_upd_ts desc");
		return example;
	}

	private MchntTermInfoMapper getMapper() {
		return this.getMapper(MchntTermInfoMapper.class);
	}

	@Override
	public MchntTermInfo selectByPrimarykey(String mchntCd, String termSn) {
		MchntTermInfoKey k = new MchntTermInfoKey();
		k.setMchntCd(mchntCd);
		k.setTermSn(termSn);
		return this.getMapper().selectByPrimaryKey(k);
	}
}
