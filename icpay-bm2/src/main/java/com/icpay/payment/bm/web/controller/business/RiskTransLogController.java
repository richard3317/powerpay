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
import com.icpay.payment.common.enums.RiskEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BizUtils;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.db.dao.mybatis.model.RiskTransLog;
import com.icpay.payment.db.service.IRiskTransLogService;

@Controller
@RequestMapping("/riskTransLog")
public class RiskTransLogController extends BaseController {
	
	private static final String RESULT_BASE_URI = "riskTransLog";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String result = m.get("result");
			m.put("resultDesc", EnumUtil.translate(RiskEnums.Result.class, result, true));
			
			BigDecimal transAt = new BigDecimal(m.get("transAt"));
			m.put("transAtDesc", transAt.movePointLeft(2).toString());
			
			String priAcctNo = m.get("priAcctNo");
//			m.put("priAcctNoMask", StringUtil.mask(priAcctNo, 3, priAcctNo.length() - 4, '*'));
			m.put("priAcctNoMask", BizUtils.strMask(priAcctNo, "*", 3, 0, 4));
			
			String intTransCd = m.get("intTransCd");
			m.put("intTransCdDesc", EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, true));
		}
	};
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/monitor.do", method = RequestMethod.GET)
	public String monitor(Model model) {
		model.addAttribute("pageSize", 30);
		return RESULT_BASE_URI + "/monitor";
	}
	
	@RequestMapping(value = "/monitor/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult monitorQry(int monitorNum) {
		if (monitorNum == 0) {
			monitorNum = 20;
		}
		IRiskTransLogService riskTransLogService = this.getDBService(IRiskTransLogService.class);
		Pager<RiskTransLog> p = riskTransLogService.selectByPage(1, monitorNum, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_RISKTRANSLOG, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		IRiskTransLogService riskTransLogService = this.getDBService(IRiskTransLogService.class);
		Pager<RiskTransLog> p = riskTransLogService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_RISKTRANSLOG, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, String transSeqId) {
		IRiskTransLogService riskTransLogService = this.getDBService(IRiskTransLogService.class);
		RiskTransLog entity = riskTransLogService.selectByPrimaryKey(transSeqId);
		AssertUtil.objIsNull(entity, "未找到风险交易信息:" + transSeqId);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_RISKTRANSLOG));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_RISKTRANSLOG, ENTITY_TRANSFER));
		return super.toDetail(model, RESULT_BASE_URI);
	}
}
