package com.icpay.payment.db.dao.mybatis.model.modelExt;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : view_txn_log_01
 * @author robin
 *
 * @mbg.generated
 */
public class AgentProfitQuerySummary implements Serializable {
	
	private String agentCd;//代理商代码
	private String agentCnNm;//代理商名称
	private Long sumTransAt;
	private Long sumAgentProfit;
	
	public AgentProfitQuerySummary() {
		super();
	}
	
	
	
	public String getAgentCd() {
		return agentCd;
	}



	public void setAgentCd(String agentCd) {
		this.agentCd = agentCd;
	}



	public String getAgentCnNm() {
		return agentCnNm;
	}



	public void setAgentCnNm(String agentCnNm) {
		this.agentCnNm = agentCnNm;
	}



	public Long getSumTransAt() {
		return sumTransAt;
	}



	public void setSumTransAt(Long sumTransAt) {
		this.sumTransAt = sumTransAt;
	}



	public Long getSumAgentProfit() {
		return sumAgentProfit;
	}



	public void setSumAgentProfit(Long sumAgentProfit) {
		this.sumAgentProfit = sumAgentProfit;
	}



	public AgentProfitQuerySummary(String agentCd, String agentCnNm, Long sumTransAt, Long sumAgentProfit) {
		super();
		this.agentCd = agentCd;
		this.agentCnNm = agentCnNm;
		this.sumTransAt = sumTransAt;
		this.sumAgentProfit = sumAgentProfit;
	}



	@Override
	public String toString() {
		return "AgentProfitQuerySummary [agentCd=" + agentCd + ", agentCnNm=" + agentCnNm + ", sumTransAt=" + sumTransAt
				+ ", sumAgentProfit=" + sumAgentProfit + "]";
	}


}