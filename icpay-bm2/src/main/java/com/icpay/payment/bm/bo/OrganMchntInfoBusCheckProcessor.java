package com.icpay.payment.bm.bo;

import com.icpay.payment.bm.cache.OrganInfoCache;
import com.icpay.payment.common.enums.BusCheckTaskEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.CommonEnums.OpType;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EncryptUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.OrganInfo;
import com.icpay.payment.db.dao.mybatis.model.modelExt.OrganMchntExtInfo;
import com.icpay.payment.db.service.IOrganMchntInfoService;

/**
 * 机构基本信息审核任务处理类
 * @author lijin
 *
 */
public class OrganMchntInfoBusCheckProcessor extends BusCheckProcessor {

	@Override
	protected String buildContent(OpType opTp, Object contentObj) {
		try {
			return JsonUtil.toJson(contentObj);
		} catch (Exception e) {
			throw new BizzException("序列化机构信息出错", e);
		}
	}
	
	@Override
	protected Object restoreContent(String content) {
		AssertUtil.strIsBlank(content, "content is blank.");
		OrganMchntExtInfo m = JsonUtil.fromJson(content, OrganMchntExtInfo.class);
		return m;
	}

	@Override
	protected String buildContentKey(OpType opTp, Object contentObj) {
		OrganMchntExtInfo m = (OrganMchntExtInfo) contentObj;
		// md5  校验是否 提交的同一个任务 
		StringBuffer organStr = new StringBuffer();
		organStr.append("|");
		if(!Utils.isEmpty(m.getOrganIdList())) {
			for (OrganInfo o : m.getOrganIdList()) {
				organStr.append(o.getOrganId()).append("|");
			}
		}
		String organId = Utils.isEmpty(m.getOrganId())  ? "" : m.getOrganId();
		String organDesc = Utils.isEmpty(m.getOrganDesc())  ? "" : m.getOrganDesc();
		String mchntCd = Utils.isEmpty(m.getMchntCd())  ? "" : m.getMchntCd();
		StringBuffer mchntCdStr = new StringBuffer();
		if(!Utils.isEmpty(m.getMchntCdStr())) {
			for(String str : m.getMchntCdStr()) {
				mchntCdStr.append(str);
			}
		}
		String  str = organId + "|" + organDesc + "|"+  mchntCd + "|"  +  mchntCdStr + "|" + organStr.substring(0,organStr.length()-1).toString();
		return EncryptUtil.md5(str);
	}
	
	@Override
	protected String getTaskTp() {
		return BusCheckTaskEnums.TaskTp._07.getCode();
	}

	@Override
	protected void processContent(CommonEnums.OpType opTp, String content, String checkOperId) {
		AssertUtil.strIsBlank(content, "content is blank.");
		OrganMchntExtInfo o = JsonUtil.fromJson(content, OrganMchntExtInfo.class);
		AssertUtil.objIsNull(o, "机构信息还原失败:" + content);
		o.setLastOperId(checkOperId);
		IOrganMchntInfoService service = DBHessionServiceClient.getService(IOrganMchntInfoService.class);

		switch (opTp) {
			case ADD:
				service.add(o);
				break;
			case UPDATE:
				service.update(o);
				break;
			case DELETE:
				service.delete(o);
				break;
			default:
				throw new BizzException("不支持该操作类型:" + opTp);
		}
		//更新缓存
		OrganInfoCache.getInstance().needRefresh();
	}
	
}
