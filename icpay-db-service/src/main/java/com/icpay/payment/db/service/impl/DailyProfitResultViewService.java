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
import com.icpay.payment.db.dao.mybatis.mapper.DailyProfitResultViewMapper;
import com.icpay.payment.db.dao.mybatis.model.DailyProfitResultView;
import com.icpay.payment.db.dao.mybatis.model.DailyProfitResultViewExample;
import com.icpay.payment.db.dao.mybatis.model.DailyProfitResultViewExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.modelExt.DailyProfitResultChnlTrans;
import com.icpay.payment.db.dao.mybatis.model.modelExt.DailyProfitResultEmployee;
import com.icpay.payment.db.dao.mybatis.model.modelExt.DailyProfitResultHuanbi;
import com.icpay.payment.db.dao.mybatis.model.modelExt.DailyProfitResultManager;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MchntProfitToMerBean;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MchntProfitToWeekBean;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MchntTransHuanbiBean;
import com.icpay.payment.db.service.IDailyProfitResultViewService;

@Service("dailyProfitResultViewService")
public class DailyProfitResultViewService extends BaseService implements IDailyProfitResultViewService {
	
	private DailyProfitResultViewMapper getViewMapper() {
		return this.getMapper(DailyProfitResultViewMapper.class);
	}

	@Override
	public Pager<DailyProfitResultView> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		DailyProfitResultViewExample example = new DailyProfitResultViewExample();
		example = this.buildQryExample(qryParamMap);
		
		Pager<DailyProfitResultView> pager = new Pager<DailyProfitResultView>();
		pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(this.getViewMapper().countByExample(example));
		
