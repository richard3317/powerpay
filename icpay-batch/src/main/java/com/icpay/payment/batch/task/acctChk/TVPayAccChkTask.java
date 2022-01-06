package com.icpay.payment.batch.task.acctChk;

import static org.apache.commons.lang.StringUtils.isEmpty;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import com.icpay.payment.batch.cache.BatchConfigCache;
import com.icpay.payment.batch.constants.BatchConstants;
import com.icpay.payment.batch.task.BatchTaskTemplate;
import com.icpay.payment.common.constants.Constant.CHANNEL;
import com.icpay.payment.common.constants.Constant.INTER_MSG;
import com.icpay.payment.common.constants.Constant.MSG;
import com.icpay.payment.common.enums.SettleEnums.AccChkRes;
import com.icpay.payment.common.jdbc.bo.ChnlVirMchntSettleInfo;
import com.icpay.payment.common.jdbc.mapper.AccChkResMapper;
import com.icpay.payment.common.jdbc.mapper.AccChkTransLogMapper;
import com.icpay.payment.common.jdbc.mapper.ChnlVirMchntSettleInfoMapper;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.FileLineHandler;
import com.icpay.payment.common.utils.FileUtil;
import com.icpay.payment.common.utils.StringUtil;



@Component("TVPayAccChkTask")
public class TVPayAccChkTask extends BatchTaskTemplate {
		
	private static final String query_tbl_chnl_virtual_mer_settle_info = "select chnl_id,mchnt_cd,settle_dt,trans_num,trans_at,settle_at,fee_at,file_path,state from tbl_chnl_virtual_mer_settle_info where chnl_id=? and  settle_dt=? and  state='1' ";
	private static final String query_trans_log=        "select trans_seq_id,mchnt_cd,agent_cd,order_id,ext_trans_dt,ext_trans_tm,int_trans_cd,acc_no,trans_at,trans_chnl,chnl_mchnt_cd,chnl_term_cd,cups_trace_num,cups_sys_dt,trans_xid,rsp_cd,term_sn from tbl_acct_chk_trans_log where trans_chnl='01' and rsp_cd='0000000' and rec_crt_ts>=? and rec_crt_ts<?";
	private static final String query_pending_trans_log="select trans_seq_id,mchnt_cd,agent_cd,order_id,ext_trans_dt,ext_trans_tm,int_trans_cd,acc_no,trans_at,trans_chnl,chnl_mchnt_cd,chnl_term_cd,cups_trace_num,cups_sys_dt,trans_xid,term_sn from tbl_acct_chk_result where trans_chnl='01' and check_result='2'";
	private static final String insert_tbl_acct_chk_result_total = "insert into tbl_acct_chk_result_total (check_dt,chnl_id,mchnt_cd,check_result,file_path,rec_crt_ts,rec_upd_ts) values(?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
	private static final String insert_tbl_acct_chk_result = "insert into tbl_acct_chk_result (check_dt, trans_seq_id, mchnt_cd, order_id, ext_trans_dt, ext_trans_tm, int_trans_cd, acc_no, trans_at, trans_chnl, cups_trace_num, cups_sys_dt, trans_xid, term_sn, check_result,agent_cd,chnl_mchnt_cd,chnl_term_cd, rec_crt_ts, rec_upd_ts) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
	private static final String update_tbl_acct_chk_result = "update tbl_acct_chk_result set check_result='1',check_dt=?,rec_upd_ts=CURRENT_TIMESTAMP where trans_seq_id=?";
	
