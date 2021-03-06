package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_event_log_01

 *
 * @mbg.generated
 */
public class EventLog implements Serializable {
    /**
     * 日志id
     * Database column : tbl_event_log_01.log_id
     *
     * @mbg.generated
     */
    private Long logId;

    /**
     * LOG时戳,可用来排序
     * Database column : tbl_event_log_01.log_ts
     *
     * @mbg.generated
     */
    private Date logTs;

    /**
     * LOG日期,格式 yyyyMMdd
     * Database column : tbl_event_log_01.log_date
     *
     * @mbg.generated
     */
    private String logDate;

    /**
     * LOG时间,格式 HHmmss.SSS
     * Database column : tbl_event_log_01.log_time
     *
     * @mbg.generated
     */
    private String logTime;

    /**
     * 日志角色: "RiskEvent"
     * Database column : tbl_event_log_01.log_role
     *
     * @mbg.generated
     */
    private String logRole;

    /**
     * 事件来源: 参考RISK.Source
     * Database column : tbl_event_log_01.log_src
     *
     * @mbg.generated
     */
    private String logSrc;

    /**
     * 事件: 参考RISK.Event
     * Database column : tbl_event_log_01.log_event
     *
     * @mbg.generated
     */
    private String logEvent;

    /**
     * 事件对象例如： 卡号+户名, IP 或用户ID等
     * Database column : tbl_event_log_01.log_event_target
     *
     * @mbg.generated
     */
    private String logEventTarget;

    /**
     * 事件结果: 参考RISK.Result
     * Database column : tbl_event_log_01.log_event_result
     *
     * @mbg.generated
     */
    private String logEventResult;

    /**
     * 造成事件结果的原因: 参考RISK.Reason
     * Database column : tbl_event_log_01.log_event_reason
     *
     * @mbg.generated
     */
    private String logEventReason;

    /**
     * 造成事件结果的原因类型: 参考RISK.ReasonType
     * Database column : tbl_event_log_01.log_event_reason_type
     *
     * @mbg.generated
     */
    private String logEventReasonType;

    /**
     * LOG节点
     * Database column : tbl_event_log_01.log_node
     *
     * @mbg.generated
     */
    private String logNode;

    /**
     * 日志等级; 0=DEBUG, 1=INFO, 2=WARN, 3=ERROR 
     * Database column : tbl_event_log_01.log_level
     *
     * @mbg.generated
     */
    private String logLevel;

    /**
     * 错误代码
     * Database column : tbl_event_log_01.log_code
     *
     * @mbg.generated
     */
    private String logCode;

    /**
     * 日志信息: Json格式，包含 format 与 args, 范例 _FMT:{"msgFormat":"商户 %s 之订单号 %s 重复", "msgArgs": ["MER9900123","ORD00000168"]}
     * Database column : tbl_event_log_01.log_msg
     *
     * @mbg.generated
     */
    private String logMsg;

    /**
     * 日志附加参数
     * Database column : tbl_event_log_01.log_params
     *
     * @mbg.generated
     */
    private String logParams;

    /**
     * 内部交易编号(仅适用交易相关LOG)
     * Database column : tbl_event_log_01.trans_seq_id
     *
     * @mbg.generated
     */
    private String transSeqId;

    /**
     * 交易类型(4位数)(仅适用交易相关LOG)
     * Database column : tbl_event_log_01.int_trans_cd
     *
     * @mbg.generated
     */
    private String intTransCd;

    /**
     * 前端商户号, 若为BM用户则填"_BM"
     * Database column : tbl_event_log_01.mchnt_cd
     *
     * @mbg.generated
     */
    private String mchntCd;

    /**
     * 使用者帐号, 若为BM用户则填"_BM"
     * Database column : tbl_event_log_01.user_id
     *
     * @mbg.generated
     */
    private String userId;

