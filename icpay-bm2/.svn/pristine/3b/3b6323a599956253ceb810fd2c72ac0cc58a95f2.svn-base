package com.icpay.payment.bm.cache;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.service.IMchntInfoService;

@Deprecated
public final class MchntInfoLazyCache extends CacheBase implements ICache{

	private static final Logger logger = Logger.getLogger(MchntInfoLazyCache.class);
	private static final MchntInfoLazyCache INSTANCE = new MchntInfoLazyCache();
	
	private Map<String, MchntInfo> cache = new HashMap<>();
	
	private MchntInfoLazyCache() {
	}
	
	public static MchntInfoLazyCache getInstance() {
		return INSTANCE;
	}
	
	public MchntInfo get(String mchntCd) {
		if (cache==null) cache = new HashMap<>();
		MchntInfo info = cache.get(mchntCd);
		if (info==null) 
			return remoteInfo(mchntCd);
		else 
			return info;
	}
	
	protected synchronized MchntInfo remoteInfo(String pkey) {
		IMchntInfoService svc = DBHessionServiceClient.getService(IMchntInfoService.class);
		MchntInfo info = svc.selectByPrimaryKey(pkey);
		if (info!=null) {
			logger.info("缓存商户数据: "+pkey);
			cache.put(pkey, info);
			logger.info("缓存商户数据完成，条数:" + cache.size());
		}
		return info;
	}

	@Override
	public synchronized void init() {
		clear();
	}
	
	@Override
	public synchronized void clear() {
		logger.info("清理商户类型信息缓存开始");
		if (cache != null) {
			cache.clear();
			//cache = null;
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
