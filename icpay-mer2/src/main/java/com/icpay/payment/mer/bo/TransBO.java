package com.icpay.payment.mer.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.TxnEnums.TxnType;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySub;
import com.icpay.payment.db.dao.mybatis.model.TransLog;
import com.icpay.payment.db.dao.mybatis.model.TxnLogView;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TxnLogSummary;
import com.icpay.payment.db.service.IMerSettlePolicySubService;
import com.icpay.payment.db.service.ITransLogService;
import com.icpay.payment.db.service.ITxnLogViewService;

@Component("transBO")
public class TransBO extends BaseBO {
	
	
	/**
	 * 获取交易信息
	 */
	public Pager<TxnLogView> qryTrans(int pageNum, int pageSize, String mon, Map<String, String> qryParams) {
		checkNecessaryFieldForQry(qryParams, "mchntCd");
		ITxnLogViewService svc = this.getDBService(ITxnLogViewService.class);
		return svc.selectByPage(pageNum, pageSize, mon, qryParams);
	}
	
	public TransLog getTransFlow(String transSeqId, String mon) {
		ITransLogService service = this.getDBService(ITransLogService.class);
		return service.selectByPrimaryKey(transSeqId, mon);
	}
	
	public TxnLogView getTxnLogViewFlow(String transSeqId, String mon) {
		ITxnLogViewService svc = this.getDBService(ITxnLogViewService.class);
		return svc.selectByPrimaryKey(transSeqId, mon);
	}
	
	/**
	 * 获取交易信息
	 */
	public List<TransLog> qryTransList(String mon, Map<String, String> qryParams) {
		checkNecessaryFieldForQry(qryParams, "mchntCd");
		ITransLogService service = this.getDBService(ITransLogService.class);
		return service.select(mon, qryParams);
	}
	
	/**
	   *    加总
	 * @param qryParamMap
	 * @param mon
	 * @return
	 */
	public JSONObject summary(Map<String, String> qryParamMap, String mon) {
		ITxnLogViewService svc = this.getDBService(ITxnLogViewService.class);
		TxnLogSummary res = svc.selectSummary(mon, qryParamMap);
		Map<String, Object> map = new HashMap<String, Object>();
		if (res != null) {
			map.put("recCount", res.getRecCount());
			map.put("sumTransAt", StringUtil.formateAmt(res.getSumTransAt()));
			map.put("sumTransFee", StringUtil.formateAmt(res.getSumTransFee()));
			map.put("sumTransFeeChnl", StringUtil.formateAmt(res.getSumTransFeeChnl()));
			map.put("sumTransFeeDelta", StringUtil.formateAmt(res.getSumTransFeeDelta()));
		}
		JSONObject result = (JSONObject) JSON.toJSON(map);
		return result;
	}
	
	/**
	 * 取得交易类型
	 * @param mchntCd
	 * @return
	 */
	public List<TxnType> getPayTypes(String mchntCd) {
		IMerSettlePolicySubService svc = this.getDBService(IMerSettlePolicySubService.class);
		List<MerSettlePolicySub>  msList = svc.select(mchntCd);
		List<TxnType> payTypes = null;
		payTypes = new ArrayList<>();
		for(MerSettlePolicySub ms: msList) {
//			String ptCode = StringUtil.right(("XXXX"+ms.getIntTransCd()), 2);
			String ptCode = ms.getIntTransCd();
			TxnType payType = EnumUtil.parseEnumByCode(TxnType.class, ptCode, null);
			if (payType!=null){
				if(!payType.getCode().startsWith("52")) {
					payTypes.add(payType);
				}
			}
				
		}
		return payTypes;
	}
	
	/**
	 * 取得交易类型(代付)
	 * @param mchntCd
	 * @return
	 */
	public List<TxnType> getPayTypesWithdraw(String mchntCd) {
		IMerSettlePolicySubService svc = this.getDBService(IMerSettlePolicySubService.class);
		List<MerSettlePolicySub> msList = svc.select(mchntCd);
		List<TxnType> payTypes = null;
		payTypes = new ArrayList<>();
		for(MerSettlePolicySub ms: msList) {
			String ptCode = ms.getIntTransCd();
			TxnType payType = EnumUtil.parseEnumByCode(TxnType.class, ptCode, null);
			if (payType!=null){
				if(payType.getCode().startsWith("52")) {
					payTypes.add(payType);
				}
			}
				
		}
		return payTypes;
	}
	
	/**
	 * 取得交易类型(代付), 增加币别
	 * @param mchntCd
	 * @return
	 */
	public List<TxnType> getPayTypesWithdraw(String mchntCd, String currCd) {
		IMerSettlePolicySubService svc = this.getDBService(IMerSettlePolicySubService.class);
		List<MerSettlePolicySub> msList = svc.select(mchntCd, currCd);
		List<TxnType> payTypes = null;
		payTypes = new ArrayList<>();
		for(MerSettlePolicySub ms: msList) {
			String ptCode = ms.getIntTransCd();
			TxnType payType = EnumUtil.parseEnumByCode(TxnType.class, ptCode, null);
			if (payType!=null){
				if(payType.getCode().startsWith("52")) {
					payTypes.add(payType);
				}
			}
				
		}
		return payTypes;
	}
}
