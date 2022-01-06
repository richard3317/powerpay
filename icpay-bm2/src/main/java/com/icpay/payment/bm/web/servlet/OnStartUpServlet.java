package com.icpay.payment.bm.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.cache.CacheManager;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.service.mq.OnsMqManager;

/**
 * 已经转移到 {@link com.icpay.payment.service.listener.OnStartListener}
 * @author robin
 *
 */
@Deprecated
public class OnStartUpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(OnStartUpServlet.class);

	@Override
	public void init() throws ServletException {

		super.init();

		logger.info("----系统初始化开始----");

//		Object context = ApplicationContextUtil.getContext();
//		logger.info("[context] "+ context);
//
//		Object applicationContextUtil = ApplicationContextUtil.getBean("applicationContextUtil");
//		logger.info("[applicationContextUtil] "+ applicationContextUtil);
//		context = ApplicationContextUtil.getContext();
//		logger.info("[context] "+ context);
//
//		Object commonDataAppContext = ApplicationContextUtil.getBean("commonDataAppContext");
//		logger.info("[commonDataAppContext] "+ commonDataAppContext);
//		context = ApplicationContextUtil.getContext();
//		logger.info("[context] "+ context);




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
		CacheManager.initCaches();
		OnsMqManager.start();

		logger.info("----系统初始化完成----");
	}
}
