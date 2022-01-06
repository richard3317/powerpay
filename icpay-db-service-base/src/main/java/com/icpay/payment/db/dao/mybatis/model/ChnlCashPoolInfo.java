package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_chnl_cash_pool_info

 *
 * @mbg.generated
 */
public class ChnlCashPoolInfo extends ChnlCashPoolInfoKey implements Serializable {
    /**
     * 优先出款时段，例："ALL" 或 "0800-1200,1400-2000"
     * Database column : tbl_chnl_cash_pool_info.tx_time_limit
     *
     * @mbg.generated
     */
    private String txTimeLimit;

    /**
     * 状态(1=有效，0-无效)
     * Database column : tbl_chnl_cash_pool_info.state
     *
     * @mbg.generated
     */
    private String state;

    /**
     * 出款状态(1-优先、0-默认)
     * Database column : tbl_chnl_cash_pool_info.wd_state
     *
     * @mbg.generated
     */
    private String wdState;

    /**
     * 优先等级，数字越小越优先
     * Database column : tbl_chnl_cash_pool_info.priority
     *
     * @mbg.generated
     */
    private Integer priority;

    /**
     * 权重，数字越大机率越高
     * Database column : tbl_chnl_cash_pool_info.weight
     *
     * @mbg.generated
     */
    private Integer weight;

    /**
     * 备注
     * Database column : tbl_chnl_cash_pool_info.comment
     *
     * @mbg.generated
     */
    private String comment;

    /**
     * Database column : tbl_chnl_cash_pool_info.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_chnl_cash_pool_info.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * 操作人
     * Database column : tbl_chnl_cash_pool_info.last_oper_id
     *
     * @mbg.generated
     */
    private String lastOperId;

    /**
     * Database table : tbl_chnl_cash_pool_info
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_chnl_cash_pool_info
     *
     * @mbg.generated
     */
    public ChnlCashPoolInfo(String poolId, String chnlId, String chnlMchntCd, String txTimeLimit, String state, String wdState, Integer priority, Integer weight, String comment, Date recCrtTs, Date recUpdTs, String lastOperId) {
        super(poolId, chnlId, chnlMchntCd);
        this.txTimeLimit = txTimeLimit;
        this.state = state;
        this.wdState = wdState;
        this.priority = priority;
        this.weight = weight;
        this.comment = comment;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
        this.lastOperId = lastOperId;
    }

    /**
     * Database table : tbl_chnl_cash_pool_info
     *
     * @mbg.generated
     */
    public ChnlCashPoolInfo() {
        super();
    }

    /**
     * 优先出款时段，例："ALL" 或 "0800-1200,1400-2000"
     * @return tx_time_limit 优先出款时段，例："ALL" 或 "0800-1200,1400-2000"
     *
     * @mbg.generated
     */
    public String getTxTimeLimit() {
        return txTimeLimit;
    }

    /**
     * 优先出款时段，例："ALL" 或 "0800-1200,1400-2000"
     * @param txTimeLimit 优先出款时段，例："ALL" 或 "0800-1200,1400-2000"
     *
     * @mbg.generated
     */
    public void setTxTimeLimit(String txTimeLimit) {
        this.txTimeLimit = txTimeLimit;
    }

    /**
     * 状态(1=有效，0-无效)
     * @return state 状态(1=有效，0-无效)
     *
     * @mbg.generated
     */
    public String getState() {
        return state;
    }

    /**
     * 状态(1=有效，0-无效)
     * @param state 状态(1=有效，0-无效)
     *
     * @mbg.generated
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 出款状态(1-优先、0-默认)
     * @return wd_state 出款状态(1-优先、0-默认)
     *
     * @mbg.generated
     */
    public String getWdState() {
        return wdState;
    }

    /**
     * 出款状态(1-优先、0-默认)
     * @param wdState 出款状态(1-优先、0-默认)
     *
     * @mbg.generated
     */
    public void setWdState(String wdState) {
        this.wdState = wdState;
    }

    /**
     * 优先等级，数字越小越优先
     * @return priority 优先等级，数字越小越优先
     *
     * @mbg.generated
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * 优先等级，数字越小越优先
     * @param priority 优先等级，数字越小越优先
     *
     * @mbg.generated
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * 权重，数字越大机率越高
     * @return weight 权重，数字越大机率越高
     *
     * @mbg.generated
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * 权重，数字越大机率越高
     * @param weight 权重，数字越大机率越高
     *
     * @mbg.generated
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
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
     * 操作人
     * @return last_oper_id 操作人
     *
     * @mbg.generated
     */
    public String getLastOperId() {
        return lastOperId;
    }

    /**
     * 操作人
     * @param lastOperId 操作人
     *
     * @mbg.generated
     */
    public void setLastOperId(String lastOperId) {
        this.lastOperId = lastOperId;
    }

    /**
     * Database table : tbl_chnl_cash_pool_info
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
        ChnlCashPoolInfo other = (ChnlCashPoolInfo) that;
        return (this.getPoolId() == null ? other.getPoolId() == null : this.getPoolId().equals(other.getPoolId()))
            && (this.getChnlId() == null ? other.getChnlId() == null : this.getChnlId().equals(other.getChnlId()))
            && (this.getChnlMchntCd() == null ? other.getChnlMchntCd() == null : this.getChnlMchntCd().equals(other.getChnlMchntCd()))
            && (this.getTxTimeLimit() == null ? other.getTxTimeLimit() == null : this.getTxTimeLimit().equals(other.getTxTimeLimit()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getWdState() == null ? other.getWdState() == null : this.getWdState().equals(other.getWdState()))
            && (this.getPriority() == null ? other.getPriority() == null : this.getPriority().equals(other.getPriority()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getComment() == null ? other.getComment() == null : this.getComment().equals(other.getComment()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()))
            && (this.getLastOperId() == null ? other.getLastOperId() == null : this.getLastOperId().equals(other.getLastOperId()));
    }

    /**
     * Database table : tbl_chnl_cash_pool_info
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPoolId() == null) ? 0 : getPoolId().hashCode());
        result = prime * result + ((getChnlId() == null) ? 0 : getChnlId().hashCode());
        result = prime * result + ((getChnlMchntCd() == null) ? 0 : getChnlMchntCd().hashCode());
        result = prime * result + ((getTxTimeLimit() == null) ? 0 : getTxTimeLimit().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getWdState() == null) ? 0 : getWdState().hashCode());
        result = prime * result + ((getPriority() == null) ? 0 : getPriority().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getComment() == null) ? 0 : getComment().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        result = prime * result + ((getLastOperId() == null) ? 0 : getLastOperId().hashCode());
        return result;
    }

    /**
     * Database table : tbl_chnl_cash_pool_info
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
        sb.append(", txTimeLimit=").append(txTimeLimit);
        sb.append(", state=").append(state);
        sb.append(", wdState=").append(wdState);
        sb.append(", priority=").append(priority);
        sb.append(", weight=").append(weight);
        sb.append(", comment=").append(comment);
        sb.append(", recCrtTs=").append(recCrtTs);
        sb.append(", recUpdTs=").append(recUpdTs);
        sb.append(", lastOperId=").append(lastOperId);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Database table : tbl_chnl_cash_pool_info
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
    public void cloneFrom(ChnlCashPoolInfo source) {
        super.cloneFrom(source);
        this.txTimeLimit=source.txTimeLimit;
        this.state=source.state;
        this.wdState=source.wdState;
        this.priority=source.priority;
        this.weight=source.weight;
        this.comment=source.comment;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
        this.lastOperId=source.lastOperId;
    }
}