package com.icpay.payment.mer.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public enum CacheType {

	SYS_CONFIG_CACHE("00", "系统配置文件缓存"),
	DATA_DIC_CACHE("01", "数据字典缓存"),
	PAGE_CONF_CACHE("02", "页面配置信息缓存"),
	AUTH_DATA_CACHE("03", "权限信息缓存"),
	VALIDATION_CONF_CACHE("05", "校验配置信息缓存"),
	MER_PARAMS_CACHE("06", "商户平台参数缓存"),
	SITE_INFO_CACHE("07", "站点信息缓存")
	;

	private String cacheTp;
	private String cacheTpDesc;

	private CacheType(String tp, String desc) {
		this.cacheTp = tp;
		this.cacheTpDesc = desc;
	}

	/**
	 * 枚举编号到名称的MAP
	 *
	 * @return
	 */
	public static Map<String, String> getMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (CacheType e : CacheType.values()) {
			map.put(e.cacheTp, e.cacheTp + "-" + e.cacheTpDesc);
		}
		return map;
	}

	public static CacheType getCacheType(String cacheTp) {
		for (CacheType e : CacheType.values()) {
			if (e.getCacheTp().equals(cacheTp)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * @return the cacheTp
	 */
	public String getCacheTp() {
		return cacheTp;
	}

	/**
	 * @return the cacheTpDesc
	 */
	public String getCacheTpDesc() {
		return cacheTpDesc;
	}
}