package com.icpay.payment.mer.web.controller;

import static com.icpay.payment.risk.MerUser.merUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.icpay.gauth.GoogleAuthenticator;
import com.icpay.payment.common.constants.RspCd;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.constants.Names.MSG;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.enums.AjaxRespEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.CommonEnums.MchntSt;
import com.icpay.payment.common.enums.MerEnums.MerUserRole;
import com.icpay.payment.common.enums.MerEnums.MerUserState;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.Converter;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.PwdUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.common.utils.logger.Log;
import com.icpay.payment.db.dao.mybatis.model.Announcement;
import com.icpay.payment.db.dao.mybatis.model.Content;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicy;
import com.icpay.payment.db.dao.mybatis.model.SiteInfo;
import com.icpay.payment.db.service.IAnnouncementService;
import com.icpay.payment.db.service.IContextService;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.IMchntUserInfoService;
import com.icpay.payment.mer.bo.MchntAccountBO;
import com.icpay.payment.mer.bo.MchntBO;
import com.icpay.payment.mer.bo.MchntSettleBO;
import com.icpay.payment.mer.cache.MerConfigCache;
import com.icpay.payment.mer.cache.MerParamCache;
import com.icpay.payment.mer.cache.SiteInfoCache;
import com.icpay.payment.mer.constants.MerConstants;
import com.icpay.payment.mer.constants.MerConstants.LastLoginState;
import com.icpay.payment.mer.constants.MerConstants.LoginState;
import com.icpay.payment.mer.constants.MerConstants.Roles;
import com.icpay.payment.mer.constants.MerConstants.SecretState;
import com.icpay.payment.mer.session.LocalSession;
import com.icpay.payment.mer.session.LocalSessionManager;
import com.icpay.payment.mer.session.SessionKeys;
import com.icpay.payment.mer.util.GoogleAuthenticatorUtils;
import com.icpay.payment.mer.util.I18nMsgUtils;
import com.icpay.payment.mer.util.MerUtils;
import com.icpay.payment.mer.web.interceptor.IgnoreSessionCheck;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.risk.MerUser;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;
import com.icpay.payment.service.SessionUtilService;

@Controller
public class LoginController extends BaseController {

    private static final Logger logger = Logger.getLogger(LoginController.class);

    public static final int DefaultLoginRetries = 5;
    public static String GoogleAuthenticatorCode = "";
    public static long GoogleAuthenticatorTime = 0L;
    private int maxLoginRetries = -1;

    protected int getMaxLoginRetries() {
        if (maxLoginRetries <= 3) {
            String sconf = MerConfigCache.getConfig(MerConstants.CONFIG_KEY_MAX_LOGIN_RETRIES);
            try {
                maxLoginRetries = Integer.parseInt(sconf);
                logger.info(String.format("[init] maxLoginRetries=%s", maxLoginRetries));
                if (maxLoginRetries <= 3) {
                    maxLoginRetries = DefaultLoginRetries;
                    logger.info(String.format("[init][default] maxLoginRetries=%s", maxLoginRetries));
                }
            } catch (Exception e) {
                maxLoginRetries = DefaultLoginRetries;
                logger.info(String.format("[init][default] maxLoginRetries=%s", maxLoginRetries));
            }
        }
        return maxLoginRetries;
    }

    @Autowired
    private MchntBO mchntBO;
    @Autowired
    private MchntAccountBO mchntAccountBO;
    @Autowired
    private MchntSettleBO mchntSettleBO;

    // @RequestMapping(value="/login")
    // @IgnoreSessionCheck
    // public @ResponseBody AjaxResult login(String mchntCd, String loginPwd, String
    // validateCode, String loginType,
    // HttpServletRequest request) {
    // AssertUtil.strIsBlank(mchntCd, "mchntCd is empty");
    // AssertUtil.strIsBlank(loginPwd, "loginPwd is empty");
    // AssertUtil.strIsBlank(validateCode, "validateCode is empty");
    //
    // try {
    // // 校验验证码
    // String sessionValidCode =
    // commonBO.getSessionData(SessionKeys.VALIDATION_CODE.getCode());
    // if (StringUtil.isBlank(sessionValidCode)) {
    // return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED, "图形验证码失效");
    // } else {
    // if (!validateCode.equalsIgnoreCase(sessionValidCode)) {
    // return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED, "图形验证码输入错误，请重新输入");
    // }
    // }
    // commonBO.removeSessionData(SessionKeys.VALIDATION_CODE.getCode());
    //
    // // 校验商户是否有效
    // MchntInfo mchnt = mchntBO.getMchnt(mchntCd);
    // if (mchnt == null ||
    // !CommonEnums.MchntSt._1.getCode().equals(mchnt.getMchntSt())) {
    // return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED, "无效商户");
    // }
    //
    // // 校验商户账户是否有效
    // MerAccountInfo accountInfo = mchntAccountBO.getMchntAccount(mchntCd);
    // if (accountInfo == null ||
    // !CommonEnums.RecSt.VALID.getCode().equals(accountInfo.getState())) {
    // return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED, "商户账户无效");
    // }
    //
    // // 检查商户是否配置清算信息
    // MerSettlePolicy msp = mchntSettleBO.getMerSettleInfo(mchntCd);
    // if (msp == null) {
    // return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED, "未给该商户配置清算信息");
    // }
    //
    // // 校验登录密码
    // if (!PwdUtil.validatePwd(loginPwd, mchnt.getLoginPwd())) {
    // return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED, "登录密码错误");
    // }
    //
    // if(mchnt.getSecretInitState() ==null ||mchnt.getSecretInitState().equals("")
    // ||mchnt.getSecretInitState().equals("0") ||
    // (mchnt.getSecretInitState().equals("1") && (loginType ==null|| loginType
    // =="")) ) {
    // // 登录成功，构造会话信息
    // commonBO.setSessionData(SessionKeys.MCHNT_INFO.getCode(), mchnt);
    // }
    // mchnt.setLastLoginIp(request.getRemoteAddr());
    // mchnt.setLastLoginTs(DateUtil.now19());
    // IMchntInfoService service = this.getService(IMchntInfoService.class);
    // service.updateLoginInfo(mchnt);
    //
    // commonBO.setSessionData(SessionKeys.GOOGLE_USER_LOGIN_TYPE.getCode(),
    // (loginType ==null|| loginType =="") ? "1" : loginType);
    //
    // List<String> list =new ArrayList<String>();
    // list.add(mchntCd);
    //// list.add(mchnt.getOptSecretAdmin());
    //// list.add(mchnt.getOtpSecretLogin());
    // list.add(mchnt.getSecretInitState());
    //
    // return commonBO.buildSuccResp("list" , list);
    // } catch (Exception e) {
    // logger.error("用户登录出错", e);
    // return commonBO.buildErrorResp("系统异常，用户登录失败");
    // }
    // }

