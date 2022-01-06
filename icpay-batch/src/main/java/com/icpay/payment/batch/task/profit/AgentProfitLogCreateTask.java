package com.icpay.payment.batch.task.profit;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import com.icpay.payment.batch.cache.BatchConfigCache;
import com.icpay.payment.batch.constants.BatchConstants;
import com.icpay.payment.batch.task.BatchTaskTemplate;
import com.icpay.payment.common.enums.ProfitEnums.ProfitPeriod;
import com.icpay.payment.common.enums.ProfitEnums.ProfitState;
import com.icpay.payment.common.jdbc.bo.AgentProfitInfo;
import com.icpay.payment.common.jdbc.bo.AgentProfitLog;
import com.icpay.payment.common.jdbc.bo.SettleProfitResult;
import com.icpay.payment.common.jdbc.mapper.AgentProfitInfoMapper;
import com.icpay.payment.common.jdbc.mapper.SettleProfitResultMapper;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.FileUtil;
import com.icpay.payment.common.utils.StringUtil;

@Component("agentProfitLogCreateTask")
public class AgentProfitLogCreateTask extends BatchTaskTemplate {
		
	private static final String query_tbl_agent_profit_info = "select agent_cd,account_area_code,account_area_info,account_bank_name,account_bank_code,account_no,account_name,profit_period from tbl_agent_profit_info";
	
	private static final String query_tbl_settle_profit_result = "select trans_seq_id,settle_dt,agent_cd,mchnt_cd,term_sn,order_id,ext_trans_dt,ext_trans_tm,int_trans_cd,acc_no,trans_at,settle_at,fee_at,profit_at,mchnt_rate,agent_rate,profit_st from tbl_settle_profit_result where agent_cd=? and profit_st='0' and rec_crt_ts>=? and rec_upd_ts<? order by mchnt_cd";
	
	private static final String update_tbl_settle_profit_result_processed = "update tbl_settle_profit_result set profit_st='1'  where agent_cd=? and profit_st='0' and rec_crt_ts>=? and rec_upd_ts<?";
	
	private static final String insert_tbl_agent_profit_task_log = "insert into tbl_agent_profit_task_log (agent_cd,profit_account,profit_account_name,profit_account_area_info,profit_account_bank_name,profit_account_bank_code,profit_dt,profit_period,trans_num,trans_at,profit_at,file_path,state, last_oper_id,comment,rec_crt_ts,rec_upd_ts) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
	
	private static final String[] header = new String[]{"商户号","交易日期时间","订单号","查询流水号","交易代码","卡号","交易金额（分）","分润金额（分）"};
	
