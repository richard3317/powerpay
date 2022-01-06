package com.icpay.payment.mer.web.controller;

import static com.icpay.payment.risk.MerUser.merUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.gauth.GoogleAuthenticator;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.constants.Names.MSG;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.MerEnums;
import com.icpay.payment.common.enums.MerEnums.MerUserRole;
import com.icpay.payment.common.enums.MerEnums.MerUserState;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BizUtils;
import com.icpay.payment.common.utils.Converter;
import com.icpay.payment.common.utils.PwdUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.db.dao.mybatis.model.RiskListItemMer;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.IRiskListItemMerService;
import com.icpay.payment.mer.bo.MchntBO;
import com.icpay.payment.mer.constants.MerConstants;
import com.icpay.payment.mer.session.SessionKeys;
import com.icpay.payment.mer.util.GoogleAuthenticatorUtils;
import com.icpay.payment.mer.util.I18nMsgUtils;
import com.icpay.payment.mer.util.MerUtils;
import com.icpay.payment.mer.web.interceptor.RolesSessionCheck;
import com.icpay.payment.mer.web.interceptor.SecureInitSessionCheck;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;
import com.icpay.payment.service.IcpaySecExchangeService;
import com.icpay.payment.service.bo.ExchangeKeyInfo;

@Controller
@RequestMapping(value="/secrity")
public class SecritySettingController extends BaseController {

