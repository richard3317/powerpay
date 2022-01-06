package com.icpay.payment.mer.util;

import java.util.Date;

import com.icpay.payment.common.enums.MerEnums.MerUserState;
import com.icpay.payment.common.utils.Converter;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.db.service.IMchntUserInfoService;
import com.icpay.payment.mer.cache.MerParamCache;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.service.SessionUtilService;

public class GoogleAuthenticatorUtils {

	public static String GaLoginFailedCount = "";
    public static long GaLoginOkFirstTime = 0L;
    
    
    /**
     * 輸入錯誤，增加次數
     * @param mchntUserInfo
     */
    public static boolean countGaLoginFail (MchntUserInfo mchntUserInfo) {
    	String gaLoginFailedCountKey = "mer." + mchntUserInfo.getMchntCd() + "." + mchntUserInfo.getUserId() + "." + "gaLoginFailedCount";
    	String gaLoginOkFirstTimeKey = "mer." + mchntUserInfo.getMchntCd() + "." + mchntUserInfo.getUserId() + "." + "gaLoginOkFirstTime";
    	String loginFailedCount = getGlobalSessionData(gaLoginFailedCountKey);
    	String loginOkFirstTime = getGlobalSessionData(gaLoginOkFirstTimeKey);
    	
    	if (!StringUtil.isEmpty(loginFailedCount) && !StringUtil.isEmpty(loginOkFirstTime)) {
    		// x分鐘內連續錯誤 y次 則鎖定該帳號 (從數據庫配置tbl_mer_params.GA_Login_Failed_Count、GA_Login_Failed_Time_Limit)
    		long now = System.currentTimeMillis();
    		long firstTime = Long.parseLong(loginOkFirstTime);
			if(((now - firstTime) / 1000) <= MerParamCache.GA_Login_Failed_Time_Limit) {
				int failedCount = Integer.valueOf(loginFailedCount) + 1;
				if (failedCount >= MerParamCache.GA_Login_Failed_Count) {
					//鎖定該帳號
					lockedUserState(mchntUserInfo);
					return false;
				} else {
					putGlobalSessionData(gaLoginFailedCountKey, String.valueOf(failedCount), MerParamCache.GA_Login_Failed_Time_Limit);
				}
			} else {
				//x分鐘後次數時間清空
				delGlobalSessionData(gaLoginFailedCountKey);
				delGlobalSessionData(gaLoginOkFirstTimeKey);
			}
    	} else {
	    	putGlobalSessionData(gaLoginFailedCountKey, "1", MerParamCache.GA_Login_Failed_Time_Limit);
	    	long now = System.currentTimeMillis();
	    	putGlobalSessionData(gaLoginOkFirstTimeKey, String.valueOf(now), MerParamCache.GA_Login_Failed_Time_Limit);
    	}
    	return true;
    }
    
    /**
     * 輸入正確，清除次數
     * @param mchntUserInfo
     */
    public static void removeGaLoginFail (MchntUserInfo mchntUserInfo) {
    	String gaLoginFailedCountKey = "mer." + mchntUserInfo.getMchntCd() + "." + mchntUserInfo.getUserId() + "." + "gaLoginFailedCount";
    	String gaLoginOkFirstTimeKey = "mer." + mchntUserInfo.getMchntCd() + "." + mchntUserInfo.getUserId() + "." + "gaLoginOkFirstTime";
    	String loginFailedCount = getGlobalSessionData(gaLoginFailedCountKey);
    	String loginOkFirstTime = getGlobalSessionData(gaLoginOkFirstTimeKey);
    	
    	if (!StringUtil.isEmpty(loginFailedCount) && !StringUtil.isEmpty(loginOkFirstTime)) {
    		delGlobalSessionData(gaLoginFailedCountKey);
			delGlobalSessionData(gaLoginOkFirstTimeKey);
    	}
    }
	
	/**
	 * 鎖定該帳號
	 * @param mchntUserInfo
	 */
	protected static void lockedUserState(MchntUserInfo mchntUserInfo) {
		IMchntUserInfoService service = DBHessionServiceClient.getService(IMchntUserInfoService.class);
        MchntUserInfo mui = new MchntUserInfo();
        String mchntCd = mchntUserInfo.getMchntCd();
        String userId = mchntUserInfo.getUserId();
        mui.setMchntCd(mchntCd);
        mui.setUserId(userId);
        mui.setUserState(MerUserState.Locked.getCode());
        mui.setLoginComment(String.format("[%s] 商户： %s; 用户： %s 谷歌验证错误，登入失败次数 %s 超限，帐户已锁定!",
                Converter.dateTimeToString(new Date()), mchntCd, userId, MerParamCache.GA_Login_Failed_Count));
        service.updateByPrimaryKeySelective(mui);
	}
	
	//存取Redis
	protected static String GlobalSessionCatalog="MER";
	protected static void putGlobalSessionData(String key, String value, int expiresInSecs) {
		SessionUtilService svc = ServiceProxy.getService(SessionUtilService.class);
		if (expiresInSecs>=0)
			svc.putWithExpires(GlobalSessionCatalog, key, value, expiresInSecs);
		else
			svc.put(GlobalSessionCatalog, key, value);
	}

	protected static String getGlobalSessionData(String key) {
		SessionUtilService svc = ServiceProxy.getService(SessionUtilService.class);
		return svc.get(GlobalSessionCatalog, key);
	}
	
	protected static boolean delGlobalSessionData(String key) {
		SessionUtilService svc = ServiceProxy.getService(SessionUtilService.class);
		return svc.delete(GlobalSessionCatalog, key);
	}
}
