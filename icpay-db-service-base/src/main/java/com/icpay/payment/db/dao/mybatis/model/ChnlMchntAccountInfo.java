package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_chnl_mchnt_account_info

 *
 * @mbg.generated
 */
public class ChnlMchntAccountInfo extends ChnlMchntAccountInfoKey implements Serializable {
    /**
     * 金額表示單位 = 0.01
     * Database column : tbl_chnl_mchnt_account_info.unit
     *
     * @mbg.generated
     */
    private BigDecimal unit;

    /**
     * 可代付馀额
     * Database column : tbl_chnl_mchnt_account_info.available_balance
     *
     * @mbg.generated
     */
    private Long availableBalance;

    /**
     * 保留余额
     * Database column : tbl_chnl_mchnt_account_info.obligated_balance
     *
     * @mbg.generated
     */
    private Long obligatedBalance;

    /**
     * 不可代付馀额(T1馀额)
     * Database column : tbl_chnl_mchnt_account_info.frozen_t1_balance
     *
     * @mbg.generated
     */
    private Long frozenT1Balance;

    /**
     * 冻结款
     * Database column : tbl_chnl_mchnt_account_info.frozen_balance
     *
     * @mbg.generated
     */
    private Long frozenBalance;

    /**
     * 下次T1应结转金额(记录值)
     * Database column : tbl_chnl_mchnt_account_info.next_t1_trans_amount
     *
     * @mbg.generated
     */
    private Long nextT1TransAmount;

    /**
     * 状态：0=无效, 1=有效,2=锁定
     * Database column : tbl_chnl_mchnt_account_info.state
     *
     * @mbg.generated
     */
    private String state;

    /**
     * 当日累计交易额(收入)
     * Database column : tbl_chnl_mchnt_account_info.dayTxnAmt
     *
     * @mbg.generated
     */
    private Long daytxnamt;

    /**
     * 当日累计交易手续费
     * Database column : tbl_chnl_mchnt_account_info.dayTxnFee
     *
     * @mbg.generated
     */
    private Long daytxnfee;

    /**
     * 当日累计交易笔数
     * Database column : tbl_chnl_mchnt_account_info.dayTxnCount
     *
     * @mbg.generated
     */
    private Integer daytxncount;

    /**
     * 当日累计提现金额(支出)
     * Database column : tbl_chnl_mchnt_account_info.dayWithdrawAmt
     *
     * @mbg.generated
     */
    private Long daywithdrawamt;

    /**
     * 当日累计提现手续费
     * Database column : tbl_chnl_mchnt_account_info.dayWithdrawFee
     *
     * @mbg.generated
     */
    private Long daywithdrawfee;

    /**
     * 当日累计提现笔数
     * Database column : tbl_chnl_mchnt_account_info.dayWithdrawCount
     *
     * @mbg.generated
     */
    private Integer daywithdrawcount;

    /**
     * 扩展标签
     * Database column : tbl_chnl_mchnt_account_info.tags
     *
     * @mbg.generated
     */
    private String tags;

