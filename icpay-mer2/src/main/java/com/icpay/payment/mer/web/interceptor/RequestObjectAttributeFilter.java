package com.icpay.payment.mer.web.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.icpay.payment.common.utils.JsonUtils;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.SiteInfo;
import com.icpay.payment.mer.cache.SiteInfoCache;

/**
 * 可在此运算并准备值放入 Request Attrs: 例如 site_id
 */
public class RequestObjectAttributeFilter implements Filter {
	
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		//request.setAttribute("_request", request);
		String hostName = request.getServerName();
		request.setAttribute("_hostName", hostName);
		
		SiteInfo siteInfo = SiteInfoCache.getSiteByDomain(hostName);
		String siteId = null;
		
		if (siteInfo!=null) {
			siteId = siteInfo.getSiteId();
			request.setAttribute("_siteId", siteId);
			request.setAttribute("_siteName", siteInfo.getSiteName());
		}
		
		if (!Utils.isEmpty(siteId)) {
			request.setAttribute("headerId", SiteInfoCache.getMerParam(siteId, "headerId", "${headerId}"));
			request.setAttribute("hpaneltype", SiteInfoCache.getMerParam(siteId, "hpaneltype", "${hpaneltype}"));
			request.setAttribute("titleColor", SiteInfoCache.getMerParam(siteId, "titleColor", "${titleColor}"));
			request.setAttribute("btnClass", SiteInfoCache.getMerParam(siteId, "btnClass", "${btnClass}"));
			request.setAttribute("login_flt_logo_name", SiteInfoCache.getMerParam(siteId, "login_flt_logo_name", "${login_flt_logo_name}"));
			request.setAttribute("login_flt_banner01_name", SiteInfoCache.getMerParam(siteId, "login_flt_banner01_name", "${login_flt_banner01_name}"));
			request.setAttribute("login_flt_banner02_name", SiteInfoCache.getMerParam(siteId, "login_flt_banner02_name", "${login_flt_banner02_name}"));
			request.setAttribute("login_flt_banner03_name", SiteInfoCache.getMerParam(siteId, "login_flt_banner03_name", "${login_flt_banner03_name}"));
			request.setAttribute("login_flt_banner04_name", SiteInfoCache.getMerParam(siteId, "login_flt_banner04_name", "${login_flt_banner04_name}"));
			request.setAttribute("loginTitle", SiteInfoCache.getMerParam(siteId, "loginTitle", "${loginTitle}"));
			request.setAttribute("navBar_style", SiteInfoCache.getMerParam(siteId, "navBar_style", "${navBar_style}"));
			request.setAttribute("logo_flag", SiteInfoCache.getMerParam(siteId, "logo_flag", "${logo_flag}"));
			request.setAttribute("footer", SiteInfoCache.getMerParam(siteId, "footer", "${footer}"));
			request.setAttribute("merTitle", SiteInfoCache.getMerParam(siteId, "merTitle", "${merTitle}"));
			request.setAttribute("footPic_style", SiteInfoCache.getMerParam(siteId, "footPic_style", "${footPic_style}"));
			request.setAttribute("footPic1", SiteInfoCache.getMerParam(siteId, "footPic1", "${footPic1}"));
			request.setAttribute("footPic2", SiteInfoCache.getMerParam(siteId, "footPic2", "${footPic2}"));
			request.setAttribute("footPic3", SiteInfoCache.getMerParam(siteId, "footPic3", "${footPic3}"));
			request.setAttribute("footPic4", SiteInfoCache.getMerParam(siteId, "footPic4", "${footPic4}"));
			request.setAttribute("footTotal1", SiteInfoCache.getMerParam(siteId, "footTotal1", "${footTotal1}"));
			request.setAttribute("footTotal2", SiteInfoCache.getMerParam(siteId, "footTotal2", "${footTotal2}"));
			request.setAttribute("footTotal3", SiteInfoCache.getMerParam(siteId, "footTotal3", "${footTotal3}"));
			request.setAttribute("footTotal4", SiteInfoCache.getMerParam(siteId, "footTotal4", "${footTotal4}"));
			request.setAttribute("footId", SiteInfoCache.getMerParam(siteId, "footId", "${footId}"));
			request.setAttribute("footcertsite", SiteInfoCache.getMerParam(siteId, "footcertsite", "${footcertsite}"));			
		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
	

	/**
     * Default constructor. 
     */
    public RequestObjectAttributeFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
