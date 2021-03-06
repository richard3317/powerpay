package com.icpay.payment.bm.web.controller.business;

import static com.icpay.payment.risk.BmUser.bmUser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import com.icpay.payment.bm.cache.ChnlMchntInfoCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.entity.ChnlMchntInfoSubMapping;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.bm.web.util.JSONUtils;
import com.icpay.payment.common.constants.Constant.INTER_MSG;
import com.icpay.payment.common.constants.Constant.MSG;
import com.icpay.payment.common.constants.RspCd;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.CurrencyEnums;
import com.icpay.payment.common.enums.SettleEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.Amount;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoKey;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoSub;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoSubKey;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntSettlePolicy;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntSettlePolicyKey;
import com.icpay.payment.db.dao.mybatis.model.VirtualTermInfo;
import com.icpay.payment.db.service.IChnlMchntInfoService;
import com.icpay.payment.db.service.IChnlMchntInfoSubService;
import com.icpay.payment.db.service.IChnlMchntSettlePolicyService;
import com.icpay.payment.db.service.IVirtualTermInfoService;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;
import com.icpay.payment.service.ChannelService;
import com.icpay.payment.service.ChnlMerAccService;

@Controller
@RequestMapping("/virtualTerm")
public class VirtualTermController extends BaseController {

	private static final Logger logger = Logger.getLogger(VirtualTermController.class);

	private static final String DFT_DAILY_LIMIT = "99999999999999";

	private static final String RESULT_BASE_URI = "virtualTerm";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String chnlId = m.get("chnlId");
			m.put("chnlIdDesc", EnumUtil.translate(TxnEnums.ChnlId.class, chnlId, true));

