package com.icpay.payment.batch.task.rpt;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.BatchTaskTemplate;
import com.icpay.payment.common.entity.Monitor;
import com.icpay.payment.common.exception.BizzException;

@Component("transStatExtractTask")
public class TransStatExtractTask extends BatchTaskTemplate {

	@Override
	protected void doTask() {
		String sql = "select count(*) from tbl_trans_stat where stat_dt=?";
		int count = jdbcTemplate.queryForObject(sql, Integer.class, new Object[] { this.batchDt });
		
		if (count > 0) {
			throw new BizzException("该统计日交易统计中间信息已抽取，请先清空");
		}
		
		// 抽取三天交易进行对账
		extract(this.batchDt);
		
		// 打印抽取结果
		final StringBuilder sb = new StringBuilder("\n****抽取结果*****\n");
		sql = "select sum(trans_num_sum) as trans_num_sum, sum(trans_at_sum) as trans_at_sum from tbl_trans_stat where stat_dt='" + this.batchDt + "'";
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				sb.append("总笔数:" + rs.getLong("trans_num_sum") + ",总金额:" + (rs.getBigDecimal("trans_at_sum") == null ? "0" : rs.getBigDecimal("trans_at_sum")));
			}
		});
		logger.info(sb.toString());
	}
	
	private void extract(String transDt) {
		String mon = transDt.substring(4, 6);
		String transLogTbl = "tbl_trans_log" + mon;
		String sql = "insert into tbl_trans_stat " +
				" (mchnt_cd, int_trans_cd, curr_cd, trans_chnl, rsp_cd, term_sn, stat_dt, trans_num_sum, trans_at_sum) " +
				" select mchnt_cd, int_trans_cd, curr_cd, trans_chnl, rsp_cd, term_sn, ext_trans_dt, " +
				" count(*) as trans_num_sum, sum(trans_at) as trans_at_sum from " + transLogTbl + 
				" where ext_trans_dt = '" + transDt + "' " +
				" group by mchnt_cd, int_trans_cd, curr_cd, trans_chnl, rsp_cd, term_sn, ext_trans_dt ";
		
		logger.info("extract trans_stat start:" + transDt);
		Monitor m = new Monitor();
		this.jdbcTemplate.execute(sql);
		logger.info("extract trans_stat end:" + transDt + ", use time:" + (m.execTm() / 1000) + "s");
	}

	@Override
	protected String getTaskNm() {
		return "交易统计中间信息抽取任务";
	}
}
