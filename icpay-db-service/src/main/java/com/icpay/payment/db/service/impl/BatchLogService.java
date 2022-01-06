package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.BatchExecLogDetailMapper;
import com.icpay.payment.db.dao.mybatis.mapper.BatchExecLogMapper;
import com.icpay.payment.db.dao.mybatis.model.BatchExecLog;
import com.icpay.payment.db.dao.mybatis.model.BatchExecLogDetail;
import com.icpay.payment.db.dao.mybatis.model.BatchExecLogDetailExample;
import com.icpay.payment.db.dao.mybatis.model.BatchExecLogExample;
import com.icpay.payment.db.dao.mybatis.model.BatchExecLogExample.Criteria;
import com.icpay.payment.db.service.IBatchLogService;

@Service("batchLogService")
public class BatchLogService extends BaseService implements IBatchLogService {
	
	private static final Logger logger = Logger.getLogger(BatchLogService.class);

	@Override
	public void addBatchLog(BatchExecLog batchLog) {
		logger.info("新增批量任务执行日志开始");
		i18ObjIsNull(batchLog, this.getClass().getSimpleName(), "待新增的记录为null");

		BatchExecLog dbBatchLog = this.selectBatchLogByPrimaryKey(batchLog.getLogId());
		i18ObjIsNotNull(dbBatchLog, this.getClass().getSimpleName(), "该批量任务执行日志已存在");
		
		Date now = new Date();
		String tmStr = DateUtil.formatDate(now, DateUtil.DATE_FORMAT_19);
		batchLog.setRunningDt(DateUtil.formatDate(now, DateUtil.DATE_FORMAT_8));
		batchLog.setStartTm(tmStr);
		batchLog.setEndTm("");
		batchLog.setRecCrtTs(now);
		batchLog.setRecUpdTs(now);
		this.getBatchExecLogMapper().insert(batchLog);
		logger.info("新增批量任务执行日志完成");
	}

	@Override
	public void addBatchLogDetail(BatchExecLogDetail batchLogDetail) {
		logger.info("新增批量任务执行详细记录开始");
		i18ObjIsNull(batchLogDetail, this.getClass().getSimpleName(), "待新增的记录为null");

		BatchExecLog dbBatchLog = this.selectBatchLogByPrimaryKey(batchLogDetail.getLogDetailId());
		i18ObjIsNotNull(dbBatchLog, this.getClass().getSimpleName(), "该批量任务执行详情记录已存在");
		Date now = new Date();
		String tmStr = DateUtil.formatDate(now, DateUtil.DATE_FORMAT_19);
		batchLogDetail.setRunningDt(DateUtil.formatDate(now, DateUtil.DATE_FORMAT_8));
		batchLogDetail.setStartTm(tmStr);
		batchLogDetail.setEndTm("");
		batchLogDetail.setRecCrtTs(now);
		batchLogDetail.setRecUpdTs(now);
		this.getBatchExecLogDetailMapper().insert(batchLogDetail);
		logger.info("新增批量任务执行详情记录完成");
	}

	@Override
	public List<BatchExecLog> selectBatchLog(Map<String, String> qryParamMap) {
		BatchExecLogExample example = this.buildBatchLogQryExample(qryParamMap);
		return this.getBatchExecLogMapper().selectByExample(example);
	}

