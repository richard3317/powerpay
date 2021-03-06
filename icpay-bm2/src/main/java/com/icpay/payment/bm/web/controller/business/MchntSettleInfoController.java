package com.icpay.payment.bm.web.controller.business;

import static com.icpay.payment.risk.BmUser.bmUser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.icpay.payment.bm.cache.BankInfoCaChe;
import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.cache.RegionInfoCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.entity.SettleAlgorithm;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.bm.web.util.JSONUtils;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.constants.Constant.TxnCatalog;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.constants.Names.MSG;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.MchntStCd;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.CurrencyEnums;
import com.icpay.payment.common.enums.SettleEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.Amount;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicy;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
//import com.icpay.payment.db.dao.mybatis.model.MerParams;
//import com.icpay.payment.db.dao.mybatis.model.MerParamsKey;
//import com.icpay.payment.db.dao.mybatis.model.MerParamsListv;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicy;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicyKey;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySub;
import com.icpay.payment.db.dao.mybatis.model.RegionInfo;
import com.icpay.payment.db.service.IAgentProfitPolicyService;
import com.icpay.payment.db.service.IMchntInfoService;
//import com.icpay.payment.db.service.IMerParamsService;
import com.icpay.payment.db.service.IMerSettlePolicyService;
import com.icpay.payment.db.service.IMerSettlePolicySubService;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;
import com.icpay.payment.service.ChnlMerAccService;

@Controller
@RequestMapping("/mchntSettleInfo")
public class MchntSettleInfoController extends BaseController {

	private static final Logger logger = Logger.getLogger(MchntSettleInfoController.class);
	private static final String DFT_DAILY_LIMIT = "99999999999999";
	private static final String RESULT_BASE_URI = "mchntSettleInfo";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String settlePeriod = m.get("settlePeriod");
			m.put("settlePeriodDesc", EnumUtil.translate(SettleEnums.SettlePeriod.class, settlePeriod, false));
			
			if (SettleEnums.SettlePeriod._T0.getCode().equals(settlePeriod)) {
				String settleLimit = m.get("settleLimit");
				if (!StringUtil.isBlank(settleLimit)) {
					m.put("settleLimit", StringUtil.formateAmt(settleLimit));
				}
			}
			String balanceTransfer = m.get("balanceTransfer");
			m.put("balanceTransferDesc", "1".equals(balanceTransfer) ? "???" : "???");
			
			String balanceTransferT1 = m.get("balanceTransferT1");
			m.put("balanceTransferT1Desc", "1".equals(balanceTransferT1) ? "???" : "???");
			
