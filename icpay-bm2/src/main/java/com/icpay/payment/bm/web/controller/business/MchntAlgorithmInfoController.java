package com.icpay.payment.bm.web.controller.business;

import static com.icpay.payment.risk.BmUser.bmUser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.bm.web.util.JSONUtils;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.constants.Constant.TxnCatalog;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.constants.Names.MSG;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.SettleEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.Amount;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicy;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MerSettleAlgorithm;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicy;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicyKey;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySub;
import com.icpay.payment.db.dao.mybatis.model.SettleAlgorithm;
import com.icpay.payment.db.service.IAgentProfitPolicyService;
import com.icpay.payment.db.service.IChnlMchntInfoService;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.IMerSettlePolicyService;
import com.icpay.payment.db.service.IMerSettlePolicySubService;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;

@Controller
@RequestMapping("/algorithm")
public class MchntAlgorithmInfoController extends SettleAlgorithmBaseController {

	private static final Logger logger = Logger.getLogger(MchntAlgorithmInfoController.class);
	private static final String DFT_DAILY_LIMIT = "99999999999999";
	private static final String RESULT_BASE_URI = "mchntAlgorithmInfo";
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
			m.put("balanceTransferDesc", "1".equals(balanceTransfer) ? "??????" : "??????");
			
			String balanceTransferT1 = m.get("balanceTransferT1");
			m.put("balanceTransferT1Desc", "1".equals(balanceTransferT1) ? "??????" : "??????");
			
			String settleMode = m.get("settleMode");
			m.put("settleModeDesc", EnumUtil.translate(SettleEnums.SettleMode.class, settleMode, false));
			
			String intTransCd = m.get("intTransCd");
			m.put("intTransCdDesc", EnumUtil.translate(SettleEnums.SettleTxnType.class, intTransCd, false));
			
			String currCd = m.get("currCd");
			m.put("currCdDesc", EnumUtil.translate(BmEnums.CurrCdType.class, currCd, true));
			
			String maxFee = m.get("maxFee");
			m.put("maxFeeDesc", formateAmt(maxFee, currCd));
			