    /**
     * Database column : tbl_chnl_mchnt_account_info.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_chnl_mchnt_account_info.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_chnl_mchnt_account_info
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_chnl_mchnt_account_info
     *
     * @mbg.generated
     */
    public ChnlMchntAccountInfo(String chnlId, String mchntCd, String currCd, BigDecimal unit, Long availableBalance, Long obligatedBalance, Long frozenT1Balance, Long frozenBalance, Long nextT1TransAmount, String state, Long daytxnamt, Long daytxnfee, Integer daytxncount, Long daywithdrawamt, Long daywithdrawfee, Integer daywithdrawcount, String tags, Date recCrtTs, Date recUpdTs) {
        super(chnlId, mchntCd, currCd);
        this.unit = unit;
        this.availableBalance = availableBalance;
        this.obligatedBalance = obligatedBalance;
        this.frozenT1Balance = frozenT1Balance;
        this.frozenBalance = frozenBalance;
        this.nextT1TransAmount = nextT1TransAmount;
        this.state = state;
        this.daytxnamt = daytxnamt;
        this.daytxnfee = daytxnfee;
        this.daytxncount = daytxncount;
        this.daywithdrawamt = daywithdrawamt;
        this.daywithdrawfee = daywithdrawfee;
        this.daywithdrawcount = daywithdrawcount;
        this.tags = tags;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_chnl_mchnt_account_info
     *
     * @mbg.generated
     */
    public ChnlMchntAccountInfo() {
        super();
    }

    /**
     * 金額表示單位 = 0.01
     * @return unit 金額表示單位 = 0.01
     *
     * @mbg.generated
     */
    public BigDecimal getUnit() {
        return unit;
    }

    /**
     * 金額表示單位 = 0.01
     * @param unit 金額表示單位 = 0.01
     *
     * @mbg.generated
     */
    public void setUnit(BigDecimal unit) {
        this.unit = unit;
    }

    /**
     * 可代付馀额
     * @return available_balance 可代付馀额
     *
     * @mbg.generated
     */
    public Long getAvailableBalance() {
        return availableBalance;
    }

    /**
     * 可代付馀额
     * @param availableBalance 可代付馀额
     *
     * @mbg.generated
     */
    public void setAvailableBalance(Long availableBalance) {
        this.availableBalance = availableBalance;
    }

    /**
     * 保留余额
     * @return obligated_balance 保留余额
     *
     * @mbg.generated
     */
    public Long getObligatedBalance() {
        return obligatedBalance;
    }

    /**
     * 保留余额
     * @param obligatedBalance 保留余额
     *
     * @mbg.generated
     */
    public void setObligatedBalance(Long obligatedBalance) {
        this.obligatedBalance = obligatedBalance;
    }

    /**
     * 不可代付馀额(T1馀额)
     * @return frozen_t1_balance 不可代付馀额(T1馀额)
     *
     * @mbg.generated
     */
    public Long getFrozenT1Balance() {
        return frozenT1Balance;
    }

    /**
     * 不可代付馀额(T1馀额)
     * @param frozenT1Balance 不可代付馀额(T1馀额)
     *
     * @mbg.generated
     */
    public void setFrozenT1Balance(Long frozenT1Balance) {
        this.frozenT1Balance = frozenT1Balance;
    }

    /**
     * 冻结款
     * @return frozen_balance 冻结款
     *
     * @mbg.generated
     */
    public Long getFrozenBalance() {
        return frozenBalance;
    }

    /**
     * 冻结款
     * @param frozenBalance 冻结款
     *
     * @mbg.generated
     */
    public void setFrozenBalance(Long frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    /**
     * 下次T1应结转金额(记录值)
     * @return next_t1_trans_amount 下次T1应结转金额(记录值)
     *
     * @mbg.generated
     */
    public Long getNextT1TransAmount() {
        return nextT1TransAmount;
    }

    /**
     * 下次T1应结转金额(记录值)
     * @param nextT1TransAmount 下次T1应结转金额(记录值)
     *
     * @mbg.generated
     */
    public void setNextT1TransAmount(Long nextT1TransAmount) {
        this.nextT1TransAmount = nextT1TransAmount;
    }

    /**
     * 状态：0=无效, 1=有效,2=锁定
     * @return state 状态：0=无效, 1=有效,2=锁定
     *
     * @mbg.generated
     */
    public String getState() {
        return state;
    }

    /**
     * 状态：0=无效, 1=有效,2=锁定
     * @param state 状态：0=无效, 1=有效,2=锁定
     *
     * @mbg.generated
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 当日累计交易额(收入)
     * @return dayTxnAmt 当日累计交易额(收入)
     *
     * @mbg.generated
     */
    public Long getDaytxnamt() {
        return daytxnamt;
    }

    /**
     * 当日累计交易额(收入)
     * @param daytxnamt 当日累计交易额(收入)
     *
     * @mbg.generated
     */
    public void setDaytxnamt(Long daytxnamt) {
        this.daytxnamt = daytxnamt;
    }

    /**
     * 当日累计交易手续费
     * @return dayTxnFee 当日累计交易手续费
     *
     * @mbg.generated
     */
    public Long getDaytxnfee() {
        return daytxnfee;
    }

    /**
     * 当日累计交易手续费
     * @param daytxnfee 当日累计交易手续费
     *
     * @mbg.generated
     */
    public void setDaytxnfee(Long daytxnfee) {
        this.daytxnfee = daytxnfee;
    }

    /**
     * 当日累计交易笔数
     * @return dayTxnCount 当日累计交易笔数
     *
     * @mbg.generated
     */
    public Integer getDaytxncount() {
        return daytxncount;
    }

    /**
     * 当日累计交易笔数
     * @param daytxncount 当日累计交易笔数
     *
     * @mbg.generated
     */
    public void setDaytxncount(Integer daytxncount) {
        this.daytxncount = daytxncount;
    }

    /**
     * 当日累计提现金额(支出)
     * @return dayWithdrawAmt 当日累计提现金额(支出)
     *
     * @mbg.generated
     */
    public Long getDaywithdrawamt() {
        return daywithdrawamt;
    }

    /**
     * 当日累计提现金额(支出)
     * @param daywithdrawamt 当日累计提现金额(支出)
     *
     * @mbg.generated
     */
    public void setDaywithdrawamt(Long daywithdrawamt) {
        this.daywithdrawamt = daywithdrawamt;
    }

    /**
     * 当日累计提现手续费
     * @return dayWithdrawFee 当日累计提现手续费
     *
     * @mbg.generated
     */
    public Long getDaywithdrawfee() {
        return daywithdrawfee;
    }

    /**
     * 当日累计提现手续费
     * @param daywithdrawfee 当日累计提现手续费
     *
     * @mbg.generated
     */
    public void setDaywithdrawfee(Long daywithdrawfee) {
        this.daywithdrawfee = daywithdrawfee;
    }

    /**
     * 当日累计提现笔数
     * @return dayWithdrawCount 当日累计提现笔数
     *
     * @mbg.generated
     */
    public Integer getDaywithdrawcount() {
        return daywithdrawcount;
    }

    /**
     * 当日累计提现笔数
     * @param daywithdrawcount 当日累计提现笔数
     *
     * @mbg.generated
     */
    public void setDaywithdrawcount(Integer daywithdrawcount) {
        this.daywithdrawcount = daywithdrawcount;
    }

    /**
     * 扩展标签
     * @return tags 扩展标签
     *
     * @mbg.generated
     */
    public String getTags() {
        return tags;
    }

    /**
     * 扩展标签
     * @param tags 扩展标签
     *
     * @mbg.generated
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * @return rec_crt_ts 
     *
     * @mbg.generated
     */
    public Date getRecCrtTs() {
        return recCrtTs;
    }

    /**
     * @param recCrtTs 
     *
     * @mbg.generated
     */
    public void setRecCrtTs(Date recCrtTs) {
        this.recCrtTs = recCrtTs;
    }

    /**
     * @return rec_upd_ts 
     *
     * @mbg.generated
     */
    public Date getRecUpdTs() {
        return recUpdTs;
    }

    /**
     * @param recUpdTs 
     *
     * @mbg.generated
     */
    public void setRecUpdTs(Date recUpdTs) {
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_chnl_mchnt_account_info
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ChnlMchntAccountInfo other = (ChnlMchntAccountInfo) that;
        return (this.getChnlId() == null ? other.getChnlId() == null : this.getChnlId().equals(other.getChnlId()))
            && (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()))
            && (this.getCurrCd() == null ? other.getCurrCd() == null : this.getCurrCd().equals(other.getCurrCd()))
            && (this.getUnit() == null ? other.getUnit() == null : this.getUnit().equals(other.getUnit()))
            && (this.getAvailableBalance() == null ? other.getAvailableBalance() == null : this.getAvailableBalance().equals(other.getAvailableBalance()))
            && (this.getObligatedBalance() == null ? other.getObligatedBalance() == null : this.getObligatedBalance().equals(other.getObligatedBalance()))
            && (this.getFrozenT1Balance() == null ? other.getFrozenT1Balance() == null : this.getFrozenT1Balance().equals(other.getFrozenT1Balance()))
            && (this.getFrozenBalance() == null ? other.getFrozenBalance() == null : this.getFrozenBalance().equals(other.getFrozenBalance()))
            && (this.getNextT1TransAmount() == null ? other.getNextT1TransAmount() == null : this.getNextT1TransAmount().equals(other.getNextT1TransAmount()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getDaytxnamt() == null ? other.getDaytxnamt() == null : this.getDaytxnamt().equals(other.getDaytxnamt()))
            && (this.getDaytxnfee() == null ? other.getDaytxnfee() == null : this.getDaytxnfee().equals(other.getDaytxnfee()))
            && (this.getDaytxncount() == null ? other.getDaytxncount() == null : this.getDaytxncount().equals(other.getDaytxncount()))
            && (this.getDaywithdrawamt() == null ? other.getDaywithdrawamt() == null : this.getDaywithdrawamt().equals(other.getDaywithdrawamt()))
            && (this.getDaywithdrawfee() == null ? other.getDaywithdrawfee() == null : this.getDaywithdrawfee().equals(other.getDaywithdrawfee()))
            && (this.getDaywithdrawcount() == null ? other.getDaywithdrawcount() == null : this.getDaywithdrawcount().equals(other.getDaywithdrawcount()))
            && (this.getTags() == null ? other.getTags() == null : this.getTags().equals(other.getTags()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_chnl_mchnt_account_info
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getChnlId() == null) ? 0 : getChnlId().hashCode());
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        result = prime * result + ((getCurrCd() == null) ? 0 : getCurrCd().hashCode());
        result = prime * result + ((getUnit() == null) ? 0 : getUnit().hashCode());
        result = prime * result + ((getAvailableBalance() == null) ? 0 : getAvailableBalance().hashCode());
        result = prime * result + ((getObligatedBalance() == null) ? 0 : getObligatedBalance().hashCode());
        result = prime * result + ((getFrozenT1Balance() == null) ? 0 : getFrozenT1Balance().hashCode());
        result = prime * result + ((getFrozenBalance() == null) ? 0 : getFrozenBalance().hashCode());
        result = prime * result + ((getNextT1TransAmount() == null) ? 0 : getNextT1TransAmount().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getDaytxnamt() == null) ? 0 : getDaytxnamt().hashCode());
        result = prime * result + ((getDaytxnfee() == null) ? 0 : getDaytxnfee().hashCode());
        result = prime * result + ((getDaytxncount() == null) ? 0 : getDaytxncount().hashCode());
        result = prime * result + ((getDaywithdrawamt() == null) ? 0 : getDaywithdrawamt().hashCode());
        result = prime * result + ((getDaywithdrawfee() == null) ? 0 : getDaywithdrawfee().hashCode());
        result = prime * result + ((getDaywithdrawcount() == null) ? 0 : getDaywithdrawcount().hashCode());
        result = prime * result + ((getTags() == null) ? 0 : getTags().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
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
        sb.append(getSuperToString());
        sb.append(", unit=").append(unit);
        sb.append(", availableBalance=").append(availableBalance);
        sb.append(", obligatedBalance=").append(obligatedBalance);
        sb.append(", frozenT1Balance=").append(frozenT1Balance);
        sb.append(", frozenBalance=").append(frozenBalance);
        sb.append(", nextT1TransAmount=").append(nextT1TransAmount);
        sb.append(", state=").append(state);
        sb.append(", daytxnamt=").append(daytxnamt);
        sb.append(", daytxnfee=").append(daytxnfee);
        sb.append(", daytxncount=").append(daytxncount);
        sb.append(", daywithdrawamt=").append(daywithdrawamt);
        sb.append(", daywithdrawfee=").append(daywithdrawfee);
        sb.append(", daywithdrawcount=").append(daywithdrawcount);
        sb.append(", tags=").append(tags);
        sb.append(", recCrtTs=").append(recCrtTs);
        sb.append(", recUpdTs=").append(recUpdTs);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Database table : tbl_chnl_mchnt_account_info
     *
     * @mbg.generated
     */
    private String getSuperToString() {
        String s = super.toString();
        String superCls = super.getClass().getSimpleName();
        if (!(s.contains("[Hash=") && s.contains(superCls))) return "";
        int end=-1;
        int start = s.indexOf("[Hash=");
        if (start>=0) {
            	start = s.indexOf(", ", start);
            	if (start>=0) {
                		end = s.lastIndexOf(']');
                		if (end>0) 
                			return ", "+s.substring(start+2, end)+"";
                	}
            }
            return "";
        }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(ChnlMchntAccountInfo source) {
        super.cloneFrom(source);
        this.unit=source.unit;
        this.availableBalance=source.availableBalance;
        this.obligatedBalance=source.obligatedBalance;
        this.frozenT1Balance=source.frozenT1Balance;
        this.frozenBalance=source.frozenBalance;
        this.nextT1TransAmount=source.nextT1TransAmount;
        this.state=source.state;
        this.daytxnamt=source.daytxnamt;
        this.daytxnfee=source.daytxnfee;
        this.daytxncount=source.daytxncount;
        this.daywithdrawamt=source.daywithdrawamt;
        this.daywithdrawfee=source.daywithdrawfee;
        this.daywithdrawcount=source.daywithdrawcount;
        this.tags=source.tags;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
    }
}