package com.icpay.payment.mer.session;

import java.util.HashMap;
import java.util.Map;

import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.mer.constants.MerConstants.LoginState;

public class LocalSession {

	private String sessionId;
	private long createTm;
	private long lastVisitTm;

	private Map<String, Object> sessionDataMap;

	public LocalSession(String sessionId) {
		AssertUtil.strIsBlank(sessionId, "sessionId is blank.");
		this.sessionId = sessionId;
		this.sessionDataMap = new HashMap<String, Object>();
		long now = System.currentTimeMillis();
		this.createTm = now;
		this.lastVisitTm = now;
	}

	public boolean isTimeout(long timeout) {
		//TODO 從 Redis 讀取 last visit time

		//System.out.println("this.lastVisitTm: "+this.lastVisitTm);
		if (timeout > 0) {
			long timeSpent= System.currentTimeMillis() - this.lastVisitTm;
			//System.out.println("timeSpent: "+timeSpent);
			//先判断上次访问时间是否超时
			boolean expired= timeSpent >= timeout;
			if (expired) return true;

			//以下判断是否尚未登入却超过验证码效期
			Object loginState = this.getSessionData(SessionKeys.LOGIN_STATE.getCode(), false);
			if(LoginState.OK.equals(loginState)) return false; //已登入则判定未超时
			//未登入状况则判断是否超过验证码效期
			expired= timeSpent >= LocalSessionManager.VCODE_TIMEOUT;
			return expired;
		}
		return false;
	}

	public Object getSessionData(String key) {
		return getSessionData(key, true);
	}

	/**
	 * 獲取 Session Data
	 * @param key
	 * @param resetVisitTime 是否重置最後存取/拜訪時間
	 * @return
	 */
	public Object getSessionData(String key, boolean resetVisitTime) {
		AssertUtil.strIsBlank(key, "key is blank.");

		if (sessionDataMap.containsKey(key)) {
			if (resetVisitTime)
				refreshVisitTm();
			return sessionDataMap.get(key);
		} else {
			return null;
		}
	}

	public Object putSessionData(String key, Object obj) {
		AssertUtil.strIsBlank(key, "key is blank.");
		AssertUtil.objIsNull(obj, "obj is null.");

		Object oldObj = null;
		if (sessionDataMap.containsKey(key)) {
			oldObj = sessionDataMap.get(key);
		}
		sessionDataMap.put(key, obj);
		refreshVisitTm();
		return oldObj;
	}

	public Object removeSessionData(String key) {
		AssertUtil.strIsBlank(key, "key is blank.");
		Object oldObj = null;
		if (sessionDataMap.containsKey(key)) {
			oldObj = sessionDataMap.get(key);
			sessionDataMap.remove(key);
		}
		return oldObj;
	}

	public void clear() {
		this.sessionDataMap.clear();
		this.sessionDataMap = new HashMap<String, Object>();
	}

	public void refreshVisitTm() {
		lastVisitTm = System.currentTimeMillis();
		//TODO write last visit time to Redis (key=merId+userId)
	}

	public long getLastVisitTm() {
		return lastVisitTm;
	}

	public void setLastVisitTm(long lastVisitTm) {
		this.lastVisitTm = lastVisitTm;
	}

	public String getSessionId() {
		return sessionId;
	}

	public long getCreateTm() {
		return createTm;
	}
}
