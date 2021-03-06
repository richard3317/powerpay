package com.icpay.payment.batch.task.settle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.BatchTaskTemplate;
import com.icpay.payment.common.enums.SettleEnums.SettlePeriod;
import com.icpay.payment.common.enums.SettleEnums.SettleTaskState;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.jdbc.bo.SettlePolicy;
import com.icpay.payment.common.jdbc.bo.SettleTask;
import com.icpay.payment.common.jdbc.mapper.SettlePolicyMapper;
import com.icpay.payment.common.utils.DateUtil;

@Component("settleTnLogCreateTask")
public class SettleTnLogCreateTask extends BatchTaskTemplate {
		
	private static final String query_tbl_mer_settle_policy = "select mchnt_cd,settle_account,settle_account_name,settle_account_area_info,settle_account_bank_name,settle_account_bank_code,settle_period,settle_limit,comment from tbl_mer_settle_policy where settle_period!='0'";
	//private static final String query_tbl_mer_settle_task_log = "select seq_id,mchnt_cd,settle_account,settle_account_name,settle_account_bank_name,settle_dt,settle_bt,settle_period,trans_num,trans_at,settle_at,fee_at,file_path,state from tbl_mer_settle_task_log where mchnt_cd=? order by seq_id desc limit 1";
	private static final String insert_tbl_mer_settle_task_log = "insert into tbl_mer_settle_task_log (mchnt_cd,settle_account,settle_account_name,settle_account_area_info,settle_account_bank_name,settle_account_bank_code,settle_dt,settle_bt,settle_period,trans_num,trans_at,settle_at,fee_at,file_path,state, last_oper_id,comment,rec_crt_ts,rec_upd_ts) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
	@Override
	protected void doTask() {
		List<SettlePolicy> settlePolicys =  querySettlePolicy();
		for(SettlePolicy settlePolicy:settlePolicys){
			SettleTask settleTask = new SettleTask();
			settleTask.setMchnt_cd(settlePolicy.getMchnt_cd());
			settleTask.setSettle_account(settlePolicy.getSettle_account());
			settleTask.setSettle_account_name(settlePolicy.getSettle_account_name());
			settleTask.setSettle_account_area_info(settlePolicy.getSettle_account_area_info());
			settleTask.setSettle_account_bank_name(settlePolicy.getSettle_account_bank_name());
			settleTask.setSettle_account_bank_code(settlePolicy.getSettle_account_bank_code());
			settleTask.setSettle_bt(1);
			settleTask.setSettle_period(settlePolicy.getSettle_period());
			settleTask.setState(SettleTaskState._0.getCode());
			settleTask.setLast_oper_id("service");
			 if(SettlePeriod._T1.getCode().equals(settlePolicy.getSettle_period())){
				settleTask.setSettle_dt(batchDt);
			}else if (SettlePeriod._T2.getCode().equals(settlePolicy.getSettle_period())){
				settleTask.setSettle_dt(DateUtil.changeDays(DateUtil.str8ToDate(batchDt), -1));
			}else if (SettlePeriod._T3.getCode().equals(settlePolicy.getSettle_period())){
				settleTask.setSettle_dt(DateUtil.changeDays(DateUtil.str8ToDate(batchDt), -2));
			}else{
				throw new BizzException("??????["+settlePolicy.getMchnt_cd()+"]????????????????????????["+settlePolicy.getSettle_period()+"]");	
			}
			
			insertSettleTask(settleTask);
			logger.info("??????"+settleTask);
		}
		logger.info("??????"+settlePolicys.size()+"???T+n????????????");

	}
	
	@Override
	protected String getTaskNm() {
		return "T+n????????????????????????";
	}
	

	@SuppressWarnings("unchecked")
	public List<SettlePolicy> querySettlePolicy() {
		List<SettlePolicy> result = jdbcTemplate.query(query_tbl_mer_settle_policy, new SettlePolicyMapper());
		return result;
	}

	
	private void insertSettleTask(final SettleTask settleTask){
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(insert_tbl_mer_settle_task_log);
				ps.setString(1, settleTask.getMchnt_cd());
				ps.setString(2, settleTask.getSettle_account());
				ps.setString(3, settleTask.getSettle_account_name());
				ps.setString(4, settleTask.getSettle_account_area_info());
				ps.setString(5, settleTask.getSettle_account_bank_name());
				ps.setString(6, settleTask.getSettle_account_bank_code());
				ps.setString(7, settleTask.getSettle_dt());
				ps.setInt(8, settleTask.getSettle_bt());
				ps.setString(9, settleTask.getSettle_period());
				ps.setInt(10, settleTask.getTrans_num());
				ps.setBigDecimal(11, settleTask.getTrans_at());
				ps.setBigDecimal(12, settleTask.getSettle_at());
				ps.setBigDecimal(13, settleTask.getFee_at());
				ps.setString(14, settleTask.getFile_path());
				ps.setString(15, settleTask.getState());
				ps.setString(16, settleTask.getLast_oper_id());
				ps.setString(17, settleTask.getComment());
				return ps;
			}
		});
	}
}