    /**
     * 订单号,客户端订单号,同一商户当日必须唯一(仅适用交易相关LOG)
     * Database column : tbl_event_log_01.order_id
     *
     * @mbg.generated
     */
    private String orderId;

    /**
     * 渠道编号(仅适用交易相关LOG)
     * Database column : tbl_event_log_01.trans_chnl
     *
     * @mbg.generated
     */
    private String transChnl;

    /**
     * 渠道商户号(仅适用交易相关LOG)
     * Database column : tbl_event_log_01.chnl_mchnt_cd
     *
     * @mbg.generated
     */
    private String chnlMchntCd;

    /**
     * 渠道订单号(仅适用交易相关LOG)
     * Database column : tbl_event_log_01.chnl_order_id
     *
     * @mbg.generated
     */
    private String chnlOrderId;

    /**
     * Session ID
     * Database column : tbl_event_log_01.session_id
     *
     * @mbg.generated
     */
    private String sessionId;

    /**
     * Exception 类名(短名称)
     * Database column : tbl_event_log_01.error_cls
     *
     * @mbg.generated
     */
    private String errorCls;

    /**
     * 标签
     * Database column : tbl_event_log_01.tags
     *
     * @mbg.generated
     */
    private String tags;

    /**
     * Database table : tbl_event_log_01
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_event_log_01
     *
     * @mbg.generated
     */
    public EventLog(Long logId, Date logTs, String logDate, String logTime, String logRole, String logSrc, String logEvent, String logEventTarget, String logEventResult, String logEventReason, String logEventReasonType, String logNode, String logLevel, String logCode, String logMsg, String logParams, String transSeqId, String intTransCd, String mchntCd, String userId, String orderId, String transChnl, String chnlMchntCd, String chnlOrderId, String sessionId, String errorCls, String tags) {
        this.logId = logId;
        this.logTs = logTs;
        this.logDate = logDate;
        this.logTime = logTime;
        this.logRole = logRole;
        this.logSrc = logSrc;
        this.logEvent = logEvent;
        this.logEventTarget = logEventTarget;
        this.logEventResult = logEventResult;
        this.logEventReason = logEventReason;
        this.logEventReasonType = logEventReasonType;
        this.logNode = logNode;
        this.logLevel = logLevel;
        this.logCode = logCode;
        this.logMsg = logMsg;
        this.logParams = logParams;
        this.transSeqId = transSeqId;
        this.intTransCd = intTransCd;
        this.mchntCd = mchntCd;
        this.userId = userId;
        this.orderId = orderId;
        this.transChnl = transChnl;
        this.chnlMchntCd = chnlMchntCd;
        this.chnlOrderId = chnlOrderId;
        this.sessionId = sessionId;
        this.errorCls = errorCls;
        this.tags = tags;
    }

    /**
     * Database table : tbl_event_log_01
     *
     * @mbg.generated
     */
    public EventLog() {
        super();
    }

    /**
     * 日志id
     * @return log_id 日志id
     *
     * @mbg.generated
     */
    public Long getLogId() {
        return logId;
    }

    /**
     * 日志id
     * @param logId 日志id
     *
     * @mbg.generated
     */
    public void setLogId(Long logId) {
        this.logId = logId;
    }

    /**
     * LOG时戳,可用来排序
     * @return log_ts LOG时戳,可用来排序
     *
     * @mbg.generated
     */
    public Date getLogTs() {
        return logTs;
    }

    /**
     * LOG时戳,可用来排序
     * @param logTs LOG时戳,可用来排序
     *
     * @mbg.generated
     */
    public void setLogTs(Date logTs) {
        this.logTs = logTs;
    }

    /**
     * LOG日期,格式 yyyyMMdd
     * @return log_date LOG日期,格式 yyyyMMdd
     *
     * @mbg.generated
     */
    public String getLogDate() {
        return logDate;
    }

