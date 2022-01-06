package com.icpay.payment.db.dao.mybatis.model.modelExt;

import java.io.Serializable;
import java.util.Date;

public class RoutingPriorityExt implements Serializable{
	/**
     * 内部交易流水号 
     * Database column : tbl_routing_priority.trans_seq_id
     *
     * @mbg.generated
     */
    private String transSeqId;

    /**
     * 交易类型(0010=消费/支付交易，5210=代付交易)
     * Database column : tbl_routing_priority.int_trans_cd
     *
     * @mbg.generated
     */
    private String intTransCd;

    /**
     * 渠道编号
     * Database column : tbl_routing_priority.chnl_id
     *
     * @mbg.generated
     */
    private String chnlId;

    /**
     * 渠道商户号
     * Database column : tbl_routing_priority.chnl_mchnt_cd
     *
     * @mbg.generated
     */
    private String chnlMchntCd;
    
    /**
     * 优先等级，数字越小越优先
     * Database column : tbl_routing_priority.priority
     *
     * @mbg.generated
     */
    private Integer priority;

    /**
     * 权重，数字越大机率越高;0表示使用餘額作為權重
     * Database column : tbl_routing_priority.weight
     *
     * @mbg.generated
     */
    private Integer weight;

    /**
     * 0=无效，1=有效
     * Database column : tbl_routing_priority.status
     *
     * @mbg.generated
     */
    private String status;

    /**
     * 扩展标签
     * Database column : tbl_routing_priority.tags
     *
     * @mbg.generated
     */
    private String tags;

    /**
     * 路由说明
     * Database column : tbl_routing_priority.route_desc
     *
     * @mbg.generated
     */
    private String routeDesc;

    /**
     * Database column : tbl_routing_priority.last_oper_id
     *
     * @mbg.generated
     */
    private String lastOperId;

    /**
     * Database column : tbl_routing_priority.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_routing_priority.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

	public String getTransSeqId() {
		return transSeqId;
	}

	public void setTransSeqId(String transSeqId) {
		this.transSeqId = transSeqId;
	}

	public String getIntTransCd() {
		return intTransCd;
	}

	public void setIntTransCd(String intTransCd) {
		this.intTransCd = intTransCd;
	}

	public String getChnlId() {
		return chnlId;
	}

	public void setChnlId(String chnlId) {
		this.chnlId = chnlId;
	}

	public String getChnlMchntCd() {
		return chnlMchntCd;
	}

	public void setChnlMchntCd(String chnlMchntCd) {
		this.chnlMchntCd = chnlMchntCd;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getRouteDesc() {
		return routeDesc;
	}

	public void setRouteDesc(String routeDesc) {
		this.routeDesc = routeDesc;
	}

	public String getLastOperId() {
		return lastOperId;
	}

	public void setLastOperId(String lastOperId) {
		this.lastOperId = lastOperId;
	}

	public Date getRecCrtTs() {
		return recCrtTs;
	}

	public void setRecCrtTs(Date recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	public Date getRecUpdTs() {
		return recUpdTs;
	}

	public void setRecUpdTs(Date recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chnlId == null) ? 0 : chnlId.hashCode());
		result = prime * result + ((chnlMchntCd == null) ? 0 : chnlMchntCd.hashCode());
		result = prime * result + ((intTransCd == null) ? 0 : intTransCd.hashCode());
		result = prime * result + ((lastOperId == null) ? 0 : lastOperId.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((recCrtTs == null) ? 0 : recCrtTs.hashCode());
		result = prime * result + ((recUpdTs == null) ? 0 : recUpdTs.hashCode());
		result = prime * result + ((routeDesc == null) ? 0 : routeDesc.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((transSeqId == null) ? 0 : transSeqId.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoutingPriorityExt other = (RoutingPriorityExt) obj;
		if (chnlId == null) {
			if (other.chnlId != null)
				return false;
		} else if (!chnlId.equals(other.chnlId))
			return false;
		if (chnlMchntCd == null) {
			if (other.chnlMchntCd != null)
				return false;
		} else if (!chnlMchntCd.equals(other.chnlMchntCd))
			return false;
		if (intTransCd == null) {
			if (other.intTransCd != null)
				return false;
		} else if (!intTransCd.equals(other.intTransCd))
			return false;
		if (lastOperId == null) {
			if (other.lastOperId != null)
				return false;
		} else if (!lastOperId.equals(other.lastOperId))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (recCrtTs == null) {
			if (other.recCrtTs != null)
				return false;
		} else if (!recCrtTs.equals(other.recCrtTs))
			return false;
		if (recUpdTs == null) {
			if (other.recUpdTs != null)
				return false;
		} else if (!recUpdTs.equals(other.recUpdTs))
			return false;
		if (routeDesc == null) {
			if (other.routeDesc != null)
				return false;
		} else if (!routeDesc.equals(other.routeDesc))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		if (transSeqId == null) {
			if (other.transSeqId != null)
				return false;
		} else if (!transSeqId.equals(other.transSeqId))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RoutingPriorityExt [transSeqId=" + transSeqId + ", intTransCd=" + intTransCd + ", chnlId=" + chnlId
				+ ", chnlMchntCd=" + chnlMchntCd + ", priority=" + priority + ", weight=" + weight + ", status="
				+ status + ", tags=" + tags + ", routeDesc=" + routeDesc + ", lastOperId=" + lastOperId + ", recCrtTs="
				+ recCrtTs + ", recUpdTs=" + recUpdTs + "]";
	}
    
    
}
