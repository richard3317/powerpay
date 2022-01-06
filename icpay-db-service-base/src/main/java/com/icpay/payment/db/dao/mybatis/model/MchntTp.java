package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_mchnt_tp

 *
 * @mbg.generated
 */
public class MchntTp implements Serializable {
    /**
     * Database column : tbl_mchnt_tp.mchnt_tp
     *
     * @mbg.generated
     */
    private String mchntTp;

    /**
     * Database column : tbl_mchnt_tp.mchnt_tp_grp
     *
     * @mbg.generated
     */
    private String mchntTpGrp;

    /**
     * Database column : tbl_mchnt_tp.mchnt_tp_desc_cn
     *
     * @mbg.generated
     */
    private String mchntTpDescCn;

    /**
     * Database column : tbl_mchnt_tp.mchnt_tp_desc_en
     *
     * @mbg.generated
     */
    private String mchntTpDescEn;

    /**
     * Database table : tbl_mchnt_tp
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_mchnt_tp
     *
     * @mbg.generated
     */
    public MchntTp(String mchntTp, String mchntTpGrp, String mchntTpDescCn, String mchntTpDescEn) {
        this.mchntTp = mchntTp;
        this.mchntTpGrp = mchntTpGrp;
        this.mchntTpDescCn = mchntTpDescCn;
        this.mchntTpDescEn = mchntTpDescEn;
    }

    /**
     * Database table : tbl_mchnt_tp
     *
     * @mbg.generated
     */
    public MchntTp() {
        super();
    }

    /**
     * @return mchnt_tp 
     *
     * @mbg.generated
     */
    public String getMchntTp() {
        return mchntTp;
    }

    /**
     * @param mchntTp 
     *
     * @mbg.generated
     */
    public void setMchntTp(String mchntTp) {
        this.mchntTp = mchntTp;
    }

    /**
     * @return mchnt_tp_grp 
     *
     * @mbg.generated
     */
    public String getMchntTpGrp() {
        return mchntTpGrp;
    }

    /**
     * @param mchntTpGrp 
     *
     * @mbg.generated
     */
    public void setMchntTpGrp(String mchntTpGrp) {
        this.mchntTpGrp = mchntTpGrp;
    }

    /**
     * @return mchnt_tp_desc_cn 
     *
     * @mbg.generated
     */
    public String getMchntTpDescCn() {
        return mchntTpDescCn;
    }

    /**
     * @param mchntTpDescCn 
     *
     * @mbg.generated
     */
    public void setMchntTpDescCn(String mchntTpDescCn) {
        this.mchntTpDescCn = mchntTpDescCn;
    }

    /**
     * @return mchnt_tp_desc_en 
     *
     * @mbg.generated
     */
    public String getMchntTpDescEn() {
        return mchntTpDescEn;
    }

    /**
     * @param mchntTpDescEn 
     *
     * @mbg.generated
     */
    public void setMchntTpDescEn(String mchntTpDescEn) {
        this.mchntTpDescEn = mchntTpDescEn;
    }

    /**
     * Database table : tbl_mchnt_tp
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
        MchntTp other = (MchntTp) that;
        return (this.getMchntTp() == null ? other.getMchntTp() == null : this.getMchntTp().equals(other.getMchntTp()))
            && (this.getMchntTpGrp() == null ? other.getMchntTpGrp() == null : this.getMchntTpGrp().equals(other.getMchntTpGrp()))
            && (this.getMchntTpDescCn() == null ? other.getMchntTpDescCn() == null : this.getMchntTpDescCn().equals(other.getMchntTpDescCn()))
            && (this.getMchntTpDescEn() == null ? other.getMchntTpDescEn() == null : this.getMchntTpDescEn().equals(other.getMchntTpDescEn()));
    }

    /**
     * Database table : tbl_mchnt_tp
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMchntTp() == null) ? 0 : getMchntTp().hashCode());
        result = prime * result + ((getMchntTpGrp() == null) ? 0 : getMchntTpGrp().hashCode());
        result = prime * result + ((getMchntTpDescCn() == null) ? 0 : getMchntTpDescCn().hashCode());
        result = prime * result + ((getMchntTpDescEn() == null) ? 0 : getMchntTpDescEn().hashCode());
        return result;
    }

    /**
     * Database table : tbl_mchnt_tp
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", mchntTp=").append(mchntTp);
        sb.append(", mchntTpGrp=").append(mchntTpGrp);
        sb.append(", mchntTpDescCn=").append(mchntTpDescCn);
        sb.append(", mchntTpDescEn=").append(mchntTpDescEn);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(MchntTp source) {
        this.mchntTp=source.mchntTp;
        this.mchntTpGrp=source.mchntTpGrp;
        this.mchntTpDescCn=source.mchntTpDescCn;
        this.mchntTpDescEn=source.mchntTpDescEn;
    }
}