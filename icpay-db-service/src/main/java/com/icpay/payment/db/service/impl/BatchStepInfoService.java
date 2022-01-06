package com.icpay.payment.db.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.BatchStepInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.BatchStepInfo;
import com.icpay.payment.db.dao.mybatis.model.BatchStepInfoExample;
import com.icpay.payment.db.dao.mybatis.model.BatchStepInfoKey;
import com.icpay.payment.db.dao.mybatis.model.BatchStepInfoExample.Criteria;
import com.icpay.payment.db.service.IBatchStepInfoService;

@Service("batchStepInfoService")
public class BatchStepInfoService extends BaseService implements IBatchStepInfoService {

	private static final Logger logger = Logger.getLogger(BatchStepInfoService.class);
	
	@Override
	public List<BatchStepInfo> select(Map<String, String> qryParamMap) {
		BatchStepInfoExample example = this.getQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}

	@Override
	public Pager<BatchStepInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		logger.info("分页查询批量任务配置信息开始");
		BatchStepInfoExample example = this.getQryExample(qryParamMap);
		BatchStepInfoMapper mapper = getMapper();
		Pager<BatchStepInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		
		logger.info("分页查询批量任务配置信息完成");
		return pager;
	}

	@Override
	public BatchStepInfo selectByPrimaryKey(int batchNo, int jobNo, int stepNo) {
		BatchStepInfoKey key = new BatchStepInfoKey();
		key.setBatchNo(batchNo);
		key.setJobNo(jobNo);
		key.setStepNo(stepNo);
		return getMapper().selectByPrimaryKey(key);
	}

	/**
	 * 新增
	 */
	@Override
	public void add(BatchStepInfo batchStepInfo) {
		logger.info("新增批量任务配置信息开始");
		i18ObjIsNull(batchStepInfo, this.getClass().getSimpleName(), "待新增的记录为null");
		BatchStepInfo dbbatchStepInfo = this.selectByPrimaryKey(batchStepInfo.getBatchNo(), batchStepInfo.getJobNo(), batchStepInfo.getStepNo());
		i18ObjIsNotNull(dbbatchStepInfo, this.getClass().getSimpleName(), "该批量任务配置信息已存在");

		batchStepInfo.setRecSt("1");
		this.getMapper().insert(batchStepInfo);
		logger.info("新增批量任务配置信息完成");
	}
	
	/**
	 * 更新
	 */
	@Override
	public void update(BatchStepInfo batchStepInfo) {
		logger.info("修改批量任务配置信息开始");
	
		i18ObjIsNull(batchStepInfo, this.getClass().getSimpleName(), "待修改的记录为null");
		BatchStepInfo dbbatchStepInfo = this.selectByPrimaryKey(batchStepInfo.getBatchNo(), batchStepInfo.getJobNo(), batchStepInfo.getStepNo());
		i18ObjIsNull(dbbatchStepInfo, this.getClass().getSimpleName(), "该批量任务配置信息不存在");
		batchStepInfo.setRecSt("1");
		getMapper().updateByPrimaryKey(batchStepInfo);
		
		logger.info("修改批量任务配置信息完成");
	}
	
	/**
	 * 删除
	 */
	@Override
	public void delete(int batchNo, int jobNo, int stepNo) {
		logger.info("删除批量任务配置信息开始");
		
		BatchStepInfo dbBatchStepInfo = this.selectByPrimaryKey(batchNo, jobNo, stepNo);
		i18ObjIsNull(dbBatchStepInfo, this.getClass().getSimpleName(), "待删除的记录不存在");
		getMapper().deleteByPrimaryKey(dbBatchStepInfo);
		
		logger.info("删除批量任务配置信息完成");
	}
	
	@Override
	protected BatchStepInfoExample buildQryExample(Map<String, String> qryParamMap) {
		BatchStepInfoExample example = new BatchStepInfoExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:步骤号
			String stepNo = StringUtil.trim(qryParamMap.get("stepNo"));
			if (!StringUtil.isBlank(stepNo)) {
				c.andStepNoEqualTo(Integer.valueOf(stepNo));
			}
			// 查询条件:批次号
			String batchNo = StringUtil.trim(qryParamMap.get("batchNo"));
			if (!StringUtil.isBlank(batchNo)) {
				c.andBatchNoEqualTo(Integer.valueOf(batchNo));
			}
			// 查询条件:步骤描述
			String stepDesc = StringUtil.trim(qryParamMap.get("stepDesc"));
			if (!StringUtil.isBlank(stepDesc)) {
				c.andStepDescLike("%" + stepDesc + "%");
			}
			// 查询条件:任务号
			String jobNo = StringUtil.trim(qryParamMap.get("jobNo"));
			if (!StringUtil.isBlank(jobNo)) {
				c.andJobNoEqualTo(Integer.valueOf(jobNo));
			}
		}
		// 排序字段
		example.setOrderByClause("batch_no, job_no, step_no");
		return example;
	}
	
	private BatchStepInfoMapper getMapper() {
		return this.getMapper(BatchStepInfoMapper.class);
	}
}
