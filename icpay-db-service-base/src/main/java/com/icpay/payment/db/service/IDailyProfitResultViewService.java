package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.DailyProfitResultView;
import com.icpay.payment.db.dao.mybatis.model.modelExt.DailyProfitResultChnlTrans;
import com.icpay.payment.db.dao.mybatis.model.modelExt.DailyProfitResultEmployee;
import com.icpay.payment.db.dao.mybatis.model.modelExt.DailyProfitResultHuanbi;
import com.icpay.payment.db.dao.mybatis.model.modelExt.DailyProfitResultManager;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MchntProfitToMerBean;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MchntProfitToWeekBean;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MchntTransHuanbiBean;

public interface IDailyProfitResultViewService {

	public Pager<DailyProfitResultView> selectByPage(int pageNum, int pageSize,Map<String, String> qryParamMap);
	
	public List<DailyProfitResultView> select(Map<String, String> qryParamMap);
	
	/*商户环比*/
	public List<MchntTransHuanbiBean> selectHuanbi(Map<String, String> qryParamMap);
	
	/*周报表*/
	public List<MchntProfitToWeekBean> selectWeek();
	
	/*商户日报*/
	public List<MchntProfitToMerBean> selectMchntProfit(Map<String, String> qryParamMap);

	//商户交易环比报表-威力麻吉
	public Pager<DailyProfitResultHuanbi> selectHuanbiPpayFpayByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	public List<DailyProfitResultHuanbi> selectHuanbiPpayFpayRpt(Map<String, String> qryParamMap);
	
	//商户交易环比报表-乐力太极
	public Pager<DailyProfitResultHuanbi> selectHuanbiLpayVpayByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	public List<DailyProfitResultHuanbi> selectHuanbiLpayVpayRpt(Map<String, String> qryParamMap);
	
	//各支付类型环比报表-主管-威力麻吉
	public Pager<DailyProfitResultManager> selectManagerPpayFpayByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	public List<DailyProfitResultManager> selectManagerPpayFpayRpt(Map<String, String> qryParamMap);
	
	//各支付类型环比报表-主管-乐力太极
	public Pager<DailyProfitResultManager> selectManagerLpayVpayByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	public List<DailyProfitResultManager> selectManagerLpayVpayRpt(Map<String, String> qryParamMap);
	
	//各支付类型环比报表-员工-威力麻吉
	public Pager<DailyProfitResultEmployee> selectEmployeePpayFpayByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	public List<DailyProfitResultEmployee> selectEmployeePpayFpayRpt(Map<String, String> qryParamMap);
	
	//各支付类型环比报表-员工-乐力太极
	public Pager<DailyProfitResultEmployee> selectEmployeeLpayVpayByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	public List<DailyProfitResultEmployee> selectEmployeeLpayVpayRpt(Map<String, String> qryParamMap);
	
	//渠道交易环比报表
	public Pager<DailyProfitResultChnlTrans> selectChnlTransByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	public List<DailyProfitResultChnlTrans> selectChnlTransRpt(Map<String, String> qryParamMap);
	
}
