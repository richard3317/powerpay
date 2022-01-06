package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : view_txn_profit_settle
 * @author robin
 *
 * @mbg.generated
 */
public class TxnProfitSettle implements Serializable {
    /**
     * Database column : view_txn_profit_settle.int_trans_cd
     *
     * @mbg.generated
     */
    private String intTransCd;

    /**
     * Database column : view_txn_profit_settle.trans_chnl
     *
     * @mbg.generated
     */
    private String transChnl;

    /**
     * Database column : view_txn_profit_settle.chnl_mchnt_cd
     *
     * @mbg.generated
     */
    private String chnlMchntCd;

    /**
     * Database column : view_txn_profit_settle.mchnt_cd
     *
     * @mbg.generated
     */
    private String mchntCd;

    /**
     * Database column : view_txn_profit_settle.sum_amt
     *
     * @mbg.generated
     */
    private BigDecimal sumAmt;

    /**
     * Database column : view_txn_profit_settle.sum_trans_fee
     *
     * @mbg.generated
     */
    private BigDecimal sumTransFee;

    /**
     * Database column : view_txn_profit_settle.sum_trans_fee_chnl
     *
     * @mbg.generated
     */
    private BigDecimal sumTransFeeChnl;

    /**
     * Database column : view_txn_profit_settle.sum_trans_fee_delta
     *
     * @mbg.generated
     */
    private BigDecimal sumTransFeeDelta;

    /**
     * Database table : view_txn_profit_settle
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : view_txn_profit_settle
     *
     * @mbg.generated
     */
    public TxnProfitSettle(String intTransCd, String transChnl, String chnlMchntCd, String mchntCd, BigDecimal sumAmt, BigDecimal sumTransFee, BigDecimal sumTransFeeChnl, BigDecimal sumTransFeeDelta) {
        this.intTransCd = intTransCd;
        this.transChnl = transChnl;
        this.chnlMchntCd = chnlMchntCd;
        this.mchntCd = mchntCd;
        this.sumAmt = sumAmt;
        this.sumTransFee = sumTransFee;
        this.sumTransFeeChnl = sumTransFeeChnl;
        this.sumTransFeeDelta = sumTransFeeDelta;
    }

    /**
     * Database table : view_txn_profit_settle
     *
     * @mbg.generated
     */
    public TxnProfitSettle() {
        super();
    }

    /**
     * @return int_trans_cd 
     *
     * @mbg.generated
     */
    public String getIntTransCd() {
        return intTransCd;
    }

    /**
     * @param intTransCd 
     *
     * @mbg.generated
     */
    public void setIntTransCd(String intTransCd) {
        this.intTransCd = intTransCd;
    }

    /**
     * @return trans_chnl 
     *
     * @mbg.generated
     */
    public String getTransChnl() {
        return transChnl;
    }

    /**
     * @param transChnl 
     *
     * @mbg.generated
     */
    public void setTransChnl(String transChnl) {
        this.transChnl = transChnl;
    }

    /**
     * @return chnl_mchnt_cd 
     *
     * @mbg.generated
     */
    public String getChnlMchntCd() {
        return chnlMchntCd;
    }

