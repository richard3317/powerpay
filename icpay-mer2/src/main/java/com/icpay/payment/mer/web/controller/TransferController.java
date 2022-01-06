package com.icpay.payment.mer.web.controller;

import static com.icpay.payment.risk.MerUser.merUser;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.gauth.GoogleAuthenticator;
import com.icpay.payment.common.constants.Constant.ACC_SUBTYPE;
import com.icpay.payment.common.constants.Constant.TxnSource;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.constants.Names.MSG;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.enums.AccEnums;
import com.icpay.payment.common.enums.AjaxRespEnums;
import com.icpay.payment.common.enums.CurrencyEnums;
import com.icpay.payment.common.enums.MerEnums.MerUserRole;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.exception.SystemException;
import com.icpay.payment.common.utils.Amount;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.Converter;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.PwdUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfoKey;
import com.icpay.payment.db.dao.mybatis.model.RiskListItemMer;
import com.icpay.payment.db.service.IMchntAccountService;
import com.icpay.payment.db.service.IRiskListItemMerService;
import com.icpay.payment.mer.bo.MchntAccountBO;
import com.icpay.payment.mer.bo.MchntBO;
import com.icpay.payment.mer.cache.BankInfoCaChe;
import com.icpay.payment.mer.util.EnumLangUtil;
import com.icpay.payment.mer.util.I18nMsgUtils;
import com.icpay.payment.mer.util.MerUtils;
import com.icpay.payment.mer.web.interceptor.RolesSessionCheck;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;
import com.icpay.payment.service.ChnlMerAccService;

@Controller
@RequestMapping("/transfer")
public class TransferController extends TransLogBasedController {

	@Autowired
	private MchntAccountBO mchntAccountBO;
	
