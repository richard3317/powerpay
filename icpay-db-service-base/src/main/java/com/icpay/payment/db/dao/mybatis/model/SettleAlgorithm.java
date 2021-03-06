package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.constants.Constant.TxnCatalog;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.Amount;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.common.utils.StringUtil;

public class SettleAlgorithm  implements Serializable {

	private static final long serialVersionUID = -1535794041082104245L;
	
	private String mchntCd;
	private String intTransCd;
	private String comment;
	private String currCd;
	
	public String getCurrCd() {
		return currCd;
	}

	public void setCurrCd(String currCd) {
		this.currCd = currCd;
	}

	private String fixRate;
	private String maxFee;
	private String minFee;
	//------------
	private String settleMode;//计费模式
	private String txDayMax;
	private String txAmtMin;
	private String txAmtMax;
	private String txTimeLimit;
	
//	private String dfMinFee;
//	private String dfTxCardDayMax;
//	private String dfTxAmtMin;
//	private String dfTxAmtMax;
//	private String dfTxT0Percent;
//	private String dfTxTimeLimit;
	
	private String txT0Percent;
	private String txCardDayMax;
	
	private String pageReturnUrl;
	private String notifyUrl;
	private String whiteIp;
	private String whiteIpState;
	
	private String intTransCdDesc;
	private String settleModeDesc;
	
	public String getIntTransCdDesc() {
		return intTransCdDesc;
	}

	public void setIntTransCdDesc(String intTransCdDesc) {
		this.intTransCdDesc = intTransCdDesc;
	}

	public String getSettleModeDesc() {
		return settleModeDesc;
	}

	public void setSettleModeDesc(String settleModeDesc) {
		this.settleModeDesc = settleModeDesc;
	}

	public String getPageReturnUrl() {
		return pageReturnUrl;
	}

	public void setPageReturnUrl(String pageReturnUrl) {
		this.pageReturnUrl = pageReturnUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	

	public String getTxCardDayMax() {
		return txCardDayMax;
	}

	public void setTxCardDayMax(String txCardDayMax) {
		this.txCardDayMax = txCardDayMax;
	}

	public String getWhiteIp() {
		return whiteIp;
	}

	public void setWhiteIp(String whiteIp) {
		this.whiteIp = whiteIp;
	}

	public String getWhiteIpState() {
		return whiteIpState;
	}

	public void setWhiteIpState(String whiteIpState) {
		this.whiteIpState = whiteIpState;
	}

	public String algorithmDesc(AgentProfitPolicy agentProfitPolicy) {
		String desc = "";
		
		Map<String, String> m = new HashMap<String, String>();
		AssertUtil.strIsBlank(fixRate, "扣率不能为空.");
		// 固定为单笔固定费率  （小数）
		m.put(Constant.SETTLE_AlG_KEY.fixRate, fixRate);

		BigDecimal bdMin = null;
		BigDecimal bdMax = null;
		BigDecimal bdFixRate = new BigDecimal(fixRate);
		if (!StringUtil.isBlank(maxFee)) {
			bdMax = (new BigDecimal(maxFee)).multiply(new BigDecimal("100"));
			m.put(Constant.SETTLE_AlG_KEY.maxFee, String.valueOf(bdMax));
		}
		if (!StringUtil.isBlank(minFee)) {
			bdMin = (new BigDecimal(minFee)).multiply(new BigDecimal("100"));
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
				&& StringUtil.isBlank(maxFee)) {
			throw new BizzException("代理商已配置封顶手续费，请输入商户封顶手续费");
		}
		if (!StringUtil.isBlank(agentMinFee) 
				&& StringUtil.isBlank(minFee)) {
			throw new BizzException("代理商已配置保底手续费，请请输入商户保底手续费");
		}
		
		// 正向交易:
		// 1. 商户的都要比代理的高
		// 2. 代理有保底商户必须有
		// 反向交易:
		// 1. 代理的保底必须〉=商户的
		// 2. 代理商有保底商户可以没有
		
		TxnCatalog cat = Constant.getTxnCatalog(intTransCd);
//		if (//ProfitEnums.ProfitTxnTp._0100.getCode().equals(intTransCd)
//			ProfitEnums.ProfitTxnTp._0121.getCode().equals(intTransCd)
//			|| ProfitEnums.ProfitTxnTp._0122.getCode().equals(intTransCd)
//			|| ProfitEnums.ProfitTxnTp._0131.getCode().equals(intTransCd)
//			|| ProfitEnums.ProfitTxnTp._0132.getCode().equals(intTransCd)
//			|| ProfitEnums.ProfitTxnTp._0133.getCode().equals(intTransCd)
//			|| ProfitEnums.ProfitTxnTp._0134.getCode().equals(intTransCd)
//			|| ProfitEnums.ProfitTxnTp._0090.getCode().equals(intTransCd)
//			|| ProfitEnums.ProfitTxnTp._0050.getCode().equals(intTransCd)
//			|| ProfitEnums.ProfitTxnTp._5210.getCode().equals(intTransCd)
//			|| ProfitEnums.ProfitTxnTp._5211.getCode().equals(intTransCd)
//			|| ProfitEnums.ProfitTxnTp._0141.getCode().equals(intTransCd)
//			|| ProfitEnums.ProfitTxnTp._0142.getCode().equals(intTransCd)
//			|| ProfitEnums.ProfitTxnTp._0143.getCode().equals(intTransCd)
//			|| ProfitEnums.ProfitTxnTp._0144.getCode().equals(intTransCd)
//				) {
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
		} 
//		else if (ProfitEnums.ProfitTxnTp._3100.getCode().equals(intTransCd)
//				|| ProfitEnums.ProfitTxnTp._0400.getCode().equals(intTransCd)) {
//			if (bdFixRate.compareTo(BigDecimal.ZERO) > 0) {
//				throw new BizzException("反向交易扣率请输入负值");
//			}
//			if (bdMax != null && bdMax.compareTo(BigDecimal.ZERO) > 0) {
//				throw new BizzException("反向交易封顶手续费请输入负值或0");
//			}
//			if (bdMin != null && bdMin.compareTo(BigDecimal.ZERO) > 0) {
//				throw new BizzException("反向交易保底手续费请输入负值或0");
//			}
//			// 商户和代理商扣率比较
//			if (agentRateBd.compareTo(bdFixRate) < 0) {
//				throw new BizzException("反向交易商户扣率不能高于所属代理商扣率");
//			}
//			if (agentMaxFeeBd != null && bdMax != null) {
//				if (bdMax.compareTo(agentMaxFeeBd) > 0) {
//					throw new BizzException("反向交易商户封顶手续费不能高于代理商封顶手续费");
//				}
//			}
//			if (agentMinFeeBd != null && bdMin != null) {
//				if (bdMin.compareTo(agentMinFeeBd) > 0) {
//					throw new BizzException("反向交易商户保底手续费不能高于代理商保底手续费");
//				}
//			}
//		}
		else {
			throw new BizzException("不支持的交易类型");
		}

		if (!m.isEmpty()) {
			
			desc = JsonUtil.toJson(m);
		}
		
		return desc;
	}
	
