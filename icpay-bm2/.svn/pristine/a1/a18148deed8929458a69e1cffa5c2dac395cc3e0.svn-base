package com.icpay.payment.bm.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.RiskListGroup;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.IRiskListGroupService;

public class RiskGroupInfoCache extends CommonCacheBase{
	
	private static final RiskGroupInfoCache INSTANCE = new RiskGroupInfoCache();
	
	public static RiskGroupInfoCache getInstance() {
		return INSTANCE;
	}

	protected RiskGroupInfoCache() {
		super();
		this.setLazyMode(false);
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
			("recCrtTs;recUpdTs;lastOperId"
			).split(";");
	@Override
	protected Map<String, Map<String, String>> reloadAll() throws Exception {
		IRiskListGroupService svc = DBHessionServiceClient.getService(IRiskListGroupService.class);
		List<RiskListGroup> recs = svc.select(new HashMap<String, String>());
		
		Map<String, Map<String, String>> map = new HashMap<>();
		for(RiskListGroup r: recs) {
			Map<String, String> mrec= BeanUtil.describe(r, IgnoredFields, null);
			if (mrec!=null) {
				map.put(toMapKey(strVal(r.getGroupId())), mrec);
			}
		}
		return map;
	}
	
	public List<Map<String,String>> list(boolean mass, String... excludes){
		List<Map<String,String>> list = new ArrayList<>();
		Map<String, Map<String,String>> curCache=this.getCache();
		if (curCache==null) return list;
		
		for(String key: curCache.keySet().toArray(new String[0])) {
			Map<String,String> rec=curCache.get(key);
			String groupId=rec.get("groupId");
			if ("1".equals(rec.get("status")) && (!Utils.isInSet(groupId, excludes))) {
				if (mass) {
					if (intVal(rec.get("groupId"))>=900)
						list.add(rec);
				}
				else {
					if (intVal(rec.get("groupId"))<900)
						list.add(rec);
				}
			}
		}
		return list;
	}
	
	protected int intVal(Object obj) {
		if (obj==null) return 0;
		try {
			return Integer.parseInt(obj.toString());
		} catch (Exception e) {
		}
		return 0;
	}


	/**
	 * 获取远端记录，key: 渠道编号, 渠道商户号
	 */
	@Override
	protected Map<String, String> remoteInfo(String... pkeys) {
		return null;
	}

}
