package com.icpay.payment.batch.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.icpay.payment.batch.cache.BatchConfigCache;
import com.icpay.payment.batch.cache.CacheManager;
import com.icpay.payment.batch.constants.BatchConstants;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.proxy.Service;

public class OnStartUpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(OnStartUpServlet.class);
	
	@Override
	public void init() throws ServletException {
		try {
			super.init();
			
			logger.info("----系统初始化开始----");

			// 如果系统启动时指定了配置文件的路径，
			// 则将该路径信息保存在常量类的SYS_CONFIG_PATH_PARAM字段中
			String configDir = System.getProperty(BatchConstants.CONFIG_PATH_PARAM);
			if (!StringUtil.isBlank(configDir)) {
				logger.info("系统配置文件路径--->" + configDir);
				BatchConstants.CONFIG_PATH = configDir;
			}
			
			// 初始化配置文件信息缓存
			BatchConfigCache.getInstance().init();
			// 初始化DB服务客户端
			DBHessionServiceClient.getInstance().init(
					BatchConfigCache.getConfig("db_service_urls"), 
					BatchConfigCache.getConfig("db_service_package"));
			
			// 初始化缓存
			CacheManager.initCaches();
			Service.publish();
			logger.info("----系统初始化完成----");
		} catch (Exception e) {
			logger.error("Start up catch exception.", e);
		}
		
		
	}
}
