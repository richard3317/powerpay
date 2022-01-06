package com.icpay.payment.batch.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.fastjson.JSONObject;
import com.icpay.payment.common.data.svc.DAO;
import com.icpay.payment.common.data.utils.SessionParams;
import com.icpay.payment.common.enums.BatchEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.exception.ChnlBizException;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.common.utils.UUIDGenerator;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.BatchTaskExecLog;
import com.icpay.payment.db.service.IBatchTaskExecLogService;

public abstract class BatchTaskTemplate implements IBatchTask {
	protected final Logger logger = Logger.getLogger(BatchTaskTemplate.class);
	
	//protected JdbcTemplate jdbcTemplate = ApplicationContextUtil.getBean("jdbcTemplate");
	protected JdbcTemplate jdbcTemplate = DAO.getJdbcTemplate();

	/**
	 * Task 执行时间
	 */
	protected Date batchTime;
	
	/**
	 * Task 执行前一天时间
	 */
	protected Date preBatchTime;

	/**
	 * Task 执行的日期
	 */
	protected String batchDt;
	/**
	 * Task 执行的日期前一日
	 */
	protected String preBatchDt;
	/**
	 * Task 执行的日期后一日
	 */
	protected String nextBatchDt;
	protected Map<String,String> params = new HashMap<String,String>();
	
	private IBatchTaskExecLogService taskExecLogService = 
		DBHessionServiceClient.getService(IBatchTaskExecLogService.class);
	
	private AtomicBoolean isRunning = new AtomicBoolean(false);

