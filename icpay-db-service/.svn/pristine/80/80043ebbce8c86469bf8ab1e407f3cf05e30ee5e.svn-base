package com.icpay.payment.db.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.CommonEnums.PrimaryKeyTp;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.AgentInfoMapper;
import com.icpay.payment.db.dao.mybatis.mapper.AgentProfitPolicyMapper;
import com.icpay.payment.db.dao.mybatis.mapper.AgentProfitQueryMapper;
import com.icpay.payment.db.dao.mybatis.model.AgentInfo;
import com.icpay.payment.db.dao.mybatis.model.AgentInfoExample;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicyExample;
import com.icpay.payment.db.dao.mybatis.model.TxnLogViewExample;
import com.icpay.payment.db.dao.mybatis.model.AgentInfoExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentProfitQueryBean;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentProfitQuerySummary;
import com.icpay.payment.db.dao.mybatis.model.modelExt.DailyProfitResultEmployee;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TxnLogSummary;
import com.icpay.payment.db.service.IAgentInfoService;
import com.icpay.payment.db.service.IAgentProfitQueryService;

@Service("agentProfitQueryService")
public class AgentProfitQueryService extends BaseService implements IAgentProfitQueryService {
	
	private static final Logger logger = Logger.getLogger(AgentProfitQueryService.class);
	
	//代理商分润查询页面
	@Override
	public Pager<AgentProfitQueryBean> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		AgentProfitQueryBean example = this.buildQryExample(qryParamMap);
		AgentProfitQueryMapper mapper = getMapper();
		Pager<AgentProfitQueryBean> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countAgentProfitQueryByExample(example));
		pager.setResultList(mapper.selectAgentProfitQueryByPage(example));
		return pager;
	}
	
	//代理商分润数据查询报表
	@Override
	public List<AgentProfitQueryBean> selectByRpt(Map<String, String> qryParamMap) {
		AgentProfitQueryBean example =  new AgentProfitQueryBean();
		example = this.buildQryExample(qryParamMap);
		AgentProfitQueryMapper mapper = getMapper();
		List<AgentProfitQueryBean> list = mapper.selectAgentProfitQueryByPage(example);  //数据库读取数据
		return list;
	}
	
	protected AgentProfitQueryBean buildQryExample(Map<String, String> qryParamMap) {
		AgentProfitQueryBean example = new AgentProfitQueryBean();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			//月份
//			mon = getMon(mon,qryParamMap);
//			example.setMon(mon);
			//代理人/中人编号
			String agentCd = StringUtil.trim(qryParamMap.get("agentCd"));
			if (!StringUtil.isBlank(agentCd)) {
				example.setAgentCd(agentCd);
			}
			//代理人(中人)类别: 1=自营，2=商户代理人，3=渠道代理人，0=未定义
			/*
			AgentInfo agentInfo = selectByPrimaryKey(agentCd);
			String agentType = agentInfo.getAgentType();
			if (!StringUtil.isBlank(agentType)) {
				example.setAgentType(agentType);
				if ("2".equals(agentType)) {
					example.setMchntFlag(agentType);
				} else {
					example.setChnlFlag(agentType);
				}
			}
			*/
			//查询范围
			String startDate = StringUtil.trim(qryParamMap.get("startDate"));
			String endDate = StringUtil.trim(qryParamMap.get("endDate"));
			if (!StringUtil.isBlank(startDate) && !StringUtil.isBlank(endDate)) {
				int differentDays = this.differentDays(startDate, endDate);
				if (differentDays > 31) {
					i18IfTrue(true, this.getClass().getSimpleName(), "最长跨度31天");
				}
				example.setStartDate(startDate);//起始查询日期
				example.setEndDate(endDate);//终止查询日期
			}
		}
		return example;
	}
	
	//代理商分润数据加总
	@Override
	public AgentProfitQuerySummary selectSummary(Map<String, String> qryParamMap) {
		AgentProfitQueryBean example = this.buildQryExample(qryParamMap);
		AgentProfitQueryMapper mapper = getMapper();
		AgentProfitQuerySummary AgentProfitQuerySummary = mapper.selectSummary(example);
		return AgentProfitQuerySummary;
	}
	
	//导出代理分润总表
	@Override
	public List<AgentProfitQuerySummary> selectTotalSummary(Map<String, String> qryParamMap) {
		AgentProfitQueryBean example =  new AgentProfitQueryBean();
		example = this.buildQryExample(qryParamMap);
		AgentProfitQueryMapper mapper = getMapper();
		List<AgentProfitQuerySummary> list = mapper.selectTotalSummary(example);  //数据库读取数据
		return list;
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

	private AgentProfitQueryMapper getMapper() {
		return this.getMapper(AgentProfitQueryMapper.class);
	}
}
