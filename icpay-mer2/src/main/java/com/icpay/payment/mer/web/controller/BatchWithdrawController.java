package com.icpay.payment.mer.web.controller;

import static com.icpay.payment.risk.MerUser.merUser;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.gauth.GoogleAuthenticator;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.constants.Constant.TxnSource;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.constants.Names.MSG;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.AccEnums;
import com.icpay.payment.common.enums.AjaxRespEnums;
import com.icpay.payment.common.enums.MerEnums.MerUserRole;
import com.icpay.payment.common.enums.MerEnums.MerUserState;
import com.icpay.payment.common.exception.SystemException;
import com.icpay.payment.common.utils.Amount;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EncryptUtil;
import com.icpay.payment.common.utils.PwdUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.BankNums;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfo;
import com.icpay.payment.db.service.IMchntAccountService;
import com.icpay.payment.gateway.msgonl.MsgRespHelper;
import com.icpay.payment.mer.bo.MchntAccountBO;
import com.icpay.payment.mer.bo.MchntBO;
import com.icpay.payment.mer.cache.BankInfoCaChe;
import com.icpay.payment.mer.cache.MerConfigCache;
import com.icpay.payment.mer.cache.MerParamCache;
import com.icpay.payment.mer.constants.MerConstants;
import com.icpay.payment.mer.session.SessionKeys;
import com.icpay.payment.mer.util.EnumLangUtil;
import com.icpay.payment.mer.util.GoogleAuthenticatorUtils;
import com.icpay.payment.mer.util.I18nMsgUtils;
import com.icpay.payment.mer.util.MerUtils;
import com.icpay.payment.mer.util.TextExport;
import com.icpay.payment.mer.web.interceptor.RolesSessionCheck;
import com.icpay.payment.risk.MerUser;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;
import com.icpay.payment.service.client.InternalGatewayClient;

import com.icpay.payment.common.utils.Converter;

@Controller
@RequestMapping("/batchWithdraw")
public class BatchWithdrawController extends BaseController {

	@Autowired
	private MchntAccountBO mchntAccountBO;
	
