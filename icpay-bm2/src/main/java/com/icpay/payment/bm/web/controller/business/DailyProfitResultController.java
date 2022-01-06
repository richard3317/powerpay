package com.icpay.payment.bm.web.controller.business;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.icpay.payment.bm.bo.DailyProfitBO;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.DailyProfitResult;
import com.icpay.payment.db.service.IDailyProfitResultService;

@Controller
@RequestMapping("/dailyProfitResult")
public class DailyProfitResultController extends BaseController {

	private static final String RESULT_BASE_URI = "dailyProfitResult";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String chnlId = m.get("chnlId");
			if (!Utils.isEmpty(chnlId))
				m.put("chnlIdDesc", EnumUtil.translate(TxnEnums.ChnlId.class, chnlId, true));
			
			String txnAmtSum = m.get("txnAmtSum");
			m.put("txnAmtSumDesc", StringUtil.formateAmt(txnAmtSum));
			String agentProfit = m.get("agentProfit");
			m.put("agentProfitDesc", StringUtil.formateAmt(agentProfit));
			
			String chnlTxnAmtSum = m.get("chnlTxnAmtSum");
			m.put("chnlTxnAmtSumDesc", StringUtil.formateAmt(chnlTxnAmtSum));
			String chnlAgentProfit = m.get("chnlAgentProfit");
			m.put("chnlAgentProfitDesc", StringUtil.formateAmt(chnlAgentProfit));
			
			String intTransCd = m.get("intTransCd");
			m.put("intTransCdDesc", EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, true));

		}
	};
		
	/**
	 * 跳到每日财报页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manageProfitRpt(Model model) {
		model.addAttribute("today", DateUtil.yesterday8());
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		IDailyProfitResultService service = this.getDBService(IDailyProfitResultService.class);
		Pager<DailyProfitResult> p = service.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_DAILYPROFITRESULT, ENTITY_TRANSFER));
	}
	
//	@Autowired
//	protected DailyProfitBO dailyProfitBO;
	/**
	 * 下载
	 * @param transDtBen
	 * @param transDtEnd
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/exportRpt.do",method = RequestMethod.GET)
	public void exportRpt(Model model, HttpServletResponse response) {
//		String filename = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TRANS_PROFIT_EXPORT_FILENM)+ "_" + DateUtil.now8() + ".xls";
		String filename = "交易日报_" + DateUtil.now8() + ".xls";
		export(model, BMConstants.PAGE_CONF_DAILYPROFITRESULT, this.getQryParamMap(), filename, response);
	}
	
	protected AjaxResult export(Model model, String pageConfTp, Map<String, String> qryParamMap, String filename, HttpServletResponse response) {
		this.debug("[export] 交易日报导出, qryParamMap: %s", qryParamMap);
		IDailyProfitResultService service = this.getDBService(IDailyProfitResultService.class);
		List<DailyProfitResult> list = service.select(qryParamMap);
		Pager<Map<String, String>> pager = commonBO.transferList(list, pageConfTp, ENTITY_TRANSFER);
		try {
//			dailyProfitBO.exportToExcel(pager,null, filename, Constant.UTF8, response);
			commonBO.exportToExcel(pager,null, filename, Constant.UTF8, response);
			return null;
		} catch (Exception e) {
			return commonBO.buildErrorResp(e.getMessage());
		}
	}
	
}
