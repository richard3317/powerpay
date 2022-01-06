package com.icpay.payment.mer.cache;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.icpay.payment.common.utils.JsonUtils;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.SiteInfo;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.ISiteInfoService;

/**
 * SiteInfo info = SiteInfoCache.getSiteInfo("LL");
 * SiteInfo info2 = SiteInfoCache.getInstance().get("LL");
 * @author robin
 *
 */
public final class SiteInfoCache implements ICache {

	private static final Logger logger = Logger.getLogger(SiteInfoCache.class);

	private static SiteInfoCache instance = new SiteInfoCache();
	
	private Map<String, SiteInfo> cacheMap = new HashMap<>();
	private Map<String, SiteInfo> domainMap = new HashMap<>();
	private Map<String, String> mchntSiteCache = new HashMap<>();
	private Map<String, Map<String,String>> merParamsCache = new HashMap<>();
	private Map<String, Map<String,String>> gatewayParamsCache = new HashMap<>();
	
	private static final Object locker = new Object();
	private static final Object lockerSiteCache = new Object();

	private SiteInfoCache() { }

	public static SiteInfoCache getInstance() {
		return instance;
	}
	
	public SiteInfo get(String siteId) {
		synchronized (locker) {
			return cacheMap.get(siteId);
		}
	}
	
	public SiteInfo getByRequest(HttpServletRequest req) {
		String domainName = req.getServerName();
		return getByDomain(domainName);
	}
	
	public SiteInfo getByDomain(String domainName) {
		synchronized (locker) {
			return domainMap.get(domainName);
		}
	}
	
	public static SiteInfo getSiteInfo(String siteId) {
		return getInstance().get(siteId);
	}
	
	public static SiteInfo getSiteByDomain(String domainName) {
		return getInstance().getByDomain(domainName);
	}
	
	public static String getMerParam(String siteId, String key, String defaultVal) {
		Map<String,String> params = getInstance().merParamsCache.get(siteId);
		if (params==null) return defaultVal;
		String val = params.get(key);
		if (val==null) return defaultVal;
		return val;
	}
	
	public static String getGatewayParam(String siteId, String key, String defaultVal) {
		Map<String,String> params = getInstance().gatewayParamsCache.get(siteId);
		if (params==null) return defaultVal;
		String val = params.get(key);
		if (val==null) return defaultVal;
		return val;
	}
	
	
	@Override
	public synchronized void init() {
		logger.info("-SiteInfoCache 初始化开始-");
		this.load();
		logger.info("-SiteInfoCache 初始化完成-");
	}

	@Override
	public void refresh() {
		logger.info("-SiteInfoCache 刷新开始-");
		this.load();
		logger.info("-SiteInfoCache 刷新完成-");
	}

	@Override
	public void clear() {

	}

	private void load() {
		
		Map<String, SiteInfo> cmap = new HashMap<>();
		Map<String, SiteInfo> dmap = new HashMap<>();
		
		Map<String, Map<String,String>> mpCache = new HashMap<>();
		Map<String, Map<String,String>> gpCache = new HashMap<>();
		
		
		List<SiteInfo> list = loadFromDatabase();
		
		for(SiteInfo s: list) {
			cmap.put(s.getSiteId(), s);
			dmap.put(s.getMerDomain(), s);
			prepareParams(s, mpCache, gpCache);
		}
		
		synchronized (locker) {
			cacheMap = cmap;
			domainMap= dmap;
			merParamsCache = mpCache;
			gatewayParamsCache = gpCache;
		}
//		synchronized (lockerSiteCache) { 
//			mchntSiteCache = new HashMap<>();// 清除/刷新
//		}
	}

	private List<SiteInfo> loadFromDatabase() {
		ISiteInfoService service = DBHessionServiceClient.getService(ISiteInfoService.class);
		List<SiteInfo> si = service.getAllSiteInfo();	
		return si;
	}
	
	public String getMchntSite(String mchntCd) {
		//Read from cache first
		//if read cache failed Read from database
		String siteId = null;
		synchronized (lockerSiteCache) {
			siteId = mchntSiteCache.get(mchntCd);
			if (siteId == null) {
				MchntInfo m = readMchntInfo(mchntCd);
				if (m==null) return null;
				siteId = m.getSiteId();
				mchntSiteCache.put(mchntCd, siteId);
			}
		}
		return siteId;
	}
	
	public static MchntInfo readMchntInfo(String mchntCd) {
		//TODO read data from database
		IMchntInfoService service = DBHessionServiceClient.getService(IMchntInfoService.class);
		MchntInfo mi = service.selectByPrimaryKey(mchntCd);
		return mi;
	}
	
//	Map<String, String> getMerParams(String siteId){
//		
//	}
	
	protected void prepareParams(SiteInfo site, Map<String, Map<String,String>> mpCache, Map<String, Map<String,String>> gpCache) {
		if (site==null) return;
		String merJson = site.getMerParams();
		String gwJson = site.getGatewayParams();
		if (!Utils.isEmpty(merJson)) {
			try {
				Map<String,String> map = new HashMap();
				map = JsonUtils.deserialize(merJson, map.getClass());
				mpCache.put(site.getSiteId(), map);
			} catch (IOException e) {
			}
		}
		if (!Utils.isEmpty(gwJson)) {
			try {
				Map<String,String> map = new HashMap();
				map = JsonUtils.deserialize(gwJson, map.getClass());
				gpCache.put(site.getSiteId(), map);
			} catch (IOException e) {
			}
		}
	}

}
