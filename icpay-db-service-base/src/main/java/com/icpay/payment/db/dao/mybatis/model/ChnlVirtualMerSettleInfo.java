package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_chnl_virtual_mer_settle_info

 *
 * @mbg.generated
 */
public class ChnlVirtualMerSettleInfo implements Serializable {
    /**
     * Database column : tbl_chnl_virtual_mer_settle_info.seq_id
     *
     * @mbg.generated
     */
    private Integer seqId;

    /**
     * 渠道编号
     * Database column : tbl_chnl_virtual_mer_settle_info.chnl_id
     *
     * @mbg.generated
     */
    private String chnlId;

    /**
     * Database column : tbl_chnl_virtual_mer_settle_info.mchnt_cd
     *
     * @mbg.generated
     */
    private String mchntCd;

    /**
     * 对账日期
     * Database column : tbl_chnl_virtual_mer_settle_info.settle_dt
     *
     * @mbg.generated
     */
    private String settleDt;

    /**
     * 对账条数
     * Database column : tbl_chnl_virtual_mer_settle_info.trans_num
     *
     * @mbg.generated
     */
    private Integer transNum;

    /**
     * 总金额
     * Database column : tbl_chnl_virtual_mer_settle_info.trans_at
     *
     * @mbg.generated
     */
    private Long transAt;

    /**
     * Database column : tbl_chnl_virtual_mer_settle_info.settle_at
     *
     * @mbg.generated
     */
    private Long settleAt;

    /**
     * 手续费总金额
     * Database column : tbl_chnl_virtual_mer_settle_info.fee_at
     *
     * @mbg.generated
     */
    private Long feeAt;

    /**
     * 文件路径
     * Database column : tbl_chnl_virtual_mer_settle_info.file_path
     *
     * @mbg.generated
     */
    private String filePath;

    /**
     * 状态：0-待确认，1-已确认
     * Database column : tbl_chnl_virtual_mer_settle_info.state
     *
     * @mbg.generated
     */
    private String state;

    /**
     * Database column : tbl_chnl_virtual_mer_settle_info.last_oper_id
     *
     * @mbg.generated
     */
    private String lastOperId;

    /**
     * Database column : tbl_chnl_virtual_mer_settle_info.comment
     *
     * @mbg.generated
     */
    private String comment;

