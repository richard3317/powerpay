package com.icpay.payment.batch.task.rpt;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;

@Component("chnlTransDailyReport")
public class ChnlTransDailyReport extends BaseDailyReportTask {
	
	@Override
	protected void initRptData() {
		this.addParam("S_DATE", batchDt);

		final Map<String, Map<String, Object>> statResult = new LinkedHashMap<String, Map<String,Object>>();
		String statSql = "select trans_chnl, int_trans_cd, sum(trans_num_sum) as total_num, sum(trans_at_sum) as total_amt " +
				"from tbl_trans_stat where stat_dt=? and rsp_cd='0000000' group by trans_chnl, int_trans_cd";
		this.jdbcTemplate.query(statSql, new Object[] { batchDt }, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				String chnl = StringUtil.trimStr(rs.getString("trans_chnl"));
				Map<String, Object> m = null;
				if (statResult.containsKey(chnl)) {
					m = statResult.get(chnl);
				} else {
					m = initDataMap(chnl);
					statResult.put(chnl, m);
				}
				
				// 计算总笔数
				Long num = rs.getLong("total_num");
				Long l = (Long) m.get("TOTAL_NUM");
				m.put("TOTAL_NUM", Long.valueOf(num + l));
				
				// 计算总金额
				BigDecimal amt = (BigDecimal) m.get("TOTAL_AMT");
				BigDecimal bd = rs.getBigDecimal("total_amt");
				m.put("TOTAL_AMT", amt.add(bd));
				
				String intTransCd = rs.getString("int_trans_cd");
//				if (TxnEnums.TxnType._0100.getCode().equals(intTransCd)) {
//					m.put("F3", String.valueOf(num));
//					m.put("F4", StringUtil.formateAmt(bd.toString()));
//				} else if (TxnEnums.TxnType._3100.getCode().equals(intTransCd)) {
//					m.put("F5", String.valueOf(num));
//					m.put("F6", StringUtil.formateAmt(bd.toString()));
//				} else if (TxnEnums.TxnType._0400.getCode().equals(intTransCd)) {
//					m.put("F7", String.valueOf(num));
//					m.put("F8", StringUtil.formateAmt(bd.toString()));
//				} else if (TxnEnums.TxnType._0500.getCode().equals(intTransCd)) {
//					m.put("F9", String.valueOf(num));
//					m.put("F10", StringUtil.formateAmt(bd.toString()));
//				} else if (TxnEnums.TxnType._0510.getCode().equals(intTransCd)) {
//					m.put("F11", String.valueOf(num));
//					m.put("F12", StringUtil.formateAmt(bd.toString()));
//				} else if (TxnEnums.TxnType._1311.getCode().equals(intTransCd)) {
//					m.put("F13", String.valueOf(num));
//					m.put("F14", StringUtil.formateAmt(bd.toString()));
//				} else if (TxnEnums.TxnType._9800.getCode().equals(intTransCd)) {
//					m.put("F15", String.valueOf(num));
//					m.put("F16", StringUtil.formateAmt(bd.toString()));
//				}
			}
		});
		
		for (String chnlId : statResult.keySet()) {
			Map<String, Object> m = statResult.get(chnlId);
			m.put("TOTAL_NUM", String.valueOf(m.get("TOTAL_NUM")));
			m.put("TOTAL_AMT", StringUtil.formateAmt(String.valueOf(m.get("TOTAL_AMT"))));
			this.addData(m);
		}
	}

	@Override
	protected String getReportNm() {
		return "渠道交易情况日报";
	}

	@Override
	protected String getTaskNm() {
		return "渠道交易情况日报任务";
	}
	
	@Override
	protected String getJasperNm() {
		return "chnlTransDailyReport.jasper";
	}
	
	private Map<String, Object> initDataMap(String chnlId) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("CHNL", EnumUtil.translate(TxnEnums.ChnlId.class, chnlId, true));
		for (int i = 3; i < 17;) {
			m.put("F" + (i ++), "0");
			m.put("F" + (i ++), "0.00");
		}
		m.put("TOTAL_NUM", Long.valueOf("0"));
		m.put("TOTAL_AMT", new BigDecimal("0"));
		return m;
	}
}
