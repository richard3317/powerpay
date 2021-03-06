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
			
			//20190703 新增保留余额字段
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
			
			//站點
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
            	throw new BizzException(String.format("%s站点查无资料，请重新选择", siteId));
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
		AssertUtil.objIsNull(acc, "未找到商户账户");
		if (!AccEnums.AccSt.ST_1.getCode().equals(acc.getState())) {
			logger.info("启用商户账户开始:" + mchntCd);
			acc.setState(AccEnums.AccSt.ST_1.getCode());
			service.update(acc);
			this.logText(BmEnums.FuncInfo._1000070000.getCode(), CommonEnums.OpType.UPDATE, "启用商户账户:" + mchntCd);
			logger.info("启用商户账户完成:" + mchntCd);
		} else {
			logger.info("该商户已经为启用状态:" + mchntCd);
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
		AssertUtil.objIsNull(acc, "未找到商户账户");
		if (!AccEnums.AccSt.ST_0.getCode().equals(acc.getState())) {
			logger.info("禁用商户账户开始:" + mchntCd);
			acc.setState(AccEnums.AccSt.ST_0.getCode());
			service.update(acc);
			this.logText(BmEnums.FuncInfo._1000070000.getCode(), CommonEnums.OpType.UPDATE, "禁用商户账户:" + mchntCd);
			logger.info("禁用商户账户完成:" + mchntCd);
		} else {
			logger.info("该商户已经为禁用状态:" + mchntCd);
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
	 * 导出
	 */
	@RequestMapping(value = "/export.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult export(String mchntCd, String transSeqId, String startDate, String endDate,
			String operateTpCat,String operateTp,
			String timeQryMethod, String note,
			HttpServletResponse response) {

		// logger.debug(String.format("导出商户账户流水: startDate=%s, startTime=%s, endDate=%s,
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
		
		debug("商户账户流水导出: " + qryParamMap);

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
		AssertUtil.objIsNull(entity, "未找到账户流水");
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
		AssertUtil.objIsNull(acc, "未找到商户账户");
		AssertUtil.strNotEquals(acc.getState(), AccEnums.AccSt.ST_1.getCode(), "商户账户状态无效");
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
		
        Map<String,String> eventParams = //其他资讯
        		Utils.newMap(
        				INTER_MSG.requestUri , "/accountAdjustSubmit.do" ,
        				MSG.merId, mchntCd,
        				INTER_MSG.adjOpType, opTp,
        				MSG.txnAmt, txnAmt, //元
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
		AssertUtil.objIsNull(acc, "未找到商户账户");
		AssertUtil.strNotEquals(acc.getState(), AccEnums.AccSt.ST_1.getCode(), "商户账户状态无效");
		
		if (!ACC_ADJUST_OP_TP.containsKey(opTp)) {
			throw new BizzException("无效操作类型:" + opTp);
		}
		
		BigDecimal txnAmtBd = (new BigDecimal(txnAmt)).movePointRight(2);
		if (txnAmtBd.compareTo(BigDecimal.ZERO) <= 0) {
			throw new BizzException("交易金额请输入正值");
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
		
		//返回当月、前月份代付(狀態处理中)的金额
		checkAmt = iTransLogService.checkAmount(qryParam);
		
		if (AccEnums.AccOperTp._40.getCode().equals(opTp)) {
			// 如果是冻结操作，则输入金额不能超过可用金额
			if (txnAmtBd.compareTo(availableBalance) > 0) {
				throw new BizzException("交易金额不能超过账户可用金额");
			}
		} else if (AccEnums.AccOperTp._41.getCode().equals(opTp)) {
			// 如果是扣除冻结金额操作，则输入金额不能超过冻结金额
			if (txnAmtBd.compareTo(frozenBalance) > 0) {
				throw new BizzException("交易金额不能超过账户冻结金额");
			}
			//冻结金额不能小于(狀態處理中)代付的總金額 : XXXX (冻结金额剪掉调整金额)
			if (frozenBalance.subtract(txnAmtBd).compareTo(checkAmt) < 0) {
				throw new BizzException("剩余冻结金额不能小于代付(状态处理中)的总金额 :" + checkAmt.movePointLeft(2));
			}
			
			
		} else if (AccEnums.AccOperTp._42.getCode().equals(opTp)) {
			// 如果是解冻操作，则输入金额不能超过冻结金额
			if (txnAmtBd.compareTo(frozenBalance) > 0) {
				throw new BizzException("交易金额不能超过账户冻结金额");
			}
			//調整的金額不能大於(狀態處理中)代付的總金額 : XXXX
			if (frozenBalance.subtract(txnAmtBd).compareTo(checkAmt) < 0) {
				throw new BizzException("剩余冻结金额不能小于代付(状态处理中)的总金额:" + checkAmt.movePointLeft(2));
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
			.message(String.format("用户： %s, 商户账户调帐成功。商户号： %s, 操作类型： %s, 金额： %s, 币别： %s, 原因： %s", commonBO.getSessionUser().getUsrId(), mchntCd, EnumUtil.translate(BmEnums.AccOperTp.class, opTp, false), txnAmt, EnumUtil.translate(BmEnums.CurrCdType.class, currCd, false), note))
			.params(eventParams)
			.submit();
		
		//TODO: 修改為多幣別版本
		
		if(opTp.equals(Constant.OPERTYPE._51)|| opTp.equals(Constant.OPERTYPE._52)) {
			//查询是否存在记录
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
			transferInfo.setTransferResult(TxnConstants.ONE);//成功
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
	 * 调整金额(含馀额,T1馀额及冻结款)，允许网页操作仅允许部分操作 
	 * @param chnlId 渠道编号，若为前端商户号请代"00"
	 * @param merId 商户号
	 * @param opType 必须是 {@link com.icpay.payment.common.constants.Constant.OPERTYPE OPERTYPE} 或 {@link com.icpay.payment.common.enums.AccEnums.AccOperTp AccEnums.AccOperTp.getCode()} 的值。
	 * @param amount 操作金额(单位为分)
	 * @param operator 操作者
	 */
	void adjust(String chnlId, String merId, String opType, String amount, String currCd, BigDecimal unit, String operator, String note) {
		logger.info(String.format("[帐户调整] adjust(%s,%s,%s,%s,%s,%s,%s,%s)", chnlId, merId, opType, amount, currCd, unit, operator, note));
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
			map.put("sumAvailableBalance", StringUtil.formateAmt(sum.getSumAvailableBalance()));//可代付总额
			map.put("sumRealAvailableBalance", StringUtil.formateAmt(sum.getSumRealAvailableBalance()));//实际可代付总额
			map.put("sumFrozenBalance", StringUtil.formateAmt(sum.getSumFrozenBalance()));//冻结总额
			map.put("sumFrozenT1Balance", StringUtil.formateAmt(sum.getSumFrozenT1Balance()));//T1帐户总额
			map.put("sumDayTxnAmt", StringUtil.formateAmt(sum.getSumDayTxnAmt()));//当日累计交易总额
			map.put("sumDayWithdrawAmt", StringUtil.formateAmt(sum.getSumDayWithdrawAmt()));//当日累计提现总额
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
		AssertUtil.objIsNull(acc, "未找到商户账户");
		AssertUtil.strNotEquals(acc.getState(), AccEnums.AccSt.ST_1.getCode(), "商户账户状态无效");
		model.addAttribute(BMConstants.ENTITY_RESULT, 
				commonBO.transferEntity(acc, BMConstants.PAGE_CONF_MERACCOUNTINFO, ENTITY_TRANSFER));
		return RESULT_BASE_URI + "/obligateAccModify";
	}
	
	@RequestMapping(value = "/obligatedBalanceModify.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult obligatedBalanceModify(Model model,String mchntCd, String obligatedBalance, String note, String currCd) {
		
        Map<String,String> eventParams = //其他资讯
        		Utils.newMap(
        				INTER_MSG.requestUri , "/obligatedBalanceModify.do" ,
        				MSG.merId, mchntCd,
        				"obligatedBalance", obligatedBalance, //元
        				MSG.currencyCode, currCd,
        				INTER_MSG.opNote, note,
        				MSG.userId, this.getSessionUsrId(),
        				INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp()
        				);
        
		BigDecimal obligatedBalanceBd = (new BigDecimal(obligatedBalance)).movePointRight(2);
		if (obligatedBalanceBd.compareTo(BigDecimal.ZERO) < 0) {
			throw new BizzException("保留余额请输入正值");
		}
		IMchntAccountService service = this.getDBService(IMchntAccountService.class);
		MerAccountInfoKey key = new MerAccountInfoKey();
		key.setMchntCd(mchntCd);
		key.setCurrCd(currCd);
		MerAccountInfo entity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(entity, "未找到账户信息");
		BigDecimal oldObligatedBalance = new BigDecimal(entity.getObligatedBalance());
		entity.setObligatedBalance(obligatedBalanceBd.longValue());
		service.update(entity);
		
		// 写入保留余额记录
		IChnlMchntObligatedFlowService cmofs = this.getDBService(IChnlMchntObligatedFlowService.class);
		ChnlMchntObligatedFlow chnlMchntObligatedFlow = new ChnlMchntObligatedFlow();
		chnlMchntObligatedFlow.setChnlId("00");
		chnlMchntObligatedFlow.setMchntCd(mchntCd);
		chnlMchntObligatedFlow.setObligatedBalance(obligatedBalanceBd.longValue());
		chnlMchntObligatedFlow.setNote("保留余额: " + oldObligatedBalance.movePointLeft(2) + " --> " + 
										obligatedBalanceBd.movePointLeft(2) + "，币别: " + EnumUtil.translate(BmEnums.CurrCdType.class, currCd, false) + "。" + note);
		chnlMchntObligatedFlow.setLastOperId(this.getSessionUsrId());
		chnlMchntObligatedFlow.setRecCrtTs(new Date());
		chnlMchntObligatedFlow.setRecUpdTs(new Date());
		cmofs.add(chnlMchntObligatedFlow);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Mchnt_Account_Adjust_Ob)
			.result(RISK.Result.Ok)
			.target(mchntCd+"|"+obligatedBalance+"|"+EnumUtil.translate(BmEnums.CurrCdType.class, currCd, false)+"|"+note)
			.message(String.format("用户： %s, 商户账户调整保留余额。商户号： %s, 金额： %s, 币别： %s, 备注： %s", commonBO.getSessionUser().getUsrId(), mchntCd, obligatedBalance, EnumUtil.translate(BmEnums.CurrCdType.class, currCd, false), note))
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
		AssertUtil.objIsNull(entity, "未找到账户流水");
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
		AssertUtil.objIsNull(entity, "未找到账户流水");		
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
		this.logObj(BmEnums.FuncInfo._1000070000.getDesc(), CommonEnums.OpType.UPDATE, "流水序号:" + seqId + ", 备注修改：" + note + ", 操作员:" + this.getSessionUsrId());
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
