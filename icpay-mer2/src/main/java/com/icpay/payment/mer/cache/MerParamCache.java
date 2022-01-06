package com.icpay.payment.mer.cache;

import org.apache.log4j.Logger;

import com.icpay.payment.common.constants.Names.PARAM;
import com.icpay.payment.common.utils.logger.Log;
import com.icpay.payment.service.client.MerParamsClient;

public final class MerParamCache implements ICache {

	private static final Logger logger = Logger.getLogger(MerParamCache.class);

	private static MerParamCache instance = new MerParamCache();

	private MerParamCache() { }

	public static MerParamCache getInstance() {
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

	/** 登入可重试次数 */
	public static Integer DefaultLoginRetries = 5;

	/** 谷歌验证效期 */
    public static Integer GA_Timeout = 30*60*1000; //TODO 设置时间在数据库
	/** 谷歌验证效期(Session) */
    public static Integer GA_Session_Expire_Time = GA_Timeout + 120*1000;
    /** 谷歌验证码连续输入错误次数 */
    public static Integer GA_Login_Failed_Count = 3;
    /** 谷歌验证码连续输入错误时间范围 */
    public static Integer GA_Login_Failed_Time_Limit = 180;

	private static final Object param_load_lock = new Object();

	private void load() {
		logger.info("-MerParamCache 初始化开始-");

		synchronized (param_load_lock) {
			DefaultLoginRetries= getSysParam("DefaultLoginRetries", DefaultLoginRetries);
			GA_Timeout= getSysParam("GA_Timeout", GA_Timeout);
			GA_Session_Expire_Time= getSysParam("GA_Session_Expire_Time", GA_Session_Expire_Time);
			GA_Login_Failed_Count= getSysParam("GA_Login_Failed_Count", GA_Login_Failed_Count);
			GA_Login_Failed_Time_Limit= getSysParam("GA_Login_Failed_Time_Limit", GA_Login_Failed_Time_Limit);
		}

		Log.debug().message("[MerParamCache][D][load] %s: %s ","DefaultLoginRetries", DefaultLoginRetries).submit();;
		Log.debug().message("[MerParamCache][D][load] %s: %s ","GA_Timeout", GA_Timeout).submit();;
		Log.debug().message("[MerParamCache][D][load] %s: %s ","GA_Session_Expire_Time", GA_Session_Expire_Time).submit();;
		Log.debug().message("[MerParamCache][D][load] %s: %s ","GA_Login_Failed_Count", GA_Login_Failed_Count).submit();;
		Log.debug().message("[MerParamCache][D][load] %s: %s ","GA_Login_Failed_Time_Limit", GA_Login_Failed_Time_Limit).submit();;

		logger.info("-MerParamCache 初始化完成-");
	}


	protected static final String PARAM_CAT="MER";

	/**
	 * 获取(渠道)商户参数，若无结果则返回默认值.
	 * @param paramId 参数名(ID)
	 * @param defaultVal 默认值
	 * @return 参数值，若无结果则返回默认值
	 */
	protected static String getSysParam(String paramId, String defaultVal) {
		return MerParamsClient.getParam(PARAM.CH_FRONT, PARAM.MER_DEFAULT, PARAM_CAT, paramId, defaultVal);
	}

	/**
	 * 获取(渠道)商户参数，若无结果则返回默认值.
	 * @param paramId 参数名(ID)
	 * @param defaultVal 默认值
	 * @return 参数值，若无结果则返回默认值
	 */
	protected static Long getSysParam(String paramId, Long defaultVal) {
		String s = MerParamsClient.getParam(PARAM.CH_FRONT, PARAM.MER_DEFAULT, PARAM_CAT, paramId);
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
		String s = MerParamsClient.getParam(PARAM.CH_FRONT, PARAM.MER_DEFAULT, PARAM_CAT, paramId);
		if (s==null) return defaultVal;
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return defaultVal;
		}
	}

}
