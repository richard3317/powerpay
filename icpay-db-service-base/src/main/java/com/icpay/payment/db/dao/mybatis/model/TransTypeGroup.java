package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_trans_type_group

 *
 * @mbg.generated
 */
public class TransTypeGroup implements Serializable {
    /**
     * Database column : tbl_trans_type_group.seq_id
     *
     * @mbg.generated
     */
    private Integer seqId;

    /**
     * Database column : tbl_trans_type_group.group_nm
     *
     * @mbg.generated
     */
    private String groupNm;

    /**
     * Database column : tbl_trans_type_group.trans_type
     *
     * @mbg.generated
     */
    private String transType;

    /**
     * Database column : tbl_trans_type_group.last_oper_id
     *
     * @mbg.generated
     */
    private String lastOperId;

    /**
     * Database column : tbl_trans_type_group.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_trans_type_group.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_trans_type_group
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_trans_type_group
     *
     * @mbg.generated
     */
    public TransTypeGroup(Integer seqId, String groupNm, String transType, String lastOperId, Date recCrtTs, Date recUpdTs) {
        this.seqId = seqId;
        this.groupNm = groupNm;
        this.transType = transType;
        this.lastOperId = lastOperId;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_trans_type_group
     *
     * @mbg.generated
     */
    public TransTypeGroup() {
        super();
    }

    /**
     * @return seq_id 
     *
     * @mbg.generated
     */
    public Integer getSeqId() {
        return seqId;
    }

    /**
     * @param seqId 
     *
     * @mbg.generated
     */
    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
    }

    /**
     * @return group_nm 
     *
     * @mbg.generated
     */
    public String getGroupNm() {
        return groupNm;
    }

    /**
     * @param groupNm 
     *
     * @mbg.generated
     */
    public void setGroupNm(String groupNm) {
        this.groupNm = groupNm;
    }

    /**
     * @return trans_type 
     *
     * @mbg.generated
     */
    public String getTransType() {
        return transType;
    }

    /**
     * @param transType 
     *
     * @mbg.generated
     */
    public void setTransType(String transType) {
        this.transType = transType;
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
     * Database table : tbl_trans_type_group
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
        TransTypeGroup other = (TransTypeGroup) that;
        return (this.getSeqId() == null ? other.getSeqId() == null : this.getSeqId().equals(other.getSeqId()))
            && (this.getGroupNm() == null ? other.getGroupNm() == null : this.getGroupNm().equals(other.getGroupNm()))
            && (this.getTransType() == null ? other.getTransType() == null : this.getTransType().equals(other.getTransType()))
            && (this.getLastOperId() == null ? other.getLastOperId() == null : this.getLastOperId().equals(other.getLastOperId()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_trans_type_group
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSeqId() == null) ? 0 : getSeqId().hashCode());
        result = prime * result + ((getGroupNm() == null) ? 0 : getGroupNm().hashCode());
        result = prime * result + ((getTransType() == null) ? 0 : getTransType().hashCode());
        result = prime * result + ((getLastOperId() == null) ? 0 : getLastOperId().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_trans_type_group
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", seqId=").append(seqId);
        sb.append(", groupNm=").append(groupNm);
        sb.append(", transType=").append(transType);
        sb.append(", lastOperId=").append(lastOperId);
        sb.append(", recCrtTs=").append(recCrtTs);
        sb.append(", recUpdTs=").append(recUpdTs);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(TransTypeGroup source) {
        this.seqId=source.seqId;
        this.groupNm=source.groupNm;
        this.transType=source.transType;
        this.lastOperId=source.lastOperId;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
    }
}