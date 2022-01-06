package com.icpay.payment.bm.web.controller.business;

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
import com.icpay.payment.db.dao.mybatis.model.TxnLogView;
import com.icpay.payment.db.service.ITxnLogViewService;
import com.icpay.payment.service.client.InternalGatewayClient;

@Controller
@RequestMapping("/pay4b2cb")
public class Pay4b2cbController extends TransLogBaseController {

	private static final String RESULT_BASE_URI = "pay4b2cb";

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
	
	@RequestMapping(value = "/appr.do", method = RequestMethod.GET)
	public String approve(Model model, String transSeqId, String mon, String txnState) {
//		return super.adjustTxnState(model, RESULT_BASE_URI, BMConstants.PAGE_OFFPAY_TRANS_LOG, transSeqId, mon,
//				txnState);
		debug("/appr.do ; mon=%s", mon);
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
		model.addAttribute(BMConstants.ENTITY_RESULT, commonBO.transferEntity(entity, BMConstants.PAGE_OFFPAY_TRANS_LOG, ENTITY_TRANSFER));
		return super.toPage(model, RESULT_BASE_URI, "appr");
		
	}

	@RequestMapping(value = "/appr/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult adjustTxnStateSubmit(Model model, TxnLogView tr, String mon, String apprResult,
			String comment) {
		//return super.adjustTxnStateSubmit(model, tr, mon, opType, comment);
		AssertUtil.strIsBlank(apprResult, "请选择审核结果");
		
		if (StringUtil.isBlank(comment) && !TXNS_TATUS.OK.equals(txnStatusFromAppr(apprResult))) {
			return commonBO.buildErrorResp("请备注失败原因");
		}
		
		Map<String,String> notifyReq= Utils.newMap(
				CHNL_MSG.channel, tr.getTransChnl(),
				INTER_MSG.intTxnType, tr.getIntTransCd(),
				CHNL_MSG.chnlMerId, tr.getChnlMchntCd(),
				CHNL_MSG.chnlOrderId, tr.getChnlOrderId(),
				CHNL_MSG.chnlTxnId, tr.getChnlTransId(),
				CHNL_MSG.chnlTxnStatus, txnStatusFromAppr(apprResult),
				CHNL_MSG.chnlTxnStatusDesc, getTxnStatusDesc(apprResult,comment),
				CHNL_MSG.chnlRespCd, RspCd._00,
				CHNL_MSG.chnlRespMsg, comment
				);
		
		Map<String, String> resp=null;
		try {
			//notifyReq.put(INTER_MSG.txnSrc, TxnSource.MER.getCode());
			info("[InternalGatewayClient][B2B/B2C网银充值审核] "+notifyReq  + " 操作员：" + super.getSessionUsrId());
			resp = InternalGatewayClient.notify(notifyReq);
			info("[InternalGatewayClient][B2B/B2C网银充值审核通知响应] "+resp);
			return commonBO.buildAjaxResp(AjaxRespEnums.SUCC,"审核请求成功");
		}catch(Exception e ) {
			error("[InternalGatewayClient][B2B/B2C网银充值审核结果通知][ERROR] 调用内部取现服务接口异常：" + e.getMessage(), e);
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

}
