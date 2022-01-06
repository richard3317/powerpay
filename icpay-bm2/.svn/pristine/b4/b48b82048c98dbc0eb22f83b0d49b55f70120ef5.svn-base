package com.icpay.payment.bm.web.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.Utils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;


@Component("enumOpts")
public class EnumOptsDirective implements TemplateDirectiveModel {
	
	private static final String ENUM_BASE_PACKAGE = "com.icpay.payment.common.enums";
	private static final String FALSE = "false";
	private static final String TRUE = "true";

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String enumNm = strVal(params.get("enumNm"),""); //params.get("enumNm").toString();
		String selected = strVal(params.get("selected"),""); //params.get("selected") != null ? params.get("selected").toString() : "";
		String showCode = strVal(params.get("showCode"),FALSE);  //params.get("showCode") != null ? params.get("showCode").toString() : "false";
		String showEmptyOpt = strVal(params.get("showEmptyOpt"),FALSE); //params.get("showEmptyOpt") != null ? params.get("showEmptyOpt").toString() : "false";
		String enumPackage = strVal(params.get("enumPackage"),ENUM_BASE_PACKAGE); //params.get("enumPackage") != null ? params.get("enumPackage").toString() : ENUM_BASE_PACKAGE;
		
		String emptyOptDesc = strVal(params.get("emptyOptDesc"),"---请选择---");
		String emptyOptValue = strVal(params.get("emptyOptValue"),"");
		//String exceptValues = strVal(params.get("exceptValues"),"");
		String[] exceptValues = strVal(params.get("exceptValues"),"").split(",");
		
		StringBuilder opts = new StringBuilder();
		if (Boolean.valueOf(showEmptyOpt)) {
			opts.append("<option value='"+emptyOptValue+"'>"+emptyOptDesc+"</option>");
		}
		Map<String, String> m = EnumUtil.enumMap(EnumUtil.findEnum(enumPackage, enumNm), Boolean.valueOf(showCode));
		for (String k : m.keySet()) {
			if (! Utils.isInSet(k, exceptValues)) {
				if (k.equals(selected)) {
					opts.append("<option value='" + k + "' selected='selected'>" + m.get(k) + "</option>");
				} else {
					opts.append("<option value='" + k + "'>" + m.get(k) + "</option>");
				}
			}
		}
		Writer out = env.getOut();
		out.append(opts.toString());
		if (body != null) {
			body.render(out);
		}
	}
	
	String strVal(Object val, String defaultVal) {
		if (Utils.isEmpty(val)) return defaultVal;
		return val.toString();
	}
}
