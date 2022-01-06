package com.icpay.payment.bm.web.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.icpay.payment.common.utils.EnumUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("enumVal")
public class EnumValDirective implements TemplateDirectiveModel {
	
	private static final String ENUM_BASE_PACKAGE = "com.icpay.payment.common.enums";

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String enumNm = params.get("enumNm").toString();
		String code = params.get("code").toString();
		String showCode = params.get("showCode") != null ? params.get("showCode").toString() : "false";
		String enumPackage = params.get("enumPackage") != null ? params.get("enumPackage").toString() : ENUM_BASE_PACKAGE;
		
		String val = EnumUtil.translate(EnumUtil.findEnum(enumPackage, enumNm), code, Boolean.valueOf(showCode));
		Writer out = env.getOut();
		out.append(val);
	}
}
