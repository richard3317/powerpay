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
	 * ????????????
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
		
		//??????????????? (????????????????????????????????????????????????)
		//(????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????)
//		MerAccountInfo mchntAcct_CNY = mchntAccountBO.getMchntAccount(mchntCd, "156");
//		AssertUtil.objIsNull(mchntAcct_CNY, mappingI18n(this.getClass().getSimpleName(), "?????????????????????", lang) + "(CNY)");
//		AssertUtil.strNotEquals(mchntAcct_CNY.getState(), AccEnums.AccSt.ST_1.getCode(), 
//				mappingI18n(this.getClass().getSimpleName(),"????????????????????????", lang));
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
	 * ????????????
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
		info(String.format("[??????????????????]\n%s, \n ??????ip: %s, ??????????????????%s, ???????????????: %s" ,  withdrawList , requestIp,validateCode,gaCode));

		String mchntCd = commonBO.getMchntCd();

        Map<String,String> eventParams = //????????????(Event?????????)
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

         	warn("????????? %s, ????????? %s, ??????IP??? %s, ??????IP????????? " , mchntCd, commonBO.getMchntUser().getUserId(), requestIp);

           	RiskEvent.mer()
               	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
               	.event(RISK.Event.Withdraw_Batch)
               	.result(RISK.Result.Failed)
               	.reason(RISK.Reason.WhiteList_IP)
               	.target(requestIp)
               	.message("????????? %s, ????????? %s, ??????IP??? %s, ??????IP????????? " , mchntCd, commonBO.getMchntUser().getUserId(), requestIp)
               	.params(eventParams) //??????????????????
               	.submit();

            return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED,mappingI18n(this.getClass().getSimpleName(),"??????IP?????????", lang));

         }

//		if (!isAllowedWithdraw())
//			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,"????????????????????????IP????????????!");

		String userId = (String) request.getSession().getAttribute("userId");
		if (Utils.isEmpty(mchntCd)) {
			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"???????????????", lang)); 
		}

		MchntUserInfo muser = mchntBO.selectMchntUserInfo(mchntCd, userId);
		String role = muser.getUserRole();
		if (!(validateWithdrawClientIp(mchntCd, requestIp, request) &&
				(MerUserRole.WithdrawUser.getCode().equals(role)||MerUserRole.SuperUser.getCode().equals(role)))) {

			warn("????????? %s, ????????? %s, ??????IP??? %s, ????????????????????????IP????????????!" , mchntCd, userId, requestIp);

         	RiskEvent.mer()
             	.who(merUser(mchntCd, userId))
             	.event(RISK.Event.Withdraw_Batch)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.WhiteList_IP)
             	.target(requestIp)
             	.message("????????? %s, ????????? %s, ??????IP??? %s, ????????????????????????IP????????????!" , mchntCd, userId, requestIp)
             	.params(eventParams) //??????????????????
             	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"????????????????????????IP????????????!", lang));
		}

		// ???????????????
		String sessionValidCode = commonBO.getSessionData(SessionKeys.VALIDATION_CODE.getCode());
		if (StringUtil.isBlank(sessionValidCode)) {
			return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED,mappingI18n(this.getClass().getSimpleName(), "?????????????????????", lang));
		} else {
			if (!validateCode.equalsIgnoreCase(sessionValidCode)) {
				sleep(1100, 2500);

	        	RiskEvent.mer()
	            	.who(merUser(mchntCd, userId))
	            	.event(RISK.Event.Withdraw_Batch)
	            	.result(RISK.Result.Failed)
	            	.reason(RISK.Reason.Verify_Captcha)
	            	.message("????????? %s, ????????? %s, ???????????????????????????", mchntCd, userId)
	            	.params(eventParams) //??????????????????
	            	.submit();

				return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED,mappingI18n(this.getClass().getSimpleName(), "?????????????????????????????????????????????", lang));
			}
		}
		commonBO.removeSessionData(SessionKeys.VALIDATION_CODE.getCode());

		String lastValidateCode = commonBO.getSessionData(SessionKeys.LAST_VALIDATION_CODE.getCode());
		if (!Utils.isEmpty(lastValidateCode) && lastValidateCode.equalsIgnoreCase(validateCode)) {
			String msg = String.format("????????? %s, ????????? %s, ??????????????????????????????\n??????????????????: %s,\n???????????????: %s", mchntCd, userId, lastValidateCode, validateCode);
			error(msg);

        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.Duplicate_Captcha)
            	.message("????????? %s, ????????? %s, ??????????????????????????????\n??????????????????: %s,\n???????????????: %s", mchntCd, userId, lastValidateCode, validateCode)
            	.params(eventParams) //??????????????????
            	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"?????????????????????????????????", lang));

		}else {
			commonBO.setSessionData(SessionKeys.LAST_VALIDATION_CODE.getCode(), validateCode);
		}

		// ??????????????????
		if (!PwdUtil.validatePwd(secretHash, gaCode, null, muser.getLoginPwd())) {

        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.Verify_Passwd)
            	.message("????????? %s; ????????? %s; ????????????", mchntCd, userId)
            	.params(eventParams) //??????????????????
            	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"????????????", lang));

		}
		boolean gaCodeFlag = commonBO.getSessionData(SessionKeys.GACODE_FLAG.getCode());

		GoogleAuthenticator ga = new GoogleAuthenticator(mchntCd +"-" + userId, muser.getOtpSecret());
		if((gaCodeFlag == true && Utils.isEmpty(gaCode)) || !(ga.checkCode(gaCode))) {

        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.Verify_Otp)
            	.message("????????? %s; ????????? %s; ??????????????????", mchntCd, userId)
            	.params(eventParams) //??????????????????
            	.submit();

        	//GA??????????????????????????????????????????
        	if (GoogleAuthenticatorUtils.countGaLoginFail(muser)) {
    			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"??????????????????", lang));
        	} else {
        		//??????session
        		muser.setUserState(MerUserState.Locked.getCode());
        		commonBO.setSessionData(SessionKeys.MCHNT_USER_INFO.getCode(), muser);
        		String msg = String.format("????????????????????????????????? %s ????????????????????????", MerParamCache.GA_Login_Failed_Count);
        		return commonBO.buildAjaxResp(AjaxRespEnums.ERROR, msg);
        	}
		}
		commonBO.removeSessionData(SessionKeys.GACODE_FLAG.getCode());

		this.updateLastGaTime();

		//??????text???md5
