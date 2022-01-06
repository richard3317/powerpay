package com.icpay.payment.bm.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.OrganInfo;
import com.icpay.payment.db.service.IOrganMchntInfoService;

public class OrganInfoCache extends CommonCacheBase{
	
	private static final OrganInfoCache INSTANCE = new OrganInfoCache();
	
	public static OrganInfoCache getInstance() {
		return INSTANCE;
	}
	
	private List<OrganInfo> list = null;
	
	public List<OrganInfo>  getOrganInfoList() {
		if (INSTANCE.list == null) {
			synchronized (INSTANCE) {
				if (INSTANCE.list == null) {
					INSTANCE.init();
				}
			}
		}
		return INSTANCE.list;
	}

	protected OrganInfoCache() {
		super();
		this.setLazyMode(false);
	}
	
	
	public static String getByOrganId(String organId) {
		Map<String,String> organ = getInstance().get(organId);
		if (!Utils.isEmpty(organ)) {
			return organ.get("organDesc");
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
		return String.format("%s-%s", strVal(0, pkeys), strVal(1, pkeys));
	}

	public static final String[] IgnoredFields=
			"comment;recCrtTs;recUpdTs;lastOperId".split(";");
	@Override
	protected Map<String, Map<String, String>> reloadAll() throws Exception {
		IOrganMchntInfoService svc = DBHessionServiceClient.getService(IOrganMchntInfoService.class);
		List<OrganInfo> recs = svc.select();
		Map<String, Map<String, String>> map = new HashMap<>();
		for(OrganInfo r: recs) {
			Map<String, String> mrec= BeanUtil.describe(r, IgnoredFields, null);
			if (mrec!=null && "1".equals(mrec.get("state"))) {
				map.put(toMapKey(r.getOrganId()), mrec);
				synchronized (INSTANCE) {
					INSTANCE.list = recs;
				}
			}
		}
		return map;
	}

	/**
	 * 获取远端记录，key: 渠道编号, 渠道商户号
	 */
	@Override
	protected Map<String, String> remoteInfo(String... pkeys) {
		return null;
	}

}
