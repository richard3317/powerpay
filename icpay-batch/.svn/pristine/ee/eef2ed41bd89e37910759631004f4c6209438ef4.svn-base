package com.icpay.payment.batch.task.chart;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.BatchTaskTemplate;

@Component("chnlMchntAccountHourTask")
public class ChnlMchntAccountHourTask extends BatchTaskTemplate {

	@Override
	protected void doTask() {
		//1先获取当前月份，根据月份来查询表格
		Calendar instance = Calendar.getInstance();
		
		//需要对月份加1
		int month = instance.get(Calendar.MONDAY) + 1;
		Calendar calendar = Calendar.getInstance();
		
		//获取当前时间的小时 
		SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
		String trans_dt = df1.format(new Date());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");

		//七天每个时间段对应的值
		Set<String> hoursSet = new HashSet<>();
		for (int j = 1; j <= 7; j++) {
			//1000*60*60*24   一天   
			//当前时间 减去一天二十四小时   再减去  一个小时    //查询的是前七天      当前时段 前一个小时的数据
			String format = df.format(calendar.getTime().getTime() - (1000 * 60 * 60 * 24) * j - 1000 * 60 * 60);
			hoursSet.add(format);
		}

		//处理月份的01 02 11 格式
		String monthStr = String.format("%02d", month);
		
		//选择拿那张表的数据
		String transLogTbl = "tbl_trans_log" + monthStr;
		
		//查询比较时间
        SimpleDateFormat sqlDf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sqlDf.format(new Date());
        Calendar calendar1 = Calendar.getInstance();
        int lastHour = calendar1.get(calendar1.HOUR_OF_DAY)-1;
        int currentHour = calendar1.get(calendar1.HOUR_OF_DAY);
        
        //设置为前一天
        calendar1.setTime(new Date());//把当前时间赋给日历
        calendar1.add(Calendar.DAY_OF_MONTH, -1);  
        Date dBefore = calendar1.getTime();   //得到前一天的时间
        String currentDateBefore = sqlDf.format(dBefore);
        
        String setHour = "" + lastHour;
        if (currentHour == 0) {
        	lastHour = 23;
        	currentDate = currentDateBefore;
        	setHour = "23";
        }
        if (lastHour < 10) {
        	setHour = "0" + lastHour;
        }
        String formatDate = currentDate + " " + setHour;
        
		
		//查前一个小时的数据
        logger.info("平台交易量生成任务处理开始: " + formatDate + "时");
		String pay_sql = "SELECT SUM(trans_at) AS trans_at FROM " + transLogTbl + " WHERE int_trans_cd <> '5210' AND txn_state = '10' AND rec_upd_ts LIKE '%" + formatDate + "%'";
		String withdraw_sql = "SELECT SUM(trans_at) AS withdraw_at FROM " + transLogTbl + " WHERE int_trans_cd = '5210' AND txn_state = '10' AND rec_upd_ts LIKE '%" + formatDate + "%'";
		String available_sql = "SELECT SUM(t.available_balance) AS available_balance, SUM(t.obligated_balance) AS obligated_balance, SUM(t.frozen_t1_balance) AS frozen_t1_balance, SUM(t.frozen_balance) AS frozen_balance FROM tbl_chnl_mchnt_account_info t INNER JOIN tbl_chnl_mchnt_info tm ON (t.mchnt_cd = tm.chnl_mchnt_cd and t.chnl_id = tm.chnl_id) AND t.state = '1'";
		
		//sql查询，前一个小时所有的数据
		List<Map<String, Object>> payList = this.jdbcTemplate.queryForList(pay_sql);
		List<Map<String, Object>> withdrawList = this.jdbcTemplate.queryForList(withdraw_sql);
		List<Map<String, Object>> availableList = this.jdbcTemplate.queryForList(available_sql);
		String trans_at = "";
		String withdraw_at = "";
		String available_balance = "";
		String obligated_balance = "";
		String frozen_t1_balance = "";
		String frozen_balance = "";
		
		if (payList != null && payList.size() > 0) {
			Map<String, Object> map = payList.get(0);
			trans_at = (map.containsKey("trans_at")) ? (map.get("trans_at") != null ? map.get("trans_at").toString() : "0") : "0";
			logger.info("充值总额: " + trans_at);
		}
		if (withdrawList != null && withdrawList.size() > 0) {
			Map<String, Object> map = withdrawList.get(0);
			withdraw_at = (map.containsKey("withdraw_at")) ? (map.get("withdraw_at") != null ? map.get("withdraw_at").toString() : "0") : "0";
			logger.info("代付总额: " + withdraw_at);
		}
		if (availableList != null && availableList.size() > 0) {
			Map<String, Object> map = availableList.get(0);
			available_balance = (map.containsKey("available_balance")) ? (map.get("available_balance") != null ? map.get("available_balance").toString() : "0") : "0";
			obligated_balance = (map.containsKey("obligated_balance")) ? (map.get("obligated_balance") != null ? map.get("obligated_balance").toString() : "0") : "0";
			frozen_t1_balance = (map.containsKey("frozen_t1_balance")) ? (map.get("frozen_t1_balance") != null ? map.get("frozen_t1_balance").toString() : "0") : "0";
			frozen_balance = (map.containsKey("frozen_balance")) ? (map.get("frozen_balance") != null ? map.get("frozen_balance").toString() : "0") : "0";
			logger.info("渠道D0可代付余额: " + available_balance);
			logger.info("风险阀值: " + obligated_balance);
			logger.info("渠道D1余额: " + frozen_t1_balance);
			logger.info("渠道冻结金额: " + frozen_balance);
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO tbl_chnl_mchnt_account_hour (trans_dt, trans_hour, trans_at, withdraw_at, available_balance, obligated_balance, frozen_t1_balance, frozen_balance, comment, last_oper_id) VALUES ('")
		  .append(trans_dt)
		  .append("', '")
		  .append(currentHour)
		  .append("', '")
		  .append(trans_at)
		  .append("', '")
		  .append(withdraw_at)
		  .append("', '")
		  .append(available_balance)
		  .append("', '")
		  .append(obligated_balance)
		  .append("', '")
		  .append(frozen_t1_balance)
		  .append("', '")
		  .append(frozen_balance)
		  .append("', '")
		  .append("', '")
		  .append("chnlMchntAccountHourJob")
		  .append("')");
		this.jdbcTemplate.execute(sb.toString());
		logger.info("平台交易量生成任务处理完成: " + formatDate + "时");
	}

	@Override
	protected String getTaskNm() {
		return "平台交易量生成任务";
	}
}
