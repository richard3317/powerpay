package com.icpay.payment.bm.web.controller.business;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.AgentInfoCache;
import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.cache.ChnlMchntInfoCache;
import com.icpay.payment.bm.cache.MchntInfoCache;
import com.icpay.payment.bm.cache.PageConfCache;
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
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitResultByChnl;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitSettleResult;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitSettleResultKey;
import com.icpay.payment.db.service.IAgentProfitSettleResultService;

@Controller
@RequestMapping("/agentProfitSettleResult")
public class AgentProfitSettleResultController extends BaseController {

	private static final String RESULT_BASE_URI = "agentProfitSettleResult";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			
			String agentCd = m.get("agentCd");
			m.put("agentDesc", AgentInfoCache.getAgentName(agentCd));
			
			String txnAmtSum = m.get("txnAmtSum");
			m.put("txnAmtSumDesc", StringUtil.formateAmt(txnAmtSum));
			
			String agentProfit = m.get("agentProfit");
			m.put("agentProfitDesc", StringUtil.formateAmt(agentProfit));
			
			String intTransCd = m.get("intTransCd");
			if (!Utils.isEmpty(intTransCd))
				m.put("intTransDesc", EnumUtil.translate(ProfitEnums.ProfitTxnTp.class, intTransCd, true));
			
			String mchntCd=m.get("mchntCd");
			Map<String,String> mchnt = MchntInfoCache.getInstance().get(mchntCd);
			if (!Utils.isEmpty(mchnt)) {
				String mchntDesc = mchnt.get("mchntCnNm");
				m.put("mchntDesc", mchntDesc);
			}

			String chnlId = m.get("chnlId");
			if (!Utils.isEmpty(chnlId))
				m.put("chnlIdDesc", EnumUtil.translate(TxnEnums.ChnlId.class, chnlId, true));
			
			String chnlMchntCd=m.get("chnlMchntCd");
			Map<String,String> chnlMchnt = ChnlMchntInfoCache.getInstance().get(chnlId, chnlMchntCd);
			if (!Utils.isEmpty(chnlMchnt)) {
				String chnlMchntDesc = chnlMchnt.get("chnlMchntDesc");
				m.put("chnlMchntDesc", chnlMchntDesc);
			}
		}
	};
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model, String settleDate) {
		if (Utils.isEmpty(settleDate))
			model.addAttribute("settleDate", settleDate);
		this.buildCommonData(model);
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		Pager<AgentProfitSettleResult> p = this.getService().selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_AgentProfitSettleResult, ENTITY_TRANSFER));
	}
	
	protected AgentProfitSettleResultKey getKeyEntity(AgentProfitSettleResult rec) {
		AgentProfitSettleResultKey key = new AgentProfitSettleResultKey();
		key.cloneFrom(rec);
		return key;
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, AgentProfitSettleResult rec) {
		AgentProfitSettleResult entity = this.getService().selectByPrimaryKey(rec);
		AssertUtil.objIsNull(entity, "未找到记录:" + getKeyEntity(rec));
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_AgentProfitSettleResult));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_AgentProfitSettleResult, ENTITY_TRANSFER));
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/export.do", method = {RequestMethod.POST, RequestMethod.GET})
	@QryMethod
	public void exportToExcel(Model model, String mon, HttpServletResponse response) {
		String filename = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_AGENT_PROFIT_RES_FILENM);
		export(model, BMConstants.PAGE_CONF_AgentProfitSettleResult, this.getQryParamMap(), mon, filename, response);
	}
	
	protected AjaxResult export(Model model, String pageConfTp, Map<String, String> qryParamMap, String mon, String filename, HttpServletResponse response) {
		this.debug("[export] 分润结果导出, qryParamMap: %s", qryParamMap);
		List<AgentProfitSettleResult> list = this.getService().select(qryParamMap);
		Pager<Map<String, String>> pager = commonBO.transferList(list, pageConfTp, ENTITY_TRANSFER);
		try {
			commonBO.exportToExcel(pager,null, filename, Constant.UTF8, response);
			return null;
		} catch (Exception e) {
			return commonBO.buildErrorResp(e.getMessage());
		}
	}

	
	IAgentProfitSettleResultService svc = null;
	protected IAgentProfitSettleResultService getService() {
		if (svc==null)
			svc=this.getDBService(IAgentProfitSettleResultService.class);
		return svc;
	}
	
	protected void buildCommonData(Model model) {
		String preDate = DateUtil.preDay(new Date());
		String today = DateUtil.now8();
		model.addAttribute("preDate", preDate);
		model.addAttribute("today", today);
		model.addAttribute("agentsList", AgentInfoCache.getAgentsByTypes(AgentType.SELF,AgentType.CHNL,AgentType.MER));
		//model.addAttribute("",getBankAccSvc().selectByAgent(agentCd, accType))
	}
	
}
