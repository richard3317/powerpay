package com.icpay.payment.bm.web.controller.business;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.entity.AjaxResult;

@Controller
@RequestMapping("/riskListMassItem")
public class RiskListMassItemController extends RiskListItemBaseController {

	private static final String RESULT_BASE_URI = "riskListMassItem";
	
	@Override
	protected String getBaseUri() {
		return RESULT_BASE_URI;
	}

	@Override
	protected String getPageConfig() {
		return BMConstants.PAGE_CONF_RISKLISTMASSITEM;
	}
	
	@Override
	protected boolean isMassList() {
		return true;
	}
	
	private static final String[] ExcludedGroupIds= {};
	
	@Override
	protected String[] excludedGroupIds() {
		return ExcludedGroupIds;
	}
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model, String groupId) {
		return super.manage(model, groupId);
	}
	
	@RequestMapping(value = "/backToManage.do", method = RequestMethod.GET)
	public String backToManage(Model model) {
		return super.backToManage(model);
	}
	
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(Model model, String groupId) {
		return super.add(model,groupId);
	}
	
	@RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addSubmit(Integer groupId, String chnlId, String mchntCd, String items, String comment) {
		return super.addRiskMassItemSubmit(groupId, chnlId, mchntCd, items, comment);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		return super.qryRiskMassItems(pageNum, pageSize);
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult delete(Integer groupId, String chnlId, String mchntCd, String item) {
		return super.deleteRiskMassItem(groupId, chnlId, mchntCd, item);
	}

}
