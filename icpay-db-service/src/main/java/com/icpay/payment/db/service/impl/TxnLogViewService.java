package com.icpay.payment.db.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.Converter;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.mapper.ExtMapper.TxnLogViewExtMapper;
import com.icpay.payment.db.dao.mybatis.model.TxnLogView;
import com.icpay.payment.db.dao.mybatis.model.TxnLogViewExample;
import com.icpay.payment.db.dao.mybatis.model.TxnLogViewExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.modelExt.ChnlMchntDaily;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TxnLogProfitQuery;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TxnLogSummary;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TxnLogSysSettleProfit;
import com.icpay.payment.db.service.ITxnLogViewService;

@Service("txnLogViewService")
public class TxnLogViewService extends BaseService implements ITxnLogViewService {
	
	private TxnLogViewExtMapper dao=null;
	protected TxnLogViewExtMapper dao() {
		if (dao==null)
			dao=getMapper(TxnLogViewExtMapper.class);
		return dao;
	}
	
	protected String getMon(String mon, Map<String, String> qryParamMap) {
		return getMon(
				mon, 
				qryParamMap.get("startDate"),
				qryParamMap.get("endDate"),
				qryParamMap.get("extTransDt"),
				qryParamMap.get("recUpdTs"),
				qryParamMap.get("recCrtTs"),
				qryParamMap.get("transSeqId"),
				null
				);
	}
	
	@Override
	public Pager<TxnLogView> selectByPage(int pageNum, int pageSize, String mon, Map<String, String> qryParamMap) {
		mon = getMon(mon,qryParamMap);
		Pager<TxnLogView> pager = buildPager(pageNum, pageSize, qryParamMap);
		TxnLogViewExample example = this.buildQryExample(qryParamMap);
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		example.setDistinct(true);
		pager.setTotal(dao().countByExample(example, mon));
		pager.setResultList(dao().selectByPage(example, mon));		
		return pager;
	}
	
	@Override
	public Pager<ChnlMchntDaily> selectChnlMchntDailyByPage(int pageNum, int pageSize, String mon, Map<String, String> qryParamMap) {
		Pager<ChnlMchntDaily> pager = buildPager(pageNum, pageSize, qryParamMap);
		String recUpdTs = qryParamMap.get("recUpdTs");
		Date sTime = convertStartDateTime(recUpdTs, "");
		Date eTime = convertEndDateTime(recUpdTs, "");
		String startDate = DateUtil.formatDate(sTime, DateUtil.DATE_FORMAT_19);
		String endDate = DateUtil.formatDate(eTime, DateUtil.DATE_FORMAT_19);
		TxnLogViewExample example = new TxnLogViewExample();
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		example.setDistinct(true);
		pager.setTotal(dao().countChnlMchntDaily(startDate, endDate, mon));
		pager.setResultList(dao().selectChnlMchntDaily(example, startDate, endDate, mon));
		return pager;
	}
	
	@Override
	public List<ChnlMchntDaily> selectChnlMchntDaily(String mon, Map<String, String> qryParamMap) {
		String recUpdTs = qryParamMap.get("recUpdTs");
		Date sTime = convertStartDateTime(recUpdTs, "");
		Date eTime = convertEndDateTime(recUpdTs, "");
		String startDate = DateUtil.formatDate(sTime, DateUtil.DATE_FORMAT_19);
		String endDate = DateUtil.formatDate(eTime, DateUtil.DATE_FORMAT_19);
		TxnLogViewExample example = new TxnLogViewExample();
		return dao().selectChnlMchntDaily(example, startDate, endDate, mon);
	}
	
