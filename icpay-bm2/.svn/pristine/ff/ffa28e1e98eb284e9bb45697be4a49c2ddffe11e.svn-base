package com.icpay.payment.bm.web.controller.business;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.ComplaintManage;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.modelExt.ChnlAccountInfoSummary;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MerAccountInfoSummaryAddRealAavailable;
import com.icpay.payment.db.service.IChnlMchntAccountInfoService;
import com.icpay.payment.db.service.IChnlMchntInfoService;
import com.icpay.payment.db.service.IComplaintManageService;
import com.icpay.payment.db.service.IMchntAccountService;
import com.icpay.payment.db.service.IMchntInfoService;

@Controller
@RequestMapping("/realTimeMonitoring")
public class RealTimeMonitoringController extends BaseController {
	private static final String RESULT_BASE_URI = "realTimeMonitoring";

	private static final Logger logger = Logger.getLogger(RealTimeMonitoringController.class);

	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		System.err.println("进入manage方法*******");
		// ***************************渠道商户***********************
		Map<String, String> map = new HashMap<String, String>();

		IChnlMchntAccountInfoService svc = this.getDBService(IChnlMchntAccountInfoService.class);
		ChnlAccountInfoSummary sum = svc.selectInfoSummaryByChnl(map);
		Map<String, Object> chnlMchntMap = new HashMap<String, Object>();
		if (sum != null) {
			// 实际可代付总额
			chnlMchntMap.put("sumRealAvailableBalance", StringUtil.formateAmt(sum.getSumRealAvailableBalance()));// 实际可代付总额
			// 可代付总额
			chnlMchntMap.put("sumAvailableBalance", StringUtil.formateAmt(sum.getSumAvailableBalance()));// 可代付总额
			// 充值总额
			chnlMchntMap.put("sumDayTxnAmt", StringUtil.formateAmt(sum.getSumDayTxnAmt()));// 当日累计交易总额
			// 代付交易总额
			chnlMchntMap.put("sumDayWithdrawAmt", StringUtil.formateAmt(sum.getSumDayWithdrawAmt()));// 当日累计提现总额
		}

		System.err.println("=======================================");
		System.err.println("=======================================");

		// ***************************商户端***********************

		IMchntAccountService svcMchnt = this.getDBService(IMchntAccountService.class);
		MerAccountInfoSummaryAddRealAavailable sumMchnt = svcMchnt.selectInfoSummary(map);
		Map<String, Object> mchntMap = new HashMap<String, Object>();
		if (sum != null) {
			mchntMap.put("sumAvailableBalance", StringUtil.formateAmt(sumMchnt.getSumAvailableBalance()));// 可代付总额
			mchntMap.put("sumRealAvailableBalance", StringUtil.formateAmt(sumMchnt.getSumRealAvailableBalance()));// 实际可代付总额
			mchntMap.put("sumDayTxnAmt", StringUtil.formateAmt(sumMchnt.getSumDayTxnAmt()));// 当日累计交易总额
			mchntMap.put("sumDayWithdrawAmt", StringUtil.formateAmt(sumMchnt.getSumDayWithdrawAmt()));// 当日累计提现总额
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		model.addAttribute("chnlMchntMap", chnlMchntMap);
		model.addAttribute("mchntMap", mchntMap);
		model.addAttribute("pushTime", simpleDateFormat.format(new Date()));

		System.err.println("渠道端结果为：" + chnlMchntMap + "; 商户端结果为：" + mchntMap);
		return super.toManage(model, false, RESULT_BASE_URI);
	}

	@RequestMapping(value = "/refresh.do", method = RequestMethod.POST)
	public void refresh(HttpServletResponse response) {
		System.err.println("进入ajax方法中");
		Map<String, Object> finalMap = new HashMap<String, Object>();
		// ***************************渠道商户***********************
		Map<String, String> map = new HashMap<String, String>();

		IChnlMchntAccountInfoService svc = this.getDBService(IChnlMchntAccountInfoService.class);
		ChnlAccountInfoSummary sum = svc.selectInfoSummaryByChnl(map);
		if (sum != null) {
			// 实际可代付总额
			finalMap.put("csumRealAvailableBalance", StringUtil.formateAmt(sum.getSumRealAvailableBalance()));// 实际可代付总额
			// 可代付总额
			finalMap.put("csumAvailableBalance", StringUtil.formateAmt(sum.getSumAvailableBalance()));// 可代付总额
			// 充值总额
			finalMap.put("csumDayTxnAmt", StringUtil.formateAmt(sum.getSumDayTxnAmt()));// 当日累计交易总额
			// 代付交易总额
			finalMap.put("csumDayWithdrawAmt", StringUtil.formateAmt(sum.getSumDayWithdrawAmt()));// 当日累计提现总额
		}

		// ***************************商户端***********************
		IMchntAccountService svcMchnt = this.getDBService(IMchntAccountService.class);
		MerAccountInfoSummaryAddRealAavailable sumMchnt = svcMchnt.selectInfoSummary(map);
		if (sum != null) {
			finalMap.put("msumAvailableBalance", StringUtil.formateAmt(sumMchnt.getSumAvailableBalance()));// 可代付总额
			finalMap.put("msumRealAvailableBalance", StringUtil.formateAmt(sumMchnt.getSumRealAvailableBalance()));// 实际可代付总额
			finalMap.put("msumDayTxnAmt", StringUtil.formateAmt(sumMchnt.getSumDayTxnAmt()));// 当日累计交易总额
			finalMap.put("msumDayWithdrawAmt", StringUtil.formateAmt(sumMchnt.getSumDayWithdrawAmt()));// 当日累计提现总额
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		finalMap.put("pushTime", simpleDateFormat.format(new Date()));
		System.err.println("ajax方法结束，最终结果为：" + finalMap);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JSON.toJSONString(finalMap));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
