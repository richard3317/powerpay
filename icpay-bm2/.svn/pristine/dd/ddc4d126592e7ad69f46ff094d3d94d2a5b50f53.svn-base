package com.icpay.payment.bm.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.icpay.payment.bm.bo.CommonBO;
import com.icpay.payment.bm.bo.OperLogBO;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.entity.SessionUserInfo;
import com.icpay.payment.bm.web.util.BaseTool;
import com.icpay.payment.common.constants.RspCd;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.service.CommonSecService;
import com.icpay.payment.service.SessionUtilService;

public abstract class BaseController extends BaseTool {
	
	protected static final String PAGE_TP_MANAGE = "manage";
	protected static final String PAGE_TP_ADD = "add";
	protected static final String PAGE_TP_EDIT = "edit";
	protected static final String PAGE_TP_DETAIL = "detail";
	
	@Autowired
	protected CommonBO commonBO;
	@Autowired
	protected OperLogBO operLogBO;
	
	protected CommonSecService getSecSvc() {
		return ServiceProxy.getService(CommonSecService.class);
	}
	
	protected Map<String, String> getQryParamMap() {
		return commonBO.getSessionAttr(commonBO.getParamMapKey(this.getClass()));
	}
	
	protected void clearQryParamMap() {
		Map<String, String> m = commonBO.getSessionAttr(commonBO.getParamMapKey(this.getClass()));
		if (m != null) {
			m.clear();
			commonBO.removeSessionAttr(BMConstants.QRY_PARAM_MAP_KEY);
		}
	}
	
	/**
	 * 记录操作日志 - 操作内容为文本
	 * @param opFuncInfo
	 * @param opTp
	 * @param opContent
	 */
	protected void logText(String opFuncInfo, CommonEnums.OpType opTp, String opContent) {
		SessionUserInfo usr = commonBO.getSessionUser();
		String operInfo = usr.getUsrId() + "-" + usr.getUsrNm();
		operLogBO.log(CommonEnums.SysInfo.CONSOLE, operInfo, opFuncInfo, 
				opTp, usr.getLoginIp(), opContent);
	}
	
	/**
	 * 记录操作日志 - 操作内容为数据库实体对象
	 * @param opFuncInfo
	 * @param opTp
	 * @param opContent
	 */
	protected void logObj(String opFuncInfo, CommonEnums.OpType opTp, Object opEntity) {
		SessionUserInfo usr = commonBO.getSessionUser();
		String operInfo = usr.getUsrId() + "-" + usr.getUsrNm();
		operLogBO.log(CommonEnums.SysInfo.CONSOLE, operInfo, opFuncInfo, 
				opTp, usr.getLoginIp(), opEntity);
	}
	
	protected <T> T getDBService(Class<T> clazz) {
		return DBHessionServiceClient.getService(clazz);
	}
	
	protected String getSessionUsrId() {
		if (commonBO.getSessionUser()==null) return null;
		return commonBO.getSessionUser().getUsrId();
	}
	
	/**
	 * 回传目前用户是否有指定功能的权限
	 * @param funcCd 功能代码
	 * @return 是否有该功能的权限
	 */
	protected boolean hasFuncRight(String funcCd) {
		return commonBO.hasFuncRight(funcCd);
	}
	
	/**
	 * 检查目前用户是否有指定功能的权限
	 * @param funcCd 功能代码
	 * @return 是否有该功能的权限
	 */
	protected void checkFuncRight(String funcCd) {
		if (!hasFuncRight(funcCd)) {
			this.error("存取限制 : sessionUserId: %s, funcCd: %s;", this.getSessionUsrId(), funcCd);
			throw new BizzException(RspCd.Z_0024, "存取限制!");
		}
	}
	
	/**
	 * 进入Manage页面
	 * @param model
	 * @param backFlg
	 * @param resultBaseUri
	 * @return
	 */
	protected String toManage(Model model, boolean backFlg, String resultBaseUri) {
		return this.toManage(model, backFlg, resultBaseUri, null);
	}
	