    /**
     * Database column : tbl_chnl_virtual_mer_settle_info.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_chnl_virtual_mer_settle_info.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_chnl_virtual_mer_settle_info
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_chnl_virtual_mer_settle_info
     *
     * @mbg.generated
     */
    public ChnlVirtualMerSettleInfo(Integer seqId, String chnlId, String mchntCd, String settleDt, Integer transNum, Long transAt, Long settleAt, Long feeAt, String filePath, String state, String lastOperId, String comment, Date recCrtTs, Date recUpdTs) {
        this.seqId = seqId;
        this.chnlId = chnlId;
        this.mchntCd = mchntCd;
        this.settleDt = settleDt;
        this.transNum = transNum;
        this.transAt = transAt;
        this.settleAt = settleAt;
        this.feeAt = feeAt;
        this.filePath = filePath;
        this.state = state;
        this.lastOperId = lastOperId;
        this.comment = comment;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_chnl_virtual_mer_settle_info
     *
     * @mbg.generated
     */
    public ChnlVirtualMerSettleInfo() {
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
     * @return mchnt_cd 
     *
     * @mbg.generated
     */
    public String getMchntCd() {
        return mchntCd;
    }

    /**
     * @param mchntCd 
     *
     * @mbg.generated
     */
    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd;
    }

    /**
     * 对账日期
     * @return settle_dt 对账日期
     *
     * @mbg.generated
     */
    public String getSettleDt() {
        return settleDt;
    }

    /**
     * 对账日期
     * @param settleDt 对账日期
     *
     * @mbg.generated
     */
    public void setSettleDt(String settleDt) {
        this.settleDt = settleDt;
    }

    /**
     * 对账条数
     * @return trans_num 对账条数
     *
     * @mbg.generated
     */
    public Integer getTransNum() {
        return transNum;
    }

    /**
     * 对账条数
     * @param transNum 对账条数
     *
     * @mbg.generated
     */
    public void setTransNum(Integer transNum) {
        this.transNum = transNum;
    }

    /**
     * 总金额
     * @return trans_at 总金额
     *
     * @mbg.generated
     */
    public Long getTransAt() {
        return transAt;
    }

    /**
     * 总金额
     * @param transAt 总金额
     *
     * @mbg.generated
     */
    public void setTransAt(Long transAt) {
        this.transAt = transAt;
    }

    /**
     * @return settle_at 
     *
     * @mbg.generated
     */
    public Long getSettleAt() {
        return settleAt;
    }

    /**
     * @param settleAt 
     *
     * @mbg.generated
     */
    public void setSettleAt(Long settleAt) {
        this.settleAt = settleAt;
    }

    /**
     * 手续费总金额
     * @return fee_at 手续费总金额
     *
     * @mbg.generated
     */
    public Long getFeeAt() {
        return feeAt;
    }

    /**
     * 手续费总金额
     * @param feeAt 手续费总金额
     *
     * @mbg.generated
     */
    public void setFeeAt(Long feeAt) {
        this.feeAt = feeAt;
    }

    /**
     * 文件路径
     * @return file_path 文件路径
     *
     * @mbg.generated
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 文件路径
     * @param filePath 文件路径
     *
     * @mbg.generated
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 状态：0-待确认，1-已确认
     * @return state 状态：0-待确认，1-已确认
     *
     * @mbg.generated
     */
    public String getState() {
        return state;
    }

    /**
     * 状态：0-待确认，1-已确认
     * @param state 状态：0-待确认，1-已确认
     *
     * @mbg.generated
     */
    public void setState(String state) {
        this.state = state;
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
     * @return comment 
     *
     * @mbg.generated
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment 
     *
     * @mbg.generated
     */
    public void setComment(String comment) {
        this.comment = comment;
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
     * Database table : tbl_chnl_virtual_mer_settle_info
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
        ChnlVirtualMerSettleInfo other = (ChnlVirtualMerSettleInfo) that;
        return (this.getSeqId() == null ? other.getSeqId() == null : this.getSeqId().equals(other.getSeqId()))
            && (this.getChnlId() == null ? other.getChnlId() == null : this.getChnlId().equals(other.getChnlId()))
            && (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()))
            && (this.getSettleDt() == null ? other.getSettleDt() == null : this.getSettleDt().equals(other.getSettleDt()))
            && (this.getTransNum() == null ? other.getTransNum() == null : this.getTransNum().equals(other.getTransNum()))
            && (this.getTransAt() == null ? other.getTransAt() == null : this.getTransAt().equals(other.getTransAt()))
            && (this.getSettleAt() == null ? other.getSettleAt() == null : this.getSettleAt().equals(other.getSettleAt()))
            && (this.getFeeAt() == null ? other.getFeeAt() == null : this.getFeeAt().equals(other.getFeeAt()))
            && (this.getFilePath() == null ? other.getFilePath() == null : this.getFilePath().equals(other.getFilePath()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getLastOperId() == null ? other.getLastOperId() == null : this.getLastOperId().equals(other.getLastOperId()))
            && (this.getComment() == null ? other.getComment() == null : this.getComment().equals(other.getComment()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_chnl_virtual_mer_settle_info
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSeqId() == null) ? 0 : getSeqId().hashCode());
        result = prime * result + ((getChnlId() == null) ? 0 : getChnlId().hashCode());
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        result = prime * result + ((getSettleDt() == null) ? 0 : getSettleDt().hashCode());
        result = prime * result + ((getTransNum() == null) ? 0 : getTransNum().hashCode());
        result = prime * result + ((getTransAt() == null) ? 0 : getTransAt().hashCode());
        result = prime * result + ((getSettleAt() == null) ? 0 : getSettleAt().hashCode());
        result = prime * result + ((getFeeAt() == null) ? 0 : getFeeAt().hashCode());
        result = prime * result + ((getFilePath() == null) ? 0 : getFilePath().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getLastOperId() == null) ? 0 : getLastOperId().hashCode());
        result = prime * result + ((getComment() == null) ? 0 : getComment().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_chnl_virtual_mer_settle_info
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
        sb.append(", chnlId=").append(chnlId);
        sb.append(", mchntCd=").append(mchntCd);
        sb.append(", settleDt=").append(settleDt);
        sb.append(", transNum=").append(transNum);
        sb.append(", transAt=").append(transAt);
        sb.append(", settleAt=").append(settleAt);
        sb.append(", feeAt=").append(feeAt);
        sb.append(", filePath=").append(filePath);
        sb.append(", state=").append(state);
        sb.append(", lastOperId=").append(lastOperId);
        sb.append(", comment=").append(comment);
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
    public void cloneFrom(ChnlVirtualMerSettleInfo source) {
        this.seqId=source.seqId;
        this.chnlId=source.chnlId;
        this.mchntCd=source.mchntCd;
        this.settleDt=source.settleDt;
        this.transNum=source.transNum;
        this.transAt=source.transAt;
        this.settleAt=source.settleAt;
        this.feeAt=source.feeAt;
        this.filePath=source.filePath;
        this.state=source.state;
        this.lastOperId=source.lastOperId;
        this.comment=source.comment;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
    }
}