package com.icpay.payment.batch.task.acctChk;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.icpay.payment.batch.task.BatchTaskTemplate;


public abstract class ChnlFileChkBaseTask extends BatchTaskTemplate {

	private static final String insert_tbl_chnl_settle_info = "insert into tbl_chnl_virtual_mer_settle_info (chnl_id,mchnt_cd,settle_dt,trans_num,trans_at,settle_at,fee_at,file_path,state,last_oper_id,rec_crt_ts,rec_upd_ts) values(?,?,?,?,?,?,?,?,'0',?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
	private static final String delete_tbl_chnl_settle_info=" DELETE FROM tbl_chnl_virtual_mer_settle_info WHERE chnl_id=? AND settle_dt=? ";
	
	public void insertChnlSettleInfo(final String fileName,final int txnNum, final BigDecimal totalTxnAmt, final BigDecimal totalFee, final String filePath,final String chnlId,final String updateOpr){
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(insert_tbl_chnl_settle_info);
				ps.setString(1, chnlId);
				ps.setString(2, fileName.substring(0, fileName.lastIndexOf(".")));
				ps.setString(3, batchDt);
				ps.setInt(4, txnNum);
				ps.setBigDecimal(5, totalTxnAmt);
				ps.setBigDecimal(6, totalTxnAmt.subtract(totalFee));
				ps.setBigDecimal(7, totalFee);
				ps.setString(8, filePath);
				ps.setString(9, updateOpr);
				return ps;
			}
		});
	}

	public void deleteChnlSettleInfo(final String chnlId){
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(delete_tbl_chnl_settle_info);
				ps.setString(1, chnlId);
				ps.setString(2, batchDt);
				return ps;
			}
		});
	}

}
