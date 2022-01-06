package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : view_risk_item_info_for_mer

 *
 * @mbg.generated
 */
public class RiskItemInfoForMer implements Serializable {
    /**
     * 渠道编号，若为前端小商户号请带入00
     * Database column : view_risk_item_info_for_mer.chnl_id
     *
     * @mbg.generated
     */
    private String chnlId;

    /**
     * 商户编号小商编
     * Database column : view_risk_item_info_for_mer.mchnt_cd
     *
     * @mbg.generated
     */
    private String mchntCd;

    /**
     * 风险管控的项目内容，例如卡号，商户号，IP等
     * Database column : view_risk_item_info_for_mer.item
     *
     * @mbg.generated
     */
    private String item;

    /**
     * 群组ID
     * Database column : view_risk_item_info_for_mer.group_id
     *
     * @mbg.generated
     */
    private Integer groupId;

    /**
     * 群组描述
     * Database column : view_risk_item_info_for_mer.group_nm
     *
     * @mbg.generated
     */
    private String groupNm;

    /**
     * 名单组类型(商户号，卡号，IP等)，参阅Constant.RISK_ITEM_TP；01:卡号；02:手机号；03:用户号；04:商户号；05:IP；06:终端序号
     * Database column : view_risk_item_info_for_mer.group_tp
     *
     * @mbg.generated
     */
    private String groupTp;

    /**
     * 列表型态，1=白名单，0=黑名单, 5=大型白名单, 6=大型黑名单,7=例外名单(无法单独使用)
     * Database column : view_risk_item_info_for_mer.list_tp
     *
     * @mbg.generated
     */
    private String listTp;

    /**
     * 风险类型
     * Database column : view_risk_item_info_for_mer.risk_tp
     *
     * @mbg.generated
     */
    private String riskTp;

    /**
     * 风险等级
     * Database column : view_risk_item_info_for_mer.risk_level
     *
     * @mbg.generated
     */
    private Integer riskLevel;

    /**
     * Database column : view_risk_item_info_for_mer.result
     *
     * @mbg.generated
     */
    private String result;

    /**
     * 状态：1=启用，2=禁用
     * Database column : view_risk_item_info_for_mer.status
     *
     * @mbg.generated
     */
    private String status;

    /**
     * 逾期时间
     * Database column : view_risk_item_info_for_mer.expired_tm
     *
     * @mbg.generated
     */
    private Date expiredTm;

    /**
     * Database table : view_risk_item_info_for_mer
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : view_risk_item_info_for_mer
     *
     * @mbg.generated
     */
    public RiskItemInfoForMer(String chnlId, String mchntCd, String item, Integer groupId, String groupNm, String groupTp, String listTp, String riskTp, Integer riskLevel, String result, String status, Date expiredTm) {
        this.chnlId = chnlId;
        this.mchntCd = mchntCd;
        this.item = item;
        this.groupId = groupId;
        this.groupNm = groupNm;
        this.groupTp = groupTp;
        this.listTp = listTp;
        this.riskTp = riskTp;
        this.riskLevel = riskLevel;
        this.result = result;
        this.status = status;
        this.expiredTm = expiredTm;
    }

    /**
     * Database table : view_risk_item_info_for_mer
     *
     * @mbg.generated
     */
    public RiskItemInfoForMer() {
        super();
    }

    /**
     * 渠道编号，若为前端小商户号请带入00
     * @return chnl_id 渠道编号，若为前端小商户号请带入00
     *
     * @mbg.generated
     */
    public String getChnlId() {
        return chnlId;
    }

    /**
     * 渠道编号，若为前端小商户号请带入00
     * @param chnlId 渠道编号，若为前端小商户号请带入00
     *
     * @mbg.generated
     */
    public void setChnlId(String chnlId) {
        this.chnlId = chnlId;
    }

    /**
     * 商户编号小商编
     * @return mchnt_cd 商户编号小商编
     *
     * @mbg.generated
     */
    public String getMchntCd() {
        return mchntCd;
    }

    /**
     * 商户编号小商编
     * @param mchntCd 商户编号小商编
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
     * 群组ID
     * @return group_id 群组ID
     *
     * @mbg.generated
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * 群组ID
     * @param groupId 群组ID
     *
     * @mbg.generated
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * 群组描述
     * @return group_nm 群组描述
     *
     * @mbg.generated
     */
    public String getGroupNm() {
        return groupNm;
    }

    /**
     * 群组描述
     * @param groupNm 群组描述
     *
     * @mbg.generated
     */
    public void setGroupNm(String groupNm) {
        this.groupNm = groupNm;
    }

    /**
     * 名单组类型(商户号，卡号，IP等)，参阅Constant.RISK_ITEM_TP；01:卡号；02:手机号；03:用户号；04:商户号；05:IP；06:终端序号
     * @return group_tp 名单组类型(商户号，卡号，IP等)，参阅Constant.RISK_ITEM_TP；01:卡号；02:手机号；03:用户号；04:商户号；05:IP；06:终端序号
     *
     * @mbg.generated
     */
    public String getGroupTp() {
        return groupTp;
    }

    /**
     * 名单组类型(商户号，卡号，IP等)，参阅Constant.RISK_ITEM_TP；01:卡号；02:手机号；03:用户号；04:商户号；05:IP；06:终端序号
     * @param groupTp 名单组类型(商户号，卡号，IP等)，参阅Constant.RISK_ITEM_TP；01:卡号；02:手机号；03:用户号；04:商户号；05:IP；06:终端序号
     *
     * @mbg.generated
     */
    public void setGroupTp(String groupTp) {
        this.groupTp = groupTp;
    }

