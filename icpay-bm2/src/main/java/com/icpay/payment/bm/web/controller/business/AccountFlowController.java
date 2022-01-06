package com.icpay.payment.bm.web.controller.business;

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
import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.AccEnums;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.Converter;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountFlow;
import com.icpay.payment.db.service.IChnlMchntAccountFlowService;

/**
 * 人工調帳報表
 */
@Controller
@RequestMapping("/accountFlow")
public class AccountFlowController extends BaseController {

	private static final Logger logger = Logger.getLogger(AccountFlowController.class);

	private static final String RESULT_BASE_URI = "accountFlow";

	private static final IEntityTransfer ACCFLOW_ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String chnlId = m.get("chnlId");
			m.put("chnlIdDesc", EnumUtil.translate(TxnEnums.ChnlId.class, chnlId, true));
			
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
			
			String operateTp = m.get("operateTp");
			m.put("operateTpDesc", EnumUtil.translate(AccEnums.AccOperTp.class, operateTp, true));
			
			String daytxnamt = m.get("daytxnamt");
			m.put("daytxnamtDesc", StringUtil.formateAmt(daytxnamt));
			
			String daytxnfee = m.get("daytxnfee");
			m.put("daytxnfeeDesc", StringUtil.formateAmt(daytxnfee));
			
			String daywithdrawamt = m.get("daywithdrawamt");
			m.put("daywithdrawamtDesc", StringUtil.formateAmt(daywithdrawamt));
			
			String daywithdrawfee = m.get("daywithdrawfee");
			m.put("daywithdrawfeeDesc", StringUtil.formateAmt(daywithdrawfee));
			
			String currCd = m.get("currCd");
			m.put("currCd", EnumUtil.translate(BmEnums.CurrCdType.class, currCd, true));
			
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
	
	/**
	 * 下拉選單
	 */
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
	
