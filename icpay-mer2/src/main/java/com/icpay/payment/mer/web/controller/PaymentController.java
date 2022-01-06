package com.icpay.payment.mer.web.controller;

import static com.icpay.payment.risk.MerUser.merUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.model.GenericResult;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.constants.Constant.TxnSource;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.constants.Names.MSG;
import com.icpay.payment.common.constants.RspCd;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.enums.AccEnums;
import com.icpay.payment.common.enums.CurrencyEnums;
import com.icpay.payment.common.enums.MerEnums.MerUserRole;
import com.icpay.payment.common.enums.TxnEnums.TxnType;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.exception.ChnlBizException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.Converter;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.MapHelper;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.common.utils.WebUtil;
import com.icpay.payment.db.dao.mybatis.model.CashPoolInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountInfoKey;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoSub;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoSubKey;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.MerBanks;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySub;
import com.icpay.payment.db.dao.mybatis.model.OffPayBank;
import com.icpay.payment.db.dao.mybatis.model.RiskListItemMer;
import com.icpay.payment.db.dao.mybatis.model.TransProof;
import com.icpay.payment.db.service.ICashPoolInfoService;
import com.icpay.payment.db.service.IChnlMchntAccountInfoService;
import com.icpay.payment.db.service.IChnlMchntInfoSubService;
import com.icpay.payment.db.service.IMchntAccountService;
import com.icpay.payment.db.service.IMerSettlePolicySubService;
import com.icpay.payment.db.service.IOffPayBankService;
import com.icpay.payment.db.service.IRiskListItemMerService;
import com.icpay.payment.db.service.ITransProofService;
import com.icpay.payment.mer.cache.BankInfoCaChe;
import com.icpay.payment.mer.cache.MerConfigCache;
import com.icpay.payment.mer.util.EnumLangUtil;
import com.icpay.payment.mer.util.I18nMsgUtils;
import com.icpay.payment.mer.util.MerUtils;
import com.icpay.payment.mer.web.interceptor.RolesSessionCheck;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;
import com.icpay.payment.service.IcpaySecService;
import com.icpay.payment.service.api.HttpClientResponse;
import com.icpay.payment.service.oss.OssUtils;
import com.icpay.payment.service.oss.SimpleProgressListener;



@Controller
@RequestMapping("/payment")
public class PaymentController extends BaseController {
	
	IMchntAccountService mchntAccSvc=null;
	
	protected IMchntAccountService getMchntAccSvc() {
		if (mchntAccSvc==null) {
			mchntAccSvc = this.getService(IMchntAccountService.class);
		}
		return mchntAccSvc;
	}
	
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			//Nothing
		}
	};
	