//		if (commonBO.isDuplicateWithdrawList(withdrawList)) {
//			error("?????????????????????????????????????????????????????????");
//			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,"?????????????????????????????????");
//		};

		withdrawList = withdrawList.replace("\r", "");
		String nowWithdrawListMd5 = EncryptUtil.md5(withdrawList);
		String withdrawListMd5 = commonBO.getSessionData(SessionKeys.WITHDRAW_LIST_MD5.getCode());
		if(!nowWithdrawListMd5.equals(withdrawListMd5)) {
			info("???????????????, nowWithdrawListMd5 : %s , withdrawListMd5 : %s" , nowWithdrawListMd5,withdrawListMd5);

        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.Exception)
            	.message("????????? %s; ????????? %s; ?????????????????????", mchntCd, userId)
            	.params(eventParams) //??????????????????
            	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"?????????????????????", lang));
		}

		AjaxResult result = null;
		String [] withrdrawArray = withdrawList.replaceAll("[,???\\s\\n]+", ",").split(",");
		int count;
		int sccCount = 0;
		StringBuffer errSb = new StringBuffer();

		if(withrdrawArray.length > SUMBITSUM ) {

        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.InvalidInfo)
            	.message("????????? %s; ????????? %s; ????????????????????????", mchntCd, userId)
            	
            	.params(eventParams) //??????????????????
            	.submit();

			result = commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"????????????????????????", lang));
		}
		for(count = 1 ; count <= withrdrawArray.length ; count++) {
			String item = StringUtil.trim(withrdrawArray[count-1]);
			if(Utils.isEmpty(item))
				continue;
			String[] withdrawInfo = item.split( "\\|");
			if(withdrawInfo.length != 5) {
				errSb.append(String.format("%s  ,????????? %s ???????????????!\n", item ,count));
				
	        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.InvalidInfo)
            	.message("????????? %s; ????????? %s; %s  ,????????? %s ???????????????!\n", mchntCd, userId, item, count)
            	.params(eventParams) //??????????????????
            	.submit();
	        	
				continue;
			}
			String cashAmt = withdrawInfo[0];
			String accName = withdrawInfo[1];
			String accNo = withdrawInfo[2];
