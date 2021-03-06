package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_agent_info

 *
 * @mbg.generated
 */
public class AgentInfo implements Serializable {
    /**
     * 代理商或中人编号
     * Database column : tbl_agent_info.agent_cd
     *
     * @mbg.generated
     */
    private String agentCd;

    /**
     * 代理人(中人)类别: 1=自营，2=商户代理人，3=渠道代理人，0=未定义
     * Database column : tbl_agent_info.agent_type
     *
     * @mbg.generated
     */
    private String agentType;

    /**
     * 中文名称
     * Database column : tbl_agent_info.agent_cn_nm
     *
     * @mbg.generated
     */
    private String agentCnNm;

    /**
     * 英文名称
     * Database column : tbl_agent_info.agent_en_nm
     *
     * @mbg.generated
     */
    private String agentEnNm;

    /**
     * 中文缩写
     * Database column : tbl_agent_info.agent_cn_abbr
     *
     * @mbg.generated
     */
    private String agentCnAbbr;

    /**
     * 英文缩写
     * Database column : tbl_agent_info.agent_en_abbr
     *
     * @mbg.generated
     */
    private String agentEnAbbr;

    /**
     * 区域编号
     * Database column : tbl_agent_info.area_cd
     *
     * @mbg.generated
     */
    private String areaCd;

    /**
     * 地址
     * Database column : tbl_agent_info.agent_addr
     *
     * @mbg.generated
     */
    private String agentAddr;

    /**
     * 联系人
     * Database column : tbl_agent_info.contact_person1
     *
     * @mbg.generated
     */
    private String contactPerson1;

    /**
     * 联系电话
     * Database column : tbl_agent_info.contact_phone1
     *
     * @mbg.generated
     */
    private String contactPhone1;

    /**
     * 联系电邮
     * Database column : tbl_agent_info.contact_mail1
     *
     * @mbg.generated
     */
    private String contactMail1;

    /**
     * 联系人2
     * Database column : tbl_agent_info.contact_person2
     *
     * @mbg.generated
     */
    private String contactPerson2;

    /**
     * 联系电话2
     * Database column : tbl_agent_info.contact_phone2
     *
     * @mbg.generated
     */
    private String contactPhone2;

    /**
     * 联系电邮2
     * Database column : tbl_agent_info.contact_mail2
     *
     * @mbg.generated
     */
    private String contactMail2;

    /**
     * 邮编
     * Database column : tbl_agent_info.zip_cd
     *
     * @mbg.generated
     */
    private String zipCd;

    /**
     * 传真
     * Database column : tbl_agent_info.fax
     *
     * @mbg.generated
     */
    private String fax;

    /**
     * 支持的清算类型。目前只有第一位有效。(暂不使用)
     * Database column : tbl_agent_info.st_cd
     *
     * @mbg.generated
     */
    private String stCd;

    /**
     * 有效状态：1=有效，其他无效。
     * Database column : tbl_agent_info.agent_st
     *
     * @mbg.generated
     */
    private String agentSt;

    /**
     * 清算周期,0=T0,1=T1,2=T2,3=T3,9=D0
     * Database column : tbl_agent_info.settle_period
     *
     * @mbg.generated
     */
    private String settlePeriod;

    /**
     * 备注
     * Database column : tbl_agent_info.comment
     *
     * @mbg.generated
     */
    private String comment;

    /**
     * Database column : tbl_agent_info.last_oper_id
     *
     * @mbg.generated
     */
    private String lastOperId;

    /**
     * Database column : tbl_agent_info.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_agent_info.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * 分润状态：1=是，其他否。
     * Database column : tbl_agent_info.deposit
     *
     * @mbg.generated
     */
    private String deposit;

    /**
     * Database column : tbl_agent_info.password
     *
     * @mbg.generated
     */
    private String password;

