package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_chnl_mchnt_account_flow01

 *
 * @mbg.generated
 */
public class ChnlMchntAccountFlow implements Serializable {
    /**
     * 记录序号
     * Database column : tbl_chnl_mchnt_account_flow01.seq_id
     *
     * @mbg.generated
     */
    private Long seqId;

    /**
     * 渠道编号
     * Database column : tbl_chnl_mchnt_account_flow01.chnl_id
     *
     * @mbg.generated
     */
    private String chnlId;

    /**
     * 商户号
     * Database column : tbl_chnl_mchnt_account_flow01.mchnt_cd
     *
     * @mbg.generated
     */
    private String mchntCd;

    /**
     * 操作金额(未扣除手续费)
     * Database column : tbl_chnl_mchnt_account_flow01.trans_at
     *
     * @mbg.generated
     */
    private Long transAt;

    /**
     * 操作手续费
     * Database column : tbl_chnl_mchnt_account_flow01.trans_fee
     *
     * @mbg.generated
     */
    private Long transFee;

    /**
     * 币别，默认156(人民币)
     * Database column : tbl_chnl_mchnt_account_flow01.curr_cd
     *
     * @mbg.generated
     */
    private String currCd;

    /**
     * 金額表示單位 = 0.01
     * Database column : tbl_chnl_mchnt_account_flow01.unit
     *
     * @mbg.generated
     */
    private BigDecimal unit;

    /**
     * 帐户操作类型; 请参考：Constant.OPERTYPE
     * Database column : tbl_chnl_mchnt_account_flow01.operate_tp
     *
     * @mbg.generated
     */
    private String operateTp;

    /**
     * 可代付馀额
     * Database column : tbl_chnl_mchnt_account_flow01.available_balance
     *
     * @mbg.generated
     */
    private Long availableBalance;

    /**
     * 不可代付馀额(T1馀额)
     * Database column : tbl_chnl_mchnt_account_flow01.frozen_t1_balance
     *
     * @mbg.generated
     */
    private Long frozenT1Balance;

    /**
     * 冻结款
     * Database column : tbl_chnl_mchnt_account_flow01.frozen_balance
     *
     * @mbg.generated
     */
    private Long frozenBalance;

    /**
     * 异动值：可代付馀额
     * Database column : tbl_chnl_mchnt_account_flow01.available_balance_delta
     *
     * @mbg.generated
     */
    private Long availableBalanceDelta;

    /**
     * 异动值：不可代付馀额(T1馀额)
     * Database column : tbl_chnl_mchnt_account_flow01.frozen_t1_balance_delta
     *
     * @mbg.generated
     */
    private Long frozenT1BalanceDelta;

    /**
     * 异动值：冻结款
     * Database column : tbl_chnl_mchnt_account_flow01.frozen_balance_delta
     *
     * @mbg.generated
     */
    private Long frozenBalanceDelta;

    /**
     * 交易编号
     * Database column : tbl_chnl_mchnt_account_flow01.trans_seq_id
     *
     * @mbg.generated
     */
    private String transSeqId;

    /**
     * 订单/交易日期,格式 yyyyMMdd (客户端时间)
     * Database column : tbl_chnl_mchnt_account_flow01.trans_dt
     *
     * @mbg.generated
     */
    private String transDt;

    /**
     * 订单/交易时间,格式 HHmmss (客户端时间)
     * Database column : tbl_chnl_mchnt_account_flow01.trans_tm
     *
     * @mbg.generated
     */
    private String transTm;

    /**
     * 备注
     * Database column : tbl_chnl_mchnt_account_flow01.note
     *
     * @mbg.generated
     */
    private String note;

    /**
     * 关联ID，用来关联相关(如同一交易)的流水
     * Database column : tbl_chnl_mchnt_account_flow01.relation_id
     *
     * @mbg.generated
     */
    private String relationId;

    /**
     * 标签(扩展用)
     * Database column : tbl_chnl_mchnt_account_flow01.tags
     *
     * @mbg.generated
     */
    private String tags;

    /**
     * 操作员
     * Database column : tbl_chnl_mchnt_account_flow01.last_oper_id
     *
     * @mbg.generated
     */
    private String lastOperId;

