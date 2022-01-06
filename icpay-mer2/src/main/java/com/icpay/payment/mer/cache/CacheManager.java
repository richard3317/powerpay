package com.icpay.payment.mer.cache;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.icpay.payment.common.utils.SysConfig;

public class CacheManager {

	private static final Logger logger = Logger.getLogger(CacheManager.class);
	private static final Object threadLocker=new Object();
	private static Thread t=null;
	private static volatile boolean state = true;
	private static Map<String, ICache> caches = new TreeMap<String, ICache>();
	private static Boolean _inited = false;
	private static Integer CACHE_REFRESH_INTERVAL = 3*60*180;
	private static final String DEFAULT_CACHE_REFRESH_INTERVAL = "3";
	
	
//	static {
//		initCaches();
//	}
	
	/**
	 * 初始化全部缓存
	 */
	public static synchronized void initCaches() {
		if (_inited) return ;
		Map<String, ICache> cacheMap = new TreeMap<String, ICache>();
		cacheMap.put(CacheType.SYS_CONFIG_CACHE.getCacheTp(), MerConfigCache.getInstance()); 	// 注册配置文件信息
		cacheMap.put(CacheType.PAGE_CONF_CACHE.getCacheTp(), PageConfCache.getInstance());
		cacheMap.put(CacheType.MER_PARAMS_CACHE.getCacheTp(), MerParamCache.getInstance());
		cacheMap.put(CacheType.SITE_INFO_CACHE.getCacheTp(), SiteInfoCache.getInstance());
		
		try {
			CACHE_REFRESH_INTERVAL = 
					Integer.parseInt(
							SysConfig.getProperty(
									SysConfig.SERVER_CACHE_REFRESH_INTERVAL, DEFAULT_CACHE_REFRESH_INTERVAL)
							) 
					* 60 * 1000;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			CACHE_REFRESH_INTERVAL = 3*60*180;
		}
		
		synchronized (threadLocker) {
			CacheManager.caches = cacheMap;
		}
		//CacheManager.refreshAllCaches();
		start();
		_inited = true;
	}

	/**
	 * 刷新全部缓存
	 */
	public static void refreshAllCaches() {
		logger.info("[CacheManager] === 刷新全部已注册的缓存开始，共【" + caches.size() + "】个缓存待刷新 ===");
		if (caches==null) return;
		for(Entry<String, ICache> ent: caches.entrySet()){
//			String.format("[CacheManager] 刷新 %s ...", ent.getKey());
//			ent.getValue().refresh();
			refreshCache(ent.getKey());
		}
		logger.info("[CacheManager] === 刷新全部已注册的缓存完成 ===");
	}

	/**
	 * 根据指定的缓存类型刷新缓存
	 * @param cacheType
	 */
	public static void refreshCache(String cacheType) {
		if (cacheType == null || !caches.containsKey(cacheType)) {
			throw new IllegalArgumentException("[CacheManager] 指定的缓存【" + cacheType + "】未注册");
		}
		CacheType ct = CacheType.getCacheType(cacheType);
		logger.info("[CacheManager] 刷新指定的缓存【" + ct.getCacheTpDesc() + "】开始");
		caches.get(cacheType).refresh();
		logger.info("[CacheManager] 刷新指定的缓存【" + ct.getCacheTpDesc() + "】完成");
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
	
	
	/**
	 * 启动 CacheManager
	 */
	public synchronized static void start() {
		synchronized (threadLocker) {
			if (t != null) return;
			
			t = new Thread() {
				public void run() {
					while (state) {
						try {
							refreshAllCaches();
						} catch (Exception e) {
							logger.error("[CacheManager] CacheManager catch exception",
									e);
						}
						try {
							Thread.sleep(CACHE_REFRESH_INTERVAL);
						} catch (InterruptedException e) {
							logger.warn("[CacheManager] Interrupted.");
							return;
						}
					}
					logger.info("[CacheManager] CacheManager destoryed.");
					t = null;
				}

			};
		}
		
		t.setDaemon(true);
		t.start();
		logger.info("[CacheManager] CacheManager Start successfully.");
	}
	
	public  synchronized static void stop() {
		state = false;
		if (t!=null)
			try {
				t.interrupt();
			} catch (Exception e) {
			}
		logger.info("[CacheManager] CacheManager Stopped!");
	}	


}
