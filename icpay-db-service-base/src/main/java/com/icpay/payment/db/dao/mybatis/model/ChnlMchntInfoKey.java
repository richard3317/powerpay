package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_chnl_mchnt_info
 * @author user
 *
 * @mbg.generated
 */
public class ChnlMchntInfoKey implements Serializable {
    /**
     * 渠道编号
     * Database column : tbl_chnl_mchnt_info.chnl_id
     *
     * @mbg.generated
     */
    private String chnlId;

    /**
     * 渠道商户号
     * Database column : tbl_chnl_mchnt_info.chnl_mchnt_cd
     *
     * @mbg.generated
     */
    private String chnlMchntCd;

    /**
     * Database table : tbl_chnl_mchnt_info
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_chnl_mchnt_info
     *
     * @mbg.generated
     */
    public ChnlMchntInfoKey(String chnlId, String chnlMchntCd) {
        this.chnlId = chnlId;
        this.chnlMchntCd = chnlMchntCd;
    }

    /**
     * Database table : tbl_chnl_mchnt_info
     *
     * @mbg.generated
     */
    public ChnlMchntInfoKey() {
        super();
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
     * 渠道商户号
     * @return chnl_mchnt_cd 渠道商户号
     *
     * @mbg.generated
     */
    public String getChnlMchntCd() {
        return chnlMchntCd;
    }

    /**
     * 渠道商户号
     * @param chnlMchntCd 渠道商户号
     *
     * @mbg.generated
     */
    public void setChnlMchntCd(String chnlMchntCd) {
        this.chnlMchntCd = chnlMchntCd;
    }

    /**
     * Database table : tbl_chnl_mchnt_info
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
        ChnlMchntInfoKey other = (ChnlMchntInfoKey) that;
        return (this.getChnlId() == null ? other.getChnlId() == null : this.getChnlId().equals(other.getChnlId()))
            && (this.getChnlMchntCd() == null ? other.getChnlMchntCd() == null : this.getChnlMchntCd().equals(other.getChnlMchntCd()));
    }

    /**
     * Database table : tbl_chnl_mchnt_info
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getChnlId() == null) ? 0 : getChnlId().hashCode());
        result = prime * result + ((getChnlMchntCd() == null) ? 0 : getChnlMchntCd().hashCode());
        return result;
    }

    /**
     * Database table : tbl_chnl_mchnt_info
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", chnlId=").append(chnlId);
        sb.append(", chnlMchntCd=").append(chnlMchntCd);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(ChnlMchntInfoKey source) {
        this.chnlId=source.chnlId;
        this.chnlMchntCd=source.chnlMchntCd;
    }
}