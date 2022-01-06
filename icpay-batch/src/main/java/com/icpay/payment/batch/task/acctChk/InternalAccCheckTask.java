package com.icpay.payment.batch.task.acctChk;
import static org.apache.commons.lang.StringUtils.isEmpty;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.BatchTaskTemplate;
import com.icpay.payment.common.constants.Constant.MSG;
import com.icpay.payment.common.constants.Constant.OPERTYPE;
import com.icpay.payment.common.constants.Constant.TXNTYPE;
import com.icpay.payment.common.constants.RspCd;
import com.icpay.payment.common.jdbc.bo.MerAccountFlow;
import com.icpay.payment.common.jdbc.mapper.AccChkTransLogMapper;
import com.icpay.payment.common.jdbc.mapper.MerAccountFlowMapper;
import com.icpay.payment.common.utils.DateUtil;

@Component("internalAccCheckTask")
public class InternalAccCheckTask extends BatchTaskTemplate {
	
	private static final String query_mer_account_flow_by_mer_date = "select seq_id,mchnt_cd,trans_at,operate_tp,available_balance,frozen_balance,trans_seq_id,note,last_oper_id from tbl_mer_account_flow where mchnt_cd=? and rec_crt_ts>=? and rec_crt_ts<? order by seq_id asc";
	private static final String query_all_account_flow_by_mer_date = "select seq_id,mchnt_cd,trans_at,operate_tp,available_balance,frozen_balance,trans_seq_id,note,last_oper_id from tbl_mer_account_flow where rec_crt_ts>=? and rec_crt_ts<? order by seq_id asc";
	private static final String query_base_account_flow_by_mer_date = "select seq_id,mchnt_cd,trans_at,operate_tp,available_balance,frozen_balance,trans_seq_id,note,last_oper_id from tbl_mer_account_flow where mchnt_cd=? and rec_crt_ts<? order by seq_id desc limit 1";
	private static final String query_account_flow_by_qid = "select seq_id,mchnt_cd,trans_at,operate_tp,available_balance,frozen_balance,trans_seq_id,note,last_oper_id from tbl_mer_account_flow where trans_seq_id=? order by seq_id asc";
	private static final String query_mchnt_by_date = "select distinct mchnt_cd from tbl_mer_account_flow where rec_crt_ts>=? and rec_crt_ts<?";
	private static final String query_trans_log_by_qid="select trans_seq_id,mchnt_cd,agent_cd,order_id,ext_trans_dt,ext_trans_tm,int_trans_cd,acc_no,trans_at,trans_chnl,chnl_mchnt_cd,chnl_term_cd,cups_settle_curr_cd,cups_settle_at,cups_settle_dt,cups_trace_num,cups_sys_dt,trans_xid,rsp_cd,settle_dt,term_sn from tbl_acct_chk_trans_log where trans_seq_id=?";
	private static final String query_all_trans_log="select trans_seq_id,mchnt_cd,agent_cd,order_id,ext_trans_dt,ext_trans_tm,int_trans_cd,acc_no,trans_at,trans_chnl,chnl_mchnt_cd,chnl_term_cd,cups_settle_curr_cd,cups_settle_at,cups_settle_dt,cups_trace_num,cups_sys_dt,trans_xid,rsp_cd,settle_dt,term_sn from tbl_acct_chk_trans_log where rec_crt_ts>=? and rec_crt_ts<? and mchnt_cd in (select mchnt_cd from tbl_mer_settle_policy)";
	
	@Override
	protected void doTask() {
		checkMerAccFlow();
		checkAccFlow2TransFlow();
		checkTransFlow2AccFlow();
	}
	
	
	private void checkMerAccFlow(){
		logger.info("===开始核算商户资金流水===");
		List<String> mchntIds = getMchntIds();
		if(mchntIds==null||mchntIds.isEmpty()){
			logger.warn("需清算商户无资金操作,内部对账结束");
			throw new RuntimeException("未发现需清算商户账户资金流水,内部对账结束");
		}else{
			logger.info("需核算资金流水的商户"+mchntIds);
		}
		
		for(String mchntId: mchntIds){
			
			BigDecimal baseAvailableBalance = new BigDecimal(0);
			BigDecimal baseFrozenBalance = new BigDecimal(0);
			MerAccountFlow baseFlow = queryBaseAccFlow(mchntId);
			if(baseFlow!=null){		
				baseAvailableBalance = baseFlow.getAvailable_balance();
				baseFrozenBalance = baseFlow.getFrozen_balance();
			}
			logger.info("开始核算商户["+mchntId+"]资金流水，日初availableBalance["+baseAvailableBalance+"],frozenBalance["+baseFrozenBalance+"]");
			List<MerAccountFlow> accFlows = queryAccFlowsMerId(mchntId);
			for(MerAccountFlow accFlow:accFlows){
				if(OPERTYPE._00.equals(accFlow.getOperate_tp())){
					baseAvailableBalance = baseAvailableBalance.add(accFlow.getTrans_at());
				}else if(OPERTYPE._01.equals(accFlow.getOperate_tp())||OPERTYPE._42.equals(accFlow.getOperate_tp())){
					baseAvailableBalance = baseAvailableBalance.add(accFlow.getTrans_at());
					baseFrozenBalance = baseFrozenBalance.subtract(accFlow.getTrans_at());
//				}else if(OPERTYPE._20.equals(accFlow.getOperate_tp())||OPERTYPE._40.equals(accFlow.getOperate_tp())){
//					baseAvailableBalance = baseAvailableBalance.subtract(accFlow.getTrans_at());
//					baseFrozenBalance = baseFrozenBalance.add(accFlow.getTrans_at());
//				}else if(OPERTYPE._21.equals(accFlow.getOperate_tp())||OPERTYPE._41.equals(accFlow.getOperate_tp())){
//					baseFrozenBalance = baseFrozenBalance.subtract(accFlow.getTrans_at());
				}else if(OPERTYPE._28.equals(accFlow.getOperate_tp())||OPERTYPE._29.equals(accFlow.getOperate_tp())){
					baseAvailableBalance = baseAvailableBalance.subtract(accFlow.getTrans_at());
				}else if(OPERTYPE._43.equals(accFlow.getOperate_tp())){
					baseFrozenBalance = baseFrozenBalance.add(accFlow.getTrans_at());
				}
				
				if(!baseFrozenBalance.equals(accFlow.getFrozen_balance())||!baseAvailableBalance.equals(accFlow.getAvailable_balance())){
					throw new RuntimeException("发现不平账 "+accFlow+", baseAvailableBalance["+baseAvailableBalance+"]baseFrozenBalance["+baseFrozenBalance+"]");
				}	
			}
			
			logger.info("核算商户["+mchntId+"]资金流水成功");
		}
		
	}
	
