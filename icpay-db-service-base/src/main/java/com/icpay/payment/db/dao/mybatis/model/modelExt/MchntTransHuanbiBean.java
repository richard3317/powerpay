package com.icpay.payment.db.dao.mybatis.model.modelExt;

import java.io.Serializable;

/**
 * 此类为与配置文件对应的类
 * @author ICP
 *
 */
public class MchntTransHuanbiBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String settleDate;//结算日期
	private String mchntCd;//商户号
	private String mchntCnNm;//商户名称
	private String txnAmtSum;//日交易额
	private String txnAmtSumOld;//昨日日交易额
	private String txnAmtSumPercent;//日交易环
	private String mon; //月份

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getMchntCd() {
		return mchntCd;
	}

	public void setMchntCd(String mchntCd) {
		this.mchntCd = mchntCd;
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

	public String getTxnAmtSumOld() {
		return txnAmtSumOld;
	}

	public void setTxnAmtSumOld(String txnAmtSumOld) {
		this.txnAmtSumOld = txnAmtSumOld;
	}

	public String getTxnAmtSumPercent() {
		return txnAmtSumPercent;
	}

	public void setTxnAmtSumPercent(String txnAmtSumPercent) {
		this.txnAmtSumPercent = txnAmtSumPercent;
	}
	
	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	@Override
	public String toString() {
		return "MchntTransBean [settleDate=" + settleDate + ", mchntCd=" + mchntCd + ", mchntCnNm=" + mchntCnNm
				+ ", txnAmtSum=" + txnAmtSum + ", txnAmtSumOld=" + txnAmtSumOld + ", txnAmtSumPercent="
				+ txnAmtSumPercent + "]";
	}
	
}
