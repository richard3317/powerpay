package com.icpay.payment.batch.task.settle;

import static org.apache.commons.lang.StringUtils.isEmpty;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import com.icpay.payment.batch.cache.BatchConfigCache;
import com.icpay.payment.batch.constants.BatchConstants;
import com.icpay.payment.batch.task.BatchTaskTemplate;
import com.icpay.payment.common.constants.Constant.INTER_MSG;
import com.icpay.payment.common.constants.Constant.MSG;
import com.icpay.payment.common.constants.Constant.SETTLE_AlG_KEY;
import com.icpay.payment.common.constants.Constant.TXNTYPE;
import com.icpay.payment.common.enums.SettleEnums.SettleMode;
import com.icpay.payment.common.enums.SettleEnums.SettlePeriod;
import com.icpay.payment.common.enums.SettleEnums.SettleTaskState;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.jdbc.bo.AgentProfitPolicy;
import com.icpay.payment.common.jdbc.bo.SettlePolicySub;
import com.icpay.payment.common.jdbc.bo.SettleProfitResult;
import com.icpay.payment.common.jdbc.bo.SettleTask;
import com.icpay.payment.common.jdbc.mapper.AccChkResMapper;
import com.icpay.payment.common.jdbc.mapper.AgentProfitPolicyMapper;
import com.icpay.payment.common.jdbc.mapper.SettlePolicySubMapper;
import com.icpay.payment.common.jdbc.mapper.SettleTaskMapper;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.FileUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.common.utils.StringUtil;

@Component("settleTnProcTask")
public class SettleTnProcTask extends BatchTaskTemplate {
	private static final String query_tbl_mchnt_info = "select trade_type from tbl_mchnt_info where mchnt_cd=?";	
	private static final String query_tbl_mer_settle_policy_sub = "select mchnt_cd,int_trans_cd,settle_mode,settle_algorithm,comment from tbl_mer_settle_policy_sub where mchnt_cd=? and int_trans_cd=?";
	private static final String query_tbl_mer_settle_task_log = "select seq_id,mchnt_cd,settle_account,settle_account_name,settle_account_area_info,settle_account_bank_name,settle_account_bank_code,settle_dt,settle_bt,settle_period,trans_num,trans_at,settle_at,fee_at,file_path,state from tbl_mer_settle_task_log where mchnt_cd=? and settle_dt=? and settle_period!='0' and state='0'";
	private static final String query_trans_log = "select trans_seq_id,mchnt_cd,agent_cd,order_id,ext_trans_dt,ext_trans_tm,int_trans_cd,acc_no,trans_at,trans_chnl,chnl_mchnt_cd,chnl_term_cd,cups_trace_num,cups_sys_dt,trans_xid,term_sn from tbl_acct_chk_result where check_result='1' and settle_st='0' and (check_dt=? or ext_trans_dt=?) and mchnt_cd=? and int_trans_cd=? order by ext_trans_tm asc";
	private static final String update_tbl_mer_settle_task_log_in_proc = "update tbl_mer_settle_task_log set state='1',rec_upd_ts=CURRENT_TIMESTAMP where mchnt_cd=? and settle_dt=?";
	private static final String update_tbl_mer_settle_task_log = "update tbl_mer_settle_task_log set trans_num=?,trans_at=?,settle_at=?,fee_at=?,file_path=?,state='2',rec_upd_ts=CURRENT_TIMESTAMP where mchnt_cd=? and settle_dt=?";
	private static final String query_tbl_agent_profit_policy = "select agent_cd,trade_type,int_trans_cd,rate,max_fee,min_fee from tbl_agent_profit_policy where agent_cd=? and trade_type=? and int_trans_cd=?";
	private static final String insert_tbl_settle_profit_result = "insert into tbl_settle_profit_result (trans_seq_id,settle_dt,agent_cd,mchnt_cd,term_sn, order_id, ext_trans_dt, ext_trans_tm, int_trans_cd, acc_no, trans_at, settle_at,fee_at,profit_at,mchnt_rate,agent_rate,profit_st,rec_crt_ts, rec_upd_ts) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";

