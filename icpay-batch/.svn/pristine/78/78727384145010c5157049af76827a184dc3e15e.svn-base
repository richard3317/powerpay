package com.icpay.payment.batch.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;

public abstract class BaseTest {

	protected ApplicationContext ctx = new ClassPathXmlApplicationContext("app-ctx.xml");
	
	public BaseTest() {
		DBHessionServiceClient.getInstance().init(
				"http://localhost:9898/icpay-db-service"
				, "com.icpay.payment.db.service");
	}

	protected <T> T getBean(String beanNm) {
		return ApplicationContextUtil.getBean(beanNm);
	}
	
	protected void print(String msg) {
		System.out.print(msg);
	}
	
	protected void println(String msg) {
		System.out.println(msg);
	}
	
	protected void print(String fmt, Object... args) {
		print(String.format(fmt, args));
	}
	
	protected void println(String fmt, Object... args) {
		println(String.format(fmt, args));
	}
	
	
	
	
}
