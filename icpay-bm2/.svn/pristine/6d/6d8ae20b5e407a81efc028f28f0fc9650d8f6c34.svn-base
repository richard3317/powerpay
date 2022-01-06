package com.icpay.payment.bm.cache;

import org.apache.log4j.Logger;

import com.icpay.payment.common.constants.Names.PARAM;
import com.icpay.payment.service.client.MerParamsClient;

public final class ParamCache extends CacheBase implements ICache {
	
	private static final Logger logger = Logger.getLogger(ParamCache.class);
	
	private static ParamCache instance = new ParamCache();
	
	private ParamCache() { }
	
	public static ParamCache getInstance() {
		return instance;
	}

	@Override
	public synchronized void init() {
		this.load();
	}
	
	@Override
	public void refresh() {
		this.load();
	}
	
	@Override
	public void clear() {
		
	}
	
	private static final Object param_load_lock = new Object();

	public static String STYLE_DEFAULT="";//默认配色，可为空值，表示使用系统默认(即白底黑字)。
	public static String STYLE_DEFAULT2="";//默认配色，可为空值，表示使用系统默认(即白底黑字)。
	public static String STYLE_DISABLE="color:silver;";//默认配色，可为空值，表示使用系统默认(即白底黑字)。
	public static String STYLE_DISABLE2="color:silver;";//默认配色，可为空值，表示使用系统默认(即白底黑字)。
	public static String STYLE_TXN_STATUS_PROC="color:green;";//交易处理中 配色，范例："color:blue;" 或 "color:#66cccc;" 或 "background-color:pink;color:blue;font-weight:bold;"
	public static String STYLE_TXN_STATUS_PROC2="color:green;";//交易处理中 配色，范例："color:blue;" 或 "color:#66cccc;" 或 "background-color:pink;color:blue;font-weight:bold;"
	public static String STYLE_TXN_STATUS_OK="color:black;";//交易成功 配色，范例："color:blue;" 或 "color:#66cccc;" 或 "background-color:pink;color:blue;font-weight:bold;"
	public static String STYLE_TXN_STATUS_OK2="color:blue;";//交易成功 配色，范例："color:blue;" 或 "color:#66cccc;" 或 "background-color:pink;color:blue;font-weight:bold;"
	public static String STYLE_TXN_STATUS_FAILED="color:red;";//交易失败 配色，范例："color:blue;" 或 "color:#66cccc;" 或 "background-color:pink;color:blue;font-weight:bold;"
	public static String STYLE_TXN_STATUS_OTHER="color:blue;";//交易其他状态 配色，范例："color:blue;" 或 "color:#66cccc;" 或 "background-color:pink;color:blue;font-weight:bold;"
	public static String STYLE_TXN_STATUS_FAILED2="color:red;";//交易失败 配色，范例："color:blue;" 或 "color:#66cccc;" 或 "background-color:pink;color:blue;font-weight:bold;"
	public static String STYLE_CASH_POOL="background-color:pink;color:blue;";//优先出款的资金池 配色，范例："color:blue;" 或 "color:#66cccc;" 或 "background-color:pink;color:blue;font-weight:bold;"
	