    /**
     * Database column : tbl_chnl_mchnt_account_flow01.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_chnl_mchnt_account_flow01.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    public ChnlMchntAccountFlow(Long seqId, String chnlId, String mchntCd, Long transAt, Long transFee, String currCd, BigDecimal unit, String operateTp, Long availableBalance, Long frozenT1Balance, Long frozenBalance, Long availableBalanceDelta, Long frozenT1BalanceDelta, Long frozenBalanceDelta, String transSeqId, String transDt, String transTm, String note, String relationId, String tags, String lastOperId, Date recCrtTs, Date recUpdTs) {
        this.seqId = seqId;
        this.chnlId = chnlId;
        this.mchntCd = mchntCd;
        this.transAt = transAt;
        this.transFee = transFee;
        this.currCd = currCd;
        this.unit = unit;
        this.operateTp = operateTp;
        this.availableBalance = availableBalance;
        this.frozenT1Balance = frozenT1Balance;
        this.frozenBalance = frozenBalance;
        this.availableBalanceDelta = availableBalanceDelta;
        this.frozenT1BalanceDelta = frozenT1BalanceDelta;
        this.frozenBalanceDelta = frozenBalanceDelta;
        this.transSeqId = transSeqId;
        this.transDt = transDt;
        this.transTm = transTm;
        this.note = note;
        this.relationId = relationId;
        this.tags = tags;
        this.lastOperId = lastOperId;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    public ChnlMchntAccountFlow() {
        super();
    }

    /**
     * 记录序号
     * @return seq_id 记录序号
     *
     * @mbg.generated
     */
    public Long getSeqId() {
        return seqId;
    }

    /**
     * 记录序号
     * @param seqId 记录序号
     *
     * @mbg.generated
     */
    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    /**
     * 渠道编号
     * @return chnl_id 渠道编号
     *
     * @mbg.generated
     */
    public String getChnlId() {
        return chnlId;
    }

    /**
     * 渠道编号
     * @param chnlId 渠道编号
     *
     * @mbg.generated
     */
    public void setChnlId(String chnlId) {
        this.chnlId = chnlId;
    }

    /**
     * 商户号
     * @return mchnt_cd 商户号
     *
     * @mbg.generated
     */
    public String getMchntCd() {
        return mchntCd;
    }

