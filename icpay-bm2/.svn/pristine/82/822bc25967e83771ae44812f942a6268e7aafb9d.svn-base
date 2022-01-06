package com.icpay.payment.bm.bo;

import java.util.Map;

import com.icpay.payment.common.enums.BusCheckTaskEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.CommonEnums.OpType;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.RoutInfo;
import com.icpay.payment.db.dao.mybatis.model.TxnRouting;
import com.icpay.payment.db.service.IRoutInfoService;
import com.icpay.payment.db.service.ITxnRoutingService;

/**
 * 路由信息审核任务处理类
 * @author lijin
 *
 */
public class RoutInfoBusCheckProcessor extends BusCheckProcessor {

	@Override
	protected String buildContent(OpType opTp, Object contentObj) {
		try {
			return JsonUtil.toJson(contentObj);
		} catch (Exception e) {
			throw new BizzException("序列化路由信息出错", e);
		}
	}
	
	@Override
	protected Object restoreContent(String content) {
		AssertUtil.strIsBlank(content, "content is blank.");
		TxnRouting rout = JsonUtil.fromJson(content, TxnRouting.class);
		Map<String, String> m = BeanUtil.desc(rout, null, null);
		/*String[] chnlArr = m.get("chnlIds").split(",");
		if (chnlArr.length == 1) {
			m.put("chnlId1", chnlArr[0]);
		}
		if (chnlArr.length == 2) {
			m.put("chnlId1", chnlArr[0]);
			m.put("chnlId2", chnlArr[1]);
		}
		if (chnlArr.length == 3) {
			m.put("chnlId1", chnlArr[0]);
			m.put("chnlId2", chnlArr[1]);
			m.put("chnlId3", chnlArr[2]);
		}*/
		return m;
	}

	@Override
	protected String buildContentKey(OpType opTp, Object contentObj) {
		TxnRouting r = (TxnRouting) contentObj;
		// 路由信息的主键有四个字段，用管道符拼起来
		return StringUtil.concat("|", r.getMchntCd(), r.getIntTransCd(), 
					r.getChnlId(), r.getChnlMchntCd());
	}
	
	@Override
	protected String getTaskTp() {
		return BusCheckTaskEnums.TaskTp._03.getCode();
	}

	@Override
	protected void processContent(CommonEnums.OpType opTp, String content, String checkOperId) {
		
		AssertUtil.strIsBlank(content, "content is blank.");
		TxnRouting txnRouting = JsonUtil.fromJson(content, TxnRouting.class);
		AssertUtil.objIsNull(txnRouting, "路由信息还原失败:" + content);
		txnRouting.setLastOperId(checkOperId);
		ITxnRoutingService service = DBHessionServiceClient.getService(ITxnRoutingService.class);
		
		switch (opTp) {
			case ADD :
				service.add(txnRouting);
				break;
			case UPDATE:
				service.update(txnRouting);
				break;
			case DELETE:
				service.delete(txnRouting);
				break;
			default:
				break;
		}
	}
}
