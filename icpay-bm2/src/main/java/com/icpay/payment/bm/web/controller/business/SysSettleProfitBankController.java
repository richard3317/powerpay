package com.icpay.payment.bm.web.controller.business;

import static com.icpay.payment.risk.BmUser.bmUser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.BankNumCache;
import com.icpay.payment.bm.cache.ChnlMchntInfoCache;
import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.constants.Constant.COMMON_STATE;
import com.icpay.payment.common.constants.Constant.TxnSource;
import com.icpay.payment.common.constants.Names.CHNL_MSG;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.constants.Names.MSG;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.SettleEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.Converter;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.common.utils.WebUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountInfoKey;
import com.icpay.payment.db.dao.mybatis.model.RoutingPriority;
import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitBank;
import com.icpay.payment.db.dao.mybatis.model.modelExt.RoutingPriorityExt;
import com.icpay.payment.db.service.IChnlMchntAccountInfoService;
import com.icpay.payment.db.service.IRoutingPriorityService;
import com.icpay.payment.db.service.ISysSettleProfitBankService;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;
import com.icpay.payment.service.CommonSecService.TYPE;
import com.icpay.payment.service.client.InternalGatewayClient;

@Controller
@RequestMapping("/sysSettleProfitBank")
public class SysSettleProfitBankController extends BaseController {
	
	private static final String RESULT_BASE_URI = "sysSettleProfitBank";
	private static final String SYS_MCHNT_CD = "999000000000SYS";
	private static final String PREFIX = "PF0Z";
//	private static final int DEFAULT_INTERVAL = 5*1000;
	
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String accountBankCode = m.get("accountBankCode");
			if (!Utils.isEmpty(accountBankCode))
				m.put("accountBankName", BankNumCache.getBankName(accountBankCode));
			
			String state = m.get("state");
			if (!Utils.isEmpty(state))
				m.put("stateDesc", EnumUtil.translate(TxnEnums.CommonValid.class, state, true));
			
