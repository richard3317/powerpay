package com.icpay.payment.batch.task.rpt;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import com.icpay.payment.batch.cache.BatchConfigCache;
import com.icpay.payment.batch.cache.DBCache;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;

@Component("chnlMchntTransDailyReport")
public class ChnlMchntTransDailyReport extends BaseDailyReportTask {
	
	@Override
	protected void initRptData() {
		this.addParam("S_DATE", batchDt);
		this.addParam("SUBREPORT_DIR", BatchConfigCache.getConfig("jasper_root_dir"));

		final Map<String, Map<String, Map<String, Object>>> statResult = new LinkedHashMap<String, Map<String, Map<String,Object>>>();
		final String mon = batchDt.substring(4, 6);
		final String transLogTbl = "tbl_trans_log" + mon;
		String statSql = "SELECT " + 
				"  t.`ext_trans_dt` AS transDt," + 
				" t.`trans_chnl` AS transChnl," +
				"  t.`chnl_mchnt_cd` AS chnlMchntCd," + 
				"  t.`int_trans_cd` AS intTransCd," + 
				"  SUM(t.`trans_at`) AS transAtTotal," + 
				"  SUM(t.`trans_fee_chnl`) AS chnlFeeTotal," + 
				"  COUNT(t.`trans_seq_id`) AS successTotal," + 
				"  CONCAT(ROUND(COUNT(t.`trans_seq_id`)/(SELECT COUNT(tl.`trans_seq_id`) FROM "+transLogTbl+" tl WHERE  tl.`ext_trans_dt`=t.`ext_trans_dt` AND  tl.`trans_chnl`= t.`trans_chnl`)*100,2),'%')  AS dailySuccessRate " + 
				" FROM " + 
				 transLogTbl+" t " + 
				" WHERE t.`ext_trans_dt` = ? " + 
				"  AND t.rsp_cd ='0000'" + 
				"  GROUP BY t.`trans_chnl`,t.`chnl_mchnt_cd` ,t.`int_trans_cd` ";
		this.jdbcTemplate.query(statSql, new Object[] { batchDt }, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				String chnl = StringUtil.trimStr(rs.getString("transChnl"));
				Map<String, Map<String, Object>> chnlMap = null;
				if (statResult.containsKey(chnl)) {
					chnlMap = statResult.get(chnl);
				} else {
					chnlMap = new LinkedHashMap<String, Map<String, Object>>();
					statResult.put(chnl, chnlMap);
				}
				
				String chnlMchntCd = StringUtil.trimStr(rs.getString("chnlMchntCd"));
				Map<String, Object> m = null;
				if (chnlMap.containsKey(chnlMchntCd)) {
					m = chnlMap.get(chnlMchntCd);
				} else {
					m = initMchntDataMap(chnlMchntCd);
					chnlMap.put(chnlMchntCd, m);
				}
				
				//交易日期
				String transDt = rs.getString("transDt");
				m.put("trans_dt", transDt);
				
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
				
				//手续费总金额
				BigDecimal chnlFeeTotal = rs.getBigDecimal("chnlFeeTotal");
				m.put("chnl_total_fee", chnlFeeTotal);
				
				//日成功比
				String dailySuccessRate = rs.getString("dailySuccessRate");
				m.put("daily_success_rate", dailySuccessRate);
				
				
				int transfeeTotal = 0;//记录交易总手续费
				String sql= "SELECT SUM(tl.`trans_fee`) FROM "+ transLogTbl +" tl WHERE  tl.`ext_trans_dt`= ? AND tl.chnl_mchnt_cd = ? AND tl.rsp_cd ='0000'";
				transfeeTotal = jdbcTemplate.queryForObject(sql,new Object[] { batchDt ,chnlMchntCd},Integer.class);

				int platformDailyPart = transfeeTotal - chnlFeeTotal.intValue();
				m.put("platform_daily_part", Integer.toString(platformDailyPart));
			}
		});
		
		for (String chnl : statResult.keySet()) {
			Map<String, Map<String, Object>> chnlStatMap = statResult.get(chnl);
			Map<String, Object> data = new HashMap<String, Object>();
			List<Map<String, Object>> mchntTransLst = new ArrayList<Map<String, Object>>();
			for (String mchntCd : chnlStatMap.keySet()) {
				Map<String, Object> mchntTransMap = chnlStatMap.get(mchntCd);
				mchntTransMap.put("chnl", EnumUtil.translate(TxnEnums.ChnlId.class, chnl, true));
				mchntTransMap.put("total_num", String.valueOf(mchntTransMap.get("total_num")));
				mchntTransMap.put("chnl_total_fee", StringUtil.formateAmt(String.valueOf(mchntTransMap.get("chnl_total_fee"))));
				mchntTransMap.put("total_amt", StringUtil.formateAmt(String.valueOf(mchntTransMap.get("total_amt"))));
				mchntTransMap.put("platform_daily_part", StringUtil.formateAmt(String.valueOf(mchntTransMap.get("platform_daily_part"))));
				mchntTransLst.add(mchntTransMap);
			}
			data.put("MCHNT_TRANS_LIST", mchntTransLst);
			this.addData(data);
		}
	}

	@Override
	protected String getReportNm() {
		return "渠道商户交易情况统计日报" + batchDt;
	}

	@Override
	protected String getTaskNm() {
		return "渠道商户交易情况统计日报任务";
	}
	
	@Override
	protected String getJasperNm() {
		return "chnlMchntTransDailyReport.jasper";
	}
	
	private Map<String, Object> initMchntDataMap(String mchntCd) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("mchnt_cd", mchntCd);
		m.put("mchnt_nm", DBCache.getMchntNm(mchntCd));
		return m;
	}
}
