package com.icpay.payment.mer.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.service.I18nMessageService;
import com.icpay.payment.mer.bo.CommonBO;
import com.icpay.payment.mer.session.LocalSession;
import com.icpay.payment.mer.session.LocalSessionManager;
import com.icpay.payment.mer.session.SessionKeys;




public class I18nMsgUtils {
	
	public static String DEFAULT_LANG = "zh_CN";

	public static String ICPAY_MER = "icpay_mer";
	
//	public static String LANG_KEY = "lan";
	
	protected static I18nMessageService svc() {
		return DBHessionServiceClient.getService(I18nMessageService.class);
	}

	private static String getDefaultLang() {
		return DEFAULT_LANG;
	}
	private static String getDefaultCategory() {
		return ICPAY_MER;
	}

	public static String getMessage(String cat, String lang, String key, String... args) {
		if (Utils.isEmpty(lang))
			lang = getDefaultLang();
		String msg = svc().getMessage(cat, lang, key);
		return formatMsg(msg, args);
	}

	public static String getMessageWithDefault(String cat, String lang, String key, String defaultMsg, String... args) {
		if (Utils.isEmpty(lang))
			lang = getDefaultLang();
		if (Utils.isEmpty(cat))
			lang = getDefaultCategory();
		I18nMessageService db = DBHessionServiceClient.getService(I18nMessageService.class);
		String msg = db.getMessageWithDefault(cat, lang, key, defaultMsg);
		return formatMsg(msg, args);
	}
	
	
	protected static String formatMsg(String fmt, String... args) {
		if (Utils.isEmpty(fmt)) return "";
		if (fmt.contains("%s")) return fmt;
		return String.format(fmt, args);
	}
	//抓COOKIE语言，抓不到就预设
	public static String getLanguage(HttpServletRequest req) {
		String language =getSessionLang(req);
		if(StringUtils.isBlank(language)) {
			language = getCookieLang(req);
		}
		
		if (StringUtils.isBlank(language)) {
			language = getDefaultLang();
		}
		return language;
	}

	public static void updateUsedLanguage(HttpServletRequest req, HttpServletResponse resp,String i18n) {
		updateSessionLang(req, i18n);
		updateCookieLang(resp, i18n);
	}
	
	public static String getKeyCombine(String className ,String keyValue ) {
		
		return new StringBuffer().append(className).append(".").append(keyValue).toString();
	}
	
	protected static String getCookieLang(HttpServletRequest request) {
		String language = null;
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals(SessionKeys.USED_LANG.getCode())) {
					language = cookie.getValue();
					break;
				}
			}
		}
		if(StringUtil.isNotBlank(language)) {
			String[] lang = language.split("_");
			language = lang[0]+ "_"+ lang[1].toUpperCase();
		}
		return language;
	}

	protected static String getSessionLang(HttpServletRequest request) {
		String language = null;
		HttpSession session = request.getSession();
		if (session != null && null != session.getAttribute(SessionKeys.USED_LANG.getCode())) {
			LocalSession ls = LocalSessionManager.getSession(session.getId());
			if(ls!= null && null != ls.getSessionData(SessionKeys.USED_LANG.getCode())) {
				language = ls.getSessionData(SessionKeys.USED_LANG.getCode()).toString();
			}
		}
		return language;
	}
	
	protected static void updateSessionLang(HttpServletRequest request, String value) {
		HttpSession session = request.getSession();
		
		if (session != null ) {
			if(StringUtils.isNoneBlank(value)) {
				value = value.split("_")[0].toLowerCase() +"_"+value.split("_")[1].toUpperCase();
			}
			session.setAttribute(SessionKeys.USED_LANG.getCode(), value);
			LocalSession ls = LocalSessionManager.getSession(session.getId());
			if(ls == null) {
				ls = LocalSessionManager.newSession(session.getId());
			}
			if(ls != null) {
				ls.putSessionData(SessionKeys.USED_LANG.getCode(), value);
			}
		}
	}
	
	protected static void updateCookieLang(HttpServletResponse resp, String value) {
		Cookie userNameCookieRemove = new Cookie(SessionKeys.USED_LANG.getCode(), "");
		userNameCookieRemove.setMaxAge(0);
		userNameCookieRemove.setPath("/");
		resp.addCookie(userNameCookieRemove);
		//要设置path，避免COOKIE重复新增
		Cookie lanCookie = new Cookie(SessionKeys.USED_LANG.getCode(), value);
		lanCookie.setPath("/");
		resp.addCookie(lanCookie);
	
	}
}
