package com.icpay.payment.mer.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icpay.payment.common.constants.Names.MSG;
import com.icpay.gauth.GoogleAuthenticator;
import com.icpay.payment.common.constants.RspCd;
import com.icpay.payment.common.exception.ChnlBizException;
import com.icpay.payment.common.utils.EncryptUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.common.utils.logger.Log;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.BankNums;
import com.icpay.payment.db.dao.mybatis.model.MerBanks;
import com.icpay.payment.db.service.IMerBanksService;
import com.icpay.payment.db.service.IRiskListItemMerService;
import com.icpay.payment.mer.bo.CommonBO;
import com.icpay.payment.mer.cache.MerConfigCache;
import com.icpay.payment.mer.cache.MerParamCache;
import com.icpay.payment.mer.session.SessionKeys;
import com.icpay.payment.mer.util.I18nMsgUtils;
import com.icpay.payment.mer.util.MerUtils;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.service.HttpClientService;
import com.icpay.payment.service.SessionUtilService;
import com.icpay.payment.service.api.HttpClientResponse;


public abstract class BaseController {

	@Autowired
	protected CommonBO commonBO;

	protected <T> T getService(Class<T> clazz) {
		return DBHessionServiceClient.getService(clazz);
	}

	/**
	 * 获取卡号白名单的项目内容
	 * @param accNo
	 * @param accName
	 * @return
	 */
	protected String accWhiteItem(String accName, String accNo) {
		return MerUtils.accWhiteItem(accName, accNo);
	}

	/**
	 * 从卡号白名单项目获取 卡号及持卡人姓名
	 * @param accWhiteItem
	 * @return
	 */
	protected String[] getAccNoInfo(String accWhiteItem) {
		return MerUtils.getAccNoInfo(accWhiteItem);
	}

	////////////////////////////////////////////////////////////////////////
	// Utils

	public HttpClientResponse httpPostByService(String reqUrl, Map<String, String> postData) throws IOException {
		HttpClientService svc = ServiceProxy.getService(HttpClientService.class);
		return svc.httpPostMap(reqUrl, postData, null, "UTF-8");
	}

	public HttpClientResponse httpPostByService(String reqUrl, String postData) throws IOException {
		HttpClientService svc = ServiceProxy.getService(HttpClientService.class);
		return svc.httpPost(reqUrl, postData, null, "UTF-8");
	}


	private Logger logger = null;

	protected Logger getLogger() {
		if (logger==null) {
			logger = Logger.getLogger(this.getClass());
		}
		return logger;
	}

	protected String format(String fmt, Object...args) {
		return String.format(fmt, args);
	}

	protected void debug(String fmt, Object...args) {
		debug(String.format(fmt, args));
	}

	protected void debug(String message) {
		getLogger().debug(message);
	}

	protected void info(String fmt, Object...args) {
		info(String.format(fmt, args));
	}

	protected void info(String message) {
		getLogger().info(message);
	}

	protected void warn(String fmt, Object...args) {
		warn(String.format(fmt, args));
	}

	protected void warn(String message) {
		getLogger().warn(message);
	}

	protected void error(Throwable err, String fmt, Object...args) {
		getLogger().error(String.format(fmt, args), err);
	}

	protected void error(String fmt, Object...args) {
		error(String.format(fmt, args));
	}

	protected void error(String message) {
		getLogger().error(message);
	}

	protected void throwError(String code, String msg) {
		error(format("[Error] %s (Code=%s);", msg, code));
		throw new ChnlBizException(code, msg);
	}

	protected void throwError(String code, String msg, Map<String, String> attach) {
		error(format("[Error] %s (Code=%s); Context: %s", msg, code, attach));
		throw new ChnlBizException(code, msg, attach);
	}

	protected void checkParam(Object param, String paramName, Map<String, String> attach, HttpServletRequest request) {
        String lang = I18nMsgUtils.getLanguage(request);
		if (Utils.isEmpty(param))
			throwError(RspCd.Z_1001, format(mappingI18n(this.getClass().getSimpleName(),"必填字段或参数'%s'缺失", lang),paramName), attach);
	}

	protected void checkDataError(int count, Map<String, String> attach, String fmt, Object...args) {
		checkDataError(count, attach, String.format(fmt, args));
	}