    /**
     * Database table : tbl_agent_info
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_agent_info
     *
     * @mbg.generated
     */
    public AgentInfo(String agentCd, String agentType, String agentCnNm, String agentEnNm, String agentCnAbbr, String agentEnAbbr, String areaCd, String agentAddr, String contactPerson1, String contactPhone1, String contactMail1, String contactPerson2, String contactPhone2, String contactMail2, String zipCd, String fax, String stCd, String agentSt, String settlePeriod, String comment, String lastOperId, Date recCrtTs, Date recUpdTs, String deposit, String password) {
        this.agentCd = agentCd;
        this.agentType = agentType;
        this.agentCnNm = agentCnNm;
        this.agentEnNm = agentEnNm;
        this.agentCnAbbr = agentCnAbbr;
        this.agentEnAbbr = agentEnAbbr;
        this.areaCd = areaCd;
        this.agentAddr = agentAddr;
        this.contactPerson1 = contactPerson1;
        this.contactPhone1 = contactPhone1;
        this.contactMail1 = contactMail1;
        this.contactPerson2 = contactPerson2;
        this.contactPhone2 = contactPhone2;
        this.contactMail2 = contactMail2;
        this.zipCd = zipCd;
        this.fax = fax;
        this.stCd = stCd;
        this.agentSt = agentSt;
        this.settlePeriod = settlePeriod;
        this.comment = comment;
        this.lastOperId = lastOperId;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
        this.deposit = deposit;
        this.password = password;
    }

    /**
     * Database table : tbl_agent_info
     *
     * @mbg.generated
     */
    public AgentInfo() {
        super();
    }

    /**
     * 代理商或中人编号
     * @return agent_cd 代理商或中人编号
     *
     * @mbg.generated
     */
    public String getAgentCd() {
        return agentCd;
    }

    /**
     * 代理商或中人编号
     * @param agentCd 代理商或中人编号
     *
     * @mbg.generated
     */
    public void setAgentCd(String agentCd) {
        this.agentCd = agentCd;
    }

    /**
     * 代理人(中人)类别: 1=自营，2=商户代理人，3=渠道代理人，0=未定义
     * @return agent_type 代理人(中人)类别: 1=自营，2=商户代理人，3=渠道代理人，0=未定义
     *
     * @mbg.generated
     */
    public String getAgentType() {
        return agentType;
    }

    /**
     * 代理人(中人)类别: 1=自营，2=商户代理人，3=渠道代理人，0=未定义
     * @param agentType 代理人(中人)类别: 1=自营，2=商户代理人，3=渠道代理人，0=未定义
     *
     * @mbg.generated
     */
    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    /**
     * 中文名称
     * @return agent_cn_nm 中文名称
     *
     * @mbg.generated
     */
    public String getAgentCnNm() {
        return agentCnNm;
    }

    /**
     * 中文名称
     * @param agentCnNm 中文名称
     *
     * @mbg.generated
     */
    public void setAgentCnNm(String agentCnNm) {
        this.agentCnNm = agentCnNm;
    }

    /**
     * 英文名称
     * @return agent_en_nm 英文名称
     *
     * @mbg.generated
     */
    public String getAgentEnNm() {
        return agentEnNm;
    }

    /**
     * 英文名称
     * @param agentEnNm 英文名称
     *
     * @mbg.generated
     */
    public void setAgentEnNm(String agentEnNm) {
        this.agentEnNm = agentEnNm;
    }

    /**
     * 中文缩写
     * @return agent_cn_abbr 中文缩写
     *
     * @mbg.generated
     */
    public String getAgentCnAbbr() {
        return agentCnAbbr;
    }

    /**
     * 中文缩写
     * @param agentCnAbbr 中文缩写
     *
     * @mbg.generated
     */
    public void setAgentCnAbbr(String agentCnAbbr) {
        this.agentCnAbbr = agentCnAbbr;
    }

    /**
     * 英文缩写
     * @return agent_en_abbr 英文缩写
     *
     * @mbg.generated
     */
    public String getAgentEnAbbr() {
        return agentEnAbbr;
    }

    /**
     * 英文缩写
     * @param agentEnAbbr 英文缩写
     *
     * @mbg.generated
     */
    public void setAgentEnAbbr(String agentEnAbbr) {
        this.agentEnAbbr = agentEnAbbr;
    }

    /**
     * 区域编号
     * @return area_cd 区域编号
     *
     * @mbg.generated
     */
    public String getAreaCd() {
        return areaCd;
    }

    /**
     * 区域编号
     * @param areaCd 区域编号
     *
     * @mbg.generated
     */
    public void setAreaCd(String areaCd) {
        this.areaCd = areaCd;
    }

    /**
     * 地址
     * @return agent_addr 地址
     *
     * @mbg.generated
     */
    public String getAgentAddr() {
        return agentAddr;
    }

    /**
     * 地址
     * @param agentAddr 地址
     *
     * @mbg.generated
     */
    public void setAgentAddr(String agentAddr) {
        this.agentAddr = agentAddr;
    }