	private static final Logger logger = Logger.getLogger(WithdrawController.class);
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			BigDecimal transAmt = null;
			if (StringUtil.isNotBlank(m.get("transAmt"))) {
				transAmt = new BigDecimal(m.get("transAmt"));
				m.put("transAmtDesc", transAmt.movePointLeft(2).toString());
			} else {
				m.put("transAmtDesc", "0.00");
			}
			BigDecimal transAt = null;
			if (StringUtil.isNotBlank(m.get("transAt"))) {
				transAt = new BigDecimal(m.get("transAt"));
				m.put("transAtDesc", transAt.movePointLeft(2).toString());
			} else {
				m.put("transAtDesc", "0.00");
			}
			BigDecimal settleAmt = null;
			if (StringUtil.isNotBlank(m.get("settleAmt"))) {
				settleAmt = new BigDecimal(m.get("settleAmt"));
				m.put("settleAmtDesc", settleAmt.movePointLeft(2).toString());
			} else {
				m.put("settleAmtDesc", "0.00");
			}
			BigDecimal transFee = null;
			if (StringUtil.isNotBlank(m.get("transFee"))) {
				transFee = new BigDecimal(m.get("transFee"));
				m.put("transFeeDesc", transFee.movePointLeft(2).toString());
			} else {
				m.put("transFeeDesc", "0.00");
			}
			String accNo = m.get("accNo");
			if (StringUtil.isBlank(accNo)) {
				m.put("accNoDesc", "");
			} else {
				m.put("accNoDesc", accNo);
			}
			String accName = m.get("accName");
			if (StringUtil.isBlank(accName)) {
				m.put("accNameDesc", "");
			} else {
				m.put("accNameDesc", accName);
			}
			// 交易类型
			String intTransCd = m.get("intTransCd");
			m.put("intTransCdDesc", EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, false));
			String rspCd = m.get("rspCd");
			if (!StringUtil.isBlank(rspCd)) {
				m.put("rspCdDesc", rspCd + "-" + m.get("errMsg"));
			} else {
				m.put("rspCdDesc", rspCd);
			}
			String txnState = m.get("txnState");
			if (!StringUtil.isBlank(txnState)) {
				m.put("txnStateDesc", EnumUtil.translate(TxnEnums.TxnStatus.class, txnState, false));
			}
			String extTransDt = m.get("extTransDt");
			String extTransTm = m.get("extTransTm");
			m.put("transTm", extTransDt + " " + extTransTm);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 日期原始格式
			String origFormat = "yyyyMMdd HHmmss";
			DateFormat format = new SimpleDateFormat(origFormat);
			Date date;
			try {
				date = format.parse(m.get("transTm"));
				m.put("transTm", sdf.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
				logger.error(e);
			}
			String txnSrc = m.get("txnSrc");
			m.put("txnSrcDesc", EnumUtil.translate(TxnSource.class, txnSrc, true));
		}
	};

	/***
	 * 转帐页面
	 *
	 * @return
	 */
	@RolesSessionCheck(roles = { MerUserRole.WithdrawUser,MerUserRole.SuperUser })
	@RequestMapping("/transferPage")
	public String transfer(Model model, HttpServletRequest req) {
        String lang = I18nMsgUtils.getLanguage(req);
		String mchntCd = commonBO.getMchntCd();
		String secretFlag = "true";
		if (!commonBO.validateSecret(mchntCd)) {
			secretFlag = "false";
		}
		model.addAttribute("secretFlag", secretFlag);
		model.addAttribute("loginType", commonBO.getLoginType());
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		
		//人民币账户
		MerAccountInfo mchntAcct_CNY = mchntAccountBO.getMchntAccount(mchntCd, "156");
		AssertUtil.objIsNull(mchntAcct_CNY, mappingI18n(this.getClass().getSimpleName(),"未找到商户账户", lang));
		AssertUtil.strNotEquals(mchntAcct_CNY.getState(), AccEnums.AccSt.ST_1.getCode(), mappingI18n(this.getClass().getSimpleName(),"商户账户状态无效", lang));
		
		if (mchntAcct_CNY != null) {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_CNY", StringUtil.formateAmt(String.valueOf(mchntAcct_CNY.getAvailableBalance()-mchntAcct_CNY.getObligatedBalance())));
			//转账页面，额外显示保留余额
			model.addAttribute("obligatedBalance_CNY", StringUtil.formateAmt(String.valueOf(mchntAcct_CNY.getObligatedBalance())));
			model.addAttribute("frozenBalance_CNY", StringUtil.formateAmt(String.valueOf(mchntAcct_CNY.getFrozenBalance())));
			model.addAttribute("frozenT1Balance_CNY", StringUtil.formateAmt(String.valueOf(mchntAcct_CNY.getFrozenT1Balance())));
			long totalBalance_CNY= sum(mchntAcct_CNY.getAvailableBalance(),mchntAcct_CNY.getFrozenBalance(),mchntAcct_CNY.getFrozenT1Balance());
			model.addAttribute("totalBalance_CNY", StringUtil.formateAmt(String.valueOf(totalBalance_CNY)));
		} else {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_CNY", "0.00");
			//转账页面，额外显示保留余额
			model.addAttribute("obligatedBalance_CNY", "0.00");
			model.addAttribute("frozenBalance_CNY", "0.00");
			model.addAttribute("frozenT1Balance_CNY", "0.00");
			model.addAttribute("totalBalance_CNY", "0.00");
		}	
		
		//越南盾账户
		MerAccountInfo mchntAcct_VND = mchntAccountBO.getMchntAccount(mchntCd, "704");
		AssertUtil.objIsNull(mchntAcct_VND, mappingI18n(this.getClass().getSimpleName(),"未找到商户账户", lang));
		AssertUtil.strNotEquals(mchntAcct_VND.getState(), AccEnums.AccSt.ST_1.getCode(), mappingI18n(this.getClass().getSimpleName(),"商户账户状态无效", lang));
		
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
		AssertUtil.objIsNull(mchntAcct_THB, mappingI18n(this.getClass().getSimpleName(),"未找到商户账户", lang));
		AssertUtil.strNotEquals(mchntAcct_THB.getState(), AccEnums.AccSt.ST_1.getCode(), mappingI18n(this.getClass().getSimpleName(),"商户账户状态无效", lang));
		
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
		
		model.addAttribute("bankNameLst", arrangeBankListByCookie(BankInfoCaChe.getBankCdLst(), req));
		List<RiskListItemMer> mList = getMchntCdWhiteList(mchntCd);
		int whiteListType = getWhiteListType(mList, true);
		model.addAttribute("whiteList", mList);// 白名单下拉选单
		model.addAttribute("whiteListType", whiteListType);
		model.addAttribute("mchntCd", mchntCd);
		model.addAttribute("rondom", String.valueOf((new Date()).getTime()));
		String randv = commonBO.genRandomVal();
		model.addAttribute("randv", randv);
		return "transfer";
	}

	protected List<RiskListItemMer> getMchntCdWhiteList(String mchntCd) {
		IRiskListItemMerService riskListItemMerService = this.getService(IRiskListItemMerService.class);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("mchntCd", mchntCd);
		qryParamMap.put("chnlId", "00");
		qryParamMap.put("groupId", "108");
		List<RiskListItemMer> list = riskListItemMerService.select(qryParamMap);
		return list;
	}

	/**
	 * 返回白名单模式: 0=无白名单，1=传统白名单
	 *
	 * @param list
	 * @param removeTheAll
	 * @return
	 */
	protected int getWhiteListType(List<RiskListItemMer> list, boolean removeTheAll) {
		if (list.size() > 0) {
			return 1;
		}
		return 0;
	}


	@Autowired
	private MchntBO mchntBO;

	/**
	 * 轉帳操作
	 *
	 * @param model
	 * @return
	 * @throws SystemException
	 */
	@RolesSessionCheck(roles = { MerUserRole.WithdrawUser,MerUserRole.SuperUser })
	@RequestMapping(value = "/submitTransfer.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult submitTransfer(HttpServletRequest request, HttpServletResponse response,
			Model model, String currencyCode, String cashAmt, String whiteItem, String gaCode, String randv, String secretHash) throws SystemException {

//		if (!isAllowedWithdraw())
//			AssertUtil.objIsNull(null, "转帐操作不允许!");

        String lang = I18nMsgUtils.getLanguage(request);
        
		AssertUtil.strIsBlank(cashAmt, "cashAmt is blank.");

		Map<String,String> eventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/submitTransfer.do" ,
        				MSG.merId, whiteItem,
        				MSG.userId, commonBO.getMchntUser().getUserId(),
        				MSG.txnAmt, cashAmt,
        				MSG.currencyCode, currencyCode,
        				INTER_MSG.reqIp, MerUtils.getRemoteIp(request)
        				);

		AjaxResult result = commonBO.buildAjaxResp(AjaxRespEnums.ERROR, mappingI18n(this.getClass().getSimpleName(),"转帐操作失败", lang));

		if (commonBO.isDuplicateTransfer(whiteItem, cashAmt, randv)) {
			String msg = String.format(mappingI18n(this.getClass().getSimpleName(),"短时间内重复操作！\n商户: %s,\n金额: %s", lang), whiteItem, cashAmt);
			error(msg);

         	RiskEvent.mer()
             	.who(merUser(whiteItem, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Transfer)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.Risk_Freq)
             	.message("商户： %s, 用户： %s, 短时间内重复操作！\n金额： %s, 币别： %s", whiteItem, commonBO.getMchntUser().getUserId(), cashAmt, currencyCode)
             	.target(cashAmt+ "|" + EnumLangUtil.translate(lang, "CurrType", currencyCode, false))
             	.params(eventParams) //夹带其他资讯
             	.submit();
			
			result = commonBO.buildAjaxResp(AjaxRespEnums.ERROR, mappingI18n(this.getClass().getSimpleName(),"系统忙碌，请稍后再试！", lang));
			return result;
		}
		;

		IMchntAccountService service = this.getService(IMchntAccountService.class);
		String mchntCd = commonBO.getMchntCd();
		AssertUtil.objIsNull(mchntCd, mappingI18n(this.getClass().getSimpleName(),"商户号为空", lang));
		
		String userId = (String) request.getSession().getAttribute("userId");
		
		MchntUserInfo muser = mchntBO.selectMchntUserInfo(mchntCd, userId);
		// 校验登录密码
		if (!PwdUtil.validatePwd(secretHash, randv, null, muser.getLoginPwd())) {

        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Transfer)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.Verify_Passwd)
            	.message("商户： %s; 用户： %s; 密码错误", mchntCd, userId)
            	.params(eventParams) //夹带其他资讯
            	.submit();
			
			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"密码错误", lang));
		}
		
		GoogleAuthenticator ga = new GoogleAuthenticator(mchntCd +"-" + userId, muser.getOtpSecret());
		if(!(ga.checkCode(gaCode))) {

        	RiskEvent.mer()
            	.who(merUser(mchntCd, userId))
            	.event(RISK.Event.Transfer)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.Verify_Otp)
            	.message("商户： %s; 用户： %s; 谷歌验证失败", mchntCd, userId)
            	.params(eventParams) //夹带其他资讯
            	.submit();
        	
			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"谷歌验证失败", lang));
		}
		
		
		MerAccountInfoKey key = new MerAccountInfoKey();
		key.setMchntCd(mchntCd);
		key.setCurrCd(currencyCode);
		MerAccountInfo acc = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(acc, mappingI18n(this.getClass().getSimpleName(),"未找到商户账户", lang));
		AssertUtil.strNotEquals(acc.getState(), AccEnums.AccSt.ST_1.getCode(), mappingI18n(this.getClass().getSimpleName(),"商户账户状态无效", lang));

		BigDecimal txnAmtBd = (new BigDecimal(cashAmt)).movePointRight(2);
		if (txnAmtBd.compareTo(BigDecimal.ZERO) <= 0) {

         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Transfer)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.InvalidInfo)
             	.target(cashAmt+ "|" + EnumLangUtil.translate(lang, "CurrType", currencyCode, false))
             	.message("商户： %s, 用户： %s, 币别： %s, 交易金额 %s 不正确" , mchntCd, commonBO.getMchntUser().getUserId(), currencyCode, cashAmt)
             	.params(eventParams) //夹带其他资讯
             	.submit();
         	
			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR, mappingI18n(this.getClass().getSimpleName(),"交易金额请输入正值", lang));
		}

		// 转换成分
		String transAt = StringUtil.trim(cashAmt);
