package com.icpay.payment.mer.web.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.CurrencyEnums;
import com.icpay.payment.common.enums.MerEnums;
import com.icpay.payment.common.enums.SettleEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountFlow;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicy;
import com.icpay.payment.mer.bo.MchntAccountBO;
import com.icpay.payment.mer.bo.MchntBO;
import com.icpay.payment.mer.bo.MchntSettleBO;
import com.icpay.payment.mer.cache.MerConfigCache;
import com.icpay.payment.mer.cache.PageConfCache;
import com.icpay.payment.mer.constants.MerConstants;
import com.icpay.payment.mer.util.DateUtils;
import com.icpay.payment.mer.util.I18nMsgUtils;

@Controller
@RequestMapping("/mchntAcct")
public class MchntAccountController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(MchntAccountController.class);
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			BigDecimal transAt = new BigDecimal(m.get("transAt"));
			m.put("transAtDesc", transAt.movePointLeft(2).toString());
			
			BigDecimal transFee = new BigDecimal(m.get("transFee"));
			m.put("transFeeDesc", transFee.movePointLeft(2).toString());
			
			String availableBalance = m.get("availableBalance");
			m.put("availableBalanceDesc", StringUtil.formateAmt(availableBalance));
			
			String frozenT1Balance = m.get("frozenT1Balance");
			m.put("frozenT1BalanceDesc", StringUtil.formateAmt(frozenT1Balance));
			
			String frozenBalance = m.get("frozenBalance");
			m.put("frozenBalanceDesc", StringUtil.formateAmt(frozenBalance));
			
			/*String payType = m.get("payType");
			m.put("payTypeDesc", EnumUtil.translate(TxnEnums.PaymentType.class, payType, false));
			*/
			
			String intTransCd = m.get("intTransCd");
			m.put("intTransCdDesc", EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, false));
			
			String operateTp = m.get("operateTp");
			try {
				m.put("operateTpDesc", EnumUtil.translate(MerEnums.AccOperTp.class, operateTp, false));
			} catch (Exception e1) {
				m.put("operateTpDesc","?");
			}
			
			String transDt = m.get("transDt");
			String transTm = m.get("transTm");
