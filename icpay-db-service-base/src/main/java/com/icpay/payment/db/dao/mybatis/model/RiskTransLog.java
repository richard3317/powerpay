package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_risk_trans_log

 *
 * @mbg.generated
 */
public class RiskTransLog implements Serializable {
    /**
     * 记录编号
     * Database column : tbl_risk_trans_log.log_id
     *
     * @mbg.generated
     */
    private Long logId;

    /**
     * 订单号
     * Database column : tbl_risk_trans_log.order_id
     *
     * @mbg.generated
     */
    private String orderId;

    /**
     * 交易流水号
     * Database column : tbl_risk_trans_log.trans_seq_id
     *
     * @mbg.generated
     */
    private String transSeqId;

    /**
     * 规则编号
     * Database column : tbl_risk_trans_log.rule_id
     *
     * @mbg.generated
     */
    private Integer ruleId;

    /**
     * 处理方式：0=记录+拒绝，1=仅记录，2=记录+警告
     * Database column : tbl_risk_trans_log.result
     *
     * @mbg.generated
     */
    private String result;

    /**
     * 訂單日期
     * Database column : tbl_risk_trans_log.ext_trans_dt
     *
     * @mbg.generated
     */
    private String extTransDt;

    /**
     * 訂單時間
     * Database column : tbl_risk_trans_log.ext_trans_tm
     *
     * @mbg.generated
     */
    private String extTransTm;

    /**
     * 交易金額
     * Database column : tbl_risk_trans_log.trans_at
     *
     * @mbg.generated
     */
    private Long transAt;

    /**
     * 到达阀值时的实际值
     * Database column : tbl_risk_trans_log.threhold_value
     *
     * @mbg.generated
     */
    private Long threholdValue;

    /**
     * 卡号
     * Database column : tbl_risk_trans_log.pri_acct_no
     *
     * @mbg.generated
     */
    private String priAcctNo;

    /**
     * 会员编号
     * Database column : tbl_risk_trans_log.user_id
     *
     * @mbg.generated
     */
    private String userId;

    /**
     * 交易类(暂不使用，请改用 txn_type与 txn_sub_type)
     * Database column : tbl_risk_trans_log.int_trans_cd
     *
     * @mbg.generated
     */
    private String intTransCd;

    /**
     * 交易类(2位数)
     * Database column : tbl_risk_trans_log.txn_type
     *
     * @mbg.generated
     */
    private String txnType;

    /**
     * 交易子类(2位数)
     * Database column : tbl_risk_trans_log.txn_sub_type
     *
     * @mbg.generated
     */
    private String txnSubType;

    /**
     * (暂不使用)
     * Database column : tbl_risk_trans_log.pay_type
     *
     * @mbg.generated
     */
    private String payType;

    /**
     * 电话号
     * Database column : tbl_risk_trans_log.phone_no
     *
     * @mbg.generated
     */
    private String phoneNo;

    /**
     * 商户号
     * Database column : tbl_risk_trans_log.mchnt_cd
     *
     * @mbg.generated
     */
    private String mchntCd;

    /**
     * 过滤IP
     * Database column : tbl_risk_trans_log.ip
     *
     * @mbg.generated
     */
    private String ip;

    /**
     * (暂不使用)
     * Database column : tbl_risk_trans_log.term_sn
     *
     * @mbg.generated
     */
    private String termSn;

    /**
     * 交易状态(支付或代付状态)，请参考 com.icpay.payment.common.constants.Constant.TXNS_TATUS
     * Database column : tbl_risk_trans_log.txn_state
     *
     * @mbg.generated
     */
    private String txnState;

    /**
     * 响应码
     * Database column : tbl_risk_trans_log.resp_code
     *
     * @mbg.generated
     */
    private String respCode;

    /**
     * 渠道编号
     * Database column : tbl_risk_trans_log.trans_chhl
     *
     * @mbg.generated
     */
    private String transChhl;

    /**
     * 渠道响应码
     * Database column : tbl_risk_trans_log.chnl_resp_code
     *
     * @mbg.generated
     */
    private String chnlRespCode;

    /**
     * Exception Class Name
     * Database column : tbl_risk_trans_log.exception_cls_name
     *
     * @mbg.generated
     */
    private String exceptionClsName;

