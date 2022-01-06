package com.icpay.payment.bm.web.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.common.utils.StringUtil;

public class SessionCheckFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(SessionCheckFilter.class);
	
	private static final String EXCLUDE_CHECK_HREFS = "excludeHrefs";	
	private FilterConfig filterConfig;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		boolean ajaxRequest = false;
		try {		
			String path = req.getServletPath();
			logger.debug("访问路径: " + path);
			ajaxRequest = req.getHeader("X-Requested-With") != null ? true : false;
			
			String excludeCheckHrefs = filterConfig.getInitParameter(EXCLUDE_CHECK_HREFS);
			String[] excludeHrefs = (excludeCheckHrefs == null ? "" : excludeCheckHrefs).split("[,]");
			HttpSession session = req.getSession(false);
			if (StringUtil.matchRegexs(path, excludeHrefs)) {			
				logger.debug(path +": 不需要检查session");
				chain.doFilter(req, res);
			} else {				
				
				// 如果session为null,或者session里面用户信息为null，则说明会话已失效			
				if (null == session || session.getAttribute(BMConstants.SESSION_KEY_USR_INFO) == null) {
					if (ajaxRequest) {
						response.setContentType("text/html;charset=UTF-8");
						PrintWriter writer = response.getWriter();
			        	writer.write("expired");
			        	writer.flush();
					} else {
						RequestDispatcher rd = req.getRequestDispatcher(BMConstants.PAGE_EXPIRED);
			        	rd.forward(req, res);
					}
				} else {				
					session.setAttribute(BMConstants.SESSION_KEY_CURRENT_FUNC_HREF, path);		//记录请求的链接路径
					chain.doFilter(req, res);						 		
				}
			}
		} catch (Exception e) {
			logger.error(req.getServletPath() + "异常！", e);
		}
	}

	@Override
	public void destroy() { }
	 
}
