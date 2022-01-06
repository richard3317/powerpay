package com.icpay.payment.mer.web.interceptor;

import static com.icpay.payment.risk.MerUser.merUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.constants.Names.MSG;
import com.icpay.payment.common.constants.RspCd;
import com.icpay.payment.common.data.svc.RiskUtilService;
import com.icpay.payment.common.data.svc.impl.RiskUtilServiceImpl;
import com.icpay.payment.common.enums.MerEnums.MerUserRole;
import com.icpay.payment.common.enums.MerEnums.MerUserState;
import com.icpay.payment.common.utils.EncryptUtil;
import com.icpay.payment.common.utils.PropUtils;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.mer.bo.CommonBO;
import com.icpay.payment.mer.cache.MerConfigCache;
import com.icpay.payment.mer.cache.MerParamCache;
import com.icpay.payment.mer.constants.MerConstants;
import com.icpay.payment.mer.constants.MerConstants.LastLoginState;
import com.icpay.payment.mer.constants.MerConstants.LoginState;
import com.icpay.payment.mer.constants.MerConstants.Roles;
import com.icpay.payment.mer.constants.MerConstants.SecretState;
import com.icpay.payment.mer.session.LocalSession;
import com.icpay.payment.mer.session.LocalSessionManager;
import com.icpay.payment.mer.session.SessionKeys;
import com.icpay.payment.mer.util.I18nMsgUtils;
import com.icpay.payment.mer.util.MerUtils;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;
import com.icpay.payment.service.SessionUtilService;

