package com.icpay.payment.mer.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.SettleEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicy;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicyKey;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySub;
import com.icpay.payment.db.dao.mybatis.model.MerSettleTaskLog;
import com.icpay.payment.db.service.IMerSettlePolicyService;
import com.icpay.payment.db.service.IMerSettlePolicySubService;
import com.icpay.payment.db.service.IMerSettleTaskLogService;
import com.icpay.payment.mer.util.EnumLangUtil;
import com.icpay.payment.mer.util.I18nMsgUtils;

@Component("mchntSettleBO")
public class MchntSettleBO extends BaseBO {
	
	public MerSettlePolicy getMerSettleInfo(String mchntCd, String currCd) {
		MerSettlePolicyKey key = new MerSettlePolicyKey();
		key.setCurrCd(currCd);
		key.setMchntCd(mchntCd);
		IMerSettlePolicyService service = this.getDBService(IMerSettlePolicyService.class);
		return service.selectByPrimaryKey(key);
	}

	public Pager<MerSettleTaskLog> qrySettleLogLst(int pageNum, int pageSize, Map<String, String> qryParams) {
		IMerSettleTaskLogService service = this.getDBService(IMerSettleTaskLogService.class);
		return service.selectByPage(pageNum, pageSize, qryParams);
	}
	
	public MerSettleTaskLog getSettleTaskLog(int seqId) {
		IMerSettleTaskLogService service = this.getDBService(IMerSettleTaskLogService.class);
		return service.selectByPrimaryKey(seqId);
	}
	
	public List<Map<String, String>> buildAlgorithmLst(HttpServletRequest req) {
		IMerSettlePolicySubService service = this.getDBService(IMerSettlePolicySubService.class);
		List<MerSettlePolicySub> lst = service.select(this.getMchntCd());
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		String lan = I18nMsgUtils.getLanguage(req);
		for (MerSettlePolicySub mps : lst) {
			Map<String, String> m = BeanUtil.desc(mps, null, null);
			m.put("intTransCdDesc", 
					EnumLangUtil.translate(lan,"SettleTxnType", mps.getIntTransCd(), false));
			m.put("currCdDesc", 
					EnumLangUtil.translate(lan,"CurrType", mps.getCurrCd(), false));
			m.put("settleModeDesc", 
					EnumLangUtil.translate(lan, "SettleMode", mps.getSettleMode(), false));
			m.put("settleAlgorithmDesc", 
					translateAlgorithm(lan, mps.getSettleMode(), mps.getSettleAlgorithm()));
			result.add(m);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private String translateAlgorithm(String lan, String settleMode, String algorithm) {
		StringBuilder desc = new StringBuilder();
		Map<String, String> m = JsonUtil.fromJson(algorithm, Map.class);
		if (SettleEnums.SettleMode._1.getCode().equals(settleMode)) {
			desc.append("单笔手续费金额:" + new BigDecimal(m.get(Constant.SETTLE_AlG_KEY.fixFee)).movePointLeft(2) + "元");
		} else if (SettleEnums.SettleMode._2.getCode().equals(settleMode)) {
			desc.append(mappingI18n(this.getClass().getSimpleName(),"扣率:", lan) + m.get(Constant.SETTLE_AlG_KEY.fixRate));
		} else if (SettleEnums.SettleMode._3.getCode().equals(settleMode)
				|| SettleEnums.SettleMode._5.getCode().equals(settleMode)) {
			
			for (int i = 1; i < 6; i ++) {
				String rangeFromKey = Constant.SETTLE_AlG_KEY.rangeFrom + i;
				String rangeToKey = Constant.SETTLE_AlG_KEY.rangeTo + i;
				String rateKey = Constant.SETTLE_AlG_KEY.rate + i;
				
				if (!StringUtil.isBlank(m.get(rangeFromKey))
						&& !StringUtil.isBlank(m.get(rangeToKey))
						&& !StringUtil.isBlank(m.get(rateKey))) {
					if (i > 1) {
						desc.append("<br/>");
					}
					BigDecimal bd1 = new BigDecimal(m.get(rangeFromKey));
					BigDecimal bd2 = new BigDecimal(m.get(rangeToKey));
					desc.append("金额区间" + i + ":" + bd1.movePointLeft(2).toString() + 
							"元至" + bd2.movePointLeft(2).toString() + "元扣率为" + m.get(rateKey));
				}
			}
			
			if (!StringUtil.isBlank(m.get(Constant.SETTLE_AlG_KEY.minFee))) {
				BigDecimal bd = new BigDecimal(m.get(Constant.SETTLE_AlG_KEY.minFee));
				desc.append("<br/>保底手续费:" + String.valueOf(bd.movePointLeft(2)) + "元");
			}
			if (!StringUtil.isBlank(m.get(Constant.SETTLE_AlG_KEY.maxFee))) {
				BigDecimal bd = new BigDecimal(m.get(Constant.SETTLE_AlG_KEY.maxFee));
				desc.append("<br/>封顶手续费:" + String.valueOf(bd.movePointLeft(2)) + "元");
			}
		} else if (SettleEnums.SettleMode._4.getCode().equals(settleMode)) {
			for (int i = 1; i < 6; i ++) {
				String rangeFromKey = Constant.SETTLE_AlG_KEY.rangeFrom + i;
				String rangeToKey = Constant.SETTLE_AlG_KEY.rangeTo + i;
				String rateKey = Constant.SETTLE_AlG_KEY.rate + i;
				
				if (!StringUtil.isBlank(m.get(rangeFromKey))
						&& !StringUtil.isBlank(m.get(rangeToKey))
						&& !StringUtil.isBlank(m.get(rateKey))) {
					if (i > 1) {
						desc.append("<br/>");
					}
					desc.append("笔数区间" + i + ":" + m.get(rangeFromKey) + 
							"笔至" + m.get(rangeToKey) + "笔扣率为" + m.get(rateKey));
				}
			}
			
			if (!StringUtil.isBlank(m.get(Constant.SETTLE_AlG_KEY.minFee))) {
				BigDecimal bd = new BigDecimal(m.get(Constant.SETTLE_AlG_KEY.minFee));
				desc.append("<br/>保底手续费:" + String.valueOf(bd.movePointLeft(2)) + "元");
			}
			if (!StringUtil.isBlank(m.get(Constant.SETTLE_AlG_KEY.maxFee))) {
				BigDecimal bd = new BigDecimal(m.get(Constant.SETTLE_AlG_KEY.maxFee));
				desc.append("<br/>封顶手续费:" + String.valueOf(bd.movePointLeft(2)) + "元");
			}
		} else {
			throw new BizzException("不支持的计费方式:" + settleMode);
		}
		return desc.toString();
	}
	
	public String mappingI18n(String name , String msg, String lang) {
		return I18nMsgUtils.getMessageWithDefault(
				I18nMsgUtils.ICPAY_MER, 
				lang,
				I18nMsgUtils.getKeyCombine(name,msg),msg);
	}
}