	private static final Logger logger = Logger.getLogger(SecritySettingController.class);

	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String item = m.get("item");
			if (StringUtil.isBlank(item)) {
				m.put("itemDesc", "");
			} else {
//				m.put("itemDesc", item.length() > 7 ?
//						StringUtil.mask(item, 6, item.length() - 4, '*') : item);
				m.put("itemDesc", BizUtils.strMask(item, "*", 4, 0, 6));
			}
		}
	};

	@Autowired
	private MchntBO mchntBO;

	/**
	 * 安全设置页面
	 * @param model
	 * @return
	 */

	@SecureInitSessionCheck
	@RequestMapping(value="/secritySetting", method=RequestMethod.GET)
	public String secritySetting(Model model) {
//		MchntUserInfo muser = commonBO.getSessionData(SessionKeys.MCHNT_USER_INFO.getCode());
		MchntUserInfo muser = commonBO.getMchntUser();
		buildIndexData(model,muser);
		return "secritySetting";
	}

	/**
	 * 获取谷歌验证码的Secret
	 * @param model
	 * @param type
	 * @return
	 */
	/*@AdminSessionCheck
	@SecureInitSessionCheck
	@RequestMapping(value="/getSecret.do", method=RequestMethod.GET)
	public @ResponseBody AjaxResult getSecret(Model model,@RequestParam String type) {
		AssertUtil.strIsBlank(type, "type is blank.");
		MchntInfo mchntInfo = new MchntInfo();
		mchntInfo.setMchntCd(commonBO.getMchntCd());

		try {
			IMchntInfoService service = this.getService(IMchntInfoService.class);
			List<Map<String, String>> secretList = new ArrayList<Map<String, String>>();
			MchntInfo mchnt = service.selectByPrimaryKey(mchntInfo.getMchntCd());
			Map<String, String> map = new HashMap<String, String>();
			map.put("otpSecretLogin", mchnt.getOtpSecretLogin() == null ? "" : mchnt.getOtpSecretLogin());
			map.put("optSecretAdmin", mchnt.getOptSecretAdmin() == null ? "" : mchnt.getOptSecretAdmin());
			map.put("showType", type.equals("0") ? "1":"2");
			secretList.add(map);
			this.info("商户%s显示验证二维码成功!",commonBO.getMchntCd());
			return commonBO.buildSuccResp("secretList" ,secretList);
		} catch (Exception e) {
			this.error("商户%s显示验证二维码错误: %s",commonBO.getMchntCd(), e.getMessage());
			return commonBO.buildErrorResp("错误: "+e.getMessage());
		}
	}*/
	/**
	 * 获取谷歌验证码的Secret
	 * @param model
	 * @param type
	 * @return
	 */
	@SecureInitSessionCheck
	@RequestMapping(value="/getSecret.do", method=RequestMethod.POST)
	public @ResponseBody AjaxResult getSecret(Model model, String role,String userId,String method) {
		AssertUtil.strIsBlank(role, "role is blank.");
		AssertUtil.strIsBlank(userId, "userId is blank.");

		String mchntCd =commonBO.getMchntCd();
//		long start = System.currentTimeMillis();
//		this.info("开始：%s" , start);
		try {
			List<Map<String, String>> secretList = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			if("0".equals(method)) {
//				this.info("生成： %s" , System.currentTimeMillis());
				String otpSecret = GoogleAuthenticator.generateSecretKey();
				this.info("生成独立密钥成功, %s",otpSecret);
				map.put("otpSecret", otpSecret);
//				this.info("生成完成：%s" , System.currentTimeMillis());
			}else {
				List<MchntUserInfo> muser = mchntBO.selectMchntUserList(mchntCd, userId,role);
				if(muser == null || muser.size() ==0  || Utils.isEmpty(muser.get(0).getOtpSecret())) {
//					this.info("生成2：%s" , System.currentTimeMillis());
					String otpSecret = GoogleAuthenticator.generateSecretKey();
					this.info("生成独立密钥成功, %s",otpSecret);
					map.put("otpSecret", otpSecret);
//					this.info("生成完成2：%s" , System.currentTimeMillis());
//					mchntBO.updateByMchntUserSelective(mchntCd, muser.getUserId(),otpSecret); //更新谷歌验证码
				}else {
					map.put("otpSecret", muser.get(0).getOtpSecret());
				}
			}
			secretList.add(map);
//			long end = System.currentTimeMillis();
//			this.info("结束：%s" ,  end);
//			this.info("花费：：%s" , (end - start)/1000);
			this.info("商户%s显示验证二维码成功!  %s ",commonBO.getMchntCd(),userId );
			return commonBO.buildSuccResp("secretList" ,secretList);
		} catch (Exception e) {
			this.error("商户%s显示验证二维码错误:  %s",commonBO.getMchntCd(), e.getMessage());
			return commonBO.buildErrorResp("错误: " + e.getMessage());
		}
	}

	/**
	 * 校验谷歌验证码是否正确，以确定是否绑定成功
	 * @param secretType
	 * @param authCode
	 * @param model
	 * @param request
	 * @return
	 */
	//@AdminSessionCheck
	@SecureInitSessionCheck
	@RequestMapping(value = "/authCodeCheck", method = RequestMethod.POST)
	public @ResponseBody AjaxResult loginCheck(String userId,String authCode,String otpSecret,Model model,HttpServletRequest request) {
		AssertUtil.strIsBlank(userId, "userId is blank.");
		AssertUtil.strIsBlank(authCode, "authCode is blank.");
		AssertUtil.strIsBlank(otpSecret, "otpSecret is blank.");
		String mchntCd = commonBO.getMchntCd();
//		MchntInfo mchnt = mchntBO.getMchnt(userId);
//		MchntUserInfo muser = mchntBO.selectMchntUserInfo(mchntCd, userId);
//
//		if(!Utils.isEmpty(muser.getOtpSecret()))
//				otpSecret = muser.getOtpSecret();

        Map<String,String> eventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/authCodeCheck" ,
        				MSG.merId, mchntCd,
        				MSG.userId, userId,
        				INTER_MSG.reqIp, ""+MerUtils.getRemoteIp(request)
        				);


		GoogleAuthenticator ga = null;
		ga = new GoogleAuthenticator(mchntCd + "-" +userId, otpSecret);
		boolean loginOk = ga.checkCode(authCode);
		if(loginOk) {
			this.updateLastGaTime();
			mchntBO.updateByMchntUserSelective(mchntCd, userId,otpSecret); //更新谷歌验证码
			info("商户%s，用户：%s，谷歌验证码绑定成功！", mchntCd, userId);

           	//TODO Publish event
           	RiskEvent.mer()
               	.who(merUser(mchntCd, userId))
               	.event(RISK.Event.GAuthCodeCheckBind)
               	.result(RISK.Result.Ok)
               	.message("商户： %s, 用户： %s, 登入IP： %s, 谷歌验证码绑定成功！" , mchntCd, userId, ""+MerUtils.getRemoteIp(request))
               	.params(eventParams) //夹带其他资讯
               	.submit();

			return commonBO.buildSuccResp(); //
		}else {
			info("商户%s，用户：%s，谷歌验证码绑定失败！", mchntCd, userId);

           	//TODO Publish event
           	RiskEvent.mer()
               	.who(merUser(mchntCd, userId))
               	.event(RISK.Event.GAuthCodeCheckBind)
               	.result(RISK.Result.Failed)
               	.reason(RISK.Reason.Verify_Otp)
               	.message("商户： %s, 用户： %s, 登入IP： %s, 谷歌验证码绑定失败！" ,mchntCd, userId, ""+MerUtils.getRemoteIp(request))
               	.params(eventParams) //夹带其他资讯
               	.submit();

			return commonBO.buildErrorResp("绑定失败！");
		}
	}

	/***
	 * 银行卡白名单页面
	 * @return
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.SuperUser})
	@RequestMapping("/bankCardWhiteList")
	public String bankCardWhiteList(Model model) {
		String mchntCd =commonBO.getMchntCd();
		String secretFlag ="true";
		if(!commonBO.validateSecret(mchntCd)) {
			secretFlag ="false";
		}
		IRiskListItemMerService riskListItemMerService = this.getService(IRiskListItemMerService.class);
		Map<String, String> qryParams = new HashMap<String, String>();
		qryParams.put("mchntCd", commonBO.getMchntCd());
		qryParams.put("groupId", "102");
		qryParams.put("chnlId", "00");
		List <RiskListItemMer> riskList = riskListItemMerService.select(qryParams);
		model.addAttribute("riskList", riskList);
		model.addAttribute("secretFlag", secretFlag);
		model.addAttribute("loginType", commonBO.getLoginType());
		model.addAttribute("role", SessionKeys.ROLE.getCode());
		return "bankCardWhiteList";
	}

	/**
	 * 构造参数
	 * @param model
	 */
	/*private void buildIndexData(Model model) {
		String mchntCd = commonBO.getMchntCd();
		MchntInfo m = mchntBO.getMchnt(mchntCd);
		IMchntInfoService service = this.getService(IMchntInfoService.class);
		if(m.getOtpSecretLogin() ==null || "".equals(m.getOtpSecretLogin()) ) {
			m.setOtpSecretLogin(GoogleAuthenticator.generateSecretKey());//设置登录的Google验证
		}
		if(m.getOptSecretAdmin() ==null || "".equals(m.getOptSecretAdmin())){
			m.setOptSecretAdmin(GoogleAuthenticator.generateSecretKey());
		}
		service.updateSecretInitState(m);
		model.addAttribute("mchntCdAdmin", mchntCd + "-admin");
		model.addAttribute("mchntCd", mchntCd);
		model.addAttribute("showType" ,"0");
		model.addAttribute("status" ,m.getSecretInitState() ==null?"":m.getSecretInitState());
		model.addAttribute("otpSecretLogin", m.getOtpSecretLogin() == null ? "" : m.getOtpSecretLogin());
		model.addAttribute("optSecretAdmin", m.getOptSecretAdmin() == null ? "" : m.getOptSecretAdmin());
		IcpaySecExchangeService svc = ServiceProxy.getService(IcpaySecExchangeService.class);
		ExchangeKeyInfo info = svc.getMacKey("00",mchntCd); //获取安全密钥
		model.addAttribute("protectKeyA", info.getProtectKeyA() == null ? "" : info.getProtectKeyA());//保护密钥A
		model.addAttribute("protectKeyB", info.getProtectKeyB() == null ? "" : info.getProtectKeyB());//保护密钥B
		model.addAttribute("protectAlgorithm", info.getProtectAlgorithm() == null ? "" : info.getProtectAlgorithm());//密钥算法
		model.addAttribute("macKey", info.getKeyValue() == null ? "" : info.getKeyValue());//密钥
		model.addAttribute("loginType", commonBO.getLoginType());
	}*/

	private void buildIndexData(Model model ,MchntUserInfo mu) {
		String mchntCd = commonBO.getMchntCd();
		String role = mu.getUserRole();
		String userId = mu.getUserId();
		model.addAttribute("mchntCd", mchntCd);
		model.addAttribute("showType" ,"0");
		model.addAttribute("role" , role);
		List<MchntUserInfo> muser = new ArrayList<MchntUserInfo>();

		if(MerEnums.MerUserRole.SuperUser.getCode().equals(role)) { //管理员的时候显示
			IcpaySecExchangeService svc = ServiceProxy.getService(IcpaySecExchangeService.class);
			ExchangeKeyInfo info = svc.getMacKey("00",mchntCd); //获取安全密钥
			model.addAttribute("protectKeyA", info.getProtectKeyA() == null ? "" : info.getProtectKeyA());//保护密钥A
			model.addAttribute("protectKeyB", info.getProtectKeyB() == null ? "" : info.getProtectKeyB());//保护密钥B
			model.addAttribute("protectAlgorithm", info.getProtectAlgorithm() == null ? "" : info.getProtectAlgorithm());//密钥算法
			model.addAttribute("macKey", info.getKeyValue() == null ? "" : info.getKeyValue());//密钥

			muser = mchntBO.selectMchntUserList(mchntCd,userId,null);
		}else {
			model.addAttribute("protectKeyA",  "");//保护密钥A
			model.addAttribute("protectKeyB",  "");//保护密钥B
			model.addAttribute("protectAlgorithm",  "");//密钥算法
			model.addAttribute("macKey",  "");//密钥

			MchntUserInfo m = mchntBO.selectMchntUserInfo(mchntCd, userId);
			muser.add(m);
		}
		model.addAttribute("muser",muser);
	}

	/**
	 * 添加银行卡白名单
	 * @return
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.SuperUser})
	@RequestMapping("/addBankList")
	public @ResponseBody AjaxResult addBankList(String bankList,String secretHash,String googlePwd, HttpServletRequest request) {
		String mchntCd = commonBO.getMchntCd();
		GoogleAuthenticator ga = null;
		MchntUserInfo muser = commonBO.getMchntUser();
		muser = mchntBO.selectMchntUserInfo(mchntCd, muser.getUserId());

        Map<String,String> eventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/addBankList.do" ,
        				MSG.merId, mchntCd,
        				MSG.userId, muser.getUserId(),
        				INTER_MSG.reqIp, ""+MerUtils.getRemoteIp(request)
        				);

		// 校验登录密码
		if (!PwdUtil.validatePwd(secretHash, googlePwd, null, muser.getLoginPwd())) {

        	RiskEvent.mer()
        	.who(merUser(mchntCd, muser.getUserId()))
        	.event(RISK.Event.Withdraw_Bankcard_Add)
        	.result(RISK.Result.Failed)
        	.reason(RISK.Reason.Verify_Passwd)
        	.message("商户： %s; 用户： %s; 密码错误", mchntCd, muser.getUserId())
        	.params(eventParams) //夹带其他资讯
        	.submit();

			return commonBO.buildSuccResp("respData", "10");
		}

		ga = new GoogleAuthenticator(mchntCd +"-" + muser.getUserId(), muser.getOtpSecret());

		boolean loginOk = ga.checkCode(googlePwd);
		if(!loginOk) {
				RiskEvent.mer()
	        	.who(merUser(mchntCd, muser.getUserId()))
	        	.event(RISK.Event.Withdraw_Bankcard_Add)
	        	.result(RISK.Result.Failed)
	        	.reason(RISK.Reason.Verify_Otp)
	        	.message("商户： %s; 用户： %s; 谷歌验证失败", mchntCd, muser.getUserId())
	        	.params(eventParams) //夹带其他资讯
	        	.submit();

			//GA验证码输入错误，记录错误次数
        	if (GoogleAuthenticatorUtils.countGaLoginFail(muser)) {
        		return commonBO.buildSuccResp("respData", "11");//谷歌验证失败
        	} else {
        		//放進session
        		muser.setUserState(MerUserState.Locked.getCode());
        		commonBO.setSessionData(SessionKeys.MCHNT_USER_INFO.getCode(), muser);
        		return commonBO.buildSuccResp("respData", "12");//谷歌验证码连续输入錯誤，帐号已被锁定
        	}
		}else {
			this.updateLastGaTime();
			String [] bankArray = bankList.replaceAll("[，,\\s\\n]", ",").split(",");
			List<String> list = new ArrayList<String>();
			for(String item : bankArray) {
				item = StringUtil.trim(item);
				String[] accInfo = this.getAccNoInfo(item);
				RiskListItemMer merItem = new RiskListItemMer();
				String accName = accInfo[0];
				String accNo = accInfo[1];
				//if(MerUtils.checkBankCard(accNo) && (!Utils.isEmpty(accName))) {//判断是否为正确的银行卡
				if (checkBankCardInfo(accName, accNo)) {
					merItem.setItem(this.accWhiteItem(accName, accNo));
					merItem.setGroupId(102);//商户代付银行卡白名单
					merItem.setChnlId("00");//前端小商户号，00
					merItem.setMchntCd(mchntCd);
					IRiskListItemMerService riskListItemMerService = this.getService(IRiskListItemMerService.class);
					riskListItemMerService.add(merItem);//记录数据
					this.info("商户%s, 用戶名：%s, 新增代付白名单成功： '%s|%s' ", mchntCd, muser.getUserId(), accName, accNo);

		           	//TODO Publish event 新增代付白名单成功
		           	RiskEvent.mer()
		               	.who(merUser(mchntCd, muser.getUserId()))
		               	.event(RISK.Event.Withdraw_Bankcard_Add)
		               	.result(RISK.Result.Ok)
		               	.message("商户： %s, 用户： %s, 新增代付白名单成功： '%s|%s' " , mchntCd, muser.getUserId(), accName, accNo)
		               	.target(accName+"|"+accNo)
		               	.params(eventParams) //夹带其他资讯
		               	.params(MSG.accName, accName)
		               	.params(MSG.accNum, accNo)
		               	.submit();

				}else {
					this.error("商户%s, 用戶名：%s, 新增代付白名单失败： 姓名 '%s' 或 银行卡号 '%s' 格式错误!", mchntCd, muser.getUserId(), accName, accNo);

		           	//TODO Publish event 新增代付白名单失败
		           	RiskEvent.mer()
		               	.who(merUser(mchntCd, muser.getUserId()))
		               	.event(RISK.Event.Withdraw_Bankcard_Add)
		               	.result(RISK.Result.Failed)
		               	.reason(RISK.Reason.InvalidInfo)
		               	.message("商户： %s, 用户： %s, 新增代付白名单失败： 姓名 '%s' 或 银行卡号 '%s' 格式错误!" , mchntCd, muser.getUserId(), accName, accNo)
		               	.target(accName+"|"+accNo)
		               	.params(eventParams) //夹带其他资讯
		               	.params(MSG.accName, accName)
		               	.params(MSG.accNum, accNo)
		               	.submit();

					list.add(accNo);
				}
			}
			return commonBO.buildSuccResp("list",list);
		}

	}


	protected boolean checkBankCardInfo(String accName, String accNo) {
		if ("ALL".equals(accName)) return true;
		if ("ALL".equals(accNo)) return true;
		return (MerUtils.checkBankCard(accNo) && (!Utils.isEmpty(accName)));
	}


	/**
	 * 预校验银行卡是否正确
	 */
	//@AdminSessionCheck
	@RequestMapping("preValid")
	public @ResponseBody AjaxResult preValid(String preValidData) {
		String validSt ="true";
		StringBuffer bf = new StringBuffer();
		List<String> list = new ArrayList<String>();
		if(!MerUtils.checkBankCard(preValidData)) {
			validSt = "false";
			bf.append(preValidData +",");
		}
		list.add(validSt);
		list.add("".equals(bf)?"":bf.toString());
		return commonBO.buildSuccResp("list",list);
	}

	/**
	 * 预校验银行卡是否正确
	 */
	//@AdminSessionCheck
	@RequestMapping("validCardList")
	public @ResponseBody AjaxResult validAccNoList(String accListData, HttpServletRequest request) {
		logger.debug(String.format("[ajax] validAccNoList: %s", accListData));
        String lang = I18nMsgUtils.getLanguage(request);
		List<String> list = MerUtils.checkBankCardList(accListData);
		String errMsg=null;
		if (list==null)
			errMsg=mappingI18n(this.getClass().getSimpleName(),"请输入持卡人及其卡号", lang);
		if (list.size()>0)
			errMsg=mappingI18n(this.getClass().getSimpleName(),"持卡人或卡号格式错误", lang);
		if (Utils.isEmpty(errMsg))
			return commonBO.buildSuccResp("errList",list);
		else
			return commonBO.buildErrorResp(errMsg,"errList",list);
	}

	/**
	 * 银行卡白名单列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/bankList")
	public @ResponseBody AjaxResult getBankList(int pageNum, int pageSize , HttpServletRequest req) {
		Map<String, String> qryParams = new HashMap<String, String>();
		qryParams.put("mchntCd", commonBO.getMchntCd());
		// 获取银行卡号
		IRiskListItemMerService riskListItemMerService = this.getService(IRiskListItemMerService.class);
		Pager<RiskListItemMer> p = riskListItemMerService.selectByPage(pageNum, pageSize,qryParams);
		return commonBO.buildSuccResp(MerConstants.PAGER_RESULT, 
				commonBO.transferPager(p, MerConstants.PAGE_CONF_BANKLIST, ENTITY_TRANSFER , req));
	}
	/**
	 * 确认登录密码与谷歌验证码
	 */
	@RequestMapping("/comfirmPwd")
	@SecureInitSessionCheck
	public @ResponseBody AjaxResult comfirmPwd(String secretHash,String googlePwd,HttpServletRequest request) {
		String mchntCd = commonBO.getMchntCd();
//		String loginType =commonBO.getLoginType();
//		MchntInfo mchnt = mchntBO.getMchnt(mchntCd);
		GoogleAuthenticator ga = null;

//		MchntUserInfo muser = commonBO.getSessionData(SessionKeys.MCHNT_USER_INFO.getCode());
		MchntUserInfo muser = commonBO.getMchntUser();
		muser = mchntBO.selectMchntUserInfo(mchntCd, muser.getUserId());

		Map<String,String> eventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/comfirmPwd" ,
        				MSG.merId, mchntCd,
        				MSG.userId, muser.getUserId(),
        				INTER_MSG.reqIp, ""+MerUtils.getRemoteIp(request)
        				);

		// 校验登录密码
		if (!PwdUtil.validatePwd(secretHash, googlePwd, null, muser.getLoginPwd())) {

			RiskEvent.mer()
        	.who(merUser(mchntCd, muser.getUserId()))
        	.event(RISK.Event.PwdCheck)
        	.result(RISK.Result.Failed)
        	.reason(RISK.Reason.Verify_Passwd)
        	.message("商户： %s; 用户： %s; 密码错误", mchntCd, muser.getUserId())
        	.params(eventParams) //夹带其他资讯
        	.submit();

			return commonBO.buildSuccResp("respData", "10");
		}