public class SessionCheckInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = Logger.getLogger(SessionCheckInterceptor.class);
	private static boolean RESET_VISIT_TIME=true;

	@Autowired
	protected CommonBO commonBO;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			String requestIp = ""+MerUtils.getRemoteIp(request);
			
			String requestIps= ""+MerUtils.getRemoteIps(request);
			if (Utils.isEmpty(requestIps))
				requestIps = requestIp;
			List<String> reqIpList = getReqIpList(requestIps);
			
            String sessionId = null;
			HttpSession hsession= request.getSession();
			if (hsession!=null) {
				sessionId = hsession.getId();
				Utils.logAttribute(Utils.K_SESSIONID, sessionId, true);
			}

        	boolean ajaxRequest = isAjaxRequest(request);
        	String reqUri = request.getRequestURI();
        	String qryStr = request.getQueryString();
        	
            //if (ignoreCheck(handler)) return true; //移到后面判断

    		try {


    			String loginMchnt = request.getParameter("mchntCd");
    			String loginUser = request.getParameter("userId");
    			String mchntCd = null;
    			String mchntUser = null;
    			
				mchntCd = Utils.getFirstAvailable(getMchntCode(sessionId),loginMchnt);
				mchntUser = Utils.getFirstAvailable(getMchntUserId(sessionId),loginUser);
    			
		        Map<String,String> eventParams = //其他资讯(Event夹带用)
		        		Utils.newMap(
		        				INTER_MSG.sessionId, sessionId,
		        				INTER_MSG.requestUri , reqUri,
		        				MSG.merId, mchntCd,
		        				MSG.userId, mchntUser,
		        				INTER_MSG.reqIp, requestIp
		        				);
		        
		        // 检查暂时被禁用的IP或用户
		        if (!this.checkRiskForTempBlock(eventParams, sessionId, mchntCd, mchntUser, requestIps, reqIpList)) {
					if (ajaxRequest) {
						response.setContentType("text/html;charset=UTF-8");
						PrintWriter writer = response.getWriter();
			        	writer.write("expired");
			        	writer.flush();
			        	writer.close();
					} else {
						RequestDispatcher rd = request.getRequestDispatcher(MerConstants.PAGE_EXPIRED);
						rd.forward(request, response);
					}
					return false;
		        }
		        
		        if (ignoreCheck(handler)) return true;

    			if (sessionId!=null) {

    				mchntCd = getMchntCode(sessionId);
    				logger.debug(String.format("[security] 资源存取检查(mchntCd: %s, sessionId: %s, requestIp: %s, requestURI: %s, queryStr: %s)", mchntCd, sessionId, requestIp, reqUri, qryStr));
    				//logger.debug(String.format("[security] 资源存取检查(mchntCd: %s, sessionId: %s, requestIp: %s, requestURI: %s, queryStr: %s;\n  request: %s)", mchntCd, sessionId, requestIp, reqUri, qryStr, request.getParameterMap()));


    				if (! this.checkFreq(mchntCd, sessionId, requestIp, reqUri)) {
    					//TODO Risk event
    		           	RiskEvent.mer()
    		               	.who(merUser(mchntCd, getMchntUserId(sessionId)))
    		               	.event(RISK.Event.SessionCheck)
    		               	.result(RISK.Result.Failed)
    		               	.reason(RISK.Reason.Risk_Freq)
    		               	.message("资源存取过于频繁(mchntCd: %s, sessionId: %s, requestIp: %s, requestURI: %s)", mchntCd, sessionId, requestIp, reqUri)
    		               	.params(eventParams) //夹带其他资讯
    		               	.submit();

						logger.warn(String.format("[security] 资源存取过于频繁(mchntCd: %s, sessionId: %s, requestIp: %s, requestURI: %s)", mchntCd, sessionId, requestIp, reqUri));
						//request.setAttribute("error_message", "系统忙碌中，请稍候再试");
						//request.getRequestDispatcher("/err_msg.jsp").forward(request, response);
						
						String value = I18nMsgUtils.getMessageWithDefault(
								I18nMsgUtils.ICPAY_MER, 
								I18nMsgUtils.getLanguage(request),
								I18nMsgUtils.getKeyCombine( this.getClass().getSimpleName(),"系统忙碌中，请稍候再试"),"系统忙碌中，请稍候再试");
						
						return toError(value, request, response ,ajaxRequest);
    				}

        			if (isLogined(sessionId, requestIp)) {

        				if (!this.checkGaSession(sessionId))
        					{
        					response.sendRedirect("/mer");
        						return false;
        						}

						//如果尚未安全初始化，则只允许存取SecureInitSessionCheck标记的资源
	    				if (! isUserInRoles(sessionId, new MerUserRole[] {MerUserRole.PaymentUser})) {
							if (isSecureInitState(sessionId)) {//用户安全状态是初始阶段
								if (secureInitSessionCheck(handler)) {//表示安全初始化页面
			    		           	RiskEvent.mer()
			    		               	.who(merUser(mchntCd, getMchntUserId(sessionId)))
			    		               	.event(RISK.Event.Security_Init)
			    		               	.message("要求绑定二维码，进行初始化安全设置(mchntCd: %s, sessionId: %s, requestIp: %s, requestURI: %s, queryStr: %s)", mchntCd, sessionId, requestIp, reqUri, qryStr)
			    		               	.params(eventParams) //夹带其他资讯
			    		               	.submit();
			    		           	logger.info(String.format("[security] 初始化安全设置(mchntCd: %s, sessionId: %s, requestIp: %s, requestURI: %s, queryStr: %s)", mchntCd, sessionId, requestIp, reqUri, qryStr));
									return true;
								}
								else {
			    		           	RiskEvent.mer()
			    		               	.who(merUser(mchntCd, getMchntUserId(sessionId)))
			    		               	.event(RISK.Event.SessionCheck)
			    		               	.result(RISK.Result.Failed)
			    		               	.reason(RISK.Reason.Access_denied)
			    		               	.message("限制存取，必须先进行安全设置(mchntCd: %s, sessionId: %s, requestIp: %s, requestURI: %s, queryStr: %s)", mchntCd, sessionId, requestIp, reqUri, qryStr)
			    		               	.params(eventParams) //夹带其他资讯
			    		               	.submit();
			    		           	logger.info(String.format("[security] 限制存取，必须进行安全设置 (mchntCd: %s, sessionId: %s, requestIp: %s, requestURI: %s, queryStr: %s)", mchntCd, sessionId, requestIp, reqUri, qryStr));
									String value = I18nMsgUtils.getMessageWithDefault(
		    								I18nMsgUtils.ICPAY_MER, 
		    								I18nMsgUtils.getLanguage(request),
		    								I18nMsgUtils.getKeyCombine( this.getClass().getSimpleName(),"限制存取，请进行安全设置!"),"限制存取，请进行安全设置!");
									return toError(value, request, response ,ajaxRequest);
								}
							}
	    				}

	    				//檢查 handler 是否只允許 admin 存取
        				if (adminCheck(handler)) {
        					if (!isAdminUser(sessionId)) {
        						//TODO Risk event
		    		           	RiskEvent.mer()
	    		               	.who(merUser(mchntCd, getMchntUserId(sessionId)))
	    		               	.event(RISK.Event.SessionCheck)
	    		               	.result(RISK.Result.Failed)
	    		               	.reason(RISK.Reason.Permission_denied)
	    		               	.message("无权限存取Admin管理页面(mchntCd: %s, sessionId: %s, requestIp: %s, requestURI: %s, queryStr: %s)", mchntCd, sessionId, requestIp, reqUri, qryStr)
	    		               	.params(eventParams) //夹带其他资讯
	    		               	.submit();

        						logger.warn(String.format("[security] 非ADMIN成员无法存取此资源(mchntCd: %s, sessionId: %s, requestIp: %s, requestURI: %s, queryStr: %s)", mchntCd, sessionId, requestIp, reqUri, qryStr));
        						String value = I18nMsgUtils.getMessageWithDefault(
	    								I18nMsgUtils.ICPAY_MER, 
	    								I18nMsgUtils.getLanguage(request),
	    								I18nMsgUtils.getKeyCombine( this.getClass().getSimpleName(),"限制存取，必须是管理员身分!"),"限制存取，必须是管理员身分!");
        						return toError(value, request, response ,ajaxRequest);
        					}
        				}

        				//檢查目前user的角色是否符合 handler 的角色要求
        				MerUserRole[] rolesReq=rolesCheck(handler);
        				if (rolesReq!=null) { //要求指定角色
        					if (!isUserInRoles(sessionId, rolesReq)) {
        						//TODO Risk event
		    		           	RiskEvent.mer()
	    		               	.who(merUser(mchntCd, getMchntUserId(sessionId)))
	    		               	.event(RISK.Event.SessionCheck)
	    		               	.result(RISK.Result.Failed)
	    		               	.reason(RISK.Reason.Permission_denied)
	    		               	.message("此页面存取权限不足(mchntCd: %s, sessionId: %s, requestIp: %s, requestURI: %s, queryStr: %s)", mchntCd, sessionId, requestIp, reqUri, qryStr)
	    		               	.params(eventParams) //夹带其他资讯
	    		               	.submit();

        						logger.warn(String.format("[security] 存取权限受限(mchntCd: %s, sessionId: %s, requestIp: %s, requestURI: %s, queryStr: %s)", mchntCd, sessionId, requestIp, reqUri, qryStr));
        						return toError("限制存取!", request, response ,ajaxRequest);
        					}
        				}

        				//檢查user是否被鎖
        				String locked = MerUserState.Locked.getCode();
        				String userState = getUserState(sessionId);
        				if (StringUtils.equals(locked, userState)) {

        					//TODO Risk event
        					RiskEvent.mer()
    		               	.who(merUser(mchntCd, getMchntUserId(sessionId)))
    		               	.event(RISK.Event.User_Id_Disable)
    		               	.result(RISK.Result.Failed)
    		               	.reason(RISK.Reason.Locked)
    		               	.message("限制存取，帐户已锁定(mchntCd: %s, sessionId: %s, requestIp: %s, requestURI: %s, queryStr: %s)", mchntCd, sessionId, requestIp, reqUri, qryStr)
    		               	.params(eventParams) //夹带其他资讯
    		               	.submit();

    						logger.warn(String.format("[security] 限制存取，帐户已锁定!(mchntCd: %s, sessionId: %s, requestIp: %s, requestURI: %s, queryStr: %s)", mchntCd, sessionId, requestIp, reqUri, qryStr));
    						return toError("限制存取，帐户已锁定!", request, response ,ajaxRequest);
        				}

        				//上次資源拜訪時間放到Redis (key: mer.${mer_id}.${user_id}.lastVisitTime)
        				String lastVisitTimeKey = "mer." + mchntCd + "." + getMchntUserId(sessionId) + ".lastVisitTime";
        				String lastVisitTimeValue = "" + System.currentTimeMillis();
        				this.putGlobalSessionData(lastVisitTimeKey, lastVisitTimeValue, -1);

        				return true;
        			}
    			}

	           	RiskEvent.mer()
               	.who(merUser(mchntCd, getMchntUserId(sessionId)))
               	.event(RISK.Event.SessionCheck)
               	.result(RISK.Result.Failed)
               	.reason(RISK.Reason.Access_denied)
               	.message("未登入却存取限制页面(mchntCd: %s, sessionId: %s, requestIp: %s, requestURI: %s, queryStr: %s)", mchntCd, sessionId, requestIp, reqUri, qryStr)
               	.params(eventParams) //夹带其他资讯
               	.submit();

				logger.warn(String.format("[security] 未登入无法存取此资源(mchntCd: %s, sessionId: %s, requestIp: %s, requestURI: %s, queryStr: %s)", mchntCd, sessionId, requestIp, reqUri, qryStr));
				if (ajaxRequest) {
					response.setContentType("text/html;charset=UTF-8");
					PrintWriter writer = response.getWriter();
		        	writer.write("expired");
		        	writer.flush();
		        	writer.close();
				} else {
					RequestDispatcher rd = request.getRequestDispatcher(MerConstants.PAGE_EXPIRED);
					rd.forward(request, response);
				}
				return false;

    		} catch (Exception e) {
    			logger.error("会话检查异常！", e);
    			return false;
    		}
        } else {
            return super.preHandle(request, response, handler);
        }
	}
	
	
	protected void error(String fmt, Object...args) {
		error(String.format(fmt, args));
	}

	protected void error(String message) {
		logger.error(message);
	}	
	
	private static RiskUtilService _riskSvc=null;
	
	protected static  RiskUtilService riskSvc() {
		if (_riskSvc==null) {
			_riskSvc = ServiceProxy.getService(RiskUtilService.class);
			if (_riskSvc==null) 
				_riskSvc = new RiskUtilServiceImpl();
		}
		return _riskSvc;
	}
	
	/**
	 * 检查是否暂停短期风控
	 * @param accName
	 * @param accNum
	 * @param mchntCd
	 * @param eventParams
	 * @param request
	 * @return
	 */
	protected boolean checkRiskForUnblock(Map<String, String> mergedCtx, String sessionId,
			String mchntCd, String userId, String reqIps, List<String> reqIpList
			) {
		if (!Utils.isEmpty(mchntCd)) {
			if (riskSvc().isUnblocked(mchntCd)) return true;
		}
		
		// IP 检查
		if ((! Utils.isEmpty(reqIps)) && (! Constant.INTERNAL_IP.equals(reqIps))) {
			if (!Utils.isEmpty(reqIpList)) {
				for (String ip : reqIpList) {
					if (riskSvc().isUnblocked(ip)) return true;
				}
			}
		}
		
//		if (!Utils.isEmpty(accName) && !Utils.isEmpty(accNum)) {
//			String accNameAndNum = String.format("%s|%s", accName, accNum);
//			if (riskSvc().isUnblocked(accNameAndNum)) return true;
//		}
		
		return false;
	}	
	
	protected boolean checkRiskForTempBlock(Map<String, String> mergedCtx, String sessionId,
			String mchntCd, String userId, String reqIps, List<String> reqIpList
			) {
		
		//if (true) return true;
		
		if (checkRiskForUnblock(mergedCtx, sessionId, mchntCd, userId, reqIps, reqIpList))
			return true;
		
		// IP 检查
		if ((! Utils.isEmpty(reqIps)) && (! Constant.INTERNAL_IP.equals(reqIps))) {
			if (!Utils.isEmpty(reqIpList)) {
				for (String ip : reqIpList) {
					if (!riskSvc().isValid(ip)) {
						this.error("[Risk][Failed][%s] 请求IP存在风险，暂被除能，商户号=%s, 用户=%s, IPs=%s", RspCd.Z_0513_001, mchntCd, userId, reqIps);
						RiskEvent.mer()
							.event(RISK.Event.SessionCheck)
							.message("请求IP存在风险，暂被除能，商户号=%s, 用户=%s, IPs=%s", mchntCd, userId, reqIps)
							.result(RISK.Result.Failed)
							.reason(RISK.Reason.Risk_Black_Ip)
							.params(mergedCtx)
							.submit();					
						return false;
					}
				}
			}
			//if(! riskSvc().isValid(reqIps)) return false;
		}
		
		// 检查商户号
		if (!Utils.isEmpty(mchntCd) && !riskSvc().isValid(mchntCd)) {
			this.error("[Risk][Failed][%s] 商户存在风险，暂被除能，商户号=%s, 用户=%s, IPs=%s", RspCd.Z_0513_021, mchntCd, userId, reqIps);
			RiskEvent.txnControl()
				.event(RISK.Event.SessionCheck)
				.message("商户存在风险，暂被除能，商户号=%s, 用户=%s, IPs=%s",mchntCd, userId, reqIps)
				.result(RISK.Result.Failed)
				.reason(RISK.Reason.Risk_Black_Mer)
				.params(mergedCtx)
				.submit();					
			return false;
		}
		
		// 检查用户ID
		String mchntUser = String.format("%s|%s", mchntCd, userId);
		if (!Utils.isEmpty(mchntCd) && !Utils.isEmpty(userId) && !riskSvc().isValid(mchntUser)) {
			this.error("[Risk][Failed][%s] 商户用户存在风险，暂被除能，商户号=%s, 用户=%s, IPs=%s", RspCd.Z_0513_023, mchntCd, userId, reqIps);
			RiskEvent.txnControl()
				.event(RISK.Event.SessionCheck)
				.message("商户用户存在风险，暂被除能，商户号=%s, 用户=%s, IPs=%s", mchntCd, userId, reqIps)
				.result(RISK.Result.Failed)
				.reason(RISK.Reason.Risk_Black_Mer_User)
				.params(mergedCtx)
				.submit();					
			return false;
		}
		
		return true;
	}		

	protected String getMchntUserId(String sessionId) {
		MchntUserInfo m =getMchntUserInfo(sessionId);
		if (m==null) return null;
		return m.getUserId();
	}

	/**
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected boolean toError(String errorMsg, HttpServletRequest request, HttpServletResponse response, boolean isAjaxRequest)
			throws ServletException, IOException {
		sleep(200);
		if (isAjaxRequest) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
        	writer.write(String.format("{\"respCode\":\"9001\", \"respMsg\":\"%s\"}", errorMsg));
        	writer.flush();
        	writer.close();
		}
		else {
			request.setAttribute("errorMsg", errorMsg);
			RequestDispatcher rd = request.getRequestDispatcher("/error");
			rd.forward(request, response);
		}

		return false;
	}

	protected void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	protected boolean ignoreCheck(Object handler) {
		if (handler==null) return false;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        IgnoreSessionCheck ignoreCheck = method.getAnnotation(IgnoreSessionCheck.class);
        return (ignoreCheck!=null);
	}

	protected boolean adminCheck(Object handler) {
		if (handler==null) return false;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        AdminSessionCheck adminCheck = method.getAnnotation(AdminSessionCheck.class);
        return (adminCheck!=null);
	}

	protected MerUserRole[] rolesCheck(Object handler) {
		if (handler==null) return null;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        RolesSessionCheck rolesAnn = method.getAnnotation(RolesSessionCheck.class);
        if (rolesAnn==null) return null;
        return rolesAnn.roles();
	}


	protected boolean secureInitSessionCheck(Object handler) {
		if (handler==null) return false;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        SecureInitSessionCheck chk = method.getAnnotation(SecureInitSessionCheck.class);
        return (chk!=null);
	}

	protected boolean isAjaxRequest(HttpServletRequest request) {
		if (request==null) return false;
		String requestWith = ""+request.getHeader("X-Requested-With");
		requestWith = requestWith.toLowerCase();
		return ("xmlhttprequest".equals(requestWith));
		//return request.getHeader("X-Requested-With") != null ? true : false;
	}

    /**
     * 获取最后谷歌认证成功的时间
     * @return
     */
    protected long getLastGaTime(String sessionId) {
    	LocalSession s = LocalSessionManager.getSession(sessionId);
		if (s==null) return -1L;
		Long lastGaTime = (Long) s.getSessionData(SessionKeys.Last_GoogleAuth_Time.getCode(), false);
    	if (lastGaTime==null) return -1L;
    	return lastGaTime;
    }

    /**
     * 判断谷歌认证时间是否过期
     * @param lastGaTime
     * @return
     */
    protected boolean isGaSessionExpired(String sessionId) {
    	long lastGaTime = getLastGaTime(sessionId);
    	long now = System.currentTimeMillis();
    	long delta = (now-lastGaTime);

//    	Log.debug()
//    		.message("[SessionCheckInterceptor][isGaSessionExpired]%s sessionId=%s, gaActived=%s, gaSessionTimeout=%s; Don't care: %s",
//    				(lastGaTime>0)&&(delta > MerParamCache.GA_Session_Expire_Time) ? "***EXPIRED***":"",
//    						sessionId, delta, MerParamCache.GA_Session_Expire_Time, (lastGaTime<0))
//    		.submit(); //DEBUG

    	if (lastGaTime<0) return false;
    	return delta > MerParamCache.GA_Session_Expire_Time;
    }

    protected boolean checkGaSession(String sessionId) {
    	boolean res = isGaSessionExpired(sessionId);
    	if (res) { //已经过期
    		resetLogin(sessionId);
    	}
    	return ! res;
    }

    protected void resetLogin(String sessionId) {
    	LocalSession s = LocalSessionManager.getSession(sessionId);
    	if (s==null) return;
    	s.putSessionData(SessionKeys.LOGIN_STATE.getCode(), LoginState.FAILED);
        //s.putSessionData(SessionKeys.ROLE.getCode(), null);
    	s.removeSessionData(SessionKeys.ROLE.getCode());
        //s.putSessionData(SessionKeys.MCHNT_INFO.getCode(), null);
        s.removeSessionData(SessionKeys.MCHNT_INFO.getCode());
        //s.putSessionData(SessionKeys.Last_GoogleAuth_Time.getCode(), null);
        s.removeSessionData(SessionKeys.Last_GoogleAuth_Time.getCode());
        MchntUserInfo sessionMchntUserInfo = (MchntUserInfo)s.getSessionData(SessionKeys.MCHNT_USER_INFO.getCode());
        if (sessionMchntUserInfo != null) {
            //寫入Redis
			//登入狀態: mer.${mer_id}.${user_id}.lastLoginState = 0 (0=未登入/1=已登入)
			String lastLoginStateKey = "mer." + sessionMchntUserInfo.getMchntCd() + "." + sessionMchntUserInfo.getUserId() + ".lastLoginState";
			this.putGlobalSessionData(lastLoginStateKey, LastLoginState.NOT_LOGGED_IN, -1);
        }
    }

	/**
	 * 检查商户是否登录
	 * @param sessionId
	 * @return
	 */
	private boolean isLogined(String sessionId, String reqIp) {
		boolean res=false;
		if (StringUtil.isBlank(sessionId)) {
			logger.debug(String.format("[security][isLogined] FAILED! sessionId: %s, reqIp: %s;", sessionId, reqIp));
			return false;
		}
		LocalSession s = LocalSessionManager.getSession(sessionId);
		if (s==null) {
			logger.debug(String.format("[security][isLogined] FAILED! sessionId: %s, reqIp: %s;", sessionId, reqIp));
			return false;
		}
		Object loginState = s.getSessionData(SessionKeys.LOGIN_STATE.getCode(), RESET_VISIT_TIME);
		Object loginRole = s.getSessionData(SessionKeys.ROLE.getCode(), RESET_VISIT_TIME);
		String orgReqIp = ""+s.getSessionData(SessionKeys.CLIENTIP.getCode(), RESET_VISIT_TIME);
		Object mchnt = s.getSessionData(SessionKeys.MCHNT_INFO.getCode(), RESET_VISIT_TIME);
		String mchntCode= "";
		if (mchnt!=null) {
			mchntCode = ((MchntInfo) mchnt).getMchntCd();
		}
		logger.debug(String.format("[security][isLogined] loginState: %s, loginRole: %s, orgReqIp:%s , mchnt:%s;", loginState, loginRole, orgReqIp, mchntCode));
		res = (!Utils.isEmpty(loginRole)) && LoginState.OK.equals(loginState) && (orgReqIp.equals(reqIp)) && (! Utils.isEmpty(mchnt));
		logger.debug(String.format("[security][isLogined] %s! sessionId: %s, reqIp: %s;", (res ? "OK" : "FAILED"), sessionId, reqIp));
		return res;
	}

	private String getLoginRole(String sessionId) {
		if (StringUtil.isBlank(sessionId)) {
			return null;
		}
		LocalSession s = LocalSessionManager.getSession(sessionId);
		if (s==null) return null;
		Object loginRole = s.getSessionData(SessionKeys.ROLE.getCode(), RESET_VISIT_TIME);
		return (String) loginRole;
	}

	private MchntInfo getMchntInfo(String sessionId) {
		if (StringUtil.isBlank(sessionId)) {
			return null;
		}
		LocalSession s = LocalSessionManager.getSession(sessionId);
		if (s==null) return null;
		Object obj = s.getSessionData(SessionKeys.MCHNT_INFO.getCode(), RESET_VISIT_TIME);
		return (MchntInfo) obj;
	}
	private MchntUserInfo getMchntUserInfo(String sessionId) {
		if (StringUtil.isBlank(sessionId)) {
			return null;
		}
		LocalSession s = LocalSessionManager.getSession(sessionId);
		if (s==null) return null;
		Object obj = s.getSessionData(SessionKeys.MCHNT_USER_INFO.getCode(), RESET_VISIT_TIME);
		return (MchntUserInfo) obj;
	}

	private String getSecretState(String sessionId) {
		if (StringUtil.isBlank(sessionId)) {
			return null;
		}
		LocalSession s = LocalSessionManager.getSession(sessionId);
		if (s==null) return null;
		Object obj = s.getSessionData(SessionKeys.SECRET_STATE.getCode(), RESET_VISIT_TIME);
		return (String) obj;
	}

	private String getMchntCode(String sessionId) {
		MchntInfo m = getMchntInfo(sessionId);
		if (m==null) return null;
		return m.getMchntCd();
	}

	private String getUserState(String sessionId) {
		MchntUserInfo m = getMchntUserInfo(sessionId);
		if (m==null) return null;
		return m.getUserState();
	}

	private boolean isUserInRoles(String sessionId, MerUserRole[] roles) {
		String cr= getLoginRole(sessionId);
		for (MerUserRole r : roles){
			if (cr==null) return false;
			if (r.getCode().equals(cr)) return true;
		}
		return false;
	}

	private boolean isAdminUser(String sessionId) {
		return Roles.ADMIN.equals(getLoginRole(sessionId));
	}

