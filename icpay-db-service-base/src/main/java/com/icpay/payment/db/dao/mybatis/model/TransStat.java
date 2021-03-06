package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_trans_stat

 *
 * @mbg.generated
 */
public class TransStat implements Serializable {
    /**
     * Database column : tbl_trans_stat.seq_id
     *
     * @mbg.generated
     */
    private Integer seqId;

    /**
     * Database column : tbl_trans_stat.mchnt_cd
     *
     * @mbg.generated
     */
    private String mchntCd;

    /**
     * Database column : tbl_trans_stat.int_trans_cd
     *
     * @mbg.generated
     */
    private String intTransCd;

    /**
     * Database column : tbl_trans_stat.curr_cd
     *
     * @mbg.generated
     */
    private String currCd;

    /**
     * Database column : tbl_trans_stat.trans_chnl
     *
     * @mbg.generated
     */
    private String transChnl;

    /**
     * Database column : tbl_trans_stat.rsp_cd
     *
     * @mbg.generated
     */
    private String rspCd;

    /**
     * Database column : tbl_trans_stat.term_sn
     *
     * @mbg.generated
     */
    private String termSn;

    /**
     * Database column : tbl_trans_stat.stat_dt
     *
     * @mbg.generated
     */
    private String statDt;

    /**
     * Database column : tbl_trans_stat.trans_num_sum
     *
     * @mbg.generated
     */
    private Integer transNumSum;

    /**
     * Database column : tbl_trans_stat.trans_at_sum
     *
     * @mbg.generated
     */
    private Long transAtSum;

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    public TransStat(Integer seqId, String mchntCd, String intTransCd, String currCd, String transChnl, String rspCd, String termSn, String statDt, Integer transNumSum, Long transAtSum) {
        this.seqId = seqId;
        this.mchntCd = mchntCd;
        this.intTransCd = intTransCd;
        this.currCd = currCd;
        this.transChnl = transChnl;
        this.rspCd = rspCd;
        this.termSn = termSn;
        this.statDt = statDt;
        this.transNumSum = transNumSum;
        this.transAtSum = transAtSum;
    }

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    public TransStat() {
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
     * @return int_trans_cd 
     *
     * @mbg.generated
     */
    public String getIntTransCd() {
        return intTransCd;
    }

    /**
     * @param intTransCd 
     *
     * @mbg.generated
     */
    public void setIntTransCd(String intTransCd) {
        this.intTransCd = intTransCd;
    }

    /**
     * @return curr_cd 
     *
     * @mbg.generated
     */
    public String getCurrCd() {
        return currCd;
    }

    /**
     * @param currCd 
     *
     * @mbg.generated
     */
    public void setCurrCd(String currCd) {
        this.currCd = currCd;
    }

    /**
     * @return trans_chnl 
     *
     * @mbg.generated
     */
    public String getTransChnl() {
        return transChnl;
    }

    /**
     * @param transChnl 
     *
     * @mbg.generated
     */
    public void setTransChnl(String transChnl) {
        this.transChnl = transChnl;
    }

    /**
     * @return rsp_cd 
     *
     * @mbg.generated
     */
    public String getRspCd() {
        return rspCd;
    }

    /**
     * @param rspCd 
     *
     * @mbg.generated
     */
    public void setRspCd(String rspCd) {
        this.rspCd = rspCd;
    }

    /**
     * @return term_sn 
     *
     * @mbg.generated
     */
    public String getTermSn() {
        return termSn;
    }

    /**
     * @param termSn 
     *
     * @mbg.generated
     */
    public void setTermSn(String termSn) {
        this.termSn = termSn;
    }

    /**
     * @return stat_dt 
     *
     * @mbg.generated
     */
    public String getStatDt() {
        return statDt;
    }

    /**
     * @param statDt 
     *
     * @mbg.generated
     */
    public void setStatDt(String statDt) {
        this.statDt = statDt;
    }

    /**
     * @return trans_num_sum 
     *
     * @mbg.generated
     */
    public Integer getTransNumSum() {
        return transNumSum;
    }

    /**
     * @param transNumSum 
     *
     * @mbg.generated
     */
    public void setTransNumSum(Integer transNumSum) {
        this.transNumSum = transNumSum;
    }

    /**
     * @return trans_at_sum 
     *
     * @mbg.generated
     */
    public Long getTransAtSum() {
        return transAtSum;
    }

    /**
     * @param transAtSum 
     *
     * @mbg.generated
     */
    public void setTransAtSum(Long transAtSum) {
        this.transAtSum = transAtSum;
    }

    /**
     * Database table : tbl_trans_stat
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
        TransStat other = (TransStat) that;
        return (this.getSeqId() == null ? other.getSeqId() == null : this.getSeqId().equals(other.getSeqId()))
            && (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()))
            && (this.getIntTransCd() == null ? other.getIntTransCd() == null : this.getIntTransCd().equals(other.getIntTransCd()))
            && (this.getCurrCd() == null ? other.getCurrCd() == null : this.getCurrCd().equals(other.getCurrCd()))
            && (this.getTransChnl() == null ? other.getTransChnl() == null : this.getTransChnl().equals(other.getTransChnl()))
            && (this.getRspCd() == null ? other.getRspCd() == null : this.getRspCd().equals(other.getRspCd()))
            && (this.getTermSn() == null ? other.getTermSn() == null : this.getTermSn().equals(other.getTermSn()))
            && (this.getStatDt() == null ? other.getStatDt() == null : this.getStatDt().equals(other.getStatDt()))
            && (this.getTransNumSum() == null ? other.getTransNumSum() == null : this.getTransNumSum().equals(other.getTransNumSum()))
            && (this.getTransAtSum() == null ? other.getTransAtSum() == null : this.getTransAtSum().equals(other.getTransAtSum()));
    }

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSeqId() == null) ? 0 : getSeqId().hashCode());
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        result = prime * result + ((getIntTransCd() == null) ? 0 : getIntTransCd().hashCode());
        result = prime * result + ((getCurrCd() == null) ? 0 : getCurrCd().hashCode());
        result = prime * result + ((getTransChnl() == null) ? 0 : getTransChnl().hashCode());
        result = prime * result + ((getRspCd() == null) ? 0 : getRspCd().hashCode());
        result = prime * result + ((getTermSn() == null) ? 0 : getTermSn().hashCode());
        result = prime * result + ((getStatDt() == null) ? 0 : getStatDt().hashCode());
        result = prime * result + ((getTransNumSum() == null) ? 0 : getTransNumSum().hashCode());
        result = prime * result + ((getTransAtSum() == null) ? 0 : getTransAtSum().hashCode());
        return result;
    }

    /**
     * Database table : tbl_trans_stat
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
        sb.append(", mchntCd=").append(mchntCd);
        sb.append(", intTransCd=").append(intTransCd);
        sb.append(", currCd=").append(currCd);
        sb.append(", transChnl=").append(transChnl);
        sb.append(", rspCd=").append(rspCd);
        sb.append(", termSn=").append(termSn);
        sb.append(", statDt=").append(statDt);
        sb.append(", transNumSum=").append(transNumSum);
        sb.append(", transAtSum=").append(transAtSum);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(TransStat source) {
        this.seqId=source.seqId;
        this.mchntCd=source.mchntCd;
        this.intTransCd=source.intTransCd;
        this.currCd=source.currCd;
        this.transChnl=source.transChnl;
        this.rspCd=source.rspCd;
        this.termSn=source.termSn;
        this.statDt=source.statDt;
        this.transNumSum=source.transNumSum;
        this.transAtSum=source.transAtSum;
    }
}