package com.icpay.payment.batch.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntMappingInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.VirtualTermInfo;
import com.icpay.payment.db.service.IChnlMchntMappingInfoService;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.IVirtualTermInfoService;

public final class DBCache implements ICache {
	
	private static final Logger logger = Logger.getLogger(DBCache.class);
	
	private static DBCache instance = new DBCache();
	
	private static Map<String, String> CHNLMCHNTMAPPING_CACHE = new HashMap<String, String>();
	private static Map<String, String> VIRTUALTERM_CACHE = new HashMap<String, String>();
	private static Map<String, String> MCHNT_NM_CACHE = new HashMap<String, String>();
	
	private DBCache() { }
	
	public static DBCache getInstance() {
		return instance;
	}
	
	public static String getVirtualTermId(String mchntCd, String chnlId) {
		return VIRTUALTERM_CACHE.get(mchntCd + "_" + chnlId);
	}
	
	public static String getMchntCd(String chnlId, String chnlMchntCd) {
		return CHNLMCHNTMAPPING_CACHE.get(chnlId + "_" + chnlMchntCd);
	}
	
	public static String getMchntNm(String mchntCd) {
		if (MCHNT_NM_CACHE.isEmpty()) {
			return mchntCd;
		}
		return MCHNT_NM_CACHE.get(mchntCd) == null ? mchntCd : MCHNT_NM_CACHE.get(mchntCd);
	}

	@Override
	public synchronized void init() {
		IVirtualTermInfoService virtualTermInfoService = 
			DBHessionServiceClient.getService(IVirtualTermInfoService.class);
		
		// 空查询条件，用于查询全表
		Map<String, String> qryParamMap = new HashMap<String, String>();
		
		// 渠道虚拟终端信息
		List<VirtualTermInfo> vtLst = virtualTermInfoService.select(qryParamMap);
		Map<String, String> virtualTermCacheTemp = new HashMap<String, String>();
		for (VirtualTermInfo vt : vtLst) {
			virtualTermCacheTemp.put(vt.getMchntCd() + "_" + vt.getChnlId(), vt.getTermId());
		}
		VIRTUALTERM_CACHE = virtualTermCacheTemp;
		logger.info("渠道虚拟终端信息缓存条数:" + VIRTUALTERM_CACHE.size());
		
		// 渠道商户映射关系
		IChnlMchntMappingInfoService chnlMchntMappingService = 
			DBHessionServiceClient.getService(IChnlMchntMappingInfoService.class);
		List<ChnlMchntMappingInfo> mappingLst = chnlMchntMappingService.select(qryParamMap);
		Map<String, String> chnlMchntMappingTemp = new HashMap<String, String>();
		for (ChnlMchntMappingInfo mapping : mappingLst) {
			chnlMchntMappingTemp.put(mapping.getChnlId() + "_" + mapping.getChnlMchntCd(), mapping.getMchntCd());
		}
		CHNLMCHNTMAPPING_CACHE = chnlMchntMappingTemp;
		logger.info("渠道商户映射关系缓存条数:" + CHNLMCHNTMAPPING_CACHE.size());
		
		IMchntInfoService mchntService = DBHessionServiceClient.getService(IMchntInfoService.class);
		List<MchntInfo> mLst = mchntService.select(qryParamMap);
		Map<String, String> mchntNmMapTemp = new HashMap<String, String>();
		for (MchntInfo m : mLst) {
			mchntNmMapTemp.put(m.getMchntCd(), m.getMchntCnNm());
		}
		MCHNT_NM_CACHE = mchntNmMapTemp;
	}
	
	@Override
	public void refresh() {
	}
	
	@Override
	public void clear() {
		
	}
}
