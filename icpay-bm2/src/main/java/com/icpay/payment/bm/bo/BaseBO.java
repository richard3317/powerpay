package com.icpay.payment.bm.bo;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_GLOBAL_SESSION;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.entity.SessionUserInfo;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.db.client.DBHessionServiceClient;

public class BaseBO {
	
	public void removeRequestAttr(String key, Object value) {
		this.removeAttr(key, SCOPE_REQUEST);
	}
	
	public void removeSessionAttr(String key) {
		this.removeAttr(key, SCOPE_SESSION);
	}
	
	public void removeGlobalAttr(String key, Object value) {
		this.removeAttr(key, SCOPE_GLOBAL_SESSION);
	}

	public void setRequestAttr(String key, Object value) {
		this.setAttr(key, value, SCOPE_REQUEST);
	}
	
	public void setSessionAttr(String key, Object value) {
		this.setAttr(key, value, SCOPE_SESSION);
	}
	
	public void setGlobalAttr(String key, Object value) {
		this.setAttr(key, value, SCOPE_GLOBAL_SESSION);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getRequestAttr(String key) {
		Object o = this.getAttr(key, SCOPE_REQUEST);
		return o == null ? null : (T) o;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getSessionAttr(String key) {
		Object o = this.getAttr(key, SCOPE_SESSION);
		return o == null ? null : (T) o;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getGlobalAttr(String key) {
		Object o = this.getAttr(key, SCOPE_GLOBAL_SESSION);
		return o == null ? null : (T) o;
	}
	
	/**
	 * 获取当前登录用户
	 * @return
	 */
	public SessionUserInfo getSessionUser() {
		return this.getSessionAttr(BMConstants.SESSION_KEY_USR_INFO);
	}
	
	/**
	 * 回传目前用户是否有指定功能的权限
	 * @param funcCd 功能代码
	 * @return 是否有该功能的权限
	 */
	public boolean hasFuncRight(String funcCd) {
		//commonBO.setSessionAttr(BMConstants.SESSION_KEY_USR_INFO, sessionUserInfo);
		SessionUserInfo uinfo=this.getSessionUser();
		if (uinfo==null) return false;
		if (uinfo.getFuncSet()==null) return false;
		return uinfo.getFuncSet().contains(funcCd);
	}
	
	
	private Object getAttr(String key, int scope) {
		HttpServletRequest request = 
			((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		if (request==null) return null;
		switch (scope) {
			case SCOPE_REQUEST:
				return request.getAttribute(key);
			case SCOPE_SESSION:
				if (request.getSession()==null) return null;
				return request.getSession().getAttribute(key);
			case SCOPE_GLOBAL_SESSION:
				if (request.getSession()==null) return null;
				if (request.getSession().getServletContext()==null) return null;
				return request.getSession().getServletContext().getAttribute(key);
			default:
				throw new BizzException("scope is incorrect.");
		}
	}
	
	private void setAttr(String key, Object value, int scope) {
		HttpServletRequest request = 
			((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		switch (scope) {
			case SCOPE_REQUEST:
				request.setAttribute(key, value);
				break;
			case SCOPE_SESSION:
				request.getSession().setAttribute(key, value);
				break;
			case SCOPE_GLOBAL_SESSION:
				request.getSession().getServletContext().setAttribute(key, value);
				break;
			default:
				throw new BizzException("scope is incorrect.");
		}
	}
	
	private void removeAttr(String key, int scope) {
		HttpServletRequest request = 
			((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		switch (scope) {
			case SCOPE_REQUEST:
				request.removeAttribute(key);
				break;
			case SCOPE_SESSION:
				request.getSession().removeAttribute(key);
				break;
			case SCOPE_GLOBAL_SESSION:
				request.getSession().getServletContext().removeAttribute(key);
				break;
			default:
				throw new BizzException("scope is incorrect.");
		}
	}
	
	protected <T> T getService(Class<T> clazz) {
		return DBHessionServiceClient.getService(clazz);
	}
}
