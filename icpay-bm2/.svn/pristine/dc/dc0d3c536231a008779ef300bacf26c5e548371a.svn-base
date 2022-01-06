package com.icpay.payment.bm.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.icpay.payment.common.enums.BusCheckTaskEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.BusCheckTask;
import com.icpay.payment.db.service.IBusCheckTaskService;

@Component("busCheckBO")
public class BusCheckBO extends BaseBO {

	private static final Logger logger = Logger.getLogger(BusCheckBO.class);
	
	/**
	 * 创建审核任务
	 */
	public BusCheckTask newTask(BusCheckTaskEnums.TaskTp taskTp, String operId, CommonEnums.OpType opTp, String operComments, Object contentOjb) {
		AssertUtil.objIsNull(taskTp, "taskTp is null");
		AssertUtil.strIsBlank(operId, "oper is blank");
		AssertUtil.objIsNull(opTp, "opTp is null");
		AssertUtil.objIsNull(contentOjb, "contentOjb is null");
		
		logger.info("创建审核任务开始:" + taskTp + "_" + operId + "_" + opTp.getCode());
		
		// 根据任务类型获取审核任务处理器
		BusCheckProcessor processor = this.getProcessor(taskTp);
		
		// 创建任务并保存至审核任务表
		BusCheckTask task = processor.createTask(operId, opTp, contentOjb);
		Map<String, String> qryMap = new HashMap<String, String>();
		qryMap.put("fullContentKey", task.getContentKeyInfo());
		qryMap.put("taskStLst", 
				BusCheckTaskEnums.TaskSt._0.getCode() + "," + BusCheckTaskEnums.TaskSt._1.getCode());
		IBusCheckTaskService service = this.getBusCheckService();
		List<BusCheckTask> qryLst = service.select(qryMap);
		if (qryLst.size() > 0) {
			throw new BizzException("创建任务失败，已存在待处理或处理中的任务：" + task.getContentKeyInfo());
		}
		task.setOperComments(StringUtil.strDefaultVal(operComments, ""));
		service.add(task);
		logger.info("创建审核任务完成:" + taskTp + "_" + operId + "_" + opTp.getCode());
		return task;
	}
	
	/**
	 * 审核通过
	 */
	public void pass(int taskId, String checker, String checkerComments) {
		IBusCheckTaskService service = this.getBusCheckService();
		BusCheckTask task = service.selectByPrimaryKey(taskId);
		AssertUtil.objIsNull(task, "未找到审核任务:" + taskId);
		AssertUtil.strNotEquals(checker, task.getCheckOperId(), "任务正在被其他操作员审核");
		
		logger.info("审核通过操作开始:" + taskId + "_" + checker);
		BusCheckTaskEnums.TaskTp taskTp = EnumUtil.parseEnumByCode(BusCheckTaskEnums.TaskTp.class, task.getTaskTp());
		BusCheckProcessor processor = this.getProcessor(taskTp);
		try {
			processor.passTask(task);
		} catch (Exception e) {
			logger.error("审核通过操作异常:" + taskId, e);
			task.setCheckerComments(checkerComments);
			task.setCheckTm(DateUtil.now19());
			task.setTaskSt(BusCheckTaskEnums.TaskSt._9.getCode());
			service.update(task);
			throw new BizzException("操作异常");
		}
		task.setCheckerComments(checkerComments);
		task.setCheckTm(DateUtil.now19());
		task.setTaskSt(BusCheckTaskEnums.TaskSt._2.getCode());
		service.update(task);
		logger.info("审核通过操作完成:" + taskId + "_" + checker);
	}
	
	/**
	 * 审核拒绝
	 */
	public void refuse(int taskId, String checker, String refuseReason) {
		IBusCheckTaskService service = this.getBusCheckService();
		BusCheckTask task = service.selectByPrimaryKey(taskId);
		AssertUtil.objIsNull(task, "未找到审核任务记录:" + taskId);
		AssertUtil.strNotEquals(checker, task.getCheckOperId(), "不能拒绝不属于自己的任务");
		AssertUtil.strNotEquals(task.getTaskSt(), BusCheckTaskEnums.TaskSt._1.getCode(), "只能拒绝状态为\"处理中\"的任务");
		
		logger.info("拒绝操作开始:" + taskId + "_" + checker);
		task.setCheckerComments(refuseReason);
		task.setCheckTm(DateUtil.now19());
		task.setTaskSt(BusCheckTaskEnums.TaskSt._4.getCode());
		service.update(task);
		logger.info("拒绝操作完成:" + taskId + "_" + checker);
	}
	
	/**
	 * 撤销任务
	 */
	public void cancle(int taskId, String oper) {
		IBusCheckTaskService service = this.getBusCheckService();
		BusCheckTask task = service.selectByPrimaryKey(taskId);
		AssertUtil.objIsNull(task, "未找到审核任务记录:" + taskId);
		AssertUtil.strNotEquals(oper, task.getOperId(), "不能撤销不属于自己的任务");
		AssertUtil.strNotEquals(task.getTaskSt(), BusCheckTaskEnums.TaskSt._0.getCode(), "只能撤销状态为\"待处理\"的任务");
		
		logger.info("撤销操作开始:" + taskId + "_" + oper);
		task.setTaskSt(BusCheckTaskEnums.TaskSt._3.getCode());
		service.update(task);
		logger.info("撤销操作完成:" + taskId + "_" + oper);
	}
	
	/**
	 * 还原审核内容
	 * @param taskId
	 * @return
	 */
	public Object restoreTaskContent(String taskTp, String content) {
		AssertUtil.strIsBlank(taskTp, "taskTp is blank.");
		AssertUtil.strIsBlank(content, "content is blank.");
		
		BusCheckTaskEnums.TaskTp tp = EnumUtil.parseEnumByCode(BusCheckTaskEnums.TaskTp.class, taskTp);
		BusCheckProcessor processor = this.getProcessor(tp);
		return processor.restoreContent(content);
	}

	/**
	 * 根据审核任务类型获取审核任务处理类
	 */
	private BusCheckProcessor getProcessor(BusCheckTaskEnums.TaskTp taskTp) {
		switch (taskTp) {
			case _01:
				return new MchntInfoBusCheckProcessor();
//			case _02:
//				return new MchntTxnCfgBusCheckProcessor();
			case _07:
				return new OrganMchntInfoBusCheckProcessor();
			case _03:
				return new RoutInfoBusCheckProcessor();
			case _04:
				return new AgentInfoBusCheckProcessor();
			case _05:
				return new MchntInfoAndMerSettlePolicyBusCheckProcessor();
			case _06:
				return new MchntInfoUpdAndDelCheckProcessor();
		///王允   added {{		
				//增加商户清算信息的审核
			case _51:
				return new MchntSettleBusCheckProcessor();
				//商户清算计费方式的审核
			case _52:
				return new MchntSettlePolicySubProcessor();	
		///}}		
			default :
				throw new BizzException("未找到任务处理类:" + taskTp);
		}
	}
	
	private IBusCheckTaskService getBusCheckService() {
		return DBHessionServiceClient.getService(IBusCheckTaskService.class);
	}
}