	private void checkAccFlow2TransFlow(){
		logger.info("===开始勾兑商户资金流水>>>交易流水===");
		List<MerAccountFlow>  accFlows = queryAllAccFlows();
		for(MerAccountFlow accFlow:accFlows){
			try{
				Map<String,String> transFlow = queryTransLog(accFlow.getTrans_seq_id());
				if(transFlow!=null){
					String transType=transFlow.get(MSG.txnType)+transFlow.get(MSG.txnSubType);
					if(OPERTYPE._00.equals(accFlow.getOperate_tp())){
						
						if(!RspCd._0000000.equals(transFlow.get(MSG.respCode))
								||!TXNTYPE._0100.equals(transType)
								||!accFlow.getTrans_at().equals(new BigDecimal(transFlow.get(MSG.txnAmt)))){
							throw new RuntimeException("资金流水与交易信息不一致"+accFlow);
						}
					}else if(OPERTYPE._01.equals(accFlow.getOperate_tp())){
						
						if(RspCd._0000000.equals(transFlow.get(MSG.respCode))||transFlow.get(MSG.respCode).startsWith("04")
								||!(TXNTYPE._0400.equals(transType)||TXNTYPE._3100.equals(transType)||TXNTYPE._9800.equals(transType))
								||!accFlow.getTrans_at().equals(new BigDecimal(transFlow.get(MSG.txnAmt)))){
							throw new RuntimeException("资金流水与交易信息不一致"+accFlow);
						}
//					}else if(OPERTYPE._20.equals(accFlow.getOperate_tp())){
//						
//						if(!(TXNTYPE._0400.equals(transType)||TXNTYPE._3100.equals(transType)||TXNTYPE._9800.equals(transType))
//								||!accFlow.getTrans_at().equals(new BigDecimal(transFlow.get(MSG.txnAmt)))){
//							throw new RuntimeException("资金流水与交易信息不一致"+accFlow);
//						}
//					}else if(OPERTYPE._21.equals(accFlow.getOperate_tp())){
//						
//						if(!RspCd._0000000.equals(transFlow.get(MSG.respCode))
//								||!(TXNTYPE._0400.equals(transType)||TXNTYPE._3100.equals(transType)||TXNTYPE._9800.equals(transType))
//								||!accFlow.getTrans_at().equals(new BigDecimal(transFlow.get(MSG.txnAmt)))){
//							throw new RuntimeException("资金流水与交易信息不一致"+accFlow);
//						}
					}else if(OPERTYPE._28.equals(accFlow.getOperate_tp())
							||OPERTYPE._29.equals(accFlow.getOperate_tp())
							||OPERTYPE._40.equals(accFlow.getOperate_tp())
							||OPERTYPE._41.equals(accFlow.getOperate_tp())
							||OPERTYPE._42.equals(accFlow.getOperate_tp())
							||OPERTYPE._43.equals(accFlow.getOperate_tp())){
						//不参与对账
					}else{
						throw new RuntimeException("未知资金流水类型"+accFlow);
					}
				}
			}catch(Exception e){
				logger.error("勾兑资金流水异常"+accFlow, e);
				throw new RuntimeException("勾兑资金流水异常"+accFlow);
			}
			
		}
		logger.info("勾兑结束,共["+accFlows.size()+"]条商户资金流水");
	}
	
