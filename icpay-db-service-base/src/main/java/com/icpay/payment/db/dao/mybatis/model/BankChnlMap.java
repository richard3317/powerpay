package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_bank_chnl_map
 * @author user
 *
 * @mbg.generated
 */
public class BankChnlMap extends BankChnlMapKey implements Serializable {
    /**
     * 渠道银行编号
     * Database column : tbl_bank_chnl_map.chnl_bank_num
     *
     * @mbg.generated
     */
    private String chnlBankNum;

    /**
     * 渠道银行名称(选填)
     * Database column : tbl_bank_chnl_map.chnl_bank_name
     *
     * @mbg.generated
     */
    private String chnlBankName;

    /**
     * 状态(1=有效,其他无效)
     * Database column : tbl_bank_chnl_map.state
     *
     * @mbg.generated
     */
    private String state;

    /**
     * 排序用，由小至大
     * Database column : tbl_bank_chnl_map.sort_seq
     *
     * @mbg.generated
     */
    private Integer sortSeq;

    /**
     * 备注
     * Database column : tbl_bank_chnl_map.comment
     *
     * @mbg.generated
     */
    private String comment;

    /**
     * 标签
     * Database column : tbl_bank_chnl_map.tags
     *
     * @mbg.generated
     */
    private String tags;

    /**
     * Database column : tbl_bank_chnl_map.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_bank_chnl_map.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    public BankChnlMap(String intTransCd, String currCd, String bankNum, String chnlId, String chnlBankNum, String chnlBankName, String state, Integer sortSeq, String comment, String tags, Date recCrtTs, Date recUpdTs) {
        super(intTransCd, currCd, bankNum, chnlId);
        this.chnlBankNum = chnlBankNum;
        this.chnlBankName = chnlBankName;
        this.state = state;
        this.sortSeq = sortSeq;
        this.comment = comment;
        this.tags = tags;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    public BankChnlMap() {
        super();
    }

    /**
     * 渠道银行编号
     * @return chnl_bank_num 渠道银行编号
     *
     * @mbg.generated
     */
    public String getChnlBankNum() {
        return chnlBankNum;
    }

    /**
     * 渠道银行编号
     * @param chnlBankNum 渠道银行编号
     *
     * @mbg.generated
     */
    public void setChnlBankNum(String chnlBankNum) {
        this.chnlBankNum = chnlBankNum;
    }

    /**
     * 渠道银行名称(选填)
     * @return chnl_bank_name 渠道银行名称(选填)
     *
     * @mbg.generated
     */
    public String getChnlBankName() {
        return chnlBankName;
    }

    /**
     * 渠道银行名称(选填)
     * @param chnlBankName 渠道银行名称(选填)
     *
     * @mbg.generated
     */
    public void setChnlBankName(String chnlBankName) {
        this.chnlBankName = chnlBankName;
    }

    /**
     * 状态(1=有效,其他无效)
     * @return state 状态(1=有效,其他无效)
     *
     * @mbg.generated
     */
    public String getState() {
        return state;
    }

    /**
     * 状态(1=有效,其他无效)
     * @param state 状态(1=有效,其他无效)
     *
     * @mbg.generated
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 排序用，由小至大
     * @return sort_seq 排序用，由小至大
     *
     * @mbg.generated
     */
    public Integer getSortSeq() {
        return sortSeq;
    }

    /**
     * 排序用，由小至大
     * @param sortSeq 排序用，由小至大
     *
     * @mbg.generated
     */
    public void setSortSeq(Integer sortSeq) {
        this.sortSeq = sortSeq;
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
     * Database table : tbl_bank_chnl_map
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
        BankChnlMap other = (BankChnlMap) that;
        return (this.getIntTransCd() == null ? other.getIntTransCd() == null : this.getIntTransCd().equals(other.getIntTransCd()))
            && (this.getCurrCd() == null ? other.getCurrCd() == null : this.getCurrCd().equals(other.getCurrCd()))
            && (this.getBankNum() == null ? other.getBankNum() == null : this.getBankNum().equals(other.getBankNum()))
            && (this.getChnlId() == null ? other.getChnlId() == null : this.getChnlId().equals(other.getChnlId()))
            && (this.getChnlBankNum() == null ? other.getChnlBankNum() == null : this.getChnlBankNum().equals(other.getChnlBankNum()))
            && (this.getChnlBankName() == null ? other.getChnlBankName() == null : this.getChnlBankName().equals(other.getChnlBankName()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getSortSeq() == null ? other.getSortSeq() == null : this.getSortSeq().equals(other.getSortSeq()))
            && (this.getComment() == null ? other.getComment() == null : this.getComment().equals(other.getComment()))
            && (this.getTags() == null ? other.getTags() == null : this.getTags().equals(other.getTags()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIntTransCd() == null) ? 0 : getIntTransCd().hashCode());
        result = prime * result + ((getCurrCd() == null) ? 0 : getCurrCd().hashCode());
        result = prime * result + ((getBankNum() == null) ? 0 : getBankNum().hashCode());
        result = prime * result + ((getChnlId() == null) ? 0 : getChnlId().hashCode());
        result = prime * result + ((getChnlBankNum() == null) ? 0 : getChnlBankNum().hashCode());
        result = prime * result + ((getChnlBankName() == null) ? 0 : getChnlBankName().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getSortSeq() == null) ? 0 : getSortSeq().hashCode());
        result = prime * result + ((getComment() == null) ? 0 : getComment().hashCode());
        result = prime * result + ((getTags() == null) ? 0 : getTags().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_bank_chnl_map
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
        sb.append(", chnlBankNum=").append(chnlBankNum);
        sb.append(", chnlBankName=").append(chnlBankName);
        sb.append(", state=").append(state);
        sb.append(", sortSeq=").append(sortSeq);
        sb.append(", comment=").append(comment);
        sb.append(", tags=").append(tags);
        sb.append(", recCrtTs=").append(recCrtTs);
        sb.append(", recUpdTs=").append(recUpdTs);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Database table : tbl_bank_chnl_map
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
    public void cloneFrom(BankChnlMap source) {
        super.cloneFrom(source);
        this.chnlBankNum=source.chnlBankNum;
        this.chnlBankName=source.chnlBankName;
        this.state=source.state;
        this.sortSeq=source.sortSeq;
        this.comment=source.comment;
        this.tags=source.tags;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
    }
}