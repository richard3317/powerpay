package com.icpay.payment.mer.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.MapHelper;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.common.utils.logger.Log;
import com.icpay.payment.db.dao.mybatis.model.EventLog;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.db.service.IEventLogService;
import com.icpay.payment.mer.bo.MchntBO;
import com.icpay.payment.mer.constants.MerConstants;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.service.RiskEventCommitService;
import com.icpay.utils.FormatedString;

@Controller
@RequestMapping("/eventLog")
public class EventLogController extends BaseController {

	private static final Logger logger = Logger.getLogger(EventLogController.class);
	@Autowired
	private MchntBO mchntBO;
	public static final String ADMIN_USER = "admin";  //商户默认管理员

	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {

			MapHelper<String, String> mh = new MapHelper<String, String>(m);

			String logEvent = m.containsKey("logEvent") ? m.get("logEvent") : "";
			m.put("logEvent", EnumUtil.translate(RISK.Event.class, logEvent, false));

			String logParams = m.containsKey("logParams") ? m.get("logParams") : "";
			String reqIp = "";
			try {
				JSONObject jObj = new JSONObject(logParams);
				reqIp = jObj.get("reqIp").toString();
			} catch (JSONException e) {
				logger.error("logParams格式解析出错", e);
			}
	    	m.put("logIp", reqIp);

	    	String logEventResult = m.containsKey("logEventResult") ? m.get("logEventResult") : "";
	    	m.put("logEventResult", EnumUtil.translate(RISK.Result.class, logEventResult, false));

	    	String logEventReason = m.containsKey("logEventReason") ? m.get("logEventReason") : "";
	    	m.put("logEventReason", EnumUtil.translate(RISK.Reason.class, logEventReason, false));

	    	String logMsg = mh.get("logMsg", "");
	    	String dispMsg = ""+deserilizeMsg(logMsg);
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


	@RequestMapping(value="/eventLog", method=RequestMethod.GET)
	public String fundMng(Model model) {
		String mchntCd =commonBO.getMchntCd();
		String secretFlag ="true";
		if(!commonBO.validateSecret(mchntCd)) {
			secretFlag ="false";
		}
		model.addAttribute("secretFlag", secretFlag);
		model.addAttribute("loginType", commonBO.getLoginType());
		model.addAttribute("defaultStart", DateUtil.now8() + " 000000");
		model.addAttribute("defaultEnd", DateUtil.now8() + " 235959");

		String loginUserId = commonBO.getMchntUser().getUserId();
		List<MchntUserInfo> mchList = new ArrayList<MchntUserInfo>();
		if (StringUtils.equals(loginUserId, ADMIN_USER)) {
			mchList = mchntBO.selectMchntUserList(mchntCd, null, null);
		} else {
			mchList = mchntBO.selectMchntUserList(mchntCd, loginUserId, null);
		}
		model.addAttribute("mchList", mchList);
		return "eventLog";
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
	@RequestMapping(value="/qryEventLog", method=RequestMethod.POST)
	public @ResponseBody AjaxResult qryEventLog(int pageNum, int pageSize, String userId, String startDt, String endDt, HttpServletRequest req) {

		String operateDt = null;
		if (!Utils.isEmpty(startDt))
			operateDt = StringUtil.left(startDt, 8);
		else
			operateDt = StringUtil.left(endDt, 8);
		AssertUtil.objIsNull(operateDt,"日期为空");

		Map<String, String> qryParams = new HashMap<String, String>();
		qryParams.put("chnlId", "00");
		qryParams.put("mchntCd", commonBO.getMchntCd());
		String loginUserId = commonBO.getMchntUser().getUserId();
		if (StringUtils.equals(loginUserId, ADMIN_USER)) {
			qryParams.put("userId", userId);
		} else {
			qryParams.put("userId", loginUserId);
		}
		String mon = StringUtil.left(operateDt, 8);
		qryParams.put("startDate", StringUtil.left(startDt, 8));
		qryParams.put("endDate", StringUtil.left(endDt, 8));
		qryParams.put("startTime", StringUtil.right(startDt, 6));//起始时间
		qryParams.put("endTime", StringUtil.right(endDt, 6)); // 结束时间

		IEventLogService service = this.getService(IEventLogService.class);
		Pager<EventLog> pager = service.selectByPage(pageNum, pageSize, mon.substring(4,6), qryParams);

		return commonBO.buildSuccResp(MerConstants.PAGER_RESULT,
				commonBO.transferPager(pager, MerConstants.EVENT_LOG, ENTITY_TRANSFER, req));
	}
}
