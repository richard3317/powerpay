package com.icpay.payment.db.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.ChnlMchntAccountHourMapper;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountHour;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountHourExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountHourExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountHourKey;
import com.icpay.payment.db.service.IChnlMchntAccountHourService;

@Service("chnlMchntAccountHourService")
public class ChnlMchntAccountHourService extends BaseService implements IChnlMchntAccountHourService {
	
	@Override
	protected ChnlMchntAccountHourExample buildQryExample(Map<String, String> qryParamMap) {
		ChnlMchntAccountHourExample example = new ChnlMchntAccountHourExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			
			//查询范围
			String startDate = StringUtil.trim(qryParamMap.get("startDate"));//起始查询日期
			String endDate = StringUtil.trim(qryParamMap.get("endDate"));//终止查询日期
			if (!StringUtil.isBlank(startDate) && !StringUtil.isBlank(endDate)) {
				int differentDays = this.differentDays(startDate, endDate);
				if (differentDays > 31) {
					i18IfTrue(true, this.getClass().getSimpleName(), "最长跨度31天");
				}
				c.andTransDtBetween(startDate, endDate);
			}
		}
		// 排序字段
		example.setOrderByClause("trans_dt, CAST(trans_hour AS UNSIGNED) asc");
		return example;
	}	

	@Override
	public Pager<ChnlMchntAccountHour> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		ChnlMchntAccountHourExample example = this.getQryExample(qryParamMap);
		ChnlMchntAccountHourMapper mapper = getMapper();
		Pager<ChnlMchntAccountHour> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public List<ChnlMchntAccountHour> selectByDate(Map<String, String> qryParamMap) {
		ChnlMchntAccountHourExample example = this.getQryExample(qryParamMap);
		return this.getMapper().selectByExample(example);
	}

	@Override
	public ChnlMchntAccountHour selectByPrimaryKey(ChnlMchntAccountHourKey key) {
		return this.getMapper().selectByPrimaryKey(key);
	}

	@Override
	public long count(Map<String, String> qryParamMap) {
		ChnlMchntAccountHourExample example = this.getQryExample(qryParamMap);
		return this.getMapper().countByExample(example);
	}

	protected ChnlMchntAccountHourMapper getMapper() {
		return super.getMapper(ChnlMchntAccountHourMapper.class);
	}
	
	protected int differentDays(String startDate, String endDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int startDay = 0;
	    int endDay = 0;
		try {
			Date startDateTime = sdf.parse(startDate);
			Date endDateTime = sdf.parse(endDate);
			Calendar startCal = Calendar.getInstance();
			startCal.setTime(startDateTime);
    	    Calendar endCal = Calendar.getInstance();
    	    endCal.setTime(endDateTime);
    	    startDay = startCal.get(Calendar.DAY_OF_YEAR);
    	    endDay = endCal.get(Calendar.DAY_OF_YEAR);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    return endDay-startDay;
	}

}
