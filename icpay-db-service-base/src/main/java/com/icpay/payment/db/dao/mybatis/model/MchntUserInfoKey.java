package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_mchnt_user_info

 *
 * @mbg.generated
 */
public class MchntUserInfoKey implements Serializable {
    /**
     * 商户编号
     * Database column : tbl_mchnt_user_info.mchnt_cd
     *
     * @mbg.generated
     */
    private String mchntCd;

    /**
     * 该商户的用户ID
     * Database column : tbl_mchnt_user_info.user_id
     *
     * @mbg.generated
     */
    private String userId;

    /**
     * Database table : tbl_mchnt_user_info
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_mchnt_user_info
     *
     * @mbg.generated
     */
    public MchntUserInfoKey(String mchntCd, String userId) {
        this.mchntCd = mchntCd;
        this.userId = userId;
    }

    /**
     * Database table : tbl_mchnt_user_info
     *
     * @mbg.generated
     */
    public MchntUserInfoKey() {
        super();
    }

    /**
     * 商户编号
     * @return mchnt_cd 商户编号
     *
     * @mbg.generated
     */
    public String getMchntCd() {
        return mchntCd;
    }

    /**
     * 商户编号
     * @param mchntCd 商户编号
     *
     * @mbg.generated
     */
    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd;
    }

    /**
     * 该商户的用户ID
     * @return user_id 该商户的用户ID
     *
     * @mbg.generated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 该商户的用户ID
     * @param userId 该商户的用户ID
     *
     * @mbg.generated
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Database table : tbl_mchnt_user_info
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
        MchntUserInfoKey other = (MchntUserInfoKey) that;
        return (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()));
    }

    /**
     * Database table : tbl_mchnt_user_info
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        return result;
    }

    /**
     * Database table : tbl_mchnt_user_info
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
        sb.append(", userId=").append(userId);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(MchntUserInfoKey source) {
        this.mchntCd=source.mchntCd;
        this.userId=source.userId;
    }
}