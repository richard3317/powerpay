package com.icpay.payment.batch.task.acctChk;


import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.icpay.payment.batch.cache.BatchConfigCache;
import com.icpay.payment.batch.constants.BatchConstants;
import com.icpay.payment.batch.task.BatchTaskTemplate;
import com.icpay.payment.common.constants.Constant.INTER_MSG;
import com.icpay.payment.common.constants.Constant.MSG;
import com.icpay.payment.common.enums.SettleEnums.AccChkRes;
import com.icpay.payment.common.jdbc.bo.ChnlVirMchntSettleInfo;
import com.icpay.payment.common.jdbc.mapper.AccChkResMapper;
import com.icpay.payment.common.jdbc.mapper.AccChkTransLogMapper;
import com.icpay.payment.common.jdbc.mapper.ChnlVirMchntSettleInfoMapper;
import com.icpay.payment.common.utils.FileUtil;

public abstract class ChnlAccChkBaseTask extends BatchTaskTemplate {
	
	
	private static final String query_tbl_chnl_settle_info ="select chnl_id,mchnt_cd,settle_dt,trans_num,trans_at,settle_at,fee_at,file_path,state from tbl_chnl_virtual_mer_settle_info where chnl_id=? and  settle_dt=? and  state='1' ";
	private static final String query_trans_log="select trans_seq_id,mchnt_cd,agent_cd,order_id,ext_trans_dt,ext_trans_tm,int_trans_cd,acc_no,trans_at,trans_chnl,chnl_mchnt_cd,chnl_order_id as cups_trace_num,chnl_trans_id as trans_xid,rsp_cd ,cups_sys_dt , term_sn, chnl_term_cd from tbl_acct_chk_trans_log where trans_chnl= ? and rsp_cd='0000' AND int_trans_cd IN('0100','0121','0122','0131','0132','0133','0134')  and ext_trans_dt>=? and ext_trans_dt<?";
	private static final String query_pending_trans_log="select trans_seq_id,mchnt_cd,agent_cd,order_id,ext_trans_dt,ext_trans_tm,int_trans_cd,acc_no,trans_at,trans_chnl,chnl_mchnt_cd,chnl_order_id as cups_trace_num,chnl_trans_id as trans_xid,cups_sys_dt,term_sn, chnl_term_cd from tbl_acct_chk_result where trans_chnl =? AND int_trans_cd IN('0100','0121','0122','0131','0132','0133','0134') and check_result='2'";
	private static final String insert_tbl_acct_chk_result_total = "insert into tbl_acct_chk_result_total (check_dt,chnl_id,mchnt_cd,check_result,file_path,rec_crt_ts,rec_upd_ts) values(?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
	private static final String insert_tbl_acct_chk_result = "insert into tbl_acct_chk_result (check_dt, trans_seq_id, mchnt_cd, order_id, ext_trans_dt, ext_trans_tm, int_trans_cd, acc_no, trans_at, trans_chnl, chnl_order_id , chnl_trans_id , check_result,agent_cd,chnl_mchnt_cd, rec_crt_ts, rec_upd_ts) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
	private static final String update_tbl_acct_chk_result = "update tbl_acct_chk_result set check_result='1',check_dt=?,rec_upd_ts=CURRENT_TIMESTAMP where trans_seq_id=?";
	private static final String delete_tbl_acct_chk_result="DELETE FROM tbl_acct_chk_result WHERE trans_chnl= ? AND check_dt= ? "; 
	private static final String delete_tbl_acct_chk_result_total=" DELETE FROM tbl_acct_chk_result_total WHERE chnl_id= ? AND check_dt= ? ";
	private static final String query_withdraw_log="select trans_seq_id,mchnt_cd,agent_cd,order_id,ext_trans_dt,ext_trans_tm,int_trans_cd,acc_no,trans_at,trans_chnl,chnl_mchnt_cd,chnl_order_id as cups_trace_num,chnl_trans_id as trans_xid,rsp_cd ,cups_sys_dt , term_sn, chnl_term_cd from tbl_acct_chk_trans_log where trans_chnl= ? and rsp_cd='0000' AND int_trans_cd IN('5210','5211','5250') and ext_trans_dt>=? and ext_trans_dt<?";
	private static final String query_pending_withdraw_log="select trans_seq_id,mchnt_cd,agent_cd,order_id,ext_trans_dt,ext_trans_tm,int_trans_cd,acc_no,trans_at,trans_chnl,chnl_mchnt_cd,chnl_order_id as cups_trace_num,chnl_trans_id as trans_xid ,cups_sys_dt,term_sn, chnl_term_cd from tbl_acct_chk_result where trans_chnl =? AND int_trans_cd IN('5210','5211','5250') and check_result='2'";
	
