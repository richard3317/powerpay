package com.icpay.payment.bm.web.controller.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icpay.payment.bm.bo.BusCheckBO;
import com.icpay.payment.bm.bo.MchntInfoBO;
import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.cache.DataDicCache;
import com.icpay.payment.bm.cache.MchntTpCache;
import com.icpay.payment.bm.cache.OrganInfoCache;
import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.cache.RegionInfoCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.entity.SettleAlgorithm;
import com.icpay.payment.bm.entity.SettlePolicy;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.MchntStCd;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.BusCheckTaskEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.CommonEnums.MchntSt;
import com.icpay.payment.common.enums.CommonEnums.MchntUserSt;
import com.icpay.payment.common.enums.CurrencyEnums.CurrType;
import com.icpay.payment.common.enums.SettleEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.Amount;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.PwdUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.AgentInfo;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicy;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfoKey;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicy;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicyKey;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySub;
import com.icpay.payment.db.dao.mybatis.model.OrganInfo;
import com.icpay.payment.db.dao.mybatis.model.OrganMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.TransTypeGroup;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MchntInfoAndMerSettlePolicy;
import com.icpay.payment.db.service.IAgentInfoService;
import com.icpay.payment.db.service.IAgentProfitPolicyService;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.IMchntUserInfoService;
import com.icpay.payment.db.service.IMerSettlePolicyService;
import com.icpay.payment.db.service.IOrganMchntInfoService;
import com.icpay.payment.db.service.ITransTypeGroupService;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;

import static com.icpay.payment.risk.BmUser.bmUser;

@Controller
@RequestMapping("/mchnt")
public class MchntController extends BaseController {

	private static final Logger logger = Logger.getLogger(MchntController.class);
	
	@Autowired
	private BusCheckBO busCheckBO;
	@Autowired
	private MchntInfoBO mchntInfoBO;
	
	private static final String RESULT_BASE_URI = "mchnt";
	private static final String CHNL_DFT_ALLOWED_REQ_SRC = "00";
	private static final String DFT_DAILY_LIMIT = "99999999999999";
	public static String SU = "su";  //???????????????????????????
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String mchntTp = m.get("mchntTp");
			String mchntTpDesc = MchntTpCache.getMchntTpMap().get(mchntTp);
			m.put("mchntTpDesc", StringUtil.isBlank(mchntTpDesc) ? mchntTp: mchntTp + "-" + mchntTpDesc);
			
			String areaCd = m.get("areaCd");
			m.put("areaCdDesc", RegionInfoCache.regionDesc(areaCd));
			
			String mchntSt = m.get("mchntSt");
			m.put("mchntStDesc", EnumUtil.translate(CommonEnums.MchntSt.class, mchntSt, true));
			
			String tradeType = m.get("tradeType");
			m.put("tradeTypeDesc", DataDicCache.translate(Constant.DATA_DIC_DATA_TP.TRADE_TYPE, tradeType));
			
			String riskFlag = m.get("riskFlag");
			m.put("riskFlagDesc", DataDicCache.translate(Constant.DATA_DIC_DATA_TP.MER_RISK_FLG, riskFlag));
			
			String apiType = (m.containsKey("apiType")) ? m.get("apiType") : "";
			if (StringUtils.isNotBlank(apiType)) {
				if ("0".equals(apiType) || "1".equals(apiType)) {
					m.put("apiType", EnumUtil.translate(TxnEnums.ApiType.class, apiType, false));
				} else {
					m.put("apiType", "?????????????????????,????????????");
				}
				m.put("apiTypeCode", apiType);
			} else {
				m.put("apiType", "");
				m.put("apiTypeCode", "");
			}
			