	@Override
	protected void doTask() {
		String charSet = BatchConfigCache.getConfig(BatchConstants.CONF_KEY_YST_CHK_FILE_CHARSET);
		Map<String,Map<String,String>> allTranLogs =  loadTransLog();
		Map<String,Map<String,String>> allPendingLogs =  loadPendingTransLog();
		for(String key:allTranLogs.keySet()){
			if(allPendingLogs.containsKey(key)){
//				if(allTranLogs.get(key).equals(allPendingLogs.get(key))){
					allPendingLogs.remove(key);
//				}else{
//					throw new RuntimeException("["+allTranLogs.get(key)+"]["+allPendingLogs.get(key)+"]渠道段关键要素重复，对账无法进行");
//				}
			}
			
		}
		
		
		List<ChnlVirMchntSettleInfo> mchntFiles = queryChnlVirMchntSettleInfo();
		List<Map<String,String>> checkLogs = new ArrayList<Map<String,String>>();
		List<Map<String,String>> checkPendingLogs = new ArrayList<Map<String,String>>();
		for(ChnlVirMchntSettleInfo mchntFile:mchntFiles ){
			String filePath = mchntFile.getFile_path();
			logger.info("开始勾兑渠道对账文件["+filePath+"]");
			File f = new File(filePath);
			if(!f.exists()){
				throw new RuntimeException("商户对账文件不存在["+f+"]");
			}
			
			final List<String> content = new ArrayList<String>();
			FileUtil.readFileByLine(f, charSet, new FileLineHandler() {
				@Override
				public void handleLine(String line) {
					content.add(line);
				}
			});
			
			List<String> errContent= new ArrayList<String>(); 
			errContent.add(content.get(12));
			for (int i = 14; i < content.size();  i ++) {
				String line = content.get(i);
				if(isEmpty(line)){
					continue;
				}
				String[] fields = StringUtil.split(line);
				String key = fields[2].substring(4)
						+"#"+fields[3]
						+"#"+fields[9]
						+"#"+fields[7]
						+"#"+(new BigDecimal(fields[5]).movePointRight(2).toString());

				if(allTranLogs.containsKey(key)){
					checkLogs.add(allTranLogs.remove(key));
					continue;
				}
				if(allPendingLogs.containsKey(key)){
					checkPendingLogs.add(allPendingLogs.remove(key));
					continue;
				}
				logger.warn("could not find key["+key+"] add to errFile");
				errContent.add(line);
			}
			String reFundFilePath = "";
			String checkRes = "1";
			if(errContent.size()>1){
				reFundFilePath = storeToRefundFile(mchntFile.getMchnt_cd(),errContent);
				checkRes = "0";
			}
			insertTotalCheckResult(mchntFile.getMchnt_cd(),checkRes,reFundFilePath);
		}

		
		if(!allTranLogs.isEmpty()){
			List<Map<String,String>> temp = new ArrayList<Map<String,String>>();
			temp.addAll(allTranLogs.values());
			batchSavePendingResult(temp);
		}
		
		if(!checkLogs.isEmpty()){
			batchSaveCheckedResult(checkLogs);
		}
		
		if(!checkPendingLogs.isEmpty()){
			batchUpdateCheckedResult(checkPendingLogs);
		}
		

	}
	
	private void batchSaveCheckedResult(final List<Map<String,String>> lst) {		
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
				ps.setString(10, CHANNEL._01);
				ps.setString(11, t.get(INTER_MSG.cupsTraceNo));
				ps.setString(12, t.get(INTER_MSG.cupsTraceTime));
				ps.setString(13, t.get(INTER_MSG.cupsQueryId));
				ps.setString(14, t.get(MSG.termSn));
				ps.setString(15, AccChkRes._1.getCode());
				ps.setString(16, t.get(INTER_MSG.agentCode));
				ps.setString(17, t.get(INTER_MSG.chnlMchntId));
				ps.setString(18, t.get(INTER_MSG.chnlTermId));
			}
			
