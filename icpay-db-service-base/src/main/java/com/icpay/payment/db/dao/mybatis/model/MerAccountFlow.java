package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_mer_account_flow

 *
 * @mbg.generated
 */
public class MerAccountFlow implements Serializable {
    /**
     * Database column : tbl_mer_account_flow.seq_id
     *
     * @mbg.generated
     */
    private Long seqId;

    /**
     * Database column : tbl_mer_account_flow.mchnt_cd
     *
     * @mbg.generated
     */
    private String mchntCd;

    /**
     * Database column : tbl_mer_account_flow.trans_at
     *
     * @mbg.generated
     */
    private Long transAt;

    /**
     * 帐户操作类型; 请参考：Constant.OPERTYPE
     * Database column : tbl_mer_account_flow.operate_tp
     *
     * @mbg.generated
     */
    private String operateTp;

    /**
     * 可代付馀额
     * Database column : tbl_mer_account_flow.available_balance
     *
     * @mbg.generated
     */
    private Long availableBalance;

    /**
     * 不可代付馀额(T1馀额)
     * Database column : tbl_mer_account_flow.frozen_t1_balance
     *
     * @mbg.generated
     */
    private Long frozenT1Balance;

    /**
     * 冻结款
     * Database column : tbl_mer_account_flow.frozen_balance
     *
     * @mbg.generated
     */
    private Long frozenBalance;

    /**
     * 交易编号
     * Database column : tbl_mer_account_flow.trans_seq_id
     *
     * @mbg.generated
     */
    private String transSeqId;

    /**
     * Database column : tbl_mer_account_flow.note
     *
     * @mbg.generated
     */
    private String note;

    /**
     * Database column : tbl_mer_account_flow.last_oper_id
     *
     * @mbg.generated
     */
    private String lastOperId;

    /**
     * Database column : tbl_mer_account_flow.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_mer_account_flow.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_mer_account_flow
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_mer_account_flow
     *
     * @mbg.generated
     */
    public MerAccountFlow(Long seqId, String mchntCd, Long transAt, String operateTp, Long availableBalance, Long frozenT1Balance, Long frozenBalance, String transSeqId, String note, String lastOperId, Date recCrtTs, Date recUpdTs) {
        this.seqId = seqId;
        this.mchntCd = mchntCd;
        this.transAt = transAt;
        this.operateTp = operateTp;
        this.availableBalance = availableBalance;
        this.frozenT1Balance = frozenT1Balance;
        this.frozenBalance = frozenBalance;
        this.transSeqId = transSeqId;
        this.note = note;
        this.lastOperId = lastOperId;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_mer_account_flow
     *
     * @mbg.generated
     */
    public MerAccountFlow() {
        super();
    }

    /**
     * @return seq_id 
     *
     * @mbg.generated
     */
    public Long getSeqId() {
        return seqId;
    }

    /**
     * @param seqId 
     *
     * @mbg.generated
     */
    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    /**
     * @return mchnt_cd 
     *
     * @mbg.generated
     */
    public String getMchntCd() {
        return mchntCd;
    }

    /**
     * @param mchntCd 
     *
     * @mbg.generated
     */
    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd;
    }

    /**
     * @return trans_at 
     *
     * @mbg.generated
     */
    public Long getTransAt() {
        return transAt;
    }

    /**
     * @param transAt 
     *
     * @mbg.generated
     */
    public void setTransAt(Long transAt) {
        this.transAt = transAt;
    }

    /**
     * 帐户操作类型; 请参考：Constant.OPERTYPE
     * @return operate_tp 帐户操作类型; 请参考：Constant.OPERTYPE
     *
     * @mbg.generated
     */
    public String getOperateTp() {
        return operateTp;
    }

