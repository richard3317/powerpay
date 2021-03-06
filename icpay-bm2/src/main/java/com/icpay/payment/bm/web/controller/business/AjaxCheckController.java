package com.icpay.payment.bm.web.controller.business;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.AgentInfo;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicy;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicyKey;
import com.icpay.payment.db.service.IAgentInfoService;
import com.icpay.payment.db.service.IAgentProfitInfoService;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.IMerSettlePolicyService;

@Controller
@RequestMapping("/ajaxCheck")
public class AjaxCheckController extends BaseController {

	@RequestMapping("/checkMchntCd.do")
	public String checkMchntCd(String mchntCd, Model model) {
		String result = "商户不存在";
		if (!StringUtil.isBlank(mchntCd) && mchntCd.length() != 15) {
			result = "商户号有误";
		} else {
			IMchntInfoService service = this.getDBService(IMchntInfoService.class);
			MchntInfo m = service.selectByPrimaryKey(mchntCd);
			if (m == null) {
				result = "商户不存在";
			} else if (!CommonEnums.MchntSt._1.getCode().equals(m.getMchntSt())) {
				result = "商户状态为无效";
			} else {
				result = "true";
			}
		}
		model.addAttribute("result", result);
		return "common/ajax_validate_result";
	}
	
	@RequestMapping("/checkSettleMchntCd.do")
	public String checkSettleMchntCd(String mchntCd, String currCd, Model model) {
		String result = "true";
		if (!StringUtil.isBlank(mchntCd) && mchntCd.length() != 15) {
			result = "商户号有误";
		} else {
			IMchntInfoService service = this.getDBService(IMchntInfoService.class);
			MchntInfo m = service.selectByPrimaryKey(mchntCd);
			if (m == null) {
				result = "商户不存在";
			} else if (!CommonEnums.MchntSt._1.getCode().equals(m.getMchntSt())) {
				result = "商户状态为无效";
			}
			
			IMerSettlePolicyService settleService = this.getDBService(IMerSettlePolicyService.class);
			MerSettlePolicyKey key = new MerSettlePolicyKey();
			key.setCurrCd(currCd);
			key.setMchntCd(mchntCd);
			MerSettlePolicy p = settleService.selectByPrimaryKey(key);
			if (p != null) {
				result = "该商户已配置清算账户信息";
			}
		}
		model.addAttribute("result", result);
		return "common/ajax_validate_result";
	}
	
	@RequestMapping("/checkProfitAgentCd.do")
	public String checkProfitAgentCd(String agentCd, Model model) {
		String result = "true";
		if (!StringUtil.isBlank(agentCd) && agentCd.length() != 15) {
			result = "代理商代码有误";
		} else {
			IAgentInfoService service = this.getDBService(IAgentInfoService.class);
			AgentInfo agent = service.selectByPrimaryKey(agentCd);
			if (agent == null) {
				result = "代理商不存在";
			} else if (!CommonEnums.AgentSt._1.getCode().equals(agent.getAgentSt())) {
				result = "代理商状态无效";
			}
			
			IAgentProfitInfoService profitService = this.getDBService(IAgentProfitInfoService.class);
			AgentProfitInfo entity = profitService.selectByPrimaryKey(agentCd);
			if (entity != null) {
				result = "该代理商已配置分润信息";
			}
		}
		model.addAttribute("result", result);
		return "common/ajax_validate_result";
	}
}