			String state = m.get("state");
			m.put("stateDesc", "1".equals(state) ? "??????" : "??????");
			
//			String balanceTransfer = m.get("balanceTransfer");
//			m.put("balanceTransferDesc", "1".equals(balanceTransfer) ? "??????" : "??????");
//
//			String balanceTransferT1 = m.get("balanceTransferT1");
//			m.put("balanceTransferT1Desc", "1".equals(balanceTransferT1) ? "??????" : "??????");
//
//			String settlePeriod = m.get("settlePeriod");
//			m.put("settlePeriodDesc",EnumUtil.translate(SettleEnums.SettlePeriod.class, settlePeriod, false));

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
		return super.toAdd(model, RESULT_BASE_URI);
	}

	@RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addSubmit(Model model, ChnlMchntInfo chnlMchntInfo) {
		
		AssertUtil.strIsBlank(chnlMchntInfo.getChnlMchntCd() , "??????????????????????????????");
		
		IChnlMchntInfoService chnlMchntInfoService = this.getDBService(IChnlMchntInfoService.class);
		ChnlMchntInfo dbEntity = chnlMchntInfoService.selectByPrimaryKey(chnlMchntInfo);
		AssertUtil.objIsNotNull(dbEntity, "????????????????????????????????????");
		chnlMchntInfo.setLastOperId(this.getSessionUsrId());
		chnlMchntInfoService.add(chnlMchntInfo);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Chnl_Mchnt_Add)
			.result(RISK.Result.Ok)
			.target(chnlMchntInfo.getChnlId()+"|"+chnlMchntInfo.getChnlMchntCd()+"|"+chnlMchntInfo.getChnlMchntDesc())
			.message(String.format("????????? %s, ??????????????????", commonBO.getSessionUser().getUsrId()))
			.params("chnlId", chnlMchntInfo.getChnlId())
			.params("chnlMchntCd", chnlMchntInfo.getChnlMchntCd())
			.params("chnlMchntDesc", chnlMchntInfo.getChnlMchntDesc())
			.params("state", chnlMchntInfo.getState())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		ChnlMchntInfoCache.getInstance().needRefresh();
		
		// ????????????????????????
		logger.info("????????????????????????:" + chnlMchntInfo.getChnlId() + chnlMchntInfo.getChnlMchntCd());
		ChnlMerAccService chnlMerAccService = ServiceProxy.getService(ChnlMerAccService.class);
		chnlMerAccService.createAccount(chnlMchntInfo.getChnlId(), chnlMchntInfo.getChnlMchntCd());
		logger.info("??????????????????????????????:" + chnlMchntInfo.getChnlId() + chnlMchntInfo.getChnlMchntCd());
		this.logObj(BmEnums.FuncInfo._1100040000.getDesc(), CommonEnums.OpType.ADD, chnlMchntInfo);
		return commonBO.buildSuccResp();
	}

	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize, int showMore) {
		//logger.debug(String.format("[***][qry] /qry.do; pageNum=%s, pageSize=%s, qryParamMap=%s",pageNum, pageSize, this.getQryParamMap()));
		Map<String,String> qryParams = new HashMap<String,String>();
		qryParams = this.getQryParamMap();

		if(showMore==1)
		{
			if(qryParams.containsKey("chnlMchntCd")) {
				qryParams.put("chnlMchntCdAssoc", qryParams.get("chnlMchntCd"));
				qryParams.put("chnlMchntCd", "");
			}
		}
		IChnlMchntInfoService chnlMchntInfoService = this.getDBService(IChnlMchntInfoService.class);
		Pager<ChnlMchntInfo> p = chnlMchntInfoService.selectByPage(pageNum, pageSize, qryParams);
		
//		if (p!=null)
//			for(ChnlMchntInfo info: p.getResultList()) {
//				logger.debug(String.format("[***][qryResult] item: %s",info.getChnlMchntCd()));
//			}
//		
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT,
				commonBO.transferPager(p, BMConstants.PAGE_CONF_MCHNT_CHNLINFO, ENTITY_TRANSFER));
	}

	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult delete(ChnlMchntInfo key) {
		IChnlMchntInfoService chnlMchntInfoService = this.getDBService(IChnlMchntInfoService.class);
		ChnlMchntInfo entity = chnlMchntInfoService.selectByPrimaryKey(key);
		AssertUtil.objIsNull(entity, "???????????????");
		// AssertUtil.strNotEquals(TxnEnums.VirtualTermStatus._0.getCode(),
		// entity.getState(), "??????????????????????????????");
		chnlMchntInfoService.delete(key);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Chnl_Mchnt_Del)
			.result(RISK.Result.Ok)
			.target(key.getChnlId()+"|"+key.getChnlMchntCd())
			.message(String.format("????????? %s, ??????????????????", commonBO.getSessionUser().getUsrId()))
			.params("chnlId", key.getChnlId())
			.params("chnlMchntCd", key.getChnlMchntCd())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		ChnlMchntInfoCache.getInstance().needRefresh();
		// ???????????????
		IChnlMchntInfoSubService chnlMchntInfoSubService = this.getDBService(IChnlMchntInfoSubService.class);
		List<ChnlMchntInfoSub> cmib = chnlMchntInfoSubService.queryAllNoIntTransCd(key);
		for (int i = 0; i < cmib.size(); i++) {
			ChnlMchntInfoSub cb = new ChnlMchntInfoSub();
			cb.setChnlId(key.getChnlId());
			cb.setChnlMchntCd(key.getChnlMchntCd());
			cb.setIntTransCd(cmib.get(i).getIntTransCd());
			chnlMchntInfoSubService.delete(cb);
		}
		this.logObj(BmEnums.FuncInfo._1100040000.getDesc(), CommonEnums.OpType.DELETE, entity);
		return commonBO.buildSuccResp();
	}

	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, ChnlMchntInfo chm) {
		IChnlMchntInfoService service = this.getDBService(IChnlMchntInfoService.class);
		ChnlMchntInfoKey key = new ChnlMchntInfoKey();
		key.setChnlId(chm.getChnlId());
		key.setChnlMchntCd(chm.getChnlMchntCd());
		ChnlMchntInfo entity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(entity, "???????????????");
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_MCHNT_CHNLINFO, ENTITY_TRANSFER));
		return super.toEdit(model, RESULT_BASE_URI);
	}

	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(Model model, ChnlMchntInfo chm) {
		IChnlMchntInfoService service = this.getDBService(IChnlMchntInfoService.class);
		ChnlMchntInfoKey key = new ChnlMchntInfoKey();
		key.setChnlId(chm.getChnlId());
		key.setChnlMchntCd(chm.getChnlMchntCd());
		ChnlMchntInfoKey entity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(entity, "???????????????");
		chm.setLastOperId(this.getSessionUsrId());
		service.update(chm);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Chnl_Mchnt_Modify)
			.result(RISK.Result.Ok)
			.target(chm.getChnlId()+"|"+chm.getChnlMchntCd()+"|"+chm.getChnlMchntDesc())
			.message(String.format("????????? %s, ??????????????????", commonBO.getSessionUser().getUsrId()))
			.params("chnlId", chm.getChnlId())
			.params("chnlMchntCd", chm.getChnlMchntCd())
			.params("chnlMchntDesc", chm.getChnlMchntDesc())
			.params("state", chm.getState())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		this.logObj(BmEnums.FuncInfo._1100040000.getDesc(), CommonEnums.OpType.UPDATE, chm);
		return commonBO.buildSuccResp();
	}

	@RequestMapping(value = "/signIn.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult signIn(VirtualTermInfo entity) {
		IVirtualTermInfoService virtualTermInfoService = this.getDBService(IVirtualTermInfoService.class);
		VirtualTermInfo dbEntity = virtualTermInfoService.selectByPrimarykey(entity);
		AssertUtil.objIsNull(dbEntity, "???????????????");

		try {
			//????????????
			ChannelService service = ServiceProxy.getService(ChannelService.class);
			Map<String, String> req = new HashMap<String, String>();
			req.put(INTER_MSG.channel, dbEntity.getChnlId());
			req.put(INTER_MSG.chnlMchntId, dbEntity.getMchntCd());
			req.put(INTER_MSG.chnlTermId, dbEntity.getTermId());
			req.put(INTER_MSG.operId, this.getSessionUsrId());
			logger.info("????????????????????????");
			Map<String, String> resp = service.signIn(req);
			String rspCode = resp.get(MSG.respCode);
			// ????????????????????????????????????
			if (RspCd.Z_0001.equals(rspCode)) {
				logger.info("????????????????????????");
				return commonBO.buildErrorResp(resp.get(MSG.respMsg));
			}
			logger.info("????????????????????????");
			this.logObj(BmEnums.FuncInfo._1100040000.getDesc(), CommonEnums.OpType.CALL_SERVICE, entity);
			return commonBO.buildSuccResp();
		} catch (Exception e) {
			logger.error("????????????????????????", e);
			return commonBO.buildErrorResp("????????????");
		}
	}

	@RequestMapping(value = "/settlePolicy/manage.do", method = RequestMethod.GET)
	public String settlePolicyMng(Model model, String chnlId, String chnlMchntCd) {
		
		logger.debug(String.format("[***][settlePolicyMng] /settlePolicy/manage.do; chnlMchntCd=%s",chnlMchntCd));
		AssertUtil.strIsBlank(chnlId, "chnlId is blank.");
		this.buildSettlePolicyLst(model, chnlId, chnlMchntCd);
		return RESULT_BASE_URI + "/settlePolicy_mng";
	}
	
	private void buildSettlePolicyLst(Model model, String chnlId, String chnlMchntCd) {
		
		IChnlMchntSettlePolicyService chnlMchntSettlePolicyService = this.getDBService(IChnlMchntSettlePolicyService.class);
		List<ChnlMchntSettlePolicy> lst = chnlMchntSettlePolicyService.select(chnlId, chnlMchntCd);
		
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		for (ChnlMchntSettlePolicy cmsp : lst) {
			Map<String, String> m = BeanUtil.desc(cmsp, null, null);
			//??????
			m.put("currCdDesc", 
					EnumUtil.translate(BmEnums.CurrCdType.class, cmsp.getCurrCd(), true));
			//????????????
			m.put("settlePeriodDesc", 
					EnumUtil.translate(SettleEnums.SettlePeriod.class, cmsp.getSettlePeriod(), false));
			
			//D0??????
			String balanceTransfer = cmsp.getBalanceTransfer();
			m.put("balanceTransferDesc", "1".equals(balanceTransfer) ? "??????" : "??????");
			
			//T1??????
			String balanceTransferT1 = cmsp.getBalanceTransferT1();
			m.put("balanceTransferT1Desc", "1".equals(balanceTransferT1) ? "??????" : "??????");
			
			//????????????
			m.put("transferModeDesc", 
					EnumUtil.translate(TxnEnums.DailyTransferMode.class, cmsp.getTransferMode(), false));
			
			result.add(m);
		}
		model.addAttribute("result", result);
		model.addAttribute("chnlId", chnlId);
		model.addAttribute("chnlMchntCd", chnlMchntCd);

	}
	
	@RequestMapping(value = "/settlePolicy/add.do", method = RequestMethod.GET)
	public String settlePolicyAdd(Model model, String chnlId, String chnlMchntCd) {
		AssertUtil.strIsBlank(chnlId, "chnlId is blank.");

		model.addAttribute("chnlId", chnlId);
		model.addAttribute("chnlMchntCd", chnlMchntCd);
		return RESULT_BASE_URI + "/settlePolicy_add";
	}

	@RequestMapping(value = "/settlePolicy/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult settlePolicyAddSubmit(Model model, ChnlMchntSettlePolicy cmsp) {
		
		AssertUtil.objIsNull(cmsp, "??????????????????null");

		logger.info("???????????????????????????????????????");
		IChnlMchntSettlePolicyService service = this.getDBService(IChnlMchntSettlePolicyService.class);
		
		ChnlMchntSettlePolicyKey key = new ChnlMchntSettlePolicyKey(cmsp.getChnlId(), cmsp.getChnlMchntCd(), cmsp.getCurrCd());
		ChnlMchntSettlePolicy dbEntity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNotNull(dbEntity,
				EnumUtil.translate(CurrencyEnums.CurrType.class, cmsp.getCurrCd(), false) + "????????????????????????");
	
		ChnlMchntSettlePolicy csp = new ChnlMchntSettlePolicy(cmsp.getChnlId(), cmsp.getChnlMchntCd(), cmsp.getCurrCd(), cmsp.getSettlePeriod(), cmsp.getTransferInterval(), cmsp.getTransferTime(), 
				cmsp.getTransferTimeT1(), cmsp.getBalanceTransfer(), cmsp.getBalanceTransferT1(), cmsp.getTransferMode(), cmsp.getPreTransferTimeT1(), cmsp.getPreTransferT1Percent(), cmsp.getComment(), this.getSessionUsrId(), new Date(), new Date());
		
		service.add(csp);
		this.logObj(BmEnums.FuncInfo._1100040000.getCode(), CommonEnums.OpType.ADD, csp);
		logger.info("???????????????????????????????????????");
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/settlePolicy/upd.do", method = RequestMethod.GET)
	public String settlePolicyUpd(Model model, String chnlId, String chnlMchntCd, String currCd) {

		AssertUtil.strIsBlank(chnlId, "chnlId is blank.");
		AssertUtil.strIsBlank(chnlMchntCd, "chnlMchntCd is blank.");
		AssertUtil.strIsBlank(currCd, "currCd is blank.");

		IChnlMchntSettlePolicyService service = this.getDBService(IChnlMchntSettlePolicyService.class);
		ChnlMchntSettlePolicyKey key = new ChnlMchntSettlePolicyKey(chnlId, chnlMchntCd, currCd);
		ChnlMchntSettlePolicy dbEntity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(dbEntity, "????????????????????????");
		
//		ChnlMchntSettlePolicy cmsp = new ChnlMchntSettlePolicy(dbEntity.getChnlId(), dbEntity.getChnlMchntCd(), dbEntity.getCurrCd(), dbEntity.getSettlePeriod(), dbEntity.getTransferInterval(), dbEntity.getTransferTime(), 
//				dbEntity.getTransferTimeT1(), dbEntity.getBalanceTransfer(), dbEntity.getBalanceTransferT1(), dbEntity.getTransferMode(), dbEntity.getPreTransferTimeT1(), dbEntity.getPreTransferT1Percent(), dbEntity.getComment(), 
//				dbEntity.getLastOperId(), dbEntity.getRecCrtTs(), dbEntity.getRecUpdTs());	

		model.addAttribute("entity", dbEntity);

		return RESULT_BASE_URI + "/settlePolicy_upd";
	}

	@RequestMapping(value = "/settlePolicy/upd/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult settlePolicyUpdSubmit(Model model, ChnlMchntSettlePolicy cmsp) {
		//this.checkFuncRight("1100040004");

		AssertUtil.objIsNull(cmsp, "??????????????????null");
	
		logger.info("???????????????????????????????????????");
		
		IChnlMchntSettlePolicyService service = this.getDBService(IChnlMchntSettlePolicyService.class);
		ChnlMchntSettlePolicyKey key = new ChnlMchntSettlePolicyKey(cmsp.getChnlId(), cmsp.getChnlMchntCd(), cmsp.getCurrCd());
		ChnlMchntSettlePolicy dbEntity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(dbEntity, "????????????????????????");
		
		ChnlMchntSettlePolicy cmspUpdate = new ChnlMchntSettlePolicy(cmsp.getChnlId(), cmsp.getChnlMchntCd(), cmsp.getCurrCd(), 
				cmsp.getSettlePeriod(), cmsp.getTransferInterval(), cmsp.getTransferTime(), 
				cmsp.getTransferTimeT1(), cmsp.getBalanceTransfer(), cmsp.getBalanceTransferT1(), cmsp.getTransferMode(), 
				cmsp.getPreTransferTimeT1(), cmsp.getPreTransferT1Percent(), cmsp.getComment(), 
				this.getSessionUsrId(), null, new Date());	

		service.update(cmspUpdate);

		this.logObj(BmEnums.FuncInfo._1100040000.getCode(), CommonEnums.OpType.UPDATE, cmspUpdate);
		logger.info("???????????????????????????????????????");
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/settlePolicy/delete.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult settlePolicyDelete(ChnlMchntSettlePolicyKey key) {

		IChnlMchntSettlePolicyService service = this.getDBService(IChnlMchntSettlePolicyService.class);
		ChnlMchntSettlePolicy entity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(entity, "???????????????");

		service.delete(key);
		// ???????????????
		this.logObj(BmEnums.FuncInfo._1100040000.getDesc(), CommonEnums.OpType.DELETE, entity);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/transParam/manage.do", method = RequestMethod.GET)
	public String algorithmMng(Model model, String chnlId, String chnlMchntCd) {
		//this.checkFuncRight("1100040004");
		
		logger.debug(String.format("[***][algorithmMng] /transParam/manage.do; chnlMchntCd=%s",chnlMchntCd));
		AssertUtil.strIsBlank(chnlId, "chnlId is blank.");
		this.buildTransParamLst(model, chnlId, chnlMchntCd);
		return RESULT_BASE_URI + "/transParam_mng";
	}

	private void buildTransParamLst(Model model, String chnlId, String chnlMchntCd) {
		IChnlMchntInfoSubService service = this.getDBService(IChnlMchntInfoSubService.class);
		List<ChnlMchntInfoSub> lst = service.select(chnlId, chnlMchntCd);
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		for (ChnlMchntInfoSub mps : lst) {
			Map<String, String> m = BeanUtil.desc(mps, null, null);
			m.put("intTransCdDesc", this.translate(mps.getIntTransCd()));
			
			m.put("currCd", EnumUtil.translate(BmEnums.CurrCdType.class, mps.getCurrCd(), true));
			
			Map<String, String> mp = JsonUtil.fromJson(mps.getSettleAlgorithm(), Map.class);
			mp.put("minFee", amtTranfer(mps.getCurrCd(), mp.get("minFee")));
			mp.put("fixRate", this.judegIsNull(mp.get("fixRate"), "0"));
			mp.put("maxFee", amtTranfer(mps.getCurrCd(), mp.get("maxFee")));
			JSONObject Json = (JSONObject) JSON.toJSON(mp);
			
			m.put("settleAlgorithmDesc",Json.toJSONString());
			
			m.put("txDayMaxDesc", amtTranfer(mps.getCurrCd(), mps.getTxDayMax()));
			m.put("txAmtMinDesc", amtTranfer(mps.getCurrCd(), mps.getTxAmtMin()));
			m.put("txAmtMaxDesc", amtTranfer(mps.getCurrCd(), mps.getTxAmtMax()));
			m.put("txTimeLimitDesc", mps.getTxTimeLimit());
			m.put("txCardDayMaxDesc", amtTranfer(mps.getCurrCd(), mps.getTxCardDayMax()));
			m.put("txT0PercentDesc", mps.getTxT0Percent());
			result.add(m);
		}
		model.addAttribute("result", result);
		model.addAttribute("chnlId", chnlId);
		model.addAttribute("chnlMchntCd", chnlMchntCd);

	}

	@RequestMapping(value = "/transParam/add.do", method = RequestMethod.GET)
	public String algorithmAdd(Model model, String chnlId, String chnlMchntCd) {
		//this.checkFuncRight("1100040004");
		AssertUtil.strIsBlank(chnlId, "chnlId is blank.");
		IChnlMchntInfoSubService service = this.getDBService(IChnlMchntInfoSubService.class);

		model.addAttribute("chnlId", chnlId);
		model.addAttribute("chnlMchntCd", chnlMchntCd);
		return RESULT_BASE_URI + "/transParam_add";
	}

	@RequestMapping(value = "/transParam/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult algorithmAddSubmit(Model model, ChnlMchntInfoSubMapping cbm) {
		//this.checkFuncRight("1100040004");
		
		AssertUtil.objIsNull(cbm, "??????????????????null");
		
        Map<String,String> eventParams = //????????????
        		Utils.newMap(
        				"requestUri" , "/transParam/add/submit.do" ,
        				INTER_MSG.channel, cbm.getChnlId(),
        				INTER_MSG.chnlMchntId, cbm.getChnlMchntCd(),
        				"intTxnType", cbm.getIntTransCd(), 
        				MSG.currencyCode, cbm.getCurrCd(),
        				INTER_MSG.userId, this.getSessionUsrId(),
        				INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp()
        				);
		
		BigDecimal txT0PercentBd;
		if (!cbm.getTxT0Percent().isEmpty()){
			txT0PercentBd = new BigDecimal(cbm.getTxT0Percent());
			if (txT0PercentBd.compareTo(new BigDecimal("1")) == 1) {
				throw new BizzException("??????????????????????????????1");
			}
		}

		logger.info("????????????????????????");
		IChnlMchntInfoSubService service = this.getDBService(IChnlMchntInfoSubService.class);
		ChnlMchntInfoSubKey key = new ChnlMchntInfoSubKey(cbm.getChnlId(), cbm.getChnlMchntCd(), cbm.getIntTransCd(), cbm.getCurrCd());
		ChnlMchntInfoSub dbEntity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNotNull(dbEntity,
				"????????????????????????????????????:" + EnumUtil.translate(SettleEnums.SettleTxnType.class, cbm.getIntTransCd(), true)
				+ "?????????:" + EnumUtil.translate(BmEnums.CurrCdType.class, cbm.getCurrCd(), true));
		Map<String, String> reqMap = new HashMap<String, String>();
		String settleAlgorithm = null;
		String txDayMax = this.judegIsNullMulUnit(cbm.getTxDayMax(), DFT_DAILY_LIMIT, cbm.getCurrCd());
		String txCardDayMax = this.judegIsNullMulUnit(cbm.getTxCardDayMax(), DFT_DAILY_LIMIT, cbm.getCurrCd());
		
		String txT0Percent=this.judegIsNull(cbm.getTxT0Percent(), "0");
		reqMap.put("minFee", this.judegIsNullMulUnit(cbm.getMinFee(), "0", cbm.getCurrCd()));
		reqMap.put("fixRate", this.judegIsNull(cbm.getFixRate(), "0"));
		reqMap.put("maxFee", this.judegIsNullMulUnit(cbm.getMaxFee(), DFT_DAILY_LIMIT, cbm.getCurrCd()));
		JSONObject Json = (JSONObject) JSON.toJSON(reqMap);
		settleAlgorithm = Json.toJSONString();
//		ChnlMchntInfoSub cb = new ChnlMchntInfoSub(cbm.getChnlId(), cbm.getChnlMchntCd(), cbm.getIntTransCd(), cbm.getCurrCd(),new BigDecimal("0.01"),
//				cbm.getSettleMode(), null, settleAlgorithm, txT0Percent, cbm.getTxTimeLimit(), this.judegIsNullMul100(cbm.getTxAmtMin(), "0"),
//				this.judegIsNullMul100(cbm.getTxAmtMax(), "0"), txDayMax, txCardDayMax, txT0Percent, txT0Percent, txT0Percent, txT0Percent, null, null);

		ChnlMchntInfoSub cb = new ChnlMchntInfoSub(cbm.getChnlId(), cbm.getChnlMchntCd(), cbm.getIntTransCd(), cbm.getCurrCd(), Amount.getDefaultUnit(cbm.getCurrCd()), cbm.getSettleMode(),
				settleAlgorithm, txT0Percent, cbm.getTxTimeLimit(), this.judegIsNullMulUnit(cbm.getTxAmtMin(), "0", cbm.getCurrCd()), this.judegIsNullMulUnit(cbm.getTxAmtMax(), "0", cbm.getCurrCd())
				, txDayMax, txCardDayMax, "", "",this.getSessionUsrId(), new Date(), new Date());
		
		//cb.setLastOperId(this.getSessionUsrId());
		
		service.add(cb);
		
		/* ????????????/??????/??????????????? */
		JSONObject resultJson = JSONObject.parseObject(JsonUtil.toJson(cb), Feature.OrderedField);
		Map<String, Object> extraParams = JSONUtils.parseJSON2Map(resultJson.toString());
		extraParams.remove("settleAlgorithm");
		extraParams.putAll(reqMap);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Chnl_Mchnt_TransParam_Add)
			.result(RISK.Result.Ok)
			.target(cbm.getChnlId()+"|"+ cbm.getChnlMchntCd()+ "|"+cbm.getIntTransCd()+"|"+EnumUtil.translate(BmEnums.CurrCdType.class, cbm.getCurrCd(), false))
			.message(String.format("????????? %s, ????????????????????????????????????%s, ?????????????????? %s, ??????????????? %s, ????????? %s", commonBO.getSessionUser().getUsrId(), cbm.getChnlId(), cbm.getChnlMchntCd(), cbm.getIntTransCd(), EnumUtil.translate(BmEnums.CurrCdType.class, cbm.getCurrCd(), false)))
			.params(eventParams)
			.params(extraParams)
			.submit();
		
		this.logObj(BmEnums.FuncInfo._1100040000.getCode(), CommonEnums.OpType.ADD, cb);
		logger.info("????????????????????????");
		return commonBO.buildSuccResp();
	}

	@RequestMapping(value = "/transParam/delete.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult transParamDelete(ChnlMchntInfoSubKey key) {
		//this.checkFuncRight("1100040004");
        
		IChnlMchntInfoSubService service = this.getDBService(IChnlMchntInfoSubService.class);
		ChnlMchntInfoSub entity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(entity, "???????????????");
		
        Map<String,String> eventParams = //????????????
        		Utils.newMap(
        				"requestUri" , "/transParam/delete.do" ,
        				INTER_MSG.channel, key.getChnlId(),
        				INTER_MSG.chnlMchntId, key.getChnlMchntCd(),
        				"intTxnType", key.getIntTransCd(), 
        				MSG.currencyCode, key.getCurrCd(),
        				INTER_MSG.userId, this.getSessionUsrId(),
        				INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp()
        				);
        
		// AssertUtil.strNotEquals(TxnEnums.VirtualTermStatus._0.getCode(),
		// entity.getState(), "??????????????????????????????");
		service.delete(key);
		
		/* ????????????/??????/??????????????? */
		JSONObject resultJson = JSONObject.parseObject(JsonUtil.toJson(entity), Feature.OrderedField);
		Map<String, Object> extraParams = JSONUtils.parseJSON2Map(resultJson.toString());
		extraParams.remove("settleAlgorithm");
		
		String settleAlgorithm = entity.getSettleAlgorithm();
		if (!StringUtil.isBlank(settleAlgorithm)) {
			JSONObject settleAlgorithmJson = JSONObject.parseObject(settleAlgorithm, Feature.OrderedField);
			Map<String, Object> settleAlgorithmParams = JSONUtils.parseJSON2Map(settleAlgorithmJson.toString());
			extraParams.putAll(settleAlgorithmParams);
		}
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Chnl_Mchnt_TransParam_Del)
			.result(RISK.Result.Ok)
			.target(key.getChnlId()+"|"+ key.getChnlMchntCd()+ "|"+key.getIntTransCd()+"|"+EnumUtil.translate(BmEnums.CurrCdType.class, key.getCurrCd(), false))
			.message(String.format("????????? %s, ????????????????????????????????????%s, ?????????????????? %s, ??????????????? %s, ????????? %s", commonBO.getSessionUser().getUsrId(), key.getChnlId(), key.getChnlMchntCd(), key.getIntTransCd(), EnumUtil.translate(BmEnums.CurrCdType.class, key.getCurrCd(), false)))
			.params(eventParams)
			.params(extraParams)
			.submit();
		
		// ???????????????
		this.logObj(BmEnums.FuncInfo._1100040000.getDesc(), CommonEnums.OpType.DELETE, entity);
		return commonBO.buildSuccResp();
	}

	@RequestMapping(value = "/transParam/upd.do", method = RequestMethod.GET)
	public String transParamUpd(Model model, String chnlId, String chnlMchntCd, String intTransCd, String currCd) {
		//this.checkFuncRight("1100040004");

		AssertUtil.strIsBlank(chnlId, "chnlId is blank.");
		AssertUtil.strIsBlank(chnlMchntCd, "chnlMchntCd is blank.");
		AssertUtil.strIsBlank(intTransCd, "intTransCd is blank.");
		AssertUtil.strIsBlank(currCd, "currCd is blank.");

		IChnlMchntInfoSubService service = this.getDBService(IChnlMchntInfoSubService.class);
		ChnlMchntInfoSubKey key = new ChnlMchntInfoSubKey(chnlId, chnlMchntCd, intTransCd, currCd);
		ChnlMchntInfoSub dbEntity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(dbEntity, "????????????????????????");

		ChnlMchntInfoSubMapping chm = new ChnlMchntInfoSubMapping(dbEntity.getChnlId(), dbEntity.getChnlMchntCd(),
				dbEntity.getSettleMode(), dbEntity.getIntTransCd(),
				amtTranfer(currCd, dbEntity.getTxDayMax()),
				amtTranfer(currCd, dbEntity.getTxAmtMin()),
				amtTranfer(currCd, dbEntity.getTxAmtMax()),
				dbEntity.getTxTimeLimit(),
				amtTranfer(currCd, dbEntity.getTxCardDayMax()),
				dbEntity.getTxT0Percent(), dbEntity.getCurrCd());

		Map<String, String> map = new HashMap<String, String>();
		map = JSONObject.parseObject(dbEntity.getSettleAlgorithm(), new TypeReference<Map<String, String>>() {
		});
		chm.setFixRate(map.get("fixRate"));
		chm.setMinFee(amtTranfer(currCd, map.get("minFee")));
		chm.setMaxFee(amtTranfer(currCd, map.get("maxFee")));
		model.addAttribute("entity", chm);

		return RESULT_BASE_URI + "/transParam_upd";
	}

	@RequestMapping(value = "/transParam/upd/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult transParamUpdSubmit(Model model, ChnlMchntInfoSubMapping chm) {
		//this.checkFuncRight("1100040004");

		AssertUtil.objIsNull(chm, "??????????????????null");
		
        Map<String,String> eventParams = //????????????
        		Utils.newMap(
        				"requestUri" , "/transParam/upd/submit.do" ,
        				INTER_MSG.channel, chm.getChnlId(),
        				INTER_MSG.chnlMchntId, chm.getChnlMchntCd(),
        				"intTxnType", chm.getIntTransCd(), 
        				MSG.currencyCode, chm.getCurrCd(),
        				INTER_MSG.userId, this.getSessionUsrId(),
        				INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp()
        				);
        
		BigDecimal txT0PercentBd;
		if (!chm.getTxT0Percent().isEmpty()){
			txT0PercentBd = new BigDecimal(chm.getTxT0Percent());
			if (txT0PercentBd.compareTo(new BigDecimal("1")) == 1) {
				throw new BizzException("??????????????????????????????1");
			}
		}

		logger.info("????????????????????????");
		IChnlMchntInfoSubService service = this.getDBService(IChnlMchntInfoSubService.class);
		ChnlMchntInfoSub cb = service.selectByPrimaryKey(
				new ChnlMchntInfoSubKey(chm.getChnlId(), chm.getChnlMchntCd(), chm.getIntTransCd(), chm.getCurrCd()));
		AssertUtil.objIsNull(cb, "????????????????????????");
		String settleAlgorithm = null;
		Map<String, String> reqMap = new HashMap<String, String>();
		String txDayMax = this.judegIsNullMulUnit(chm.getTxDayMax(), DFT_DAILY_LIMIT, chm.getCurrCd());
		String txCardDayMax = this.judegIsNullMulUnit(chm.getTxCardDayMax(), DFT_DAILY_LIMIT, chm.getCurrCd());
		String tTxT0Percent=this.judegIsNull(chm.getTxT0Percent(), "0");
		reqMap.put("minFee", this.judegIsNullMulUnit(chm.getMinFee(), "0", chm.getCurrCd()));
		reqMap.put("fixRate", this.judegIsNull(chm.getFixRate(), "0"));
		reqMap.put("maxFee", this.judegIsNullMulUnit(chm.getMaxFee(), DFT_DAILY_LIMIT, chm.getCurrCd()));
		JSONObject Json = (JSONObject) JSON.toJSON(reqMap);
		settleAlgorithm = Json.toJSONString();

		ChnlMchntInfoSub cb2 = new ChnlMchntInfoSub(chm.getChnlId(), chm.getChnlMchntCd(), chm.getIntTransCd(), 
				chm.getCurrCd(), Amount.getDefaultUnit(chm.getCurrCd()),
				chm.getSettleMode(), settleAlgorithm, tTxT0Percent, chm.getTxTimeLimit(),  
				this.judegIsNullMulUnit(chm.getTxAmtMin(), "0", chm.getCurrCd()),
				this.judegIsNullMulUnit(chm.getTxAmtMax(), "0", chm.getCurrCd()),
				txDayMax, txCardDayMax, null, null, this.getSessionUsrId(), null, null);

		service.update(cb2);
		
		/* ????????????/??????/??????????????? */
		JSONObject resultJson = JSONObject.parseObject(JsonUtil.toJson(cb2), Feature.OrderedField);
		Map<String, Object> extraParams = JSONUtils.parseJSON2Map(resultJson.toString());
		extraParams.remove("settleAlgorithm");
		extraParams.putAll(reqMap);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Chnl_Mchnt_TransParam_Modify)
			.result(RISK.Result.Ok)
			.target(chm.getChnlId()+"|"+ chm.getChnlMchntCd()+ "|"+chm.getIntTransCd()+"|"+EnumUtil.translate(BmEnums.CurrCdType.class, chm.getCurrCd(), false))
			.message(String.format("????????? %s, ????????????????????????????????????%s, ?????????????????? %s, ??????????????? %s, ????????? %s", commonBO.getSessionUser().getUsrId(), chm.getChnlId(), chm.getChnlMchntCd(), chm.getIntTransCd(), EnumUtil.translate(BmEnums.CurrCdType.class, chm.getCurrCd(), false)))
			.params(eventParams)
			.params(extraParams)
			.submit();
		

		this.logObj(BmEnums.FuncInfo._1100040000.getCode(), CommonEnums.OpType.UPDATE, cb);
		logger.info("????????????????????????");
		return commonBO.buildSuccResp();
	}

	// ----------------
	private String transSettlePeriod(String settlePeriod) {
//		String settlePeriodDesc = null;
//		if ("0".equals(settlePeriod)) {
//			settlePeriodDesc = "T0";
//		} else if ("1".equals(settlePeriod)) {
//			settlePeriodDesc = "T1";
//		} else if ("2".equals(settlePeriod)) {
//			settlePeriodDesc = "T2";
//		} else if ("3".equals(settlePeriod)) {
//			settlePeriodDesc = "T3";
//		} else if ("9".equals(settlePeriod)) {
//			settlePeriodDesc = "D0";
//		}
		String settlePeriodDesc = EnumUtil.translate(SettleEnums.SettlePeriod.class, settlePeriod, false);
		return settlePeriodDesc;
	}

	private String translate(String intTransCd) {
		String intTransCdDesc= EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, true);

		return intTransCdDesc;
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