    protected void resetSession() {
        commonBO.setSessionData(SessionKeys.ROLE.getCode(), Roles.NONE);
        commonBO.setSessionData(SessionKeys.SECRET_STATE.getCode(), SecretState.INIT);
		commonBO.setSessionData(SessionKeys.LOGIN_STATE.getCode(), LoginState.FAILED);
		commonBO.setSessionData(SessionKeys.Last_GoogleAuth_Time.getCode(), LoginState.FAILED);
        commonBO.removeSessionData(SessionKeys.CLIENTIP.getCode());
//         commonBO.removeSessionData(SessionKeys.MCHNT_INFO.getCode());
//         commonBO.removeSessionData(SessionKeys.MCHNT_USER_INFO.getCode());
        commonBO.removeSessionData(SessionKeys.Last_GoogleAuth_Time.getCode());

        //寫入Redis
		//登入狀態: mer.${mer_id}.${user_id}.lastLoginState = 0 (0=未登入/1=已登入)
        MchntUserInfo sessionMchntUserInfo = commonBO.getSessionData(SessionKeys.MCHNT_USER_INFO.getCode());
        if (sessionMchntUserInfo != null) {
        	String lastLoginStateKey = "mer." + sessionMchntUserInfo.getMchntCd() + "." + sessionMchntUserInfo.getUserId() + ".lastLoginState";
    		this.putGlobalSessionData(lastLoginStateKey, LastLoginState.NOT_LOGGED_IN);
        }
    }

    @RequestMapping(value = "/login")
    @IgnoreSessionCheck
    public @ResponseBody
    AjaxResult login(String mchntCd, String userId, String secretHash, String validateCode,
                     String loginType, HttpServletRequest request, HttpServletResponse resp) {
        String lang = I18nMsgUtils.getLanguage(request);
                
        AssertUtil.strIsBlank(mchntCd, "mchntCd is empty");
        AssertUtil.strIsBlank(userId, "userId is empty");
        AssertUtil.strIsBlank(secretHash, "secretHash is empty");
        AssertUtil.strIsBlank(validateCode, "validateCode is empty");
        userId = (userId==null? "" : userId.toLowerCase());
        
        /* 该站点 和 商户所属站点 是否吻合 start */
        String siteId = "";
        String mchntSiteId = "";
        String serverName = request.getServerName();

        SiteInfo siteInfo = SiteInfoCache.getSiteByDomain(serverName);
        if (siteInfo != null) {
        	siteId = siteInfo.getSiteId();  //用域名找到该站点
        	logger.debug("[Login]siteId=" + siteId);
        	AssertUtil.strIsBlank(siteId, "siteId is empty");
        } else {
        	return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,
            		mappingI18n(this.getClass().getSimpleName(),"未找到站资讯", lang));
        }

		MchntInfo mInfo = SiteInfoCache.readMchntInfo(mchntCd);
		if (mInfo != null) {
			mchntSiteId = mInfo.getSiteId();
			logger.debug("[Login]mchntSiteId=" + mchntSiteId);
			AssertUtil.strIsBlank(mchntSiteId, "mSiteId is empty");
		} else {
        	Log.warn()
        	.message("商户： %s; 用户： %s; 商户不存在!", mchntCd, userId)
    		.code(RspCd.Z_0007)
    		.attach("siteId", siteId)
    		.submit();

        	//商户不存在
            RiskEvent.mer()
        		.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Login)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.InvalidInfo)
            	.target(mchntCd)
            	.message(String.format(mappingI18n(this.getClass().getSimpleName(),"商户： %s; 用户： %s; 商户不存在!", lang), mchntCd, userId))
            	.params("siteId", siteId)
            	.submit();

            return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED,
            		mappingI18n(this.getClass().getSimpleName(),"商户不存在", lang));
		}
		
		//比较 该站点 和 商户所属站点 是否吻合
		if(siteId.equals(mchntSiteId)) { 
			//不动作
			logger.debug("[Login]站点和商户所属站点吻合!");
		} else {
			// 不吻合则做后续动作...
        	Log.warn()
        	.message("商户： %s; 用户： %s; 站点与商户不合!", mchntCd, userId)
    		.code(RspCd.Z_0007)
    		.attach("siteId", siteId)
    		.attach("mchntSiteId", mchntSiteId)
    		.submit();

        	//站点id不合法
            RiskEvent.mer()
        		.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Login)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.Invaild_Siteid)
            	.target(mchntCd)
            	.message(String.format(mappingI18n(this.getClass().getSimpleName(),"商户： %s; 用户： %s; 站点与商户不合!", lang), mchntCd, userId))
            	.params("siteId", siteId)
            	.params("mchntSiteId", mchntSiteId)
            	.submit();

            return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED,
            		mappingI18n(this.getClass().getSimpleName(),"站点与商户不合!", lang));
		}
		/* 该站点 和 商户所属站点 是否吻合 end */
		
        resetSession();

        String clientIp = MerUtils.getRemoteIp(request);
        info(String.format("[登录请求] 商户: %s, 登录用户: %s,  请求ip: %s, 密码: %s , 图形验证码: %s, 登录类型: %s, 站点: %s" ,mchntCd,userId, clientIp, secretHash, validateCode,loginType, siteId));

        Map<String,String> logingInfo = //登入资讯(Event夹带用)
        		Utils.newMap(
        				"loginType", loginType,
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/login",
        				MSG.merId, mchntCd,
        				MSG.userId, userId,
        				INTER_MSG.reqIp, clientIp,
        				INTER_MSG.siteId, siteId
        				);


        // if (isEmpty(loginType))
        // loginType = "1"; //一般用戶，未勾選管理者登入 //"1":表示一般User，"0"表示 admin 登入

        // String adminCheckBox = "0".equals(loginType) ? "checked" : "";
        // this.setCookie(resp, "adminCheckBox", adminCheckBox, 3*Secs_OneDay);

        try {
            // 校验验证码
            String sessionValidCode = commonBO.getSessionData(SessionKeys.VALIDATION_CODE.getCode());
            if (StringUtil.isBlank(sessionValidCode)) {
            	//info("图形验证码失效");
                return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED,
                		mappingI18n(this.getClass().getSimpleName(),"图形验证码失效", lang));
            } else {
                if (!validateCode.equalsIgnoreCase(sessionValidCode)) {
                    sleep(1100, 2500);
                    Log.warn()
                    .message("[登录请求] 图形验证码输入错误; 商户： %s, 登录用户: %s,  请求ip: %s, 密码: %s , 图形验证码: %s, 登录类型: %s" ,mchntCd,userId, clientIp, secretHash, validateCode,loginType)
                    .submit();

		        	RiskEvent.mer()
		            	.who(merUser(mchntCd, userId))
		            	.event(RISK.Event.Login)
		            	.result(RISK.Result.Failed)
		            	.reason(RISK.Reason.Verify_Captcha)
		            	.message(String.format(mappingI18n(this.getClass().getSimpleName(),"商户： %s, 用户： %s, 图形验证码输入错误", lang), mchntCd, userId))
		            	.params(logingInfo) //夹带其他资讯
		            	.submit();

                    return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED, 
                    		mappingI18n(this.getClass().getSimpleName(),"图形验证码输入错误，请重新输入", lang));
                }
            }
            commonBO.removeSessionData(SessionKeys.VALIDATION_CODE.getCode());

            if (!validateClientIp(mchntCd, clientIp, request)) {
            	//warn("登入IP不合法");

            	Log.warn()
            		.message("验证客户端IP白名单失败! 商户： %s; 用户： %s; 客户端IP: %s;", mchntCd, userId, clientIp)
            		.code(RspCd.Z_9001)
            		.attach(logingInfo) //夹带登入IP等资讯
            		.submit();

            	//登入IP不合法
                RiskEvent.mer()
            		.who(merUser(mchntCd, userId))
	            	.event(RISK.Event.Login)
	            	.result(RISK.Result.Failed)
	            	.reason(RISK.Reason.WhiteList_IP)
	            	.target(clientIp)
	            	.message(String.format(mappingI18n(this.getClass().getSimpleName(),"验证客户端IP白名单失败!  商户： %s; 用户： %s; 客户端IP: %s;", lang), mchntCd, userId, clientIp))
	            	.params(logingInfo) //夹带登入IP等资讯
	            	.submit();

                return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED, 
                		mappingI18n(this.getClass().getSimpleName(),"登入IP不合法", lang));
            }

            commonBO.setSessionData(SessionKeys.CLIENTIP.getCode(), "" + clientIp);

            // 校验商户是否有效
            MchntInfo mchnt = mchntBO.getMchnt(mchntCd);
            if (mchnt == null || !CommonEnums.MchntSt._1.getCode().equals(mchnt.getMchntSt())) {
                if (CommonEnums.MchntSt._9.getCode().equals(mchnt.getMchntSt())) {
//                	warn("商户已被锁定，请联系管理人员!");

                	Log.warn()
                	.message("商户： %s; 用户： %s; 已被锁定!", mchntCd, userId)
            		.code(RspCd.Z_9001)
            		.attach(logingInfo) //夹带登入IP等资讯
            		.submit();

                	//商户已被锁定，请联系管理人员!
                    RiskEvent.mer()
                	.who(merUser(mchntCd, userId))
                	.event(RISK.Event.Login)
                	.result(RISK.Result.Failed)
                	.reason(RISK.Reason.Locked)
                	.target(mchntCd)
	            	.message(String.format(mappingI18n(this.getClass().getSimpleName(),"商户： %s; 用户： %s; 已被锁定!", lang), mchntCd, userId))
	            	.params(logingInfo) //夹带登入IP等资讯
                	.submit();

                    return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED, 
                    		mappingI18n(this.getClass().getSimpleName(),"商户已被锁定，请联系管理人员!", lang));
                }else {
//                	warn("无效商户");

                	Log.warn()
                	.message("商户： %s; 用户： %s; 无效商户!", mchntCd, userId)
            		.code(RspCd.Z_9001)
            		.attach(logingInfo) //夹带登入IP等资讯
            		.submit();

                	//无效商户
                    RiskEvent.mer()
                		.who(merUser(mchntCd, userId))
	                	.event(RISK.Event.Login)
	                	.result(RISK.Result.Failed)
	                	.reason(RISK.Reason.Disabled)
	                	.target(mchntCd)
		            	.message(String.format(mappingI18n(this.getClass().getSimpleName(),"商户： %s; 用户： %s; 无效商户!", lang), mchntCd, userId))
	                	.params(logingInfo) //夹带登入IP等资讯
	                	.submit();

                    return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED,
                    		mappingI18n(this.getClass().getSimpleName(),"无效商户", lang));
                }
            }

