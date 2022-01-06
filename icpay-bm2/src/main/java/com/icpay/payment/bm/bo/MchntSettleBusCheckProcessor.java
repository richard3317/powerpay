package com.icpay.payment.bm.bo;

import org.apache.log4j.Logger;

import com.icpay.payment.common.enums.BusCheckTaskEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.CommonEnums.OpType;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicy;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicyKey;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.IMerSettlePolicyService;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.service.MerAccService;

/**
 * 商户清算信息审核任务处理类
 * @author wangyun
 *
 */
public class MchntSettleBusCheckProcessor extends BusCheckProcessor {

	private static final Logger logger = Logger.getLogger(MchntSettleBusCheckProcessor.class);
	/**
	 * 将类转为Json的格式
	 */
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
		MerSettlePolicy m = JsonUtil.fromJson(content, MerSettlePolicy.class);
		return m;
	}

	/**
	 * 创建任务的时候，作为主键查询之用
	 */
	@Override
	protected String buildContentKey(OpType opTp, Object contentObj) {
		MerSettlePolicy m = (MerSettlePolicy) contentObj;
		//商户号作为商户清算的主键
		return m.getMchntCd();
	}
	
	@Override
	protected String getTaskTp() {
		//需要修改为清算的标识
		return BusCheckTaskEnums.TaskTp._51.getCode();
	}

	@Override
	protected void processContent(CommonEnums.OpType opTp, String content, String checkOperId) {
		AssertUtil.strIsBlank(content, "content is blank.");
		MerSettlePolicy m = JsonUtil.fromJson(content, MerSettlePolicy.class);
		AssertUtil.objIsNull(m, "商户清算信息还原失败:" + content);
		m.setLastOperId(checkOperId);
		IMerSettlePolicyService service = DBHessionServiceClient.getService(IMerSettlePolicyService.class);
		
		switch (opTp) {
			case ADD:
				logger.info("新增商户清算信息开始:" + m.getMchntCd());
				service.add(m);
				logger.info("新增商户清算信息完成:" + m.getMchntCd());

				// 创建账户
				logger.info("创建商户账户开始:" + m.getMchntCd());
				MerAccService merAccService = ServiceProxy.getService(MerAccService.class);
				merAccService.careateAccount(m.getMchntCd());
				logger.info("创建商户账户完成:" + m.getMchntCd());
				break;
			case UPDATE:
				service.update(m);
				break;
			case DELETE:
				MerSettlePolicyKey key = new MerSettlePolicyKey();
				key.setCurrCd(m.getCurrCd());
				key.setMchntCd(m.getMchntCd());
				service.delete(key);
				break;
			default:
				throw new BizzException("不支持该操作类型:" + opTp);
		}
	}
}