    /**
     * 列表型态，1=白名单，0=黑名单, 5=大型白名单, 6=大型黑名单,7=例外名单(无法单独使用)
     * @return list_tp 列表型态，1=白名单，0=黑名单, 5=大型白名单, 6=大型黑名单,7=例外名单(无法单独使用)
     *
     * @mbg.generated
     */
    public String getListTp() {
        return listTp;
    }

    /**
     * 列表型态，1=白名单，0=黑名单, 5=大型白名单, 6=大型黑名单,7=例外名单(无法单独使用)
     * @param listTp 列表型态，1=白名单，0=黑名单, 5=大型白名单, 6=大型黑名单,7=例外名单(无法单独使用)
     *
     * @mbg.generated
     */
    public void setListTp(String listTp) {
        this.listTp = listTp;
    }

    /**
     * 风险类型
     * @return risk_tp 风险类型
     *
     * @mbg.generated
     */
    public String getRiskTp() {
        return riskTp;
    }

    /**
     * 风险类型
     * @param riskTp 风险类型
     *
     * @mbg.generated
     */
    public void setRiskTp(String riskTp) {
        this.riskTp = riskTp;
    }

    /**
     * 风险等级
     * @return risk_level 风险等级
     *
     * @mbg.generated
     */
    public Integer getRiskLevel() {
        return riskLevel;
    }

    /**
     * 风险等级
     * @param riskLevel 风险等级
     *
     * @mbg.generated
     */
    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    /**
     * @return result 
     *
     * @mbg.generated
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result 
     *
     * @mbg.generated
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 状态：1=启用，2=禁用
     * @return status 状态：1=启用，2=禁用
     *
     * @mbg.generated
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态：1=启用，2=禁用
     * @param status 状态：1=启用，2=禁用
     *
     * @mbg.generated
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 逾期时间
     * @return expired_tm 逾期时间
     *
     * @mbg.generated
     */
    public Date getExpiredTm() {
        return expiredTm;
    }

    /**
     * 逾期时间
     * @param expiredTm 逾期时间
     *
     * @mbg.generated
     */
    public void setExpiredTm(Date expiredTm) {
        this.expiredTm = expiredTm;
    }

    /**
     * Database table : view_risk_item_info_for_mer
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
        RiskItemInfoForMer other = (RiskItemInfoForMer) that;
        return (this.getChnlId() == null ? other.getChnlId() == null : this.getChnlId().equals(other.getChnlId()))
            && (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()))
            && (this.getItem() == null ? other.getItem() == null : this.getItem().equals(other.getItem()))
            && (this.getGroupId() == null ? other.getGroupId() == null : this.getGroupId().equals(other.getGroupId()))
            && (this.getGroupNm() == null ? other.getGroupNm() == null : this.getGroupNm().equals(other.getGroupNm()))
            && (this.getGroupTp() == null ? other.getGroupTp() == null : this.getGroupTp().equals(other.getGroupTp()))
            && (this.getListTp() == null ? other.getListTp() == null : this.getListTp().equals(other.getListTp()))
            && (this.getRiskTp() == null ? other.getRiskTp() == null : this.getRiskTp().equals(other.getRiskTp()))
            && (this.getRiskLevel() == null ? other.getRiskLevel() == null : this.getRiskLevel().equals(other.getRiskLevel()))
            && (this.getResult() == null ? other.getResult() == null : this.getResult().equals(other.getResult()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getExpiredTm() == null ? other.getExpiredTm() == null : this.getExpiredTm().equals(other.getExpiredTm()));
    }

    /**
     * Database table : view_risk_item_info_for_mer
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getChnlId() == null) ? 0 : getChnlId().hashCode());
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        result = prime * result + ((getItem() == null) ? 0 : getItem().hashCode());
        result = prime * result + ((getGroupId() == null) ? 0 : getGroupId().hashCode());
        result = prime * result + ((getGroupNm() == null) ? 0 : getGroupNm().hashCode());
        result = prime * result + ((getGroupTp() == null) ? 0 : getGroupTp().hashCode());
        result = prime * result + ((getListTp() == null) ? 0 : getListTp().hashCode());
        result = prime * result + ((getRiskTp() == null) ? 0 : getRiskTp().hashCode());
        result = prime * result + ((getRiskLevel() == null) ? 0 : getRiskLevel().hashCode());
        result = prime * result + ((getResult() == null) ? 0 : getResult().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getExpiredTm() == null) ? 0 : getExpiredTm().hashCode());
        return result;
    }

    /**
     * Database table : view_risk_item_info_for_mer
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
        sb.append(", mchntCd=").append(mchntCd);
        sb.append(", item=").append(item);
        sb.append(", groupId=").append(groupId);
        sb.append(", groupNm=").append(groupNm);
        sb.append(", groupTp=").append(groupTp);
        sb.append(", listTp=").append(listTp);
        sb.append(", riskTp=").append(riskTp);
        sb.append(", riskLevel=").append(riskLevel);
        sb.append(", result=").append(result);
        sb.append(", status=").append(status);
        sb.append(", expiredTm=").append(expiredTm);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(RiskItemInfoForMer source) {
        this.chnlId=source.chnlId;
        this.mchntCd=source.mchntCd;
        this.item=source.item;
        this.groupId=source.groupId;
        this.groupNm=source.groupNm;
        this.groupTp=source.groupTp;
        this.listTp=source.listTp;
        this.riskTp=source.riskTp;
        this.riskLevel=source.riskLevel;
        this.result=source.result;
        this.status=source.status;
        this.expiredTm=source.expiredTm;
    }
}