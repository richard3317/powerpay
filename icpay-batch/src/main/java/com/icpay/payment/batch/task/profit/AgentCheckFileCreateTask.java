package com.icpay.payment.batch.task.profit;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import com.icpay.payment.batch.cache.BatchConfigCache;
import com.icpay.payment.batch.constants.BatchConstants;
import com.icpay.payment.batch.task.BatchTaskTemplate;
import com.icpay.payment.common.enums.SettleEnums.SettlePeriod;
import com.icpay.payment.common.jdbc.bo.AgentAccCheckFile;
import com.icpay.payment.common.jdbc.bo.SettleProfitResult;
import com.icpay.payment.common.jdbc.mapper.SettleProfitResultMapper;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.FileUtil;
import com.icpay.payment.common.utils.StringUtil;

@Component("agentCheckFileCreateTask")
public class AgentCheckFileCreateTask extends BatchTaskTemplate {
	private static final String query_agent_cds_2 = "select distinct agent_cd from tbl_mchnt_info join tbl_mer_settle_policy on tbl_mchnt_info.mchnt_cd=tbl_mer_settle_policy.mchnt_cd where tbl_mchnt_info.mchnt_st='1' and (tbl_mer_settle_policy.settle_period=? or tbl_mer_settle_policy.settle_period=?)";
	private static final String query_agent_cds_1 = "select distinct agent_cd from tbl_mchnt_info join tbl_mer_settle_policy on tbl_mchnt_info.mchnt_cd=tbl_mer_settle_policy.mchnt_cd where tbl_mchnt_info.mchnt_st='1' and tbl_mer_settle_policy.settle_period=?";
    private static final String query_tbl_settle_profit_result = "select trans_seq_id,settle_dt,agent_cd,mchnt_cd,term_sn,order_id,ext_trans_dt,ext_trans_tm,int_trans_cd,acc_no,trans_at,settle_at,fee_at,profit_at,mchnt_rate,agent_rate,profit_st from tbl_settle_profit_result where agent_cd=? and settle_dt=? order by mchnt_cd";
	
	
	private static final String insert_tbl_agent_acc_check_file = "insert into tbl_agent_acc_check_file (agent_cd,settle_dt,trans_num,trans_at,settle_at,fee_at,file_path,last_oper_id,comment,rec_crt_ts,rec_upd_ts) values(?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
	
	private static final String[] header = new String[]{"商户号","交易日期时间","订单号","查询流水号","交易代码","卡号","交易金额（分）","清算金额（分）","手续费（分）"};
	
	@Override
	protected void doTask() {
		List<String> agentCds = queryAgentCds2();
		for(String agentCd:agentCds){
			if(!StringUtil.isEmpty(agentCd)){
				createFile(agentCd,batchDt);
			}	
		}
		logger.info("已生成T+0/1代理商对账文件["+agentCds.size()+"]个");
		
		agentCds = queryAgentCds1(SettlePeriod._T2.getCode());
		String settleDt = DateUtil.changeDays(DateUtil.str8ToDate(batchDt), -1);
		for(String agentCd:agentCds){
			if(!StringUtil.isEmpty(agentCd)){
				createFile(agentCd,settleDt);
			}	
		}
		logger.info("已生成T+2代理商对账文件["+agentCds.size()+"]个");
		
		agentCds = queryAgentCds1(SettlePeriod._T3.getCode());
		settleDt = DateUtil.changeDays(DateUtil.str8ToDate(batchDt), -2);
		for(String agentCd:agentCds){
			if(!StringUtil.isEmpty(agentCd)){
				createFile(agentCd,settleDt);
			}	
		}
		logger.info("已生成T+3代理商对账文件["+agentCds.size()+"]个");
	}
	
