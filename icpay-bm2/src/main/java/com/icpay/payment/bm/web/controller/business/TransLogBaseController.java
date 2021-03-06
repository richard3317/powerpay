package com.icpay.payment.bm.web.controller.business;

import static com.icpay.payment.risk.BmUser.bmUser;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.cache.ParamCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.util.DateUtils;
import com.icpay.payment.bm.web.util.LogCompareTool;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.constants.Constant.OPERTYPE;
import com.icpay.payment.common.constants.Constant.TXNS_TATUS;
import com.icpay.payment.common.constants.Constant.TxnSource;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.constants.Names.TAG;
import com.icpay.payment.common.constants.RspCd;
import com.icpay.payment.common.constants.TxnStateEnums;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums.OrderState;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.enums.TxnEnums.TxnStatus;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.exception.SystemException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.TagUtils;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.TransLog;
import com.icpay.payment.db.dao.mybatis.model.TxnLogView;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TxnLogSummary;
import com.icpay.payment.db.service.ITransLogService;
import com.icpay.payment.db.service.ITxnLogViewService;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;
import com.icpay.payment.service.ChnlMerAccService;
import com.icpay.payment.service.api.HttpClientResponse;
import com.icpay.payment.service.client.InternalGatewayClient;

/**
 * ?????????????????? Controller 
 * @author robin
 */
public class TransLogBaseController extends BaseController {
	
	protected static Long longVal(String s, Long defaultVal) {
		if (s==null) return defaultVal;
		try {
			return Long.parseLong(s);
		} catch (Exception e) {
			return defaultVal;
		}
	}

	protected static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String transAt = m.get("transAt");
			m.put("transAmtDesc", StringUtil.formateAmt(transAt));
			String transFee = m.get("transFee");
			m.put("transFeeDesc", StringUtil.formateAmt(transFee));
			String transFeeChnl = m.get("transFeeChnl");
			m.put("transFeeChnlDesc", StringUtil.formateAmt(transFeeChnl));
			
			Long iTransAt = longVal(transAt, 0L);
			Long iTransFeeChnl = longVal(transFeeChnl, 0L);
			Long iChnlFeeTransAmt= iTransAt- iTransFeeChnl;
			m.put("chnlFeeTransAmt", iChnlFeeTransAmt.toString());
			m.put("chnlFeeTransAmtDesc", StringUtil.formateAmt(iChnlFeeTransAmt.toString()));

			String transFeeDelta = m.get("transFeeDelta");
			m.put("transFeeDeltaDesc", StringUtil.formateAmt(transFeeDelta));

