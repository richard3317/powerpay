package com.icpay.payment.bm.web.freemarker.directive;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.entity.SessionUserInfo;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("authCheck")
public class AuthCheckDirective implements TemplateDirectiveModel {
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String funcCode = params.get("funcCode").toString();
		HttpServletRequest request = 
			((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		if (session != null) {
			SessionUserInfo sui = (SessionUserInfo) session.getAttribute(BMConstants.SESSION_KEY_USR_INFO);
			if (sui != null && sui.getFuncSet().contains(funcCode)) {
				body.render(env.getOut());
			}
		}
	}
}
