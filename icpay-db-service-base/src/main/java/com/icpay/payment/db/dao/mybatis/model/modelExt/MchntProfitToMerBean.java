package com.icpay.payment.db.dao.mybatis.model.modelExt;

import java.io.Serializable;

/**
 * 此类为与配置文件对应的类
 * @author ICP
 *
 */
public class MchntProfitToMerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String settleDate;//结算日期
	private String chnlId;//渠道编号
	private String chnlDesc;//渠道名称
	private String mchntCnNm;//商户名称
	private String txnAmtSum;//日交易额
	private String agentProfit;//日分润
	
	private String txnAmtSumPercent;//日交易环
	private String chnlTxnAmtSum;//渠道日交易额
	private String chnlAgentProfit;//渠道日分润
	private String chnlTxnAmtSumPercent;//渠道日交易环比
	
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getChnlId() {
		return chnlId;
	}
	public void setChnlId(String chnlId) {
		this.chnlId = chnlId;
	}
	public String getChnlDesc() {
		return chnlDesc;
	}
	public void setChnlDesc(String chnlDesc) {
		this.chnlDesc = chnlDesc;
	}
	public String getMchntCnNm() {
		return mchntCnNm;
	}
	public void setMchntCnNm(String mchntCnNm) {
		this.mchntCnNm = mchntCnNm;
	}
	public String getTxnAmtSum() {
		return txnAmtSum;
	}
	public void setTxnAmtSum(String txnAmtSum) {
		this.txnAmtSum = txnAmtSum;
	}
	public String getAgentProfit() {
		return agentProfit;
	}
	public void setAgentProfit(String agentProfit) {
		this.agentProfit = agentProfit;
	}
	public String getTxnAmtSumPercent() {
		return txnAmtSumPercent;
	}
	public void setTxnAmtSumPercent(String txnAmtSumPercent) {
		this.txnAmtSumPercent = txnAmtSumPercent;
	}
	public String getChnlTxnAmtSum() {
		return chnlTxnAmtSum;
	}
	public void setChnlTxnAmtSum(String chnlTxnAmtSum) {
		this.chnlTxnAmtSum = chnlTxnAmtSum;
	}
	public String getChnlAgentProfit() {
		return chnlAgentProfit;
	}
	public void setChnlAgentProfit(String chnlAgentProfit) {
		this.chnlAgentProfit = chnlAgentProfit;
	}
	public String getChnlTxnAmtSumPercent() {
		return chnlTxnAmtSumPercent;
	}
	public void setChnlTxnAmtSumPercent(String chnlTxnAmtSumPercent) {
		this.chnlTxnAmtSumPercent = chnlTxnAmtSumPercent;
	}
	@Override
	public String toString() {
		return "MchntProfitToWeekBean [settleDate=" + settleDate + ", chnlId=" + chnlId + ", chnlDesc=" + chnlDesc
				+ ", mchntCnNm=" + mchntCnNm + ", txnAmtSum=" + txnAmtSum + ", agentProfit=" + agentProfit
				+ ", txnAmtSumPercent=" + txnAmtSumPercent + ", chnlTxnAmtSum=" + chnlTxnAmtSum + ", chnlAgentProfit="
				+ chnlAgentProfit + ", chnlTxnAmtSumPercent=" + chnlTxnAmtSumPercent + "]";
	}
	
}
