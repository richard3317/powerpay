package com.icpay.payment.db.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.icpay.payment.common.constants.RspCd;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.exception.ChnlBizException;
import com.icpay.payment.common.exception.I18nBizException;
import com.icpay.payment.common.exception.I18nFactory;
import com.icpay.payment.common.exception.I18nMsgSpec;
import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.Converter;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;

import com.icpay.payment.db.dao.mybatis.mapper.IntKeyMapper;
import com.icpay.payment.db.dao.mybatis.mapper.MerParams_BakMapper;
import com.icpay.payment.db.dao.mybatis.model.AgentFuncInfo;
import com.icpay.payment.db.dao.mybatis.model.IntKey;
import com.icpay.payment.db.dao.mybatis.model.MerParams;
import com.icpay.payment.db.dao.mybatis.model.MerParamsKey;
import com.icpay.payment.db.service.I18nMessageService;



public class BaseService {
	
	//private static final Logger logger = Logger.getLogger(BaseService.class);
	
	private static final int MAX_RISK_KEY = 99999;
	private static final int MAX_MCHNT_CD_KEY = 9999;
	private static final int MAX_AGENT_CD_KEY = 9999999;
	
//	@Autowired
//	private I18nMessageServiceImpl i18nMessageServiceImpl;
//	@Autowired
//	private I18nMessageService i18nMessageService;
	/**
	 * 构造分页对象
	 * @param <T>
	 * @param pageNum
	 * @param pageSize
	 * @param qryParamMap
	 * @return
	 */
	protected <T> Pager<T> buildPager(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		if (this.getLogger().isDebugEnabled()) {
			StringBuilder qryInfo = new StringBuilder();
			qryInfo.append("\n-----QRY INFO-----");
			qryInfo.append("\npageNum:" + pageNum);
			qryInfo.append("\npageSize:" + pageSize);
			qryInfo.append("\nqryParamMap:" + qryParamMap.toString());
			qryInfo.append("------------------");
			debug(qryInfo.toString());
		}
		
		// 构造分页对象
		Pager<T> pager = new Pager<T>();
		pager.setPageNum(pageNum);
		pager.setPageSize(pageSize);
		
		return pager;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T getQryExample(Map<String, String> qryParamMap) {
		Object o = this.buildQryExample(qryParamMap);
		if (o != null) {
			return (T) o;
		} else {
			return null;
		}
	}
	
	protected synchronized int genIntKey(CommonEnums.PrimaryKeyTp keyTp) {
		AssertUtil.objIsNull(keyTp, "keyTp is null");
		
		int val = Integer.MAX_VALUE;
		IntKeyMapper mapper = this.getMapper(IntKeyMapper.class);
		IntKey key = mapper.selectByPrimaryKey(keyTp.getCode());
		val = key.getKeyVal();
		if (CommonEnums.PrimaryKeyTp._01.equals(keyTp) && (val > MAX_RISK_KEY)) {
			throw new I18nBizException(new I18nMsgSpec("zh_CN", this.getClass().getSimpleName(),null, "风险规则主键取值已到上限 %s",String.valueOf(MAX_RISK_KEY)));
		}
		/*
		 * 1. 如果商户代码，则到了9999后，从1开始
		 * 2. 如果代理商代码，则到了9999999后，从1开始
		 */
		if ((CommonEnums.PrimaryKeyTp._03.equals(keyTp) && val == MAX_MCHNT_CD_KEY)
				|| (CommonEnums.PrimaryKeyTp._04.equals(keyTp) && val == MAX_AGENT_CD_KEY)) {
			key.setKeyVal(1);
		} else {
			key.setKeyVal(val + 1);
		}
		mapper.updateByPrimaryKey(key);
		return val;
	}
	
	protected <T> T getMapper(Class<T> clazz) {
		SqlSession sqlSession = ApplicationContextUtil.getBean("sqlSession");
		T mapper = sqlSession.getMapper(clazz);
		i18ObjIsNull(mapper, this.getClass().getSimpleName(), "未找到指定的mapper: %s",clazz.getName());

		return mapper;
	}
	
	protected void closeSession(SqlSession session) {
		if (session != null) {
			session.close();
		}
	}
	
	protected Object buildQryExample(Map<String, String> qryParamMap) {
		return null;
	}
	
	private Logger _logger=null;
	
	protected Logger getLogger() {
		if (_logger==null) {
			_logger = Logger.getLogger(this.getClass());
		}
		return _logger;
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

	protected String getLogPrefix() {
		return "";
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
	
	protected String strVal(Object obj) {
		if (obj==null) return "";
		return obj.toString();
	}
	
	protected Long convertAmount(String amt) {
		try {
			BigDecimal a = new BigDecimal(amt).multiply(new BigDecimal(100));
			return a.longValue();
		} catch (Exception e) {
			this.error("查询时，金额格式错误: '%s'", amt);
			throw new I18nBizException(new I18nMsgSpec("zh_CN", "BaseService",null, "查询时，金额格式错误: %s",amt));
		}
	}
	
	
	protected Date convertDateTime(String fmt, String dateTime) {
		try {
			return Converter.stringToDateTimeFmt(dateTime, fmt);
		} catch (ParseException e) {
			this.error("查询时，日期格式错误: %s", dateTime);
			throw new I18nBizException(new I18nMsgSpec("zh_CN", "BaseService",null, "查询时，日期格式错误: '%s'", dateTime));
		}
	}
	
	protected Date convertDateTime(String dateTime) {
		try {
			return Converter.stringToDateTime(dateTime);
		} catch (ParseException e) {
			this.error("查询时，日期格式错误: %s", dateTime);
			throw new I18nBizException(new I18nMsgSpec("zh_CN", "BaseService",null, "查询时，日期格式错误: '%s'", dateTime));
		}
	}
	
	protected Date convertDateTime(String fmt, String date, String time, String suffix) {
		return convertDateTime(fmt, StringUtils.left(strVal(date)+strVal(time)+strVal(suffix), fmt.length()));
	}
	
	protected Date convertStartDateTime(String date, String time) {
		return convertDateTime(DEFAULT_TIME_FORMAT, date, time, "000000");
	}

	protected Date convertEndDateTime(String date, String time) {
		if (Utils.isEmpty(time)) 
			time = "235959.999";
		else
			time = StringUtils.left(time+"000000",6)+".000";
		
		return convertDateTime(DEFAULT_LONG_TIME_FORMAT, date, time, "");
	}	

	protected static final String DEFAULT_TIME_FORMAT="yyyyMMddHHmmss";
	protected static final String DEFAULT_LONG_TIME_FORMAT="yyyyMMddHHmmss.SSS";
	
	protected static String getMon(String... dates) {
		if (dates!=null)
			for(String d: dates) {
				if (!isEmpty(d)) {
					return getMonth(d);
				}
			}
		return getMonth();
	}
	
	protected static boolean isEmpty(Object obj) {
		if (obj==null) return true;
		String s=obj.toString();
		if (StringUtils.isBlank(s)) return true;
		return (Utils.isInSet(s, "undefined", "null"));
	}
	
	public static String getMonth(String src, int startIndex, int endIndex){
		if (StringUtil.isEmpty(src)) return null;
		if (src.length()<endIndex) return null;
		String month = src.substring(startIndex, endIndex);
		if (month.length()!=2) return null;
		if (!((month.compareTo("00")>0) && (month.compareTo("13")<0))) return null;
		return month;
	}
	
	public static String getMonth(String src, int startIndex){
		String mon = getMonth(src, startIndex, startIndex+2);
		//if (mon==null)
		//	error(RspCd.Z_1001,"日期格式错误");
		return mon;
	}
	
	/**
	 * 获取月份
	 * @param tm 时间
	 * @return
	 */
	public static String getMonth(Date tm) {
		String dt = Converter.dateToString(tm);
		return getMonth(dt);
	}
	
	/**
	 * 获取月份
	 * @param date 日期格式yyyyMMdd
	 * @return
	 */
	public static String getMonth(String date) {
		return getMonth(date,4);
	}
	
	/**
	 * 获取目前时间的月份
	 * @return
	 */
	public static String getMonth() {
		return getMonth(new Date());
	}
	
	/**
	 * 获取指定日期前一日的月份
	 * @param time
	 * @return
	 */
	public static String getLastDayMonth(Date time) {
		if (time==null) time = new Date();
		Date tm = new Date(time.getTime()-86400000L);
		System.out.println("[DateTime]"+Converter.dateTimeToString(tm));
		return getMonth(tm);
	}
	
	/**
	 * 获取昨日的月份
	 * @return
	 */
	public static String getLastDayMonth() {
		return getLastDayMonth(null);
	}
	
	protected boolean strEquals(Object a, Object b) {
		if (a==null) return b==null;
		return a.toString().equals(b.toString());
	}
	
	///////////////////////////////////////////////////////
	// Logger & Exception Utils
	
	protected void throwError(String code, String msg) {
		getLogger().error(String.format("[Error] %s (Code=%s);", msg, code));
		throw new ChnlBizException(code, msg);
	}
	
	protected void throwError(String code, String msg, Throwable err) {
		getLogger().error(String.format("[Error] %s (Code=%s);", msg, code));
		throw new ChnlBizException(code, msg, err, null);
	}
	
	protected void throwError(String code, String msg, Map<String, String> context) {
		getLogger().error(String.format("[Error] %s (Code=%s); Context: %s", msg, code, context));
		throw new ChnlBizException(code, msg, context);
	}
	
	protected void throwError(String code, String msg, Throwable err, Map<String, String> context) {
		getLogger().error(String.format("[Error] %s (Code=%s); Context: %s", msg, code, context));
		throw new ChnlBizException(code, msg, err, context);
	}
	
	protected void checkDataError(int count, String fmt, Object...args) {
		checkDataError(count, String.format(fmt, args));
	}
	
	protected void checkDataError(int count, String msg) {
		checkDataErrorWithCode(count, RspCd.Z_5001, msg);
	}
	
	protected void checkDataErrorWithCode(int count, String code, String fmt, Object...args) {
		checkDataErrorWithCode(count, code, String.format(fmt, args));
	}
	
	protected void checkDataErrorWithCode(int count, String code, String msg) {
		if (count<=0) {
			throwError(code, msg);
		}
	}
	
	protected void checkQueryError(Object rec, String fmt, Object...args) {
		checkQueryErrorWithCode(rec, RspCd.Z_5005, fmt, args);
	}
	
	protected void checkQueryError(List<?> list, String fmt, Object...args) {
		checkQueryErrorWithCode(list, RspCd.Z_5005, fmt, args);
	}
	
	protected void checkQueryErrorWithCode(Object rec, String code, String fmt, Object...args) {
		if (rec==null) {
			throwError(code, String.format(fmt, args));
		}
	}
	
	protected void checkQueryErrorWithCode(List<?> list, String code, String fmt, Object...args) {
		if ((list==null) || (list.size()==0)) {
			throwError(code, String.format(fmt, args));
		}
	}
	
	protected void checkParam(Object param, String fmt, Object...args) {
		if (Utils.isEmpty(param))
			throwError(RspCd.Z_1002, String.format(fmt, args));
	}
	
	protected void checkParam(Object param, String paramName) {
		checkParam(param, "缺失参数: %s", paramName);
	}
	
	protected boolean hasRecord(List<?> list) {
		return ((list!=null) && (list.size()>0));
	}
	
	protected boolean hasRecord(Object obj) {
		return (obj!=null);
	}
	

	public static void i18ObjIsNull(Object o, String className, String msg, String... args) {
		if (o == null) {
			if (StringUtil.isBlank(msg)) {
				msg = "对象为null";
			}
			throw new I18nBizException(new I18nMsgSpec("zh_CN", className, null, msg, args));
		}
	}
	
	
	public static void i18StrIsBlank(String str, String className, String msg, String... args) {
		if (StringUtil.isBlank(str)) {
			if (StringUtil.isBlank(msg)) {
				msg = "字符串为空";
			}
			throw new I18nBizException(new I18nMsgSpec("zh_CN", className, null, msg, args));
		}
	}
	
	/**
	 * 检查参数是否为空值
	 * 
	 * @param param
	 * @param paramName
	 * @param className
	 */
	public static void checkNotEmpty(Object param, String paramName, String className) {
		if (Utils.isEmpty(param))
			i18ArgIsNull(param, className, "参数 %s 不可为空值", paramName);
	}
	
	public static void i18ArgIsNull(Object o, String className, String msg, String... args) {
		if (o == null) {
			if (StringUtil.isBlank(msg)) {
				msg = "参数为null";
			}
			throw new I18nBizException(new I18nMsgSpec("zh_CN", className, null, msg, args));
		}
	}
	
	public static void i18ObjIsNotNull(Object o, String className, String msg, String... args) {
		if (o != null) {
			if (StringUtil.isBlank(msg)) {
				msg = "对象不为null";
			}
			throw new I18nBizException(new I18nMsgSpec("zh_CN", className, null, msg, args));
		}
	}
	
	public static void i18IfTrue(boolean cond, String className, String msg, String... args) {
		if (cond) {
			if (StringUtil.isBlank(msg)) {
				msg = "数据异常";
			}
			throw new I18nBizException(new I18nMsgSpec("zh_CN", className, null, msg, args));
		}
	}
	
	public static void i18ArgIsBlank(String str, String className, String msg, String... args) {
		if (StringUtil.isBlank(str)) {
			if (StringUtil.isBlank(msg))
				msg = "字符串为空";
			throw new I18nBizException(new I18nMsgSpec("zh_CN", className, null, msg, args));
		}
	}
	
	public static void intIsZero(int i, String className, String msg, String... args) {
		if (i == 0) {
			if (StringUtil.isBlank(msg)) {
				throw new BizzException("i is zero.");
			}
			throw new I18nBizException(new I18nMsgSpec("zh_CN", className, null, msg, args));
		}
	}
	
//	public  String getI18nMsg4Svc(String className, String msg, String... params) {
//		//String msgId = md5(msg);
//		String lang =  "zh_CN" ;
//		MerParamsKey merParamsKey = new MerParamsKey();
//		merParamsKey.setChnlId(PARAM.CH_NONE);
//		merParamsKey.setMchntCd(PARAM.MER_NONE);
//		merParamsKey.setParamCat(PARAM.CAT_SYS);
//		merParamsKey.setParamId("i18n.default.lang");
//		MerParams m = getMapper(MerParamsMapper.class).selectByPrimaryKey(merParamsKey);
//		//MerParams m = iMerParamsService.selectByPrimaryKey(merParamsKey);		
//		lang = m.getParamValue();
//		I18nMessageServiceImpl i18nMessageService = new I18nMessageServiceImpl();
//
//		String convertMsg = i18nMessageService.getMessageWithDefault("BM", lang, className+"."+msg, msg);
//		msg = convertMsg;	
//		return msg;
//		
//	}
// 匡哥說要變成的樣子
//	public  String getI18nMsg4Svc(String className, String msg, String... params) {
//		String msgId = md5(msg);
//		String lang =  "zh_CN" ;
//		MerParamsKey merParamsKey = new MerParamsKey();
//		merParamsKey.setChnlId(PARAM.CH_NONE);
//		merParamsKey.setMchntCd(PARAM.MER_NONE);
//		merParamsKey.setParamCat(PARAM.CAT_SYS);
//		merParamsKey.setParamId("i18n.default.lang");
//		MerParams m = getMapper(MerParamsMapper.class).selectByPrimaryKey(merParamsKey);
//		//MerParams m = iMerParamsService.selectByPrimaryKey(merParamsKey);		
//		lang = m.getParamValue();
//		I18nMessageServiceImpl i18nMessageService = new I18nMessageServiceImpl();
//
//		String convertMsg = i18nMessageService.getMessageWithDefault("BM", lang, className+"."+msgId, msg);
//		msg = String.format(convertMsg, params);	
//		return msg;
//		
//	}
	
	
}
