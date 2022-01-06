package com.icpay.payment.service.listener;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.springframework.web.context.ContextLoader;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.mer.cache.CacheManager;
import com.icpay.payment.mer.cache.MerConfigCache;
import com.icpay.payment.mer.constants.MerConstants;
import com.icpay.payment.mer.session.LocalSessionManager;
import com.icpay.payment.risk.RiskEventPublisherManager;
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
		String configDir = System.getProperty(MerConstants.CONFIG_PATH_PARAM);
		if (!StringUtil.isBlank(configDir)) {
			logger.info("系统配置文件路径--->" + configDir);
			MerConstants.CONFIG_PATH = configDir;
		}

		// 初始化配置文件信息缓存
		MerConfigCache.getInstance().init();
		// 初始化DB服务客户端
		DBHessionServiceClient.getInstance().init(
				MerConfigCache.getConfig("db_service_urls"),
				MerConfigCache.getConfig("db_service_package"));

		// 初始化缓存
		CacheManager.initCaches();

		// 初始化Session管理器
		int maxSessions = intVal(MerConfigCache.getConfig("maxSessions"), 6400);
		int timeout = intVal(MerConfigCache.getConfig("sessionTimeout"), 15 * 60 * 1000);
		int validCodeTimeout = intVal(MerConfigCache.getConfig("validCodeTimeout"), 2 * 60 * 1000);
		//LocalSessionManager.init(6400, 30 * 10 * 1000);
		LocalSessionManager.init(maxSessions, timeout, validCodeTimeout);

		// 启动Session管理器定时监控-5分钟执行一次
		Timer monitorTimer = new Timer();
		monitorTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				LocalSessionManager.monitor();
			}
		}, 37 * 1000, 5 * 60 * 1000);

		// 启动Session定时清理任务-5分钟执行一次
		Timer cleanUpTimer = new Timer();
		cleanUpTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				LocalSessionManager.cleanUpTimeout();
			}
		}, 53 * 1000, 5 * 60 * 1000);

		OnsMqManager.start();
		RiskEventPublisherManager.start();

		logger.info("----系统初始化完成----");
	}

	protected int intVal(String val, int defVal) {
		if (val==null) return defVal;
		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
			return defVal;
		}
	}

	public void shutdown() {
		CacheManager.stop();
		RiskEventPublisherManager.shutdown();
		LogManager.shutdown();
		OnsMqManager.shutdown();
	}

}