    /**
     * 商户号
     * @param mchntCd 商户号
     *
     * @mbg.generated
     */
    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd;
    }

    /**
     * 操作金额(未扣除手续费)
     * @return trans_at 操作金额(未扣除手续费)
     *
     * @mbg.generated
     */
    public Long getTransAt() {
        return transAt;
    }

    /**
     * 操作金额(未扣除手续费)
     * @param transAt 操作金额(未扣除手续费)
     *
     * @mbg.generated
     */
    public void setTransAt(Long transAt) {
        this.transAt = transAt;
    }

    /**
     * 操作手续费
     * @return trans_fee 操作手续费
     *
     * @mbg.generated
     */
    public Long getTransFee() {
        return transFee;
    }

    /**
     * 操作手续费
     * @param transFee 操作手续费
     *
     * @mbg.generated
     */
    public void setTransFee(Long transFee) {
        this.transFee = transFee;
    }

    /**
     * 币别，默认156(人民币)
     * @return curr_cd 币别，默认156(人民币)
     *
     * @mbg.generated
     */
    public String getCurrCd() {
        return currCd;
    }

    /**
     * 币别，默认156(人民币)
     * @param currCd 币别，默认156(人民币)
     *
     * @mbg.generated
     */
    public void setCurrCd(String currCd) {
        this.currCd = currCd;
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
     * 异动值：可代付馀额
     * @return available_balance_delta 异动值：可代付馀额
     *
     * @mbg.generated
     */
    public Long getAvailableBalanceDelta() {
        return availableBalanceDelta;
    }

    /**
     * 异动值：可代付馀额
     * @param availableBalanceDelta 异动值：可代付馀额
     *
     * @mbg.generated
     */
    public void setAvailableBalanceDelta(Long availableBalanceDelta) {
        this.availableBalanceDelta = availableBalanceDelta;
    }

    /**
     * 异动值：不可代付馀额(T1馀额)
     * @return frozen_t1_balance_delta 异动值：不可代付馀额(T1馀额)
     *
     * @mbg.generated
     */
    public Long getFrozenT1BalanceDelta() {
        return frozenT1BalanceDelta;
    }

    /**
     * 异动值：不可代付馀额(T1馀额)
     * @param frozenT1BalanceDelta 异动值：不可代付馀额(T1馀额)
     *
     * @mbg.generated
     */
    public void setFrozenT1BalanceDelta(Long frozenT1BalanceDelta) {
        this.frozenT1BalanceDelta = frozenT1BalanceDelta;
    }

    /**
     * 异动值：冻结款
     * @return frozen_balance_delta 异动值：冻结款
     *
     * @mbg.generated
     */
    public Long getFrozenBalanceDelta() {
        return frozenBalanceDelta;
    }

    /**
     * 异动值：冻结款
     * @param frozenBalanceDelta 异动值：冻结款
     *
     * @mbg.generated
     */
    public void setFrozenBalanceDelta(Long frozenBalanceDelta) {
        this.frozenBalanceDelta = frozenBalanceDelta;
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
     * 订单/交易日期,格式 yyyyMMdd (客户端时间)
     * @return trans_dt 订单/交易日期,格式 yyyyMMdd (客户端时间)
     *
     * @mbg.generated
     */
    public String getTransDt() {
        return transDt;
    }

    /**
     * 订单/交易日期,格式 yyyyMMdd (客户端时间)
     * @param transDt 订单/交易日期,格式 yyyyMMdd (客户端时间)
     *
     * @mbg.generated
     */
    public void setTransDt(String transDt) {
        this.transDt = transDt;
    }

    /**
     * 订单/交易时间,格式 HHmmss (客户端时间)
     * @return trans_tm 订单/交易时间,格式 HHmmss (客户端时间)
     *
     * @mbg.generated
     */
    public String getTransTm() {
        return transTm;
    }

    /**
     * 订单/交易时间,格式 HHmmss (客户端时间)
     * @param transTm 订单/交易时间,格式 HHmmss (客户端时间)
     *
     * @mbg.generated
     */
    public void setTransTm(String transTm) {
        this.transTm = transTm;
    }

    /**
     * 备注
     * @return note 备注
     *
     * @mbg.generated
     */
    public String getNote() {
        return note;
    }

    /**
     * 备注
     * @param note 备注
     *
     * @mbg.generated
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 关联ID，用来关联相关(如同一交易)的流水
     * @return relation_id 关联ID，用来关联相关(如同一交易)的流水
     *
     * @mbg.generated
     */
    public String getRelationId() {
        return relationId;
    }

    /**
     * 关联ID，用来关联相关(如同一交易)的流水
     * @param relationId 关联ID，用来关联相关(如同一交易)的流水
     *
     * @mbg.generated
     */
    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    /**
     * 标签(扩展用)
     * @return tags 标签(扩展用)
     *
     * @mbg.generated
     */
    public String getTags() {
        return tags;
    }

    /**
     * 标签(扩展用)
     * @param tags 标签(扩展用)
     *
     * @mbg.generated
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * 操作员
     * @return last_oper_id 操作员
     *
     * @mbg.generated
     */
    public String getLastOperId() {
        return lastOperId;
    }

    /**
     * 操作员
     * @param lastOperId 操作员
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
     * Database table : tbl_chnl_mchnt_account_flow01
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
        ChnlMchntAccountFlow other = (ChnlMchntAccountFlow) that;
        return (this.getSeqId() == null ? other.getSeqId() == null : this.getSeqId().equals(other.getSeqId()))
            && (this.getChnlId() == null ? other.getChnlId() == null : this.getChnlId().equals(other.getChnlId()))
            && (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()))
            && (this.getTransAt() == null ? other.getTransAt() == null : this.getTransAt().equals(other.getTransAt()))
            && (this.getTransFee() == null ? other.getTransFee() == null : this.getTransFee().equals(other.getTransFee()))
            && (this.getCurrCd() == null ? other.getCurrCd() == null : this.getCurrCd().equals(other.getCurrCd()))
            && (this.getUnit() == null ? other.getUnit() == null : this.getUnit().equals(other.getUnit()))
            && (this.getOperateTp() == null ? other.getOperateTp() == null : this.getOperateTp().equals(other.getOperateTp()))
            && (this.getAvailableBalance() == null ? other.getAvailableBalance() == null : this.getAvailableBalance().equals(other.getAvailableBalance()))
            && (this.getFrozenT1Balance() == null ? other.getFrozenT1Balance() == null : this.getFrozenT1Balance().equals(other.getFrozenT1Balance()))
            && (this.getFrozenBalance() == null ? other.getFrozenBalance() == null : this.getFrozenBalance().equals(other.getFrozenBalance()))
            && (this.getAvailableBalanceDelta() == null ? other.getAvailableBalanceDelta() == null : this.getAvailableBalanceDelta().equals(other.getAvailableBalanceDelta()))
            && (this.getFrozenT1BalanceDelta() == null ? other.getFrozenT1BalanceDelta() == null : this.getFrozenT1BalanceDelta().equals(other.getFrozenT1BalanceDelta()))
            && (this.getFrozenBalanceDelta() == null ? other.getFrozenBalanceDelta() == null : this.getFrozenBalanceDelta().equals(other.getFrozenBalanceDelta()))
            && (this.getTransSeqId() == null ? other.getTransSeqId() == null : this.getTransSeqId().equals(other.getTransSeqId()))
            && (this.getTransDt() == null ? other.getTransDt() == null : this.getTransDt().equals(other.getTransDt()))
            && (this.getTransTm() == null ? other.getTransTm() == null : this.getTransTm().equals(other.getTransTm()))
            && (this.getNote() == null ? other.getNote() == null : this.getNote().equals(other.getNote()))
            && (this.getRelationId() == null ? other.getRelationId() == null : this.getRelationId().equals(other.getRelationId()))
            && (this.getTags() == null ? other.getTags() == null : this.getTags().equals(other.getTags()))
            && (this.getLastOperId() == null ? other.getLastOperId() == null : this.getLastOperId().equals(other.getLastOperId()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSeqId() == null) ? 0 : getSeqId().hashCode());
        result = prime * result + ((getChnlId() == null) ? 0 : getChnlId().hashCode());
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        result = prime * result + ((getTransAt() == null) ? 0 : getTransAt().hashCode());
        result = prime * result + ((getTransFee() == null) ? 0 : getTransFee().hashCode());
        result = prime * result + ((getCurrCd() == null) ? 0 : getCurrCd().hashCode());
        result = prime * result + ((getUnit() == null) ? 0 : getUnit().hashCode());
        result = prime * result + ((getOperateTp() == null) ? 0 : getOperateTp().hashCode());
        result = prime * result + ((getAvailableBalance() == null) ? 0 : getAvailableBalance().hashCode());
        result = prime * result + ((getFrozenT1Balance() == null) ? 0 : getFrozenT1Balance().hashCode());
        result = prime * result + ((getFrozenBalance() == null) ? 0 : getFrozenBalance().hashCode());
        result = prime * result + ((getAvailableBalanceDelta() == null) ? 0 : getAvailableBalanceDelta().hashCode());
        result = prime * result + ((getFrozenT1BalanceDelta() == null) ? 0 : getFrozenT1BalanceDelta().hashCode());
        result = prime * result + ((getFrozenBalanceDelta() == null) ? 0 : getFrozenBalanceDelta().hashCode());
        result = prime * result + ((getTransSeqId() == null) ? 0 : getTransSeqId().hashCode());
        result = prime * result + ((getTransDt() == null) ? 0 : getTransDt().hashCode());
        result = prime * result + ((getTransTm() == null) ? 0 : getTransTm().hashCode());
        result = prime * result + ((getNote() == null) ? 0 : getNote().hashCode());
        result = prime * result + ((getRelationId() == null) ? 0 : getRelationId().hashCode());
        result = prime * result + ((getTags() == null) ? 0 : getTags().hashCode());
        result = prime * result + ((getLastOperId() == null) ? 0 : getLastOperId().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
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
        sb.append(", chnlId=").append(chnlId);
        sb.append(", mchntCd=").append(mchntCd);
        sb.append(", transAt=").append(transAt);
        sb.append(", transFee=").append(transFee);
        sb.append(", currCd=").append(currCd);
        sb.append(", unit=").append(unit);
        sb.append(", operateTp=").append(operateTp);
        sb.append(", availableBalance=").append(availableBalance);
        sb.append(", frozenT1Balance=").append(frozenT1Balance);
        sb.append(", frozenBalance=").append(frozenBalance);
        sb.append(", availableBalanceDelta=").append(availableBalanceDelta);
        sb.append(", frozenT1BalanceDelta=").append(frozenT1BalanceDelta);
        sb.append(", frozenBalanceDelta=").append(frozenBalanceDelta);
        sb.append(", transSeqId=").append(transSeqId);
        sb.append(", transDt=").append(transDt);
        sb.append(", transTm=").append(transTm);
        sb.append(", note=").append(note);
        sb.append(", relationId=").append(relationId);
        sb.append(", tags=").append(tags);
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
    public void cloneFrom(ChnlMchntAccountFlow source) {
        this.seqId=source.seqId;
        this.chnlId=source.chnlId;
        this.mchntCd=source.mchntCd;
        this.transAt=source.transAt;
        this.transFee=source.transFee;
        this.currCd=source.currCd;
        this.unit=source.unit;
        this.operateTp=source.operateTp;
        this.availableBalance=source.availableBalance;
        this.frozenT1Balance=source.frozenT1Balance;
        this.frozenBalance=source.frozenBalance;
        this.availableBalanceDelta=source.availableBalanceDelta;
        this.frozenT1BalanceDelta=source.frozenT1BalanceDelta;
        this.frozenBalanceDelta=source.frozenBalanceDelta;
        this.transSeqId=source.transSeqId;
        this.transDt=source.transDt;
        this.transTm=source.transTm;
        this.note=source.note;
        this.relationId=source.relationId;
        this.tags=source.tags;
        this.lastOperId=source.lastOperId;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
    }
}