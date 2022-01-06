package com.icpay.payment.mer.bo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountFlow;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfoKey;
import com.icpay.payment.db.service.IChnlMchntAccountFlowService;
import com.icpay.payment.db.service.IMchntAccountService;

@Component("mchntAccountBO")
public class MchntAccountBO extends BaseBO {
	/**
	 * 获取商户账户信息
	 */
	public MerAccountInfo getMchntAccount(String mchntCd, String currCd) {
		AssertUtil.strIsBlank(mchntCd, "mchnt is blank.");
		AssertUtil.strIsBlank(currCd, "currCd is blank.");
		
		IMchntAccountService accountService = this.getDBService(IMchntAccountService.class);
		MerAccountInfoKey key = new MerAccountInfoKey();
		key.setMchntCd(mchntCd);
		key.setCurrCd(currCd);
		MerAccountInfo accountInfo = accountService.selectByPrimaryKey(key);
		return accountInfo;
	}
	
	/**
	 * 获取商户账户流水记录
	 * @param pageNum
	 * @param pageSize
	 * @param mon
	 * @param qryParams
	 * @return
	 */
	public Pager<ChnlMchntAccountFlow> qryMerAcctFlow(int pageNum, int pageSize,String mon, Map<String, String> qryParams) {
		IChnlMchntAccountFlowService service = this.getDBService(IChnlMchntAccountFlowService.class);
		return service.selectByPage(pageNum, pageSize, mon, qryParams);
	}
	
	public ChnlMchntAccountFlow getMerAcctFlow(String seqId,String mon) {
		IChnlMchntAccountFlowService service = this.getDBService(IChnlMchntAccountFlowService.class);
		return service.selectByPrimaryKey(seqId, mon);
	}
	
	public List<ChnlMchntAccountFlow> qryMerAcctFlowList(String mon, Map<String, String> qryParams) {
		IChnlMchntAccountFlowService service = this.getDBService(IChnlMchntAccountFlowService.class);
		return service.select(mon, qryParams);
	}

}
