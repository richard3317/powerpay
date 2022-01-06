package com.icpay.payment.batch.task.transfer;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.data.dao.MerAccountInfoMapper;
import com.icpay.payment.common.data.model.MerAccountInfo;
import com.icpay.payment.common.data.model.MerAccountInfoKey;
import com.icpay.payment.common.data.svc.DAO;
import com.icpay.payment.common.data.utils.TxnDataUtils;
import com.icpay.payment.common.enums.CurrencyEnums.CurrType;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.service.MerParams;

/**
 * TODO : 结转功能需改为多币别支持
 */
@Component("D0BalanceTransferTask")
public class D0BalanceTransferTask extends BalanceTransferBaseTask {

	
	//  //查詢所有需要D0進行結轉的商戶號
	//SELECT
	//	t.mchnt_cd,t.balance_transfer,t.balance_transfer_t1
	//FROM
	//	tbl_mer_settle_policy AS t
	//	JOIN tbl_mchnt_info AS m ON t.mchnt_cd = m.mchnt_cd 
	//WHERE
	//	(t.`balance_transfer` = '1' OR t.`balance_transfer_t1` = '1')  
	//	AND m.mchnt_st = '1' 
	//	AND t.mchnt_cd NOT IN ( 
	//		SELECT
	//			tr.mchnt_cd
	//		FROM
	//			tbl_balance_transfer_log tr 
	//		WHERE
	//			tr.transfer_dt = ?
	//			AND tr.trans_chnl = '00' 
	//			AND tr.d0_result = '1'
	//	)
	
	/**
	 * 查詢所有需要進行結轉的商戶號
	 */
	private static final String query_settle_policy=
	  //"SELECT t.mchnt_cd,t.transfer_time,t.balance_transfer,t.balance_transfer_t1 FROM tbl_mer_settle_policy AS t JOIN tbl_mchnt_info AS m ON t.mchnt_cd = m.mchnt_cd WHERE (t.`balance_transfer` = '1' OR t.`balance_transfer_t1` = '1') AND m.mchnt_st = '1' AND t.mchnt_cd NOT IN ( SELECT tr.mchnt_cd FROM tbl_balance_transfer_log tr WHERE tr.transfer_dt = ? AND tr.trans_chnl = '00' AND tr.d0_result = '1' )" ;
			"SELECT t.mchnt_cd, \r\n"
			+ "       t.transfer_time, \r\n"
			+ "       t.balance_transfer, \r\n"
			+ "       t.balance_transfer_t1 \r\n"
			+ "FROM   tbl_mer_settle_policy AS t \r\n"
			+ "       join tbl_mchnt_info AS m \r\n"
			+ "         ON t.mchnt_cd = m.mchnt_cd \r\n"
			+ "WHERE  ( t .`balance_transfer` = '1' \r\n"
			+ "          OR t .`balance_transfer_t1` = '1' ) \r\n"
			+ "			 AND t.curr_cd = ? \r\n"
			+ "       AND m.mchnt_st = '1' \r\n"
			+ "       AND t.mchnt_cd NOT IN (SELECT tr.mchnt_cd \r\n"
			+ "                              FROM   tbl_balance_transfer_log tr \r\n"
			+ "                              WHERE  tr.transfer_dt = ? \r\n"
			+ "                                     AND tr.trans_chnl = '00' \r\n"
			+ "										AND tr.curr_cd = t.curr_cd \r\n"
			+ "                                     AND tr.d0_result = '1') \r\n"
			+ "";
	
//	/**
//	 * 查詢虛擬帳戶
//	 */
	//private static final String query_vaild_mchnt_account_info ="SELECT t.`mchnt_cd`,t.`available_balance`,t.`frozen_t1_balance`,t.`dayTxnAmt`,t.`dayTxnCount`,t.`dayTxnFee`,t.`dayWithdrawAmt`,t.`dayWithdrawCount`,t.`dayWithdrawFee` FROM tbl_mer_account_info t WHERE t.`mchnt_cd` =? AND t.state='1'";
	