    /**
     * 备注
     * Database column : tbl_risk_trans_log.comment
     *
     * @mbg.generated
     */
    private String comment;

    /**
     * Database column : tbl_risk_trans_log.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_risk_trans_log.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_risk_trans_log
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_risk_trans_log
     *
     * @mbg.generated
     */
    public RiskTransLog(Long logId, String orderId, String transSeqId, Integer ruleId, String result, String extTransDt, String extTransTm, Long transAt, Long threholdValue, String priAcctNo, String userId, String intTransCd, String txnType, String txnSubType, String payType, String phoneNo, String mchntCd, String ip, String termSn, String txnState, String respCode, String transChhl, String chnlRespCode, String exceptionClsName, String comment, Date recCrtTs, Date recUpdTs) {
        this.logId = logId;
        this.orderId = orderId;
        this.transSeqId = transSeqId;
        this.ruleId = ruleId;
        this.result = result;
        this.extTransDt = extTransDt;
        this.extTransTm = extTransTm;
        this.transAt = transAt;
        this.threholdValue = threholdValue;
        this.priAcctNo = priAcctNo;
        this.userId = userId;
        this.intTransCd = intTransCd;
        this.txnType = txnType;
        this.txnSubType = txnSubType;
        this.payType = payType;
        this.phoneNo = phoneNo;
        this.mchntCd = mchntCd;
        this.ip = ip;
        this.termSn = termSn;
        this.txnState = txnState;
        this.respCode = respCode;
        this.transChhl = transChhl;
        this.chnlRespCode = chnlRespCode;
        this.exceptionClsName = exceptionClsName;
        this.comment = comment;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_risk_trans_log
     *
     * @mbg.generated
     */
    public RiskTransLog() {
        super();
    }

    /**
     * 记录编号
     * @return log_id 记录编号
     *
     * @mbg.generated
     */
    public Long getLogId() {
        return logId;
    }

    /**
     * 记录编号
     * @param logId 记录编号
     *
     * @mbg.generated
     */
    public void setLogId(Long logId) {
        this.logId = logId;
    }

    /**
     * 订单号
     * @return order_id 订单号
     *
     * @mbg.generated
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 订单号
     * @param orderId 订单号
     *
     * @mbg.generated
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 交易流水号
     * @return trans_seq_id 交易流水号
     *
     * @mbg.generated
     */
    public String getTransSeqId() {
        return transSeqId;
    }

    /**
     * 交易流水号
     * @param transSeqId 交易流水号
     *
     * @mbg.generated
     */
    public void setTransSeqId(String transSeqId) {
        this.transSeqId = transSeqId;
    }

    /**
     * 规则编号
     * @return rule_id 规则编号
     *
     * @mbg.generated
     */
    public Integer getRuleId() {
        return ruleId;
    }

    /**
     * 规则编号
     * @param ruleId 规则编号
     *
     * @mbg.generated
     */
    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    /**
     * 处理方式：0=记录+拒绝，1=仅记录，2=记录+警告
     * @return result 处理方式：0=记录+拒绝，1=仅记录，2=记录+警告
     *
     * @mbg.generated
     */
    public String getResult() {
        return result;
    }

    /**
     * 处理方式：0=记录+拒绝，1=仅记录，2=记录+警告
     * @param result 处理方式：0=记录+拒绝，1=仅记录，2=记录+警告
     *
     * @mbg.generated
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 訂單日期
     * @return ext_trans_dt 訂單日期
     *
     * @mbg.generated
     */
    public String getExtTransDt() {
        return extTransDt;
    }

    /**
     * 訂單日期
     * @param extTransDt 訂單日期
     *
     * @mbg.generated
     */
    public void setExtTransDt(String extTransDt) {
        this.extTransDt = extTransDt;
    }

    /**
     * 訂單時間
     * @return ext_trans_tm 訂單時間
     *
     * @mbg.generated
     */
    public String getExtTransTm() {
        return extTransTm;
    }

    /**
     * 訂單時間
     * @param extTransTm 訂單時間
     *
     * @mbg.generated
     */
    public void setExtTransTm(String extTransTm) {
        this.extTransTm = extTransTm;
    }

    /**
     * 交易金額
     * @return trans_at 交易金額
     *
     * @mbg.generated
     */
    public Long getTransAt() {
        return transAt;
    }

    /**
     * 交易金額
     * @param transAt 交易金額
     *
     * @mbg.generated
     */
    public void setTransAt(Long transAt) {
        this.transAt = transAt;
    }

    /**
     * 到达阀值时的实际值
     * @return threhold_value 到达阀值时的实际值
     *
     * @mbg.generated
     */
    public Long getThreholdValue() {
        return threholdValue;
    }

    /**
     * 到达阀值时的实际值
     * @param threholdValue 到达阀值时的实际值
     *
     * @mbg.generated
     */
    public void setThreholdValue(Long threholdValue) {
        this.threholdValue = threholdValue;
    }

    /**
     * 卡号
     * @return pri_acct_no 卡号
     *
     * @mbg.generated
     */
    public String getPriAcctNo() {
        return priAcctNo;
    }

    /**
     * 卡号
     * @param priAcctNo 卡号
     *
     * @mbg.generated
     */
    public void setPriAcctNo(String priAcctNo) {
        this.priAcctNo = priAcctNo;
    }

    /**
     * 会员编号
     * @return user_id 会员编号
     *
     * @mbg.generated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 会员编号
     * @param userId 会员编号
     *
     * @mbg.generated
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 交易类(暂不使用，请改用 txn_type与 txn_sub_type)
     * @return int_trans_cd 交易类(暂不使用，请改用 txn_type与 txn_sub_type)
     *
     * @mbg.generated
     */
    public String getIntTransCd() {
        return intTransCd;
    }

    /**
     * 交易类(暂不使用，请改用 txn_type与 txn_sub_type)
     * @param intTransCd 交易类(暂不使用，请改用 txn_type与 txn_sub_type)
     *
     * @mbg.generated
     */
    public void setIntTransCd(String intTransCd) {
        this.intTransCd = intTransCd;
    }

    /**
     * 交易类(2位数)
     * @return txn_type 交易类(2位数)
     *
     * @mbg.generated
     */
    public String getTxnType() {
        return txnType;
    }

    /**
     * 交易类(2位数)
     * @param txnType 交易类(2位数)
     *
     * @mbg.generated
     */
    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    /**
     * 交易子类(2位数)
     * @return txn_sub_type 交易子类(2位数)
     *
     * @mbg.generated
     */
    public String getTxnSubType() {
        return txnSubType;
    }

    /**
     * 交易子类(2位数)
     * @param txnSubType 交易子类(2位数)
     *
     * @mbg.generated
     */
    public void setTxnSubType(String txnSubType) {
        this.txnSubType = txnSubType;
    }

    /**
     * (暂不使用)
     * @return pay_type (暂不使用)
     *
     * @mbg.generated
     */
    public String getPayType() {
        return payType;
    }

    /**
     * (暂不使用)
     * @param payType (暂不使用)
     *
     * @mbg.generated
     */
    public void setPayType(String payType) {
        this.payType = payType;
    }

    /**
     * 电话号
     * @return phone_no 电话号
     *
     * @mbg.generated
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * 电话号
     * @param phoneNo 电话号
     *
     * @mbg.generated
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
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
     * 过滤IP
     * @return ip 过滤IP
     *
     * @mbg.generated
     */
    public String getIp() {
        return ip;
    }

    /**
     * 过滤IP
     * @param ip 过滤IP
     *
     * @mbg.generated
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * (暂不使用)
     * @return term_sn (暂不使用)
     *
     * @mbg.generated
     */
    public String getTermSn() {
        return termSn;
    }

    /**
     * (暂不使用)
     * @param termSn (暂不使用)
     *
     * @mbg.generated
     */
    public void setTermSn(String termSn) {
        this.termSn = termSn;
    }

    /**
     * 交易状态(支付或代付状态)，请参考 com.icpay.payment.common.constants.Constant.TXNS_TATUS
     * @return txn_state 交易状态(支付或代付状态)，请参考 com.icpay.payment.common.constants.Constant.TXNS_TATUS
     *
     * @mbg.generated
     */
    public String getTxnState() {
        return txnState;
    }

    /**
     * 交易状态(支付或代付状态)，请参考 com.icpay.payment.common.constants.Constant.TXNS_TATUS
     * @param txnState 交易状态(支付或代付状态)，请参考 com.icpay.payment.common.constants.Constant.TXNS_TATUS
     *
     * @mbg.generated
     */
    public void setTxnState(String txnState) {
        this.txnState = txnState;
    }

    /**
     * 响应码
     * @return resp_code 响应码
     *
     * @mbg.generated
     */
    public String getRespCode() {
        return respCode;
    }

    /**
     * 响应码
     * @param respCode 响应码
     *
     * @mbg.generated
     */
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    /**
     * 渠道编号
     * @return trans_chhl 渠道编号
     *
     * @mbg.generated
     */
    public String getTransChhl() {
        return transChhl;
    }

    /**
     * 渠道编号
     * @param transChhl 渠道编号
     *
     * @mbg.generated
     */
    public void setTransChhl(String transChhl) {
        this.transChhl = transChhl;
    }

    /**
     * 渠道响应码
     * @return chnl_resp_code 渠道响应码
     *
     * @mbg.generated
     */
    public String getChnlRespCode() {
        return chnlRespCode;
    }

    /**
     * 渠道响应码
     * @param chnlRespCode 渠道响应码
     *
     * @mbg.generated
     */
    public void setChnlRespCode(String chnlRespCode) {
        this.chnlRespCode = chnlRespCode;
    }

    /**
     * Exception Class Name
     * @return exception_cls_name Exception Class Name
     *
     * @mbg.generated
     */
    public String getExceptionClsName() {
        return exceptionClsName;
    }

    /**
     * Exception Class Name
     * @param exceptionClsName Exception Class Name
     *
     * @mbg.generated
     */
    public void setExceptionClsName(String exceptionClsName) {
        this.exceptionClsName = exceptionClsName;
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
     * Database table : tbl_risk_trans_log
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
        RiskTransLog other = (RiskTransLog) that;
        return (this.getLogId() == null ? other.getLogId() == null : this.getLogId().equals(other.getLogId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getTransSeqId() == null ? other.getTransSeqId() == null : this.getTransSeqId().equals(other.getTransSeqId()))
            && (this.getRuleId() == null ? other.getRuleId() == null : this.getRuleId().equals(other.getRuleId()))
            && (this.getResult() == null ? other.getResult() == null : this.getResult().equals(other.getResult()))
            && (this.getExtTransDt() == null ? other.getExtTransDt() == null : this.getExtTransDt().equals(other.getExtTransDt()))
            && (this.getExtTransTm() == null ? other.getExtTransTm() == null : this.getExtTransTm().equals(other.getExtTransTm()))
            && (this.getTransAt() == null ? other.getTransAt() == null : this.getTransAt().equals(other.getTransAt()))
            && (this.getThreholdValue() == null ? other.getThreholdValue() == null : this.getThreholdValue().equals(other.getThreholdValue()))
            && (this.getPriAcctNo() == null ? other.getPriAcctNo() == null : this.getPriAcctNo().equals(other.getPriAcctNo()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getIntTransCd() == null ? other.getIntTransCd() == null : this.getIntTransCd().equals(other.getIntTransCd()))
            && (this.getTxnType() == null ? other.getTxnType() == null : this.getTxnType().equals(other.getTxnType()))
            && (this.getTxnSubType() == null ? other.getTxnSubType() == null : this.getTxnSubType().equals(other.getTxnSubType()))
            && (this.getPayType() == null ? other.getPayType() == null : this.getPayType().equals(other.getPayType()))
            && (this.getPhoneNo() == null ? other.getPhoneNo() == null : this.getPhoneNo().equals(other.getPhoneNo()))
            && (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getTermSn() == null ? other.getTermSn() == null : this.getTermSn().equals(other.getTermSn()))
            && (this.getTxnState() == null ? other.getTxnState() == null : this.getTxnState().equals(other.getTxnState()))
            && (this.getRespCode() == null ? other.getRespCode() == null : this.getRespCode().equals(other.getRespCode()))
            && (this.getTransChhl() == null ? other.getTransChhl() == null : this.getTransChhl().equals(other.getTransChhl()))
            && (this.getChnlRespCode() == null ? other.getChnlRespCode() == null : this.getChnlRespCode().equals(other.getChnlRespCode()))
            && (this.getExceptionClsName() == null ? other.getExceptionClsName() == null : this.getExceptionClsName().equals(other.getExceptionClsName()))
            && (this.getComment() == null ? other.getComment() == null : this.getComment().equals(other.getComment()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_risk_trans_log
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLogId() == null) ? 0 : getLogId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getTransSeqId() == null) ? 0 : getTransSeqId().hashCode());
        result = prime * result + ((getRuleId() == null) ? 0 : getRuleId().hashCode());
        result = prime * result + ((getResult() == null) ? 0 : getResult().hashCode());
        result = prime * result + ((getExtTransDt() == null) ? 0 : getExtTransDt().hashCode());
        result = prime * result + ((getExtTransTm() == null) ? 0 : getExtTransTm().hashCode());
        result = prime * result + ((getTransAt() == null) ? 0 : getTransAt().hashCode());
        result = prime * result + ((getThreholdValue() == null) ? 0 : getThreholdValue().hashCode());
        result = prime * result + ((getPriAcctNo() == null) ? 0 : getPriAcctNo().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getIntTransCd() == null) ? 0 : getIntTransCd().hashCode());
        result = prime * result + ((getTxnType() == null) ? 0 : getTxnType().hashCode());
        result = prime * result + ((getTxnSubType() == null) ? 0 : getTxnSubType().hashCode());
        result = prime * result + ((getPayType() == null) ? 0 : getPayType().hashCode());
        result = prime * result + ((getPhoneNo() == null) ? 0 : getPhoneNo().hashCode());
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getTermSn() == null) ? 0 : getTermSn().hashCode());
        result = prime * result + ((getTxnState() == null) ? 0 : getTxnState().hashCode());
        result = prime * result + ((getRespCode() == null) ? 0 : getRespCode().hashCode());
        result = prime * result + ((getTransChhl() == null) ? 0 : getTransChhl().hashCode());
        result = prime * result + ((getChnlRespCode() == null) ? 0 : getChnlRespCode().hashCode());
        result = prime * result + ((getExceptionClsName() == null) ? 0 : getExceptionClsName().hashCode());
        result = prime * result + ((getComment() == null) ? 0 : getComment().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_risk_trans_log
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", logId=").append(logId);
        sb.append(", orderId=").append(orderId);
        sb.append(", transSeqId=").append(transSeqId);
        sb.append(", ruleId=").append(ruleId);
        sb.append(", result=").append(result);
        sb.append(", extTransDt=").append(extTransDt);
        sb.append(", extTransTm=").append(extTransTm);
        sb.append(", transAt=").append(transAt);
        sb.append(", threholdValue=").append(threholdValue);
        sb.append(", priAcctNo=").append(priAcctNo);
        sb.append(", userId=").append(userId);
        sb.append(", intTransCd=").append(intTransCd);
        sb.append(", txnType=").append(txnType);
        sb.append(", txnSubType=").append(txnSubType);
        sb.append(", payType=").append(payType);
        sb.append(", phoneNo=").append(phoneNo);
        sb.append(", mchntCd=").append(mchntCd);
        sb.append(", ip=").append(ip);
        sb.append(", termSn=").append(termSn);
        sb.append(", txnState=").append(txnState);
        sb.append(", respCode=").append(respCode);
        sb.append(", transChhl=").append(transChhl);
        sb.append(", chnlRespCode=").append(chnlRespCode);
        sb.append(", exceptionClsName=").append(exceptionClsName);
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
    public void cloneFrom(RiskTransLog source) {
        this.logId=source.logId;
        this.orderId=source.orderId;
        this.transSeqId=source.transSeqId;
        this.ruleId=source.ruleId;
        this.result=source.result;
        this.extTransDt=source.extTransDt;
        this.extTransTm=source.extTransTm;
        this.transAt=source.transAt;
        this.threholdValue=source.threholdValue;
        this.priAcctNo=source.priAcctNo;
        this.userId=source.userId;
        this.intTransCd=source.intTransCd;
        this.txnType=source.txnType;
        this.txnSubType=source.txnSubType;
        this.payType=source.payType;
        this.phoneNo=source.phoneNo;
        this.mchntCd=source.mchntCd;
        this.ip=source.ip;
        this.termSn=source.termSn;
        this.txnState=source.txnState;
        this.respCode=source.respCode;
        this.transChhl=source.transChhl;
        this.chnlRespCode=source.chnlRespCode;
        this.exceptionClsName=source.exceptionClsName;
        this.comment=source.comment;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
    }
}