	@Override
	protected void doTask() {
		List<AgentProfitInfo> agentProfitInfos =  queryAgentProfitInfo();
		
		Calendar batchDay = Calendar.getInstance();
		batchDay.setTime(DateUtil.parseDate8(batchDt));
		for(AgentProfitInfo agentProfitInfo:agentProfitInfos){
			if(ProfitPeriod.DAY.getCode().equals(agentProfitInfo.getProfit_period())
					||(ProfitPeriod.WEEK.getCode().equals(agentProfitInfo.getProfit_period())&&Calendar.TUESDAY==batchDay.get(Calendar.DAY_OF_WEEK))
					||(ProfitPeriod.MONTH.getCode().equals(agentProfitInfo.getProfit_period())&&2==batchDay.get(Calendar.DAY_OF_MONTH))){
				AgentProfitLog agentProfitLog = new AgentProfitLog();
				
				Calendar beginCalender = Calendar.getInstance();
				beginCalender.setTimeInMillis(batchDay.getTimeInMillis());
				Calendar endCalender= Calendar.getInstance();
				endCalender.setTimeInMillis(batchDay.getTimeInMillis());
				if(ProfitPeriod.DAY.getCode().equals(agentProfitInfo.getProfit_period())){
					beginCalender.add(Calendar.DAY_OF_MONTH, -1);
				}else if(ProfitPeriod.WEEK.getCode().equals(agentProfitInfo.getProfit_period())){
					beginCalender.add(Calendar.DAY_OF_MONTH, -7);
				}else {
					beginCalender.add(Calendar.MONTH, -1);
				}
				Timestamp begin = new Timestamp(beginCalender.getTimeInMillis());
				Timestamp end = new Timestamp(endCalender.getTimeInMillis());
				logger.info("代理商["+agentProfitInfo.getAgent_cd()+"]分润周期["+agentProfitInfo.getProfit_period()+"]开始分润"+begin+"--"+end+"清算的交易流水");
				agentProfitLog.setAgent_cd(agentProfitInfo.getAgent_cd());
				agentProfitLog.setProfit_account(agentProfitInfo.getAccount_no());
				agentProfitLog.setProfit_account_name(agentProfitInfo.getAccount_name());
				agentProfitLog.setProfit_account_bank_code(agentProfitInfo.getAccount_bank_code());
				agentProfitLog.setProfit_account_bank_name(agentProfitInfo.getAccount_bank_name());
				agentProfitLog.setProfit_account_area_info(agentProfitInfo.getAccount_area_info());
				agentProfitLog.setProfit_dt(batchDt);
				agentProfitLog.setProfit_period(agentProfitInfo.getProfit_period());
				agentProfitLog.setState(ProfitState._0.getCode());

				int transNum = 0;
				BigDecimal transAmt = BigDecimal.ZERO;
				BigDecimal profitAmt = BigDecimal.ZERO;
				
				List<SettleProfitResult> result = querySettleProfitLogs(agentProfitLog.getAgent_cd(),begin,end);
				List<String[]> fileContent = new ArrayList<String[]>(result.size()+1);
				fileContent.add(header);
				for(SettleProfitResult p:result){
					fileContent.add(new String[]{p.getMchnt_cd(),
							p.getExt_trans_dt()+p.getExt_trans_tm(),
							p.getOrder_id(),
							p.getTrans_seq_id(),
							p.getInt_trans_cd(),
							StringUtil.mask(p.getAcc_no(), 6, p.getAcc_no().length()-4, '*'),
							p.getTrans_at().toString(),
							p.getProfit_at().toString()});
					transNum++;
					transAmt = transAmt.add(p.getTrans_at());
					profitAmt= profitAmt.add(p.getProfit_at());
				}
				String filePath = storeToFile(agentProfitLog.getAgent_cd(),fileContent);
				agentProfitLog.setTrans_num(transNum);
				agentProfitLog.setTrans_at(transAmt);
				agentProfitLog.setProfit_at(profitAmt);
				agentProfitLog.setFile_path(filePath);
				agentProfitLog.setLast_oper_id("batchService");
				agentProfitLog.setComment("");
				insertAgentProfitLog(agentProfitLog);
				updateSettleTaskInProc(agentProfitLog.getAgent_cd(),begin,end);
				logger.info("处理完成"+agentProfitLog);
			}else{
				logger.info("未到分润日期,代理商["+agentProfitInfo.getAgent_cd()+"]分润周期["+agentProfitInfo.getProfit_period()+"]");
			}	
		}
	}
	
	@Override
	protected String getTaskNm() {
		return "代理商分润文件生成任务";
	}
	

	@SuppressWarnings("unchecked")
	public List<AgentProfitInfo> queryAgentProfitInfo() {
		List<AgentProfitInfo> result = jdbcTemplate.query(query_tbl_agent_profit_info, new AgentProfitInfoMapper());
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<SettleProfitResult> querySettleProfitLogs(String agentCd,Timestamp begin,Timestamp end) {
		List<SettleProfitResult> result = jdbcTemplate.query(query_tbl_settle_profit_result, new Object[]{agentCd,begin,end}, new SettleProfitResultMapper());
		return result;
	}

	
	private void insertAgentProfitLog(final AgentProfitLog agentProfitLog){
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(insert_tbl_agent_profit_task_log);
				ps.setString(1, agentProfitLog.getAgent_cd());
				ps.setString(2, agentProfitLog.getProfit_account());
				ps.setString(3, agentProfitLog.getProfit_account_name());
				ps.setString(4, agentProfitLog.getProfit_account_area_info());
				ps.setString(5, agentProfitLog.getProfit_account_bank_name());
				ps.setString(6, agentProfitLog.getProfit_account_bank_code());
				ps.setString(7, agentProfitLog.getProfit_dt());
				ps.setString(8, agentProfitLog.getProfit_period());
				ps.setInt(9, agentProfitLog.getTrans_num());
				ps.setBigDecimal(10, agentProfitLog.getTrans_at());
				ps.setBigDecimal(11, agentProfitLog.getProfit_at());
				ps.setString(12, agentProfitLog.getFile_path());
				ps.setString(13, agentProfitLog.getState());
				ps.setString(14, agentProfitLog.getLast_oper_id());
				ps.setString(15, agentProfitLog.getComment());
				return ps;
			}
		});
	}
	
	
	private String storeToFile(String agentCd,List<String[]> lines){
		String dir = BatchConfigCache.getConfig(BatchConstants.CONF_KEY_AGENT_PROFIT_FILE_PATH);
		String filePath = dir + batchDt + File.separator + "AGENT_PROFIT_"+batchDt+"_"+agentCd;
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
	
	
	public int updateSettleTaskInProc(final String agentCd, final Timestamp begin,final Timestamp end){

		return this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(update_tbl_settle_profit_result_processed);
				ps.setString(1, agentCd);
				ps.setTimestamp(2, begin);
				ps.setTimestamp(3, end);
				return ps;
			}
		});

	}
}

