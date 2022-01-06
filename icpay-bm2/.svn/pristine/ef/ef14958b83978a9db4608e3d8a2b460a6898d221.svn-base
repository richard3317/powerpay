package com.icpay.payment.bm.quartz.job;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.icpay.payment.bm.bo.OperLogBO;

public abstract class BaseQuartzJob {

	private static final Logger logger = Logger.getLogger(BaseQuartzJob.class);
	
	@Autowired
	protected OperLogBO operLogBO;
	
	private AtomicBoolean isRunning = new AtomicBoolean(false);
	
	/**
	 * 单线程执行方法
	 */
	public void singleThreadExec() {
		if (isRunning.compareAndSet(false, true)) {
			try {
				logger.info("###任务开始###:" + this.getJobDesc());
				doProcess();
				logger.info("###任务结束###:" + this.getJobDesc());
			} catch (Exception e) {
				logger.error("###任务执行出错###:" + this.getJobDesc(), e);
			}
			isRunning.set(false);
		} else {
			logger.info("one thread is running the doProcess method...");
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
