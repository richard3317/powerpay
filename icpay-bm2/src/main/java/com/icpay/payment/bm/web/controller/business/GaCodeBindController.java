package com.icpay.payment.bm.web.controller.business;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.gauth.GoogleAuthenticator;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.db.dao.mybatis.model.BmUserInfo;
import com.icpay.payment.db.service.IBmUserService;

@Controller
@RequestMapping("/gaCodeBind")
public class GaCodeBindController extends BaseController {

	private static final String RESULT_BASE_URI = "gaCodeBind";
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model, String settleDate) {
		model.addAttribute("userId", this.getSessionUsrId());	
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	/**
	 * 获取谷歌验证码的Secret
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/getSecret.do", method=RequestMethod.POST)
	public @ResponseBody AjaxResult getSecret(Model model, String userId) {
		AssertUtil.strIsBlank(userId, "userId is blank.");

		try {
			String otpSecret = GoogleAuthenticator.generateSecretKey();
			this.info("生成独立密钥成功, %s",otpSecret);
			this.info("%s显示验证二维码成功!  ", userId );
			return commonBO.buildSuccResp("otpSecret" ,otpSecret);
		} catch (Exception e) {
			this.error("%s显示验证二维码错误:  %s", userId, e.getMessage());
			return commonBO.buildErrorResp("错误: " + e.getMessage());
		}
	}
	
	/**
	 * 校验谷歌验证码是否正确，以确定是否绑定成功
	 * @param model
	 * @param userId
	 * @param authCode
	 * @param otpSecret
	 * @return
	 */
	@RequestMapping(value = "/authCodeCheck.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult authCodeCheck(Model model, String userId, String authCode, String otpSecret) {
		AssertUtil.strIsBlank(userId, "userId is blank.");
		AssertUtil.strIsBlank(authCode, "authCode is blank.");
		AssertUtil.strIsBlank(otpSecret, "otpSecret is blank.");
		
		GoogleAuthenticator ga = new GoogleAuthenticator(userId, otpSecret);
		boolean loginOk = ga.checkCode(authCode);
		if(loginOk) {
			IBmUserService userService = this.getDBService(IBmUserService.class);
			// 根据登录名获取用户信息
			BmUserInfo usrInfo = userService.selectByPrimaryKey(userId);
			// 取出暫存在OtpSecret裡的原有角色權限
			String tempRoleId = usrInfo.getOtpSecret();
			// 把只有綁定谷歌驗證碼的角色取代掉
			usrInfo.setRoleId(Integer.valueOf(tempRoleId));
			usrInfo.setOtpSecret(otpSecret);
			userService.update(usrInfo, false);
			info("用户名：%s，谷歌验证码绑定成功！", userId);
			return commonBO.buildSuccResp();
		}else {
			info("用户名：%s，谷歌验证码绑定失败！", userId);
			return commonBO.buildErrorResp("绑定失败！");
		}
	}
	
}
