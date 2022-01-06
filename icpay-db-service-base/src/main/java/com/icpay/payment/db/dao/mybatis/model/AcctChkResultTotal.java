package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_acct_chk_result_total

 *
 * @mbg.generated
 */
public class AcctChkResultTotal implements Serializable {
    /**
     * Database column : tbl_acct_chk_result_total.seq_id
     *
     * @mbg.generated
     */
    private Integer seqId;

    /**
     * 对账日期
     * Database column : tbl_acct_chk_result_total.check_dt
     *
     * @mbg.generated
     */
    private String checkDt;

    /**
     * 渠道商户号
     * Database column : tbl_acct_chk_result_total.chnl_id
     *
     * @mbg.generated
     */
    private String chnlId;

    /**
     * Database column : tbl_acct_chk_result_total.mchnt_cd
     *
     * @mbg.generated
     */
    private String mchntCd;

    /**
     * 对账结果：0-对账成功有差错，1-对账成功
     * Database column : tbl_acct_chk_result_total.check_result
     *
     * @mbg.generated
     */
    private String checkResult;

    /**
     * 退款文件路径
     * Database column : tbl_acct_chk_result_total.file_path
     *
     * @mbg.generated
     */
    private String filePath;

    /**
     * Database column : tbl_acct_chk_result_total.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_acct_chk_result_total.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_acct_chk_result_total
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_acct_chk_result_total
     *
     * @mbg.generated
     */
    public AcctChkResultTotal(Integer seqId, String checkDt, String chnlId, String mchntCd, String checkResult, String filePath, Date recCrtTs, Date recUpdTs) {
        this.seqId = seqId;
        this.checkDt = checkDt;
        this.chnlId = chnlId;
        this.mchntCd = mchntCd;
        this.checkResult = checkResult;
        this.filePath = filePath;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_acct_chk_result_total
     *
     * @mbg.generated
     */
    public AcctChkResultTotal() {
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
     * 对账日期
     * @return check_dt 对账日期
     *
     * @mbg.generated
     */
    public String getCheckDt() {
        return checkDt;
    }

    /**
     * 对账日期
     * @param checkDt 对账日期
     *
     * @mbg.generated
     */
    public void setCheckDt(String checkDt) {
        this.checkDt = checkDt;
    }

    /**
     * 渠道商户号
     * @return chnl_id 渠道商户号
     *
     * @mbg.generated
     */
    public String getChnlId() {
        return chnlId;
    }

    /**
     * 渠道商户号
     * @param chnlId 渠道商户号
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
     * 对账结果：0-对账成功有差错，1-对账成功
     * @return check_result 对账结果：0-对账成功有差错，1-对账成功
     *
     * @mbg.generated
     */
    public String getCheckResult() {
        return checkResult;
    }

    /**
     * 对账结果：0-对账成功有差错，1-对账成功
     * @param checkResult 对账结果：0-对账成功有差错，1-对账成功
     *
     * @mbg.generated
     */
    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    /**
     * 退款文件路径
     * @return file_path 退款文件路径
     *
     * @mbg.generated
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 退款文件路径
     * @param filePath 退款文件路径
     *
     * @mbg.generated
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
     * Database table : tbl_acct_chk_result_total
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
        AcctChkResultTotal other = (AcctChkResultTotal) that;
        return (this.getSeqId() == null ? other.getSeqId() == null : this.getSeqId().equals(other.getSeqId()))
            && (this.getCheckDt() == null ? other.getCheckDt() == null : this.getCheckDt().equals(other.getCheckDt()))
            && (this.getChnlId() == null ? other.getChnlId() == null : this.getChnlId().equals(other.getChnlId()))
            && (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()))
            && (this.getCheckResult() == null ? other.getCheckResult() == null : this.getCheckResult().equals(other.getCheckResult()))
            && (this.getFilePath() == null ? other.getFilePath() == null : this.getFilePath().equals(other.getFilePath()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_acct_chk_result_total
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSeqId() == null) ? 0 : getSeqId().hashCode());
        result = prime * result + ((getCheckDt() == null) ? 0 : getCheckDt().hashCode());
        result = prime * result + ((getChnlId() == null) ? 0 : getChnlId().hashCode());
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        result = prime * result + ((getCheckResult() == null) ? 0 : getCheckResult().hashCode());
        result = prime * result + ((getFilePath() == null) ? 0 : getFilePath().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_acct_chk_result_total
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
        sb.append(", checkDt=").append(checkDt);
        sb.append(", chnlId=").append(chnlId);
        sb.append(", mchntCd=").append(mchntCd);
        sb.append(", checkResult=").append(checkResult);
        sb.append(", filePath=").append(filePath);
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
    public void cloneFrom(AcctChkResultTotal source) {
        this.seqId=source.seqId;
        this.checkDt=source.checkDt;
        this.chnlId=source.chnlId;
        this.mchntCd=source.mchntCd;
        this.checkResult=source.checkResult;
        this.filePath=source.filePath;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
    }
}