//		BigDecimal at = new BigDecimal(transAt);
//		if (!StringUtil.isBlank(transAt)) {
//			// 转换成分
//			at = at.multiply(new BigDecimal(100));
//		}
//
//		Long amt = at.longValue();
		
		Amount aTransAt = Amount.create(currencyCode).regularUnit().amount(transAt).toDefaultUnit();
		Long amt  = aTransAt.getAmountValue().longValue();
		BigDecimal unit = aTransAt.getUnit();
		

		// 判断取现金额是否小于可用金额（保留余额加入计算）
		Long realAvailableBalance = acc.getAvailableBalance() - acc.getObligatedBalance();
		if (realAvailableBalance <= 0) {
			realAvailableBalance = 0L;
		}
		if (amt > realAvailableBalance) {

         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Transfer)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.InvalidInfo)
             	.message("商户： %s, 用户： %s, 输入的取款金额大于可代付额度" , mchntCd, commonBO.getMchntUser().getUserId())
             	.params(eventParams) //夹带其他资讯
             	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR, mappingI18n(this.getClass().getSimpleName(),"輸入的取款金額大於可取现金额", lang));
		}

		// 查询是否录入转帐白名单
		int checkRes = checkTransferWhiteList(whiteItem, mchntCd);
		if (checkRes == 0) {

         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Transfer)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.WhiteList_Transfer)
             	.target(whiteItem)
             	.message("商户： %s, 用户： %s, 此商户号未绑定「转帐白名单」" , mchntCd, commonBO.getMchntUser().getUserId())
             	.params(eventParams) //夹带其他资讯
             	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR, mappingI18n(this.getClass().getSimpleName(),"此商户号未绑定，请至「转帐白名单」绑定商户号", lang));
		}

		// 取得商户号字符串
		String toMchntCd = whiteItem.substring(whiteItem.indexOf("|") + 1);

		// 商戶帳戶餘額轉帳
		ChnlMerAccService cmas = ServiceProxy.getService(ChnlMerAccService.class);
		cmas.transfer("00", mchntCd, ACC_SUBTYPE.B0, toMchntCd, ACC_SUBTYPE.B0, 
				amt.toString(), currencyCode, unit, 
				userId,mappingI18n(this.getClass().getSimpleName(), "商户帐户余额转帐", lang));

		// 判断成功回传
		result = commonBO.buildAjaxResp(AjaxRespEnums.SUCC, String.format(mappingI18n(this.getClass().getSimpleName(),"转帐成功！", lang)));

     	RiskEvent.mer()
     	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
     	.event(RISK.Event.Transfer)
     	.result(RISK.Result.Ok)
     	.target(whiteItem)
     	.message("商户： %s, 用户： %s, 转账成功！" , mchntCd, commonBO.getMchntUser().getUserId())
     	.params(eventParams) //夹带其他资讯
     	.submit();

		return result;
	}

	/**
	 * @param accName
	 * @param accNo
	 * @param mchntCd
	 * @return 0=检查失败, 1=单一卡号match, 2=ALL
	 */
	protected int checkTransferWhiteList(String whiteItem, String mchntCd) {
		IRiskListItemMerService riskListItemMerService = this.getService(IRiskListItemMerService.class);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("item", whiteItem);
		qryParamMap.put("mchntCd", mchntCd);
		qryParamMap.put("chnlId", "00");
		qryParamMap.put("groupId", "108");
		List<RiskListItemMer> list = riskListItemMerService.select(qryParamMap);
		if (list.size() > 0) {
			return 1;
		}
		return 0;
	}

}
