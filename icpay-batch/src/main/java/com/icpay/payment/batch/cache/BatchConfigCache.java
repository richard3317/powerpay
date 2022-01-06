package com.icpay.payment.batch.cache;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.icpay.payment.batch.constants.BatchConstants;
import com.icpay.payment.common.utils.PropertyUtil;
import com.icpay.payment.common.utils.StringUtil;

public final class BatchConfigCache implements ICache {
	
	private static final Logger logger = Logger.getLogger(BatchConfigCache.class);
	
	private static BatchConfigCache instance = new BatchConfigCache();
	
	private Properties props;
	
	private BatchConfigCache() { }
	
	public static BatchConfigCache getInstance() {
		return instance;
	}
	
	public Properties getProps() {
		if (props==null) {
			this.load();
		}
		return props;
	}

	public static String getConfig(String configKey){
		return getInstance().getProps().getProperty(configKey);
	}

	public static String getConfig(String configKey, String defaultVal){
		return getInstance().getProps().getProperty(configKey, defaultVal);
	}

	@Override
	public synchronized void init() {
		if (props == null) {
			this.load();
		}
	}
	
	@Override
	public void refresh() {
		this.load();
	}
	
	@Override
	public void clear() {
		
	}
	
	private void load() {
		logger.info("-初始化系统配置文件缓存开始-");
		Properties propsTemp = new Properties();
		if (!StringUtil.isBlank(BatchConstants.CONFIG_PATH)) {
			String configFileFullPath = BatchConstants.CONFIG_PATH + "batch.properties";
			logger.info("从指定的路径载入配置文件-->" +  configFileFullPath);
			
			InputStream in = null;
			try {
				// 属性文件输入流
				in = new FileInputStream(configFileFullPath);
				// 加载属性文件
				propsTemp.load(in);
			} catch (Exception ex) {
				logger.error("打开文件流失败，文件名："+configFileFullPath, ex);
				return;
			}finally {
				try {
					if (null != in) {
						in.close();
					}
				} catch (Exception e) {
					logger.error("关闭文件流失败，文件名："+configFileFullPath, e);
				}
			}
		} else {
			logger.info("从项目类路径载入配置文件");
			propsTemp = PropertyUtil.getProperties("batch.properties", this);
		}
		
		synchronized (this) {
			props=propsTemp;
		}
		
		StringBuffer buffer = new StringBuffer("\r\nBatchConfig{\r\n");
		for(Object key : props.keySet()){
			buffer.append("\t"+ key + "=" );
			buffer.append(props.getProperty((String)key));
			buffer.append("\r\n");
		} 
		buffer.append("}");
		logger.info("配置文件内容: " +buffer.toString());
		
		logger.info("-初始化系统配置文件缓存完成-");
	}
}
