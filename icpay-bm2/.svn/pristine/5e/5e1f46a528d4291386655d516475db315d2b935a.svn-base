package com.icpay.payment.bm.bo;

import com.icpay.payment.bm.cache.AgentInfoCache;
import com.icpay.payment.bm.cache.CacheManager;
import com.icpay.payment.bm.cache.CacheType;
import com.icpay.payment.common.enums.BusCheckTaskEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.CommonEnums.OpType;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.AgentInfo;
import com.icpay.payment.db.service.IAgentInfoService;

/**
 * 商户基本信息审核任务处理类
 * @author lijin
 *
 */
public class AgentInfoBusCheckProcessor extends BusCheckProcessor {

	@Override
	protected String buildContent(OpType opTp, Object contentObj) {
		try {
			return JsonUtil.toJson(contentObj);
		} catch (Exception e) {
			throw new BizzException("序列化代理商信息出错", e);
		}
	}
	
	@Override
	protected Object restoreContent(String content) {
		AssertUtil.strIsBlank(content, "content is blank.");
		return JsonUtil.fromJson(content, AgentInfo.class);
	}

	@Override
	protected String buildContentKey(OpType opTp, Object contentObj) {
		AgentInfo entity = (AgentInfo) contentObj;
		return entity.getAgentCnNm();
	}
	
	@Override
	protected String getTaskTp() {
		return BusCheckTaskEnums.TaskTp._04.getCode();
	}

	@Override
	protected void processContent(CommonEnums.OpType opTp, String content, String checkOperId) {
		AssertUtil.strIsBlank(content, "content is blank.");
		AgentInfo entity = JsonUtil.fromJson(content, AgentInfo.class);
		AssertUtil.objIsNull(entity, "代理商信息还原失败:" + content);
		entity.setLastOperId(checkOperId);
		IAgentInfoService service = DBHessionServiceClient.getService(IAgentInfoService.class);
		
		switch (opTp) {
			case ADD:
				service.add(entity);
				CacheManager.refreshCache(CacheType.AGENT_INFO_CACHE.getCacheTp());
				break;
			case UPDATE:
				service.update(entity);
				CacheManager.refreshCache(CacheType.AGENT_INFO_CACHE.getCacheTp());
				break;
			case DELETE:
				service.delete(entity.getAgentCd());
				CacheManager.refreshCache(CacheType.AGENT_INFO_CACHE.getCacheTp());
				break;
			default:
				throw new BizzException("不支持该操作类型:" + opTp);
		}
		AgentInfoCache.getInstance().needRefresh();
	}
}