    /**
     * 帐户操作类型; 请参考：Constant.OPERTYPE
     * @param operateTp 帐户操作类型; 请参考：Constant.OPERTYPE
     *
     * @mbg.generated
     */
    public void setOperateTp(String operateTp) {
        this.operateTp = operateTp;
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
     * 交易编号
     * @return trans_seq_id 交易编号
     *
     * @mbg.generated
     */
    public String getTransSeqId() {
        return transSeqId;
    }

    /**
     * 交易编号
     * @param transSeqId 交易编号
     *
     * @mbg.generated
     */
    public void setTransSeqId(String transSeqId) {
        this.transSeqId = transSeqId;
    }

    /**
     * @return note 
     *
     * @mbg.generated
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note 
     *
     * @mbg.generated
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return last_oper_id 
     *
     * @mbg.generated
     */
    public String getLastOperId() {
        return lastOperId;
    }

    /**
     * @param lastOperId 
     *
     * @mbg.generated
     */
    public void setLastOperId(String lastOperId) {
        this.lastOperId = lastOperId;
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
     * Database table : tbl_mer_account_flow
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
        MerAccountFlow other = (MerAccountFlow) that;
        return (this.getSeqId() == null ? other.getSeqId() == null : this.getSeqId().equals(other.getSeqId()))
            && (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()))
            && (this.getTransAt() == null ? other.getTransAt() == null : this.getTransAt().equals(other.getTransAt()))
            && (this.getOperateTp() == null ? other.getOperateTp() == null : this.getOperateTp().equals(other.getOperateTp()))
            && (this.getAvailableBalance() == null ? other.getAvailableBalance() == null : this.getAvailableBalance().equals(other.getAvailableBalance()))
            && (this.getFrozenT1Balance() == null ? other.getFrozenT1Balance() == null : this.getFrozenT1Balance().equals(other.getFrozenT1Balance()))
            && (this.getFrozenBalance() == null ? other.getFrozenBalance() == null : this.getFrozenBalance().equals(other.getFrozenBalance()))
            && (this.getTransSeqId() == null ? other.getTransSeqId() == null : this.getTransSeqId().equals(other.getTransSeqId()))
            && (this.getNote() == null ? other.getNote() == null : this.getNote().equals(other.getNote()))
            && (this.getLastOperId() == null ? other.getLastOperId() == null : this.getLastOperId().equals(other.getLastOperId()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_mer_account_flow
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSeqId() == null) ? 0 : getSeqId().hashCode());
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        result = prime * result + ((getTransAt() == null) ? 0 : getTransAt().hashCode());
        result = prime * result + ((getOperateTp() == null) ? 0 : getOperateTp().hashCode());
        result = prime * result + ((getAvailableBalance() == null) ? 0 : getAvailableBalance().hashCode());
        result = prime * result + ((getFrozenT1Balance() == null) ? 0 : getFrozenT1Balance().hashCode());
        result = prime * result + ((getFrozenBalance() == null) ? 0 : getFrozenBalance().hashCode());
        result = prime * result + ((getTransSeqId() == null) ? 0 : getTransSeqId().hashCode());
        result = prime * result + ((getNote() == null) ? 0 : getNote().hashCode());
        result = prime * result + ((getLastOperId() == null) ? 0 : getLastOperId().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_mer_account_flow
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", seqId=").append(seqId);
        sb.append(", mchntCd=").append(mchntCd);
        sb.append(", transAt=").append(transAt);
        sb.append(", operateTp=").append(operateTp);
        sb.append(", availableBalance=").append(availableBalance);
        sb.append(", frozenT1Balance=").append(frozenT1Balance);
        sb.append(", frozenBalance=").append(frozenBalance);
        sb.append(", transSeqId=").append(transSeqId);
        sb.append(", note=").append(note);
        sb.append(", lastOperId=").append(lastOperId);
        sb.append(", recCrtTs=").append(recCrtTs);
        sb.append(", recUpdTs=").append(recUpdTs);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(MerAccountFlow source) {
        this.seqId=source.seqId;
        this.mchntCd=source.mchntCd;
        this.transAt=source.transAt;
        this.operateTp=source.operateTp;
        this.availableBalance=source.availableBalance;
        this.frozenT1Balance=source.frozenT1Balance;
        this.frozenBalance=source.frozenBalance;
        this.transSeqId=source.transSeqId;
        this.note=source.note;
        this.lastOperId=source.lastOperId;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
    }
}