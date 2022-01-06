package com.icpay.payment.db.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.BmOperLogMapper;
import com.icpay.payment.db.dao.mybatis.model.BmOperLog;
import com.icpay.payment.db.dao.mybatis.model.BmOperLogExample;
import com.icpay.payment.db.dao.mybatis.model.BmOperLogExample.Criteria;
import com.icpay.payment.db.service.IBmOperLogService;

@Service("bmOperLogService")
public class BmOperLogService extends BaseService implements IBmOperLogService {

	private static final Logger logger = Logger.getLogger(BmOperLogService.class);

	@Override
	public Pager<BmOperLog> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		logger.info("分页查询操作日志开始");
		BmOperLogExample example = this.getQryExample(qryParamMap);
		BmOperLogMapper mapper = getMapper();
		Pager<BmOperLog> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		
		logger.info("分页查询操作日志完成");
		return pager;
	}

	@Override
	public BmOperLog selectByPrimaryKey(int logId) {
		return getMapper().selectByPrimaryKey(logId);
	}
	
	public void add(BmOperLog log) {
		i18ObjIsNull(log, this.getClass().getSimpleName(), "日志对象为null");

		this.getMapper().insert(log);
	}
	
	protected BmOperLogExample buildQryExample(Map<String, String> qryParamMap) {
		BmOperLogExample example = new BmOperLogExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:系统ID
			String sysId = StringUtil.trim(qryParamMap.get("sysId"));
			if (!StringUtil.isBlank(sysId)) {
				c.andSysIdEqualTo(sysId);
			}
			// 查询条件:操作类型
			String opTp = StringUtil.trim(qryParamMap.get("opTp"));
			if (!StringUtil.isBlank(opTp)) {
				c.andOpTpEqualTo(opTp);
			}
			// 查询条件:操作员信息
			String operInfo = StringUtil.trim(qryParamMap.get("operInfo"));
			if (!StringUtil.isBlank(operInfo)) {
				c.andOperInfoLike("%" + operInfo + "%");
			}
			// 查询条件:操作日期
			String startOpDt = StringUtil.trim(qryParamMap.get("startOpDt"));
			if (!StringUtil.isBlank(startOpDt)) {
				c.andOpDtGreaterThanOrEqualTo(startOpDt);
			}
			String endOpDt = StringUtil.trim(qryParamMap.get("endOpDt"));
			if (!StringUtil.isBlank(endOpDt)) {
				c.andOpDtLessThanOrEqualTo(endOpDt);
			}
			// 查询条件: 功能模块
			String opFuncInfo = StringUtil.trim(qryParamMap.get("opFuncInfo"));
			if (!StringUtil.isBlank(opFuncInfo)) {
				c.andOpFuncInfoEqualTo(opFuncInfo);
			}
		}
		// 排序字段
		example.setOrderByClause("op_tm desc");
		return example;
	}
	
	private BmOperLogMapper getMapper() {
		return this.getMapper(BmOperLogMapper.class);
	}
}