	private void checkTransFlow2AccFlow(){
		logger.info("===开始勾兑交易流水>>>商户资金流水===");
		List<Map<String,String>>  transFlows = queryAllTransLogs();
		int size = 0;
		for(Map<String,String> transFlow : transFlows){
			String intTxnType = transFlow.get(MSG.txnType) + transFlow.get(MSG.txnSubType);
			if(!(TXNTYPE._0100.equals(intTxnType)||TXNTYPE._0400.equals(intTxnType)||TXNTYPE._3100.equals(intTxnType)||TXNTYPE._9800.equals(intTxnType))){
				continue;
			}
			size ++;
			List<MerAccountFlow>  accFlows = queryAccFlowsByQid(transFlow.get(MSG.queryId));

			if(TXNTYPE._0100.equals(intTxnType)){
				if((RspCd._0000000.equals(transFlow.get(MSG.respCode))&&accFlows.size()==1&&OPERTYPE._00.equals(accFlows.get(0).getOperate_tp()))
						||(!RspCd._0000000.equals(transFlow.get(MSG.respCode))&&accFlows.size()==0)){
					continue;
				}
				throw new RuntimeException("交易流水与资金流水不一致"+transFlow);
			}else if((TXNTYPE._3100.equals(intTxnType)||TXNTYPE._0400.equals(intTxnType)||TXNTYPE._9800.equals(intTxnType))){
				//交易状态未知
				if(!isEmpty(transFlow.get(MSG.respCode))&&transFlow.get(MSG.respCode).startsWith("04")&&accFlows.size()==1&&(OPERTYPE._20.equals(accFlows.get(0).getOperate_tp()))){
					continue;
				}
				
				//交易成功
				if(RspCd._0000000.equals(transFlow.get(MSG.respCode))&&accFlows.size()==2){
//					if(OPERTYPE._20.equals(accFlows.get(0).getOperate_tp())
//							&&OPERTYPE._21.equals(accFlows.get(1).getOperate_tp())){
//						continue;
//					}
				}
				//交易失败
				if(!RspCd._0000000.equals(transFlow.get(MSG.respCode))&&!transFlow.get(MSG.respCode).startsWith("04")){
					if(accFlows.size()==2&&OPERTYPE._20.equals(accFlows.get(0).getOperate_tp())
							&&OPERTYPE._01.equals(accFlows.get(1).getOperate_tp())){
						continue;
					}
					
					if(accFlows.size()==0){
						continue;
					}	
				}
				
				throw new RuntimeException("交易流水与资金流水不一致"+transFlow);
			}
		}
		
		logger.info("勾兑结束,共["+size+"]条交易流水");
	}


	private List<String> getMchntIds(){
		return this.jdbcTemplate.queryForList(query_mchnt_by_date,new Object[]{new Timestamp(DateUtil.str8ToDate(batchDt).getTime()),new Timestamp(DateUtil.str8ToDate(nextBatchDt).getTime())}, String.class);
	}
	
	private List<MerAccountFlow> queryAccFlowsMerId(String merId){
		return this.jdbcTemplate.query(query_mer_account_flow_by_mer_date, new Object[]{merId,new Timestamp(DateUtil.str8ToDate(batchDt).getTime()),new Timestamp(DateUtil.str8ToDate(nextBatchDt).getTime())}, new MerAccountFlowMapper());

	}
	
	private List<MerAccountFlow> queryAllAccFlows(){
		return this.jdbcTemplate.query(query_all_account_flow_by_mer_date, new Object[]{new Timestamp(DateUtil.str8ToDate(batchDt).getTime()),new Timestamp(DateUtil.str8ToDate(nextBatchDt).getTime())}, new MerAccountFlowMapper());
	}
	
	private List<MerAccountFlow> queryAccFlowsByQid(String qryId){
		return this.jdbcTemplate.query(query_account_flow_by_qid, new Object[]{qryId}, new MerAccountFlowMapper());
	}
	
	private MerAccountFlow queryBaseAccFlow(String merId){
		List<MerAccountFlow> res = this.jdbcTemplate.query(query_base_account_flow_by_mer_date, new Object[]{merId,new Timestamp(DateUtil.str8ToDate(batchDt).getTime())}, new MerAccountFlowMapper());
		if(res!=null&&res.size()==1){
			return res.get(0);
		}else{
			return null;
		}
	}
	
	private Map<String,String> queryTransLog(String qryId) {
		@SuppressWarnings("unchecked")
		List<Map<String,String>> res=this.jdbcTemplate.query(query_trans_log_by_qid,  new Object[]{qryId},new AccChkTransLogMapper());
		if(res!=null&&res.size()==1){
			return res.get(0);
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<Map<String,String>> queryAllTransLogs() {
		return this.jdbcTemplate.query(query_all_trans_log,  new Object[]{new Timestamp(DateUtil.str8ToDate(batchDt).getTime()),new Timestamp(DateUtil.str8ToDate(nextBatchDt).getTime())},new AccChkTransLogMapper());

	}
	
	@Override
	protected String getTaskNm() {
		return "内部对账批量任务";
	}
	
//	public static void main(String[] args){
//		System.out.println(new Timestamp(DateUtil.str8ToDate("20150120").getTime()));
//		//System.out.println(Timestamp.valueOf("20150120"));
//	}
}
