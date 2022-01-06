package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BatchEnums;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.BatchTaskExecLogMapper;
import com.icpay.payment.db.dao.mybatis.model.BatchTaskExecLog;
import com.icpay.payment.db.dao.mybatis.model.BatchTaskExecLogExample;
import com.icpay.payment.db.dao.mybatis.model.BatchTaskExecLogExample.Criteria;
import com.icpay.payment.db.service.IBatchTaskExecLogService;

@Service("batchTaskExecLogService")
public class BatchTaskExecLogService extends BaseService implements IBatchTaskExecLogService {

	@Override
	public Pager<BatchTaskExecLog> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		BatchTaskExecLogExample example = this.getQryExample(qryParamMap);
		BatchTaskExecLogMapper mapper = getMapper();
		Pager<BatchTaskExecLog> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public BatchTaskExecLog selectByPrimaryKey(String logId) {
		return this.getMapper().selectByPrimaryKey(logId);
	}

	private BatchTaskExecLogMapper getMapper() {
		return this.getMapper(BatchTaskExecLogMapper.class);
	}

	@Override
	protected BatchTaskExecLogExample buildQryExample(Map<String, String> qryParamMap) {
		BatchTaskExecLogExample example = new BatchTaskExecLogExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件: 任务名称
			String taskNm = StringUtil.trim(qryParamMap.get("taskNm"));
			if (!StringUtil.isBlank(taskNm)) {
				c.andTaskNmLike("%" + taskNm + "%");
			}
			
			// 查询条件: 批量日期
			String batchDt = StringUtil.trim(qryParamMap.get("batchDt"));
			if (!StringUtil.isBlank(batchDt)) {
				c.andBatchDtEqualTo(batchDt);
			}
		}
		// 排序字段
		example.setOrderByClause("rec_crt_ts desc");
		return example;
	}

	@Override
	public void add(BatchTaskExecLog batchStepInfo) {
		batchStepInfo.setStatus(BatchEnums.RunningStatus._0.getCode());
		Date now = new Date();
		batchStepInfo.setStartTm(DateUtil.dateToStr19(now));
		batchStepInfo.setRecCrtTs(now);
		batchStepInfo.setRecUpdTs(now);
		this.getMapper().insert(batchStepInfo);
	}

	@Override
	public void update(BatchTaskExecLog batchStepInfo) {
		BatchTaskExecLog dbEntity = this.getMapper().selectByPrimaryKey(batchStepInfo.getLogId());
		BeanUtil.cloneEntity(batchStepInfo, dbEntity, new String[] {
				"endTm", "status", "execMsg"
		});
		dbEntity.setRecUpdTs(new Date());
		this.getMapper().updateByPrimaryKey(dbEntity);
	}
}
