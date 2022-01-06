package com.icpay.payment.db.dao.mybatis.model.modelExt;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * Database table : tbl_agent_profit_withdraw_log	
 * @author richard
 *
 * @mbg.generated
 */
public class AgentProfitWithdrawSummary implements Serializable {
	
	private Long sumTxnAmt;
	
	public AgentProfitWithdrawSummary() {
		super();
	}
	
	public AgentProfitWithdrawSummary(Long sumTxnAmt) {
		super();
		this.sumTxnAmt = sumTxnAmt;
	}

	public Long getSumTxnAmt() {
		return sumTxnAmt;
	}

	public void setSumTxnAmt(Long sumTxnAmt) {
		this.sumTxnAmt = sumTxnAmt;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sumTxnAmt == null) ? 0 : sumTxnAmt.hashCode());
        
        return result;
    }
	
    /**
     * Database table : tbl_agent_profit_withdraw_log
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", sumTxnAmt=").append(sumTxnAmt);
        sb.append("]");
        return sb.toString();
    }

}