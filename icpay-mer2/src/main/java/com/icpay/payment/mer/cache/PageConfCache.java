package com.icpay.payment.mer.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.icpay.payment.common.entity.ColumnInfo;
import com.icpay.payment.common.entity.DetailConfInfo;
import com.icpay.payment.common.utils.FileLineHandler;
import com.icpay.payment.common.utils.FileUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.mer.util.I18nMsgUtils;

/**
 * 页面配置信息缓存
 *
 */
public class PageConfCache implements ICache {
	
private static final Logger logger = Logger.getLogger(PageConfCache.class);
	
	private static final PageConfCache INSTANCE = new PageConfCache();
	
	private Map<String, List<ColumnInfo>> columnConfCache = null;
	private Map<String, List<DetailConfInfo>> detailConfCache = null;
	private Map<String, Set<String>> containedFieldsCache = null;
	
	private PageConfCache() { }
	
	public static PageConfCache getInstance() {
		return INSTANCE;
	}
	
	public static List<ColumnInfo> getColumnLst(String type, String lang) {
		List<ColumnInfo> dataList = Collections.emptyList();
		List<ColumnInfo> result = new LinkedList<ColumnInfo>();
		if (INSTANCE.columnConfCache != null && !StringUtil.isBlank(type)) {
			dataList = INSTANCE.columnConfCache.get(type);
			if(dataList.size() != 0 ) {
				for(ColumnInfo data :dataList) {
					
					ColumnInfo enitiy = new ColumnInfo();
					result.add(enitiy);
					enitiy.setAlign(data.getAlign());
					enitiy.setField(data.getField());
					enitiy.setWidth(data.getWidth());
					enitiy.setTitle(I18nMsgUtils.getMessageWithDefault(
							I18nMsgUtils.ICPAY_MER, 
							lang,
							I18nMsgUtils.getKeyCombine( type,data.getTitle()),data.getTitle()));
				}
			}
		}
		return result;
	}
	
	public static List<DetailConfInfo> getDetailConfLst(String type, String lang) {
		List<DetailConfInfo> data = Collections.emptyList();
		List<DetailConfInfo> result = new LinkedList<DetailConfInfo>();
		if (INSTANCE.detailConfCache != null && !StringUtil.isBlank(type)) {
			data = INSTANCE.detailConfCache.get(type);
			for(DetailConfInfo column:data) {
				DetailConfInfo entity = new DetailConfInfo();
				String value = column.getLabel();
				String tranValue = I18nMsgUtils.getMessageWithDefault(
						I18nMsgUtils.ICPAY_MER, 
						lang,
						I18nMsgUtils.getKeyCombine(type, value),value);
				System.out.print(value+"-"+lang+"-"+tranValue);
				entity.setLabel(tranValue);
				entity.setField(column.getField());
				result.add(entity);
			}
		}
		return result;
	}
	
	public static Set<String> getContainedFields(String type) {
		if (INSTANCE.containedFieldsCache != null && !StringUtil.isBlank(type)) {
			return INSTANCE.containedFieldsCache.get(type);
		} else {
			return Collections.emptySet();
		}
	}

	@Override
	public synchronized void init() {
		logger.info("初始化查询结果列信息缓存信息开始");
		
		try {
			String filePath = PageConfCache.class.getClassLoader().getResource("pageConfig.conf").getFile();
			final Map<String, List<ColumnInfo>> columnConfCacheTemp = new HashMap<String, List<ColumnInfo>>();
			final Map<String, List<DetailConfInfo>> detailConfCacheTemp = new HashMap<String, List<DetailConfInfo>>();
			final Map<String, Set<String>> containedFieldsCacheTemp = new HashMap<String, Set<String>>();
			// 逐行处理配置文件
			FileUtil.readFileByLine(filePath, "UTF-8", new FileLineHandler() {
				@Override
				public void handleLine(String line) {
					if (StringUtil.isBlank(line)) {
						//logger.info("空行跳过..");
						return;
					}
					String[] config = line.split("[|]");
					if (config.length != 5) {
						logger.error("配置不符合规则:" + line);
					}
					try {
						Set<String> st = containedFieldsCacheTemp.get(config[0]);
						if (st == null) {
							st = new HashSet<String>();
							containedFieldsCacheTemp.put(config[0], st);
						}
						st.add(config[1]);
						
						String showConf = config[4];
						if (showConf.charAt(0) == '1') {
							ColumnInfo c = new ColumnInfo();
							c.setField(config[1]);
							c.setTitle(config[2]);
							c.setWidth(Integer.parseInt(config[3]));
							List<ColumnInfo> cLst = columnConfCacheTemp.get(config[0]);
							if (cLst == null) {
								cLst = new ArrayList<ColumnInfo>();
								columnConfCacheTemp.put(config[0], cLst);
							}
							cLst.add(c);
						}
						
						if (showConf.charAt(1) == '1') {
							DetailConfInfo d = new DetailConfInfo();
							d.setField(config[1]);
							d.setLabel(config[2]);
							List<DetailConfInfo> dLst = detailConfCacheTemp.get(config[0]);
							if (dLst == null) {
								dLst = new ArrayList<DetailConfInfo>();
								detailConfCacheTemp.put(config[0], dLst);
							}
							dLst.add(d);
						}
					} catch (Exception e) {
						logger.error("解析配置信息错误:" + line+" 错误原因为"+e.getLocalizedMessage());
					}
				}
			});
			columnConfCache = columnConfCacheTemp;
			detailConfCache = detailConfCacheTemp;
			containedFieldsCache = containedFieldsCacheTemp;
		} catch (Exception e) {
			logger.error("初始化页面配置信息缓存失败", e);
			throw new RuntimeException(e);
		}
		logger.info("初始化页面配置信息缓存完成");
	}

	@Override
	public void refresh() {
		logger.info("刷新页面配置信息缓存开始");
		this.init();
		logger.info("刷新页面配置信息缓存完成");
	}
	
	@Override
	public void clear() {
		logger.info("清空页面配置信息缓存开始");
		if (columnConfCache != null) {
			columnConfCache.clear();
			columnConfCache = null;
		}
		if (detailConfCache != null) {
			detailConfCache.clear();
			detailConfCache = null;
		}
		logger.info("清空页面配置信息缓存完成");
	}
}
