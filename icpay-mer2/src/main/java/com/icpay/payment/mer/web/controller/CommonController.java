package com.icpay.payment.mer.web.controller;

import static com.icpay.payment.risk.MerUser.merUser;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.constants.Names.MSG;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.ValidateCodeImg;
import com.icpay.payment.common.enums.AjaxRespEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.IOUtil;
import com.icpay.payment.common.utils.PwdUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.BankInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.db.service.IMchntUserInfoService;
import com.icpay.payment.mer.bo.MchntBO;
import com.icpay.payment.mer.cache.BankInfoCaChe;
import com.icpay.payment.mer.session.SessionKeys;
import com.icpay.payment.mer.util.I18nMsgUtils;
import com.icpay.payment.mer.util.MerUtils;
import com.icpay.payment.mer.web.interceptor.IgnoreSessionCheck;
import com.icpay.payment.mer.web.interceptor.SecureInitSessionCheck;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;

@Controller
public class CommonController extends BaseController {

	//private static final Logger logger = Logger.getLogger(CommonController.class);

	@Autowired
	private MchntBO mchntBO;

	@RequestMapping(value = "/expired", method = RequestMethod.GET)
	@IgnoreSessionCheck
	public String expired() {
		return "common/expired";
	}

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	@IgnoreSessionCheck
	public String errorMessage(Model model, String errorMsg) {
		if (!Utils.isEmpty(errorMsg)) {
			model.addAttribute("errorMsg", errorMsg);
		}
		return "common/error";
	}

	@SecureInitSessionCheck
	@RequestMapping(value = "/chngPwd", method = RequestMethod.GET)
	public String chngPwd() {
		return "chngPwd";
	}

	@SecureInitSessionCheck
	@RequestMapping(value = "/chngPwdSubmit", method = RequestMethod.POST)
	public @ResponseBody AjaxResult chngPwdSubmit(String oldPwd, String newPwd1, String newPwd2,String userId, HttpServletRequest request) {
		String lang = I18nMsgUtils.getLanguage(request);
		AssertUtil.strIsBlank(oldPwd, mappingI18n(this.getClass().getSimpleName(),"oldPwd is blank.", lang));
		AssertUtil.strIsBlank(newPwd1, mappingI18n(this.getClass().getSimpleName(),"newPwd1 is blank.", lang));
		AssertUtil.strIsBlank(newPwd2, mappingI18n(this.getClass().getSimpleName(),"newPwd2 is blank.", lang));
		AssertUtil.strNotEquals(newPwd1, newPwd2, mappingI18n(this.getClass().getSimpleName(),"新密码两次输入不一致", lang));
		
		String mchntCd = commonBO.getMchntCode();
		
        Map<String,String> eventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/chngPwdSubmit" ,
        				MSG.merId, commonBO.getMchntCd(),
        				MSG.userId, commonBO.getMchntUser().getUserId(),
        				INTER_MSG.reqIp, ""+MerUtils.getRemoteIp(request)
        				);
		
//		MchntInfo m = service.selectByPrimaryKey(mchntCd);
//		AssertUtil.objIsNull(m, "未找到商户信息");

//		MchntInfo mchntInfo = commonBO.getLoginMer();
//		MchntUserInfo muser = commonBO.getSessionData(SessionKeys.MCHNT_USER_INFO.getCode());
//		MchntUserInfo muser = commonBO.getMchntUser();
		MchntUserInfo muser = mchntBO.selectMchntUserInfo(mchntCd, userId);
		MchntInfo mchntNew = new MchntInfo();
		mchntNew.setMchntCd(mchntCd);
		/*if(!PwdUtil.validatePwd(oldPwd,mchntInfo.getLoginPwd())) {
			this.warn("[更新密码] 商户 %s 原密码输入错误。", mchntCd);
			return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED, "原密码输入错误");
		}*/

		if(!PwdUtil.validatePwd(oldPwd,muser.getLoginPwd())) {
			this.warn("[更新密码] 用户 %s 原密码输入错误。", mchntCd);
			
			RiskEvent.mer()
           	.who(merUser(mchntCd, muser.getUserId()))
           	.event(RISK.Event.Change_Pwd)
           	.result(RISK.Result.Failed)
           	.reason(RISK.Reason.InvalidInfo)
           	.message("商户： %s, 用户： %s, 原密码输入错误，更新密码失败 " , mchntCd, muser.getUserId())
           	.params(eventParams) //夹带其他资讯
           	.submit();
			
			return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED, mappingI18n(this.getClass().getSimpleName(),"原密码输入错误", lang));
		}