	@Override
	public Pager<TxnLogView> selectAllOrderId(int pageNum, int pageSize, String mon, Map<String, String> qryParamMap) {
		Pager<TxnLogView> pager = buildPager(pageNum, pageSize, qryParamMap);
		TxnLogViewExample example = this.buildQryExample(qryParamMap);
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		example.setDistinct(true);
		String allOrderId = StringUtil.trim(qryParamMap.get("allOrderId"));
		
		//????????????
        Calendar rightNow1 = Calendar.getInstance();
        rightNow1.setTime(new Date());//??????????????? Date ????????? Calendar ???????????? 
        rightNow1.add(Calendar.MONTH, -1);// ?????????1??????
        Date dt1 = rightNow1.getTime();//????????????????????? Calendar ???????????? Date ?????????
        String today1 = DateUtil.date8(dt1);
        String mon1 = getMonth(today1);
        
        //????????????
        Calendar rightNow2 = Calendar.getInstance();
        rightNow2.setTime(new Date());//??????????????? Date ????????? Calendar ???????????? 
        rightNow2.add(Calendar.MONTH, -2);// ?????????2??????
        Date dt2 = rightNow2.getTime();//????????????????????? Calendar ???????????? Date ?????????
        String today2 = DateUtil.date8(dt2);
        String mon2 = getMonth(today2);
		
		pager.setTotal(dao().countAllOrderId(example, mon, mon1, mon2, allOrderId));
		pager.setResultList(dao().selectAllOrderId(example, mon, mon1, mon2, allOrderId));		
		return pager;
	}

	@Override
	public List<TxnLogView> select(String mon, Map<String, String> qryParamMap) {
		//AssertUtil.notMonStr(mon);
		mon = getMon(mon,qryParamMap);
		TxnLogViewExample example = this.buildQryExample(qryParamMap);
		return dao().selectByExample(example, mon);
	}
	
	@Override
	public List<TxnLogProfitQuery> selectForAgentProfitQueryCreateTaske(String mon, Map<String, Object> qryParamMap) {
		String intTransCd = (qryParamMap.containsKey("int_trans_cd")) ? qryParamMap.get("int_trans_cd").toString() : "";
		String mchntCd = (qryParamMap.containsKey("mchnt_cd")) ? qryParamMap.get("mchnt_cd").toString() : "";
		String chnlId = (qryParamMap.containsKey("chnl_id")) ? qryParamMap.get("chnl_id").toString() : "";
		String chnlMchntCd = (qryParamMap.containsKey("chnl_mchnt_cd")) ? qryParamMap.get("chnl_mchnt_cd").toString() : "";
		String beginDate = (qryParamMap.containsKey("begin_date")) ? qryParamMap.get("begin_date").toString() : "";
		String endDate = (qryParamMap.containsKey("end_date")) ? qryParamMap.get("end_date").toString() : "";
		return dao().selectForAgentProfitQueryCreateTaske(intTransCd, mchntCd, chnlId, chnlMchntCd, beginDate, endDate, mon);
	}

	@Override
	public TxnLogView selectByPrimaryKey(String transSeqId, String mon) {
		//AssertUtil.notMonStr(mon);
		mon = getMon(mon,transSeqId);
		TxnLogViewExample example = new TxnLogViewExample();
		example.createCriteria().andTransSeqIdEqualTo(transSeqId);
		List<TxnLogView> list= dao().selectByExample(example, mon);
		if ((list==null)||(list.size()==0))
			return null;
		return list.get(0);
	}

	@Override
	public Long count(String mon, Map<String, String> qryParamMap) {
		mon = getMon(mon,qryParamMap);
		AssertUtil.notMonStr(mon);
		TxnLogViewExample example = this.buildQryExample(qryParamMap);
		return dao().countByExample(example, mon);
	}

	@Override
	public TxnLogSummary selectSummary(String mon, Map<String, String> qryParamMap) {
		//AssertUtil.notMonStr(mon);
		mon = getMon(mon,qryParamMap);
		TxnLogViewExample example = this.buildQryExample(qryParamMap);
		
		TxnLogSummary txnLogSummary=dao().selectSummary(example, mon);
		String sumTransFeeDelta=dao().selecttransFeeDelta(example, mon);
		if(sumTransFeeDelta!=null && !"".equals(sumTransFeeDelta)){
			txnLogSummary.setSumTransFeeDelta(Long.valueOf(sumTransFeeDelta));
		}else{
			txnLogSummary.setSumTransFeeDelta(Long.valueOf(0));
		}
		return txnLogSummary;
	}
	
