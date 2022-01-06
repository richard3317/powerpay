package com.icpay.payment.bm.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.FileLineHandler;
import com.icpay.payment.common.utils.FileUtil;
import com.icpay.payment.common.utils.StringUtil;

public final class ValidationConfCache extends CacheBase implements ICache {
	
	private static final Logger logger = Logger.getLogger(ValidationConfCache.class);
	
	private static final ValidationConfCache INSTANCE = new ValidationConfCache();
	
	private static final String CONFIG_SPLITTER = "=";
	
	private Map<String, String> cache = null;
	
	private ValidationConfCache() { }
	
	public static ValidationConfCache getInstance() {
		return INSTANCE;
	}
	
	public static String getConfig(String configKey) {
		if (INSTANCE.cache == null) {
			INSTANCE.init();
		}
		return INSTANCE.cache.get(configKey);
	}
	
	@Override
	public synchronized void init() {
		logger.info("初始化验证配置信息缓存信息开始");
		
		try {
			String filePath = this.getClass().getClassLoader().getResource("validation.conf").getFile();
			final Map<String, String> temp = new HashMap<String, String>();
			// 逐行处理配置文件
			FileUtil.readFileByLine(filePath, "UTF-8", new FileLineHandler() {
				@Override
				public void handleLine(String line) {
					if (StringUtil.isBlank(line.trim())) {
						//logger.info("空行跳过..");
						return;
					}
					String[] config = line.split(CONFIG_SPLITTER);
					if (config.length != 2) {
						logger.error("配置不符合规则:" + line);
						return;
					}
					temp.put(config[0].trim(), config[1].trim());
				}
			});
			cache = temp;
		} catch (Exception e) {
			throw new BizzException("初始化验证配置信息缓存信息失败", e);
		}
		
		logger.info("初始化验证配置信息缓存信息完成");
	}

	@Override
	public void refresh() {
		logger.info("刷新验证配置信息缓存信息开始");
		this.init();
		logger.info("刷新验证配置信息缓存信息完成");
	}
	
	@Override
	public void clear() {
		logger.info("清空验证配置信息缓存信息开始");
		if (cache != null) {
			cache.clear();
			cache = null;
		}
		logger.info("清空验证配置信息缓存信息完成");
	}
}