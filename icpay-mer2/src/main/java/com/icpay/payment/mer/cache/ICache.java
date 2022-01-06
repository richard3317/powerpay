package com.icpay.payment.mer.cache;

public interface ICache {
	/**
	 * 初始化缓存
	 */
	public void init();

	/**
	 * 刷新缓存
	 */
	public void refresh();
	
	/**
	 * 清空缓存
	 */
	public void clear();
	
}