	@Override
	public List<TxnLogSysSettleProfit> selectSysSettleProfitSummary(String mon, String startDate, String endDate) {
		return dao().selectSysSettleProfitSummary(mon, startDate, endDate);
	}
	
	@Override
	public String sumTransFeeDelta(String mon, Map<String, String> qryParamMap) {
		TxnLogViewExample example = this.buildQryExample(qryParamMap);
		return dao().selecttransFeeDelta(example, mon);
	}
	
	@Override
	protected TxnLogViewExample buildQryExample(Map<String, String> qryParamMap) {
		TxnLogViewExample example= new TxnLogViewExample();
		if (qryParamMap==null) return example;
		if (qryParamMap.isEmpty()) return example;
		
		Criteria c= buildDefaultCriteria(example, qryParamMap);
		
		String defaultOrderBy = "rec_upd_ts DESC";
		//????????????
		String startDate = StringUtil.trim(qryParamMap.get("startDate"));
		String startTime = StringUtil.trim(qryParamMap.get("startTime"));
		String endDate = StringUtil.trim(qryParamMap.get("endDate"));
		String endTime = StringUtil.trim(qryParamMap.get("endTime"));
		String timeQryMethod = StringUtil.trim(qryParamMap.get("timeQryMethod"));
		if ((!StringUtil.isBlank(startDate)) || (!StringUtil.isBlank(endDate))) {
			if (StringUtil.isBlank(startDate)) startDate = endDate;
			if (StringUtil.isBlank(endDate)) endDate = startDate; //Converter.dateToString(tomorrow);
			Date date1 = this.convertStartDateTime(startDate, startTime);
			Date date2 = this.convertEndDateTime(endDate, endTime);
			if (BmEnums.TimeQryMethod.ByOrderTime.getCode().equals(timeQryMethod)) {
				c.andExtTransDtGreaterThanOrEqualTo(startDate);
				c.andExtTransDtLessThanOrEqualTo(endDate);
				c.andExtTransTmGreaterThanOrEqualTo(Converter.timeToString(date1));
				c.andExtTransTmLessThanOrEqualTo(Converter.timeToString(date2));
				defaultOrderBy = "ext_trans_dt DESC, ext_trans_tm DESC";
			}
			else if (BmEnums.TimeQryMethod.ByCrtateTs.getCode().equals(timeQryMethod)) {
				c.andRecCrtTsGreaterThanOrEqualTo(date1);
				c.andRecCrtTsLessThanOrEqualTo(date2);
				defaultOrderBy = "rec_crt_ts DESC";
			}
			else {
				c.andRecUpdTsGreaterThanOrEqualTo(date1);
				c.andRecUpdTsLessThanOrEqualTo(date2);
				defaultOrderBy = "rec_upd_ts DESC";				
			}
		}
		
		//???????????????
		String userId=qryParamMap.get("userId");
		  
		  if(userId!=null && !"".equals(userId)){
		  c.andUserIdEqualTo(userId);
		  }
		  
		  
		
		// ??????????????? ?????????????????????
		String monitorTm = StringUtil.trim(qryParamMap.get("monitorTm"));
		if (!StringUtil.isBlank(monitorTm)) {
			// ???????????????????????????????????????
			long t = (long) Integer.parseInt(monitorTm) * 60 * 1000;
			Date now = new Date();
			Date mTm = new Date(now.getTime() - t);
			c.andRecUpdTsGreaterThanOrEqualTo(mTm);
		}
		
		String transAmtMin = StringUtil.trim(qryParamMap.get("transAmtMin"));
		String transAmtMax = StringUtil.trim(qryParamMap.get("transAmtMax"));
		if ((!StringUtil.isBlank(transAmtMin)) || (!StringUtil.isBlank(transAmtMax))) {
			if (StringUtil.isBlank(transAmtMin)) transAmtMin = transAmtMax;
			if (StringUtil.isBlank(transAmtMax)) transAmtMax = transAmtMin;
			c.andTransAtGreaterThanOrEqualTo(this.convertAmount(transAmtMin));
			c.andTransAtLessThanOrEqualTo(this.convertAmount(transAmtMax));
		}
		
		String transType = StringUtil.trim(qryParamMap.get("transType"));
		if (!StringUtil.isBlank(transType)) {
			if (!transType.contains(",")) {
				if (transType.length()<4)
					transType = transType+"%";
				c.andIntTransCdLike(transType);
			}
			else {
				List<String> values= Utils.newList(Utils.strSplit(transType, ","));
				c.andIntTransCdIn(values);
			}
		}
		
		String orderBy = StringUtil.trim(qryParamMap.get("orderBy"));
		if (!Utils.isEmpty(defaultOrderBy))
			example.setOrderByClause(defaultOrderBy);
		if (!Utils.isEmpty(orderBy))
			example.setOrderByClause(orderBy);
		
		//example.setOrderByClause("rec_upd_ts desc");
		return example;
	}
	
