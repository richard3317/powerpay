package com.icpay.payment.db.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.TermSignInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.TermSignInfo;
import com.icpay.payment.db.dao.mybatis.model.TermSignInfoExample;
import com.icpay.payment.db.dao.mybatis.model.TermSignInfoExample.Criteria;
import com.icpay.payment.db.service.ITermSignInfoService;

@Service("termSignInfoService")
public class TermSignInfoService extends BaseService implements ITermSignInfoService {

	@Override
	public Pager<TermSignInfo> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		TermSignInfoExample example = this.getQryExample(qryParamMap);
		TermSignInfoMapper mapper = this.getMapper(TermSignInfoMapper.class);
		Pager<TermSignInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	@Override
	protected TermSignInfoExample buildQryExample(Map<String, String> qryParamMap) {
		TermSignInfoExample example = new TermSignInfoExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:终端序号
			String termSn = StringUtil.trim(qryParamMap.get("termSn"));
			if (!StringUtil.isBlank(termSn)) {
				c.andTermSnEqualTo(termSn);
			}
			// 查询条件:终端批次号
			String termBn = StringUtil.trim(qryParamMap.get("termBn"));
			if (!StringUtil.isBlank(termBn)) {
				c.andTermBnEqualTo(termBn);
			}
			// 查询条件:终端型号
			String termMn = StringUtil.trim(qryParamMap.get("termMn"));
			if (!StringUtil.isBlank(termMn)) {
				c.andTermMnEqualTo(termMn);
			}
			// 查询条件:签到日期
			String signDt = qryParamMap.get("signDt");
			if (!StringUtil.isBlank(signDt)) {
				Date startDt = DateUtil.parseDate8(signDt);
				Calendar cl = Calendar.getInstance();
				cl.setTime(startDt);
				cl.add(Calendar.DAY_OF_YEAR, 1);
				Date endDt = cl.getTime();
				c.andRecCrtTsGreaterThanOrEqualTo(startDt);
				c.andRecCrtTsLessThan(endDt);
			}
		}
		// 排序字段
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}

}
