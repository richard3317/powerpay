package com.icpay.payment.mer.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.enums.AjaxRespEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfo;
import com.icpay.payment.mer.bo.CommonBO;
import com.icpay.payment.mer.bo.MchntAccountBO;
import com.icpay.payment.mer.bo.MchntBO;
import com.icpay.payment.mer.bo.MerParamsBo;
import com.icpay.payment.mer.constants.MerConstants.LoginState;
import com.icpay.payment.mer.session.LocalSession;
import com.icpay.payment.mer.session.LocalSessionManager;
import com.icpay.payment.mer.session.SessionKeys;
import com.icpay.payment.mer.util.I18nMsgUtils;
import com.icpay.payment.mer.util.MerUtils;
import com.icpay.payment.mer.web.interceptor.IgnoreSessionCheck;

@Controller
public class IndexController extends BaseController {

	@Autowired
	private MchntAccountBO mchntAccountBO;
	@Autowired
	private MchntBO mchntBO;
	@Autowired
	private MerParamsBo merParamsBo;
	
	@RequestMapping(value="/index" , method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) throws InterruptedException {
		//增加Google验证码验证登陆
//		reciprocal(request);
//		MchntUserInfo muser = commonBO.getSessionData(SessionKeys.MCHNT_USER_INFO.getCode());
		MchntUserInfo muser = commonBO.getMchntUser();
		if(CommonEnums.MchntSt._0.getCode().equals(muser.getUserState()))
			return "chngPwd";
		else
			buildIndexData(model,muser.getUserId());
			return "index";								
	}
	
	@RequestMapping(value="/updateI18n" , method = RequestMethod.POST)
	@IgnoreSessionCheck
	public @ResponseBody
    AjaxResult updateI18n(Model model,String i18n, HttpServletRequest request, HttpServletResponse resp) {
		
		String lan = request.getParameter(SessionKeys.USED_LANG.getCode());
		if(lan.contains("-")) {
			lan = lan.replace("-","_");
		}
		I18nMsgUtils.updateUsedLanguage(request, resp, lan);
				
		AjaxResult result = new AjaxResult();
		result.setRespCode(AjaxRespEnums.SUCC.getRespCode());
		String usedLan = StringUtil.isNotBlank(lan)?lan:I18nMsgUtils.getLanguage(request);
		result.addResultData(SessionKeys.USED_LANG.getCode(), usedLan);
		
		return result;
	}
	
