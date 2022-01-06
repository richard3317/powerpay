package com.icpay.payment.bm.bo;

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
import com.icpay.payment.db.dao.mybatis.model.OrganInfo;
import com.icpay.payment.db.dao.mybatis.model.OrganMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MchntInfoAndMerSettlePolicy;
import com.icpay.payment.db.dao.mybatis.model.modelExt.OrganMchntExtInfo;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.IOrganMchntInfoService;

/**
 * 商户基本信息、清算信息、支付方式审核任务处理类
 * @author richard
 *
 */
public class MchntInfoUpdAndDelCheckProcessor extends BusCheckProcessor {

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
		return BusCheckTaskEnums.TaskTp._06.getCode();
	}
	
	@Override
	protected void processContent(CommonEnums.OpType opTp, String content, String checkOperId) {
		AssertUtil.strIsBlank(content, "content is blank.");
		MchntInfoAndMerSettlePolicy m = JsonUtil.fromJson(content, MchntInfoAndMerSettlePolicy.class);
		AssertUtil.objIsNull(m, "商户信息还原失败:" + content);
		m.setLastOperId(checkOperId);
		MchntInfo mchntInfo = MchntInfoAndMerSettlePolicy.transMchntInfo(m);
		IMchntInfoService mchntInfoService = DBHessionServiceClient.getService(IMchntInfoService.class);
		
		OrganInfo organInfo = MchntInfoAndMerSettlePolicy.transOrganInfo(m); //从m2得到organInfo数据
		
		switch (opTp) {
			case UPDATE:
				MchntInfo dbEntity = mchntInfoService.selectByPrimaryKey(mchntInfo.getMchntCd());
				mchntInfo.setLoginPwd(dbEntity.getLoginPwd());
				mchntInfo.setLastLoginIp(dbEntity.getLastLoginIp());
				mchntInfo.setLastLoginTs(dbEntity.getLastLoginTs());
				mchntInfoService.update(mchntInfo);
				
				if(!Utils.isEmpty(organInfo.getOrganId())) { //当更新审核的数据有Organ ID时
					
					IOrganMchntInfoService organService = DBHessionServiceClient.getService(IOrganMchntInfoService.class);
					//用 商户号 查询, 此商户是否已归属在某个机构下
					OrganMchntInfo organMchntInfo = organService.selectMchntByMchnt(mchntInfo.getMchntCd());
					
					if(!Utils.isEmpty(organMchntInfo)) { //已归属在某个机构下
						//删除后, 再新增机构
						OrganMchntExtInfo organMchntExtInfoDel = new OrganMchntExtInfo();
						organMchntExtInfoDel.setMchntCd(mchntInfo.getMchntCd());
						organMchntExtInfoDel.setOrganId(organMchntInfo.getOrganId());
						organService.delete(organMchntExtInfoDel);
						
						OrganMchntExtInfo organMchntExtInfoAdd = new OrganMchntExtInfo();
						organMchntExtInfoAdd.setMchntCd(mchntInfo.getMchntCd());
						organMchntExtInfoAdd.setOrganId(organInfo.getOrganId());
						organMchntExtInfoAdd.setLastOperId(checkOperId);
						organMchntExtInfoAdd.setState(RecSt.VALID.getCode());
						organService.add(organMchntExtInfoAdd);	
					} else { //没有在任何机构下
						//在机构中新增此商户
						OrganMchntExtInfo record = new OrganMchntExtInfo();
						record.setMchntCd(mchntInfo.getMchntCd());
						record.setOrganId(organInfo.getOrganId());
						record.setLastOperId(checkOperId);
						record.setState(RecSt.VALID.getCode());
						organService.add(record);
					}
				} else { //当更新审核的数据没有Organ ID时
					IOrganMchntInfoService organService = DBHessionServiceClient.getService(IOrganMchntInfoService.class);
					//用 商户号 查询, 此商户是否已归属在某个机构下
					OrganMchntInfo organMchntInfo = organService.selectMchntByMchnt(mchntInfo.getMchntCd());
					
					if(!Utils.isEmpty(organMchntInfo)) { //已归属在某个机构下
						//删除
						OrganMchntExtInfo organMchntExtInfoDel = new OrganMchntExtInfo();
						organMchntExtInfoDel.setMchntCd(mchntInfo.getMchntCd());
						organMchntExtInfoDel.setOrganId(organMchntInfo.getOrganId());
						organService.delete(organMchntExtInfoDel);
					} 
				}

				break;
			case DELETE:
				mchntInfoService.delete(mchntInfo.getMchntCd());
				break;
			default:
				throw new BizzException("不支持该操作类型:" + opTp);
		}
		MchntInfoCache.getInstance().needRefresh();
	}
	
}
