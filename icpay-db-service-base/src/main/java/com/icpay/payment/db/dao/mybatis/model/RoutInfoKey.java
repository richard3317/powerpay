package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_rout_info

 *
 * @mbg.generated
 */
public class RoutInfoKey implements Serializable {
    /**
     * Database column : tbl_rout_info.mchnt_cd
     *
     * @mbg.generated
     */
    private String mchntCd;

    /**
     * Database column : tbl_rout_info.int_trans_cd
     *
     * @mbg.generated
     */
    private String intTransCd;

    /**
     * Database column : tbl_rout_info.pay_type
     *
     * @mbg.generated
     */
    private String payType;

    /**
     * Database column : tbl_rout_info.term_sn_regex
     *
     * @mbg.generated
     */
    private String termSnRegex;

    /**
     * Database table : tbl_rout_info
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_rout_info
     *
     * @mbg.generated
     */
    public RoutInfoKey(String mchntCd, String intTransCd, String payType, String termSnRegex) {
        this.mchntCd = mchntCd;
        this.intTransCd = intTransCd;
        this.payType = payType;
        this.termSnRegex = termSnRegex;
    }

    /**
     * Database table : tbl_rout_info
     *
     * @mbg.generated
     */
    public RoutInfoKey() {
        super();
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
     * @return pay_type 
     *
     * @mbg.generated
     */
    public String getPayType() {
        return payType;
    }

    /**
     * @param payType 
     *
     * @mbg.generated
     */
    public void setPayType(String payType) {
        this.payType = payType;
    }

    /**
     * @return term_sn_regex 
     *
     * @mbg.generated
     */
    public String getTermSnRegex() {
        return termSnRegex;
    }

    /**
     * @param termSnRegex 
     *
     * @mbg.generated
     */
    public void setTermSnRegex(String termSnRegex) {
        this.termSnRegex = termSnRegex;
    }

    /**
     * Database table : tbl_rout_info
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
        RoutInfoKey other = (RoutInfoKey) that;
        return (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()))
            && (this.getIntTransCd() == null ? other.getIntTransCd() == null : this.getIntTransCd().equals(other.getIntTransCd()))
            && (this.getPayType() == null ? other.getPayType() == null : this.getPayType().equals(other.getPayType()))
            && (this.getTermSnRegex() == null ? other.getTermSnRegex() == null : this.getTermSnRegex().equals(other.getTermSnRegex()));
    }

    /**
     * Database table : tbl_rout_info
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        result = prime * result + ((getIntTransCd() == null) ? 0 : getIntTransCd().hashCode());
        result = prime * result + ((getPayType() == null) ? 0 : getPayType().hashCode());
        result = prime * result + ((getTermSnRegex() == null) ? 0 : getTermSnRegex().hashCode());
        return result;
    }

    /**
     * Database table : tbl_rout_info
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", mchntCd=").append(mchntCd);
        sb.append(", intTransCd=").append(intTransCd);
        sb.append(", payType=").append(payType);
        sb.append(", termSnRegex=").append(termSnRegex);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(RoutInfoKey source) {
        this.mchntCd=source.mchntCd;
        this.intTransCd=source.intTransCd;
        this.payType=source.payType;
        this.termSnRegex=source.termSnRegex;
    }
}