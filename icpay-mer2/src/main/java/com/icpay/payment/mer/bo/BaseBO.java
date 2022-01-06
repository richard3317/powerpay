package com.icpay.payment.mer.bo;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.icpay.payment.common.constants.RspCd;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.enums.AjaxRespEnums;
import com.icpay.payment.common.enums.MerEnums;
import com.icpay.payment.common.enums.MerEnums.MerUserRole;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.exception.ChnlBizException;
import com.icpay.payment.common.utils.EncryptUtil;
import com.icpay.payment.common.utils.PropUtils;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.db.service.IMchntUserInfoService;
import com.icpay.payment.mer.constants.MerConstants.LoginState;
import com.icpay.payment.mer.session.LocalSession;
import com.icpay.payment.mer.session.LocalSessionManager;
import com.icpay.payment.mer.session.SessionKeys;
import com.icpay.payment.mer.util.MerUtils;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.service.SessionUtilService;

public abstract class BaseBO {
	protected HttpSession getSession() {
		return this.getSession(null);
	}

	protected HttpSession getSession(HttpServletRequest request) {
		if (request==null)
			request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		if (request==null) return null;
		if (request.getSession(false) == null
				|| StringUtil.isBlank(request.getSession(false).getId())) {
			return null;
		}
		return request.getSession(false);
	}
	public LocalSession getLocalSession() {
		return getLocalSession(null);
	}
	public LocalSession getLocalSession(HttpServletRequest request) {
		if (request==null)
			request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		if (request==null) return null;
		HttpSession session=getSession(request);
		if (session==null) return null;
		LocalSession ls = LocalSessionManager.getSession(session.getId());
		return ls;
	}
	
