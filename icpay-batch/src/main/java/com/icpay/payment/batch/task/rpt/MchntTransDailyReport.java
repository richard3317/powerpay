package com.icpay.payment.batch.task.rpt;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import com.icpay.payment.batch.cache.DBCache;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;

@Component("mchntTransDailyReport")
public class MchntTransDailyReport extends BaseDailyReportTask {
	
	@Override
	protected void initRptData() {
		this.addParam("S_DATE", batchDt);

		final Map<String, Map<String, Object>> statResult = new LinkedHashMap<String, Map<String,Object>>();
		String mon = batchDt.substring(4, 6);
		String transLogTbl = "tbl_trans_log" + mon;
		String statSql = "SELECT " + 
				"  t.`ext_trans_dt` AS transDt," + 
				"  t.`mchnt_cd` AS mchntCd," + 
				"  t.`int_trans_cd` AS intTransCd," + 
				"  SUM(t.`trans_at`) AS transAtTotal," + 
				"  SUM(t.`trans_fee`) AS feeTotal," + 
				"  COUNT(t.`trans_seq_id`) AS successTotal," + 
				"  CONCAT(ROUND(COUNT(t.`trans_seq_id`)/(SELECT COUNT(tl.`trans_seq_id`) FROM "+transLogTbl+" tl WHERE  tl.`ext_trans_dt`=t.`ext_trans_dt` AND tl.`trans_chnl`= t.`trans_chnl`)*100,2),'%')  AS dailySuccessRate," + 
				"  (SELECT ag.`agent_cn_nm` FROM `tbl_agent_info` ag WHERE t.`agent_cd` = ag.`agent_cd`) AS agentName " + 
				" FROM " + 
				transLogTbl+" t "+
				" WHERE t.`ext_trans_dt` = ? " + 
				" AND  t.`rsp_cd`='0000'" + 
				" GROUP BY t.`trans_chnl`,t.`mchnt_cd`,t.`int_trans_cd` ";
		this.jdbcTemplate.query(statSql, new Object[] { batchDt }, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				String mchntCd = StringUtil.trimStr(rs.getString("mchntCd"));
				Map<String, Object> m = null;
				if (statResult.containsKey(mchntCd)) {
					m = statResult.get(mchntCd);
				} else {
					m = initDataMap(mchntCd);
					statResult.put(mchntCd, m);
				}
				
				//总笔数
				BigDecimal successTotal = rs.getBigDecimal("successTotal");
				m.put("total_num", successTotal);
				
				//总金额
				BigDecimal transAtTotal = rs.getBigDecimal("transAtTotal");
				m.put("total_amt", transAtTotal);
				
				//交易类型
				String intTransCd = rs.getString("intTransCd");
				intTransCd = EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, true);
				m.put("int_trans_cd", intTransCd);
				
				//交易日期
				String transDt = rs.getString("transDt");
				m.put("trans_dt", transDt);
				
				//手续费总金额
				String feeTotal = rs.getString("feeTotal");
				m.put("total_fee", feeTotal);
				
				//日成功比
				String dailySuccessRate = rs.getString("dailySuccessRate");
				m.put("daily_success_rate", dailySuccessRate);
				
				//代理商
				String agentName = rs.getString("agentName");
				m.put("agent_name", agentName==null?"":agentName);
				
				
			}
		});
		
		for (String mchntCd : statResult.keySet()) {
			Map<String, Object> m = statResult.get(mchntCd);
			m.put("total_num", String.valueOf(m.get("total_num")));
			m.put("total_amt", StringUtil.formateAmt(String.valueOf(m.get("total_amt"))));
			m.put("total_fee", StringUtil.formateAmt(String.valueOf(m.get("total_fee"))));
			this.addData(m);
		}
	}

	@Override
	protected String getReportNm() {
		return "商户交易情况日报" + batchDt;
	}

	@Override
	protected String getTaskNm() {
		return "商户交易情况日报任务";
	}
	
	@Override
	protected String getJasperNm() {
		return "mchntTransDailyReport.jasper";
	}
	
	private Map<String, Object> initDataMap(String mchntCd) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("mchnt_cd", mchntCd);
		m.put("mchnt_nm", DBCache.getMchntNm(mchntCd));
		return m;
	}
}
