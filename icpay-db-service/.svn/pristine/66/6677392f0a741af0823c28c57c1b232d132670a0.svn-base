package com.icpay.payment.db.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.BatchInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.BatchInfo;
import com.icpay.payment.db.dao.mybatis.model.BatchInfoExample;
import com.icpay.payment.db.dao.mybatis.model.BatchInfoExample.Criteria;
import com.icpay.payment.db.service.IBatchInfoService;

@Service("batchInfoService")
public class BatchInfoService extends BaseService implements IBatchInfoService {

	private static final Logger logger = Logger.getLogger(BatchInfoService.class);
	
	@Override
	public List<BatchInfo> select(Map<String, String> qryParamMap) {
		BatchInfoExample example = this.getQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}

	@Override
	public Pager<BatchInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		logger.info("分页查询批量任务配置信息开始");
		BatchInfoExample example = this.getQryExample(qryParamMap);
		BatchInfoMapper mapper = getMapper();
		Pager<BatchInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		
		logger.info("分页查询批量任务配置信息完成");
		return pager;
	}

	@Override
	public BatchInfo selectByPrimaryKey(int batchNo) {
		return getMapper().selectByPrimaryKey(batchNo);
	}

	/**
	 * 新增
	 */
	@Override
	public void add(BatchInfo batchInfo) {
		logger.info("新增批量任务配置信息开始");
		i18ObjIsNull(batchInfo, this.getClass().getSimpleName(), "待新增的记录为null");
		BatchInfo dbbatchInfo = this.selectByPrimaryKey(batchInfo.getBatchNo());
		i18ObjIsNotNull(dbbatchInfo, this.getClass().getSimpleName(), "该批量任务配置信息已存在");

		batchInfo.setRecSt("1");
		this.getMapper().insert(batchInfo);
		logger.info("新增批量任务配置信息完成");
	}
	
	/**
	 * 更新
	 */
	@Override
	public void update(BatchInfo batchInfo) {
		logger.info("修改批量任务配置信息开始");
	
		i18ObjIsNull(batchInfo, this.getClass().getSimpleName(), "待修改的记录为null");

		BatchInfo dbbatchInfo = this.selectByPrimaryKey(batchInfo.getBatchNo());
		i18ObjIsNull(dbbatchInfo, this.getClass().getSimpleName(), "该批量任务配置信息不存在");

		dbbatchInfo.setBatchDesc(batchInfo.getBatchDesc());
		dbbatchInfo.setRunCycle(batchInfo.getRunCycle());
		dbbatchInfo.setRunTmConfig(batchInfo.getRunTmConfig());
		getMapper().updateByPrimaryKey(dbbatchInfo);
		
		logger.info("修改批量任务配置信息完成");
	}
	
	/**
	 * 删除
	 */
	@Override
	public void delete(int batchNo) {
		logger.info("删除批量任务配置信息开始");
		
		BatchInfo dbBatchInfo = this.selectByPrimaryKey(batchNo);
		i18ObjIsNull(dbBatchInfo, this.getClass().getSimpleName(), "待删除的记录不存在");

		getMapper().deleteByPrimaryKey(batchNo);
		
		logger.info("删除批量任务配置信息完成");
	}
	
	@Override
	protected BatchInfoExample buildQryExample(Map<String, String> qryParamMap) {
		BatchInfoExample example = new BatchInfoExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:批次描述
			String batchDesc = StringUtil.trim(qryParamMap.get("batchDesc"));
			if (!StringUtil.isBlank(batchDesc)) {
				c.andBatchDescLike("%" + batchDesc + "%");
			}
			// 查询条件:批次号
			String batchNo = StringUtil.trim(qryParamMap.get("batchNo"));
			if (!StringUtil.isBlank(batchNo)) {
				c.andBatchNoEqualTo(Integer.valueOf(batchNo));
			}
		}
		// 排序字段
		example.setOrderByClause("batch_no asc");
		return example;
	}
	
	private BatchInfoMapper getMapper() {
		return this.getMapper(BatchInfoMapper.class);
	}
}
