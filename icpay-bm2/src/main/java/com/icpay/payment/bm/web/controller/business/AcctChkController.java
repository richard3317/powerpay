package com.icpay.payment.bm.web.controller.business;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.db.dao.mybatis.model.AcctChkResult;
import com.icpay.payment.db.service.IAcctChkResultService;

@Controller
@RequestMapping("/acctChk")
public class AcctChkController extends BaseController {
	
	private static final String RESULT_BASE_URI = "acctChk";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String transAt = m.get("transAt");
			m.put("transAtDesc", String.valueOf(new BigDecimal(transAt).movePointLeft(2)));
			
			String transChnl = m.get("transChnl");
			m.put("transChnlDesc", EnumUtil.translate(TxnEnums.ChnlId.class, transChnl, true));
			
			String checkResult = m.get("checkResult");
			m.put("checkResultDesc", EnumUtil.translate(CommonEnums.AcctChkResult.class, checkResult, true));
			
			String intTransCd = m.get("intTransCd");
			m.put("intTransCdDesc", EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, true));
		}
	};
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	//-------------查询方法
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		IAcctChkResultService acctChkService = this.getDBService(IAcctChkResultService.class);
		Pager<AcctChkResult> p = acctChkService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_ACCTCHKRESULT, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, int seqId) {
		IAcctChkResultService acctChkService = this.getDBService(IAcctChkResultService.class);
		AcctChkResult execLog = acctChkService.selectByPrimaryKey(seqId);
		AssertUtil.objIsNull(execLog, "未找到记录:" + seqId);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_ACCTCHKRESULT));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(execLog, BMConstants.PAGE_CONF_ACCTCHKRESULT, ENTITY_TRANSFER));
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/cmt.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult cmt(int seqId, String comments) {
		IAcctChkResultService acctChkService = this.getDBService(IAcctChkResultService.class);
		acctChkService.cmt(seqId, comments);
		return commonBO.buildSuccResp();
	}
}
