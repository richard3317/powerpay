package com.icpay.payment.mer.web.freemarker.directive;

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
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.mer.session.SessionKeys;
import com.icpay.payment.mer.util.I18nMsgUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

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
	@Autowired
    private HttpServletRequest request;
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String cat = null;
		String lang = StringUtil.isNotBlank(request.getParameter(SessionKeys.USED_LANG.getCode()))? 
				request.getParameter(SessionKeys.USED_LANG.getCode()) :I18nMsgUtils.getLanguage(request);
		String[] argsAry = null;

		if (!Utils.isEmpty(params.get("cat"))) {

			cat = params.get("cat").toString();
		} else {
			Object catVar = env.getVariable("cat");
			if ((catVar != null) && (catVar != TemplateModel.NOTHING)) {
				cat = "" + catVar;
			}
		}
		if (Utils.isEmpty(params.get("lang"))) {
			Object langVar = env.getVariable("lang");
			if ((langVar != null) && (langVar != TemplateModel.NOTHING)) {
				lang = "" + langVar;
			}
		}

		String code = params.get("code").toString();
		String defaultMsg = params.get("default").toString();

		if (!Utils.isEmpty(params.get("args"))) {
			String args = params.get("args").toString();
				if (!Utils.isEmpty(args)) {
				argsAry = args.split(";");
				}
		}

		String msg = I18nMsgUtils.getMessageWithDefault(cat, lang, code, defaultMsg);
		Writer out = env.getOut();
		out.append(StringUtil.isBlank(msg) ? "" : msg);
	}
}
