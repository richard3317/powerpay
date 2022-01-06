package com.icpay.payment.bm.web.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.common.utils.StringUtil;

/**
 * 参数列表拦截器，过滤 {@link com.icpay.payment.bm.web.interceptor.QryMethod QryMethod} 方法，自动将请求参数加入查询参数列表。
 */
public class ParamMapInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = Logger.getLogger(ParamMapInterceptor.class);

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            QryMethod annt = method.getAnnotation(QryMethod.class);
            HttpSession session = request.getSession();
            if (annt != null) {
            	String controllerName = handlerMethod.getBean().getClass().getName();
            	String qryParamMapKey = controllerName + BMConstants.QRY_PARAM_MAP_KEY;
            	Map<String, String> qryParamMap = (Map<String, String>) session.getAttribute(qryParamMapKey);
            	if (qryParamMap == null) {
					qryParamMap = new HashMap<String, String>();
					session.setAttribute(qryParamMapKey, qryParamMap);
				}
            	Map params = request.getParameterMap();
            	for (Object key : params.keySet()) {
            		String k = (String) key;
        			String[] v = (String[]) params.get(k);
        			// 将查询参数放入当前Action在session中对应的查询Map
        			if (k.startsWith(BMConstants.QRY_PARAM_KEY_PREFFIX)) {
        				if (v != null && v.length == 1) {
        					qryParamMap.put(k.substring(BMConstants.QRY_PARAM_KEY_PREFFIX_LENGTH), StringUtil.trim(v[0]));
        				}
        			}
        			if (BMConstants.REQ_PARAM_NM_PAGENUM.equals(k) 
        					|| BMConstants.REQ_PARAM_NM_PAGESIZE.equals(k)) {
        				qryParamMap.put(k, StringUtil.trim(v[0]));
        			}
        		}
        		
        		// 在调试模式下，打印出各类参数MAP的信息
        		if (logger.isDebugEnabled()) {
        			logger.debug("qryParamMap-->" + qryParamMap);
        		}
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
	}
	
}