//            // 校验商户账户是否有效(2021/4/28 已确认生产的，都是有效的，所以注解)
//            MerAccountInfo accountInfo = mchntAccountBO.getMchntAccount(mchntCd);
//            if (accountInfo == null || !CommonEnums.RecSt.VALID.getCode().equals(accountInfo.getState())) {
////            	warn("商户账户无效");
//
//            	Log.warn()
//            	.message("商户： %s; 用户： %s; 商户账户无效!", mchntCd, userId)
//        		.code(RspCd.Z_9001)
//        		.attach(logingInfo) //夹带登入IP等资讯
//        		.submit();
//
//            	//商户账户无效
//                RiskEvent.mer()
//            		.who(merUser(mchntCd, userId))
//	            	.event(RISK.Event.Login)
//	            	.result(RISK.Result.Failed)
//	            	.reason(RISK.Reason.InvalidInfo)
//	            	.target(mchntCd)
//	            	.message("商户： %s; 用户： %s; 商户账户无效!", mchntCd, userId)
//	            	.params(logingInfo) //夹带登入IP等资讯
//	            	.submit();
//
//                return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED,
//                		mappingI18n(this.getClass().getSimpleName(),"商户账户无效"));
//            }

//            因加入了多币别, 所以此段先注解  2021/5/19
//            // 检查商户是否配置清算信息
//            MerSettlePolicy msp = mchntSettleBO.getMerSettleInfo(mchntCd);
//            if (msp == null) {
////            	warn("未给该商户配置清算信息");
//
//            	Log.warn()
//            	.message("商户： %s; 用户： %s; 未给该商户配置清算信息!", mchntCd, userId)
//        		.code(RspCd.Z_9001)
//        		.attach(logingInfo) //夹带登入IP等资讯
//        		.submit();
//
//            	//未给该商户配置清算信息
//                RiskEvent.mer()
//            		.who(merUser(mchntCd, userId))
//	            	.event(RISK.Event.Login)
//	            	.result(RISK.Result.Failed)
//	            	.reason(RISK.Reason.InvalidInfo)
//	            	.target(mchntCd)
//	            	.message(String.format(mappingI18n(this.getClass().getSimpleName(),"商户： %s; 用户： %s; 未给该商户配置清算信息!", lang), mchntCd, userId))
//	            	.params(logingInfo) //夹带登入IP等资讯
//	            	.submit();
//
//                return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED, 
//                		mappingI18n(this.getClass().getSimpleName(),"未给该商户配置清算信息", lang));
//            }
            MchntUserInfo muser = mchntBO.selectMchntUserInfo(mchntCd, userId);

            String userState = "0";
            if (muser == null) {
//            	warn("商户用户信息无效");

            	Log.warn()
            	.message("商户： %s; 用户： %s; 商户用户信息无效!", mchntCd, userId)
        		.code(RspCd.Z_9001)
        		.attach(logingInfo) //夹带登入IP等资讯
        		.submit();

                RiskEvent.mer()
            		.who(merUser(mchntCd, userId))
	            	.event(RISK.Event.Login)
	            	.result(RISK.Result.Failed)
	            	.reason(RISK.Reason.InvalidInfo)
	            	.target(userId)
	            	.message(String.format(mappingI18n(this.getClass().getSimpleName(),"商户： %s; 用户： %s; 商户用户信息无效!", lang), mchntCd, userId))
	            	.params(logingInfo) //夹带登入IP等资讯
	            	.submit();

                return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED, 
                		mappingI18n(this.getClass().getSimpleName(),"商户用户信息无效", lang));
            } else if (muser != null && CommonEnums.MchntSt._9.getCode().equals(muser.getUserState())) {
//            	warn("商户用户已被锁定!");

            	Log.warn()
            	.message("商户： %s; 用户： %s; 商户用户已被锁定!", mchntCd, userId)
        		.code(RspCd.Z_9001)
        		.attach(logingInfo) //夹带登入IP等资讯
        		.submit();

                RiskEvent.mer()
            		.who(merUser(mchntCd, userId))
	            	.event(RISK.Event.Login)
	            	.result(RISK.Result.Failed)
	            	.reason(RISK.Reason.Locked)
	            	.message(String.format(mappingI18n(this.getClass().getSimpleName(),"商户： %s; 用户： %s; 商户用户已被锁定!", lang), mchntCd, userId))
	            	.target(userId)
	            	.params(logingInfo) //夹带登入IP等资讯
	            	.submit();

                return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED, 
                		mappingI18n(this.getClass().getSimpleName(),"商户用户已被锁定，请联系管理人员!", lang));
            } else if (muser != null && CommonEnums.MchntSt._2.getCode().equals(muser.getUserState())) {
//            	warn("商户用户信息无效");

            	Log.warn()
            	.message("商户： %s; 用户： %s; 商户用户信息无效!", mchntCd, userId)
        		.code(RspCd.Z_9001)
        		.attach(logingInfo) //夹带登入IP等资讯
        		.submit();

            	RiskEvent.mer()
	            	.who(merUser(mchntCd, userId))
	            	.event(RISK.Event.Login)
	            	.result(RISK.Result.Failed)
	            	.reason(RISK.Reason.InvalidInfo)
	            	.target(userId)
	            	.message(String.format(mappingI18n(this.getClass().getSimpleName(),"商户： %s; 用户： %s; 商户用户信息无效!", lang), mchntCd, userId))
	            	.params(logingInfo) //夹带登入IP等资讯
	            	.submit();

                return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED,
                		mappingI18n(this.getClass().getSimpleName(),"商户用户信息无效", lang));
            } else if (muser != null && CommonEnums.MchntSt._1.getCode().equals(muser.getUserState())) {
                userState = "1";
            }
            // userState = "0" 條件: muser==null ||  CommonEnums.MchntSt 不為"1"

            // 校验登录密码 查询用户信息较验密码
            // String timeStamp = commonBO.getSessionData(SessionKeys.TIME_STAMP.getCode());
            String timeStamp = validateCode; // 改用validateCode避免 replay 攻击
            if (!PwdUtil.validatePwd(secretHash, timeStamp, null, muser.getLoginPwd())) {
                updateLoginState(request, muser, false, 0, "/login");
                 warn("登录密码错误",muser );
                // TODO Alert and warning in updateLoginState()

                 return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED,
                  		mappingI18n(this.getClass().getSimpleName(),"登录密码错误", lang));
            }

            // 安全设置状态：0=未完成，1=已完成(Admin已绑定，User未绑定)，2=已完成(Admin已绑定，User已绑定)
            // String secretState = getSecretState(mchnt.getSecretInitState());
            String secretState = getSecretState(muser); // 0=未设置(要求设置)，1=管理员已设置，2=代付user已设置，3=一般user不须设置, 4=一般user已设置
            commonBO.setSessionData(SessionKeys.SECRET_STATE.getCode(), secretState);

            // String role = Roles.USER;
            // if (LoginType.ADMIN.equals(loginType))
            // role = Roles.ADMIN;
            commonBO.setSessionData(SessionKeys.MCHNT_INFO.getCode(), mchnt);
            muser.setLoginPwd("");
            muser.setOtpSecret(""); // 清空后放入session
            commonBO.setSessionData(SessionKeys.MCHNT_USER_INFO.getCode(), muser);

            //updateLoginState
            /*
             * if (SecretState.INIT.equals(secretState)) { //尚未初始化 //role = Roles.ADMIN;
             * commonBO.setSessionData(SessionKeys.ROLE.getCode(), role);
             * commonBO.setSessionData(SessionKeys.LOGIN_STATE.getCode(), LoginState.OK);
             * updateLoginState(request, mchnt, true,0); } else if
             * (SecretState.ADMIN_INITED.equals(secretState)) { //1=已完成(Admin已绑定，User未绑定)
             * commonBO.setSessionData(SessionKeys.ROLE.getCode(), role); if
             * (Roles.USER.equals(role)) {
             * commonBO.setSessionData(SessionKeys.LOGIN_STATE.getCode(), LoginState.OK);
             * updateLoginState(request, mchnt, true,0); } } else {
             * //2=已完成(Admin已绑定，User已绑定) commonBO.setSessionData(SessionKeys.ROLE.getCode(),
             * role); }
             */

            String role = muser.getUserRole();
            commonBO.setSessionData(SessionKeys.ROLE.getCode(), role);

            // secretState: 0=未设置(要求设置)，1=管理员已设置，2=代付user已设置，3=一般user不须设置, 4=一般user已设置
            // userState: 0=User不OK, 1= user檢查OK
            // TODO 此邏輯看起來不合理，請確認
            // 預期:
            if("0".equals(userState) || "0".equals(secretState) || "3".equals(secretState)) {
            	commonBO.setSessionData(SessionKeys.LOGIN_STATE.getCode(), LoginState.OK); //不需GA認證
            }else {
            	commonBO.setSessionData(SessionKeys.LOGIN_STATE.getCode(), LoginState.PRELOGIN);
            }