//	protected MerAccountInfo checkMchntAcc(String mchntCd) { //2021/4/28注解
//		AssertUtil.strIsBlank(mchntCd, mappingI18n(this.getClass().getSimpleName(),"商户号为空值"));
//		MerAccountInfo acc = getMchntAccSvc().selectByPrimaryKey(mchntCd);
//		AssertUtil.objIsNull(acc,mappingI18n(this.getClass().getSimpleName(), "未找到商户账户:")+mchntCd);
//		AssertUtil.strNotEquals(acc.getState(), AccEnums.AccSt.ST_1.getCode(), String.format(mappingI18n(this.getClass().getSimpleName(),"商户%s账户状态无效"), mchntCd));
//		return acc;
//	}

	
	/***
	 * 充值页面
	 * @return
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.PaymentUser,MerUserRole.SuperUser})
	@RequestMapping(value = "/commonPay", method = RequestMethod.GET)
	public String commonPay(Model model, HttpServletRequest req) {
		String mchntCd =commonBO.getMchntCode();
//		MerAccountInfo acc = checkMchntAcc(mchntCd); // 2021/4/28注解
		model.addAttribute("loginType", commonBO.getLoginType());
//		model.addAttribute("availableBalance", StringUtil.formateAmt(String.valueOf(acc.getAvailableBalance())));
//		model.addAttribute("frozenT1Balance", StringUtil.formateAmt(String.valueOf(acc.getFrozenT1Balance())));
//		model.addAttribute("frozenBalance", StringUtil.formateAmt(String.valueOf(acc.getFrozenBalance())));
		model.addAttribute("orderId", nextOrderId(PrefixForPayment));
		model.addAttribute("bankNameLst", arrangeBankListByCookie(BankInfoCaChe.getBankCdLst(), req));//BankInfoCaChe.getBankCdLst()
//		model.addAttribute("payTypes", this.getPayTypes(mchntCd));
		//model.addAttribute("merBanks", arrangeMerBanksByCookie(this.getMerBanks("0121",mchntCd), req));
		model.addAttribute("today", DateUtil.now8());
		
		List<RiskListItemMer> wList= getCardList(mchntCd, "601");
		int whiteListType = getCardListType(wList, true);
		model.addAttribute("cardList", wList);
		model.addAttribute("cardListType", whiteListType);
		
		List<RiskListItemMer> wList2= getCardList(mchntCd, "106");
		int whiteListType2 = getCardListType(wList2, true);
		model.addAttribute("cardList2", wList2);
		model.addAttribute("cardListType2", whiteListType2);
		
		List<RiskListItemMer> wList0191 = getCardList0191(mchntCd, "107");//107：电汇自充-卡号
		int whiteListType0191 = getCardListType(wList0191, true);
		model.addAttribute("cardList0191", wList0191);
		model.addAttribute("cardListType0191", whiteListType0191);
		
		return "commonPay";
	}
	
	/**
	 * 充值操作
	 * @param model
	 * @param intTransCd
	 * @param payType
	 * @param txnAmt
	 * @return
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.PaymentUser,MerUserRole.SuperUser})
	@RequestMapping(value = "/commonPaySubmit.do", method = RequestMethod.POST)
	public String commonPaySubmit(Model model, String currCd, String payType, String orderId, String txnAmt, String accNum, String accName, String bankNum, String actualTxnDate, MultipartFile uploadImage, HttpServletRequest request, HttpServletResponse response) {
		String result = null;
		
        Map<String,String> paymentEventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/commonPaySubmit.do" ,
        				MSG.merId, commonBO.getMchntCd(),
        				MSG.userId, (String) request.getSession().getAttribute("userId"),
        				MSG.currencyCode, currCd,
        				"payType", payType,
        				MSG.orderId, orderId,
        				MSG.accNum, accNum,
        				MSG.accName, accName,
        				MSG.bankNum, bankNum,
        				MSG.txnAmt, txnAmt,
        				INTER_MSG.reqIp, ""+MerUtils.getRemoteIp(request),
        				INTER_MSG.siteId, request.getSession().getAttribute("siteId").toString()
        				);
        
		try {
			result = this.doCommonPaySubmit(model, currCd, payType, orderId, txnAmt, accNum, accName, bankNum, actualTxnDate, uploadImage, request, response, paymentEventParams);
		} catch (ChnlBizException err) {
			error(String.format("[%s] %s", err.getErrorCode(),err.getErrorMsg()), err);
			return buildResult(err.getErrorCode(),err.getErrorMsg(), model, paymentEventParams, request);
		} catch (BizzException err) {
			error(String.format("[%s] %s", err.getErrorCode(),err.getErrorMsg()), err);
			return buildResult(err.getErrorCode(),err.getErrorMsg(), model, paymentEventParams, request);
		} catch (Exception err) {
			error(String.format("[%s] %s", RspCd._91,err.getMessage()), err);
			return buildResult(RspCd._91,err.getMessage(), model, paymentEventParams, request);
		}
		return result;
	}
	
	public String doCommonPaySubmit(Model model, String currCd, String payType, String orderId, String txnAmt, String accNum, String accName, String bankNum, String actualTxnDate, MultipartFile uploadImage, HttpServletRequest request, HttpServletResponse response, Map<String,String> paymentEventParams) throws Exception {
        String lang = I18nMsgUtils.getLanguage(request);
		String mchntCd =commonBO.getMchntCode();
		addFreqBankNums(bankNum, request, response);
		String userId = (String) request.getSession().getAttribute("userId");
		String siteId = request.getSession().getAttribute("siteId").toString();
//		MerAccountInfo acc = checkMchntAcc(mchntCd); //没使用到 2021/4/28注解
		Date now = new Date();
		String reqIp = WebUtil.getRemoteIp(request);
		String orderDate = Converter.dateToString(now); 
		String orderTime = Converter.timeToString(now);
		payType = StringUtils.right(payType, 2);
		String txnType="01";
		String txnSubType=payType;
		if ("91".equals(txnSubType) && uploadImage.getSize() != 0) {
			if (!checkFileSize(uploadImage.getSize(), 1, "M")) {//图片大小限制1MB
				throw new ChnlBizException(RspCd.Z_0005,mappingI18n(this.getClass().getSimpleName(),"图片大小限制1MB", lang));
			}
		}
		if (Utils.isEmpty(orderId))
			orderId = this.nextOrderId(PrefixForPayment);
		
		Map<String, String> payParams = Utils.newMap(
				MSG.txnType,txnType,
				MSG.txnSubType,txnSubType,
				MSG.txnAmt, toIntAmt(txnAmt),
				MSG.orderDate, orderDate,
				MSG.orderTime, orderTime,
				MSG.orderId, orderId,
				MSG.merId, mchntCd,
				MSG.macKeyId, mchntCd,
				MSG.clientIp, reqIp,
				//INTER_MSG.reqIp, reqIp,
				MSG.productTitle,"sp",
				MSG.productDesc,"sp",
				MSG.currencyCode, currCd,
				MSG.cardType,"DT01",
				MSG.pageReturnUrl, getPageRetUrl(request),
				MSG.notifyUrl, Constant.NOOP_URL,
				MSG.sceneBizType,"WAP",
				MSG.secpVer, "icp3-1.0",
				MSG.secpMode, "perm",
				MSG.appType, "B3002",
				INTER_MSG.userId, userId,
				INTER_MSG.txnSrc, TxnSource.MER.getCode(),
				INTER_MSG.siteId, siteId
			);
		if (!Utils.isEmpty(actualTxnDate) && Utils.isInSet(payType, "91"))
			payParams.put(MSG.actualTxnDate, actualTxnDate);
		if (!Utils.isEmpty(accNum) && Utils.isInSet(payType, "91", "21", "22", "92", "93"))
			payParams.put(MSG.accNum, accNum);
		if (!Utils.isEmpty(accName) && Utils.isInSet(payType, "21", "91", "92", "93"))
			payParams.put(MSG.accName, accName);
		if (!Utils.isEmpty(bankNum) && Utils.isInSet(payType, "21", "92", "93"))
			payParams.put(MSG.bankNum, bankNum);
		
		genMac(payParams);
				
		String reqUrl = getPayGatewayUrl(siteId);//payment.pageRet.url. 加上 siteId
		HttpClientResponse httpResp = this.httpPostByService(reqUrl, payParams);
		if (! this.checkHttpRespCodePrefix(httpResp.getCode())) {
			
			RiskEvent.mer()
			.who(merUser(mchntCd, userId))
			.event(RISK.Event.Payment)
			.result(RISK.Result.Error)
			.reason(RISK.Reason.Exception_HttpRespCode)
			.target(txnAmt+ "|" + EnumLangUtil.translate(lang, "CurrType", currCd, false))
			.message("支付响应错误，http响应码：%s; 商户： %s, 订单号： %s, 订单金额： %s, 币别： %s, 请求IP: %s; 交易类型: %s", httpResp.getCode(), mchntCd, orderId, txnAmt, EnumLangUtil.translate(lang, "CurrType", currCd, false), reqIp, paymentEventParams.get("payType"))
			.params(paymentEventParams)
			.params("orderId", orderId)
			.submit();
			
			throw new ChnlBizException(RspCd.Z_0012,mappingI18n(this.getClass().getSimpleName(),"支付响应错误", lang));
		}
		String body=httpResp.getBody();
		//上传凭证
		if ("91".equals(txnSubType) && uploadImage.getSize() != 0) {
			uploadImage(uploadImage, body);
		}
		if (isJsonStr(body)) {
			Map resMap = toMap(body);
			this.debug("[请求响应] %s", resMap);
			return buildResult(resMap, model, paymentEventParams, request);
		}
		else {
			
			RiskEvent.mer()
			.who(merUser(mchntCd, userId))
			.event(RISK.Event.Payment)
			.result(RISK.Result.None)
			.target(txnAmt+ "|" + EnumLangUtil.translate(lang, "CurrType", currCd, false))
			.message("充值请求已发起，处理中; 商户： %s, 订单号： %s, 订单金额： %s, 币别： %s, 请求IP: %s; 交易类型: %s", mchntCd, orderId, txnAmt, EnumLangUtil.translate(lang, "CurrType", currCd, false), reqIp, paymentEventParams.get("payType"))
			.params(paymentEventParams)
			.params("orderId", orderId)
			.submit();
			
			responseHtml(response, "200", body);
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String buildResult(Map resMap, Model model, Map<String,String> paymentEventParams, HttpServletRequest request) {
		
        String lang = I18nMsgUtils.getLanguage(request);
        
		@SuppressWarnings("unchecked")
		MapHelper mh = new MapHelper(resMap);
		
		String currCd = mh.getAsString(MSG.currencyCode);
		if (!Utils.isEmpty(currCd)) {
			resMap.put(MSG.currencyCode, EnumLangUtil.translate(lang, "CurrType", currCd, false));
		}
		
		String txnAmt = mh.getAsString(MSG.txnAmt);
		
		if (!Utils.isEmpty(txnAmt)) {
			txnAmt = this.toFloatAmt(txnAmt);
			mh.putAsStringNotEmpty(MSG.txnAmt, txnAmt);
		}
		
		//mh.putAsStringNotNull(MSG.codeImgUrl, "http://www.google.com/?search=test");//test only
		
		String imgUrl = mh.getAsString(MSG.codeImgUrl);
		if (Utils.isEmpty(imgUrl))
			imgUrl= mh.getAsString(MSG.codePageUrl);
		
		String qrContent="";
		if (!Utils.isEmpty(imgUrl))
			try {
				qrContent = URLEncoder.encode(imgUrl, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				qrContent="";
			}
		else
			imgUrl="";
		
		String orgiMsg= strVal(resMap.get(MSG.respMsg));
		if (!Utils.isEmpty(model)) {
			boolean isOk =this.isOkCode(mh.getAsString(MSG.respCode));
			if (isOk) {
				
				RiskEvent.mer()
				.who(merUser(paymentEventParams.get("merId"), paymentEventParams.get("userId")))
				.event(RISK.Event.Payment)
				.result(RISK.Result.Ok)
				.target(txnAmt+ "|" + EnumLangUtil.translate(lang, "CurrType", paymentEventParams.get("currencyCode"), false))
				.message("充值请求成功; 商户： %s, 订单号： %s, 订单金额： %s, 币别： %s, 请求IP: %s; 交易类型: %s", paymentEventParams.get("merId"), mh.getAsString(MSG.orderId), txnAmt, EnumLangUtil.translate(lang, "CurrType", paymentEventParams.get("currencyCode"), false), paymentEventParams.get("reqIp"), paymentEventParams.get("payType"))
				.params(paymentEventParams)
				.params("orderId", mh.getAsString(MSG.orderId))
				.submit();
				
				resMap.put(MSG.respMsg, mappingI18n(this.getClass().getSimpleName(),"处理中，稍后请到查询页面确认交易结果。", lang));
			}
			else {
				
				RiskEvent.mer()
				.who(merUser(paymentEventParams.get("merId"), paymentEventParams.get("userId")))
				.event(RISK.Event.Payment)
				.result(RISK.Result.Error)
				.reason(RISK.Reason.Exception)
				.target(txnAmt+ "|" + EnumLangUtil.translate(lang, "CurrType", paymentEventParams.get("currencyCode"), false))
				.message("充值请求异常，原因：%s; 商户： %s, 订单号： %s, 订单金额： %s, 币别： %s, 请求IP: %s; 交易类型: %s", orgiMsg, paymentEventParams.get("merId"), mh.getAsString(MSG.orderId), txnAmt, EnumLangUtil.translate(lang, "CurrType", paymentEventParams.get("currencyCode"), false), paymentEventParams.get("reqIp"), paymentEventParams.get("payType"))
				.params(paymentEventParams)
				.params("orderId", mh.getAsString(MSG.orderId))
				.submit();
				
				resMap.put(MSG.respMsg, String.format(mappingI18n(this.getClass().getSimpleName(),"请求异常，请到查询页面确认交易结果。<br/>%s", lang), orgiMsg));
			}
			model.addAttribute("isOk", isOk);
			model.addAttribute("panelClass", isOk ? "panel-info" : "panel-danger");
			model.addAttribute("qrContent", qrContent);
			model.addAttribute("qrContentUrl", imgUrl);
			model.addAttribute("hasQrcode", isOk && (!Utils.isEmpty(imgUrl)));
			model.addAttribute("payResult", resMap);
			//以交易类型区分结果页的流程导向(H5的交易需能自动跳转)
			String payType = mh.getAsString(MSG.txnType)+mh.getAsString(MSG.txnSubType);
			model.addAttribute("payType",payType);
			this.debug("[请求响应中的交易类型] %s", payType);
		}
		return "commonPayResult";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String buildResult(String code, String msg, Model model, Map<String,String> paymentEventParams, HttpServletRequest request) {
		Map resMap = new HashMap();
		@SuppressWarnings("unchecked")
		MapHelper mh = new MapHelper(resMap);
		mh.putAsStringNotEmpty(MSG.respCode, code);
		mh.putAsStringNotEmpty(MSG.respMsg, msg);
		return buildResult(resMap, model, paymentEventParams, request);
	}

	private boolean isJsonStr(String body) {
		String sbuf = ""+StringUtil.trim(body);
		return sbuf.startsWith("{") && sbuf.endsWith("}");
	}
	
	protected String getPageRetUrl(HttpServletRequest request) {
		//String pageRetUrl =MerConfigCache.getConfig("payment.pageRet.url");
		//com.icpay.payment.common.utils.HttpUtil.resolveUrl(String, String)
		return getBasePath(request);
	}
	
	protected String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
	    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		return basePath;
	}
	
	/**
	 * 产生MAC
	 * @param context
	 */
	protected void genMac(Map<String, String> msg) {
		msg.remove(MSG.mac);
		msg.put(MSG.timeStamp, Utils.getTimeStamp());
		IcpaySecService secService = ServiceProxy.getService(IcpaySecService.class);
		try{
			String mac = secService.genMac(msg);
			msg.put(MSG.mac, mac);
		}catch (Exception e) {
			this.throwError(RspCd.Z_0032, "产生MAC失败"+e.getMessage());
		}
	}
	
	static final String respTmpl=
			"响应代码： %s <br/>\r\n" + 
			"交易讯息： %s <br/>\r\n" + 
			"商户编号： %s <br/>\r\n" + 
			"交易金额： %s <br/>\r\n" + 
			"订单日期： %s <br/> \r\n" + 
			"订单编号： %s <br/>\r\n" + 
			"";
	
	public boolean isOkCode(String code) {
		return Utils.isInSet(code, RspCd._00, RspCd._0000000, "0000", "1101", "00", "000", "00000");
	}
	
	AjaxResult buildAjaxResult(String code, String msg) {
		AjaxResult res = new AjaxResult();

		res.setRespCode(code);
		res.setRespMsg(msg);

		return res;
		
	}
	AjaxResult buildAjaxResult(String code, Map<String,String> respData) {
		AjaxResult res = new AjaxResult();
		if (respData==null)
			respData = new HashMap<>();
		String msg = String.format(respTmpl, 
				respData.get(MSG.respCode), 
				respData.get(MSG.respMsg), 
				respData.get(MSG.merId), 
				respData.get(this.toFloatAmt(MSG.txnAmt)), 
				respData.get(MSG.orderDate), 
				respData.get(MSG.orderId)
				);
		res.setRespCode(code);
		res.setRespMsg(msg);
		res.getRespData().putAll(respData);
		return res;
	}
	
	//IMerSettlePolicySubService
	
