package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_agent_profit_info

 *
 * @mbg.generated
 */
public class AgentProfitInfo extends AgentProfitInfoKey implements Serializable {
    /**
     * 打款帐号户名
     * Database column : tbl_agent_profit_info.account_name
     *
     * @mbg.generated
     */
    private String accountName;

    /**
     * 打款行联号
     * Database column : tbl_agent_profit_info.account_bank_code
     *
     * @mbg.generated
     */
    private String accountBankCode;

    /**
     * 状态(1=有效,其他无效)
     * Database column : tbl_agent_profit_info.state
     *
     * @mbg.generated
     */
    private String state;

    /**
     * Database column : tbl_agent_profit_info.sort_order
     *
     * @mbg.generated
     */
    private Integer sortOrder;

    /**
     * 打款银行名称
     * Database column : tbl_agent_profit_info.account_bank_name
     *
     * @mbg.generated
     */
    private String accountBankName;

    /**
     * 地区代码
     * Database column : tbl_agent_profit_info.account_area_code
     *
     * @mbg.generated
     */
    private String accountAreaCode;

    /**
     * 地区说明
     * Database column : tbl_agent_profit_info.account_area_info
     *
     * @mbg.generated
     */
    private String accountAreaInfo;

    /**
     * 暂不使用
     * Database column : tbl_agent_profit_info.profit_period
     *
     * @mbg.generated
     */
    private String profitPeriod;

    /**
     * 备注
     * Database column : tbl_agent_profit_info.comment
     *
     * @mbg.generated
     */
    private String comment;

    /**
     * 系统验证码(验证数据是否被窜改)
     * Database column : tbl_agent_profit_info.sys_mac
     *
     * @mbg.generated
     */
    private String sysMac;

    /**
     * Database column : tbl_agent_profit_info.last_oper_id
     *
     * @mbg.generated
     */
    private String lastOperId;

