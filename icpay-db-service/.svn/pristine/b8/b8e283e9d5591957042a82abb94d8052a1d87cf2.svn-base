package com.icpay.payment.db.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.exception.I18nBizException;
import com.icpay.payment.common.exception.I18nMsgSpec;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.TermInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.TermInfo;
import com.icpay.payment.db.dao.mybatis.model.TermInfoExample;
import com.icpay.payment.db.dao.mybatis.model.TermInfoExample.Criteria;
import com.icpay.payment.db.service.ITermInfoService;

@Service("termInfoService")
public class TermInfoService extends BaseService implements ITermInfoService {

	private static final Logger logger = Logger.getLogger(TermInfoService.class);
	
	@Override
	public void add(TermInfo termInfo) {
		AssertUtil.objIsNull(termInfo, "termInfo is null.");
		AssertUtil.strIsBlank(termInfo.getTermBn(), "termSn is blank.");
		AssertUtil.strIsBlank(termInfo.getTermMn(), "termMn is blank.");
		AssertUtil.strIsBlank(termInfo.getTermBn(), "termBn is blank.");
		AssertUtil.strIsBlank(termInfo.getTmk(), "tmk is blank.");
		AssertUtil.strIsBlank(termInfo.getTak(), "tak is blank.");
		AssertUtil.strIsBlank(termInfo.getOperId(), "operId is blank.");
		
		logger.info("新增终端信息:" + termInfo.getTermSn());
		termInfo.setState(CommonEnums.RecSt.VALID.getCode());
		Date now = new Date();
		termInfo.setRecCrtTs(now);
		termInfo.setRecUpdTs(now);
		
		this.getMapper().insert(termInfo);
	}
	
	@Override
	public List<TermInfo> select(Map<String, String> qryParamMap) {
		TermInfoExample example = this.buildExample(qryParamMap);
		return this.getMapper().selectByExample(example);
	}

	@Override
	public Pager<TermInfo> selectByPage(int pageNum, int pageSize, 
			Map<String, String> qryParamMap) {
		logger.info("分页查询终端信息");

		TermInfoExample example = this.buildExample(qryParamMap);
		TermInfoMapper mapper = getMapper();
		Pager<TermInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public TermInfo selectByPrimaryKey(int termId) {
		return this.getMapper().selectByPrimaryKey(termId);
	}
	
	protected TermInfoExample buildExample(Map<String, String> qryParamMap) {
		TermInfoExample example = new TermInfoExample();
		Criteria c = example.createCriteria();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			// 查询条件 - 批次号(全匹配)
			String batNo = qryParamMap.get("batNo");
			if (!StringUtil.isBlank(batNo)) {
				c.andBatNoEqualTo(batNo);
			}
			// 查询条件 - 终端序号(全匹配)
			String termSn = qryParamMap.get("termSn");
			if (!StringUtil.isBlank(termSn)) {
				c.andTermSnEqualTo(termSn);
			}
			// 查询条件 - 终端型号：模糊匹配
			String termMn = qryParamMap.get("termMn");
			if (!StringUtil.isBlank(termMn)) {
				c.andTermMnLike("%" + termMn + "%");
			}
			// 查询条件 - 批次号：*必须为全匹配
			String termBn = qryParamMap.get("termBn");
			if (!StringUtil.isBlank(termBn)) {
				c.andTermBnEqualTo(termBn);
			}
			// 查询条件 - 终端状态
			String state = qryParamMap.get("state");
			if (!StringUtil.isBlank(state)) {
				c.andStateEqualTo(state);
			}
			// 查询条件 - 导入日期
			String importDt = qryParamMap.get("importDt");
			if (!StringUtil.isBlank(importDt)) {
				Date startDt = DateUtil.parseDate8(importDt);
				Calendar cl = Calendar.getInstance();
				cl.setTime(startDt);
				cl.add(Calendar.DAY_OF_YEAR, 1);
				Date endDt = cl.getTime();
				c.andRecCrtTsGreaterThanOrEqualTo(startDt);
				c.andRecCrtTsLessThan(endDt);
			}
		}
		// 查询结果按照更新时间倒序排列
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}

	private TermInfoMapper getMapper() {
		return this.getMapper(TermInfoMapper.class);
	}

	@Override
	public TermInfo selectByTermSn(String termSn) {
		AssertUtil.strIsBlank(termSn, "termSn is blank.");
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("termSn", termSn);
		List<TermInfo> lst = this.select(mp);
		if (lst.size() > 1) {
			throw new I18nBizException(new I18nMsgSpec("zh_CN", this.getClass().getSimpleName(),null, "找到多条终端信息"));
		}
		if (lst.size() == 0) {
			return null;
		}
		return lst.get(0);
	}
}
