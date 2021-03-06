package com.icpay.payment.bm.web.controller.business;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.db.dao.mybatis.model.TxnRouting;

@Controller
@RequestMapping("/routDaifu")
public class RouteDaifuController extends RouteBaseController {
	
	private static final String RESULT_BASE_URI = "routDaifu";
	@Override
	protected String getResultBaseUri() {
		return RESULT_BASE_URI;
	}

	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	@QryMethod
	public String manage(Model model) {
		return super.manage(model);
	}

	@RequestMapping(value = "/backToManage.do", method = RequestMethod.GET)
	public String backToManage(Model model) {
		return super.backToManage(model);
	}

	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize, int showMore) {
		return super.qry(pageNum, pageSize, showMore);
	}

	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(Model model) {
		return super.add(model);
	}

	@RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addSubmit(TxnRouting txnRouting) {
		return super.addSubmit(txnRouting, false);
	}

	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, String intTransCd, String mchntCd, String chnlId, String chnlMchntCd, String currCd) {
		return super.edit(model, intTransCd, mchntCd, chnlId, chnlMchntCd,currCd);
	}

	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(Model model, TxnRouting tr) {
		return super.editSubmit(model, tr, false);
	}

	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult delete(String mchntCd, String intTransCd, String chnlId, String chnlMchntCd, String currCd) {
		return super.delete(mchntCd, intTransCd, chnlId, chnlMchntCd, currCd, false);
	}
	
	
	@RequestMapping(value = "/batchEdit.do", method = {RequestMethod.POST, RequestMethod.GET})
	@QryMethod	
	public String batchEdit(Model model) {
		return super.batchEdit(model);
	}
	
	@RequestMapping(value = "/batchEditSubmit.do", method = RequestMethod.POST)
	@QryMethod	
	public @ResponseBody AjaxResult batchEditSubmit(Model model, TxnRouting tr) {
		this.checkFuncRight("2000030011");
		return super.batchEditSubmit(model, tr, false);
	}
	
	@RequestMapping(value = "/batchAdd.do", method = {RequestMethod.POST, RequestMethod.GET})
	@QryMethod
	public String batchAdd(Model model) {
		this.debug("QryParamMap: "+this.getQryParamMap());
		return super.batchAdd(model);
	}
	
	@RequestMapping(value = "/batchAddSubmit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult batchAddSubmit(Model model, TxnRouting tr, String mchntCdsList) {
		this.checkFuncRight("2000030013");
		return super.batchAddSubmit(model, tr, mchntCdsList, false);
	}

	@RequestMapping(value = "/batchDelete.do", method = {RequestMethod.POST, RequestMethod.GET})
	@QryMethod
	public @ResponseBody AjaxResult batchDelete(Model model, TxnRouting tr) {
		this.checkFuncRight("2000030012");
		return super.batchDelete(model, false);
	}
	
	@RequestMapping(value = "/getChnlMchnts.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult getChnlMchnts(String chnlId) {
		return super.getChnlMchnts(chnlId);
	}
	
	@RequestMapping(value = "/exportRoutDaifuLog.do", method = RequestMethod.GET)
	public @ResponseBody void exportRoutDaifuLog(Model model, HttpServletResponse response) {
		String filename = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_ROUTDAIFU_EXPORT_FILENM);
		super.export(model, BMConstants.PAGE_TXN_ROUTING_MAPPING, this.getQryParamMap(), filename, response);
	}

}
