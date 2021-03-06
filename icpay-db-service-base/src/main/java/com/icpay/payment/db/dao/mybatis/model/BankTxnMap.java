package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_bank_txn_map

 *
 * @mbg.generated
 */
public class BankTxnMap extends BankTxnMapKey implements Serializable {
    /**
     * 排序用，由小至大
     * Database column : tbl_bank_txn_map.sort_seq
     *
     * @mbg.generated
     */
    private Integer sortSeq;

    /**
     * 标签
     * Database column : tbl_bank_txn_map.tags
     *
     * @mbg.generated
     */
    private String tags;

    /**
     * Database column : tbl_bank_txn_map.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_bank_txn_map.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_bank_txn_map
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_bank_txn_map
     *
     * @mbg.generated
     */
    public BankTxnMap(String intTransCd, String bankNum, Integer sortSeq, String tags, Date recCrtTs, Date recUpdTs) {
        super(intTransCd, bankNum);
        this.sortSeq = sortSeq;
        this.tags = tags;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_bank_txn_map
     *
     * @mbg.generated
     */
    public BankTxnMap() {
        super();
    }

    /**
     * 排序用，由小至大
     * @return sort_seq 排序用，由小至大
     *
     * @mbg.generated
     */
    public Integer getSortSeq() {
        return sortSeq;
    }

    /**
     * 排序用，由小至大
     * @param sortSeq 排序用，由小至大
     *
     * @mbg.generated
     */
    public void setSortSeq(Integer sortSeq) {
        this.sortSeq = sortSeq;
    }

    /**
     * 标签
     * @return tags 标签
     *
     * @mbg.generated
     */
    public String getTags() {
        return tags;
    }

    /**
     * 标签
     * @param tags 标签
     *
     * @mbg.generated
     */
    public void setTags(String tags) {
        this.tags = tags;
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
     * Database table : tbl_bank_txn_map
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
        BankTxnMap other = (BankTxnMap) that;
        return (this.getIntTransCd() == null ? other.getIntTransCd() == null : this.getIntTransCd().equals(other.getIntTransCd()))
            && (this.getBankNum() == null ? other.getBankNum() == null : this.getBankNum().equals(other.getBankNum()))
            && (this.getSortSeq() == null ? other.getSortSeq() == null : this.getSortSeq().equals(other.getSortSeq()))
            && (this.getTags() == null ? other.getTags() == null : this.getTags().equals(other.getTags()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_bank_txn_map
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIntTransCd() == null) ? 0 : getIntTransCd().hashCode());
        result = prime * result + ((getBankNum() == null) ? 0 : getBankNum().hashCode());
        result = prime * result + ((getSortSeq() == null) ? 0 : getSortSeq().hashCode());
        result = prime * result + ((getTags() == null) ? 0 : getTags().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_bank_txn_map
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
        sb.append(", sortSeq=").append(sortSeq);
        sb.append(", tags=").append(tags);
        sb.append(", recCrtTs=").append(recCrtTs);
        sb.append(", recUpdTs=").append(recUpdTs);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Database table : tbl_bank_txn_map
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
    public void cloneFrom(BankTxnMap source) {
        super.cloneFrom(source);
        this.sortSeq=source.sortSeq;
        this.tags=source.tags;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
    }
}