	@Override
	protected void doTask() {
//		String taskTime = MerParams.getParam("00",DEFAULT_MER, TRANSFER_CAT , TRANSFER_D0TIME, "235000");
//		if (! this.shouldRunTask(taskTime)) return ;
		
		String defaultTaskTime =
				MerParams.getParam("00",DEFAULT_MER, TRANSFER_CAT , TRANSFER_D0TIME, "000000");
//		//查詢所有需要進行結轉的商戶號
//		//List<String> mchntList = jdbcTemplate.queryForList(query_settle_policy,new Object[]{this.batchDt}, String.class);
//		List<Map<String, Object>> mchntList = jdbcTemplate.queryForList(query_settle_policy,new Object[]{this.batchDt, "156"});
//		if (! TxnDataUtils.hasRecord(mchntList)) {
//			info("渠道：%s 日期：%s， 无任何商户符合结转条件!","00", this.batchDt);
//			return;
//		}
//		else {
//			debug("渠道：%s 日期：%s， %s 開始。。。","00", this.batchDt, this.getTaskNm());
//		}
//		
		for (CurrType currency :CurrType.values())
			doTaskForCurrency(defaultTaskTime, currency);
		
	}

	private void doTaskForCurrency(String defaultTaskTime, CurrType currency) {
		
		//查詢所有需要進行結轉的商戶號
		//List<String> mchntList = jdbcTemplate.queryForList(query_settle_policy,new Object[]{this.batchDt}, String.class);
		List<Map<String, Object>> mchntList = jdbcTemplate.queryForList(query_settle_policy,new Object[]{currency.getCode(),this.batchDt});
		if (! TxnDataUtils.hasRecord(mchntList)) {
			info("渠道:%s, 日期:%s, 币别:%s, 无任何商户符合结转条件!", "00", this.batchDt, currency.getCode());
			return;
		}
		else {
			debug("渠道:%s, 日期:%s, 币别:%s, %s 開始。。。", "00", this.batchDt, currency.getCode(), this.getTaskNm());
		}
		
		
		String taskTime;
		for(Map<String, Object> mchntInfo : mchntList) {
			String mchnt = ""+mchntInfo.get("mchnt_cd");
			String trans_d0 = ""+mchntInfo.get("balance_transfer");
			String trans_t1 = ""+mchntInfo.get("balance_transfer_t1");
			taskTime = strVal(mchntInfo.get("transfer_time"));
			if (Utils.isEmpty(taskTime)) taskTime = defaultTaskTime;
			
			boolean shouldRun= this.shouldRunTask(taskTime, "00", mchnt);
			debug("[檢查執行時間] 渠道：%s, 商戶號：%s, 日期：%s, 币别:%s, 任務：%s; 執行時間：%s, 執行與否：%s;", "00", mchnt, this.batchDt, currency.getCode(), this.getTaskNm(), taskTime, shouldRun);
			if (! shouldRun) continue; //非执行时间
			
			//查詢虛擬帳戶
			MerAccountInfoMapper dao = DAO.getMerAccountInfoMapper();
			MerAccountInfo mer = dao.selectByPrimaryKey(new MerAccountInfoKey(mchnt,currency.getCode()));
			//MerAccountInfo mer = jdbcTemplate.queryForObject(query_vaild_mchnt_account_info, new Object[]{mchnt}, new MerAccountInfo());
			if(mer != null ) {
				String opType = Constant.OPERTYPE._51 ;
				String d0Amount =  ""+mer.getAvailableBalance();
				if (!"1".equals(trans_d0))
					d0Amount="0";				
				//String t1Amount =  ""+mer.getFrozenT1Balance();
				//Long nextTtTransAmt = add(d0Amount, t1Amount);
				String memo = String.format("商戶: %s, 结转金额: %s, 操作: %s", mchnt, d0Amount, opType);
				info(memo);
				String taskResult="";
				try {
					this.chnlMerAccService().adjust("00", mchnt, opType, d0Amount, currency.getCode(), currency.getDefaultUnit(), this.getTaskNm(), memo);
					taskResult="1";
				} catch (Exception e) {
					taskResult="2";
					error(e, "结转任务发生失败! 商戶:%s, 结转金额:%s, 操作:%s", mchnt, d0Amount, opType);
				}
				//插入余额结转结果表
				info("插入余额结转结果表，执行结果：" + taskResult);
				insertBalanceTransferD0Result("00", mchnt, d0Amount, currency.getCode(), currency.getDefaultUnit(), taskResult, trans_t1, this.getTaskNm(), opType);
			}
			else {
				warn(String.format("无效商户: %s", mchnt));
			}
		}
	}
	
	@Override
	protected String getTaskNm() {
		return "商户D0余额结转任务";
	}
}
