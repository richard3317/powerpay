package com.icpay.payment.bm.web.interceptor;

import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.exception.BizzException;

public class ExceptionHandler extends SimpleMappingExceptionResolver {
	
	private static final Logger logger = Logger.getLogger(ExceptionHandler.class);
	
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
			request.setAttribute("errorMsg", ex.getMessage());
		} else {
			request.setAttribute("errorMsg", "系统异常，请联系管理员");
		}
		return new ModelAndView("common/error");
	}
}