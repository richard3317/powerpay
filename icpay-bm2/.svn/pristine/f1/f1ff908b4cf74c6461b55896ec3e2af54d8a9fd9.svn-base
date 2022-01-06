package com.icpay.payment.bm.web.controller.business;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.MapHelper;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.EventLog;
import com.icpay.payment.db.service.IEventLogService;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.service.RiskEventCommitService;
import com.icpay.utils.FormatedString;

/**
 * 操作日志
 */
@Controller
@RequestMapping("/eventLog")
public class EventLogControlle extends BaseController {

	private static final Logger logger = Logger.getLogger(EventLogControlle.class);

	private static final String RESULT_BASE_URI = "eventLog";

	private static final IEntityTransfer ACCFLOW_ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			MapHelper<String, String> mh = new MapHelper<String, String>(m);

			String logEvent = m.containsKey("logEvent") ? m.get("logEvent") : "";
			m.put("logEvent", EnumUtil.translate(RISK.Event.class, logEvent, false));

			String logParams = m.containsKey("logParams") ? m.get("logParams") : "";
			String reqIp = "";
			try {
				JSONObject jObj = new JSONObject(logParams);
				if (jObj.has("reqIp")) {
					reqIp = jObj.get("reqIp").toString();
				}
			} catch (JSONException e) {
				logger.error("logParams格式解析出错", e);
			}
	    	m.put("logIp", reqIp);

	    	String logEventResult = m.containsKey("logEventResult") ? m.get("logEventResult") : "";
	    	m.put("logEventResult", EnumUtil.translate(RISK.Result.class, logEventResult, false));

	    	String logEventReason = m.containsKey("logEventReason") ? m.get("logEventReason") : "";
	    	m.put("logEventReason", EnumUtil.translate(RISK.Reason.class, logEventReason, false));

	    	String logMsg = mh.get("logMsg", "");
	    	String dispMsg = "" + deserilizeMsg(logMsg);
	    	m.put("logMsg",dispMsg);
		}
	};
	
	private static FormatedString deserilizeMsg(String savedMsg) {
		/////////////////////////////////////////
		//由 savedMsg 反序列化，并显示格式化后的完整讯息
		if (savedMsg.startsWith(RiskEventCommitService.FORMATED_PREFIX)) {
			String json = savedMsg.substring(RiskEventCommitService.FORMATED_PREFIX.length());
			try {
				FormatedString loadedMsg = FormatedString.deserializeFromJson(json);
				return loadedMsg;
			} catch (Exception e) {
			}
		}
		else {
		}
		//没有FORMATED_PREFIX带头, 直接输出
		return new FormatedString("%s",savedMsg);
	}
	
	/**
	 * 進入頁面
	 */
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		if (logger.isDebugEnabled()) {
			logger.debug("操作日志");
		}
		model.addAttribute("today", DateUtil.now8() + " 000000");
		model.addAttribute("today_end", DateUtil.now8() + " 235959");
		return super.toManage(model, false, RESULT_BASE_URI);
	}

	/**
	 * 查詢
	 */
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult eventLogQry(int pageNum, int pageSize, String lastOperId, 
			String mchntCd, String mchntUser, String logSrc, String startDate, String endDate) {
		IEventLogService service = this.getDBService(IEventLogService.class);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("mchntCd", mchntCd);
		qryParamMap.put("startDate", StringUtil.left(startDate, 8));
		qryParamMap.put("endDate", StringUtil.left(endDate, 8));
		qryParamMap.put("startTime", StringUtil.right(startDate, 6));//起始时间
		qryParamMap.put("endTime", StringUtil.right(endDate, 6)); // 结束时间
		qryParamMap.put("logSrc", logSrc);
		if (!lastOperId.isEmpty()) {
			qryParamMap.put("userId", lastOperId);
		}
		if (!mchntUser.isEmpty()) {
			qryParamMap.put("userId", mchntUser);
		}
		if (!lastOperId.isEmpty() && !mchntUser.isEmpty()) {
			if (logSrc.equals(RISK.Source.BM.getCode())) {
				qryParamMap.put("userId", lastOperId);
			}
			if (logSrc.equals(RISK.Source.MER.getCode())) {
				qryParamMap.put("userId", mchntUser);
			}
		}
		Pager<EventLog> p = service.selectByPage(pageNum, pageSize,this.getMonth(startDate, endDate) , qryParamMap);
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_EVENT_LOG, ACCFLOW_ENTITY_TRANSFER));
	}
	
	
	/**
	 * 查看詳情
	 */
	@RequestMapping(value = "/detail.do", method = RequestMethod.GET)
	public String eventLogDetail(Model model, long logId, String logTs) {
		IEventLogService service = this.getDBService(IEventLogService.class);
		EventLog entity = service.selectByPrimaryKey(String.valueOf(logId), logTs.substring(5,7));
		AssertUtil.objIsNull(entity, "未找到操作日志");
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_EVENT_LOG));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_EVENT_LOG, ACCFLOW_ENTITY_TRANSFER));
		
		return RESULT_BASE_URI + "/detail";
	}
	
}
