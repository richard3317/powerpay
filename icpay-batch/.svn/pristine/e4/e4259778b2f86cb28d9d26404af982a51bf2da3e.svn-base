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
import com.icpay.payment.common.enums.SettleEnums.SettleResFileState;
import com.icpay.payment.common.jdbc.bo.AgentProfitLog;
import com.icpay.payment.common.jdbc.bo.ProfitResFile;
import com.icpay.payment.common.jdbc.mapper.AgentProfitLogMapper;
import com.icpay.payment.common.utils.FileUtil;

@Component("agentProfitResCreateTask")
public class AgentProfitResCreateTask extends BatchTaskTemplate {
		
	
	private static final String query_tbl_agent_profit_task_log = "select agent_cd,profit_account,profit_account_name,profit_account_area_info,profit_account_bank_name,profit_account_bank_code,profit_dt,profit_period,trans_num,trans_at,profit_at,file_path,state, last_oper_id,comment,rec_crt_ts,rec_upd_ts from tbl_agent_profit_task_log where profit_dt=? and profit_at!=0 and state='0'";
	private static final String update_tbl_agent_profit_task_log_in_process = "update tbl_agent_profit_task_log set state='1',rec_upd_ts=CURRENT_TIMESTAMP where profit_dt=? and state='0'";
	private static final String update_tbl_agent_profit_task_log_processed = "update tbl_agent_profit_task_log set state='2',rec_upd_ts=CURRENT_TIMESTAMP where profit_dt=? and state='1'";
	private static final String insert_tbl_profit_res_file = "insert into tbl_profit_res_file (profit_dt,profit_num,trans_at,profit_at,file_path,state, last_oper_id,comment,rec_crt_ts,rec_upd_ts) values(?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
	private static final String[] TITLE = new String[]{"代理商代码","分润账号","分润账号户名","分润账号开户行地址","分润账号开户行名称","分润账号开户行联行号","分润日期","交易总笔数","交易总金额（分）","分润金额（分）"};
	@Override
	protected void doTask() {
		 List<AgentProfitLog> agentProfitLogs = queryProfitLogs();
	     logger.info("待生成划款文件的分润记录："+ agentProfitLogs);
	     updateProfitLogInProcess();
		 int transNum = 0;
		 BigDecimal transAt = new BigDecimal(0);
		 BigDecimal profitAt = new BigDecimal(0);
	 
		 List<String[]> contents = new ArrayList<String[]>(agentProfitLogs.size()+1);
		 contents.add(TITLE);
		 for(AgentProfitLog agentProfitLog:agentProfitLogs){
			 contents.add(new String[]{agentProfitLog.getAgent_cd()
					 ,agentProfitLog.getProfit_account()
					 ,agentProfitLog.getProfit_account_bank_name()
					 ,agentProfitLog.getProfit_account_area_info()
					 ,agentProfitLog.getProfit_account_bank_name()
					 ,agentProfitLog.getProfit_account_bank_code()
					 ,agentProfitLog.getProfit_dt()
					 ,String.valueOf(agentProfitLog.getTrans_num())
					 ,agentProfitLog.getTrans_at().toString()
					 ,agentProfitLog.getProfit_at().toString()}); 
			 transNum+=agentProfitLog.getTrans_num();
			 transAt = transAt.add(agentProfitLog.getTrans_at());
			 profitAt = profitAt.add(agentProfitLog.getProfit_at());
		 }
		 String filePath = storeToFile(contents); 
		 ProfitResFile profitResFile = new ProfitResFile();
		 
		
		 profitResFile.setProfit_dt(batchDt);
		 profitResFile.setProfit_num(transNum);
		 profitResFile.setTrans_at(transAt);
		 profitResFile.setProfit_at(profitAt);
		
		 profitResFile.setFile_path(filePath);
		 profitResFile.setState(SettleResFileState._0.getCode());
		 profitResFile.setLast_oper_id("BatchTask");
		 insertProfitResFile(profitResFile);
		 
		 updateProfitLogProcessed();
	}

	@Override
	protected String getTaskNm() {
		return "代理商分润划款文件生成任务";
	}
	
	private String storeToFile(List<String[]> lines){
		String dir = BatchConfigCache.getConfig(BatchConstants.CONF_KEY_AGENT_PROFIT_RES_FILE_PATH);
		String filePath = dir + batchDt + File.separator + "agent_profit_res_"+batchDt+".csv";
		List<String> content = new ArrayList<String>(lines.size());
		for(String[] line:lines){
			String tmp = Arrays.toString(line);
			content.add(tmp.substring(1,tmp.length()-1));
		}
		try {
			if(FileUtil.newFile(filePath,true)){
				FileUtil.writeStrsToFile(filePath, content, "GBK");
				return filePath;
			}else{
				throw new RuntimeException("创建文件["+filePath+"]失败");
			}
		} catch (Exception e) {
			throw new RuntimeException("写入文件["+filePath+"]失败",e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<AgentProfitLog> queryProfitLogs() {
		return jdbcTemplate.query(
				query_tbl_agent_profit_task_log, new Object[] {batchDt},
				new AgentProfitLogMapper());

	}
	
	private void insertProfitResFile(final ProfitResFile profitResFile){
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(insert_tbl_profit_res_file);
				ps.setString(1, profitResFile.getProfit_dt());
				ps.setInt(2, profitResFile.getProfit_num());
				ps.setBigDecimal(3, profitResFile.getTrans_at());
				ps.setBigDecimal(4, profitResFile.getProfit_at());
				ps.setString(5, profitResFile.getFile_path());
				ps.setString(6, profitResFile.getState());
				ps.setString(7, profitResFile.getLast_oper_id());
				ps.setString(8, profitResFile.getComment());
				return ps;
			}
		});
	}
	
	public int updateProfitLogInProcess(){
		return this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(update_tbl_agent_profit_task_log_in_process);
				ps.setString(1, batchDt);
				return ps;
			}
		});
	}
	
	public int updateProfitLogProcessed(){
		return this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(update_tbl_agent_profit_task_log_processed);
				ps.setString(1, batchDt);
				return ps;
			}
		});
	}


}