			String minFee = m.get("minFee");
			m.put("minFeeDesc", formateAmt(minFee, currCd));
			String txAmtMax = m.get("txAmtMax");
			m.put("txAmtMaxDesc", formateAmt(txAmtMax, currCd));
			String txAmtMin = m.get("txAmtMin");
			m.put("txAmtMinDesc", formateAmt(txAmtMin, currCd));
			String txCardDayMax = m.get("txCardDayMax");
			m.put("txCardDayMaxDesc", formateAmt(txCardDayMax, currCd));
			String txDayMax = m.get("txDayMax");
			m.put("txDayMaxDesc", formateAmt(txDayMax, currCd));
			
		}
	};

	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult algorithmQry(int pageNum, int pageSize) {
		/*List<SettleAlgorithm> lst = buildAlgorithmList(pageNum, pageSize,this.getQryParamMap());
		Pager<SettleAlgorithm> p = new Pager<SettleAlgorithm>();
		p.setResultList(lst);
		p.setPageNum(pageNum);
		p.setPageSize(pageSize);
		p.setTotal(lst.size());*/
		
		IMerSettlePolicySubService service = this.getDBService(IMerSettlePolicySubService.class);
//		Pager<SettleAlgorithm> p = service.selectByPage(pageNum, pageSize, this.getQryParamMap());
		Pager<MerSettleAlgorithm> p = service.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_MERALGORITHM, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String algorithmAdd(Model model) {
		return super.toAdd(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult algorithmAddSubmit(Model model, SettleAlgorithm algorithm) {
		AssertUtil.objIsNull(algorithm, "??????????????????null");
		AssertUtil.strIsBlank(algorithm.getMchntCd(), "mchntCd is blank.");
		AssertUtil.strIsBlank(algorithm.getIntTransCd(), "intTransCd is blank.");
		AssertUtil.strIsBlank(algorithm.getCurrCd(), "currCd is blank.");
		
        Map<String,String> eventParams = //????????????
        		Utils.newMap(
        				INTER_MSG.requestUri , "/add/submit.do" ,
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
				EnumUtil.translate(SettleEnums.SettleTxnType.class, algorithm.getIntTransCd(), true)
				+ "?????????:" + EnumUtil.translate(BmEnums.CurrCdType.class, algorithm.getCurrCd(), true));
		MerSettlePolicySub mps = new MerSettlePolicySub();
		mps.setCurrCd(algorithm.getCurrCd());
		mps.setUnit(Amount.getDefaultUnit(algorithm.getCurrCd()));
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
		mps.setTxDayMax(txDayMax);
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
		
		this.logObj(BmEnums.FuncInfo._1000050000.getDesc(), CommonEnums.OpType.ADD, mps);
		logger.info("????????????????????????");
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String algorithmUpd(Model model, String mchntCd, String intTransCd,String currCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		AssertUtil.strIsBlank(intTransCd, "intTransCd is blank.");
		AssertUtil.strIsBlank(currCd, "currCd is blank.");
		String[] currCdSplit = currCd.split("-");
		currCd = currCdSplit[0];
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
		
		model.addAttribute("algorithm", algorithm);
		
//		//2021/05/22 ???????????????????????????
//		IMerSettlePolicyService mpService = this.getDBService(IMerSettlePolicyService.class);
//		MerSettlePolicyKey key = new MerSettlePolicyKey();
//		key.setCurrCd(currCd);
//		key.setMchntCd(mchntCd);
//		MerSettlePolicy msp = mpService.selectByPrimaryKey(key);
//		model.addAttribute("settlePeriod", msp.getSettlePeriod());
		
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult algorithmUpdSubmit(Model model, SettleAlgorithm algorithm) {
		AssertUtil.objIsNull(algorithm, "??????????????????null");
		AssertUtil.strIsBlank(algorithm.getMchntCd(), "mchntCd is blank.");
		AssertUtil.strIsBlank(algorithm.getIntTransCd(), "intTransCd is blank.");
		
        Map<String,String> eventParams = //????????????
        		Utils.newMap(
        				INTER_MSG.requestUri , "/edit/submit.do" ,
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
//		
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
		
		
		dbEntity.setComment(algorithm.getComment());
		dbEntity.setLastOperId(this.getSessionUsrId());
		service.update(dbEntity);
		
		/* ????????????/??????/??????????????? */
		JSONObject resultJson = JSONObject.parseObject(JsonUtil.toJson(algorithm), Feature.OrderedField);
		Map<String, Object> extraParams = JSONUtils.parseJSON2Map(resultJson.toString());
		
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Mchnt_Algorithm_Modify)
			.result(RISK.Result.Ok)
			.target(algorithm.getMchntCd()+"|"+algorithm.getIntTransCd()+"|"+EnumUtil.translate(BmEnums.CurrCdType.class, algorithm.getCurrCd(), false))
			.message(String.format("????????? %s, ??????????????????????????????????????? %s, ??????????????? %s, ????????? %s", commonBO.getSessionUser().getUsrId(), algorithm.getMchntCd(), algorithm.getIntTransCd(), EnumUtil.translate(BmEnums.CurrCdType.class, algorithm.getCurrCd(), false)))
			.params(eventParams)
			.params(extraParams)
			.submit();
		
		this.logObj(BmEnums.FuncInfo._1000050000.getDesc(), CommonEnums.OpType.UPDATE, algorithm);
		logger.info("????????????????????????");
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "delete.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult algorithmDelete(Model model, String mchntCd, String intTransCd,String currCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		AssertUtil.strIsBlank(intTransCd, "intTransCd is blank.");
		AssertUtil.strIsBlank(currCd, "currCd is blank.");
		
        Map<String,String> eventParams = //????????????
        		Utils.newMap(
        				INTER_MSG.requestUri , "delete.do" ,
        				MSG.merId, mchntCd,
        				INTER_MSG.intTxnType, intTransCd, 
        				MSG.currencyCode, currCd,
        				MSG.userId, this.getSessionUsrId(),
        				INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp()
        				);
        
		String[] currCdSplit = currCd.split("-");
		currCd = currCdSplit[0];
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
		
		this.logObj(BmEnums.FuncInfo._1000050000.getDesc(), CommonEnums.OpType.DELETE, dbEntity);
		logger.info("????????????????????????");
		return commonBO.buildSuccResp();
	}
	
	private AgentProfitPolicy getAgentProfitPolicy(String mchntCd, String intTransCd) {
		IMchntInfoService mchntService = this.getDBService(IMchntInfoService.class);
		MchntInfo mchnt = mchntService.selectByPrimaryKey(mchntCd);
		AssertUtil.objIsNull(mchnt, "???????????????:" + mchntCd);
		String agentCd = mchnt.getAgentCd();
		String tradeType = mchnt.getTradeType();
		IAgentProfitPolicyService apSvc = this.getDBService(IAgentProfitPolicyService.class);
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
	
	public static String formateAmt(String fieldAmt, String currCd) {
		if (StringUtil.isEmpty(fieldAmt)) {
			return "0.00";
		}
		BigDecimal srcAmt = Amount.create(currCd).defaultUnit().amount(fieldAmt).toRegularUnit().getAmountValue();
		String targetAmt = srcAmt.stripTrailingZeros().toPlainString(); //?????????????????????0
		return targetAmt;
	}
	
	/*private List<SettleAlgorithm> buildAlgorithmList(int pageNum, int pageSize,Map<String,String> m) {
		IMerSettlePolicySubService service = this.getDBService(IMerSettlePolicySubService.class);
//		String mchntCd =  m.get("mchntCd");
		List<MerSettlePolicySub> lst = service.selectByPage(pageNum, pageSize, m);
		List<SettleAlgorithm> result = new ArrayList<SettleAlgorithm>();
		for (MerSettlePolicySub mps : lst) {
			SettleAlgorithm am = new SettleAlgorithm();
			am.setMchntCd(mps.getMchntCd());
			am.setIntTransCd(mps.getIntTransCd());
			am.setIntTransCdDesc(EnumUtil.translate(SettleEnums.SettleTxnType.class, mps.getIntTransCd(), false));
			am.setSettleMode(mps.getSettleMode());
			am.setSettleModeDesc(EnumUtil.translate(SettleEnums.SettleMode.class, mps.getSettleMode(), false));
			
			am.setTxT0Percent(mps.getTxT0Percent());
			
			String settleAlgorithm=mps.getSettleAlgorithm();
			Map<String, String> map = JSONObject.parseObject(settleAlgorithm, new TypeReference<Map<String, String>>() {
			});
			am.setMinFee(new BigDecimal(map.get("minFee")).divide(new BigDecimal("100")).toString());
			am.setFixRate(map.get("fixRate"));
			am.setMaxFee(new BigDecimal(map.get("maxFee")).divide(new BigDecimal("100")).toString());
			
			am.setTxDayMax(mps.getTxDayMax());
			am.setTxAmtMin(mps.getTxAmtMin());
			am.setTxAmtMax(mps.getTxAmtMax());
			am.setTxTimeLimit(mps.getTxTimeLimit());
			am.setTxT0Percent(mps.getTxT0Percent());
			am.setTxCardDayMax(mps.getTxCardDayMax());
			am.setComment(mps.getComment());
			result.add(am);
		}
		return result;
		
	}*/
	
	@RequestMapping(value = "/batchFixRate.do", method = {RequestMethod.POST, RequestMethod.GET})
	@QryMethod
	public @ResponseBody AjaxResult batchFixRate(Model model, String newFixRate,String newMaxfee,String newMinfee,String newTxDayMax,String currCd,
			String newTxAmtMin,String newTxAmtMax,String newTxCardDayMax,String newTxT0Percent,String comment,String txTimeLimitInpt, HttpServletResponse response) {
		
		BigDecimal txT0PercentBd;
		String lastOperId = this.getSessionUsrId();
		IMerSettlePolicySubService service1 = this.getDBService(IMerSettlePolicySubService.class);
		IMerSettlePolicySubService service = this.getDBService(IMerSettlePolicySubService.class);
		//??????????????????
		Map<String, String> paramMap = this.getQryParamMap();
		
		//(??????????????????????????????)?????????qry.do?????????
		Pager<MerSettleAlgorithm> pager = service.selectByPage4Update(Integer.valueOf(paramMap.get("pageNum")), Integer.valueOf(paramMap.get("pageSize")), paramMap);
		List<MerSettleAlgorithm> lst = pager.getResultList();
		
		//?????????????????????????????????
		//List<MerSettleAlgorithmView> lst = service.selectByMap(this.getQryParamMap());
		StringBuffer bf = new StringBuffer();
		int count=0;
		for(MerSettleAlgorithm view : lst) {
			MerSettlePolicySub entity = new MerSettlePolicySub();
			entity.setMchntCd(view.getMchntCd());
			entity.setIntTransCd(view.getIntTransCd());
			entity.setSettleMode(view.getSettleMode());
			entity.setTxTimeLimit(view.getTxTimeLimit());
			if (newTxAmtMax != null) {
				entity.setTxAmtMax(this.judegIsNullMulUnit(newTxAmtMax, "0", currCd));
			}
			if (newTxAmtMin != null) {
				entity.setTxAmtMin(this.judegIsNullMulUnit(newTxAmtMin, "0", currCd));
			}
			if (newTxCardDayMax != null) {
				entity.setTxCardDayMax(this.judegIsNullMulUnit(newTxCardDayMax, DFT_DAILY_LIMIT, currCd));
			}
			if (newTxDayMax != null) {
				entity.setTxDayMax(this.judegIsNullMulUnit(newTxDayMax, DFT_DAILY_LIMIT, currCd));
			}
			if (txTimeLimitInpt != null) {
				entity.setTxTimeLimit(txTimeLimitInpt);
			}
			if (newTxT0Percent != null) {
				txT0PercentBd = new BigDecimal(newTxT0Percent);
				if (txT0PercentBd.compareTo(new BigDecimal("1")) == 1) {
					throw new BizzException("??????????????????????????????1");
				}
				entity.setTxT0Percent(this.judegIsNull(newTxT0Percent, "0"));
			}
			try {
				if (newFixRate != null && newMaxfee != null && newMinfee != null) {
					//2021/06/01 ????????????????????????????????????????????????
//					AgentProfitPolicy agentProfitPolicy = 
//					this.getAgentProfitPolicy(view.getMchntCd(), view.getIntTransCd());
//					entity.setSettleAlgorithm(this.algorithmDesc(agentProfitPolicy,view.getIntTransCd(),newFixRate,newMaxfee,newMinfee));
					
					Map<String, String> reqMap = new HashMap<String, String>();
					String settleAlgorithm = null;
					String minFee = this.judegIsNullMulUnit(newMinfee, "0", currCd);
					String maxFee = this.judegIsNullMulUnit(newMaxfee, DFT_DAILY_LIMIT, currCd);
					String fixRate = this.judegIsNull(newFixRate, "0");
					
					//???????????????algorithmDesc???????????????????????????
					if (minFee != null && maxFee != null 
							&& (new BigDecimal(minFee).compareTo(new BigDecimal(maxFee)) != -1)) {
						throw new BizzException("??????????????????????????????????????????");
					}
					
					TxnCatalog cat = Constant.getTxnCatalog(view.getIntTransCd());
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
					entity.setSettleAlgorithm(settleAlgorithm);
					
				}
			}catch (Exception e) {
				error(e.getMessage());
				bf.append("????????????" + view.getMchntCd()+ e.getMessage() + "???");
				continue;
			}
			entity.setLastOperId(lastOperId);
			entity.setCurrCd(currCd);
			count += service1.updateByExampleSelective(entity);
		}
		String errmsg ="";
		if(bf != null && bf.length() != 0) {
			errmsg = bf.toString();
			errmsg = errmsg.substring(0, errmsg.length()-1);
		}
		return this.commonBO.buildSuccResp(String.format("???????????????????????????????????? %s", count) +  (errmsg != null && errmsg.length() != 0? errmsg :""));
	}
	
	@RequestMapping(value = "/getChnlMchnts.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult getChnlMchnts(String chnlId) {
		List<ChnlMchntInfo> list = new ArrayList<>();
		
		IChnlMchntInfoService service = this.getDBService(IChnlMchntInfoService.class);
		List<ChnlMchntInfo> mchntList = service.getAllChnlInfo();

		//?????????????????????
		for(ChnlMchntInfo mchnt:mchntList) {
			if(mchnt.getChnlId().equals(chnlId)) {
				if(mchnt.getState().equals("1")) {
					list.add(mchnt);
				}
			}
		}
		AjaxResult resp= commonBO.buildSuccResp("chnlMchntList", list);
		return resp;
	}
	
	
}