	protected void checkDataError(int count, Map<String, String> attach, String msg) {
		if (count<=0) {
			throwError(RspCd._05, msg, attach);
		}
	}

	protected void checkQueryError(Object record, String msg) {
		if (record==null) {
			throwError(RspCd.Z_0019, msg);
		}
	}

	protected void checkQueryError(Object record, String fmt, Object...args) {
		checkQueryError(record, String.format(fmt, args));
	}

	protected boolean checkQueryResult(Map<String,String> resp, Object record, String fmt, Object...args) {
		try {
			checkQueryError(record, String.format(fmt, args));
			return true;
		} catch (ChnlBizException e) {
			resp.put(MSG.respCode, e.getErrorCode());
			resp.put(MSG.respMsg, e.getErrorMsg());
			return false;
		}
	}

	protected void checkParam(Object param, String fmt, Object...args) {
		if (Utils.isEmpty(param))
			throwError(RspCd.Z_1001, String.format(fmt, args));
	}

	protected void checkParam(Object param, String paramName, HttpServletRequest request) {
        String lang = I18nMsgUtils.getLanguage(request);
		checkParam(param, mappingI18n(this.getClass().getSimpleName(),"缺失参数: %s", lang), paramName);
	}

	protected boolean hasRecord(List<?> list) {
		return ((list!=null) && (list.size()>0));
	}

	protected boolean hasRecord(Object obj) {
		return (obj!=null);
	}

	protected String toFloatAmt(String amt) {
		if (Utils.isEmpty(amt)) return "";
		return new BigDecimal(amt).movePointLeft(2).toString();
	}

	protected String toIntAmt(String amt) {
		if (Utils.isEmpty(amt)) return "";
		return ""+ new BigDecimal(amt).movePointRight(2).longValue();
	}

	protected static String DefaultExpectHttpRespCodePrefix="2;3;";
	/**
	 * 检查是否为预期的响应码
	 * @param respCode 响应码，如:"200","302","404"...
	 * @return 是否为预期的响应码
	 */
	protected boolean checkHttpRespCodePrefix(String respCode) {
		return checkHttpRespCodePrefix(respCode,DefaultExpectHttpRespCodePrefix);
	}
	/**
	 * 检查是否为预期的响应码
	 *
	 * @param respCode
	 *            响应码，如:"200","302","404"...
	 * @return 是否为预期的响应码
	 */
	protected boolean checkHttpRespCodePrefix(String respCode, String expectPrefixs) {
		String prefixs = expectPrefixs + ";";
		String cp = ("" + respCode).substring(0, 1);
		return prefixs.contains(cp + ";");
	}


	protected void responseHtml(HttpServletResponse resp, String statusCode, String msg) throws IOException {
		this.sendResponse(resp, "200", msg, "text/html", null);
	}

	/**
	 *
	 * @param resp
	 * @param statusCode
	 * @param respText
	 * @param contentType
	 * @param encoding
	 * @throws IOException
	 */
	protected void sendResponse(HttpServletResponse resp, String statusCode, String respText, String contentType, String encoding) throws IOException {
		Integer iStatus=null;
		if (!Utils.isEmpty(statusCode))
			iStatus = Integer.parseInt(statusCode);
		this.sendResponse(resp, iStatus, respText, contentType, encoding);
	}

	/**
	 *
	 * @param resp
	 * @param statusCode
	 * @param respText
	 * @param contentType
	 * @param encoding
	 * @throws IOException
	 */
	protected void sendResponse(HttpServletResponse resp, Integer statusCode, String respText, String contentType, String encoding) throws IOException {
		if (!Utils.isEmpty(statusCode))
			resp.setStatus(statusCode);

		if (Utils.isEmpty(encoding))
			encoding = "utf-8";

		if (Utils.isEmpty(contentType))
			contentType = "text/html; charset=" + encoding;

		resp.setContentType(contentType);

		this.debug("[sendResponse] statusCode: %s; contentType: %s; body: %s", statusCode, contentType, respText);

		ServletOutputStream sos = resp.getOutputStream();
		try {
			sos.write(respText.getBytes(encoding));
		} finally {
			sos.close();
		}
	}