    /**
     * 联系人
     * @return contact_person1 联系人
     *
     * @mbg.generated
     */
    public String getContactPerson1() {
        return contactPerson1;
    }

    /**
     * 联系人
     * @param contactPerson1 联系人
     *
     * @mbg.generated
     */
    public void setContactPerson1(String contactPerson1) {
        this.contactPerson1 = contactPerson1;
    }

    /**
     * 联系电话
     * @return contact_phone1 联系电话
     *
     * @mbg.generated
     */
    public String getContactPhone1() {
        return contactPhone1;
    }

    /**
     * 联系电话
     * @param contactPhone1 联系电话
     *
     * @mbg.generated
     */
    public void setContactPhone1(String contactPhone1) {
        this.contactPhone1 = contactPhone1;
    }

    /**
     * 联系电邮
     * @return contact_mail1 联系电邮
     *
     * @mbg.generated
     */
    public String getContactMail1() {
        return contactMail1;
    }

    /**
     * 联系电邮
     * @param contactMail1 联系电邮
     *
     * @mbg.generated
     */
    public void setContactMail1(String contactMail1) {
        this.contactMail1 = contactMail1;
    }

    /**
     * 联系人2
     * @return contact_person2 联系人2
     *
     * @mbg.generated
     */
    public String getContactPerson2() {
        return contactPerson2;
    }

    /**
     * 联系人2
     * @param contactPerson2 联系人2
     *
     * @mbg.generated
     */
    public void setContactPerson2(String contactPerson2) {
        this.contactPerson2 = contactPerson2;
    }

    /**
     * 联系电话2
     * @return contact_phone2 联系电话2
     *
     * @mbg.generated
     */
    public String getContactPhone2() {
        return contactPhone2;
    }

    /**
     * 联系电话2
     * @param contactPhone2 联系电话2
     *
     * @mbg.generated
     */
    public void setContactPhone2(String contactPhone2) {
        this.contactPhone2 = contactPhone2;
    }

    /**
     * 联系电邮2
     * @return contact_mail2 联系电邮2
     *
     * @mbg.generated
     */
    public String getContactMail2() {
        return contactMail2;
    }

    /**
     * 联系电邮2
     * @param contactMail2 联系电邮2
     *
     * @mbg.generated
     */
    public void setContactMail2(String contactMail2) {
        this.contactMail2 = contactMail2;
    }

    /**
     * 邮编
     * @return zip_cd 邮编
     *
     * @mbg.generated
     */
    public String getZipCd() {
        return zipCd;
    }

    /**
     * 邮编
     * @param zipCd 邮编
     *
     * @mbg.generated
     */
    public void setZipCd(String zipCd) {
        this.zipCd = zipCd;
    }

    /**
     * 传真
     * @return fax 传真
     *
     * @mbg.generated
     */
    public String getFax() {
        return fax;
    }

    /**
     * 传真
     * @param fax 传真
     *
     * @mbg.generated
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 支持的清算类型。目前只有第一位有效。(暂不使用)
     * @return st_cd 支持的清算类型。目前只有第一位有效。(暂不使用)
     *
     * @mbg.generated
     */
    public String getStCd() {
        return stCd;
    }

    /**
     * 支持的清算类型。目前只有第一位有效。(暂不使用)
     * @param stCd 支持的清算类型。目前只有第一位有效。(暂不使用)
     *
     * @mbg.generated
     */
    public void setStCd(String stCd) {
        this.stCd = stCd;
    }

    /**
     * 有效状态：1=有效，其他无效。
     * @return agent_st 有效状态：1=有效，其他无效。
     *
     * @mbg.generated
     */
    public String getAgentSt() {
        return agentSt;
    }

    /**
     * 有效状态：1=有效，其他无效。
     * @param agentSt 有效状态：1=有效，其他无效。
     *
     * @mbg.generated
     */
    public void setAgentSt(String agentSt) {
        this.agentSt = agentSt;
    }

    /**
     * 清算周期,0=T0,1=T1,2=T2,3=T3,9=D0
     * @return settle_period 清算周期,0=T0,1=T1,2=T2,3=T3,9=D0
     *
     * @mbg.generated
     */
    public String getSettlePeriod() {
        return settlePeriod;
    }

