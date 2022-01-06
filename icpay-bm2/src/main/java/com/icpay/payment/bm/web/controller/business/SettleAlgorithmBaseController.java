package com.icpay.payment.bm.web.controller.business;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.constants.Constant.TxnCatalog;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicy;

public class SettleAlgorithmBaseController extends BaseController{
	
	public String algorithmDesc(AgentProfitPolicy agentProfitPolicy,String intTransCd,String newFixRate,String newMaxfee,String newMinfee) {
		String desc = "";
		
		Map<String, String> m = new HashMap<String, String>();
		AssertUtil.strIsBlank(newFixRate, "扣率不能为空.");
		// 固定为单笔固定费率  （小数）
		m.put(Constant.SETTLE_AlG_KEY.fixRate, newFixRate);

		BigDecimal bdMin = null;
		BigDecimal bdMax = null;
		BigDecimal bdFixRate = new BigDecimal(newFixRate);
		if (!StringUtil.isBlank(newMaxfee)) {
			bdMax = (new BigDecimal(newMaxfee)).multiply(new BigDecimal("100"));
			m.put(Constant.SETTLE_AlG_KEY.maxFee, String.valueOf(bdMax));
		}
		if (!StringUtil.isBlank(newMinfee)) {
			bdMin = (new BigDecimal(newMinfee)).multiply(new BigDecimal("100"));
			m.put(Constant.SETTLE_AlG_KEY.minFee, String.valueOf(bdMin));
		}
		if (bdMin != null && bdMax != null && (bdMin.compareTo(bdMax) != -1)) {
			throw new BizzException("保底手续费必须低于封顶手续费");
		}
		
		// 与商户所属代理商的计费扣率、封顶手续费、保底手续费进行比较
		String agentRate = agentProfitPolicy.getRate();
		BigDecimal agentRateBd = new BigDecimal(agentRate);
		String agentMaxFee = agentProfitPolicy.getMaxFee();
		BigDecimal agentMaxFeeBd = StringUtil.isBlank(agentMaxFee) ? null : new BigDecimal(agentMaxFee).multiply(new BigDecimal("100"));
		String agentMinFee = agentProfitPolicy.getMinFee();
		BigDecimal agentMinFeeBd = StringUtil.isBlank(agentMinFee) ? null : new BigDecimal(agentMinFee).multiply(new BigDecimal("100"));
		
		if (!StringUtil.isBlank(agentMaxFee) 
				&& StringUtil.isBlank(newMaxfee)) {
			throw new BizzException("代理商已配置封顶手续费，请输入商户封顶手续费");
		}
		if (!StringUtil.isBlank(agentMinFee) 
				&& StringUtil.isBlank(newMinfee)) {
			throw new BizzException("代理商已配置保底手续费，请请输入商户保底手续费");
		}
		
		// 正向交易:
		// 1. 商户的都要比代理的高
		// 2. 代理有保底商户必须有
		// 反向交易:
		// 1. 代理的保底必须〉=商户的
		// 2. 代理商有保底商户可以没有
		
		TxnCatalog cat = Constant.getTxnCatalog(intTransCd);
		if (TxnCatalog.CONSUME.equals(cat) || TxnCatalog.WITHDRAW.equals(cat)) {
			if (bdFixRate.compareTo(BigDecimal.ZERO) < 0) {
				throw new BizzException("正向交易扣率请输入正值");
			}
			if (bdMax != null && bdMax.compareTo(BigDecimal.ZERO) < 0) {
				throw new BizzException("正向交易封顶手续费请输入正值或0");
			}
			if (bdMin != null && bdMin.compareTo(BigDecimal.ZERO) < 0) {
				throw new BizzException("正向交易保底手续费请输入正值或0");
			}
			// 商户和代理商扣率比较
			if (agentRateBd.compareTo(bdFixRate) > 0) {
				throw new BizzException("正向交易商户扣率不能低于所属代理商扣率");
			}
			if (agentMaxFeeBd != null && bdMax != null) {
				if ((bdMax.multiply(new BigDecimal("100")).compareTo(agentMaxFeeBd) < 0)) {
					throw new BizzException("正向交易商户封顶手续费不能低于代理商封顶手续费");
				}
			}
			if (agentMinFeeBd != null && bdMin != null) {
				if ((bdMin.multiply(new BigDecimal("100")).compareTo(agentMinFeeBd) < 0)) {
					throw new BizzException("正向交易商户保底手续费不能低于代理商保底手续费");
				}
			}
		}else {
			throw new BizzException("不支持的交易类型");
		}

		if (!m.isEmpty()) {
			
			desc = JsonUtil.toJson(m);
		}
		
		return desc;
	}
}
