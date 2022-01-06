package com.icpay.payment.batch.task.profit;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.BatchTaskTemplate;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitQuery;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TxnLogProfitQuery;
import com.icpay.payment.db.service.ITxnLogViewService;
import com.icpay.payment.service.MerParams;

@Component("agentProfitQueryCreateTask")
public class AgentProfitQueryCreateTask extends BatchTaskTemplate {
	
	private static final String query_tbl_agent_profit_policy = "SELECT DISTINCT p.agent_cd, p.int_trans_cd, p.mchnt_cd, p.chnl_id, p.chnl_mchnt_cd, p.rate FROM tbl_agent_profit_policy p JOIN tbl_agent_account_info i ON p.agent_cd = i.agent_cd JOIN tbl_agent_info o ON i.agent_cd = o.agent_cd WHERE i.state = '1' AND p.state = '1' AND o.deposit = '1'";	
	
	private static final String insert_tbl_agent_profit_query = "INSERT INTO tbl_agent_profit_query (settle_date, agent_cd, int_trans_cd, mchnt_cd, chnl_id, chnl_mchnt_cd, txn_amt_sum, rate, agent_profit, comment, last_oper_id, rec_crt_ts, rec_upd_ts) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

	protected boolean shouldRunTask(String targetTime) {
		String timeErr = MerParams.getParam("00", Consts.DEFAULT_MER, Consts.CAT_DAILY, Consts.PNAME_TIME_ERR, "600");
		boolean r=false;
		try {
			r = Utils.isTimeEqualNow(targetTime, Integer.parseInt(timeErr));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	@Override
	protected void doTask() {
		String taskTime = 
			MerParams.getParam("00", Consts.DEFAULT_MER, Consts.CAT_DAILY, Consts.PNAME_TASK_TIME_AGENT_PROFIT_QUERY, "050000");
		if (shouldRunTask(taskTime)) {
			//取得begin日期、end日期
			String settleDate = DateUtil.yesterday8();
			Date dateS = DateUtil.parseDate(settleDate, DateUtil.DATE_FORMAT_8);
			String beginDate = DateUtil.dateToStr19(dateS).substring(0,10);
			String now = DateUtil.now8();
			Date dateN = DateUtil.parseDate(now, DateUtil.DATE_FORMAT_8);
			String endDate = DateUtil.dateToStr19(dateN).substring(0,10);
			//取得月份
			String monthStr = settleDate.substring(4, 6);
			List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(query_tbl_agent_profit_policy);
			logger.info("代理商基本资料查询共: " + queryForList.size() + " 笔");
			if (queryForList != null && queryForList.size() != 0) {
				for (Map<String, Object> map : queryForList) {
					String agentCd = (map.containsKey("agent_cd")) ? map.get("agent_cd").toString() : "";
					String intTransCd = (map.containsKey("int_trans_cd")) ? map.get("int_trans_cd").toString() : "";
					String rate = (map.containsKey("rate")) ? map.get("rate").toString() : "";
					map.put("begin_date", beginDate);
					map.put("end_date", endDate);
					ITxnLogViewService svc = this.getDBService(ITxnLogViewService.class);
					List<TxnLogProfitQuery> result = svc.selectForAgentProfitQueryCreateTaske(monthStr, map);
					if (result != null && !result.isEmpty()) {
						logger.info("代理商[" + agentCd + "]开始分润" + intTransCd + "数据");
						
						//交易量加总
						long txnAmtSum = 0L;
						for (TxnLogProfitQuery pq : result) {
							txnAmtSum = txnAmtSum + pq.getTxnAmSum();
						}
						
						TxnLogProfitQuery view = result.get(0);
						BigDecimal agent_profit = new BigDecimal(0);
						AgentProfitQuery agentProfitQuery = new AgentProfitQuery();
						agentProfitQuery.setSettleDate(StringUtils.trim(settleDate));
						agentProfitQuery.setAgentCd(StringUtils.trim(agentCd));
						agentProfitQuery.setIntTransCd(StringUtils.trim(view.getIntTransCd()));
						agentProfitQuery.setMchntCd(StringUtils.trim(view.getMchntCd()));
						agentProfitQuery.setChnlId(StringUtils.trim(view.getTransChnl()));
						agentProfitQuery.setChnlMchntCd(StringUtils.trim(view.getChnlMchntCd()));
						agentProfitQuery.setTxnAmtSum(txnAmtSum);
						agentProfitQuery.setRate(StringUtils.trim(rate));
						agentProfitQuery.setAgentProfit(agent_profit.longValue());
						
						//计算中人分润
						BigDecimal txnAmtSumB = new BigDecimal(txnAmtSum);
						BigDecimal rateToMultiply = new BigDecimal(agentProfitQuery.getRate());
						agentProfitQuery.setAgentProfit(Math.round(txnAmtSumB.multiply(rateToMultiply).doubleValue()));
						
						insertAgentProfitQuery(agentProfitQuery);
						logger.info("处理完成"+agentProfitQuery);
						
					}
				}
			}
		}
	}
	
	@Override
	protected String getTaskNm() {
		return "代理商分润数据生成任务";
	}
	
	private void insertAgentProfitQuery(final AgentProfitQuery agentProfitQuery){
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(insert_tbl_agent_profit_query);
				ps.setString(1, agentProfitQuery.getSettleDate());
				ps.setString(2, agentProfitQuery.getAgentCd());
				ps.setString(3, agentProfitQuery.getIntTransCd());
				ps.setString(4, agentProfitQuery.getMchntCd());
				ps.setString(5, agentProfitQuery.getChnlId());
				ps.setString(6, agentProfitQuery.getChnlMchntCd());
				ps.setLong(7, agentProfitQuery.getTxnAmtSum());
				ps.setString(8, agentProfitQuery.getRate());
				ps.setLong(9, agentProfitQuery.getAgentProfit());
				ps.setString(10, agentProfitQuery.getComment());
				ps.setString(11, "agentProfitQueryCreateJob");
				return ps;
			}
		});
	}
	
	protected <T> T getDBService(Class<T> clazz) {
		return DBHessionServiceClient.getService(clazz);
	}
	
}

