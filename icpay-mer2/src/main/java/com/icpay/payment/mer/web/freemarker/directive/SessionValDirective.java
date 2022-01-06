package com.icpay.payment.mer.web.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.mer.bo.CommonBO;
import com.icpay.payment.mer.session.SessionKeys;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("sessionVal")
public class SessionValDirective implements TemplateDirectiveModel {

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String key = params.get("key").toString();
		
		CommonBO commonBO = ApplicationContextUtil.getBean("commonBO");
		
		String val = "";
		MchntInfo m = commonBO.getSessionData(SessionKeys.MCHNT_INFO.getCode());
		
		MchntUserInfo muser = commonBO.getSessionData(SessionKeys.MCHNT_USER_INFO.getCode());
		
//		AssertUtil.objIsNull(m, "会话已失效");
//		if ("mchntCd".equals(key)) {
//			val = m.getMchntCd();
//		} else if ("mchntNm".equals(key)) {
//			val = m.getMchntCnNm();
//		}
		
		if (m!=null) {
			if ("mchntCd".equals(key)) {
				val = m.getMchntCd();
			} else if ("mchntNm".equals(key)) {
				val = m.getMchntCnNm();
			}	
		}
		if(muser != null) {
			if("userId".equals(key))
				val = muser.getUserId();
		}
		
		Writer out = env.getOut();
		out.append(val);
	}
}
