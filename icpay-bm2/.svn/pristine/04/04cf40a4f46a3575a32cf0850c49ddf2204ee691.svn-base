package com.icpay.payment.bm.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.log4j.Logger;

import com.icpay.payment.common.utils.Utils;

public abstract class CommonCacheBase  extends CacheBase implements ICache{

	//private static final CommonCacheBase INSTANCE = new CommonCacheBase();
	//	protected CommonCacheBase() {
	//	}
		
	//	public static CommonCacheBase getInstance() {
	//		return INSTANCE;
	//	}
	
	protected final Object cacheLock= new Object();
	private Map<String, Map<String,String>> cache = null;
	private boolean lazyMode=false;
	
	private Logger _logger=null;
	protected Logger getLogger() {
		if (_logger==null) {
			_logger = Logger.getLogger(this.getClass());
		}
		return _logger;
	}
	
	protected abstract String toMapKey(String... pkeys);
	/**
	 * LazyMode 为 true 时会被调用
	 * @param pkeys
	 * @return
	 */
	protected abstract Map<String,String> remoteInfo(String... pkeys);
	/**
	 * LazyMode 为 false 时会被调用
	 * @return
	 * @throws Exception 
	 */
	protected abstract Map<String, Map<String,String>> reloadAll() throws Exception;

	public boolean isLazyMode() {
		return lazyMode;
	}

	protected void setLazyMode(boolean lazyMode) {
		this.lazyMode = lazyMode;
	}
	
	protected List<Map<String,String>> getByKeyPattent(String keyPattern){
		List<Map<String,String>>  res = Utils.findInMapByPattern(cache, keyPattern);
		return res;
	}

	/**
	 * 获取物件(对象)值，结果以map表示
	 * @param pkeys 键值
	 * @return 结果，以map表示
	 */
	public Map<String,String> get(String... pkeys) {
		Map<String,String> rec = null;
		String mapKey=toMapKey(pkeys);
		synchronized(cacheLock) {
			if (cache==null) cache = createCache();
			rec = cache.get(mapKey);
		}
		if ((rec==null) && (isLazyMode())) 
			rec = cacheFromRemoteInfo(pkeys);
		return rec;
	}
	
	/**
	 * 获取物件(对象)值，结果以map表示
	 * @param pkeys 键值
	 * @return 结果，以map表示
	 */
	public Map<String,String> get(Object... pkeys) {
		String[] strs=new String[pkeys.length];
		int i=0;
		for(Object k: pkeys) {
			strs[i]=strVal(k);
			i++;
		}
		return get(strs);
	}
	
	protected Map<String, Map<String,String>> getCache(){
		synchronized (cacheLock) {
			return cache;
		}
	}
	
	public List<Map<String,String>> list(){
		List<Map<String,String>> list = new ArrayList<>();
		Map<String, Map<String,String>> curCache=null;
		synchronized (cacheLock) {
			if (this.cache==null) return list;
			curCache=this.cache;
		}
		
		for(String key: curCache.keySet().toArray(new String[0])) {
			list.add(cache.get(key));
		}
		return list;
	}
	
	public List<Map<String,String>> list(String... filters){
		List<Map<String,String>> list = new ArrayList<>();
		Map<String, Map<String,String>> curCache=this.getCache();
		if (curCache==null) return list;
		
		for(String key: curCache.keySet().toArray(new String[0])) {
			Map<String,String> rec=curCache.get(key);
			if (rec!=null) {
				if (Utils.isEmpty(filters))
					list.add(rec);
				else {
					boolean ok=true;
					for(int i=0; i<filters.length/2;i++) {
						int ndxName=i*2;
						int ndxValue=ndxName+1;
						if ((ndxName<filters.length)&&(ndxValue<filters.length)) {
							boolean filtered= strVal(filters[ndxValue]).equalsIgnoreCase(rec.get(filters[ndxName]));
							ok = ok && filtered;
						}
					}
					if (ok) list.add(rec);
				}
			}
		}
		return list;
	}
	

	
	protected Map<String,String> cacheFromRemoteInfo(String... pkeys){
		Map<String,String> rec = remoteInfo(pkeys);
		if (rec!=null) {
			getLogger().info(String.format("缓存遠端数据: %s; keys: %s", this.getClass().getSimpleName(), toMapKey(pkeys)));
			synchronized(cacheLock) {
				cache.put(toMapKey(pkeys), rec);
			}
			getLogger().info(String.format("缓存遠端数据完成: %s; 目前緩存數量: %s", this.getClass().getSimpleName(), cache.size()));
		}
		return rec;
	}

	@Override
	public synchronized void init() {
		if (isLazyMode()) {
			clear();
		}
		else {
			Map<String, Map<String, String>> newCache;
			try {
				newCache = reloadAll();
				this.refreshed();
				synchronized(cacheLock) {
					this.cache = newCache;
				}
			} catch (Exception e) {
				this.getLogger().error(String.format("Reload error : %s", e.getMessage()), e);
			}
		}
	}
	
	@Override
	public void clear() {
		getLogger().info("清理缓存开始: "+this.getClass().getSimpleName());
		if (cache != null) {
			synchronized(cacheLock) {
				cache.clear();
				cache=null;
				cache = createCache();
			}
		}
		getLogger().info("清理缓存完成: "+this.getClass().getSimpleName());
	}
	
	protected Map<String, Map<String,String>> createCache(){
		Map<String, Map<String,String>> map = new TreeMap<>();
		return map;
	}

	@Override
	public void refresh() {
		getLogger().info("刷新缓存开始: "+this.getClass().getSimpleName());
		this.init();
		getLogger().info("刷新完成: "+this.getClass().getSimpleName());
	}
	
	protected String strVal(int index, String... pkeys) {
		if (pkeys==null) return "";
		if (index<pkeys.length)
			return strVal(pkeys[index]);
		else
			return "";
	}
	
	protected String strVal(Object obj) {
		if (obj==null) return "";
		return obj.toString();
	}
	

}
