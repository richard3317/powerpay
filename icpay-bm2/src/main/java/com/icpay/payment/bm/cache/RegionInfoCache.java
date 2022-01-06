package com.icpay.payment.bm.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.RegionInfo;
import com.icpay.payment.db.service.IRegionInfoService;

/**
 * 行政区划信息缓存
 *
 */
public final class RegionInfoCache extends CacheBase implements ICache{

	private static final Logger logger = Logger.getLogger(RegionInfoCache.class);
	private static final RegionInfoCache INSTANCE = new RegionInfoCache();
	
	private Map<String, RegionInfo> regionCache = null;
	private Map<String, String> provCache = null;
	private Map<String, List<RegionInfo>> cityCache = null;
	private Map<String, List<RegionInfo>> districtCache = null;
	
	private RegionInfoCache() {}
	
	public static RegionInfoCache getInstance() {
		return INSTANCE;
	}
	
	/**
	 * 直辖市列表
	 */
	private static final Set<String> dirCitySet = new LinkedHashSet<String>();
	static {
		dirCitySet.add("110000"); // 北京市
		dirCitySet.add("120000"); // 天津市
		dirCitySet.add("310000"); // 上海市
		dirCitySet.add("500000"); // 重庆市
	}
	/**
	 * 特别行政区
	 */
	public static final Set<String> specialAreaSet = new HashSet<String>();
	static {
		specialAreaSet.add("810000"); // 香港
		specialAreaSet.add("820000"); // 澳门
	}
	
	/**
	 * 省会城市列表
	 */
	private static final Set<String> capitalCitySet = new LinkedHashSet<String>();
	static {
		capitalCitySet.add("110000"); // 北京市
		capitalCitySet.add("120000"); // 天津市
		capitalCitySet.add("310000"); // 上海市
		capitalCitySet.add("500000"); // 重庆市
		capitalCitySet.add("130100"); // 石家庄
		capitalCitySet.add("410100"); // 郑州
		capitalCitySet.add("420100"); // 武汉市
		capitalCitySet.add("430100"); // 长沙市
		capitalCitySet.add("320100"); // 南京市
		capitalCitySet.add("360100"); // 南昌市
		capitalCitySet.add("210100"); // 沈阳市
		capitalCitySet.add("220100"); // 长春市
		capitalCitySet.add("230100"); // 哈尔滨市
		capitalCitySet.add("610100"); // 西安市
		capitalCitySet.add("140100"); // 太原市
		capitalCitySet.add("370100"); // 济南市
		capitalCitySet.add("510100"); // 成都市
		capitalCitySet.add("630100"); // 西宁市
		capitalCitySet.add("340100"); // 合肥市
		capitalCitySet.add("460100"); // 海口市
		capitalCitySet.add("440100"); // 广州市
		capitalCitySet.add("520100"); // 贵阳市
		capitalCitySet.add("330100"); // 杭州市
		capitalCitySet.add("350100"); // 福州市
		capitalCitySet.add("620100"); // 兰州市
		capitalCitySet.add("530100"); // 昆明市
		capitalCitySet.add("540100"); // 拉萨市
		capitalCitySet.add("640100"); // 银川市
		capitalCitySet.add("450100"); // 南宁市
		capitalCitySet.add("650100"); // 乌鲁木齐市
		capitalCitySet.add("150100"); // 呼和浩特市
	}
	
	/**
	 * 获取省份列表
	 * @return
	 */
	public static Map<String, String> getProvMap() {
		if (INSTANCE.provCache == null) {
			return Collections.emptyMap();
		} else {
			return INSTANCE.provCache;
		}
	}
	
	public static String regionDesc(String regionCd) {
		String desc = regionCd;
		if (INSTANCE.regionCache != null) {
			RegionInfo r = INSTANCE.regionCache.get(regionCd);
			if (r != null) {
				if (CommonEnums.RegionLvl._1.getCode().equals(r.getRegionLvl())) {
					desc = r.getRegionCnNm();
				} else if (CommonEnums.RegionLvl._2.getCode().equals(r.getRegionLvl())) {
					String provCd = r.getProvRegionCd();
					RegionInfo prov = INSTANCE.regionCache.get(provCd);
					desc = prov.getRegionCnNm() + "-" + r.getRegionCnNm();
				}
			}
		}
		return desc;
	}
	
