package com.icpay.payment.service.listener;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.icpay.payment.proxy.Service;
import com.icpay.payment.service.api.OnlTxnChnlMgr;
import com.icpay.payment.service.cache.CacheManager;
import com.icpay.payment.service.cache.SystemParamCache;
import com.icpay.payment.service.cache.TerminalInfoCache;
import com.icpay.payment.service.channel.ChannelMgr;
import com.icpay.payment.service.channel.tvpay.TVPayChannel;
import com.icpay.payment.service.channel.ums.UMSChannel;
import com.icpay.payment.service.mq.MqManager;
import com.icpay.payment.service.mq.OnsMqManager;
import com.icpay.payment.service.spring.BeanUtil;
import com.icpay.payment.service.cache.CodeMsgsCache;
import com.icpay.payment.service.cache.MchntInfoCache;
import com.icpay.payment.service.cache.MerParamCache;


public class OnStartListener implements ServletContextListener {
	private static final Logger log = Logger.getLogger(OnStartListener.class);
	private ContextLoader contextLoader;

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {

		try {
			initSpring(event);
			CacheManager.addCache(SystemParamCache.class);
			CacheManager.addCache(MerParamCache.class);
			CacheManager.addCache(CodeMsgsCache.class);

			OnsMqManager.start();
			CacheManager.start();
			//Service.publish();
		} catch (Exception e) {
			log.error("Start up catch exception.", e);
		}

	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {

		try {
			//Service.destory();
			CacheManager.stop();
		    LogManager.shutdown();
		    OnsMqManager.shutdown();
			destroySpring(event);
		} catch (Exception e) {
			log.error("Destroy catch exception.", e);
		}
	}

	public void initSpring(ServletContextEvent event){
		ServletContext context = event.getServletContext();
		contextLoader = new ContextLoader();
		contextLoader.initWebApplicationContext(context);
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		BeanUtil.setContext(ctx);
	}

	public void destroySpring(ServletContextEvent event){
		if (this.contextLoader != null) {
			this.contextLoader.closeWebApplicationContext(event.getServletContext());
		}
	}

}
