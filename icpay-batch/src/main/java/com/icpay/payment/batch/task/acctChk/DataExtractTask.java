package com.icpay.payment.batch.task.acctChk;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.BatchTaskTemplate;
import com.icpay.payment.common.utils.DateUtil;

@Component("dataExtractTask")
public class DataExtractTask extends BatchTaskTemplate {

	@Override
	protected void doTask() {
		String sql = "select count(*) from tbl_acct_chk_trans_log";
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		
		if (count > 0) {
			// 如果对账交易表非空，先清空
			logger.info("对账交易表非空，先清空");
			jdbcTemplate.execute("TRUNCATE table tbl_acct_chk_trans_log");
		}
		
		// 抽取三天交易进行对账
		extract(this.preBatchDt);
		extract(this.batchDt);
		extract(this.nextBatchDt);
		
		// 打印抽取结果
		final StringBuilder sb = new StringBuilder("****抽取结果*****\n");
		sql = "select ext_trans_dt, count(*) as c from tbl_acct_chk_trans_log group by ext_trans_dt";
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				sb.append(rs.getString("ext_trans_dt") + "抽取交易条数:" + rs.getInt("c")+"\n");
			}
		});
		logger.info(sb.toString());
	}
	
/*	private void extract(String transDt) {
		String mon = transDt.substring(4, 6);
		String transLogTbl = "tbl_trans_log" + mon;
		String sql = "insert into tbl_acct_chk_trans_log " +
				"(trans_seq_id, mchnt_cd,agent_cd,order_id, ext_trans_dt, ext_trans_tm, " +
				"int_trans_cd, acc_no, trans_at, trans_chnl,chnl_mchnt_cd,chnl_term_cd,cups_settle_curr_cd, " +
				"cups_settle_at, cups_settle_dt, cups_trace_num, cups_sys_dt, trans_xid, " +
				"rsp_cd, settle_dt, term_sn, rec_crt_ts, rec_upd_ts) " +
				"select trans_seq_id, mchnt_cd,agent_cd, order_id, ext_trans_dt, ext_trans_tm, " +
				"int_trans_cd, acc_no, trans_at, trans_chnl,chnl_mchnt_cd,chnl_term_cd,cups_settle_curr_cd, " +
				"cups_settle_at, cups_settle_dt, cups_trace_num, cups_sys_dt, trans_xid, " +
				"rsp_cd, settle_dt, term_sn, rec_crt_ts, rec_upd_ts from " + transLogTbl
				+ " where int_trans_cd in('0100','0121','0122','0131','0132','0133','0134','5210','5211')"
				+ " AND rec_crt_ts >= '" + new Timestamp(DateUtil.str8ToDate(transDt).getTime()) + "' and rec_crt_ts < '"+ new Timestamp(DateUtil.str8ToDate(DateUtil.nextDay(DateUtil.str8ToDate(transDt))).getTime()) +"'";
		
		logger.info("extract start:" + transDt);
		this.jdbcTemplate.execute(sql);
		//删除已经冲正的原交易
		String deleteCorrectSql=" DELETE FROM `tbl_acct_chk_trans_log` WHERE trans_seq_id IN (SELECT trans_seq_id FROM tbl_order"+mon+" WHERE state = '5')";
		this.jdbcTemplate.execute(deleteCorrectSql);
		logger.info("extract end:" + transDt);
	}*/
	
	private void extract(String transDt) {
		String mon = transDt.substring(4, 6);
		String transLogTbl = "tbl_trans_log" + mon;
		String sql = "insert into tbl_acct_chk_trans_log " +
				"(trans_seq_id, mchnt_cd,agent_cd,order_id, ext_trans_dt, ext_trans_tm, " +
				"int_trans_cd, acc_no, trans_at, trans_chnl,chnl_mchnt_cd," +
				"chnl_order_id, chnl_trans_id, " + 
				"rsp_cd, rec_crt_ts, rec_upd_ts) " +
				"select trans_seq_id, mchnt_cd,agent_cd, order_id, ext_trans_dt, ext_trans_tm, " +
				"int_trans_cd, acc_no, trans_at, trans_chnl,chnl_mchnt_cd, " +
				"chnl_order_id,chnl_trans_id, " +
				"rsp_cd, rec_crt_ts, rec_upd_ts from " + transLogTbl
				+ " where int_trans_cd in('0100','0121','0122','0131','0132','0133','0134','5210','5211')"
				+ " AND rec_crt_ts >= '" + new Timestamp(DateUtil.str8ToDate(transDt).getTime()) + "' and rec_crt_ts < '"+ new Timestamp(DateUtil.str8ToDate(DateUtil.nextDay(DateUtil.str8ToDate(transDt))).getTime()) +"'";
		
		logger.info("extract start:" + transDt);
		this.jdbcTemplate.execute(sql);
		logger.info("extract end:" + transDt);
	}

	@Override
	protected String getTaskNm() {
		return "数据抽取任务";
	}
}
