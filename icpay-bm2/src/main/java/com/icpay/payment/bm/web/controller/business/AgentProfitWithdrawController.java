package com.icpay.payment.bm.web.controller.business;

import static com.icpay.payment.risk.BmUser.bmUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icpay.payment.bm.bo.BusCheckBO;
import com.icpay.payment.bm.cache.AgentInfoCache;
import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.cache.MchntInfoCache;
import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.cache.ParamCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.constants.Constant.AGENT_ACC_TYPE;
import com.icpay.payment.common.constants.Constant.PROC_STATUS;
import com.icpay.payment.common.constants.Constant.TXNS_TATUS;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.data.svc.ProfitWithdrawService;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.ProfitEnums;
import com.icpay.payment.common.enums.ProfitEnums.AgentType;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.Converter;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.AgentAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitWithdrawLog;
import com.icpay.payment.db.dao.mybatis.model.TxnLogView;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentForChnlMerEx;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentProfitWithdrawSummary;
import com.icpay.payment.db.service.IAgentBankAccsService;
import com.icpay.payment.db.service.IAgentForChnlMerService;
import com.icpay.payment.db.service.IAgentProfitWithdrawLogService;
import com.icpay.payment.db.service.ITxnLogViewService;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;
import com.icpay.payment.common.enums.TxnEnums.TxnStatus;

@Controller
@RequestMapping("/agentProfitWithdraw")
public class AgentProfitWithdrawController extends BaseController {
	
	private static final String RESULT_BASE_URI = "agentProfitWithdraw";
	private static final String WITHDRAW_TYPE="1"; //0=未定义,1=分润打款,9=紧急打款
	
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			
			String agentCd = m.get("agentCd");
			m.put("agentDesc", AgentInfoCache.getAgentName(agentCd));
			
			String mchntCd = m.get("mchntCd");
			m.put("mchntDesc", MchntInfoCache.getMchntName(mchntCd));
			
			String apAmtTotal = m.get("apAmtTotal");
			m.put("apAmtTotalDesc", StringUtil.formateAmt(apAmtTotal));
			
			String apAmt = m.get("apAmt");
			m.put("apAmtDesc", StringUtil.formateAmt(apAmt));
			
			String txnAmt = m.get("txnAmt");
			m.put("txnAmtDesc", StringUtil.formateAmt(txnAmt));
			
			String txnFee = m.get("txnFee");
			m.put("txnFeeDesc", StringUtil.formateAmt(txnFee));
			
			String state = m.get("state");
			if (!Utils.isEmpty(state))
				m.put("stateDesc", EnumUtil.translate(TxnEnums.CommonSwitch.class, state, true));
			
			String withdrawType = m.get("withdrawType");
			if (!Utils.isEmpty(withdrawType))
				m.put("withdrawTypeDesc", EnumUtil.translate(ProfitEnums.AgentAccUsage.class, withdrawType, true));
			
			String txnState = m.get("txnState");
			if (!Utils.isEmpty(txnState))
				m.put("txnStateDesc", EnumUtil.translate(TxnEnums.TxnStatus.class, txnState, true));
			
			String procState = m.get("procState");
			if (!Utils.isEmpty(txnState))
				m.put("procStateDesc", EnumUtil.translate(TxnEnums.ProcStatus.class, procState, true));
			
			////////////////////////////////////////////////
			//  rowStyler = null; //"color:black;"; //'background-color:pink;color:blue;font-weight:bold;';
			
			String rowStyler = ParamCache.STYLE_DEFAULT2; 
			
			if (TxnEnums.CommonSwitch.OFF.getCode().equals(state))
				rowStyler=ParamCache.STYLE_DISABLE2;
			if (TXNS_TATUS.PROC.equals(txnState))
				rowStyler=ParamCache.STYLE_TXN_STATUS_PROC2;
			if (TXNS_TATUS.OK.equals(txnState))
				rowStyler=ParamCache.STYLE_TXN_STATUS_OK2;
			if (TXNS_TATUS.FAILED.equals(txnState) || PROC_STATUS.REQUEST_FAILED.equals(procState))
				rowStyler=ParamCache.STYLE_TXN_STATUS_FAILED2;
			
