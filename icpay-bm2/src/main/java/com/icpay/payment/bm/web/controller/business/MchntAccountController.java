package com.icpay.payment.bm.web.controller.business;

import static com.icpay.payment.risk.BmUser.bmUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.cache.MchntInfoCache;
import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.constants.TxnConstants;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.constants.Names.MSG;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.AccEnums;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.Amount;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.Converter;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.BalanceTransferResult;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountFlow;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntObligatedFlow;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfoKey;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MerAccountInfoSummaryAddRealAavailable;
import com.icpay.payment.db.service.IBalanceTransferResultService;
import com.icpay.payment.db.service.IChnlMchntAccountFlowService;
import com.icpay.payment.db.service.IChnlMchntAccountInfoService;
import com.icpay.payment.db.service.IChnlMchntObligatedFlowService;
import com.icpay.payment.db.service.IMchntAccountService;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.ITransLogService;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;
import com.icpay.payment.service.ChnlMerAccService;

@Controller
@RequestMapping("/mchntAccout")
public class MchntAccountController extends BaseController {

	private static final Logger logger = Logger.getLogger(MchntAccountController.class);
	
	private static final String RESULT_BASE_URI = "mchntAccout";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			
			String mchntCd=m.get("mchntCd");
			
			Map<String,String> mchnt = MchntInfoCache.getInstance().get(mchntCd);
			if (!Utils.isEmpty(mchnt)) {
				String mchntDesc = mchnt.get("mchntCnNm");
				m.put("mchntDesc", mchntDesc);
			}
			
			
			String availableBalance = m.get("availableBalance");
			m.put("availableBalanceDesc", StringUtil.formateAmt(availableBalance));
			
			//20190703 ????????????????????????
			BigDecimal obligatedBalance = new BigDecimal(m.get("obligatedBalance"));
			m.put("obligatedBalanceDesc", obligatedBalance.movePointLeft(2).toString());
			
			BigDecimal availableBalanceBd = new BigDecimal(m.get("availableBalance"));
			BigDecimal realAvailableBalance  = availableBalanceBd.subtract(obligatedBalance);
			if(realAvailableBalance.compareTo(BigDecimal.ZERO) > 0) {
				m.put("realAvailableBalanceDesc", realAvailableBalance.movePointLeft(2).toString());
			} else {
				m.put("realAvailableBalanceDesc", "0.00");
			}
			
			
			String frozenBalance = m.get("frozenBalance");
			m.put("frozenBalanceDesc", StringUtil.formateAmt(frozenBalance));
			
			String frozenT1Balance = m.get("frozenT1Balance");
			m.put("frozenT1BalanceDesc", StringUtil.formateAmt(frozenT1Balance));
			
			String state = m.get("state");
			m.put("stateDesc", EnumUtil.translate(AccEnums.AccSt.class, state, true));
			
			String currCd = m.get("currCd");
			m.put("currCdDesc", EnumUtil.translate(BmEnums.CurrCdType.class, currCd, true));
			
			String daytxnamt = m.get("daytxnamt");
			m.put("daytxnamtDesc", StringUtil.formateAmt(daytxnamt));
			
			String daytxnfee = m.get("daytxnfee");
			m.put("daytxnfeeDesc", StringUtil.formateAmt(daytxnfee));
			
			String daywithdrawamt = m.get("daywithdrawamt");
			m.put("daywithdrawamtDesc", StringUtil.formateAmt(daywithdrawamt));
			
			String daywithdrawfee = m.get("daywithdrawfee");
			m.put("daywithdrawfeeDesc", StringUtil.formateAmt(daywithdrawfee));
			
