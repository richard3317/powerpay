package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_bm_oper_log

 *
 * @mbg.generated
 */
public class BmOperLog implements Serializable {
    /**
     * Database column : tbl_bm_oper_log.log_id
     *
     * @mbg.generated
     */
    private Integer logId;

    /**
     * Database column : tbl_bm_oper_log.sys_id
     *
     * @mbg.generated
     */
    private String sysId;

    /**
     * Database column : tbl_bm_oper_log.oper_info
     *
     * @mbg.generated
     */
    private String operInfo;

    /**
     * Database column : tbl_bm_oper_log.op_func_info
     *
     * @mbg.generated
     */
    private String opFuncInfo;

    /**
     * 01新增;03修改;05删除，詳見CommonEnums.OpType.class
     * Database column : tbl_bm_oper_log.op_tp
     *
     * @mbg.generated
     */
    private String opTp;

    /**
     * Database column : tbl_bm_oper_log.op_dt
     *
     * @mbg.generated
     */
    private String opDt;

    /**
     * Database column : tbl_bm_oper_log.op_tm
     *
     * @mbg.generated
     */
    private String opTm;

    /**
     * Database column : tbl_bm_oper_log.op_ip
     *
     * @mbg.generated
     */
    private String opIp;

    /**
     * Database column : tbl_bm_oper_log.op_content_tp
     *
     * @mbg.generated
     */
    private String opContentTp;

    /**
     * Database column : tbl_bm_oper_log.op_content
     *
     * @mbg.generated
     */
    private String opContent;

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    public BmOperLog(Integer logId, String sysId, String operInfo, String opFuncInfo, String opTp, String opDt, String opTm, String opIp, String opContentTp) {
        this.logId = logId;
        this.sysId = sysId;
        this.operInfo = operInfo;
        this.opFuncInfo = opFuncInfo;
        this.opTp = opTp;
        this.opDt = opDt;
        this.opTm = opTm;
        this.opIp = opIp;
        this.opContentTp = opContentTp;
    }

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    public BmOperLog(Integer logId, String sysId, String operInfo, String opFuncInfo, String opTp, String opDt, String opTm, String opIp, String opContentTp, String opContent) {
        this.logId = logId;
        this.sysId = sysId;
        this.operInfo = operInfo;
        this.opFuncInfo = opFuncInfo;
        this.opTp = opTp;
        this.opDt = opDt;
        this.opTm = opTm;
        this.opIp = opIp;
        this.opContentTp = opContentTp;
        this.opContent = opContent;
    }

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    public BmOperLog() {
        super();
    }

    /**
     * @return log_id 
     *
     * @mbg.generated
     */
    public Integer getLogId() {
        return logId;
    }

    /**
     * @param logId 
     *
     * @mbg.generated
     */
    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    /**
     * @return sys_id 
     *
     * @mbg.generated
     */
    public String getSysId() {
        return sysId;
    }

    /**
     * @param sysId 
     *
     * @mbg.generated
     */
    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    /**
     * @return oper_info 
     *
     * @mbg.generated
     */
    public String getOperInfo() {
        return operInfo;
    }

    /**
     * @param operInfo 
     *
     * @mbg.generated
     */
    public void setOperInfo(String operInfo) {
        this.operInfo = operInfo;
    }

    /**
     * @return op_func_info 
     *
     * @mbg.generated
     */
    public String getOpFuncInfo() {
        return opFuncInfo;
    }

    /**
     * @param opFuncInfo 
     *
     * @mbg.generated
     */
    public void setOpFuncInfo(String opFuncInfo) {
        this.opFuncInfo = opFuncInfo;
    }

    /**
     * 01新增;03修改;05删除，詳見CommonEnums.OpType.class
     * @return op_tp 01新增;03修改;05删除，詳見CommonEnums.OpType.class
     *
     * @mbg.generated
     */
    public String getOpTp() {
        return opTp;
    }

    /**
     * 01新增;03修改;05删除，詳見CommonEnums.OpType.class
     * @param opTp 01新增;03修改;05删除，詳見CommonEnums.OpType.class
     *
     * @mbg.generated
     */
    public void setOpTp(String opTp) {
        this.opTp = opTp;
    }

    /**
     * @return op_dt 
     *
     * @mbg.generated
     */
    public String getOpDt() {
        return opDt;
    }

    /**
     * @param opDt 
     *
     * @mbg.generated
     */
    public void setOpDt(String opDt) {
        this.opDt = opDt;
    }