			String defaultAmt = m.get("defaultAmt");
			if (!Utils.isEmpty(defaultAmt))
				m.put("defaultAmtDesc", toFloatAmt(defaultAmt));
			
		}
	};
	
	private ISysSettleProfitBankService svc = null;
	
	protected ISysSettleProfitBankService getService() {
		if (svc==null)
			svc=this.getDBService(ISysSettleProfitBankService.class);
		return svc;
	}
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	@QryMethod
	public String manage(Model model, String accountNo) {
		if (!Utils.isEmpty(accountNo))
			model.addAttribute("accountNo", accountNo);		
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/backToManage.do", method = RequestMethod.GET)
	public String backToManage(Model model) {
		return super.toManage(model, true, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(Model model) {
		String agentCd = this.getQryParamMap().get("agentCd");
		if (!Utils.isEmpty(agentCd))
			model.addAttribute("agentCd", agentCd);		
		this.buildCommonData(model);
		return super.toAdd(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addSubmit(SysSettleProfitBank record, String defaultAmtDesc) {
		AssertUtil.objIsNull(record, "????????????");
		AssertUtil.strIsBlank(record.getAccountBankCode(), "???????????????");
		AssertUtil.strIsBlank(record.getAccountNo(), "????????????");
		AssertUtil.strIsBlank(record.getAccountName(), "??????????????????");
		
		this.info("??????????????????????????????: %s", record);
		
		SysSettleProfitBank resQry = getService().selectByPrimaryKey(record.getAccountNo());
		if (resQry!=null)
			throw new BizzException(String.format("????????????: ??????= %s|%s", record.getAccountNo(), record.getAccountName()));
		
		record.setState(COMMON_STATE.YSE);
		record.setLastOperId(this.getSessionUsrId());
		record.setAccountBankName(BankNumCache.getBankName(record.getAccountBankCode()));
		if (!Utils.isEmpty(defaultAmtDesc))
			record.setDefaultAmt(Long.parseLong(toIntAmt(defaultAmtDesc)));

		genSign(record);
		getService().insertSelective(record);
		info("??????????????????????????????: %s", record);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Sys_Settle_Profit_Bank_Add)
			.result(RISK.Result.Ok)
			.target(record.getAccountBankCode() + "|" + record.getAccountNo() + "|" + record.getAccountName() + "|" + defaultAmtDesc + "|" + record.getComment())
			.message(String.format("????????? %s, ????????????????????????????????????????????? %s, ??????????????? %s, ??????????????? %s, ??????????????????: %s, ????????? %s", commonBO.getSessionUser().getUsrId(), record.getAccountBankCode(), record.getAccountNo(), record.getAccountName(), defaultAmtDesc, record.getComment()))
			.params("accountBankCode", record.getAccountBankCode())
			.params("accountNo", record.getAccountNo())
			.params("accountName", record.getAccountName())
			.params("defaultAmtDesc", defaultAmtDesc)
			.params("comment", record.getComment())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();
	}
	
	
	protected void genSign(SysSettleProfitBank record) {
		Map<String,String> map = BeanUtil.desc(record, null, null);
		String sign= getSecSvc().signMap(TYPE.AgentAccountInfo, map, true);
		record.setSysMac(sign);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		
		Pager<SysSettleProfitBank> p = getService().selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_SYS_SETTLE_PROFIT_BANK, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, SysSettleProfitBank record) {
		this.buildBankData(model, record);
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, SysSettleProfitBank record) {
		this.buildBankData(model, record);
		this.buildCommonData(model);
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(SysSettleProfitBank record, String defaultAmtDesc) {
		AssertUtil.objIsNull(record, "????????????.");
		AssertUtil.strIsBlank(record.getAccountBankCode(), "???????????????");
		AssertUtil.strIsBlank(record.getAccountNo(), "????????????");
		AssertUtil.strIsBlank(record.getAccountName(), "??????????????????");
		
		this.info("??????????????????????????????: %s", record);
		
		record.setAccountBankName(BankNumCache.getBankName(record.getAccountBankCode()));
		if (!Utils.isEmpty(defaultAmtDesc))
			record.setDefaultAmt(Long.parseLong(toIntAmt(defaultAmtDesc)));
		genSign(record);
		record.setLastOperId(this.getSessionUsrId());
		record.setRecUpdTs(new Date());
		getService().updateByPrimaryKeySelective(record);
		
		info("??????????????????????????????: %s", record);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Sys_Settle_Profit_Bank_Modify)
			.result(RISK.Result.Ok)
			.target(record.getAccountBankCode() + "|" + record.getAccountNo() + "|" + record.getAccountName() + "|" + record.getState() + "|"+ defaultAmtDesc + "|" + record.getComment())
			.message(String.format("????????? %s, ????????????????????????????????????????????? %s, ??????????????? %s, ??????????????? %s, ????????? %s, ??????????????????: %s, ????????? %s", commonBO.getSessionUser().getUsrId(), record.getAccountBankCode(), record.getAccountNo(), record.getAccountName(), record.getState(), defaultAmtDesc, record.getComment()))
			.params("accountBankCode", record.getAccountBankCode())
			.params("accountNo", record.getAccountNo())
			.params("accountName", record.getAccountName())
			.params("state", record.getState())
			.params("defaultAmtDesc", defaultAmtDesc)
			.params("comment", record.getComment())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult delete(SysSettleProfitBank record) {
		AssertUtil.objIsNull(record, "????????????.");
		AssertUtil.strIsBlank(record.getAccountNo(), "????????????");
		
		this.info("??????????????????????????????: %s", record);
		
		getService().deleteByPrimaryKey(record.getAccountNo());
		
		info("??????????????????????????????: %s", record);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Sys_Settle_Profit_Bank_Del)
			.result(RISK.Result.Ok)
			.target(record.getAccountNo())
			.message(String.format("????????? %s, ?????????????????????????????????????????? %s", commonBO.getSessionUser().getUsrId(), record.getAccountNo()))
			.params("accountNo", record.getAccountNo())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();
	}
	
	/**
	 * ??????????????????????????????
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sysWithdraw.do", method = RequestMethod.GET)
	public String sysWithdraw(Model model) {
		ISysSettleProfitBankService svc = this.getDBService(ISysSettleProfitBankService.class);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("state", COMMON_STATE.YSE);
		List<SysSettleProfitBank> sysList = svc.selectByExample(qryParamMap);
		ArrayList transferList = new ArrayList();
		BigDecimal targetAmtDesc = new BigDecimal(0);
		for (int i = 0; i < sysList.size(); i++) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("i", String.valueOf(i));
			SysSettleProfitBank sysBank = sysList.get(i);
			m.put("accountNo", sysBank.getAccountNo());
			m.put("accountName", sysBank.getAccountName());
			m.put("accountBankName", sysBank.getAccountBankName());
			m.put("defaultAmtDesc", StringUtil.formateAmt(String.valueOf(sysBank.getDefaultAmt())));
			targetAmtDesc = targetAmtDesc.add(new BigDecimal(sysBank.getDefaultAmt()));
			transferList.add(m);
		}
		model.addAttribute("sysList", transferList);
		model.addAttribute("totalCount", transferList.size());
		model.addAttribute("targetAmtDesc", targetAmtDesc.movePointLeft(2).toString());
		return RESULT_BASE_URI + "/sysWithdraw";
	}
	
	/**
	 * ????????????????????????
	 * @param request
	 * @param accountNo
	 * @param defaultAmtDesc
	 * @param chnlId
	 * @param chnlMchntCd
	 * @return
	 */
	@RequestMapping(value = "/sysWithdraw/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult sysWithdrawSubmit(HttpServletRequest request, String[] accountNo, String[] defaultAmtDesc, String[] chnlId, String[] chnlMchntCd) {
		String userId = this.getSessionUsrId();
		String requestIp = ""+ WebUtil.getRemoteIp(request);
		String routingIdRandom = Utils.getRandomString(16);
		String routingId = "";
		
		List<Map<String,String>> listWdReq = new ArrayList<Map<String,String>>();
		for (int i = 0; i < accountNo.length; i ++) {
			info("[withdraw] ??????????????????: ????????????:%s, ??????:%s, ??????:%s, ????????????:%s, ?????????:%s", accountNo[i], defaultAmtDesc[i], chnlId[i], chnlMchntCd[i], userId);
			//??????????????????MAP
			String orderId = nextWithdrawOrderId();
			SysSettleProfitBank sysBank = sysBankSvc().selectByPrimaryKey(accountNo[i]);
			if (sysBank != null) {
				Date now = new Date();
				Map <String,String> req = new HashMap<String,String>();
				req.put(MSG.txnType, "52");
				req.put(MSG.txnSubType, "10");
				req.put(INTER_MSG.intTxnType, "5210");
				req.put(MSG.orderDate, Converter.dateToString(now));
				req.put(MSG.orderTime, Converter.timeToString(now));
				req.put(MSG.merId, SYS_MCHNT_CD);
				req.put(MSG.orderId, orderId);
				req.put(MSG.txnAmt, StringUtil.transferAmt(defaultAmtDesc[i]));
				req.put(MSG.currencyCode, Constant.CURR_CN);
				req.put(MSG.accName, sysBank.getAccountName());//??????
				req.put(MSG.accNum, accountNo[i]);//??????
				req.put(MSG.bankNum, sysBank.getAccountBankCode());//?????????
				req.put(MSG.clientIp, requestIp);//ip
				req.put(MSG.notifyUrl, Constant.NOOP_URL);//?????????????????????Constant.NOOP_URL??????????????????http://noop.noop:0000/
				req.put(INTER_MSG.userId, userId);
				req.put(INTER_MSG.txnSrc, TxnSource.MER.getCode());
				routingId = String.format("%s%s%04d", PREFIX, routingIdRandom, i);
				req.put(INTER_MSG.routingId, routingId);
				req.put(CHNL_MSG.channel, chnlId[i]);
				req.put(CHNL_MSG.chnlMerId, chnlMchntCd[i]);
				listWdReq.add(req);
			}
		}
		
		//?????????????????????
		List<Map<String,String>> listRouting = new ArrayList<Map<String,String>>();
		for (Map<String,String> req: listWdReq) {
			debug("[withdraw][routing-add] ????????????: ??????ID:%s, ??????:%s, ????????????:%s", req.get(INTER_MSG.routingId), req.get(CHNL_MSG.channel), req.get(CHNL_MSG.chnlMerId));
			Map<String,String> routeReq= Utils.newMap(
					INTER_MSG.intTxnType, "5210",
					INTER_MSG.routingId, req.get(INTER_MSG.routingId),
					CHNL_MSG.channel, req.get(INTER_MSG.channel),
					CHNL_MSG.chnlMerId, req.get(CHNL_MSG.chnlMerId)
					);
			listRouting.add(routeReq);
		}
		routingSvc().initPriorityRoutings(PREFIX, listRouting, true, userId);
		
		//??????
		for (Map<String,String> req: listWdReq) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Map<String, String> resp =new HashMap<String,String>();
						info("[InternalGatewayClient][??????][??????????????????] "+req);
						resp = InternalGatewayClient.request(req, null) ;
						info("[InternalGatewayClient][??????][??????????????????] "+resp);
					}catch(Exception e ) {
						error("[InternalGatewayClient][ERROR] ???????????????????????????????????????" + e.getMessage(), e);
					}
				}
			}).start();
		}
		info("[withdraw] ????????????????????????");
		return commonBO.buildSuccResp("????????????????????????????????????????????????????????????????????????????????????????????????????????????");
	}
	
	/**
	 * ????????????????????????
	 * @param chnlId
	 * @param defaultAmt
	 * @return
	 */
	@RequestMapping(value = "/getChnlMchnts.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult getChnlMchnts(String chnlId, String defaultAmt) {
		List<Map<String,String>> list = new ArrayList<>();
		List<Map<String,String>> chnlMchntList = new ArrayList<>();
		list = ChnlMchntInfoCache.getInstance().getByChnlId(chnlId);
		
		for (int i = 0; i < list.size(); i++) {
			String c_chnlId = list.get(i).containsKey("chnlId") ? list.get(i).get("chnlId") : "";
			String c_chnlMchntCd = list.get(i).containsKey("chnlMchntCd") ? list.get(i).get("chnlMchntCd") : "";
			String c_chnlMchntDesc = list.get(i).containsKey("chnlMchntDesc") ? list.get(i).get("chnlMchntDesc") : "";
			IChnlMchntAccountInfoService svc = this.getDBService(IChnlMchntAccountInfoService.class);
			ChnlMchntAccountInfoKey key = new ChnlMchntAccountInfoKey();
			key.setChnlId(c_chnlId);
			key.setMchntCd(c_chnlMchntCd);
			ChnlMchntAccountInfo info = svc.selectByPrimaryKey(key);
			if (info != null) {
				//????????????
				BigDecimal availableBalance = new BigDecimal(info.getAvailableBalance());
				BigDecimal obligatedBalance = new BigDecimal(info.getObligatedBalance());
				BigDecimal realAvailableBalance  = availableBalance.subtract(obligatedBalance);
				if (realAvailableBalance.compareTo(BigDecimal.ZERO) < 0) {
					realAvailableBalance = BigDecimal.ZERO;
				}
				
				//?????????????????????????????????????????? > ???????????????????????? ,??????????????????????????????
				BigDecimal defaultAmtBd = new BigDecimal(defaultAmt).movePointRight(2);
				if (realAvailableBalance.compareTo(defaultAmtBd) >= 0) {
					Map<String,String> m = new HashMap<String, String>();
					m.put("chnlId", c_chnlId);
					m.put("chnlMchntCd", c_chnlMchntCd);
					m.put("chnlMchntDesc", c_chnlMchntDesc);
					m.put("realAvailableBalance", realAvailableBalance.movePointLeft(2).toString());
					chnlMchntList.add(m);
				}
			}
		}
		AjaxResult resp= commonBO.buildSuccResp("chnlMchntList", chnlMchntList);
		return resp;
	}
	
	ISysSettleProfitBankService sysBankService = null;
	protected ISysSettleProfitBankService sysBankSvc() {
		if (sysBankService==null)
			sysBankService = DBHessionServiceClient.getService(ISysSettleProfitBankService.class);
		return sysBankService;
	}
	
	IRoutingPriorityService routingService = null;
	protected IRoutingPriorityService routingSvc() {
		if (routingService==null)
			routingService = DBHessionServiceClient.getService(IRoutingPriorityService.class);
		return routingService;
	}
	
	private void buildCommonData(Model model) {
		model.addAttribute("bankCodeList", BankNumCache.getBankNumsList());
	}
	
	private void buildBankData(Model model, SysSettleProfitBank record) {
		AssertUtil.objIsNull(record, "????????????");
		
		SysSettleProfitBank resQry = this.getService().selectByPrimaryKey(record.getAccountNo());
		AssertUtil.objIsNull(resQry, "????????????: " + record);
		model.addAttribute("record", resQry);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_SYS_SETTLE_PROFIT_BANK));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(resQry, BMConstants.PAGE_CONF_SYS_SETTLE_PROFIT_BANK, ENTITY_TRANSFER));
	}
}
