package com.icpay.payment.bm.web.controller;

import static com.icpay.payment.risk.BmUser.bmUser;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.icpay.gauth.GoogleAuthenticator;
import com.icpay.payment.bm.cache.AuthDataCache;
import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.entity.SessionUserInfo;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.constants.Names.MSG;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.enums.AjaxRespEnums;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.PwdUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.common.utils.WebUtil;
import com.icpay.payment.db.dao.mybatis.model.BmRoleInfo;
import com.icpay.payment.db.dao.mybatis.model.BmUserInfo;
import com.icpay.payment.db.service.IBmUserService;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;

@Controller
public class LoginController extends BaseController {

	private static final Logger logger = Logger.getLogger(LoginController.class);
	private static final int unboundGaCOdeRoleId = 200;
	
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("rondom", String.valueOf((new Date()).getTime()));
		return "login";
	}

	@RequestMapping(value = "/login/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult loginSubmit(String usrId, String password, String validateCode, String gaCode, HttpServletRequest request) {
		AssertUtil.strIsBlank(usrId, "用户名为空");
		AssertUtil.strIsBlank(password, "密码为空");
		AssertUtil.strIsBlank(validateCode, "validateCode is blank.");
		
        Map<String,String> logingInfo = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.requestUri, "/login/submit.do",
        				MSG.userId, usrId,
        				INTER_MSG.reqIp, ""+ WebUtil.getRemoteIp(request)
        				);
		
		BmUserInfo usrInfo = null;
		try {
			
			// 校验图形验证码
			if (commonBO.getSessionAttr(Constant.VALIDATION_CODE_KEY) == null) {
				
				RiskEvent.bm()
	            	.who(bmUser(usrId))
	            	.event(RISK.Event.Login_Bm)
	            	.result(RISK.Result.Failed)
	            	.reason(RISK.Reason.Verify_Captcha)
                	.target(usrId)
	            	.message(String.format("用户： %s, 图形验证码失效", usrId))
	            	.params(logingInfo) //夹带其他资讯
	            	.submit();
				
				return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED, "图形验证码失效");
			} else {
				if (!validateCode.equalsIgnoreCase((String) commonBO.getSessionAttr(Constant.VALIDATION_CODE_KEY))) {
						RiskEvent.bm()
			            	.who(bmUser(usrId))
			            	.event(RISK.Event.Login_Bm)
			            	.result(RISK.Result.Failed)
			            	.reason(RISK.Reason.Verify_Captcha)
		                	.target(usrId)
			            	.message(String.format("用户： %s, 图形验证码输入错误", usrId))
			            	.params(logingInfo) //夹带其他资讯
			            	.submit();
					return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED, "图形验证码输入错误，请重新输入");
				}
			}
			
			IBmUserService userService = this.getDBService(IBmUserService.class);
			
			// 根据登录名获取用户信息
			usrInfo = userService.selectByPrimaryKey(usrId);
			
			// 判断用户是否存在
			if (usrInfo == null) {
				
				RiskEvent.bm()
	            	.who(bmUser(usrId))
	            	.event(RISK.Event.Login_Bm)
	            	.result(RISK.Result.Failed)
	            	.reason(RISK.Reason.Exception)
                	.target(usrId)
	            	.message(String.format("用户： %s, 不存在", usrId))
	            	.params(logingInfo) //夹带其他资讯
	            	.submit();

				AssertUtil.objIsNull(usrInfo, "用户不存在");
			}
			
			// 判断用户状态是否有效
			if (!usrInfo.getUsrSt().equals(BmEnums.UserSt.ST_1.getCode())) {
				
				RiskEvent.bm()
	            	.who(bmUser(usrId))
	            	.event(RISK.Event.Login_Bm)
	            	.result(RISK.Result.Failed)
	            	.reason(RISK.Reason.Disabled)
                	.target(usrId)
	            	.message(String.format("用户： %s, 状态失效", usrId))
	            	.params(logingInfo) //夹带其他资讯
	            	.submit();
				
				AssertUtil.strNotEquals(usrInfo.getUsrSt(), BmEnums.UserSt.ST_1.getCode(), "用户状态失效");
			}
			
			// 判断用户密码是否正确
			String encPwd = PwdUtil.computeMD5Pwd(password);
			if (!usrInfo.getPassword().equals(encPwd)) {
				
				RiskEvent.bm()
	            	.who(bmUser(usrId))
	            	.event(RISK.Event.Login_Bm)
	            	.result(RISK.Result.Failed)
	            	.reason(RISK.Reason.Verify_Passwd)
                	.target(usrId)
	            	.message(String.format("用户： %s, 密码错误", usrId))
	            	.params(logingInfo) //夹带其他资讯
	            	.submit();
				
				throw new BizzException("用户密码错误");
			}
			
			// 判断是否绑定谷歌验证码
			if (Utils.isEmpty(usrInfo.getOtpSecret())) {
				// 未绑定：将原角色权限暂放到OtpSecret栏位，给予一个未绑定谷歌的角色权限
				String tempRoleId = String.valueOf(usrInfo.getRoleId());
				usrInfo.setOtpSecret(tempRoleId);
				usrInfo.setRoleId(unboundGaCOdeRoleId);
				userService.update(usrInfo, false);
			} else {
				if (usrInfo.getRoleId() == unboundGaCOdeRoleId) {
					loginAssistant(usrInfo);
					return commonBO.buildSuccResp();
				} else {
					GoogleAuthenticator ga = new GoogleAuthenticator(usrId, usrInfo.getOtpSecret());
					boolean loginOk = ga.checkCode(gaCode);
					if(!loginOk) {
						
						RiskEvent.bm()
			            	.who(bmUser(usrId))
			            	.event(RISK.Event.Login_Bm)
		                	.result(RISK.Result.Failed)
		                	.reason(RISK.Reason.Verify_Otp)
		                	.target(usrId)
			            	.message(String.format("用户： %s, 谷歌验证码错误", usrId))
			            	.params(logingInfo) //夹带其他资讯
			            	.submit();
						
						throw new BizzException("谷歌验证码错误");
					}
				}
			}

			loginAssistant(usrInfo);

			this.logText(BmEnums.FuncInfo._0000000000.getDesc(), CommonEnums.OpType.LOGIN, "控制台登录");

			return commonBO.buildSuccResp();
		} catch (Exception e) {
			logger.error("用户登录失败:" + usrId, e);
			String errMsg = null;
			if (e instanceof BizzException) {
				errMsg = e.getMessage();
			} else {
				errMsg = "系统错误，请联系管理员";
			}
			return commonBO.buildErrorResp(errMsg);
		}
	}

	private void loginAssistant(BmUserInfo usrInfo) {
		// 构造Session用户信息
		SessionUserInfo sessionUserInfo = new SessionUserInfo();
		// 构建用户登录IP和时间信息
		Date loginTs = new Date();
		HttpServletRequest request = 
			((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String loginIp = request.getRemoteAddr(); // 获取登录IP
		String loginTsStr = DateUtil.dateToStr17(loginTs); // 获取本次登录时间
		String lastLoginIp = usrInfo.getLastLoginIp();
		String lastLoginTsStr = usrInfo.getLastLoginTs();
		// 如果上次登录IP为空，表示该用户是第一次登录
		if (StringUtil.isBlank(lastLoginIp)) {
			lastLoginIp = loginIp;
			lastLoginTsStr = loginTsStr;
		}
		sessionUserInfo.setUsrId(usrInfo.getUsrId());
		sessionUserInfo.setUsrNm(usrInfo.getUsrNm());
		sessionUserInfo.setLoginIp(loginIp);
		sessionUserInfo.setLoginTs(loginTsStr);
		sessionUserInfo.setLastLoginIp(usrInfo.getLastLoginIp());
		sessionUserInfo.setLastLoginTs(lastLoginTsStr);

		// 获取角色相关权限
		boolean isSuperAdmin = BMConfigCache.getSuperAdmin().equals(usrInfo.getUsrId());
		if (!isSuperAdmin) {
			// 如果不是超级管理员，则需要验证用户角色
			int roleId = usrInfo.getRoleId();
			BmRoleInfo roleInfo = AuthDataCache.getInstance().getRoleInfo(roleId);
			AssertUtil.objIsNull(roleInfo, "未给该用户分配角色");
			sessionUserInfo.setRoleId(roleId);
			sessionUserInfo.setRoleNm(roleInfo.getRoleNm());
			Set<String> funcSet = AuthDataCache.getInstance().getRoleFuncSet(roleId);
			sessionUserInfo.setFuncSet(funcSet);
			AssertUtil.objIsNull(sessionUserInfo.getMenuLst(), "该用户分配角色没有权限");
		} else {
			sessionUserInfo.setRoleId(BMConstants.SUPER_ADMIN_ROLE_ID);
			sessionUserInfo.setRoleNm(BMConstants.SUPER_ADMIN_ROLE_NM);
			sessionUserInfo.setFuncSet(AuthDataCache.getInstance().getRoleFuncSet(BMConstants.SUPER_ADMIN_ROLE_ID));
		}

		// 将用户信息存放在Session中
		commonBO.setSessionAttr(BMConstants.SESSION_KEY_USR_INFO, sessionUserInfo);

		// 更新用户的最近登录时间和最近登录IP
		usrInfo.setLastLoginIp(loginIp);
		usrInfo.setLastLoginTs(loginTsStr);
		IBmUserService userService = this.getDBService(IBmUserService.class);
		userService.updateLoginInfo(usrInfo);

		// 构造登录成功日志信息
		StringBuilder loginMsg = new StringBuilder("\n******用户登录成功******");
		loginMsg.append("\n用户ID:" + usrInfo.getUsrId());
		loginMsg.append("\n角色:" + sessionUserInfo.getRoleNm());
		loginMsg.append("\n登录IP:" + loginIp);
		loginMsg.append("\n登录时间:" + loginTsStr);
		loginMsg.append("\n用户权限:\n");
		for (String funcCd : sessionUserInfo.getFuncSet()) {
			loginMsg.append(funcCd + "\n");
		}
		loginMsg.append("********************");
		
		RiskEvent.bm()
	    	.who(bmUser(usrInfo.getUsrId()))
	    	.event(RISK.Event.Login_Bm)
	    	.result(RISK.Result.Ok)
	    	.target(usrInfo.getUsrId())
	    	.message(String.format("用户： %s, 登入成功！", usrInfo.getUsrId()))
	    	.params(INTER_MSG.reqIp, loginIp)
	    	.submit();
		
		logger.info(loginMsg.toString());
	}

	@RequestMapping(value = "/logout.do")
	public String logout(Model model) {
		try {
			HttpServletRequest request = 
				((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			HttpSession session = request.getSession(false);
			if (session != null) {
				try {
					session.invalidate();
				} catch (IllegalStateException e) {
					logger.error("session已失效", e);
				}
			}
		} catch (Exception e) {
			logger.error("注销失败", e);
		}
		return "logout";
	}
}