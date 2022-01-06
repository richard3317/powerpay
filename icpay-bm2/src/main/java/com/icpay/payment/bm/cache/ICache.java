package com.icpay.payment.bm.cache;

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

	/**
	 * 回傳是否需要刷新
	 * @return
	 */
	boolean isNeedRefresh();

	/**
	 * 標記已刷新
	 */
	void refreshed();

	/**
	 * 刷新請求
	 */
	void needRefresh();
	
}
