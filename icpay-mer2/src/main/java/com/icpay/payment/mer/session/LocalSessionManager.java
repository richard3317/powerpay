package com.icpay.payment.mer.session;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.UUIDGenerator;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
/**
 * 会话管理类
 * @author lijin
 *
 */
public final class LocalSessionManager {

	private static final Logger logger = Logger.getLogger(LocalSessionManager.class);

	private static final LocalSessionManager _INSTANCE = new LocalSessionManager();

	private static final int MIN_MAXCAPACITY = 64; // 最小存放会话信息个数
	private static final int DFT_TIMEOUT = 10 * 60 * 1000; // 默认失效时间：30分钟
	public static int VCODE_TIMEOUT = 2 * 60 * 1000; // 验证码效期：2分钟

	private ConcurrentMap<String, LocalSession> storage=null;
	private int maxCapacity;
	private boolean init;
	private int timeout;
	private int cleanUpThreshold;
	private Boolean cleaningUp = false;
	private ExecutorService executor = Executors.newSingleThreadExecutor();

	private LocalSessionManager() {}


	protected ConcurrentMap<String, LocalSession> getStorage(){
		if (this.storage==null)
			this.storage = new ConcurrentHashMap<String, LocalSession>(maxCapacity);
		return this.storage;
	}

	/**
	 * 初始化会话信息管理器
	 * @param maxCapacity
	 * @param timeout
	 */
	public static void init(int maxCapacity, int timeout, int vcodeTimeout) {
		if (!_INSTANCE.init) {
			_INSTANCE._init(maxCapacity, timeout, vcodeTimeout);
		}
	}

	/**
	 * 创建会话
	 * @param sessionId
	 * @return
	 */
	public static LocalSession newSession(String sessionId) {
		if (StringUtil.isBlank(sessionId)) {
			sessionId = UUIDGenerator.getUUID() + Thread.currentThread().getId();
		}
		LocalSession session = new LocalSession(sessionId);
		_INSTANCE._put(sessionId, session);
		return session;
	}

	/**
	 * 获取会话信息
	 * @param sessionId
	 * @return
	 */
	public static LocalSession getSession(String sessionId) {
		AssertUtil.strIsBlank(sessionId, "sessionId is blank.");
		LocalSession se = _INSTANCE._get(sessionId);
		// 检查取出的会话信息与sessionId是否一致
		if (se != null && !se.getSessionId().equals(sessionId)) {
			logger.error("error session data:" + sessionId + "_" + se.getSessionId());
			throw new BizzException(String.format("会话异常(key=%s, sessionId=%s)", sessionId, se.getSessionId()));
		}
		return (se == null || se.isTimeout(_INSTANCE.timeout)) ? null : se;
	}

	/**
	 * 删除会话信息
	 * @param sessionId
	 * @return
	 */
	public static LocalSession removeSession(String sessionId) {
		AssertUtil.strIsBlank(sessionId, "sessionId is blank.");
		LocalSession session = _INSTANCE._remove(sessionId);
		return session;

	}


	/**
	 * @param sessionId
	 * @param session
	 */
	protected static void debugSession(String prefix, LocalSession session) {
		if (session==null) return;
		MchntInfo merInfo = (MchntInfo) session.getSessionData(SessionKeys.MCHNT_INFO.getCode(), false);
		String merId= (merInfo==null ? "" : merInfo.getMchntCd());
		logger.debug(String.format(prefix+"sessionId=%s, mchntCd=%s, createTm=%s, lastVisitTm=%s", session.getSessionId(), merId, new Date(session.getCreateTm()), new Date(session.getLastVisitTm())));
	}

	public static void monitor() {
		logger.info("#####session monitor started#####");
		logger.info("##current session number: " + _INSTANCE.getStorage().size() + "##");
		int c = 0;
		//移除sessionId与Key不同的session
		for (String key : _INSTANCE.getStorage().keySet()) {
			LocalSession session = _INSTANCE.getStorage().get(key);
			if ((session != null) && (!session.getSessionId().equals(key))) {
				logger.error("error session data:" + key + "_" + session.getSessionId());
				session = removeSession(key);
				debugSession(String.format("[monitor] Remove session for key '%s': ", key),session);
			}

			if (session != null
					&& session.isTimeout(_INSTANCE.timeout)) {
				c ++;
			}
		}
		logger.info("##timeout session number: " + c + "##");
		logger.info("#####session monitor end#####");
	}

