package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_mer_params

 *
 * @mbg.generated
 */
public class MerParams extends MerParamsKey implements Serializable {
    /**
     * 参数值
     * Database column : tbl_mer_params.param_value
     *
     * @mbg.generated
     */
    private String paramValue;

    /**
     * 顺序号(纯排序)
     * Database column : tbl_mer_params.orderSeq
     *
     * @mbg.generated
     */
    private Integer orderseq;

    /**
     * 参数说明
     * Database column : tbl_mer_params.param_desc
     *
     * @mbg.generated
     */
    private String paramDesc;

    /**
     * Database column : tbl_mer_params.last_oper_id
     *
     * @mbg.generated
     */
    private String lastOperId;

    /**
     * Database column : tbl_mer_params.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_mer_params.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_mer_params
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_mer_params
     *
     * @mbg.generated
     */
    public MerParams(String chnlId, String mchntCd, String paramCat, String paramId, String paramValue, Integer orderseq, String paramDesc, String lastOperId, Date recCrtTs, Date recUpdTs) {
        super(chnlId, mchntCd, paramCat, paramId);
        this.paramValue = paramValue;
        this.orderseq = orderseq;
        this.paramDesc = paramDesc;
        this.lastOperId = lastOperId;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_mer_params
     *
     * @mbg.generated
     */
    public MerParams() {
        super();
    }

    /**
     * 参数值
     * @return param_value 参数值
     *
     * @mbg.generated
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * 参数值
     * @param paramValue 参数值
     *
     * @mbg.generated
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    /**
     * 顺序号(纯排序)
     * @return orderSeq 顺序号(纯排序)
     *
     * @mbg.generated
     */
    public Integer getOrderseq() {
        return orderseq;
    }

    /**
     * 顺序号(纯排序)
     * @param orderseq 顺序号(纯排序)
     *
     * @mbg.generated
     */
    public void setOrderseq(Integer orderseq) {
        this.orderseq = orderseq;
    }

    /**
     * 参数说明
     * @return param_desc 参数说明
     *
     * @mbg.generated
     */
    public String getParamDesc() {
        return paramDesc;
    }

    /**
     * 参数说明
     * @param paramDesc 参数说明
     *
     * @mbg.generated
     */
    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
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
     * Database table : tbl_mer_params
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
        MerParams other = (MerParams) that;
        return (this.getChnlId() == null ? other.getChnlId() == null : this.getChnlId().equals(other.getChnlId()))
            && (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()))
            && (this.getParamCat() == null ? other.getParamCat() == null : this.getParamCat().equals(other.getParamCat()))
            && (this.getParamId() == null ? other.getParamId() == null : this.getParamId().equals(other.getParamId()))
            && (this.getParamValue() == null ? other.getParamValue() == null : this.getParamValue().equals(other.getParamValue()))
            && (this.getOrderseq() == null ? other.getOrderseq() == null : this.getOrderseq().equals(other.getOrderseq()))
            && (this.getParamDesc() == null ? other.getParamDesc() == null : this.getParamDesc().equals(other.getParamDesc()))
            && (this.getLastOperId() == null ? other.getLastOperId() == null : this.getLastOperId().equals(other.getLastOperId()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_mer_params
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getChnlId() == null) ? 0 : getChnlId().hashCode());
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        result = prime * result + ((getParamCat() == null) ? 0 : getParamCat().hashCode());
        result = prime * result + ((getParamId() == null) ? 0 : getParamId().hashCode());
        result = prime * result + ((getParamValue() == null) ? 0 : getParamValue().hashCode());
        result = prime * result + ((getOrderseq() == null) ? 0 : getOrderseq().hashCode());
        result = prime * result + ((getParamDesc() == null) ? 0 : getParamDesc().hashCode());
        result = prime * result + ((getLastOperId() == null) ? 0 : getLastOperId().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_mer_params
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
        sb.append(", paramValue=").append(paramValue);
        sb.append(", orderseq=").append(orderseq);
        sb.append(", paramDesc=").append(paramDesc);
        sb.append(", lastOperId=").append(lastOperId);
        sb.append(", recCrtTs=").append(recCrtTs);
        sb.append(", recUpdTs=").append(recUpdTs);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Database table : tbl_mer_params
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
    public void cloneFrom(MerParams source) {
        super.cloneFrom(source);
        this.paramValue=source.paramValue;
        this.orderseq=source.orderseq;
        this.paramDesc=source.paramDesc;
        this.lastOperId=source.lastOperId;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
    }
}