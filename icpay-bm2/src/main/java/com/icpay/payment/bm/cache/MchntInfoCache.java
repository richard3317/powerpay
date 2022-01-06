package com.icpay.payment.bm.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.service.IMchntInfoService;

public class MchntInfoCache extends CommonCacheBase{
	
	private static final MchntInfoCache INSTANCE = new MchntInfoCache();
	
	public static MchntInfoCache getInstance() {
		return INSTANCE;
	}

	protected MchntInfoCache() {
		super();
		this.setLazyMode(false);
	}
	
	public static String getMchntName(String mchntCd) {
		Map<String,String> mchnt = getInstance().get(mchntCd);
		if (!Utils.isEmpty(mchnt)) {
			return mchnt.get("mchntCnNm");
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
			"insCd;mchntEnAbbr;mchntAddr;zipCd;bizLicenseNo;contactPerson;contactPhone;contactMail;fax;"
			+ "transType;payType;stCd;shareCertMchntCd;trustDomain;areaCd;"
			+ "tradeType;riskFlag;mchntTp;agentCd;bgRetUrl;pageRetUrl;"
			+ "comment;recCrtTs;recUpdTs;lastOperId"
			).split(";");
	@Override
	protected Map<String, Map<String, String>> reloadAll() throws Exception {
		IMchntInfoService svc = DBHessionServiceClient.getService(IMchntInfoService.class);
		List<MchntInfo> recs = svc.select(new HashMap<String, String>());
		
		Map<String, Map<String, String>> map = new HashMap<>();
		for(MchntInfo r: recs) {
			Map<String, String> mrec= BeanUtil.describe(r, IgnoredFields, null);
			if (mrec!=null) {
				map.put(toMapKey(r.getMchntCd()), mrec);
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