	protected String toManage(Model model, boolean backFlg, String resultBaseUri, String lastError) {
		this.preparePageData(model, PAGE_TP_MANAGE);
		if (!Utils.isEmpty(lastError)) {
			model.addAttribute("lastError", lastError);
		}
		else {
			//model.addAttribute("lastError", "");
		}
		if (backFlg) {
			//this.debug("backToManager('%s'): qryParamMap: %s", resultBaseUri, this.getQryParamMap());
			model.addAttribute("qryParamMap", this.getQryParamMap());
		} else {
			//this.debug("toManage('%s'): clearQryParamMap!", resultBaseUri);
			this.clearQryParamMap();
		}
		String view= resultBaseUri + "/" + PAGE_TP_MANAGE;
		//this.debug("View: "+view);
		return view;
	}
	
	protected String toAdd(Model model, String resultBaseUri) {
		this.preparePageData(model, PAGE_TP_ADD);
		return resultBaseUri + "/" + PAGE_TP_ADD;
	}
	
	protected String toEdit(Model model, String resultBaseUri) {
		this.preparePageData(model, PAGE_TP_EDIT);
		return resultBaseUri + "/" + PAGE_TP_EDIT;
	}
	
	protected String toDetail(Model model, String resultBaseUri) {
		this.preparePageData(model, PAGE_TP_DETAIL);
		return resultBaseUri + "/" + PAGE_TP_DETAIL;
	}
	
	/**
	 * 转移到指定页面
	 * @param model 数据模型
	 * @param resultBaseUri baseUri
	 * @param pageTyp 页面类型(名称)
	 * @return
	 */
	protected String toPage(Model model, String resultBaseUri, String pageTyp) {
		this.preparePageData(model, pageTyp);
		return resultBaseUri + "/" + pageTyp;
	}
	
	protected void preparePageData(Model model, String pageTp) { }
	
	
	////////////////////////////////////////////////////////////////////////
	// Utils
	
	/**
	 * 获取充值订单号 "BP" 带头
	 * @return
	 */
	protected String nextPayOrderId() { //TODO use jedis
		//return CommonController.newOrderId();
		SessionUtilService svc = ServiceProxy.getService(SessionUtilService.class);
		//return svc.nextSeq(SessionUtilService.SEQ_MER_ORDERID);
		return svc.nextSeqWithSubType(SessionUtilService.SEQ_MER_ORDERID, "BP");
	}
	
	/**
	 * 获取代付订单号 "BW" 带头
	 * @return
	 */
	protected String nextWithdrawOrderId() { //TODO use jedis
		//return CommonController.newOrderId();
		SessionUtilService svc = ServiceProxy.getService(SessionUtilService.class);
		//return svc.nextSeq(SessionUtilService.SEQ_MER_ORDERID);
		return svc.nextSeqWithSubType(SessionUtilService.SEQ_MER_ORDERID, "BW");
	}
	
	protected String nullIfEmpty(String str) {
		if (Utils.isInSet(str, null,"undefined","null")) return null;
		return str;
	}
	
	/**
	 * 去重
	 * @param ss
	 * @return
	 */
	public static List<String> list_unique(List<String> covList) {
	    List<String> list =new ArrayList<String>();
			for(String s: covList){
				if(!list.contains(s))
					list.add(s);
			}
			return list;
	}
	
//	/////////////////////////////////////////
//	// Logger
//	
//	private Logger logger = null;
//	
//	protected Logger getLogger() {
//		if (logger==null) {
//			logger = Logger.getLogger(this.getClass());
//		}
//		return logger;
//	}
//
//	protected String format(String fmt, Object...args) {
//		return String.format(fmt, args);
//	}
//
//	protected void debug(String message) {
//		getLogger().debug(getLogPrefix()+message);
//	}
//
//	protected void debug(String fmt, Object... args) {
//		debug(String.format(fmt, args));
//	}
//
//	protected void info(String message) {
//		getLogger().info(getLogPrefix()+message);
//	}
//
//	protected void info(String fmt, Object... args) {
//		info(String.format(fmt, args));
//	}
//
//	protected void warn(String message) {
//		getLogger().warn(getLogPrefix()+message);
//	}
//
//	protected void warn(String fmt, Object... args) {
//		warn(String.format(fmt, args));
//	}
//
//	protected void error(String message) {
//		getLogger().error(getLogPrefix()+message);
//	}
//
//	protected void error(String fmt, Object... args) {
//		error(String.format(fmt, args));
//	}
//
//	protected void error(String message, Throwable t) {
//		getLogger().error(getLogPrefix()+message, t);
//	}
//
//	protected void error(Throwable t, String fmt, Object... args) {
//		error(String.format(fmt, args), t);
//	}
//
//	protected String getLogPrefix() {
//		return "";
//	}		

	
}