//TODO 以下邏輯驗證是否正確
//            if ("1".equals(userState)) {
//            	if (Utils.isInSet(secretState, "0","3"))
//            		//不需要GA驗證
//            		commonBO.setSessionData(SessionKeys.LOGIN_STATE.getCode(), LoginState.OK);
//            	else
//            		//需要GA驗證
//            		commonBO.setSessionData(SessionKeys.LOGIN_STATE.getCode(), LoginState.PRELOGIN);
//            }
//            else
//            	commonBO.setSessionData(SessionKeys.LOGIN_STATE.getCode(), LoginState.FAILED);


            updateLoginState(request, muser, true, 0, "/login");

            HttpSession session = request.getSession(true);
//            boolean isIpWithdrawAllowed = this.validateWithdrawClientIp(mchntCd, clientIp);//考虑拿掉
//            session.setAttribute("allowedWithdraw",
//                    strVal(isIpWithdrawAllowed && MerUserRole.WithdrawUser.getCode().equals(role))); ////考虑拿掉
            session.setAttribute("userRole", role);
            session.setAttribute("userId", userId);
            session.setAttribute("secretState", secretState);
            session.setAttribute("userState", userState);
            session.setAttribute("lastLoginTs1", mchnt.getLastLoginTs());
            session.setAttribute("lastLoginIp1", mchnt.getLastLoginIp());

            info(String.format("当前登录用户: %s ,  角色: %s , 谷歌配置状态: %s ", userId, role, secretState));

