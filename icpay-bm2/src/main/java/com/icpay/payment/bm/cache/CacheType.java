package com.icpay.payment.bm.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public enum CacheType {

	SYS_CONFIG_CACHE("00", "系统配置文件缓存"),
	DATA_DIC_CACHE("01", "数据字典缓存"),
	PAGE_CONF_CACHE("02", "页面配置信息缓存"),
	AUTH_DATA_CACHE("03", "权限信息缓存"),
	VALIDATION_CONF_CACHE("05", "校验配置信息缓存"),
	MCHNT_TP_CACHE("06", "商户类型缓存"),
	REGION_INFO_CACHE("07", "行政区划信息缓存"),
	AGENT_INFO_CACHE("08", "代理商信息缓存"),
	BANK_NUMS_CACHE("09", "银行编号缓存"),
	CASH_POOL_CACHE("10", "资金池缓存"),
	
	MCHNT_CACHE("11", "商户数据缓存"),
	CHNL_MCHNT_CACHE("12", "渠道商户数据缓存"),
	ORGAN_INFO_CACHE("13", "机构缓存"),
	RISK_GROUP_CACHE("21", "风险组信息缓存"),
	PARAM_CACHE("31", "系统参数缓存"),
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

	@Override
	public String toString() {
		return String.format("%s-%s", this.getCacheTp(), this.getCacheTpDesc());
	}
	
}