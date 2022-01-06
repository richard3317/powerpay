package com.icpay.payment.bm.cache;
import com.icpay.payment.bm.cache.ICache;

public abstract class CacheBase implements ICache {


	private int cntNeedRefresh = 0;

	@Override
	public boolean isNeedRefresh() {
		return cntNeedRefresh>0;
	}

	@Override
	public void needRefresh() {
		this.cntNeedRefresh++;
	}
	
	@Override
	public void refreshed() {
		cntNeedRefresh = 0;
	}
	
}
