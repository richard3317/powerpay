package com.icpay.payment.db.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.BatchJobInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.BatchJobInfo;
import com.icpay.payment.db.dao.mybatis.model.BatchJobInfoExample;
import com.icpay.payment.db.dao.mybatis.model.BatchJobInfoKey;
import com.icpay.payment.db.dao.mybatis.model.BatchJobInfoExample.Criteria;
import com.icpay.payment.db.service.IBatchJobInfoService;

@Service("batchJobInfoService")
public class BatchJobInfoService extends BaseService implements IBatchJobInfoService {

	private static final Logger logger = Logger.getLogger(BatchJobInfoService.class);
	
	@Override
	public List<BatchJobInfo> select(Map<String, String> qryParamMap) {
		BatchJobInfoExample example = this.getQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}

	@Override
	public Pager<BatchJobInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		logger.info("分页查询任务配置信息开始");
		BatchJobInfoExample example = this.getQryExample(qryParamMap);
		BatchJobInfoMapper mapper = getMapper();
		Pager<BatchJobInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		
		logger.info("分页查询任务配置信息完成");
		return pager;
	}

	@Override
	public BatchJobInfo selectByPrimaryKey(int batchNo, int jobNo) {
		BatchJobInfoKey key = new BatchJobInfoKey();
		key.setBatchNo(batchNo);
		key.setJobNo(jobNo);
		return getMapper().selectByPrimaryKey(key);
	}

	/**
	 * 新增
	 */
	@Override
	public void add(BatchJobInfo batchJobInfo) {
		logger.info("新增任务配置信息开始");
		i18ObjIsNull(batchJobInfo, this.getClass().getSimpleName(), "待新增的记录为null");

		BatchJobInfo dbbatchJobInfo = this.selectByPrimaryKey(batchJobInfo.getBatchNo(), batchJobInfo.getJobNo());
		i18ObjIsNotNull(dbbatchJobInfo, this.getClass().getSimpleName(), "该任务配置信息已存在");

		batchJobInfo.setRecSt("1");
		this.getMapper().insert(batchJobInfo);
		logger.info("新增任务配置信息完成");
	}
	
	/**
	 * 更新
	 */
	@Override
	public void update(BatchJobInfo batchJobInfo) {
		logger.info("修改任务配置信息开始");
	
		i18ObjIsNull(batchJobInfo, this.getClass().getSimpleName(), "待修改的记录为null");
		BatchJobInfo dbbatchJobInfo = this.selectByPrimaryKey(batchJobInfo.getBatchNo(), batchJobInfo.getJobNo());
		i18ObjIsNull(dbbatchJobInfo, this.getClass().getSimpleName(), "该任务配置信息不存在");

		dbbatchJobInfo.setJobDesc(batchJobInfo.getJobDesc());
		dbbatchJobInfo.setJobCtrlFlgs(batchJobInfo.getJobCtrlFlgs());
		getMapper().updateByPrimaryKey(dbbatchJobInfo);
		
		logger.info("修改任务配置信息完成");
	}
	
	/**
	 * 删除
	 */
	@Override
	public void delete(int batchNo, int jobNo) {
		logger.info("删除任务配置信息开始");
		
		BatchJobInfo dbBatchJobInfo = this.selectByPrimaryKey(batchNo, jobNo);
		i18ObjIsNull(dbBatchJobInfo, this.getClass().getSimpleName(), "待删除的记录不存在");

		getMapper().deleteByPrimaryKey(dbBatchJobInfo);
		
		logger.info("删除任务配置信息完成");
	}
	
	@Override
	protected BatchJobInfoExample buildQryExample(Map<String, String> qryParamMap) {
		BatchJobInfoExample example = new BatchJobInfoExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:任务号
			String jobNo = StringUtil.trim(qryParamMap.get("jobNo"));
			if (!StringUtil.isBlank(jobNo)) {
				c.andJobNoEqualTo(Integer.valueOf(jobNo));
			}
			// 查询条件:批次号
			String batchNo = StringUtil.trim(qryParamMap.get("batchNo"));
			if (!StringUtil.isBlank(batchNo)) {
				c.andBatchNoEqualTo(Integer.valueOf(batchNo));
			}
			// 查询条件:任务描述
			String jobDesc = StringUtil.trim(qryParamMap.get("jobDesc"));
			if (!StringUtil.isBlank(jobDesc)) {
				c.andJobDescLike("%" + jobDesc + "%");
			}
		}
		// 排序字段
		example.setOrderByClause("batch_no asc, job_no asc");
		return example;
	}
	
	private BatchJobInfoMapper getMapper() {
		return this.getMapper(BatchJobInfoMapper.class);
	}
}
