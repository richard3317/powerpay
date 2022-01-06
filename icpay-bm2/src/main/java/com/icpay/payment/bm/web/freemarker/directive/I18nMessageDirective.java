package com.icpay.payment.bm.web.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.icpay.payment.common.utils.StringUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 页面内容国际化自定义标签
 * @author lijin
 *
 */
@Component("i18nMsg")
public class I18nMessageDirective implements TemplateDirectiveModel {
	
	private static Map<String, Locale> LOCALE_MAP = new ConcurrentHashMap<String, Locale>();
	
	static {
		LOCALE_MAP.put("zh_CN", Locale.SIMPLIFIED_CHINESE);
		LOCALE_MAP.put("zh_TW", Locale.TRADITIONAL_CHINESE);
		LOCALE_MAP.put("en_US", Locale.US);
	}

	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String name = params.get("name").toString();
		String localeNm = params.get("locale") == null ? null : params.get("locale").toString();
		Locale local = Locale.SIMPLIFIED_CHINESE;
		if (StringUtil.isBlank(localeNm) || LOCALE_MAP.get(localeNm) == null) {
			HttpServletRequest request = 
				((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			local = request.getLocale();
		} else {
			local = LOCALE_MAP.get(localeNm);
		}
		String msg = messageSource.getMessage(name, null, local);
		Writer out = env.getOut();
		out.append(StringUtil.isBlank(msg) ? "" : msg);
	}
}
