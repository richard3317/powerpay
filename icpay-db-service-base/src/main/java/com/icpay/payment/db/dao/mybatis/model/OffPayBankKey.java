package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_off_pay_bank
 * @author user
 *
 * @mbg.generated
 */
public class OffPayBankKey implements Serializable {
    /**
     * 分类
     * Database column : tbl_off_pay_bank.catalog
     *
     * @mbg.generated
     */
    private String catalog;

    /**
     * 银行卡号
     * Database column : tbl_off_pay_bank.acc_no
     *
     * @mbg.generated
     */
    private String accNo;

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
    public OffPayBankKey(String catalog, String accNo) {
        this.catalog = catalog;
        this.accNo = accNo;
    }

    /**
     * Database table : tbl_off_pay_bank
     *
     * @mbg.generated
     */
    public OffPayBankKey() {
        super();
    }

    /**
     * 分类
     * @return catalog 分类
     *
     * @mbg.generated
     */
    public String getCatalog() {
        return catalog;
    }

    /**
     * 分类
     * @param catalog 分类
     *
     * @mbg.generated
     */
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    /**
     * 银行卡号
     * @return acc_no 银行卡号
     *
     * @mbg.generated
     */
    public String getAccNo() {
        return accNo;
    }

    /**
     * 银行卡号
     * @param accNo 银行卡号
     *
     * @mbg.generated
     */
    public void setAccNo(String accNo) {
        this.accNo = accNo;
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
        OffPayBankKey other = (OffPayBankKey) that;
        return (this.getCatalog() == null ? other.getCatalog() == null : this.getCatalog().equals(other.getCatalog()))
            && (this.getAccNo() == null ? other.getAccNo() == null : this.getAccNo().equals(other.getAccNo()));
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
        sb.append(", catalog=").append(catalog);
        sb.append(", accNo=").append(accNo);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(OffPayBankKey source) {
        this.catalog=source.catalog;
        this.accNo=source.accNo;
    }
}