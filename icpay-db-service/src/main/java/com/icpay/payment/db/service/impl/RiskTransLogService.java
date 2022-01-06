package com.icpay.payment.db.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.RiskTransLogMapper;
import com.icpay.payment.db.dao.mybatis.model.RiskTransLog;
import com.icpay.payment.db.dao.mybatis.model.RiskTransLogExample;
import com.icpay.payment.db.dao.mybatis.model.RiskTransLogExample.Criteria;
import com.icpay.payment.db.service.IRiskTransLogService;

@Service("riskTransLogService")
public class RiskTransLogService extends BaseService implements IRiskTransLogService {

	private static final Logger logger = Logger.getLogger(RiskTransLogService.class);
	
	@Override
	public List<RiskTransLog> select(Map<String, String> qryParamMap) {
		RiskTransLogExample example = this.getQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}

	@Override
	public Pager<RiskTransLog> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		logger.info("分页查询风险交易信息开始");
		RiskTransLogExample example = this.getQryExample(qryParamMap);
		RiskTransLogMapper mapper = getMapper();
		Pager<RiskTransLog> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		
		logger.info("分页查询风险交易信息完成");
		return pager;
	}

	@Override
	public RiskTransLog selectByPrimaryKey(String transSeqId) {
		return getMapper().selectByPrimaryKey(Long.valueOf(transSeqId));
	}
		
	@Override
	protected RiskTransLogExample buildQryExample(Map<String, String> qryParamMap) {
		RiskTransLogExample example = new RiskTransLogExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:交易流水号
			String transSeqId = StringUtil.trim(qryParamMap.get("transSeqId"));
			if (!StringUtil.isBlank(transSeqId)) {
				c.andTransSeqIdEqualTo(transSeqId);
			}
			// 查询条件:违反风险规则ID
			String ruleId = StringUtil.trim(qryParamMap.get("ruleId"));
			if (!StringUtil.isBlank(ruleId)) {
				c.andRuleIdEqualTo(Integer.valueOf(ruleId));
			}
			// 查询条件:处理结果
			String result = StringUtil.trim(qryParamMap.get("result"));
			if (!StringUtil.isBlank(result)) {
				c.andResultEqualTo(result);
			}
			// 查询条件:交易订单号
			String orderId = StringUtil.trim(qryParamMap.get("orderId"));
			if (!StringUtil.isBlank(orderId)) {
				c.andOrderIdLike("%" + orderId + "%");
			}
			// 查询条件:卡号
			String priAcctNo = StringUtil.trim(qryParamMap.get("priAcctNo"));
			if (!StringUtil.isBlank(priAcctNo)) {
				c.andPriAcctNoEqualTo(priAcctNo);
			}
			// 查询条件:用户ID
			String userId = StringUtil.trim(qryParamMap.get("userId"));
			if (!StringUtil.isBlank(userId)) {
				c.andUserIdEqualTo(userId);
			}
			// 查询条件:内部交易码
			String intTransCd = StringUtil.trim(qryParamMap.get("intTransCd"));
			if (!StringUtil.isBlank(intTransCd)) {
				c.andIntTransCdEqualTo(intTransCd);
			}
			// 查询条件:支付方式
			String payType = StringUtil.trim(qryParamMap.get("payType"));
			if (!StringUtil.isBlank(payType)) {
				c.andPayTypeEqualTo(payType);
			}
			// 查询条件:手机号
			String phoneNo = StringUtil.trim(qryParamMap.get("phoneNo"));
			if (!StringUtil.isBlank(phoneNo)) {
				c.andPhoneNoEqualTo(phoneNo);
			}
			// 查询条件:商户号
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			// 查询条件:ip地址
			String ip = StringUtil.trim(qryParamMap.get("ip"));
			if (!StringUtil.isBlank(ip)) {
				c.andIpEqualTo(ip);
			}
			// 查询条件:起始日期
			String startExtTransDt = StringUtil.trim(qryParamMap.get("startExtTransDt"));
			if (!StringUtil.isBlank(startExtTransDt)) {
				c.andExtTransDtGreaterThanOrEqualTo(startExtTransDt);
			}
			// 查询条件:结束日期
			String endExtTransDt = StringUtil.trim(qryParamMap.get("endExtTransDt"));
			if (!StringUtil.isBlank(endExtTransDt)) {
				c.andExtTransDtLessThanOrEqualTo(endExtTransDt);
			}
			// 查询条件:交易金额
			String transAt = StringUtil.trim(qryParamMap.get("transAt"));
			if (!StringUtil.isBlank(transAt)) {
				// 转换成分
				BigDecimal at = new BigDecimal(transAt).multiply(new BigDecimal(100));
				c.andTransAtEqualTo(at.longValue());
			}
			
			// 查询条件： 监控的时间范围
			String monitorTm = StringUtil.trim(qryParamMap.get("monitorTm"));
			if (!StringUtil.isBlank(monitorTm)) {
				// 页面录入是分钟，换算为毫秒
				long l = (long) Integer.parseInt(monitorTm) * 60 * 1000;
				Date now = new Date();
				Date mTm = new Date(now.getTime() - l);
				c.andRecUpdTsGreaterThanOrEqualTo(mTm);
			}
		}
		// 排序字段
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}
	
	private RiskTransLogMapper getMapper() {
		return this.getMapper(RiskTransLogMapper.class);
	}
}
