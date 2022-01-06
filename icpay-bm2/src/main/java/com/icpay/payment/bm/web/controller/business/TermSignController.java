package com.icpay.payment.bm.web.controller.business;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.TermSignInfo;
import com.icpay.payment.db.service.ITermSignInfoService;

@Controller
@RequestMapping("/termSign")
public class TermSignController extends BaseController {

	private static final String RESULT_BASE_URI = "terminal";

	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		return RESULT_BASE_URI + "/term_sign";
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		ITermSignInfoService termSignService = this.getDBService(ITermSignInfoService.class);
		Pager<TermSignInfo> p = termSignService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_TERMSIGNINFO, null));
	}
	
}