			//??????
			String siteId = selectSiteIdByMchntCd(mchntCd);
			m.put("siteId", EnumUtil.translate(CommonEnums.Site.class, siteId, true));
		}
	};
	
	private static final IEntityTransfer ACCFLOW_ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			
			String currCd = m.get("currCd");
			m.put("currCd", EnumUtil.translate(BmEnums.CurrCdType.class, currCd, true));
			
			BigDecimal transAt = new BigDecimal(m.get("transAt"));
			m.put("transAtDesc", transAt.movePointLeft(2).toString());
			
			BigDecimal transFee = new BigDecimal(m.get("transFee"));
			m.put("transFeeDesc", transFee.movePointLeft(2).toString());
			
			BigDecimal availableBalance = new BigDecimal(m.get("availableBalance"));
			m.put("availableBalanceDesc", availableBalance.movePointLeft(2).toString());
			
			String frozenBalance = m.get("frozenBalance");
			m.put("frozenBalanceDesc", StringUtil.formateAmt(frozenBalance));
			
			String frozenT1Balance = m.get("frozenT1Balance");
			m.put("frozenT1BalanceDesc", StringUtil.formateAmt(frozenT1Balance));
			
			String daytxnamt = m.get("daytxnamt");
			m.put("daytxnamtDesc", StringUtil.formateAmt(daytxnamt));
			
			String daytxnfee = m.get("daytxnfee");
			m.put("daytxnfeeDesc", StringUtil.formateAmt(daytxnfee));
			
			String daywithdrawamt = m.get("daywithdrawamt");
			m.put("daywithdrawamtDesc", StringUtil.formateAmt(daywithdrawamt));
			
			String daywithdrawfee = m.get("daywithdrawfee");
			m.put("daywithdrawfeeDesc", StringUtil.formateAmt(daywithdrawfee));
			
			String operateTp = m.get("operateTp");
			m.put("operateTpDesc", EnumUtil.translate(AccEnums.AccOperTp.class, operateTp, true));
			
			String transDt = m.get("transDt");
			String transTm = m.get("transTm");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (!Utils.isEmpty(transDt)) {
				try {
					Date dt = Converter.stringToDateTime(transDt+transTm);
					transTm = sdf.format(dt);
				} catch (Exception e) {
					transTm = String.format("%s %s", transDt,transTm);
				}
				m.put("transTm", transTm);
			}
			
		}
	};
	
	private static final IEntityTransfer OBLIGATEDFLOW_ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String obligatedBalance = m.get("obligatedBalance");
			m.put("obligatedBalanceDesc", StringUtil.formateAmt(obligatedBalance));
		}
	};	
	
	private static final Map<String, String> ACC_ADJUST_OP_TP = new LinkedHashMap<String, String>();
	static {
		ACC_ADJUST_OP_TP.put(AccEnums.AccOperTp._40.getCode(), AccEnums.AccOperTp._40.getDesc());
		ACC_ADJUST_OP_TP.put(AccEnums.AccOperTp._41.getCode(), AccEnums.AccOperTp._41.getDesc());
		ACC_ADJUST_OP_TP.put(AccEnums.AccOperTp._42.getCode(), AccEnums.AccOperTp._42.getDesc());
		ACC_ADJUST_OP_TP.put(AccEnums.AccOperTp._43.getCode(), AccEnums.AccOperTp._43.getDesc());
		ACC_ADJUST_OP_TP.put(AccEnums.AccOperTp._45.getCode(), AccEnums.AccOperTp._45.getDesc());
		ACC_ADJUST_OP_TP.put(AccEnums.AccOperTp._46.getCode(), AccEnums.AccOperTp._46.getDesc());
		ACC_ADJUST_OP_TP.put(AccEnums.AccOperTp._51.getCode(), AccEnums.AccOperTp._51.getDesc());
		ACC_ADJUST_OP_TP.put(AccEnums.AccOperTp._52.getCode(), AccEnums.AccOperTp._52.getDesc());
	}

	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/backToManage.do", method = RequestMethod.GET)
	public String backToManage(Model model) {
		return super.toManage(model, true, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		String siteId = this.getQryParamMap().get("siteId");
		if (!Utils.isEmpty(siteId)) {
			IMchntInfoService service = this.getDBService(IMchntInfoService.class);
            Map<String, String> qryParamMap_siteId = new HashMap<>();
            qryParamMap_siteId.put("siteId", siteId);
            List<MchntInfo> select = service.select(qryParamMap_siteId);
            if (Utils.isEmpty(select)) {
            	throw new BizzException(String.format("%s????????????????????????????????????", siteId));
            }
		}
		IMchntAccountService service = this.getDBService(IMchntAccountService.class);
		Pager<MerAccountInfo> p = service.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_MERACCOUNTINFO, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/enable.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult enable(String mchntCd, String currCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		IMchntAccountService service = this.getDBService(IMchntAccountService.class);
		MerAccountInfoKey key = new MerAccountInfoKey();
		key.setMchntCd(mchntCd);
		key.setCurrCd(currCd);
		MerAccountInfo acc = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(acc, "?????????????????????");
		if (!AccEnums.AccSt.ST_1.getCode().equals(acc.getState())) {
			logger.info("????????????????????????:" + mchntCd);
			acc.setState(AccEnums.AccSt.ST_1.getCode());
			service.update(acc);
			this.logText(BmEnums.FuncInfo._1000070000.getCode(), CommonEnums.OpType.UPDATE, "??????????????????:" + mchntCd);
			logger.info("????????????????????????:" + mchntCd);
		} else {
			logger.info("??????????????????????????????:" + mchntCd);
		}
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/disable.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult disable(String mchntCd, String currCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		IMchntAccountService service = this.getDBService(IMchntAccountService.class);
		MerAccountInfoKey key = new MerAccountInfoKey();
		key.setMchntCd(mchntCd);
		key.setCurrCd(currCd);
		MerAccountInfo acc = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(acc, "?????????????????????");
		if (!AccEnums.AccSt.ST_0.getCode().equals(acc.getState())) {
			logger.info("????????????????????????:" + mchntCd);
			acc.setState(AccEnums.AccSt.ST_0.getCode());
			service.update(acc);
			this.logText(BmEnums.FuncInfo._1000070000.getCode(), CommonEnums.OpType.UPDATE, "??????????????????:" + mchntCd);
			logger.info("????????????????????????:" + mchntCd);
		} else {
			logger.info("??????????????????????????????:" + mchntCd);
		}
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/accountFlow.do", method = RequestMethod.GET)
	public String accountFlow(Model model, String mchntCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		model.addAttribute("mchntCd", mchntCd);
		model.addAttribute("today", DateUtil.now8());
		return RESULT_BASE_URI + "/accountFlow";
	}
	
	@RequestMapping(value = "/accountFlowQry.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult accountFlowQry(int pageNum, int pageSize, 
			String mchntCd, String transSeqId,String startDate,String endDate,String operateTp,String operateTpCat,
			String timeQryMethod, String note) {
//		if(recUpdTs==null || "".equals(recUpdTs)){ //TODO
//			recUpdTs=DateUtil.now8();
//		}
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		IChnlMchntAccountFlowService service = this.getDBService(IChnlMchntAccountFlowService.class);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("mchntCd", mchntCd);
//		qryParamMap.put("transDt", transDt);
//		qryParamMap.put("recUpdTs", recUpdTs);
//		qryParamMap.put("operateTp", operateTp);
		qryParamMap.put("startDate", startDate);
		qryParamMap.put("endDate", endDate);
		qryParamMap.put("operateTpCat", operateTpCat);
		qryParamMap.put("operateTp", operateTp);
		qryParamMap.put("timeQryMethod", timeQryMethod);
		qryParamMap.put("note", note);
		
		qryParamMap.put("transSeqId", transSeqId);
		qryParamMap.put("chnlId", "00");
		Pager<ChnlMchntAccountFlow> p = service.selectByPage(pageNum, pageSize, this.getMonth(startDate, endDate) , qryParamMap);
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_CHNLMCHNTACCOUNTFLOW2, ACCFLOW_ENTITY_TRANSFER));
	}
	
	/**
	 * ??????
	 */
	@RequestMapping(value = "/export.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult export(String mchntCd, String transSeqId, String startDate, String endDate,
			String operateTpCat,String operateTp,
			String timeQryMethod, String note,
			HttpServletResponse response) {

		// logger.debug(String.format("????????????????????????: startDate=%s, startTime=%s, endDate=%s,
		// endTime=%s, operateTp=%s, transSeqId=%s, transAt=%s", startDate, startTime,
		// endDate, endTime, operateTp, transSeqId, transAt));

		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("mchntCd", mchntCd);
		qryParamMap.put("startDate", nullIfEmpty(startDate));
		qryParamMap.put("endDate", nullIfEmpty(endDate));
		qryParamMap.put("operateTpCat", nullIfEmpty(operateTpCat));
		//qryParamMap.put("operateTp", nullIfEmpty(operateTp));
		
		qryParamMap.put("timeQryMethod", timeQryMethod);
		qryParamMap.put("note", note);

		qryParamMap.put("transSeqId", nullIfEmpty(transSeqId));
		qryParamMap.put("chnlId", "00");
		
		debug("????????????????????????: " + qryParamMap);

		String mon = this.getMonth(startDate, endDate);
		IChnlMchntAccountFlowService service = this.getDBService(IChnlMchntAccountFlowService.class);
		List<ChnlMchntAccountFlow> list = service.select(mon, qryParamMap);
		Pager<Map<String, String>> pager = commonBO.transferList(list, BMConstants.PAGE_CONF_CHNLMCHNTACCOUNTFLOW2, ACCFLOW_ENTITY_TRANSFER);
		
		String fileNm = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_ACCFLOW_EXPORT_FILENM);
		//String filePath = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_EXPORT_FILE_PATH);
		//commonBO.exportToExcel(pager,filePath, fileNm, Constant.UTF8, response);
		commonBO.exportToExcel(pager, fileNm, Constant.UTF8, response);

		return null;
	}
	
	@RequestMapping(value = "/accountFlowDetail.do", method = RequestMethod.GET)
	public String accountFlowDetail(Model model, long seqId,String recUpdTs) {
		IChnlMchntAccountFlowService service = this.getDBService(IChnlMchntAccountFlowService.class);
		ChnlMchntAccountFlow entity = service.selectByPrimaryKey(String.valueOf(seqId), recUpdTs.substring(5,7));
		AssertUtil.objIsNull(entity, "?????????????????????");
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_CHNLMCHNTACCOUNTFLOW));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_CHNLMCHNTACCOUNTFLOW2, ACCFLOW_ENTITY_TRANSFER));
		
		return RESULT_BASE_URI + "/accountFlowDetail";
	}
	
	@RequestMapping(value = "/accountAdjust.do", method = RequestMethod.GET)
	public String accountAdjust(Model model, String mchntCd, String currCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		IMchntAccountService service = this.getDBService(IMchntAccountService.class);
		MerAccountInfoKey key = new MerAccountInfoKey();
		key.setMchntCd(mchntCd);
		key.setCurrCd(currCd);
		MerAccountInfo acc = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(acc, "?????????????????????");
		AssertUtil.strNotEquals(acc.getState(), AccEnums.AccSt.ST_1.getCode(), "????????????????????????");
		model.addAttribute(BMConstants.ENTITY_RESULT, 
				commonBO.transferEntity(acc, BMConstants.PAGE_CONF_MERACCOUNTINFO, ENTITY_TRANSFER));
		model.addAttribute("accOpTp", ACC_ADJUST_OP_TP);
		return RESULT_BASE_URI + "/accountAdjust";
	}
	
	@RequestMapping(value = "/accountAdjustSubmit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult accountAdjustSubmit(Model model, 
			String mchntCd, String opTp, String txnAmt, String note, String currCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		AssertUtil.strIsBlank(opTp, "opTp is blank.");
		AssertUtil.strIsBlank(txnAmt, "txnAmt is blank.");
		AssertUtil.strIsBlank(note, "note is blank.");
		
        Map<String,String> eventParams = //????????????
        		Utils.newMap(
        				INTER_MSG.requestUri , "/accountAdjustSubmit.do" ,
        				MSG.merId, mchntCd,
        				INTER_MSG.adjOpType, opTp,
        				MSG.txnAmt, txnAmt, //???
        				MSG.currencyCode, currCd,
        				INTER_MSG.opNote, note,
        				MSG.userId, this.getSessionUsrId(),
        				INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp()
        				);
		
		IMchntAccountService service = this.getDBService(IMchntAccountService.class);
		MerAccountInfoKey key = new MerAccountInfoKey();
		key.setMchntCd(mchntCd);
		key.setCurrCd(currCd);
		MerAccountInfo acc = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(acc, "?????????????????????");
		AssertUtil.strNotEquals(acc.getState(), AccEnums.AccSt.ST_1.getCode(), "????????????????????????");
		
		if (!ACC_ADJUST_OP_TP.containsKey(opTp)) {
			throw new BizzException("??????????????????:" + opTp);
		}
		
		BigDecimal txnAmtBd = (new BigDecimal(txnAmt)).movePointRight(2);
		if (txnAmtBd.compareTo(BigDecimal.ZERO) <= 0) {
			throw new BizzException("???????????????????????????");
		}
		BigDecimal availableBalance = new BigDecimal(acc.getAvailableBalance());
		BigDecimal frozenBalance = new BigDecimal(acc.getFrozenBalance());
		BigDecimal checkAmt = BigDecimal.ZERO;
		//MerAccService accService = ServiceProxy.getService(MerAccService.class);
//		Map<String,String> req = new HashMap<String, String>();
//		req.put(Constant.MSG.merId, mchntCd);
//		req.put(Constant.MSG.txnAmt, txnAmtBd.toString());
//		req.put(Constant.INTER_MSG.accOperType, opTp);
//		req.put(Constant.INTER_MSG.operId, this.getSessionUsrId());
//		req.put(Constant.INTER_MSG.note, note);
//		req.put(Constant.MSG.queryId, "");
		Map<String, String> qryParam = new HashMap<String, String>();
		ITransLogService iTransLogService = this.getDBService(ITransLogService.class);
		
		qryParam.put("intTransCd", "52%");
		qryParam.put("mchntCd", mchntCd);
		qryParam.put("txnState", "01");
		
		//??????????????????????????????(???????????????)?????????
		checkAmt = iTransLogService.checkAmount(qryParam);
		
		if (AccEnums.AccOperTp._40.getCode().equals(opTp)) {
			// ???????????????????????????????????????????????????????????????
			if (txnAmtBd.compareTo(availableBalance) > 0) {
				throw new BizzException("??????????????????????????????????????????");
			}
		} else if (AccEnums.AccOperTp._41.getCode().equals(opTp)) {
			// ???????????????????????????????????????????????????????????????????????????
			if (txnAmtBd.compareTo(frozenBalance) > 0) {
				throw new BizzException("??????????????????????????????????????????");
			}
			//????????????????????????(???????????????)?????????????????? : XXXX (??????????????????????????????)
			if (frozenBalance.subtract(txnAmtBd).compareTo(checkAmt) < 0) {
				throw new BizzException("????????????????????????????????????(???????????????)???????????? :" + checkAmt.movePointLeft(2));
			}
			
			
		} else if (AccEnums.AccOperTp._42.getCode().equals(opTp)) {
			// ???????????????????????????????????????????????????????????????
			if (txnAmtBd.compareTo(frozenBalance) > 0) {
				throw new BizzException("??????????????????????????????????????????");
			}
			//???????????????????????????(???????????????)?????????????????? : XXXX
			if (frozenBalance.subtract(txnAmtBd).compareTo(checkAmt) < 0) {
				throw new BizzException("????????????????????????????????????(???????????????)????????????:" + checkAmt.movePointLeft(2));
			}
		}
		
		BigDecimal currUnit = Amount.getDefaultUnit(currCd);
		
		//this.chnlMerAccService().adjust(chnlId, mchntCd,  opTp,txnAmtBd.toString(), this.getSessionUsrId());
		this.adjust("00", mchntCd, opTp, txnAmtBd.toString(), currCd, currUnit,this.getSessionUsrId(), note);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Mchnt_Account_Adjust)
			.result(RISK.Result.Ok)
			.target(mchntCd+"|"+EnumUtil.translate(BmEnums.AccOperTp.class, opTp, false)+"|"+txnAmt+"|"+EnumUtil.translate(BmEnums.CurrCdType.class, currCd, false)+"|"+note)
			.message(String.format("????????? %s, ??????????????????????????????????????? %s, ??????????????? %s, ????????? %s, ????????? %s, ????????? %s", commonBO.getSessionUser().getUsrId(), mchntCd, EnumUtil.translate(BmEnums.AccOperTp.class, opTp, false), txnAmt, EnumUtil.translate(BmEnums.CurrCdType.class, currCd, false), note))
			.params(eventParams)
			.submit();
		
		//TODO: ????????????????????????
		
		if(opTp.equals(Constant.OPERTYPE._51)|| opTp.equals(Constant.OPERTYPE._52)) {
			//????????????????????????
			IBalanceTransferResultService svc = this.getDBService(IBalanceTransferResultService.class);
			Map <String,String> qryParamMap = new HashMap<String,String>();
			String transferDt =DateUtil.now8();
			qryParamMap.put("transChnl", "00");
			qryParamMap.put("mchntCd", mchntCd);
			qryParamMap.put("currCd", currCd);
			qryParamMap.put("transferAt", txnAmtBd.toString());
			qryParamMap.put("transferDt", transferDt);
			Long count =svc.count(qryParamMap);
			BalanceTransferResult transferInfo = new BalanceTransferResult();
			transferInfo.setTransChnl("00");
			transferInfo.setMchntCd(mchntCd);
			transferInfo.setTransferAt(txnAmtBd.longValue());
			transferInfo.setTransferDt(transferDt);
			transferInfo.setCurrCd(currCd);
			transferInfo.setUnit(currUnit);
			transferInfo.setTransferResult(TxnConstants.ONE);//??????
			if(count == 0) {
				svc.add(transferInfo);
			}else {
				svc.update(transferInfo);
			}
		}
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/amtSum.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody JSONObject amtSum(Model model, String mon) {
		model.addAttribute("qryParamMap", this.getQryParamMap());
		return summary(this.getQryParamMap(), mon);
	}
	
	ChnlMerAccService cmas=null;
	protected ChnlMerAccService chnlMerAccService(){
		if(cmas==null){
			cmas=ServiceProxy.getService(ChnlMerAccService.class);
		}
		return cmas;
	}
	
	