//		mchntNew.setLoginPwd(PwdUtil.computeMD5Pwd(newPwd1));
		muser.setLoginPwd(PwdUtil.computeMD5Pwd(newPwd1));
		try {
			// 保存新密码
			/*IMchntInfoService service = this.getService(IMchntInfoService.class);
			service.updateLoginInfo(mchntNew);*/
			String oriState = muser.getUserState();
			this.info("[更新密码] 用户状态 %s ", oriState);
			IMchntUserInfoService service = this.getService(IMchntUserInfoService.class);
			//muser.setRecUpdTs(new Date());
			muser.setPwdUpdTs(DateUtil.now19());
			muser.setUserState("1");//有效
			service.updateByPrimaryKeySelective(muser);

			this.info("[更新密码] 用户 %s 更新密码成功", muser.getUserId());
			
           	RiskEvent.mer()
               	.who(merUser(mchntCd, muser.getUserId()))
               	.event(RISK.Event.Change_Pwd)
               	.result(RISK.Result.Ok)
               	.message("商户： %s, 用户： %s, 更新密码成功 " , mchntCd, muser.getUserId())
               	.params(eventParams) //夹带其他资讯
               	.submit();
			
			return commonBO.buildSuccResp("oriState",oriState);
		} catch (Exception e) {
			this.error(e, "[更新密码] 用户 %s 更新密码失败： %s", muser.getUserId(), e.getMessage());
			
           	RiskEvent.mer()
           	.who(merUser(mchntCd, muser.getUserId()))
           	.event(RISK.Event.Change_Pwd)
           	.result(RISK.Result.Failed)
           	.reason(RISK.Reason.Exception)
           	.message("商户： %s, 用户： %s, 更新密码失败 " , mchntCd, muser.getUserId())
           	.params(eventParams) //夹带其他资讯
           	.submit();
           	
			return commonBO.buildErrorResp(mappingI18n(this.getClass().getSimpleName(),"变更密码失败!", lang));
		}

	}

	@RequestMapping(value = "/validateCode", method = RequestMethod.GET)
	@IgnoreSessionCheck
	public String validateCode(HttpServletResponse response) {
		ValidateCodeImg img = new ValidateCodeImg();
		commonBO.setSessionData(SessionKeys.VALIDATION_CODE.getCode(), img.getStrRand(), true);
		BufferedImage image = img.getImage();
		OutputStream os = null;
		try {
			response.reset();
			response.setContentType("image/jpeg");
			os = response.getOutputStream();
			ImageIO.write(image, "jpg", os);
		} catch (Exception e) {
			this.error(e, "生成图形校验码失败: %s", e.getMessage());
		} finally {
			if (os != null) {
				IOUtil.close(os);
			}
		}
		return null;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody AjaxResult test() {
		return commonBO.buildSuccResp();
	}

	@RequestMapping(value="/index2")
	public String index2(String mchntCd, String loginPwd, String validateCode,
			HttpServletRequest request) {
		return "index_sample";
	}

	@RequestMapping(value = "/loadBankBranchLst.do")
	public @ResponseBody List<BankInfo> loadBankBranchLst(String bankName, String brankBranchNm) {
		AssertUtil.strIsBlank(bankName, "bankName is blank.");

		List<BankInfo> lst = BankInfoCaChe.getBankBranchLst(bankName);
		List<BankInfo> result = new ArrayList<BankInfo>();
		for (BankInfo b : lst) {
			if (StringUtil.isBlank(brankBranchNm)
					|| b.getBranchBankName().indexOf(brankBranchNm) > -1) {
				result.add(b);
				if (result.size() == 50) {
					break;
				}
			}
		}
		return result;
	}


	/**
	 * 预校验银行卡是否正确
	 */
	@RequestMapping("preValid.do")
	public String preValid(String accNo, Model model, HttpServletRequest request) {
        String lang = I18nMsgUtils.getLanguage(request);
		String result = "true";
		if(!MerUtils.checkBankCard(accNo)) {
			result=mappingI18n(this.getClass().getSimpleName(),"输入的银行卡不合法!", lang);
		}
		model.addAttribute("result", result);
		return "common/ajax_validate_result";
	}

	/**
	 * 获取新的订单号
	 */
	@RequestMapping("/newOrderNum.do")
	public @ResponseBody String getNewOrderId() {
		return this.nextOrderId(PrefixForPayment);
	}

//	public String newOrderId() { //TODO use jedis
//		try {
//			Long tm = System.currentTimeMillis();
//			return (""+tm)+Utils.getRandomInt(1000, 9999);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "";
//		}
//	}
}