    /**
     * Database column : tbl_agent_profit_info.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_agent_profit_info.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_agent_profit_info
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_agent_profit_info
     *
     * @mbg.generated
     */
    public AgentProfitInfo(String agentCd, String accountNo, String accountName, String accountBankCode, String state, Integer sortOrder, String accountBankName, String accountAreaCode, String accountAreaInfo, String profitPeriod, String comment, String sysMac, String lastOperId, Date recCrtTs, Date recUpdTs) {
        super(agentCd, accountNo);
        this.accountName = accountName;
        this.accountBankCode = accountBankCode;
        this.state = state;
        this.sortOrder = sortOrder;
        this.accountBankName = accountBankName;
        this.accountAreaCode = accountAreaCode;
        this.accountAreaInfo = accountAreaInfo;
        this.profitPeriod = profitPeriod;
        this.comment = comment;
        this.sysMac = sysMac;
        this.lastOperId = lastOperId;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_agent_profit_info
     *
     * @mbg.generated
     */
    public AgentProfitInfo() {
        super();
    }

    /**
     * 打款帐号户名
     * @return account_name 打款帐号户名
     *
     * @mbg.generated
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 打款帐号户名
     * @param accountName 打款帐号户名
     *
     * @mbg.generated
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 打款行联号
     * @return account_bank_code 打款行联号
     *
     * @mbg.generated
     */
    public String getAccountBankCode() {
        return accountBankCode;
    }

    /**
     * 打款行联号
     * @param accountBankCode 打款行联号
     *
     * @mbg.generated
     */
    public void setAccountBankCode(String accountBankCode) {
        this.accountBankCode = accountBankCode;
    }

    /**
     * 状态(1=有效,其他无效)
     * @return state 状态(1=有效,其他无效)
     *
     * @mbg.generated
     */
    public String getState() {
        return state;
    }

    /**
     * 状态(1=有效,其他无效)
     * @param state 状态(1=有效,其他无效)
     *
     * @mbg.generated
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return sort_order 
     *
     * @mbg.generated
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder 
     *
     * @mbg.generated
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * 打款银行名称
     * @return account_bank_name 打款银行名称
     *
     * @mbg.generated
     */
    public String getAccountBankName() {
        return accountBankName;
    }

    /**
     * 打款银行名称
     * @param accountBankName 打款银行名称
     *
     * @mbg.generated
     */
    public void setAccountBankName(String accountBankName) {
        this.accountBankName = accountBankName;
    }

    /**
     * 地区代码
     * @return account_area_code 地区代码
     *
     * @mbg.generated
     */
    public String getAccountAreaCode() {
        return accountAreaCode;
    }

    /**
     * 地区代码
     * @param accountAreaCode 地区代码
     *
     * @mbg.generated
     */
    public void setAccountAreaCode(String accountAreaCode) {
        this.accountAreaCode = accountAreaCode;
    }

    /**
     * 地区说明
     * @return account_area_info 地区说明
     *
     * @mbg.generated
     */
    public String getAccountAreaInfo() {
        return accountAreaInfo;
    }

    /**
     * 地区说明
     * @param accountAreaInfo 地区说明
     *
     * @mbg.generated
     */
    public void setAccountAreaInfo(String accountAreaInfo) {
        this.accountAreaInfo = accountAreaInfo;
    }

    /**
     * 暂不使用
     * @return profit_period 暂不使用
     *
     * @mbg.generated
     */
    public String getProfitPeriod() {
        return profitPeriod;
    }

    /**
     * 暂不使用
     * @param profitPeriod 暂不使用
     *
     * @mbg.generated
     */
    public void setProfitPeriod(String profitPeriod) {
        this.profitPeriod = profitPeriod;
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
     * 系统验证码(验证数据是否被窜改)
     * @return sys_mac 系统验证码(验证数据是否被窜改)
     *
     * @mbg.generated
     */
    public String getSysMac() {
        return sysMac;
    }

    /**
     * 系统验证码(验证数据是否被窜改)
     * @param sysMac 系统验证码(验证数据是否被窜改)
     *
     * @mbg.generated
     */
    public void setSysMac(String sysMac) {
        this.sysMac = sysMac;
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
     * Database table : tbl_agent_profit_info
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
        AgentProfitInfo other = (AgentProfitInfo) that;
        return (this.getAgentCd() == null ? other.getAgentCd() == null : this.getAgentCd().equals(other.getAgentCd()))
            && (this.getAccountNo() == null ? other.getAccountNo() == null : this.getAccountNo().equals(other.getAccountNo()))
            && (this.getAccountName() == null ? other.getAccountName() == null : this.getAccountName().equals(other.getAccountName()))
            && (this.getAccountBankCode() == null ? other.getAccountBankCode() == null : this.getAccountBankCode().equals(other.getAccountBankCode()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getSortOrder() == null ? other.getSortOrder() == null : this.getSortOrder().equals(other.getSortOrder()))
            && (this.getAccountBankName() == null ? other.getAccountBankName() == null : this.getAccountBankName().equals(other.getAccountBankName()))
            && (this.getAccountAreaCode() == null ? other.getAccountAreaCode() == null : this.getAccountAreaCode().equals(other.getAccountAreaCode()))
            && (this.getAccountAreaInfo() == null ? other.getAccountAreaInfo() == null : this.getAccountAreaInfo().equals(other.getAccountAreaInfo()))
            && (this.getProfitPeriod() == null ? other.getProfitPeriod() == null : this.getProfitPeriod().equals(other.getProfitPeriod()))
            && (this.getComment() == null ? other.getComment() == null : this.getComment().equals(other.getComment()))
            && (this.getSysMac() == null ? other.getSysMac() == null : this.getSysMac().equals(other.getSysMac()))
            && (this.getLastOperId() == null ? other.getLastOperId() == null : this.getLastOperId().equals(other.getLastOperId()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_agent_profit_info
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAgentCd() == null) ? 0 : getAgentCd().hashCode());
        result = prime * result + ((getAccountNo() == null) ? 0 : getAccountNo().hashCode());
        result = prime * result + ((getAccountName() == null) ? 0 : getAccountName().hashCode());
        result = prime * result + ((getAccountBankCode() == null) ? 0 : getAccountBankCode().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getSortOrder() == null) ? 0 : getSortOrder().hashCode());
        result = prime * result + ((getAccountBankName() == null) ? 0 : getAccountBankName().hashCode());
        result = prime * result + ((getAccountAreaCode() == null) ? 0 : getAccountAreaCode().hashCode());
        result = prime * result + ((getAccountAreaInfo() == null) ? 0 : getAccountAreaInfo().hashCode());
        result = prime * result + ((getProfitPeriod() == null) ? 0 : getProfitPeriod().hashCode());
        result = prime * result + ((getComment() == null) ? 0 : getComment().hashCode());
        result = prime * result + ((getSysMac() == null) ? 0 : getSysMac().hashCode());
        result = prime * result + ((getLastOperId() == null) ? 0 : getLastOperId().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_agent_profit_info
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(getSuperToString());
        sb.append(", accountName=").append(accountName);
        sb.append(", accountBankCode=").append(accountBankCode);
        sb.append(", state=").append(state);
        sb.append(", sortOrder=").append(sortOrder);
        sb.append(", accountBankName=").append(accountBankName);
        sb.append(", accountAreaCode=").append(accountAreaCode);
        sb.append(", accountAreaInfo=").append(accountAreaInfo);
        sb.append(", profitPeriod=").append(profitPeriod);
        sb.append(", comment=").append(comment);
        sb.append(", sysMac=").append(sysMac);
        sb.append(", lastOperId=").append(lastOperId);
        sb.append(", recCrtTs=").append(recCrtTs);
        sb.append(", recUpdTs=").append(recUpdTs);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Database table : tbl_agent_profit_info
     *
     * @mbg.generated
     */
    private String getSuperToString() {
        String s = super.toString();
        String superCls = super.getClass().getSimpleName();
        if (!(s.contains("[Hash=") && s.contains(superCls))) return "";
        int end=-1;
        int start = s.indexOf("[Hash=");
        if (start>=0) {
            	start = s.indexOf(", ", start);
            	if (start>=0) {
                		end = s.lastIndexOf(']');
                		if (end>0) 
                			return ", "+s.substring(start+2, end)+"";
                	}
            }
            return "";
        }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(AgentProfitInfo source) {
        super.cloneFrom(source);
        this.accountName=source.accountName;
        this.accountBankCode=source.accountBankCode;
        this.state=source.state;
        this.sortOrder=source.sortOrder;
        this.accountBankName=source.accountBankName;
        this.accountAreaCode=source.accountAreaCode;
        this.accountAreaInfo=source.accountAreaInfo;
        this.profitPeriod=source.profitPeriod;
        this.comment=source.comment;
        this.sysMac=source.sysMac;
        this.lastOperId=source.lastOperId;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
    }
}