	/**
	 * 将JSON对象转换成Map
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Map toMap(JSONObject json) {
		return json.toJavaObject(json, Map.class);
	}

	/**
	 * 将JSON内容转换成MAP
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Map toMap(String jsonStr) {
		return toMap(JSON.parseObject(jsonStr));
	}

	public static final String PrefixForPayment="M1";
	public static final String PrefixForWithdraw="M2";

	protected String nextOrderId(String prefix) { //TODO use jedis
		//return CommonController.newOrderId();
		SessionUtilService svc = ServiceProxy.getService(SessionUtilService.class);
		//return svc.nextSeq(SessionUtilService.SEQ_MER_ORDERID);
		return svc.nextSeqWithSubType(SessionUtilService.SEQ_MER_ORDERID, prefix);
	}

	protected long sum(Long... args) {
		if (args==null) return 0L;
		long r=0L;
		for(Long a: args) {
			if (a!=null) r=r+a;
		}
		return r;
	}

	protected void sleep(Integer interval) {
		if (interval==null) return;
		sleep(Long.valueOf(interval));
	}
	protected void sleep(Long interval) {
		if (interval==null) return;
		try {
			Thread.sleep(interval);
		} catch (InterruptedException e) {
		}
	}

	protected void sleep(Long minInterval, Long maxInterval) {
		if (maxInterval==null)
			sleep(minInterval);
		if (minInterval==null)
			sleep(maxInterval);
		sleep(Utils.getRandomLong(minInterval, maxInterval));
	}

	protected void sleep(Integer minInterval, Integer maxInterval) {
		if (maxInterval==null)
			sleep(minInterval);
		if (minInterval==null)
			sleep(maxInterval);
		sleep(Utils.getRandomLong(minInterval, maxInterval));
	}

	protected boolean validateClientIp(String mchntCd, String clientIp, HttpServletRequest request) {
		if (Utils.isEmpty(mchntCd)) return false;
		if (Utils.isEmpty(clientIp)) return false;
        String lang = I18nMsgUtils.getLanguage(request);

		boolean validated=false;

		try {
			IRiskListItemMerService riskListItemMerService = this.getService(IRiskListItemMerService.class);
			Map<String, String> qryParamMap = new HashMap<String, String>();
			qryParamMap.put("chnlId", "00");
			qryParamMap.put("groupId", "104");
			qryParamMap.put("mchntCd", mchntCd);
			qryParamMap.put("item", clientIp);
			validated = riskListItemMerService.validRiskItem("104", "00", mchntCd, clientIp, 1);
			if (validated) return true;
			this.warn(String.format("验证客户端IP白名单失败! 商户: %s; 客户端IP: %s;", mchntCd, clientIp));
			return false;
		} catch (Exception e) {
			this.error(String.format(mappingI18n(this.getClass().getSimpleName(),"验证客户端IP异常! 商户: %s; 客户端IP: %s; 错误讯息: %s", lang), mchntCd, clientIp, e.getMessage()));
		}
		return false;
	}

	protected boolean isAllowedWithdraw() {
		if (commonBO==null) return  false;
		return commonBO.isAllowedWithdraw();
	}

	protected boolean validateWithdrawClientIp(String mchntCd, String clientIp, HttpServletRequest request) {
		if (Utils.isEmpty(mchntCd)) return false;
		if (Utils.isEmpty(clientIp)) return false;
        String lang = I18nMsgUtils.getLanguage(request);

		boolean validated=false;

		try {
			IRiskListItemMerService riskListItemMerService = this.getService(IRiskListItemMerService.class);
			Map<String, String> qryParamMap = new HashMap<String, String>();
			qryParamMap.put("chnlId", "00");
			qryParamMap.put("groupId", "105");
			qryParamMap.put("mchntCd", mchntCd);
			qryParamMap.put("item", clientIp);
			validated = riskListItemMerService.validRiskItem("105", "00", mchntCd, clientIp, 0);
			this.debug(String.format("客户端IP是否允许代付操作: %s; 商户: %s; 客户端IP: %s;", validated, mchntCd, clientIp));
			if (validated) return true;
			return false;
		} catch (Exception e) {
			this.error(String.format(mappingI18n(this.getClass().getSimpleName(),"验证客户端代付IP白名单时异常! 商户: %s; 客户端IP: %s; 错误讯息: %s", lang), mchntCd, clientIp, e.getMessage()));
		}
		return false;
	}

	public String strVal(Object obj) {
		if (obj==null) return "";
		return obj.toString();
	}

	/**
	 * 获取请求中Cookie的值，设定到 Model 的属性。
	 * @param cookieName
	 * @param defaultVal 默认值，若默认值为null，而又无该cookie则，不设定属性。
	 * @param request
	 * @param model
	 */
	protected void addAttributeFromCookie(String cookieName, String defaultVal, HttpServletRequest request, Model model) {
		String val = this.getCookieValue(request, cookieName, defaultVal);
		if (val!=null)
			model.addAttribute(cookieName, val);
	}

