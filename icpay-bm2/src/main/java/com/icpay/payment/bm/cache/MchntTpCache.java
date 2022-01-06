package com.icpay.payment.bm.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.MchntTp;
import com.icpay.payment.db.service.IMchntTpService;

public final class MchntTpCache extends CacheBase implements ICache{

	private static final Logger logger = Logger.getLogger(MchntTpCache.class);
	private static final MchntTpCache INSTANCE = new MchntTpCache();
	
	private Map<String, String> cache = null;
	
	private MchntTpCache() {}
	
	public static MchntTpCache getInstance() {
		return INSTANCE;
	}
	
	public static Map<String, String> getMchntTpMap() {
		Map<String, String> m = new HashMap<String, String>();
		return INSTANCE.cache == null ? m : Collections.unmodifiableMap(INSTANCE.cache);
	}
	
	
	@Override
	public synchronized void init() {
		logger.info("初始化商户类型信息缓存开始");
		try {
			IMchntTpService service = DBHessionServiceClient.getService(IMchntTpService.class);
			Map<String, String> tmp = new TreeMap<String, String>();
			
			for (MchntTp mt : service.selectAll()) {
				String mchntTp = StringUtil.trim(mt.getMchntTp());
				tmp.put(mchntTp, mt.getMchntTpDescCn());
			}
			cache = tmp;
			logger.info("初始化商户类型信息缓存完成，条数:" + cache.size());
		} catch (Exception e) {
			logger.error("初始化商户类型信息缓存失败", e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void clear() {
		logger.info("清理商户类型信息缓存开始");
		if (cache != null) {
			cache.clear();
			cache = null;
		}
		logger.info("清理商户类型信息缓存完成");
	}

	@Override
	public void refresh() {
		logger.info("刷新商户类型信息缓存开始");
		this.init();
		logger.info("刷新商户类型信息缓存完成");
	}

}
