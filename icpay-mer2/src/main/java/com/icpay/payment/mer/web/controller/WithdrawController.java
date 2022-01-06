package com.icpay.payment.mer.web.controller;

import static com.icpay.payment.risk.MerUser.merUser;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONObject;
import com.icpay.gauth.GoogleAuthenticator;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.constants.RspCd;
import com.icpay.payment.common.constants.Constant.TxnSource;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.constants.Names.MSG;
import com.icpay.payment.common.data.svc.RiskUtilService;
import com.icpay.payment.common.data.svc.impl.RiskUtilServiceImpl;
import com.icpay.payment.common.constants.TxnConstants;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.AccEnums;
import com.icpay.payment.common.enums.AjaxRespEnums;
import com.icpay.payment.common.enums.MerEnums.MerUserRole;
import com.icpay.payment.common.enums.MerEnums.MerUserState;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.enums.TxnEnums.TxnType;
import com.icpay.payment.common.exception.SystemException;
import com.icpay.payment.common.utils.Amount;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.Converter;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.PwdUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.common.utils.WebUtil;
import com.icpay.payment.db.dao.mybatis.model.BankNums;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySub;
import com.icpay.payment.db.dao.mybatis.model.RiskListItemMer;
import com.icpay.payment.db.dao.mybatis.model.TransLog;
import com.icpay.payment.db.dao.mybatis.model.TxnLogView;
import com.icpay.payment.db.dao.mybatis.model.TxnRoutingInfoWdPool;
import com.icpay.payment.db.dao.mybatis.model.TxnRoutingKey;
import com.icpay.payment.db.service.IMerSettlePolicySubService;
import com.icpay.payment.db.service.IRiskListItemMerService;
import com.icpay.payment.db.service.ITxnRoutingInfoWdPoolService;
import com.icpay.payment.db.service.ITxnRoutingService;
import com.icpay.payment.gateway.msgonl.MsgRespHelper;
import com.icpay.payment.mer.bo.MchntAccountBO;
import com.icpay.payment.mer.bo.MchntBO;
import com.icpay.payment.mer.bo.TransBO;
import com.icpay.payment.mer.cache.BankInfoCaChe;
import com.icpay.payment.mer.cache.MerConfigCache;
import com.icpay.payment.mer.cache.MerParamCache;
import com.icpay.payment.mer.cache.PageConfCache;
import com.icpay.payment.mer.constants.MerConstants;
import com.icpay.payment.mer.session.SessionKeys;
import com.icpay.payment.mer.util.EnumLangUtil;
import com.icpay.payment.mer.util.GoogleAuthenticatorUtils;
import com.icpay.payment.mer.util.I18nMsgUtils;
import com.icpay.payment.mer.util.MerUtils;
import com.icpay.payment.mer.web.interceptor.RolesSessionCheck;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;
import com.icpay.payment.service.api.HttpClientResponse;
import com.icpay.payment.service.client.InternalGatewayClient;

@Controller
@RequestMapping("/withdraw")
public class WithdrawController extends TransLogBasedController {

	@Autowired
	private MchntAccountBO mchntAccountBO;
	
	private static final Logger logger = Logger.getLogger(WithdrawController.class);
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			BigDecimal transAmt = null;
			if(StringUtil.isNotBlank(m.get("transAmt"))) {
				transAmt = new BigDecimal(m.get("transAmt"));
				m.put("transAmtDesc", transAmt.movePointLeft(2).toString());
			}else {
				m.put("transAmtDesc", "0.00");
			}
			BigDecimal transAt = null;
			if(StringUtil.isNotBlank(m.get("transAt"))) {
				transAt = new BigDecimal(m.get("transAt"));
				m.put("transAtDesc", transAt.movePointLeft(2).toString());
			}else {
				m.put("transAtDesc", "0.00");
			}
//			BigDecimal settleAmt = new BigDecimal(m.get("settleAmt"));
//			m.put("settleAmtDesc", settleAmt.movePointLeft(2).toString());

			BigDecimal settleAmt = null;
			if(StringUtil.isNotBlank(m.get("settleAmt"))) {
				settleAmt = new BigDecimal(m.get("settleAmt"));
				 m.put("settleAmtDesc", settleAmt.movePointLeft(2).toString());
			}else {
				m.put("settleAmtDesc","0.00");
			}

			BigDecimal transFee = null;
			if(StringUtil.isNotBlank(m.get("transFee"))) {
				 transFee = new BigDecimal(m.get("transFee"));
				 m.put("transFeeDesc", transFee.movePointLeft(2).toString());
			}else {
				m.put("transFeeDesc","0.00");
			}

			String accNo = m.get("accNo");
			if (StringUtil.isBlank(accNo)) {
				m.put("accNoDesc", "");
			} else {
				//m.put("accNoDesc", BizUtils.strMask(accNo, "*", 4, 4, 6));
				m.put("accNoDesc", accNo);
			}

			String accName = m.get("accName");
			if (StringUtil.isBlank(accName)) {
				m.put("accNameDesc", "");
			} else {
				//m.put("accNameDesc", BizUtils.strMask(accName, "*", 1, 2, 0));
				m.put("accNameDesc", accName);
			}

			//交易类型
			String intTransCd = m.get("intTransCd");
			m.put("intTransCdDesc", EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, false));

			//支付方式