			String intTransCd = m.get("intTransCd");
			m.put("intTransCdDesc", EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, true));

			String orderState = m.get("orderState");
			m.put("orderStateDesc", EnumUtil.translate(OrderState.class, orderState, true));

			String txnState = m.get("txnState");
			// ???????????????????????????
			String stateMark = "";
			String orderToTxnState = TxnStateEnums.OrderStateToTxnState.get(orderState);
			if (!Utils.isInSet(orderToTxnState, txnState, null, ""))
				stateMark = "*?????????*";
			String precheckMark= hasPreCheckTag(m.get("tags")) ? "???" : "";
			m.put("txnStateDesc", precheckMark+EnumUtil.translate(TxnStatus.class, txnState, true) + stateMark);

			String transChnl = m.get("transChnl");
			m.put("transChnlDesc", EnumUtil.translate(TxnEnums.ChnlId.class, transChnl, true));

			String chnlTxnStateMsg = m.get("chnlTxnStateMsg");
			String chnlRespMsg = m.get("chnlRespMsg");
			m.put("chnlTxnStateDesc", getLongDesc(chnlTxnStateMsg, chnlRespMsg));

			String respCd = m.get("respCd");
			String errMsg = m.get("errMsg");
			String txnStateMsg = m.get("txnStateMsg");
			if (!isOkCode(respCd))
				m.put("respDesc", getLongDesc(errMsg, txnStateMsg));

			String txnSrc = m.get("txnSrc");
			m.put("txnSrcDesc", EnumUtil.translate(TxnSource.class, txnSrc, true));
			
			//INTER_MSG.execResult
			String execResult = m.get(INTER_MSG.execResult);
			if (!Utils.isEmpty(execResult)) {
				String execResultDesc = "true".equals(execResult) ? "???" : "???";
				m.put(INTER_MSG.execResult, execResultDesc);
			}
			
			String currCd = m.get("currCd");
			m.put("currCd", EnumUtil.translate(BmEnums.CurrCdType.class, currCd, true));
			
			String siteId = m.get("siteId");
			m.put("siteId", EnumUtil.translate(CommonEnums.Site.class, siteId, true));
			
			////////////////////////////////////////////////
			//  rowStyler = null; //"color:black;"; //'background-color:pink;color:blue;font-weight:bold;';
			
			String rowStyler = ParamCache.STYLE_DEFAULT; 
			
			if (TXNS_TATUS.PROC.equals(txnState))
				rowStyler=ParamCache.STYLE_TXN_STATUS_PROC;
			if (TXNS_TATUS.OK.equals(txnState))
				rowStyler=ParamCache.STYLE_TXN_STATUS_OK;
			if (TXNS_TATUS.FAILED.equals(txnState))
				rowStyler=ParamCache.STYLE_TXN_STATUS_FAILED;
			if (TXNS_TATUS.OTHER.equals(txnState))
				rowStyler=ParamCache.STYLE_TXN_STATUS_OTHER;
			
			if (!Utils.isEmpty(rowStyler))
				m.put("rowStyler", rowStyler);
			

		}
	};
	
	protected static boolean isOkCode(String code) {
		return (Utils.isInSet(code, RspCd._00, RspCd._0000000, "00"));
	}

	protected static String getLongDesc(String... args) {
		int len = 0;
		String msg = "";
		for (String arg : args) {
			if (!StringUtils.isBlank(arg)) {
				if ((arg.length() > len) && (!arg.contains("??????"))) {
					len = arg.length();
					msg = arg;
				}
			}
		}
		return msg;
	}

	protected static String getFirst(String... args) {
		for (String arg : args) {
			if (!StringUtils.isBlank(arg))
				return arg;
		}
		return "";
	}
	
	public String manage(Model model, String baseUri) {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
		model.addAttribute("today", simpleDateFormat.format(new Date()));
		return super.toManage(model, false, baseUri);
	}

	public AjaxResult query(Model model, String pageConfTp, Map<String, String> qryParamMap, int pageNum,
			int pageSize, String mon) {
		this.debug("[ITxnLogViewService][query] qryParamMap: %s", qryParamMap);
		ITxnLogViewService svc = this.getDBService(ITxnLogViewService.class);
		Pager<TxnLogView> p = svc.selectByPage(pageNum, pageSize, mon, qryParamMap);
		 Pager<Map<String, String>> transferPager = commonBO.transferPager(p, pageConfTp, ENTITY_TRANSFER);
		 //tags????????????????????????
		 List<Map<String, String>> results = transferPager.getResultList();//??????????????????????????????
		 if(!results.isEmpty()) {
			 for (int i = 0; i < results.size(); i++) {
				 String tags = results.get(i).get("tags");
				 if(!Utils.isEmpty(tags)) {
					 String[] lists = tags.split(":");
					 	//?????????????????????
						if(lists.length>3) {
							String name = lists[1].substring(1,lists[1].length());
							String name2 = lists[2].substring(3,lists[2].length());
							String value = name+","+name2;
							results.get(i).put("tags", value);
						}else {
							String value = lists[1].substring(1,lists[1].length());
							results.get(i).put("tags", value);
						}
						results.set(i, results.get(i));
						transferPager.setResultList(results);
				 }
			 }
		 }
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, transferPager);
	}
	
	public AjaxResult allOrderIdQuery(Model model, String pageConfTp, Map<String, String> qryParamMap, int pageNum,
			int pageSize, String mon) {
		this.debug("[ITxnLogViewService][query] qryParamMap: %s", qryParamMap);
		ITxnLogViewService svc = this.getDBService(ITxnLogViewService.class);
		Pager<TxnLogView> p = svc.selectAllOrderId(pageNum, pageSize, mon, qryParamMap);
		 Pager<Map<String, String>> transferPager = commonBO.transferPager(p, pageConfTp, ENTITY_TRANSFER);
		 //tags????????????????????????
		 List<Map<String, String>> results = transferPager.getResultList();//??????????????????????????????
		 if(!results.isEmpty()) {
			 for (int i = 0; i < results.size(); i++) {
				 String tags = results.get(i).get("tags");
				 if(!Utils.isEmpty(tags)) {
					 String[] lists = tags.split(":");
					 	//?????????????????????
						if(lists.length>3) {
							String name = lists[1].substring(1,lists[1].length());
							String name2 = lists[2].substring(3,lists[2].length());
							String value = name+","+name2;
							results.get(i).put("tags", value);
						}else {
							String value = lists[1].substring(1,lists[1].length());
							results.get(i).put("tags", value);
						}
						results.set(i, results.get(i));
						transferPager.setResultList(results);
				 }
			 }
		 }
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, transferPager);
	}

	public JSONObject summary(Map<String, String> qryParamMap, String mon) {
		this.debug("[ITxnLogViewService][summary] qryParamMap: %s", qryParamMap);
		ITxnLogViewService svc = this.getDBService(ITxnLogViewService.class);
		TxnLogSummary res = svc.selectSummary(mon, qryParamMap);
		Map<String, Object> map = new HashMap<String, Object>();
		if (res != null) {
			map.put("recCount", res.getRecCount());
			map.put("sumTransAt", StringUtil.formateAmt(res.getSumTransAt()));
			map.put("sumTransFee", StringUtil.formateAmt(res.getSumTransFee()));
			map.put("sumTransFeeChnl", StringUtil.formateAmt(res.getSumTransFeeChnl()));
			map.put("sumTransFeeDelta", StringUtil.formateAmt(res.getSumTransFeeDelta()));
		}
		JSONObject result = (JSONObject) JSON.toJSON(map);
		return result;
	}

	public String detail(Model model, String baseUri, String pageConfTp, String transSeqId, String mon) {
		this.debug("[detail] /detail.do, transSeqId: %s", transSeqId);
		ITxnLogViewService svc = this.getDBService(ITxnLogViewService.class);
		TxnLogView entity = svc.selectByPrimaryKey(transSeqId, mon);
		AssertUtil.objIsNull(entity, "?????????????????????:" + transSeqId);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, PageConfCache.getDetailConf(pageConfTp));
		model.addAttribute(BMConstants.ENTITY_RESULT, commonBO.transferEntity(entity, pageConfTp, ENTITY_TRANSFER));
		return super.toDetail(model, baseUri);
	}
	
	protected String queryChnlResult(Model model, String baseUri, String pageConfTp, String transSeqId, String orderDate) {
		this.debug("[queryChnlResult] ????????????????????????????????????=%s, ????????????=%s, ?????????=%s, ????????????=%s", transSeqId, orderDate, this.getSessionUsrId(), DateUtils.dateToStr19(new Date()));
		AssertUtil.objIsNull(transSeqId, "transSeqId is null.");
		if (Utils.isEmpty(orderDate)) {
			orderDate = StringUtils.left(transSeqId, 8);
		}
		Map<String, String>  resp=null;
		try {
			resp = InternalGatewayClient.queryChnlTxnResult(transSeqId, orderDate);
		} catch (SystemException e) {
			resp = new HashMap<>();
			Utils.errorToResp(e, resp);
		}
		this.debug("[queryChnlResult] resp: %s", resp);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, PageConfCache.getDetailConf(pageConfTp));
		model.addAttribute(BMConstants.ENTITY_RESULT, resp);
		return super.toDetail(model, baseUri);
	}

	

	public String adjustTxnState(Model model, String baseUri, String pageConfTp, String transSeqId, String mon,
			String txnState) {
		debug("/transSateModify.do ; mon=%s", mon);
		// ??????????????????
		if (StringUtil.isBlank(mon)) {
			if (!Utils.isEmpty(transSeqId)) {
				mon = transSeqId.substring(0, 6);
			} else {
				String today = DateUtil.now8();
				mon = today.substring(0, 6);
			}
		}

		ITxnLogViewService svc = this.getDBService(ITxnLogViewService.class);
		TxnLogView entity = svc.selectByPrimaryKey(transSeqId, mon);
		this.debug("entity: "+ entity);

		AssertUtil.objIsNull(entity, "?????????????????????:" + transSeqId);
		model.addAttribute(BMConstants.ENTITY_RESULT, commonBO.transferEntity(entity, pageConfTp, ENTITY_TRANSFER));
		return super.toPage(model, baseUri, "adjust");
	}
	
	public String adjustMandatoryTxnState(Model model, String baseUri, String pageConfTp, String transSeqId, String mon,
			String txnState) {
		debug("/adjustMandatoryTxnState.do ; mon=%s", mon);
		// ??????????????????
		if (StringUtil.isBlank(mon)) {
			if (!Utils.isEmpty(transSeqId)) {
				mon = transSeqId.substring(0, 6);
			} else {
				String today = DateUtil.now8();
				mon = today.substring(0, 6);
			}
		}
		
		ITxnLogViewService svc = this.getDBService(ITxnLogViewService.class);
		TxnLogView entity = svc.selectByPrimaryKey(transSeqId, mon);
		this.debug("entity: "+ entity);
		
		AssertUtil.objIsNull(entity, "?????????????????????:" + transSeqId);
		model.addAttribute(BMConstants.ENTITY_RESULT, commonBO.transferEntity(entity, pageConfTp, ENTITY_TRANSFER));
		return super.toPage(model, baseUri, "mandatoryAdjust");
	}
	