//	private void adjust(String chnlId, String chnlMchntCd,String opTp, String txnAmtBd,  String sessionUsrId) {
//		this.chnlMerAccService().adjust(chnlId, chnlMchntCd, opTp, txnAmtBd, sessionUsrId);
//	}
	/**
	 * ????????????(?????????,T1??????????????????)?????????????????????????????????????????? 
	 * @param chnlId ??????????????????????????????????????????"00"
	 * @param merId ?????????
	 * @param opType ????????? {@link com.icpay.payment.common.constants.Constant.OPERTYPE OPERTYPE} ??? {@link com.icpay.payment.common.enums.AccEnums.AccOperTp AccEnums.AccOperTp.getCode()} ?????????
	 * @param amount ????????????(????????????)
	 * @param operator ?????????
	 */
	void adjust(String chnlId, String merId, String opType, String amount, String currCd, BigDecimal unit, String operator, String note) {
		logger.info(String.format("[????????????] adjust(%s,%s,%s,%s,%s,%s,%s,%s)", chnlId, merId, opType, amount, currCd, unit, operator, note));
		this.chnlMerAccService().adjust(chnlId, merId, opType, amount, currCd, unit, operator, note);
	}
	/**
	 * yyyy-MM-dd HH:ss:mm-->yyyymmdd
	 */
	private static String getTransFormDate(Date date) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sim.format(date);
		return time.substring(0, time.indexOf(" ")).replaceAll("-", "");
	}
	
	public JSONObject summary(Map<String, String> qryParamMap, String mon) {
		this.debug("[IMchntAccountService][summary] qryParamMap: %s", qryParamMap);
		IMchntAccountService svc = this.getDBService(IMchntAccountService.class);
		MerAccountInfoSummaryAddRealAavailable sum = svc.selectInfoSummary(qryParamMap);
		Map<String, Object> map = new HashMap<String, Object>();
		if (sum != null) {
			map.put("sumAvailableBalance", StringUtil.formateAmt(sum.getSumAvailableBalance()));//???????????????
			map.put("sumRealAvailableBalance", StringUtil.formateAmt(sum.getSumRealAvailableBalance()));//?????????????????????
			map.put("sumFrozenBalance", StringUtil.formateAmt(sum.getSumFrozenBalance()));//????????????
			map.put("sumFrozenT1Balance", StringUtil.formateAmt(sum.getSumFrozenT1Balance()));//T1????????????
			map.put("sumDayTxnAmt", StringUtil.formateAmt(sum.getSumDayTxnAmt()));//????????????????????????
			map.put("sumDayWithdrawAmt", StringUtil.formateAmt(sum.getSumDayWithdrawAmt()));//????????????????????????
		}
		JSONObject result = (JSONObject) JSON.toJSON(map);
		return result;
	}
	
	@RequestMapping(value = "/obligatedBalanceModify.goto.do", method = RequestMethod.GET)
	public String gotoObligatedBalanceModify(Model model,String mchntCd, String currCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		IMchntAccountService service = this.getDBService(IMchntAccountService.class);
		MerAccountInfoKey key = new MerAccountInfoKey();
		key.setMchntCd(mchntCd);
		key.setCurrCd(currCd);
		MerAccountInfo acc = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(acc, "?????????????????????");
		AssertUtil.strNotEquals(acc.getState(), AccEnums.AccSt.ST_1.getCode(), "????????????????????????");
		model.addAttribute(BMConstants.ENTITY_RESULT, 
				commonBO.transferEntity(acc, BMConstants.PAGE_CONF_MERACCOUNTINFO, ENTITY_TRANSFER));
		return RESULT_BASE_URI + "/obligateAccModify";
	}
	
	@RequestMapping(value = "/obligatedBalanceModify.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult obligatedBalanceModify(Model model,String mchntCd, String obligatedBalance, String note, String currCd) {
		
        Map<String,String> eventParams = //????????????
        		Utils.newMap(
        				INTER_MSG.requestUri , "/obligatedBalanceModify.do" ,
        				MSG.merId, mchntCd,
        				"obligatedBalance", obligatedBalance, //???
        				MSG.currencyCode, currCd,
        				INTER_MSG.opNote, note,
        				MSG.userId, this.getSessionUsrId(),
        				INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp()
        				);
        
		BigDecimal obligatedBalanceBd = (new BigDecimal(obligatedBalance)).movePointRight(2);
		if (obligatedBalanceBd.compareTo(BigDecimal.ZERO) < 0) {
			throw new BizzException("???????????????????????????");
		}
		IMchntAccountService service = this.getDBService(IMchntAccountService.class);
		MerAccountInfoKey key = new MerAccountInfoKey();
		key.setMchntCd(mchntCd);
		key.setCurrCd(currCd);
		MerAccountInfo entity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(entity, "?????????????????????");
		BigDecimal oldObligatedBalance = new BigDecimal(entity.getObligatedBalance());
		entity.setObligatedBalance(obligatedBalanceBd.longValue());
		service.update(entity);
		
		// ????????????????????????
		IChnlMchntObligatedFlowService cmofs = this.getDBService(IChnlMchntObligatedFlowService.class);
		ChnlMchntObligatedFlow chnlMchntObligatedFlow = new ChnlMchntObligatedFlow();
		chnlMchntObligatedFlow.setChnlId("00");
		chnlMchntObligatedFlow.setMchntCd(mchntCd);
		chnlMchntObligatedFlow.setObligatedBalance(obligatedBalanceBd.longValue());
		chnlMchntObligatedFlow.setNote("????????????: " + oldObligatedBalance.movePointLeft(2) + " --> " + 
										obligatedBalanceBd.movePointLeft(2) + "?????????: " + EnumUtil.translate(BmEnums.CurrCdType.class, currCd, false) + "???" + note);
		chnlMchntObligatedFlow.setLastOperId(this.getSessionUsrId());
		chnlMchntObligatedFlow.setRecCrtTs(new Date());
		chnlMchntObligatedFlow.setRecUpdTs(new Date());
		cmofs.add(chnlMchntObligatedFlow);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Mchnt_Account_Adjust_Ob)
			.result(RISK.Result.Ok)
			.target(mchntCd+"|"+obligatedBalance+"|"+EnumUtil.translate(BmEnums.CurrCdType.class, currCd, false)+"|"+note)
			.message(String.format("????????? %s, ????????????????????????????????????????????? %s, ????????? %s, ????????? %s, ????????? %s", commonBO.getSessionUser().getUsrId(), mchntCd, obligatedBalance, EnumUtil.translate(BmEnums.CurrCdType.class, currCd, false), note))
			.params(eventParams)
			.submit();
		
		return commonBO.buildSuccResp();
	}
	
	
	@RequestMapping(value = "/existChnlMchnt.do", method = RequestMethod.POST)
	@QryMethod
	public void existChnlMchnt(HttpServletResponse response,String chnlMchntCd) {
		String[] split = chnlMchntCd.split(",");
		String result="";
		IChnlMchntAccountInfoService chnlService = this.getDBService(IChnlMchntAccountInfoService.class);
		for (String string : split) {
			Map<String, String> qryParamMap=new HashMap<>();
			qryParamMap.put("mchntCd", string);
			Pager<ChnlMchntAccountInfo> selectByPage = chnlService.selectByPage(1, 50, qryParamMap);
			if(selectByPage.getResultList().size()==0) {
				result+=string+"	,";
			} 
			
			if(!"".equals(result)) {
				result=result.substring(0,result.indexOf(","));
			}
		}
		try {
			PrintWriter writer = response.getWriter();
			writer.write(result);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value = "/obligatedFlow.do", method = RequestMethod.GET)
	public String obligatedFlow(Model model, String mchntCd) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		model.addAttribute("mchntCd", mchntCd);
		model.addAttribute("today", DateUtil.now8());
		return RESULT_BASE_URI + "/obligatedFlow";
	}
	
	@RequestMapping(value = "/obligatedFlowQry.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult obligatedFlowQry(int pageNum, int pageSize, String mchntCd, String startDate, String endDate, String timeQryMethod) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		IChnlMchntObligatedFlowService service = this.getDBService(IChnlMchntObligatedFlowService.class);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("chnlId", "00");
		qryParamMap.put("mchntCd", mchntCd);
		qryParamMap.put("startDate", startDate);
		qryParamMap.put("endDate", endDate);
		qryParamMap.put("timeQryMethod", timeQryMethod);
		Pager<ChnlMchntObligatedFlow> p = service.selectByPage(pageNum, pageSize, qryParamMap);
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_CHNLMCHNTOBLIGATEDFLOW2, OBLIGATEDFLOW_ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/obligatedFlowDetail.do", method = RequestMethod.GET)
	public String obligatedFlowDetail(Model model, long seqId) {
		IChnlMchntObligatedFlowService service = this.getDBService(IChnlMchntObligatedFlowService.class);
		ChnlMchntObligatedFlow entity = service.selectByPrimaryKey(String.valueOf(seqId));
		AssertUtil.objIsNull(entity, "?????????????????????");
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_CHNLMCHNTOBLIGATEDFLOW));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_CHNLMCHNTOBLIGATEDFLOW2, OBLIGATEDFLOW_ENTITY_TRANSFER));
		
		return RESULT_BASE_URI + "/obligatedFlowDetail";
	}
	
	@RequestMapping(value = "/flowNoteEdit.goto.do", method = RequestMethod.GET)
	public String gotoFlowNoteEdit(Model model, long seqId, String recUpdTs) {
		IChnlMchntAccountFlowService service = this.getDBService(IChnlMchntAccountFlowService.class);
		ChnlMchntAccountFlow entity = service.selectByPrimaryKey(String.valueOf(seqId), recUpdTs.substring(5,7));
		AssertUtil.objIsNull(entity, "?????????????????????");		
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_CHNLMCHNTACCOUNTFLOW2, ACCFLOW_ENTITY_TRANSFER));
		
		return RESULT_BASE_URI + "/flowNoteEdit";
	}
	
	@RequestMapping(value = "/flowNoteEdit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult flowNoteEdit(Model model, long seqId, String recUpdTs, String note) {
		IChnlMchntAccountFlowService service = this.getDBService(IChnlMchntAccountFlowService.class);
		ChnlMchntAccountFlow entity = service.selectByPrimaryKey(String.valueOf(seqId), recUpdTs.substring(5,7));
		entity.setNote(note);
		entity.setLastOperId(this.getSessionUsrId());
		service.update(entity);
		this.logObj(BmEnums.FuncInfo._1000070000.getDesc(), CommonEnums.OpType.UPDATE, "????????????:" + seqId + ", ???????????????" + note + ", ?????????:" + this.getSessionUsrId());
		return commonBO.buildSuccResp();
	}
	
	private static String selectSiteIdByMchntCd(String mchntCd) {
		String siteId = "";
		IMchntInfoService service = DBHessionServiceClient.getService(IMchntInfoService.class);
		MchntInfo mchntInfo = service.selectByPrimaryKey(mchntCd);
		if (!Utils.isEmpty(mchntInfo)) {
			siteId = mchntInfo.getSiteId();
		}
		return siteId;
	}
}
