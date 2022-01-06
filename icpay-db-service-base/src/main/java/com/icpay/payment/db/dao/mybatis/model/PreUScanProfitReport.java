package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PreUScanProfitReport implements Serializable {
   
	/**
	 * 预充值云闪付统计报表
	 */
	private static final long serialVersionUID = 7161561639062571323L;
	
	String chnlMchntCd;//渠道商户号
	String chnlMchntName;//渠道商户名称
	String mchntCd;//商户号
	String mchntName;//商户名称
	BigDecimal fixRate = new BigDecimal(0);//云闪付转账费率
	BigDecimal chnlFixRate = new BigDecimal(0);//渠道费率
	BigDecimal sumAmtW = new BigDecimal(0);//手续费充值总金额
	BigDecimal sumAmtM = new BigDecimal(0);//网银充值总金额
	BigDecimal sumAmtN = new BigDecimal(0);//快捷充值总金额
	BigDecimal sumAmtA = new BigDecimal(0);//充值到上游金额
	BigDecimal sumAmtP = new BigDecimal(0);//实际收益
	BigDecimal sumAmtC = new BigDecimal(0);//收款成本
	BigDecimal sumAmtY = new BigDecimal(0);//手续费出款金额
	String settDate;//结算日期
	
	BigDecimal sumAmt = new BigDecimal(0);//总金额
	String intTransCd;//交易类型
	
	public String getChnlMchntCd() {
		return chnlMchntCd;
	}
	public void setChnlMchntCd(String chnlMchntCd) {
		this.chnlMchntCd = chnlMchntCd;
	}
	public String getChnlMchntName() {
		return chnlMchntName;
	}
	public void setChnlMchntName(String chnlMchntName) {
		this.chnlMchntName = chnlMchntName;
	}
	public String getMchntCd() {
		return mchntCd;
	}
	public void setMchntCd(String mchntCd) {
		this.mchntCd = mchntCd;
	}
	public String getMchntName() {
		return mchntName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	public BigDecimal getFixRate() {
		return fixRate;
	}
	public void setFixRate(BigDecimal fixRate) {
		this.fixRate = fixRate;
	}
	public BigDecimal getSumAmtW() {
		return sumAmtW;
	}
	public void setSumAmtW(BigDecimal sumAmtW) {
		this.sumAmtW = sumAmtW;
	}
	public BigDecimal getSumAmtM() {
		return sumAmtM;
	}
	public void setSumAmtM(BigDecimal sumAmtM) {
		this.sumAmtM = sumAmtM;
	}
	public BigDecimal getSumAmtN() {
		return sumAmtN;
	}
	public void setSumAmtN(BigDecimal sumAmtN) {
		this.sumAmtN = sumAmtN;
	}
	public BigDecimal getSumAmtA() {
		return sumAmtA;
	}
	public void setSumAmtA(BigDecimal sumAmtA) {
		this.sumAmtA = sumAmtA;
	}
	public BigDecimal getSumAmtP() {
		return sumAmtP;
	}
	public void setSumAmtP(BigDecimal sumAmtP) {
		this.sumAmtP = sumAmtP;
	}
	public BigDecimal getSumAmtC() {
		return sumAmtC;
	}
	public void setSumAmtC(BigDecimal sumAmtC) {
		this.sumAmtC = sumAmtC;
	}
	public BigDecimal getSumAmtY() {
		return sumAmtY;
	}
	public void setSumAmtY(BigDecimal sumAmtY) {
		this.sumAmtY = sumAmtY;
	}
	public String getSettDate() {
		return settDate;
	}
	public void setSettDate(String settDate) {
		this.settDate = settDate;
	}
	public BigDecimal getSumAmt() {
		return sumAmt;
	}
	public void setSumAmt(BigDecimal sumAmt) {
		this.sumAmt = sumAmt;
	}
	public String getIntTransCd() {
		return intTransCd;
	}
	public void setIntTransCd(String intTransCd) {
		this.intTransCd = intTransCd;
	}
	public BigDecimal getChnlFixRate() {
		return chnlFixRate;
	}
	public void setChnlFixRate(BigDecimal chnlFixRate) {
		this.chnlFixRate = chnlFixRate;
	}
	
	public void clone(PreUScanProfitReport prpt) {
		this.chnlMchntCd=prpt.chnlMchntCd;//渠道商户号
		this.chnlMchntName=prpt.chnlMchntName;//渠道商户名称
		this.mchntCd=prpt.mchntCd;//商户号
		this.mchntName=prpt.mchntName;//商户名称
		this.fixRate=prpt.fixRate;//云闪付转账费率
		this.chnlFixRate =prpt.chnlFixRate;//渠道费率
		this.sumAmtW =prpt.sumAmtW;//手续费充值总金额
		this.sumAmtM =prpt.sumAmtM;//网银充值总金额
		this.sumAmtN =prpt.sumAmtN;//快捷充值总金额
		this.sumAmtA =prpt.sumAmtA;//充值到上游金额
		this.sumAmtP=prpt.sumAmtP;//实际收益
		this.sumAmtC =prpt.sumAmtC;//收款成本
		this.sumAmtY=prpt.sumAmtY;//手续费出款金额
		this.settDate =prpt.settDate;//结算日期
		this.sumAmt = prpt.sumAmt;//总金额
		this.intTransCd=prpt.intTransCd;//交易类型
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
}