//			if(StringUtil.isBlank(m.get("transTm"))) {
//				m.put("transTm", m.get("recUpdTs"));
//			}
			
			if(!StringUtil.isBlank(m.get("transTm"))) {
				m.put("transTm", transDt + " " + transTm);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//日期原始格式  
				String origFormat = "yyyyMMdd HHmmss";  
				DateFormat format = new SimpleDateFormat(origFormat);
				Date date;
				try {
					try {
						date = format.parse(m.get("transTm"));
						m.put("transTm", sdf.format(date));
					} catch (Exception e) {
						date = sdf.parse(m.get("transTm"));
						m.put("transTm", sdf.format(date));
					}
				} catch (ParseException e) {
					m.put("transTm", null);
					e.printStackTrace();
					logger.error(e);
				}
			}

			
		}
	};

	@Autowired
	private MchntAccountBO mchntAccountBO;
	@Autowired
	private MchntBO mchntBO;
	@Autowired
	private MchntSettleBO mchntSettleBO;
	
	@RequestMapping(value="/acctMng", method=RequestMethod.GET)
	public String acctMng(Model model, HttpServletRequest req) {
		
		String mchntCd = commonBO.getMchntCd();
		model.addAttribute("mchntCd", mchntCd);
		
		//人民币账户
		MerAccountInfo mchntAcct_CNY = mchntAccountBO.getMchntAccount(mchntCd, "156");
		if (mchntAcct_CNY != null) {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_CNY", StringUtil.formateAmt(String.valueOf(mchntAcct_CNY.getAvailableBalance()-mchntAcct_CNY.getObligatedBalance())));
			//页面额外显示保留余额
			model.addAttribute("obligatedBalance_CNY", StringUtil.formateAmt(String.valueOf(mchntAcct_CNY.getObligatedBalance())));
			model.addAttribute("frozenBalance_CNY", StringUtil.formateAmt(String.valueOf(mchntAcct_CNY.getFrozenBalance())));
			model.addAttribute("frozenT1Balance_CNY", StringUtil.formateAmt(String.valueOf(mchntAcct_CNY.getFrozenT1Balance())));
			long totalBalance_CNY= sum(mchntAcct_CNY.getAvailableBalance(),mchntAcct_CNY.getFrozenBalance(),mchntAcct_CNY.getFrozenT1Balance());
			model.addAttribute("totalBalance_CNY", StringUtil.formateAmt(String.valueOf(totalBalance_CNY)));
		} else {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_CNY", "0.00");
			//页面额外显示保留余额
			model.addAttribute("obligatedBalance_CNY", "0.00");
			model.addAttribute("frozenBalance_CNY", "0.00");
			model.addAttribute("frozenT1Balance_CNY", "0.00");
			model.addAttribute("totalBalance_CNY", "0.00");
		}
		
		//越南盾账户
		MerAccountInfo mchntAcct_VND = mchntAccountBO.getMchntAccount(mchntCd, "704");
		if (mchntAcct_VND != null) {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_VND", StringUtil.formateAmt(String.valueOf(mchntAcct_VND.getAvailableBalance()-mchntAcct_VND.getObligatedBalance())));
			//页面额外显示保留余额
			model.addAttribute("obligatedBalance_VND", StringUtil.formateAmt(String.valueOf(mchntAcct_VND.getObligatedBalance())));
			model.addAttribute("frozenBalance_VND", StringUtil.formateAmt(String.valueOf(mchntAcct_VND.getFrozenBalance())));
			model.addAttribute("frozenT1Balance_VND", StringUtil.formateAmt(String.valueOf(mchntAcct_VND.getFrozenT1Balance())));
			long totalBalance_VND= sum(mchntAcct_VND.getAvailableBalance(),mchntAcct_VND.getFrozenBalance(),mchntAcct_VND.getFrozenT1Balance());
			model.addAttribute("totalBalance_VND", StringUtil.formateAmt(String.valueOf(totalBalance_VND)));
		} else {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_VND", "0.00");
			//页面额外显示保留余额
			model.addAttribute("obligatedBalance_VND", "0.00");
			model.addAttribute("frozenBalance_VND", "0.00");
			model.addAttribute("frozenT1Balance_VND", "0.00");
			model.addAttribute("totalBalance_VND", "0.00");
		}
		
		//泰铢账户
		MerAccountInfo mchntAcct_THB = mchntAccountBO.getMchntAccount(mchntCd, "764");
		if (mchntAcct_THB != null) {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_THB", StringUtil.formateAmt(String.valueOf(mchntAcct_THB.getAvailableBalance()-mchntAcct_THB.getObligatedBalance())));
			//页面额外显示保留余额
			model.addAttribute("obligatedBalance_THB", StringUtil.formateAmt(String.valueOf(mchntAcct_THB.getObligatedBalance())));
			model.addAttribute("frozenBalance_THB", StringUtil.formateAmt(String.valueOf(mchntAcct_THB.getFrozenBalance())));
			model.addAttribute("frozenT1Balance_THB", StringUtil.formateAmt(String.valueOf(mchntAcct_THB.getFrozenT1Balance())));
			long totalBalance_THB= sum(mchntAcct_THB.getAvailableBalance(),mchntAcct_THB.getFrozenBalance(),mchntAcct_THB.getFrozenT1Balance());
			model.addAttribute("totalBalance_THB", StringUtil.formateAmt(String.valueOf(totalBalance_THB)));
		} else {
			//可代付余额，须扣除保留余额
			model.addAttribute("availableBalance_THB", "0.00");
			//页面额外显示保留余额
			model.addAttribute("obligatedBalance_THB", "0.00");
			model.addAttribute("frozenBalance_THB", "0.00");
			model.addAttribute("frozenT1Balance_THB", "0.00");
			model.addAttribute("totalBalance_THB", "0.00");
		}
		
		MchntInfo m = mchntBO.getMchnt(mchntCd);
		model.addAttribute("mchntNm", m.getMchntCnNm());
		
//		MerSettlePolicy msp = mchntSettleBO.getMerSettleInfo(mchntCd); //未使用到
//		model.addAttribute("settleInfo", msp);
//		model.addAttribute("settlePeriodDesc", 
//				EnumUtil.translate(SettleEnums.SettlePeriod.class, msp.getSettlePeriod(), false));
//		if (SettleEnums.SettlePeriod._T0.getCode().equals(msp.getSettlePeriod())) {
//			model.addAttribute("settleLimit", StringUtil.transferAmt(msp.getSettleLimit()));
//		}
		model.addAttribute("settleAlgorithmLst", mchntSettleBO.buildAlgorithmLst(req));
		
		return "acctMng";
	}
	
	@RequestMapping(value="/fundMng", method=RequestMethod.GET)
	public String fundMng(Model model) {
		String mchntCd =commonBO.getMchntCd();
		String secretFlag ="true";
		if(!commonBO.validateSecret(mchntCd)) {
			secretFlag ="false";
		}
		model.addAttribute("secretFlag", secretFlag);
		model.addAttribute("loginType", commonBO.getLoginType());
//		model.addAttribute("today", DateUtil.now8());
		model.addAttribute("defaultStart", DateUtil.now8() + " 000000");
		model.addAttribute("defaultEnd", DateUtil.now8() + " 235959");
		return "fundMng";
	}

	/**
	 * 查询帐户资金流水
	 * @param pageNum
	 * @param pageSize
	 * @param operateDt
	 * @param operateTp
	 * @param transSeqId
	 * @param transAt
	 * @return
	 */
	@RequestMapping(value="/qryAcctFlow", method=RequestMethod.POST)
	public @ResponseBody AjaxResult qryAcctFlow(int pageNum, int pageSize, 
			String startDt, String endDt,
			String operateTpCat, String operateTp, String transSeqId, String transAt, 
			String timeQryMethod, String note, String currCd, HttpServletRequest req) {
		logger.debug(String.format("查询商户账户流水: pageNum=%s, pageSize=%s, startDate=%s,  endDate=%s,  operateTp=%s, transSeqId=%s, transAt=%s, currCd=%s", pageNum, pageSize, startDt,  endDt, operateTp, transSeqId, transAt, currCd));

        String lang = I18nMsgUtils.getLanguage(req);
        
		String operateDt = null;
		if (!Utils.isEmpty(startDt))
			operateDt = StringUtil.left(startDt, 8);
		else
			operateDt = StringUtil.left(endDt, 8);
		AssertUtil.objIsNull(operateDt,mappingI18n(this.getClass().getSimpleName(),"日期为空", lang));
		
		Map<String, String> qryParams = new HashMap<String, String>();
		qryParams.put("chnlId", "00");
		qryParams.put("mchntCd", commonBO.getMchntCd());
		qryParams.put("operateTp", operateTp);
		qryParams.put("operateTpCat", operateTpCat);
		
		//qryParams.put("transDt", operateDt);
		
//		qryParams.put("startDate", startDate);
//		qryParams.put("startTime", startTime);
//		qryParams.put("endDate", endDate);
//		qryParams.put("endTime", endTime);
		
		String mon = StringUtil.left(operateDt, 8);
		qryParams.put("startDate", StringUtil.left(startDt, 8));
		qryParams.put("endDate", StringUtil.left(endDt, 8));
		qryParams.put("startTime", StringUtil.right(startDt, 6));//起始时间
		qryParams.put("endTime", StringUtil.right(endDt, 6)); // 结束时间
		
//		qryParams.put("timeQryMethod", timeQryMethod); //UpdTS,CrtTS,OrdDT
		qryParams.put("timeQryMethod", timeQryMethod); //UpdTS,OrdDT
		qryParams.put("note", note);
		
		qryParams.put("transSeqId", transSeqId);
		if (!StringUtil.isBlank(transAt)) {
			qryParams.put("transAt", StringUtil.transferAmt(transAt));
		}
		
		qryParams.put("currCd", currCd);
		
		logger.debug(String.format("查询商户账户流水开始: %s", qryParams));

		Pager<ChnlMchntAccountFlow> pager = mchntAccountBO.qryMerAcctFlow(pageNum, pageSize, mon.substring(4,6), qryParams);
		logger.info("查询商户账户流水完成..");
		Pager<Map<String, String>> transferPager = commonBO.transferPager(pager, MerConstants.PAGE_CONF_MERACCTFLOW, ENTITY_TRANSFER,req);
		transferPager = commonBO.transferResultListByLang(transferPager, req); //再将某些栏位的查询结果，做多国语的转换
		return commonBO.buildSuccResp(MerConstants.PAGER_RESULT, transferPager);
	}
	
	/**
	 * 导出
	 */
	@RequestMapping("/export")  //导出
	public @ResponseBody AjaxResult export(String startDt,  String endDt, 
			String operateTpCat, String operateTp, String transSeqId, String transAt,
			String timeQryMethod, String note, String currCd,
			HttpServletResponse response, HttpServletRequest req) {

		logger.debug(String.format("导出商户账户流水: startDate=%s,  endDate=%s,  operateTp=%s, transSeqId=%s, transAt=%s", startDt, endDt, operateTp, transSeqId, transAt));

        String lang = I18nMsgUtils.getLanguage(req);
        
		String operateDt = null;
		if (!Utils.isEmpty(startDt))
			operateDt = StringUtil.left(startDt, 8);
		else
			operateDt = StringUtil.left(endDt, 8);
		AssertUtil.objIsNull(operateDt,mappingI18n(this.getClass().getSimpleName(),"日期为空", lang));
		
		Map<String, String> qryParams = new HashMap<String, String>();
		qryParams.put("chnlId", "00");
		qryParams.put("mchntCd", commonBO.getMchntCd());
		qryParams.put("operateTp", nullIfEmpty(operateTp));
		qryParams.put("operateTpCat", nullIfEmpty(operateTpCat));
		
		//qryParams.put("transDt", operateDt);
		
//		qryParams.put("startDate", nullIfEmpty(startDate));
//		qryParams.put("startTime", nullIfEmpty(startTime));
//		qryParams.put("endDate", nullIfEmpty(endDate));
//		qryParams.put("endTime", nullIfEmpty(endTime));
		
		qryParams.put("startDate", nullIfEmpty(StringUtil.left(startDt, 8)));
		qryParams.put("endDate", nullIfEmpty(StringUtil.left(endDt, 8)));
		qryParams.put("startTime", nullIfEmpty(StringUtil.right(startDt, 6)));//起始时间
		qryParams.put("endTime", nullIfEmpty(StringUtil.right(endDt, 6))); // 结束时间
		
//		qryParams.put("timeQryMethod", timeQryMethod); //UpdTS,CrtTS,OrdDT
		qryParams.put("timeQryMethod", timeQryMethod); //UpdTS,OrdDT
		qryParams.put("note", note);
		
		qryParams.put("transSeqId",nullIfEmpty(transSeqId));
		if (!StringUtil.isBlank(nullIfEmpty(transAt))) {
			qryParams.put("transAt", StringUtil.transferAmt(transAt));
		}
		
		qryParams.put("currCd", currCd);
		
		info("商户账户流水查询开始: " + qryParams);
		String mon = operateDt.substring(4,6);
		
		List<ChnlMchntAccountFlow> list = mchntAccountBO.qryMerAcctFlowList(mon, qryParams);
		
		System.out.println("List<ChnlMchntAccountFlow> list = "+ list.size());
		
		String mchntCd =commonBO.getMchntCd();
		AssertUtil.objIsNull(commonBO,mappingI18n(this.getClass().getSimpleName(),"商户号为空", lang));
		
		String fileNm = mchntCd+Utils.getRandomInt(1000, 9999)+MerConfigCache.getConfig(MerConstants.CONFIG_KEY_ACCFLOW_EXPORT_FILENM);
		String filePath = MerConfigCache.getConfig(MerConstants.CONFIG_KEY_TRANSLOG_EXPORT_FILE_PATH);
		
		Pager<Map<String, String>> pager = commonBO.transferList(list, MerConstants.PAGE_CONF_MERACCTFLOW, ENTITY_TRANSFER, req);
		pager = commonBO.transferResultListByLang(pager, req); //再将某些栏位的查询结果，做多国语的转换
		commonBO.exportAccflowExcel(pager,filePath, fileNm, Constant.UTF8, response);
		return null;
	}
	
	
	private String nullIfEmpty(String str) {
		if (Utils.isInSet(str, null,"undefined","null")) return null;
		return str;
	}
	
	
	/**
	 * 查询帐户账户明细
	 * @param model
	 * @param seqId
	 * @param startDate
	 * @param startTime
	 * @param endDate
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value="/acctFlowDetail", method=RequestMethod.GET)
	public String acctFlowDetail(Model model, String seqId, 
			String transDt,String transTm , HttpServletRequest req) {
        String lang = I18nMsgUtils.getLanguage(req);
		AssertUtil.strIsBlank(transTm, "transTm is blank.");
		String opDate = null;
		if (!Utils.isEmpty(transDt))
			opDate = transDt;
		else
			opDate = DateUtils.formatDateStr(transTm,DateUtils.DATE_FORMAT_10, DateUtils.DATE_FORMAT_8);
		AssertUtil.objIsNull(opDate,mappingI18n(this.getClass().getSimpleName(),"日期为空", lang));
		
		ChnlMchntAccountFlow flow = mchntAccountBO.getMerAcctFlow(seqId,opDate.substring(4,6));
		AssertUtil.objIsNull(flow, mappingI18n(this.getClass().getSimpleName(),"未找到流水明细:", lang) + seqId);
		model.addAttribute(MerConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConfLst(MerConstants.PAGE_CONF_MERACCTFLOW,I18nMsgUtils.getLanguage(req)));
		model.addAttribute(MerConstants.ENTITY_RESULT, 
				commonBO.transferEntity(flow, MerConstants.PAGE_CONF_MERACCTFLOW, ENTITY_TRANSFER, I18nMsgUtils.getLanguage(req)));
		return "acctFlowDetail";
	}
	
}
