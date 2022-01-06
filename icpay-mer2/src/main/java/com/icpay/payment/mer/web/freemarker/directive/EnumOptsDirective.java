package com.icpay.payment.mer.web.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.mer.bo.CommonBO;
import com.icpay.payment.mer.util.I18nMsgUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("enumOpts")
public class EnumOptsDirective implements TemplateDirectiveModel {
	
	private static final String ENUM_BASE_PACKAGE = "com.icpay.payment.common.enums";
	@Autowired
    private HttpServletRequest request;
	@Autowired
	protected CommonBO commonBO;

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String enumNm = params.get("enumNm").toString();
		String selected = params.get("selected") != null ? params.get("selected").toString() : "";
		String showCode = params.get("showCode") != null ? params.get("showCode").toString() : "false";
		String showEmptyOpt = params.get("showEmptyOpt") != null ? params.get("showEmptyOpt").toString() : "false";
		String enumPackage = params.get("enumPackage") != null ? params.get("enumPackage").toString() : ENUM_BASE_PACKAGE;
		
		StringBuilder opts = new StringBuilder();
		if (Boolean.valueOf(showEmptyOpt)) {
			
			
			
			String value = I18nMsgUtils.getMessageWithDefault(
					I18nMsgUtils.ICPAY_MER, 
					I18nMsgUtils.getLanguage(request),
					I18nMsgUtils.getKeyCombine( this.getClass().getSimpleName(),"---请选择---"),"---请选择---");
			opts.append("<option value=''"+value+"</option>");
		}
		Map<String, String> m = EnumUtil.enumMap(EnumUtil.findEnum(enumPackage, enumNm), Boolean.valueOf(showCode));
		for (String k : m.keySet()) {
			String value = 
			I18nMsgUtils.getMessageWithDefault(
					I18nMsgUtils.ICPAY_MER, 
					I18nMsgUtils.getLanguage(request),
					I18nMsgUtils.getKeyCombine( this.getClass().getSimpleName(), m.get(k)), m.get(k));
			if (k.equals(selected)) {
				opts.append("<option value='" + k + "' selected='selected'>" + value + "</option>");
			} else {
				opts.append("<option value='" + k + "'>" + value + "</option>");
			}
		}
		Writer out = env.getOut();
		out.append(opts.toString());
		if (body != null) {
			body.render(out);
		}
	}
}