	@RequestMapping(value="/langList" , method = RequestMethod.GET)
	@IgnoreSessionCheck
	public @ResponseBody
    AjaxResult getLangList(Model model,String i18n, HttpServletRequest request, HttpServletResponse resp) {	
		AjaxResult result = new AjaxResult();
		result.setRespCode(AjaxRespEnums.SUCC.getRespCode());
		result.addResultData("langList", merParamsBo.getSysParams());
		return result;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@IgnoreSessionCheck
	public String base(Model model, HttpServletRequest request, HttpServletResponse resp) {
		String lan = request.getParameter("lan");
		if(StringUtil.isBlank(lan)) {
			lan =I18nMsgUtils.getLanguage(request);
		}
		
		addAttributeFromCookie("adminCheckBox", "", request, model);
		//URL变更语系会从此进入，须变更COOKIE相关才能吃到设定
		I18nMsgUtils.updateUsedLanguage(request, resp, lan);

		//if (commonBO.getLoginMer() != null) {
		if (commonBO.isLogined(""+MerUtils.getRemoteIp(request))) {
//			MchntUserInfo muser = commonBO.getSessionData(SessionKeys.MCHNT_USER_INFO.getCode());
			MchntUserInfo muser = commonBO.getMchntUser();
			buildIndexData(model,muser.getUserId());
			request.getSession().setAttribute("mchntCds", muser.getMchntCd());
			return "index";
		} else {
			model.addAttribute("rondom", String.valueOf((new Date()).getTime()));
			String timeStamp = String.valueOf((new Date()).getTime()+Utils.getRandomInt(10, 999)); //TIME_STAMP
			model.addAttribute("timeStamp", timeStamp);
//			String sessionId = request.getSession().getId();
//			LocalSession session = LocalSessionManager.getSession(sessionId);
//			if (session != null) {
//				session.clear();
//			}
			//以下代码会在尚未登入前建立Session，主要目的是存放图形验证码。
//			else {
//				LocalSessionManager.newSession(request.getSession().getId());
//			}
//			commonBO.setSessionData(SessionKeys.TIME_STAMP.getCode(), timeStamp);

			LocalSession session = commonBO.getLocalSession(request);
			if (session!=null) {
				session.clear();
			}
			return "login";
		}
	}


	private String buildIndexData(Model model,String userId) {

		String mchntCd = commonBO.getMchntCd();
		model.addAttribute("mchntCd", mchntCd);
		
		//人民币账户
		MerAccountInfo mchntAcct_CNY = mchntAccountBO.getMchntAccount(mchntCd, "156");
		if (mchntAcct_CNY != null) {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_CNY", StringUtil.formateAmt(String.valueOf(mchntAcct_CNY.getAvailableBalance()-mchntAcct_CNY.getObligatedBalance())));
			//页面额外显示保留余额
			model.addAttribute("obligatedBalance_CNY", StringUtil.formateAmt(String.valueOf(mchntAcct_CNY.getObligatedBalance())));
			model.addAttribute("frozenBalance_CNY", StringUtil.formateAmt(String.valueOf(mchntAcct_CNY.getFrozenBalance())));
			model.addAttribute("frozenT1Balance_CNY", StringUtil.formateAmt(String.valueOf(mchntAcct_CNY.getFrozenT1Balance())));
			long totalBalance_CNY= sum(mchntAcct_CNY.getAvailableBalance(),mchntAcct_CNY.getFrozenBalance(),mchntAcct_CNY.getFrozenT1Balance());
			model.addAttribute("totalBalance_CNY", StringUtil.formateAmt(String.valueOf(totalBalance_CNY)));
		} else {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_CNY", "0.00");
			//页面额外显示保留余额
			model.addAttribute("obligatedBalance_CNY", "0.00");
			model.addAttribute("frozenBalance_CNY", "0.00");
			model.addAttribute("frozenT1Balance_CNY", "0.00");
			model.addAttribute("totalBalance_CNY", "0.00");
		}
		
		//越南盾账户
		MerAccountInfo mchntAcct_VND = mchntAccountBO.getMchntAccount(mchntCd, "704");
		if (mchntAcct_VND != null) {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_VND", StringUtil.formateAmt(String.valueOf(mchntAcct_VND.getAvailableBalance()-mchntAcct_VND.getObligatedBalance())));
			//页面额外显示保留余额
			model.addAttribute("obligatedBalance_VND", StringUtil.formateAmt(String.valueOf(mchntAcct_VND.getObligatedBalance())));
			model.addAttribute("frozenBalance_VND", StringUtil.formateAmt(String.valueOf(mchntAcct_VND.getFrozenBalance())));
			model.addAttribute("frozenT1Balance_VND", StringUtil.formateAmt(String.valueOf(mchntAcct_VND.getFrozenT1Balance())));
			long totalBalance_VND= sum(mchntAcct_VND.getAvailableBalance(),mchntAcct_VND.getFrozenBalance(),mchntAcct_VND.getFrozenT1Balance());
			model.addAttribute("totalBalance_VND", StringUtil.formateAmt(String.valueOf(totalBalance_VND)));
		} else {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_VND", "0.00");
			//页面额外显示保留余额
			model.addAttribute("obligatedBalance_VND", "0.00");
			model.addAttribute("frozenBalance_VND", "0.00");
			model.addAttribute("frozenT1Balance_VND", "0.00");
			model.addAttribute("totalBalance_VND", "0.00");
		}
		
		//泰铢账户
		MerAccountInfo mchntAcct_THB = mchntAccountBO.getMchntAccount(mchntCd, "764");
		if (mchntAcct_THB != null) {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_THB", StringUtil.formateAmt(String.valueOf(mchntAcct_THB.getAvailableBalance()-mchntAcct_THB.getObligatedBalance())));
			//页面额外显示保留余额
			model.addAttribute("obligatedBalance_THB", StringUtil.formateAmt(String.valueOf(mchntAcct_THB.getObligatedBalance())));
			model.addAttribute("frozenBalance_THB", StringUtil.formateAmt(String.valueOf(mchntAcct_THB.getFrozenBalance())));
			model.addAttribute("frozenT1Balance_THB", StringUtil.formateAmt(String.valueOf(mchntAcct_THB.getFrozenT1Balance())));
			long totalBalance_THB= sum(mchntAcct_THB.getAvailableBalance(),mchntAcct_THB.getFrozenBalance(),mchntAcct_THB.getFrozenT1Balance());
			model.addAttribute("totalBalance_THB", StringUtil.formateAmt(String.valueOf(totalBalance_THB)));
		} else {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_THB", "0.00");
			//页面额外显示保留余额
			model.addAttribute("obligatedBalance_THB", "0.00");
			model.addAttribute("frozenBalance_THB", "0.00");
			model.addAttribute("frozenT1Balance_THB", "0.00");
			model.addAttribute("totalBalance_THB", "0.00");
		}
		
		
		model.addAttribute("userID", userId);

		MchntInfo m = mchntBO.getMchnt(mchntCd);
		model.addAttribute("mchntNm", m.getMchntCnNm());
		/*model.addAttribute("lastLoginIp", m.getLastLoginIp());
		model.addAttribute("lastLoginTs", m.getLastLoginTs());
		model.addAttribute("mchntCdAdmin", mchntAcct.getMchntCd() + "-admin");
		model.addAttribute("status" , isEmpty(m.getSecretInitState()) ? SecretState.INIT :m.getSecretInitState());
		model.addAttribute("otpSecretLogin", m.getOtpSecretLogin() == null ? "" : m.getOtpSecretLogin());
		model.addAttribute("optSecretAdmin", m.getOptSecretAdmin() == null ? "" : m.getOptSecretAdmin());*/

		MchntUserInfo muser = mchntBO.selectMchntUserInfo(mchntCd, userId);
		model.addAttribute("lastLoginIp", muser.getLastLoginIp());
		model.addAttribute("lastLoginTs", muser.getLastLoginTs());
		model.addAttribute("otpSecret", muser.getOtpSecret());
		//写入是否开启密码提示(True/False)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date FromUpDate = new Date();
		String FromUpDateStr = muser.getPwdUpdTs();
		Date ToNowDate  = muser.getRecUpdTs();
		//
		if(FromUpDateStr==null || FromUpDateStr.isEmpty() || FromUpDateStr.equals("")) {
			FromUpDate=ToNowDate;
		}
		else {
			try {
				FromUpDate = sdfdate.parse(FromUpDateStr);
			} catch (ParseException e) {
				debug("剖析时间字符串错误："+FromUpDateStr);
				e.printStackTrace();
			}
		}
		long from = FromUpDate.getTime();
		long to = ToNowDate.getTime();
		int sub_up_days = (int) ((to - from)/(1000 * 60 * 60 * 24));
		model.addAttribute("changePwdTip", sub_up_days>=92?"True":"False");
		model.addAttribute("updateFromDate", sdf.format(FromUpDate));
		model.addAttribute("loginToDate", sdf.format(ToNowDate));
		info("密码已经"+sub_up_days+"天未修改，最后登录时间："+ToNowDate.toString()+"，最后密码更新时间："+FromUpDate.toString());
		return m.getSecretInitState();
	}

	boolean isEmpty(String a) {
		return a==null || "".equals(a);
	}

//	public void reciprocal(HttpServletRequest request) throws InterruptedException {
////		commonBO.setSessionData("Google_STATE","preLogin");
//		System.out.println("線程開始");
//			Thread.sleep(12000000);
//		System.out.println("狀態待驗證");
//		commonBO.setSessionData("Google_STATE","preLogin");
//		System.out.println("60秒內要驗證");
//			Thread.sleep(12000000);
//		HttpSession session = request.getSession(false);
//		if (session != null) {
//			String sessionId = session.getId();
//			LocalSession s = LocalSessionManager.getSession(sessionId);
//			System.out.println(s.getSessionData("Google_STATE") + "狀態");
//			if (s.getSessionData("Google_STATE").equals("ok")) {
//				System.out.println("驗證成功狀態重製");
//				reciprocal(request);
//			} else {
//				System.out.println("驗證失敗狀態登出");
//				commonBO.setSessionData(SessionKeys.LOGIN_STATE.getCode(), LoginState.FAILED);
//			}
//		}
//
//	}

}
