package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_chnl_mchnt_mapping_info

 *
 * @mbg.generated
 */
public class ChnlMchntMappingInfo extends ChnlMchntMappingInfoKey implements Serializable {
    /**
     * Database column : tbl_chnl_mchnt_mapping_info.last_oper_id
     *
     * @mbg.generated
     */
    private String lastOperId;

    /**
     * Database column : tbl_chnl_mchnt_mapping_info.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_chnl_mchnt_mapping_info.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database column : tbl_chnl_mchnt_mapping_info.comment
     *
     * @mbg.generated
     */
    private String comment;

    /**
     * Database table : tbl_chnl_mchnt_mapping_info
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_chnl_mchnt_mapping_info
     *
     * @mbg.generated
     */
    public ChnlMchntMappingInfo(String mchntCd, String chnlId, String chnlMchntCd, String lastOperId, Date recCrtTs, Date recUpdTs, String comment) {
        super(mchntCd, chnlId, chnlMchntCd);
        this.lastOperId = lastOperId;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
        this.comment = comment;
    }

    /**
     * Database table : tbl_chnl_mchnt_mapping_info
     *
     * @mbg.generated
     */
    public ChnlMchntMappingInfo() {
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
     * Database table : tbl_chnl_mchnt_mapping_info
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
        ChnlMchntMappingInfo other = (ChnlMchntMappingInfo) that;
        return (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()))
            && (this.getChnlId() == null ? other.getChnlId() == null : this.getChnlId().equals(other.getChnlId()))
            && (this.getChnlMchntCd() == null ? other.getChnlMchntCd() == null : this.getChnlMchntCd().equals(other.getChnlMchntCd()))
            && (this.getLastOperId() == null ? other.getLastOperId() == null : this.getLastOperId().equals(other.getLastOperId()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()))
            && (this.getComment() == null ? other.getComment() == null : this.getComment().equals(other.getComment()));
    }

    /**
     * Database table : tbl_chnl_mchnt_mapping_info
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        result = prime * result + ((getChnlId() == null) ? 0 : getChnlId().hashCode());
        result = prime * result + ((getChnlMchntCd() == null) ? 0 : getChnlMchntCd().hashCode());
        result = prime * result + ((getLastOperId() == null) ? 0 : getLastOperId().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        result = prime * result + ((getComment() == null) ? 0 : getComment().hashCode());
        return result;
    }

    /**
     * Database table : tbl_chnl_mchnt_mapping_info
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
        sb.append(", comment=").append(comment);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Database table : tbl_chnl_mchnt_mapping_info
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
    public void cloneFrom(ChnlMchntMappingInfo source) {
        super.cloneFrom(source);
        this.lastOperId=source.lastOperId;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
        this.comment=source.comment;
    }
}