			String currCd = m.get("currCd");
			m.put("currCdDesc", EnumUtil.translate(BmEnums.CurrCdType.class, currCd, true));
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
		model.addAttribute("provMap", RegionInfoCache.getProvMap());
		model.addAttribute("bankNameLst", BankInfoCaChe.getBankNameLst());
		return super.toAdd(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addSubmit(MerSettlePolicy merSettlePolicy) {
		String preTransferTimeT1 = merSettlePolicy.getPreTransferTimeT1();
		String preTransferT1Percent = merSettlePolicy.getPreTransferT1Percent();
		if(!Utils.isEmpty(preTransferTimeT1) && Utils.isEmpty(preTransferT1Percent))
			AssertUtil.argIsBlank(preTransferT1Percent, "??????????????????T1????????????");
		
		String mchntCd = merSettlePolicy.getMchntCd();
		IMchntInfoService mchntService = this.getDBService(IMchntInfoService.class);
		MchntInfo m = mchntService.selectByPrimaryKey(mchntCd);
		AssertUtil.objIsNull(m, "???????????????:" + mchntCd);
		MchntStCd mchntStCd = new MchntStCd();
		mchntStCd.parseStCd(m.getStCd());
		/*AssertUtil.strNotEquals(MchntStCdEnums.SelfSettle._1.getCode(), 
				mchntStCd.getSelfSettle(), "?????????????????????????????????");*/
		IMerSettlePolicyService service = this.getDBService(IMerSettlePolicyService.class);
		if (SettleEnums.SettlePeriod._T0.getCode().equals(merSettlePolicy.getSettlePeriod())) {
			String settleLimit = merSettlePolicy.getSettleLimit();
			if (!StringUtil.isBlank(settleLimit)) {
				// ??????????????????
				settleLimit = StringUtil.transferAmt(settleLimit);
				merSettlePolicy.setSettleLimit(settleLimit);
			} else {
				merSettlePolicy.setSettleLimit("");
			}
		} else {
			merSettlePolicy.setSettleLimit("");
		}
		
		// ????????????????????????
		String settleAccountAreaCode = merSettlePolicy.getSettleAccountAreaCode();
		RegionInfo r = RegionInfoCache.getRegionInfo(settleAccountAreaCode);
		//AssertUtil.objIsNull(r, "?????????????????????" + settleAccountAreaCode);
		if (r != null) { //????????????????????????
			String provCd = r.getProvRegionCd();
			RegionInfo prov = RegionInfoCache.getRegionInfo(provCd);
			String cityCd = r.getCityRegionCd();
			RegionInfo city = RegionInfoCache.getRegionInfo(cityCd);
			String settleAccountAreaInfo = prov.getRegionCnNm() + city.getRegionCnNm() + r.getRegionCnNm();
			merSettlePolicy.setSettleAccountAreaInfo(settleAccountAreaInfo);
		}
		
		merSettlePolicy.setLastOperId(this.getSessionUsrId());
		logger.info("??????????????????????????????:" + m.getMchntCd());
		service.add(merSettlePolicy);
		logger.info("??????????????????????????????:" + m.getMchntCd());

		// ????????????
		logger.info("????????????????????????:" + m.getMchntCd());
//		MerAccService merAccService = ServiceProxy.getService(MerAccService.class);
//		merAccService.careateAccount(m.getMchntCd());
		try {
			chnlMerAccService().createAccount("00", m.getMchntCd());
			logger.info("????????????????????????:" + m.getMchntCd());
		} catch (Exception e) {
			logger.error("????????????????????????:" + m.getMchntCd(), e);
		}
		
		this.logObj(BmEnums.FuncInfo._1000050000.getCode(), CommonEnums.OpType.ADD, merSettlePolicy);
		return commonBO.buildSuccResp();
	}
	
	ChnlMerAccService cmas=null;
	private ChnlMerAccService chnlMerAccService(){
		if(cmas==null){
			cmas=ServiceProxy.getService(ChnlMerAccService.class);
		}
		return cmas;
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		IMerSettlePolicyService service = this.getDBService(IMerSettlePolicyService.class);
		Pager<MerSettlePolicy> p = service.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_MERSETTLEPOLICY, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, String mchntCd, String currCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		AssertUtil.strIsBlank(currCd, "currCd is blank.");
		IMerSettlePolicyService service = this.getDBService(IMerSettlePolicyService.class);
		MerSettlePolicyKey key = new MerSettlePolicyKey();
		key.setCurrCd(currCd);
		key.setMchntCd(mchntCd);
		MerSettlePolicy entity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(entity, "???????????????????????????:" + mchntCd);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_MERSETTLEPOLICY));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_MERSETTLEPOLICY, ENTITY_TRANSFER));
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, String mchntCd, String currCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		
		model.addAttribute("provMap", RegionInfoCache.getProvMap());
		model.addAttribute("bankNameLst", BankInfoCaChe.getBankNameLst());
		
		IMerSettlePolicyService service = this.getDBService(IMerSettlePolicyService.class);
		MerSettlePolicyKey key = new MerSettlePolicyKey();
		key.setCurrCd(currCd);
		key.setMchntCd(mchntCd);
		MerSettlePolicy entity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(entity, "?????????????????????:" + mchntCd);
		
		/*String settleAccountAreaCode = entity.getSettleAccountAreaCode();
		if (!StringUtil.isBlank(settleAccountAreaCode)) {
			RegionInfo r = RegionInfoCache.getRegionInfo(settleAccountAreaCode);
			AssertUtil.objIsNull(r, "?????????????????????" + settleAccountAreaCode);
			String provCd = r.getProvRegionCd();
			model.addAttribute("provCd", provCd);
			model.addAttribute("cityLst", RegionInfoCache.getCityLst(provCd));
			String cityCd = r.getCityRegionCd();
			if (RegionInfoCache.isDirectCity(provCd)) {
				model.addAttribute("cityCd", provCd);
				model.addAttribute("districtLst", RegionInfoCache.getDistrictLst(provCd));
			} else {
				model.addAttribute("cityCd", cityCd);
				model.addAttribute("districtLst", RegionInfoCache.getDistrictLst(cityCd));
			}
		}
		
		// ??????????????????????????????????????????
		String bankBranchName = entity.getSettleAccountBankName();
		String bankCd = entity.getSettleAccountBankCode();
		if (!StringUtil.isBlank(bankBranchName) && !StringUtil.isBlank(bankCd)) {
			IBankInfoService bs = this.getDBService(IBankInfoService.class);
			BankInfo b = bs.qryBankInfoByBankBranchInfo(bankBranchName, bankCd);
			AssertUtil.objIsNull(b, "????????????????????????:" + bankBranchName);
			model.addAttribute("bankName", b.getBankName());
		}*/
		
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_MERSETTLEPOLICY, ENTITY_TRANSFER));
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(MerSettlePolicy merSettlePolicy) {
		logger.info("??????????????????????????????:" + merSettlePolicy.getMchntCd());
		String preTransferTimeT1 = merSettlePolicy.getPreTransferTimeT1();
		String preTransferT1Percent = merSettlePolicy.getPreTransferT1Percent();
		if(!Utils.isEmpty(preTransferTimeT1) && Utils.isEmpty(preTransferT1Percent))
			AssertUtil.argIsBlank(preTransferT1Percent, "??????????????????T1????????????");
		
		IMerSettlePolicyService service = this.getDBService(IMerSettlePolicyService.class);
		MerSettlePolicyKey key = new MerSettlePolicyKey();
		key.setCurrCd(merSettlePolicy.getCurrCd());
		key.setMchntCd(merSettlePolicy.getMchntCd());
		MerSettlePolicy dbEntity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(dbEntity, "?????????????????????:" + merSettlePolicy.getMchntCd() + "?????????:" + EnumUtil.translate(CurrencyEnums.CurrType.class, merSettlePolicy.getCurrCd(), false));
		merSettlePolicy.setLastOperId(this.getSessionUsrId());
		if (SettleEnums.SettlePeriod._T0.getCode().equals(dbEntity.getSettlePeriod())) {
			String settleLimit = merSettlePolicy.getSettleLimit();
			if (!StringUtil.isBlank(settleLimit)) {
				// ??????????????????
				settleLimit = StringUtil.transferAmt(settleLimit);
				merSettlePolicy.setSettleLimit(settleLimit);
			} else {
				merSettlePolicy.setSettleLimit("");
			}
		} else {
			merSettlePolicy.setSettleLimit("");
		}
		
		// ????????????????????????
		/*String settleAccountAreaCode = merSettlePolicy.getSettleAccountAreaCode();
		RegionInfo r = RegionInfoCache.getRegionInfo(settleAccountAreaCode);
		AssertUtil.objIsNull(r, "?????????????????????" + settleAccountAreaCode);
		String provCd = r.getProvRegionCd();
		RegionInfo prov = RegionInfoCache.getRegionInfo(provCd);
		String cityCd = r.getCityRegionCd();
		RegionInfo city = RegionInfoCache.getRegionInfo(cityCd);
		String settleAccountAreaInfo = prov.getRegionCnNm() + city.getRegionCnNm() + r.getRegionCnNm();
		merSettlePolicy.setSettleAccountAreaInfo(settleAccountAreaInfo);*/
		
		service.update(merSettlePolicy);
		//?????????????????????????????????????????????????????????????????????
		IMchntInfoService mchntService = this.getDBService(IMchntInfoService.class);
		MchntInfo mchntInfo = mchntService.selectByPrimaryKey(merSettlePolicy.getMchntCd());
		mchntInfo.setMchntCnNm(merSettlePolicy.getSettleAccountName());
		mchntService.update(mchntInfo);
		this.logObj(BmEnums.FuncInfo._1000050000.getCode(), CommonEnums.OpType.UPDATE, merSettlePolicy);
		logger.info("??????????????????????????????:" + merSettlePolicy.getMchntCd());
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult delete(String mchntCd, String currCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		logger.info("??????????????????????????????:" + mchntCd);
		IMerSettlePolicyService service = this.getDBService(IMerSettlePolicyService.class);
		MerSettlePolicyKey key = new MerSettlePolicyKey();
		key.setCurrCd(currCd);
		key.setMchntCd(mchntCd);
		MerSettlePolicy entity = service.selectByPrimaryKey(key);
		service.delete(key);
		
		this.logObj(BmEnums.FuncInfo._1000050000.getCode(), CommonEnums.OpType.DELETE, entity);
		logger.info("??????????????????????????????:" + mchntCd);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/algorithm/manage.do", method = RequestMethod.GET)
	public String algorithmMng(Model model, String mchntCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		this.buildAlgorithmLst(model, mchntCd);
		return RESULT_BASE_URI + "/algorithm_mng";
	}
	
	@RequestMapping(value = "/algorithm/add.do", method = RequestMethod.GET)
	public String algorithmAdd(Model model, String mchntCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
//		IMerSettlePolicyService service = this.getDBService(IMerSettlePolicyService.class);
//		MerSettlePolicyKey key = new MerSettlePolicyKey();
//		key.setCurrCd(currCd);
//		key.setMchntCd(mchntCd);
//		MerSettlePolicy mp = service.selectByPrimaryKey(key);
		model.addAttribute("mchntCd", mchntCd);
//		model.addAttribute("settlePeriod", mp.getSettlePeriod());
		return RESULT_BASE_URI + "/algorithm_add";
	}
	
	@RequestMapping(value = "/algorithm/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult algorithmAddSubmit(Model model, SettleAlgorithm algorithm) {
		AssertUtil.objIsNull(algorithm, "??????????????????null");
		AssertUtil.strIsBlank(algorithm.getMchntCd(), "mchntCd is blank.");
		AssertUtil.strIsBlank(algorithm.getIntTransCd(), "intTransCd is blank.");
		AssertUtil.strIsBlank(algorithm.getCurrCd(), "currCd is blank.");

        Map<String,String> eventParams = //????????????
        		Utils.newMap(
        				INTER_MSG.requestUri , "/algorithm/add/submit.do" ,
        				MSG.merId, algorithm.getMchntCd(),
        				INTER_MSG.intTxnType, algorithm.getIntTransCd(), 
        				MSG.currencyCode, algorithm.getCurrCd(),
        				MSG.userId, this.getSessionUsrId(),
        				INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp()
        				);
		
		BigDecimal txT0PercentBd;
		if (!algorithm.getTxT0Percent().isEmpty()){
			txT0PercentBd = new BigDecimal(algorithm.getTxT0Percent());
			if (txT0PercentBd.compareTo(new BigDecimal("1")) == 1) {
				throw new BizzException("??????????????????????????????1");
			}
		}
		
		logger.info("????????????????????????");
		IMerSettlePolicySubService service = this.getDBService(IMerSettlePolicySubService.class);
		MerSettlePolicySub dbEntity = service.selectByPrimaryKey(algorithm.getMchntCd(), algorithm.getIntTransCd(),algorithm.getCurrCd());
		AssertUtil.objIsNotNull(dbEntity, "????????????????????????????????????:" + 
				EnumUtil.translate(SettleEnums.SettleTxnType.class, algorithm.getIntTransCd(), true));
		MerSettlePolicySub mps = new MerSettlePolicySub();
		mps.setMchntCd(algorithm.getMchntCd());
		mps.setIntTransCd(algorithm.getIntTransCd());
		Map<String, String> reqMap = new HashMap<String, String>();
		String settleAlgorithm = null;
		String txDayMax = this.judegIsNullMulUnit(algorithm.getTxDayMax(), DFT_DAILY_LIMIT, algorithm.getCurrCd());
		String txCardDayMax = this.judegIsNullMulUnit(algorithm.getTxCardDayMax(), DFT_DAILY_LIMIT, algorithm.getCurrCd());
		String txT0Percent=this.judegIsNull(algorithm.getTxT0Percent(), "0");
		reqMap.put("minFee", this.judegIsNullMulUnit(algorithm.getMinFee(), "0", algorithm.getCurrCd()));
		reqMap.put("fixRate", this.judegIsNull(algorithm.getFixRate(), "0"));
		reqMap.put("maxFee", this.judegIsNullMulUnit(algorithm.getMaxFee(), DFT_DAILY_LIMIT, algorithm.getCurrCd()));
		JSONObject Json = (JSONObject) JSON.toJSON(reqMap);
		settleAlgorithm = Json.toJSONString();
		mps.setCurrCd(algorithm.getCurrCd());
		mps.setTxDayMax(txDayMax);
		mps.setUnit(Amount.getDefaultUnit(algorithm.getCurrCd()));
		mps.setTxCardDayMax(txCardDayMax);
		mps.setTxT0Percent(txT0Percent);
		mps.setSettleAlgorithm(settleAlgorithm);
		mps.setSettleMode("2");
		mps.setTxAmtMax(this.judegIsNullMulUnit(algorithm.getTxAmtMax(), DFT_DAILY_LIMIT, algorithm.getCurrCd()));
		mps.setTxAmtMin(this.judegIsNullMulUnit(algorithm.getTxAmtMin(), "0", algorithm.getCurrCd()));
		mps.setTxTimeLimit(algorithm.getTxTimeLimit());
		AgentProfitPolicy agentProfitPolicy = 
			this.getAgentProfitPolicy(algorithm.getMchntCd(), algorithm.getIntTransCd());
		
		AssertUtil.objIsNull(agentProfitPolicy, "?????????????????????????????????");
		//mps.setSettleAlgorithm(algorithm.algorithmDesc(agentProfitPolicy));
		mps.setComment(algorithm.getComment());
		mps.setLastOperId(this.getSessionUsrId());
		service.add(mps);
		
		/* ????????????/??????/??????????????? */
		JSONObject resultJson = JSONObject.parseObject(JsonUtil.toJson(mps), Feature.OrderedField);
		Map<String, Object> extraParams = JSONUtils.parseJSON2Map(resultJson.toString());
		extraParams.remove("settleAlgorithm");
		extraParams.putAll(reqMap);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Mchnt_Algorithm_Add)
			.result(RISK.Result.Ok)
			.target(algorithm.getMchntCd()+"|"+algorithm.getIntTransCd()+"|"+EnumUtil.translate(BmEnums.CurrCdType.class, algorithm.getCurrCd(), false))
			.message(String.format("????????? %s, ??????????????????????????????????????? %s, ??????????????? %s, ????????? %s", commonBO.getSessionUser().getUsrId(), algorithm.getMchntCd(), algorithm.getIntTransCd(), EnumUtil.translate(BmEnums.CurrCdType.class, algorithm.getCurrCd(), false)))
			.params(eventParams)
			.params(extraParams)
			.submit();
//		IMerParamsService service2 = this.getDBService(IMerParamsService.class);
//		MerParams mp=new MerParams();
//		if(algorithm.getPageReturnUrl()!=null || !"".equals(algorithm.getPageReturnUrl())){
//			mp.setChnlId("00");
//			mp.setMchntCd(algorithm.getMchntCd());
//			mp.setParamCat(algorithm.getIntTransCd());
//			mp.setParamId("pageReturnUrl");
//			mp.setParamValue(algorithm.getPageReturnUrl());
//			mp.setParamDesc("??????????????????");
//			mp.setLastOperId(this.getSessionUsrId());
//			service2.add(mp);
//		}
//		if(algorithm.getNotifyUrl()!=null || !"".equals(algorithm.getNotifyUrl())){
//			mp.setChnlId("00");
//			mp.setMchntCd(algorithm.getMchntCd());
//			mp.setParamCat(algorithm.getIntTransCd());
//			mp.setParamId("notifyUrl");
//			mp.setParamValue(algorithm.getNotifyUrl());
//			mp.setParamDesc("??????????????????");
//			mp.setLastOperId(this.getSessionUsrId());
//			service2.add(mp);
//		}
//		if("0".equals(algorithm.getWhiteIpState())){
//			mp.setChnlId("00");
//			mp.setMchntCd(algorithm.getMchntCd());
//			mp.setParamCat(algorithm.getIntTransCd());
//			mp.setParamId("white_ip");
//			mp.setParamValue("list");
//			mp.setParamDesc("?????????;?????????(list=????????????,disable=?????????/??????????????????)");
//			mp.setLastOperId(this.getSessionUsrId());
//			service2.add(mp);
//		}else{
//			mp.setChnlId("00");
//			mp.setMchntCd(algorithm.getMchntCd());
//			mp.setParamCat(algorithm.getIntTransCd());
//			mp.setParamId("white_ip");
//			mp.setParamValue("disable");
//			mp.setParamDesc("?????????;?????????(list=????????????,disable=?????????/??????????????????)");
//			mp.setLastOperId(this.getSessionUsrId());
//			service2.add(mp);
//		}
//		IMerParamsListvService service3 = this.getDBService(IMerParamsListvService.class);
//		MerParamsListv mpl=new MerParamsListv();
//		if(algorithm.getWhiteIp()!=null || !"".equals(algorithm.getWhiteIp())){
//			String whiteIp=algorithm.getWhiteIp();
//			String[] ipList=whiteIp.split("\\|");
//			for(int i=0;i<ipList.length;i++){
//				mpl.setChnlId("00");
//				mpl.setMchntCd(algorithm.getMchntCd());
//				mpl.setParamCat(algorithm.getIntTransCd());
//				mpl.setParamId("white_ip");
//				mpl.setParamValue(ipList[i]);
//				mpl.setLastOperId(this.getSessionUsrId());
//				service3.add(mpl);
//			}
//		}
		this.logObj(BmEnums.FuncInfo._1000050000.getCode(), CommonEnums.OpType.ADD, mps);
		logger.info("????????????????????????");
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/algorithm/upd.do", method = RequestMethod.GET)
	public String algorithmUpd(Model model, String mchntCd, String intTransCd,String currCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		AssertUtil.strIsBlank(intTransCd, "intTransCd is blank.");
		AssertUtil.strIsBlank(currCd, "currCd is blank.");
		
		IMerSettlePolicySubService service = this.getDBService(IMerSettlePolicySubService.class);
		MerSettlePolicySub dbEntity = service.selectByPrimaryKey(mchntCd, intTransCd,currCd);
		AssertUtil.objIsNull(dbEntity, "????????????????????????");
		SettleAlgorithm algorithm = new SettleAlgorithm();
		algorithm.parseAlgorithm(dbEntity);
		algorithm.setCurrCd(currCd);
		algorithm.setTxDayMax(amtTranfer(currCd, dbEntity.getTxDayMax()));
		algorithm.setTxAmtMin(amtTranfer(currCd, dbEntity.getTxAmtMin()));
		algorithm.setTxAmtMax(amtTranfer(currCd, dbEntity.getTxAmtMax()));
		algorithm.setTxTimeLimit(dbEntity.getTxTimeLimit());
		algorithm.setTxCardDayMax(amtTranfer(currCd, dbEntity.getTxCardDayMax()));
		algorithm.setTxT0Percent(dbEntity.getTxT0Percent());
		//---
//		IMerParamsService service1=this.getDBService(IMerParamsService.class);
//		MerParamsKey mpk=new MerParamsKey("00", mchntCd, intTransCd);
//		mpk.setParamId("pageReturnUrl");
//		MerParams ms=service1.selectByPrimaryKey(mpk);
//		if (ms!=null)
//			algorithm.setPageReturnUrl(ms.getParamValue());
//		else
//			algorithm.setPageReturnUrl("");
//		
//		mpk=new MerParamsKey("00", mchntCd, intTransCd);
//		mpk.setParamId("notifyUrl");
//		ms=service1.selectByPrimaryKey(mpk);
//		if (ms!=null)
//			algorithm.setNotifyUrl(ms.getParamValue());
//		else
//			algorithm.setNotifyUrl("");
//		
//		mpk=new MerParamsKey("00", mchntCd, intTransCd);
//		mpk.setParamId("white_ip");
//		ms=service1.selectByPrimaryKey(mpk);
//		if (ms!=null) {
//			algorithm.setWhiteIpState(ms.getParamValue());
//			
//			IMerParamsListvService service2=this.getDBService(IMerParamsListvService.class);
//			MerParamsListv mp=new MerParamsListv("00", mchntCd, intTransCd);
//			mpk.setParamId("white_ip");
//			List<Long> idList=service2.getMerParamsListvKey(mp);
//			StringBuffer whiteIp=new StringBuffer(); 
//			for(int i=0;i<idList.size();i++){
//				whiteIp.append((service2.selectByPrimaryKey(idList.get(i))).getParamValue()+"|");
//			}
//			algorithm.setWhiteIp(whiteIp.subSequence(0, whiteIp.length()-1).toString());
//		}
		
		model.addAttribute("algorithm", algorithm);
		
//		IMerSettlePolicyService mpService = this.getDBService(IMerSettlePolicyService.class);
//		MerSettlePolicyKey key = new MerSettlePolicyKey();
//		key.setCurrCd(currCd);
//		key.setMchntCd(mchntCd);
//		MerSettlePolicy msp = mpService.selectByPrimaryKey(key);
//		AssertUtil.objIsNull(msp, "?????????????????????");
//		model.addAttribute("settlePeriod", msp.getSettlePeriod());
		
		return RESULT_BASE_URI + "/algorithm_upd";
	}
	
	@RequestMapping(value = "/algorithm/upd/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult algorithmUpdSubmit(Model model, SettleAlgorithm algorithm) {
		AssertUtil.objIsNull(algorithm, "??????????????????null");
		AssertUtil.strIsBlank(algorithm.getMchntCd(), "mchntCd is blank.");
		AssertUtil.strIsBlank(algorithm.getIntTransCd(), "intTransCd is blank.");
		AssertUtil.strIsBlank(algorithm.getCurrCd(), "currCd is blank.");
		
        Map<String,String> eventParams = //????????????
        		Utils.newMap(
        				INTER_MSG.requestUri , "/algorithm/upd/submit.do" ,
        				MSG.merId, algorithm.getMchntCd(),
        				INTER_MSG.intTxnType, algorithm.getIntTransCd(), 
        				MSG.currencyCode, algorithm.getCurrCd(),
        				MSG.userId, this.getSessionUsrId(),
        				INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp()
        				);
        
		BigDecimal txT0PercentBd;
		if (!algorithm.getTxT0Percent().isEmpty()){
			txT0PercentBd = new BigDecimal(algorithm.getTxT0Percent());
			if (txT0PercentBd.compareTo(new BigDecimal("1")) == 1) {
				throw new BizzException("??????????????????????????????1");
			}
		}
		
		logger.info("????????????????????????");
		IMerSettlePolicySubService service = this.getDBService(IMerSettlePolicySubService.class);
		MerSettlePolicySub dbEntity = service.selectByPrimaryKey(algorithm.getMchntCd(), algorithm.getIntTransCd(),algorithm.getCurrCd());
		AssertUtil.objIsNull(dbEntity, "????????????????????????");
		
		//2021/06/01 ????????????????????????????????????????????????
//		AgentProfitPolicy agentProfitPolicy = 
//			this.getAgentProfitPolicy(algorithm.getMchntCd(), algorithm.getIntTransCd());
//		AssertUtil.objIsNull(agentProfitPolicy, "???????????????????????????????????????");
		
//		dbEntity.setSettleAlgorithm(algorithm.algorithmDesc(agentProfitPolicy));
		
		Map<String, String> reqMap = new HashMap<String, String>();
		String settleAlgorithm = null;
		String minFee = this.judegIsNullMulUnit(algorithm.getMinFee(), "0", algorithm.getCurrCd());
		String maxFee = this.judegIsNullMulUnit(algorithm.getMaxFee(), DFT_DAILY_LIMIT, algorithm.getCurrCd());
		String fixRate = this.judegIsNull(algorithm.getFixRate(), "0");
		
		//???????????????algorithmDesc???????????????????????????
		if (minFee != null && maxFee != null 
				&& (new BigDecimal(minFee).compareTo(new BigDecimal(maxFee)) != -1)) {
			throw new BizzException("??????????????????????????????????????????");
		}
		
		TxnCatalog cat = Constant.getTxnCatalog(algorithm.getIntTransCd());
		if (TxnCatalog.CONSUME.equals(cat) || TxnCatalog.WITHDRAW.equals(cat)) {
			if (new BigDecimal(fixRate).compareTo(BigDecimal.ZERO) < 0) {
				throw new BizzException("?????????????????????????????????");
			}
			if (new BigDecimal(maxFee) != null && new BigDecimal(maxFee).compareTo(BigDecimal.ZERO) < 0) {
				throw new BizzException("?????????????????????????????????????????????0");
			}
			if (new BigDecimal(minFee) != null && new BigDecimal(minFee).compareTo(BigDecimal.ZERO) < 0) {
				throw new BizzException("?????????????????????????????????????????????0");
			}
		} else {
			throw new BizzException("????????????????????????");
		}
		
		reqMap.put("minFee", minFee);
		reqMap.put("maxFee", maxFee);
		reqMap.put("fixRate", fixRate);
		JSONObject Json = (JSONObject) JSON.toJSON(reqMap);
		settleAlgorithm = Json.toJSONString();
		dbEntity.setSettleAlgorithm(settleAlgorithm);
		
		String txDayMax = this.judegIsNullMulUnit(algorithm.getTxDayMax(), DFT_DAILY_LIMIT, algorithm.getCurrCd());
		String txCardDayMax = this.judegIsNullMulUnit(algorithm.getTxCardDayMax(), DFT_DAILY_LIMIT, algorithm.getCurrCd());
		String txT0Percent=this.judegIsNull(algorithm.getTxT0Percent(), "0");
		dbEntity.setTxDayMax(txDayMax);
		dbEntity.setTxCardDayMax(txCardDayMax);
		dbEntity.setTxT0Percent(txT0Percent);

		dbEntity.setTxAmtMax(this.judegIsNullMulUnit(algorithm.getTxAmtMax(), "0", algorithm.getCurrCd()));
		dbEntity.setTxAmtMin(this.judegIsNullMulUnit(algorithm.getTxAmtMin(), "0", algorithm.getCurrCd()));
		dbEntity.setTxTimeLimit(algorithm.getTxTimeLimit());
		
//		IMerParamsService service2 = this.getDBService(IMerParamsService.class);
//		MerParams mp=new MerParams();
//		if(algorithm.getPageReturnUrl()!=null || !"".equals(algorithm.getPageReturnUrl())){
//			mp.setChnlId("00");
//			mp.setMchntCd(algorithm.getMchntCd());
//			mp.setParamCat(algorithm.getIntTransCd());
//			mp.setParamId("pageReturnUrl");
//			mp.setParamValue(algorithm.getPageReturnUrl());
//			mp.setParamDesc("??????????????????");
//			mp.setLastOperId(this.getSessionUsrId());
//			service2.update(mp);
//		}
//		if(algorithm.getNotifyUrl()!=null || !"".equals(algorithm.getNotifyUrl())){
//			mp.setChnlId("00");
//			mp.setMchntCd(algorithm.getMchntCd());
//			mp.setParamCat(algorithm.getIntTransCd());
//			mp.setParamId("notifyUrl");
//			mp.setParamValue(algorithm.getNotifyUrl());
//			mp.setParamDesc("??????????????????");
//			mp.setLastOperId(this.getSessionUsrId());
//			service2.update(mp);
//		}
//		if("0".equals(algorithm.getWhiteIpState())){
//			mp.setChnlId("00");
//			mp.setMchntCd(algorithm.getMchntCd());
//			mp.setParamCat(algorithm.getIntTransCd());
//			mp.setParamId("white_ip");
//			mp.setParamValue("list");
//			mp.setParamDesc("?????????;?????????(list=????????????,disable=?????????/??????????????????)");
//			mp.setLastOperId(this.getSessionUsrId());
//			service2.update(mp);
//		}else{
//			mp.setChnlId("00");
//			mp.setMchntCd(algorithm.getMchntCd());
//			mp.setParamCat(algorithm.getIntTransCd());
//			mp.setParamId("white_ip");
//			mp.setParamValue("disable");
//			mp.setParamDesc("?????????;?????????(list=????????????,disable=?????????/??????????????????)");
//			mp.setLastOperId(this.getSessionUsrId());
//			service2.update(mp);
//		}
//		
//		IMerParamsListvService service3 = this.getDBService(IMerParamsListvService.class);
//		MerParamsListv mpl=new MerParamsListv();
//		mpl.setChnlId("00");
//		mpl.setMchntCd(algorithm.getMchntCd());
//		mpl.setParamCat(algorithm.getIntTransCd());
//		List<Long> idList=service3.getMerParamsListvKey(mpl);
//		for(int i=0;i<idList.size();i++){
//			service3.delete(idList.get(i));
//		}
//		if(algorithm.getWhiteIp()!=null || !"".equals(algorithm.getWhiteIp())){
//			String whiteIp=algorithm.getWhiteIp();
//			String[] ipList=whiteIp.split("\\|");
//			for(int i=0;i<ipList.length;i++){
//
//				mpl.setParamId("white_ip");
//				mpl.setParamValue(ipList[i]);
//				mpl.setLastOperId(this.getSessionUsrId());
//				service3.add(mpl);
//			}
//		}
		
		dbEntity.setComment(algorithm.getComment());
		dbEntity.setLastOperId(this.getSessionUsrId());
		service.update(dbEntity);
		
		/* ????????????/??????/??????????????? */
		JSONObject resultJson = JSONObject.parseObject(JsonUtil.toJson(dbEntity), Feature.OrderedField);
		Map<String, Object> extraParams = JSONUtils.parseJSON2Map(resultJson.toString());
		extraParams.remove("settleAlgorithm");
		
		if (!StringUtil.isBlank(settleAlgorithm)) {
			JSONObject settleAlgorithmJson = JSONObject.parseObject(settleAlgorithm, Feature.OrderedField);
			Map<String, Object> settleAlgorithmParams = JSONUtils.parseJSON2Map(settleAlgorithmJson.toString());
			extraParams.putAll(settleAlgorithmParams);
		}
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Mchnt_Algorithm_Modify)
			.result(RISK.Result.Ok)
			.target(algorithm.getMchntCd()+"|"+algorithm.getIntTransCd()+"|"+EnumUtil.translate(BmEnums.CurrCdType.class, algorithm.getCurrCd(), false))
			.message(String.format("????????? %s, ??????????????????????????????????????? %s, ??????????????? %s, ????????? %s", commonBO.getSessionUser().getUsrId(), algorithm.getMchntCd(), algorithm.getIntTransCd(), EnumUtil.translate(BmEnums.CurrCdType.class, algorithm.getCurrCd(), false)))
			.params(eventParams)
			.params(extraParams)
			.submit();
		
		this.logObj(BmEnums.FuncInfo._1000050000.getCode(), CommonEnums.OpType.UPDATE, algorithm);
		logger.info("????????????????????????");
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/algorithm/delete.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult algorithmDelete(Model model, String mchntCd, String intTransCd,String currCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		AssertUtil.strIsBlank(intTransCd, "intTransCd is blank.");
		AssertUtil.strIsBlank(currCd, "currCd is blank.");
		
        Map<String,String> eventParams = //????????????
        		Utils.newMap(
        				INTER_MSG.requestUri , "/algorithm/delete.do" ,
        				MSG.merId, mchntCd,
        				INTER_MSG.intTxnType, intTransCd, 
        				MSG.currencyCode, currCd,
        				MSG.userId, this.getSessionUsrId(),
        				INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp()
        				);
        
		logger.info("????????????????????????");
		IMerSettlePolicySubService service = this.getDBService(IMerSettlePolicySubService.class);
		MerSettlePolicySub dbEntity = service.selectByPrimaryKey(mchntCd, intTransCd,currCd);
		AssertUtil.objIsNull(dbEntity, "????????????????????????");
		service.delete(mchntCd, intTransCd,currCd);
		
		/* ????????????/??????/??????????????? */
		JSONObject resultJson = JSONObject.parseObject(JsonUtil.toJson(dbEntity), Feature.OrderedField);
		Map<String, Object> extraParams = JSONUtils.parseJSON2Map(resultJson.toString());
		extraParams.remove("settleAlgorithm");
		
		String settleAlgorithm = dbEntity.getSettleAlgorithm();
		if (!StringUtil.isBlank(settleAlgorithm)) {
			JSONObject settleAlgorithmJson = JSONObject.parseObject(settleAlgorithm, Feature.OrderedField);
			Map<String, Object> settleAlgorithmParams = JSONUtils.parseJSON2Map(settleAlgorithmJson.toString());
			extraParams.putAll(settleAlgorithmParams);
		}
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Mchnt_Algorithm_Del)
			.result(RISK.Result.Ok)
			.target(mchntCd+"|"+intTransCd+"|"+EnumUtil.translate(BmEnums.CurrCdType.class, currCd, false))
			.message(String.format("????????? %s, ??????????????????????????????????????? %s, ??????????????? %s, ????????? %s", commonBO.getSessionUser().getUsrId(), mchntCd, intTransCd, EnumUtil.translate(BmEnums.CurrCdType.class, currCd, false)))
			.params(eventParams)
			.params(extraParams)
			.submit();
		
//		IMerParamsService service1=this.getDBService(IMerParamsService.class);
//		MerParamsKey mpk=new MerParamsKey("00", mchntCd, intTransCd);
//		service1.deleteNoParamId(mpk);
//		IMerParamsListvService service2=this.getDBService(IMerParamsListvService.class);
//		MerParamsListv mp=new MerParamsListv("00", mchntCd, intTransCd);
//		List<Long> idList=service2.getMerParamsListvKey(mp);
//		for(int i=0;i<idList.size();i++){
//			service2.delete(idList.get(i));
//		}
		this.logObj(BmEnums.FuncInfo._1000050000.getCode(), CommonEnums.OpType.DELETE, dbEntity);
		logger.info("????????????????????????");
		return commonBO.buildSuccResp();
	}
	
	private void buildAlgorithmLst(Model model, String mchntCd) {
		IMerSettlePolicySubService service = this.getDBService(IMerSettlePolicySubService.class);
		List<MerSettlePolicySub> lst = service.select(mchntCd);
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		for (MerSettlePolicySub mps : lst) {
			Map<String, String> m = BeanUtil.desc(mps, null, null);
			m.put("currCdDesc", EnumUtil.translate(BmEnums.CurrCdType.class, mps.getCurrCd(), true));
			m.put("intTransCdDesc", 
					EnumUtil.translate(SettleEnums.SettleTxnType.class, mps.getIntTransCd(), true));
			m.put("settleModeDesc", 
					EnumUtil.translate(SettleEnums.SettleMode.class, mps.getSettleMode(), true));
			
			m.put("txT0Percent", mps.getTxT0Percent());
			
			String settleAlgorithm=mps.getSettleAlgorithm();
			Map<String, String> map = JSONObject.parseObject(settleAlgorithm, new TypeReference<Map<String, String>>() {
			});
			map.put("minFee", amtTranfer(mps.getCurrCd(), map.get("minFee")));
			map.put("fixRate",map.get("fixRate"));
			map.put("maxFee", amtTranfer(mps.getCurrCd(), map.get("maxFee")));
			JSONObject Json = (JSONObject) JSON.toJSON(map);
			settleAlgorithm = Json.toJSONString();
			m.put("settleAlgorithmDesc", 
					translateAlgorithm(mps.getSettleMode(),settleAlgorithm));
			
			m.put("settleAlgorithmLimit", translateAlgorithmLimit(mps));
			
//			m.put("comment", mps.getComment());
//			m.put("txDayMax", mps.getTxDayMax());
//			m.put("txAmtMin", mps.getTxAmtMin());
//			m.put("txAmtMax", mps.getTxAmtMax());
//			m.put("txTimeLimit", mps.getTxTimeLimit());
//			m.put("txT0Percent", mps.getTxT0Percent());
//			m.put("txCardDayMax", mps.getTxCardDayMax());
//			m.put("pageReturnUrl", mps.getComment());
//			m.put("comment", mps.getComment());
			result.add(m);
		}
		model.addAttribute("result", result);
		model.addAttribute("mchntCd", mchntCd);
	}
	
	private String translateAlgorithmLimit(MerSettlePolicySub mps) {
		StringBuilder desc = new StringBuilder();
		String format="????????????: %s, ????????????: %s (???)<br/>????????????: %s, ????????????: %s (???)<br/>????????????: %s";
		if (mps!=null) {
			desc.append(String.format(format, 
					amtTranfer(mps.getCurrCd(), mps.getTxAmtMin()), amtTranfer(mps.getCurrCd(), mps.getTxAmtMax()),
					amtTranfer(mps.getCurrCd(), mps.getTxCardDayMax()), amtTranfer(mps.getCurrCd(), mps.getTxDayMax()),
					mps.getTxTimeLimit()
					));
		}
		//this.toFloatAmt(amt)
		return desc.toString();
	}

	@SuppressWarnings("unchecked")
	private String translateAlgorithm(String settleMode, String algorithm) {
		StringBuilder desc = new StringBuilder();
		Map<String, String> m = JsonUtil.fromJson(algorithm, Map.class);
		if (SettleEnums.SettleMode._1.getCode().equals(settleMode)) {
			desc.append("?????????????????????:" + new BigDecimal(m.get(Constant.SETTLE_AlG_KEY.fixFee)).movePointLeft(2) + "???");
		} else if (SettleEnums.SettleMode._2.getCode().equals(settleMode)) {
			desc.append("??????:" + m.get(Constant.SETTLE_AlG_KEY.fixRate));
			if (!StringUtil.isBlank(m.get(Constant.SETTLE_AlG_KEY.minFee))) {
				BigDecimal bd = new BigDecimal(m.get(Constant.SETTLE_AlG_KEY.minFee));
				desc.append("<br/>???????????????:" + String.valueOf(bd) + "???");
			}
			if (!StringUtil.isBlank(m.get(Constant.SETTLE_AlG_KEY.maxFee))) {
				BigDecimal bd = new BigDecimal(m.get(Constant.SETTLE_AlG_KEY.maxFee));
				desc.append("<br/>???????????????:" + String.valueOf(bd) + "???");
			}
		} else if (SettleEnums.SettleMode._3.getCode().equals(settleMode)
				|| SettleEnums.SettleMode._5.getCode().equals(settleMode)) {
			
			for (int i = 1; i < 6; i ++) {
				String rangeFromKey = Constant.SETTLE_AlG_KEY.rangeFrom + i;
				String rangeToKey = Constant.SETTLE_AlG_KEY.rangeTo + i;
				String rateKey = Constant.SETTLE_AlG_KEY.rate + i;
				
				if (!StringUtil.isBlank(m.get(rangeFromKey))
						&& !StringUtil.isBlank(m.get(rangeToKey))
						&& !StringUtil.isBlank(m.get(rateKey))) {
					if (i > 1) {
						desc.append("<br/>");
					}
					BigDecimal bd1 = new BigDecimal(m.get(rangeFromKey));
					BigDecimal bd2 = new BigDecimal(m.get(rangeToKey));
					desc.append("????????????" + i + ":" + bd1.toString() + 
							"??????" + bd2.toString() + "????????????" + m.get(rateKey));
				}
			}
			
			if (!StringUtil.isBlank(m.get(Constant.SETTLE_AlG_KEY.minFee))) {
				BigDecimal bd = new BigDecimal(m.get(Constant.SETTLE_AlG_KEY.minFee));
				desc.append("<br/>???????????????:" + String.valueOf(bd) + "???");
			}
			if (!StringUtil.isBlank(m.get(Constant.SETTLE_AlG_KEY.maxFee))) {
				BigDecimal bd = new BigDecimal(m.get(Constant.SETTLE_AlG_KEY.maxFee));
				desc.append("<br/>???????????????:" + String.valueOf(bd) + "???");
			}
		} else if (SettleEnums.SettleMode._4.getCode().equals(settleMode)) {
			for (int i = 1; i < 6; i ++) {
				String rangeFromKey = Constant.SETTLE_AlG_KEY.rangeFrom + i;
				String rangeToKey = Constant.SETTLE_AlG_KEY.rangeTo + i;
				String rateKey = Constant.SETTLE_AlG_KEY.rate + i;
				
				if (!StringUtil.isBlank(m.get(rangeFromKey))
						&& !StringUtil.isBlank(m.get(rangeToKey))
						&& !StringUtil.isBlank(m.get(rateKey))) {
					if (i > 1) {
						desc.append("<br/>");
					}
					desc.append("????????????" + i + ":" + m.get(rangeFromKey) + 
							"??????" + m.get(rangeToKey) + "????????????" + m.get(rateKey));
				}
			}
			
			if (!StringUtil.isBlank(m.get(Constant.SETTLE_AlG_KEY.minFee))) {
				BigDecimal bd = new BigDecimal(m.get(Constant.SETTLE_AlG_KEY.minFee));
				desc.append("<br/>???????????????:" + String.valueOf(bd) + "???");
			}
			if (!StringUtil.isBlank(m.get(Constant.SETTLE_AlG_KEY.maxFee))) {
				BigDecimal bd = new BigDecimal(m.get(Constant.SETTLE_AlG_KEY.maxFee));
				desc.append("<br/>???????????????:" + String.valueOf(bd) + "???");
			}
		} else {
			throw new BizzException("????????????????????????:" + settleMode);
		}
		return desc.toString();
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
	
	/*
	 * ??????????????????(???)
	 */
	public String amtTranfer(String currCd, String fieldAmt) {
		BigDecimal srcAmt = Amount.create(currCd).defaultUnit().amount(fieldAmt).toRegularUnit().getAmountValue();
		String targetAmt = srcAmt.stripTrailingZeros().toPlainString(); //?????????????????????0
		return targetAmt;
	}
}