	private static int SUMBITSUM = 50;

	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {

		}
	};

	@Autowired
	private MchntBO mchntBO;

	/***
	 * 取现页面
	 * @return
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.SuperUser})
	@RequestMapping("/withdrawMng")
	public String withdrawal(Model model, HttpServletRequest req) {
		String lang = I18nMsgUtils.getLanguage(req);
		String mchntCd =commonBO.getMchntCd();
		String secretFlag ="true";
		if(!commonBO.validateSecret(mchntCd)) {
			secretFlag ="false";
		}
		model.addAttribute("role", SessionKeys.ROLE.getCode());
		model.addAttribute("secretFlag", secretFlag);
		model.addAttribute("loginType", commonBO.getLoginType());
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		
		//人民币账户 (批量代付，先保留原逻辑，单一币别)
		//(現行多幣別機制，此檢核先拔掉，進入批量取現頁面在選擇幣別，會在檢核是否有該幣別的商戶帳戶)
//		MerAccountInfo mchntAcct_CNY = mchntAccountBO.getMchntAccount(mchntCd, "156");
//		AssertUtil.objIsNull(mchntAcct_CNY, mappingI18n(this.getClass().getSimpleName(), "未找到商户账户", lang) + "(CNY)");
//		AssertUtil.strNotEquals(mchntAcct_CNY.getState(), AccEnums.AccSt.ST_1.getCode(), 
//				mappingI18n(this.getClass().getSimpleName(),"商户账户状态无效", lang));
//		model.addAttribute("availableBalance", StringUtil.formateAmt(String.valueOf(mchntAcct_CNY.getAvailableBalance())));
//		model.addAttribute("frozenT1Balance", StringUtil.formateAmt(String.valueOf(mchntAcct_CNY.getFrozenT1Balance())));
//		model.addAttribute("frozenBalance", StringUtil.formateAmt(String.valueOf(mchntAcct_CNY.getFrozenBalance())));
//
//		long totalBalance= sum(mchntAcct_CNY.getAvailableBalance(),mchntAcct_CNY.getFrozenBalance(),mchntAcct_CNY.getFrozenT1Balance());
//		model.addAttribute("totalBalance", StringUtil.formateAmt(String.valueOf(totalBalance)));

		model.addAttribute("mchntCd", mchntCd);
		model.addAttribute("rondom", String.valueOf((new Date()).getTime()));
		return "batchWithdraw";
	}

	protected boolean checkAdminGAuth(String gaCode) {
		if (Utils.isEmpty(gaCode)) return false;
		if (Utils.isEmpty(commonBO)) return false;
		MchntInfo merInfo = this.commonBO.getLoginMer();
		if (Utils.isEmpty(merInfo)) return false;
		if (Utils.isEmpty(merInfo.getOptSecretAdmin())) return false;
		//GoogleAuthenticator ga = new GoogleAuthenticator(merInfo.getMchntCd(), merInfo.getOptSecretAdmin());
		//return ga.checkCode(gaCode);
        return this.checkGaCode(gaCode, merInfo.getMchntCd(), merInfo.getOptSecretAdmin(), false);
	}

	/**
	 * 取现操作
	 * @param model
	 * @return
	 * @throws SystemException
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.SuperUser})
	@RequestMapping(value = "/submitWithdraw.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult submitWithdraw(HttpServletRequest request, HttpServletResponse response,String withdrawList,
				String gaCode,String secretHash,String validateCode,String currencyCode) throws SystemException {
		String lang = I18nMsgUtils.getLanguage(request);
        String siteId = request.getSession().getAttribute("siteId").toString();

		AssertUtil.strIsBlank(validateCode, "validateCode is empty");
		AssertUtil.strIsBlank(withdrawList, "withdrawList is blank.");
		String requestIp = ""+MerUtils.getRemoteIp(request);
		info(String.format("[批量代付请求]\n%s, \n 请求ip: %s, 图形验证码：%s, 谷歌验证码: %s" ,  withdrawList , requestIp,validateCode,gaCode));

		String mchntCd = commonBO.getMchntCd();

        Map<String,String> eventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/submitWithdraw.do" ,
        				MSG.merId, mchntCd,
        				MSG.userId, commonBO.getMchntUser().getUserId(),
        				INTER_MSG.reqIp, requestIp,
        				"withdrawList", withdrawList,
        				INTER_MSG.siteId, siteId
        				);

         if (!validateClientIp(mchntCd, requestIp, request)) {

         	warn("商户： %s, 用户： %s, 登入IP： %s, 登入IP不合法 " , mchntCd, commonBO.getMchntUser().getUserId(), requestIp);

           	RiskEvent.mer()
               	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
               	.event(RISK.Event.Withdraw_Batch)
               	.result(RISK.Result.Failed)
               	.reason(RISK.Reason.WhiteList_IP)
               	.target(requestIp)
               	.message("商户： %s, 用户： %s, 登入IP： %s, 登入IP不合法 " , mchntCd, commonBO.getMchntUser().getUserId(), requestIp)
               	.params(eventParams) //夹带其他资讯
               	.submit();

            return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED,mappingI18n(this.getClass().getSimpleName(),"登入IP不合法", lang));

         }

//		if (!isAllowedWithdraw())
//			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,"商户平台代付不在IP白名单内!");

		String userId = (String) request.getSession().getAttribute("userId");
		if (Utils.isEmpty(mchntCd)) {
			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"商户号为空", lang)); 
		}

		MchntUserInfo muser = mchntBO.selectMchntUserInfo(mchntCd, userId);
		String role = muser.getUserRole();
		if (!(validateWithdrawClientIp(mchntCd, requestIp, request) &&
				(MerUserRole.WithdrawUser.getCode().equals(role)||MerUserRole.SuperUser.getCode().equals(role)))) {

			warn("商户： %s, 用户： %s, 登入IP： %s, 商户平台代付不在IP白名单内!" , mchntCd, userId, requestIp);

         	RiskEvent.mer()
             	.who(merUser(mchntCd, userId))
             	.event(RISK.Event.Withdraw_Batch)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.WhiteList_IP)
             	.target(requestIp)
             	.message("商户： %s, 用户： %s, 登入IP： %s, 商户平台代付不在IP白名单内!" , mchntCd, userId, requestIp)
             	.params(eventParams) //夹带其他资讯
             	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"商户平台代付不在IP白名单内!", lang));
		}

		// 校验验证码
		String sessionValidCode = commonBO.getSessionData(SessionKeys.VALIDATION_CODE.getCode());
		if (StringUtil.isBlank(sessionValidCode)) {
			return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED,mappingI18n(this.getClass().getSimpleName(), "图形验证码失效", lang));
		} else {
			if (!validateCode.equalsIgnoreCase(sessionValidCode)) {
				sleep(1100, 2500);

	        	RiskEvent.mer()
	            	.who(merUser(mchntCd, userId))
	            	.event(RISK.Event.Withdraw_Batch)
	            	.result(RISK.Result.Failed)
	            	.reason(RISK.Reason.Verify_Captcha)
	            	.message("商户： %s, 用户： %s, 图形验证码输入错误", mchntCd, userId)
	            	.params(eventParams) //夹带其他资讯
	            	.submit();

				return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED,mappingI18n(this.getClass().getSimpleName(), "图形验证码输入错误，请重新输入", lang));
			}
		}
		commonBO.removeSessionData(SessionKeys.VALIDATION_CODE.getCode());

		String lastValidateCode = commonBO.getSessionData(SessionKeys.LAST_VALIDATION_CODE.getCode());
		if (!Utils.isEmpty(lastValidateCode) && lastValidateCode.equalsIgnoreCase(validateCode)) {
			String msg = String.format("商户： %s, 用户： %s, 连续两次验证码一样！\n上一次验证码: %s,\n当前验证码: %s", mchntCd, userId, lastValidateCode, validateCode);
			error(msg);

        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.Duplicate_Captcha)
            	.message("商户： %s, 用户： %s, 连续两次验证码一样！\n上一次验证码: %s,\n当前验证码: %s", mchntCd, userId, lastValidateCode, validateCode)
            	.params(eventParams) //夹带其他资讯
            	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"系统忙碌，请稍后再试！", lang));

		}else {
			commonBO.setSessionData(SessionKeys.LAST_VALIDATION_CODE.getCode(), validateCode);
		}

		// 校验登录密码
		if (!PwdUtil.validatePwd(secretHash, gaCode, null, muser.getLoginPwd())) {

        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.Verify_Passwd)
            	.message("商户： %s; 用户： %s; 密码错误", mchntCd, userId)
            	.params(eventParams) //夹带其他资讯
            	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"密码错误", lang));

		}
		boolean gaCodeFlag = commonBO.getSessionData(SessionKeys.GACODE_FLAG.getCode());

		GoogleAuthenticator ga = new GoogleAuthenticator(mchntCd +"-" + userId, muser.getOtpSecret());
		if((gaCodeFlag == true && Utils.isEmpty(gaCode)) || !(ga.checkCode(gaCode))) {

        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.Verify_Otp)
            	.message("商户： %s; 用户： %s; 谷歌验证失败", mchntCd, userId)
            	.params(eventParams) //夹带其他资讯
            	.submit();

        	//GA验证码输入错误，记录错误次数
        	if (GoogleAuthenticatorUtils.countGaLoginFail(muser)) {
    			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"谷歌验证失败", lang));
        	} else {
        		//放進session
        		muser.setUserState(MerUserState.Locked.getCode());
        		commonBO.setSessionData(SessionKeys.MCHNT_USER_INFO.getCode(), muser);
        		String msg = String.format("谷歌验证码连续输入錯誤 %s 次，帐号已被锁定", MerParamCache.GA_Login_Failed_Count);
        		return commonBO.buildAjaxResp(AjaxRespEnums.ERROR, msg);
        	}
		}
		commonBO.removeSessionData(SessionKeys.GACODE_FLAG.getCode());

		this.updateLastGaTime();

		//校验text的md5
//		if (commonBO.isDuplicateWithdrawList(withdrawList)) {
//			error("短时间内连续两次提交相同的批量代付内容");
//			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,"系统忙碌，请稍后再试！");
//		};

		withdrawList = withdrawList.replace("\r", "");
		String nowWithdrawListMd5 = EncryptUtil.md5(withdrawList);
		String withdrawListMd5 = commonBO.getSessionData(SessionKeys.WITHDRAW_LIST_MD5.getCode());
		if(!nowWithdrawListMd5.equals(withdrawListMd5)) {
			info("两者不一致, nowWithdrawListMd5 : %s , withdrawListMd5 : %s" , nowWithdrawListMd5,withdrawListMd5);

        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.Exception)
            	.message("商户： %s; 用户： %s; 批量代付失败！", mchntCd, userId)
            	.params(eventParams) //夹带其他资讯
            	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"批量代付失败！", lang));
		}

		AjaxResult result = null;
		String [] withrdrawArray = withdrawList.replaceAll("[,，\\s\\n]+", ",").split(",");
		int count;
		int sccCount = 0;
		StringBuffer errSb = new StringBuffer();

		if(withrdrawArray.length > SUMBITSUM ) {

        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.InvalidInfo)
            	.message("商户： %s; 用户： %s; 提交笔数超过限制", mchntCd, userId)
            	
            	.params(eventParams) //夹带其他资讯
            	.submit();

			result = commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"提交笔数超过限制", lang));
		}
		for(count = 1 ; count <= withrdrawArray.length ; count++) {
			String item = StringUtil.trim(withrdrawArray[count-1]);
			if(Utils.isEmpty(item))
				continue;
			String[] withdrawInfo = item.split( "\\|");
			if(withdrawInfo.length != 5) {
				errSb.append(String.format("%s  ,行数： %s 数据不完整!\n", item ,count));
				
	        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.InvalidInfo)
            	.message("商户： %s; 用户： %s; %s  ,行数： %s 数据不完整!\n", mchntCd, userId, item, count)
            	.params(eventParams) //夹带其他资讯
            	.submit();
	        	
				continue;
			}
			String cashAmt = withdrawInfo[0];
			String accName = withdrawInfo[1];
			String accNo = withdrawInfo[2];
//			String bankName = withdrawInfo[3];
			String bankCd = withdrawInfo[4];
//			if (!isAllowedWithdraw()) {
//				errSb.append(String.format("%s  ,行数： %s 代付操作不允许!\n", item,count));
//				continue;
//			}

			if(!checkBankNum(bankCd)) {
				errSb.append(String.format("%s   ,行数： %s, 开户行代码 %s 错误! \n", item, count,bankCd));
				
	        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.InvalidInfo)
            	.message("商户： %s; 用户： %s; %s   ,行数： %s, 开户行代码 %s 错误! \n", mchntCd, userId, item, count, bankCd)
            	.params(eventParams) //夹带其他资讯
            	.submit();
	        	
				continue;
			}

			//檢核批量取现页面的幣別帳戶
			MerAccountInfo acc = mchntAccountBO.getMchntAccount(mchntCd, currencyCode);
			if(Utils.isEmpty(acc)) {
				errSb.append(String.format("%s   ,行数： %s 未找到商户账户!\n", item,count));
				
	        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.InvalidInfo)
            	.message("商户： %s; 用户： %s; %s   ,行数： %s 未找到商户账户!\n", mchntCd, userId, item, count)
            	.params(eventParams) //夹带其他资讯
            	.submit();
	        	
				continue;
			}
			if(!Utils.isInSet(acc.getState(), AccEnums.AccSt.ST_1.getCode())) {
				errSb.append(String.format("%s   ,行数： %s 商户账户状态无效! \n", item,count));
				
	        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.InvalidInfo)
            	.message("商户： %s; 用户： %s; %s   ,行数： %s 商户账户状态无效! \n", mchntCd, userId, item, count)
            	.params(eventParams) //夹带其他资讯
            	.submit();
	        	
				continue;
			}
			BigDecimal txnAmtBd = (new BigDecimal(cashAmt)).movePointRight(2);
			if (txnAmtBd.compareTo(BigDecimal.ZERO) <= 0) {
				errSb.append(String.format("%s   ,行数： %s 交易金额请输入正值! \n", item,count));
				
	        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.InvalidInfo)
            	.message("商户： %s; 用户： %s; %s   ,行数： %s 交易金额请输入正值! \n", mchntCd, userId, item, count)
            	.params(eventParams) //夹带其他资讯
            	.submit();
	        	
				continue;
			}

			//转换成分
			String transAt = StringUtil.trim(cashAmt);
			BigDecimal at = new BigDecimal(transAt);
			if (!StringUtil.isBlank(transAt)) {
				// 转换成分
				at = at.multiply(new BigDecimal(100));
			}

			Long amt = at.longValue();

			//判断取现金额是否小于可用金额
			if(acc.getAvailableBalance() < at.longValue()) {
				errSb.append(String.format("%s   ,行数： %s 输入的取款金额大于可代付额度! \n", item,count));
				
	        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.InvalidInfo)
            	.message("商户： %s; 用户： %s; %s   ,行数： %s 输入的取款金额大于可代付额度! \n", mchntCd, userId, item, count)
            	.params(eventParams) //夹带其他资讯
            	.submit();
	        	
				continue;
			}

			if(!MerUtils.checkBankCard(accNo)) {//判断是否为正确的银行卡
				errSb.append(String.format("%s   ,行数： %s 请输入正确的银行卡号! \n", item,count));
				
	        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.InvalidInfo)
            	.message("商户： %s; 用户： %s; %s   ,行数： %s 请输入正确的银行卡号! \n", mchntCd, userId, item, count)
            	.params(eventParams) //夹带其他资讯
            	.submit();
	        	
				continue;
			}

			Map<String, String> resp =new HashMap<String,String>();
			try {
				resp = withdraw(mchntCd, accNo, accName, amt.toString(), requestIp, userId, bankCd, validateCode, currencyCode, siteId);
			}catch(Exception e ) {
				error("[InternalGatewayClient][ERROR] 调用内部取现服务接口异常：" + e.getMessage(), e);
				errSb.append(String.format("%s   , 行数： %s 调用内部取现服务接口异常! \n", item ,count));
				
	        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Error)
            	.reason(RISK.Reason.Exception)
            	.message("商户： %s; 用户： %s; %s   , 行数： %s 调用内部取现服务接口异常! \n", mchntCd, userId, item, count)
            	.params(eventParams) //夹带其他资讯
            	.submit();
	        	
				continue;
			}

			if (MsgRespHelper.isOkCode(resp.get(MSG.respCode))) {
				sccCount ++ ;

				//请求成功
				RiskEvent.mer()
				.who(merUser(mchntCd, userId))
				.event(RISK.Event.Withdraw_Batch)
				.result(RISK.Result.Ok)
				.message("代付交易请求成功; 商户： %s, 用户： %s, 卡号： %s, 持卡人： %s, 订单金额： %s, 币别： %s, 联行号： %s, 请求IP: %s", mchntCd, userId, accNo, accName, cashAmt, currencyCode, bankCd, requestIp)
             	.target(accName+"|"+accNo+"|"+cashAmt+"|"+EnumLangUtil.translate(lang, "CurrType", currencyCode, false))
				.params(eventParams)
				.submit();

			}else {
				errSb.append(String.format("%s   ,行数： %s %s \n", item, count,resp.get(MSG.respMsg)));

				//请求失败
				RiskEvent.mer()
				.who(merUser(mchntCd, userId))
				.event(RISK.Event.Withdraw_Batch)
				.result(RISK.Result.Failed)
				.reason(RISK.Reason.Exception)
				.message("代付交易请求失败; 商户： %s, 用户： %s, 卡号： %s, 持卡人： %s, 订单金额： %s, 币别： %s, 联行号： %s, 请求IP: %s; %s   ,行数： %s %s \n", mchntCd, userId, accNo, accName, cashAmt, currencyCode, bankCd, requestIp, item, count,resp.get(MSG.respMsg))
             	.target(accName+"|"+accNo+"|"+cashAmt+"|"+ EnumLangUtil.translate(lang, "CurrType", currencyCode, false))
				.params(eventParams)
				.submit();

				continue;
			}
		}
		info("代付失败信息： " + errSb.toString());
		//生成代付失败文件
		String batchNo = buildErrFile(errSb.toString(), lang);
		error("批次号: " + batchNo);

		String msg = String.format(mappingI18n(this.getClass().getSimpleName(),"您共发起 %s笔取现申请 <br/> 提交成功: %s笔，请到【取现查询】页面查看取现结果。<br/> 提交失败: %s笔 ，请导出失败数据查看错误原因。", lang), count-1, sccCount, count - 1 - sccCount) ;
		result = commonBO.buildSuccResp(msg,"batchNo", batchNo);
		return result;
	}
	/**
	 * 取现操作确认
	 * @param model
	 * @return
	 * @throws SystemException
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.SuperUser})
	@RequestMapping(value = "/checkWithdraw.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult checkWithdraw(HttpServletRequest request, HttpServletResponse response,String withdrawList
			) throws SystemException {
//		AssertUtil.strIsBlank(validateCode, "validateCode is empty");
		String lang = I18nMsgUtils.getLanguage(request);
        String siteId = request.getSession().getAttribute("siteId").toString();
		
		if(Utils.isEmpty(withdrawList))
			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(), "withdrawList is blank.", lang));

		String mchntCd = commonBO.getMchntCd();
		String requestIp = ""+MerUtils.getRemoteIp(request);

        Map<String,String> eventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/checkWithdraw.do" ,
        				MSG.merId, commonBO.getMchntCd(),
        				MSG.userId, commonBO.getMchntUser().getUserId(),
        				INTER_MSG.reqIp, requestIp,
        				"withdrawList", withdrawList,
        				INTER_MSG.siteId, siteId
        				);

         if (!validateClientIp(mchntCd, requestIp, request)) {

        	warn("商户： %s, 用户： %s, 登入IP： %s, 登入IP不合法 " , mchntCd, commonBO.getMchntUser().getUserId(), requestIp);

           	RiskEvent.mer()
               	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
               	.event(RISK.Event.Withdraw_Batch)
               	.result(RISK.Result.Failed)
               	.reason(RISK.Reason.WhiteList_IP)
               	.target(requestIp)
               	.message("商户： %s, 用户： %s, 登入IP： %s, 登入IP不合法 " , mchntCd, commonBO.getMchntUser().getUserId(), requestIp)
               	.params(eventParams) //夹带其他资讯
               	.submit();

            return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED,mappingI18n(this.getClass().getSimpleName(),"登入IP不合法", lang));
         }

		if (Utils.isEmpty(mchntCd)) {
			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"商户号为空", lang)); 
		}

		info(String.format("[批量代付请求]\n%s, \n 请求ip: %s" ,  withdrawList , requestIp ));

		MchntUserInfo muser = commonBO.getMchntUser();
		String role = muser.getUserRole();
		if (!(validateWithdrawClientIp(mchntCd, requestIp, request) &&
				(MerUserRole.WithdrawUser.getCode().equals(role) || MerUserRole.SuperUser.getCode().equals(role)))) {

			warn("商户： %s, 用户： %s, 登入IP： %s, 商户平台代付不在IP白名单内!" , mchntCd, commonBO.getMchntUser().getUserId(), requestIp);

         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Withdraw_Batch)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.WhiteList_IP)
             	.target(requestIp)
             	.message("商户： %s, 用户： %s, 登入IP： %s, 商户平台代付不在IP白名单内!" , mchntCd, commonBO.getMchntUser().getUserId(), requestIp)
             	.params(eventParams) //夹带其他资讯
             	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"商户平台代付不在IP白名单内!", lang));
		}

		//校验text的md5
		withdrawList = withdrawList.replace("\r", "");
		if (commonBO.isDuplicateWithdrawList(withdrawList)) {
			error("商户： %s, 用户： %s, 登入IP： %s, 短时间内连续两次提交相同的批量代付内容" , mchntCd, commonBO.getMchntUser().getUserId(), requestIp);

         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Withdraw_Batch)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.Risk_Freq)
             	.message("商户： %s, 用户： %s, 登入IP： %s, 短时间内连续两次提交相同的批量代付内容" , mchntCd, commonBO.getMchntUser().getUserId(), requestIp)
             	.params(eventParams) //夹带其他资讯
             	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"系统忙碌，请稍后再试！", lang));

		};
		commonBO.setSessionData(SessionKeys.GACODE_FLAG.getCode(), true);

		return commonBO.buildSuccResp();
	}

	/**
	 * 批量代付失败导出
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.SuperUser})
	@RequestMapping("/export")  //导出
	public @ResponseBody AjaxResult export(String batchNo, HttpServletResponse response) {
		String mchntCd = commonBO.getMchntCd();
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		String fileNm = mchntCd+ batchNo + MerConfigCache.getConfig(MerConstants.CONFIG_KEY_BACTH_WITHDRAW_ERROR_FILENM);
		String filePath = MerConfigCache.getConfig(MerConstants.CONFIG_KEY_TRANSLOG_EXPORT_FILE_PATH);
		commonBO.downFile(filePath, fileNm, Constant.UTF8, response);
		return null;
	}

	/**
	 * 银行代码表
	 * @return
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.SuperUser})
	@RequestMapping("/downBankCodeList")  //导出
	public @ResponseBody AjaxResult downBankCodeList(HttpServletResponse response, HttpServletRequest req) {
		String mchntCd = commonBO.getMchntCd();
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		List<BankNums> list = BankInfoCaChe.getBankCdLst();
		String fileNm = mchntCd+Utils.getRandomInt(1000, 9999)+MerConfigCache.getConfig(MerConstants.CONFIG_KEY_BANK_CODE_FILENM);
		String filePath = MerConfigCache.getConfig(MerConstants.CONFIG_KEY_TRANSLOG_EXPORT_FILE_PATH);
		Pager<Map<String, String>> pager = commonBO.transferList(list, MerConstants.PAGE_CONF_BANK_CODE_EXP, ENTITY_TRANSFER, req);
		commonBO.exportAccflowExcel(pager,filePath, fileNm, Constant.UTF8, response);
		return null;
	}

	/**
	 * 检查开户行代码是否正确
	 * @param bankCd
	 * @return
	 */
	public boolean checkBankNum(String bankCd) {
		boolean flag = false;
		List<BankNums> lst = BankInfoCaChe.getBankCdLst();
		for(BankNums bs : lst) {
			if(bs.getBankNum().equals(bankCd))
				flag = true;
		}
		return flag;
	}

	/**
	 * 生成批量代付失败文件
	 * @param err
	 * @return
	 */
	public String buildErrFile(String err, String lang) {
		String mchntCd = commonBO.getMchntCd();
		String batchNo = "id_" + Utils.getRandomInt(1000, 9999);
		String fileNm = mchntCd+ batchNo + MerConfigCache.getConfig(MerConstants.CONFIG_KEY_BACTH_WITHDRAW_ERROR_FILENM);
		String filePath = MerConfigCache.getConfig(MerConstants.CONFIG_KEY_TRANSLOG_EXPORT_FILE_PATH);
		try {
			TextExport.creatTxtFile(fileNm,filePath,err);
		} catch (IOException e) {
			error("生成批量取现失败文件错误" + e.getMessage());
			return batchNo + mappingI18n(this.getClass().getSimpleName(),"生成批量取现失败文件错误", lang);
		}
		return batchNo;
	}


	private Map<String, String> withdraw(String mchntCd ,String accNo, String accName, String amt, String requestIp, String userId,String bankCd, String validateCode , String currencyCode, String siteId) throws SystemException {
		String orderId=this.nextOrderId(PrefixForWithdraw);
		info("[代付请求] 卡号： %s, 持卡人： %s, 订单号： %s, 订单金额： %s, 币别： %s, 联行号： %s, 请求IP: %s; 操作用户: %s; validateCode: %s", accNo, accName, orderId, amt, currencyCode, bankCd, requestIp, userId, validateCode);

		Date now = new Date();
		Map <String,String> req = new HashMap<String,String>();
		req.put(MSG.txnType, "52");
		req.put(MSG.txnSubType, "10");
		req.put(MSG.orderDate, Converter.dateToString(now));
		req.put(MSG.orderTime, Converter.timeToString(now));
		req.put(MSG.merId, mchntCd);
		req.put(MSG.orderId, orderId );
		req.put(MSG.txnAmt, amt);
		req.put(MSG.currencyCode, currencyCode);

		req.put(MSG.accName, accName);//户名
		req.put(MSG.accNum, accNo);//卡号
		req.put(MSG.bankNum, bankCd);//联行号
		req.put(MSG.notifyUrl, Constant.NOOP_URL);//后台通知地址，Constant.NOOP_URL表示不需通知http://noop.noop:0000/

		req.put(INTER_MSG.userId, userId);
		req.put(MSG.clientIp, requestIp);//IP
		req.put(INTER_MSG.txnSrc, TxnSource.MER.getCode());
		req.put(MSG.unit, Amount.getDefaultUnit(currencyCode).toString());
		req.put(INTER_MSG.siteId, siteId);
		
		Map<String, String> resp =new HashMap<String,String>();
		info("[InternalGatewayClient][代付][内部网关请求] "+req);
		resp = InternalGatewayClient.request(req, null) ;
		info("[InternalGatewayClient][代付][内部网关响应] "+resp);
		info("[代付结果] 卡号： %s, 持卡人： %s, 订单号： %s, 订单金额： %s, 币别： %s, 返回代码： %s, 返回信息： %s; ", accNo, accName, req.get(MSG.orderId), req.get(MSG.txnAmt), currencyCode, resp.get(MSG.respCode), resp.get(MSG.respMsg));
		return resp;
	}
}
