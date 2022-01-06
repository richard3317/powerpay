package com.icpay.payment.service.listener;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.service.mq.OnsMqManager;


public class OnStartListener implements ServletContextListener {
	private static final Logger log = Logger.getLogger(OnStartListener.class);
	private ContextLoader contextLoader;

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		try {
			//initSpring(event);
			//test();
			OnsMqManager.start();
			//Service.publish();
		} catch (Exception e) {
			log.error("Service publish catch exception : "+e.getMessage(), e);
		}

	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {

		try {
			//Service.destory();
			LogManager.shutdown();
			OnsMqManager.shutdown();
			//destroySpring(event);
		} catch (Exception e) {
			LogLog.error("Destroy catch exception : "+e.getMessage(), e);
		}
	}

	public void initSpring(ServletContextEvent event){
		ServletContext context = event.getServletContext();
		contextLoader = new ContextLoader();
		contextLoader.initWebApplicationContext(context);
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		ApplicationContextUtil.initContext(ctx);
	}

	public void destroySpring(ServletContextEvent event){
		ApplicationContextUtil.closeContext();
		if (this.contextLoader != null) {
			this.contextLoader.closeWebApplicationContext(event.getServletContext());
		}
	}



	protected void test() {
		try {
			com.mchange.v2.c3p0.ComboPooledDataSource ds =ApplicationContextUtil.getBean("jdbcDatasource");
			System.out.println("***** TEST ***** C3P0 user: "+ds.getUser());
			log.debug("***** TEST ***** C3P0 user: "+ds.getUser());
		} catch (Exception e) {
			log.error("Spring beans test error!", e);
			e.printStackTrace();
		}
	}

}
