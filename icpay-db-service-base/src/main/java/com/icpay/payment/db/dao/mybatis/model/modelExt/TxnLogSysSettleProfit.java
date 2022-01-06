package com.icpay.payment.db.dao.mybatis.model.modelExt;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : view_txn_log_01
 * @author richard
 *
 * @mbg.generated
 */
public class TxnLogSysSettleProfit implements Serializable {
	
	private String recUpdTs;
	private String siteId;
	private String mchntCd;
	private String transChnl;
	private String chnlMchntCd;
	private String currCd;
	private BigDecimal unit;
	private Long sumTransFeeDelta;
	
	public TxnLogSysSettleProfit() {
		super();
	}
	
	public TxnLogSysSettleProfit(String recUpdTs, String siteId, String mchntCd, String transChnl, String chnlMchntCd, String currCd, BigDecimal unit, Long sumTransFeeDelta) {
		super();
		this.recUpdTs = recUpdTs;
		this.siteId = siteId;
		this.mchntCd = mchntCd;
		this.transChnl = transChnl;
		this.chnlMchntCd = chnlMchntCd;
		this.currCd = currCd;
		this.unit = unit;
		this.sumTransFeeDelta = sumTransFeeDelta;
	}

	public String getRecUpdTs() {
		return recUpdTs;
	}

	public void setRecUpdTs(String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}
	
	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getMchntCd() {
		return mchntCd;
	}

	public void setMchntCd(String mchntCd) {
		this.mchntCd = mchntCd;
	}

	public String getTransChnl() {
		return transChnl;
	}

	public void setTransChnl(String transChnl) {
		this.transChnl = transChnl;
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
	
	public BigDecimal getUnit() {
		return unit;
	}

	public void setUnit(BigDecimal unit) {
		this.unit = unit;
	}

	public Long getSumTransFeeDelta() {
		return sumTransFeeDelta;
	}

	public void setSumTransFeeDelta(Long sumTransFeeDelta) {
		this.sumTransFeeDelta = sumTransFeeDelta;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((recUpdTs == null) ? 0 : recUpdTs.hashCode());
        result = prime * result + ((siteId == null) ? 0 : siteId.hashCode());
        result = prime * result + ((mchntCd == null) ? 0 : mchntCd.hashCode());
        result = prime * result + ((transChnl == null) ? 0 : transChnl.hashCode());
        result = prime * result + ((chnlMchntCd == null) ? 0 : chnlMchntCd.hashCode());
        result = prime * result + ((currCd == null) ? 0 : currCd.hashCode());
        result = prime * result + ((unit == null) ? 0 : unit.hashCode());
        result = prime * result + ((sumTransFeeDelta == null) ? 0 : sumTransFeeDelta.hashCode());        
        
        return result;
    }
	
    /**
     * Database table : view_txn_log_01
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", recUpdTs=").append(recUpdTs);
        sb.append(", siteId=").append(siteId);
        sb.append(", mchntCd=").append(mchntCd);
        sb.append(", transChnl=").append(transChnl);
        sb.append(", chnlMchntCd=").append(chnlMchntCd);
        sb.append(", currCd=").append(currCd);
        sb.append(", unit=").append(unit);
        sb.append(", sumTransFeeDelta=").append(sumTransFeeDelta);
        sb.append("]");
        return sb.toString();
    }

}