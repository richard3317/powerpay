package com.icpay.payment.db.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.TransLogMapper;
import com.icpay.payment.db.dao.mybatis.mapper.ExtMapper.TransLogExt2Mapper;
import com.icpay.payment.db.dao.mybatis.mapper.ExtMapper.TransLogExtMapper;
import com.icpay.payment.db.dao.mybatis.model.TransLog;
import com.icpay.payment.db.dao.mybatis.model.TransLogExample;
import com.icpay.payment.db.dao.mybatis.model.TransLogExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TransLogMapping;
import com.icpay.payment.db.service.ITransLogService;

@Service("transLogService")
public class TransLogService extends BaseService implements ITransLogService {
	
	private static final Logger logger = Logger.getLogger(TransLogService.class);

	@Override
	public List<TransLog> select(String mon, Map<String, String> qryParamMap) {
		// 为了防止SQL注入，对MON进行格式校验
		AssertUtil.notMonStr(mon);
		TransLogExample example = this.buildQryExample(qryParamMap);
		return this.getMapper(TransLogExt2Mapper.class).selectByExample(example, mon);
	}
	
	public BigDecimal checkAmount(Map<String, String> qryParamMap) {
		//取得当月份
		String today = DateUtil.now8();
		String thisMon = today.substring(4, 6);
		//取得前月份
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); // 設置為當前時間
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 設置為上一個月 +為后一個月 0 為本月
		date = calendar.getTime();
		String lastMon = dateFormat.format(date).substring(4, 6);
		
		TransLogExample example = new TransLogExample();
		Criteria c = example.createCriteria();
		
		//交易类型
		String intTransCd = StringUtil.trim(qryParamMap.get("intTransCd"));
		if (!StringUtil.isBlank(intTransCd)) {
			c.andIntTransCdLike(intTransCd);
		}
		
		// 查询条件:商户号
		String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
		if (!StringUtil.isBlank(mchntCd)) {
			c.andMchntCdEqualTo(mchntCd);
		}
		
		// 查询条件:渠道号
		String transChnl = StringUtil.trim(qryParamMap.get("transChnl"));
		if (!StringUtil.isBlank(transChnl)) {
			c.andTransChnlEqualTo(transChnl);
		}
		
		// 查询条件:渠道商户号
		String chnlMchntCd = StringUtil.trim(qryParamMap.get("chnlMchntCd"));
		if (!StringUtil.isBlank(chnlMchntCd)) {
			c.andChnlMchntCdEqualTo(chnlMchntCd);
		}
		
		//交易状态
		String txnState = StringUtil.trim(qryParamMap.get("txnState"));
		if (!StringUtil.isBlank(txnState)) {
			c.andTxnStateEqualTo(txnState);
		}
		//當月份的状态处理中代付金额
		List<TransLog> transLogDatas = this.getMapper(TransLogExt2Mapper.class).selectByExample(example, thisMon);
		
		//查询并加总此月份的状态处理中的代付金额
		long transAt = 0L;
		for(TransLog transLogData:transLogDatas) {
			 transAt += transLogData.getTransAt();
		}
		//上月份的状态处理中代付金额
		List<TransLog> transLogDatasLastMon = this.getMapper(TransLogExt2Mapper.class).selectByExample(example, lastMon);
		for(TransLog transLogDataLastMon:transLogDatasLastMon) {
			 transAt += transLogDataLastMon.getTransAt();
		}
		
