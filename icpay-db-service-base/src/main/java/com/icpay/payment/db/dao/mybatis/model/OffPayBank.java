package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_off_pay_bank
 * @author user
 *
 * @mbg.generated
 */
public class OffPayBank extends OffPayBankKey implements Serializable {
    /**
     * 渠道编号
     * Database column : tbl_off_pay_bank.chnl_id
     *
     * @mbg.generated
     */
    private String chnlId;

    /**
     * 渠道商户号
     * Database column : tbl_off_pay_bank.chnl_mchnt_cd
     *
     * @mbg.generated
     */
    private String chnlMchntCd;

    /**
     * 银行名称
     * Database column : tbl_off_pay_bank.bank_name
     *
     * @mbg.generated
     */
    private String bankName;

    /**
     * 帐户名称
     * Database column : tbl_off_pay_bank.acc_name
     *
     * @mbg.generated
     */
    private String accName;

    /**
     * 开户行
     * Database column : tbl_off_pay_bank.account_bank_name
     *
     * @mbg.generated
     */
    private String accountBankName;

    /**
     * 状态(0=无效,1=有效)
     * Database column : tbl_off_pay_bank.state
     *
     * @mbg.generated
     */
    private String state;

    /**
     * 最后操作人员
     * Database column : tbl_off_pay_bank.last_oper_id
     *
     * @mbg.generated
     */
    private String lastOperId;

    /**
     * 最近更新时间
     * Database column : tbl_off_pay_bank.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_off_pay_bank
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_off_pay_bank
     *
     * @mbg.generated
     */
    public OffPayBank(String catalog, String accNo, String chnlId, String chnlMchntCd, String bankName, String accName, String accountBankName, String state, String lastOperId, Date recUpdTs) {
        super(catalog, accNo);
        this.chnlId = chnlId;
        this.chnlMchntCd = chnlMchntCd;
        this.bankName = bankName;
        this.accName = accName;
        this.accountBankName = accountBankName;
        this.state = state;
        this.lastOperId = lastOperId;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_off_pay_bank
     *
     * @mbg.generated
     */
    public OffPayBank() {
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
     * 银行名称
     * @return bank_name 银行名称
     *
     * @mbg.generated
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 银行名称
     * @param bankName 银行名称
     *
     * @mbg.generated
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 帐户名称
     * @return acc_name 帐户名称
     *
     * @mbg.generated
     */
    public String getAccName() {
        return accName;
    }

    /**
     * 帐户名称
     * @param accName 帐户名称
     *
     * @mbg.generated
     */
    public void setAccName(String accName) {
        this.accName = accName;
    }

    /**
     * 开户行
     * @return account_bank_name 开户行
     *
     * @mbg.generated
     */
    public String getAccountBankName() {
        return accountBankName;
    }

    /**
     * 开户行
     * @param accountBankName 开户行
     *
     * @mbg.generated
     */
    public void setAccountBankName(String accountBankName) {
        this.accountBankName = accountBankName;
    }

    /**
     * 状态(0=无效,1=有效)
     * @return state 状态(0=无效,1=有效)
     *
     * @mbg.generated
     */
    public String getState() {
        return state;
    }

    /**
     * 状态(0=无效,1=有效)
     * @param state 状态(0=无效,1=有效)
     *
     * @mbg.generated
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 最后操作人员
     * @return last_oper_id 最后操作人员
     *
     * @mbg.generated
     */
    public String getLastOperId() {
        return lastOperId;
    }

    /**
     * 最后操作人员
     * @param lastOperId 最后操作人员
     *
     * @mbg.generated
     */
    public void setLastOperId(String lastOperId) {
        this.lastOperId = lastOperId;
    }

    /**
     * 最近更新时间
     * @return rec_upd_ts 最近更新时间
     *
     * @mbg.generated
     */
    public Date getRecUpdTs() {
        return recUpdTs;
    }

    /**
     * 最近更新时间
     * @param recUpdTs 最近更新时间
     *
     * @mbg.generated
     */
    public void setRecUpdTs(Date recUpdTs) {
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_off_pay_bank
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
        OffPayBank other = (OffPayBank) that;
        return (this.getCatalog() == null ? other.getCatalog() == null : this.getCatalog().equals(other.getCatalog()))
            && (this.getAccNo() == null ? other.getAccNo() == null : this.getAccNo().equals(other.getAccNo()))
            && (this.getChnlId() == null ? other.getChnlId() == null : this.getChnlId().equals(other.getChnlId()))
            && (this.getChnlMchntCd() == null ? other.getChnlMchntCd() == null : this.getChnlMchntCd().equals(other.getChnlMchntCd()))
            && (this.getBankName() == null ? other.getBankName() == null : this.getBankName().equals(other.getBankName()))
            && (this.getAccName() == null ? other.getAccName() == null : this.getAccName().equals(other.getAccName()))
            && (this.getAccountBankName() == null ? other.getAccountBankName() == null : this.getAccountBankName().equals(other.getAccountBankName()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getLastOperId() == null ? other.getLastOperId() == null : this.getLastOperId().equals(other.getLastOperId()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_off_pay_bank
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCatalog() == null) ? 0 : getCatalog().hashCode());
        result = prime * result + ((getAccNo() == null) ? 0 : getAccNo().hashCode());
        result = prime * result + ((getChnlId() == null) ? 0 : getChnlId().hashCode());
        result = prime * result + ((getChnlMchntCd() == null) ? 0 : getChnlMchntCd().hashCode());
        result = prime * result + ((getBankName() == null) ? 0 : getBankName().hashCode());
        result = prime * result + ((getAccName() == null) ? 0 : getAccName().hashCode());
        result = prime * result + ((getAccountBankName() == null) ? 0 : getAccountBankName().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getLastOperId() == null) ? 0 : getLastOperId().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_off_pay_bank
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
        sb.append(", chnlId=").append(chnlId);
        sb.append(", chnlMchntCd=").append(chnlMchntCd);
        sb.append(", bankName=").append(bankName);
        sb.append(", accName=").append(accName);
        sb.append(", accountBankName=").append(accountBankName);
        sb.append(", state=").append(state);
        sb.append(", lastOperId=").append(lastOperId);
        sb.append(", recUpdTs=").append(recUpdTs);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Database table : tbl_off_pay_bank
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
    public void cloneFrom(OffPayBank source) {
        super.cloneFrom(source);
        this.chnlId=source.chnlId;
        this.chnlMchntCd=source.chnlMchntCd;
        this.bankName=source.bankName;
        this.accName=source.accName;
        this.accountBankName=source.accountBankName;
        this.state=source.state;
        this.lastOperId=source.lastOperId;
        this.recUpdTs=source.recUpdTs;
    }
}