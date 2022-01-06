package com.icpay.payment.bm.bo;

import org.apache.log4j.Logger;

import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.CommonEnums.OpType;
import com.icpay.payment.common.enums.CommonEnums.SysInfo;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.BusCheckTask;

public abstract class BusCheckProcessor {
	
	private static final Logger logger = Logger.getLogger(BusCheckProcessor.class);
	
	private static final int DB_CONTENT_KEY_MAX_LENGTH = 128;

	/**
	 * 创建复核任务
	 * @param context
	 * @return
	 */
	public BusCheckTask createTask(String operId, CommonEnums.OpType opTp, Object contentOjb) {
		logger.info("创建审核任务开始");
		BusCheckTask task = new BusCheckTask();
		task.setSysId(SysInfo.CONSOLE.getCode());
		task.setTaskTp(this.getTaskTp());
		task.setOperId(operId);
		task.setOpTp(opTp.getCode());
		String contentKey = this.buildContentKey(opTp, contentOjb);
		if (StringUtil.strLen(contentKey, Constant.UTF8) > DB_CONTENT_KEY_MAX_LENGTH) {
			throw new BizzException("contentKey超过数据库长度 :" + contentKey);
		}
		task.setContentKeyInfo(contentKey);
		String content = this.buildContent(opTp, contentOjb);
		task.setContent(content);
		logger.info("创建审核任务完成，任务信息如下:");
		return task;
	}
	
	/**
	 * 审核通过
	 * @param task
	 * @param context
	 */
	public void passTask(BusCheckTask task) {
		AssertUtil.objIsNull(task, "待处理的任务为null");
		try {
			logger.info("处理任务开始:" + task.getTaskId());
			CommonEnums.OpType opTp = EnumUtil.parseEnumByCode(CommonEnums.OpType.class, task.getOpTp());
			processContent(opTp, task.getContent(), task.getCheckOperId());
			logger.info("处理任务完成:" + task.getTaskId());
		} catch (Exception e) {
			throw new BizzException("任务处理失败,taskId=" + task.getTaskId(), e);
		}
		
	}
	
	/**
	 * 构造审核任务详细信息
	 * @param opTp
	 * @param context
	 * @return
	 */
	protected abstract String buildContent(OpType opTp, Object contentObj);
	
	/**
	 * 构造审核任务KEY值
	 * @param opTp
	 * @param context
	 * @return
	 */
	protected abstract String buildContentKey(OpType opTp, Object contentObj);
	
	/**
	 * 处理审核任务
	 * @param content
	 */
	protected abstract void processContent(CommonEnums.OpType opTp, String content, String checkOperId);
	
	/**
	 * 当前审核任务类型
	 * @return
	 */
	protected abstract String getTaskTp();
	
	/**
	 * 还原
	 */
	protected abstract Object restoreContent(String content);
}