	/**
	 * 進入頁面
	 */
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		if (logger.isDebugEnabled()) {
			logger.debug("人工调帐报表");
		}
		model.addAttribute("today", DateUtil.now8());
		model.addAttribute("today_end", DateUtil.now8());
		model.addAttribute("accOpTp", ACC_ADJUST_OP_TP);
		return super.toManage(model, false, RESULT_BASE_URI);
	}

	/**
	 * 查詢
	 */
	@RequestMapping(value = "/accountFlowQry.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult accountFlowQry(int pageNum, int pageSize, 
			String chnlId, String mchntCd, String transSeqId,String startDate,String endDate, 
			String operateTpCat, String timeQryMethod, String note, String lastOperId) {
		IChnlMchntAccountFlowService service = this.getDBService(IChnlMchntAccountFlowService.class);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("chnlId", chnlId);
		qryParamMap.put("mchntCd", mchntCd);
		qryParamMap.put("startDate", startDate);
		qryParamMap.put("endDate", endDate);
		if ("ALL".equals(operateTpCat)) {
			operateTpCat = "";
			Map<String, String> opTp = ACC_ADJUST_OP_TP;
			for (String key : opTp.keySet()) {
				operateTpCat = operateTpCat + key + ";";
	        }
			if (operateTpCat.lastIndexOf(";") != -1) {
				operateTpCat = operateTpCat.substring(0, operateTpCat.lastIndexOf(";"));
			}
		}
		qryParamMap.put("operateTpCat", operateTpCat);
		qryParamMap.put("transSeqId", transSeqId);
		qryParamMap.put("timeQryMethod", timeQryMethod);
		qryParamMap.put("note", note);
		qryParamMap.put("lastOperId", lastOperId);
		
		Pager<ChnlMchntAccountFlow> p = service.selectByPage(pageNum, pageSize,this.getMonth(startDate, endDate) , qryParamMap);
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_CHNLMCHNTACCOUNTFLOW, ACCFLOW_ENTITY_TRANSFER));
	}
	
	/**
	 * 导出
	 */
	@RequestMapping(value = "/export.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult export(String chnlId, String mchntCd, String transSeqId,String startDate,String endDate, 
			String operateTpCat, String timeQryMethod, String note, String lastOperId, HttpServletResponse response) {
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("chnlId", chnlId);
		qryParamMap.put("mchntCd", mchntCd);
		qryParamMap.put("startDate", nullIfEmpty(startDate));
		qryParamMap.put("endDate", nullIfEmpty(endDate));
		if ("ALL".equals(operateTpCat)) {
			operateTpCat = "";
			Map<String, String> opTp = ACC_ADJUST_OP_TP;
			for (String key : opTp.keySet()) {
				operateTpCat = operateTpCat + key + ";";
	        }
			if (operateTpCat.lastIndexOf(";") != -1) {
				operateTpCat = operateTpCat.substring(0, operateTpCat.lastIndexOf(";"));
			}
		}
		qryParamMap.put("operateTpCat", nullIfEmpty(operateTpCat));
		qryParamMap.put("transSeqId", nullIfEmpty(transSeqId));
		qryParamMap.put("timeQryMethod", timeQryMethod);
		qryParamMap.put("note", note);
		qryParamMap.put("lastOperId", lastOperId);
		debug("渠道账户流水导出: " + qryParamMap);
		String mon = this.getMonth(startDate, endDate);
		IChnlMchntAccountFlowService service = this.getDBService(IChnlMchntAccountFlowService.class);
		List<ChnlMchntAccountFlow> list = service.select(mon, qryParamMap);
		Pager<Map<String, String>> pager = commonBO.transferList(list, BMConstants.PAGE_CONF_CHNLMCHNTACCOUNTFLOW, ACCFLOW_ENTITY_TRANSFER);
		String fileNm = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_ACCFLOW_EXPORT_FILENM);
		commonBO.exportToExcel(pager, fileNm, Constant.UTF8, response);
		return null;
	}
	
	/**
	 * 查看詳情
	 */
	@RequestMapping(value = "/accountFlowDetail.do", method = RequestMethod.GET)
	public String accountFlowDetail(Model model, long seqId,String recUpdTs) {
		IChnlMchntAccountFlowService service = this.getDBService(IChnlMchntAccountFlowService.class);
		ChnlMchntAccountFlow entity = service.selectByPrimaryKey(String.valueOf(seqId), recUpdTs.substring(5,7));
		AssertUtil.objIsNull(entity, "未找到账户流水");
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_CHNLMCHNTACCOUNTFLOW));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_CHNLMCHNTACCOUNTFLOW, ACCFLOW_ENTITY_TRANSFER));
		
		return RESULT_BASE_URI + "/detail";
	}
	
	/**
	 * 加總
	 */
	@RequestMapping(value = "/amtSum.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody JSONObject amtSum(Model model, String chnlId, String mchntCd, 
			String transSeqId, String startDate,String endDate, String operateTpCat, 
			String timeQryMethod, String note, String lastOperId) {
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("chnlId", chnlId);
		qryParamMap.put("mchntCd", mchntCd);
		qryParamMap.put("startDate", nullIfEmpty(startDate));
		qryParamMap.put("endDate", nullIfEmpty(endDate));
		if ("ALL".equals(operateTpCat)) {
			operateTpCat = "";
			Map<String, String> opTp = ACC_ADJUST_OP_TP;
			for (String key : opTp.keySet()) {
				operateTpCat = operateTpCat + key + ";";
	        }
			if (operateTpCat.lastIndexOf(";") != -1) {
				operateTpCat = operateTpCat.substring(0, operateTpCat.lastIndexOf(";"));
			}
		}
		qryParamMap.put("operateTpCat", nullIfEmpty(operateTpCat));
		qryParamMap.put("transSeqId", nullIfEmpty(transSeqId));
		qryParamMap.put("timeQryMethod", timeQryMethod);
		qryParamMap.put("note", note);
		qryParamMap.put("lastOperId", lastOperId);
		String mon = this.getMonth(startDate, endDate);
		this.debug("[IChnlMchntAccountInfoService][summary] qryParamMap: %s", qryParamMap);
		IChnlMchntAccountFlowService svc = this.getDBService(IChnlMchntAccountFlowService.class);
		
		ChnlMchntAccountFlow sum = svc.selectSummary(mon, qryParamMap);
		Map<String, Object> map = new HashMap<String, Object>();
		if (sum != null) {
			map.put("sumTransAt", StringUtil.formateAmt(sum.getTransAt()));//操作金额总额
		}
		JSONObject result = (JSONObject) JSON.toJSON(map);
		return result;
	}
}
