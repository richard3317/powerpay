package com.icpay.payment.batch.task.settle;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import com.icpay.payment.batch.cache.BatchConfigCache;
import com.icpay.payment.batch.constants.BatchConstants;
import com.icpay.payment.batch.task.BatchTaskTemplate;
import com.icpay.payment.common.constants.Constant.INTER_MSG;
import com.icpay.payment.common.constants.Constant.MSG;
import com.icpay.payment.common.constants.Constant.OPERTYPE;
import com.icpay.payment.common.enums.SettleEnums.SettlePeriod;
import com.icpay.payment.common.enums.SettleEnums.SettleResFileState;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.jdbc.bo.SettleResFile;
import com.icpay.payment.common.jdbc.bo.SettleTask;
import com.icpay.payment.common.jdbc.mapper.SettleTaskMapper;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.FileUtil;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.service.MerAccService;

@Component("settleTnResCreateTask")
public class SettleTnResCreateTask extends BatchTaskTemplate {
		
	
	private static final String query_tbl_mer_settle_task_log = "select seq_id,mchnt_cd,settle_account,settle_account_name,settle_account_area_info,settle_account_bank_name,settle_account_bank_code,settle_dt,settle_bt,settle_period,trans_num,trans_at,settle_at,fee_at,file_path,state from tbl_mer_settle_task_log where settle_period=? and settle_dt=? and settle_at!=0 and state='2'";
	private static final String update_tbl_mer_settle_task_log = "update tbl_mer_settle_task_log set state='3',rec_upd_ts=CURRENT_TIMESTAMP where mchnt_cd=? and settle_period=? and settle_dt=? and settle_bt=? and state='2'";
	private static final String insert_tbl_settle_res_file = "insert into tbl_settle_res_file (settle_dt,settle_bt,settle_num,trans_at,settle_at,fee_at,file_path,state, last_oper_id,comment,rec_crt_ts,rec_upd_ts) values(?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
	private static final String[] TITLE = new String[]{"商户号","商户清算账号","商户清算账号户名","商户清算账号开户行地址","商户清算账号开户行名称","商户清算账号开户行联行号","清算日期","交易笔数","交易金额（分）","清算金额（分）","手续费（分）"};
	@Override
	protected void doTask() {
		 List<SettleTask> settleTasks = new ArrayList<SettleTask>();
		 settleTasks.addAll(querySettleTask(batchDt,SettlePeriod._T1.getCode()));
		 settleTasks.addAll(querySettleTask(preBatchDt,SettlePeriod._T2.getCode()));
		 settleTasks.addAll(querySettleTask(DateUtil.preDay(DateUtil.parseDate8(preBatchDt)),SettlePeriod._T3.getCode()));
		 logger.info("待生成划款文件的清算记录："+ settleTasks);
		 
		 int settleNum = 0;
		 BigDecimal transAt = new BigDecimal(0);
		 BigDecimal settleAt = new BigDecimal(0);
		 BigDecimal feeAt = new BigDecimal(0);
		 
		 List<String[]> contents = new ArrayList<String[]>(settleTasks.size()+1);
		 contents.add(TITLE);
		 for(SettleTask settleTask:settleTasks){
			 contents.add(new String[]{settleTask.getMchnt_cd()
					 ,settleTask.getSettle_account()
					 ,settleTask.getSettle_account_name()
					 ,settleTask.getSettle_account_area_info()
					 ,settleTask.getSettle_account_bank_name()
					 ,settleTask.getSettle_account_bank_code()
					 ,settleTask.getSettle_dt()
					 ,String.valueOf(settleTask.getTrans_num())
					 ,settleTask.getTrans_at().toString()
					 ,settleTask.getSettle_at().toString()
					 ,settleTask.getFee_at().toString()}); 
			 settleNum+=settleTask.getTrans_num();
			 transAt = transAt.add(settleTask.getTrans_at());
			 settleAt = settleAt.add(settleTask.getSettle_at());
			 feeAt = feeAt.add(settleTask.getFee_at());
		 }
		 String filePath = storeToFile(contents); 
		 
		 for(SettleTask settleTask:settleTasks){
			 if(settleTask.getSettle_at().compareTo(BigDecimal.ZERO)==1){
					MerAccService accService = ServiceProxy.getService(MerAccService.class);
					Map<String,String> accReq = new HashMap<String,String>();
					accReq.put(MSG.merId, settleTask.getMchnt_cd());
					accReq.put(MSG.txnAmt, settleTask.getFee_at().toString());
					accReq.put(INTER_MSG.accOperType, OPERTYPE._28);
					accReq.put(INTER_MSG.note, settleTask.getSettle_dt()+"日交易手续费扣除");
					accReq.put(MSG.queryId, "");
					accReq.put(INTER_MSG.operId, "batchService");
					accService.reduceFee(accReq);
					
					accReq.put(MSG.txnAmt, settleTask.getSettle_at().toString());
					accReq.put(INTER_MSG.accOperType, OPERTYPE._29);
					accReq.put(INTER_MSG.note, settleTask.getSettle_dt()+"日清算资金扣除");
					accService.settle(accReq);
					if(updateSettleTaskInSettled(settleTask)!=1){
						throw new BizzException("更新"+settleTask+"已划款失败");
					}
					
					logger.info("扣除商户["+settleTask.getMchnt_cd()+"]账户手续费、清算金额成功");
			 }		 
		 }
		 logger.info("===操作商户资金账户完成 ===");
		 
		 SettleResFile settleResFile = new SettleResFile();
		 settleResFile.setSettle_dt(batchDt);
		 settleResFile.setSettle_bt(0);
		 settleResFile.setSettle_num(settleNum);
		 settleResFile.setTrans_at(transAt);
		 settleResFile.setSettle_at(settleAt);
		 settleResFile.setFee_at(feeAt);
		 settleResFile.setFile_path(filePath);
		 settleResFile.setState(SettleResFileState._0.getCode());
		 settleResFile.setLast_oper_id("BatchTask");
		 insertSettleResFile(settleResFile);
	}