	private static String[] intTxnTypes = new String[]{TXNTYPE._0100,TXNTYPE._3100,TXNTYPE._0400};
	private static final String[] header = new String[]{"商户号,交易日期时间,订单号,查询流水号,交易代码,交易金额（分）,卡号,手续费（分）"};
	@Override
	protected void doTask() {
		String merId = this.params.get(MSG.merId);
		AssertUtil.strIsBlank(merId, "merId is blank.");
		SettleTask settleTask = querySettleTask(merId);
		if(settleTask==null||!SettleTaskState._0.getCode().equals(settleTask.getState())){
			throw new BizzException("清算任务状态不正确");
		}
		
		if(SettlePeriod._T0.getCode().equals(settleTask.getSettle_period())){
			throw new BizzException("清算周期不正确");
		}

		updateSettleTaskInProc(settleTask);
		
		List<String[]> allSettleLogs = new ArrayList<String[]>();
		allSettleLogs.add(header);
		for(String intTxntype:intTxnTypes){
			allSettleLogs.addAll(settle(merId, intTxntype));
		}
		int totalNum = allSettleLogs.size()-1;
		long totalTxnAmt = 0;
		int totalFee  = 0;

		for( int i=1;i< allSettleLogs.size();i++){
			String[] line = allSettleLogs.get(i);
			
			totalFee+=Integer.parseInt(line[7]);
			if(TXNTYPE._0100.equals(line[4])){
				totalTxnAmt +=Long.parseLong(line[5]);
			}else if(TXNTYPE._3100.equals(line[4])||TXNTYPE._0400.equals(line[4])||TXNTYPE._9800.equals(line[4])){
				totalTxnAmt -=Long.parseLong(line[5]);
			}
		}
		
		String filePath = storeToFile(merId,allSettleLogs);
		settleTask.setFile_path(filePath);
		settleTask.setTrans_num(totalNum);
		settleTask.setTrans_at(new BigDecimal(totalTxnAmt));
		settleTask.setSettle_at(new BigDecimal(totalTxnAmt-totalFee));
		settleTask.setFee_at(new BigDecimal(totalFee));
		updateSettleTask(settleTask);
	}
	