    /**
     * @param chnlMchntCd 
     *
     * @mbg.generated
     */
    public void setChnlMchntCd(String chnlMchntCd) {
        this.chnlMchntCd = chnlMchntCd;
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
     * @return sum_amt 
     *
     * @mbg.generated
     */
    public BigDecimal getSumAmt() {
        return sumAmt;
    }

    /**
     * @param sumAmt 
     *
     * @mbg.generated
     */
    public void setSumAmt(BigDecimal sumAmt) {
        this.sumAmt = sumAmt;
    }

    /**
     * @return sum_trans_fee 
     *
     * @mbg.generated
     */
    public BigDecimal getSumTransFee() {
        return sumTransFee;
    }

    /**
     * @param sumTransFee 
     *
     * @mbg.generated
     */
    public void setSumTransFee(BigDecimal sumTransFee) {
        this.sumTransFee = sumTransFee;
    }

    /**
     * @return sum_trans_fee_chnl 
     *
     * @mbg.generated
     */
    public BigDecimal getSumTransFeeChnl() {
        return sumTransFeeChnl;
    }

    /**
     * @param sumTransFeeChnl 
     *
     * @mbg.generated
     */
    public void setSumTransFeeChnl(BigDecimal sumTransFeeChnl) {
        this.sumTransFeeChnl = sumTransFeeChnl;
    }

    /**
     * @return sum_trans_fee_delta 
     *
     * @mbg.generated
     */
    public BigDecimal getSumTransFeeDelta() {
        return sumTransFeeDelta;
    }

    /**
     * @param sumTransFeeDelta 
     *
     * @mbg.generated
     */
    public void setSumTransFeeDelta(BigDecimal sumTransFeeDelta) {
        this.sumTransFeeDelta = sumTransFeeDelta;
    }

    /**
     * Database table : view_txn_profit_settle
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
        TxnProfitSettle other = (TxnProfitSettle) that;
        return (this.getIntTransCd() == null ? other.getIntTransCd() == null : this.getIntTransCd().equals(other.getIntTransCd()))
            && (this.getTransChnl() == null ? other.getTransChnl() == null : this.getTransChnl().equals(other.getTransChnl()))
            && (this.getChnlMchntCd() == null ? other.getChnlMchntCd() == null : this.getChnlMchntCd().equals(other.getChnlMchntCd()))
            && (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()))
            && (this.getSumAmt() == null ? other.getSumAmt() == null : this.getSumAmt().equals(other.getSumAmt()))
            && (this.getSumTransFee() == null ? other.getSumTransFee() == null : this.getSumTransFee().equals(other.getSumTransFee()))
            && (this.getSumTransFeeChnl() == null ? other.getSumTransFeeChnl() == null : this.getSumTransFeeChnl().equals(other.getSumTransFeeChnl()))
            && (this.getSumTransFeeDelta() == null ? other.getSumTransFeeDelta() == null : this.getSumTransFeeDelta().equals(other.getSumTransFeeDelta()));
    }

    /**
     * Database table : view_txn_profit_settle
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIntTransCd() == null) ? 0 : getIntTransCd().hashCode());
        result = prime * result + ((getTransChnl() == null) ? 0 : getTransChnl().hashCode());
        result = prime * result + ((getChnlMchntCd() == null) ? 0 : getChnlMchntCd().hashCode());
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        result = prime * result + ((getSumAmt() == null) ? 0 : getSumAmt().hashCode());
        result = prime * result + ((getSumTransFee() == null) ? 0 : getSumTransFee().hashCode());
        result = prime * result + ((getSumTransFeeChnl() == null) ? 0 : getSumTransFeeChnl().hashCode());
        result = prime * result + ((getSumTransFeeDelta() == null) ? 0 : getSumTransFeeDelta().hashCode());
        return result;
    }

    /**
     * Database table : view_txn_profit_settle
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", intTransCd=").append(intTransCd);
        sb.append(", transChnl=").append(transChnl);
        sb.append(", chnlMchntCd=").append(chnlMchntCd);
        sb.append(", mchntCd=").append(mchntCd);
        sb.append(", sumAmt=").append(sumAmt);
        sb.append(", sumTransFee=").append(sumTransFee);
        sb.append(", sumTransFeeChnl=").append(sumTransFeeChnl);
        sb.append(", sumTransFeeDelta=").append(sumTransFeeDelta);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(TxnProfitSettle source) {
        this.intTransCd=source.intTransCd;
        this.transChnl=source.transChnl;
        this.chnlMchntCd=source.chnlMchntCd;
        this.mchntCd=source.mchntCd;
        this.sumAmt=source.sumAmt;
        this.sumTransFee=source.sumTransFee;
        this.sumTransFeeChnl=source.sumTransFeeChnl;
        this.sumTransFeeDelta=source.sumTransFeeDelta;
    }
}