    /**
     * LOG日期,格式 yyyyMMdd
     * @param logDate LOG日期,格式 yyyyMMdd
     *
     * @mbg.generated
     */
    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    /**
     * LOG时间,格式 HHmmss.SSS
     * @return log_time LOG时间,格式 HHmmss.SSS
     *
     * @mbg.generated
     */
    public String getLogTime() {
        return logTime;
    }

    /**
     * LOG时间,格式 HHmmss.SSS
     * @param logTime LOG时间,格式 HHmmss.SSS
     *
     * @mbg.generated
     */
    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    /**
     * 日志角色: "RiskEvent"
     * @return log_role 日志角色: "RiskEvent"
     *
     * @mbg.generated
     */
    public String getLogRole() {
        return logRole;
    }

    /**
     * 日志角色: "RiskEvent"
     * @param logRole 日志角色: "RiskEvent"
     *
     * @mbg.generated
     */
    public void setLogRole(String logRole) {
        this.logRole = logRole;
    }

    /**
     * 事件来源: 参考RISK.Source
     * @return log_src 事件来源: 参考RISK.Source
     *
     * @mbg.generated
     */
    public String getLogSrc() {
        return logSrc;
    }

    /**
     * 事件来源: 参考RISK.Source
     * @param logSrc 事件来源: 参考RISK.Source
     *
     * @mbg.generated
     */
    public void setLogSrc(String logSrc) {
        this.logSrc = logSrc;
    }

    /**
     * 事件: 参考RISK.Event
     * @return log_event 事件: 参考RISK.Event
     *
     * @mbg.generated
     */
    public String getLogEvent() {
        return logEvent;
    }

    /**
     * 事件: 参考RISK.Event
     * @param logEvent 事件: 参考RISK.Event
     *
     * @mbg.generated
     */
    public void setLogEvent(String logEvent) {
        this.logEvent = logEvent;
    }

    /**
     * 事件对象例如： 卡号+户名, IP 或用户ID等
     * @return log_event_target 事件对象例如： 卡号+户名, IP 或用户ID等
     *
     * @mbg.generated
     */
    public String getLogEventTarget() {
        return logEventTarget;
    }

    /**
     * 事件对象例如： 卡号+户名, IP 或用户ID等
     * @param logEventTarget 事件对象例如： 卡号+户名, IP 或用户ID等
     *
     * @mbg.generated
     */
    public void setLogEventTarget(String logEventTarget) {
        this.logEventTarget = logEventTarget;
    }

    /**
     * 事件结果: 参考RISK.Result
     * @return log_event_result 事件结果: 参考RISK.Result
     *
     * @mbg.generated
     */
    public String getLogEventResult() {
        return logEventResult;
    }

    /**
     * 事件结果: 参考RISK.Result
     * @param logEventResult 事件结果: 参考RISK.Result
     *
     * @mbg.generated
     */
    public void setLogEventResult(String logEventResult) {
        this.logEventResult = logEventResult;
    }

    /**
     * 造成事件结果的原因: 参考RISK.Reason
     * @return log_event_reason 造成事件结果的原因: 参考RISK.Reason
     *
     * @mbg.generated
     */
    public String getLogEventReason() {
        return logEventReason;
    }

    /**
     * 造成事件结果的原因: 参考RISK.Reason
     * @param logEventReason 造成事件结果的原因: 参考RISK.Reason
     *
     * @mbg.generated
     */
    public void setLogEventReason(String logEventReason) {
        this.logEventReason = logEventReason;
    }

    /**
     * 造成事件结果的原因类型: 参考RISK.ReasonType
     * @return log_event_reason_type 造成事件结果的原因类型: 参考RISK.ReasonType
     *
     * @mbg.generated
     */
    public String getLogEventReasonType() {
        return logEventReasonType;
    }

    /**
     * 造成事件结果的原因类型: 参考RISK.ReasonType
     * @param logEventReasonType 造成事件结果的原因类型: 参考RISK.ReasonType
     *
     * @mbg.generated
     */
    public void setLogEventReasonType(String logEventReasonType) {
        this.logEventReasonType = logEventReasonType;
    }

