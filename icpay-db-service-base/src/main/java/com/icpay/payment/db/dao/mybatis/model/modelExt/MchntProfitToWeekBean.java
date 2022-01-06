package com.icpay.payment.db.dao.mybatis.model.modelExt;

import java.io.Serializable;

/**
 * 
 *
 */
public class MchntProfitToWeekBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mchntCd;//商户号
	private String mchntCnNm;//商户名称
	private String txnAmtSum;//本周交易总额
	private String txnAmtSumOld;//上周交易总额
	private String txnAmtSumPercent;//本周交易环比
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
	@Override
	public String toString() {
		return "MchntProfitToWeekBean [mchntCd=" + mchntCd + ", mchntCnNm=" + mchntCnNm + ", txnAmtSum=" + txnAmtSum
				+ ", txnAmtSumOld=" + txnAmtSumOld + ", txnAmtSumPercent=" + txnAmtSumPercent + "]";
	}
	
}