	private void load() {
		logger.info("-ParamCache 初始化开始-");

		synchronized (param_load_lock) {
			STYLE_DEFAULT = getSysParam("BM.Style.STYLE_DEFAULT", STYLE_DEFAULT); //默认配色，可为空值，表示使用系统默认(即白底黑字)。
			STYLE_DISABLE = getSysParam("BM.Style.STYLE_DISABLE", STYLE_DISABLE); //默认配色，可为空值，表示使用系统默认(即白底黑字)。
			STYLE_TXN_STATUS_PROC = getSysParam("BM.Style.STYLE_TXN_STATUS_PROC", STYLE_TXN_STATUS_PROC); //交易处理中 配色，范例："color:blue;" 或 "color:#66cccc;" 或 "background-color:pink;color:blue;font-weight:bold;"
			STYLE_TXN_STATUS_OK = getSysParam("BM.Style.STYLE_TXN_STATUS_OK", STYLE_TXN_STATUS_OK); //交易成功 配色，范例："color:blue;" 或 "color:#66cccc;" 或 "background-color:pink;color:blue;font-weight:bold;"
			STYLE_TXN_STATUS_FAILED = getSysParam("BM.Style.STYLE_TXN_STATUS_FAILED", STYLE_TXN_STATUS_FAILED); //交易失败 配色，范例："color:blue;" 或 "color:#66cccc;" 或 "background-color:pink;color:blue;font-weight:bold;"
			STYLE_TXN_STATUS_OTHER = getSysParam("BM.Style.STYLE_TXN_STATUS_OTHER", STYLE_TXN_STATUS_OTHER); //交易其他状态 配色，范例："color:blue;" 或 "color:#66cccc;" 或 "background-color:pink;color:blue;font-weight:bold;"


			STYLE_DEFAULT2 = getSysParam("BM.Style.STYLE_DEFAULT2", STYLE_DEFAULT); //默认配色，可为空值，表示使用系统默认(即白底黑字)。
			STYLE_DISABLE2 = getSysParam("BM.Style.STYLE_DISABLE2", STYLE_DISABLE); //默认配色，可为空值，表示使用系统默认(即白底黑字)。
			STYLE_TXN_STATUS_PROC2 = getSysParam("BM.Style.STYLE_TXN_STATUS_PROC2", STYLE_TXN_STATUS_PROC2); //交易处理中 配色，范例："color:blue;" 或 "color:#66cccc;" 或 "background-color:pink;color:blue;font-weight:bold;"
			STYLE_TXN_STATUS_OK2 = getSysParam("BM.Style.STYLE_TXN_STATUS_OK2", STYLE_TXN_STATUS_OK2); //交易成功 配色，范例："color:blue;" 或 "color:#66cccc;" 或 "background-color:pink;color:blue;font-weight:bold;"
			STYLE_TXN_STATUS_FAILED2 = getSysParam("BM.Style.STYLE_TXN_STATUS_FAILED2", STYLE_TXN_STATUS_FAILED2); //交易失败 配色，范例："color:blue;" 或 "color:#66cccc;" 或 "background-color:pink;color:blue;font-weight:bold;"
			STYLE_CASH_POOL = getSysParam("BM.Style.STYLE_CASH_POOL", STYLE_CASH_POOL); //优先出款的资金池 配色，范例："color:blue;" 或 "color:#66cccc;" 或 "background-color:pink;color:blue;font-weight:bold;"
			
//			logger.debug("[ParamCache] STYLE_DEFAULT: " + STYLE_DEFAULT);
//			logger.debug("[ParamCache] STYLE_DISABLE: " + STYLE_DISABLE);
//			logger.debug("[ParamCache] STYLE_TXN_STATUS_PROC: " + STYLE_TXN_STATUS_PROC);
//			logger.debug("[ParamCache] STYLE_TXN_STATUS_OK: " + STYLE_TXN_STATUS_OK);
//			logger.debug("[ParamCache] STYLE_TXN_STATUS_FAILED: " + STYLE_TXN_STATUS_FAILED);			
		}
		logger.info("-ParamCache 初始化完成-");
	}
	
	
	/**
	 * 获取(渠道)商户参数，若无结果则返回默认值.
	 * @param paramId 参数名(ID)
	 * @param defaultVal 默认值
	 * @return 参数值，若无结果则返回默认值
	 */
	protected static String getSysParam(String paramId, String defaultVal) {
		return MerParamsClient.getParam(PARAM.CH_NONE, PARAM.MER_NONE, PARAM.CAT_SYS, paramId, defaultVal);
	}	
	
	/**
	 * 获取(渠道)商户参数，若无结果则返回默认值.
	 * @param paramId 参数名(ID)
	 * @param defaultVal 默认值
	 * @return 参数值，若无结果则返回默认值
	 */
	protected static Long getSysParam(String paramId, Long defaultVal) {
		String s = MerParamsClient.getParam(PARAM.CH_NONE, PARAM.MER_NONE, PARAM.CAT_SYS, paramId);
		if (s==null) return defaultVal;
		try {
			return Long.parseLong(s);
		} catch (Exception e) {
			return defaultVal;
		}
	}	
	
	/**
	 * 获取(渠道)商户参数，若无结果则返回默认值.
	 * @param paramId 参数名(ID)
	 * @param defaultVal 默认值
	 * @return 参数值，若无结果则返回默认值
	 */
	protected static Integer getSysParam(String paramId, Integer defaultVal) {
		String s = MerParamsClient.getParam(PARAM.CH_NONE, PARAM.MER_NONE, PARAM.CAT_SYS, paramId);
		if (s==null) return defaultVal;
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return defaultVal;
		}
	}		
	
}