	@Override
	public void exec(String batchDt, BatchEnums.RunningMode runningMode) {
		if (isRunning.compareAndSet(false, true)) {
			BatchTaskExecLog log = new BatchTaskExecLog();
			try {
				logger.info("****["+batchDt+"]"+getTaskNm()+"执行任务开始****");
				this.batchDt = batchDt;
				this.batchTime = new Date();
				this.preBatchTime = new Date(batchTime.getTime()-86400000L);
				
				Date dt = DateUtil.parseDate8(batchDt);
				preBatchDt = DateUtil.preDay(dt);
				nextBatchDt = DateUtil.nextDay(dt);
				
				log.setLogId(UUIDGenerator.getUUID());
				log.setTaskNm(this.getTaskNm());
				log.setBatchDt(batchDt);
				log.setRunningMode(runningMode.getCode());
				log.setEndTm("");
				log.setExecMsg("");
				taskExecLogService.add(log);
				
				this.doTask();
				
				log.setStatus(BatchEnums.RunningStatus._1.getCode());
				log.setExecMsg("执行成功");
			} catch (Exception e) {
				log.setStatus(BatchEnums.RunningStatus._2.getCode());
				logger.warn("执行失败",e);
				log.setExecMsg("执行失败：" + e.getMessage());
				throw new BizzException("执行批量任务失败:" + this.getTaskNm(), e);
			} finally {
				params.clear();
				preBatchDt = null;
				nextBatchDt = null;
				isRunning.set(false);
				log.setEndTm(DateUtil.now19());
				taskExecLogService.update(log);
				logger.info("****["+batchDt+"]"+getTaskNm()+"执行任务结束****");
				batchDt = null;
			}
		} else {
			throw new BizzException("批量任务正在执行，无法重复拉起:" + this.getTaskNm());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void setParam(String jsonString){
		params = JsonUtil.fromJson(jsonString, Map.class);
	}
	
	public void setParam(String key,String value){
		params.put(key, value);
	}
	
	protected abstract void doTask();
	
	protected abstract String getTaskNm();
	
	

	private Logger logInstance = null;

	protected Logger getLogger() {
		if (logInstance==null) {
			logInstance = Logger.getLogger(this.getClass());
		}
		return logInstance;
	}

	protected String format(String fmt, Object...args) {
		return String.format(fmt, args);
	}

	protected void debug(String message) {
		getLogger().debug(getLogPrefix()+message);
	}

	protected void debug(String fmt, Object... args) {
		debug(String.format(fmt, args));
	}

	protected void info(String message) {
		getLogger().info(getLogPrefix()+message);
	}

	protected void info(String fmt, Object... args) {
		info(String.format(fmt, args));
	}

	protected void warn(String message) {
		getLogger().warn(getLogPrefix()+message);
	}

	protected void warn(String fmt, Object... args) {
		warn(String.format(fmt, args));
	}

	protected void error(String message) {
		getLogger().error(getLogPrefix()+message);
	}

	protected void error(String fmt, Object... args) {
		error(String.format(fmt, args));
	}

	protected void error(String message, Throwable t) {
		getLogger().error(getLogPrefix()+message, t);
	}

	protected void error(Throwable t, String fmt, Object... args) {
		error(String.format(fmt, args), t);
	}

//	protected String getLogPrefix() {
//		return "";
//	}
	
	protected String getLogPrefix() {
		return format("[%s]",this.getTaskNm());
	}	

	/**
	 * Put value if val is not empty.
	 * @param map
	 * @param key
	 * @param val
	 */
	protected void putIfNotEmpty(Map<String, String> map, String key, Object val) {
		if (!Utils.isEmpty(val) ) map.put(key, val.toString());
	}

	/**
	 * Put vale if val is not empty.
	 * @param map
	 * @param key
	 * @param val
	 */
	protected void putIfNotEmpty(JSONObject map, String key, Object val) {
		if (!Utils.isEmpty(val) ) map.put(key, val.toString());
	}

//	/**
//	 * 掷出ChnlBizException错误
//	 * @param channel 渠道编号
//	 * @param errorCode 错误代码
//	 * @param errorMsg 错误讯息
//	 * @param e 原始错误
//	 * @param context 附带内容
//	 */
//	protected void throwError(String channel, String errorCode, String errorMsg, Throwable e, Map<String, String> context) {
//		error(String.format("[%s-%s] %s", channel, errorCode, errorMsg));
//		throw new ChnlBizException(channel, errorCode, errorMsg, e, context);
//	}

	/**
	 * 掷出ChnlBizException错误
	 * @param errorCode 错误代码
	 * @param errorMsg 错误讯息
	 * @param e 原始错误
	 * @param context 附带内容
	 */
	protected void throwError(String errorCode, String errorMsg, Throwable e, Map<String, String> context) {
		error(String.format("[%s] %s", errorCode, errorMsg));
		throw new ChnlBizException(ChnlBizException.DEFAULT_CATALOG, errorCode, errorMsg, e, context);
	}

//	protected void throwError(String channel, String errorCode, String errorMsg) {
//		error(String.format("[%s-%s] %s", channel, errorCode,errorMsg));
//		throw new ChnlBizException(channel, errorCode, errorMsg, (Throwable) null, (Map<String, String>) null);
//	}

	protected void throwError(String code, String msg) {
		error(String.format("[%s] %s", code,msg));
		throw new ChnlBizException(code, msg);
	}	
	
	protected List<String> toList(String... items){
		ArrayList<String> list = new ArrayList<>();
		for(String item : items) {
			list.add(item);
		}
		return list;
	}
	
	public static class Consts{
		/** 默认商户/不区分 */
		public static final String DEFAULT_MER="#DEFAULT#";
		
		
		///////////////////////////////////////
		// PARAM CATALOG
		/** 每日Job */
		public static final String CAT_DAILY="Daily";
		public static final String CAT_DAILY_PROFIT="DailyProfit";
		public static final String CAT_DAILY_CURRENCY_QUERY="DailyCurrencyQuery";
		/** 时间误差 */
		@Deprecated
		public static final String CAT_DAILY_RESET="DailyReset";
		
		///////////////////////////////////////
		// PARAM NAME
		/** BITPAY 币种汇率查询_ */
		public static final String CAT="cat";
		/** BITPAY 币种汇率查询_渠道编号 */
		public static final String CHNL_ID="chnlId";
		/** BITPAY 币种汇率查询_渠道商户号 */
		public static final String CHNL_MER_ID="chnlMerId";
		/** 时间误差 */
		public static final String PNAME_TIME_ERR="time_err";
		/** 默認值行時間 */
		public static final String PNAME_TASK_TIME="taskTime";
		/** 默認值行時間 */
		public static final String PNAME_PROFIT_SETTLE_TASK_TIME="taskTime.settle.profit";
		/** 默認值行時間 */
		public static final String PNAME_TASK_TIME_AGENT_PROFIT_QUERY="taskTime.agentProfitQuery";
		/** 默認執行時間 */
		public static final String PNAME_TASK_TIME_SYS_SETTLE_PROFIT="taskTime.sys.settle.profit";
	}
	
	static String MANUAL_TASK_CAT="batch.task.manual";
	static final Integer DEFAULT_TIMEOUT =60*3;
	public boolean isManualTrigger() {
		String val = SessionParams.get(MANUAL_TASK_CAT, this.getTaskNm());
		return "1".equals(val);
	}
	
	public void setManualTrigger(Integer timeout) {
		if ((timeout==null) || (timeout<0))
			timeout=DEFAULT_TIMEOUT;
		SessionParams.put(MANUAL_TASK_CAT, this.getTaskNm(), "1", timeout);
	}
	
	
	
}
