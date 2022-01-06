package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.mapper.ExtMapper.EventLogExtMapper;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountFlow;
import com.icpay.payment.db.dao.mybatis.model.EventLog;
import com.icpay.payment.db.dao.mybatis.model.EventLogExample;
import com.icpay.payment.db.dao.mybatis.model.EventLogExample.Criteria;
import com.icpay.payment.db.service.IEventLogService;

@Service("eventLogService")
public class EventLogService extends BaseService implements IEventLogService {

	private static final Logger logger = Logger.getLogger(EventLogService.class);

	@Override
	public Pager<EventLog> selectByPage(int pageNum, int pageSize, String mon, Map<String, String> qryParamMap) {
		// 为了防止SQL注入，对MON进行格式校验
		AssertUtil.notMonStr(mon);
		EventLogExample example = this.getQryExample(qryParamMap);
		EventLogExtMapper mapper = getMapper();
		Pager<EventLog> pager = buildPager(pageNum, pageSize, qryParamMap);
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		long total = mapper.countByExample(example, mon);
		pager.setTotal(new Long(total).intValue());
		pager.setResultList(mapper.selectByPage(example, mon));
		return pager;
	}
	
	@Override
	public EventLog selectByPrimaryKey(String seqId, String mon) {
		// 为了防止SQL注入，对MON进行格式校验
		AssertUtil.notMonStr(mon);
		return this.getMapper().selectByPrimaryKey(Long.valueOf(seqId), mon);
	}

	@Override
	protected EventLogExample buildQryExample(Map<String, String> qryParamMap) {
		EventLogExample example = new EventLogExample();
		String orderBy = "log_ts desc";
		
		Criteria c = example.createCriteria();
		
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			
			// 查询条件:商户号
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			
			// 查询条件:使用者ID
			String userId = StringUtil.trim(qryParamMap.get("userId"));
			if (!StringUtil.isBlank(userId)) {
				c.andUserIdEqualTo(userId);
			}
			/*
			if (!StringUtils.equals("admin", userId)) {//admin可以看到全部
				c.andUserIdEqualTo(userId);
			}
			*/
			
			// 查询条件:操作时间
			String startDate = StringUtil.trim(qryParamMap.get("startDate"));
			String startTime = StringUtil.trim(qryParamMap.get("startTime"));
			String endDate = StringUtil.trim(qryParamMap.get("endDate"));
			String endTime = StringUtil.trim(qryParamMap.get("endTime"));
			if ((!StringUtil.isBlank(startDate)) || (!StringUtil.isBlank(endDate))) {
				if (StringUtil.isBlank(startDate)) startDate = endDate;
				if (StringUtil.isBlank(endDate)) endDate = startDate;
				Date date1 = this.convertStartDateTime(startDate, startTime);
				Date date2 = this.convertEndDateTime(endDate, endTime);
				c.andLogTsGreaterThanOrEqualTo(date1);
				c.andLogTsLessThanOrEqualTo(date2);
			}
			
			// 查询条件:事件来源
			String logSrc = StringUtil.trim(qryParamMap.get("logSrc"));
			if (!StringUtil.isBlank(logSrc)) {
				c.andLogSrcEqualTo(logSrc);
			}

		}
		// 排序字段
		example.setOrderByClause(orderBy);
		return example;
	}
	
	private EventLogExtMapper getMapper() {
		return this.getMapper(EventLogExtMapper.class);
	}

}
