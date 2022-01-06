package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.RptInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.RptInfo;
import com.icpay.payment.db.dao.mybatis.model.RptInfoExample;
import com.icpay.payment.db.dao.mybatis.model.RptInfoExample.Criteria;
import com.icpay.payment.db.service.IRptInfoService;

@Service("rptInfoService")
public class RptInfoService extends BaseService implements IRptInfoService {

	@Override
	public void add(RptInfo rptInfo) {
		AssertUtil.objIsNull(rptInfo, "entity is null");
		Date now = new Date();
		rptInfo.setRecCrtTs(now);
		this.getMapper().insert(rptInfo);
	}

	@Override
	public Pager<RptInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		RptInfoExample example = this.getQryExample(qryParamMap);
		RptInfoMapper mapper = getMapper();
		Pager<RptInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	@Override
	public List<RptInfo> select(Map<String, String> qryParamMap) {
		RptInfoExample example = this.getQryExample(qryParamMap);
		RptInfoMapper mapper = getMapper();
		return mapper.selectByExample(example);
	}

	@Override
	public RptInfo selectByPrimaryKey(int seqId) {
		return this.getMapper().selectByPrimaryKey(seqId);
	}

	@Override
	protected RptInfoExample buildQryExample(Map<String, String> qryParamMap) {
		RptInfoExample example = new RptInfoExample();
		Criteria c = example.createCriteria();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			String rptDt = StringUtil.trimStr(qryParamMap.get("rptDt"));
			if (!StringUtil.isBlank(rptDt)) {
				c.andRptDtEqualTo(rptDt);
			}
			String rptTp = StringUtil.trimStr(qryParamMap.get("rptTp"));
			if (!StringUtil.isBlank(rptTp)) {
				c.andRptTpEqualTo(rptTp);
			}
			String rptNm = StringUtil.trimStr(qryParamMap.get("rptNm"));
			if (!StringUtil.isBlank(rptNm)) {
				c.andRptNmEqualTo(rptNm);
			}
		}
		example.setOrderByClause("rec_crt_ts desc");
		return example;
	}
	
	private RptInfoMapper getMapper() {
		return super.getMapper(RptInfoMapper.class);
	}
}
