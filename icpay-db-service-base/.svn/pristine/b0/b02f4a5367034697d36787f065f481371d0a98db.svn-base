package com.icpay.payment.db.dao.mybatis.model.modelExt;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 此类为与配置文件对应的类
 * @author ICP
 *
 */
public class DailyProfitResultManager implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String intTransCd;//交易类型
	private String mchntCnNm;//商户名称
	private BigDecimal mchntTxnAmtSum;//总交易额
	private BigDecimal mchntAgentProfit;//总分润
	private BigDecimal mchntCtTxnAmtSum;//上期总交易额
	private BigDecimal mchntHuanbi; //商户环比
	private BigDecimal transTxnAmtSum; //各类支付交易额
	private BigDecimal transAgentProfit; //各类支付分润
	private BigDecimal transCtTxnAmtSum; //上期各类支付交易额
	private BigDecimal transHuanbi; //各类支付交易额环比
	private String qryStartDate; //起始查询日期
	private String qryEndDate; //终止查询日期
	private String ctStartDate; //起始对比日期
	private String ctEndDate; //终止对比日期
	private String agentCd; //代理人/中人编号
	protected Integer pageSize;
	protected Integer startNum;
	
	public DailyProfitResultManager() {
		super();
	}

	public String getQryStartDate() {
		return qryStartDate;
	}

	public void setQryStartDate(String qryStartDate) {
		this.qryStartDate = qryStartDate;
	}

	public String getQryEndDate() {
		return qryEndDate;
	}

	public void setQryEndDate(String qryEndDate) {
		this.qryEndDate = qryEndDate;
	}

	public String getCtStartDate() {
		return ctStartDate;
	}

	public void setCtStartDate(String ctStartDate) {
		this.ctStartDate = ctStartDate;
	}

	public String getCtEndDate() {
		return ctEndDate;
	}

	public void setCtEndDate(String ctEndDate) {
		this.ctEndDate = ctEndDate;
	}
	
	public String getIntTransCd() {
		return intTransCd;
	}

	public void setIntTransCd(String intTransCd) {
		this.intTransCd = intTransCd;
	}

	public String getMchntCnNm() {
		return mchntCnNm;
	}

	public void setMchntCnNm(String mchntCnNm) {
		this.mchntCnNm = mchntCnNm;
	}

	public BigDecimal getMchntTxnAmtSum() {
		return mchntTxnAmtSum;
	}

	public void setMchntTxnAmtSum(BigDecimal mchntTxnAmtSum) {
		this.mchntTxnAmtSum = mchntTxnAmtSum;
	}

	public BigDecimal getMchntAgentProfit() {
		return mchntAgentProfit;
	}

	public void setMchntAgentProfit(BigDecimal mchntAgentProfit) {
		this.mchntAgentProfit = mchntAgentProfit;
	}

	public BigDecimal getMchntCtTxnAmtSum() {
		return mchntCtTxnAmtSum;
	}

	public void setMchntCtTxnAmtSum(BigDecimal mchntCtTxnAmtSum) {
		this.mchntCtTxnAmtSum = mchntCtTxnAmtSum;
	}

	public BigDecimal getMchntHuanbi() {
		return mchntHuanbi;
	}

	public void setMchntHuanbi(BigDecimal mchntHuanbi) {
		this.mchntHuanbi = mchntHuanbi;
	}

	public BigDecimal getTransTxnAmtSum() {
		return transTxnAmtSum;
	}

	public void setTransTxnAmtSum(BigDecimal transTxnAmtSum) {
		this.transTxnAmtSum = transTxnAmtSum;
	}

	public BigDecimal getTransAgentProfit() {
		return transAgentProfit;
	}

	public void setTransAgentProfit(BigDecimal transAgentProfit) {
		this.transAgentProfit = transAgentProfit;
	}

	public BigDecimal getTransCtTxnAmtSum() {
		return transCtTxnAmtSum;
	}

	public void setTransCtTxnAmtSum(BigDecimal transCtTxnAmtSum) {
		this.transCtTxnAmtSum = transCtTxnAmtSum;
	}

	public BigDecimal getTransHuanbi() {
		return transHuanbi;
	}

	public void setTransHuanbi(BigDecimal transHuanbi) {
		this.transHuanbi = transHuanbi;
	}
	
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }
	
    public Integer getStartNum() {
        return startNum;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }
    
	public String getAgentCd() {
		return agentCd;
	}

	public void setAgentCd(String agentCd) {
		this.agentCd = agentCd;
	}

	@Override
	public String toString() {
		return "MchntTransHuanbiManager [intTransCd=" + intTransCd + ", mchntCnNm=" + mchntCnNm + 
				", mchntTxnAmtSum=" + mchntTxnAmtSum + ", mchntAgentProfit=" + mchntAgentProfit + 
				", mchntCtTxnAmtSum=" + mchntCtTxnAmtSum + 
				", mchntHuanbi=" + mchntHuanbi + ", transTxnAmtSum=" + transTxnAmtSum + 
				", transAgentProfit=" + transAgentProfit + ", transCtTxnAmtSum=" + transCtTxnAmtSum + 
				", transHuanbi=" + transHuanbi +  "]";
	}
	
	public DailyProfitResultManager(String intTransCd, String mchntCnNm, BigDecimal mchntTxnAmtSum, BigDecimal mchntAgentProfit,
			BigDecimal mchntCtTxnAmtSum, BigDecimal mchntHuanbi, BigDecimal transTxnAmtSum,
			BigDecimal transAgentProfit, BigDecimal transCtTxnAmtSum, 
			BigDecimal transHuanbi) {
		super();
		this.intTransCd = intTransCd;
		this.mchntCnNm = mchntCnNm;
		this.mchntTxnAmtSum = mchntTxnAmtSum;
		this.mchntAgentProfit = mchntAgentProfit;
		this.mchntCtTxnAmtSum = mchntCtTxnAmtSum;
		this.mchntHuanbi = mchntHuanbi;
		this.transTxnAmtSum = transTxnAmtSum;
		this.transAgentProfit = transAgentProfit;
		this.transCtTxnAmtSum = transCtTxnAmtSum;
		this.transHuanbi = transHuanbi;
	}
}