	private void createFile(String agentCd,String settleDt){
		List<SettleProfitResult> settleLogs = querySettleProfitResult(agentCd,settleDt);
		AgentAccCheckFile agentAccCheckFile = new AgentAccCheckFile();
		agentAccCheckFile.setAgent_cd(agentCd);
		agentAccCheckFile.setSettle_dt(settleDt);
		int transNum = 0;
		BigDecimal transAmt = new BigDecimal(0);
		BigDecimal settleAmt = new BigDecimal(0);
		BigDecimal feeAmt = new BigDecimal(0);
		List<String[]> fileContent = new ArrayList<String[]>(settleLogs.size());
		fileContent.add(header);
		for(SettleProfitResult settleLog:settleLogs){
			String[] line = new String[9];
			line[0] = settleLog.getMchnt_cd();
			line[1] = settleLog.getExt_trans_dt()+settleLog.getExt_trans_tm();
			line[2] = settleLog.getOrder_id();
			line[3] = settleLog.getTrans_seq_id();
			line[4] = settleLog.getInt_trans_cd();
			
			String cardNum = settleLog.getAcc_no();
			line[5] = StringUtil.mask(cardNum, 6, cardNum.length()-4, '*');
			line[6] = settleLog.getTrans_at().toString();
			line[7] = settleLog.getSettle_at().toString();
			line[8] = settleLog.getFee_at().toString();
			fileContent.add(line);
			transNum++;
			transAmt = transAmt.add(settleLog.getTrans_at());
			settleAmt= settleAmt.add(settleLog.getSettle_at());
			feeAmt = feeAmt.add(settleLog.getFee_at());
		}
		agentAccCheckFile.setTrans_num(transNum);
		agentAccCheckFile.setTrans_at(transAmt);
		agentAccCheckFile.setSettle_at(settleAmt);
		agentAccCheckFile.setFee_at(feeAmt);
		String filePath = storeToFile(agentCd,settleDt,fileContent);
		agentAccCheckFile.setFile_path(filePath);
		agentAccCheckFile.setLast_oper_id("batchTask");
		insertAgentAccCheckFile(agentAccCheckFile);
		logger.info("生成"+agentAccCheckFile);
	}
	
	@Override
	protected String getTaskNm() {
		return "代理商对账文件生成任务";
	}
	


	public List<String> queryAgentCds2() {
		List<String> result = jdbcTemplate.queryForList(query_agent_cds_2, new Object[]{SettlePeriod._T0.getCode(),SettlePeriod._T1.getCode()}, String.class);
		return result;
	}

	public List<String> queryAgentCds1(String settlePeriod) {
		List<String> result = jdbcTemplate.queryForList(query_agent_cds_1, new Object[]{settlePeriod}, String.class);
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SettleProfitResult> querySettleProfitResult(String agentCd,String settleDt) {
		List<SettleProfitResult> result = jdbcTemplate.query(query_tbl_settle_profit_result, new Object[]{agentCd,settleDt},new SettleProfitResultMapper());
		return result;
	}
	
	private String storeToFile(String agentCd,String settleDt,List<String[]> lines){
		String dir = BatchConfigCache.getConfig(BatchConstants.CONF_KEY_AGENT_CHECK_FILE_PATH);
		String filePath = dir + batchDt + File.separator + "AGENT_ACC_CHECK_"+settleDt+"_"+agentCd;
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
	
	private void insertAgentAccCheckFile(final AgentAccCheckFile agentAccCheckFile){
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(insert_tbl_agent_acc_check_file);
				ps.setString(1, agentAccCheckFile.getAgent_cd());
				ps.setString(2, agentAccCheckFile.getSettle_dt());
				ps.setInt(3, agentAccCheckFile.getTrans_num());
				ps.setBigDecimal(4, agentAccCheckFile.getTrans_at());
				ps.setBigDecimal(5, agentAccCheckFile.getSettle_at());
				ps.setBigDecimal(6, agentAccCheckFile.getFee_at());
				ps.setString(7, agentAccCheckFile.getFile_path());
				ps.setString(8, agentAccCheckFile.getLast_oper_id());
				ps.setString(9, agentAccCheckFile.getComment());
				return ps;
			}
		});
	}
}