    /**
     * LOG节点
     * @return log_node LOG节点
     *
     * @mbg.generated
     */
    public String getLogNode() {
        return logNode;
    }

    /**
     * LOG节点
     * @param logNode LOG节点
     *
     * @mbg.generated
     */
    public void setLogNode(String logNode) {
        this.logNode = logNode;
    }

    /**
     * 日志等级; 0=DEBUG, 1=INFO, 2=WARN, 3=ERROR 
     * @return log_level 日志等级; 0=DEBUG, 1=INFO, 2=WARN, 3=ERROR 
     *
     * @mbg.generated
     */
    public String getLogLevel() {
        return logLevel;
    }

    /**
     * 日志等级; 0=DEBUG, 1=INFO, 2=WARN, 3=ERROR 
     * @param logLevel 日志等级; 0=DEBUG, 1=INFO, 2=WARN, 3=ERROR 
     *
     * @mbg.generated
     */
    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * 错误代码
     * @return log_code 错误代码
     *
     * @mbg.generated
     */
    public String getLogCode() {
        return logCode;
    }

    /**
     * 错误代码
     * @param logCode 错误代码
     *
     * @mbg.generated
     */
    public void setLogCode(String logCode) {
        this.logCode = logCode;
    }

    /**
     * 日志信息: Json格式，包含 format 与 args, 范例 _FMT:{"msgFormat":"商户 %s 之订单号 %s 重复", "msgArgs": ["MER9900123","ORD00000168"]}
     * @return log_msg 日志信息: Json格式，包含 format 与 args, 范例 _FMT:{"msgFormat":"商户 %s 之订单号 %s 重复", "msgArgs": ["MER9900123","ORD00000168"]}
     *
     * @mbg.generated
     */
    public String getLogMsg() {
        return logMsg;
    }

    /**
     * 日志信息: Json格式，包含 format 与 args, 范例 _FMT:{"msgFormat":"商户 %s 之订单号 %s 重复", "msgArgs": ["MER9900123","ORD00000168"]}
     * @param logMsg 日志信息: Json格式，包含 format 与 args, 范例 _FMT:{"msgFormat":"商户 %s 之订单号 %s 重复", "msgArgs": ["MER9900123","ORD00000168"]}
     *
     * @mbg.generated
     */
    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    /**
     * 日志附加参数
     * @return log_params 日志附加参数
     *
     * @mbg.generated
     */
    public String getLogParams() {
        return logParams;
    }

    /**
     * 日志附加参数
     * @param logParams 日志附加参数
     *
     * @mbg.generated
     */
    public void setLogParams(String logParams) {
        this.logParams = logParams;
    }

    /**
     * 内部交易编号(仅适用交易相关LOG)
     * @return trans_seq_id 内部交易编号(仅适用交易相关LOG)
     *
     * @mbg.generated
     */
    public String getTransSeqId() {
        return transSeqId;
    }

    /**
     * 内部交易编号(仅适用交易相关LOG)
     * @param transSeqId 内部交易编号(仅适用交易相关LOG)
     *
     * @mbg.generated
     */
    public void setTransSeqId(String transSeqId) {
        this.transSeqId = transSeqId;
    }

    /**
     * 交易类型(4位数)(仅适用交易相关LOG)
     * @return int_trans_cd 交易类型(4位数)(仅适用交易相关LOG)
     *
     * @mbg.generated
     */
    public String getIntTransCd() {
        return intTransCd;
    }

    /**
     * 交易类型(4位数)(仅适用交易相关LOG)
     * @param intTransCd 交易类型(4位数)(仅适用交易相关LOG)
     *
     * @mbg.generated
     */
    public void setIntTransCd(String intTransCd) {
        this.intTransCd = intTransCd;
    }

