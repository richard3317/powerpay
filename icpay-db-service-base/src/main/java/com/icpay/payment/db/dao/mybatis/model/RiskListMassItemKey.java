package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_risk_list_mass_item

 *
 * @mbg.generated
 */
public class RiskListMassItemKey implements Serializable {
    /**
     * 风险项目群组编号(定义于tbl_risk_list_grop)
     * Database column : tbl_risk_list_mass_item.group_id
     *
     * @mbg.generated
     */
    private Integer groupId;

    /**
     * 渠道编号，默认 00
     * Database column : tbl_risk_list_mass_item.chnl_id
     *
     * @mbg.generated
     */
    private String chnlId;

    /**
     * 商户编号，默认 0000
     * Database column : tbl_risk_list_mass_item.mchnt_cd
     *
     * @mbg.generated
     */
    private String mchntCd;

    /**
     * 风险管控的项目内容，例如卡号，商户号，IP等
     * Database column : tbl_risk_list_mass_item.item
     *
     * @mbg.generated
     */
    private String item;

    /**
     * Database table : tbl_risk_list_mass_item
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_risk_list_mass_item
     *
     * @mbg.generated
     */
    public RiskListMassItemKey(Integer groupId, String chnlId, String mchntCd, String item) {
        this.groupId = groupId;
        this.chnlId = chnlId;
        this.mchntCd = mchntCd;
        this.item = item;
    }

    /**
     * Database table : tbl_risk_list_mass_item
     *
     * @mbg.generated
     */
    public RiskListMassItemKey() {
        super();
    }

    /**
     * 风险项目群组编号(定义于tbl_risk_list_grop)
     * @return group_id 风险项目群组编号(定义于tbl_risk_list_grop)
     *
     * @mbg.generated
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * 风险项目群组编号(定义于tbl_risk_list_grop)
     * @param groupId 风险项目群组编号(定义于tbl_risk_list_grop)
     *
     * @mbg.generated
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * 渠道编号，默认 00
     * @return chnl_id 渠道编号，默认 00
     *
     * @mbg.generated
     */
    public String getChnlId() {
        return chnlId;
    }

    /**
     * 渠道编号，默认 00
     * @param chnlId 渠道编号，默认 00
     *
     * @mbg.generated
     */
    public void setChnlId(String chnlId) {
        this.chnlId = chnlId;
    }

    /**
     * 商户编号，默认 0000
     * @return mchnt_cd 商户编号，默认 0000
     *
     * @mbg.generated
     */
    public String getMchntCd() {
        return mchntCd;
    }

    /**
     * 商户编号，默认 0000
     * @param mchntCd 商户编号，默认 0000
     *
     * @mbg.generated
     */
    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd;
    }

    /**
     * 风险管控的项目内容，例如卡号，商户号，IP等
     * @return item 风险管控的项目内容，例如卡号，商户号，IP等
     *
     * @mbg.generated
     */
    public String getItem() {
        return item;
    }

    /**
     * 风险管控的项目内容，例如卡号，商户号，IP等
     * @param item 风险管控的项目内容，例如卡号，商户号，IP等
     *
     * @mbg.generated
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * Database table : tbl_risk_list_mass_item
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
        RiskListMassItemKey other = (RiskListMassItemKey) that;
        return (this.getGroupId() == null ? other.getGroupId() == null : this.getGroupId().equals(other.getGroupId()))
            && (this.getChnlId() == null ? other.getChnlId() == null : this.getChnlId().equals(other.getChnlId()))
            && (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()))
            && (this.getItem() == null ? other.getItem() == null : this.getItem().equals(other.getItem()));
    }

    /**
     * Database table : tbl_risk_list_mass_item
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGroupId() == null) ? 0 : getGroupId().hashCode());
        result = prime * result + ((getChnlId() == null) ? 0 : getChnlId().hashCode());
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        result = prime * result + ((getItem() == null) ? 0 : getItem().hashCode());
        return result;
    }

    /**
     * Database table : tbl_risk_list_mass_item
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", groupId=").append(groupId);
        sb.append(", chnlId=").append(chnlId);
        sb.append(", mchntCd=").append(mchntCd);
        sb.append(", item=").append(item);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(RiskListMassItemKey source) {
        this.groupId=source.groupId;
        this.chnlId=source.chnlId;
        this.mchntCd=source.mchntCd;
        this.item=source.item;
    }
}