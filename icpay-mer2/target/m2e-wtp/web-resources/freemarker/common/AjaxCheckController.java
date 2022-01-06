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
import com.icpay.payment.db.service.IAgentInfoService;
import com.icpay.payment.db.service.IAgentProfitInfoService;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.IMerSettlePolicyService;
import com.icpay.payment.mer.util.CheckBankCardUtil;

@Controller
@RequestMapping("/ajaxCheck")
public class AjaxCheckController extends BaseController {
	/**
	 * 预校验银行卡是否正确
	 */
	@RequestMapping("preValid.do")
	public String preValid(String accNo, Model model) {
		String result = "true";
		if(!CheckBankCardUtil.checkBankCard(accNo)) {
			result="输入的银行卡不合法!";
		}
		model.addAttribute("result", result);
		return "common/ajax_validate_result";
	}
}
