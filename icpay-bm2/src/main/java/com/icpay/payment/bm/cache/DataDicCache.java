package com.icpay.payment.bm.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.DataDic;
import com.icpay.payment.db.service.IDataDicService;

public final class DataDicCache extends CacheBase implements ICache{

	private static final Logger logger = Logger.getLogger(DataDicCache.class);
	private static final DataDicCache INSTANCE = new DataDicCache();
	
	private Map<String, List<DataDic>> cache = null;
	
	private DataDicCache() {}
	
	public static DataDicCache getInstance() {
		return INSTANCE;
	}
	
	public static String getDataDicVal(String dataTp, String dataKey) {
		return INSTANCE.getDataValue(dataTp, dataKey);
	}
	
	public static List<DataDic> getDataDicLst(String dataTp) {
		List<DataDic> lst = INSTANCE.cache.get(dataTp);
		if (lst == null || lst.size() == 0) {
			return Collections.emptyList();
		} else {
			return Collections.unmodifiableList(lst);
		}
	}
	
	public static String translate(String dataTp, String dataKey) {
		String result = dataKey;
		String dataVal = INSTANCE.getDataValue(dataTp, dataKey);
		if (!StringUtil.isBlank(dataVal)) {
			result = dataKey + "-" + dataVal;
		}
		return result;
	}
	
	/**
	 * 获取全部的数据字典类型
	 * @return
	 */
	public static List<String> getAllDataTp() {
		if (INSTANCE.cache == null) {
			logger.error("数据字典缓存未初始化");
		}
		List<String> lst = new ArrayList<String>();
		for (String dt : INSTANCE.cache.keySet()) {
			lst.add(dt);
		}
		return lst;
	}

	@Override
	public synchronized void init() {
		logger.info("初始化数据字典信息缓存开始");
		try {
			IDataDicService dataDicService = DBHessionServiceClient.getService(IDataDicService.class);
			Map<String, List<DataDic>> tmp = new TreeMap<String, List<DataDic>>();
			
			for (DataDic d : dataDicService.getAllDataDic()) {
				String dataTp = StringUtil.trim(d.getDataTp());
				if (StringUtil.isBlank(dataTp)) {
					logger.info("该条数据字典信息不符合规则:" + BeanUtils.describe(d));
					continue;
				}
				List<DataDic> lst = tmp.get(dataTp);
				if (lst == null) {
					lst = new ArrayList<DataDic>();
					tmp.put(dataTp, lst);
				}
				lst.add(d);
			}
			cache = tmp;
			logger.info("初始化数据字典信息缓存完成，数据字典信息条数:" + cache.size());
		} catch (Exception e) {
			logger.error("初始化数据字典信息缓存失败", e);
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getDataMap(String dataTp) {
		if (null == cache)
			return null;
		if (cache.containsKey(dataTp)) {
			Map<String, String> map = new TreeMap<String, String>();
			for (DataDic d : cache.get(dataTp))  {
				map.put(d.getDataKey(), d.getDataVal());
			}
			return map;
		} else {
			return Collections.EMPTY_MAP;
		}
	}
	
	/**
	 * 根据数据字典类型和KEY获取数据字典值
	 * @param dataTp
	 * @param dataKey
	 * @return
	 */
	public String getDataValue(String dataTp, String dataKey) {
		AssertUtil.argIsBlank(dataTp, "数据字典类型为空"+dataTp);
		String value = dataKey;
		List<DataDic> lst = cache.get(dataTp);
		if (lst != null) {
			for (DataDic d : lst) {
				if (d.getDataTp().equals(dataTp) && d.getDataKey().equals(dataKey)) {
					value = d.getDataVal();
					break;
				}
			}
		}
		return value;
	}
	
	@Override
	public void clear() {
		logger.info("清理数据字典信息缓存开始");
		if (cache != null) {
			cache.clear();
			cache = null;
		}
		logger.info("清理数据字典信息缓存完成");
	}

	@Override
	public void refresh() {
		logger.info("刷新数据字典信息缓存开始");
		this.init();
		logger.info("刷新数据字典信息缓存完成");
	}

}
