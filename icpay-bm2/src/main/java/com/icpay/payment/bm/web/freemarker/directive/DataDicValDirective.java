package com.icpay.payment.bm.web.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.icpay.payment.bm.cache.DataDicCache;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("dataDicVal")
public class DataDicValDirective implements TemplateDirectiveModel {

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String dataTp = params.get("dataTp").toString();
		String dataKey = params.get("dataKey").toString();
		String includeKey = params.get("includeKey") != null ? params.get("includeKey").toString() : "false";
		
		String val = null;
		if (Boolean.valueOf(includeKey)) {
			val = DataDicCache.translate(dataTp, dataKey);
		} else {
			val = DataDicCache.getDataDicVal(dataTp, dataKey);
		}
		
		Writer out = env.getOut();
		out.append(val);
	}

}