//		if(loginType.equals("0")) {
//			mchntCd = mchntCd +"-admin";
//			//验证谷歌验证码
//			ga = new GoogleAuthenticator(mchntCd, mchnt.getOptSecretAdmin());
//		}else {

		ga = new GoogleAuthenticator(mchntCd +"-" + muser.getUserId(), muser.getOtpSecret());
//		}

		boolean loginOk = ga.checkCode(googlePwd);
		if(!loginOk) {

			RiskEvent.mer()
        	.who(merUser(mchntCd, muser.getUserId()))
        	.event(RISK.Event.GAuthCodeCheck)
        	.result(RISK.Result.Failed)
        	.reason(RISK.Reason.Verify_Otp)
        	.message("商户： %s; 用户： %s; 谷歌验证失败", mchntCd, muser.getUserId())
        	.params(eventParams) //夹带其他资讯
        	.submit();

			//GA验证码输入错误，记录错误次数
			if (GoogleAuthenticatorUtils.countGaLoginFail(muser)) {
        		return commonBO.buildSuccResp("respData", "11");//谷歌验证失败
        	} else {
        		//放進session
        		muser.setUserState(MerUserState.Locked.getCode());
        		commonBO.setSessionData(SessionKeys.MCHNT_USER_INFO.getCode(), muser);
        		return commonBO.buildSuccResp("respData", "12");//谷歌验证码连续输入錯誤，帐号已被锁定
        	}
		}
		this.updateLastGaTime();
		return commonBO.buildSuccResp("respData", "00");
	}

	/**
	 * 重置安全密钥
	 * @return
	 */
	@RolesSessionCheck(roles= {MerUserRole.SuperUser})
	@RequestMapping(value = "/genMacKey.do",method = RequestMethod.GET)
	public @ResponseBody AjaxResult genMacKey(HttpServletRequest request) {
		String mchntCd = commonBO.getMchntCd();

        Map<String,String> eventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/genMacKey.do" ,
        				MSG.merId, mchntCd,
        				MSG.userId, commonBO.getMchntUser().getUserId(),
        				INTER_MSG.reqIp, ""+MerUtils.getRemoteIp(request)
        				);

		this.info("商户 %s 请求重置密钥...", mchntCd);
		try {
			IcpaySecExchangeService svc = ServiceProxy.getService(IcpaySecExchangeService.class);
			ExchangeKeyInfo info = svc.genMacKey("00",mchntCd);//调用接口，重置安全密钥
			List<Map<String,String>> list =new ArrayList<Map<String,String>>();
			Map<String,String> map =new HashMap<String,String>();
			map.put("protectKeyA", info.getProtectKeyA() == null ? "" : info.getProtectKeyA());//保护密钥A
			map.put("protectKeyB", info.getProtectKeyB() == null ? "" : info.getProtectKeyB());//保护密钥B
			map.put("protectAlgorithm", info.getProtectAlgorithm() == null ? "" : info.getProtectAlgorithm());//密钥算法
			map.put("macKey", info.getKeyValue() == null ? "" : info.getKeyValue());
			list.add(map);
			this.info("商户 %s 重置密钥成功 !", mchntCd);

           	//TODO Publish event
           	RiskEvent.mer()
               	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
               	.event(RISK.Event.Gen_Mac_Key)
               	.result(RISK.Result.Ok)
               	.message("商户： %s, 用户： %s, 重置密钥成功 ! " , mchntCd, commonBO.getMchntUser().getUserId())
               	.params(eventParams) //夹带其他资讯
               	.submit();

			return commonBO.buildSuccResp("list",list);
		} catch (Exception e) {
			this.error("商户 %s 重置密钥失败: %s", mchntCd, e.getMessage());

           	//TODO Publish event
           	RiskEvent.mer()
               	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
               	.event(RISK.Event.Gen_Mac_Key)
               	.result(RISK.Result.Failed)
               	.message("商户： %s, 用户： %s, 重置密钥失败： %s" , mchntCd, commonBO.getMchntUser().getUserId(), e.getMessage())
               	.params(eventParams) //夹带其他资讯
               	.submit();

			return commonBO.buildErrorResp("重置失败: "+e.getMessage());
		}
	}

	/**
	 * 删除银行卡
	 * @param id
	 * @return
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.SuperUser})
	@RequestMapping(value="/deleteBankCard",method=RequestMethod.GET)
	public @ResponseBody AjaxResult deleteBankCard(String id, HttpServletRequest request) {

		MchntUserInfo muser = commonBO.getMchntUser();

        Map<String,String> eventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/deleteBankCard" ,
        				MSG.merId, commonBO.getMchntCd(),
        				MSG.userId, commonBO.getMchntUser().getUserId(),
        				INTER_MSG.reqIp, ""+MerUtils.getRemoteIp(request)
        				);

		IRiskListItemMerService riskListItemMerService = this.getService(IRiskListItemMerService.class);

		RiskListItemMer merItem = riskListItemMerService.selectByPrimaryKey(Long.valueOf(id));
		riskListItemMerService.delete(Long.valueOf(id));
		this.info("商户： %s, 用戶名：%s, 删除銀行卡成功：%s", commonBO.getMchntCd(), muser.getUserId(), merItem.getItem());

       	RiskEvent.mer()
           	.who(merUser(commonBO.getMchntCd(), commonBO.getMchntUser().getUserId()))
           	.event(RISK.Event.Withdraw_Bankcard_Del)
           	.result(RISK.Result.Ok)
           	.message("商户： %s, 用户： %s, 删除銀行卡成功：%s" , commonBO.getMchntCd(), commonBO.getMchntUser().getUserId(), merItem.getItem())
           	.target(merItem.getItem())
           	.params(eventParams) //夹带其他资讯
           	.submit();

		return commonBO.buildSuccResp();
	}


	/***
	 * 转帐白名单页面
	 * @return
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.SuperUser})
	@RequestMapping("/transferWhiteList")
	public String transferWhiteList(Model model) {
		String mchntCd =commonBO.getMchntCd();
		String secretFlag ="true";
		if(!commonBO.validateSecret(mchntCd)) {
			secretFlag ="false";
		}
		IRiskListItemMerService riskListItemMerService = this.getService(IRiskListItemMerService.class);
		Map<String, String> qryParams = new HashMap<String, String>();
		qryParams.put("mchntCd", commonBO.getMchntCd());
		qryParams.put("groupId", "108");
		qryParams.put("chnlId", "00");
		List <RiskListItemMer> riskList = riskListItemMerService.select(qryParams);
		model.addAttribute("riskList", riskList);
		model.addAttribute("secretFlag", secretFlag);
		model.addAttribute("loginType", commonBO.getLoginType());
		return "transferWhiteList";
	}

	/**
	 * 预校验商户号是否正确
	 */
	//@AdminSessionCheck
	@RequestMapping("validMchntCdList")
	public @ResponseBody AjaxResult validMchntCdList(String mchntCdListData, HttpServletRequest request) {
		logger.debug(String.format("[ajax] validMchntCdList: %s", mchntCdListData));
        String lang = I18nMsgUtils.getLanguage(request);
		String[] respList = mchntCdListData.replaceAll("[\\s,，]", ",").split("[,]");
		String errMsg = null;

		//验证格式位数
		List<String> list = MerUtils.checkMchntCdList(mchntCdListData);
		if (list == null) {
			errMsg = mappingI18n(this.getClass().getSimpleName(),"请输入商户号", lang);
			return commonBO.buildSuccResp("errList",respList);
		}
		if (list.size()>0) {
			errMsg = mappingI18n(this.getClass().getSimpleName(), "无此商户号，请检查后输入", lang);
			return commonBO.buildErrorResp(errMsg,"errList",respList);
		}

		//验证商户號是否存在
		List<String> mchntCdList = this.checkMchntCd(mchntCdListData);
		if (mchntCdList.size() == 0) {
			errMsg = mappingI18n(this.getClass().getSimpleName(), "无此商户号，请检查后输入", lang);
			return commonBO.buildErrorResp(errMsg,"errList",respList);
		}

		//验证商户号是否同集团、同交易类型
		List<String> errList = this.checkMchntName(mchntCdListData);
		if (errList.size() == 0) {
			errMsg = mappingI18n(this.getClass().getSimpleName(),"该商户非同集团同交易类型商户，请联系客服确认", lang);
			return commonBO.buildErrorResp(errMsg,"errList",respList);
		}
		return commonBO.buildSuccResp("errList",mchntCdListData);
	}

	/**
	 * 验证商户号是否同集团、同交易类型
	 * @param mchntCdListData
	 * @return
	 */
	private List<String> checkMchntName(String mchntCdListData) {
		String mchntCd = commonBO.getMchntCd();
		IMchntInfoService mchntInfoService = this.getService(IMchntInfoService.class);
		MchntInfo mchntInfo = mchntInfoService.selectByPrimaryKey(mchntCd);
		String mchntCnNm = mchntInfo.getMchntCnNm();
		String checkMchntCnNm = mchntCnNm.substring(0, mchntCnNm.indexOf("_"));//目前登入的商户名
		String lastName = mchntCnNm.substring(mchntCnNm.indexOf("_") + 1);
		String payTypeName = lastName.substring(0, lastName.indexOf("_"));//交易类型
		String name = checkMchntCnNm + "_" + payTypeName;//商户名_交易类型
		String[] mchntCdList = mchntCdListData.replaceAll("[\\s,，]", ",").split("[,]");
		List<String> errList = new ArrayList<String>();
		for(int i=0; i < mchntCdList.length; i++) {
			Map<String, String> qryParams = new HashMap<String, String>();
			qryParams.put("mchntCd", mchntCdList[i]);
			qryParams.put("mchntCnNm", name + "%");
			List<MchntInfo> mchntInfoList = mchntInfoService.select(qryParams);
			if (mchntInfoList != null && mchntInfoList.size() > 0) {
				errList.add(mchntCdList[i]);
			}
		}
		if (errList.size() != mchntCdList.length) {
			errList.clear();
		}
		return errList;
	}

	/**
	 * 验证商户號是否存在
	 * @param mchntCdListData
	 * @return
	 */
	private List<String> checkMchntCd(String mchntCdListData) {
		IMchntInfoService mchntInfoService = this.getService(IMchntInfoService.class);
		String[] mchntCdList = mchntCdListData.replaceAll("[\\s,，]", ",").split("[,]");
		List<String> errList = new ArrayList<String>();
		for(int i=0; i < mchntCdList.length; i++) {
			Map<String, String> qryParams = new HashMap<String, String>();
			qryParams.put("mchntCd", mchntCdList[i]);
			List<MchntInfo> mchntInfoList = mchntInfoService.select(qryParams);
			if (mchntInfoList != null && mchntInfoList.size() > 0) {
				errList.add(mchntCdList[i]);
			}
		}
		if (errList.size() != mchntCdList.length) {
			errList.clear();
		}
		return errList;
	}

	/**
	 * 添加商户号白名单
	 * @return
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.SuperUser})
	@RequestMapping("/addMchntCdList")
	public @ResponseBody AjaxResult addMchntCdList(String mchntCdList, HttpServletRequest request) {
		String mchntCd = commonBO.getMchntCd();
		MchntUserInfo muser = commonBO.getMchntUser();

        Map<String,String> eventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/addMchntCdList" ,
        				MSG.merId, mchntCd,
        				MSG.userId, commonBO.getMchntUser().getUserId(),
        				INTER_MSG.reqIp, ""+MerUtils.getRemoteIp(request)
        				);

		IMchntInfoService mchntInfoService = this.getService(IMchntInfoService.class);
		String [] mchntCdArray = mchntCdList.replaceAll("[，,\\s\\n]", ",").split(",");
		List<String> list = new ArrayList<String>();
		for(String item : mchntCdArray) {
			item = StringUtil.trim(item);
			MchntInfo mchntInfo = mchntInfoService.selectByPrimaryKey(item);
			String mchntCnNm = mchntInfo.getMchntCnNm();
			RiskListItemMer merItem = new RiskListItemMer();
			merItem.setItem(mchntCnNm + "|" + item);
			merItem.setGroupId(108);//商户转帐白名单
			merItem.setChnlId("00");//前端小商户号，00
			merItem.setMchntCd(mchntCd);
			IRiskListItemMerService riskListItemMerService = this.getService(IRiskListItemMerService.class);
			riskListItemMerService.add(merItem);//记录数据

           	//TODO Publish event
           	RiskEvent.mer()
               	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
               	.event(RISK.Event.Transfer_WhiteList_Add)
               	.result(RISK.Result.Ok)
               	.message("商户： %s, 用户： %s, 新增商户转账白名单成功： '%s' " , mchntCd, commonBO.getMchntUser().getUserId(), item)
               	.target(mchntCnNm+ "|" +item)
               	.params(eventParams) //夹带其他资讯
               	.submit();

			this.info("商户：%s, 用戶名：%s, 新增商户转账白名单成功： '%s' ", mchntCd, muser.getUserId(), item);
		}
		return commonBO.buildSuccResp("list",list);
	}

}
