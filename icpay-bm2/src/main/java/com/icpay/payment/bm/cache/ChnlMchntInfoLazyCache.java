package com.icpay.payment.bm.cache;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoKey;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.service.IChnlMchntInfoService;
import com.icpay.payment.db.service.IMchntInfoService;

@Deprecated
public final class ChnlMchntInfoLazyCache extends CacheBase implements ICache{

	private static final Logger logger = Logger.getLogger(ChnlMchntInfoLazyCache.class);
	private static final ChnlMchntInfoLazyCache INSTANCE = new ChnlMchntInfoLazyCache();
	
	private Map<String, ChnlMchntInfo> cache = new HashMap<>();
	
	private ChnlMchntInfoLazyCache() {
		
	}
	
	public static ChnlMchntInfoLazyCache getInstance() {
		return INSTANCE;
	}
	
	public ChnlMchntInfo get(String chnlId, String chnlMchntCd,String currCd) {
		if (cache==null) cache = new HashMap<>();
		ChnlMchntInfo info = cache.get(toMapKey(chnlId, chnlMchntCd));
		if (info==null) 
			return remoteInfo(chnlId, chnlMchntCd,currCd);
		else 
			return info;
	}
	
	protected synchronized ChnlMchntInfo remoteInfo(String chnlId, String chnlMchntCd,String currCd) {
		IChnlMchntInfoService svc = DBHessionServiceClient.getService(IChnlMchntInfoService.class);
		ChnlMchntInfo info = svc.selectByPrimaryKey(new ChnlMchntInfoKey(chnlId, chnlMchntCd));
		if (info!=null) {
			logger.info(String.format("缓存渠道商户数据: %s-%s", chnlId, chnlMchntCd));
			cache.put(toMapKey(chnlId, chnlMchntCd), info);
			logger.info("缓存渠道商户数据完成，条数:" + cache.size());
		}
		return info;
	}
	
	protected String toMapKey(String chnlId, String chnlMchntCd) {
		return String.format("%s-%s", chnlId, chnlMchntCd);
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
