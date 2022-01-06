package com.icpay.payment.bm.web.controller.business;

import static com.icpay.payment.risk.BmUser.bmUser;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant.TXNS_TATUS;
import com.icpay.payment.common.constants.Names.CHNL_MSG;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.constants.RspCd;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.enums.AjaxRespEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.TransProof;
import com.icpay.payment.db.dao.mybatis.model.TxnLogView;
import com.icpay.payment.db.service.ITransProofService;
import com.icpay.payment.db.service.ITxnLogViewService;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;
import com.icpay.payment.service.client.InternalGatewayClient;
import com.icpay.payment.service.oss.OssUtils;

@Controller
@RequestMapping("/offpay")
public class OffPayController extends TransLogBaseController {

	private static final String RESULT_BASE_URI = "offpay";

	// ------------取现查询
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		return super.manage(model, RESULT_BASE_URI);
	}

	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(Model model, int pageNum, int pageSize, String mon) {
		String orderBy="rec_upd_ts ASC";
		if (this.getQryParamMap()!=null)
			this.getQryParamMap().put("orderBy", orderBy);
		return this.query(model, BMConstants.PAGE_OFFPAY_TRANS_LOG, this.getQryParamMap(), pageNum, pageSize, mon);
	}

	@RequestMapping(value = "/detail.do")
	public String detail(Model model, String transSeqId, String mon) {
		return super.detail(model, RESULT_BASE_URI, BMConstants.PAGE_OFFPAY_TRANS_LOG, transSeqId, mon);
	}
	
	@RequestMapping(value = "/offpayAppr.do", method = RequestMethod.GET)
	public String offpayApprove(Model model, String transSeqId, String mon, String txnState) {
//		return super.adjustTxnState(model, RESULT_BASE_URI, BMConstants.PAGE_OFFPAY_TRANS_LOG, transSeqId, mon,
//				txnState);
		debug("/offpayAppr.do ; mon=%s", mon);
		// 查询流水状态
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

		AssertUtil.objIsNull(entity, "未找到交易信息:" + transSeqId);
		
		//查询汇款凭证
		ITransProofService proofSvc = this.getDBService(ITransProofService.class);
		TransProof proof = proofSvc.selectByPrimaryKey(transSeqId);
		String proofTitle = "";
		String imageUrl = "";
		if (proof != null) {
			String fileName = proof.getFileDesc();
			imageUrl = OssUtils.getImageUrl(fileName);
		} else {
			proofTitle = "无凭证";
		}
		model.addAttribute("proofTitle", proofTitle);
		model.addAttribute("imageUrl", imageUrl);
		model.addAttribute(BMConstants.ENTITY_RESULT, commonBO.transferEntity(entity, BMConstants.PAGE_OFFPAY_TRANS_LOG, ENTITY_TRANSFER));
		return super.toPage(model, RESULT_BASE_URI, "offpayAppr");
		
	}

	@RequestMapping(value = "/offpayAppr/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult adjustTxnStateSubmit(Model model, TxnLogView tr, String mon, String apprResult,
			String comment) {
		String optype="";
		if(apprResult.equals("1")) {
			optype="12";//審核成功
		}else {
			optype="14";//審核失敗
		}
		super.adjustTxnStateSubmit(model, tr, mon, optype, comment);
		AssertUtil.strIsBlank(apprResult, "请选择审核结果");
		
		if (StringUtil.isBlank(comment) && !TXNS_TATUS.OK.equals(txnStatusFromAppr(apprResult))) {
			return commonBO.buildErrorResp("请备注失败原因");
		}
		
		Map<String,String> notifyReq= Utils.newMap(
				CHNL_MSG.channel, tr.getTransChnl(),
				INTER_MSG.intTxnType, "0191", //tr.getIntTransCd(),
				CHNL_MSG.chnlMerId, tr.getChnlMchntCd(),
				CHNL_MSG.chnlTxnId, tr.getChnlTransId(),
				CHNL_MSG.chnlOrderId, tr.getChnlOrderId(),
				CHNL_MSG.chnlTxnStatus, txnStatusFromAppr(apprResult),
				CHNL_MSG.chnlTxnStatusDesc, getTxnStatusDesc(apprResult,comment),
				CHNL_MSG.chnlRespCd, RspCd._00,
				CHNL_MSG.chnlRespMsg, comment
				);
		
		Map<String, String> resp=null;
		try {
			//notifyReq.put(INTER_MSG.txnSrc, TxnSource.MER.getCode());
			info("[InternalGatewayClient][电汇自充审核] "+notifyReq + " 操作员：" + super.getSessionUsrId());
			resp = InternalGatewayClient.notify(notifyReq);;
			info("[InternalGatewayClient][电汇自充审核通知响应] "+resp);
			
			RiskEvent.bm()
				.who(bmUser(commonBO.getSessionUser().getUsrId()))
				.event(RISK.Event.Offpay_Appr)
				.result(RISK.Result.Ok)
				.target(("1".equals(apprResult) ? "成功" : "失败") + "|" + comment)
				.message(String.format("用户： %s, 电汇自充审核请求成功", commonBO.getSessionUser().getUsrId()))
				.params(notifyReq)
				.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
				.submit();
			
			return commonBO.buildAjaxResp(AjaxRespEnums.SUCC,"审核请求成功");
		}catch(Exception e ) {
			error("[InternalGatewayClient][电汇自充审核结果通知][ERROR] 调用内部取现服务接口异常：" + e.getMessage(), e);
			
			RiskEvent.bm()
				.who(bmUser(commonBO.getSessionUser().getUsrId()))
				.event(RISK.Event.Offpay_Appr)
				.result(RISK.Result.Error)
				.message(String.format("用户： %s, 电汇自充审核请求失败", commonBO.getSessionUser().getUsrId()))
				.params(notifyReq)
				.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
				.submit();
			
			return commonBO.buildAjaxResp(AjaxRespEnums.ERROR,e.getMessage());
		}
	}
	
	protected String getTxnStatusDesc(String apprResult, String comment) {
		String status=txnStatusFromAppr(apprResult);
		if (TXNS_TATUS.OK.equals(status)) {
			return "交易成功"+getDesc("，", comment);
		}
		else if (TXNS_TATUS.FAILED.equals(status)) {
			return "交易失败"+getDesc("，", comment);
		}
		return "其他状态"+getDesc("，", comment);
	}
	
	protected String getDesc(String prefix, String desc) {
		if (Utils.isEmpty(desc)) return "";
		return prefix+desc;
	}
	
	protected String txnStatusFromAppr(String apprResult) {
		if ("1".equals(apprResult))
			return TXNS_TATUS.OK;
		else if ("0".equals(apprResult))
			return TXNS_TATUS.FAILED;
		else return TXNS_TATUS.OTHER;
	}

	@RequestMapping(value = "/backToManage.do", method = RequestMethod.GET)
	public String backToManage(Model model) {
		return super.backToManage(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/amtSum.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody JSONObject amtSum(Model model, String mon) {
		model.addAttribute("qryParamMap", this.getQryParamMap());
		return this.summary(this.getQryParamMap(), mon);
	}

	// 根据条件查询记录（导出）
	@RequestMapping(value = "/exportTransLog.do", method = {RequestMethod.POST, RequestMethod.GET})
	@QryMethod
	public void exportTransLog(Model model, String mon, HttpServletResponse response) {
		String filename = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TRANS_EXPORT_FILENM);
		super.export(model, BMConstants.PAGE_OFFPAY_TRANS_LOG, this.getQryParamMap(), mon, filename, response);
	}
	
	//查看汇款凭证
	@RequestMapping(value = "/viewImage.do")
	public String viewImage(Model model, String transSeqId) {
		this.debug("[viewImage] /viewImage.do, transSeqId: %s", transSeqId);
		ITransProofService svc = this.getDBService(ITransProofService.class);
		TransProof entity = svc.selectByPrimaryKey(transSeqId);
		AssertUtil.objIsNull(entity, "未找到汇款凭证:" + transSeqId);
		String imageUrl = "";
		if (entity != null) {
			String fileName = entity.getFileDesc();
			imageUrl = OssUtils.getImageUrl(fileName);
		}
		model.addAttribute("imageUrl", imageUrl);
		return RESULT_BASE_URI + "/" + "viewImage";
	}
	
	@RequestMapping(value = "/countOffPay.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody JSONObject countOffPay(Model model, String mon) {
		model.addAttribute("qryParamMap", this.getQryParamMap());
		return this.count(this.getQryParamMap(), mon);
	}

}