    /**
     * 前端商户号, 若为BM用户则填"_BM"
     * @return mchnt_cd 前端商户号, 若为BM用户则填"_BM"
     *
     * @mbg.generated
     */
    public String getMchntCd() {
        return mchntCd;
    }

    /**
     * 前端商户号, 若为BM用户则填"_BM"
     * @param mchntCd 前端商户号, 若为BM用户则填"_BM"
     *
     * @mbg.generated
     */
    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd;
    }

    /**
     * 使用者帐号, 若为BM用户则填"_BM"
     * @return user_id 使用者帐号, 若为BM用户则填"_BM"
     *
     * @mbg.generated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 使用者帐号, 若为BM用户则填"_BM"
     * @param userId 使用者帐号, 若为BM用户则填"_BM"
     *
     * @mbg.generated
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 订单号,客户端订单号,同一商户当日必须唯一(仅适用交易相关LOG)
     * @return order_id 订单号,客户端订单号,同一商户当日必须唯一(仅适用交易相关LOG)
     *
     * @mbg.generated
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 订单号,客户端订单号,同一商户当日必须唯一(仅适用交易相关LOG)
     * @param orderId 订单号,客户端订单号,同一商户当日必须唯一(仅适用交易相关LOG)
     *
     * @mbg.generated
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 渠道编号(仅适用交易相关LOG)
     * @return trans_chnl 渠道编号(仅适用交易相关LOG)
     *
     * @mbg.generated
     */
    public String getTransChnl() {
        return transChnl;
    }

    /**
     * 渠道编号(仅适用交易相关LOG)
     * @param transChnl 渠道编号(仅适用交易相关LOG)
     *
     * @mbg.generated
     */
    public void setTransChnl(String transChnl) {
        this.transChnl = transChnl;
    }

    /**
     * 渠道商户号(仅适用交易相关LOG)
     * @return chnl_mchnt_cd 渠道商户号(仅适用交易相关LOG)
     *
     * @mbg.generated
     */
    public String getChnlMchntCd() {
        return chnlMchntCd;
    }

    /**
     * 渠道商户号(仅适用交易相关LOG)
     * @param chnlMchntCd 渠道商户号(仅适用交易相关LOG)
     *
     * @mbg.generated
     */
    public void setChnlMchntCd(String chnlMchntCd) {
        this.chnlMchntCd = chnlMchntCd;
    }

    /**
     * 渠道订单号(仅适用交易相关LOG)
     * @return chnl_order_id 渠道订单号(仅适用交易相关LOG)
     *
     * @mbg.generated
     */
    public String getChnlOrderId() {
        return chnlOrderId;
    }

    /**
     * 渠道订单号(仅适用交易相关LOG)
     * @param chnlOrderId 渠道订单号(仅适用交易相关LOG)
     *
     * @mbg.generated
     */
    public void setChnlOrderId(String chnlOrderId) {
        this.chnlOrderId = chnlOrderId;
    }

    /**
     * Session ID
     * @return session_id Session ID
     *
     * @mbg.generated
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Session ID
     * @param sessionId Session ID
     *
     * @mbg.generated
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Exception 类名(短名称)
     * @return error_cls Exception 类名(短名称)
     *
     * @mbg.generated
     */
    public String getErrorCls() {
        return errorCls;
    }

    /**
     * Exception 类名(短名称)
     * @param errorCls Exception 类名(短名称)
     *
     * @mbg.generated
     */
    public void setErrorCls(String errorCls) {
        this.errorCls = errorCls;
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
     * Database table : tbl_event_log_01
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
        EventLog other = (EventLog) that;
        return (this.getLogId() == null ? other.getLogId() == null : this.getLogId().equals(other.getLogId()))
            && (this.getLogTs() == null ? other.getLogTs() == null : this.getLogTs().equals(other.getLogTs()))
            && (this.getLogDate() == null ? other.getLogDate() == null : this.getLogDate().equals(other.getLogDate()))
            && (this.getLogTime() == null ? other.getLogTime() == null : this.getLogTime().equals(other.getLogTime()))
            && (this.getLogRole() == null ? other.getLogRole() == null : this.getLogRole().equals(other.getLogRole()))
            && (this.getLogSrc() == null ? other.getLogSrc() == null : this.getLogSrc().equals(other.getLogSrc()))
            && (this.getLogEvent() == null ? other.getLogEvent() == null : this.getLogEvent().equals(other.getLogEvent()))
            && (this.getLogEventTarget() == null ? other.getLogEventTarget() == null : this.getLogEventTarget().equals(other.getLogEventTarget()))
            && (this.getLogEventResult() == null ? other.getLogEventResult() == null : this.getLogEventResult().equals(other.getLogEventResult()))
            && (this.getLogEventReason() == null ? other.getLogEventReason() == null : this.getLogEventReason().equals(other.getLogEventReason()))
            && (this.getLogEventReasonType() == null ? other.getLogEventReasonType() == null : this.getLogEventReasonType().equals(other.getLogEventReasonType()))
            && (this.getLogNode() == null ? other.getLogNode() == null : this.getLogNode().equals(other.getLogNode()))
            && (this.getLogLevel() == null ? other.getLogLevel() == null : this.getLogLevel().equals(other.getLogLevel()))
            && (this.getLogCode() == null ? other.getLogCode() == null : this.getLogCode().equals(other.getLogCode()))
            && (this.getLogMsg() == null ? other.getLogMsg() == null : this.getLogMsg().equals(other.getLogMsg()))
            && (this.getLogParams() == null ? other.getLogParams() == null : this.getLogParams().equals(other.getLogParams()))
            && (this.getTransSeqId() == null ? other.getTransSeqId() == null : this.getTransSeqId().equals(other.getTransSeqId()))
            && (this.getIntTransCd() == null ? other.getIntTransCd() == null : this.getIntTransCd().equals(other.getIntTransCd()))
            && (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getTransChnl() == null ? other.getTransChnl() == null : this.getTransChnl().equals(other.getTransChnl()))
            && (this.getChnlMchntCd() == null ? other.getChnlMchntCd() == null : this.getChnlMchntCd().equals(other.getChnlMchntCd()))
            && (this.getChnlOrderId() == null ? other.getChnlOrderId() == null : this.getChnlOrderId().equals(other.getChnlOrderId()))
            && (this.getSessionId() == null ? other.getSessionId() == null : this.getSessionId().equals(other.getSessionId()))
            && (this.getErrorCls() == null ? other.getErrorCls() == null : this.getErrorCls().equals(other.getErrorCls()))
            && (this.getTags() == null ? other.getTags() == null : this.getTags().equals(other.getTags()));
    }

    /**
     * Database table : tbl_event_log_01
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLogId() == null) ? 0 : getLogId().hashCode());
        result = prime * result + ((getLogTs() == null) ? 0 : getLogTs().hashCode());
        result = prime * result + ((getLogDate() == null) ? 0 : getLogDate().hashCode());
        result = prime * result + ((getLogTime() == null) ? 0 : getLogTime().hashCode());
        result = prime * result + ((getLogRole() == null) ? 0 : getLogRole().hashCode());
        result = prime * result + ((getLogSrc() == null) ? 0 : getLogSrc().hashCode());
        result = prime * result + ((getLogEvent() == null) ? 0 : getLogEvent().hashCode());
        result = prime * result + ((getLogEventTarget() == null) ? 0 : getLogEventTarget().hashCode());
        result = prime * result + ((getLogEventResult() == null) ? 0 : getLogEventResult().hashCode());
        result = prime * result + ((getLogEventReason() == null) ? 0 : getLogEventReason().hashCode());
        result = prime * result + ((getLogEventReasonType() == null) ? 0 : getLogEventReasonType().hashCode());
        result = prime * result + ((getLogNode() == null) ? 0 : getLogNode().hashCode());
        result = prime * result + ((getLogLevel() == null) ? 0 : getLogLevel().hashCode());
        result = prime * result + ((getLogCode() == null) ? 0 : getLogCode().hashCode());
        result = prime * result + ((getLogMsg() == null) ? 0 : getLogMsg().hashCode());
        result = prime * result + ((getLogParams() == null) ? 0 : getLogParams().hashCode());
        result = prime * result + ((getTransSeqId() == null) ? 0 : getTransSeqId().hashCode());
        result = prime * result + ((getIntTransCd() == null) ? 0 : getIntTransCd().hashCode());
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getTransChnl() == null) ? 0 : getTransChnl().hashCode());
        result = prime * result + ((getChnlMchntCd() == null) ? 0 : getChnlMchntCd().hashCode());
        result = prime * result + ((getChnlOrderId() == null) ? 0 : getChnlOrderId().hashCode());
        result = prime * result + ((getSessionId() == null) ? 0 : getSessionId().hashCode());
        result = prime * result + ((getErrorCls() == null) ? 0 : getErrorCls().hashCode());
        result = prime * result + ((getTags() == null) ? 0 : getTags().hashCode());
        return result;
    }

    /**
     * Database table : tbl_event_log_01
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
        sb.append(", logTs=").append(logTs);
        sb.append(", logDate=").append(logDate);
        sb.append(", logTime=").append(logTime);
        sb.append(", logRole=").append(logRole);
        sb.append(", logSrc=").append(logSrc);
        sb.append(", logEvent=").append(logEvent);
        sb.append(", logEventTarget=").append(logEventTarget);
        sb.append(", logEventResult=").append(logEventResult);
        sb.append(", logEventReason=").append(logEventReason);
        sb.append(", logEventReasonType=").append(logEventReasonType);
        sb.append(", logNode=").append(logNode);
        sb.append(", logLevel=").append(logLevel);
        sb.append(", logCode=").append(logCode);
        sb.append(", logMsg=").append(logMsg);
        sb.append(", logParams=").append(logParams);
        sb.append(", transSeqId=").append(transSeqId);
        sb.append(", intTransCd=").append(intTransCd);
        sb.append(", mchntCd=").append(mchntCd);
        sb.append(", userId=").append(userId);
        sb.append(", orderId=").append(orderId);
        sb.append(", transChnl=").append(transChnl);
        sb.append(", chnlMchntCd=").append(chnlMchntCd);
        sb.append(", chnlOrderId=").append(chnlOrderId);
        sb.append(", sessionId=").append(sessionId);
        sb.append(", errorCls=").append(errorCls);
        sb.append(", tags=").append(tags);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(EventLog source) {
        this.logId=source.logId;
        this.logTs=source.logTs;
        this.logDate=source.logDate;
        this.logTime=source.logTime;
        this.logRole=source.logRole;
        this.logSrc=source.logSrc;
        this.logEvent=source.logEvent;
        this.logEventTarget=source.logEventTarget;
        this.logEventResult=source.logEventResult;
        this.logEventReason=source.logEventReason;
        this.logEventReasonType=source.logEventReasonType;
        this.logNode=source.logNode;
        this.logLevel=source.logLevel;
        this.logCode=source.logCode;
        this.logMsg=source.logMsg;
        this.logParams=source.logParams;
        this.transSeqId=source.transSeqId;
        this.intTransCd=source.intTransCd;
        this.mchntCd=source.mchntCd;
        this.userId=source.userId;
        this.orderId=source.orderId;
        this.transChnl=source.transChnl;
        this.chnlMchntCd=source.chnlMchntCd;
        this.chnlOrderId=source.chnlOrderId;
        this.sessionId=source.sessionId;
        this.errorCls=source.errorCls;
        this.tags=source.tags;
    }
}