package com.icpay.payment.db.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.WithdrawLogMapper;
import com.icpay.payment.db.dao.mybatis.mapper.ExtMapper.TransLogExtMapper;
import com.icpay.payment.db.dao.mybatis.mapper.ExtMapper.WithdrawLogExtMapper;
import com.icpay.payment.db.dao.mybatis.model.WithdrawLog;
import com.icpay.payment.db.dao.mybatis.model.WithdrawLogExample;
import com.icpay.payment.db.dao.mybatis.model.WithdrawLogExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.modelExt.WithdrawLogMapping;
import com.icpay.payment.db.service.IWithdrawLogService;


@Service("withdrawLogService")
public class WithdrawLogService extends BaseService implements IWithdrawLogService {
	
	private static final Logger logger = Logger.getLogger(WithdrawLogService.class);

	@Override
	public List<WithdrawLog> select(String mon, Map<String, String> qryParamMap) {
		// 为了防止SQL注入，对MON进行格式校验
		AssertUtil.notMonStr(mon);
		WithdrawLogExample example = this.buildQryExample(qryParamMap);
		return this.getMapper(WithdrawLogExtMapper.class).selectByExample(example, mon);
	}
	
	
	@Override
	public List<WithdrawLogMapping> selectWithdrawLog(String mon, Map<String, String> qryParamMap) {
		// 为了防止SQL注入，对MON进行格式校验
		AssertUtil.notMonStr(mon);
		WithdrawLogExample example = this.buildQryExample(qryParamMap);
		TransLogExtMapper mapper2=this.getMapper(TransLogExtMapper.class);
		return mapper2.selectWithdrawLogMappingByExample(example, mon);
	}
	
	
	@Override
	public Pager<WithdrawLog> selectByPage(int pageNum, int pageSize,
			String mon, Map<String, String> qryParamMap) {
		// 为了防止SQL注入，对MON进行格式校验
		AssertUtil.notMonStr(mon);
		logger.info("分页查询取现交易信息开始");
		WithdrawLogExample example = this.getQryExample(qryParamMap);
		WithdrawLogExtMapper mapper = this.getMapper(WithdrawLogExtMapper.class);
		Pager<WithdrawLog> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example, mon));
		pager.setResultList(mapper.selectByPage(example, mon));
		
		logger.info("分页查询取现交易信息完成");
		return pager;
	}

	@Override
	public Pager<WithdrawLogMapping> selectWithdrawLogMappingByPage(int pageNum, int pageSize,
			String mon, Map<String, String> qryParamMap) {
		// 为了防止SQL注入，对MON进行格式校验
		AssertUtil.notMonStr(mon);
		logger.info("分页查询取现交易信息开始");
		WithdrawLogExample example = this.getQryExample(qryParamMap);
		WithdrawLogExtMapper mapper = this.getMapper(WithdrawLogExtMapper.class);
		TransLogExtMapper mapper2=this.getMapper(TransLogExtMapper.class);
		Pager<WithdrawLogMapping> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example, mon));
		pager.setResultList(mapper2.selectWithdrawLogMappingByPage(example, mon));
		
		logger.info("分页查询取现交易信息完成");
		return pager;
	}

	@Override
	public WithdrawLogMapping select(String orderSeqId, String mon) {
		// 为了防止SQL注入，对MON进行格式校验
		AssertUtil.notMonStr(mon);
		TransLogExtMapper mapper2=this.getMapper(TransLogExtMapper.class);
		return mapper2.selectByPrimaryKey(orderSeqId, mon);
	}
	
	@Override
	protected WithdrawLogExample buildQryExample(Map<String, String> qryParamMap) {
		WithdrawLogExample example = new WithdrawLogExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:交易流水号
			String transSeqId = StringUtil.trim(qryParamMap.get("transSeqId"));
			if (!StringUtil.isBlank(transSeqId)) {
				c.andTransSeqIdEqualTo(transSeqId);
			}
			// 查询条件:交易订单号
			String orderId = StringUtil.trim(qryParamMap.get("orderId"));
			if (!StringUtil.isBlank(orderId)) {
				c.andOrderIdLike("%" + orderId + "%");
			}
			// 查询条件：交易订单号
			String orderSeqId = StringUtil.trim(qryParamMap.get("orderSeqId"));
			if (!StringUtil.isBlank(orderSeqId)) {
				c.andOrderSeqIdEqualTo(orderSeqId);
			}
			// 查询条件:内部交易码
			String intTransCd = StringUtil.trim(qryParamMap.get("intTransCd"));
			if (!StringUtil.isBlank(intTransCd)) {
				c.andIntTransCdEqualTo(intTransCd);
			}
			// 查询条件:系统跟踪号
			String traceId = StringUtil.trim(qryParamMap.get("traceId"));
			if (!StringUtil.isBlank(traceId)) {
				c.andTraceIdEqualTo(traceId);
			}
			// 查询条件:交易状态
			String orderState = StringUtil.trim(qryParamMap.get("orderState"));
			if (!StringUtil.isBlank(orderState)) {
				c.andOrderStateEqualTo(orderState);
			}
			// 查询条件:商户号
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
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
			
			// 查询条件:订单/交易时间开始
			String startExtTransTm = StringUtil.trim(qryParamMap.get("startExtTransTm"));
			if (!StringUtil.isBlank(startExtTransTm)) {
				c.andExtTransTmLessThanOrEqualTo(startExtTransTm);
			}
			
			// 查询条件:订单/交易时间结束
			String endExtTransTm = StringUtil.trim(qryParamMap.get("endExtTransTm"));
			if (!StringUtil.isBlank(endExtTransTm)) {
				c.andExtTransTmLessThanOrEqualTo(endExtTransTm);
			}
			
			// 查询条件:前端终端号
			String termSn = StringUtil.trim(qryParamMap.get("termSn"));
			if (!StringUtil.isBlank(termSn)) {
				c.andTermSnEqualTo(termSn);
			}
			
			// 查询条件:响应码
			String rspCd = StringUtil.trim(qryParamMap.get("rspCd"));
			if (!StringUtil.isBlank(rspCd)) {
				c.andRspCdLike("%" + rspCd + "%");
			}
			
			//
			String transState = StringUtil.trim(qryParamMap.get("transState"));
			if (!StringUtil.isBlank(transState)) {
				if ("1".equals(transState)) {
					c.andRspCdEqualTo(TxnEnums.RspCd._0000000.getCode());
				} else if ("2".equals(transState)) {
					c.andRspCdNotEqualTo(TxnEnums.RspCd._0000000.getCode());
					c.andRspCdNotEqualTo("");
				} else if ("3".equals(transState)) {
					c.andRspCdEqualTo("");
				}
			}
			
			// 查询条件:渠道响应信息
			String cupsRspMsg = StringUtil.trimStr(qryParamMap.get("cupsRspMsg"));
			if (!StringUtil.isBlank(cupsRspMsg)) {
				c.andCupsRspMsgEqualTo(cupsRspMsg);
			}
			
			// 查询条件:响应信息
			String rspMsg = StringUtil.trimStr(qryParamMap.get("rspMsg"));
			if (!StringUtil.isBlank(rspMsg)) {
				c.andRspMsgEqualTo(rspMsg);
			}
			
			// 查询条件:收款人帐号
			String accNo = StringUtil.trim(qryParamMap.get("accNo"));
			if (!StringUtil.isBlank(accNo)) {
				c.andAccNoLike("%" + accNo);
			}
			
			// 查询条件:渠道响应码
			String cupsRspCd = StringUtil.trim(qryParamMap.get("cupsRspCd"));
			if (!StringUtil.isBlank(cupsRspCd)) {
				c.andCupsRspCdEqualTo(cupsRspCd);
			}
			
			// 查询条件:代付渠道商户号
			String chnlMchntCd = StringUtil.trim(qryParamMap.get("chnlMchntCd"));
			if (!StringUtil.isBlank(chnlMchntCd)) {
				c.andChnlMchntCdEqualTo(chnlMchntCd);
			}
			
			// 查询条件:代付渠道终端号
			String chnlTermCd = StringUtil.trim(qryParamMap.get("chnlTermCd"));
			if (!StringUtil.isBlank(chnlTermCd)) {
				c.andChnlTermCdEqualTo(chnlTermCd);
			}
			
			// 查询条件:代付渠道编号
			String transChnl = StringUtil.trim(qryParamMap.get("transChnl"));
			if (!StringUtil.isBlank(transChnl)) {
				c.andTransChnlEqualTo(transChnl);
			}
			
			// 查询条件:标签
			String tags = StringUtil.trim(qryParamMap.get("tags"));
			if (!StringUtil.isBlank(tags)) {
				c.andTagsEqualTo(tags);
			}
			
			// 查询条件:清算标识，0=T+0, 1=T+1
			String settleType = StringUtil.trim(qryParamMap.get("settleType"));
			if (!StringUtil.isBlank(settleType)) {
				c.andSettleTypeEqualTo(settleType);
			}
			
			// 查询条件:收款人银行联行号
			String bankNo = StringUtil.trim(qryParamMap.get("bankNo"));
			if (!StringUtil.isBlank(bankNo)) {
				c.andBankNoEqualTo(bankNo);
			}
			
			// 查询条件:收款人银行名称
			String bankName = StringUtil.trim(qryParamMap.get("bankName"));
			if (!StringUtil.isBlank(bankName)) {
				c.andBankNameEqualTo(bankName);
			}
			
			// 查询条件:收款人帐号类型，0=对私, 1=对公
			String accType = StringUtil.trim(qryParamMap.get("accType"));
			if (!StringUtil.isBlank(accType)) {
				c.andAccTypeEqualTo(accType);
			}
			
			// 查询条件:交易金额
			String transAmt = StringUtil.trim(qryParamMap.get("transAmt"));
			if (!StringUtil.isBlank(transAmt)) {
				// 转换成分
				BigDecimal at = new BigDecimal(transAmt).multiply(new BigDecimal(100));
				c.andTransAmtEqualTo(at.longValue());
			}
			
			// 查询条件:划款备注
			String transMemo = StringUtil.trim(qryParamMap.get("transMemo"));
			if (!StringUtil.isBlank(transMemo)) {
				c.andTransMemoEqualTo(transMemo);
			}
			
			// 查询条件:清算日期
			String settleDt = StringUtil.trim(qryParamMap.get("settleDt"));
			if (!StringUtil.isBlank(settleDt)) {
				c.andSettleDtGreaterThanOrEqualTo(settleDt);
			}
			
			// 查询条件:清算时间
			String settleTm = StringUtil.trim(qryParamMap.get("settleTm"));
			if (!StringUtil.isBlank(settleTm)) {
				c.andSettleTmGreaterThanOrEqualTo(settleTm);
			}
			
			// 查询条件:清算金额
			String settleAmt = StringUtil.trim(qryParamMap.get("settleAmt"));
			if (!StringUtil.isBlank(settleAmt)) {
				// 转换成分
				BigDecimal at = new BigDecimal(settleAmt).multiply(new BigDecimal(100));
				c.andSettleAmtEqualTo(at.longValue());
			}
			
			// 查询条件:清算币别
			String settleCurrency = StringUtil.trim(qryParamMap.get("settleCurrency"));
			if (!StringUtil.isBlank(settleCurrency)) {
				c.andSettleCurrencyEqualTo(settleCurrency);
			}
			
			// 查询条件:原始消费交易订单号
			String orgOrderSeqId = StringUtil.trim(qryParamMap.get("orgOrderSeqId"));
			if (!StringUtil.isBlank(orgOrderSeqId)) {
				c.andOrgOrderSeqIdEqualTo(orgOrderSeqId);
			}
			
			// 查询条件:原始消费交易流水ID
			String orgTransSeqId = StringUtil.trim(qryParamMap.get("orgTransSeqId"));
			if (!StringUtil.isBlank(orgTransSeqId)) {
				c.andOrgTransSeqIdEqualTo(orgTransSeqId);
			}
		}
		// 排序字段
		example.setOrderByClause("ext_trans_dt,ext_trans_tm desc");
		return example;
	}

	private WithdrawLogMapper getMapper() {
		return this.getMapper(WithdrawLogMapper.class);
	}


	@Override
	public WithdrawLog selectByPrimaryKey(String orderSeqId, String mon) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