	private List<String[]> settle(String merId, String intTxnType){
		List<String[]> res = new ArrayList<String[]>();
		List<Map<String,String>> txnLogs = queryTransLogs(merId,intTxnType);
		if(txnLogs.isEmpty()){
			logger.info("商户["+merId+"]没有待清算的["+intTxnType+"]交易");
			return res; 
		}
		SettlePolicySub policy =querySettlePolicySub(merId,intTxnType);
		if(policy==null){
			throw new BizzException("未找到商户["+merId+"]交易["+intTxnType+"]的 清算策略");
		}
		String agentCd = txnLogs.get(0).get(INTER_MSG.agentCode);
		String tradeType = queryTradeType(merId);
		AgentProfitPolicy agentProfitPolicy= queryAgentProfitPolicy(agentCd,tradeType,intTxnType);
		if (agentProfitPolicy == null) {
			throw new BizzException("未找到代理商[" + agentCd + "]交易[" + intTxnType
					+ "]的分润策略");
		}
		
		@SuppressWarnings("unchecked")
		Map<String,String> algorithm = JsonUtil.fromJson(policy.getSettle_algorithm(), Map.class);
		List<SettleProfitResult> settleProfitResults = new ArrayList<SettleProfitResult>(txnLogs.size());
			for(Map<String,String> txnLog:txnLogs){
				String[] line = new String[8];
				line[0] = merId;
				line[1] = txnLog.get(MSG.txnTime);
				line[2] = txnLog.get(MSG.orderId);
				line[3] = txnLog.get(MSG.queryId);
				line[4] = intTxnType;
				line[5] = txnLog.get(MSG.txnAmt);
				String cardNum = txnLog.get(MSG.cardNo);
				line[6] = StringUtil.mask(cardNum, 6, cardNum.length()-4, '*');
		
				BigDecimal fee = new BigDecimal(0);
				if(SettleMode._2.getCode().equals(policy.getSettle_mode())){
					fee = new BigDecimal(algorithm.get(SETTLE_AlG_KEY.fixRate)).multiply(new BigDecimal(line[5]));
				}else{
					throw new BizzException("无效计费方式["+policy.getSettle_mode()+"]");
				}
				
				if(algorithm.containsKey(SETTLE_AlG_KEY.minFee)){
					BigDecimal minFee = new BigDecimal(algorithm.get(SETTLE_AlG_KEY.minFee));
					if(fee.compareTo(minFee)==-1){
						fee = minFee;
					}
				}
				if(algorithm.containsKey(SETTLE_AlG_KEY.maxFee)){
					BigDecimal maxFee = new BigDecimal(algorithm.get(SETTLE_AlG_KEY.maxFee));
					if(fee.compareTo(maxFee)==1){
						fee = maxFee;
					}
				}
				line[7] = String.valueOf(fee.intValue());
				res.add(line);
				
				BigDecimal agentFee = new BigDecimal(agentProfitPolicy.getRate()).multiply(new BigDecimal(line[5]));
				if(!isEmpty(agentProfitPolicy.getMax_fee())){
					BigDecimal maxFee = new BigDecimal(
							agentProfitPolicy.getMax_fee());
					if (agentFee.compareTo(maxFee) == 1) {
						agentFee = maxFee;
					}
				}
				
				if(!isEmpty(agentProfitPolicy.getMin_fee())){
					BigDecimal minFee = new BigDecimal(
							agentProfitPolicy.getMin_fee());
					if (agentFee.compareTo(minFee) == -1) {
						agentFee = minFee;
					}
				}
				BigDecimal agentProfit = fee.subtract(agentFee);	
				SettleProfitResult settleProfitResult= new SettleProfitResult();
				settleProfitResult.setTrans_seq_id(txnLog.get(MSG.queryId));
				settleProfitResult.setSettle_dt(batchDt);
				settleProfitResult.setAgent_cd(agentCd);
				settleProfitResult.setMchnt_cd(merId);
				settleProfitResult.setTerm_sn(txnLog.get(MSG.termSn));
				settleProfitResult.setOrder_id(txnLog.get(MSG.orderId));
				settleProfitResult.setExt_trans_dt(txnLog.get(MSG.txnTime).substring(0, 8));
				settleProfitResult.setExt_trans_tm(txnLog.get(MSG.txnTime).substring(8));
				settleProfitResult.setInt_trans_cd(intTxnType);
				settleProfitResult.setAcc_no(cardNum);
				settleProfitResult.setTrans_at(new BigDecimal(txnLog.get(MSG.txnAmt)));
				settleProfitResult.setFee_at(fee);
				settleProfitResult.setSettle_at(settleProfitResult.getTrans_at().subtract(fee));
				settleProfitResult.setProfit_at(agentProfit);
				settleProfitResult.setMchnt_rate(algorithm.get(SETTLE_AlG_KEY.fixRate));
				settleProfitResult.setAgent_rate(agentProfitPolicy.getRate());
				settleProfitResults.add(settleProfitResult);
			}
			batchInsertSettleProfitResult(settleProfitResults);
		return res;
	}
	
	@Override
	protected String getTaskNm() {
		return "T+n商户清算任务";
	}
	@SuppressWarnings("unchecked")
	public SettlePolicySub querySettlePolicySub(String merId,String intTxnType) {
		List<SettlePolicySub> res =  jdbcTemplate.query(
				query_tbl_mer_settle_policy_sub, new Object[] {merId,intTxnType},
				new SettlePolicySubMapper());
		if(res!=null&&res.size()==1){
			return res.get(0);
		}else{
			return null;
		}

	}
	
	@SuppressWarnings("unchecked")
	public SettleTask querySettleTask(String merId) {
		List<SettleTask> result = jdbcTemplate.query(
				query_tbl_mer_settle_task_log, new Object[] { merId ,batchDt},
				new SettleTaskMapper());
		if (result.size() == 1) {
			return result.get(0);
		} else {
			return null;
		}
	}
	