	public static int Secs_OneMin=60;
	public static int Secs_OneHour=60*60;
	public static int Secs_OneDay=24*60*60;
	public static int Secs_OneMonth=30*Secs_OneDay;
	public static int Secs_OneYear=365*Secs_OneDay;

	/**
	 *
	 * @param resp
	 * @param cookieName
	 * @param value
	 * @param expiry 效期，单位：秒
	 */
	protected Cookie setCookie(HttpServletResponse resp, String cookieName, String value, int expiry) {
		Cookie c;
		String cookKey = getCookieKey(cookieName);
		c = new Cookie(cookKey, encodeCookieVal(value));
		c.setMaxAge(expiry);
		resp.addCookie(c);
		return c;
	}

	protected String getCookieValue(Map<String, String> cookies, String cookieName, String defaultValue) {
		String cookKey = getCookieKey(cookieName);
		String val = cookies.get(cookKey);
		if (Utils.isEmpty(val)) return defaultValue;
		return val;
	}

	protected String getCookieValue(HttpServletRequest req, String cookieName, String defaultValue) {
		Map<String, String> cookies = getCookies(req);
		return getCookieValue(cookies, cookieName, defaultValue);
	}

	protected Map<String, String> getCookies(HttpServletRequest req){
		Map<String, String> map=new HashMap<>();
		Cookie[] cs =req.getCookies();
		if (cs!=null) {
			for(Cookie c : cs) {
				map.put(strVal(c.getName()), decodeCookieVal(c.getValue()));
			}
		}
		return map;
	}

	private static final String COOKIE_KEY_PREFIX="MER_COOKIE_KEY_";
	private String getCookieKey(String name) {
		return EncryptUtil.md5(COOKIE_KEY_PREFIX+name);
	}

