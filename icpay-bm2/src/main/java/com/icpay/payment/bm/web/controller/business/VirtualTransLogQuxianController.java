package com.icpay.payment.bm.web.controller.business;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.cache.BankNumCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.TxnLogView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/virtualTransLogQuxian")
public class VirtualTransLogQuxianController extends TransLogBaseController {

	private static final String RESULT_BASE_URI = "virtualTransLogQuxian";

	// ------------取现查询
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
		model.addAttribute("today_start", simpleDateFormat.format(new Date())+" 000000");
		model.addAttribute("today_end", simpleDateFormat.format(new Date())+" 235959");
		model.addAttribute("bankCodeList", BankNumCache.getBankNumsList());
		return super.manage(model, RESULT_BASE_URI);
	}

	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(Model model, int pageNum, int pageSize, String mon) {
		Map<String, String> qryParamMap = this.getQryParamMap();
		String startDate=qryParamMap.get("startDate");
		String endDate=qryParamMap.get("endDate");
		qryParamMap.put("startDate",StringUtil.left(startDate,8));
		qryParamMap.put("endDate",StringUtil.left(endDate,8));
		qryParamMap.put("startTime",StringUtil.right(startDate,6));
		qryParamMap.put("endTime",StringUtil.right(endDate,6));
		
		qryParamMap.put("today_start", startDate);
		qryParamMap.put("today_end", endDate);
		return this.query(model, BMConstants.PAGE_VIRTUAL_TBLWITH_DRAW_LOG, qryParamMap, pageNum, pageSize, mon);
	}

	@RequestMapping(value = "/amtSum.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody JSONObject amtSum(Model model, String mon) {
		Map<String, String> qryParamMap = this.getQryParamMap();
		String startDate=qryParamMap.get("startDate");
		String endDate=qryParamMap.get("endDate");
		qryParamMap.put("startDate",StringUtil.left(startDate,8));
		qryParamMap.put("endDate",StringUtil.left(endDate,8));
		qryParamMap.put("startTime",StringUtil.right(startDate,6));
		qryParamMap.put("endTime",StringUtil.right(endDate,6));
		
		qryParamMap.put("today_start", startDate);
		qryParamMap.put("today_end", endDate);
		return this.summary(qryParamMap, mon);
	}

	@RequestMapping(value = "/detail.do")
	public String detail(Model model, String transSeqId, String mon) {
		return super.detail(model, RESULT_BASE_URI, BMConstants.PAGE_TBLWITH_DRAW_LOG, transSeqId, mon);
	}
	
	@RequestMapping(value = "/queryChnlResult.do")
	public String queryChnlResult(Model model, String transSeqId, String orderDate) {
		return super.queryChnlResult(model, RESULT_BASE_URI, BMConstants.PAGE_CHNL_QRY_RESP, transSeqId, orderDate);
	}

	@RequestMapping(value = "/transSateModify.do", method = RequestMethod.GET)
	public String adjustTxnState(Model model, String transSeqId, String mon, String txnState) {
		return super.adjustTxnState(model, RESULT_BASE_URI, BMConstants.PAGE_TBLWITH_DRAW_LOG, transSeqId, mon,
				txnState);
	}
	
	@RequestMapping(value = "/mandatoryModify.do", method = RequestMethod.GET)
	public String adjustMandatoryTxnState(Model model, String transSeqId, String mon, String txnState) {
		return super.adjustMandatoryTxnState(model, RESULT_BASE_URI, BMConstants.PAGE_TBLWITH_DRAW_LOG, transSeqId, mon,
				txnState);
	}

	@RequestMapping(value = "/transSateModify/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult adjustTxnStateSubmit(Model model, TxnLogView tr, String mon, String opType,
			String comment) {
		return super.adjustTxnStateSubmit(model, tr, mon, opType, comment);
	}
	
//	@RequestMapping(value = "/differentModify.do", method = RequestMethod.POST)
//	public @ResponseBody AjaxResult differentModify(Model model, TxnLogView tr, String mon, String opType,
//			String comment) {
//		return super.differentModify(model, tr, mon, opType, comment);
//	}

	@RequestMapping(value = "/backToManage.do", method = RequestMethod.GET)
	public String backToManage(Model model) {
		model.addAttribute("bankCodeList", BankNumCache.getBankNumsList());
		return super.backToManage(model, RESULT_BASE_URI);
	}

	// 根据条件查询记录（导出）
	@RequestMapping(value = "/exportWithdrawLog.do", method = {RequestMethod.POST, RequestMethod.GET})
	@QryMethod
	public @ResponseBody void exportWithdrawLog(Model model, String mon, HttpServletResponse response) {
		Map<String, String> qryParamMap = this.getQryParamMap();
		String startDate=qryParamMap.get("startDate");
		String endDate=qryParamMap.get("endDate");
		qryParamMap.put("startDate",StringUtil.left(startDate,8));
		qryParamMap.put("endDate",StringUtil.left(endDate,8));
		qryParamMap.put("startTime",StringUtil.right(startDate,6));
		qryParamMap.put("endTime",StringUtil.right(endDate,6));

		qryParamMap.put("today_start", startDate);
		qryParamMap.put("today_end", endDate);
		String filename = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_WITHDRAW_EXPORT_FILENM);
		super.export(model, BMConstants.PAGE_TBLWITH_DRAW_LOG, qryParamMap, mon, filename, response);
	}
	
	/**
	 * 重发通知
	 * @param transSeqId
	 * @param mon
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/renotify.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult resendNotify(String transSeqId, String orderDate, HttpServletResponse response) {
		return super.resendNotify(transSeqId, orderDate, response);
	}

}
