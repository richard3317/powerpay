package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.mapper.ExtMapper.AgentMchntTxnExtMapper;
import com.icpay.payment.db.dao.mybatis.model.AgentMchntTxn;
import com.icpay.payment.db.dao.mybatis.model.AgentMchntTxnExample;
import com.icpay.payment.db.dao.mybatis.model.AgentMchntTxnExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentMchntTxnSummary;
import com.icpay.payment.db.service.IAgentMchntTxnService;

@Service("agentMchntTxnService")
public class AgentMchntTxnService extends BaseService implements IAgentMchntTxnService {
	
	private AgentMchntTxnExtMapper dao=null;
	protected AgentMchntTxnExtMapper dao() {
		if (dao==null)
			dao=getMapper(AgentMchntTxnExtMapper.class);
		return dao;
	}
	
	protected String getMon(String mon, Map<String, String> qryParamMap) {
		return getMon(
				mon, 
				qryParamMap.get("startDate"),
				qryParamMap.get("endDate"),
				qryParamMap.get("recUpdTs"),
				qryParamMap.get("recCrtTs"),
				qryParamMap.get("transSeqId"),
				null
				);
	}
	
	@Override
	public Pager<AgentMchntTxn> selectByPage(int pageNum, int pageSize, String mon, Map<String, String> qryParamMap) {
		mon = getMon(mon,qryParamMap);
		Pager<AgentMchntTxn> pager = buildPager(pageNum, pageSize, qryParamMap);
		AgentMchntTxnExample example = this.buildQryExample(qryParamMap);
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);		
		pager.setTotal(dao().countByExample(example, mon));
		pager.setResultList(dao().selectByPage(example, mon));		
		return pager;
	}

	@Override
	public List<AgentMchntTxn> select(String mon, Map<String, String> qryParamMap) {
		//AssertUtil.notMonStr(mon);
		mon = getMon(mon,qryParamMap);
		AgentMchntTxnExample example = this.buildQryExample(qryParamMap);
		return dao().selectByExample(example, mon);
	}

	@Override
	public AgentMchntTxn selectByPrimaryKey(String transSeqId, String mon) {
		//AssertUtil.notMonStr(mon);
		mon = getMon(mon,transSeqId);
		AgentMchntTxnExample example = new AgentMchntTxnExample();
		example.createCriteria().andTransSeqIdEqualTo(transSeqId);
		List<AgentMchntTxn> list= dao().selectByExample(example, mon);
		if ((list==null)||(list.size()==0))
			return null;
		return list.get(0);
	}

	@Override
	public Long count(String mon, Map<String, String> qryParamMap) {
		mon = getMon(mon,qryParamMap);
		AssertUtil.notMonStr(mon);
		AgentMchntTxnExample example = this.buildQryExample(qryParamMap);
		return dao().countByExample(example, mon);
	}

	@Override
	public AgentMchntTxnSummary selectSummary(String mon, Map<String, String> qryParamMap) {
		//AssertUtil.notMonStr(mon);
		mon = getMon(mon,qryParamMap);
		AgentMchntTxnExample example = this.buildQryExample(qryParamMap);
		
		AgentMchntTxnSummary agentMchntTxnSummary=dao().selectSummary(example, mon);

		return agentMchntTxnSummary;
	}
	@Override
	protected AgentMchntTxnExample buildQryExample(Map<String, String> qryParamMap) {
		AgentMchntTxnExample example= new AgentMchntTxnExample();
		if (qryParamMap==null) return example;
		if (qryParamMap.isEmpty()) return example;
		
		Criteria c= buildDefaultCriteria(example, qryParamMap);
		
		//更新时间
		String startDate = StringUtil.trim(qryParamMap.get("startDate"));
		String startTime = StringUtil.trim(qryParamMap.get("startTime"));
		String endDate = StringUtil.trim(qryParamMap.get("endDate"));
		String endTime = StringUtil.trim(qryParamMap.get("endTime"));

		if ((!StringUtil.isBlank(startDate)) || (!StringUtil.isBlank(endDate))) {
			if (StringUtil.isBlank(startDate)) startDate = endDate;
			if (StringUtil.isBlank(endDate)) endDate = startDate; //Converter.dateToString(tomorrow);
			Date date1 = this.convertStartDateTime(startDate, startTime);
			Date date2 = this.convertEndDateTime(endDate, endTime);

			c.andRecUpdTsGreaterThanOrEqualTo(date1);
			c.andRecUpdTsLessThanOrEqualTo(date2);	
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
		
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}
	
	protected Criteria buildDefaultCriteria(AgentMchntTxnExample example, Map<String, String> qryParamMap) {
		if (qryParamMap==null) return null;
		if (qryParamMap.isEmpty()) return null;
				
//		String orderBy = StringUtil.trim(qryParamMap.get("orderBy"));
//		
//		if (!Utils.isEmpty(orderBy))
//			example.setOrderByClause(orderBy);
		
		Criteria c = example.createCriteria();
		
	    /*
	     * 内部交易编号
	     * Database column : view_agent_mchnt_txn_01.trans_seq_id
	     *
	     * @mbg.generated
	     */
	    String transSeqId = StringUtil.trim(qryParamMap.get("transSeqId"));
	    if (!StringUtil.isBlank(transSeqId)) {
	        c.andTransSeqIdEqualTo(transSeqId);
	    }

	    /*
	     * 交易状态
	     * Database column : view_agent_mchnt_txn_01.order_state
	     *
	     * @mbg.generated
	     */
	    String orderState = StringUtil.trim(qryParamMap.get("orderState"));
	    if (!StringUtil.isBlank(orderState)) {
	        c.andOrderStateEqualTo(orderState);
	    }

	    /*
	     * 前端商户号
	     * Database column : view_agent_mchnt_txn_01.mchnt_cd
	     *
	     * @mbg.generated
	     */
	    String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
	    if (!StringUtil.isBlank(mchntCd)) {
	        c.andMchntCdEqualTo(mchntCd);
	    }
	    
	    /*
	     * 商户中文简称
	     * Database column : view_agent_mchnt_txn_01.mchnt_cn_abbr
	     *
	     * @mbg.generated
	     */
	    String mchntCnAbbr = StringUtil.trim(qryParamMap.get("mchntCnAbbr"));
	    if (!StringUtil.isBlank(mchntCnAbbr)) {
	        c.andMchntCnAbbrEqualTo(mchntCnAbbr);
	    }


	    /*
	     * 前端订单号
	     * Database column : view_agent_mchnt_txn_01.order_id
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
	     * 交易类型(4位数)
	     * Database column : view_agent_mchnt_txn_01.int_trans_cd
	     *
	     * @mbg.generated
	     */
	    String intTransCd = StringUtil.trim(qryParamMap.get("intTransCd"));
	    if (!StringUtil.isBlank(intTransCd)) {
	        c.andIntTransCdEqualTo(intTransCd);
	    }

	    /*
	     * 交易金额
	     * Database column : view_agent_mchnt_txn_01.trans_at
	     *
	     * @mbg.generated
	     */
	    String transAt = StringUtil.trim(qryParamMap.get("transAt"));
	    if (!StringUtil.isBlank(transAt)) {
	        c.andTransAtEqualTo(convertAmount(transAt));
	    }

	    /*
	     * 交易手续费
	     * Database column : view_agent_mchnt_txn_01.trans_fee
	     *
	     * @mbg.generated
	     */
	    String transFee = StringUtil.trim(qryParamMap.get("transFee"));
	    if (!StringUtil.isBlank(transFee)) {
	        c.andTransFeeEqualTo(convertAmount(transFee));
	    }

	    /*
	     * 交易状态(支付或代付状态)，请参考 com.icpay.payment.common.constants.Constant.TXNS_TATUS
	     * Database column : view_agent_mchnt_txn_01.txn_state
	     *
	     * @mbg.generated
	     */
	    String txnState = StringUtil.trim(qryParamMap.get("txnState"));
	    if (!StringUtil.isBlank(txnState)) {
	        c.andTxnStateEqualTo(txnState);
	    }

	    /*
	     * 户名
	     * Database column : view_agent_mchnt_txn_01.acc_name
	     *
	     * @mbg.generated
	     */
	    String accName = StringUtil.trim(qryParamMap.get("accName"));
	    if (!StringUtil.isBlank(accName)) {
	        c.andAccNameEqualTo(accName);
	    }

	    /*
	     * 联行号(银行编号)
	     * Database column : view_agent_mchnt_txn_01.bank_num
	     *
	     * @mbg.generated
	     */
	    String bankNum = StringUtil.trim(qryParamMap.get("bankNum"));
	    if (!StringUtil.isBlank(bankNum)) {
	        c.andBankNumEqualTo(bankNum);
	    }
	    
	    /*
	     * 银行名称
	     * Database column : view_agent_mchnt_txn_01.bank_name
	     *
	     * @mbg.generated
	     */
	    String bankName = StringUtil.trim(qryParamMap.get("bankName"));
	    if (!StringUtil.isBlank(bankName)) {
	        c.andBankNameEqualTo(bankName);
	    }

	    /*
	     * Database column : view_agent_mchnt_txn_01.rec_crt_ts
	     *
	     * @mbg.generated
	     */
	    String recCrtTs = StringUtil.trim(qryParamMap.get("recCrtTs"));
	    if (!StringUtil.isBlank(recCrtTs)) {
			c.andRecCrtTsEqualTo(convertDateTime(recCrtTs));
	    }

	    /*
	     * Database column : view_agent_mchnt_txn_01.rec_upd_ts
	     *
	     * @mbg.generated
	     */
	    String recUpdTs = StringUtil.trim(qryParamMap.get("recUpdTs"));
	    if (!StringUtil.isBlank(recUpdTs)) {
			c.andRecUpdTsEqualTo(convertDateTime(recUpdTs));
	    }
	    
	    /*
	     * 代理人编号
	     * Database column : view_agent_mchnt_txn_01.agent_cd
	     *
	     * @mbg.generated
	     */
	    String agentCd = StringUtil.trim(qryParamMap.get("agentCd"));
	    if (!StringUtil.isBlank(agentCd)) {
	        c.andAgentCdEqualTo(agentCd);
	    }
	    
		
		return c;
	}

}
