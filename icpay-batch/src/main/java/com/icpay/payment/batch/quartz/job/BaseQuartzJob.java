package com.icpay.payment.batch.quartz.job;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import com.icpay.payment.batch.cache.BatchConfigCache;
import com.icpay.payment.batch.constants.BatchConstants;

public abstract class BaseQuartzJob {

	private static final Logger logger = Logger.getLogger(BaseQuartzJob.class);
	
	private AtomicBoolean isRunning = new AtomicBoolean(false);
	
	private static String fuzzyDay_cutTime_D0 = null;
	private static String fuzzyDay_cutTime_T1 = null;
	
//	static {
//		FuzzyDay_CutTime = BatchConfigCache.getConfig(BatchConstants.FUZZY_DAY_CUTTIME);
//	}

	protected static String getFuzzyDayCutTimeD0() {
		if (fuzzyDay_cutTime_D0==null) {
			fuzzyDay_cutTime_D0 = BatchConfigCache.getConfig(BatchConstants.FUZZY_DAY_CUTTIME_D0,"210000");
		}
		return fuzzyDay_cutTime_D0;
	}
	
	protected static String getFuzzyDayCutTimeT1() {
		if (fuzzyDay_cutTime_T1==null) {
			fuzzyDay_cutTime_T1 = BatchConfigCache.getConfig(BatchConstants.FUZZY_DAY_CUTTIME_T1,"230000");
		}
		return fuzzyDay_cutTime_T1;
	}
	
	/**
	 * 单线程执行方法
	 */
	public void singleThreadExec() {
		if (isRunning.compareAndSet(false, true)) {
			try {
				logger.info("###定时任务开始:" + this.getJobDesc() + "###");
				doProcess();
				logger.info("###定时任务结束:" + this.getJobDesc() + "###");
			} catch (Exception e) {
				logger.error("###定时任务任务执行失败:" + this.getJobDesc() + "###", e);
			}
			isRunning.set(false);
		} else {
			logger.info("one thread is running...");
		}
	}
	
	/**
	 * 多线程执行方法
	 */
	public void multiThreadExec() {
		try {
			logger.info("###任务开始###:" + this.getJobDesc());
			doProcess();
			logger.info("###任务结束###:" + this.getJobDesc());
		} catch (Exception e) {
			logger.error("###任务执行出错###:" + this.getJobDesc(), e);
		}
	}
	
	/**
	 * 各定时任务子类自行实现
	 */
	protected abstract void doProcess();
	
	/**
	 * 任务描述
	 */
	protected abstract String getJobDesc();
}
