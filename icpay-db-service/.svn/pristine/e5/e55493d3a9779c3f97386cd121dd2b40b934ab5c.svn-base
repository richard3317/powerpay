package com.icpay.payment.db.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.exception.I18nBizException;
import com.icpay.payment.common.exception.I18nMsgSpec;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.mapper.AgentProfitWithdrawLogMapper;
import com.icpay.payment.db.dao.mybatis.mapper.MchntInfoMapper;
import com.icpay.payment.db.dao.mybatis.mapper.ExtMapper.AgentProfitWithdrawLogExtMapper;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitWithdrawLog;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitWithdrawLogExample;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfoExample;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitWithdrawLogExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentProfitWithdrawSummary;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MerAccountInfoSummary;
import com.icpay.payment.db.service.IAgentProfitWithdrawLogService;

@Service("AgentProfitWithdrawLogService")
public class AgentProfitWithdrawLogService extends BaseService implements IAgentProfitWithdrawLogService {

	@Override
	protected AgentProfitWithdrawLogExample buildQryExample(Map<String, String> qryParamMap) {
		AgentProfitWithdrawLogExample example = new AgentProfitWithdrawLogExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			
			String strId = qryParamMap.get("id");
			if (!StringUtil.isBlank(strId)) {
			    c.andIdEqualTo(Long.parseLong(strId));
			    return example;
			}
			
			String startSettleDate = StringUtil.trim(qryParamMap.get("startSettleDate"));
			String endSettleDate = StringUtil.trim(qryParamMap.get("endSettleDate"));
			if ((StringUtil.isNotBlank(startSettleDate)) || (StringUtil.isNotBlank(endSettleDate))) {
				if (StringUtil.isBlank(startSettleDate)) startSettleDate = endSettleDate;
				if (StringUtil.isBlank(endSettleDate)) endSettleDate = startSettleDate;
				c.andSettleDateGreaterThanOrEqualTo(startSettleDate);
				c.andSettleDateLessThanOrEqualTo(endSettleDate);
			}

			String withdrawType = qryParamMap.get("withdrawType");
			if (!StringUtil.isBlank(withdrawType)) {
			    c.andWithdrawTypeEqualTo(withdrawType);
			}

			String agentCd = qryParamMap.get("agentCd");
			if (!StringUtil.isBlank(agentCd)) {
			    c.andAgentCdEqualTo(agentCd);
			}

			String mchntCd = qryParamMap.get("mchntCd");
			if (!StringUtil.isBlank(mchntCd)) {
			    c.andMchntCdEqualTo(mchntCd);
			}

			String accountNo = qryParamMap.get("accountNo");
			if (!StringUtil.isBlank(accountNo)) {
			    c.andAccountNoEqualTo(accountNo);
			}

			String seq = qryParamMap.get("seq");
			if (!StringUtil.isBlank(seq)) {
			    c.andSeqEqualTo(Integer.parseInt(seq));
			}

			String state = qryParamMap.get("state");
			if (!StringUtil.isBlank(state)) {
			    c.andStateEqualTo(state);
			}

			String accountName = qryParamMap.get("accountName");
			if (!StringUtil.isBlank(accountName)) {
			    c.andAccountNameLike("%"+accountName+"%");
			}

			String accountBankCode = qryParamMap.get("accountBankCode");
			if (!StringUtil.isBlank(accountBankCode)) {
			    c.andAccountBankCodeEqualTo(accountBankCode);
			}

			String accountBankName = qryParamMap.get("accountBankName");
			if (!StringUtil.isBlank(accountBankName)) {
			    c.andAccountBankNameLike("%"+accountBankName+"%");
			}

			String txnOrderDate = qryParamMap.get("txnOrderDate");
			if (!StringUtil.isBlank(txnOrderDate)) {
			    c.andTxnOrderDateEqualTo(txnOrderDate);
			}

			String txnOrderTime = qryParamMap.get("txnOrderTime");
			if (!StringUtil.isBlank(txnOrderDate)) {
			    c.andTxnOrderTimeEqualTo(txnOrderTime);
			}

			String txnOrderId = qryParamMap.get("txnOrderId");
			if (!StringUtil.isBlank(txnOrderId)) {
			    c.andTxnOrderIdEqualTo(txnOrderId);
			}
			
			String txnBatchId = qryParamMap.get("txnBatchId");
			if (!StringUtil.isBlank(txnBatchId)) {
			    c.andTxnBatchIdEqualTo(txnBatchId);
			}
			
			String procState = qryParamMap.get("procState");
			if (!StringUtil.isBlank(procState)) {
			    c.andProcStateEqualTo(procState);
			}

			String procStateMsg = qryParamMap.get("procStateMsg");
			if (!StringUtil.isBlank(procStateMsg)) {
			    c.andProcStateMsgLike("%"+procStateMsg+"%");
			}

			String txnState = qryParamMap.get("txnState");
			if (!StringUtil.isBlank(txnState)) {
			    c.andTxnStateEqualTo(txnState);
			}

			String txnStateMsg = qryParamMap.get("txnStateMsg");
			if (!StringUtil.isBlank(txnStateMsg)) {
			    c.andTxnStateMsgLike("%"+txnStateMsg+"%");
			}

			String comment = qryParamMap.get("comment");
			if (!StringUtil.isBlank(comment)) {
				c.andCommentLike("%"+comment+"%");
			}
			
			//String mchntName
			String mchntName = StringUtil.trim(qryParamMap.get("mchntName"));
			if (!StringUtil.isBlank(mchntName)) {
				MchntInfoMapper mdao = getMapper(MchntInfoMapper.class);
				MchntInfoExample mex= new MchntInfoExample();
				MchntInfoExample.Criteria c1 = mex.createCriteria().andMchntCnAbbrLike("%"+mchntName+"%");
				MchntInfoExample.Criteria c2 = mex.or().andMchntCnNmLike("%"+mchntName+"%");
				List<MchntInfo> mers=mdao.selectByExample(mex);
				if ((mers!=null)&& mers.size()>0) {
					List<String> merIds= new ArrayList<>();
					for(MchntInfo m: mers) {
						merIds.add(m.getMchntCd());
					}
					c.andMchntCdIn(merIds);
				}
			}
		}
		// 排序字段
		//example.setOrderByClause("rec_upd_ts desc");
		example.setOrderByClause("settle_date DESC, txn_amt DESC");
		return example;
	}
	
	@Override
	public AgentProfitWithdrawLog selectByPrimaryKey(Long id) {
		AssertUtil.objIsNull(id, "id is null");
		return this.getMapper().selectByPrimaryKey(id);
	}

	@Override
	public List<AgentProfitWithdrawLog> select(Map<String, String> qryParamMap) {
		AgentProfitWithdrawLogExample example = this.buildQryExample(qryParamMap);
		return this.getMapper().selectByExample(example);
	}
	
	@Override
	public Pager<AgentProfitWithdrawLog> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		AgentProfitWithdrawLogMapper mapper = getMapper();
		AgentProfitWithdrawLogExample example = this.buildQryExample(qryParamMap);
		Pager<AgentProfitWithdrawLog> pager = buildPager(pageNum, pageSize, qryParamMap);
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	@Override
	public void add(AgentProfitWithdrawLog entity) {
		AssertUtil.objIsNull(entity, "entity is null");
		AssertUtil.strIsBlank(entity.getAgentCd(), "agentCd is blank.");
		AssertUtil.strIsBlank(entity.getSettleDate(), "settleDate is balnk");
		AssertUtil.strIsBlank(entity.getMchntCd(), "mchntCd is blank");
		AssertUtil.strIsBlank(entity.getAccountNo(), "accountNo is blank");
		AssertUtil.objIsNull(entity.getSeq(), "seq is blank");		
		
		Date now = new Date();
		entity.setRecCrtTs(now);
		entity.setRecUpdTs(now);
		
		//this.getMapper().insert(entity);
		this.getMapper().insertSelective(entity);
	}

	@Override
	public int delete(Long id) {
		AssertUtil.objIsNull(id, "id is null");
		return this.getMapper().deleteByPrimaryKey(id);
	}

	@Override
	public void update(AgentProfitWithdrawLog entity) {
		AssertUtil.objIsNull(entity, "entity is null");
		AssertUtil.strIsBlank(entity.getAgentCd(), "agentCd is blank.");
		AssertUtil.strIsBlank(entity.getSettleDate(), "settleDate is balnk");
		AssertUtil.strIsBlank(entity.getMchntCd(), "mchntCd is blank");
		AssertUtil.strIsBlank(entity.getAccountNo(), "accountNo is blank");
		AssertUtil.objIsNull(entity.getSeq(), "seq is blank");		
		
		AgentProfitWithdrawLogMapper mapper = getMapper();
		int r = mapper.updateByPrimaryKeySelective(entity);
		if (r==0)
			throw new I18nBizException(new I18nMsgSpec("zh_CN", this.getClass().getSimpleName(),null, "數據更新失敗"));
	}

	private AgentProfitWithdrawLogMapper getMapper() {
		return this.getMapper(AgentProfitWithdrawLogMapper.class);
	}

	@Override
	public int enableAll(Map<String, String> qryParamMap, boolean enabled, String lastOperId) {
		AgentProfitWithdrawLogExample example = this.buildQryExample(qryParamMap);
		List<AgentProfitWithdrawLog> list= this.getMapper().selectByExample(example);
		int count=0;
		for(AgentProfitWithdrawLog rec: list) {
			if (isRecordEditable(rec)) {
				rec.setState(enabled ? "1" : "0");
				rec.setLastOperId(lastOperId);
				rec.setRecUpdTs(new Date());
				try {
					count += this.getMapper().updateByPrimaryKeySelective(rec);
				} catch (Exception e) {
					this.error("[enableAll] 数据更新失败: "+ rec, e);
				}
			}
		}
		return count;
	}
	
	protected boolean isRecordEditable(AgentProfitWithdrawLog rec) {
		boolean r= (Utils.isInSet(rec.getProcState(), "00","",null)) && (Utils.isEmpty(rec.getTxnOrderId()));
		return r;
	}
	
	
//	private String listToString(List<?> list) {
//		if (list==null) return "";
//		if (list.size()==0) return "";
//		StringBuilder buf=new StringBuilder("{").append("\n");
//		for(Object item: list) {
//			buf.append(item).append(",\n");
//		}
//		buf.append("\n");
//		return buf.toString();
//	}
	
	@Override
	public AgentProfitWithdrawSummary selectSummary(Map<String, String> qryParamMap) {
		AgentProfitWithdrawLogExample example = this.getQryExample(qryParamMap);
		AgentProfitWithdrawSummary sum = this.getMapper(AgentProfitWithdrawLogExtMapper.class).selectSummary(example);
		return sum;
	}

}