			@Override
			public int getBatchSize() {
				return lst.size();
			}
		});
	}
	
	private void batchSavePendingResult(final List<Map<String,String>> lst) {		
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
				ps.setString(10, CHANNEL._01);
				ps.setString(11, t.get(INTER_MSG.cupsTraceNo));
				ps.setString(12, t.get(INTER_MSG.cupsTraceTime));
				ps.setString(13, t.get(INTER_MSG.cupsQueryId));
				ps.setString(14, t.get(MSG.termSn));
				ps.setString(15, AccChkRes._2.getCode());
				ps.setString(16, t.get(INTER_MSG.agentCode));
				ps.setString(17, t.get(INTER_MSG.chnlMchntId));
				ps.setString(18, t.get(INTER_MSG.chnlTermId));
			}
			
			@Override
			public int getBatchSize() {
				return lst.size();
			}
		});
	}
	
	private void batchUpdateCheckedResult(final List<Map<String,String>> lst) {		
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
	
	private void insertTotalCheckResult(final String virMchntId,final String res,final String errFile){
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(insert_tbl_acct_chk_result_total);
				ps.setString(1, batchDt);
				ps.setString(2, CHANNEL._01);
				ps.setString(3, virMchntId);
				ps.setString(4, res);
				ps.setString(5, errFile);
				return ps;
			}
		});
	}
	
	private String storeToRefundFile(String virMchntId,List<String> errContent){
		String dir = BatchConfigCache.getConfig(BatchConstants.CONF_KEY_REFUND_FILE_PATH);
		String filePath = dir + batchDt + File.separator + "ERR0_CHNL01_"+batchDt+"_"+virMchntId;
		try {
			if(FileUtil.newFile(filePath)){
				FileUtil.writeStrsToFile(filePath, errContent, "UTF-8");
				return filePath;
			}else{
				throw new RuntimeException("创建文件["+filePath+"]失败");
			}
		} catch (Exception e) {
			throw new RuntimeException("写入文件["+filePath+"]失败",e);
		}
	}
	
	private Map<String,Map<String,String>> loadTransLog(){
		List<Map<String,String>> allTransLogs =queryAllTransLogs();
		Map<String, Map<String,String>> res = new HashMap<String,Map<String,String>>(allTransLogs.size());
		for(Map<String,String> transLog:allTransLogs){
			String key = transLog.get(INTER_MSG.cupsTraceTime)
			+"#"+transLog.get(INTER_MSG.cupsTraceNo)
			+"#"+transLog.get(INTER_MSG.cupsQueryId)
			+"#"+StringUtil.mask(transLog.get(MSG.cardNo), 6, transLog.get(MSG.cardNo).length()-4, '*')
			+"#"+transLog.get(MSG.txnAmt);
			if(res.containsKey(key)){
				throw new RuntimeException("["+res.get(key)+"]["+transLog.get(MSG.queryId)+"]渠道段关键要素重复，对账无法进行");
			}
			res.put(key, transLog);
		}
		allTransLogs.clear();
		logger.info(batchDt+"日银视通通道成功交易["+res.size()+"]");
		return res;
	}
	
	private Map<String,Map<String,String>> loadPendingTransLog(){
		List<Map<String,String>> pendingTransLogs =queryPendingTransLogs();
		Map<String, Map<String,String>> res = new HashMap<String,Map<String,String>>(pendingTransLogs.size());
		for(Map<String,String> transLog:pendingTransLogs){
			String key = transLog.get(INTER_MSG.cupsTraceTime)
			+"#"+transLog.get(INTER_MSG.cupsTraceNo)
			+"#"+transLog.get(INTER_MSG.cupsQueryId)
			+"#"+StringUtil.mask(transLog.get(MSG.cardNo), 6, transLog.get(MSG.cardNo).length()-4, '*')
			+"#"+transLog.get(MSG.txnAmt);
			if(res.containsKey(key)){
				throw new RuntimeException("["+res.get(key)+"]["+transLog.get(MSG.queryId)+"]渠道段关键要素重复，对账无法进行");
			}
			res.put(key, transLog);
		}
		pendingTransLogs.clear();
		logger.info("挂账功交易["+res.size()+"]");
		return res;
	}
	

	@Override
	protected String getTaskNm() {
		return "银视通对账任务";
	}
	
	@SuppressWarnings("unchecked")
	public List<ChnlVirMchntSettleInfo> queryChnlVirMchntSettleInfo() {
		List<ChnlVirMchntSettleInfo> result = jdbcTemplate.query(query_tbl_chnl_virtual_mer_settle_info, new Object[]{CHANNEL._01,batchDt}, new ChnlVirMchntSettleInfoMapper());
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	private List<Map<String,String>> queryAllTransLogs() {
		return this.jdbcTemplate.query(query_trans_log, new Object[]{new Timestamp(DateUtil.str8ToDate(batchDt).getTime()),new Timestamp(DateUtil.str8ToDate(nextBatchDt).getTime())},new AccChkTransLogMapper());
	}
	
	@SuppressWarnings("unchecked")
	private List<Map<String,String>> queryPendingTransLogs() {
		return this.jdbcTemplate.query(query_pending_trans_log, new AccChkResMapper());
	}
	
	public static void main(String[] args){
		System.out.println(StringUtil.mask("123456789012", 6, "123456789012".length()-4, '*'));
	}
}

