package com.icpay.payment.mer.session.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.icpay.payment.mer.session.LocalSessionManager;

public class LocalSessionManagerTest {

	@Test
	public void testSessionManager() {
		LocalSessionManager.init(50000, 10 * 60 * 1000, 1* 60 * 1000);
		ExecutorService es = Executors.newFixedThreadPool(100);
		for (int j = 0; j < 1000; j ++) {
			es.submit(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 10000; i ++) {
						try {
							LocalSessionManager.newSession(null);
							Thread.sleep((i % 3) * 1000);
							LocalSessionManager.monitor();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
		try {
			Thread.sleep(60*1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
