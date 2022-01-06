package com.icpay.payment.bm.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.icpay.payment.common.entity.ColumnInfo;
import com.icpay.payment.common.entity.DetailConfInfo;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.PageConfig;
import com.icpay.payment.db.service.IPageConfigService;

/**
 * 页面配置信息缓存
 * @author lijin
 *
 */
public class PageConfCache extends CacheBase implements ICache {
	
	private static final Logger logger = Logger.getLogger(PageConfCache.class);
	
	private static final PageConfCache INSTANCE = new PageConfCache();
	
	private Map<String, List<ColumnInfo>> dataGridColumsCache = null;
	private Map<String, List<DetailConfInfo>> detailPageConfCache = null;
	private Map<String, Set<String>> containedFieldsCache = null;
	
	private PageConfCache() {}
	
	public static PageConfCache getInstance() {
		return INSTANCE;
	}
	
	public static List<ColumnInfo> getColumnLst(String type) {
		if (INSTANCE.dataGridColumsCache != null && !StringUtil.isBlank(type)) {
			return INSTANCE.dataGridColumsCache.get(type);
		} else {
			return Collections.emptyList();
		}
	}
	
	public static List<DetailConfInfo> getDetailConf(String type) {
		if (INSTANCE.detailPageConfCache != null && !StringUtil.isBlank(type)) {
			return INSTANCE.detailPageConfCache.get(type);
		} else {
			return Collections.emptyList();
		}
	}
	
	public static Set<String> getContainedFields(String type) {
		if (INSTANCE.containedFieldsCache != null && !StringUtil.isBlank(type)) {
			return INSTANCE.containedFieldsCache.get(type);
		} else {
			return Collections.emptySet();
		}
	}

	@Override
	public void clear() {
		if (dataGridColumsCache != null) {
			dataGridColumsCache.clear();
			dataGridColumsCache = null;
		}
		if (detailPageConfCache != null) {
			detailPageConfCache.clear();
			detailPageConfCache = null;
		}
	}

	@Override
	public void init() {
		try {
			logger.info("##初始化页面配置信息开始##");
			IPageConfigService service = DBHessionServiceClient.getService(IPageConfigService.class);
			List<PageConfig> lst = service.selectAll();
			Map<String, List<ColumnInfo>> dataGridColumConf = new HashMap<String, List<ColumnInfo>>();
			Map<String, List<DetailConfInfo>> detailPageConf = new HashMap<String, List<DetailConfInfo>>();
			Map<String, Set<String>> containedFields = new HashMap<String, Set<String>>();
			for (PageConfig conf : lst) {
				String entityNm = conf.getEntityNm();
				
				Set<String> st = containedFields.get(entityNm);
				if (st == null) {
					st = new HashSet<String>();
					containedFields.put(entityNm, st);
				}
				st.add(conf.getField());
				
				String displayConfig = conf.getDisplayConfig();
				if (StringUtil.isBlank(displayConfig)) {
					logger.error("displayConfig is blank:" + entityNm + "-" + conf.getField());
					continue;
				}
				char[] cs = displayConfig.toCharArray();
				if (cs.length < 2) {
					logger.error("displayConfig not corrent: " + entityNm + "-" + conf.getField());
					continue;
				}
				// 查询结果列表显示配置信息
				if (cs[0] == '1') {
					ColumnInfo col = new ColumnInfo();
					col.setField(conf.getField());
					col.setTitle(conf.getFieldNm());
					col.setWidth(conf.getDisplayWidth());
					if (!Utils.isEmpty(conf.getAlign()))
						col.setAlign(conf.getAlign());
					List<ColumnInfo> colConfLst = dataGridColumConf.get(entityNm);
					if (colConfLst == null) {
						colConfLst = new ArrayList<ColumnInfo>();
						dataGridColumConf.put(entityNm, colConfLst);
					}
					colConfLst.add(col);
				}
				
				// 详情页面配置信息
				if (cs[1] == '1') {
					DetailConfInfo detailConf = new DetailConfInfo();
					detailConf.setField(conf.getField());
					detailConf.setLabel(conf.getFieldNm());
					List<DetailConfInfo> detailConfLst = detailPageConf.get(entityNm);
					if (detailConfLst == null) {
						detailConfLst = new ArrayList<DetailConfInfo>();
						detailPageConf.put(entityNm, detailConfLst);
					}
					detailConfLst.add(detailConf);
				}
			}
			dataGridColumsCache = dataGridColumConf;
			detailPageConfCache = detailPageConf;
			containedFieldsCache = containedFields;
			logger.info("##初始化页面配置信息完成##");
		} catch (Exception e) {
			logger.error("##初始化页面配置信息失败##", e);
			throw new BizzException("初始化页面配置信息失败", e);
		}
	}

	@Override
	public void refresh() {
		this.init();
	}
}
