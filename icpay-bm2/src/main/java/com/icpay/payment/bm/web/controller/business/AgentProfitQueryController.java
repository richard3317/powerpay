package com.icpay.payment.bm.web.controller.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icpay.payment.bm.bo.MchntProfitBO;
import com.icpay.payment.bm.cache.AgentInfoCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.ProfitEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.enums.ProfitEnums.AgentType;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentProfitQueryBean;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentProfitQuerySummary;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TxnLogSummary;
import com.icpay.payment.db.service.IAgentInfoService;
import com.icpay.payment.db.service.IAgentProfitQueryService;
import com.icpay.payment.db.service.ITxnLogViewService;

@Controller
@RequestMapping("/agentProfitQuery")
public class AgentProfitQueryController extends BaseController {

	private static final String RESULT_BASE_URI = "agentProfitQuery";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String intTransCd = m.get("intTransCd");
			m.put("intTransCd", EnumUtil.translate(ProfitEnums.ProfitTxnTp.class, intTransCd, true));
			String transAt = m.get("transAt");
			m.put("transAt", StringUtil.formateAmt(transAt));
			String agentProfit = m.get("agentProfit");
			m.put("agentProfit", StringUtil.formateAmt(agentProfit));
			String chnlId = m.get("chnlId");
			m.put("chnlId", EnumUtil.translate(TxnEnums.ChnlId.class, chnlId, true));
		}
	};
	
	/**
	 * 跳到代理商分润查询页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manageProfitRpt(Model model) {
		model.addAttribute("today", DateUtil.yesterday8());
		this.buildCommonData(model);
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize){
		IAgentProfitQueryService service = this.getDBService(IAgentProfitQueryService.class);
		Pager<AgentProfitQueryBean> p = service.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_AGENTPROFITQUERY, ENTITY_TRANSFER));
	}
	
	@Autowired
	protected MchntProfitBO mchntProfitBO;
	
	/**
	 * 导出报表
	 */
	@RequestMapping(value = "/exportRpt.do",method = RequestMethod.GET)
	public void exportRpt(Model model, HttpServletResponse response){
		String agentCd = getQryParamMap().get("agentCd");
		String startDate = getQryParamMap().get("startDate");
		String endDate = getQryParamMap().get("endDate");
		IAgentProfitQueryService service = this.getDBService(IAgentProfitQueryService.class);
		List<AgentProfitQueryBean> respList = service.selectByRpt(this.getQryParamMap());
		AgentProfitQuerySummary res = service.selectSummary(this.getQryParamMap());
		String summary = "";
		if (res != null) {
			String sumTransAt = StringUtil.formateAmt(res.getSumTransAt());
			String sumAgentProfit = StringUtil.formateAmt(res.getSumAgentProfit());
			summary = "交易额加总:" + sumTransAt + "分润加总:" + sumAgentProfit;
		}
		if (StringUtils.isBlank(agentCd)) {
			agentCd = "agentProfit";
		}
		String fileName = agentCd + "_" + startDate + "_" + endDate + ".xls";
		mchntProfitBO.doAgentProfitQueryReportFile(respList, fileName, Constant.UTF8, response, summary);
	}
	
	/**
	 * 导出代理分润总表
	 */
	@RequestMapping(value = "/exportTotalRpt.do",method = RequestMethod.GET)
	public void exportTotalRpt(Model model, HttpServletResponse response){
		String startDate = getQryParamMap().get("startDate");
		String endDate = getQryParamMap().get("endDate");
		IAgentProfitQueryService service = this.getDBService(IAgentProfitQueryService.class);
		List<AgentProfitQuerySummary> respList = service.selectTotalSummary(this.getQryParamMap());
		String fileName = "agentProfit_" + startDate + "_" + endDate + ".xls";
		mchntProfitBO.doAgentProfitTotalReportFile(respList, fileName, Constant.UTF8, response);
	}
	
	/**
	 * 加总
	 */
	@RequestMapping(value = "/amtSum.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody JSONObject amtSum(Model model, String mon) {
		Map<String, String> qryParamMap = this.getQryParamMap();
		return this.summary(qryParamMap);
	}
	
	public JSONObject summary(Map<String, String> qryParamMap) {
		this.debug("[IAgentProfitQueryService][summary] qryParamMap: %s", qryParamMap);
		IAgentProfitQueryService service = this.getDBService(IAgentProfitQueryService.class);
		AgentProfitQuerySummary res = service.selectSummary(qryParamMap);
		Map<String, Object> map = new HashMap<String, Object>();
		if (res != null) {
			map.put("sumTransAt", StringUtil.formateAmt(res.getSumTransAt()));
			map.put("sumAgentProfit", StringUtil.formateAmt(res.getSumAgentProfit()));
		}
		JSONObject result = (JSONObject) JSON.toJSON(map);
		return result;
	}
	
	protected void buildCommonData(Model model) {
		model.addAttribute("agentsList", AgentInfoCache.getAgentsByTypes(AgentType.SELF,AgentType.CHNL,AgentType.MER));
	}
	
}
