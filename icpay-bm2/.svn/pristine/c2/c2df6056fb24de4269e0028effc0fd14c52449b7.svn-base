package com.icpay.payment.bm.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfo;
import com.icpay.payment.db.service.IChnlMchntInfoService;

public class ChnlMchntInfoCache extends CommonCacheBase{
	
	private static final ChnlMchntInfoCache INSTANCE = new ChnlMchntInfoCache();
	
	public static ChnlMchntInfoCache getInstance() {
		return INSTANCE;
	}
	private boolean filterEffect = false;
	private List<ChnlMchntInfo> list = null;
	
	public List<ChnlMchntInfo>  getChnlMchntInfoList() {
		if (INSTANCE.list == null) {
			synchronized (INSTANCE) {
				if (INSTANCE.list == null) {
					INSTANCE.init();
				}
			}
		}
		return INSTANCE.list;
	}

	protected ChnlMchntInfoCache() {
		super();
		this.setLazyMode(false);
	}
	
	public List<Map<String,String>> getByChnlId(String chnlId){
		String keyPattern = chnlId+".*";
		//System.out.println(keyPattern);
		return this.getByKeyPattent(keyPattern);
	}
	
	public void setFilterEffect(boolean value) {
		this.filterEffect = value;
	}
	
	public boolean GetFilterFffect() {
		return this.filterEffect;
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
		IChnlMchntInfoService svc = DBHessionServiceClient.getService(IChnlMchntInfoService.class);
		List<ChnlMchntInfo> recs = svc.getAllChnlInfo();
		Map<String, Map<String, String>> map = new HashMap<>();
		for(ChnlMchntInfo r: recs) {
			Map<String, String> mrec= BeanUtil.describe(r, IgnoredFields, null);
			if (mrec!=null) {
				if(filterEffect && "0".equals(mrec.get("state"))) {
					continue;
				}
				map.put(toMapKey(r.getChnlId(), r.getChnlMchntCd()), mrec);
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