			if (!Utils.isEmpty(rowStyler))
				m.put("rowStyler", rowStyler);
		}
	};
	
	@Autowired
	private BusCheckBO busCheckBO;

	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	@QryMethod
	public String manage(Model model, String settleDate) {
		if (Utils.isEmpty(settleDate))
			model.addAttribute("settleDate", settleDate);
		this.buildCommonData(model);
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/backToManage.do", method = RequestMethod.GET)
	public String backToManage(Model model) {
		this.buildCommonData(model);
		return super.toManage(model, true, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(Model model) {
		this.buildCommonData(model);
		return super.toAdd(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addSubmit(AgentProfitWithdrawLog record, String txnAmtDesc) {
		AssertUtil.objIsNull(record, "數據內容為空.");
		AssertUtil.strIsBlank(record.getSettleDate(), "结算日期为空");
		AssertUtil.strIsBlank(record.getAgentCd(), "代理商編號为空");
		AssertUtil.strIsBlank(record.getMchntCd(), "商戶号为空");
		AssertUtil.strIsBlank(record.getAccountNo(), "帐户号为空");
		
		this.info("请求新增打款数据: %s", record);
		
		String[] bacc=split(record.getAccountNo(), ";");
		record.setAccountNo(getByIndex(bacc,0));
		record.setAccountName(getByIndex(bacc,1));
		record.setAccountBankCode(getByIndex(bacc,2));
		record.setAccountBankName(getByIndex(bacc,3));
		
		String[] mers=split(record.getMchntCd(), ";");
		record.setMchntCd(getByIndex(mers,0));
		String comment = String.format("%s%s", 
				Utils.isEmpty( record.getComment()) ? "" : record.getComment()+"; ",  
				getByIndex(mers,1));
		record.setComment(comment);
		record.setWithdrawType(WITHDRAW_TYPE);
		if (!Utils.isEmpty(txnAmtDesc))
			record.setTxnAmt(toLongAmt(txnAmtDesc));
		
		record.setSeq(0);
		record.setLastOperId(this.getSessionUsrId());
		record.setApAmt(null); //不允许修改
		record.setApAmtTotal(null); //不允许修改
		record.setRecCrtTs(new Date());
		record.setRecUpdTs(new Date());
		
		this.getService().add(record);
		
		//this.logObj(BmEnums.FuncInfo._1000090000.getCode(), CommonEnums.OpType.UPDATE, record);
		info("打款数据已新增: "+ record);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Agent_Profit_Withdraw_Add)
			.result(RISK.Result.Ok)
			.target(record.getAgentCd() + "|" + record.getMchntCd() + "|" + record.getAccountNo() + "|" + txnAmtDesc + "|" + ("1".equals(record.getState())? "开启" : "关闭") + "|" + record.getComment())
			.message(String.format("用户： %s, 分润打款新增。代理商： %s, 商户号： %s, 银行帐号： %s, 实际打款金额： %s, 打款操作： %s, 备注： %s", commonBO.getSessionUser().getUsrId(), record.getAgentCd(), record.getMchntCd(), record.getAccountNo(), txnAmtDesc, ("1".equals(record.getState())? "开启" : "关闭"), record.getComment()))
			.params("agentCd", record.getAgentCd())
			.params("mchntCd", record.getMchntCd())
			.params("accountNo", record.getAccountNo())
			.params("txnAmtDesc", txnAmtDesc)
			.params("state", record.getState())
			.params("comment", record.getComment())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();		
	}
	
	String[] split(String src, String splitor) {
		if (Utils.isEmpty(src)) return new String[0];
		return src.split(splitor);
	}
	
	String getByIndex(String[] list, int index) {
		if (list==null) return null;
		if (index>=list.length) return null;
		return list[index];
	}
	
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, Long id) {
		//checkAgentProfitWithdrawLogEditable(id); //check in page
		this.buildAgentData(model, id);
		this.buildCommonData(model);
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(AgentProfitWithdrawLog record, String txnAmtDesc) {
		AssertUtil.objIsNull(record, "數據內容為空.");
		AssertUtil.strIsBlank(record.getSettleDate(), "结算日期为空");
		AssertUtil.strIsBlank(record.getAgentCd(), "代理商編號为空");
		AssertUtil.strIsBlank(record.getMchntCd(), "商戶号为空");
		AssertUtil.strIsBlank(record.getAccountNo(), "帐户号为空");
		AssertUtil.objIsNull(record.getSeq(), "打款序号为空");
		
		this.info("请求更新打款数据: %s", record);
		
		checkAgentProfitWithdrawLogEditable(record.getId());
		
		record.setWithdrawType(WITHDRAW_TYPE);
		if (!Utils.isEmpty(txnAmtDesc))
			record.setTxnAmt(toLongAmt(txnAmtDesc));
		
		record.setLastOperId(this.getSessionUsrId());
		record.setApAmt(null); //不允许修改
		record.setApAmtTotal(null); //不允许修改
		record.setRecUpdTs(new Date());
		
		this.getService().update(record);
		
		//this.logObj(BmEnums.FuncInfo._1000090000.getCode(), CommonEnums.OpType.UPDATE, record);
		info("打款数据已更新: "+ record);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Agent_Profit_Withdraw_Modify)
			.result(RISK.Result.Ok)
			.target(record.getAgentCd() + "|" + record.getMchntCd() + "|" + record.getAccountNo() + "|" + txnAmtDesc + "|" + ("1".equals(record.getState())? "开启" : "关闭") + "|" + record.getComment())
			.message(String.format("用户： %s, 分润打款修改。代理商： %s, 商户号： %s, 银行帐号： %s, 实际打款金额： %s, 打款操作： %s, 备注： %s", commonBO.getSessionUser().getUsrId(), record.getAgentCd(), record.getMchntCd(), record.getAccountNo(), txnAmtDesc, ("1".equals(record.getState())? "开启" : "关闭"), record.getComment()))
			.params("agentCd", record.getAgentCd())
			.params("mchntCd", record.getMchntCd())
			.params("accountNo", record.getAccountNo())
			.params("txnAmtDesc", txnAmtDesc)
			.params("state", record.getState())
			.params("comment", record.getComment())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();
		
	}
	
	protected AgentProfitWithdrawLog checkAgentProfitWithdrawLogEditable(Long id) {
		AssertUtil.objIsNull(id, "键值为空");
		AgentProfitWithdrawLog resQry = this.getService().selectByPrimaryKey(id);
		AssertUtil.objIsNull(resQry, "查无数据: " + id);
		return checkAgentProfitWithdrawLogEditable(resQry);
	}
	
	protected AgentProfitWithdrawLog checkAgentProfitWithdrawLogEditable(AgentProfitWithdrawLog rec) {
		AssertUtil.objIsNull(rec, "数据为空");
		boolean r= (Utils.isInSet(rec.getProcState(), "00","",null)) && (Utils.isEmpty(rec.getTxnOrderId()));
		if (!r) throw new BizzException("已打款的数据不得修改或删除!");
		return rec;
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult delete(Long id) {
		AssertUtil.objIsNull(id, "鍵值為空.");
		
		info("请求删除打款数据: %s", id);
		
		checkAgentProfitWithdrawLogEditable(id);
		
		int r = this.getService().delete(id);
		
		if (r<=0) {
			this.error("打款数据 %s 删除失败", id);
			return commonBO.buildErrorResp(String.format("打款数据 %s 删除失败", id));
		}
		else {
			info("打款数据 %s 已删除!", id);
			
			RiskEvent.bm()
				.who(bmUser(commonBO.getSessionUser().getUsrId()))
				.event(RISK.Event.Agent_Profit_Withdraw_Del)
				.result(RISK.Result.Ok)
				.target(id+"")
				.message(String.format("用户： %s, 分润打款删除。ID： %s", commonBO.getSessionUser().getUsrId(), id))
				.params("id", id)
				.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
				.submit();
			
			return commonBO.buildSuccResp();
		}
		
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		
		Pager<AgentProfitWithdrawLog> p = getService().selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_AgentProfitWithdrawLog, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, Long id) {
		this.buildAgentData(model, id);
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/agentBankAccs.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult getAgentBankAccs(String agentCd) {
		List<AgentAccountInfo> list =
			getBankAccSvc().selectByAgent(agentCd, 
					ProfitEnums.AgentAccUsage.PROFIT.getCode(), 
					ProfitEnums.AgentAccUsage.COMMON.getCode()
					); //TODO 删除重复的acc
		list = removeDuplicate(list);
		AjaxResult resp= commonBO.buildSuccResp("accsList", list);
		
		List<AgentForChnlMerEx> amerList = getAmerSvc().selectByAgentEx(agentCd);
		if (amerList==null) amerList= new ArrayList<>();
		resp.addResultData("amerList", amerList);
		
		return resp;
	}
	
	protected List<AgentAccountInfo> removeDuplicate(List<AgentAccountInfo> srcList) {
		List<AgentAccountInfo> resList = new ArrayList<>();
		if (srcList==null) return resList; 
		Map<String, Object> hash= new HashMap<>();
		for(AgentAccountInfo a : srcList) {
			String key= a.getAccountNo()+a.getAgentCd();
			if (!hash.containsKey(key)) {
				resList.add(a);
				hash.put(key, 1);
			}
		}
		return resList;
	}
	
//	@RequestMapping(value = "/agentProfitMerList.do", method = RequestMethod.GET)
//	public @ResponseBody AjaxResult getAgentProfitMerList(String agentCd) {
//		List<AgentForChnlMerEx> list = getAmerSvc().selectByAgentEx(agentCd);
//		return commonBO.buildSuccResp("amerList", list);
//	}
	
	private void buildCommonData(Model model) {
		String preDate = DateUtil.preDay(new Date());
		String today = DateUtil.now8();
		model.addAttribute("preDate", preDate);
		model.addAttribute("today", today);
		model.addAttribute("agentsList", AgentInfoCache.getAgentsByTypes(AgentType.SELF,AgentType.CHNL,AgentType.MER));
		//model.addAttribute("",getBankAccSvc().selectByAgent(agentCd, accType))
	}
	
	private void buildAgentData(Model model, Long id) {
		AssertUtil.objIsNull(id, "键值为空");
		
		AgentProfitWithdrawLog resQry = this.getService().selectByPrimaryKey(id);
		AssertUtil.objIsNull(resQry, "查无数据: " + id);
		model.addAttribute("record", resQry);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_AgentProfitWithdrawLog));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(resQry, BMConstants.PAGE_CONF_AgentProfitWithdrawLog, ENTITY_TRANSFER));
	}
	
	private IAgentProfitWithdrawLogService svc = null;
	
	protected IAgentProfitWithdrawLogService getService() {
		if (svc==null)
			svc=this.getDBService(IAgentProfitWithdrawLogService.class);
		return svc;
	}
	
	private IAgentBankAccsService accSvc=null;
	protected IAgentBankAccsService getBankAccSvc() {
		if (accSvc==null)
			accSvc = this.getDBService(IAgentBankAccsService.class);
		return accSvc;
	}
	
	private IAgentForChnlMerService amerSvc=null;
	protected IAgentForChnlMerService getAmerSvc() {
		if (amerSvc==null)
			amerSvc =this.getDBService(IAgentForChnlMerService.class);
		return amerSvc;
	}
	
	@RequestMapping(value = "/resetData.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult resetData(Model model, String settleDate) {
		settleDate = DateUtil.preDay(new Date());
		info(String.format("[resetData] 结算打款数据: settleDate=%s; ", settleDate));
		try {
			ProfitWithdrawService svc = ServiceProxy.getService(ProfitWithdrawService.class);
			svc.settleProfitWithdrawData(settleDate);
			info("[resetData] 结算打款数据OK.");
			return this.commonBO.buildSuccResp();
		} catch (Exception e) {
			this.error(e, "[resetData] 结算打款数据失败: settleDate=%s;", settleDate);
			return this.commonBO.buildErrorResp(String.format("结算打款数据失败: 結算日期=%s; %s", settleDate, e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/withdraw.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult withdrawAll(Model model) {
		final String settleDate = DateUtil.preDay(new Date());
		info(String.format("[withdrawAll] 批量打款: settleDate=%s; ", settleDate));
//		try {
//			ProfitWithdrawService svc = ServiceProxy.getService(ProfitWithdrawService.class);
//			int errs = svc.requestProfitWithdraw(settleDate, AGENT_ACC_TYPE.PROFIT, this.getSessionUsrId());
//			info("[withdrawAll] 打款请求完成，错误笔数:"+errs);
//			if (errs > 0)
//				return this.commonBO.buildErrorResp(String.format("打款请求完成但有错误: 結算日期=%s; 错误笔数:%s;", settleDate, errs));
//			else
//				return this.commonBO.buildSuccResp();
//		} catch (Exception e) {
//			this.error(e, "[withdrawAll] 批量打款失败: settleDate=%s;", settleDate);
//			return this.commonBO.buildErrorResp(String.format("打款请求失败: 結算日期=%s; %s", settleDate, e.getMessage()));
//		}
		
		final String userId = this.getSessionUsrId();
		final ProfitWithdrawService svc = ServiceProxy.getService(ProfitWithdrawService.class);
		new Thread(new Runnable() {
			@Override
			public void run() {
				int errs = svc.requestProfitWithdraw(settleDate, AGENT_ACC_TYPE.PROFIT, userId);
				info("[withdrawAll] 打款请求完成，错误笔数:"+errs);
			}
		}).start();
		return this.commonBO.buildSuccResp("打款请求已完成，打款将陆续完成，请刷新页面读取打款结果。");
	}
	
	// 根据条件查询记录（导出）
	@RequestMapping(value = "/export.do", method = {RequestMethod.POST, RequestMethod.GET})
	@QryMethod
	public String export(Model model, String mon, HttpServletResponse response) {
		this.debug("[export] 导出查询结果, qryParamMap: %s", this.getQryParamMap());
		Map<String, Object> mapSummary = null;
		List<AgentProfitWithdrawLog>  list = getService().select(this.getQryParamMap());
		Pager<Map<String, String>> pager = commonBO.transferList(list, BMConstants.PAGE_CONF_AgentProfitWithdrawLog, ENTITY_TRANSFER);
		String filename = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_PROFIT_BATCH_WITHDRAW_FILENM);
		try {
			commonBO.exportToExcel(pager,mapSummary, filename, Constant.UTF8, response);
			return null;
		} catch (Exception e) {
			this.buildCommonData(model);
			return super.toManage(model, true, RESULT_BASE_URI, e.getMessage());
		}
	}
	
	// 根据条件查询记录（导出）
	@RequestMapping(value = "/enableAll.do", method = {RequestMethod.POST, RequestMethod.GET})
	@QryMethod
	public @ResponseBody AjaxResult enableAll(Model model, String enabled, HttpServletResponse response) {
		this.debug("[enableAll] 开启/关闭查询结果, enabled=%s; qryParamMap: %s", enabled, this.getQryParamMap());
		int r = getService().enableAll(this.getQryParamMap(), "true".equalsIgnoreCase(enabled), this.getSessionUsrId());
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Agent_Profit_Withdraw_Enable)
			.result(RISK.Result.Ok)
			.target("true".equalsIgnoreCase(enabled) ? "开启" : "关闭")
			.message(String.format("用户： %s, 分润打款批量 %s。操作成功，记录更新笔数： %s", commonBO.getSessionUser().getUsrId(), "true".equalsIgnoreCase(enabled) ? "开启" : "关闭", r))
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return this.commonBO.buildSuccResp(String.format("操作成功，记录更新笔数： %s", r));
	}

	// 加总
	@RequestMapping(value = "/amtSum.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody JSONObject amtSum(Model model, String mon) {
		model.addAttribute("qryParamMap", this.getQryParamMap());
		return summary(this.getQryParamMap(), mon);
	}
	
	public JSONObject summary(Map<String, String> qryParamMap, String mon) {
		this.debug("[IAgentProfitWithdrawLogService][selectSummary] qryParamMap: %s", qryParamMap);
		IAgentProfitWithdrawLogService svc = this.getDBService(IAgentProfitWithdrawLogService.class);
		AgentProfitWithdrawSummary sum = svc.selectSummary(qryParamMap);
		Map<String, Object> map = new HashMap<String, Object>();
		if (sum != null) {
			map.put("sumTxnAmt", StringUtil.formateAmt(sum.getSumTxnAmt()));//实际打款金额
		}
		JSONObject result = (JSONObject) JSON.toJSON(map);
		return result;
	}
	
}