	@SuppressWarnings("unchecked")
	public void parseAlgorithm(MerSettlePolicySub mps) {
		
		this.setMchntCd(mps.getMchntCd());
		this.setIntTransCd(mps.getIntTransCd());
		this.setComment(mps.getComment());
		
		String algorithmStr = mps.getSettleAlgorithm();
		Map<String, String> m = JsonUtil.fromJson(algorithmStr, Map.class);
		String mchntRate = m.get(Constant.SETTLE_AlG_KEY.fixRate);
		AssertUtil.strIsBlank(mchntRate, "扣率不能为空.");
		this.setFixRate(mchntRate);
		//String mchntMaxFee = m.get(Constant.SETTLE_AlG_KEY.maxFee);
		//String mchntMinFee = m.get(Constant.SETTLE_AlG_KEY.minFee);
		String mchntMaxFee = Amount.create(mps.getCurrCd()).defaultUnit().amount(m.get(Constant.SETTLE_AlG_KEY.maxFee)).toRegularUnit().getAmountValue().stripTrailingZeros().toPlainString();
		String mchntMinFee = Amount.create(mps.getCurrCd()).defaultUnit().amount(m.get(Constant.SETTLE_AlG_KEY.minFee)).toRegularUnit().getAmountValue().stripTrailingZeros().toPlainString();
		if (!StringUtil.isBlank(mchntMaxFee)) {
			this.setMaxFee(mchntMaxFee);
		}
		if (!StringUtil.isBlank(mchntMinFee)) {
			this.setMinFee(mchntMinFee);
		}
	}
	
	public String getMchntCd() {
		return mchntCd;
	}
	public void setMchntCd(String mchntCd) {
		this.mchntCd = mchntCd;
	}
	public String getIntTransCd() {
		return intTransCd;
	}
	public void setIntTransCd(String intTransCd) {
		this.intTransCd = intTransCd;
	}
	public String getFixRate() {
		return fixRate;
	}
	public void setFixRate(String fixRate) {
		this.fixRate = fixRate;
	}
	public String getMaxFee() {
		return maxFee;
	}
	public void setMaxFee(String maxFee) {
		this.maxFee = maxFee;
	}
	public String getMinFee() {
		return minFee;
	}
	public void setMinFee(String minFee) {
		this.minFee = minFee;
	}
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSettleMode() {
		return settleMode;
	}

	public void setSettleMode(String settleMode) {
		this.settleMode = settleMode;
	}

	public String getTxDayMax() {
		return txDayMax;
	}

	public void setTxDayMax(String txDayMax) {
		this.txDayMax = txDayMax;
	}

	public String getTxAmtMin() {
		return txAmtMin;
	}

	public void setTxAmtMin(String txAmtMin) {
		this.txAmtMin = txAmtMin;
	}

	public String getTxAmtMax() {
		return txAmtMax;
	}

	public void setTxAmtMax(String txAmtMax) {
		this.txAmtMax = txAmtMax;
	}

	public String getTxTimeLimit() {
		return txTimeLimit;
	}

	public void setTxTimeLimit(String txTimeLimit) {
		this.txTimeLimit = txTimeLimit;
	}

	public String getTxT0Percent() {
		return txT0Percent;
	}

	public void setTxT0Percent(String txT0Percent) {
		this.txT0Percent = txT0Percent;
	}

}
