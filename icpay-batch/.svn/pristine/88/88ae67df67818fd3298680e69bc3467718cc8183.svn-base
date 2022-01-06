package com.icpay.payment.batch.task.acctChk;
import static org.apache.commons.lang.StringUtils.isEmpty;

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
import com.icpay.payment.common.constants.Constant.CHANNEL;
import com.icpay.payment.common.utils.FileLineHandler;
import com.icpay.payment.common.utils.FileUtil;
import com.icpay.payment.common.utils.StringUtil;

@Component("TVPayFileChkTask")
public class TVPayFileChkTask extends BatchTaskTemplate {
		
	private static final String query_mchntIds = "select mchnt_cd from tbl_virtual_term_info where chnl_id='01'";
	private static final String insert_tbl_chnl_virtual_mer_settle_info = "insert into tbl_chnl_virtual_mer_settle_info (chnl_id,mchnt_cd,settle_dt,trans_num,trans_at,settle_at,fee_at,file_path,state,last_oper_id,rec_crt_ts,rec_upd_ts) values(?,?,?,?,?,?,?,?,'0','TVPayFileChkTask',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";

	@Override
	protected void doTask() {
		List<String> merIds =getMchntIds();
		logger.info("待清算的渠道虚拟商户"+merIds);
		if(merIds==null||merIds.isEmpty()){
			throw new RuntimeException("未发现渠道清算商户");
		}
		String basePath = BatchConfigCache.getConfig(BatchConstants.CONF_KEY_YST_MER_CHK_FILE_PATH);
		String charSet = BatchConfigCache.getConfig(BatchConstants.CONF_KEY_YST_CHK_FILE_CHARSET);
		for(String merId: merIds){
			
			String filePath = basePath+batchDt + File.separator+merId+"_"+batchDt+"_COM.txt";
			logger.info("检查商户对账文件["+filePath+"]");
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
			int txnNum = 0;
			BigDecimal totalTxnAmt = new BigDecimal(0);
			BigDecimal totalFee = new BigDecimal(0);

			for (int i = 14; i < content.size();  i ++) {
				String line = content.get(i);
				if(isEmpty(line)){
					continue;
				}
				String[] fields = StringUtil.split(line);
				logger.info(Arrays.toString(fields));
				if("消费".equals(fields[4])){
					totalTxnAmt=totalTxnAmt.add(new BigDecimal(fields[5]));
					totalFee=totalFee.add(new BigDecimal(fields[6]));
					txnNum++;
				}
				
			}
			insertChnlVirMchntSettleInfo(merId,txnNum,totalTxnAmt.movePointRight(2),totalFee.movePointRight(2),filePath);
			
		}

	}
	
	public void insertChnlVirMchntSettleInfo(final String merId,final int txnNum, final BigDecimal totalTxnAmt, final BigDecimal totalFee, final String filePath){
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(insert_tbl_chnl_virtual_mer_settle_info);
				ps.setString(1, CHANNEL._01);
				ps.setString(2, merId);
				ps.setString(3, batchDt);
				ps.setInt(4, txnNum);
				ps.setBigDecimal(5, totalTxnAmt);
				ps.setBigDecimal(6, totalTxnAmt.subtract(totalFee));
				ps.setBigDecimal(7, totalFee);
				ps.setString(8, filePath);
				return ps;
			}
		});
	}

	@Override
	protected String getTaskNm() {
		return "下载核对银视通对账文件任务";
	}
	
	private List<String> getMchntIds(){
		return this.jdbcTemplate.queryForList(query_mchntIds, String.class);
	}
	


}

