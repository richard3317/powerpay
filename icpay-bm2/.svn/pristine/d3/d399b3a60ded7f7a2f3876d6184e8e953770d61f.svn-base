package com.icpay.payment.bm.web.controller.business;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.db.dao.mybatis.model.RiskListItemMer;

@Controller
@RequestMapping("/riskListItem")
public class RiskListItemController extends RiskListItemBaseController {

	private static final String RESULT_BASE_URI = "riskListItem";
	
	@Override
	protected String getBaseUri() {
		return RESULT_BASE_URI;
	}

	@Override
	protected String getPageConfig() {
		return BMConstants.PAGE_CONF_RISKLISTITEM;
	}
	
	@Override
	protected boolean isMassList() {
		return false;
	}
	
	private static final String[] ExcludedGroupIds= {"102","601"};
	
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
		return super.addRiskItemSubmit(groupId, chnlId, mchntCd, items, comment);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		return super.qryRiskItems(pageNum, pageSize);
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult delete(Long id, Integer groupId) {
		return super.deleteRiskItem(id, groupId);
	}
	
	@RequestMapping(value = "/getRiskGroup.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult getRiskGroup(Model model, String groupId) {
		return super.getRiskGroup(model, groupId);
	}
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, Long id) {
		return super.edit(model, id);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(RiskListItemMer entity) {
		return super.editSubmit(entity);
	}
}
