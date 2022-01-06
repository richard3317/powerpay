package com.icpay.payment.bm.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.CashPoolInfo;
import com.icpay.payment.db.service.ICashPoolInfoService;

public class CashPoolInfoCache extends CommonCacheBase{
	
	private static final CashPoolInfoCache INSTANCE = new CashPoolInfoCache();
	
	public static CashPoolInfoCache getInstance() {
		return INSTANCE;
	}

	protected CashPoolInfoCache() {
		super();
		this.setLazyMode(false);
	}
	
	public static String getPoolName(String poolId) {
		Map<String,String> pool = getInstance().get(poolId);
		if (!Utils.isEmpty(pool)) {
			return pool.get("poolDesc");
		}
		return null;
	}
	
	public List<Map<String,String>> getAll(){
		String keyPattern = ".*";
		return this.getByKeyPattent(keyPattern);
	}	
	
	@Override
	public boolean isLazyMode() {
		return false;
	}

	/**
	 * key: 渠道编号, 渠道商户号
	 */
	@Override
	protected String toMapKey(String... pkeys) {
		return strVal(0, pkeys);
	}

	public static final String[] IgnoredFields=
			(
			"comment;recCrtTs;recUpdTs;lastOperId"
			).split(";");
	@Override
	protected Map<String, Map<String, String>> reloadAll() throws Exception {
		ICashPoolInfoService svc = DBHessionServiceClient.getService(ICashPoolInfoService.class);
		List<CashPoolInfo> recs = svc.getCashPoolInfo();
		
		Map<String, Map<String, String>> map = new HashMap<>();
		for(CashPoolInfo r: recs) {
			Map<String, String> mrec= BeanUtil.describe(r, IgnoredFields, null);
			if (mrec!=null) {
				map.put(toMapKey(r.getPoolId()), mrec);
			}
		}
		return map;
	}

	/**
	 * 获取远端记录，key: 
	 */
	@Override
	protected Map<String, String> remoteInfo(String... pkeys) {
		return null;
	}

}
