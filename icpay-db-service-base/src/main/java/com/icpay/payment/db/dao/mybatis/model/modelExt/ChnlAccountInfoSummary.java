package com.icpay.payment.db.dao.mybatis.model.modelExt;

import java.io.Serializable;

/**
 * [Model class] 
 *
 * Database table : tbl_chnl_mchnt_account_info	
 * @author owen
 *
 * @mbg.generated
 */
public class ChnlAccountInfoSummary implements Serializable {
	
	private Long sumRealAvailableBalance;
	private Long sumAvailableBalance;
	private Long sumObligatedBalance;
	private Long sumFrozenBalance;
	private Long sumFrozenT1Balance;
	private Long sumDayTxnAmt;
	private Long sumDayWithdrawAmt;
	
	public ChnlAccountInfoSummary() {
		super();
	}
	
	public ChnlAccountInfoSummary(Long sumRealAvailableBalance, Long sumAvailableBalance, Long sumObligatedBalance, Long sumFrozenBalance, Long sumFrozenT1Balance, Long sumDayTxnAmt,
			Long sumDayWithdrawAmt) {
		super();
		this.sumRealAvailableBalance = sumRealAvailableBalance;
		this.sumAvailableBalance = sumAvailableBalance;
		this.sumObligatedBalance = sumObligatedBalance;
		this.sumFrozenBalance = sumFrozenBalance;
		this.sumFrozenT1Balance = sumFrozenT1Balance;
		this.sumDayTxnAmt = sumDayTxnAmt;
		this.sumDayWithdrawAmt = sumDayWithdrawAmt;
	}

	public Long getSumAvailableBalance() {
		return sumAvailableBalance;
	}

	public void setSumAvailableBalance(Long sumAvailableBalance) {
		this.sumAvailableBalance = sumAvailableBalance;
	}
	
	public Long getSumRealAvailableBalance() {
		return sumRealAvailableBalance;
	}

	public void setSumRealAvailableBalance(Long sumRealAvailableBalance) {
		this.sumRealAvailableBalance = sumRealAvailableBalance;
	}

	public Long getSumObligatedBalance() {
		return sumObligatedBalance;
	}

	public void setSumObligatedBalance(Long sumObligatedBalance) {
		this.sumObligatedBalance = sumObligatedBalance;
	}

	public Long getSumFrozenBalance() {
		return sumFrozenBalance;
	}

	public void setSumFrozenBalance(Long sumFrozenBalance) {
		this.sumFrozenBalance = sumFrozenBalance;
	}

	public Long getSumFrozenT1Balance() {
		return sumFrozenT1Balance;
	}

	public void setSumFrozenT1Balance(Long sumFrozenT1Balance) {
		this.sumFrozenT1Balance = sumFrozenT1Balance;
	}

	public Long getSumDayTxnAmt() {
		return sumDayTxnAmt;
	}

	public void setSumDayTxnAmt(Long sumDayTxnAmt) {
		this.sumDayTxnAmt = sumDayTxnAmt;
	}

	public Long getSumDayWithdrawAmt() {
		return sumDayWithdrawAmt;
	}

	public void setSumDayWithdrawAmt(Long sumDayWithdrawAmt) {
		this.sumDayWithdrawAmt = sumDayWithdrawAmt;
	}
	
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sumRealAvailableBalance == null) ? 0 : sumRealAvailableBalance.hashCode());
        result = prime * result + ((sumAvailableBalance == null) ? 0 : sumAvailableBalance.hashCode());
        result = prime * result + ((sumObligatedBalance == null) ? 0 : sumObligatedBalance.hashCode());
        result = prime * result + ((sumFrozenBalance == null) ? 0 : sumFrozenBalance.hashCode());
        result = prime * result + ((sumFrozenT1Balance == null) ? 0 : sumFrozenT1Balance.hashCode());
        result = prime * result + ((sumDayTxnAmt == null) ? 0 : sumDayTxnAmt.hashCode());
        result = prime * result + ((sumDayWithdrawAmt == null) ? 0 : sumDayWithdrawAmt.hashCode());        
        
        return result;
    }
	
    /**
     * Database table : tbl_chnl_mchnt_account_info
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", sumRealAvailableBalance=").append(sumRealAvailableBalance);
        sb.append(", sumAvailableBalance=").append(sumAvailableBalance);
        sb.append(", sumObligatedBalance=").append(sumObligatedBalance);
        sb.append(", sumFrozenBalance=").append(sumFrozenBalance);
        sb.append(", sumFrozenT1Balance=").append(sumFrozenT1Balance);
        sb.append(", sumDayTxnAmt=").append(sumDayTxnAmt);
        sb.append(", sumDayWithdrawAmt=").append(sumDayWithdrawAmt);
        sb.append("]");
        return sb.toString();
    }
    
}