//			String payType = m.get("payType");
//			if (!StringUtil.isBlank(payType)) {
//				m.put("payTypeDesc", EnumUtil.translate(TxnEnums.PaymentType.class, payType, true));
//			}
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
			//日期原始格式
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

	@Autowired
	private TransBO transBO;
	/**
	 * 取现查询页面
	 * @return
	 */
	@RequestMapping("/withdrawMng")
	public String withdrawalMng(Model model) {
//		model.addAttribute("today", DateUtil.now8());
		model.addAttribute("defaultStart", DateUtil.now8() + " 000000");
		model.addAttribute("defaultEnd", DateUtil.now8() + " 235959");
		String mchntCd =commonBO.getMchntCd();
		String secretFlag ="true";
		if(!commonBO.validateSecret(mchntCd)) {
			secretFlag ="false";
		}
		model.addAttribute("secretFlag", secretFlag);
		model.addAttribute("loginType", commonBO.getLoginType());
		model.addAttribute("payTypes", this.getPayTypesWithdraw(mchntCd));//交易类型
		return "withdrawMng";
	}

	/***
	 * 查询取现信息
	 * @return
	 */
	@RequestMapping("/qryWithdraw")
	public @ResponseBody AjaxResult qryWithdraw(int pageNum, int pageSize, String startDt, String endDt,
			String startTm, String endTm, String orderId, String transAmt ,String transState, 
			String accName, String accNo, String userId, String txnSrc, String transType, String currCd, HttpServletRequest req) {
		Map<String, String> qryParams = new HashMap<String, String>();
		qryParams.put("mchntCd", commonBO.getMchntCd());
		qryParams.put("orderId", orderId);
		qryParams.put("transAt", transAmt);
//		qryParams.put("startDate", startDt);
//		qryParams.put("endDate", endDt);
//		qryParams.put("startTime", startTm);//起始时间
//		qryParams.put("endTime", endTm);//结束时间
		String mon = StringUtil.left(startDt, 8);
		qryParams.put("startDate", StringUtil.left(startDt, 8));
		qryParams.put("endDate", StringUtil.left(endDt, 8));
		qryParams.put("startTime", StringUtil.right(startDt, 6));//起始时间
		qryParams.put("endTime", StringUtil.right(endDt, 6)); // 结束时间
		qryParams.put("txnState", transState);//交易状态
		qryParams.put("accName", accName);
		qryParams.put("accNo", accNo);
		qryParams.put("userId", userId);
		qryParams.put("transType", Utils.isEmpty(transType) ? "52":transType);//交易类型
		qryParams.put("txnSrc", txnSrc);//交易来源
		qryParams.put("currCd", currCd);//币别

		// 获取取现记录
		//info("取现交易查询开始:" + BeanUtil.desc(qryParams, null, null));
		info("取现交易查询开始:" + qryParams);
//		Pager<WithdrawLog> p = withdrawLogBO.qryTrans(pageNum, pageSize, mon.substring(4), qryParams);
		Pager<TxnLogView> p = transBO.qryTrans(pageNum, pageSize, mon, qryParams);
		Pager<Map<String, String>> transferPager = commonBO.transferPager(p, MerConstants.PAGE_CONF_WITHDRAWMNG, ENTITY_TRANSFER,req);
		transferPager = commonBO.transferResultListByLang(transferPager, req); //再将某些栏位的查询结果，做多国语的转换
		return commonBO.buildSuccResp(MerConstants.PAGER_RESULT, transferPager);
	}
	/**
	 * 查询取现交易明细
	 */
	@RequestMapping("/withdrawFlowDetail")
	public String withdrawFlowDetail(Model model, String transSeqId, String mon ,HttpServletRequest req) {
        String lang = I18nMsgUtils.getLanguage(req);
//		AssertUtil.notMonStr(mon.substring(4));
		AssertUtil.strIsBlank(transSeqId, "transSeqId is blank.");
		AssertUtil.strIsBlank(mon, "mon is blank.");
//		mon = DateUtils.formatDateStr(mon,DateUtils.DATE_FORMAT_10, DateUtils.DATE_FORMAT_8);
//		WithdrawLog log = withdrawLogBO.getTransFlow(orderId, mon.substring(4));
		TxnLogView log = transBO.getTxnLogViewFlow(transSeqId, mon);
		AssertUtil.objIsNull(log,mappingI18n(this.getClass().getSimpleName(), "未找到取现明细:", lang) + transSeqId);
		//detailConf 取现明细就是在这里定义的
		model.addAttribute(MerConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConfLst(MerConstants.PAGE_CONF_WITHDRAWMNG,I18nMsgUtils.getLanguage(req)));
		model.addAttribute(MerConstants.ENTITY_RESULT, 
				commonBO.transferEntity(log, MerConstants.PAGE_CONF_WITHDRAWMNG, ENTITY_TRANSFER, I18nMsgUtils.getLanguage(req)));
		return "withdrawFlowDetail";
	}
	
	/***
	 * 取现页面
	 * @return
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.SuperUser})
	@RequestMapping("/withdrawPage")
	public String withdrawal(Model model, HttpServletRequest req) {
        String lang = I18nMsgUtils.getLanguage(req);
		String mchntCd =commonBO.getMchntCd();
		String secretFlag ="true";
		if(!commonBO.validateSecret(mchntCd)) {
			secretFlag ="false";
		}
		model.addAttribute("secretFlag", secretFlag);
		model.addAttribute("loginType", commonBO.getLoginType());
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		
		//人民币账户
		MerAccountInfo mchntAcct_CNY = mchntAccountBO.getMchntAccount(mchntCd, "156");
		AssertUtil.objIsNull(mchntAcct_CNY, mappingI18n(this.getClass().getSimpleName(),"未找到商户账户", lang) + "(CNY)");
		AssertUtil.strNotEquals(mchntAcct_CNY.getState(), AccEnums.AccSt.ST_1.getCode(), mappingI18n(this.getClass().getSimpleName(),"商户账户状态无效", lang));
		
		if (mchntAcct_CNY != null) {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_CNY", StringUtil.formateAmt(String.valueOf(mchntAcct_CNY.getAvailableBalance()-mchntAcct_CNY.getObligatedBalance())));
			//保留余额需显示在代付页面
			model.addAttribute("obligatedBalance_CNY", StringUtil.formateAmt(String.valueOf(mchntAcct_CNY.getObligatedBalance())));
			model.addAttribute("frozenBalance_CNY", StringUtil.formateAmt(String.valueOf(mchntAcct_CNY.getFrozenBalance())));
			model.addAttribute("frozenT1Balance_CNY", StringUtil.formateAmt(String.valueOf(mchntAcct_CNY.getFrozenT1Balance())));
			long totalBalance_CNY= sum(mchntAcct_CNY.getAvailableBalance(),mchntAcct_CNY.getFrozenBalance(),mchntAcct_CNY.getFrozenT1Balance());
			model.addAttribute("totalBalance_CNY", StringUtil.formateAmt(String.valueOf(totalBalance_CNY)));
		} else {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_CNY", "0.00");
			//保留余额需显示在代付页面
			model.addAttribute("obligatedBalance_CNY", "0.00");
			model.addAttribute("frozenBalance_CNY", "0.00");
			model.addAttribute("frozenT1Balance_CNY", "0.00");
			model.addAttribute("totalBalance_CNY", "0.00");
		}	
		
		//越南盾账户
		MerAccountInfo mchntAcct_VND = mchntAccountBO.getMchntAccount(mchntCd, "704");
		AssertUtil.objIsNull(mchntAcct_VND, mappingI18n(this.getClass().getSimpleName(),"未找到商户账户", lang) + "(VND)");
		AssertUtil.strNotEquals(mchntAcct_VND.getState(), AccEnums.AccSt.ST_1.getCode(), mappingI18n(this.getClass().getSimpleName(),"商户账户状态无效", lang));
		
		if (mchntAcct_VND != null) {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_VND", StringUtil.formateAmt(String.valueOf(mchntAcct_VND.getAvailableBalance()-mchntAcct_VND.getObligatedBalance())));
			//保留余额需显示在代付页面
			model.addAttribute("obligatedBalance_VND", StringUtil.formateAmt(String.valueOf(mchntAcct_VND.getObligatedBalance())));
			model.addAttribute("frozenBalance_VND", StringUtil.formateAmt(String.valueOf(mchntAcct_VND.getFrozenBalance())));
			model.addAttribute("frozenT1Balance_VND", StringUtil.formateAmt(String.valueOf(mchntAcct_VND.getFrozenT1Balance())));
			long totalBalance_VND= sum(mchntAcct_VND.getAvailableBalance(),mchntAcct_VND.getFrozenBalance(),mchntAcct_VND.getFrozenT1Balance());
			model.addAttribute("totalBalance_VND", StringUtil.formateAmt(String.valueOf(totalBalance_VND)));
		} else {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_VND", "0.00");
			//保留余额需显示在代付页面
			model.addAttribute("obligatedBalance_VND", "0.00");
			model.addAttribute("frozenBalance_VND", "0.00");
			model.addAttribute("frozenT1Balance_VND", "0.00");
			model.addAttribute("totalBalance_VND", "0.00");
		}
		
		//泰铢账户
		MerAccountInfo mchntAcct_THB = mchntAccountBO.getMchntAccount(mchntCd, "764");
		AssertUtil.objIsNull(mchntAcct_THB, mappingI18n(this.getClass().getSimpleName(),"未找到商户账户", lang) + "(THB)");
		AssertUtil.strNotEquals(mchntAcct_THB.getState(), AccEnums.AccSt.ST_1.getCode(), mappingI18n(this.getClass().getSimpleName(),"商户账户状态无效", lang));
		
		if (mchntAcct_THB != null) {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_THB", StringUtil.formateAmt(String.valueOf(mchntAcct_THB.getAvailableBalance()-mchntAcct_THB.getObligatedBalance())));
			//保留余额需显示在代付页面
			model.addAttribute("obligatedBalance_THB", StringUtil.formateAmt(String.valueOf(mchntAcct_THB.getObligatedBalance())));
			model.addAttribute("frozenBalance_THB", StringUtil.formateAmt(String.valueOf(mchntAcct_THB.getFrozenBalance())));
			model.addAttribute("frozenT1Balance_THB", StringUtil.formateAmt(String.valueOf(mchntAcct_THB.getFrozenT1Balance())));
			long totalBalance_THB= sum(mchntAcct_THB.getAvailableBalance(),mchntAcct_THB.getFrozenBalance(),mchntAcct_THB.getFrozenT1Balance());
			model.addAttribute("totalBalance_THB", StringUtil.formateAmt(String.valueOf(totalBalance_THB)));
		} else {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_THB", "0.00");
			//保留余额需显示在代付页面
			model.addAttribute("obligatedBalance_THB", "0.00");
			model.addAttribute("frozenBalance_THB", "0.00");
			model.addAttribute("frozenT1Balance_THB", "0.00");
			model.addAttribute("totalBalance_THB", "0.00");
		}

		//找出符合 资金池/代付路由器 的渠道
		List<String> uniqueChnlsId = new ArrayList<String>();
		uniqueChnlsId = getChnl(mchntCd, "156"); //预设进入页面的币别是人民币
		model.addAttribute("bankNameLst", arrangeBankListByCookie(BankInfoCaChe.getBankCdLstForWithdraw(uniqueChnlsId, "156"), req));
		
		List<RiskListItemMer> wList= getWithdrawWhiteList(mchntCd);
		int whiteListType = getWhiteListType(wList, true);
		model.addAttribute("whiteList", wList);
		model.addAttribute("whiteListType", whiteListType);
		model.addAttribute("mchntCd", mchntCd);
		model.addAttribute("role", SessionKeys.ROLE.getCode());
		model.addAttribute("rondom", String.valueOf((new Date()).getTime()));

		String randv=commonBO.genRandomVal();
		model.addAttribute("randv", randv);
		model.addAttribute("payTypes", transBO.getPayTypesWithdraw(mchntCd, "156"));//交易类型
		return "withdraw";
	}


	protected List<RiskListItemMer> getWithdrawWhiteList(String mchntCd){
		IRiskListItemMerService riskListItemMerService = this.getService(IRiskListItemMerService.class);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		//qryParamMap.put("item", accWhiteItem(accName,accNo));
		qryParamMap.put("mchntCd", mchntCd);
		qryParamMap.put("chnlId", "00");
		qryParamMap.put("groupId", "102");
		List<RiskListItemMer> list = riskListItemMerService.select(qryParamMap);
		return list;
	}

	/**
	 * 返回白名单模式:
	 * 0=无白名单，1=传统白名单，2=ALL+白名单
	 * @param list
	 * @param removeTheAll
	 * @return
	 */
	protected int getWhiteListType(List<RiskListItemMer> list, boolean removeTheAll) {
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

	protected boolean checkAdminGAuth(String gaCode,MchntUserInfo m) {
		if (Utils.isEmpty(gaCode)) return false;
		if (Utils.isEmpty(commonBO)) return false;
//		MchntInfo merInfo = this.commonBO.getLoginMer();
//		if (Utils.isEmpty(merInfo)) return false;
//		if (Utils.isEmpty(merInfo.getOptSecretAdmin())) return false;
//		GoogleAuthenticator ga = new GoogleAuthenticator(merInfo.getMchntCd(), merInfo.getOptSecretAdmin());
		if(Utils.isEmpty(m.getOtpSecret())) return false;
		GoogleAuthenticator ga = new GoogleAuthenticator(m.getMchntCd()+"-"+m.getUserId(), m.getOtpSecret());
		return ga.checkCode(gaCode);
	}

	/**
	 * GA输入完成后，取现操作
	 * @param model
	 * @return
	 * @throws SystemException
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.SuperUser})
	@RequestMapping(value = "/submitWithdraw.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult submitWithdraw(HttpServletRequest request, HttpServletResponse response, Model model,
					String cashAmt ,String accNo,String accName,String bankCd, String gaCode, String randv, String payType, String currencyCode) throws SystemException {
        String lang = I18nMsgUtils.getLanguage(request);
        String siteId = request.getSession().getAttribute("siteId").toString();
        
		AssertUtil.strIsBlank(cashAmt, "cashAmt is blank.");

        Map<String,String> eventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/submitWithdraw.do" ,
        				MSG.merId, commonBO.getMchntCd(),
        				MSG.userId, (String) request.getSession().getAttribute("userId"),
        				MSG.accNum, accNo,
        				MSG.accName, accName,
        				MSG.bankNum, bankCd,
        				MSG.txnAmt, cashAmt,
        				INTER_MSG.reqIp, ""+MerUtils.getRemoteIp(request),
        				INTER_MSG.siteId, siteId
        				);

		// 校验登录ip是否合法
		String mchntCd = commonBO.getMchntCd();
		String requestIp = ""+MerUtils.getRemoteIp(request);
         if (!validateClientIp(mchntCd, requestIp, request)) {

        	 warn("商户： %s, 用户： %s, 登入IP： %s, 登入IP不合法 " , mchntCd, commonBO.getMchntUser().getUserId(), requestIp);

           	RiskEvent.mer()
               	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
               	.event(RISK.Event.Withdraw)
               	.result(RISK.Result.Failed)
               	.reason(RISK.Reason.WhiteList_IP)
               	.target(accName+"|"+accNo)
               	.message("商户： %s, 用户： %s, 登入IP： %s, 登入IP不合法 " , mchntCd, commonBO.getMchntUser().getUserId(), requestIp)
               	.params(eventParams) //夹带其他资讯
               	.submit();

            return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED, mappingI18n(this.getClass().getSimpleName(),"登入IP不合法", lang));
         }

//		if (!isAllowedWithdraw())
//			AssertUtil.objIsNull(null, "代付操作不允许!");

		String userId = (String) request.getSession().getAttribute("userId");
		MchntUserInfo muser = mchntBO.selectMchntUserInfo(mchntCd, userId);
		String role = muser.getUserRole();
		// 校验代付ip与角色是否合法，代付跟超级管理员皆可提交
		if (!(validateWithdrawClientIp(mchntCd, requestIp, request) &&
				(MerUserRole.WithdrawUser.getCode().equals(role)||MerUserRole.SuperUser.getCode().equals(role)))) {

			warn("商户： %s, 用户： %s, 登入IP： %s, 商户平台代付不在IP白名单内!" , mchntCd, commonBO.getMchntUser().getUserId(), requestIp);

         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Withdraw)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.WhiteList_IP)
             	.target(accName+"|"+accNo)
             	.message("商户： %s, 用户： %s, 登入IP： %s, 商户平台代付不在IP白名单内!" , mchntCd, commonBO.getMchntUser().getUserId(), requestIp)
             	.params(eventParams) //夹带其他资讯
             	.submit();

			AssertUtil.objIsNull(null,mappingI18n(this.getClass().getSimpleName(), "商户平台代付不在IP白名单内!", lang));

		}
//		String nowSession = commonBO.getWithdrawHash(accName,accNo, cashAmt,bankCd);
//		String sessionWithdraw = commonBO.getSessionData(SessionKeys.WITHDRAW_MD5.getCode());
//		if((!nowSession.equals(sessionWithdraw))) { // 两次提交的不一致，代付失败
//			warn("代付Session异常: accName=%s, accNo=%s, cashAmt=%s, bankCd=%s", accName, accNo, cashAmt, bankCd);
//			commonBO.removeSessionData(SessionKeys.WITHDRAW_MD5.getCode());
//			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,"取现操作失败");  // TODO 修改返回描述
//		}

		boolean cr = commonBO.compareSessionAttach(SessionKeys.WITHDRAW_MD5.getCode(), accName, accNo, cashAmt, bankCd, true);
		if (!cr) {
			warn("代付Session异常: accName=%s, accNo=%s, cashAmt=%s, bankCd=%s", accName, accNo, cashAmt, bankCd);
			commonBO.removeSessionData(SessionKeys.WITHDRAW_MD5.getCode());
			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,"取现操作失败");  // TODO 修改返回描述
		}
		commonBO.removeSessionData(SessionKeys.WITHDRAW_MD5.getCode());


//		boolean gaCodeFlag = commonBO.getSessionData(SessionKeys.GACODE_FLAG.getCode());
		
		//查询是否录入银行卡白名单
		
		if (!checkRiskForTempBlock(accName, accNo, mchntCd, eventParams, request)) {
			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"此卡存在风险暂时被拒，请稍后再试", lang));
		}
		
		int checkRes = checkWhiteList(accName, accNo, mchntCd);
		if(checkRes == 0) {

			warn("商户： %s, 用户： %s, 此卡 %s 未绑定，请至「银行卡白名单」绑定银行卡" , mchntCd, commonBO.getMchntUser().getUserId(), accNo);

         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Withdraw)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.WhiteList_BankCard)
             	.message("商户： %s, 用户： %s, 此卡 %s 未绑定，请至「银行卡白名单」绑定银行卡" , mchntCd, commonBO.getMchntUser().getUserId(), accNo)
             	.target(accName+"|"+accNo)
             	.params(eventParams) //夹带其他资讯
             	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"此卡未绑定，请至「银行卡白名单」绑定银行卡", lang));
		}
		else {
			if (Utils.isEmpty(gaCode)) {
				warn("输入的谷歌验证码为空值！请求IP： %s", requestIp);

	         	RiskEvent.mer()
	             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
	             	.event(RISK.Event.Withdraw)
	             	.result(RISK.Result.Failed)
	             	.reason(RISK.Reason.Verify_Otp)
	             	.target(accName+"|"+accNo)
	             	.message("商户： %s, 用户： %s, 请求IP： %s, 输入的谷歌验证码为空值！" , mchntCd, commonBO.getMchntUser().getUserId(), requestIp)
	             	.params(eventParams) //夹带其他资讯
	             	.submit();

				return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"请输入谷歌验证码", lang));
			}

			if (!checkAdminGAuth(gaCode,muser)) {
				warn("谷歌验证码验证失败！输入的谷歌验证码为： %s，请求IP： %s", gaCode, requestIp);

	         	RiskEvent.mer()
	             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
	             	.event(RISK.Event.Withdraw)
	             	.result(RISK.Result.Failed)
	             	.reason(RISK.Reason.Verify_Otp)
	             	.target(accName+"|"+accNo)
	             	.message("商户： %s, 用户： %s, 请求IP： %s, 谷歌验证码验证失败！输入的谷歌验证码为： %s" , mchntCd, commonBO.getMchntUser().getUserId(), requestIp, gaCode)
	             	.params(eventParams) //夹带其他资讯
	             	.submit();

	         	//GA验证码输入错误，记录错误次数
	         	if (GoogleAuthenticatorUtils.countGaLoginFail(muser)) {
	        		return commonBO.buildAjaxResp(AjaxRespEnums.ERROR, mappingI18n(this.getClass().getSimpleName(),"谷歌验证失败", lang));
	        	} else {
	        		//放進session
	        		muser.setUserState(MerUserState.Locked.getCode());
	        		commonBO.setSessionData(SessionKeys.MCHNT_USER_INFO.getCode(), muser);
	        		String msg = String.format("谷歌验证码连续输入錯誤 %s 次，帐号已被锁定", MerParamCache.GA_Login_Failed_Count);
	        		return commonBO.buildAjaxResp(AjaxRespEnums.ERROR, mappingI18n(this.getClass().getSimpleName(), msg, lang));
	        	}
			}

			info("谷歌验证码验证成功！输入的谷歌验证码为：%s，请求IP：%s", gaCode, requestIp);
		}

		addFreqBankNums(bankCd, request, response);

		if(!MerUtils.checkBankCard(accNo)) {//判断是否为正确的银行卡

			warn("商户： %s, 用户： %s, %s 卡号不正确" , mchntCd, commonBO.getMchntUser().getUserId(), accNo);

         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Withdraw)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.InvalidInfo)
             	.target(accName+"|"+accNo)
             	.message("商户： %s, 用户： %s, %s 卡号不正确" , mchntCd, commonBO.getMchntUser().getUserId(), accNo)
             	.params(eventParams) //夹带其他资讯
             	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"请输入正确的银行卡号", lang));
		}

		return  withdraw(mchntCd , accNo,  accName,  cashAmt, requestIp,  userId, bankCd, payType, currencyCode, eventParams, lang, siteId);
	}


	/**
	 * 检查代付信息，回传实际POST的位址。
	 * 触发时机： 按下[取现]按钮(尚未GA验证)
	 * @param request
	 * @param response
	 * @param model
	 * @param cashAmt
	 * @param accNo
	 * @param accName
	 * @param bankCd
	 * @param validateCode
	 * @param randv
	 * @param payType
	 * @return 验证结果有三类: 1.验证成功而且不要求GA验证
	 * @throws SystemException
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.SuperUser})
	@RequestMapping(value = "/checkWithdraw.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult checkWithdraw(HttpServletRequest request, HttpServletResponse response, Model model,
			String cashAmt ,String accNo,String accName,String bankCd,String validateCode, String randv, String payType, String currencyCode) throws SystemException {

        String lang = I18nMsgUtils.getLanguage(request);
        String siteId = request.getSession().getAttribute("siteId").toString();
        
		Boolean checkResult = false;

		AssertUtil.strIsBlank(validateCode, "validateCode is empty");
		AssertUtil.strIsBlank(cashAmt, "cashAmt is blank.");
		String requestIp = ""+MerUtils.getRemoteIp(request);
		info("[代付请求] 卡号： %s, 持卡人： %s, 订单金额： %s, 币别： %s, 请求IP: %s; validateCode=%s, randv=%s", accNo, accName, cashAmt, EnumLangUtil.translate(lang, "CurrType", currencyCode, false), requestIp, validateCode, randv);


        Map<String,String> eventParams = //登入资讯(Event夹带用)
        		Utils.newMap(
        				INTER_MSG.sessionId, this.getSessionId(request),
        				INTER_MSG.requestUri , "/checkWithdraw.do" ,
        				MSG.merId, commonBO.getMchntCd(),
        				MSG.userId, commonBO.getMchntUser().getUserId(),
        				MSG.accNum, accNo,
        				MSG.accName, accName,
        				MSG.bankNum, bankCd,
        				MSG.txnAmt, cashAmt,
        				MSG.currencyCode, currencyCode,
        				INTER_MSG.reqIp, requestIp,
        				INTER_MSG.siteId, siteId
        				);

		// 校验验证码
		String sessionValidCode = commonBO.getSessionData(SessionKeys.VALIDATION_CODE.getCode());
		try {
			if (StringUtil.isBlank(sessionValidCode)) {
				return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED, mappingI18n(this.getClass().getSimpleName(),"图形验证码失效", lang));
				} else {
				if (!validateCode.equalsIgnoreCase(sessionValidCode)) {
					sleep(1100, 2500);
					info("图形验证码输入错误，%s" , validateCode);

		        	RiskEvent.mer()
		            	.who(merUser(commonBO.getMchntCd(), commonBO.getMchntUser().getUserId()))
		            	.event(RISK.Event.Withdraw)
		            	.result(RISK.Result.Failed)
		            	.reason(RISK.Reason.Verify_Captcha)
		            	.target(accName+"|"+accNo)
		            	.message("商户： %s, 用户： %s, 图形验证码输入错误", commonBO.getMchntCd(), commonBO.getMchntUser().getUserId())
		            	.params(eventParams) //夹带其他资讯
		            	.submit();

					return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED,mappingI18n(this.getClass().getSimpleName(), "图形验证码输入错误，请重新输入", lang));
				}
			}
		}
		finally {
			commonBO.removeSessionData(SessionKeys.VALIDATION_CODE.getCode());
		}

		String lastValidateCode = commonBO.getSessionData(SessionKeys.LAST_VALIDATION_CODE.getCode());
		if (!Utils.isEmpty(lastValidateCode) && lastValidateCode.equalsIgnoreCase(validateCode)) {
			info("商户： %s, 用户： %s, 连续两次验证码一样！上一次验证码: %s,当前验证码: %s" , commonBO.getMchntCd(), commonBO.getMchntUser().getUserId(), lastValidateCode, validateCode);
			String msg = String.format("商户： %s, 用户： %s, 连续两次验证码一样！\n上一次验证码: %s,\n当前验证码: %s", commonBO.getMchntCd(), commonBO.getMchntUser().getUserId(), lastValidateCode, validateCode);
			error(msg);

        	RiskEvent.mer()
            	.who(merUser(commonBO.getMchntCd(), commonBO.getMchntUser().getUserId()))
            	.event(RISK.Event.Withdraw)
            	.result(RISK.Result.Failed)
            	.reason(RISK.Reason.Duplicate_Captcha)
            	.target(accName+"|"+accNo)
            	.message("商户： %s, 用户： %s, 连续两次验证码一样！\n上一次验证码: %s,\n当前验证码: %s", commonBO.getMchntCd(), commonBO.getMchntUser().getUserId(), lastValidateCode, validateCode)
            	.params(eventParams) //夹带其他资讯
            	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"系统忙碌，请稍后再试！", lang));
		}else {
			commonBO.setSessionData(SessionKeys.LAST_VALIDATION_CODE.getCode(), validateCode);
		}

//		if (!isAllowedWithdraw())
//			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,"商户平台代付不在IP白名单内!");

		// 校验登录ip是否合法
		String mchntCd = commonBO.getMchntCd();
	    if (!validateClientIp(mchntCd, requestIp, request)) {
	    	 warn("商户： %s, 用户： %s, 登入IP： %s, 登入IP不合法" , mchntCd, commonBO.getMchntUser().getUserId(), requestIp);

	      	RiskEvent.mer()
	          	.who(merUser(commonBO.getMchntCd(), commonBO.getMchntUser().getUserId()))
	          	.event(RISK.Event.Withdraw)
	          	.result(RISK.Result.Failed)
	          	.reason(RISK.Reason.WhiteList_IP)
	          	.target(accName+"|"+accNo)
	          	.message("商户： %s, 用户： %s, 登入IP： %s, 登入IP不合法 " , mchntCd, commonBO.getMchntUser().getUserId(), requestIp)
	          	.params(eventParams) //夹带其他资讯
	          	.submit();

            return commonBO.buildAjaxResp(AjaxRespEnums.LOGIN_FAILED, mappingI18n(this.getClass().getSimpleName(),"登入IP不合法", lang));
	    }

		MchntUserInfo muser = commonBO.getMchntUser();
		String role = muser.getUserRole();
		if (!(validateWithdrawClientIp(mchntCd, requestIp, request) &&
				(MerUserRole.WithdrawUser.getCode().equals(role)||MerUserRole.SuperUser.getCode().equals(role)))) {
			warn("商户： %s, 用户： %s, 登入IP： %s, 商户平台代付不在IP白名单内!" , mchntCd, commonBO.getMchntUser().getUserId(), requestIp);

         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Withdraw)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.WhiteList_IP)
             	.target(accName+"|"+accNo)
             	.message("商户： %s, 用户： %s, 登入IP： %s, 商户平台代付不在IP白名单内!" , mchntCd, commonBO.getMchntUser().getUserId(), requestIp)
             	.params(eventParams) //夹带其他资讯
             	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"商户平台代付不在IP白名单内!", lang));
	}

		if (commonBO.isDuplicateWithdraw(accName, accNo, cashAmt, randv)) {
			warn("商户： %s, 用户： %s, 短时间内重复操作！卡号: %s, 持卡人: %s,金额: %s" , mchntCd, commonBO.getMchntUser().getUserId(), accNo, accName, cashAmt);
			//TODO Alert
			String msg = String.format("商户： %s, 用户： %s, 短时间内重复操作！\n卡号: %s,\n持卡人: %s,\n金额: %s", mchntCd, commonBO.getMchntUser().getUserId(), accNo, accName, cashAmt);
			error(msg);

         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Withdraw)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.Risk_Freq)
             	.target(accName+"|"+accNo+"|"+cashAmt+ "|" + EnumLangUtil.translate(lang, "CurrType", currencyCode, false))
             	.message("商户： %s, 用户： %s, 短时间内重复操作！\n卡号: %s,\n持卡人: %s,\n金额: %s, 币别: %s", mchntCd, commonBO.getMchntUser().getUserId(), accNo, accName, cashAmt, EnumLangUtil.translate(lang, "CurrType", currencyCode, false))
             	.params(eventParams) //夹带其他资讯
             	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"系统忙碌，请稍后再试！", lang));
		};

		addFreqBankNums(bankCd, request, response);

		BigDecimal txnAmtBd = (new BigDecimal(cashAmt)).movePointRight(2);
		if (txnAmtBd.compareTo(BigDecimal.ZERO) <= 0) {
			warn("商户： %s, 用户： %s, 交易金额 %s 不正确" , mchntCd, commonBO.getMchntUser().getUserId(), cashAmt);

         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Withdraw)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.InvalidInfo)
             	.target(accName+"|"+accNo+"|"+cashAmt+ "|" + EnumLangUtil.translate(lang, "CurrType", currencyCode, false))
             	.message("商户： %s, 用户： %s, 币别：%s, 交易金额 %s 不正确" , mchntCd, commonBO.getMchntUser().getUserId(), EnumLangUtil.translate(lang, "CurrType", currencyCode, false), cashAmt)
             	.params(eventParams) //夹带其他资讯
             	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"交易金额请输入正值", lang));
		}

		MerAccountInfo acc = mchntAccountBO.getMchntAccount(mchntCd, currencyCode);
		//提交前检核，提交金额是否大于可代付金额。
		String availableBalance = String.valueOf(acc.getAvailableBalance()-acc.getObligatedBalance());
		if (txnAmtBd.compareTo(new BigDecimal(availableBalance)) > 0) {
			warn("商户： %s, 用户： %s, 交易金额不可大于可代付馀额" , mchntCd, commonBO.getMchntUser().getUserId());

         	//TODO Publish event
         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Withdraw)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.InvalidInfo)
             	.target(accName+"|"+accNo+"|"+cashAmt+ "|" + EnumLangUtil.translate(lang, "CurrType", currencyCode, false))
             	.message("商户： %s, 用户： %s, 交易金额不可大于可代付馀额" , mchntCd, commonBO.getMchntUser().getUserId())
             	.params(eventParams) //夹带其他资讯
             	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,"交易金额不可大于可代付馀额");
		}

		if(!MerUtils.checkBankCard(accNo)) {//判断是否为正确的银行卡
			warn("商户： %s, 用户： %s, %s 卡号不正确" , mchntCd, commonBO.getMchntUser().getUserId(), accNo);

         	//TODO Publish event
         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Withdraw)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.InvalidInfo)
             	.target(accName+"|"+accNo)
             	.message("商户： %s, 用户： %s, %s 卡号不正确" , mchntCd, commonBO.getMchntUser().getUserId(), accNo)
             	.params(eventParams) //夹带其他资讯
             	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"请输入正确的银行卡号", lang));
		}

		if(accName.indexOf(" ")!=-1||accName.indexOf("　")!=-1 || accName.indexOf("\t")!=-1) {
			warn("商户： %s, 用户： %s, %s 取现户名不能包含空白" , mchntCd, commonBO.getMchntUser().getUserId(), accName);

         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Withdraw)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.InvalidInfo)
             	.target(accName+"|"+accNo)
             	.message("商户： %s, 用户： %s, %s 取现户名不能包含空白" , mchntCd, commonBO.getMchntUser().getUserId(), accName)
             	.params(eventParams) //夹带其他资讯
             	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,"取现户名不能包含空白");
		}

		String userId = (String) request.getSession().getAttribute("userId");

		//查询是否录入银行卡白名单
		
		if (!checkRiskForTempBlock(accName, accNo, mchntCd, eventParams, request)) {
			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"此卡存在风险暂时被拒，请稍后再试", lang));
		}
		
		int checkRes = checkWhiteList(accName, accNo, mchntCd); //0=检查失败, 1=单一卡号match, 2=ALL
		if(checkRes == 0) {
			warn("商户： %s, 用户： %s, 此卡 %s 未绑定，请至「银行卡白名单」绑定银行卡" , mchntCd, commonBO.getMchntUser().getUserId(), accNo);

         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Withdraw)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.WhiteList_BankCard)
             	.target(accName+"|"+accNo)
             	.message("商户： %s, 用户： %s, 此卡 %s 未绑定，请至「银行卡白名单」绑定银行卡" , mchntCd, commonBO.getMchntUser().getUserId(), accNo)
             	.params(eventParams) //夹带其他资讯
             	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"此卡未绑定，请至「银行卡白名单」绑定银行卡", lang));
		}else if (checkRes==2) {
			Map<String,String> res = new HashMap<String,String>();
			res.put("bankCd", bankCd);
			res.put("accName", accName);
			res.put("accNo", accNo);
			res.put("cashAmt", cashAmt);

			checkResult = true;

			//保存session
//			String withdrawSession = commonBO.getWithdrawHash(accName,accNo, cashAmt,bankCd);
//			commonBO.setSessionData(SessionKeys.WITHDRAW_MD5.getCode(), withdrawSession);
////			commonBO.setSessionData(SessionKeys.GACODE_FLAG.getCode(), true);
			commonBO.rememberSessionAttach(SessionKeys.WITHDRAW_MD5.getCode(), true, accName,accNo, cashAmt,bankCd, checkResult);


			//表示需要GA验证码
			//return commonBO.buildSuccResp("res",res);
			return commonBO.buildAjaxResp(AjaxRespEnums.REQUEST_OK).addResultData("res",res);
		}else {
			//到此不需验证GA，开始真正代付
			AjaxResult r = withdraw(mchntCd , accNo,  accName,  cashAmt, requestIp,  userId, bankCd, payType, currencyCode, eventParams, lang, siteId);
			return r;
		}

	}


	private AjaxResult withdraw(String mchntCd ,String accNo, String accName, String cashAmt, String requestIp, String userId,String bankCd, String payType, String currencyCode, Map<String,String> eventParams, String lang, String siteId) throws SystemException {
		String orderId=this.nextOrderId(PrefixForWithdraw);
		info("[代付请求] 卡号： %s, 持卡人： %s, 订单号： %s, 订单金额： %s, 币别： %s, 联行号： %s, 请求IP: %s; 操作用户: %s, 交易类型: %s", accNo, accName, orderId, cashAmt, EnumLangUtil.translate(lang, "CurrType", currencyCode, false), bankCd, requestIp, userId, payType);

		AssertUtil.objIsNull(mchntCd, mappingI18n(this.getClass().getSimpleName(),"商户号为空", lang));
		
		MerAccountInfo acc = mchntAccountBO.getMchntAccount(mchntCd, currencyCode);
		AssertUtil.objIsNull(acc, mappingI18n(this.getClass().getSimpleName(),"未找到商户账户", lang));
		AssertUtil.strNotEquals(acc.getState(), AccEnums.AccSt.ST_1.getCode(), mappingI18n(this.getClass().getSimpleName(),"商户账户状态无效", lang));
		
		MchntUserInfo m = mchntBO.selectMchntUserInfo(mchntCd, userId);
		AssertUtil.strNotEquals(m.getUserState(), AccEnums.AccSt.ST_1.getCode(), mappingI18n(this.getClass().getSimpleName(),"用户状态无效", lang));
		
		BigDecimal txnAmtBd = (new BigDecimal(cashAmt)).movePointRight(2);
		if (txnAmtBd.compareTo(BigDecimal.ZERO) <= 0) {
         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Withdraw)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.InvalidInfo)
             	.target(accName+"|"+accNo+"|"+cashAmt+ "|" + EnumLangUtil.translate(lang, "CurrType", currencyCode, false))
             	.message("商户： %s, 用户： %s, 币别： %s, 交易金额 %s 不正确" , mchntCd, commonBO.getMchntUser().getUserId(), EnumLangUtil.translate(lang, "CurrType", currencyCode, false), cashAmt)
             	.params(eventParams) //夹带其他资讯
             	.submit();
			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"交易金额请输入正值", lang));
		}
		
		if(!MerUtils.checkBankCard(accNo)) {//判断是否为正确的银行卡

         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Withdraw)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.InvalidInfo)
             	.target(accName+"|"+accNo)
             	.message("商户： %s, 用户： %s, %s 卡号不正确" , mchntCd, commonBO.getMchntUser().getUserId(), accNo)
             	.params(eventParams) //夹带其他资讯
             	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"请输入正确的银行卡号", lang));
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

         	//TODO Publish event
         	RiskEvent.mer()
             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
             	.event(RISK.Event.Withdraw)
             	.result(RISK.Result.Failed)
             	.reason(RISK.Reason.InvalidInfo)
             	.target(accName+"|"+accNo+"|"+cashAmt+ "|" + EnumLangUtil.translate(lang, "CurrType", currencyCode, false))
             	.message("商户： %s, 用户： %s, 输入的取款金额大于可代付额度" , mchntCd, commonBO.getMchntUser().getUserId())
             	.params(eventParams) //夹带其他资讯
             	.submit();

			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"输入的取款金额大于可代付额度", lang));
		}

		Date now = new Date();
		Map <String,String> req = new HashMap<String,String>();
		req.put(MSG.txnType, "52");
		req.put(MSG.txnSubType, payType.substring(2, 4));
		req.put(MSG.orderDate, Converter.dateToString(now));
		req.put(MSG.orderTime, Converter.timeToString(now));
		req.put(MSG.merId, mchntCd);
		req.put(MSG.orderId, orderId );
		//req.put(MSG.txnAmt, at.toString());
		req.put(MSG.txnAmt, amt.toString());
		req.put(MSG.currencyCode, currencyCode);

		req.put(MSG.accName, accName);//户名
		req.put(MSG.accNum, accNo);//卡号
		req.put(MSG.bankNum, bankCd);//联行号
		//req.put(INTER_MSG.reqIp, requestIp);//ip
		req.put(MSG.clientIp, requestIp);//ip

		//req.put("bankName", bankName);//银行名称
		req.put(MSG.notifyUrl, Constant.NOOP_URL);//后台通知地址，Constant.NOOP_URL表示不需通知http://noop.noop:0000/

		req.put(INTER_MSG.userId, userId);

		req.put(INTER_MSG.txnSrc, TxnSource.MER.getCode());
		req.put(MSG.unit, Amount.getDefaultUnit(currencyCode).toString());
		
		req.put(INTER_MSG.siteId, siteId);
		
		AjaxResult result = commonBO.buildAjaxResp(AjaxRespEnums.ERROR,mappingI18n(this.getClass().getSimpleName(),"取现操作失败", lang));
		
		Map<String, String> resp =new HashMap<String,String>();
		try {
			info("[InternalGatewayClient][代付][内部网关请求] "+req);
			resp = InternalGatewayClient.request(req, null) ;
			info("[InternalGatewayClient][代付][内部网关响应] "+resp);
			info("[代付结果] 卡号： %s, 持卡人： %s, 订单号： %s, 订单金额： %s, 币别： %s, 返回代码： %s, 返回信息： %s; ", accNo, accName, req.get(MSG.orderId), req.get(MSG.txnAmt), EnumLangUtil.translate(lang, "CurrType", currencyCode, false), resp.get(MSG.respCode), resp.get(MSG.respMsg));
		}catch(Exception e ) {
			error("[InternalGatewayClient][ERROR] 调用内部取现服务接口异常：" + e.getMessage(), e);
			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,e.getMessage());
		}

		if (!MsgRespHelper.isOkCode(resp.get(MSG.respCode))) {
			String statusDesc = null;
			if (resp.get("txnStatusDesc") != null) {
				statusDesc = resp.get("txnStatusDesc").toString();
			} else {
				if (resp.get("orderState") != null) {
					if("20".equals(resp.get("orderState").toString()))
						statusDesc = "处理中";
					else if("32".equals(resp.get("orderState").toString()))
						statusDesc = "交易失败";
					else if("33".equals(resp.get("orderState").toString()))
						statusDesc = "其他状态，请联系管理员";
				}
				else {
					statusDesc = "处理中";
				}
			}

			RiskEvent.mer()
				.who(merUser(mchntCd, userId))
				.event(RISK.Event.Withdraw)
				.result(RISK.Result.Failed)
				.reason(RISK.Reason.Exception)
             	.target(accName+"|"+accNo+"|"+cashAmt+ "|" + EnumLangUtil.translate(lang, "CurrType", currencyCode, false))
				.message("代付交易请求失败; 商户： %s, 用户： %s, 卡号： %s, 持卡人： %s, 订单号： %s, 订单金额： %s, 币别： %s, 联行号： %s, 请求IP: %s; 交易类型: %s", mchntCd, userId, accNo, accName, orderId, cashAmt, EnumLangUtil.translate(lang, "CurrType", currencyCode, false), bankCd, requestIp, payType)
				.params(eventParams)
				.submit();

			result = commonBO.buildAjaxResp(AjaxRespEnums.ERROR, String.format(
					"<font color='red' >" 
			+mappingI18n(this.getClass().getSimpleName(), statusDesc, lang)
			+mappingI18n(this.getClass().getSimpleName(),"！请到取现查询页面查看结果！", lang)+"</font> "
			+"</br>"+ mappingI18n(this.getClass().getSimpleName(),"订单号：", lang)
			+req.get(MSG.orderId)+"</br>"+ 
			mappingI18n(this.getClass().getSimpleName(),"订单时间：", lang)
			+req.get(MSG.orderDate)+req.get(MSG.orderTime)+"</br>"
			+mappingI18n(this.getClass().getSimpleName(),"订单金额：", lang)
			+new BigDecimal(req.get(MSG.txnAmt)).movePointLeft(2).toString()+"</br>"
			+mappingI18n(this.getClass().getSimpleName(),"币别：", lang)
			+EnumLangUtil.translate(lang, "CurrType", resp.get(MSG.currencyCode), false)+"</br>"
			+mappingI18n(this.getClass().getSimpleName(),"错误代码：", lang)
			+resp.get(MSG.respCode)+"</br>"
			+mappingI18n(this.getClass().getSimpleName(),"讯息：", lang) +resp.get(MSG.respMsg),resp.get(MSG.respCode)));
			
		}
		else {
			RiskEvent.mer()
				.who(merUser(mchntCd, userId))
				.event(RISK.Event.Withdraw)
				.result(RISK.Result.Ok)
             	.target(accName+"|"+accNo+"|"+cashAmt+ "|" + EnumLangUtil.translate(lang, "CurrType", currencyCode, false))
				.message("代付交易请求成功; 商户： %s, 用户： %s, 卡号： %s, 持卡人： %s, 订单号： %s, 订单金额： %s, 币别： %s, 联行号： %s, 请求IP: %s; 交易类型: %s", mchntCd, userId, accNo, accName, orderId, cashAmt, EnumLangUtil.translate(lang, "CurrType", currencyCode, false), bankCd, requestIp, payType)
				.params(eventParams)
				.submit();
			
			result = commonBO.buildAjaxResp(AjaxRespEnums.SUCC, String.format(
					"<font color='blue' >"+mappingI18n(this.getClass().getSimpleName()," 处理中，请到取现查询页面查看结果！", lang)+"</font> "
			+"</br>"+ mappingI18n(this.getClass().getSimpleName(),"订单号：", lang)
			+req.get(MSG.orderId)+"</br>"+ 
			mappingI18n(this.getClass().getSimpleName(),"订单时间：", lang)
			+req.get(MSG.orderDate)+req.get(MSG.orderTime)+"</br>"
			+mappingI18n(this.getClass().getSimpleName(),"订单金额：", lang)
			+new BigDecimal(req.get(MSG.txnAmt)).movePointLeft(2).toString()+"</br>"
			+mappingI18n(this.getClass().getSimpleName(),"币别：", lang)
			+EnumLangUtil.translate(lang, "CurrType", resp.get(MSG.currencyCode), false)+"</br>"
			+mappingI18n(this.getClass().getSimpleName(),"返回代码：", lang) +resp.get(MSG.respCode)));
					
		}
		return result;
	}

	/**
	 * @param accName
	 * @param accNo
	 * @param mchntCd
	 * @return 0=检查失败, 1=单一卡号match, 2=ALL
	 */
	protected int checkWhiteList(String accName, String accNo, String mchntCd) {
		IRiskListItemMerService riskListItemMerService = this.getService(IRiskListItemMerService.class);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("item", accWhiteItem(accName,accNo));
		qryParamMap.put("mchntCd", mchntCd);
		qryParamMap.put("chnlId", "00");
		qryParamMap.put("groupId", "102");
		List<RiskListItemMer> list = riskListItemMerService.select(qryParamMap);
		return this.getWhiteListType(list, false);
//
//		if (list.size()>0) {
//			for(RiskListItemMer item: list) {
//				if ("ALL".equals(item.getItem()))
//					return 2;
//			}
//			return 1;
//		}
//		return 0;
	}

	protected boolean isOKCode(String code) {
		return Utils.isInSet(code, "1101", "00", "0000");
	}


	@Autowired
	private MchntBO mchntBO;
	/**
	 * 取现确认
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.SuperUser})
	@RequestMapping("/comfirmWithdraw")
	public @ResponseBody AjaxResult comfirmWithdraw(String pwd) {
		String mchntCd = commonBO.getMchntCd();
		MchntInfo mchnt = mchntBO.getMchnt(mchntCd);
		// 校验登录密码
		if (!PwdUtil.computeMD5Pwd(pwd).equals(mchnt.getLoginPwd())) {
			return commonBO.buildSuccResp("respData", "10");
		}
		return commonBO.buildSuccResp("respData", "00");
	}

//	/**
//	 * 导出
//	 */
//	@RequestMapping("/export")  //导出
//	public @ResponseBody AjaxResult export(String mon, String startDt, String endDt,
//			String orderId, String transAt ,String transState, String accName, String accNo, HttpServletResponse response) {
//		AssertUtil.notMonStr(mon.substring(4));
//		AssertUtil.strIsBlank(startDt, "startDt is blank.");
//		AssertUtil.strIsBlank(startDt, "startDt is blank.");
//		Map<String, String> qryParams = new HashMap<String, String>();
//		String mchntCd = commonBO.getMchntCd();
//		qryParams.put("mchntCd", mchntCd);
//		qryParams.put("orderId", orderId);
//		qryParams.put("transAmt", transAt);
//		qryParams.put("startExtTransDt", startDt);
//		qryParams.put("endExtTransDt", endDt);
//		qryParams.put("transState", transState);
//
//		qryParams.put("accName", accName);
//		qryParams.put("accNo", accNo);
//
//		qryParams.put("ifPay", TxnConstants.ZERO);
//		info("代付交易查询开始:" + BeanUtil.desc(qryParams, null, null));
//		List<TransLog> list = transBO.qryTransList(mon.substring(4), qryParams);
//		String fileNm = mchntCd+Utils.getRandomInt(1000, 9999)+MerConfigCache.getConfig(MerConstants.CONFIG_KEY_WITHDRAW_EXPORT_FILENM);
//		String filePath = MerConfigCache.getConfig(MerConstants.CONFIG_KEY_TRANSLOG_EXPORT_FILE_PATH);
//		commonBO.exportTransLogExcel(list,filePath, fileNm, Constant.UTF8, response);
//		return null;
//	}

	/**
	 * 导出
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.PaymentUser,MerUserRole.SuperUser})
	@RequestMapping("/export")  //导出
	public @ResponseBody AjaxResult export(String startDt, String endDt,
			String orderId, String transAt ,String transState, String accName, String accNo, String currCd, HttpServletResponse response, HttpServletRequest req) {
//		AssertUtil.notMonStr(mon.substring(4));
		AssertUtil.strIsBlank(startDt, "startDt is blank.");
		AssertUtil.strIsBlank(startDt, "startDt is blank.");
		Map<String, String> qryParams = new HashMap<String, String>();
		String mchntCd = commonBO.getMchntCd();
		qryParams.put("mchntCd", mchntCd);
		qryParams.put("orderId", orderId);
		qryParams.put("transAmt", transAt);
//		qryParams.put("startExtTransDt", startDt);
//		qryParams.put("endExtTransDt", endDt);
		String mon = StringUtil.left(startDt, 8);
		qryParams.put("startExtTransDt", StringUtil.left(startDt, 8));
		qryParams.put("endExtTransDt", StringUtil.left(endDt, 8));
		qryParams.put("transState", transState);

		qryParams.put("accName", accName);
		qryParams.put("accNo", accNo);

		qryParams.put("ifPay", TxnConstants.ZERO);
		qryParams.put("currCd", currCd);

		info("代付交易查询开始:" + BeanUtil.desc(qryParams, null, null));

		String fileNm = mchntCd+Utils.getRandomInt(1000, 9999)+MerConfigCache.getConfig(MerConstants.CONFIG_KEY_WITHDRAW_EXPORT_FILENM);
		String filePath = MerConfigCache.getConfig(MerConstants.CONFIG_KEY_TRANSLOG_EXPORT_FILE_PATH);
		//List<TransLog> list = transBO.qryTransList(mon.substring(4), qryParams);
		//commonBO.exportTransLogExcel(list,filePath, fileNm, Constant.UTF8, response);
		this.exportData(transBO, mon, qryParams, ENTITY_TRANSFER, MerConstants.PAGE_CONF_WITHDRAWMNGEXP, filePath, fileNm, Constant.UTF8, response, req);
		return null;
	}

	/**
	 * 银行卡白名单列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.SuperUser})
	@RequestMapping("/accWhiteList")
	public @ResponseBody AjaxResult getAccWhiteItemList(int pageNum, int pageSize) {
		Map<String, String> qryParams = new HashMap<String, String>();
		qryParams.put("mchntCd", commonBO.getMchntCd());
		// 获取银行卡号
		IRiskListItemMerService riskListItemMerService = this.getService(IRiskListItemMerService.class);
		List<RiskListItemMer> p = riskListItemMerService.select(qryParams);
		List<String> list = new ArrayList<>();
		for(RiskListItemMer item : p ) {
			list.add(item.getItem());
		}
		return commonBO.buildSuccResp("list", list);
	}

	/**
	 * 重发通知
	 * @param transSeqId
	 * @param mon
	 * @param response
	 * @return
	 */
	@RequestMapping("/renotify.do")
	public @ResponseBody AjaxResult resendNotify(String transSeqId, String mon, HttpServletResponse response, HttpServletRequest request) {

        String lang = I18nMsgUtils.getLanguage(request);
		String msg="";
		try {
			AssertUtil.notMonStr(mon.substring(4,6));
			AssertUtil.strIsBlank(transSeqId, mappingI18n(this.getClass().getSimpleName(),"交易编号为空", lang));
			TransLog log = transBO.getTransFlow(transSeqId, mon.substring(4,6));
			AssertUtil.objIsNull(log, mappingI18n(this.getClass().getSimpleName(),"未找到交易明细:", lang) + transSeqId);
			String notifyUrl = log.getMchntBackUrl();
			checkNotifiUrl(notifyUrl);

			HttpClientResponse hr = super.resendNotifyToMer(transSeqId, mon);
			if (hr!=null) {
				msg = String.format(mappingI18n(this.getClass().getSimpleName(),"交易 %s 补发通知成功!\n通知地址: '%s' \n商户平台响应: [%s] %s", lang), transSeqId, notifyUrl, hr.getCode(), hr.getBody());
				return commonBO.buildSuccResp(msg);
			}
			else {
				msg = String.format(mappingI18n(this.getClass().getSimpleName(),"交易 %s 异步通知已补发!\n通知地址: '%s'", lang), transSeqId, notifyUrl);
				this.error("[resendNotify] "+msg);
			}
		} catch (Exception e) {
			msg = String.format(mappingI18n(this.getClass().getSimpleName(),"交易 %s 补发通知异常!\n%s", lang), transSeqId, mappingI18n(this.getClass().getSimpleName(), e.getMessage(), lang));
			this.error("[resendNotify] "+msg,e);
		}
		return commonBO.buildErrorResp(msg);
	}

	/**
	 * 加总
	 */
	@RequestMapping("/amtSum.do")
	public @ResponseBody JSONObject amtSum(String startDt, String endDt, String startTm, String endTm, String orderId,
			String transAt ,String transState, String accName, String userId,String accNo, String txnSrc, String currCd) {
//		AssertUtil.notMonStr(mon.substring(4));
		AssertUtil.strIsBlank(startDt, "startDt is blank.");
		Map<String, String> qryParams = new HashMap<String, String>();
		qryParams.put("mchntCd", commonBO.getMchntCd());
		qryParams.put("orderId", orderId);
		qryParams.put("transAt", transAt);
//		qryParams.put("startDate", startDt);
//		qryParams.put("endDate", endDt);
//		qryParams.put("startTime", startTm);//起始时间
//		qryParams.put("endTime", endTm);//结束时间
		String mon = StringUtil.left(startDt, 8);
		qryParams.put("startDate", StringUtil.left(startDt, 8));
		qryParams.put("endDate", StringUtil.left(endDt, 8));
		qryParams.put("startTime", StringUtil.right(startDt, 6));//起始时间
		qryParams.put("endTime", StringUtil.right(endDt, 6));//结束时间
		qryParams.put("transType", "52");
		qryParams.put("accName", accName);
		qryParams.put("accNo", accNo);
		qryParams.put("txnState", transState);//交易状态
		qryParams.put("txnSrc", txnSrc);//交易来源
		qryParams.put("userId", userId);
		qryParams.put("currCd", currCd);//币别
		logger.info("交易加總:" + BeanUtil.desc(qryParams, null, null));
		return transBO.summary(qryParams, mon);
	}

	/**
	 * 取得交易类型
	 * @param mchntCd
	 * @return
	 */
	protected List<TxnType> getPayTypesWithdraw(String mchntCd) {
		List<TxnType> getPayTypes = transBO.getPayTypesWithdraw(mchntCd);
		
        //去重复出现在选单的交易类型
        List<TxnType> uniquePayTypes = new ArrayList<>();
        for (TxnType payTypes : getPayTypes) {
        	if (getPayTypes != null && getPayTypes.size() > 0) {
				if (!uniquePayTypes.contains(payTypes))
					uniquePayTypes.add(payTypes);
        	}
		}
		return uniquePayTypes;
	}
	
	/***
	 * 藉由币别更动，改变交易类型选单
	 * @param currCd
	 * @return
	 */
    @RequestMapping(value = "/getPayTypesWithdrawByCurrCd.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult getPayTypesWithdrawByCurrCd(String currCd) {
    	String mchntCd =commonBO.getMchntCode();	
		
    	IMerSettlePolicySubService svc = this.getService(IMerSettlePolicySubService.class);
		List<MerSettlePolicySub>  msList = svc.select(mchntCd, currCd);
		List<Map<String, String>> payTypes = new ArrayList<>();
		for(MerSettlePolicySub ms: msList) {
			String ptCode = ms.getIntTransCd();
			TxnType payType = EnumUtil.parseEnumByCode(TxnType.class, ptCode, null);
			if (payType!=null){
				if(payType.getCode().startsWith("52")) {
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
    
	/***
	 * 币别改变，银行表也要联动
	 * @param req
	 * @param model
	 * @param currCd
	 * @return
	 */
	@RequestMapping(value = "/bankNums.do", method = RequestMethod.GET)
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.PaymentUser,MerUserRole.SuperUser})
	public  @ResponseBody AjaxResult bankNumsList(HttpServletRequest req, Model model, String currCd) {
		
		String mchntCd =commonBO.getMchntCd();
		
		//找出符合 资金池/代付路由器 的渠道
		List<String> uniqueChnlsId = new ArrayList<String>();
		uniqueChnlsId = getChnl(mchntCd, currCd);
		
		List<BankNums> res = arrangeBankListByCookie(BankInfoCaChe.getBankCdLstForWithdraw(uniqueChnlsId, currCd), req);
		if (res==null)
			res = new ArrayList<BankNums>();
		return commonBO.buildSuccResp("bankNums", res);
	}
	
	
	/***
	 * 找出符合 资金池/代付路由器 的渠道
	 * @param mchntCd
	 * @param currCd
	 * @return List<String>
	 */
	List<String> getChnl(String mchntCd, String currCd) {
		
		//找资金池的渠道(找商户所配置的资金池之大商编对应的渠道)
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("int_trans_cd", "5210");
		qryParamMap.put("mchnt_cd", mchntCd);
		qryParamMap.put("curr_cd", currCd);
		qryParamMap.put("route_status", "1");
		qryParamMap.put("account_state", "1");
		ITxnRoutingInfoWdPoolService txnRoutingInfoWdPoolService = this.getService(ITxnRoutingInfoWdPoolService.class);
		List<TxnRoutingInfoWdPool> selectChnls = txnRoutingInfoWdPoolService.select(qryParamMap);
		logger.info("在资金池找到的渠道数: " + selectChnls.size());

		//记录 资金池 和 代付路由器 所找到的渠道集合
		List<String> chnlsId= new ArrayList<>();

		//先将"资金池"找到的渠道编号放入list
        for (TxnRoutingInfoWdPool txnRoutingInfoWdPool : selectChnls) {
            chnlsId.add(txnRoutingInfoWdPool.getChnlId());
        }

		//找代付路由(在路由表上, 符合 交易类型是52带头, 状态是有效, 代入 商户号, 币别, 去找符合的渠道编号)
		ITxnRoutingService txnRoutingService = this.getService(ITxnRoutingService.class);
		
		TxnRoutingKey key = new TxnRoutingKey();
		key.setMchntCd(mchntCd);
		key.setCurrCd(currCd);
        List<TxnRoutingKey> select = txnRoutingService.selectByMchntCdAndCurrCd(key);
        logger.info("在代付路由找到的渠道数: " + select.size());

        //再将"代付路由器"找到的渠道编号也放入list
        for (TxnRoutingKey txnRouting_mapping : select) {
            chnlsId.add(txnRouting_mapping.getChnlId());
        }

        //除去lsit里, 重复的渠道编号(因为一个渠道会配到很多个大商编)
        List<String> uniqueChnlsId = new ArrayList<String>();
        for (String chnlId : chnlsId) {
        	if (chnlsId != null && chnlsId.size() > 0) {
				if (!uniqueChnlsId.contains(chnlId))
					uniqueChnlsId.add(chnlId);
        	}
		}
        logger.info("找到的总渠道数: " + uniqueChnlsId.size());
        
        return uniqueChnlsId;
	}
	
	////////////////////////////////////////////////////
	// 短期风控相关
	
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
	protected boolean checkRiskForUnblock(String accName, String accNum, String mchntCd, Map<String,String> eventParams, HttpServletRequest request) {
		if (!Utils.isEmpty(mchntCd)) {
			if (riskSvc().isUnblocked(mchntCd)) return true;
		}
		
		String reqIps= ""+MerUtils.getRemoteIps(request);
		List<String> reqIpList = getReqIpList(reqIps);
		
		// IP 检查
		if ((! Utils.isEmpty(reqIps)) && (! Constant.INTERNAL_IP.equals(reqIps))) {
			if (!Utils.isEmpty(reqIpList)) {
				for (String ip : reqIpList) {
					if (riskSvc().isUnblocked(ip)) return true;
				}
			}
			//if(! riskSvc().isValid(reqIps)) return false;
		}
		
		if (!Utils.isEmpty(accName) && !Utils.isEmpty(accNum)) {
			String accNameAndNum = String.format("%s|%s", accName, accNum);
			if (riskSvc().isUnblocked(accNameAndNum)) return true;
		}
		
		return false;
	}
	
	/**
	 * 检查是否被短期风控
	 * @param accName
	 * @param accNum
	 * @param mchntCd
	 * @param eventParams
	 * @param request
	 * @return
	 */
	protected boolean checkRiskForTempBlock(String accName, String accNum, String mchntCd, Map<String,String> eventParams, HttpServletRequest request) {
		
		if (checkRiskForUnblock(accName, accNum, mchntCd, eventParams, request)) return true;
		
		String accNameAndNum = String.format("%s|%s", accName, accNum);
		if (!Utils.isEmpty(accName) && !Utils.isEmpty(accNum)) {
			if (!riskSvc().isValid(accNameAndNum)) {
				warn("银行帐户(%s)存在风险，代付暂时被拒! 商户:%s, 用户:%s" , accNameAndNum, mchntCd, commonBO.getMchntUser().getUserId());
	         	RiskEvent.mer()
	             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
	             	.event(RISK.Event.Withdraw)
	             	.result(RISK.Result.Failed)
	             	.reason(RISK.Reason.Risk_Black_BankAcc)
	             	.target(accNameAndNum)
	             	.message("银行帐户(%s)存在风险，代付暂时被拒! 商户:%s, 用户:%s" , accNameAndNum, mchntCd, commonBO.getMchntUser().getUserId())
	             	.params(eventParams) //夹带其他资讯
	             	.submit();
				
				return false;
			}
		}
		if (!Utils.isEmpty(accName)) {
			if (!riskSvc().isValid(accName)) {
				warn("银行帐户名(%s)存在风险，代付暂时被拒! 商户:%s, 用户:%s" , accName, mchntCd, commonBO.getMchntUser().getUserId());
	         	RiskEvent.mer()
	             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
	             	.event(RISK.Event.Withdraw)
	             	.result(RISK.Result.Failed)
	             	.reason(RISK.Reason.Risk_Black_BankCard)
	             	.target(accName)
	             	.message("银行帐户名(%s)存在风险，代付暂时被拒! 商户:%s, 用户:%s" , accName, mchntCd, commonBO.getMchntUser().getUserId())
	             	.params(eventParams) //夹带其他资讯
	             	.submit();
	         	
				return false;
			}
		}
		if (!Utils.isEmpty(accNum)) {
			if (!riskSvc().isValid(accNum)) {
				warn("银行帐户号(%s)存在风险，代付暂时被拒! 商户:%s, 用户:%s" , accNum, mchntCd, commonBO.getMchntUser().getUserId());
	         	RiskEvent.mer()
	             	.who(merUser(mchntCd, commonBO.getMchntUser().getUserId()))
	             	.event(RISK.Event.Withdraw)
	             	.result(RISK.Result.Failed)
	             	.reason(RISK.Reason.Risk_Black_BankAccName)
	             	.target(accNum)
	             	.message("银行帐户号(%s)存在风险，代付暂时被拒! 商户:%s, 用户:%s" , accNum, mchntCd, commonBO.getMchntUser().getUserId())
	             	.params(eventParams) //夹带其他资讯
	             	.submit();
	         	
				return false;
			}
		}
		return true;
	}	
}