	public static void cleanUpTimeout() {
		logger.info(String.format("##### Cleanup timeout session started (sessions: %s, timeout: %s) #####", _INSTANCE.getStorage().size(), _INSTANCE.timeout));
		int c = 0;
		for (String key : _INSTANCE.getStorage().keySet()) {
			LocalSession session = _INSTANCE.getStorage().get(key);
			if (session != null
					&& session.isTimeout(_INSTANCE.timeout)) {
				session = removeSession(key);
				debugSession("[cleanUpTimeout] Remove session: ",session);
				c ++;
			}

			if (session!=null) {

			}

		}
		logger.info("## Cleanup timeout session number: " + c + " ##");
		logger.info(String.format("##### Cleanup timeout session end (sessions: %s) #####", _INSTANCE.getStorage().size()));
	}

	private void _init(int maxCapacity, int timeout, int vcodeTimeout) {
		if (vcodeTimeout>(30*1000))
			VCODE_TIMEOUT = vcodeTimeout;
		this.maxCapacity = maxCapacity < MIN_MAXCAPACITY ? MIN_MAXCAPACITY : maxCapacity;
		//this.maxCapacity = 9600;
		this.storage = new ConcurrentHashMap<String, LocalSession>(this.maxCapacity);
		this.timeout = timeout > DFT_TIMEOUT ? timeout : DFT_TIMEOUT;
		this.cleanUpThreshold = this.maxCapacity >> 2;
		init = true;
	}

	private void _put(String sessionId, LocalSession session) {
		AssertUtil.strIsBlank(sessionId, "sessionId is blank.");
		AssertUtil.objIsNull(session, "session is null.");

		if (getStorage().containsKey(sessionId)) {
			throw new BizzException("session already exist:" + sessionId);
		}

		if (getStorage().size() >= maxCapacity) {
			throw new BizzException(String.format("[Error] session manager is full. current size=%s, maxCapacity=%s",getStorage().size() , maxCapacity));
		}

		if (_needCleanUp()) {
			_cleanUp();
		}

		getStorage().put(sessionId, session);
	}

	private LocalSession _get(String sessionId) {
		AssertUtil.strIsBlank(sessionId, "sessionId is blank.");
		LocalSession s = null;
		if (getStorage() != null && getStorage().containsKey(sessionId)) {
			s = getStorage().get(sessionId);
			// 获取会话信息后，刷新访问时间
			s.refreshVisitTm();
		}
		return s;
	}

	private LocalSession _remove(String sessionId) {
		AssertUtil.strIsBlank(sessionId, "sessionId is blank.");
		LocalSession s = null;
		if (getStorage().containsKey(sessionId)) {
			s=getStorage().remove(sessionId);
		}
		//debugSession("[_remove] Remove session: ",s);
		return s;
	}

	private boolean _needCleanUp() {
		if (cleaningUp) {
			return false;
		}
		return getStorage().size() > cleanUpThreshold;
	}

	private void _cleanUp() {
		if (cleaningUp) {
			return;
		}

		executor.execute(new Runnable() {
			@Override
			public void run() {
				if (_needCleanUp()) {
					synchronized (cleaningUp) {
						if (_needCleanUp()) {
							logger.info("#######session cleanup started#######");
							int startNum = getStorage().size();
							cleaningUp = true;
							int c = 0;
							try {
								for (String key : getStorage().keySet()) {
									LocalSession session = getStorage().get(key);
									if (session.isTimeout(timeout)) {
										getStorage().remove(key);
										c ++;
										debugSession("[_cleanUp] Remove session: ",session);
									}
								}
							} finally {
								cleaningUp = false;
							}
							int endNum = getStorage().size();
							logger.info("##cleanup from [" + startNum + "] to [" + endNum + "]" +
									", [" + c + "] timeout session are removed##");
							logger.info("#######session cleanup end#######");
						} else {
							logger.info("##already cleaned##");
						}
					}
				}
			}
		});
	}
}