	protected void batchSaveCheckedResult(final List<Map<String,String>> lst,final String chnlId) {		
		this.jdbcTemplate.batchUpdate(insert_tbl_acct_chk_result, new BatchPreparedStatementSetter() {			
			@Override
			public void setValues(PreparedStatement ps, int idx) throws SQLException {
				Map<String,String> t = lst.get(idx);
				ps.setString(1, batchDt);
				ps.setString(2, t.get(MSG.queryId));
				ps.setString(3, t.get(MSG.merId));
				ps.setString(4, t.get(MSG.orderId));
				ps.setString(5, t.get(MSG.txnTime).substring(0, 8));
				ps.setString(6, t.get(MSG.txnTime).substring(8));
				ps.setString(7, t.get(MSG.txnType)+t.get(MSG.txnSubType));
				ps.setString(8, t.get(MSG.cardNo));
				ps.setBigDecimal(9, new BigDecimal(t.get(MSG.txnAmt)));
				ps.setString(10, chnlId);
				ps.setString(11, t.get(INTER_MSG.cupsTraceNo));
//				ps.setString(12, t.get(INTER_MSG.cupsTraceTime));
				ps.setString(12, t.get(INTER_MSG.cupsQueryId));
//				ps.setString(14, t.get(MSG.termSn));
				ps.setString(13, AccChkRes._1.getCode());
				ps.setString(14, t.get(INTER_MSG.agentCode));
				ps.setString(15, t.get(INTER_MSG.chnlMchntId));
//				ps.setString(18, t.get(INTER_MSG.chnlTermId));
			}
			
			@Override
			public int getBatchSize() {
				return lst.size();
			}
		});
	}
	
	protected void batchSavePendingResult(final List<Map<String,String>> lst,final String chnlId) {		
		this.jdbcTemplate.batchUpdate(insert_tbl_acct_chk_result, new BatchPreparedStatementSetter() {			
			@Override
			public void setValues(PreparedStatement ps, int idx) throws SQLException {
				Map<String,String> t = lst.get(idx);
				ps.setString(1, batchDt);
				ps.setString(2, t.get(MSG.queryId));
				ps.setString(3, t.get(MSG.merId));
				ps.setString(4, t.get(MSG.orderId));
				ps.setString(5, t.get(MSG.txnTime).substring(0, 8));
				ps.setString(6, t.get(MSG.txnTime).substring(8));
				ps.setString(7, t.get(MSG.txnType)+t.get(MSG.txnSubType));
				ps.setString(8, t.get(MSG.cardNo));
				ps.setBigDecimal(9, new BigDecimal(t.get(MSG.txnAmt)));
				ps.setString(10, chnlId);
				ps.setString(11, t.get(INTER_MSG.cupsTraceNo));
//				ps.setString(12, t.get(INTER_MSG.cupsTraceTime));
				ps.setString(12, t.get(INTER_MSG.cupsQueryId));
//				ps.setString(14, t.get(MSG.termSn));
				ps.setString(13, AccChkRes._2.getCode());
				ps.setString(14, t.get(INTER_MSG.agentCode));
				ps.setString(15, t.get(INTER_MSG.chnlMchntId));
//				ps.setString(18, t.get(INTER_MSG.chnlTermId));
			}
			
			@Override
			public int getBatchSize() {
				return lst.size();
			}
		});
	}
	