//	protected boolean hasPreCheckOpt(TxnLogView tr, String mon, String opType) {
//		//this.debug("tr: "+ tr);
//		if (!isHighRiskOpt(opType)) return true;
//		String tags = TagUtils.getString(tr.getTags(), TAG.preCheck, "");
//		String strOpType=":"+opType+";";
//		if (strVal(tags).contains(strOpType)) return true;
//		String opr = this.getSessionUsrId();
//		String newTags = TagUtils.setTag(tr.getTags(), TAG.preCheck, strVal(tags)+opr+strOpType);
//		updateTag(tr, mon, newTags);
//		return false;
//	}
	
	protected boolean hasPreCheckOpt(TxnLogView tr, String mon, String opType) {
		//this.debug("tr: "+ tr);
		String opr = strVal(this.getSessionUsrId());
		String strOpType=":"+opType+";";
		String tags = strVal(TagUtils.getString(tr.getTags(), TAG.preCheck, ""));
		if ((!isHighRiskOpt(opType)) && Utils.isEmpty(tags)) return true;
		String[] tagsAry = Utils.strSplit(tags, ";");
		for(String tag: tagsAry) {
			tag = StringUtil.trim(tag);
			if (!Utils.isEmpty(tag)) {
				String[] tagContent=Utils.strSplit(tag, ":", 2);
				String tOpr=strVal(Utils.selectItem(tagContent,0));
				String tOpType=strVal(Utils.selectItem(tagContent,1));
				if ((!tOpr.equals(opr)) && (tOpType.equals(opType))) return true;
			}
		}
		String newTags = TagUtils.setTag(tr.getTags(), TAG.preCheck, tags+opr+strOpType);
		updateTag(tr, mon, newTags);
		return false;
	}	
	
	protected static boolean hasPreCheckTag(String tags) {
		//this.debug("tr: "+ tr);
		if (tags==null) return false;
		return tags.contains(TAG.preCheck);
	}
	

	
	static final Map<String, String> HIGH_RISK_OPTS = Utils.newMap(
			OPERTYPE._12,"", //??????: ????????? -> ??????
			OPERTYPE._13,"", //??????: ?????? -> ??????
			OPERTYPE._14,"", //??????: ?????? -> ??????
			OPERTYPE._17,"", //??????: ????????? -> ??????
			OPERTYPE._18,"", //??????: ?????? -> ??????
			OPERTYPE._20,"" //??????: ?????? -> ??????
			); 
	
	protected boolean isHighRiskOpt(String opType) {
		return HIGH_RISK_OPTS.containsKey(opType);
	}

	protected void updateTag(TxnLogView tr, String mon, String tags) {
		String transSeqId = tr.getTransSeqId();
		String orderDate = tr.getExtTransDt(); 
		// ??????????????????
		if (StringUtil.isBlank(mon)) {
			if (!Utils.isEmpty(orderDate)) {
				mon = orderDate;
			} 
			else if (!Utils.isEmpty(transSeqId)) {
				mon = transSeqId.substring(0, 6);
			} else {
				String today = DateUtil.now8();
				mon = today.substring(0, 6);
			}
		}
		mon = mon.substring(4, 6);
		ITransLogService svc = this.getDBService(ITransLogService.class);
		svc.updateTxnTags(transSeqId, mon, tags);
	}

	public AjaxResult adjustTxnStateSubmit(Model model, TxnLogView tr, String mon, String opType, String comment) {
		AssertUtil.objIsNull(tr, "TransLog is null.");
		String transSeqId = tr.getTransSeqId();
		if (hasPreCheckOpt(tr, mon, opType)) {
			String opr = this.getSessionUsrId();
			if (!Utils.isEmpty(tr.getExtTransDt()))
				mon = tr.getExtTransDt();
			try {
				String opTypeDesc = EnumUtil.translate(TxnEnums.TransLogIncomeAdjustOp.class, opType, true);
				String opTime = DateUtils.dateToStr19(new Date());
				this.info("[adjustTxnRecord] ????????????????????????????????????=%s, ????????????=%s, ??????????????????=%s, ??????=%s, ?????????=%s, ????????????=%s", transSeqId, mon, opTypeDesc, comment, opr, opTime);
				this.chnlMerAccService().adjustTxnRecord(tr.getTransSeqId(), mon, opType, opr, comment);
				return commonBO.buildSuccResp();
			} catch (Exception e) {
				return commonBO.buildErrorResp("??????????????????????????? "+e.getLocalizedMessage());
			}
		}
		else {
			return commonBO.buildSuccResp(String.format("?????? %s ??????????????????\n?????????????????????????????????????????????????????????", transSeqId));
		}
	}
	