		pager.setResultList(this.getViewMapper().selectByPage(example));
		return pager;
	}
	
	@Override
	public List<DailyProfitResultView> select(Map<String, String> qryParamMap) {
		DailyProfitResultViewExample example = new DailyProfitResultViewExample();
		example = this.buildQryExample(qryParamMap);
		return this.getViewMapper().selectByPage(example);
	}
	
	
	@Override
	protected DailyProfitResultViewExample buildQryExample(Map<String, String> qryParamMap) {
		DailyProfitResultViewExample example = new DailyProfitResultViewExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			//????????????
			String settleDate = StringUtil.trim(qryParamMap.get("settleDate"));
			if (!StringUtil.isBlank(settleDate)) {
				c.andSettleDateEqualTo(settleDate);
			}
			//????????????
			String chnlId = StringUtil.trim(qryParamMap.get("chnlId"));
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}
			
			//mchntCnNm
			String mchntCnNm = StringUtil.trim(qryParamMap.get("mchntCnNm"));
			if (!StringUtil.isBlank(mchntCnNm)) {
				c.andMchntCnNmLike("%" + mchntCnNm + "%" );
			}
			
		}
		// ????????????
		example.setOrderByClause("settle_date desc");
		return example;
	}

	@Override
	public List<MchntTransHuanbiBean> selectHuanbi(Map<String, String> qryParamMap) {
		String settleDate = qryParamMap.get("settleDate");
		MchntTransHuanbiBean bean =  new MchntTransHuanbiBean();
		bean.setSettleDate(settleDate);
		bean.setMon(settleDate.substring(4,settleDate.length()-2));
		List<MchntTransHuanbiBean> list = this.getViewMapper().selectHuanbi(bean);  //?????????????????????
		return list;
	}

	@Override
	public List<MchntProfitToWeekBean> selectWeek() {
		 MchntProfitToWeekBean bean =  new MchntProfitToWeekBean();
//		bean.setSettleDate(settleDate);
		List<MchntProfitToWeekBean> list = this.getViewMapper().selectWeek(bean);  //?????????????????????
		return list;
	}
	@Override
	public List<MchntProfitToMerBean> selectMchntProfit(Map<String, String> qryParamMap) {
		 MchntProfitToMerBean bean =  new MchntProfitToMerBean();
		bean.setSettleDate(qryParamMap.get("settleDate"));
		List<MchntProfitToMerBean> list = this.getViewMapper().selectMchntProfit(bean);  //?????????????????????
		return list;
	}
	
	//??????????????????-????????????
	@Override
	public Pager<DailyProfitResultHuanbi> selectHuanbiPpayFpayByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		DailyProfitResultHuanbi example = new DailyProfitResultHuanbi();
		example = this.buildQryExampleHuanbi(qryParamMap);
		Pager<DailyProfitResultHuanbi> pager = new Pager<DailyProfitResultHuanbi>();
		pager = buildPager(pageNum, pageSize, qryParamMap);
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		pager.setTotal(this.getViewMapper().countByHuanbiPpayFpay(example));
		pager.setResultList(this.getViewMapper().selectHuanbiPpayFpay(example));
		return pager;
	}
	
	//????????????????????????-????????????
	public List<DailyProfitResultHuanbi> selectHuanbiPpayFpayRpt(Map<String, String> qryParamMap) {
		DailyProfitResultHuanbi example =  new DailyProfitResultHuanbi();
		example = this.buildQryExampleHuanbi(qryParamMap);
		List<DailyProfitResultHuanbi> list = this.getViewMapper().selectHuanbiPpayFpay(example);  //?????????????????????
		return list;
	}
	
	//??????????????????-????????????
	@Override
	public Pager<DailyProfitResultHuanbi> selectHuanbiLpayVpayByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		DailyProfitResultHuanbi example = new DailyProfitResultHuanbi();
		example = this.buildQryExampleHuanbi(qryParamMap);
		Pager<DailyProfitResultHuanbi> pager = new Pager<DailyProfitResultHuanbi>();
		pager = buildPager(pageNum, pageSize, qryParamMap);
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		pager.setTotal(this.getViewMapper().countByHuanbiLpayVpay(example));
		pager.setResultList(this.getViewMapper().selectHuanbiLpayVpay(example));
		return pager;
	}
	
	//????????????????????????-????????????
	public List<DailyProfitResultHuanbi> selectHuanbiLpayVpayRpt(Map<String, String> qryParamMap) {
		DailyProfitResultHuanbi example =  new DailyProfitResultHuanbi();
		example = this.buildQryExampleHuanbi(qryParamMap);
		List<DailyProfitResultHuanbi> list = this.getViewMapper().selectHuanbiLpayVpay(example);  //?????????????????????
		return list;
	}
	
	protected DailyProfitResultHuanbi buildQryExampleHuanbi(Map<String, String> qryParamMap) {
		DailyProfitResultHuanbi example = new DailyProfitResultHuanbi();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			//?????????/????????????
			String agentCd = StringUtil.trim(qryParamMap.get("agentCd"));
			if (!StringUtil.isBlank(agentCd)) {
				example.setAgentCd(agentCd);
			}
			//????????????
			String mchntCnNm = StringUtil.trim(qryParamMap.get("mchntCnNm"));
			if (!StringUtil.isBlank(mchntCnNm)) {
				example.setMchntCnNm("%" + mchntCnNm + "%");
			}
			//????????????
			String qryStartDate = StringUtil.trim(qryParamMap.get("qryStartDate"));
			String qryEndDate = StringUtil.trim(qryParamMap.get("qryEndDate"));
			if (!StringUtil.isBlank(qryStartDate) && !StringUtil.isBlank(qryEndDate)) {
				example.setQryStartDate(qryStartDate);//??????????????????
				example.setQryEndDate(qryEndDate);//??????????????????
				int differentDays = this.differentDays(qryStartDate, qryEndDate);
				if (differentDays > 31) {
					i18IfTrue(true, this.getClass().getSimpleName(), "????????????31???");
				}
				String ctStartDate = this.subtractDaysByCalendar(qryStartDate, differentDays);
				example.setCtStartDate(ctStartDate);//??????????????????
				String ctEndDate = this.subtractDaysByCalendar(qryEndDate, differentDays);
				example.setCtEndDate(ctEndDate);//??????????????????
			}
		}
		return example;
	}
	
	//?????????????????????-??????-????????????
	@Override
	public Pager<DailyProfitResultManager> selectManagerPpayFpayByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		DailyProfitResultManager example = new DailyProfitResultManager();
		example = this.buildQryExampleManager(qryParamMap);
		Pager<DailyProfitResultManager> pager = new Pager<DailyProfitResultManager>();
		pager = buildPager(pageNum, pageSize, qryParamMap);
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		pager.setTotal(this.getViewMapper().countByManagerPpayFpay(example));
		pager.setResultList(this.getViewMapper().selectManagerPpayFpay(example));
		return pager;
	}
	
	//???????????????????????????-??????-????????????
	public List<DailyProfitResultManager> selectManagerPpayFpayRpt(Map<String, String> qryParamMap) {
		DailyProfitResultManager example =  new DailyProfitResultManager();
		example = this.buildQryExampleManager(qryParamMap);
		List<DailyProfitResultManager> list = this.getViewMapper().selectManagerPpayFpay(example);  //?????????????????????
		return list;
	}
	
	//?????????????????????-??????-????????????
	@Override
	public Pager<DailyProfitResultManager> selectManagerLpayVpayByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		DailyProfitResultManager example = new DailyProfitResultManager();
		example = this.buildQryExampleManager(qryParamMap);
		Pager<DailyProfitResultManager> pager = new Pager<DailyProfitResultManager>();
		pager = buildPager(pageNum, pageSize, qryParamMap);
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		pager.setTotal(this.getViewMapper().countByManagerLpayVpay(example));
		pager.setResultList(this.getViewMapper().selectManagerLpayVpay(example));
		return pager;
	}
	
	//???????????????????????????-??????-????????????
	public List<DailyProfitResultManager> selectManagerLpayVpayRpt(Map<String, String> qryParamMap) {
		DailyProfitResultManager example =  new DailyProfitResultManager();
		example = this.buildQryExampleManager(qryParamMap);
		List<DailyProfitResultManager> list = this.getViewMapper().selectManagerLpayVpay(example);  //?????????????????????
		return list;
	}
	
	protected DailyProfitResultManager buildQryExampleManager(Map<String, String> qryParamMap) {
		DailyProfitResultManager example = new DailyProfitResultManager();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			//?????????/????????????
			String agentCd = StringUtil.trim(qryParamMap.get("agentCd"));
			if (!StringUtil.isBlank(agentCd)) {
				example.setAgentCd(agentCd);
			}
			//????????????
			String intTransCd = StringUtil.trim(qryParamMap.get("intTransCd"));
			if (!StringUtil.isBlank(intTransCd)) {
				example.setIntTransCd(qryParamMap.get("intTransCd"));
			}
			//????????????
			String qryStartDate = StringUtil.trim(qryParamMap.get("qryStartDate"));
			String qryEndDate = StringUtil.trim(qryParamMap.get("qryEndDate"));
			if (!StringUtil.isBlank(qryStartDate) && !StringUtil.isBlank(qryEndDate)) {
				example.setQryStartDate(qryStartDate);//??????????????????
				example.setQryEndDate(qryEndDate);//??????????????????
				int differentDays = this.differentDays(qryStartDate, qryEndDate);
				if (differentDays > 31) {
					i18IfTrue(true, this.getClass().getSimpleName(), "????????????31???");
				}
				String ctStartDate = this.subtractDaysByCalendar(qryStartDate, differentDays);
				example.setCtStartDate(ctStartDate);//??????????????????
				String ctEndDate = this.subtractDaysByCalendar(qryEndDate, differentDays);
				example.setCtEndDate(ctEndDate);//??????????????????
			}
		}
		return example;
	}
	
	//?????????????????????-??????-????????????
	@Override
	public Pager<DailyProfitResultEmployee> selectEmployeePpayFpayByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		DailyProfitResultEmployee example = new DailyProfitResultEmployee();
		example = this.buildQryExampleEmployee(qryParamMap);
		Pager<DailyProfitResultEmployee> pager = new Pager<DailyProfitResultEmployee>();
		pager = buildPager(pageNum, pageSize, qryParamMap);
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		pager.setTotal(this.getViewMapper().countByEmployeePpayFpay(example));
		pager.setResultList(this.getViewMapper().selectEmployeePpayFpay(example));
		return pager;
	}
	
	//???????????????????????????-??????-????????????
	public List<DailyProfitResultEmployee> selectEmployeePpayFpayRpt(Map<String, String> qryParamMap) {
		DailyProfitResultEmployee example =  new DailyProfitResultEmployee();
		example = this.buildQryExampleEmployee(qryParamMap);
		List<DailyProfitResultEmployee> list = this.getViewMapper().selectEmployeePpayFpay(example);  //?????????????????????
		return list;
	}
	
	//?????????????????????-??????-????????????
	@Override
	public Pager<DailyProfitResultEmployee> selectEmployeeLpayVpayByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		DailyProfitResultEmployee example = new DailyProfitResultEmployee();
		example = this.buildQryExampleEmployee(qryParamMap);
		Pager<DailyProfitResultEmployee> pager = new Pager<DailyProfitResultEmployee>();
		pager = buildPager(pageNum, pageSize, qryParamMap);
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		pager.setTotal(this.getViewMapper().countByEmployeeLpayVpay(example));
		pager.setResultList(this.getViewMapper().selectEmployeeLpayVpay(example));
		return pager;
	}
	
	//???????????????????????????-??????-????????????
	public List<DailyProfitResultEmployee> selectEmployeeLpayVpayRpt(Map<String, String> qryParamMap) {
		DailyProfitResultEmployee example =  new DailyProfitResultEmployee();
		example = this.buildQryExampleEmployee(qryParamMap);
		List<DailyProfitResultEmployee> list = this.getViewMapper().selectEmployeeLpayVpay(example);  //?????????????????????
		return list;
	}
	
	protected DailyProfitResultEmployee buildQryExampleEmployee(Map<String, String> qryParamMap) {
		DailyProfitResultEmployee example = new DailyProfitResultEmployee();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			//?????????/????????????
			String agentCd = StringUtil.trim(qryParamMap.get("agentCd"));
			if (!StringUtil.isBlank(agentCd)) {
				example.setAgentCd(agentCd);
			}
			//????????????
			String intTransCd = StringUtil.trim(qryParamMap.get("intTransCd"));
			if (!StringUtil.isBlank(intTransCd)) {
				example.setIntTransCd(qryParamMap.get("intTransCd"));
			}
			//????????????
			String qryStartDate = StringUtil.trim(qryParamMap.get("qryStartDate"));
			String qryEndDate = StringUtil.trim(qryParamMap.get("qryEndDate"));
			if (!StringUtil.isBlank(qryStartDate) && !StringUtil.isBlank(qryEndDate)) {
				example.setQryStartDate(qryStartDate);//??????????????????
				example.setQryEndDate(qryEndDate);//??????????????????
				int differentDays = this.differentDays(qryStartDate, qryEndDate);
				if (differentDays > 31) {
					i18IfTrue(true, this.getClass().getSimpleName(), "????????????31???");
				}
				String ctStartDate = this.subtractDaysByCalendar(qryStartDate, differentDays);
				example.setCtStartDate(ctStartDate);//??????????????????
				String ctEndDate = this.subtractDaysByCalendar(qryEndDate, differentDays);
				example.setCtEndDate(ctEndDate);//??????????????????
			}
		}
		return example;
	}
	
	//??????????????????
	@Override
	public Pager<DailyProfitResultChnlTrans> selectChnlTransByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		DailyProfitResultChnlTrans example = new DailyProfitResultChnlTrans();
		example = this.buildQryExampleChnlTrans(qryParamMap);
		Pager<DailyProfitResultChnlTrans> pager = new Pager<DailyProfitResultChnlTrans>();
		pager = buildPager(pageNum, pageSize, qryParamMap);
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		pager.setTotal(this.getViewMapper().countByChnlTrans(example));
		pager.setResultList(this.getViewMapper().selectChnlTrans(example));
		return pager;
	}
	
	//????????????????????????
	public List<DailyProfitResultChnlTrans> selectChnlTransRpt(Map<String, String> qryParamMap) {
		DailyProfitResultChnlTrans example =  new DailyProfitResultChnlTrans();
		example = this.buildQryExampleChnlTrans(qryParamMap);
		List<DailyProfitResultChnlTrans> list = this.getViewMapper().selectChnlTrans(example);  //?????????????????????
		return list;
	}
	
	protected DailyProfitResultChnlTrans buildQryExampleChnlTrans(Map<String, String> qryParamMap) {
		DailyProfitResultChnlTrans example = new DailyProfitResultChnlTrans();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			//?????????/????????????
			String agentCd = StringUtil.trim(qryParamMap.get("agentCd"));
			if (!StringUtil.isBlank(agentCd)) {
				example.setAgentCd(agentCd);
			}
			//????????????
			String chnlId = StringUtil.trim(qryParamMap.get("transChnl"));
			if (!StringUtil.isBlank(chnlId)) {
				example.setChnlId(qryParamMap.get("transChnl"));
			}
			//????????????
			String qryStartDate = StringUtil.trim(qryParamMap.get("qryStartDate"));
			String qryEndDate = StringUtil.trim(qryParamMap.get("qryEndDate"));
			if (!StringUtil.isBlank(qryStartDate) && !StringUtil.isBlank(qryEndDate)) {
				example.setQryStartDate(qryStartDate);//??????????????????
				example.setQryEndDate(qryEndDate);//??????????????????
				int differentDays = this.differentDays(qryStartDate, qryEndDate);
				if (differentDays > 31) {
					i18IfTrue(true, this.getClass().getSimpleName(), "????????????31???");
				}
				String ctStartDate = this.subtractDaysByCalendar(qryStartDate, differentDays);
				example.setCtStartDate(ctStartDate);//??????????????????
				String ctEndDate = this.subtractDaysByCalendar(qryEndDate, differentDays);
				example.setCtEndDate(ctEndDate);//??????????????????
			}
		}
		return example;
	}
	
	protected int differentDays(String qryStartDate, String qryEndDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int startDay = 0;
	    int endDay = 0;
		try {
			Date startDateTime = sdf.parse(qryStartDate);
			Date endDateTime = sdf.parse(qryEndDate);
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
	
	protected String subtractDaysByCalendar(String date,int n){ 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String newDate = "";
		try {
			Date dateTime = sdf.parse(date);
			Calendar calstart = Calendar.getInstance();
			calstart.setTime(dateTime); 
			calstart.add(Calendar.DAY_OF_YEAR, -(n+1));
			newDate = sdf.format(calstart.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	} 
	
}
