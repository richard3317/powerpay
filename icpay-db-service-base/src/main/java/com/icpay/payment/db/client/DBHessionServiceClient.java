package com.icpay.payment.db.client;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianURLConnectionFactory;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.PropUtils;

public final class DBHessionServiceClient {
	
	private static final Logger logger = Logger.getLogger(DBHessionServiceClient.class);

	private static final DBHessionServiceClient INSTANCE = new DBHessionServiceClient();

	private final Map<String, Class<?>> serviceMap = new HashMap<String, Class<?>>();
	private final Map<String, Object> serviceCache = new HashMap<String, Object>();
	
	private String[] serviceUrls = null;
	private HessianProxyFactory factory = null;
	private HessianURLConnectionFactory connFactory = null;
	private int idx = 0;

	private DBHessionServiceClient() {
	}

	public static DBHessionServiceClient getInstance() {
		return INSTANCE;
	}

	public void init(String baseUrls, String servicePackage) {
		AssertUtil.strIsBlank(baseUrls, "DBService服务URL配置为空");
		AssertUtil.strIsBlank(servicePackage, "DBService服务接口路径配置为空");

		// 服务的根路径
		serviceUrls = baseUrls.split(",");
		
		logger.info("db url number:" + serviceUrls.length);
		for (String url : serviceUrls) {
			logger.info(url);
		}

		// 初始化工厂类
		factory = new HessianProxyFactory(); //解决Hessian调用重载方法报错问题
		factory.setOverloadEnabled(true);
		Properties props = null;
		try {
			props = PropUtils.loadResProperties("DBHessionService.properties");
			factory.setConnectTimeout(Integer.parseInt(PropUtils.getProperty(props, "connectTimeout", "10000")));
			factory.setReadTimeout(Integer.parseInt(PropUtils.getProperty(props, "readTimeout", "30000")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		connFactory = new DBServiceHessianConnectionFactory();
		factory.setConnectionFactory(connFactory);
		connFactory.setHessianProxyFactory(factory);
		
		logger.info(String.format("HessianProxyFactory: connectTimeout=%s", factory.getConnectTimeout()));
		logger.info(String.format("HessianProxyFactory: readTimeout=%s", factory.getReadTimeout()));

		try {
			// 注册服务：以接口类的类名为服务名
			String packageDirName = servicePackage.replace('.', '/');
			Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			while (urls.hasMoreElements()) {
				URL u = urls.nextElement();
				String protocol = u.getProtocol();
				if ("file".equals(protocol)) {
					String filePath = URLDecoder.decode(u.getFile(), "UTF-8");
					loadByPackage(servicePackage,filePath);
				} else if ("jar".equals(protocol)) {
					JarFile jar = ((JarURLConnection) u.openConnection()).getJarFile();
					loadByJar(jar, servicePackage);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizzException("注册服务失败", e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getService(Class<T> clazz) {
		String serviceName = clazz.getSimpleName();
		return (T) INSTANCE.createService(serviceName);
	}
	
	private Object createService(String serviceName) {
		AssertUtil.objIsNull(factory, "DBService客户端实例尚未初始化");
		if (!serviceMap.containsKey(serviceName)) {
			throw new BizzException("服务未注册:" + serviceName);
		}
		try {
			String url = this.chooseUrl() + serviceName;
			// 如果以前创建过这个服务代理，则使用已经创建的对象，否则新建一个，并放入缓存
			if (serviceCache.containsKey(url)) {
				if (logger.isDebugEnabled()) {
					logger.debug("use existed service: " + url);
				}
				return serviceCache.get(url);
			}
//			if (logger.isDebugEnabled()) {
//				logger.debug("choosed db service url:" + url);
//			}
			// 防止多线程情况，需做同步处理
			synchronized (this) {
				if (serviceCache.containsKey(url)) {
					return serviceCache.get(url);
				} else {
					Object svc = factory.create(serviceMap.get(serviceName), url);
					if (logger.isDebugEnabled()) {
						logger.debug("new service: " + url);
					}
					serviceCache.put(url, svc);
					return svc;
				}
			}
		} catch (Exception e) {
			throw new BizzException("获取服务失败:" + serviceName, e);
		}
	}

	private String chooseUrl() {
		synchronized (INSTANCE) {
			String url = serviceUrls[idx];
			idx = ((idx + 1) == serviceUrls.length) ? 0 : idx + 1;
			return url.endsWith("/") ? url : url + "/";
		}
	}

	private void loadByPackage(String packageName, String packagePath) {
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		File[] dirfiles = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.getName().endsWith(".class");
			}
		});
		for (File file : dirfiles) {
			if (!file.isDirectory()) {
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					serviceMap.put(className, Class.forName(packageName + "." + className));
				} catch (ClassNotFoundException e) {
					throw new BizzException("载入类信息失败", e);
				}
			}
		}
	}
	
	private void loadByJar(JarFile jar, String packageName) {
		Enumeration<JarEntry> entries = jar.entries();
		String packageDirName = packageName.replace('.', '/');
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			String name = entry.getName();
			if (name.charAt(0) == '/') {
				name = name.substring(1);
			}
			if (name.startsWith(packageDirName)) {
				int idx = name.lastIndexOf('/');
				if (idx > 0 && name.endsWith(".class")) {
					String className = name.substring(packageName.length() + 1, name.length() - 6);
					try {
						serviceMap.put(className, Class.forName(packageName + '.' + className));
					} catch (ClassNotFoundException e) {
						throw new BizzException("载入类信息失败", e);
					}
				}
			}
		}
	}
}