//	private boolean isSecureInitState(String sessionId) {
//		//SecretState.INIT.equals(secretState)
//		MchntInfo mchnt =getMchntInfo(sessionId);
//		if (mchnt==null) return false;
//		return SecretState.INIT.equals(getSecretState(mchnt.getSecretInitState()));
//	}
	private boolean isSecureInitState(String sessionId) {
		//SecretState.INIT.equals(secretState)
//		MchntUserInfo mchnt =getMchntUserInfo(sessionId);
//		if (mchnt==null) return true;
		return SecretState.INIT.equals(getSecretState(sessionId));
	}

//	protected String getSecretState(String stateVal) {
//		if (isEmpty(stateVal)) return SecretState.INIT;
//		return stateVal;
//	}

	protected boolean isEmpty(Object obj) {
		if (obj==null) return true;
		return ("".equals(obj.toString()));
	}

	/////////////////////////////////////////////////////
	// Global Session & Freq check support

	protected static String GlobalSessionCatalog="MER";
	protected void putGlobalSessionData(String key, String value, int expiresInSecs) {
		SessionUtilService svc = ServiceProxy.getService(SessionUtilService.class);
		if (expiresInSecs>=0)
			svc.putWithExpires(GlobalSessionCatalog, key, value, expiresInSecs);
		else
			svc.put(GlobalSessionCatalog, key, value);
	}

	protected String getGlobalSessionData(String key, String defaultVal) {
		SessionUtilService svc = ServiceProxy.getService(SessionUtilService.class);
		return svc.getWithDefault(GlobalSessionCatalog, key, defaultVal);
	}

	protected String strVal(Object obj) {
		if (obj==null) return "";
		return obj.toString();
	}

	protected String md5(String... strs) {
		if (Utils.isEmpty(strs))
			return "";
		StringBuffer buf = new StringBuffer();
		for(String s: strs) {
			buf.append(s);
		}
		return EncryptUtil.md5(buf.toString());
	}

	protected static int ResMinAccessInterval=getResMinAccessInterval(3);
	protected static String KeyPrefix_LastAccess="lastMerAccess.";
	protected boolean checkFreq(String mchntCd, String sessionId, String requestIp, String reqUri) {
		String key=KeyPrefix_LastAccess+md5(mchntCd, sessionId, requestIp, reqUri);
		long tNow = System.currentTimeMillis();
		//String strLastTime=
		String last = this.getGlobalSessionData(key, null);
		boolean res = (last==null);
		if (res)
			this.putGlobalSessionData(key, ""+tNow, ResMinAccessInterval);

		return res;
	}

	protected static int intVal(String val, int defVal) {
		if (val==null) return defVal;
		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
			return defVal;
		}
	}

	/**
	 * 资源存取允许的最小存取间隔
	 * @param defaultVal
	 * @return
	 */
	protected static int getResMinAccessInterval(int defaultVal) {
		int r=defaultVal;
		try {
			String s =PropUtils.getProperty("MerConfig.properties", "resMinAccessInterval", ""+defaultVal);
			r = Integer.parseInt(s);
			//r = intVal(MerConfigCache.getConfig("ResMinAccessInterval"), defaultVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	protected List<String> getReqIpList(String reqIps){
		List<String> addrList = new ArrayList<>();
		String[] addrs = Utils.strSplit(reqIps, ";");
		for(String ip: addrs) {
			ip = StringUtil.trimStr(ip);
			if (!isInternalAddr(ip))
				addrList.add(ip);
		}
		return addrList;
	}
	
	protected static String INTERNAL_ADDR_PREFIX="";
	protected static String[] INTERNAL_ADDR_PREFIXS=new String[0];
	
	static {
		try {
			INTERNAL_ADDR_PREFIX=MerConfigCache.getConfig("internal.ip.prefixs", INTERNAL_ADDR_PREFIX);
			INTERNAL_ADDR_PREFIXS=Utils.strSplit(INTERNAL_ADDR_PREFIX, ";");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	private boolean isInternalAddr(String addr) {
		if (Utils.isEmpty(addr)) return true;
		boolean r = Utils.isInSet(addr, Constant.INTERNAL_IP, "localhost", "127.0.0.1", "0:0:0:0:0:0:0:1");
		if (r) return true;

		if (!Utils.isEmpty(INTERNAL_ADDR_PREFIXS))
			for(String prefix:INTERNAL_ADDR_PREFIXS) {
				if ((!Utils.isEmpty(prefix) && addr.startsWith(prefix))) return true;
			}

		return false;
	}	

}