	@Override
	public Pager<BatchExecLog> selectBatchLogByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		logger.info("分页查询批量任务执行日志开始");
		BatchExecLogExample example = this.buildBatchLogQryExample(qryParamMap);
		BatchExecLogMapper mapper = this.getBatchExecLogMapper();
		Pager<BatchExecLog> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		
		logger.info("分页查询批量任务执行日志完成");
		return pager;
	}

	@Override
	public BatchExecLog selectBatchLogByPrimaryKey(String logId) {
		return this.getBatchExecLogMapper().selectByPrimaryKey(logId);
	}

	@Override
	public List<BatchExecLogDetail> selectBatchLogDetail(Map<String, String> qryParamMap) {
		BatchExecLogDetailExample example = this.buildBatchLogDetailQryExample(qryParamMap);
		return this.getBatchExecLogDetailMapper().selectByExample(example);
	}

	@Override
	public Pager<BatchExecLogDetail> selectBatchLogDetailByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		logger.info("分页查询批量任务执行详情记录开始");
		BatchExecLogDetailExample example = this.buildBatchLogDetailQryExample(qryParamMap);
		BatchExecLogDetailMapper mapper = this.getBatchExecLogDetailMapper();
		Pager<BatchExecLogDetail> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		
		logger.info("分页查询批量任务执行详情记录完成");
		return pager;
	}

	@Override
	public BatchExecLogDetail selectBatchLogDetailByPrimaryKey(String logDetailId) {
		return this.getBatchExecLogDetailMapper().selectByPrimaryKey(logDetailId);
	}

	@Override
	public void updateBatchLog(BatchExecLog batchLog) {
		logger.info("修改批量任务执行日志开始");
		i18ObjIsNull(batchLog, this.getClass().getSimpleName(), "待修改的记录为null");
		BatchExecLog dbBatchLog = this.selectBatchLogByPrimaryKey(batchLog.getLogId());
		i18ObjIsNull(dbBatchLog, this.getClass().getSimpleName(), "该批量任务执行日志不存在");

		// 只能更新如下几个字段：exec_msg，end_tm, status
		dbBatchLog.setExecMsg(batchLog.getExecMsg());
		dbBatchLog.setStatus(batchLog.getStatus());
		dbBatchLog.setEndTm(batchLog.getEndTm());
		dbBatchLog.setRecUpdTs(new Date());
		
		this.getBatchExecLogMapper().updateByPrimaryKey(dbBatchLog);
		logger.info("修改批量任务执行日志完成");
	}

	@Override
	public void updateBatchLogDetail(BatchExecLogDetail batchLogDetail) {
		logger.info("修改批量任务执行详情记录开始");
		i18ObjIsNull(batchLogDetail, this.getClass().getSimpleName(), "待修改的记录为null");
		BatchExecLogDetail dbBatchLogDetail = this.selectBatchLogDetailByPrimaryKey(batchLogDetail.getLogDetailId());
		i18ObjIsNull(dbBatchLogDetail, this.getClass().getSimpleName(), "该批量任务执行详情记录不存在");

		
		// 只能更新如下几个字段：err_msg, exec_msg, end_tm, status
		dbBatchLogDetail.setErrMsg(batchLogDetail.getErrMsg());
		dbBatchLogDetail.setExecMsg(batchLogDetail.getExecMsg());
		dbBatchLogDetail.setEndTm(batchLogDetail.getEndTm());
		dbBatchLogDetail.setStatus(batchLogDetail.getStatus());
		dbBatchLogDetail.setRecUpdTs(new Date());
		
		this.getBatchExecLogDetailMapper().updateByPrimaryKey(dbBatchLogDetail);
		logger.info("修改批量任务执行详情记录完成");
	}

	private BatchExecLogMapper getBatchExecLogMapper() {
		return this.getMapper(BatchExecLogMapper.class);
	}
	
	private BatchExecLogDetailMapper getBatchExecLogDetailMapper() {
		return this.getMapper(BatchExecLogDetailMapper.class);
	}
	
	protected BatchExecLogExample buildBatchLogQryExample(Map<String, String> qryParamMap) {
		BatchExecLogExample example = new BatchExecLogExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:批次号
			String batchNo = StringUtil.trim(qryParamMap.get("batchNo"));
			if (!StringUtil.isBlank(batchNo)) {
				c.andBatchNoEqualTo(Integer.valueOf(batchNo));
			}
			// 批量日期范围:起始日期
			String startBatchDt = StringUtil.trim(qryParamMap.get("startBatchDt"));
			if (!StringUtil.isBlank(startBatchDt)) {
				c.andBatchDtGreaterThanOrEqualTo(startBatchDt);
			}
			// 批量日期范围:结束日期
			String endBatchDt = StringUtil.trim(qryParamMap.get("endBatchDt"));
			if (!StringUtil.isBlank(endBatchDt)) {
				c.andBatchDtLessThanOrEqualTo(endBatchDt);
			}
			// 批量日期
			String batchDt = StringUtil.trim(qryParamMap.get("batchDt"));
			if (!StringUtil.isBlank(batchDt)) {
				c.andBatchDtEqualTo(batchDt);
			}
			// 状态
			String status = StringUtil.trim(qryParamMap.get("status"));
			if (!StringUtil.isBlank(status)) {
				c.andStatusEqualTo(status);
			}
			// 执行日期范围:起始日期
			String startRunningDt = StringUtil.trim(qryParamMap.get("startRunningDt"));
			if (!StringUtil.isBlank(startRunningDt)) {
				c.andRunningDtGreaterThanOrEqualTo(startRunningDt);
			}
			// 执行日期范围:结束日期
			String endRunningDt = StringUtil.trim(qryParamMap.get("endRunningDt"));
			if (!StringUtil.isBlank(endRunningDt)) {
				c.andRunningDtLessThanOrEqualTo(endRunningDt);
			}
			// 执行日期
			String runningDt = StringUtil.trim(qryParamMap.get("runningDt"));
			if (!StringUtil.isBlank(runningDt)) {
				c.andRunningDtEqualTo(runningDt);
			}
		}
		// 排序字段
		example.setOrderByClause("rec_crt_ts desc");
		return example;
	}
	
	protected BatchExecLogDetailExample buildBatchLogDetailQryExample(Map<String, String> qryParamMap) {
		BatchExecLogDetailExample example = new BatchExecLogDetailExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			com.icpay.payment.db.dao.mybatis.model.BatchExecLogDetailExample.Criteria c = example.createCriteria();
			// 查询条件: 日志明细所属的批次日志信息ID
			String logId = StringUtil.trim(qryParamMap.get("logId"));
			if (!StringUtil.isBlank(logId)) {
				c.andLogIdEqualTo(logId);
			}
			// 查询条件:批次号
			String batchNo = StringUtil.trim(qryParamMap.get("batchNo"));
			if (!StringUtil.isBlank(batchNo)) {
				c.andBatchNoEqualTo(Integer.valueOf(batchNo));
			}
			// 查询条件:任务号
			String jobNo = StringUtil.trim(qryParamMap.get("jobNo"));
			if (!StringUtil.isBlank(jobNo)) {
				c.andJobNoEqualTo(Integer.valueOf(jobNo));
			}
			// 查询条件:步骤号
			String stepNo = StringUtil.trim(qryParamMap.get("stepNo"));
			if (!StringUtil.isBlank(stepNo)) {
				c.andStepNoEqualTo(Integer.valueOf(stepNo));
			}
			// 批量日期范围:起始日期
			String startBatchDt = StringUtil.trim(qryParamMap.get("startBatchDt"));
			if (!StringUtil.isBlank(startBatchDt)) {
				c.andBatchDtGreaterThanOrEqualTo(startBatchDt);
			}
			// 批量日期范围:结束日期
			String endBatchDt = StringUtil.trim(qryParamMap.get("endBatchDt"));
			if (!StringUtil.isBlank(endBatchDt)) {
				c.andBatchDtLessThanOrEqualTo(endBatchDt);
			}
			// 批量日期
			String batchDt = StringUtil.trim(qryParamMap.get("batchDt"));
			if (!StringUtil.isBlank(batchDt)) {
				c.andBatchDtEqualTo(batchDt);
			}
			// 状态
			String status = StringUtil.trim(qryParamMap.get("status"));
			if (!StringUtil.isBlank(status)) {
				c.andStatusEqualTo(status);
			}
			// 执行日期范围:起始日期
			String startRunningDt = StringUtil.trim(qryParamMap.get("startRunningDt"));
			if (!StringUtil.isBlank(startRunningDt)) {
				c.andRunningDtGreaterThanOrEqualTo(startRunningDt);
			}
			// 执行日期范围:结束日期
			String endRunningDt = StringUtil.trim(qryParamMap.get("endRunningDt"));
			if (!StringUtil.isBlank(endRunningDt)) {
				c.andRunningDtLessThanOrEqualTo(endRunningDt);
			}
			// 执行日期
			String runningDt = StringUtil.trim(qryParamMap.get("runningDt"));
			if (!StringUtil.isBlank(runningDt)) {
				c.andRunningDtEqualTo(runningDt);
			}
		}
		// 排序字段
		example.setOrderByClause("rec_crt_ts desc");
		return example;
	}
}