	protected void batchUpdateCheckedResult(final List<Map<String,String>> lst) {		
		this.jdbcTemplate.batchUpdate(update_tbl_acct_chk_result, new BatchPreparedStatementSetter() {			
			@Override
			public void setValues(PreparedStatement ps, int idx) throws SQLException {
				Map<String,String> t = lst.get(idx);
				ps.setString(1, batchDt);
				ps.setString(2, t.get(MSG.queryId));
			}
			
			@Override
			public int getBatchSize() {
				return lst.size();
			}
		});
	}
	
	protected void insertTotalCheckResult(final String srcFileName,final String res,final String errFile,final String chnlId){
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(insert_tbl_acct_chk_result_total);
				ps.setString(1, batchDt);
				ps.setString(2, chnlId);
				ps.setString(3, srcFileName.substring(0, srcFileName.lastIndexOf("_")));
				ps.setString(4, res);
				ps.setString(5, errFile);
				return ps;
			}
		});
	}
	
	protected void delteTotalCheckResult(final String chnlId){
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(delete_tbl_acct_chk_result_total);
				ps.setString(1, chnlId);
				ps.setString(2, batchDt);
				
				return ps;
			}
		});
	}
	
	protected void delteCheckResult(final String chnlId){
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(delete_tbl_acct_chk_result);
				ps.setString(1, chnlId);
				ps.setString(2, batchDt);
				return ps;
			}
		});
	}
	
	protected String storeToRefundFile(String fileName,List<String> errContent,final String chnlId){
		String dir = BatchConfigCache.getConfig(BatchConstants.CONF_KEY_REFUND_FILE_PATH);
		String filePath = dir + batchDt + File.separator + "ERR0_CHNL"+chnlId+"_"+batchDt+"_"+fileName;
		try {
			if(FileUtil.newFile(filePath,true)){
				FileUtil.writeStrsToFile(filePath, errContent, "UTF-8");
				return filePath;
			}else{
				throw new RuntimeException("创建文件["+filePath+"]失败");
			}
		} catch (Exception e) {
			throw new RuntimeException("写入文件["+filePath+"]失败",e);
		}
	}
	
	

	
	@SuppressWarnings("unchecked")
	protected List<ChnlVirMchntSettleInfo> queryChnlSettleInfo(final String chnlId) {
		List<ChnlVirMchntSettleInfo> result = jdbcTemplate.query(query_tbl_chnl_settle_info, new Object[]{chnlId,batchDt}, new ChnlVirMchntSettleInfoMapper());
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	protected List<Map<String,String>> queryAllTransLogs(final String chnlId) {
		return this.jdbcTemplate.query(query_trans_log, new Object[]{chnlId,batchDt,nextBatchDt},new AccChkTransLogMapper());
	}
	
	@SuppressWarnings("unchecked")
	protected List<Map<String,String>> queryPendingTransLogs(final String chnlId) {
		return this.jdbcTemplate.query(query_pending_trans_log,new Object[]{chnlId}, new AccChkResMapper());
	}
	
	@SuppressWarnings("unchecked")
	protected List<Map<String,String>> queryAllWithdrawLogs(final String chnlId) {
		return this.jdbcTemplate.query(query_withdraw_log, new Object[]{chnlId,batchDt,nextBatchDt},new AccChkTransLogMapper());
	}
	
	@SuppressWarnings("unchecked")
	protected List<Map<String,String>> queryPendingWithdrawLogs(final String chnlId) {
		return this.jdbcTemplate.query(query_pending_withdraw_log,new Object[]{chnlId}, new AccChkResMapper());
	}
	
}