//	public AjaxResult differentModify(Model model, TxnLogView tr, String mon, String opType, String comment) {
//		AssertUtil.objIsNull(tr, "TransLog is null.");
//		String transSeqId = tr.getTransSeqId();
//		if (hasPreCheckOpt(tr, mon, opType)) {
//			String opr = this.getSessionUsrId();
//			if (!Utils.isEmpty(tr.getExtTransDt()))
//				mon = tr.getExtTransDt();
//			try {
//				this.info("adjustTxnRecord('%s','%s','%s','%s','%s')", tr.getTransSeqId(), mon, opType, opr, comment);
//				this.chnlMerAccService().adjustTxnRecord(tr.getTransSeqId(), mon, opType, opr, comment);
//				return commonBO.buildSuccResp();
//			} catch (Exception e) {
//				return commonBO.buildErrorResp("??????????????????????????? "+e.getLocalizedMessage());
//			}
//		}
//		else {
//			return commonBO.buildSuccResp(String.format("?????? %s ??????????????????\n?????????????????????????????????????????????????????????", transSeqId));
//		}
//	}
	
	public String backToManage(Model model, String baseUri) {
		model.addAttribute("today", DateUtil.now8());
		return super.toManage(model, true, baseUri);
	}
	
	protected AjaxResult compare(Model model, String pageConfTp, Map<String, String> qryParamMap, String mon, String outputFn, MultipartFile chnlLogFile, HttpServletResponse response) {
		String chnlFilename="";
		if (chnlLogFile!=null)
			chnlFilename=chnlLogFile.getOriginalFilename();
		this.debug("[export] ????????????, chnlFilename='%s', qryParamMap: %s", chnlFilename, qryParamMap);

		ITxnLogViewService svc = this.getDBService(ITxnLogViewService.class);
		List<TxnLogView> list = svc.select(mon, qryParamMap);
		Pager<Map<String, String>> pager = commonBO.transferList(list, pageConfTp, ENTITY_TRANSFER);
		
		TxnLogSummary res = svc.selectSummary(mon, qryParamMap);
		Map<String, Object> mapSummary = new LinkedHashMap<String, Object>();
		if (res != null) {
			mapSummary.put("????????????", res.getRecCount());
			mapSummary.put("????????????(???)", StringUtil.formateAmt(res.getSumTransAt()));
			mapSummary.put("???????????????(???)", StringUtil.formateAmt(res.getSumTransFee()));
			mapSummary.put("?????????????????????(???)", StringUtil.formateAmt(res.getSumTransFeeChnl()));
			mapSummary.put("????????????(???)", StringUtil.formateAmt(res.getSumTransFeeDelta()));
		}
		
		//String fileNm = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_ACCFLOW_EXPORT_FILENM);
		try {
			commonBO.exportToExcel(pager,mapSummary, outputFn, Constant.UTF8, response);
			return null;
		} catch (Exception e) {
			return commonBO.buildErrorResp(e.getMessage());
		}
	}
	
	protected AjaxResult export(Model model, String pageConfTp, Map<String, String> qryParamMap, String mon, String filename, HttpServletResponse response) {
		
		this.debug("[export] ????????????????????????, qryParamMap: %s", qryParamMap);

		ITxnLogViewService svc = this.getDBService(ITxnLogViewService.class);
		List<TxnLogView> list = svc.select(mon, qryParamMap);
		Pager<Map<String, String>> pager = commonBO.transferList(list, pageConfTp, ENTITY_TRANSFER);
		
		TxnLogSummary res = svc.selectSummary(mon, qryParamMap);
		Map<String, Object> mapSummary = new LinkedHashMap<String, Object>();
		if (res != null) {
			mapSummary.put("????????????", res.getRecCount());
			mapSummary.put("????????????", StringUtil.formateAmt(res.getSumTransAt()));
			mapSummary.put("???????????????", StringUtil.formateAmt(res.getSumTransFee()));
			mapSummary.put("?????????????????????", StringUtil.formateAmt(res.getSumTransFeeChnl()));
			mapSummary.put("????????????", StringUtil.formateAmt(res.getSumTransFeeDelta()));
		}
		
		//String fileNm = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_ACCFLOW_EXPORT_FILENM);
		try {
			commonBO.exportToExcel(pager,mapSummary, filename, Constant.UTF8, response);
			return null;
		} catch (Exception e) {
			return commonBO.buildErrorResp(e.getMessage());
		}
	}
	
	protected AjaxResult compareLogs(Model model, String pageConfTp, Map<String, String> qryParamMap, String mon, MultipartFile chnlLogFile, String outFilename, HttpServletResponse response) {
		
		this.debug("[export] ????????????????????????, qryParamMap: %s", qryParamMap);

		ITxnLogViewService svc = this.getDBService(ITxnLogViewService.class);
		List<TxnLogView> list = svc.select(mon, qryParamMap);
		Pager<Map<String, String>> pager = commonBO.transferList(list, pageConfTp, ENTITY_TRANSFER);
		
//		TxnLogSummary res = svc.selectSummary(mon, qryParamMap);
//		Map<String, Object> mapSummary = new LinkedHashMap<String, Object>();
//		if (res != null) {
//			mapSummary.put("????????????", res.getRecCount());
//			mapSummary.put("????????????(???)", StringUtil.formateAmt(res.getSumTransAt()));
//			mapSummary.put("???????????????(???)", StringUtil.formateAmt(res.getSumTransFee()));
//			mapSummary.put("?????????????????????(???)", StringUtil.formateAmt(res.getSumTransFeeChnl()));
//			mapSummary.put("????????????(???)", StringUtil.formateAmt(res.getSumTransFeeDelta()));
//		}
		
		outFilename = getUniqueFileName(outFilename);
		try {
			LogCompareTool tool= new LogCompareTool(pager, null, outFilename, chnlLogFile, "UTF-8", response);
			tool.proc();
			return null;
		} catch (Exception e) {
			return commonBO.buildErrorResp(e.getMessage());
		}
	}


	ChnlMerAccService cmas = null;

	protected ChnlMerAccService chnlMerAccService() {
		if (cmas == null) {
			cmas = ServiceProxy.getService(ChnlMerAccService.class);
		}
		return cmas;
	}
	
	/**
	 * ????????????
	 * @param transSeqId
	 * @param mon
	 * @param response
	 * @return
	 */
	public AjaxResult resendNotify(String transSeqId, String orderDate, HttpServletResponse response) {
		
		String msg="";
		try {
			AssertUtil.notMonStr(orderDate.substring(4, 6));
			AssertUtil.strIsBlank(transSeqId, "??????????????????");
			
			ITransLogService service = this.getDBService(ITransLogService.class);
			TransLog log = service.selectByPrimaryKey(transSeqId, orderDate.substring(4, 6));
			
			AssertUtil.objIsNull(log, "?????????????????????:" + transSeqId);
			String notifyUrl = log.getMchntBackUrl();
			checkNotifiUrl(notifyUrl);
			
			HttpClientResponse hr = resendNotifyToMer(transSeqId, orderDate);
			if (hr!=null) {
				msg = String.format("?????? %s ??????????????????!\n????????????: '%s' \n??????????????????: [%s] %s", transSeqId, notifyUrl, hr.getCode(), hr.getBody());
				
				RiskEvent.bm()
					.who(bmUser(commonBO.getSessionUser().getUsrId()))
					.event(RISK.Event.Resend_Notify)
					.result(RISK.Result.Ok)
					.target(transSeqId + "|" + orderDate + "|" + notifyUrl)
					.message(String.format("????????? %s, ", commonBO.getSessionUser().getUsrId()) + msg)
					.params("transSeqId",transSeqId)
					.params("orderDate",orderDate)
					.params("code",hr.getCode())
					.params("body",hr.getBody())
					.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
					.submit();
				
				return commonBO.buildSuccResp(msg);
			}
			else {
				msg = String.format("?????? %s ??????????????????! \n????????????: '%s'", transSeqId, notifyUrl);
				this.error("[resendNotify] "+msg);
				
				RiskEvent.bm()
				.who(bmUser(commonBO.getSessionUser().getUsrId()))
				.event(RISK.Event.Resend_Notify)
				.result(RISK.Result.Error)
				.target(transSeqId + "|" + orderDate + "|" + notifyUrl)
				.message(String.format("????????? %s, ", commonBO.getSessionUser().getUsrId()) + msg)
				.params("transSeqId",transSeqId)
				.params("orderDate",orderDate)
				.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
				.submit();
			}
			
			
		} catch (Exception e) {
			msg = String.format("?????? %s ??????????????????!\n%s", transSeqId, e.getMessage());
			this.error("[resendNotify] "+msg,e);
			
			RiskEvent.bm()
				.who(bmUser(commonBO.getSessionUser().getUsrId()))
				.event(RISK.Event.Resend_Notify)
				.result(RISK.Result.Error)
				.target(transSeqId + "|" + orderDate)
				.message(String.format("????????? %s, ", commonBO.getSessionUser().getUsrId()) + msg)
				.params("transSeqId",transSeqId)
				.params("orderDate",orderDate)
				.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
				.submit();
		}
		return commonBO.buildErrorResp(msg);
	}
	
	protected HttpClientResponse resendNotifyToMer(String txnId, String orderDate) {
		this.getLogger().info(String.format("[resendNotifyToMer] ????????????????????????????????????=%s, ????????????=%s, ?????????=%s, ????????????=%s", txnId, orderDate, this.getSessionUsrId(), DateUtils.dateToStr19(new Date())));
		if (Utils.isEmpty(orderDate)) {
			orderDate= (txnId+"010101").substring(4,6);
		}
		try {
			HttpClientResponse hr = InternalGatewayClient.resendNotifyToMer(txnId, orderDate, false);
			if (hr!=null)
				this.info("[resendNotifyToMer] ??????????????????! ????????????: [%s] %s", hr.getCode(), hr.getBody());
			else
				this.info("[resendNotifyToMer] ????????????????????????! ??????????????????????????????");
			return hr;
		} catch (SystemException e) {
			this.error(e.getMessage());
		}
		return null;
	}
	
	protected void checkNotifiUrl(String notifyUrl) {
		if (isNoopUrl(notifyUrl))
			throw new BizzException("??????????????????????????????????????????");
		
		if (!isValidNotifiUrl(notifyUrl))
			throw new BizzException("???????????????????????????");
	}
	
	protected boolean isNoopUrl(String notifyUrl) {
		if (Utils.isEmpty(notifyUrl)) return true;
		if (Constant.isNoopUrl(notifyUrl)) return true;
		if (Constant.INTERNAL_IP.equals(notifyUrl)) return true;
		return false;
	}
	
	protected boolean isValidNotifiUrl(String notifyUrl) {
		if (Utils.isEmpty(notifyUrl)) return false;
		if (Constant.INTERNAL_IP.equals(notifyUrl)) return false;
		if (Constant.isNoopUrl(notifyUrl)) return false;
		try {
			URI uri = new URI(notifyUrl);
			if (!uri.isAbsolute()) return false;
			if ("127.0.0.1".equals(uri.getHost())) return false;
			if ("localhost".equals(uri.getHost())) return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public JSONObject count(Map<String, String> qryParamMap, String mon) {
		this.debug("[ITxnLogViewService][count] qryParamMap: %s", qryParamMap);
		ITxnLogViewService svc = this.getDBService(ITxnLogViewService.class);
		Long res = svc.count(mon, qryParamMap);
		Map<String, Object> map = new HashMap<String, Object>();
		if (res != null) {
			map.put("countOffPay", res);
		}
		JSONObject result = (JSONObject) JSON.toJSON(map);
		return result;
	}
}
