package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_mchnt_term_info

 *
 * @mbg.generated
 */
public class MchntTermInfo extends MchntTermInfoKey implements Serializable {
    /**
     * Database column : tbl_mchnt_term_info.last_oper_id
     *
     * @mbg.generated
     */
    private String lastOperId;

    /**
     * Database column : tbl_mchnt_term_info.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_mchnt_term_info.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_mchnt_term_info
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_mchnt_term_info
     *
     * @mbg.generated
     */
    public MchntTermInfo(String termSn, String mchntCd, String lastOperId, Date recCrtTs, Date recUpdTs) {
        super(termSn, mchntCd);
        this.lastOperId = lastOperId;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_mchnt_term_info
     *
     * @mbg.generated
     */
    public MchntTermInfo() {
        super();
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
     * Database table : tbl_mchnt_term_info
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
        MchntTermInfo other = (MchntTermInfo) that;
        return (this.getTermSn() == null ? other.getTermSn() == null : this.getTermSn().equals(other.getTermSn()))
            && (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()))
            && (this.getLastOperId() == null ? other.getLastOperId() == null : this.getLastOperId().equals(other.getLastOperId()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_mchnt_term_info
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTermSn() == null) ? 0 : getTermSn().hashCode());
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        result = prime * result + ((getLastOperId() == null) ? 0 : getLastOperId().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_mchnt_term_info
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
        sb.append(", lastOperId=").append(lastOperId);
        sb.append(", recCrtTs=").append(recCrtTs);
        sb.append(", recUpdTs=").append(recUpdTs);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Database table : tbl_mchnt_term_info
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
    public void cloneFrom(MchntTermInfo source) {
        super.cloneFrom(source);
        this.lastOperId=source.lastOperId;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
    }
}