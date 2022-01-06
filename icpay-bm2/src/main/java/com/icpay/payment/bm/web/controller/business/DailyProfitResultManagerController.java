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

import com.icpay.payment.bm.bo.MchntProfitBO;
import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.ProfitEnums;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.AgentInfo;
import com.icpay.payment.db.dao.mybatis.model.modelExt.DailyProfitResultManager;
import com.icpay.payment.db.service.IAgentInfoService;
import com.icpay.payment.db.service.IDailyProfitResultViewService;

@Controller
@RequestMapping("/dailyProfitResultManager")
public class DailyProfitResultManagerController extends BaseController {

	private static final String RESULT_BASE_URI = "dailyProfitResultManager";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String intTransCd = m.get("intTransCd");
			m.put("intTransCd", EnumUtil.translate(ProfitEnums.ProfitTxnTp.class, intTransCd, true));
			String mchntTxnAmtSum = m.get("mchntTxnAmtSum");
			m.put("mchntTxnAmtSum", StringUtil.formateAmt(mchntTxnAmtSum));
			String mchntAgentProfit = m.get("mchntAgentProfit");
			m.put("mchntAgentProfit", StringUtil.formateAmt(mchntAgentProfit));
			String mchntCtTxnAmtSum = m.get("mchntCtTxnAmtSum");
			m.put("mchntCtTxnAmtSum", StringUtil.formateAmt(mchntCtTxnAmtSum));
			String transTxnAmtSum = m.get("transTxnAmtSum");
			m.put("transTxnAmtSum", StringUtil.formateAmt(transTxnAmtSum));
			String transAgentProfit = m.get("transAgentProfit");
			m.put("transAgentProfit", StringUtil.formateAmt(transAgentProfit));
			String transCtTxnAmtSum = m.get("transCtTxnAmtSum");
			m.put("transCtTxnAmtSum", StringUtil.formateAmt(transCtTxnAmtSum));
		}
	};
		
	/**
	 * 跳到商户交易环比页面
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
		IDailyProfitResultViewService service = this.getDBService(IDailyProfitResultViewService.class);
		IAgentInfoService agentInfoService = this.getDBService(IAgentInfoService.class);
		List<AgentInfo> list = agentInfoService.selectValidAgentsByType("1");
		String agentCd = list != null ?	list.get(0).getAgentCd() : "";
		String payName = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_PAY_NAME);
		Pager<DailyProfitResultManager> p = null;
		if ("lelipay".equals(payName) || "xihapay".equals(payName)) {
			getQryParamMap().put("agentCd", agentCd);
			p = service.selectManagerLpayVpayByPage(pageNum, pageSize, this.getQryParamMap());
		} else {
			getQryParamMap().put("agentCd", agentCd);
			p = service.selectManagerPpayFpayByPage(pageNum, pageSize, this.getQryParamMap());
		}
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_DAILYPROFITRESULTMANAGER, ENTITY_TRANSFER));
	}
	
	@Autowired
	protected MchntProfitBO mchntProfitBO;
	/**
	 * 报表
	 * @param transDtBen
	 * @param transDtEnd
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/exportRpt.do",method = RequestMethod.GET)
	public void exportRpt(Model model, HttpServletResponse response) {
		IDailyProfitResultViewService service = this.getDBService(IDailyProfitResultViewService.class);
		String payName = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_PAY_NAME);
		List<DailyProfitResultManager> respList = null;
		if ("lelipay".equals(payName) || "xihapay".equals(payName)) {
			respList = service.selectManagerLpayVpayRpt(this.getQryParamMap());
		} else {
			respList = service.selectManagerPpayFpayRpt(this.getQryParamMap());
		}
		String qryStartDate = getQryParamMap().get("qryStartDate");
		String qryEndDate = getQryParamMap().get("qryEndDate");
		String fileNm = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_MANAGER_REPORT_FILE_NM) + "-" + DateUtil.now8() + ".xls";
		String filePath = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TRANS_PROFIT_FILE_PATH);
		String topTitle = getPayName(payName) + "_" + qryStartDate + "-" + qryEndDate;
		mchntProfitBO.exportTransHuanbiExcel(respList,null,null,filePath, fileNm, Constant.UTF8,response, topTitle);
	}
	
	public String getPayName(String payName) {
		String name = "威力";
		if ("machipay".equals(payName)) {
			name = "麻吉";
		} else if ("lelipay".equals(payName)) {
			name = "乐力";
		} else if ("xihapay".equals(payName)) {
			name = "太极";
		}
		return name;
	}
	
}