    /**
     * @return op_tm 
     *
     * @mbg.generated
     */
    public String getOpTm() {
        return opTm;
    }

    /**
     * @param opTm 
     *
     * @mbg.generated
     */
    public void setOpTm(String opTm) {
        this.opTm = opTm;
    }

    /**
     * @return op_ip 
     *
     * @mbg.generated
     */
    public String getOpIp() {
        return opIp;
    }

    /**
     * @param opIp 
     *
     * @mbg.generated
     */
    public void setOpIp(String opIp) {
        this.opIp = opIp;
    }

    /**
     * @return op_content_tp 
     *
     * @mbg.generated
     */
    public String getOpContentTp() {
        return opContentTp;
    }

    /**
     * @param opContentTp 
     *
     * @mbg.generated
     */
    public void setOpContentTp(String opContentTp) {
        this.opContentTp = opContentTp;
    }

    /**
     * @return op_content 
     *
     * @mbg.generated
     */
    public String getOpContent() {
        return opContent;
    }

    /**
     * @param opContent 
     *
     * @mbg.generated
     */
    public void setOpContent(String opContent) {
        this.opContent = opContent;
    }

    /**
     * Database table : tbl_bm_oper_log
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
        BmOperLog other = (BmOperLog) that;
        return (this.getLogId() == null ? other.getLogId() == null : this.getLogId().equals(other.getLogId()))
            && (this.getSysId() == null ? other.getSysId() == null : this.getSysId().equals(other.getSysId()))
            && (this.getOperInfo() == null ? other.getOperInfo() == null : this.getOperInfo().equals(other.getOperInfo()))
            && (this.getOpFuncInfo() == null ? other.getOpFuncInfo() == null : this.getOpFuncInfo().equals(other.getOpFuncInfo()))
            && (this.getOpTp() == null ? other.getOpTp() == null : this.getOpTp().equals(other.getOpTp()))
            && (this.getOpDt() == null ? other.getOpDt() == null : this.getOpDt().equals(other.getOpDt()))
            && (this.getOpTm() == null ? other.getOpTm() == null : this.getOpTm().equals(other.getOpTm()))
            && (this.getOpIp() == null ? other.getOpIp() == null : this.getOpIp().equals(other.getOpIp()))
            && (this.getOpContentTp() == null ? other.getOpContentTp() == null : this.getOpContentTp().equals(other.getOpContentTp()))
            && (this.getOpContent() == null ? other.getOpContent() == null : this.getOpContent().equals(other.getOpContent()));
    }

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLogId() == null) ? 0 : getLogId().hashCode());
        result = prime * result + ((getSysId() == null) ? 0 : getSysId().hashCode());
        result = prime * result + ((getOperInfo() == null) ? 0 : getOperInfo().hashCode());
        result = prime * result + ((getOpFuncInfo() == null) ? 0 : getOpFuncInfo().hashCode());
        result = prime * result + ((getOpTp() == null) ? 0 : getOpTp().hashCode());
        result = prime * result + ((getOpDt() == null) ? 0 : getOpDt().hashCode());
        result = prime * result + ((getOpTm() == null) ? 0 : getOpTm().hashCode());
        result = prime * result + ((getOpIp() == null) ? 0 : getOpIp().hashCode());
        result = prime * result + ((getOpContentTp() == null) ? 0 : getOpContentTp().hashCode());
        result = prime * result + ((getOpContent() == null) ? 0 : getOpContent().hashCode());
        return result;
    }

    /**
     * Database table : tbl_bm_oper_log
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
        sb.append(", sysId=").append(sysId);
        sb.append(", operInfo=").append(operInfo);
        sb.append(", opFuncInfo=").append(opFuncInfo);
        sb.append(", opTp=").append(opTp);
        sb.append(", opDt=").append(opDt);
        sb.append(", opTm=").append(opTm);
        sb.append(", opIp=").append(opIp);
        sb.append(", opContentTp=").append(opContentTp);
        sb.append(", opContent=").append(opContent);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(BmOperLog source) {
        this.logId=source.logId;
        this.sysId=source.sysId;
        this.operInfo=source.operInfo;
        this.opFuncInfo=source.opFuncInfo;
        this.opTp=source.opTp;
        this.opDt=source.opDt;
        this.opTm=source.opTm;
        this.opIp=source.opIp;
        this.opContentTp=source.opContentTp;
        this.opContent=source.opContent;
    }
}