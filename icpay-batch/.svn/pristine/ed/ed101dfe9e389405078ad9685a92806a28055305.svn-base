package com.icpay.payment.batch.task.daily;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.BatchTaskTemplate;
import com.icpay.payment.common.data.svc.DAO;
import com.icpay.payment.common.data.svc.TransactionContext;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.DailyProfitResult;
import com.icpay.payment.service.MerParams;

@Component("dailyProfitResultTask")
public class DailyProfitResultTask  extends BatchTaskTemplate {
	
	
	protected boolean shouldRunTask(String targetTime) {
		String timeErr = MerParams.getParam("00", Consts.DEFAULT_MER, Consts.CAT_DAILY_PROFIT, Consts.PNAME_TIME_ERR, "600");
		boolean r=false;
		try {
			r = Utils.isTimeEqualNow(targetTime, Integer.parseInt(timeErr));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	/*
	 * INSERT INTO icpay.tbl_mer_params (chnl_id,mchnt_cd,param_cat,param_id,param_value,orderSeq,param_desc,last_oper_id) VALUES ('00','#DEFAULT#','DailyProfit','taskTime','023000',100,'分润报表','vicky');
	 */
	private static final String mchnt_profit_count="select count(*) from tbl_daily_profit_result t where t.settle_date = ? ";
	private static final String query_chnl_txn_result="select\n" + 
			"	t.settle_date as settleDate,\n" + 
			"	t.chnl_id as chnlId,\n" + 
			"	t.int_trans_cd as intTransCd,\n" + 
			"	sum( t.txn_amt_sum ) as chnlTxnAmtSum\n" + 
//			"	sum( t.agent_profit ) as chnlAgentProfit\n" + 
			"from\n" + 
			"	view_daily_txn_sum_result t\n" + 
			"where\n" + 
			"	t.settle_date = ?\n" + 
			"group by\n" + 
			"	t.chnl_id,\n" + 
			"	t.int_trans_cd";
	private static final String query_chnl_profit_result="select\n" + 
			"	t.settle_date as settleDate,\n" + 
			"	t.chnl_id as chnlId,\n" + 
			"	t.int_trans_cd as intTransCd,\n" + 
//			"	sum( t.txn_amt_sum ) as chnlTxnAmtSum,\n" + 
			"	sum( t.agent_profit ) as chnlAgentProfit\n" + 
			"from\n" + 
			"	view_daily_profit_sum_result_new t\n" + 
			"where\n" + 
			"	t.settle_date = ? \n" + 
			"group by\n" + 
			"	t.chnl_id,\n" + 
			"	t.int_trans_cd";
	private static final String query_mchnt_profit_result="select t.settle_date as settleDate,t.chnl_id as chnlId,t.int_trans_cd as intTransCd,t.mchnt_cd as mchntCd,t.mchnt_cn_nm as mchntCnNm,t.txn_amt_sum as txnAmtSum,t.agent_profit as agentProfit, t.chnl_txn_amt_sum as chnlTxnAmtSum, t.chnl_agent_profit as chnlAgentProfit from tbl_daily_profit_result t where t.settle_date = ? ";
	private static final String insert_tbl_daily_profit_result="INSERT INTO tbl_daily_profit_result (settle_date,chnl_id,int_trans_cd,mchnt_cd,mchnt_cn_nm,txn_amt_sum,agent_profit,txn_amt_sum_percent,chnl_txn_amt_sum,chnl_agent_profit,chnl_txn_amt_sum_percent) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	private static final String query_daily_profit_sum_result="SELECT\n" + 
			"	`t`.`settle_date` AS `settleDate`,\n" + 
			"	`t`.`chnl_id` AS `chnlId`,\n" + 
			"	`t`.`chnl_desc` AS `chnlDesc`,\n" + 
			"	`t`.`int_trans_cd` AS `intTransCd`,\n" + 
			"	`t`.`mchnt_cd` AS `mchntCd`,\n" + 
			"	`t`.`mchnt_cn_nm` AS `mchntCnNm`,\n" + 
//			"	`t`.`txn_amt_sum` AS `txnAmtSum`,\n" + 
			"	`t`.`agent_profit` AS `agentProfit` \n" + 
			"FROM\n" + 
			"	`view_daily_profit_sum_result_new` `t`\n" + 
			"WHERE\n" + 
			"	`t`.`settle_date` = ?";
	private static final String query_daily_txn_sum_result="SELECT\n" + 
			"	`t`.`settle_date` AS `settleDate`,\n" + 
			"	`t`.`chnl_id` AS `chnlId`,\n" + 
			"	`t`.`chnl_desc` AS `chnlDesc`,\n" + 
			"	`t`.`int_trans_cd` AS `intTransCd`,\n" + 
			"	`t`.`mchnt_cd` AS `mchntCd`,\n" + 
			"	`t`.`mchnt_cn_nm` AS `mchntCnNm`,\n" + 
			"	`t`.`txn_amt_sum` AS `txnAmtSum`\n" + 
//			"	`t`.`agent_profit` AS `agentProfit` \n" + 
			"FROM\n" + 
			"	`view_daily_txn_sum_result` `t`\n" + 
			"WHERE\n" + 
			"	`t`.`settle_date` = ?";
	
	@Override
	protected void doTask() {
		String taskTime = MerParams.getParam("00", Consts.DEFAULT_MER, Consts.CAT_DAILY_PROFIT, Consts.PNAME_TASK_TIME, "023000");
		if (shouldRunTask(taskTime)) {
//		if (true) {
			info(String.format("每日财务报表， 参数: settleDate=%s; taskTime=%s", this.batchDt, taskTime));
			int count = 0 ;
			//当天的财报
			int profitCount = jdbcTemplate.queryForObject(mchnt_profit_count,new Object[]{this.batchDt}, Integer.class);
			if(profitCount != 0) {
				info("财务报表已存在");
				return;
			}
			//获取当日的报表
			List<DailyProfitResult> profitList = getTodayProfitList(this.batchDt);
			info("[%s]的利润的记录数：%s", this.batchDt, profitList.size());
			List<DailyProfitResult> chnlTxnResultList = getTodayChnlResult(this.batchDt);
			info("[%s]的渠道利润的记录数：%s", this.batchDt, chnlTxnResultList.size());
			
			List<DailyProfitResult> todayList = bulidProfitList(profitList, chnlTxnResultList);
			info("[%s]的交易利润的记录数：%s", this.batchDt, todayList.size());
			
			//前一天的财报
			List<DailyProfitResult> yesterdayList = jdbcTemplate.query(query_mchnt_profit_result,new Object[]{this.preBatchDt}, new BeanPropertyRowMapper<DailyProfitResult>(DailyProfitResult.class));
			
			if(Utils.isEmpty(yesterdayList)) {
				List<DailyProfitResult> preprofitList = getTodayProfitList(this.preBatchDt);
				info("[%s]的交易利润的记录数：%s" ,this.preBatchDt, preprofitList.size());
				List<DailyProfitResult> prechnlTxnResultList = getTodayChnlResult(this.preBatchDt);
				info("[%s]的渠道利润的记录数：%s" ,this.preBatchDt, prechnlTxnResultList.size());
				yesterdayList = bulidProfitList(preprofitList, prechnlTxnResultList);
			}
			
			info("[%s]的交易利润的记录数：%s" , this.preBatchDt, yesterdayList.size());
			 
			TransactionContext tx = DAO.beginTrans();
			this.info("Begin transaction ...");
			try {
				for(DailyProfitResult result : todayList) {
					if("5210".equals(result.getIntTransCd())) {
						result.setTxnAmtSum(0L);
						result.setChnlTxnAmtSum(0L);
						result.setTxnAmtSumPercent("--");
						result.setChnlTxnAmtSumPercent("--");
					}else {
						for(DailyProfitResult result1 : yesterdayList) {
							if(result.getChnlId().equals(result1.getChnlId()) && 
									result.getMchntCnNm().equals(result1.getMchntCnNm())
									&& result.getIntTransCd().equals(result1.getIntTransCd())) {
								if(Utils.isEmpty(result.getTxnAmtSum())|| Utils.isEmpty(result1.getTxnAmtSum()) 
											|| result.getTxnAmtSum().equals(0L) || result1.getTxnAmtSum().equals(0L)) {
									result.setTxnAmtSumPercent("--");
								}else {
	//								result.setTxnAmtSumPercent(String.format("%.2f",(result.getTxnAmtSum().divide(result1.getTxnAmtSum(), 2, BigDecimal.ROUND_HALF_UP))));
									result.setTxnAmtSumPercent(String.format("%.2f",(result.getTxnAmtSum().doubleValue()/(result1.getTxnAmtSum().doubleValue()))));
								}
							}
							
							if(result.getChnlId().equals(result1.getChnlId())
									&& result.getIntTransCd().equals(result1.getIntTransCd())
									) {
									if(Utils.isEmpty(result.getChnlTxnAmtSum())|| Utils.isEmpty(result1.getChnlTxnAmtSum()) 
											|| result.getChnlTxnAmtSum().equals(0L) || result1.getChnlTxnAmtSum().equals(0L)) {
									result.setChnlTxnAmtSumPercent("--");
								}else {
	//								result.setChnlTxnAmtSumPercent(String.format("%.2f",(result.getChnlTxnAmtSum().divide(result1.getChnlTxnAmtSum(), 2, BigDecimal.ROUND_HALF_UP))));
									result.setChnlTxnAmtSumPercent(String.format("%.2f",(result.getChnlTxnAmtSum().doubleValue()/(result1.getChnlTxnAmtSum().doubleValue()))));
								}
							}
						}
					
						if(Utils.isEmpty(result.getTxnAmtSumPercent()))
							result.setTxnAmtSumPercent("--");
						
						if(Utils.isEmpty(result.getChnlTxnAmtSumPercent()))
							result.setChnlTxnAmtSumPercent("--");
					}
					//插入每日财务报表结果表
					insertDailyProfitResult(result);
					count ++ ;
				}
			}catch (Exception e) {
				info("Rollback transaction ..." + e);
				DAO.rollback(tx);
				return;
			}
			this.info("Commit transaction ...");
			DAO.commit(tx);
			this.info("end transaction ...");
			info("执行利润条数：" + count);
			info("每日财务报表结束");
		}
	}

	/**
	 * 组合小商户的list 和 渠道的list
	 * @param profitList
	 * @param chnlTxnResultList
	 * @return
	 */
	private List<DailyProfitResult> bulidProfitList(List<DailyProfitResult> profitList,
			List<DailyProfitResult> chnlTxnResultList) {
		// 组合小商户的list 和 渠道的list
//		List<DailyProfitResult> retList = new ArrayList<DailyProfitResult>();
		
		for(DailyProfitResult result : profitList) {
			for(DailyProfitResult result1 : chnlTxnResultList) {
				if(result.getChnlId().equals(result1.getChnlId())
						 && result.getIntTransCd().equals(result1.getIntTransCd())
						) {
					result.setChnlTxnAmtSum(result1.getChnlTxnAmtSum());
					result.setChnlAgentProfit(result1.getChnlAgentProfit());
				}
			}
		}
		
		return profitList;
	}

	@SuppressWarnings("unchecked")
	private List<DailyProfitResult> getTodayProfitList(String batchDt) {
		// 获取当日的报表
		List<DailyProfitResult> txnResultList = jdbcTemplate.query(query_daily_txn_sum_result, new Object[]{batchDt}, new BeanPropertyRowMapper<DailyProfitResult>(DailyProfitResult.class));
		info("[%s]的交易金额的记录数：%s", batchDt, txnResultList.size());
		List<DailyProfitResult> profitResultList = jdbcTemplate.query(query_daily_profit_sum_result, new Object[]{batchDt}, new BeanPropertyRowMapper<DailyProfitResult>(DailyProfitResult.class));
		info("[%s]的交易利润的记录数：%s" ,batchDt, profitResultList.size());
		
		List<DailyProfitResult> resultList = new ArrayList<DailyProfitResult>();
//		for(int i = 0 ; i< txnResultList.size() ; i++ ) {
		for(int i = txnResultList.size()-1 ; i>=0 ; i-- ) {
			DailyProfitResult txnResult = txnResultList.get(i);
			for(int j= 0 ; j< profitResultList.size() ; j++) {
				DailyProfitResult profitResult = profitResultList.get(j);
				
				if(txnResult.getChnlId().equals(profitResult.getChnlId()) 
						&& txnResult.getIntTransCd().equals(profitResult.getIntTransCd())
						&& txnResult.getMchntCnNm().equals(profitResult.getMchntCnNm())) {
					txnResult.setAgentProfit(profitResult.getAgentProfit());
					resultList.add(txnResult);
					txnResultList.remove(i);
					profitResultList.remove(j);
					break;
				}
			}
		}
		
		resultList = mergerLists(resultList, txnResultList);
		resultList = mergerLists(resultList, profitResultList);
		
		return resultList;
	}

	@SuppressWarnings("unchecked")
	private List<DailyProfitResult> getTodayChnlResult(String batchDt) {
		List<DailyProfitResult> chnlTxnResultList = jdbcTemplate.query(query_chnl_txn_result,new Object[]{batchDt}, new BeanPropertyRowMapper<DailyProfitResult>(DailyProfitResult.class));
		info("[%s]的渠道交易利润的记录数：%s" , batchDt , chnlTxnResultList.size());
		List<DailyProfitResult> chnlProfitResultList = jdbcTemplate.query(query_chnl_profit_result,new Object[]{batchDt}, new BeanPropertyRowMapper<DailyProfitResult>(DailyProfitResult.class));
		info("[%s]的渠道交易利润的记录数：%s" , batchDt , chnlProfitResultList.size());
		List<DailyProfitResult> resultList = new ArrayList<DailyProfitResult>();
		for(int i = chnlTxnResultList.size()-1 ; i>=0 ; i-- ) {
			DailyProfitResult chnlTxnResult = chnlTxnResultList.get(i);
			for(int j= 0 ; j< chnlProfitResultList.size() ; j++) {
				DailyProfitResult chnlProfitResult = chnlProfitResultList.get(j);
				if(chnlTxnResult.getChnlId().equals(chnlProfitResult.getChnlId()) 
						&& chnlTxnResult.getIntTransCd().equals(chnlProfitResult.getIntTransCd())
						) {
					chnlTxnResult.setChnlAgentProfit(chnlProfitResult.getChnlAgentProfit());
					resultList.add(chnlTxnResult);
					chnlTxnResultList.remove(i);
					chnlProfitResultList.remove(j);
					break;
				}
			}
		}
		resultList = mergerLists(resultList, chnlTxnResultList);
		resultList = mergerLists(resultList, chnlProfitResultList);
		
		return resultList;
	}

	@Override
	protected String getTaskNm() {
		return "每日财务报表";
	}
	
	private void insertDailyProfitResult(final DailyProfitResult rec){
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(insert_tbl_daily_profit_result);
					ps.setString(1, rec.getSettleDate());
					ps.setString(2, rec.getChnlId());
					ps.setString(3, rec.getIntTransCd());
					ps.setString(4, rec.getMchntCd());
					ps.setString(5, rec.getMchntCnNm());
//					ps.setBigDecimal(6, rec.getTxnAmtSum());
					ps.setLong(6, rec.getTxnAmtSum());
//					ps.setBigDecimal(7, rec.getAgentProfit());
					ps.setLong(7, rec.getAgentProfit());
					ps.setString(8, rec.getTxnAmtSumPercent());
//					ps.setBigDecimal(9, rec.getChnlTxnAmtSum());
					ps.setLong(9, rec.getChnlTxnAmtSum());
//					ps.setBigDecimal(10, rec.getChnlAgentProfit());
					ps.setLong(10, rec.getChnlAgentProfit());
					ps.setString(11, rec.getChnlTxnAmtSumPercent());
					return ps;
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List mergerLists(List... lists) {
		if (lists == null) {
			return new ArrayList();
		}
		List rlist = new ArrayList();
		for (List l : lists) {
			if (l != null) {
				for (int i = 0; i < l.size(); i++) {
					rlist.add(l.get(i));
				}
			}
		}
		return rlist;
	}
	
}
