package com.icpay.payment.service.listener;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.springframework.web.context.ContextLoader;

import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.cache.CacheManager;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.service.mq.OnsMqManager;


public class OnStartListener implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(OnStartListener.class);
	private ContextLoader contextLoader;

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		try {
			init();
		} catch (Exception e) {
			logger.error("Service publish catch exception : "+e.getMessage(), e);
		}

	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {

		try {
			shutdown();
		} catch (Exception e) {
			LogLog.error("Destroy catch exception : "+e.getMessage(), e);
		}
	}

	public void initSpring(ServletContextEvent event){
		ServletContext context = event.getServletContext();
		contextLoader = new ContextLoader();
		contextLoader.initWebApplicationContext(context);
	}

	public void destroySpring(ServletContextEvent event){
		if (this.contextLoader != null) {
			this.contextLoader.closeWebApplicationContext(event.getServletContext());
		}
	}

	public void init() throws ServletException {

		logger.info("----系统初始化开始----");

		// 如果系统启动时指定了配置文件的路径，
		// 则将该路径信息保存在常量类的SYS_CONFIG_PATH_PARAM字段中
		String configDir = System.getProperty(BMConstants.CONFIG_PATH_PARAM);
		if (!StringUtil.isBlank(configDir)) {
			logger.info("系统配置文件路径--->" + configDir);
			BMConstants.CONFIG_PATH = configDir;
		}

		// 初始化配置文件信息缓存
		BMConfigCache.getInstance().init();
		// 初始化DB服务客户端
		DBHessionServiceClient.getInstance().init(
				BMConfigCache.getConfig("db_service_urls"),
				BMConfigCache.getConfig("db_service_package"));

		// 初始化缓存
		OnsMqManager.start();
		CacheManager.initCaches();
		logger.info("----系统初始化完成----");
	}

	public void shutdown() {
		CacheManager.stop();
		LogManager.shutdown();
		OnsMqManager.shutdown();
	}

}
