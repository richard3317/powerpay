package com.icpay.payment.db.dao.mybatis.model.modelExt;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 此类为与配置文件对应的类
 * @author ICP
 *
 */
public class DailyProfitResultHuanbi implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String mchntCnNm;//商户名称
	private BigDecimal mchntTxnAmtSum;//总交易额
	private BigDecimal mchntAgentProfit;//总分润
	private BigDecimal mchntCtTxnAmtSum;//上期交易额
	private BigDecimal mchntHuanbi; //商户交易额环比
	private String qryStartDate; //起始查询日期
	private String qryEndDate; //终止查询日期
	private String ctStartDate; //起始对比日期
	private String ctEndDate; //终止对比日期
	private String agentCd; //代理人/中人编号
	protected Integer pageSize;
	protected Integer startNum;
	
	public DailyProfitResultHuanbi() {
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
		return "DailyProfitResultHuanbi [mchntCnNm=" + mchntCnNm + ", mchntTxnAmtSum=" + mchntTxnAmtSum
				+ ", mchntAgentProfit=" + mchntAgentProfit + ", mchntCtTxnAmtSum=" + mchntCtTxnAmtSum + ", mchntHuanbi="
				+ mchntHuanbi + "]";
	}
	
	public DailyProfitResultHuanbi(String mchntCnNm, BigDecimal mchntTxnAmtSum, BigDecimal mchntAgentProfit,
			BigDecimal mchntCtTxnAmtSum, BigDecimal mchntHuanbi) {
		super();
		this.mchntCnNm = mchntCnNm;
		this.mchntTxnAmtSum = mchntTxnAmtSum;
		this.mchntAgentProfit = mchntAgentProfit;
		this.mchntCtTxnAmtSum = mchntCtTxnAmtSum;
		this.mchntHuanbi = mchntHuanbi;
	}
}
