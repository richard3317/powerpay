package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_trans_rpt_info
 * @author USER
 *
 * @mbg.generated
 */
public class TransRptInfo implements Serializable {
    /**
     * 主键
     * Database column : tbl_trans_rpt_info.rpt_id
     *
     * @mbg.generated
     */
    private Integer rptId;

    /**
     * 日期,格式 yyyyMMdd
     * Database column : tbl_trans_rpt_info.trans_dt
     *
     * @mbg.generated
     */
    private String transDt;

    /**
     * 小时
     * Database column : tbl_trans_rpt_info.trans_hour
     *
     * @mbg.generated
     */
    private String transHour;

    /**
     * 商户号
     * Database column : tbl_trans_rpt_info.mchnt_cd
     *
     * @mbg.generated
     */
    private String mchntCd;

    /**
     * 商户名
     * Database column : tbl_trans_rpt_info.mchnt_cn_nm
     *
     * @mbg.generated
     */
    private String mchntCnNm;

    /**
     * 交易类型
     * Database column : tbl_trans_rpt_info.int_trans_cd
     *
     * @mbg.generated
     */
    private String intTransCd;

    /**
     * 交易金额
     * Database column : tbl_trans_rpt_info.trans_at
     *
     * @mbg.generated
     */
    private Long transAt;

    /**
     * 上次交易金额
     * Database column : tbl_trans_rpt_info.last_trans_at
     *
     * @mbg.generated
     */
    private Long lastTransAt;

    /**
     * 交易状态
     * Database column : tbl_trans_rpt_info.txn_state
     *
     * @mbg.generated
     */
    private String txnState;

    /**
     * 渠道编号
     * Database column : tbl_trans_rpt_info.chnl_id
     *
     * @mbg.generated
     */
    private String chnlId;

    /**
     * 平均小时交易量
     * Database column : tbl_trans_rpt_info.average_at
     *
     * @mbg.generated
     */
    private Long averageAt;

    /**
     * 代付平均小时交易量
     * Database column : tbl_trans_rpt_info.average_at_wd
     *
     * @mbg.generated
     */
    private Long averageAtWd;

    /**
     * 成功笔数
     * Database column : tbl_trans_rpt_info.scce_cnt
     *
     * @mbg.generated
     */
    private Integer scceCnt;

    /**
     * 失败笔数
     * Database column : tbl_trans_rpt_info.failed_cnt
     *
     * @mbg.generated
     */
    private Integer failedCnt;

    /**
     * 总笔数
     * Database column : tbl_trans_rpt_info.all_cnt
     *
     * @mbg.generated
     */
    private Integer allCnt;

    /**
     * 创建时间
     * Database column : tbl_trans_rpt_info.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * 更新时间
     * Database column : tbl_trans_rpt_info.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_trans_rpt_info
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_trans_rpt_info
     *
     * @mbg.generated
     */
    public TransRptInfo(Integer rptId, String transDt, String transHour, String mchntCd, String mchntCnNm, String intTransCd, Long transAt, Long lastTransAt, String txnState, String chnlId, Long averageAt, Long averageAtWd, Integer scceCnt, Integer failedCnt, Integer allCnt, Date recCrtTs, Date recUpdTs) {
        this.rptId = rptId;
        this.transDt = transDt;
        this.transHour = transHour;
        this.mchntCd = mchntCd;
        this.mchntCnNm = mchntCnNm;
        this.intTransCd = intTransCd;
        this.transAt = transAt;
        this.lastTransAt = lastTransAt;
        this.txnState = txnState;
        this.chnlId = chnlId;
        this.averageAt = averageAt;
        this.averageAtWd = averageAtWd;
        this.scceCnt = scceCnt;
        this.failedCnt = failedCnt;
        this.allCnt = allCnt;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_trans_rpt_info
     *
     * @mbg.generated
     */
    public TransRptInfo() {
        super();
    }

    /**
     * 主键
     * @return rpt_id 主键
     *
     * @mbg.generated
     */
    public Integer getRptId() {
        return rptId;
    }

    /**
     * 主键
     * @param rptId 主键
     *
     * @mbg.generated
     */
    public void setRptId(Integer rptId) {
        this.rptId = rptId;
    }

    /**
     * 日期,格式 yyyyMMdd
     * @return trans_dt 日期,格式 yyyyMMdd
     *
     * @mbg.generated
     */
    public String getTransDt() {
        return transDt;
    }

    /**
     * 日期,格式 yyyyMMdd
     * @param transDt 日期,格式 yyyyMMdd
     *
     * @mbg.generated
     */
    public void setTransDt(String transDt) {
        this.transDt = transDt;
    }

    /**
     * 小时
     * @return trans_hour 小时
     *
     * @mbg.generated
     */
    public String getTransHour() {
        return transHour;
    }

    /**
     * 小时
     * @param transHour 小时
     *
     * @mbg.generated
     */
    public void setTransHour(String transHour) {
        this.transHour = transHour;
    }

    /**
     * 商户号
     * @return mchnt_cd 商户号
     *
     * @mbg.generated
     */
    public String getMchntCd() {
        return mchntCd;
    }

    /**
     * 商户号
     * @param mchntCd 商户号
     *
     * @mbg.generated
     */
    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd;
    }

