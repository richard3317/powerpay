package com.icpay.payment.bm.bo;

import com.icpay.payment.bm.cache.MchntInfoCache;
import com.icpay.payment.common.enums.BusCheckTaskEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.CommonEnums.OpType;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.IMchntUserInfoService;

/**
 * 商户基本信息审核任务处理类
 * @author lijin
 *
 */
public class MchntInfoBusCheckProcessor extends BusCheckProcessor {

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
		MchntInfo m = JsonUtil.fromJson(content, MchntInfo.class);
		return m;
	}

	@Override
	protected String buildContentKey(OpType opTp, Object contentObj) {
		MchntInfo m = (MchntInfo) contentObj;
		return m.getMchntCnNm();
	}
	
	@Override
	protected String getTaskTp() {
		return BusCheckTaskEnums.TaskTp._01.getCode();
	}

	@Override
	protected void processContent(CommonEnums.OpType opTp, String content, String checkOperId) {
		AssertUtil.strIsBlank(content, "content is blank.");
		MchntInfo m = JsonUtil.fromJson(content, MchntInfo.class);
		AssertUtil.objIsNull(m, "商户信息还原失败:" + content);
		m.setLastOperId(checkOperId);
		IMchntInfoService service = DBHessionServiceClient.getService(IMchntInfoService.class);

		switch (opTp) {
			case ADD:
				service.add(m);
				break;
			case UPDATE:
				MchntInfo dbEntity = service.selectByPrimaryKey(m.getMchntCd());
				m.setLoginPwd(dbEntity.getLoginPwd());
				m.setLastLoginIp(dbEntity.getLastLoginIp());
				m.setLastLoginTs(dbEntity.getLastLoginTs());
				service.update(m);
				break;
			case DELETE:
				service.delete(m.getMchntCd());
				break;
			default:
				throw new BizzException("不支持该操作类型:" + opTp);
		}
		MchntInfoCache.getInstance().needRefresh();
	}
	
}
