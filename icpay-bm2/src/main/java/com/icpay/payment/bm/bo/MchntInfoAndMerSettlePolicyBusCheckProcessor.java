package com.icpay.payment.bm.bo;

import java.util.List;

import com.icpay.payment.bm.cache.MchntInfoCache;
import com.icpay.payment.common.enums.BusCheckTaskEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.CommonEnums.OpType;
import com.icpay.payment.common.enums.CommonEnums.RecSt;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicy;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySub;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MchntInfoAndMerSettlePolicy;
import com.icpay.payment.db.dao.mybatis.model.modelExt.OrganMchntExtInfo;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.IMerSettlePolicyService;
import com.icpay.payment.db.service.IMerSettlePolicySubService;
import com.icpay.payment.db.service.IOrganMchntInfoService;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.service.ChnlMerAccService;

/**
 * 商户基本信息、清算信息、支付方式审核任务处理类
 * @author richard
 *
 */
public class MchntInfoAndMerSettlePolicyBusCheckProcessor extends BusCheckProcessor {

	@Override
	protected String buildContent(OpType opTp, Object contentObj) {
		try {
			return JsonUtil.toJson(contentObj);
		} catch (Exception e) {
			throw new BizzException("序列化商户信息出错", e);
		}
	}
	
	@Override
	protected Object restoreContent(String content) {
		AssertUtil.strIsBlank(content, "content is blank.");
		MchntInfoAndMerSettlePolicy m = JsonUtil.fromJson(content, MchntInfoAndMerSettlePolicy.class);
		return m;
	}

	@Override
	protected String buildContentKey(OpType opTp, Object contentObj) {
		MchntInfoAndMerSettlePolicy m = (MchntInfoAndMerSettlePolicy) contentObj;
		return m.getMchntCnNm();
	}
	
	@Override
	protected String getTaskTp() {
		return BusCheckTaskEnums.TaskTp._05.getCode();
	}
	
	@Override
	protected void processContent(CommonEnums.OpType opTp, String content, String checkOperId) {
		AssertUtil.strIsBlank(content, "content is blank.");
		MchntInfoAndMerSettlePolicy m = JsonUtil.fromJson(content, MchntInfoAndMerSettlePolicy.class);
		AssertUtil.objIsNull(m, "商户信息还原失败:" + content);
		m.setLastOperId(checkOperId);
		MchntInfo mchntInfo = MchntInfoAndMerSettlePolicy.transMchntInfo(m);
		IMchntInfoService mchntInfoService = DBHessionServiceClient.getService(IMchntInfoService.class);
		IMerSettlePolicyService merSettlePolicyService = DBHessionServiceClient.getService(IMerSettlePolicyService.class);
		IMerSettlePolicySubService merSettlePolicySubService = DBHessionServiceClient.getService(IMerSettlePolicySubService.class);
		ChnlMerAccService cmas = ServiceProxy.getService(ChnlMerAccService.class);
		
		switch (opTp) {
			case ADD_ALL:
				String mchntCd = mchntInfoService.addAndReturnMchntCd(mchntInfo);//基本信息录入
				List<MerSettlePolicy> msp = m.getMerSettlePolicy();
				for (MerSettlePolicy msps : msp) {
					msps.setMchntCd(mchntCd);
					merSettlePolicyService.add(msps);//清算信息设置
				}
				
				cmas.createAccount("00", mchntCd);//创建商户账户
				List<MerSettlePolicySub> sub = m.getMerSettlePolicySub();
				for (MerSettlePolicySub msps : sub) {
					msps.setMchntCd(mchntCd);
					merSettlePolicySubService.add(msps);//支付方式配置
				}
				if(!Utils.isEmpty(m.getOrganId()))
					// 插入机构商户表
					insertOrganMchnt(m.getOrganId(),mchntCd,checkOperId);
				break;
			default:
				throw new BizzException("不支持该操作类型:" + opTp);
		}
		MchntInfoCache.getInstance().needRefresh();
	}
	
	protected void insertOrganMchnt(String organId , String mchntCd , String checkOperId) {
		IOrganMchntInfoService service = DBHessionServiceClient.getService(IOrganMchntInfoService.class);
		OrganMchntExtInfo record = new OrganMchntExtInfo();
		record.setMchntCd(mchntCd);
		record.setOrganId(organId);
		record.setLastOperId(checkOperId);
		record.setState(RecSt.VALID.getCode());
		service.add(record);
	}
}
