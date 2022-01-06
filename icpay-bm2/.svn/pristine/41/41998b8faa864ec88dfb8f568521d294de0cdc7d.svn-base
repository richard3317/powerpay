package com.icpay.payment.bm.web.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("enumCheckBox")
public class EnumCheckBoxDirective implements TemplateDirectiveModel {
	
	private static final String ENUM_BASE_PACKAGE = "com.icpay.payment.common.enums";

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String enumNm = params.get("enumNm").toString();
		String name = params.get("name").toString();
		String readOnly = params.get("readOnly") != null ? params.get("readOnly").toString() : "false";
		String checkedVals = params.get("checkVals") == null ? "" : params.get("checkVals").toString();
		char[] checkedIdx = null;
		if (!StringUtil.isBlank(checkedVals)) {
			checkedIdx = checkedVals.toCharArray();
		}
		String enumPackage = params.get("enumPackage") != null ? 
				params.get("enumPackage").toString() : ENUM_BASE_PACKAGE;
		
		Class clazz = EnumUtil.findEnum(enumPackage, enumNm);
		Enum[] enums = (Enum[]) clazz.getEnumConstants();
		StringBuilder sb = new StringBuilder("<ul class='enum_chkbox_group'>");
		try {
			for (int i = 0; i < enums.length; i ++) {
				Enum e = enums[i];
				Method m1 = e.getClass().getMethod("getCode", new Class[]{});
				sb.append("<li><input type='checkbox' class='chkbox_inpt' name='" + name + "' value='" + (String) m1.invoke(e, new Object[]{}) + "'");
				if (checkedIdx != null && checkedIdx.length > i && checkedIdx[i] == '1') {
					sb.append("checked='checked'");
				}
				if (Boolean.valueOf(readOnly) == true) {
					sb.append("disabled='disabled'");
				}
				Method m2 = e.getClass().getMethod("getDesc", new Class[]{});
				sb.append(" />").append("<span class='chk_box_lable'>" + (String) m2.invoke(e, new Object[] {}) + "</span></li>");
			}
			
			Writer out = env.getOut();
			out.append(sb.toString());
		} catch (Exception e) {
			throw new BizzException("checkbox display error!!!", e);
		}
		
	}
}
