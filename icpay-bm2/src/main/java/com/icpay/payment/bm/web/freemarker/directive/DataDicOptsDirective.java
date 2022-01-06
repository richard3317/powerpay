package com.icpay.payment.bm.web.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.icpay.payment.bm.cache.DataDicCache;
import com.icpay.payment.db.dao.mybatis.model.DataDic;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("dataDicOpts")
public class DataDicOptsDirective implements TemplateDirectiveModel {

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String dataTp = params.get("dataTp").toString();
		String selected = params.get("selected") != null ? params.get("selected").toString() : "";
		String showKey = params.get("showKey") != null ? params.get("showKey").toString() : "false";
		String showEmptyOpt = params.get("showEmptyOpt") != null ? params.get("showEmptyOpt").toString() : "false";
		
		StringBuilder opts = new StringBuilder();
		if (Boolean.valueOf(showEmptyOpt)) {
			opts.append("<option value=''>---请选择---</option>");
		}
		List<DataDic> ddLst = DataDicCache.getDataDicLst(dataTp);
		for (DataDic dd : ddLst) {
			String txt = null;
			if (Boolean.valueOf(showKey)) {
				txt = dd.getDataKey() + "-" + dd.getDataVal();
			} else {
				txt = dd.getDataVal();
			}
			if (dd.getDataKey().equals(selected)) {
				opts.append("<option value='" + dd.getDataKey() + "' selected='selected'>" + txt + "</option>");
			} else {
				opts.append("<option value='" + dd.getDataKey() + "'>" + txt + "</option>");
			}
		}
		Writer out = env.getOut();
		out.append(opts.toString());
		if (body != null) {
			body.render(out);
		}
	}
}
