package com.icpay.payment.db.dao.mybatis.model.modelExt;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * [Model class] 
 * @author richard
 */
public class ChnlMchntDaily implements Serializable {
	
	private String recUpdTs; //日期
	private String mchntCd; //小商編
	private String chnlId; //渠道編號
	private String chnlMchntCd; //大商編
	private String currCd; //幣別
	private Long transAt; //金額
	private Long zhifuTransAt; //支付金額
	private Long daifuTransAt; //代付金額
	private Long frozenBalance; //凍結金額
	
	public ChnlMchntDaily() {
		super();
	}
	
    public ChnlMchntDaily(String recUpdTs, String mchntCd, String chnlId, String chnlMchntCd, String currCd,
    		Long transAt, Long zhifuTransAt, Long daifuTransAt, Long frozenBalance) {
		super();
		this.recUpdTs = recUpdTs;
		this.mchntCd = mchntCd;
		this.chnlId = chnlId;
		this.chnlMchntCd = chnlMchntCd;
		this.currCd = currCd;
		this.transAt = transAt;
		this.zhifuTransAt = zhifuTransAt;
		this.daifuTransAt = daifuTransAt;
		this.frozenBalance = frozenBalance;
	}

	public String getRecUpdTs() {
		return recUpdTs;
	}

	public void setRecUpdTs(String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

	public String getMchntCd() {
		return mchntCd;
	}

	public void setMchntCd(String mchntCd) {
		this.mchntCd = mchntCd;
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
	
	public String getCurrCd() {
		return currCd;
	}

	public void setCurrCd(String currCd) {
		this.currCd = currCd;
	}

	public Long getTransAt() {
		return transAt;
	}

	public void setTransAt(Long transAt) {
		this.transAt = transAt;
	}

	public Long getZhifuTransAt() {
		return zhifuTransAt;
	}

	public void setZhifuTransAt(Long zhifuTransAt) {
		this.zhifuTransAt = zhifuTransAt;
	}

	public Long getDaifuTransAt() {
		return daifuTransAt;
	}

	public void setDaifuTransAt(Long daifuTransAt) {
		this.daifuTransAt = daifuTransAt;
	}

	public Long getFrozenBalance() {
		return frozenBalance;
	}

	public void setFrozenBalance(Long frozenBalance) {
		this.frozenBalance = frozenBalance;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chnlId == null) ? 0 : chnlId.hashCode());
		result = prime * result + ((chnlMchntCd == null) ? 0 : chnlMchntCd.hashCode());
		result = prime * result + ((currCd == null) ? 0 : currCd.hashCode());
		result = prime * result + ((daifuTransAt == null) ? 0 : daifuTransAt.hashCode());
		result = prime * result + ((frozenBalance == null) ? 0 : frozenBalance.hashCode());
		result = prime * result + ((mchntCd == null) ? 0 : mchntCd.hashCode());
		result = prime * result + ((recUpdTs == null) ? 0 : recUpdTs.hashCode());
		result = prime * result + ((transAt == null) ? 0 : transAt.hashCode());
		result = prime * result + ((zhifuTransAt == null) ? 0 : zhifuTransAt.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "ChnlMchntDaily [recUpdTs=" + recUpdTs + ", mchntCd=" + mchntCd + ", chnlId=" + chnlId + ", chnlMchntCd="
				+ chnlMchntCd + ", currCd=" + currCd + ", transAt=" + transAt + ", zhifuTransAt=" + zhifuTransAt
				+ ", daifuTransAt=" + daifuTransAt + ", frozenBalance=" + frozenBalance + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChnlMchntDaily other = (ChnlMchntDaily) obj;
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
		if (currCd == null) {
			if (other.currCd != null)
				return false;
		} else if (!currCd.equals(other.currCd))
			return false;
		if (daifuTransAt == null) {
			if (other.daifuTransAt != null)
				return false;
		} else if (!daifuTransAt.equals(other.daifuTransAt))
			return false;
		if (frozenBalance == null) {
			if (other.frozenBalance != null)
				return false;
		} else if (!frozenBalance.equals(other.frozenBalance))
			return false;
		if (mchntCd == null) {
			if (other.mchntCd != null)
				return false;
		} else if (!mchntCd.equals(other.mchntCd))
			return false;
		if (recUpdTs == null) {
			if (other.recUpdTs != null)
				return false;
		} else if (!recUpdTs.equals(other.recUpdTs))
			return false;
		if (transAt == null) {
			if (other.transAt != null)
				return false;
		} else if (!transAt.equals(other.transAt))
			return false;
		if (zhifuTransAt == null) {
			if (other.zhifuTransAt != null)
				return false;
		} else if (!zhifuTransAt.equals(other.zhifuTransAt))
			return false;
		return true;
	}
}