//            commonBO.setSessionData(SessionKeys.IS_IP_ALLOWED_WITHDRAW.getCode(), isIpWithdrawAllowed); // true/false //考虑拿掉

            // commonBO.setSessionData(SessionKeys.GOOGLE_USER_LOGIN_TYPE.getCode(),
            // loginType); //"1":表示一般User，"0"表示 admin 登入
            List<String> list = new ArrayList<String>();
            list.add(mchntCd);
            // list.add(mchnt.getOptSecretAdmin());
            // list.add(mchnt.getOtpSecretLogin());
            list.add(secretState);
            list.add(userId);
            list.add(role);
            list.add(userState);
            // 依据登入结果回传值
            request.getSession().setAttribute("userId", userId);// 由于本身没有找到对应的登录session TransferController转账需要操作用户
            
            request.getSession().setAttribute("siteId", siteId);
            
            
            return commonBO.buildSuccResp("list", list);
        } catch (Exception e) {
            logger.error("用户登录出错", e);

	        	RiskEvent.mer()
		        	.who(merUser(mchntCd, userId))
		        	.event(RISK.Event.Login)
		        	.result(RISK.Result.Failed)
		        	.reason(RISK.Reason.Exception)
		        	.target(userId)
		        	.message(String.format(mappingI18n(this.getClass().getSimpleName(),"商户： %s; 用户： %s; 系统异常，用户登录失败!", lang), mchntCd, userId))
		        	.params(logingInfo) //夹带资讯
		        	.submit();

            resetSession();
            return commonBO.buildErrorResp(
            		mappingI18n(this.getClass().getSimpleName(),"系统异常，用户登录失败", lang));
        }
    }

    /**
     *
     * @param request
     * @param mchntCd
     */
    @Deprecated //以前只有商戶號登入時使用的，現已改成多User，不需使用。
    protected void updateLoginState(HttpServletRequest request, MchntInfo mchnt, Boolean loginOk, int typ) {
        String lang = I18nMsgUtils.getLanguage(request);
        if (mchnt == null)
            throw new BizzException(RspCd.Z_0007, mappingI18n(this.getClass().getSimpleName(),"商户信息无效(null)", lang));

        String mchntCd = mchnt.getMchntCd();
        MchntInfo mchntNew = new MchntInfo();
        mchntNew.setMchntCd(mchntCd);
        String reqIp = MerUtils.getRemoteIp(request);
        mchntNew.setLastLoginIp("" + reqIp);
        mchntNew.setLastLoginTs(DateUtil.now19());
        if (loginOk != null) {
            if (loginOk) {
                mchntNew.setLoginFailedCnt(0);
            } else {
                String act = "登入";
                if (typ == 1)
                    act = "谷歌验证";

                int cnt = intVal(mchnt.getLoginFailedCnt()) + 1;
                mchntNew.setLoginFailedCnt(cnt);
                if (cnt >= getMaxLoginRetries()) {
                    mchntNew.setMchntSt(MchntSt._9.getCode());
                    mchntNew.setLoginComment(String.format("[%s] 商户%s %s错误，登入失败次数%s超限，登入IP: %s，帐户已锁定!",
                            Converter.dateTimeToString(new Date()), mchntCd, act, cnt, reqIp));
                } else
                    mchntNew.setLoginComment(String.format("[%s] 商户%s %s错误，登入失败次数%s，登入IP: %s!",
                            Converter.dateTimeToString(new Date()), mchntCd, act, cnt, reqIp));
            }
        }
        IMchntInfoService service = this.getService(IMchntInfoService.class);
        service.updateLoginInfo(mchntNew);
    }

    /**
     * 更新用户状态
     *
     * @param request
     * @param mchntCd
     */
    protected void updateLoginState(HttpServletRequest request, MchntUserInfo mchntUser, Boolean loginOk, int typ, String requestUri) {
        String lang = I18nMsgUtils.getLanguage(request);
    	if (mchntUser == null)
            throw new BizzException(RspCd.Z_0007, mappingI18n(this.getClass().getSimpleName(),"商户信息无效(null)", lang));

        String mchntCd = mchntUser.getMchntCd();
        String userId = mchntUser.getUserId();
        MchntUserInfo recNew = new MchntUserInfo();
        recNew.setMchntCd(mchntCd);
        recNew.setUserId(userId);
        String reqIp = MerUtils.getRemoteIp(request);
        recNew.setLastLoginIp("" + reqIp);
        recNew.setLastLoginTs(DateUtil.now19());

        Map<String,String> eventParams = //更新用户状态资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , requestUri,
        				MSG.merId, mchntCd,
        				MSG.userId, userId,
        				INTER_MSG.reqIp, reqIp
        				);

        if (loginOk != null) {
            if (loginOk) {
                recNew.setLoginFailedCnt(0);
                if (typ == 1) {
                	GoogleAuthenticatorUtils.removeGaLoginFail(mchntUser);//GA驗證碼輸入正確，清除次數
                }
                //检查修改密码时间是否为空
                if(mchntUser.getPwdUpdTs()==null || mchntUser.getPwdUpdTs().isEmpty() || mchntUser.getPwdUpdTs().equals(""))
                {
                	recNew.setPwdUpdTs(DateUtil.now19());
                	info(String.format("商户： %s; 用户： %s; 密码更新时间初始为:%s", mchntCd, userId, recNew.getPwdUpdTs()));

                	//誰登入了
                	RiskEvent.mer()
	                	.who(merUser(mchntCd, userId))
		            	.event(RISK.Event.Login)
		            	.result(RISK.Result.Ok)
		            	.message(String.format(mappingI18n(this.getClass().getSimpleName(),"商户： %s; 用户： %s; 密码更新时间初始为:%s", lang), mchntCd, userId, recNew.getPwdUpdTs()))
		            	.target(userId)
	                	.params(eventParams) //夹带资讯
	                	.submit();
                }
            } else {
                String act = "登入";
                if (typ == 1)
                    act = "谷歌验证";

                int cnt = intVal(mchntUser.getLoginFailedCnt()) + 1;
                recNew.setLoginFailedCnt(cnt);
                if (cnt >= getMaxLoginRetries()) {
                    recNew.setUserState(MerUserState.Locked.getCode());
                    recNew.setLoginComment(String.format("[%s] 商户： %s; 用户： %s %s错误，登入失败次数%s超限，登入IP: %s，帐户已锁定!",
                            Converter.dateTimeToString(new Date()), mchntCd, userId, act, cnt, reqIp));

                    //TODO Publish event 记LOG，加ALERT对象是MER&BM, 規則引擎起來後不需 publish event
                	RiskEvent.mer()
                	.who(merUser(mchntCd, userId))
                	.event(RISK.Event.Login)
                	.result(RISK.Result.Failed)
                	.reason(RISK.Reason.Verify_Passwd)
                	.target(userId)
                	.message(String.format(mappingI18n(this.getClass().getSimpleName(),"商户： %s; 用户： %s %s错误，登入失败次数%s超限，登入IP: %s，帐户已锁定!", lang), mchntCd, userId, act, cnt, reqIp))
                	.params(eventParams) //夹带资讯
                	.submit();

                    //TODO check 規則引擎

                } else {
                	if (typ == 0)  {
	                    recNew.setLoginComment(String.format("[%s] 商户： %s; 用户： %s %s错误，登入失败次数%s，登入IP: %s!",
	                            Converter.dateTimeToString(new Date()), mchntCd, userId, act, cnt, reqIp));

		            	RiskEvent.mer()
		            	.who(merUser(mchntCd, userId))
		            	.event(RISK.Event.Login)
	                	.result(RISK.Result.Failed)
	                	.reason(RISK.Reason.Verify_Passwd)
	                	.target(userId)
		            	.message(String.format(mappingI18n(this.getClass().getSimpleName(),"商户： %s; 用户： %s %s错误，登入失败次数%s，登入IP: %s!", lang), mchntCd, userId, act, cnt, reqIp))
		            	.params(eventParams) //夹带资讯
		            	.submit();
                	} else {
	                    recNew.setLoginComment(String.format("[%s] 商户： %s; 用户： %s %s错误，登入失败，登入IP: %s!",
	                            Converter.dateTimeToString(new Date()), mchntCd, userId, act, cnt, reqIp));

		            	RiskEvent.mer()
		            	.who(merUser(mchntCd, userId))
		            	.event(RISK.Event.Login)
	                	.result(RISK.Result.Failed)
	                	.reason( RISK.Reason.Verify_Otp)
	                	.target(userId)
		            	.message(String.format(mappingI18n(this.getClass().getSimpleName(),"商户： %s; 用户： %s %s错误，登入失败，登入IP: %s!", lang), mchntCd, userId, act, reqIp))
		            	.params(eventParams) //夹带资讯
		            	.submit();
                	}
                }
            }
        }
        IMchntUserInfoService service = this.getService(IMchntUserInfoService.class);
        service.updateByPrimaryKeySelective(recNew);
    }

    protected int intVal(Integer i) {
        if (i == null)
            return 0;
        return i.intValue();
    }

    protected boolean isEmpty(Object obj) {
        if (obj == null)
            return true;
        return ("".equals(obj.toString()));
    }

    /*
     * protected String getSecretState(String stateVal) { if (isEmpty(stateVal))
     * return SecretState.INIT; return stateVal; }
     */

    protected String getSecretState(MchntUserInfo muser) {
//        if (isEmpty(stateVal))
//            return SecretState.INIT;
//        if (!isEmpty(stateVal))
//            return SecretState.OK;
//
//        return stateVal;
    	if ((!muser.getUserRole().equals(MerUserRole.PaymentUser.getCode())) && Utils.isEmpty(muser.getOtpSecret())) {
    		return SecretState.INIT;
    	}
    	else {
    		if(!Utils.isEmpty(muser.getOtpSecret())) {
    			if(muser.getUserRole().equals(MerUserRole.SuperUser.getCode())) return SecretState.ADMIN_INITED;
    			else if(muser.getUserRole().equals(MerUserRole.WithdrawUser.getCode())) return SecretState.WD_USER_BIND;
    			else return SecretState.USER_BIND;
    		}
    		return SecretState.OK;
    	}
    }

    /**
     * Goggle验证码
     *
     * @param authCode
     * @param mchntCd
     * @param model
     * @param request
     * @return
     */
    /*
     * @IgnoreSessionCheck
     *
     * @RequestMapping(value = "/loginCheck", method = RequestMethod.GET)
     * public @ResponseBody AjaxResult loginCheck(String authCode,String
     * mchntCd,Model model,HttpServletRequest request) { // String userId =
     * commonBO.getLoginMer().getMchntCd(); String loginType
     * =commonBO.getLoginType(); MchntInfo mchnt = mchntBO.getMchnt(mchntCd);
     * commonBO.setSessionData(SessionKeys.MCHNT_INFO.getCode(), mchnt);
     *
     * if(mchnt.getOptSecretAdmin() == null || mchnt.getOptSecretAdmin().equals("")
     * ) { commonBO.setSessionData(SessionKeys.LOGIN_STATE.getCode(),
     * LoginState.OK); return commonBO.buildSuccResp("respData","00"); }
     * GoogleAuthenticator ga = null; if(LoginType.ADMIN.equals(loginType)) {
     * mchntCd = mchntCd +"-admin"; ga = new GoogleAuthenticator(mchntCd,
     * mchnt.getOptSecretAdmin()); }else { ga = new GoogleAuthenticator(mchntCd,
     * mchnt.getOtpSecretLogin()); } ga.setWindowSize(2); boolean loginOk =
     * ga.checkCode(authCode); updateLoginState(request, mchnt, loginOk, 1);
     *
     * if(loginOk) { commonBO.setSessionData(SessionKeys.LOGIN_STATE.getCode(),
     * LoginState.OK); return commonBO.buildSuccResp("respData","00"); }else {
     * commonBO.setSessionData(SessionKeys.LOGIN_STATE.getCode(),
     * LoginState.FAILED); sleep(900, 2000); return
     * commonBO.buildSuccResp("respData","10"); } }
     */
    @IgnoreSessionCheck
    @RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResult loginCheck(String authCode, String mchntCd, String userId, Model model,
                          HttpServletRequest request) {
    	String lang = I18nMsgUtils.getLanguage(request);
        // String userId = commonBO.getLoginMer().getMchntCd();
        // String loginType =commonBO.getLoginType();
        MchntInfo mchnt = mchntBO.getMchnt(mchntCd);
        MchntUserInfo muser = mchntBO.selectMchntUserInfo(mchntCd, userId);

        Map<String,String> logingInfo = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/loginCheck",
        				MSG.merId, mchntCd,
        				MSG.userId, userId,
        				INTER_MSG.reqIp, MerUtils.getRemoteIp(request)
        				);

        String loginState = commonBO.getSessionData(SessionKeys.LOGIN_STATE.getCode());
        if(!LoginState.PRELOGIN.equals(loginState)) {
        	commonBO.setSessionData(SessionKeys.LOGIN_STATE.getCode(), LoginState.FAILED);
            sleep(900, 2000);
            return commonBO.buildErrorResp(mappingI18n(this.getClass().getSimpleName(),"登录失败", lang));
        }

        commonBO.setSessionData(SessionKeys.MCHNT_INFO.getCode(), mchnt);
        commonBO.setSessionData(SessionKeys.MCHNT_USER_INFO.getCode(), muser);

        if (Utils.isEmpty(muser.getOtpSecret())) {
            commonBO.setSessionData(SessionKeys.LOGIN_STATE.getCode(), LoginState.OK);
            return commonBO.buildSuccResp("respData", "00");
        }

        //GoogleAuthenticator ga = new GoogleAuthenticator(mchntCd + "-" + userId, muser.getOtpSecret());
        //ga.setWindowSize(2);
        //boolean loginOk = ga.checkCode(authCode);

        boolean loginOk =
        		this.checkGaCode(authCode, mchntCd + "-" + userId, muser.getOtpSecret(), true);

        //google 驗證 , 於60秒內輸入同樣的 pin 碼 , 系統應自動回拒, 登入失效
        if (loginOk) {
        	if (!checkGoogleEqualCode(authCode, loginOk)) {
            	return commonBO.buildErrorResp("60秒內輸入同樣 pin 碼 ，登入失效");
            }
        }

        updateLoginState(request, muser, loginOk, 1, "/loginCheck");

        if (loginOk) {
        	//比對Redis登入資訊(lastLoginState)
            String lastLoginStateKey = "mer." + mchntCd + "." + userId + ".lastLoginState";
            String lastLoginStateValue = this.getGlobalSessionData(lastLoginStateKey);
            if (lastLoginStateValue == null || StringUtils.equals(lastLoginStateValue, LastLoginState.NOT_LOGGED_IN)) {
            	loginOk = true;
            }

            if (StringUtils.equals(lastLoginStateValue, LastLoginState.LOGGED_IN)) {
            	//踢掉上一个人的session(sessionId), 登入
            	String sessionIdKey = "mer." + mchntCd + "." + userId + ".sessionId";
                String sessionIdValue = this.getGlobalSessionData(sessionIdKey);
                if (sessionIdValue != null) {
                	LocalSessionManager.removeSession(sessionIdValue);

                	RiskEvent.mer()
	        		.who(merUser(mchntCd, userId))
	            	.event(RISK.Event.Login)
	            	.result(RISK.Result.Ok)
	            	.target(userId)
	            	.message(String.format(mappingI18n(this.getClass().getSimpleName(),"商户： %s, 用户： %s, 客户端IP： %s; 登入成功，并将上一位使用者登出", lang), mchntCd, userId, MerUtils.getRemoteIp(request)))
	            	.params(logingInfo)
	            	.submit();
                }

                //如果 isTimeout(lastVisitTime): 踢掉上一个人的session(sessionId), 登入
                String lastVisitTimeKey = "mer." + mchntCd + "." + userId + ".lastVisitTime";
                String lastVisitTimeValue = this.getGlobalSessionData(lastVisitTimeKey);
                if (StringUtil.isNotBlank(lastVisitTimeValue)) {
                	LocalSession session = new LocalSession(request.getSession().getId());
                    boolean isTimeOut = session.isTimeout(Long.parseLong(trim(lastVisitTimeValue)));
                    if (isTimeOut) {
                        LocalSessionManager.removeSession(sessionIdValue);

                        RiskEvent.mer()
    	        		.who(merUser(mchntCd, userId))
    	            	.event(RISK.Event.Login)
    	            	.result(RISK.Result.Ok)
    	            	.target(userId)
    	            	.message(String.format(mappingI18n(this.getClass().getSimpleName(),"商户： %s, 用户： %s, 客户端IP： %s; 登入成功，并将上一位使用者登出", lang), mchntCd, userId, MerUtils.getRemoteIp(request)))
    	            	.params(logingInfo)
    	            	.submit();
                    }
                }
            }

            //寫入Redis
            //登入狀態: mer.${mer_id}.${user_id}.lastLoginState = 0 (0=未登入/1=已登入)
            this.putGlobalSessionData(lastLoginStateKey, LastLoginState.LOGGED_IN);

            //上次登入時間: mer.${mer_id}.${user_id}.lastLoginTime
            String lastLoginTimeKey = "mer." + mchntCd + "." + userId + ".lastLoginTime";
            String lastLoginTimeValue = "" + System.currentTimeMillis();
            this.putGlobalSessionData(lastLoginTimeKey, lastLoginTimeValue);

            //登入的SessionID mer.${mer_id}.${user_id}.sessionId
            HttpSession httpSession = request.getSession();
            String sessionIdKey = "mer." + mchntCd + "." + userId + ".sessionId";
            String sessionIdValue = httpSession.getId();
            this.putGlobalSessionData(sessionIdKey, sessionIdValue);

            //寫入session (lastLoginState=1, lastLoginTime=now(), lastVisitTime= now())
            commonBO.setSessionData(SessionKeys.LAST_LOGIN_STATE.getCode(), LastLoginState.LOGGED_IN);
        	commonBO.setSessionData(SessionKeys.LAST_LOGIN_TIME.getCode(), System.currentTimeMillis());
        	commonBO.setSessionData(SessionKeys.LAST_VISIT_TIME.getCode(), System.currentTimeMillis());

        	//寫入DB
        	muser.setLastLoginOkIp(MerUtils.getRemoteIp(request));
        	muser.setLastLoginOkTs(DateUtil.now19());
        	muser.setLastLoginSessionId(request.getSession().getId());
            IMchntUserInfoService service = this.getService(IMchntUserInfoService.class);
            service.updateByPrimaryKeySelective(muser);

            commonBO.setSessionData(SessionKeys.LOGIN_STATE.getCode(), LoginState.OK);
            //this.resetLastGaTime(); //在 checkGaCode 中重置
        	//登入成功
        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Login)
            	.result(RISK.Result.Ok)
            	.target(userId)
            	.message(String.format(mappingI18n(this.getClass().getSimpleName(),"商户： %s, 用户： %s, 客户端IP： %s; 登入成功！", lang), commonBO.getMchntCd(), commonBO.getMchntUser().getUserId(), MerUtils.getRemoteIp(request)))
            	.params(logingInfo)
            	.submit();

            return commonBO.buildSuccResp();
        } else {
        	String msg = "谷歌验证码错误";
        	//GA验证码输入错误，记录错误次数
        	if (!GoogleAuthenticatorUtils.countGaLoginFail(muser)) {
        		msg = String.format("谷歌验证码连续输入錯誤 %s 次，帐号已被锁定", MerParamCache.GA_Login_Failed_Count); 
        	}
            commonBO.setSessionData(SessionKeys.LOGIN_STATE.getCode(), LoginState.FAILED);
            sleep(900, 2000);
//            return commonBO.buildSuccResp("respData", "10");
            return commonBO.buildErrorResp(mappingI18n(this.getClass().getSimpleName(),msg, lang));
        }
    }

    @RequestMapping(value = "/logout")
    @IgnoreSessionCheck
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            try {
                String sessionId = session.getId();
                LocalSession s = LocalSessionManager.getSession(sessionId);
                if (s != null) {
                	MchntUserInfo sessionMchntUserInfo = commonBO.getSessionData(SessionKeys.MCHNT_USER_INFO.getCode());
                    if (sessionMchntUserInfo != null) {
                    	//寫入數據庫
                        IMchntUserInfoService service = this.getService(IMchntUserInfoService.class);
                        MchntUserInfo mchntUserInfo = new MchntUserInfo();
                        mchntUserInfo.setMchntCd(sessionMchntUserInfo.getMchntCd());
                        mchntUserInfo.setUserId(sessionMchntUserInfo.getUserId());
                        mchntUserInfo.setLastLogoutTs(DateUtil.now19());
                        service.updateByPrimaryKeySelective(mchntUserInfo);

                        //寫入Redis
        				//登入狀態: mer.${mer_id}.${user_id}.lastLoginState = 0 (0=未登入/1=已登入)
        				String lastLoginStateKey = "mer." + sessionMchntUserInfo.getMchntCd() + "." + sessionMchntUserInfo.getUserId() + ".lastLoginState";
        				this.putGlobalSessionData(lastLoginStateKey, LastLoginState.NOT_LOGGED_IN);
                    }
                    s.clear();
                    LocalSessionManager.removeSession(sessionId);
                }
                session.invalidate();
            } catch (IllegalStateException e) {
                logger.error("session已失效", e);
            }
        }
        return "common/logout";
    }

    @RequestMapping(value = "/login/checkMchntCd")
    @IgnoreSessionCheck
    public @ResponseBody
    String checkMchntCd(String mchntCd, HttpServletRequest request) {
        String lang = I18nMsgUtils.getLanguage(request);
        if (StringUtil.isBlank(mchntCd)) {
            return mappingI18n(this.getClass().getSimpleName(),"商户代码为空", lang);
        }
        MchntInfo mchnt = null;
        if (mchntBO != null)
            mchnt = mchntBO.getMchnt(mchntCd);
        if (mchnt == null) {
            return mappingI18n(this.getClass().getSimpleName(),"商户不存在，请重新输入", lang);
        } else if (!CommonEnums.MchntSt._1.getCode().equals(mchnt.getMchntSt())) {
            return mappingI18n(this.getClass().getSimpleName(),"请输入有效商户", lang);
        }
        return "true";
    }

    /**
     * ajax获得前端页面展示
     *
     * @param request
     * @param response
     * @throws ParseException
     */
    @RequestMapping(value = "/login/queryValidAnnouncement", method = RequestMethod.POST)
    public void queryValidAnnouncement(HttpServletRequest request, HttpServletResponse response, String mchntCd) throws ParseException {
    	IAnnouncementService iAnnouncementService = this.getService(IAnnouncementService.class);
        IContextService iContextService = this.getService(IContextService.class);
        //结果集，传到前端用
        List<Content> list = new ArrayList<>();
        Map<String, String> annonQryParamMap = new HashMap<String, String>();
        annonQryParamMap.put("mchntCd", mchntCd);
        //获得该商户所属的公告编号信息
        List<Announcement> select = iAnnouncementService.select(annonQryParamMap);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        //获得所有的公告信息   公告id

        Map<String, String> conQryParamMap = new HashMap<String, String>();
        conQryParamMap.put("contentState","0");//公告表中状态为0
        List<Content> select1 = iContextService.select(conQryParamMap);
        List<Content> testList=new ArrayList<>();
        if(select!=null && select.size()>0&&select1!=null &&select1.size()>0){
            for (Announcement announcement : select) {
                for (Content content : select1) {
                    //首先，取出改商户的第一个，作为弹框处理
                    if(content.getBeginTime().getTime() <= System.currentTimeMillis() &&
                            content.getEndTime().getTime() >= System.currentTimeMillis() &&
                            announcement.getAnnouncementState() == 0 &&
                            announcement.getAnnouncementId().equals(content.getContentId())){
                       // System.err.println("公告条件符合");
                        content.setContentState(6);
                        list.add(content);
                    }else if(content.getBeginTime().getTime() <= System.currentTimeMillis() &&
                            content.getEndTime().getTime() >= System.currentTimeMillis()&&
                            announcement.getAnnouncementId().equals(content.getContentId())){
                        list.add(content);
                    }
                }
            }
        }
        try {
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(list));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 点击下次不再提示，修改状态
     *
     * @param announcementId
     * @param response
     */
    @RequestMapping(value = "/login/updateState", method = RequestMethod.POST)
    public void updateState(String mchntCd, String contentId, HttpServletResponse response) {
        IAnnouncementService iAnnouncementService = this.getService(IAnnouncementService.class);
        Announcement announcement = new Announcement();
        announcement.setAnnouncementId(Integer.valueOf(contentId));
        announcement.setMchntCd(mchntCd);
        announcement.setAnnouncementState(1);
        iAnnouncementService.update(announcement);
        try {
            PrintWriter writer = response.getWriter();
            writer.write("");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/checkGoogle", method = RequestMethod.POST)
	@IgnoreSessionCheck
	@ResponseBody
	public AjaxResult checkGoogle(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		//System.out.println("检查是否需要google驗證");
		if (session != null) {
			try {
				//String sessionId = session.getId();

				MchntUserInfo mchntUserInfo = commonBO.getMchntUser();
				if (mchntUserInfo==null)
					return commonBO.buildErrorResp("Invalid session");

//				long lastGaTime = getLastGaTime();
//				if (lastGaTime<0)
//					return commonBO.buildErrorResp("Invalid session");
//				if (! isGaExpired(lastGaTime)) //还在时效内，不需GA验证
//					return commonBO.buildSuccResp("OK");

				if (! isGaExpired()) //还在时效内或不需GA验证
					return commonBO.buildSuccResp("OK");

				return commonBO.buildAjaxResp(AjaxRespEnums.REQUEST_OK)
						.addResultData("mchntCd", mchntUserInfo.getMchntCd())
						.addResultData("userId", mchntUserInfo.getUserId());
			} catch (Exception e) {
				logger.error("session已失效", e);
			}
		}
		return commonBO.buildErrorResp("Invalid session");
	}

	@RequestMapping(value = "/enterGoogle", method = RequestMethod.POST)
	//@IgnoreSessionCheck
	@ResponseBody
	public AjaxResult enterGoogle(HttpServletRequest request, String authCode) {

		String lang = I18nMsgUtils.getLanguage(request);
		String sessionId =this.getSessionId(request, false);

		Log.debug()
			.message("定时要求谷歌验证, Submit: authCode=%s, sessionId=%s;", authCode, sessionId)
			.submit();

		if (!Utils.isEmpty(sessionId)) {
			MchntUserInfo mchntUserInfo = commonBO.getMchntUser();
			if (mchntUserInfo==null)
				return commonBO.buildErrorResp("Invalid session");

			//GoogleAuthenticator ga = new GoogleAuthenticator(
			//		mchntUserInfo.getMchntCd() + "-" + mchntUserInfo.getUserId(),
			//		mchntUserInfo.getOtpSecret());
			//ga.setWindowSize(2);
			//boolean loginOk = ga.checkCode(authCode);

	        boolean loginOk =
	        		this.checkGaCode(authCode, mchntUserInfo.getMchntCd() + "-" + mchntUserInfo.getUserId(), mchntUserInfo.getOtpSecret(), false);

			Log.debug()
				.message("定时谷歌验证结果: %s, authCode=%s, sessionId=%s;", loginOk, authCode, sessionId)
				.submit();

			if (loginOk) {
				//updateLastGaTime(); //update in checkGaCode()
				return commonBO.buildSuccResp();
			} else {
				//commonBO.setSessionData(SessionKeys.LOGIN_STATE.getCode(), LoginState.FAILED);
				resetSession();
				sleep(700, 2000);
	            return commonBO.buildErrorResp(mappingI18n(this.getClass().getSimpleName(),"谷歌验证码错误", lang));
			}

		}
		//commonBO.setSessionData(SessionKeys.LOGIN_STATE.getCode(), LoginState.FAILED);
		resetSession();
		return commonBO.buildErrorResp("系統錯誤，請聯繫管理員");
	}

    /**
     * google 驗證 , 於60秒內輸入同樣的 pin 碼 , 系統應自動回拒, 登入失效
     * @param authCode
     */
    private boolean checkGoogleEqualCode(String authCode, boolean loginOk) {
    	if (!StringUtil.isEmpty(GoogleAuthenticatorCode) && GoogleAuthenticatorTime != 0) {
    		if (StringUtils.equals(GoogleAuthenticatorCode, authCode)) {
    			long now = System.currentTimeMillis();
    			if(((now - GoogleAuthenticatorTime) / 1000) <= 65) {
    				resetSession();
    				return false;
    			}
    		}
    	}
    	GoogleAuthenticatorCode = authCode;
		GoogleAuthenticatorTime = System.currentTimeMillis();
    	return loginOk;
    }

    /**
     * 存取Redis
     */
    protected static String GlobalSessionCatalog="MER";
	protected void putGlobalSessionData(String key, String value) {
		SessionUtilService svc = ServiceProxy.getService(SessionUtilService.class);
		svc.put(GlobalSessionCatalog, key, value);
	}
	protected String getGlobalSessionData(String key) {
		SessionUtilService svc = ServiceProxy.getService(SessionUtilService.class);
		return svc.get(GlobalSessionCatalog, key);
	}

}