	@Override
	protected String getTaskNm() {
		return "T+n清算划款文件生成任务";
	}
	
	private String storeToFile(List<String[]> lines){
		String dir = BatchConfigCache.getConfig(BatchConstants.CONF_KEY_SETTLE_FILE_PATH);
		String filePath = dir + batchDt + File.separator + "SETTLE_Tn_"+batchDt+".csv";
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
	public List<SettleTask> querySettleTask(String settleDate,String settlePeriod) {
		return jdbcTemplate.query(
				query_tbl_mer_settle_task_log, new Object[] {settlePeriod, settleDate},
				new SettleTaskMapper());

	}
	
	private void insertSettleResFile(final SettleResFile settleResFile){
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(insert_tbl_settle_res_file);

				ps.setString(1, settleResFile.getSettle_dt());
				ps.setInt(2, settleResFile.getSettle_bt());

				ps.setInt(3, settleResFile.getSettle_num());
				ps.setBigDecimal(4, settleResFile.getTrans_at());
				ps.setBigDecimal(5, settleResFile.getSettle_at());
				ps.setBigDecimal(6, settleResFile.getFee_at());
				ps.setString(7, settleResFile.getFile_path());
				ps.setString(8, settleResFile.getState());
				ps.setString(9, settleResFile.getLast_oper_id());
				ps.setString(10, settleResFile.getComment());
				return ps;
			}
		});
	}
	
	public int updateSettleTaskInSettled(final SettleTask settleTask){
		return this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(update_tbl_mer_settle_task_log);
				ps.setString(1, settleTask.getMchnt_cd());
				ps.setString(2, settleTask.getSettle_period());
				ps.setString(3, settleTask.getSettle_dt());
				ps.setString(4, String.valueOf(settleTask.getSettle_bt()));
				return ps;
			}
		});

	}

	public static void main(String[] args){
		System.out.println(BigDecimal.ZERO);
		System.out.println(new BigDecimal("-1"));
	}
}