	protected String encodeCookieVal(String value) {
		try {
			return URLEncoder.encode(strVal(value),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected String decodeCookieVal(String value) {
		try {
			return URLDecoder.decode(strVal(value), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param mchntCd
	 * @return
	 */
	protected List<MerBanks> getMerBanks(String intTransCd, String mchntCd, String currCd) {
		IMerBanksService svc = this.getService(IMerBanksService.class);
		List<MerBanks>  list = svc.select(intTransCd, mchntCd, currCd);
		return list;
	}

	protected List<MerBanks> arrangeMerBanksByCookie(List<MerBanks> srcBankList, HttpServletRequest req) {
		if (srcBankList==null) return null;
		String cookieName="freqBankNums";
		String freqBankNums = this.getCookieValue(req, cookieName, "");
		if (Utils.isEmpty(freqBankNums))
			return srcBankList;

		List<MerBanks> resList = new ArrayList<>();

		Map<String, Integer> srcIndexes = new HashMap<>();
		for(int i=0; i<srcBankList.size(); i++) {
			MerBanks item = srcBankList.get(i);
			srcIndexes.put(item.getBankNum(), i);
		}

		List<String> freqBankNumsList = splitToList(freqBankNums);
		for(String bn : freqBankNumsList) {
			Integer ndx= srcIndexes.get(bn);
			if (ndx!=null && ndx>=0) {
				MerBanks bank = srcBankList.get(ndx);
				if (bank!=null) {
					resList.add(bank);
					srcBankList.set(ndx, null);
				}
			}
		}

		for(MerBanks item:srcBankList) {
			if (item!=null)
				resList.add(item);
		}
		return resList;
	}

	protected List<BankNums> arrangeBankListByCookie(List<BankNums> srcBankList, HttpServletRequest req) {
		if (srcBankList==null) return null;
		String cookieName="freqBankNums";
		String freqBankNums = this.getCookieValue(req, cookieName, "");
		if (Utils.isEmpty(freqBankNums))
			return srcBankList;

		List<BankNums> resList = new ArrayList<>();

		Map<String, Integer> srcIndexes = new HashMap<>();
		for(int i=0; i<srcBankList.size(); i++) {
			BankNums item = srcBankList.get(i);
			srcIndexes.put(item.getBankNum(), i);
		}

		List<String> freqBankNumsList = splitToList(freqBankNums);
		for(String bn : freqBankNumsList) {
			Integer ndx= srcIndexes.get(bn);
			if (ndx!=null && ndx>=0) {
				BankNums bank = srcBankList.get(ndx);
				if (bank!=null) {
					resList.add(bank);
					srcBankList.set(ndx, null);
				}
			}
		}

		for(BankNums item:srcBankList) {
			if (item!=null)
				resList.add(item);
		}
		return resList;
	}

	protected void addFreqBankNums(String bankNum, HttpServletRequest req, HttpServletResponse resp) {
		if (Utils.isEmpty(bankNum)) return;
		String cookieName="freqBankNums";
		String freqBankNums = this.getCookieValue(req, cookieName, "");
		freqBankNums= addToFavList(freqBankNums, bankNum);

		this.setCookie(resp, cookieName, freqBankNums, 7* Secs_OneDay);
	}

	protected List<String> splitToList(String items){
		return splitToList(items, null);
	}

	protected List<String> splitToList(String items, String splitor){
		if (Utils.isEmpty(splitor))
			splitor = ";";
		List<String> list=new ArrayList<>();
		String[] array= items.split(splitor);
		if (array!=null)
			for(String a:array) {
				a=trim(a);
				if (!Utils.isEmpty(a)) {
					list.add(a);
				}
			}
		return list;
	}

	protected String addToFavList(String items, String item){
		return this.addToFavList(items, null, item, null);
	}
	protected String addToFavList(String items, String splitor, String item, Integer maxItems){
		if (Utils.isEmpty(item)) return items;
		if (maxItems==null)
			maxItems = 10;
		if (Utils.isEmpty(splitor))
			splitor = ";";
		String[] array= items.split(splitor);
		List<String> list=new ArrayList<>();
		list.add(item);
		if (array!=null)
			for(String a:array) {
				a=trim(a);
				if (!Utils.isEmpty(a)) {
					if (!item.equals(a))
						list.add(a);
				}
			}
		StringBuffer buf = new StringBuffer();
		int i=0;
		for(String a: list) {
			if (i<maxItems)
				buf.append(a).append(splitor);
			i++;
		}
		return buf.toString();
	}

	protected String trim(String s) {
		if (s==null) return null;
		return StringUtil.trim(s);
	}

	/**
	 * 获取目前请求的SessionId，如果没有则自动产生一个SessionId
	 * @param request
	 * @return
	 */
	protected String getSessionId(HttpServletRequest request) {
		HttpSession hsession= request.getSession();
		return (hsession==null ? "" : hsession.getId());
	}

	/**
	 * 获取目前请求的SessionId
	 * @param request 请求
	 * @param create 如果没有是否要自动产生一个SessionId
	 * @return
	 */
	protected String getSessionId(HttpServletRequest request, boolean create) {
		HttpSession hsession= request.getSession(create);
		return (hsession==null ? "" : hsession.getId());
	}

	///////////////////////////////////////////////////////////////
	// Google Auth

    /**
     * 获取最后谷歌认证成功的时间
     * @return
     */
    protected long getLastGaTime() {
    	Long lastGaTime = commonBO.getSessionData(SessionKeys.Last_GoogleAuth_Time.getCode());
    	if (lastGaTime==null) return -1L;
    	return lastGaTime;
//    	String strLastGaTime = commonBO.getGlobalSessionData(sessionId, SessionKeys.Last_GoogleAuth_Time.getCode(), "-1");
//		try {
//			long lastGaTime = Long.parseLong(strLastGaTime);
//			return lastGaTime;
//		} catch (NumberFormatException e) {
//			return -1L;
//		}
    }

    /**
     * 判断谷歌认证时间是否过期
     * @param lastGaTime
     * @return
     */
    protected boolean isGaExpired() {
    	long lastGAT = getLastGaTime();
    	return isGaExpired(lastGAT);
    }

    /**
     * 判断谷歌认证时间是否过期
     * @param lastGaTime
     * @return
     */
    protected boolean isGaExpired(long lastGaTime) {
    	long now = System.currentTimeMillis();
    	long delta = now-lastGaTime ;
//    	Log.debug()
//    		.message("[isGaExpired] gaActived=%s; (gaTimeout=%s, now=%s, lastGaTime=%s)", delta/1000, MerParamCache.GA_Timeout/1000, now/1000, lastGaTime/1000)
//    		.submit(); //DEBUG
    	if (lastGaTime<0) return false;
    	return delta > MerParamCache.GA_Timeout;
    }

    /**
     * 重置最后谷歌认证时间为现在
     * @return
     */
    protected Long resetLastGaTime() {
    	Long now = System.currentTimeMillis();
    	//commonBO.putGlobalSessionData(sessionId, SessionKeys.Last_GoogleAuth_Time.getCode(), ""+now, GA_Session_Expire_Time);
    	//Log.debug().message("[resetLastGaTime] *** RESET GA_TIME *** now=%s", now).submit(); //DEBUG
    	commonBO.setSessionData(SessionKeys.Last_GoogleAuth_Time.getCode(), now);
    	return now;
    }

    /**
     * 更新最后谷歌认证时间为现在, 若不存在则不更新，并回传-1
     * @return
     */
    protected Long updateLastGaTime() {
    	Long now = System.currentTimeMillis();
    	Long lastGaTime = getLastGaTime();
    	//Log.debug().message("[updateLastGaTime] *** UPDATE GA_TIME *** lastGaTime=%s, now=%s", lastGaTime, now).submit(); //DEBUG

    	if (lastGaTime<0) return -1L;
    	commonBO.setSessionData(SessionKeys.Last_GoogleAuth_Time.getCode(), now);
    	return now;
    }

    /**
     * 验证谷歌码
     * @param code 待验证的码
     * @param user 用户识别
     * @param otpSecret OTP secret
     * @param resetGaTime 是否重置最后谷歌验证时间, true=重置, false=更新
     * @return
     */
    protected boolean checkGaCode(String code, String user, String otpSecret, boolean resetGaTime) {
    	if (Utils.isEmpty(code)) return false;
    	if (Utils.isEmpty(user)) return false;
    	if (Utils.isEmpty(otpSecret)) return false;
		GoogleAuthenticator ga = new GoogleAuthenticator(user, otpSecret);
		ga.setWindowSize(2);
		boolean r= ga.checkCode(code);
		if (r) { //如果验证成功 更新或重置 最后谷歌验证时间
			if (resetGaTime)
				resetLastGaTime();
			else
				updateLastGaTime();
		}
		return r;
    }
    
//	public String mappingI18n(String name , String msg) {
//		return I18nMsgUtils.getMessageWithDefault(
//				I18nMsgUtils.ICPAY_MER, 
//				commonBO.getUsedLang(),
//				I18nMsgUtils.getKeyCombine(name,msg),msg);
//	}
	
	public String mappingI18n(String name , String msg, String lang) {
		return I18nMsgUtils.getMessageWithDefault(
				I18nMsgUtils.ICPAY_MER, 
				lang,
				I18nMsgUtils.getKeyCombine(name,msg),msg);
	}
	
	/**
	 * Get gateway url by site id
	 * @param siteId
	 * @return
	 */
	protected String getPayGatewayUrl(String siteId) {
		return MerConfigCache.getConfig("payment.gateway.url."+siteId);
	}
	

	/**
	 * Get page return url by site id
	 * @param siteId
	 * @return
	 */
	protected String getPageRetUrl(String siteId) {
		return MerConfigCache.getConfig("payment.pageRet.url."+siteId);
	}
	


}