		return  new BigDecimal(transAt);
	}
	
	@Override
	public List<TransLogMapping> selectTransLogMapping(String mon, Map<String, String> qryParamMap){
		// 为了防止SQL注入，对MON进行格式校验
		AssertUtil.notMonStr(mon);
		TransLogExample example = this.buildQryExample(qryParamMap);
		TransLogExtMapper mapper2=this.getMapper(TransLogExtMapper.class);
		return mapper2.selectTransLogMappingByExample(example, mon);
	}
	
	
	@Override
	public Pager<TransLog> selectByPage(int pageNum, int pageSize,
			String mon, Map<String, String> qryParamMap) {
		// 为了防止SQL注入，对MON进行格式校验
		AssertUtil.notMonStr(mon);
		logger.info("分页查询交易信息开始");
		TransLogExample example = this.getQryExample(qryParamMap);
		TransLogExt2Mapper mapper = this.getMapper(TransLogExt2Mapper.class);
		Pager<TransLog> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example, mon));
		pager.setResultList(mapper.selectByPage(example, mon));
		
		logger.info("分页查询交易信息完成");
		return pager;
	}

	@Override
	public Pager<TransLogMapping> selectTransLogMappingByPage(int pageNum, int pageSize,
			String mon, Map<String, String> qryParamMap) {
		// 为了防止SQL注入，对MON进行格式校验
		AssertUtil.notMonStr(mon);
		logger.info("分页查询交易信息开始");
		TransLogExample example = this.getQryExample(qryParamMap);
		TransLogMapper mapper = getMapper();
		TransLogExt2Mapper mapper2=this.getMapper(TransLogExt2Mapper.class);
		Pager<TransLogMapping> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper2.countByExample(example, mon));
		pager.setResultList(mapper2.selectTransLogMappingByPage(example, mon));
		
		logger.info("分页查询交易信息完成");
		return pager;
	}

	@Override
	public TransLog selectByPrimaryKey(String transSeqId, String mon) {
		// 为了防止SQL注入，对MON进行格式校验
		AssertUtil.notMonStr(mon);
		return this.getMapper(TransLogExt2Mapper.class).selectByPrimaryKey(transSeqId, mon);
	}
	
	@Override
	protected TransLogExample buildQryExample(Map<String, String> qryParamMap) {
		TransLogExample example = new TransLogExample();
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
				//c.andOrderIdLike("%" + orderId + "%");
				c.andOrderIdEqualTo(orderId);
			}
			// 查询条件：用户ID
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
			String mobile = StringUtil.trim(qryParamMap.get("mobile"));
			if (!StringUtil.isBlank(mobile)) {
				c.andMobileEqualTo(mobile);
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
			// 查询条件:交易金额
			String transAt = StringUtil.trim(qryParamMap.get("transAt"));
			if (!StringUtil.isBlank(transAt)) {
				// 转换成分
				BigDecimal at = new BigDecimal(transAt).multiply(new BigDecimal(100));
				c.andTransAtEqualTo(at.longValue());
			}
			
			String startDt = StringUtil.trim(qryParamMap.get("startDt"));
			if (!StringUtil.isBlank(startDt)) {
				c.andRecCrtTsGreaterThanOrEqualTo(DateUtil.str8ToDate(startDt));
			}
			
			String endDt = StringUtil.trim(qryParamMap.get("endDt"));
			if (!StringUtil.isBlank(endDt)) {
				c.andRecCrtTsLessThan(DateUtil.str8ToDate(endDt));
			}
			
			String termSn = StringUtil.trim(qryParamMap.get("termSn"));
			if (!StringUtil.isBlank(termSn)) {
				c.andTermSnEqualTo(termSn);
			}
			
			String rspCd = StringUtil.trim(qryParamMap.get("rspCd"));
			if (!StringUtil.isBlank(rspCd)) {
				c.andRspCdLike("%" + rspCd + "%");
			}
			
			String transState = StringUtil.trim(qryParamMap.get("transState"));
			if (!StringUtil.isBlank(transState)) {
//				if ("1".equals(transState)) {
//					c.andRspCdEqualTo(TxnEnums.RspCd._0000000.getCode());
//				} else if ("2".equals(transState)) {
//					c.andRspCdNotEqualTo(TxnEnums.RspCd._0000000.getCode());
//					c.andRspCdNotEqualTo("");
//				} else if ("3".equals(transState)) {
//					c.andRspCdEqualTo("");
//				}
				c.andTxnStateEqualTo(transState);
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
			
			String chnlId = StringUtil.trimStr(qryParamMap.get("chnlId"));
			if (!StringUtil.isBlank(chnlId)) {
				c.andTransChnlEqualTo(chnlId);
			}
			
			String minTransAt = StringUtil.trim(qryParamMap.get("minTransAt"));
			if (!StringUtil.isBlank(minTransAt)) {
				// 转换成分
				BigDecimal at = new BigDecimal(minTransAt).multiply(new BigDecimal(100));
				c.andTransAtGreaterThanOrEqualTo(at.longValue());
			}
			
			String maxTransAt = StringUtil.trim(qryParamMap.get("maxTransAt"));
			if (!StringUtil.isBlank(maxTransAt)) {
				// 转换成分
				BigDecimal at = new BigDecimal(maxTransAt).multiply(new BigDecimal(100));
				c.andTransAtLessThanOrEqualTo(at.longValue());
			}
			
			String cardNo = StringUtil.trim(qryParamMap.get("cardNo"));
			if (!StringUtil.isBlank(cardNo)) {
				//c.andAccNoLike("%" + cardNo);
				c.andAccNoEqualTo(cardNo);
			}
			
			String agentCd = StringUtil.trim(qryParamMap.get("agentCd"));
			if (!StringUtil.isBlank(agentCd)) {
				c.andAgentCdEqualTo(agentCd);
			}
			String chnlMchntCd = StringUtil.trim(qryParamMap.get("chnlMchntCd"));
			if (!StringUtil.isBlank(chnlMchntCd)) {
				c.andChnlMchntCdEqualTo(chnlMchntCd);
			}
			String chnlTermCd = StringUtil.trim(qryParamMap.get("chnlTermCd"));
			if (!StringUtil.isBlank(chnlTermCd)) {
				c.andChnlTermCdEqualTo(chnlTermCd);
			}
			//渠道凭证号
			String transXid = StringUtil.trim(qryParamMap.get("transXid"));
			if (!StringUtil.isBlank(transXid)) {
				c.andTransXidEqualTo(transXid);
			}
			
			//交易状态
			String txnState = StringUtil.trim(qryParamMap.get("txnState"));
			if (!StringUtil.isBlank(txnState)) {
				c.andTxnStateEqualTo(txnState);
			}
			
			//平台订单号
			String chnlOrderId = StringUtil.trim(qryParamMap.get("chnlOrderId"));
			if (!StringUtil.isBlank(chnlOrderId)) {
				c.andChnlOrderIdEqualTo(chnlOrderId);
			}
			
//			//卡号
//			String accNo = StringUtil.trim(qryParamMap.get("accNo"));
//			if (!StringUtil.isBlank(accNo)) {
//				c.andAccNoEqualTo(accNo);
//			}
//			
//			//户名
//			String accName = StringUtil.trim(qryParamMap.get("accName"));
//			if (!StringUtil.isBlank(accName)) {
//				c.andAccNameEqualTo(accName);
//			}
			
			String accName = StringUtil.trim(qryParamMap.get("accName"));
			if (!StringUtil.isBlank(accName)) {
				//c.andAccNameLike("%" + accName + "%");
				c.andAccNameEqualTo(accName);
			}
			
			String accNo = StringUtil.trim(qryParamMap.get("accNo"));
			if (!StringUtil.isBlank(accNo)) {
				//c.andAccNoLike("%" + accNo + "%");
				c.andAccNoEqualTo(accNo);
			}
			
			//订单日期
			String extTransDt = StringUtil.trim(qryParamMap.get("extTransDt"));
			if (!StringUtil.isBlank(extTransDt)) {
				c.andExtTransDtEqualTo(extTransDt);
			}
			
			String ifPay=qryParamMap.get("ifPay");
			List<String> list = new ArrayList<>();
			list.add("5210");
			list.add("5220");
			list.add("5250");
			if("1".equals(ifPay)){//交易
				c.andIntTransCdNotIn(list);
			}else if("0".equals(ifPay)){//代付
				c.andIntTransCdIn(list);
			}
			
			// 查询条件：幣別
			String currCd = StringUtil.trim(qryParamMap.get("currCd"));
			if (!StringUtil.isBlank(currCd)) {
				c.andCurrCdEqualTo(currCd);
			}
		}
		// 排序字段
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}

	private TransLogMapper getMapper() {
		return this.getMapper(TransLogMapper.class);
	}

	@Override
	public TransLog countTransAt(String mon, Map<String, String> qryParamMap) {
		AssertUtil.notMonStr(mon);
		logger.info("分页查询交易信息开始");
		TransLogExample example = this.getQryExample(qryParamMap);
		TransLogExt2Mapper mapper = this.getMapper(TransLogExt2Mapper.class);
		TransLog cunt = mapper.countTranAt(example, mon);
		return cunt;
	}

	@Override
	public void updateTxnState(String mon,TransLog log) {
		AssertUtil.notMonStr(mon);
		TransLogExt2Mapper mapper = this.getMapper(TransLogExt2Mapper.class);
		mapper.updateTxnState(log, mon);
	}

	@Override
	public void updateState(String mon,TransLog log) {
		AssertUtil.notMonStr(mon);
		TransLogExt2Mapper mapper = this.getMapper(TransLogExt2Mapper.class);
		mapper.updateState(log, mon);
	}
	
	@Override
	public int updateTxnTags(String transSeqId, String mon, String tags) {
		TransLogExt2Mapper dao = this.getMapper(TransLogExt2Mapper.class);
		TransLog rec = dao.selectByPrimaryKey(transSeqId, mon);
		rec.setTags(tags);
		return dao.updateTxnTags(transSeqId, mon, tags);
	}
}