	protected void checkNecessaryFieldForQry(Map<String, String> qryParams, String fieldName) {
		if (qryParams==null) return;
		String v = qryParams.get(fieldName);
		if (Utils.isEmpty(v)) {
			qryParams.put(fieldName, "(NULL)");
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getSessionData(String key) {
//		HttpServletRequest request =
//			((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//		if (request.getSession(false) == null || StringUtil.isBlank(request.getSession(false).getId())) {
//			return null;
//		}
//		LocalSession session = LocalSessionManager.getSession(request.getSession().getId());
		LocalSession session = this.getLocalSession();
		return session == null ? null : (T) session.getSessionData(key);
	}

	public void setSessionData(String key, Object value) {
		setSessionData(key, value, false);
	}

	public void setSessionData(String key, Object value, boolean createIfNotExists) {
		HttpServletRequest request =
			((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		LocalSession session = null;

//		if (request.getSession(false) == null || StringUtil.isBlank(request.getSession(false).getId())) {
//			if (!createIfNotExists) throw new BizzException("会话已失效，请重新登录.");
//		}
//		else {
//			session=LocalSessionManager.getSession(request.getSession().getId());
//		}
//
//		if (session==null) {
//			if (!createIfNotExists) throw new BizzException("会话已过期");
//			session=LocalSessionManager.newSession(request.getSession().getId());
//		}

		if (getSession(request) == null) {
			if (!createIfNotExists) throw new BizzException("会话已失效，请重新登录.");
		}

		session= this.getLocalSession();
		if (session==null) {
			if (!createIfNotExists) throw new BizzException("会话已过期");
			session=LocalSessionManager.newSession(request.getSession(true).getId());
		}

		session.putSessionData(key, value);
	}

	public void removeSessionData(String key) {
		HttpServletRequest request =
			((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//		if (this.getSession(request) == null) {
//			throw new BizzException("会话已失效，请重新登录.");
//		}
		LocalSession session = this.getLocalSession(request);
		if (session!=null)
			session.removeSessionData(key);
	}

	public AjaxResult buildSuccResp() {
//		AjaxResult rst = new AjaxResult();
//		rst.setRespCode(AjaxRespEnums.SUCC.getRespCode());
//		rst.setRespMsg(AjaxRespEnums.SUCC.getRespMsg());
//		return rst;
		return this.buildAjaxResp(AjaxRespEnums.SUCC);
	}

	public AjaxResult buildSuccResp(String msg) {
//		AjaxResult rst = new AjaxResult();
//		rst.setRespCode(AjaxRespEnums.SUCC.getRespCode());
//		rst.setRespMsg(msg);
//		return rst;
		return this.buildAjaxResp(AjaxRespEnums.SUCC, msg);
	}

	public AjaxResult buildSuccResp(String resultDataKey, Object resultData) {
//		AjaxResult rst = new AjaxResult();
//		rst.setRespCode(AjaxRespEnums.SUCC.getRespCode());
//		rst.setRespMsg(AjaxRespEnums.SUCC.getRespMsg());
//		if (!StringUtil.isEmpty(resultDataKey)
//				&& resultData != null) {
//			rst.addResultData(resultDataKey, resultData);
//		}
//		return rst;

		return this.buildAjaxResp(AjaxRespEnums.SUCC)
				  .addResultData(resultDataKey, resultData);

	}

	public AjaxResult buildSuccResp(String msg, String resultDataKey, Object resultData) {
//		AjaxResult rst = new AjaxResult();
//		rst.setRespCode(AjaxRespEnums.SUCC.getRespCode());
//		rst.setRespMsg(msg);
//		if (!StringUtil.isEmpty(resultDataKey)
//				&& resultData != null) {
//			rst.addResultData(resultDataKey, resultData);
//		}
//		return rst;

		return this.buildAjaxResp(AjaxRespEnums.SUCC, msg)
				  .addResultData(resultDataKey, resultData);
	}

	public AjaxResult buildErrorResp(String errMsg, String resultDataKey, Object resultData) {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(AjaxRespEnums.ERROR.getRespCode());
		if (Utils.isEmpty(errMsg))
			errMsg = AjaxRespEnums.ERROR.getRespMsg();
		rst.setRespMsg(errMsg);
		if (!StringUtil.isEmpty(resultDataKey)
				&& resultData != null) {
			rst.addResultData(resultDataKey, resultData);
		}
		return rst;
	}


	public AjaxResult buildAjaxResp(AjaxRespEnums ajaxResp) {
//		AjaxResult rst = new AjaxResult();
//		rst.setRespCode(ajaxResp.getRespCode());
//		rst.setRespMsg(ajaxResp.getRespMsg());
//		return rst;
		return buildAjaxResp(ajaxResp.getRespCode(), ajaxResp.getRespMsg());
	}

	public AjaxResult buildAjaxResp(AjaxRespEnums ajaxResp, Map<String,Object> respData) {
		AjaxResult rst = buildAjaxResp(ajaxResp);
		rst.setRespData(respData);
		return rst;
	}

	public AjaxResult buildAjaxResp(AjaxRespEnums ajaxResp, String msg) {
//		AjaxResult rst = new AjaxResult();
//		rst.setRespCode(ajaxResp.getRespCode());
//		if (StringUtil.isBlank(msg)) {
//			rst.setRespMsg(ajaxResp.getRespMsg());
//		} else {
//			rst.setRespMsg(msg);
//		}
//		return rst;

		if (StringUtil.isBlank(msg))
			msg = ajaxResp.getRespMsg();
		return buildAjaxResp(ajaxResp.getRespCode(), msg);
	}

	public AjaxResult buildAjaxResp(AjaxRespEnums ajaxResp, String msg,  Map<String,Object> respData) {
		AjaxResult rst = buildAjaxResp(ajaxResp, msg);
		rst.setRespData(respData);
		return rst;
	}

	public AjaxResult buildAjaxResp(String code, String msg) {
		return buildAjaxResp(code,msg,null);
	}

	public AjaxResult buildAjaxResp(String code, String msg, Map<String,Object> respData) {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(code);
		rst.setRespMsg(msg);
		rst.setRespData(respData);
		return rst;
	}



	public AjaxResult buildErrorResp() {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(AjaxRespEnums.ERROR.getRespCode());
		rst.setRespMsg(AjaxRespEnums.ERROR.getRespMsg());
		return rst;
	}

	public AjaxResult buildErrorResp(String msg) {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(AjaxRespEnums.ERROR.getRespCode());
		if (StringUtil.isEmpty(msg)) {
			rst.setRespMsg(AjaxRespEnums.ERROR.getRespMsg());
		} else {
			rst.setRespMsg(msg);
		}
		return rst;
	}

	public MchntInfo getLoginMer() {
		return this.getSessionData(SessionKeys.MCHNT_INFO.getCode());
	}

	public MchntUserInfo getMchntUser() {
		return this.getSessionData(SessionKeys.MCHNT_USER_INFO.getCode());
	}

	public String getMchntCd() {
		MchntInfo merInfo = getLoginMer();
		if (merInfo==null) return null;
		return merInfo.getMchntCd();
	}

	public String getMchntCode() {
		return getMchntCd();
	}

	public String getLoginType() {
		return this.getSessionData(SessionKeys.GOOGLE_USER_LOGIN_TYPE.getCode());
	}

	protected <T> T getDBService(Class<T> clazz) {
		return DBHessionServiceClient.getService(clazz);
	}

	/**
	 * 检查商户是否登录
	 * @param sessionId
	 * @return
	 */
	public boolean isLogined(String reqIp) {
		//HttpServletRequest request
		Object loginState = this.getSessionData(SessionKeys.LOGIN_STATE.getCode());
		Object loginRole = this.getSessionData(SessionKeys.ROLE.getCode());
		String orgReqIp = ""+this.getSessionData(SessionKeys.CLIENTIP.getCode());
		Object mchnt = this.getSessionData(SessionKeys.MCHNT_INFO.getCode());
		Object muser = this.getSessionData(SessionKeys.MCHNT_USER_INFO.getCode());
		return (!Utils.isEmpty(loginRole)) && LoginState.OK.equals(loginState) && (orgReqIp.equals(reqIp)) && (! Utils.isEmpty(mchnt))  && (!Utils.isEmpty(muser));
	}

	public boolean isLogined(HttpServletRequest request) {
		String reqIp = ""+ MerUtils.getRemoteIp(request);
		return isLogined(reqIp);
	}

	/**
	 * 回傳登入的角色，注意有回傳值不代表登入成功，需一起判斷 isLogined()
	 * @return
	 */
	public String loginedRole() {
		Object loginRole = this.getSessionData(SessionKeys.ROLE.getCode());
		return strVal(loginRole, null);
	}

	public String getLoginedIp() {
		Object obj = this.getSessionData(SessionKeys.CLIENTIP.getCode());
		return strVal(obj, null);
	}

	/**
	 * 回傳目前角色是否允許代付
	 * @return
	 */
	public boolean isAllowedWithdraw() {
		Object wd = this.getSessionData(SessionKeys.IS_IP_ALLOWED_WITHDRAW.getCode());
		if (! "true".equals(strVal(wd,"FALSE"))) return false;
//		return Roles.ADMIN.equals(loginedRole());
		return MerEnums.MerUserRole.WithdrawUser.getCode().equals(loginedRole());
	}

	/**
	 * 判断某个用户是否在指定的角色之中。
	 * 以下范例会检测当前用户角色是否为超级用户(SuperUser)或代付用户(WithdrawUser): <pre>
	 * if (bo.isUserInRoles(MerUserRole.WithdrawUser, MerUserRole.SuperUser)){
     *    ......
     * }
	 * </pre>
	 * @param roles 角色
	 * @return
	 */
	public boolean isUserInRoles(MerUserRole... roles) {
		MerUserRole curRole=getUserRole();
		return Utils.isInSet(curRole, roles);
	}

	public MerUserRole getUserRole() {
		//TODO 待續
		return MerUserRole.WithdrawUser;
	}

	protected String strVal(Object obj, String defaultVal) {
		if (obj==null) return defaultVal;
		return obj.toString();
	}

	public Long lastWithdrawTime() {
		Object obj = this.getSessionData(SessionKeys.LAST_WITHDRAW_TIME.getCode());
		return Long.parseLong(strVal(obj,"0"));
	}

	public String lastWithdrawContent() {
		Object obj = this.getSessionData(SessionKeys.LAST_WITHDRAW_CONTENT.getCode());
		return strVal(obj,"");
	}

	public String lastWithdrawListMd5() {
		Object obj = this.getSessionData(SessionKeys.LAST_WITHDRAW_LIST_MD5.getCode());
		return strVal(obj,"");
	}

	public String genRandomVal() {
		String randv = Utils.getRandomString(8);
		this.setSessionData(SessionKeys.RANDOM.getCode(), randv);
		return randv;
	}

	public boolean checkRandomVal(String randv) {
		Object obj = this.getSessionData(SessionKeys.RANDOM.getCode());
		String randv0= strVal(obj,"");
		return randv0.equals(randv);
	}

	public boolean compareOrSetLastRandomVal(String randv) {
		Object obj = this.getSessionData(SessionKeys.LAST_RANDOM.getCode());
		String randv0= strVal(obj,"");
		boolean res= randv0.equals(randv);
		if(!res)
			this.setSessionData(SessionKeys.LAST_RANDOM.getCode(), randv);
		return res;
	}

	public synchronized boolean isDuplicateWithdraw(String accName, String accNum, String txnAmt, String randv) {

		// 检查乱数是否与session中的一致，若不同回传 true 表示重复
		if (!checkRandomVal(randv)) return true;
		// 检查乱数是否与上次的相同
		if (compareOrSetLastRandomVal(randv)) return true;

		//String cnt = String.format("%s;%s;%s", accName,accNum,txnAmt);
		String cnt = String.format("%s;%s", accNum,txnAmt);
		Long tWin= DuplicateOptWindow*1000L; //16 secs

		long tLast= lastWithdrawTime();
		String cntLast=lastWithdrawContent();

		long tnow= (new Date()).getTime();

		if ((tnow - tLast)<tWin) {
			if (cntLast.equals(cnt))
				//throw new ChnlBizException(RspCd.Z_3501,"代付操作重复！");
				return true;
		}

		this.setSessionData(SessionKeys.LAST_WITHDRAW_TIME.getCode(), tnow);
		this.setSessionData(SessionKeys.LAST_WITHDRAW_CONTENT.getCode(), cnt);

		return false;
	}
	public boolean isDuplicateWithdrawList(String withdrawList) {
		String cnt = EncryptUtil.md5(withdrawList);
		Long tWin= (DuplicateOptWindow+15)*1000L; //120 secs

		long tLast= lastWithdrawTime();
		String cntLast=lastWithdrawListMd5();

		long tnow= (new Date()).getTime();

		if ((tnow - tLast)<tWin) {
			if (cntLast.equals(cnt))
				return true;
		}

		this.setSessionData(SessionKeys.LAST_WITHDRAW_TIME.getCode(), tnow);
		this.setSessionData(SessionKeys.LAST_WITHDRAW_LIST_MD5.getCode(), cnt);
		this.setSessionData(SessionKeys.WITHDRAW_LIST_MD5.getCode(), cnt);

		return false;
	}

	public synchronized boolean isDuplicateTransfer(String whiteItem, String txnAmt, String randv) {

		// 检查乱数是否与session中的一致，若不同回传 true 表示重复
		if (!checkRandomVal(randv)) return true;
		// 检查乱数是否与上次的相同
		if (compareOrSetLastRandomVal(randv)) return true;

		String cnt = String.format("%s;%s", whiteItem,txnAmt,randv);
		Long tWin= 10*1000L; //10 secs

		long tLast= lastWithdrawTime();
		String cntLast=lastWithdrawContent();

		long tnow= (new Date()).getTime();
		if ((tnow - tLast)<tWin) {
			if (cntLast.equals(cnt))
				return true;
		}

		this.setSessionData(SessionKeys.LAST_WITHDRAW_TIME.getCode(), tnow);
		this.setSessionData(SessionKeys.LAST_WITHDRAW_CONTENT.getCode(), cnt);

		return false;
	}

	public void checkDuplicateWithdraw(String accName, String accNum, String txnAmt, String randv) {
		if (isDuplicateWithdraw(accName, accNum, txnAmt,randv))
			throw new ChnlBizException(RspCd.Z_3501,"代付操作重复！");
	}


	/**
	 * 获取代付相关信息的 MD5 hash
	 * @param accName
	 * @param accNum
	 * @param txnAmt
	 * @param bankCd
	 * @return
	 */
	public String getWithdrawHash(String accName, String accNum, String txnAmt, String bankCd) {
		StringBuffer bf = new StringBuffer();
		bf.append(accName).append("|")
		.append(accNum).append("|")
		.append(txnAmt).append("|")
		.append(bankCd);
		String cnt = EncryptUtil.md5(bf.toString());
		return cnt;
	}

	/**
	 * 商戶用戶登入信息服務
	 * @return
	 */
	protected IMchntUserInfoService mchntUserSvc() {
		return this.getDBService(IMchntUserInfoService.class);
	}

	protected static int DuplicateOptWindow = getDuplicateOptWindow(70);
	protected static int getDuplicateOptWindow(int defaultVal) {
		int r=defaultVal;
		try {
			String s =PropUtils.getProperty("MerConfig.properties", "duplicateOptWindow", ""+defaultVal);
			r = Integer.parseInt(s);
			//r = intVal(MerConfigCache.getConfig("duplicateOptWindow"), defaultVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	/**
	 * 将指定的一系列值序列化之后写入指定的 Session Key 值
	 * @param sessionKey Session Key
	 * @param overrwrite 是否复写
	 * @param values 是否写入成功
	 * @return
	 */
	public boolean setSessionAttach(String sessionKey, boolean overrwrite, Object... values) {
		Object val = this.getSessionData(sessionKey);
		if (Utils.isEmpty(val) || overrwrite) {
			String s = serilizeToStr(values);
			this.setSessionData(sessionKey, s);
			return true;
		}
		return false;
	}
	public Object getSessionAttach(String sessionKey) {
		return this.getSessionData(sessionKey);
	}

	static final String ATTACH_MD5_SALT="Gt6sh15shaa";
	public boolean rememberSessionAttach(String sessionKey, boolean overrwrite, Object... values) {
		Object val = this.getSessionData(sessionKey);
		if (Utils.isEmpty(val) || overrwrite) {
			String md5 = md5ObjectsWithSalt(values);
			this.setSessionData(sessionKey, md5);
			return true;
		}
		return false;
	}

	/**
	 * 比对透过 rememberSessionAttach() 所记录的值与此次是否相符
	 * @param sessionKey
	 * @param overrwrite
	 * @param newValues 新的值
	 * @return
	 */
	public boolean compareSessionAttach(String sessionKey, Object... newValues) {
		String md5 = md5ObjectsWithSalt(newValues);
		String lastMd5 = ""+getSessionAttach(sessionKey);
		return lastMd5.equals(md5);
	}

	/**
	 * 将物件序列化成字符串，以 ; 区隔
	 * @param values
	 * @return
	 */
	public String serilizeToStr(Object... values) {
		if (values==null) return "";
		StringBuffer buf = new StringBuffer();
		for(Object obj: values) {
			buf.append(objToStr(obj)).append(";");
		}
		return buf.toString();
	}

	/**
	 * 将多个物件 MD5
	 * @param values
	 * @return
	 */
	public String md5Objects(Object... values) {
		String str = serilizeToStr(values);
		return EncryptUtil.md5(str);
	}

	public String md5ObjectsWithSalt(Object... values) {
		String str = serilizeToStr(values)+ATTACH_MD5_SALT;
		return EncryptUtil.md5(str);
	}

	protected String objToStr(Object obj) {
		if (obj==null) return "";
		return obj.toString();
	}

	protected static String GlobalSessionCatalog="_MER_";

	protected String getGlobalSessionCatalog(String sessionId) {
		if (sessionId==null) sessionId = "";
		return GlobalSessionCatalog+sessionId;
	}

	public void putGlobalSessionData(String sessionId, String key, String value, int expiresInSecs) {
		SessionUtilService svc = ServiceProxy.getService(SessionUtilService.class);
		if (expiresInSecs>=0)
			svc.putWithExpires(getGlobalSessionCatalog(sessionId), key, value, expiresInSecs);
		else
			svc.put(GlobalSessionCatalog, key, value);
	}

	public void putGlobalSessionData(String key, String value, int expiresInSecs) {
		putGlobalSessionData(null, key, value, expiresInSecs);
	}

	public String getGlobalSessionData(String sessionId, String key, String defaultVal) {
		SessionUtilService svc = ServiceProxy.getService(SessionUtilService.class);
		return svc.getWithDefault(getGlobalSessionCatalog(sessionId), key, defaultVal);
	}

	public String getGlobalSessionData(String key, String defaultVal) {
		return getGlobalSessionData(null, key, defaultVal);
	}


}
