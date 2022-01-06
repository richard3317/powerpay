package com.icpay.payment.bm.bo;

import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.CommonEnums.OpType;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.service.IMchntInfoService;

/**
 * 商户交易权限配置信息任务处理类
 * @author lijin
 *
 */
public class MchntTxnCfgBusCheckProcessor extends BusCheckProcessor {

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
		return m.getMchntCd();
	}
	
	@Override
	protected String getTaskTp() {
//		return BusCheckTaskEnums.TaskTp._02.getCode();
		return null;
	}

	@Override
	protected void processContent(CommonEnums.OpType opTp, String content, String checkOperId) {
		AssertUtil.strIsBlank(content, "content is blank.");
		MchntInfo m = JsonUtil.fromJson(content, MchntInfo.class);
		AssertUtil.objIsNull(m, "商户信息还原失败:" + content);
		switch (opTp) {
			case UPDATE:
				IMchntInfoService service = DBHessionServiceClient.getService(IMchntInfoService.class);
				MchntInfo dbEntity = service.selectByPrimaryKey(m.getMchntCd());
				// 只允许修改交易权限
				dbEntity.setTransType(m.getTransType());
				dbEntity.setLastOperId(checkOperId);
				service.update(dbEntity);
				break;
			default:
				throw new BizzException("不支持改操作类型:" + opTp);
		}
	}
}