//	/***
//	 * 查詢支付方式
//	 * @return
//	 */
//	@RequestMapping(value = "/paymentTypes", method = RequestMethod.GET)
//	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.PaymentUser,MerUserRole.SuperUser})
//	public  @ResponseBody AjaxResult paymentTypes(Model model, HttpServletRequest request) {
//        String lang = I18nMsgUtils.getLanguage(request);
//		String mchntCd =commonBO.getMchntCode();
//		List<TxnType> payTypes = getPayTypes(mchntCd);
//		if (payTypes.size()>0)
//			return commonBO.buildSuccResp("payTypes", payTypes);
//		else
//			return commonBO.buildErrorResp(mappingI18n(this.getClass().getSimpleName(),"無支持的支付方式", lang), "payTypes", payTypes);
//	}
	
	/***
	 * 查詢支付方式
	 * @return
	 */
	@RequestMapping(value = "/merBanks.do", method = RequestMethod.GET)
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.PaymentUser,MerUserRole.SuperUser})
	public  @ResponseBody AjaxResult merBanksList(HttpServletRequest req, Model model, String payType, String currCd) {
		String mchntCd =commonBO.getMchntCode();
		String intTransCd=payType;
		List<MerBanks> res = arrangeMerBanksByCookie(getMerBanks(intTransCd, mchntCd, currCd),req);
		if (res==null)
			res = new ArrayList<MerBanks>();
		return commonBO.buildSuccResp("merBanks", res);
	}


