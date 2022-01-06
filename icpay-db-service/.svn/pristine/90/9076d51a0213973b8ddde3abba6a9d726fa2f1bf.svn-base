package com.icpay.payment.db.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BusCheckTaskEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.BusCheckTaskMapper;
import com.icpay.payment.db.dao.mybatis.model.BusCheckTask;
import com.icpay.payment.db.dao.mybatis.model.BusCheckTaskExample;
import com.icpay.payment.db.dao.mybatis.model.BusCheckTaskExample.Criteria;
import com.icpay.payment.db.service.IBusCheckTaskService;

@Service("busCheckTaskService")
public class BusCheckTaskService extends BaseService implements IBusCheckTaskService {

	private static final Logger logger = Logger.getLogger(BusCheckTaskService.class);
	
	@Override
	public List<BusCheckTask> select(Map<String, String> qryParamMap) {
		BusCheckTaskExample example = this.getQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}

	@Override
	public Pager<BusCheckTask> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		logger.info("分页查询业务审核任务开始");
		BusCheckTaskExample example = this.getQryExample(qryParamMap);
		BusCheckTaskMapper mapper = getMapper();
		Pager<BusCheckTask> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		
		logger.info("分页查询业务审核任务完成");
		return pager;
	}

	@Override
	public BusCheckTask selectByPrimaryKey(int taskId) {
		return getMapper().selectByPrimaryKey(taskId);
	}

	/**
	 * 新增
	 */
	@Override
	public int add(BusCheckTask busCheckTask) {
		logger.info("新增业务审核任务开始");
		
		i18ObjIsNull(busCheckTask, this.getClass().getSimpleName(), "待新增的记录为null");
		// 先获取主键
		int taskId = this.genIntKey(CommonEnums.PrimaryKeyTp._02);
		BusCheckTask dbEntity = this.selectByPrimaryKey(taskId);
		// 判断主键是否为空
		i18ObjIsNotNull(dbEntity, this.getClass().getSimpleName(), "主键冲突: %s", String.valueOf(busCheckTask.getTaskId()));

		// 新增时复核员信息设置为空
		busCheckTask.setTaskId(taskId);
		busCheckTask.setCheckOperId("");
		busCheckTask.setCheckerComments("");
		busCheckTask.setCheckTm("");
		busCheckTask.setTaskSt(BusCheckTaskEnums.TaskSt._0.getCode()); // 新增时，任务状态置为待处理
		Date now = new Date();
		busCheckTask.setOpDt(DateUtil.dateToStr8(now));
		busCheckTask.setOpTm(DateUtil.dateToStr19(now));
		busCheckTask.setRecUpdTs(now);
		busCheckTask.setRecCrtTs(now);
		
		this.getMapper().insert(busCheckTask);
		
		logger.info("新增业务审核任务完成");
		return taskId;
	}
	
	/**
	 * 更新
	 */
	@Override
	public void update(BusCheckTask busCheckTask) {
		logger.info("修改业务审核任务开始");
	
		i18ObjIsNull(busCheckTask, this.getClass().getSimpleName(), "待修改的记录为null");
		BusCheckTask dbEntity = this.selectByPrimaryKey(busCheckTask.getTaskId());
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "该业务审核任务不存在");

		
		// 只能修改复核员、复核时间、任务状态、操作员备注字段
		dbEntity.setCheckOperId(StringUtil.strDefaultVal(busCheckTask.getCheckOperId(), ""));
		dbEntity.setCheckTm(StringUtil.strDefaultVal(busCheckTask.getCheckTm(), ""));
		dbEntity.setTaskSt(busCheckTask.getTaskSt());
		dbEntity.setCheckerComments(StringUtil.strDefaultVal(busCheckTask.getCheckerComments(), ""));
		dbEntity.setRecUpdTs(new Date());
		
		getMapper().updateByPrimaryKey(dbEntity);
		
		logger.info("修改业务审核任务完成");
	}
	
	@Override
	protected BusCheckTaskExample buildQryExample(Map<String, String> qryParamMap) {
		BusCheckTaskExample example = new BusCheckTaskExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:系统来源
			String sysId = StringUtil.trim(qryParamMap.get("sysId"));
			if (!StringUtil.isBlank(sysId)) {
				c.andSysIdEqualTo(sysId);
			}
			// 查询条件:任务类型
			String taskTp = StringUtil.trim(qryParamMap.get("taskTp"));
			if (!StringUtil.isBlank(taskTp)) {
				c.andTaskTpEqualTo(taskTp);
			}
			// 查询条件:单个任务状态
			String taskSt = StringUtil.trim(qryParamMap.get("taskSt"));
			if (!StringUtil.isBlank(taskSt)) {
				c.andTaskStEqualTo(taskSt);
			}
			// 查询条件:多个任务状态
			String taskStLst = StringUtil.trim(qryParamMap.get("taskStLst"));
			if (!StringUtil.isBlank(taskStLst)) {
				String[] stArr = taskStLst.split(",");
				if (stArr.length > 0) {
					List<String> lst = new ArrayList<String>();
					for (String s : stArr) {
						lst.add(s);
					}
					c.andTaskStIn(lst);
				}
			}
			// 查询条件:操作员
			String operId = StringUtil.trim(qryParamMap.get("operId"));
			if (!StringUtil.isBlank(operId)) {
				c.andOperIdEqualTo(operId);
			}
			// 查询条件:操作类型
			String opTp = StringUtil.trim(qryParamMap.get("opTp"));
			if (!StringUtil.isBlank(opTp)) {
				c.andOpTpEqualTo(opTp);
			}
			// 查询条件:操作日期范围-起始日期
			String startOpDt = StringUtil.trim(qryParamMap.get("startOpDt"));
			if (!StringUtil.isBlank(startOpDt)) {
				c.andOpDtGreaterThanOrEqualTo(startOpDt);
			}
			// 查询条件:操作日期范围-结束日期
			String endOpDt = StringUtil.trim(qryParamMap.get("endOpDt"));
			if (!StringUtil.isBlank(endOpDt)) {
				c.andOpDtLessThanOrEqualTo(endOpDt);
			}
			// 查询条件:复核员
			String checkOperId = StringUtil.trim(qryParamMap.get("checkOperId"));
			if (!StringUtil.isBlank(checkOperId)) {
				c.andCheckOperIdEqualTo(checkOperId);
			}
			// 查询条件:模糊任务关键信息
			String contentKey = StringUtil.trim(qryParamMap.get("contentKey"));
			if (!StringUtil.isBlank(contentKey)) {
				c.andContentKeyInfoLike("%" + contentKey + "%");
			}
			// 查询条件：精确匹配任务关键信息
			String fullContentKey = StringUtil.trim(qryParamMap.get("fullContentKey"));
			if (!StringUtil.isBlank(fullContentKey)) {
				c.andContentKeyInfoEqualTo(fullContentKey);
			}
		}
		// 排序字段
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}
	
	private BusCheckTaskMapper getMapper() {
		return this.getMapper(BusCheckTaskMapper.class);
	}
}
