package com.icpay.payment.bm.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class CacheManager {

	protected static final Logger logger = Logger.getLogger(CacheManager.class);

	private static Map<String, ICache> caches = new TreeMap<String, ICache>();
	//private static List<CacheType> autoRefreshCaches = new ArrayList<>();
	private static Map<CacheType, Integer> autoRefreshCaches = new LinkedHashMap<>();
	private static Map<CacheType, Integer> refreshingCaches = null;

	public static final int INTERVAL_UNIT=60*1000; //单位:60秒

	private static RefreshThread mon = null;

	static {
		caches.put(CacheType.SYS_CONFIG_CACHE.getCacheTp(), BMConfigCache.getInstance()); 	// 注册配置文件信息
		caches.put(CacheType.DATA_DIC_CACHE.getCacheTp(), DataDicCache.getInstance()); // 数据字典缓存
		caches.put(CacheType.AUTH_DATA_CACHE.getCacheTp(), AuthDataCache.getInstance());
		caches.put(CacheType.VALIDATION_CONF_CACHE.getCacheTp(), ValidationConfCache.getInstance());
		caches.put(CacheType.PAGE_CONF_CACHE.getCacheTp(), PageConfCache.getInstance());
		caches.put(CacheType.MCHNT_TP_CACHE.getCacheTp(), MchntTpCache.getInstance()); // 商户MCC缓存
		caches.put(CacheType.REGION_INFO_CACHE.getCacheTp(), RegionInfoCache.getInstance()); // 行政区划信息缓存
		caches.put(CacheType.AGENT_INFO_CACHE.getCacheTp(), AgentInfoCache.getInstance()); // 代理商信息缓存

		//caches.put(CacheType.MCHNT_CACHE.getCacheTp(), MchntInfoLazyCache.getInstance()); // 商户信息缓存
		//caches.put(CacheType.CHNL_MCHNT_CACHE.getCacheTp(), ChnlMchntInfoLazyCache.getInstance()); // 商户信息缓存

		caches.put(CacheType.MCHNT_CACHE.getCacheTp(), MchntInfoCache.getInstance()); // 商户信息缓存
		caches.put(CacheType.CHNL_MCHNT_CACHE.getCacheTp(), ChnlMchntInfoCache.getInstance()); // 商户信息缓存
		caches.put(CacheType.RISK_GROUP_CACHE.getCacheTp(), RiskGroupInfoCache.getInstance()); // 风险组信息缓存

		caches.put(CacheType.BANK_NUMS_CACHE.getCacheTp(), BankNumCache.getInstance()); // 银行编号缓存
		caches.put(CacheType.PARAM_CACHE.getCacheTp(), ParamCache.getInstance()); // 系统参数缓存

		caches.put(CacheType.CASH_POOL_CACHE.getCacheTp(), CashPoolInfoCache.getInstance()); // 资金池缓存
		caches.put(CacheType.ORGAN_INFO_CACHE.getCacheTp(), OrganInfoCache.getInstance()); // 机构缓存

		//以下注册哪些缓存需要自动刷新
		autoRefreshCaches.put(CacheType.PAGE_CONF_CACHE, 15*INTERVAL_UNIT);
		autoRefreshCaches.put(CacheType.AUTH_DATA_CACHE, 20*INTERVAL_UNIT);

		autoRefreshCaches.put(CacheType.MCHNT_CACHE, 10*INTERVAL_UNIT); //指定多久刷新
		autoRefreshCaches.put(CacheType.CHNL_MCHNT_CACHE, 10*INTERVAL_UNIT);
		autoRefreshCaches.put(CacheType.AGENT_INFO_CACHE, 15*INTERVAL_UNIT);
		autoRefreshCaches.put(CacheType.RISK_GROUP_CACHE, 20*INTERVAL_UNIT);
		autoRefreshCaches.put(CacheType.BANK_NUMS_CACHE, 20*INTERVAL_UNIT);
		autoRefreshCaches.put(CacheType.PARAM_CACHE, 5*INTERVAL_UNIT); // 5 分钟

		autoRefreshCaches.put(CacheType.CASH_POOL_CACHE, 10*INTERVAL_UNIT);
		autoRefreshCaches.put(CacheType.ORGAN_INFO_CACHE, 10*INTERVAL_UNIT);
	}

	/**
	 * 初始化全部缓存
	 */
	public static synchronized void initCaches() {
		logger.info("=== 初始化全部已注册的缓存开始，共【" + caches.size() + "】个缓存待初始化 ===");
		stopping = false;
		for (String key : caches.keySet()) {
			CacheType ct = CacheType.getCacheType(key);
			logger.info("初始化指定的缓存【" + ct.getCacheTpDesc() + "】开始");
			caches.get(key).init();
			logger.info("初始化指定的缓存【" + ct.getCacheTpDesc() + "】完成");
		}
		if (mon==null) {
			mon= new RefreshThread();
			mon.start();
		}
		logger.info("=== 初始化全部已注册的缓存完成 ===");
	}

	private static boolean stopping = false;

	protected static synchronized boolean isStoping() {
		return stopping;
	}

	public static synchronized void stop() {
		stopping = true;
	}

	/**
	 * 刷新全部缓存
	 */
	public static void refreshAllCaches() {
		logger.info("===刷新全部已注册的缓存开始，共【" + caches.size() + "】个缓存待刷新===");
		for (ICache c : caches.values()) {
			c.refresh();
			logger.info("\n");
		}
		logger.info("===刷新全部已注册的缓存完成===");
	}

	/**
	 * 根据指定的缓存类型刷新缓存
	 * @param cacheType
	 */
	public static void refreshCache(String cacheType) {
		if (cacheType == null || !caches.containsKey(cacheType)) {
			throw new IllegalArgumentException("指定的缓存【" + cacheType + "】未注册");
		}
		CacheType ct = CacheType.getCacheType(cacheType);
		ICache  cache= caches.get(cacheType);
		if (cache==null) return;
		synchronized (ct) {
			logger.info("刷新指定的缓存【" + ct.getCacheTpDesc() + "】开始");
			cache.refresh();
			logger.info("刷新指定的缓存【" + ct.getCacheTpDesc() + "】完成");
		}
	}

	/**
	 * 根据指定的缓存类型刷新缓存
	 * @param cacheType
	 */
	public static boolean refreshCacheForNeeded(CacheType ct) {
		if (ct==null)
			throw new IllegalArgumentException("指定的缓存為空");
		String cacheType = ct.getCacheTp();
		if (!caches.containsKey(cacheType))
			throw new IllegalArgumentException("指定的缓存【" + cacheType + "】未注册");

		ICache cache= caches.get(cacheType);
		if ((cache==null) || (! cache.isNeedRefresh())) return false;
		synchronized (ct) {
			logger.info("立即刷新指定的缓存【" + ct.getCacheTpDesc() + "】开始");
			caches.get(cacheType).refresh();
			logger.info("立即刷新指定的缓存【" + ct.getCacheTpDesc() + "】完成");
		}
		return true;
	}


	/**
	 * 清空全部缓存
	 */
	public static void clearCaches() {
		logger.info("===清空全部已注册的缓存开始，共【" + caches.size() + "】个缓存待清空===");
		for (ICache c : caches.values()) {
			c.clear();
		}
		logger.info("===清空全部已注册的缓存完成===");
	}
	/**
	 * 根据缓存类型，获取缓存中的管理对象
	 * @param cacheKey
	 * @return
	 */
	public static ICache getCache(String cacheKey){
		return caches.get(cacheKey);
	}


	public static class RefreshThread extends Thread{
		public static int DEFAULT_INTERVAL=10*1000;

		private int refreshInterval = DEFAULT_INTERVAL ;

		public RefreshThread() {
			super();
			this.refreshInterval = DEFAULT_INTERVAL ;
		}

		/**
		 *
		 * @param refreshInterval 刷新周期(分钟)
		 */
		public RefreshThread(int secsInterval) {
			super();
			this.refreshInterval = secsInterval*1000;
		}

		protected void doRefresh() {
//			for(CacheType ct: autoRefreshCaches)
//				try {
//					doSingleRefresh(ct);
//				} catch (Exception e) {
//					CacheManager.logger.error(String.format("刷新缓存 %s 失败。 ", ct)+e.getMessage(), e);
//				}
			for(CacheType ct: refreshingCaches.keySet())
				try {
					doSingleRefresh(ct);
					try {
						Thread.sleep(100); //每個 cache 刷新的間隔
					} catch (InterruptedException e) { //偵測中斷
						return;
					}
				} catch (Exception e) {
					CacheManager.logger.error(String.format("刷新缓存 %s 失败。 ", ct)+e.getMessage(), e);
				}
		}

		protected boolean shouldRefresh(CacheType cacheTyp) {
			int interval=Integer.MAX_VALUE;
			boolean res=false;
			synchronized (refreshingCaches) {
				interval=refreshingCaches.get(cacheTyp) - this.refreshInterval;
				if (interval<=0) {
					interval=autoRefreshCaches.get(cacheTyp);
					res=true;
				}
				refreshingCaches.put(cacheTyp, interval);
			}
			return res;
		}

		protected void doSingleRefresh(CacheType cacheTyp) {
			if (CacheManager.refreshCacheForNeeded(cacheTyp)) return;
			if (shouldRefresh(cacheTyp)) {
				logger.info("自動初始化指定缓存【" + cacheTyp.getCacheTpDesc() + "】开始。。。");
				CacheManager.refreshCache(cacheTyp.getCacheTp());
				logger.info("自動初始化指定缓存【" + cacheTyp.getCacheTpDesc() + "】結束");
			}
		}

		@Override
		public void run() {
			refreshingCaches = new LinkedHashMap<>(autoRefreshCaches);
			while((!this.isInterrupted())&&(! CacheManager.isStoping())) {
				doRefresh();
				try {
					Thread.sleep(this.refreshInterval);
				} catch (InterruptedException e) {
					return;
				}

			}
		}

	}

}