	protected Criteria buildDefaultCriteria(TxnLogViewExample example, Map<String, String> qryParamMap) {
		if (qryParamMap==null) return null;
		if (qryParamMap.isEmpty()) return null;
		
		//TxnLogViewExample example= new TxnLogViewExample();
		
		String orderBy = StringUtil.trim(qryParamMap.get("orderBy"));
		
		if (!Utils.isEmpty(orderBy))
			example.setOrderByClause(orderBy);
		
		Criteria c = example.createCriteria();
		
	    /*
	     * ??????????????????
	     * Database column : view_txn_log_01.trans_seq_id
	     *
	     * @mbg.generated
	     */
	    String transSeqId = StringUtil.trim(qryParamMap.get("transSeqId"));
	    if (!StringUtil.isBlank(transSeqId)) {
	        c.andTransSeqIdEqualTo(transSeqId);
	    }

	    /*
	     * ????????????
	     * Database column : view_txn_log_01.order_state
	     *
	     * @mbg.generated
	     */
	    String orderState = StringUtil.trim(qryParamMap.get("orderState"));
	    if (!StringUtil.isBlank(orderState)) {
	        c.andOrderStateEqualTo(orderState);
	    }

	    /*
	     * ???????????????
	     * Database column : view_txn_log_01.mchnt_cd
	     *
	     * @mbg.generated
	     */
	    String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
	    if (!StringUtil.isBlank(mchntCd)) {
	        c.andMchntCdEqualTo(mchntCd);
	    }
	    
	    String mchntCdArr = StringUtil.trim(qryParamMap.get("mchntCdArr"));
	    if (!StringUtil.isBlank(mchntCdArr)) {
	    	c.andMchntCdIn(Utils.strSplitToList(mchntCdArr, ",", true));
	    }

	    /*
	     * ???????????????
	     * Database column : view_txn_log_01.order_id
	     *
	     * @mbg.generated
	     */
	    String orderId = StringUtil.trim(qryParamMap.get("orderId"));
	    if (!StringUtil.isBlank(orderId)) {
		    if (StringUtil.isBlank(mchntCd) || (orderId.length()<4)) {
		    	c.andOrderIdEqualTo(orderId);
		    }
		    else{
		    	c.andOrderIdLike("%" + orderId + "%");
		    }
	    }

	    /*
	     * ????????????,?????? yyyyMMdd (???????????????)
	     * Database column : view_txn_log_01.ext_trans_dt
	     *
	     * @mbg.generated
	     */
	    String extTransDt = StringUtil.trim(qryParamMap.get("extTransDt"));
	    if (!StringUtil.isBlank(extTransDt)) {
	        c.andExtTransDtEqualTo(extTransDt);
	    }

	    /*
	     * ????????????,?????? HHmmss (???????????????)
	     * Database column : view_txn_log_01.ext_trans_tm
	     *
	     * @mbg.generated
	     */
	    String extTransTm = StringUtil.trim(qryParamMap.get("extTransTm"));
	    if (!StringUtil.isBlank(extTransTm)) {
	        c.andExtTransTmEqualTo(extTransTm);
	    }

	    /*
	     * ????????????(4??????)
	     * Database column : view_txn_log_01.int_trans_cd
	     *
	     * @mbg.generated
	     */
	    String intTransCd = StringUtil.trim(qryParamMap.get("intTransCd"));
	    if (!StringUtil.isBlank(intTransCd)) {
	        c.andIntTransCdEqualTo(intTransCd);
	    }

	    /*
	     * ??????
	     * Database column : view_txn_log_01.curr_cd
	     *
	     * @mbg.generated
	     */
	    String currCd = StringUtil.trim(qryParamMap.get("currCd"));
	    if (!StringUtil.isBlank(currCd)) {
	        c.andCurrCdEqualTo(currCd);
	    }
	    
	    /*
	     * ????????????
	     * Database column : view_txn_log_01.trans_at
	     *
	     * @mbg.generated
	     */
	    String transAt = StringUtil.trim(qryParamMap.get("transAt"));
	    if (!StringUtil.isBlank(transAt)) {
	        c.andTransAtEqualTo(convertAmount(transAt));
	    }

	    /*
	     * ???????????????
	     * Database column : view_txn_log_01.trans_fee
	     *
	     * @mbg.generated
	     */
	    String transFee = StringUtil.trim(qryParamMap.get("transFee"));
	    if (!StringUtil.isBlank(transFee)) {
	        c.andTransFeeEqualTo(convertAmount(transFee));
	    }

	    /*
	     * ?????????????????????
	     * Database column : view_txn_log_01.trans_fee_chnl
	     *
	     * @mbg.generated
	     */
	    String transFeeChnl = StringUtil.trim(qryParamMap.get("transFeeChnl"));
	    if (!StringUtil.isBlank(transFeeChnl)) {
	        c.andTransFeeChnlEqualTo(convertAmount(transFeeChnl));
	    }

	    /*
	     * Database column : view_txn_log_01.trans_fee_delta
	     *
	     * @mbg.generated
	     */
	    String transFeeDelta = StringUtil.trim(qryParamMap.get("transFeeDelta"));
	    if (!StringUtil.isBlank(transFeeDelta)) {
	        c.andTransFeeDeltaLessThan(convertAmount(transFeeDelta));//20210412 ?????????????????????????????????
	    }

	    /*
	     * T0????????????(??????'0.95','0.9','1.0'???)
	     * Database column : view_txn_log_01.tx_t0_percent
	     *
	     * @mbg.generated
	     */
	    String txT0Percent = StringUtil.trim(qryParamMap.get("txT0Percent"));
	    if (!StringUtil.isBlank(txT0Percent)) {
	        c.andTxT0PercentEqualTo(txT0Percent);
	    }

	    /*
	     * ????????????T0????????????(??????'0.95','0.9','1.0'???)
	     * Database column : view_txn_log_01.tx_t0_percent_chnl
	     *
	     * @mbg.generated
	     */
	    String txT0PercentChnl = StringUtil.trim(qryParamMap.get("txT0PercentChnl"));
	    if (!StringUtil.isBlank(txT0PercentChnl)) {
	        c.andTxT0PercentChnlEqualTo(txT0PercentChnl);
	    }

	    /*
	     * ????????????
	     * Database column : view_txn_log_01.trans_chnl
	     *
	     * @mbg.generated
	     */
	    String transChnl = StringUtil.trim(qryParamMap.get("transChnl"));
	    if (!StringUtil.isBlank(transChnl)) {
	        c.andTransChnlEqualTo(transChnl);
	    }

	    /*
	     * ???????????????
	     * Database column : view_txn_log_01.chnl_mchnt_cd
	     *
	     * @mbg.generated
	     */
	    String chnlMchntCd = StringUtil.trim(qryParamMap.get("chnlMchntCd"));
	    if (!StringUtil.isBlank(chnlMchntCd)) {
	        c.andChnlMchntCdEqualTo(chnlMchntCd);
	    }

	    /*
	     * ???????????????
	     * Database column : view_txn_log_01.chnl_order_id
	     *
	     * @mbg.generated
	     */
	    String chnlOrderId = StringUtil.trim(qryParamMap.get("chnlOrderId"));
	    if (!StringUtil.isBlank(chnlOrderId)) {
	        c.andChnlOrderIdEqualTo(chnlOrderId);
	    }

	    /*
	     * ??????????????????(yyyyMMddhhmmss)
	     * Database column : view_txn_log_01.chnl_order_time
	     *
	     * @mbg.generated
	     */
	    String chnlOrderTime = StringUtil.trim(qryParamMap.get("chnlOrderTime"));
	    if (!StringUtil.isBlank(chnlOrderTime)) {
	        c.andChnlOrderTimeEqualTo(chnlOrderTime);
	    }

	    /*
	     * ??????????????????
	     * Database column : view_txn_log_01.chnl_trans_id
	     *
	     * @mbg.generated
	     */
	    String chnlTransId = StringUtil.trim(qryParamMap.get("chnlTransId"));
	    if (!StringUtil.isBlank(chnlTransId)) {
	        c.andChnlTransIdEqualTo(chnlTransId);
	    }

	    /*
	     * ??????????????????
	     * Database column : view_txn_log_01.rsp_cd
	     *
	     * @mbg.generated
	     */
	    String rspCd = StringUtil.trim(qryParamMap.get("rspCd"));
	    if (!StringUtil.isBlank(rspCd)) {
	        c.andRspCdEqualTo(rspCd);
	    }

	    /*
	     * ????????????(??????)
	     * Database column : view_txn_log_01.err_msg
	     *
	     * @mbg.generated
	     */
	    String errMsg = StringUtil.trim(qryParamMap.get("errMsg"));
	    if (!StringUtil.isBlank(errMsg)) {
	        c.andErrMsgEqualTo(errMsg);
	    }

	    /*
	     * ????????????(?????????????????????)???????????? com.icpay.payment.common.constants.Constant.TXNS_TATUS
	     * Database column : view_txn_log_01.txn_state
	     *
	     * @mbg.generated
	     */
	    String txnState = StringUtil.trim(qryParamMap.get("txnState"));
	    if (!StringUtil.isBlank(txnState)) {
	        c.andTxnStateEqualTo(txnState);
	    }

	    /*
	     * ??????????????????
	     * Database column : view_txn_log_01.txn_state_msg
	     *
	     * @mbg.generated
	     */
	    String txnStateMsg = StringUtil.trim(qryParamMap.get("txnStateMsg"));
	    if (!StringUtil.isBlank(txnStateMsg)) {
	        c.andTxnStateMsgEqualTo(txnStateMsg);
	    }

	    /*
	     * ???????????????????????????
	     * Database column : view_txn_log_01.chnl_txn_state_cd
	     *
	     * @mbg.generated
	     */
	    String chnlTxnStateCd = StringUtil.trim(qryParamMap.get("chnlTxnStateCd"));
	    if (!StringUtil.isBlank(chnlTxnStateCd)) {
	        c.andChnlTxnStateCdEqualTo(chnlTxnStateCd);
	    }

	    /*
	     * ???????????????????????????
	     * Database column : view_txn_log_01.chnl_txn_state_msg
	     *
	     * @mbg.generated
	     */
	    String chnlTxnStateMsg = StringUtil.trim(qryParamMap.get("chnlTxnStateMsg"));
	    if (!StringUtil.isBlank(chnlTxnStateMsg)) {
	        c.andChnlTxnStateMsgEqualTo(chnlTxnStateMsg);
	    }

	    /*
	     * ?????????????????????(cupsRespCd)?????????CHNL_MSG.chnlRespCd
	     * Database column : view_txn_log_01.chnl_resp_cd
	     *
	     * @mbg.generated
	     */
	    String chnlRespCd = StringUtil.trim(qryParamMap.get("chnlRespCd"));
	    if (!StringUtil.isBlank(chnlRespCd)) {
	        c.andChnlRespCdEqualTo(chnlRespCd);
	    }

	    /*
	     * ??????????????????
	     * Database column : view_txn_log_01.chnl_resp_msg
	     *
	     * @mbg.generated
	     */
	    String chnlRespMsg = StringUtil.trim(qryParamMap.get("chnlRespMsg"));
	    if (!StringUtil.isBlank(chnlRespMsg)) {
	        c.andChnlRespMsgEqualTo(chnlRespMsg);
	    }

	    /*
	     * ?????????IP
	     * Database column : view_txn_log_01.req_ip
	     *
	     * @mbg.generated
	     */
	    String reqIp = StringUtil.trim(qryParamMap.get("reqIp"));
	    if (!StringUtil.isBlank(reqIp)) {
	        c.andReqIpEqualTo(reqIp);
	    }

	    /*
	     * ??????????????? ??????=01, ??????(?????????)??????=02, ????????????=03, ????????????=04
	     * Database column : view_txn_log_01.txn_src
	     *
	     * @mbg.generated
	     */
	    String txnSrc = StringUtil.trim(qryParamMap.get("txnSrc"));
	    if (!StringUtil.isBlank(txnSrc)) {
	        c.andTxnSrcEqualTo(txnSrc);
	    }

	    /*
	     * ???????????????
	     * Database column : view_txn_log_01.acc_no
	     *
	     * @mbg.generated
	     */
	    String accNo = StringUtil.trim(qryParamMap.get("accNo"));
	    if (!StringUtil.isBlank(accNo)) {
	        c.andAccNoEqualTo(accNo);
	    }

	    /*
	     * ??????
	     * Database column : view_txn_log_01.acc_name
	     *
	     * @mbg.generated
	     */
	    String accName = StringUtil.trim(qryParamMap.get("accName"));
	    if (!StringUtil.isBlank(accName)) {
	        c.andAccNameEqualTo(accName);
	    }

	    /*
	     * ?????????(????????????)
	     * Database column : view_txn_log_01.bank_num
	     *
	     * @mbg.generated
	     */
	    String bankNum = StringUtil.trim(qryParamMap.get("bankNum"));
	    if (!StringUtil.isBlank(bankNum)) {
	        c.andBankNumEqualTo(bankNum);
	    }
	    
	    /*
	     * ????????????/??????????????? ???????????????
	     * Database column : view_txn_log_01.ex_txn_id
	     *
	     * @mbg.generated
	     */
	    String exTxnId = StringUtil.trim(qryParamMap.get("exTxnId"));
	    if (!StringUtil.isBlank(exTxnId)) {
	        c.andExTxnIdEqualTo(exTxnId);
	    }
	    
	    /*
	     * ????????????
	     * Database column : view_txn_log_01.ex_address
	     *
	     * @mbg.generated
	     */
	    String exAddress = StringUtil.trim(qryParamMap.get("exAddress"));
	    if (!StringUtil.isBlank(exAddress)) {
	        c.andExAddressEqualTo(exAddress);
	    }

	    /*
	     * Database column : view_txn_log_01.rec_crt_ts
	     *
	     * @mbg.generated
	     */
	    String recCrtTs = StringUtil.trim(qryParamMap.get("recCrtTs"));
	    if (!StringUtil.isBlank(recCrtTs)) {
			c.andRecCrtTsEqualTo(convertDateTime(recCrtTs));
	    }

	    /*
	     * Database column : view_txn_log_01.rec_upd_ts
	     *
	     * @mbg.generated
	     */
	    String recUpdTs = StringUtil.trim(qryParamMap.get("recUpdTs"));
	    if (!StringUtil.isBlank(recUpdTs)) {
			c.andRecUpdTsEqualTo(convertDateTime(recUpdTs));
	    }
	    
	    /*
	     * ??????
	     * Database column : view_txn_log_01.site_id
	     *
	     * @mbg.generated
	     */
	    String siteId = StringUtil.trim(qryParamMap.get("siteId"));
	    if (!StringUtil.isBlank(siteId)) {
	        c.andSiteIdEqualTo(siteId);
	    }
	    
		return c;
	}

}