    /**
     * 清算周期,0=T0,1=T1,2=T2,3=T3,9=D0
     * @param settlePeriod 清算周期,0=T0,1=T1,2=T2,3=T3,9=D0
     *
     * @mbg.generated
     */
    public void setSettlePeriod(String settlePeriod) {
        this.settlePeriod = settlePeriod;
    }

    /**
     * 备注
     * @return comment 备注
     *
     * @mbg.generated
     */
    public String getComment() {
        return comment;
    }

    /**
     * 备注
     * @param comment 备注
     *
     * @mbg.generated
     */
    public void setComment(String comment) {
        this.comment = comment;
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
     * 分润状态：1=是，其他否。
     * @return deposit 分润状态：1=是，其他否。
     *
     * @mbg.generated
     */
    public String getDeposit() {
        return deposit;
    }

    /**
     * 分润状态：1=是，其他否。
     * @param deposit 分润状态：1=是，其他否。
     *
     * @mbg.generated
     */
    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    /**
     * @return password 
     *
     * @mbg.generated
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password 
     *
     * @mbg.generated
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Database table : tbl_agent_info
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
        AgentInfo other = (AgentInfo) that;
        return (this.getAgentCd() == null ? other.getAgentCd() == null : this.getAgentCd().equals(other.getAgentCd()))
            && (this.getAgentType() == null ? other.getAgentType() == null : this.getAgentType().equals(other.getAgentType()))
            && (this.getAgentCnNm() == null ? other.getAgentCnNm() == null : this.getAgentCnNm().equals(other.getAgentCnNm()))
            && (this.getAgentEnNm() == null ? other.getAgentEnNm() == null : this.getAgentEnNm().equals(other.getAgentEnNm()))
            && (this.getAgentCnAbbr() == null ? other.getAgentCnAbbr() == null : this.getAgentCnAbbr().equals(other.getAgentCnAbbr()))
            && (this.getAgentEnAbbr() == null ? other.getAgentEnAbbr() == null : this.getAgentEnAbbr().equals(other.getAgentEnAbbr()))
            && (this.getAreaCd() == null ? other.getAreaCd() == null : this.getAreaCd().equals(other.getAreaCd()))
            && (this.getAgentAddr() == null ? other.getAgentAddr() == null : this.getAgentAddr().equals(other.getAgentAddr()))
            && (this.getContactPerson1() == null ? other.getContactPerson1() == null : this.getContactPerson1().equals(other.getContactPerson1()))
            && (this.getContactPhone1() == null ? other.getContactPhone1() == null : this.getContactPhone1().equals(other.getContactPhone1()))
            && (this.getContactMail1() == null ? other.getContactMail1() == null : this.getContactMail1().equals(other.getContactMail1()))
            && (this.getContactPerson2() == null ? other.getContactPerson2() == null : this.getContactPerson2().equals(other.getContactPerson2()))
            && (this.getContactPhone2() == null ? other.getContactPhone2() == null : this.getContactPhone2().equals(other.getContactPhone2()))
            && (this.getContactMail2() == null ? other.getContactMail2() == null : this.getContactMail2().equals(other.getContactMail2()))
            && (this.getZipCd() == null ? other.getZipCd() == null : this.getZipCd().equals(other.getZipCd()))
            && (this.getFax() == null ? other.getFax() == null : this.getFax().equals(other.getFax()))
            && (this.getStCd() == null ? other.getStCd() == null : this.getStCd().equals(other.getStCd()))
            && (this.getAgentSt() == null ? other.getAgentSt() == null : this.getAgentSt().equals(other.getAgentSt()))
            && (this.getSettlePeriod() == null ? other.getSettlePeriod() == null : this.getSettlePeriod().equals(other.getSettlePeriod()))
            && (this.getComment() == null ? other.getComment() == null : this.getComment().equals(other.getComment()))
            && (this.getLastOperId() == null ? other.getLastOperId() == null : this.getLastOperId().equals(other.getLastOperId()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()))
            && (this.getDeposit() == null ? other.getDeposit() == null : this.getDeposit().equals(other.getDeposit()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()));
    }

    /**
     * Database table : tbl_agent_info
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAgentCd() == null) ? 0 : getAgentCd().hashCode());
        result = prime * result + ((getAgentType() == null) ? 0 : getAgentType().hashCode());
        result = prime * result + ((getAgentCnNm() == null) ? 0 : getAgentCnNm().hashCode());
        result = prime * result + ((getAgentEnNm() == null) ? 0 : getAgentEnNm().hashCode());
        result = prime * result + ((getAgentCnAbbr() == null) ? 0 : getAgentCnAbbr().hashCode());
        result = prime * result + ((getAgentEnAbbr() == null) ? 0 : getAgentEnAbbr().hashCode());
        result = prime * result + ((getAreaCd() == null) ? 0 : getAreaCd().hashCode());
        result = prime * result + ((getAgentAddr() == null) ? 0 : getAgentAddr().hashCode());
        result = prime * result + ((getContactPerson1() == null) ? 0 : getContactPerson1().hashCode());
        result = prime * result + ((getContactPhone1() == null) ? 0 : getContactPhone1().hashCode());
        result = prime * result + ((getContactMail1() == null) ? 0 : getContactMail1().hashCode());
        result = prime * result + ((getContactPerson2() == null) ? 0 : getContactPerson2().hashCode());
        result = prime * result + ((getContactPhone2() == null) ? 0 : getContactPhone2().hashCode());
        result = prime * result + ((getContactMail2() == null) ? 0 : getContactMail2().hashCode());
        result = prime * result + ((getZipCd() == null) ? 0 : getZipCd().hashCode());
        result = prime * result + ((getFax() == null) ? 0 : getFax().hashCode());
        result = prime * result + ((getStCd() == null) ? 0 : getStCd().hashCode());
        result = prime * result + ((getAgentSt() == null) ? 0 : getAgentSt().hashCode());
        result = prime * result + ((getSettlePeriod() == null) ? 0 : getSettlePeriod().hashCode());
        result = prime * result + ((getComment() == null) ? 0 : getComment().hashCode());
        result = prime * result + ((getLastOperId() == null) ? 0 : getLastOperId().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        result = prime * result + ((getDeposit() == null) ? 0 : getDeposit().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        return result;
    }

    /**
     * Database table : tbl_agent_info
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", agentCd=").append(agentCd);
        sb.append(", agentType=").append(agentType);
        sb.append(", agentCnNm=").append(agentCnNm);
        sb.append(", agentEnNm=").append(agentEnNm);
        sb.append(", agentCnAbbr=").append(agentCnAbbr);
        sb.append(", agentEnAbbr=").append(agentEnAbbr);
        sb.append(", areaCd=").append(areaCd);
        sb.append(", agentAddr=").append(agentAddr);
        sb.append(", contactPerson1=").append(contactPerson1);
        sb.append(", contactPhone1=").append(contactPhone1);
        sb.append(", contactMail1=").append(contactMail1);
        sb.append(", contactPerson2=").append(contactPerson2);
        sb.append(", contactPhone2=").append(contactPhone2);
        sb.append(", contactMail2=").append(contactMail2);
        sb.append(", zipCd=").append(zipCd);
        sb.append(", fax=").append(fax);
        sb.append(", stCd=").append(stCd);
        sb.append(", agentSt=").append(agentSt);
        sb.append(", settlePeriod=").append(settlePeriod);
        sb.append(", comment=").append(comment);
        sb.append(", lastOperId=").append(lastOperId);
        sb.append(", recCrtTs=").append(recCrtTs);
        sb.append(", recUpdTs=").append(recUpdTs);
        sb.append(", deposit=").append(deposit);
        sb.append(", password=").append(password);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(AgentInfo source) {
        this.agentCd=source.agentCd;
        this.agentType=source.agentType;
        this.agentCnNm=source.agentCnNm;
        this.agentEnNm=source.agentEnNm;
        this.agentCnAbbr=source.agentCnAbbr;
        this.agentEnAbbr=source.agentEnAbbr;
        this.areaCd=source.areaCd;
        this.agentAddr=source.agentAddr;
        this.contactPerson1=source.contactPerson1;
        this.contactPhone1=source.contactPhone1;
        this.contactMail1=source.contactMail1;
        this.contactPerson2=source.contactPerson2;
        this.contactPhone2=source.contactPhone2;
        this.contactMail2=source.contactMail2;
        this.zipCd=source.zipCd;
        this.fax=source.fax;
        this.stCd=source.stCd;
        this.agentSt=source.agentSt;
        this.settlePeriod=source.settlePeriod;
        this.comment=source.comment;
        this.lastOperId=source.lastOperId;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
        this.deposit=source.deposit;
        this.password=source.password;
    }
}