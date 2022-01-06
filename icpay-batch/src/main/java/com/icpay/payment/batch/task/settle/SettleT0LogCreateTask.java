package com.icpay.payment.batch.task.settle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.BatchTaskTemplate;
import com.icpay.payment.common.constants.Constant.INTER_MSG;
import com.icpay.payment.common.constants.Constant.MSG;
import com.icpay.payment.common.enums.SettleEnums.SettleTaskState;
import com.icpay.payment.common.jdbc.bo.SettlePolicy;
import com.icpay.payment.common.jdbc.bo.SettleTask;
import com.icpay.payment.common.jdbc.mapper.SettlePolicyMapper;

@Component("settleT0LogCreateTask")
public class SettleT0LogCreateTask extends BatchTaskTemplate {
		
	private static final String query_tbl_mer_settle_policy = "select mchnt_cd,settle_account,settle_account_name,settle_account_area_info,settle_account_bank_name,settle_account_bank_code,settle_period,settle_limit,comment from tbl_mer_settle_policy where settle_period='0'";
	
	private static final String insert_tbl_mer_settle_task_log = "insert into tbl_mer_settle_task_log (mchnt_cd,settle_account,settle_account_name,settle_account_area_info,settle_account_bank_name,settle_account_bank_code,settle_dt,settle_bt,settle_period,trans_num,trans_at,settle_at,fee_at,file_path,state, last_oper_id,comment,rec_crt_ts,rec_upd_ts) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
	@Override
	protected void doTask() {
		String settleDate = this.params.get(MSG.settleDate);
		String settleBatch = this.params.get(INTER_MSG.settleBatch);

		
		logger.info("开始创建"+settleDate+"批次"+settleBatch+" T+0清算记录");
		List<SettlePolicy> settlePolicys =  querySettlePolicy();
		for(SettlePolicy settlePolicy:settlePolicys){
			SettleTask settleTask = new SettleTask();
			settleTask.setMchnt_cd(settlePolicy.getMchnt_cd());
			settleTask.setSettle_account(settlePolicy.getSettle_account());
			settleTask.setSettle_account_name(settlePolicy.getSettle_account_name());
			settleTask.setSettle_account_area_info(settlePolicy.getSettle_account_area_info());
			settleTask.setSettle_account_bank_name(settlePolicy.getSettle_account_bank_name());
			settleTask.setSettle_account_bank_code(settlePolicy.getSettle_account_bank_code());
			settleTask.setSettle_dt(settleDate);
			settleTask.setSettle_period(settlePolicy.getSettle_period());
			settleTask.setState(SettleTaskState._0.getCode());
			settleTask.setLast_oper_id("service");
			settleTask.setSettle_bt(Integer.parseInt(settleBatch));			
			insertSettleTask(settleTask);
			logger.info("创建"+settleTask);
		}
		logger.info("创建"+settlePolicys.size()+"条T+0清算记录");
	}
	
	@Override
	protected String getTaskNm() {
		return "T+0清算记录创建任务";
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

