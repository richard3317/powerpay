package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_organ_mchnt_info
 * @author vicky
 *
 * @mbg.generated
 */
public class OrganMchntInfoKey implements Serializable {
    /**
     * 机构编号
     * Database column : tbl_organ_mchnt_info.organ_id
     *
     * @mbg.generated
     */
    private String organId;

    /**
     * 商户号
     * Database column : tbl_organ_mchnt_info.mchnt_cd
     *
     * @mbg.generated
     */
    private String mchntCd;

    /**
     * Database table : tbl_organ_mchnt_info
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_organ_mchnt_info
     *
     * @mbg.generated
     */
    public OrganMchntInfoKey(String organId, String mchntCd) {
        this.organId = organId;
        this.mchntCd = mchntCd;
    }

    /**
     * Database table : tbl_organ_mchnt_info
     *
     * @mbg.generated
     */
    public OrganMchntInfoKey() {
        super();
    }

    /**
     * 机构编号
     * @return organ_id 机构编号
     *
     * @mbg.generated
     */
    public String getOrganId() {
        return organId;
    }

    /**
     * 机构编号
     * @param organId 机构编号
     *
     * @mbg.generated
     */
    public void setOrganId(String organId) {
        this.organId = organId;
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
     * Database table : tbl_organ_mchnt_info
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
        OrganMchntInfoKey other = (OrganMchntInfoKey) that;
        return (this.getOrganId() == null ? other.getOrganId() == null : this.getOrganId().equals(other.getOrganId()))
            && (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()));
    }

    /**
     * Database table : tbl_organ_mchnt_info
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrganId() == null) ? 0 : getOrganId().hashCode());
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        return result;
    }

    /**
     * Database table : tbl_organ_mchnt_info
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", organId=").append(organId);
        sb.append(", mchntCd=").append(mchntCd);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(OrganMchntInfoKey source) {
        this.organId=source.organId;
        this.mchntCd=source.mchntCd;
    }
}