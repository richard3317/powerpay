package com.icpay.payment.mer.web.interceptor;

import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.mer.bo.CommonBO;
import com.icpay.payment.mer.util.I18nMsgUtils;

public class ExceptionHandler extends SimpleMappingExceptionResolver {
	
	private static final Logger logger = Logger.getLogger(ExceptionHandler.class);
	
	@Autowired
	protected CommonBO commonBO;
	
	private static String DFT_AJAX_ERR_MSG = "{\"respCode\":\"ZZ\",\"respMsg\":\"系统异常，请联系管理员\"}";
	private static String AJAX_ERR_MSG_TMPL = "{\"respCode\":\"ZZ\",\"respMsg\":\"{errMsg}\"}";

	@Override
	public ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
            String classNm = handlerMethod.getBean().getClass().getName();
            String methodNm = method.getName();
            logger.error("handler error: " + classNm + "@" + methodNm, ex);
            if (method.getReturnType().equals(AjaxResult.class)) {
            	response.setContentType("text/html;charset=UTF-8");
            	try {
            		PrintWriter writer = response.getWriter();
            		String errMsg = null;
    	        	if (ex instanceof BizzException) {
    	        		errMsg = AJAX_ERR_MSG_TMPL.replace("{errMsg}", ex.getMessage());
    	        		BizzException bizEx =(BizzException) ex;
    	    			logger.error(StringUtils.replace("print exception info failed!!! %s", "%s", bizEx.getMessage()));
    	        	} else {
    	        		errMsg = DFT_AJAX_ERR_MSG;
    	        	}
    	        	writer.write(errMsg);
    	        	writer.flush();
    	        	return null;
            	} catch (Exception e) {
            		logger.error("print exception info failed!!!", e);
            	}
            }
		}
		if (ex instanceof BizzException) {
			BizzException bizEx =(BizzException) ex;
			logger.error(StringUtils.replace("print exception info failed!!! %s", "%s", bizEx.getMessage()));
			request.setAttribute("errorMsg", bizEx.getMessage());
		} else {
			String value = I18nMsgUtils.getMessageWithDefault(
					I18nMsgUtils.ICPAY_MER, 
					I18nMsgUtils.getLanguage(request),
					I18nMsgUtils.getKeyCombine( this.getClass().getSimpleName(),"系统异常，请联系管理员"),"系统异常，请联系管理员");
			request.setAttribute("errorMsg", value);
		}
		return new ModelAndView("common/error");
	}
}