    /**
     * 商户名
     * @return mchnt_cn_nm 商户名
     *
     * @mbg.generated
     */
    public String getMchntCnNm() {
        return mchntCnNm;
    }

    /**
     * 商户名
     * @param mchntCnNm 商户名
     *
     * @mbg.generated
     */
    public void setMchntCnNm(String mchntCnNm) {
        this.mchntCnNm = mchntCnNm;
    }

    /**
     * 交易类型
     * @return int_trans_cd 交易类型
     *
     * @mbg.generated
     */
    public String getIntTransCd() {
        return intTransCd;
    }

    /**
     * 交易类型
     * @param intTransCd 交易类型
     *
     * @mbg.generated
     */
    public void setIntTransCd(String intTransCd) {
        this.intTransCd = intTransCd;
    }

    /**
     * 交易金额
     * @return trans_at 交易金额
     *
     * @mbg.generated
     */
    public Long getTransAt() {
        return transAt;
    }

    /**
     * 交易金额
     * @param transAt 交易金额
     *
     * @mbg.generated
     */
    public void setTransAt(Long transAt) {
        this.transAt = transAt;
    }

    /**
     * 上次交易金额
     * @return last_trans_at 上次交易金额
     *
     * @mbg.generated
     */
    public Long getLastTransAt() {
        return lastTransAt;
    }

    /**
     * 上次交易金额
     * @param lastTransAt 上次交易金额
     *
     * @mbg.generated
     */
    public void setLastTransAt(Long lastTransAt) {
        this.lastTransAt = lastTransAt;
    }

    /**
     * 交易状态
     * @return txn_state 交易状态
     *
     * @mbg.generated
     */
    public String getTxnState() {
        return txnState;
    }

