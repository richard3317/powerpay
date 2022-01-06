package com.icpay.payment.bm.bo;

import org.apache.log4j.Logger;

import com.icpay.payment.common.enums.BusCheckTaskEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.CommonEnums.OpType;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySub;
import com.icpay.payment.db.service.IMerSettlePolicySubService;

/**
 * 商户清算信息  计费方式管理  审核任务处理类
 * @author wangyun
 *
 */
public class MchntSettlePolicySubProcessor extends BusCheckProcessor {

	private static final Logger logger = Logger.getLogger(MchntSettlePolicySubProcessor.class);
	/**
	 * 将类转为Json的格式
	 */
	@Override
	protected String buildContent(OpType opTp, Object contentObj) {
		try {
			return JsonUtil.toJson(contentObj);
		} catch (Exception e) {
			throw new BizzException("序列化商户清算计费方式管理信息出错", e);
		}
	}
	
	@Override
	protected Object restoreContent(String content) {
		AssertUtil.strIsBlank(content, "content is blank.");
		MerSettlePolicySub m = JsonUtil.fromJson(content, MerSettlePolicySub.class);
		return m;
	}

	/**
	 * 创建任务的时候，作为主键查询之用
	 */
	@Override
	protected String buildContentKey(OpType opTp, Object contentObj) {
		MerSettlePolicySub m = (MerSettlePolicySub) contentObj;
		// 路由信息的主键有四个字段，用管道符拼起来
		return StringUtil.concat("|", m.getMchntCd(), m.getIntTransCd());
					
	}
	
	@Override
	protected String getTaskTp() {
		//需要修改为清算计费方式管理的标识
		return BusCheckTaskEnums.TaskTp._52.getCode();
		
	}

	@Override
	protected void processContent(CommonEnums.OpType opTp, String content, String checkOperId) {
		AssertUtil.strIsBlank(content, "content is blank.");
		MerSettlePolicySub m = JsonUtil.fromJson(content, MerSettlePolicySub.class);
		AssertUtil.objIsNull(m, "商户清算信息计费方式管理还原失败:" + content);
		m.setLastOperId(checkOperId);
		IMerSettlePolicySubService service = DBHessionServiceClient.getService(IMerSettlePolicySubService.class);
		
		switch (opTp) {
			case ADD:
				logger.info("新增商户清算信息计费方式管理开始:" + m.getMchntCd());
				service.add(m);
				logger.info("新增商户清算信息计费方式管理完成:" + m.getMchntCd());

				break;
			case UPDATE:
				service.update(m);
				break;
			case DELETE:
				service.delete(m.getMchntCd(),m.getIntTransCd(),m.getCurrCd());
				break;
			default:
				throw new BizzException("不支持该操作类型:" + opTp);
		}
	}
}
