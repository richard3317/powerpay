package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_routing_global

 *
 * @mbg.generated
 */
public class RoutingGlobalKey implements Serializable {
    /**
     * 交易类型(0010=消费/支付交易，5210=代付交易)
     * Database column : tbl_routing_global.int_trans_cd
     *
     * @mbg.generated
     */
    private String intTransCd;

    /**
     * 币别
     * Database column : tbl_routing_global.curr_cd
     *
     * @mbg.generated
     */
    private String currCd;

    /**
     * 渠道编号
     * Database column : tbl_routing_global.chnl_id
     *
     * @mbg.generated
     */
    private String chnlId;

    /**
     * 渠道商户号
     * Database column : tbl_routing_global.chnl_mchnt_cd
     *
     * @mbg.generated
     */
    private String chnlMchntCd;

    /**
     * Database table : tbl_routing_global
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_routing_global
     *
     * @mbg.generated
     */
    public RoutingGlobalKey(String intTransCd, String currCd, String chnlId, String chnlMchntCd) {
        this.intTransCd = intTransCd;
        this.currCd = currCd;
        this.chnlId = chnlId;
        this.chnlMchntCd = chnlMchntCd;
    }

    /**
     * Database table : tbl_routing_global
     *
     * @mbg.generated
     */
    public RoutingGlobalKey() {
        super();
    }

    /**
     * 交易类型(0010=消费/支付交易，5210=代付交易)
     * @return int_trans_cd 交易类型(0010=消费/支付交易，5210=代付交易)
     *
     * @mbg.generated
     */
    public String getIntTransCd() {
        return intTransCd;
    }

    /**
     * 交易类型(0010=消费/支付交易，5210=代付交易)
     * @param intTransCd 交易类型(0010=消费/支付交易，5210=代付交易)
     *
     * @mbg.generated
     */
    public void setIntTransCd(String intTransCd) {
        this.intTransCd = intTransCd;
    }

    /**
     * 币别
     * @return curr_cd 币别
     *
     * @mbg.generated
     */
    public String getCurrCd() {
        return currCd;
    }

    /**
     * 币别
     * @param currCd 币别
     *
     * @mbg.generated
     */
    public void setCurrCd(String currCd) {
        this.currCd = currCd;
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
     * 渠道商户号
     * @return chnl_mchnt_cd 渠道商户号
     *
     * @mbg.generated
     */
    public String getChnlMchntCd() {
        return chnlMchntCd;
    }

    /**
     * 渠道商户号
     * @param chnlMchntCd 渠道商户号
     *
     * @mbg.generated
     */
    public void setChnlMchntCd(String chnlMchntCd) {
        this.chnlMchntCd = chnlMchntCd;
    }

    /**
     * Database table : tbl_routing_global
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
        RoutingGlobalKey other = (RoutingGlobalKey) that;
        return (this.getIntTransCd() == null ? other.getIntTransCd() == null : this.getIntTransCd().equals(other.getIntTransCd()))
            && (this.getCurrCd() == null ? other.getCurrCd() == null : this.getCurrCd().equals(other.getCurrCd()))
            && (this.getChnlId() == null ? other.getChnlId() == null : this.getChnlId().equals(other.getChnlId()))
            && (this.getChnlMchntCd() == null ? other.getChnlMchntCd() == null : this.getChnlMchntCd().equals(other.getChnlMchntCd()));
    }

    /**
     * Database table : tbl_routing_global
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIntTransCd() == null) ? 0 : getIntTransCd().hashCode());
        result = prime * result + ((getCurrCd() == null) ? 0 : getCurrCd().hashCode());
        result = prime * result + ((getChnlId() == null) ? 0 : getChnlId().hashCode());
        result = prime * result + ((getChnlMchntCd() == null) ? 0 : getChnlMchntCd().hashCode());
        return result;
    }

    /**
     * Database table : tbl_routing_global
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", intTransCd=").append(intTransCd);
        sb.append(", currCd=").append(currCd);
        sb.append(", chnlId=").append(chnlId);
        sb.append(", chnlMchntCd=").append(chnlMchntCd);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(RoutingGlobalKey source) {
        this.intTransCd=source.intTransCd;
        this.currCd=source.currCd;
        this.chnlId=source.chnlId;
        this.chnlMchntCd=source.chnlMchntCd;
    }
}