			String siteId = m.get("siteId");
			m.put("siteId", EnumUtil.translate(CommonEnums.Site.class, siteId, true));
		}
	};

	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/backToManage.do", method = RequestMethod.GET)
	public String backToManage(Model model) {
		return super.toManage(model, true, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(Model model) {
		this.buildCommonData(model);
		return super.toAdd(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addSubmit(MchntInfo mchntInfo, MchntStCd mchntStCd, MerSettlePolicy merSettlePolicy, SettlePolicy settlePolicy,SettleAlgorithm algorithm, OrganInfo organ) {
		AssertUtil.objIsNull(mchntInfo, "mchntInfo is null.");
		AssertUtil.strIsBlank(mchntInfo.getMchntCnNm(), "???????????????");
		AssertUtil.strIsBlank(mchntInfo.getExpiredDt(), "??????????????????");
		AssertUtil.strIsBlank(mchntInfo.getContactMail(), "????????????");
		AssertUtil.strIsBlank(mchntInfo.getAllowedReqSrc(), "???????????????????????????");
		AssertUtil.strIsBlank(mchntInfo.getAllowedReqSrcWd(), "???????????????????????????");
		MchntInfoAndMerSettlePolicy mchntInfoAndMerSettlePolicy = new MchntInfoAndMerSettlePolicy();
		mchntInfoAndMerSettlePolicy.setLastOperId(this.getSessionUsrId());
		IMchntInfoService service = this.getDBService(IMchntInfoService.class);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("fullMchntCnNm", mchntInfo.getMchntCnNm());
		List<MchntInfo> lst = service.select(qryParamMap);
		if (lst != null && !lst.isEmpty()) {
			throw new BizzException("???????????????:" + mchntInfo.getMchntCnNm());
		}
		//????????????????????????
		mchntInfoAndMerSettlePolicy = addMchntInfo(mchntInfo, mchntStCd, mchntInfoAndMerSettlePolicy);
		
		//????????????????????????
		mchntInfoAndMerSettlePolicy = addMerSettlePolicy(merSettlePolicy, settlePolicy, mchntInfoAndMerSettlePolicy);

		
		//????????????????????????
		mchntInfoAndMerSettlePolicy = addMerSettlePolicySub(algorithm, mchntInfoAndMerSettlePolicy);
		//????????????
		mchntInfoAndMerSettlePolicy = addOrganInfo(organ, mchntInfoAndMerSettlePolicy);
		
		// ??????????????????
		busCheckBO.newTask(BusCheckTaskEnums.TaskTp._05, commonBO.getSessionUser().getUsrId(), 
					CommonEnums.OpType.ADD_ALL, "??????????????????????????????????????????????????????", mchntInfoAndMerSettlePolicy);
		// ??????????????????
		this.logObj(BmEnums.FuncInfo._1000010000.getCode(), CommonEnums.OpType.ADD_REQUEST, mchntInfoAndMerSettlePolicy);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Mchnt_Add)
			.result(RISK.Result.Ok)
			.target(mchntInfo.getMchntCnNm())
			.message(String.format("????????? %s, ??????????????????(????????????)", commonBO.getSessionUser().getUsrId()))
			.params("mchntCnNm", mchntInfo.getMchntCnNm())
			.params("reqIp", commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		if (logger.isDebugEnabled()) {
			logger.debug("qry mchnt started...");
		}
		IMchntInfoService mchntService = this.getDBService(IMchntInfoService.class);
		Pager<MchntInfo> p = mchntService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_MCHNTINFO, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/txnConfig.do", method = RequestMethod.GET)
	public String txnConfig(Model model, String mchntCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		
		IMchntInfoService mchntService = this.getDBService(IMchntInfoService.class);
		MchntInfo m = mchntService.selectByPrimaryKey(mchntCd);
		AssertUtil.objIsNull(m, "?????????????????????:" + mchntCd);
		AssertUtil.strNotEquals(m.getMchntSt(), CommonEnums.MchntSt._1.getCode(), "??????????????????");
		model.addAttribute("mchnt", m);
		return RESULT_BASE_URI + "/txn_config";
	}
	
//	@RequestMapping(value = "/txnConfig/submit.do", method = RequestMethod.POST)
//	public @ResponseBody AjaxResult txnConfigSubmit(String mchntCd, String[] txnTypes) {
//		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
//		IMchntInfoService mchntService = this.getDBService(IMchntInfoService.class);
//		MchntInfo m = mchntService.selectByPrimaryKey(mchntCd);
//		AssertUtil.objIsNull(m, "?????????????????????:" + mchntCd);
//		AssertUtil.strNotEquals(m.getMchntSt(), CommonEnums.MchntSt._1.getCode(), "??????????????????");
//		m.setTransType(EnumUtil.buildCtrlRule(TxnEnums.TxnType.class, txnTypes, Constant.CHNL_DFT_TXN_TYPES));
//		// ??????????????????
//		busCheckBO.newTask(BusCheckTaskEnums.TaskTp._02, this.getSessionUsrId(), 
//				CommonEnums.OpType.UPDATE, "????????????????????????", m);
//		// ??????????????????
//		this.logObj(BmEnums.FuncInfo._1000010000.getCode(), CommonEnums.OpType.UPDATE_REQUEST, m);
//		return commonBO.buildSuccResp();
//	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, String mchntCd) {
		this.buildMchntData(model, mchntCd);
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, String mchntCd) {
		this.buildMchntData(model, mchntCd);
		this.buildCommonData(model);
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(MchntInfo mchntInfo, OrganInfo organ) {
		AssertUtil.objIsNull(mchntInfo, "entity is null.");
		AssertUtil.strIsBlank(mchntInfo.getAllowedReqSrc(), "???????????????????????????");
		AssertUtil.strIsBlank(mchntInfo.getAllowedReqSrcWd(), "???????????????????????????");
		
		IMchntInfoService mchntService = this.getDBService(IMchntInfoService.class);
		MchntInfo dbEntity = mchntService.selectByPrimaryKey(mchntInfo.getMchntCd());
		IMerSettlePolicyService merSettlePolicyService = this.getDBService(IMerSettlePolicyService.class);
//		MerSettlePolicy dbEntityPolicy = merSettlePolicyService.selectByPrimaryKey(mchntInfo.getMchntCd()); //???????????????????????????????????????????????????????????????????????????????????????!
		AssertUtil.objIsNull(dbEntity, "???????????????");
		
		MchntInfoAndMerSettlePolicy mchntInfoAndMerSettlePolicy = new MchntInfoAndMerSettlePolicy();
		// ??????????????????
		mchntInfoAndMerSettlePolicy.setMchntCd(mchntInfo.getMchntCd());
		mchntInfoAndMerSettlePolicy.setMchntCnNm(mchntInfo.getMchntCnNm());
		
		mchntInfoAndMerSettlePolicy.setMchntCnAbbr(mchntInfo.getMchntCnNm());
		//??????????????????????????????????????????????????????????????????????????????(????????????????????????)
		IMerSettlePolicyService service = this.getDBService(IMerSettlePolicyService.class);
		Map<String, String> map = new HashMap<String, String>();
		map.put("mchntCd", mchntInfo.getMchntCd());
		Pager<MerSettlePolicy> page = service.selectByPage(1, 20, map);
		List<MerSettlePolicy> lists = page.getResultList();
		//?????????????????????????????????????????????????????????
		for(MerSettlePolicy list:lists) {
			list.setSettleAccountName(mchntInfo.getMchntCnNm());
			service.update(list);
		}
		
		mchntInfoAndMerSettlePolicy.setAgentCd(mchntInfo.getAgentCd());
		mchntInfoAndMerSettlePolicy.setExpiredDt(mchntInfo.getExpiredDt());
		mchntInfoAndMerSettlePolicy.setTransTypeGroupId(mchntInfo.getTransTypeGroupId());
		mchntInfoAndMerSettlePolicy.setMchntSt(mchntInfo.getMchntSt());
		mchntInfoAndMerSettlePolicy.setContactPerson(mchntInfo.getContactPerson());
		mchntInfoAndMerSettlePolicy.setContactPhone(mchntInfo.getContactPhone());
		mchntInfoAndMerSettlePolicy.setContactMail(mchntInfo.getContactMail());
		mchntInfoAndMerSettlePolicy.setComment(mchntInfo.getComment());
		mchntInfoAndMerSettlePolicy.setMchntTp(dbEntity.getMchntTp());
		mchntInfoAndMerSettlePolicy.setAreaCd(dbEntity.getAreaCd());
		mchntInfoAndMerSettlePolicy.setTransType(dbEntity.getTransType());
		mchntInfoAndMerSettlePolicy.setTradeType(dbEntity.getTradeType());
//		mchntInfoAndMerSettlePolicy.setSettlePeriod(dbEntityPolicy.getSettlePeriod());
		mchntInfoAndMerSettlePolicy.setApiType(mchntInfo.getApiType());
		if (mchntInfo.getAllowedReqSrc().length() == 5) {
			mchntInfoAndMerSettlePolicy.setAllowedReqSrc("ALL");
		} else {
			mchntInfoAndMerSettlePolicy.setAllowedReqSrc(mchntInfo.getAllowedReqSrc() + ",04");//??????04:BM????????????
		}
		if (mchntInfo.getAllowedReqSrcWd().length() == 5) {
			mchntInfoAndMerSettlePolicy.setAllowedReqSrcWd("ALL");
		} else {
			mchntInfoAndMerSettlePolicy.setAllowedReqSrcWd(mchntInfo.getAllowedReqSrcWd() + ",04");//??????04:BM????????????
		}
		StringBuilder buf= new StringBuilder();
		buf.append(mchntInfo.getTrustDomain());
		if (buf.length()>0) buf.delete(buf.length()-1, buf.length());
		mchntInfoAndMerSettlePolicy.setTrustDomain(buf.toString());
		
		if(!Utils.isEmpty(organ.getOrganId())) {
			mchntInfoAndMerSettlePolicy.setOrganId(organ.getOrganId());
			mchntInfoAndMerSettlePolicy.setOrganDesc(organ.getOrganDesc());
		}
		
		busCheckBO.newTask(BusCheckTaskEnums.TaskTp._06, commonBO.getSessionUser().getUsrId(), 
				CommonEnums.OpType.UPDATE, "??????????????????", mchntInfoAndMerSettlePolicy);
		// ??????????????????
		this.logObj(BmEnums.FuncInfo._1000010000.getCode(), CommonEnums.OpType.UPDATE_REQUEST, mchntInfoAndMerSettlePolicy);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Mchnt_Modify)
			.result(RISK.Result.Ok)
			.target(mchntInfo.getMchntCd()+"|"+mchntInfo.getMchntCnNm())
			.message(String.format("????????? %s, ??????????????????(????????????)", commonBO.getSessionUser().getUsrId()))
			.params("mchntCd", mchntInfo.getMchntCd())
			.params("mchntCnNm", mchntInfo.getMchntCnNm())
			.params("reqIp", commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult delete(String mchntCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		
		IMchntInfoService mchntService = this.getDBService(IMchntInfoService.class);
		MchntInfo dbEntity = mchntService.selectByPrimaryKey(mchntCd);
		AssertUtil.objIsNull(dbEntity, "???????????????");
//		logger.debug("dbEntity??????:" + dbEntity);
				
		MchntInfoAndMerSettlePolicy mchntInfoAndMerSettlePolicy = new MchntInfoAndMerSettlePolicy();
		//??????MchntInfo dbEntity?????????
    	mchntInfoAndMerSettlePolicy.setMchntCd(dbEntity.getMchntCd());
    	mchntInfoAndMerSettlePolicy.setInsCd(dbEntity.getInsCd());
    	mchntInfoAndMerSettlePolicy.setLoginPwd(dbEntity.getLoginPwd());
    	mchntInfoAndMerSettlePolicy.setMchntCnNm(dbEntity.getMchntCnNm());
    	mchntInfoAndMerSettlePolicy.setMchntEnNm(dbEntity.getMchntEnNm());
    	mchntInfoAndMerSettlePolicy.setMchntCnAbbr(dbEntity.getMchntCnAbbr());
    	mchntInfoAndMerSettlePolicy.setMchntEnAbbr(dbEntity.getMchntEnAbbr());
    	mchntInfoAndMerSettlePolicy.setMchntAddr(dbEntity.getMchntAddr());
    	mchntInfoAndMerSettlePolicy.setZipCd(dbEntity.getZipCd());
    	mchntInfoAndMerSettlePolicy.setBizLicenseNo(dbEntity.getBizLicenseNo());
    	mchntInfoAndMerSettlePolicy.setContactPerson(dbEntity.getContactPerson());
    	mchntInfoAndMerSettlePolicy.setContactPhone(dbEntity.getContactPhone());
    	mchntInfoAndMerSettlePolicy.setContactMail(dbEntity.getContactMail());
    	mchntInfoAndMerSettlePolicy.setFax(dbEntity.getFax());
    	mchntInfoAndMerSettlePolicy.setTransType(dbEntity.getTransType());
    	mchntInfoAndMerSettlePolicy.setPayType(dbEntity.getPayType());
    	mchntInfoAndMerSettlePolicy.setStCd(dbEntity.getStCd());
    	mchntInfoAndMerSettlePolicy.setShareCertMchntCd(dbEntity.getShareCertMchntCd());
    	mchntInfoAndMerSettlePolicy.setTrustDomain(dbEntity.getTrustDomain());
    	mchntInfoAndMerSettlePolicy.setExpiredDt(dbEntity.getExpiredDt());
    	mchntInfoAndMerSettlePolicy.setMchntSt(dbEntity.getMchntSt());
    	
    	mchntInfoAndMerSettlePolicy.setComment(dbEntity.getComment());
    	mchntInfoAndMerSettlePolicy.setLastOperId(dbEntity.getLastOperId());
    	mchntInfoAndMerSettlePolicy.setRecCrtTs(dbEntity.getRecCrtTs());
    	mchntInfoAndMerSettlePolicy.setRecUpdTs(dbEntity.getRecUpdTs());
    	
    	mchntInfoAndMerSettlePolicy.setAreaCd(dbEntity.getAreaCd());
    	mchntInfoAndMerSettlePolicy.setTradeType(dbEntity.getTradeType());
    	mchntInfoAndMerSettlePolicy.setRiskFlag(dbEntity.getRiskFlag());
    	mchntInfoAndMerSettlePolicy.setLastLoginIp(dbEntity.getLastLoginIp());
    	mchntInfoAndMerSettlePolicy.setLastLoginTs(dbEntity.getLastLoginTs());
    	mchntInfoAndMerSettlePolicy.setLoginFailedCnt(dbEntity.getLoginFailedCnt());
    	mchntInfoAndMerSettlePolicy.setLoginComment(dbEntity.getLoginComment());
    	mchntInfoAndMerSettlePolicy.setTransTypeGroupId(dbEntity.getTransTypeGroupId());
    	mchntInfoAndMerSettlePolicy.setAllowedReqSrc(dbEntity.getAllowedReqSrc());
    	mchntInfoAndMerSettlePolicy.setAllowedReqSrcWd(dbEntity.getAllowedReqSrcWd());
    	mchntInfoAndMerSettlePolicy.setMchntTp(dbEntity.getMchntTp());
    	mchntInfoAndMerSettlePolicy.setAgentCd(dbEntity.getAgentCd());
    	mchntInfoAndMerSettlePolicy.setBgRetUrl(dbEntity.getBgRetUrl());
    	mchntInfoAndMerSettlePolicy.setPageRetUrl(dbEntity.getPageRetUrl());
    	mchntInfoAndMerSettlePolicy.setOtpSecretLogin(dbEntity.getOtpSecretLogin());
    	mchntInfoAndMerSettlePolicy.setOptSecretAdmin(dbEntity.getOptSecretAdmin());
    	mchntInfoAndMerSettlePolicy.setSecretInitState(dbEntity.getSecretInitState());
    	mchntInfoAndMerSettlePolicy.setApiType(dbEntity.getApiType());
    	
//    	merSettlePolicyService.selectByPrimaryKey???????????????????????????????????????????????????????????????????????????????????????!
//		IMerSettlePolicyService merSettlePolicyService = this.getDBService(IMerSettlePolicyService.class);
//		MerSettlePolicy dbEntityPolicy = merSettlePolicyService.selectByPrimaryKey(mchntCd);
////		logger.debug("dbEntityPolicy??????:" + dbEntityPolicy);
//		
//		if(!Utils.isEmpty(dbEntityPolicy)) {
//	    	//??????MerSettlePolicy dbEntityPolicy?????????
//	    	mchntInfoAndMerSettlePolicy.setSettleAccount(dbEntityPolicy.getSettleAccount());
//	    	mchntInfoAndMerSettlePolicy.setSettleAccountName(dbEntityPolicy.getSettleAccountName());
//	    	mchntInfoAndMerSettlePolicy.setSettleAccountAreaCode(dbEntityPolicy.getSettleAccountAreaCode());
//	    	mchntInfoAndMerSettlePolicy.setSettleAccountAreaInfo(dbEntityPolicy.getSettleAccountAreaInfo());
//	    	mchntInfoAndMerSettlePolicy.setSettleAccountBankName(dbEntityPolicy.getSettleAccountBankName());
//	    	mchntInfoAndMerSettlePolicy.setSettleAccountBankCode(dbEntityPolicy.getSettleAccountBankCode());
//	    	mchntInfoAndMerSettlePolicy.setSettlePeriod(dbEntityPolicy.getSettlePeriod());
//	    	mchntInfoAndMerSettlePolicy.setSettleLimit(dbEntityPolicy.getSettleLimit());
//			
//	    	//??????table??????????????????, ??????dbEntityPolicy??????(????????????dbEntity??????)
//	    	mchntInfoAndMerSettlePolicy.setComment(dbEntityPolicy.getComment());
//	    	mchntInfoAndMerSettlePolicy.setLastOperId(dbEntityPolicy.getLastOperId());
//	    	mchntInfoAndMerSettlePolicy.setRecCrtTs(dbEntityPolicy.getRecCrtTs());
//	    	mchntInfoAndMerSettlePolicy.setRecUpdTs(dbEntityPolicy.getRecUpdTs());
//			
//	    	mchntInfoAndMerSettlePolicy.setTransferInterval(dbEntityPolicy.getTransferInterval());
//	    	mchntInfoAndMerSettlePolicy.setTransferTime(dbEntityPolicy.getTransferTime());
//	    	mchntInfoAndMerSettlePolicy.setTransferTimeT1(dbEntityPolicy.getTransferTimeT1());
//	    	mchntInfoAndMerSettlePolicy.setBalanceTransfer(dbEntityPolicy.getBalanceTransfer());
//	    	mchntInfoAndMerSettlePolicy.setBalanceTransferT1(dbEntityPolicy.getBalanceTransferT1());
//	    	mchntInfoAndMerSettlePolicy.setTransferMode(dbEntityPolicy.getTransferMode());
//	    	mchntInfoAndMerSettlePolicy.setMchntSuffix(dbEntityPolicy.getMchntSuffix());
//	    	mchntInfoAndMerSettlePolicy.setPreTransferTimeT1(dbEntityPolicy.getPreTransferTimeT1());
//	    	mchntInfoAndMerSettlePolicy.setPreTransferT1Percent(dbEntityPolicy.getPreTransferT1Percent());
//		}
    	
		// ????????????????????????
		IOrganMchntInfoService organService = this.getDBService(IOrganMchntInfoService.class);
		OrganMchntInfo organ = organService.selectMchntByMchnt(mchntCd);
//		logger.debug("organ??????:" + organ);
		if(!Utils.isEmpty(organ)) {
			OrganInfoCache.getInstance();	
			mchntInfoAndMerSettlePolicy.setOrganId(organ.getOrganId());
			mchntInfoAndMerSettlePolicy.setOrganDesc(OrganInfoCache.getByOrganId(organ.getOrganId()));
//			logger.debug("OrganDesc??????:" + OrganInfoCache.getByOrganId(organ.getOrganId()));
		} else {
			mchntInfoAndMerSettlePolicy.setOrganId("");
			mchntInfoAndMerSettlePolicy.setOrganDesc("");
		}
		
		// ??????????????????
		busCheckBO.newTask(BusCheckTaskEnums.TaskTp._06, commonBO.getSessionUser().getUsrId(), 
				CommonEnums.OpType.DELETE, "??????????????????", mchntInfoAndMerSettlePolicy);
		// ??????????????????
		this.logObj(BmEnums.FuncInfo._1000010000.getCode(), CommonEnums.OpType.DELETE_REQUEST, dbEntity);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Mchnt_Del)
			.result(RISK.Result.Ok)
			.target(mchntCd)
			.message(String.format("????????? %s, ??????????????????(????????????)", commonBO.getSessionUser().getUsrId()))
			.params("mchntCd", mchntCd)
			.params("reqIp", commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/import.do", method = RequestMethod.GET)
	public String batchImport(Model model) {
		return RESULT_BASE_URI + "/import";
	}
	
	@RequestMapping(value = "/import/submit.do", method = RequestMethod.POST)
	public String batchImportSubmit(@RequestParam(value = "importFile", required = false) MultipartFile file, Model model) {
		this.logText(BmEnums.FuncInfo._1000010000.getDesc(), CommonEnums.OpType.UPLOAD_FILE, 
				"????????????????????????:" + file.getOriginalFilename());
		Map<String, Object> resultMap = mchntInfoBO.batchImport(file);
		commonBO.setSessionAttr(BMConstants.SESSION_KEY_MCHNT_IMPORT_RSLT_FILENM, 
				resultMap.get(BMConstants.SESSION_KEY_MCHNT_IMPORT_RSLT_FILENM));
		model.addAttribute("total", String.valueOf(resultMap.get("total")));
		model.addAttribute("succ", String.valueOf(resultMap.get("succ")));
		model.addAttribute("fail", String.valueOf(resultMap.get("fail")));
		return RESULT_BASE_URI + "/import_result";
	}
	
	@RequestMapping(value = "/downResult.do")
	public String downResult(HttpServletRequest request, HttpServletResponse response) {
		String resultFileNm = commonBO.getSessionAttr(BMConstants.SESSION_KEY_MCHNT_IMPORT_RSLT_FILENM);
		this.logText(BmEnums.FuncInfo._1000010000.getDesc(), CommonEnums.OpType.DOWNLOAD_FILE, 
				"??????????????????????????????:" + resultFileNm);
		commonBO.downFile(BMConfigCache.getConfig(BMConstants.CONFIG_KEY_MCHNT_IMPORT_FILE_PATH), resultFileNm, Constant.UTF8, response);
		return null;
	}
	
	@RequestMapping(value = "/downSample.do")
	public String downSample(HttpServletRequest request, HttpServletResponse response) {
		String sampleFileNm = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_MCHNT_IMPORT_SAMPLE_FILE_NM);
		this.logText(BmEnums.FuncInfo._1000010000.getDesc(), CommonEnums.OpType.DOWNLOAD_FILE, 
				"??????????????????????????????:" + sampleFileNm);
		commonBO.downFile(BMConfigCache.getConfig(BMConstants.CONFIG_KEY_MCHNT_IMPORT_FILE_PATH), sampleFileNm, Constant.GBK, response);
		return null;
	}
	
	@RequestMapping(value="/resetPwd.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult resetPwd(String mchntCd) {
		AssertUtil.strIsBlank(mchntCd, "???????????????:" + mchntCd);
//		IMchntInfoService service = this.getDBService(IMchntInfoService.class);
//		MchntInfo m = service.selectByPrimaryKey(mchntCd);
//		AssertUtil.objIsNull(m, "???????????????:" + mchntCd);
		String newPwd = StringUtil.randomPwd(10); /// ????????????10????????????
//		m.setLoginPwd(PwdUtil.computeMD5Pwd(newPwd));
//		if (MchntSt._9.getCode().equals(m.getMchntSt())) {
//			m.setMchntSt(MchntUserSt._0.getCode());
//		}
//		m.setLoginFailedCnt(0);
//		service.updateLoginInfo(m);
		
		// ??????????????????????????????
		IMchntUserInfoService svc = this.getDBService(IMchntUserInfoService.class);
		MchntUserInfoKey key = new MchntUserInfoKey();
		key.setMchntCd(mchntCd);
		key.setUserId(BMConstants.ADMIN_USER);
		MchntUserInfo user = svc.selectByPrimaryKey(key);
		AssertUtil.objIsNull(user, "????????????????????????:" + mchntCd);
		
		MchntUserInfo info = new MchntUserInfo();
		info.setLoginPwd(PwdUtil.computeMD5Pwd(newPwd));
		info.setPwdUpdTs(DateUtil.now19());
		info.setLoginFailedCnt(0);
		info.setMchntCd(mchntCd);
		info.setUserId(BMConstants.ADMIN_USER);
//		if (MchntSt._9.getCode().equals(user.getUserState())) {
//			info.setUserState(MchntUserSt._0.getCode());
//		}
		info.setUserState(MchntUserSt._0.getCode());
		svc.updateByPrimaryKeySelective(info);
		
		return commonBO.buildSuccResp("newPwd", newPwd);
	}
	

	@RequestMapping(value="/resetGaCode.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult resetGaCode(String mchntCd) {
		AssertUtil.strIsBlank(mchntCd, mchntCd);
		IMchntInfoService service = this.getDBService(IMchntInfoService.class);
		MchntInfo m = service.selectByPrimaryKey(mchntCd);
		AssertUtil.objIsNull(m, "???????????????:" + mchntCd);
//		if("2".equals(m.getSecretInitState())) {
//			m.setOptSecretAdmin("");
//			m.setSecretInitState("0");
//		}else {
//			m.setOptSecretAdmin("");
//			m.setOtpSecretLogin("");
//			m.setSecretInitState("0");
//		}
//		if (MchntSt._9.getCode().equals(m.getMchntSt())) {
//			m.setMchntSt(MchntSt._1.getCode());
//		}
//		m.setLoginFailedCnt(0);
//		service.updateSecretInitState(m);
		
		// ??????????????????
		IMchntUserInfoService svc = this.getDBService(IMchntUserInfoService.class);
		MchntUserInfoKey key = new MchntUserInfoKey();
		key.setMchntCd(mchntCd);
		key.setUserId(BMConstants.ADMIN_USER);
		MchntUserInfo user = svc.selectByPrimaryKey(key);
		AssertUtil.objIsNull(user, "????????????????????????:" + mchntCd);
		
		MchntUserInfo info = new MchntUserInfo();
		info.setOtpSecret("");
		info.setMchntCd(mchntCd);
		info.setUserId(BMConstants.ADMIN_USER);
		info.setUserRole(SU);
		if (MchntSt._9.getCode().equals(m.getMchntSt())) {
			info.setUserState(MchntSt._1.getCode());
		}
		info.setLoginFailedCnt(0);
		svc.updateByPrimaryKeySelective(info);
		// ??????????????????
		this.logObj("???????????????????????????????????????", CommonEnums.OpType.UPDATE, info);
		return commonBO.buildSuccResp("mchntCd", mchntCd);
	}
	
	@RequestMapping(value="/unlockAdmin.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult unlockAdmin(String mchntCd) {
		//????????????
		AssertUtil.strIsBlank(mchntCd, mchntCd);
		IMchntInfoService service = this.getDBService(IMchntInfoService.class);
		MchntInfo m = service.selectByPrimaryKey(mchntCd);
		AssertUtil.objIsNull(m, "???????????????:" + mchntCd);
		//?????????????????????
		IMchntUserInfoService svc = this.getDBService(IMchntUserInfoService.class);
		MchntUserInfoKey key = new MchntUserInfoKey();
		key.setMchntCd(mchntCd);
		key.setUserId(BMConstants.ADMIN_USER);
		MchntUserInfo user = svc.selectByPrimaryKey(key);
		AssertUtil.objIsNull(user, "????????????????????????:" + mchntCd);
		
		//???????????????admin??????????????????????????????????????????admin
		Map<String, String> map = new HashMap<String, String>();
		map.put("mchntCd", mchntCd);
		map.put("userId", BMConstants.ADMIN_USER);
		map.put("userState", "9");
		
		List<MchntUserInfo> MchntUserInfo = svc.selectByExample(map);
		if(MchntUserInfo.isEmpty()){
			throw new BizzException("?????????Admin????????????: " + mchntCd);
		}
		
		//??????????????????admin?????????(9)??????????????????(1)???tbl_mchnt_user_info???user_state
		MchntUserInfo info = new MchntUserInfo();
		info.setMchntCd(mchntCd);
		info.setUserId(BMConstants.ADMIN_USER);
		info.setUserState(MchntSt._1.getCode());

		svc.updateByPrimaryKeySelective(info);
		// ??????????????????
		this.logObj("??????admin??????", CommonEnums.OpType.UPDATE, info);
		return commonBO.buildSuccResp("mchntCd", mchntCd);
	}
	
	private void buildCommonData(Model model) {
		model.addAttribute("mchntTpMap", MchntTpCache.getMchntTpMap());
		model.addAttribute("provMap", RegionInfoCache.getProvMap());
		ITransTypeGroupService service = this.getDBService(ITransTypeGroupService.class);
		List<TransTypeGroup> lst = service.select(null);
		model.addAttribute("transTpGroup", lst);
		
		IAgentInfoService agentService = this.getDBService(IAgentInfoService.class);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("agentSt", CommonEnums.AgentSt._1.getCode());
		List<AgentInfo> agentlst = agentService.select(qryParamMap);
		model.addAttribute("agentlst", agentlst);
		
		model.addAttribute("olst", OrganInfoCache.getInstance().getOrganInfoList());
	}
	
	private void buildMchntData(Model model, String mchntCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		IMchntInfoService mchntService = this.getDBService(IMchntInfoService.class);
		MchntInfo m = mchntService.selectByPrimaryKey(mchntCd);
		AssertUtil.objIsNull(m, "?????????????????????:" + mchntCd);
		model.addAttribute("mchnt", m);
		MchntStCd mchntStCd = new MchntStCd();
		mchntStCd.parseStCd(m.getStCd());
		model.addAttribute("mchntStCd", mchntStCd);
		model.addAttribute("trustDomain", m.getTrustDomain());
		ITransTypeGroupService service = this.getDBService(ITransTypeGroupService.class);
		List<TransTypeGroup> lst = service.select(null);
		for (TransTypeGroup g : lst) {
			if (g.getSeqId() == m.getTransTypeGroupId()) {
				model.addAttribute("transTpGroupDesc", g.getSeqId() + "-" + g.getGroupNm());
				model.addAttribute("transType", g.getTransType());
			}
		}
		model.addAttribute("transTpGroup", lst);
		IAgentInfoService agentService = this.getDBService(IAgentInfoService.class);
		if (!StringUtil.isBlank(m.getAgentCd())) {
			AgentInfo agent = agentService.selectByPrimaryKey(m.getAgentCd());
			if (agent!=null)
				model.addAttribute("agentInfo", agent.getAgentCd() + "-" + agent.getAgentCnNm());
			else
				model.addAttribute("agentInfo", "");
		} else {
			model.addAttribute("agentInfo", "");
		}
		//????????????
		if (Utils.isEmpty(m.getApiType())) {
			model.addAttribute("apiType", CHNL_DFT_ALLOWED_REQ_SRC);
		} else if ("0".equals(m.getApiType())) {
			model.addAttribute("apiType", "10");
		} else if ("1".equals(m.getApiType())) {
			model.addAttribute("apiType", "01");
		} else if (m.getApiType().length() > 1) {
			model.addAttribute("apiType", "11");
		}
		//?????????????????????
		if (Utils.isEmpty(m.getAllowedReqSrc())) {
			model.addAttribute("allowedReqSrc", CHNL_DFT_ALLOWED_REQ_SRC);
		} else if ("ALL".equals(m.getAllowedReqSrc())) {
			model.addAttribute("allowedReqSrc", "11");
		} else {
			String[] allowedReqSrc = m.getAllowedReqSrc().split(",");
			model.addAttribute("allowedReqSrc", buildCtrlRule(TxnEnums.AllowedReqSrc.class, allowedReqSrc, CHNL_DFT_ALLOWED_REQ_SRC));
		}
		//?????????????????????
		if (Utils.isEmpty(m.getAllowedReqSrcWd())) {
			model.addAttribute("allowedReqSrcWd", CHNL_DFT_ALLOWED_REQ_SRC);
		} else if ("ALL".equals(m.getAllowedReqSrcWd())) {
			model.addAttribute("allowedReqSrcWd", "11");
		} else {
			String[] allowedReqSrcWd = m.getAllowedReqSrcWd().split(",");
			model.addAttribute("allowedReqSrcWd", buildCtrlRule(TxnEnums.AllowedReqSrc.class, allowedReqSrcWd, CHNL_DFT_ALLOWED_REQ_SRC));
		}
		
		// ???????????????????????????
		IOrganMchntInfoService organService = this.getDBService(IOrganMchntInfoService.class);
		OrganMchntInfo organ = organService.selectMchntByMchnt(mchntCd);
		if(!Utils.isEmpty(organ)) {
		OrganInfoCache.getInstance();
			model.addAttribute("organId", organ.getOrganId());
			model.addAttribute("organDesc",OrganInfoCache.getByOrganId(organ.getOrganId()));
		}else {
			model.addAttribute("organId", "");
			model.addAttribute("organDesc","");
		}
		
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_MCHNTINFO));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(m, BMConstants.PAGE_CONF_MCHNTINFO, ENTITY_TRANSFER));
	}
	
	public static String buildCtrlRule(Class clazz, String[] values, String ctrlRuleDftVal) {
		if ((values == null) || (values.length == 0)) {
			return ctrlRuleDftVal;
		} 
		char[] cs = ctrlRuleDftVal.toCharArray();
		for (String v : values) {
			if (!"04".equals(v)) {
				int idx = EnumUtil.parseIndx(TxnEnums.AllowedReqSrc.class, v);
				cs[idx] = '1';
			}
		}
		return new String(cs);
	}
	
	private String judegIsNull(String start, String result) {
		if ((start.trim() == null || "".equals(start.trim()))) {
			start = result;
		}
		return start;
	}
//	private String judegIsNullMul100(String start, String result) {
//		if ((start.trim() == null || "".equals(start.trim()))) {
//			start = result;
//		}else{
//			long bd = new BigDecimal(start).multiply(new BigDecimal("100")).longValue();
//			long bd2 = new BigDecimal(result).longValue();
//			if(!"0".equals(result)){
//				logger.info("bd---"+bd);
//				logger.info("bd2---"+bd2);
//				if(bd>=bd2){
//					start=String.valueOf(bd2);
//				}else{
//					start=String.valueOf(bd);
//				}	
//			}else{
//				start=String.valueOf(bd);
//			}
//		}
//		return start;
//	}
	
	private String judegIsNullMulUnit(String start, String result, String currCd) {
		if ((start.trim() == null || "".equals(start.trim()))) {
			start = result;
		}else{
			BigDecimal unit = Amount.getDefaultUnit(currCd);
			long bd = Amount.create(currCd).regularUnit().amount(start).toUnit(unit).getAmountValue().longValue();
			long bd2 = new BigDecimal(result).longValue();
			if(!"0".equals(result)){
				logger.info("bd---"+bd);
				logger.info("bd2---"+bd2);
				if(bd>=bd2){
					start=String.valueOf(bd2);
				}else{
					start=String.valueOf(bd);
				}	
			}else{
				start=String.valueOf(bd);
			}
		}
		return start;
	}
	
	private AgentProfitPolicy getAgentProfitPolicy(String mchntCd, String intTransCd) {
		IMchntInfoService mchntService = this.getDBService(IMchntInfoService.class);
		MchntInfo mchnt = mchntService.selectByPrimaryKey(mchntCd);
		AssertUtil.objIsNull(mchnt, "???????????????:" + mchntCd);
		String agentCd = mchnt.getAgentCd();
		String tradeType = mchnt.getTradeType();
		IAgentProfitPolicyService apSvc = this.getDBService(IAgentProfitPolicyService.class);
		//AgentProfitPolicyKey key = new AgentProfitPolicyKey();
		//key.setAgentCd(agentCd);
		//key.setIntTransCd(intTransCd);
		//key.setTradeType(tradeType);
		return apSvc.selectForMerTransType(agentCd, mchntCd, intTransCd, tradeType);
	}
	
	private MchntInfoAndMerSettlePolicy addMchntInfo(MchntInfo mchntInfo, MchntStCd mchntStCd, MchntInfoAndMerSettlePolicy mchntInfoAndMerSettlePolicy) {
		mchntInfoAndMerSettlePolicy.setSiteId(mchntInfo.getSiteId());
		mchntInfoAndMerSettlePolicy.setMchntCnNm(mchntInfo.getMchntCnNm());
		mchntInfoAndMerSettlePolicy.setMchntCnAbbr(mchntInfo.getMchntCnNm());
		mchntInfoAndMerSettlePolicy.setMchntEnNm("");
		mchntInfoAndMerSettlePolicy.setMchntEnAbbr("");
		mchntInfoAndMerSettlePolicy.setMchntAddr("");
		mchntInfoAndMerSettlePolicy.setBizLicenseNo("");
		mchntInfoAndMerSettlePolicy.setAreaCd("");
		mchntInfoAndMerSettlePolicy.setAgentCd(mchntInfo.getAgentCd());
		mchntInfoAndMerSettlePolicy.setExpiredDt(mchntInfo.getExpiredDt());
		mchntInfoAndMerSettlePolicy.setApiType(mchntInfo.getApiType());
		mchntInfoAndMerSettlePolicy.setTransTypeGroupId(mchntInfo.getTransTypeGroupId());
		mchntInfoAndMerSettlePolicy.setContactPerson(mchntInfo.getContactPerson());
		mchntInfoAndMerSettlePolicy.setContactPhone(mchntInfo.getContactPhone());
		mchntInfoAndMerSettlePolicy.setContactMail(mchntInfo.getContactMail());
		mchntInfoAndMerSettlePolicy.setComment(mchntInfo.getComment());
		mchntInfoAndMerSettlePolicy.setTransType("0000000000000000000000000000000000000000");
		mchntInfoAndMerSettlePolicy.setMchntSt(CommonEnums.MchntSt._1.getCode()); // ?????????????????????1-??????
		mchntInfoAndMerSettlePolicy.setStCd(mchntStCd.toStCd());
		mchntInfoAndMerSettlePolicy.setMchntTp("0000");//????????????
		mchntInfoAndMerSettlePolicy.setZipCd("");
		mchntInfoAndMerSettlePolicy.setTradeType("00");//????????????
		if (mchntInfo.getAllowedReqSrc().length() == 5) {
			mchntInfoAndMerSettlePolicy.setAllowedReqSrc("ALL");
		} else {
			mchntInfoAndMerSettlePolicy.setAllowedReqSrc(mchntInfo.getAllowedReqSrc() + ",04");//??????04:BM????????????
		}
		if (mchntInfo.getAllowedReqSrcWd().length() == 5) {
			mchntInfoAndMerSettlePolicy.setAllowedReqSrcWd("ALL");
		} else {
			mchntInfoAndMerSettlePolicy.setAllowedReqSrcWd(mchntInfo.getAllowedReqSrcWd() + ",04");//??????04:BM????????????
		}
		StringBuilder buf= new StringBuilder();
		buf.append(mchntInfo.getTrustDomain());
		if (buf.length()>0) buf.delete(buf.length()-1, buf.length());
		mchntInfoAndMerSettlePolicy.setTrustDomain(buf.toString());
		return mchntInfoAndMerSettlePolicy;
	}
	
	@Deprecated
	private MchntInfoAndMerSettlePolicy addMerSettlePolicy(MerSettlePolicy merSettlePolicy, MchntInfoAndMerSettlePolicy mchntInfoAndMerSettlePolicy) {
		String preTransferT1Percent = merSettlePolicy.getPreTransferT1Percent();
		if(!Utils.isEmpty(preTransferT1Percent)) {
			if (preTransferT1Percent.indexOf("%") != 0 && preTransferT1Percent.indexOf(".") != 0) {
				preTransferT1Percent = preTransferT1Percent + "%";
			}
		}
		if (SettleEnums.SettlePeriod._T0.getCode().equals(merSettlePolicy.getSettlePeriod())) {
			String settleLimit = merSettlePolicy.getSettleLimit();
			if (!StringUtil.isBlank(settleLimit)) {
				// ??????????????????
				settleLimit = StringUtil.transferAmt(settleLimit);
				mchntInfoAndMerSettlePolicy.setSettleLimit(settleLimit);
			} else {
				mchntInfoAndMerSettlePolicy.setSettleLimit("");
			}
		} else {
			mchntInfoAndMerSettlePolicy.setSettleLimit("");
		}
		mchntInfoAndMerSettlePolicy.setSettleAccountName(mchntInfoAndMerSettlePolicy.getMchntCnNm());
		mchntInfoAndMerSettlePolicy.setSettlePeriod(merSettlePolicy.getSettlePeriod());
		mchntInfoAndMerSettlePolicy.setBalanceTransfer(merSettlePolicy.getBalanceTransfer());
		mchntInfoAndMerSettlePolicy.setTransferTime(merSettlePolicy.getTransferTime());
		mchntInfoAndMerSettlePolicy.setBalanceTransferT1(merSettlePolicy.getBalanceTransferT1());
		mchntInfoAndMerSettlePolicy.setPreTransferTimeT1(merSettlePolicy.getPreTransferTimeT1());
		mchntInfoAndMerSettlePolicy.setPreTransferT1Percent(preTransferT1Percent);
		mchntInfoAndMerSettlePolicy.setTransferTimeT1(merSettlePolicy.getTransferTimeT1());
		mchntInfoAndMerSettlePolicy.setCurrCd(merSettlePolicy.getCurrCd());
		return mchntInfoAndMerSettlePolicy;
	}
	
	private MchntInfoAndMerSettlePolicy addMerSettlePolicy(MerSettlePolicy merSettlePolicy, SettlePolicy settlePolicy, MchntInfoAndMerSettlePolicy mchntInfoAndMerSettlePolicy) {
		
		String[] merSettlePolicyAll = settlePolicy.getMerSettlePolicyAll();
		if (!(merSettlePolicyAll.length == 1 && merSettlePolicyAll[0].equals(""))) { //???????????????????????????????????????????????? ???????????????????????????
			List<MerSettlePolicy> merSettlePolicyList = new ArrayList<MerSettlePolicy>();
			String[] settlePolicyAll = settlePolicy.getMerSettlePolicyAll();
			for (String mSettlePolicyAll : settlePolicyAll) {
				String[] policyAll = mSettlePolicyAll.split(",");
				for (String merSettlePolicyString : policyAll) {
					String[] policy = merSettlePolicyString.split("@~",-1);
					if (policy.length == 8) {
						MerSettlePolicy msp = new MerSettlePolicy();
						msp.setCurrCd(policy[0]);	
						msp.setSettlePeriod(policy[1]);
						msp.setBalanceTransfer(policy[2]);
						msp.setTransferTime(policy[3]);
						msp.setBalanceTransferT1(policy[4]);
						msp.setPreTransferTimeT1(policy[5]);
						
						String preTransferT1Percent = policy[6];
						if(!Utils.isEmpty(preTransferT1Percent)) {
							if (preTransferT1Percent.indexOf("%") == -1 && preTransferT1Percent.indexOf(".") == -1) {
								preTransferT1Percent = preTransferT1Percent + "%";
							}
						}
						msp.setPreTransferT1Percent(preTransferT1Percent);
						
						msp.setTransferTimeT1(policy[7]);
						msp.setComment("");
						msp.setLastOperId(this.getSessionUsrId());
						msp.setSettleAccountName(mchntInfoAndMerSettlePolicy.getMchntCnNm());
						merSettlePolicyList.add(msp);
					} else {
						AssertUtil.ifFalse(false, "????????????????????????");
					}
				}
			}
			mchntInfoAndMerSettlePolicy.setMerSettlePolicy(merSettlePolicyList);
			
		} else {
			if (!merSettlePolicy.getCurrCd().equals("all")) { 
				AssertUtil.ifFalse(false, "????????????????????????(????????????????????????)");
			}
			 //??? ?????? ?????? ????????????????????? ???????????????????????????
			List<MerSettlePolicy> merSettlePolicyList = new ArrayList<MerSettlePolicy>();
			
			Object[] currs = EnumUtil.getEnumConstants(CurrType.class);
			for (Object item: currs) {
				CurrType curr = (CurrType) item;
				MerSettlePolicy msp = new MerSettlePolicy();
				msp.setCurrCd(curr.getCode());
				msp.setSettleAccountName(mchntInfoAndMerSettlePolicy.getMchntCnNm());
				msp.setSettlePeriod(merSettlePolicy.getSettlePeriod());
				msp.setBalanceTransfer(merSettlePolicy.getBalanceTransfer());
				msp.setTransferTime(merSettlePolicy.getTransferTime());
				msp.setBalanceTransferT1(merSettlePolicy.getBalanceTransferT1());
				msp.setPreTransferTimeT1(merSettlePolicy.getPreTransferTimeT1());
				
				String preTransferT1Percent = merSettlePolicy.getPreTransferT1Percent();
				if(!Utils.isEmpty(preTransferT1Percent)) {
					if (preTransferT1Percent.indexOf("%") == -1 && preTransferT1Percent.indexOf(".") == -1) {
						preTransferT1Percent = preTransferT1Percent + "%";
					}
				}
				msp.setPreTransferT1Percent(preTransferT1Percent);
				msp.setTransferTimeT1(merSettlePolicy.getTransferTimeT1());
				msp.setComment("");
				msp.setLastOperId(this.getSessionUsrId());			
				merSettlePolicyList.add(msp);
			}
			mchntInfoAndMerSettlePolicy.setMerSettlePolicy(merSettlePolicyList);

		}

		return mchntInfoAndMerSettlePolicy;
	}
	
	private MchntInfoAndMerSettlePolicy addMerSettlePolicySub(SettleAlgorithm algorithm, MchntInfoAndMerSettlePolicy mchntInfoAndMerSettlePolicy) {
		List<MerSettlePolicySub> merSettlePolicySub = new ArrayList<MerSettlePolicySub>();
		AssertUtil.objIsNull(algorithm.getSettleAlgorithmAll(), "??????????????????null");
		String[] settleAlgorithmAll = algorithm.getSettleAlgorithmAll();
		for (String algorithmAll : settleAlgorithmAll) {
			String[] settleAll = algorithmAll.split(",");
			for (String settleAlgorithm : settleAll) {
				String[] settle = settleAlgorithm.split("@~",-1);
				if (settle.length == 11) {
					MerSettlePolicySub policySub = new MerSettlePolicySub();
					policySub.setIntTransCd(settle[0]);
					AssertUtil.strIsBlank(settle[0], "?????????????????????");
					Map<String, String> reqMap = new HashMap<String, String>();
					String settleAlgorithmJ = null;
					String txDayMax = this.judegIsNullMulUnit(settle[4], DFT_DAILY_LIMIT, settle[10]);
					String txCardDayMax = this.judegIsNullMulUnit(settle[5], DFT_DAILY_LIMIT, settle[10]);
					String txT0Percent=this.judegIsNull(settle[9], "0");
					reqMap.put("minFee", this.judegIsNullMulUnit(settle[2], "0", settle[10]));
					reqMap.put("fixRate", this.judegIsNull(settle[1], "0"));
					reqMap.put("maxFee", this.judegIsNullMulUnit(settle[3], DFT_DAILY_LIMIT, settle[10]));
					JSONObject Json = (JSONObject) JSON.toJSON(reqMap);
					settleAlgorithmJ = Json.toJSONString();
					policySub.setTxDayMax(txDayMax);
					policySub.setTxCardDayMax(txCardDayMax);
					policySub.setTxT0Percent(txT0Percent);
					policySub.setSettleAlgorithm(settleAlgorithmJ);
					policySub.setSettleMode("2");
					policySub.setTxAmtMax(this.judegIsNullMulUnit(settle[7], "0", settle[10]));
					policySub.setTxAmtMin(this.judegIsNullMulUnit(settle[6], "0", settle[10]));
					String txTimeLimit = settle[8];
					if (StringUtils.isNotBlank(settle[8]) && txTimeLimit.indexOf("???") != -1) {
						txTimeLimit = txTimeLimit.replace("???", ",");
					}
					policySub.setTxTimeLimit(txTimeLimit);
					policySub.setComment("");
					policySub.setLastOperId(this.getSessionUsrId());
					policySub.setUnit(Amount.getDefaultUnit(settle[10]));
					policySub.setCurrCd(settle[10]);
					
					merSettlePolicySub.add(policySub);
				} else {
					AssertUtil.ifFalse(false, "????????????????????????");
				}
			}
		}
		mchntInfoAndMerSettlePolicy.setMerSettlePolicySub(merSettlePolicySub);
		return mchntInfoAndMerSettlePolicy;
	}
	
	private MchntInfoAndMerSettlePolicy addOrganInfo(OrganInfo organ, MchntInfoAndMerSettlePolicy mchntInfoAndMerSettlePolicy) {
		if(!Utils.isEmpty(organ.getOrganId())) {
			mchntInfoAndMerSettlePolicy.setOrganId(organ.getOrganId());
			mchntInfoAndMerSettlePolicy.setOrganDesc(organ.getOrganDesc());
		}
		return mchntInfoAndMerSettlePolicy;
	}
}