	public int updateSettleTaskInProc(final SettleTask settleTask){

		return this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(update_tbl_mer_settle_task_log_in_proc);
				ps.setString(1, settleTask.getMchnt_cd());
				ps.setString(2, settleTask.getSettle_dt());
				return ps;
			}
		});

	}
	
	public int updateSettleTask(final SettleTask settleTask){

		return this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(update_tbl_mer_settle_task_log);
				ps.setInt(1, settleTask.getTrans_num());
				ps.setBigDecimal(2, settleTask.getTrans_at());
				ps.setBigDecimal(3, settleTask.getSettle_at());
				ps.setBigDecimal(4, settleTask.getFee_at());
				ps.setString(5, settleTask.getFile_path());
				ps.setString(6, settleTask.getMchnt_cd());
				ps.setString(7, settleTask.getSettle_dt());
				return ps;
			}
		});

	}

	@SuppressWarnings("unchecked")
	private List<Map<String,String>> queryTransLogs(String merId,String intTxnType) {
		return this.jdbcTemplate.query(query_trans_log,new Object[]{batchDt,batchDt,merId,intTxnType}, new AccChkResMapper());
	}
	
	
	
	private String storeToFile(String merId,List<String[]> lines){
		String dir = BatchConfigCache.getConfig(BatchConstants.CONF_KEY_MER_SETTLE_FILE_PATH);
		String filePath = dir + batchDt + File.separator + "MER_"+batchDt+"_"+merId;
		List<String> content = new ArrayList<String>(lines.size());
		for(String[] line:lines){
			String tmp = Arrays.toString(line);
			content.add(tmp.substring(1,tmp.length()-1));
		}
		try {
			if(FileUtil.newFile(filePath,true)){
				FileUtil.writeStrsToFile(filePath, content, "UTF-8");
				return filePath;
			}else{
				throw new RuntimeException("创建文件["+filePath+"]失败");
			}
		} catch (Exception e) {
			throw new RuntimeException("写入文件["+filePath+"]失败",e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public AgentProfitPolicy queryAgentProfitPolicy(String agentCd,String tradeType,String intTransCd) {
		List<AgentProfitPolicy> res =  jdbcTemplate.query(
				query_tbl_agent_profit_policy, new Object[] { agentCd,tradeType,intTransCd},
				new AgentProfitPolicyMapper());
		if(res!=null&&res.size()==1){
			return res.get(0);
		}
		return null;

	}
	
	
	public String queryTradeType(String mchntCd) {
		String res =  jdbcTemplate.queryForObject(
				query_tbl_mchnt_info, new Object[] { mchntCd},String.class);
		if(!isEmpty(res)){
			return res;
		}
		return "";
	}
	
	
	public int[] batchInsertSettleProfitResult(final List<SettleProfitResult> settleProfitResults){
		return this.jdbcTemplate.batchUpdate(insert_tbl_settle_profit_result, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, settleProfitResults.get(i).getTrans_seq_id());
				ps.setString(2, settleProfitResults.get(i).getSettle_dt());
				ps.setString(3, settleProfitResults.get(i).getAgent_cd());
				ps.setString(4, settleProfitResults.get(i).getMchnt_cd());
				ps.setString(5, settleProfitResults.get(i).getTerm_sn());
				ps.setString(6, settleProfitResults.get(i).getOrder_id());
				ps.setString(7, settleProfitResults.get(i).getExt_trans_dt());
				ps.setString(8, settleProfitResults.get(i).getExt_trans_tm());
				ps.setString(9, settleProfitResults.get(i).getInt_trans_cd());
				ps.setString(10, settleProfitResults.get(i).getAcc_no());		
				ps.setBigDecimal(11,  settleProfitResults.get(i).getTrans_at());
				ps.setBigDecimal(12,  settleProfitResults.get(i).getSettle_at());
				ps.setBigDecimal(13,  settleProfitResults.get(i).getFee_at());
				ps.setBigDecimal(14,  settleProfitResults.get(i).getProfit_at());
				ps.setString(15, settleProfitResults.get(i).getMchnt_rate());	
				ps.setString(16, settleProfitResults.get(i).getAgent_rate());
				ps.setString(17, settleProfitResults.get(i).getProfit_st());

			}
			public int getBatchSize() {
				return settleProfitResults.size();
			}
		});
	}
	public static void main(String[] args){
//		String s = "839320548990009 20000022 20150323081626 006739 消费               100.00         0.70 403392******6297    578140 27667                                    058005                        ";
//		System.out.println(new BigDecimal("-1.01").intValue());
//		System.out.println(new BigDecimal("0.391").intValue());
//		System.out.println(new BigDecimal("-2111.01").intValue());
//		System.out.println(new BigDecimal("-0.49").intValue());
//		System.out.println(Integer.parseInt("-12"));
//		System.out.println("[123]".substring(1, "[123]".length()));
	}
}