    /**
     * 交易状态
     * @param txnState 交易状态
     *
     * @mbg.generated
     */
    public void setTxnState(String txnState) {
        this.txnState = txnState;
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
     * 平均小时交易量
     * @return average_at 平均小时交易量
     *
     * @mbg.generated
     */
    public Long getAverageAt() {
        return averageAt;
    }

    /**
     * 平均小时交易量
     * @param averageAt 平均小时交易量
     *
     * @mbg.generated
     */
    public void setAverageAt(Long averageAt) {
        this.averageAt = averageAt;
    }

    /**
     * 代付平均小时交易量
     * @return average_at_wd 代付平均小时交易量
     *
     * @mbg.generated
     */
    public Long getAverageAtWd() {
        return averageAtWd;
    }

    /**
     * 代付平均小时交易量
     * @param averageAtWd 代付平均小时交易量
     *
     * @mbg.generated
     */
    public void setAverageAtWd(Long averageAtWd) {
        this.averageAtWd = averageAtWd;
    }

    /**
     * 成功笔数
     * @return scce_cnt 成功笔数
     *
     * @mbg.generated
     */
    public Integer getScceCnt() {
        return scceCnt;
    }

    /**
     * 成功笔数
     * @param scceCnt 成功笔数
     *
     * @mbg.generated
     */
    public void setScceCnt(Integer scceCnt) {
        this.scceCnt = scceCnt;
    }

    /**
     * 失败笔数
     * @return failed_cnt 失败笔数
     *
     * @mbg.generated
     */
    public Integer getFailedCnt() {
        return failedCnt;
    }

    /**
     * 失败笔数
     * @param failedCnt 失败笔数
     *
     * @mbg.generated
     */
    public void setFailedCnt(Integer failedCnt) {
        this.failedCnt = failedCnt;
    }

    /**
     * 总笔数
     * @return all_cnt 总笔数
     *
     * @mbg.generated
     */
    public Integer getAllCnt() {
        return allCnt;
    }

    /**
     * 总笔数
     * @param allCnt 总笔数
     *
     * @mbg.generated
     */
    public void setAllCnt(Integer allCnt) {
        this.allCnt = allCnt;
    }

    /**
     * 创建时间
     * @return rec_crt_ts 创建时间
     *
     * @mbg.generated
     */
    public Date getRecCrtTs() {
        return recCrtTs;
    }

    /**
     * 创建时间
     * @param recCrtTs 创建时间
     *
     * @mbg.generated
     */
    public void setRecCrtTs(Date recCrtTs) {
        this.recCrtTs = recCrtTs;
    }

    /**
     * 更新时间
     * @return rec_upd_ts 更新时间
     *
     * @mbg.generated
     */
    public Date getRecUpdTs() {
        return recUpdTs;
    }

    /**
     * 更新时间
     * @param recUpdTs 更新时间
     *
     * @mbg.generated
     */
    public void setRecUpdTs(Date recUpdTs) {
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_trans_rpt_info
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
        TransRptInfo other = (TransRptInfo) that;
        return (this.getRptId() == null ? other.getRptId() == null : this.getRptId().equals(other.getRptId()))
            && (this.getTransDt() == null ? other.getTransDt() == null : this.getTransDt().equals(other.getTransDt()))
            && (this.getTransHour() == null ? other.getTransHour() == null : this.getTransHour().equals(other.getTransHour()))
            && (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()))
            && (this.getMchntCnNm() == null ? other.getMchntCnNm() == null : this.getMchntCnNm().equals(other.getMchntCnNm()))
            && (this.getIntTransCd() == null ? other.getIntTransCd() == null : this.getIntTransCd().equals(other.getIntTransCd()))
            && (this.getTransAt() == null ? other.getTransAt() == null : this.getTransAt().equals(other.getTransAt()))
            && (this.getLastTransAt() == null ? other.getLastTransAt() == null : this.getLastTransAt().equals(other.getLastTransAt()))
            && (this.getTxnState() == null ? other.getTxnState() == null : this.getTxnState().equals(other.getTxnState()))
            && (this.getChnlId() == null ? other.getChnlId() == null : this.getChnlId().equals(other.getChnlId()))
            && (this.getAverageAt() == null ? other.getAverageAt() == null : this.getAverageAt().equals(other.getAverageAt()))
            && (this.getAverageAtWd() == null ? other.getAverageAtWd() == null : this.getAverageAtWd().equals(other.getAverageAtWd()))
            && (this.getScceCnt() == null ? other.getScceCnt() == null : this.getScceCnt().equals(other.getScceCnt()))
            && (this.getFailedCnt() == null ? other.getFailedCnt() == null : this.getFailedCnt().equals(other.getFailedCnt()))
            && (this.getAllCnt() == null ? other.getAllCnt() == null : this.getAllCnt().equals(other.getAllCnt()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_trans_rpt_info
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRptId() == null) ? 0 : getRptId().hashCode());
        result = prime * result + ((getTransDt() == null) ? 0 : getTransDt().hashCode());
        result = prime * result + ((getTransHour() == null) ? 0 : getTransHour().hashCode());
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        result = prime * result + ((getMchntCnNm() == null) ? 0 : getMchntCnNm().hashCode());
        result = prime * result + ((getIntTransCd() == null) ? 0 : getIntTransCd().hashCode());
        result = prime * result + ((getTransAt() == null) ? 0 : getTransAt().hashCode());
        result = prime * result + ((getLastTransAt() == null) ? 0 : getLastTransAt().hashCode());
        result = prime * result + ((getTxnState() == null) ? 0 : getTxnState().hashCode());
        result = prime * result + ((getChnlId() == null) ? 0 : getChnlId().hashCode());
        result = prime * result + ((getAverageAt() == null) ? 0 : getAverageAt().hashCode());
        result = prime * result + ((getAverageAtWd() == null) ? 0 : getAverageAtWd().hashCode());
        result = prime * result + ((getScceCnt() == null) ? 0 : getScceCnt().hashCode());
        result = prime * result + ((getFailedCnt() == null) ? 0 : getFailedCnt().hashCode());
        result = prime * result + ((getAllCnt() == null) ? 0 : getAllCnt().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_trans_rpt_info
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", rptId=").append(rptId);
        sb.append(", transDt=").append(transDt);
        sb.append(", transHour=").append(transHour);
        sb.append(", mchntCd=").append(mchntCd);
        sb.append(", mchntCnNm=").append(mchntCnNm);
        sb.append(", intTransCd=").append(intTransCd);
        sb.append(", transAt=").append(transAt);
        sb.append(", lastTransAt=").append(lastTransAt);
        sb.append(", txnState=").append(txnState);
        sb.append(", chnlId=").append(chnlId);
        sb.append(", averageAt=").append(averageAt);
        sb.append(", averageAtWd=").append(averageAtWd);
        sb.append(", scceCnt=").append(scceCnt);
        sb.append(", failedCnt=").append(failedCnt);
        sb.append(", allCnt=").append(allCnt);
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
    public void cloneFrom(TransRptInfo source) {
        this.rptId=source.rptId;
        this.transDt=source.transDt;
        this.transHour=source.transHour;
        this.mchntCd=source.mchntCd;
        this.mchntCnNm=source.mchntCnNm;
        this.intTransCd=source.intTransCd;
        this.transAt=source.transAt;
        this.lastTransAt=source.lastTransAt;
        this.txnState=source.txnState;
        this.chnlId=source.chnlId;
        this.averageAt=source.averageAt;
        this.averageAtWd=source.averageAtWd;
        this.scceCnt=source.scceCnt;
        this.failedCnt=source.failedCnt;
        this.allCnt=source.allCnt;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
    }
}