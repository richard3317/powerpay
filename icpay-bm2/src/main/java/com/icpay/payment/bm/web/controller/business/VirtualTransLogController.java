package com.icpay.payment.bm.web.controller.business;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.cache.BankNumCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.bm.web.util.DateUtils;
import com.icpay.payment.common.constants.Constant.TXNS_TATUS;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.exception.SystemException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.TransLog;
import com.icpay.payment.db.dao.mybatis.model.TxnLogView;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TransLogMapping;
import com.icpay.payment.db.service.ITransLogService;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.service.InternalGatewayService;
import com.icpay.payment.service.api.HttpClientResponse;

@Controller
@RequestMapping("/virtualTransLog")
public class VirtualTransLogController extends TransLogBaseController {
	private static final String RESULT_BASE_URI = "virtualTransLog";
	private static final String intTxnType ="0092";
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
		model.addAttribute("today", simpleDateFormat.format(new Date())+" 000000");
		model.addAttribute("today_end", simpleDateFormat.format(new Date())+" 235959");
		model.addAttribute("bankCodeList", BankNumCache.getBankNumsList());
		return super.toManage(model, false, RESULT_BASE_URI);
	}

	@RequestMapping(value = "/monitor.do", method = RequestMethod.GET)
	public String monitor(Model model) {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        model.addAttribute("today", simpleDateFormat.format(new Date())+" 000000");
        model.addAttribute("today_end", simpleDateFormat.format(new Date())+" 235959");
		model.addAttribute("pageSize", 30);
		return RESULT_BASE_URI + "/monitor";
	}

	@RequestMapping(value = "/monitor/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult monitorQry(Model model,int monitorNum) {
		String today = DateUtil.now8();
		if (monitorNum <= 0) {
			monitorNum = 20;
		}
		Map<String, String> qryParamMap = this.getQryParamMap();
		return this.query(model, BMConstants.PAGE_CONF_TRANSLOG, qryParamMap, 1, monitorNum, null);
	}
	
	@RequestMapping(value = "/compare.do", method = RequestMethod.GET)
	public String compareChnlTransLog(Model model) {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        model.addAttribute("today", simpleDateFormat.format(new Date())+" 000000");
        model.addAttribute("today_end", simpleDateFormat.format(new Date())+" 235959");
		model.addAttribute("pageNum", 1);
		model.addAttribute("pageSize", 30);
		return RESULT_BASE_URI + "/compareTransLog";
	}
	
	@RequestMapping(value = "/compare/submit.do", method = RequestMethod.POST)
	@QryMethod
	public void compareChnlTransLogSubmit(
			Model model, String mon, 
			//@RequestParam(value = "chnlLogFile", required = false) MultipartFile chnlLogFile, 
			MultipartFile chnlLogFile, 
			HttpServletResponse response) {
		
		model.addAttribute("qryParamMap", this.getQryParamMap());
		
		System.out.println(chnlLogFile);
		if (chnlLogFile!=null)
			System.out.println(chnlLogFile.getOriginalFilename());
			
		//return this.query(model, BMConstants.PAGE_CONF_TRANSLOG, this.getQryParamMap(), pageNum, pageSize, mon);
		String filename = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TRANS_COMPARE_FILENM);
		compareLogs(model, BMConstants.PAGE_TBLWITH_DRAW_LOG, this.getQryParamMap(), mon, chnlLogFile, filename, response);
	}


	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(Model model, int pageNum, int pageSize, String mon) {
		Map<String, String> qryParamMap = this.getQryParamMap();
		model.addAttribute("qryParamMap", this.getQryParamMap());
		String startDate=qryParamMap.get("startDate");
		String endDate=qryParamMap.get("endDate");

		qryParamMap.put("startDate",StringUtil.left(startDate,8));
		qryParamMap.put("endDate",StringUtil.left(endDate,8));
		qryParamMap.put("startTime",StringUtil.right(startDate,6));
		qryParamMap.put("endTime",StringUtil.right(endDate,6));
		qryParamMap.put("today", startDate);
		qryParamMap.put("today_end", endDate);
		return this.query(model, BMConstants.PAGE_CONF_VIRTUALTRANSLOG, qryParamMap, pageNum, pageSize, mon);
	}
	
	@RequestMapping(value = "/amtSum.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody JSONObject amtSum(Model model, String mon) {
		Map<String, String> qryParamMap = this.getQryParamMap();
		model.addAttribute("qryParamMap", this.getQryParamMap());
		String startDate=qryParamMap.get("startDate");
		String endDate=qryParamMap.get("endDate");

		qryParamMap.put("startDate",StringUtil.left(startDate,8));
		qryParamMap.put("endDate",StringUtil.left(endDate,8));
		qryParamMap.put("startTime",StringUtil.right(startDate,6));
		qryParamMap.put("endTime",StringUtil.right(endDate,6));
		return this.summary(this.getQryParamMap(), mon);
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, String transSeqId, String mon) {
		return super.detail(model, RESULT_BASE_URI, BMConstants.PAGE_CONF_TRANSLOG, transSeqId, mon);
	}
	
	@RequestMapping(value = "/queryChnlResult.do")
	public String queryChnlResult(Model model, String transSeqId, String orderDate) {
		return super.queryChnlResult(model, RESULT_BASE_URI, BMConstants.PAGE_CHNL_QRY_RESP, transSeqId, orderDate);
	}
	
	@RequestMapping(value = "/cancelPayment.do")
	public @ResponseBody AjaxResult cancelPayment(Model model, String chnlOrderId, String extTransDt, String transSeqId,String chnlMchntCd) {
		
		String msg="";
		try {
			AssertUtil.strIsBlank(transSeqId, "交易编号为空");
			
			ITransLogService service = this.getDBService(ITransLogService.class);
			TransLog log = service.selectByPrimaryKey(transSeqId, extTransDt.substring(4, 6));
			
			AssertUtil.objIsNull(log, "未找到交易明细:" + transSeqId);
			
			if (!Utils.isEmpty(log.getTxnState())){
				if (!TXNS_TATUS.PROC.equals(log.getTxnState())) {
					msg = String.format("取消失败，交易状态非处理中，%s", transSeqId);
					return commonBO.buildErrorResp(msg);
				}
			}
			
			Map param = new HashMap<String, String>();
			Map req = new HashMap<String, String>();
			//商户订单编号或支付平台生成的支付单id
			req.put("chnlOrderId", chnlOrderId);
			//渠道商户号
			req.put("chnlMerId", chnlMchntCd);
			//请求上游得到响应
			Map<String, String> res = invokeChnlService("V0", intTxnType, req, param);
			this.info("[cancelPayment] 渠道端响应: %s" , res );
			if (!res.isEmpty() && res != null) {
				if("true".equals(res.get("status"))) {
					msg = String.format("交易单号： %s ，取消充币单成功!", transSeqId);
					//响应完更改状态
					String opr = this.getSessionUsrId();
					String opTime = DateUtils.dateToStr19(new Date());
					//处理中调整至失败
					this.info("[cancelPayment] 取消充币单：交易流水号=%s, 订单日期=%s, 交易状态调整=%s, 备注=%s, 操作人=%s, 操作时间=%s", transSeqId, extTransDt, "11", "", opr, opTime);
					String comment = "取消充币单調整为失败，交易流水号= " + transSeqId ;
					this.chnlMerAccService().adjustTxnRecord(transSeqId, extTransDt, "11", opr, comment);
					return commonBO.buildSuccResp(msg);	

				}else {
					msg = String.format("交易 %s ，取消充币单失敗!", transSeqId);
					this.error("[cancelPayment] "+msg);
				}

			}else {
				msg = String.format("交易 %s ，取消充币单异常!", transSeqId);
				this.error("[cancelPayment] "+msg);
			}
			
		} catch (Exception e) {
			msg = String.format("交易 %s ，取消充币单异常!\n%s", transSeqId, e.getMessage());
			this.error("[cancelPayment] "+msg,e);
		}
		return commonBO.buildErrorResp(msg);
	}
	
	
	// 根据条件查询记录（导出）
	@RequestMapping(value = "/exportTransLog.do", method = {RequestMethod.POST, RequestMethod.GET})
	@QryMethod
	public void exportTransLog(Model model, String mon, HttpServletResponse response) {
		Map<String, String> qryParamMap = this.getQryParamMap();
		model.addAttribute("qryParamMap", this.getQryParamMap());
		String startDate=qryParamMap.get("startDate");
		String endDate=qryParamMap.get("endDate");

		qryParamMap.put("startDate",StringUtil.left(startDate,8));
		qryParamMap.put("endDate",StringUtil.left(endDate,8));
		qryParamMap.put("startTime",StringUtil.right(startDate,6));
		qryParamMap.put("endTime",StringUtil.right(endDate,6));
		String filename = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TRANS_EXPORT_FILENM);
		super.export(model, BMConstants.PAGE_CONF_TRANSLOG, qryParamMap, mon, filename, response);
	}

	
	@RequestMapping(value = "/transSateModify.do", method = RequestMethod.GET)
	public String adjustTxnState(Model model, String transSeqId, String mon, String txnState) {
		return super.adjustTxnState(model, RESULT_BASE_URI, BMConstants.PAGE_CONF_TRANSLOG, transSeqId, mon,
				txnState);
	}
	
	@RequestMapping(value = "/mandatoryModify.do", method = RequestMethod.GET)
	public String adjustMandatoryTxnState(Model model, String transSeqId, String mon, String txnState) {
		return super.adjustMandatoryTxnState(model, RESULT_BASE_URI, BMConstants.PAGE_CONF_TRANSLOG, transSeqId, mon,
				txnState);
	}

	@RequestMapping(value = "/transSateModify/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult adjustTxnStateSubmit(Model model, TxnLogView tr, String mon, String opType,
			String comment) {
		return super.adjustTxnStateSubmit(model, tr, mon, opType, comment);
	}

	
	@RequestMapping(value = "/backToManage.do", method = RequestMethod.GET)
	public String backToManage(Model model) {
		model.addAttribute("bankCodeList", BankNumCache.getBankNumsList());
		return super.backToManage(model, RESULT_BASE_URI);
	}
	
	/**
	 * 重发通知
	 * @param transSeqId
	 * @param mon
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/renotify.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult resendNotify(String transSeqId, String orderDate, HttpServletResponse response) {
		return super.resendNotify(transSeqId, orderDate, response);
	}
	
	private Map<String, String> invokeChnlService(String chnlId, String intTxnType, Map req, Map param) throws SystemException {
		InternalGatewayService svc = getSvc();
		return svc.invokeChnlService(chnlId, intTxnType, req, param);
		
	}
	
	private static InternalGatewayService getSvc() throws SystemException {
		InternalGatewayService svc = (InternalGatewayService) ServiceProxy.getService(InternalGatewayService.class);
		if (svc == null)
			throw new SystemException("InternalGatewayService not found!");
		return svc;
	}
	
}


////--------------------
//
//@Controller
//@RequestMapping("/transLog")
//public class TransLogController extends BaseController {
//	private static final Logger logger = Logger.getLogger(TransLogController.class);
//	private static final String RESULT_BASE_URI = "transLog";
//	PrintWriter out = null;
//	
//
//	private String amtSum = "";
//	private String feeSum = "";
//	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
//		@Override
//		public void transfer(Map<String, String> m) {
//			BigDecimal transAt = new BigDecimal(m.get("transAt"));
//			m.put("transAtDesc", transAt.movePointLeft(2).toString());
//
//			String accNo = m.get("accNo");
//			if (StringUtil.isBlank(accNo)) {
//				m.put("accNoDesc", "");
//			} else {
////				m.put("accNoDesc", accNo.length() > 7 ? StringUtil.mask(accNo, 4, accNo.length() - 6, '*') : accNo);
//				m.put("accNoDesc", BizUtils.strMask(accNo, "*", 4, 0, 6));
//			}
//
//			String intTransCd = m.get("intTransCd");
//			m.put("intTransCdDesc", EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, true));
//
//			String rspCd = m.get("rspCd");
//			if (!StringUtil.isBlank(rspCd) && rspCd.length() > 2) {
//				m.put("rspCdDesc", rspCd + "-" + m.get("errMsg"));
//			} else {
//				m.put("rspCdDesc", rspCd);
//			}
//
//			String txnState = m.get("txnState");
//			if ("00".equals(txnState)) {
//				m.put("stateDesc", "00-初始状态");
//			} else if ("01".equals(txnState)) {
//				m.put("stateDesc", "01-等待付款");
//			} else if ("10".equals(txnState)) {
//				m.put("stateDesc", "10-交易成功");
//			} else if ("20".equals(txnState)) {
//				m.put("stateDesc", "20-交易失败");
//			} else {
//				m.put("stateDesc", "30-其他");
//			}
//
//			String transFee = m.get("transFee");
//			m.put("transFeeDesc", StringUtil.formateAmt(transFee));
//
//			String transChnl = m.get("transChnl");
//			m.put("transChnl", EnumUtil.translate(TxnEnums.ChnlId.class, transChnl, true));
//
//		}
//	};
//
//	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
//	public String manage(Model model) {
//		model.addAttribute("today", DateUtil.now8());
//		return super.toManage(model, false, RESULT_BASE_URI);
//	}
//
//	@RequestMapping(value = "/monitor.do", method = RequestMethod.GET)
//	public String monitor(Model model) {
//		model.addAttribute("pageSize", 30);
//		return RESULT_BASE_URI + "/monitor";
//	}
//
//	@RequestMapping(value = "/monitor/qry.do", method = RequestMethod.POST)
//	@QryMethod
//	public @ResponseBody AjaxResult monitorQry(int monitorNum) {
//		String today = DateUtil.now8();
//		if (monitorNum == 0) {
//			monitorNum = 20;
//		}
//		ITransLogService transLogService = this.getDBService(ITransLogService.class);
//		Pager<TransLogMapping> p = transLogService.selectTransLogMappingByPage(1, monitorNum, today.substring(4, 6),
//				this.getQryParamMap());
//		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT,
//				commonBO.transferPager(p, BMConstants.PAGE_CONF_TRANSLOG, ENTITY_TRANSFER));
//	}
//
//	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
//	@QryMethod
//	public @ResponseBody AjaxResult qry(int pageNum, int pageSize, String mon) {
//		ITransLogService transLogService = this.getDBService(ITransLogService.class);
//		Pager<TransLogMapping> p = transLogService.selectTransLogMappingByPage(pageNum, pageSize, mon.substring(4),
//				this.getQryParamMap());
////		List<TransLogMapping> list = p.getResultList();
////		Long transAtSum = 0L;
////		Long transFeeSum = 0L;
////		for (TransLogMapping item : list) {
////			transAtSum = transAtSum + this.judegIsNull(item.getTransAt(), 0L);
////			transFeeSum = transFeeSum + this.judegIsNull(item.getTransFee(), 0L);
////		}
//		
////		TransLog trans = transLogService.countTransAt(mon.substring(4), this.getQryParamMap());
//		
////		String transAtSum = this.getAmtSum();
////		String transFeeSum = this.getFeeSum();
////		if(trans != null) {
////			String transAtSum = trans.getCountTransAt();
////			String transFeeSum = trans.getCountTransFee();
////			
////			this.setAmtSum(transAtSum);
////			this.setFeeSum(transFeeSum);
////		}
//		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT,
//				commonBO.transferPager(p, BMConstants.PAGE_CONF_TRANSLOG, ENTITY_TRANSFER));
//	}
//
//	@RequestMapping(value = "/amtSum.do", method = RequestMethod.POST)
//	public @ResponseBody JSONObject amtSum( String mchntCd,String orderId, 
//			String intTransCd,String chnlId,String chnlMchntCd,String transSeqId, 
//			String startExtTransDt, String endExtTransDt,String mon,
//			String minTransAt,String maxTransAt,String txnState,
//			String ifPay) {
//		
//		Map<String,String> map1 = new HashMap<String,String>();
//		map1.put("mchntCd", mchntCd);
//		map1.put("orderId", orderId);
//		map1.put("intTransCd", intTransCd);
//		map1.put("chnlId", chnlId);
//		map1.put("chnlMchntCd", chnlMchntCd);
//		map1.put("transSeqId", transSeqId);
//		map1.put("startExtTransDt", startExtTransDt);
//		map1.put("endExtTransDt", endExtTransDt);
//		map1.put("minTransAt", minTransAt);
//		map1.put("maxTransAt", maxTransAt);
//		map1.put("txnState", txnState);
//		map1.put("ifPay", ifPay);
//		ITransLogService transLogService = this.getDBService(ITransLogService.class);
//		TransLog trans = transLogService.countTransAt(mon.substring(4), map1);
//		
////		String transAtSum = this.getAmtSum();
////		String transFeeSum = this.getFeeSum();
//		
//		String transAtSum = trans.getCountTransAt();
//		String transFeeSum = trans.getCountTransFee();
//		transAtSum = new BigDecimal(transAtSum.toString()).divide(new BigDecimal("100")).toString();
//		transFeeSum = new BigDecimal(transFeeSum.toString()).divide(new BigDecimal("100")).toString();
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("transAtSum", transAtSum);
//		map.put("transFeeSum", transFeeSum);
//		JSONObject result = (JSONObject) JSON.toJSON(map);
//		return result;
//	}
//
//	@RequestMapping(value = "/detail.do")
//	public String detail(Model model, String transSeqId, String mon) {
//		if (StringUtil.isBlank(mon)) {
//			String today = DateUtil.now8();
//			mon = today.substring(0, 6);
//		}
//		ITransLogService transLogService = this.getDBService(ITransLogService.class);
//		TransLog entity = transLogService.selectByPrimaryKey(transSeqId, mon.substring(4));
//
//		// IMchntInfoService mchntService =
//		// this.getDBService(IMchntInfoService.class);
//		// MchntInfo dbEntity =
//		// mchntService.selectByPrimaryKey(entity.getMchntCd());
//
//		AssertUtil.objIsNull(entity, "未找到交易信息:" + transSeqId);
//		model.addAttribute(BMConstants.DETAIL_CONF_LST, PageConfCache.getDetailConf(BMConstants.PAGE_CONF_TRANSLOG));
//		model.addAttribute(BMConstants.ENTITY_RESULT,
//				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_TRANSLOG, ENTITY_TRANSFER));
//		// model.addAttribute("mchntCnNm",dbEntity.getMchntCnNm());
//		return super.toDetail(model, RESULT_BASE_URI);
//	}
//
//	// 根据条件查询记录（导出）
//	@RequestMapping(value = "/exportTransLog.do", method = RequestMethod.GET)
//	public @ResponseBody AjaxResult exportTransLog(String mchntCd,String orderId, 
//					String intTransCd,String chnlId,String chnlMchntCd,String transSeqId, 
//					String startExtTransDt, String endExtTransDt,String mon,
//					String minTransAt,String maxTransAt,String txnState,
//					String ifPay,HttpServletResponse response) {
////		Properties pro = Helper.loadProperties("systemData.properties", TransLogController.class);
////		String mchntCd = request.getParameter("mchntCd");
////		String orderId = request.getParameter("orderId");
////		String intTransCd = request.getParameter("intTransCd");
////		String chnlId = request.getParameter("chnlId");
////		String chnlMchntCd = request.getParameter("chnlMchntCd");
////		String transSeqId = request.getParameter("transSeqId");
////		String startExtTransDt = request.getParameter("startExtTransDt");
////		String endExtTransDt = request.getParameter("endExtTransDt");
////		String mon = request.getParameter("mon");
////		String minTransAt = request.getParameter("minTransAt");
////		String maxTransAt = request.getParameter("maxTransAt");
////		String txnState = request.getParameter("txnState");
////		String ifPay = request.getParameter("ifPay");
//		Map<String, String> qryParamMap = new HashMap<String, String>();
//		qryParamMap.put("mchntCd", mchntCd);
//		qryParamMap.put("orderId", orderId);
//		qryParamMap.put("intTransCd", intTransCd);
//		qryParamMap.put("chnlId", chnlId);
//		qryParamMap.put("chnlMchntCd", chnlMchntCd);
//		qryParamMap.put("transSeqId", transSeqId);
//		qryParamMap.put("startExtTransDt", startExtTransDt);
//		qryParamMap.put("endExtTransDt", endExtTransDt);
//		qryParamMap.put("minTransAt", minTransAt);
//		qryParamMap.put("maxTransAt", maxTransAt);
//		qryParamMap.put("txnState", txnState);
//		qryParamMap.put("ifPay", ifPay);
//		
//		ITransLogService transLogService = this.getDBService(ITransLogService.class);
//		List<TransLogMapping> listTransLog=transLogService.selectTransLogMapping(mon.substring(4), qryParamMap);
//		
////		String path = request.getSession().getServletContext().getRealPath("/upload/exportTransLog");
////		String downUrl = ExportUtil.writeTransLog(path, listTransLog, false);
////		try {
////			out = response.getWriter();
////			out.print(downUrl);
////		} catch (IOException e) {
////			e.printStackTrace();
////		} finally {
////			out.flush();
////			out.close();
////		}
//		
//		String fileNm = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TRANS_EXPORT_FILENM);
//		String filePath = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_EXPORT_FILE_PATH);
//		commonBO.exportTransLogExcel(listTransLog,filePath, fileNm, Constant.UTF8, response);
//		return null;
//	}
//
//	// 导出流水后删除
//	/*@RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
//	public void deleteFile(HttpServletRequest request, HttpServletResponse response) {
//		String path = request.getSession().getServletContext().getRealPath("/upload");
//		boolean success = ExportUtil.deleteDir(new File(path));
//		if (success) {
//			logger.info("删除已填充目录成功: " + path);
//		} else {
//			logger.info("删除已填充目录失败: " + path);
//		}
//	}*/
//	
//	@RequestMapping(value ="/transSateModify.do" , method =RequestMethod.GET)
//	public String transSateModify(Model model,String transSeqId,String mon,String txnState) {
//		//查询流水状态
//		
//		if (StringUtil.isBlank(mon)) {
//			if (!Utils.isEmpty(transSeqId)) {
//				mon = transSeqId.substring(0, 6);
//			}
//			else {
//				String today = DateUtil.now8();
//				mon = today.substring(0, 6);
//			}
//		}
//
//		ITransLogService transLogService = this.getDBService(ITransLogService.class);
//		TransLog entity = transLogService.selectByPrimaryKey(transSeqId, mon.substring(4));
//		AssertUtil.objIsNull(entity, "未找到交易信息:" + transSeqId);
//		model.addAttribute(BMConstants.ENTITY_RESULT,
//				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_TRANSLOG, ENTITY_TRANSFER));
//		//return super.toEdit(model, RESULT_BASE_URI);
//		return super.toPage(model, RESULT_BASE_URI, "adjust"); //跳转到 adjust.ftl
//	}
//	
//	@RequestMapping(value ="/transSateModify/submit.do" , method =RequestMethod.POST)
//	public  @ResponseBody AjaxResult transSateModifySubmit(Model model,TransLog tr,String mon, String opType, String comment) {
//		AssertUtil.objIsNull(tr, "TransLog is null.");
//		String opr = this.getSessionUsrId();
//		if (!Utils.isEmpty(tr.getExtTransDt()))
//			mon = tr.getExtTransDt();
//		this.chnlMerAccService().adjustTxnRecord(tr.getTransSeqId(), mon, opType, opr, comment);
//		return commonBO.buildSuccResp();
//	}
//
////參考：主要邏輯
//
////	void adjustTxnRecord() {
////		this.getLogger().info(String.format("[adjustTxnRecord] 交易记录调帐， 参数: %s, %s, %s, %s, %s; ", txnId, txnDate, opType, operator, note));
////		ChnlMerAccService svc = ServiceProxy.getService(ChnlMerAccService.class);
////		svc.adjustTxnRecord(txnId, txnDate, opType, operator, note);
////		this.getLogger().info("[adjustTxnRecord]  交易记录调帐结束，无额外错误讯息.");
////		println("");
////	}
//
//	
//	@RequestMapping(value = "/backToManage.do", method = RequestMethod.GET)
//	public String backToManage(Model model) {
//		model.addAttribute("today", DateUtil.now8());
//		return super.toManage(model, true, RESULT_BASE_URI);
//	}
//
//	public String getAmtSum() {
//		return amtSum;
//	}
//
//	public void setAmtSum(String amtSum) {
//		this.amtSum = amtSum;
//	}
//
//	public String getFeeSum() {
//		return feeSum;
//	}
//
//	public void setFeeSum(String feeSum) {
//		this.feeSum = feeSum;
//	}
//
//	ChnlMerAccService cmas=null;
//	protected ChnlMerAccService chnlMerAccService(){
//		if(cmas==null){
//			cmas=ServiceProxy.getService(ChnlMerAccService.class);
//		}
//		return cmas;
//	}
//}
