package com.icpay.payment.db.dao.mybatis.model.modelExt;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.icpay.payment.db.dao.mybatis.model.OrganInfo;
import com.icpay.payment.db.dao.mybatis.model.OrganMchntInfoKey;

/**
 * @author vicky
 *
 * @mbg.generated
 */
public class OrganMchntExtInfo extends OrganMchntInfoKey implements Serializable {
    /**
     * 状态(1=有效)
     * Database column : tbl_organ_mchnt_info.state
     *
     * @mbg.generated
     */
    private String state;

    /**
     * 备注
     * Database column : tbl_organ_mchnt_info.comment
     *
     * @mbg.generated
     */
    private String comment;

    /**
     * Database column : tbl_organ_mchnt_info.last_oper_id
     *
     * @mbg.generated
     */
    private String lastOperId;

    /**
     * Database column : tbl_organ_mchnt_info.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_organ_mchnt_info.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;
    
    
    private String [] mchntCdStr;
    private List<OrganInfo> organIdList;
    private String mchntCnNm;
    private String organDesc;

    public String[] getMchntCdStr() {
		return mchntCdStr;
	}

	public void setMchntCdStr(String[] mchntCdStr) {
		this.mchntCdStr = mchntCdStr;
	}

	public List<OrganInfo> getOrganIdList() {
		return organIdList;
	}

	public void setOrganIdList(List<OrganInfo> organIdList) {
		this.organIdList = organIdList;
	}

	public String getMchntCnNm() {
		return mchntCnNm;
	}

	public void setMchntCnNm(String mchntCnNm) {
		this.mchntCnNm = mchntCnNm;
	}

	public String getOrganDesc() {
		return organDesc;
	}

	public void setOrganDesc(String organDesc) {
		this.organDesc = organDesc;
	}

	/**
     * Database table : tbl_organ_mchnt_info
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_organ_mchnt_info
     *
     * @mbg.generated
     */
    public OrganMchntExtInfo(String organId, String mchntCd, String state, String comment, String lastOperId, Date recCrtTs, Date recUpdTs) {
        super(organId, mchntCd);
        this.state = state;
        this.comment = comment;
        this.lastOperId = lastOperId;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_organ_mchnt_info
     *
     * @mbg.generated
     */
    public OrganMchntExtInfo() {
        super();
    }

    /**
     * 状态(1=有效)
     * @return state 状态(1=有效)
     *
     * @mbg.generated
     */
    public String getState() {
        return state;
    }

    /**
     * 状态(1=有效)
     * @param state 状态(1=有效)
     *
     * @mbg.generated
     */
    public void setState(String state) {
        this.state = state;
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
     * Database table : tbl_organ_mchnt_info
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
        OrganMchntExtInfo other = (OrganMchntExtInfo) that;
        return (this.getOrganId() == null ? other.getOrganId() == null : this.getOrganId().equals(other.getOrganId()))
            && (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getComment() == null ? other.getComment() == null : this.getComment().equals(other.getComment()))
            && (this.getLastOperId() == null ? other.getLastOperId() == null : this.getLastOperId().equals(other.getLastOperId()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_organ_mchnt_info
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrganId() == null) ? 0 : getOrganId().hashCode());
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getComment() == null) ? 0 : getComment().hashCode());
        result = prime * result + ((getLastOperId() == null) ? 0 : getLastOperId().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_organ_mchnt_info
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
        sb.append(", state=").append(state);
        sb.append(", comment=").append(comment);
        sb.append(", lastOperId=").append(lastOperId);
        sb.append(", recCrtTs=").append(recCrtTs);
        sb.append(", recUpdTs=").append(recUpdTs);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Database table : tbl_organ_mchnt_info
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
    public void cloneFrom(OrganMchntExtInfo source) {
        super.cloneFrom(source);
        this.state=source.state;
        this.comment=source.comment;
        this.lastOperId=source.lastOperId;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
    }
}