	/**
	 * 根据省份获取城市列表
	 * @param provCd
	 * @return
	 */
	public static List<RegionInfo> getCityLst(String provCd) {
		List<RegionInfo> result = Collections.emptyList();
		// 如果是直辖市，则返回自己
		if (dirCitySet.contains(provCd)) {
			result = new ArrayList<RegionInfo>();
			result.add(INSTANCE.regionCache.get(provCd));
		} else if (INSTANCE.cityCache != null && INSTANCE.cityCache.get(provCd) != null) {
			result = INSTANCE.cityCache.get(provCd);
		}
		return result;
	}
	
	public static List<RegionInfo> getDistrictLst(String cityCd) {
		return INSTANCE.districtCache.get(cityCd);
	}
	
	public static RegionInfo getRegionInfo(String regionCd) {
		return INSTANCE.regionCache.get(regionCd);
	}
	
	public static Set<String> getDirCitySet() {
		return Collections.unmodifiableSet(dirCitySet);
	}
	
	public static boolean isDirectCity(String regionCd) {
		return dirCitySet.contains(regionCd);
	}
	
	@Override
	public synchronized void init() {
		logger.info("初始化行政区划信息缓存开始");
		try {
			IRegionInfoService service = DBHessionServiceClient.getService(IRegionInfoService.class);
			
			Map<String, RegionInfo> regionTmp = new HashMap<String, RegionInfo>();
			Map<String, String> provTmp = new LinkedHashMap<String, String>();
			Map<String, List<RegionInfo>> cityTmp = new LinkedHashMap<String, List<RegionInfo>>();
			Map<String, List<RegionInfo>> districtCacheTmp = new LinkedHashMap<String, List<RegionInfo>>();
			
			List<RegionInfo> lst = service.selectAll();
			for (RegionInfo r : lst) {
				if (specialAreaSet.contains(r.getRegionCd())) {
					continue;
				}
				regionTmp.put(r.getRegionCd(), r);
				String provCd = r.getProvRegionCd();
				String cityCd = r.getCityRegionCd();
				if (CommonEnums.RegionLvl._1.getCode().equals(r.getRegionLvl())) {
					provTmp.put(r.getRegionCd(), r.getRegionCnNm());
				} else if (CommonEnums.RegionLvl._2.getCode().equals(r.getRegionLvl())) {
					if (!dirCitySet.contains(provCd)) {
						List<RegionInfo> cityLst = cityTmp.get(provCd);
						if (cityLst == null) {
							cityLst = new ArrayList<RegionInfo>();
							cityTmp.put(provCd, cityLst);
						}
						// 省会城市在前
						if (capitalCitySet.contains(r.getRegionCd())) {
							cityLst.add(0, r);
						} else {
							cityLst.add(r);
						}
					}
				} else if (CommonEnums.RegionLvl._3.getCode().equals(r.getRegionLvl())) {
					if (!dirCitySet.contains(provCd)) {
						// 如果不是直辖市，则用城市地区码对应各区县
						List<RegionInfo> districtLst = districtCacheTmp.get(cityCd);
						if (districtLst == null) {
							districtLst = new ArrayList<RegionInfo>();
							districtCacheTmp.put(cityCd, districtLst);
						}
						districtLst.add(r);
					} else {
						// 如果是直辖市，则用直辖市的地区码对应下属各区
						List<RegionInfo> districtLst = districtCacheTmp.get(provCd);
						if (districtLst == null) {
							districtLst = new ArrayList<RegionInfo>();
							districtCacheTmp.put(provCd, districtLst);
						}
						districtLst.add(r);
					}
				}
			}
			
			this.regionCache = regionTmp;
			this.provCache = provTmp;
			this.cityCache = cityTmp;
			this.districtCache = districtCacheTmp;
			
			logger.info("初始化行政区划信息缓存完成");
		} catch (Exception e) {
			logger.error("初始化行政区划信息缓存失败", e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void clear() {
		logger.info("清理行政区划信息缓存开始");
		if (regionCache != null) {
			regionCache.clear();
			regionCache = null;
		}
		if (provCache != null) {
			provCache.clear();
			provCache = null;
		}
		if (cityCache != null) {
			cityCache.clear();
			cityCache = null;
		}
		logger.info("清理行政区划信息缓存完成");
	}

	@Override
	public void refresh() {
		logger.info("刷新行政区划信息缓存开始");
		this.init();
		logger.info("刷新行政区划信息缓存完成");
	}

}