//	/**
//	 * @param mchntCd
//	 * @return
//	 */
//	protected List<TxnType> getPayTypes(String mchntCd) {
//		IMerSettlePolicySubService svc = this.getService(IMerSettlePolicySubService.class);
//		List<MerSettlePolicySub>  msList = svc.select(mchntCd);
//		List<TxnType> payTypes = null;
//		payTypes = new ArrayList<>();
//		for(MerSettlePolicySub ms: msList) {
////			String ptCode = StringUtil.right(("XXXX"+ms.getIntTransCd()), 2);
//			String ptCode = ms.getIntTransCd();
//			TxnType payType = EnumUtil.parseEnumByCode(TxnType.class, ptCode, null);
//			if (payType!=null){
//				if(!payType.getCode().startsWith("52")) {
//					payTypes.add(payType);
//				}
//			}
//				
//		}
//		return payTypes;
//	}
	
	/***
	 * 银行卡白名单页面
	 * @return
	 */
	@RequestMapping("/paymentCardList")
	public String paymentCardList(Model model) {
		String mchntCd =commonBO.getMchntCd();
		String secretFlag ="true";
		if(!commonBO.validateSecret(mchntCd)) {
			secretFlag ="false";
		}
		IRiskListItemMerService riskListItemMerService = this.getService(IRiskListItemMerService.class);
		Map<String, String> qryParams = new HashMap<String, String>();
		qryParams.put("mchntCd", commonBO.getMchntCd());
		qryParams.put("groupId", "601");
		qryParams.put("chnlId", "00");
		List <RiskListItemMer> riskList = riskListItemMerService.select(qryParams);
		model.addAttribute("riskList", riskList);
		model.addAttribute("secretFlag", secretFlag);
		model.addAttribute("loginType", commonBO.getLoginType());
		return "paymentCardList";
	}

	/**
	 * 添加银行卡白名单
	 * @return
	 */
	@RequestMapping("/addCardList")
	public @ResponseBody AjaxResult addCardList(String cardList, HttpServletRequest request) {
		String mchntCd = commonBO.getMchntCd();
		
        Map<String,String> eventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/addCardList" ,
        				MSG.merId, mchntCd,
        				MSG.userId, commonBO.getMchntUser().getUserId(),
        				INTER_MSG.reqIp, ""+MerUtils.getRemoteIp(request)
        				);
        
		String [] bankArray = cardList.replaceAll("[，,\\s\\n]", ",").split(",");
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
				merItem.setGroupId(601);//商户常用自充卡白名单
				merItem.setChnlId("00");//前端小商户号，00
				merItem.setMchntCd(mchntCd);
				IRiskListItemMerService riskListItemMerService = this.getService(IRiskListItemMerService.class);
				riskListItemMerService.add(merItem);//记录数据
				
				this.info("商户： %s, 用户： %s, 新增商户常用自充卡成功： '%s|%s' ", mchntCd, commonBO.getMchntUser().getUserId(), accName, accNo);
				
	           	RiskEvent.mer()
	               	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
	               	.event(RISK.Event.Payment_CardList_Add)
	               	.result(RISK.Result.Ok)
	               	.message("商户： %s, 用户： %s, 新增商户常用自充卡成功： '%s|%s' " , mchntCd, commonBO.getMchntUser().getUserId(), accName, accNo)
	               	.target(accName+"|"+accNo)
	               	.params(eventParams) //夹带其他资讯
	               	.params(MSG.accName, accName)
	               	.params(MSG.accNum, accNo)
	               	.submit();
	           	
			}else {
//				warn(String.format("姓名 '%s' 或 银行卡号 '%s' 格式错误!", accName, accNo));
				this.error("商户： %s, 用户： %s, 新增商户常用自充卡失败： 姓名 '%s' 或 银行卡号 '%s' 格式错误!", mchntCd, commonBO.getMchntUser().getUserId(), accName, accNo);
				
	           	RiskEvent.mer()
	               	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
	               	.event(RISK.Event.Payment_CardList_Add)
	               	.result(RISK.Result.Failed)
	               	.reason(RISK.Reason.InvalidInfo)
	               	.message("商户： %s, 用户： %s, 新增商户常用自充卡失败： 姓名 '%s' 或 银行卡号 '%s' 格式错误!" , mchntCd, commonBO.getMchntUser().getUserId(), accName, accNo)
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
	
	/**
	 * 删除银行卡
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/deleteCard",method=RequestMethod.GET)
	public @ResponseBody AjaxResult deleteBankCard(String id, HttpServletRequest request) {
		
        Map<String,String> eventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/deleteCard" ,
        				MSG.merId, commonBO.getMchntCd(),
        				MSG.userId, commonBO.getMchntUser().getUserId(),
        				INTER_MSG.reqIp, ""+MerUtils.getRemoteIp(request)
        				);
        
		IRiskListItemMerService riskListItemMerService = this.getService(IRiskListItemMerService.class);
		RiskListItemMer merItem = riskListItemMerService.selectByPrimaryKey(Long.valueOf(id));
		riskListItemMerService.delete(Long.valueOf(id));
		
		this.info("商户： %s, 用户： %s, 删除銀行卡(自充卡)成功：%s", merItem.getMchntCd(), commonBO.getMchntUser().getUserId(), merItem.getItem());
		
       	RiskEvent.mer()
           	.who(merUser(commonBO.getMchntCd(), commonBO.getMchntUser().getUserId()))
           	.event(RISK.Event.Payment_CardList_Del)
           	.result(RISK.Result.Ok)
           	.message("商户： %s, 用户： %s, 删除銀行卡(自充卡)成功：%s" , merItem.getMchntCd(), commonBO.getMchntUser().getUserId(), merItem.getItem())
           	.target(merItem.getItem())
           	.params(eventParams) //夹带其他资讯
           	.submit();
       	
		return commonBO.buildSuccResp();
	}	
	
	protected boolean checkBankCardInfo(String accName, String accNo) {
		if ("ALL".equals(accName)) return true;
		if ("ALL".equals(accNo)) return true;
		return (MerUtils.checkBankCard(accNo) && (!Utils.isEmpty(accName)));
		//return (!Utils.isEmpty(accName)) && (!Utils.isEmpty(accNo));
	}
	
	protected List<RiskListItemMer> getCardList(String mchntCd, String groupId){
		IRiskListItemMerService riskListItemMerService = this.getService(IRiskListItemMerService.class);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		//qryParamMap.put("item", accWhiteItem(accName,accNo));
		qryParamMap.put("mchntCd", mchntCd);
		qryParamMap.put("chnlId", "00");
		qryParamMap.put("groupId", groupId);
		List<RiskListItemMer> list = riskListItemMerService.select(qryParamMap);
		return list;
	}
	
	protected List<RiskListItemMer> getCardList0191(String mchntCd, String groupId){
		IRiskListItemMerService riskListItemMerService = this.getService(IRiskListItemMerService.class);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("mchntCd", mchntCd);
		qryParamMap.put("chnlId", "00");
		qryParamMap.put("groupId", groupId);
		List<RiskListItemMer> list = riskListItemMerService.select(qryParamMap);
		for (int i = 0; i < list.size(); i++) {
			RiskListItemMer riskListItemMer = list.get(i);
			String comment = riskListItemMer.getComment();
			if ("0".equals(comment)) {//备注0：不显示卡号
				list.remove(i--);
			}
		}
		return list;
	}
	
	/**
	 * 返回白名单模式:
	 * 0=无白名单，1=传统白名单，2=ALL+白名单
	 * @param list
	 * @param removeTheAll
	 * @return
	 */
	protected int getCardListType(List<RiskListItemMer> list, boolean removeTheAll) {
		int i=0;
		if (list.size()>0) {
			for(RiskListItemMer item: list.toArray(new RiskListItemMer[0])) {
				if ("ALL".equals(item.getItem())) {
					if (removeTheAll)
						list.remove(i);
					return 2;
				}
				i++;
			}
			return 1;
		}
		return 0;
	}	
	
	/***
	 * 取得户名、银行卡号、银行名称、开户行
	 * @return
	 */
	@RequestMapping(value = "/offPayBank.do", method = RequestMethod.GET)
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.PaymentUser,MerUserRole.SuperUser})
	public  @ResponseBody AjaxResult getOffPayBank(HttpServletRequest req, Model model) {
		String mchntCd =commonBO.getMchntCode();
		IOffPayBankService offPayBankService = this.getService(IOffPayBankService.class);
		OffPayBank list = offPayBankService.select(mchntCd);
		return commonBO.buildSuccResp("offPayBank", list);
	}
	
	public void uploadImage(MultipartFile uploadImage, String body) throws IOException {
		if (isJsonStr(body)) {
			Map resMap = toMap(body);
			if ("0000".equals(resMap.get("respCode"))) {
				String txnId = resMap.get("txnId").toString();
				if(StringUtil.isNotBlank(txnId)) {
					String fileName = uploadImage.getOriginalFilename();
					int deputy = fileName.lastIndexOf(".");
					String contentType = fileName.substring(deputy + 1);
					byte[] img = loadImage(uploadImage);
					SimpleProgressListener listener = new SimpleProgressListener();
					String newFileName = "0191-proof-"+txnId+"."+contentType;
					GenericResult r = OssUtils.putImage(newFileName, img, listener);
					String uniqueName = OssUtils.getUniqueName(newFileName);
					int number = uniqueName.indexOf(".");
					String objectName = uniqueName.substring(0, number);
					ITransProofService service = this.getService(ITransProofService.class);
					TransProof transProof = new TransProof();
					transProof.setPlatformId("00000000");
					transProof.setTxnId(txnId);//交易流水号
					transProof.setProofType("0191");
					transProof.setFileDesc(newFileName);
					transProof.setContentType(contentType);//文件格式：如：jpg，png等
					transProof.setContentBase64(objectName);//文件内容base64
					transProof.setFileId(uniqueName);//文件ID
					service.add(transProof);
				}
			}
		}
	}
	
	private static byte[] loadImage(MultipartFile fname) throws IOException {
		InputStream fileIn = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			fileIn = fname.getInputStream();
			int c=-1;
			byte[] buf=new byte[1024];
			while (true) {
				c=fileIn.read(buf);
				if (c>0) {
					os.write(buf, 0, c);
				}
				else {
					break;
				}
			}
			return os.toByteArray();
		} finally {
			if (fileIn != null)
				fileIn.close();
		}
	}
	
	/**
	 * 判断文件大小
	 * @param len文件长度
	 * @param size限制大小
	 * @param unit限制单位（B,K,M,G）
	 * @return
	 */
    public static boolean checkFileSize(Long len, int size, String unit) {
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }
    
    @RequestMapping(value = "/getPayTypeByCurrCd.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult getPayTypeByCurrCd(String currCd) {
    	String mchntCd =commonBO.getMchntCode();	
		
    	IMerSettlePolicySubService svc = this.getService(IMerSettlePolicySubService.class);
		List<MerSettlePolicySub>  msList = svc.select(mchntCd, currCd);
		List<Map<String, String>> payTypes = new ArrayList<>();
		for(MerSettlePolicySub ms: msList) {
			String ptCode = ms.getIntTransCd();
			TxnType payType = EnumUtil.parseEnumByCode(TxnType.class, ptCode, null);
			if (payType!=null){
				if(!payType.getCode().startsWith("52")) {
					Map<String, String> paytype = new HashMap();
					paytype.put("code", payType.getCode());
					paytype.put("desc", payType.getDesc());
					payTypes.add(paytype);
				}
			}	
		}
		
		AjaxResult resp= commonBO.buildSuccResp("payTypes", payTypes);
		return resp;
	}

}
