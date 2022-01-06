package com.icpay.payment.batch.cache;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class CacheManager {

	private static final Logger logger = Logger.getLogger(CacheManager.class);
	
	private static Map<String, ICache> caches = new TreeMap<String, ICache>();
	static {
		caches.put(CacheType.SYS_CONFIG_CACHE.getCacheTp(), BatchConfigCache.getInstance()); 	// 注册配置文件信息
		caches.put(CacheType.DB_CACHE.getCacheTp(), DBCache.getInstance());
	}
	
	/** 
	 * 初始化全部缓存
	 */
	public static void initCaches() {
		logger.info("===初始化全部已注册的缓存开始，共【" + caches.size() + "】个缓存待初始化===");
		for (String key : caches.keySet()) {
			CacheType ct = CacheType.getCacheType(key);
			logger.info("初始化指定的缓存【" + ct.getCacheTpDesc() + "】开始");
			caches.get(key).init();
			logger.info("初始化指定的缓存【" + ct.getCacheTpDesc() + "】完成");
		}
		logger.info("===初始化全部已注册的缓存完成===");
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
		logger.info("刷新指定的缓存【" + ct.getCacheTpDesc() + "】开始");
		caches.get(cacheType).refresh();
		logger.info("刷新指定的缓存【" + ct.getCacheTpDesc() + "】完成");
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
	

}