//			String bankName = withdrawInfo[3];
			String bankCd = withdrawInfo[4];
//			if (!isAllowedWithdraw()) {
//				errSb.append(String.format("%s  ,????????? %s ?????????????????????!\n", item,count));
//				continue;
//			}

			if(!checkBankNum(bankCd)) {
				errSb.append(String.format("%s   ,????????? %s, ??????????????? %s ??????! \n", item, count,bankCd));
				
	        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.InvalidInfo)
            	.message("????????? %s; ????????? %s; %s   ,????????? %s, ??????????????? %s ??????! \n", mchntCd, userId, item, count, bankCd)
            	.params(eventParams) //??????????????????
            	.submit();
	        	
				continue;
			}

			//???????????????????????????????????????
			MerAccountInfo acc = mchntAccountBO.getMchntAccount(mchntCd, currencyCode);
			if(Utils.isEmpty(acc)) {
				errSb.append(String.format("%s   ,????????? %s ?????????????????????!\n", item,count));
				
	        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.InvalidInfo)
            	.message("????????? %s; ????????? %s; %s   ,????????? %s ?????????????????????!\n", mchntCd, userId, item, count)
            	.params(eventParams) //??????????????????
            	.submit();
	        	
				continue;
			}
			if(!Utils.isInSet(acc.getState(), AccEnums.AccSt.ST_1.getCode())) {
				errSb.append(String.format("%s   ,????????? %s ????????????????????????! \n", item,count));
				
	        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.InvalidInfo)
            	.message("????????? %s; ????????? %s; %s   ,????????? %s ????????????????????????! \n", mchntCd, userId, item, count)
            	.params(eventParams) //??????????????????
            	.submit();
	        	
				continue;
			}
			BigDecimal txnAmtBd = (new BigDecimal(cashAmt)).movePointRight(2);
			if (txnAmtBd.compareTo(BigDecimal.ZERO) <= 0) {
				errSb.append(String.format("%s   ,????????? %s ???????????????????????????! \n", item,count));
				
	        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.InvalidInfo)
            	.message("????????? %s; ????????? %s; %s   ,????????? %s ???????????????????????????! \n", mchntCd, userId, item, count)
            	.params(eventParams) //??????????????????
            	.submit();
	        	
				continue;
			}

			//????????????
			String transAt = StringUtil.trim(cashAmt);
			BigDecimal at = new BigDecimal(transAt);
			if (!StringUtil.isBlank(transAt)) {
				// ????????????
				at = at.multiply(new BigDecimal(100));
			}

			Long amt = at.longValue();

			//??????????????????????????????????????????
			if(acc.getAvailableBalance() < at.longValue()) {
				errSb.append(String.format("%s   ,????????? %s ??????????????????????????????????????????! \n", item,count));
				
	        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.InvalidInfo)
            	.message("????????? %s; ????????? %s; %s   ,????????? %s ??????????????????????????????????????????! \n", mchntCd, userId, item, count)
            	.params(eventParams) //??????????????????
            	.submit();
	        	
				continue;
			}

			if(!MerUtils.checkBankCard(accNo)) {//?????????????????????????????????
				errSb.append(String.format("%s   ,????????? %s ??????????????????????????????! \n", item,count));
				
	        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.InvalidInfo)
            	.message("????????? %s; ????????? %s; %s   ,????????? %s ??????????????????????????????! \n", mchntCd, userId, item, count)
            	.params(eventParams) //??????????????????
            	.submit();
	        	
				continue;
			}

			Map<String, String> resp =new HashMap<String,String>();
			try {
				resp = withdraw(mchntCd, accNo, accName, amt.toString(), requestIp, userId, bankCd, validateCode, currencyCode, siteId);
			}catch(Exception e ) {
				error("[InternalGatewayClient][ERROR] ???????????????????????????????????????" + e.getMessage(), e);
				errSb.append(String.format("%s   , ????????? %s ????????????????????????????????????! \n", item ,count));
				
	        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Withdraw_Batch)
            	.result(RISK.Result.Error)
            	.reason(RISK.Reason.Exception)
            	.message("????????? %s; ????????? %s; %s   , ????????? %s ????????????????????????????????????! \n", mchntCd, userId, item, count)
            	.params(eventParams) //??????????????????
            	.submit();
	        	
				continue;
			}

			if (MsgRespHelper.isOkCode(resp.get(MSG.respCode))) {
				sccCount ++ ;

				//????????????
				RiskEvent.mer()
				.who(merUser(mchntCd, userId))
				.event(RISK.Event.Withdraw_Batch)
				.result(RISK.Result.Ok)
				.message("????????????????????????; ????????? %s, ????????? %s, ????????? %s, ???????????? %s, ??????????????? %s, ????????? %s, ???????????? %s, ??????IP: %s", mchntCd, userId, accNo, accName, cashAmt, currencyCode, bankCd, requestIp)
             	.target(accName+"|"+accNo+"|"+cashAmt+"|"+EnumLangUtil.translate(lang, "CurrType", currencyCode, false))
				.params(eventParams)
				.submit();

			}else {
				errSb.append(String.format("%s   ,????????? %s %s \n", item, count,resp.get(MSG.respMsg)));

				//????????????
				RiskEvent.mer()
				.who(merUser(mchntCd, userId))
				.event(RISK.Event.Withdraw_Batch)
				.result(RISK.Result.Failed)
				.reason(RISK.Reason.Exception)
				.message("????????????????????????; ????????? %s, ????????? %s, ????????? %s, ???????????? %s, ??????????????? %s, ????????? %s, ???????????? %s, ??????IP: %s; %s   ,????????? %s %s \n", mchntCd, userId, accNo, accName, cashAmt, currencyCode, bankCd, requestIp, item, count,resp.get(MSG.respMsg))
             	.target(accName+"|"+accNo+"|"+cashAmt+"|"+ EnumLangUtil.translate(lang, "CurrType", currencyCode, false))
				.params(eventParams)
				.submit();

				continue;
			}
		}
		info("????????????????????? " + errSb.toString());
		//????????????????????????
		String batchNo = buildErrFile(errSb.toString(), lang);
		error("?????????: " + batchNo);

		String msg = String.format(mappingI18n(this.getClass().getSimpleName(),"???????????? %s??????????????? <br/> ????????????: %s?????????????????????????????????????????????????????????<br/> ????????????: %s??? ?????????????????????????????????????????????", lang), count-1, sccCount, count - 1 - sccCount) ;
		result = commonBO.buildSuccResp(msg,"batchNo", batchNo);
		return result;
	}
	/**
	 * ??????????????????
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

        Map<String,String> eventParams = //????????????(Event?????????)
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

        	warn("????????? %s, ????????? %s, ??????IP??? %s, ??????IP????????? " , mchntCd, commonBO.getMchntUser().getUserId(), requestIp);

           	RiskEvent.mer()
               	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
               	.event(RISK.Event.Withdraw_Batch)
               	.result(RISK.Result.Failed)
               	.reason(RISK.Reason.WhiteList_IP)
               	.target(requestIp)
               	.message("????????? %s, ????????? %s, ??????IP??? %s, ??????IP????????? " , mchntCd, commonBO.getMchntUser().getUserId(), requestIp)
               	.params(eventParams) //??????????????????
               	.submit();

            return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED,mappingI18n(this.getClass().getSimpleName(),"??????IP?????????", lang));
         }

		if (Utils.isEmpty(mchntCd)) {
			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"???????????????", lang)); 
		}

		info(String.format("[??????????????????]\n%s, \n ??????ip: %s" ,  withdrawList , requestIp ));

		MchntUserInfo muser = commonBO.getMchntUser();
		String role = muser.getUserRole();
		if (!(validateWithdrawClientIp(mchntCd, requestIp, request) &&
				(MerUserRole.WithdrawUser.getCode().equals(role) || MerUserRole.SuperUser.getCode().equals(role)))) {

			warn("????????? %s, ????????? %s, ??????IP??? %s, ????????????????????????IP????????????!" , mchntCd, commonBO.getMchntUser().getUserId(), requestIp);

         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Withdraw_Batch)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.WhiteList_IP)
             	.target(requestIp)
             	.message("????????? %s, ????????? %s, ??????IP??? %s, ????????????????????????IP????????????!" , mchntCd, commonBO.getMchntUser().getUserId(), requestIp)
             	.params(eventParams) //??????????????????
             	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"????????????????????????IP????????????!", lang));
		}

		//??????text???md5
		withdrawList = withdrawList.replace("\r", "");
		if (commonBO.isDuplicateWithdrawList(withdrawList)) {
			error("????????? %s, ????????? %s, ??????IP??? %s, ?????????????????????????????????????????????????????????" , mchntCd, commonBO.getMchntUser().getUserId(), requestIp);

         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Withdraw_Batch)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.Risk_Freq)
             	.message("????????? %s, ????????? %s, ??????IP??? %s, ?????????????????????????????????????????????????????????" , mchntCd, commonBO.getMchntUser().getUserId(), requestIp)
             	.params(eventParams) //??????????????????
             	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"?????????????????????????????????", lang));

		};
		commonBO.setSessionData(SessionKeys.GACODE_FLAG.getCode(), true);

		return commonBO.buildSuccResp();
	}

	/**
	 * ????????????????????????
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.SuperUser})
	@RequestMapping("/export")  //??????
	public @ResponseBody AjaxResult export(String batchNo, HttpServletResponse response) {
		String mchntCd = commonBO.getMchntCd();
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		String fileNm = mchntCd+ batchNo + MerConfigCache.getConfig(MerConstants.CONFIG_KEY_BACTH_WITHDRAW_ERROR_FILENM);
		String filePath = MerConfigCache.getConfig(MerConstants.CONFIG_KEY_TRANSLOG_EXPORT_FILE_PATH);
		commonBO.downFile(filePath, fileNm, Constant.UTF8, response);
		return null;
	}

	/**
	 * ???????????????
	 * @return
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.SuperUser})
	@RequestMapping("/downBankCodeList")  //??????
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
	 * ?????????????????????????????????
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
	 * ??????????????????????????????
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
			error("????????????????????????????????????" + e.getMessage());
			return batchNo + mappingI18n(this.getClass().getSimpleName(),"????????????????????????????????????", lang);
		}
		return batchNo;
	}


	private Map<String, String> withdraw(String mchntCd ,String accNo, String accName, String amt, String requestIp, String userId,String bankCd, String validateCode , String currencyCode, String siteId) throws SystemException {
		String orderId=this.nextOrderId(PrefixForWithdraw);
		info("[????????????] ????????? %s, ???????????? %s, ???????????? %s, ??????????????? %s, ????????? %s, ???????????? %s, ??????IP: %s; ????????????: %s; validateCode: %s", accNo, accName, orderId, amt, currencyCode, bankCd, requestIp, userId, validateCode);

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

		req.put(MSG.accName, accName);//??????
		req.put(MSG.accNum, accNo);//??????
		req.put(MSG.bankNum, bankCd);//?????????
		req.put(MSG.notifyUrl, Constant.NOOP_URL);//?????????????????????Constant.NOOP_URL??????????????????http://noop.noop:0000/

		req.put(INTER_MSG.userId, userId);
		req.put(MSG.clientIp, requestIp);//IP
		req.put(INTER_MSG.txnSrc, TxnSource.MER.getCode());
		req.put(MSG.unit, Amount.getDefaultUnit(currencyCode).toString());
		req.put(INTER_MSG.siteId, siteId);
		
		Map<String, String> resp =new HashMap<String,String>();
		info("[InternalGatewayClient][??????][??????????????????] "+req);
		resp = InternalGatewayClient.request(req, null) ;
		info("[InternalGatewayClient][??????][??????????????????] "+resp);
		info("[????????????] ????????? %s, ???????????? %s, ???????????? %s, ??????????????? %s, ????????? %s, ??????????????? %s, ??????????????? %s; ", accNo, accName, req.get(MSG.orderId), req.get(MSG.txnAmt), currencyCode, resp.get(MSG.respCode), resp.get(MSG.respMsg));
		return resp;
	}
}
