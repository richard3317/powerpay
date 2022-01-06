package com.icpay.payment.bm.web.filter;

import java.io.IOException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.icpay.payment.bm.cache.AuthDataCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.entity.SessionUserInfo;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.BmFuncInfo;

public class AuthCheckFilter implements Filter {
	
	private static final Logger logger = Logger.getLogger(AuthCheckFilter.class);
	
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

		try {		
			String path = req.getServletPath();
			String excludeCheckHrefs = filterConfig.getInitParameter(EXCLUDE_CHECK_HREFS);
			String[] excludeHrefs = (excludeCheckHrefs == null ? "" : excludeCheckHrefs).split("[,]");
			if (StringUtil.matchRegexs(path, excludeHrefs) || path.contains("/resources/")) {
				if (logger.isDebugEnabled()) {
					logger.debug(path +": 不需要检查权限");
				}
				chain.doFilter(req, res);
			} else {
				SessionUserInfo sui = (SessionUserInfo) req.getSession().getAttribute(BMConstants.SESSION_KEY_USR_INFO);
				Set<String> funcSet = sui.getFuncSet();
				for (String funcCd : funcSet) {
					BmFuncInfo func = AuthDataCache.getInstance().getFuncInfo(funcCd);
					if (func.getFuncHref().equals(path)) {
						if (logger.isDebugEnabled()) {
							logger.debug(path +": 权限校验通过");
						}
						chain.doFilter(request, response);
						return;
					}
				}
				RequestDispatcher rd = req.getRequestDispatcher(BMConstants.PAGE_OPFAIL);
	        	rd.forward(req, res);
			}
		} catch (Exception e) {
			logger.error(req.getServletPath() + "异常！", e);
		}
	}

	@Override
	public void destroy() { }
}
