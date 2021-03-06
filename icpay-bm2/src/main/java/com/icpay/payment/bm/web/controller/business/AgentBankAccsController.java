package com.icpay.payment.bm.web.controller.business;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.bo.BusCheckBO;
import com.icpay.payment.bm.cache.BankNumCache;
import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant.COMMON_STATE;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.ProfitEnums.AgentAccUsage;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.AgentAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.AgentAccountInfoKey;
import com.icpay.payment.db.service.IAgentBankAccsService;
import com.icpay.payment.service.CommonSecService.TYPE;

@Controller
@RequestMapping("/agentBankAccs")
public class AgentBankAccsController extends BaseController {
	
	private static final String RESULT_BASE_URI = "agentBankAccs";
	
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String accountBankCode = m.get("accountBankCode");
			if (!Utils.isEmpty(accountBankCode))
				m.put("accountBankName", BankNumCache.getBankName(accountBankCode));
			
			String accountType = m.get("accountType");
			if (!Utils.isEmpty(accountType))
				m.put("accountTypeDesc", EnumUtil.translate(AgentAccUsage.class, accountType, true));
			
			String state = m.get("state");
			if (!Utils.isEmpty(state))
				m.put("stateDesc", EnumUtil.translate(TxnEnums.CommonValid.class, state, true));
			
			String maxCardAmt = m.get("maxCardAmt");
			if (!Utils.isEmpty(maxCardAmt))
				m.put("maxCardAmtDesc", toFloatAmt(maxCardAmt));
			
		}
	};
	
	private IAgentBankAccsService svc = null;
	
	protected IAgentBankAccsService getService() {
		if (svc==null)
			svc=this.getDBService(IAgentBankAccsService.class);
		return svc;
	}
	
	@Autowired
	private BusCheckBO busCheckBO;

	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	@QryMethod
	public String manage(Model model, String agentCd) {
		debug("%s/%s", RESULT_BASE_URI, agentCd);
		if (!Utils.isEmpty(agentCd))
			model.addAttribute("agentCd", agentCd);		
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
	public @ResponseBody AjaxResult addSubmit(AgentAccountInfo record, String maxCardAmtDesc) {
		AssertUtil.objIsNull(record, "????????????.");
		AssertUtil.strIsBlank(record.getAccountBankCode(), "???????????????");
		AssertUtil.strIsBlank(record.getAccountNo(), "????????????");
		AssertUtil.strIsBlank(record.getAccountName(), "??????????????????");
		AssertUtil.strIsBlank(record.getAccountType(), "????????????????????????");
		
		this.info("?????????????????????(??????)????????????: %s", record);
		
		AgentAccountInfo resQry=getService().selectByPrimaryKey(record);
		if (resQry!=null)
			throw new BizzException(String.format("????????????: ??????= %s|%s; ??????=%s" , record.getAccountNo(), record.getAccountName(), record.getAccountType()));
		
		record.setState(COMMON_STATE.YSE);
		record.setAccountBankName(BankNumCache.getBankName(record.getAccountBankCode()));
		if (!Utils.isEmpty(maxCardAmtDesc))
			record.setMaxCardAmt(Long.parseLong(toIntAmt(maxCardAmtDesc)));

		
		// ??????????????????
		//busCheckBO.newTask(BusCheckTaskEnums.TaskTp._04, commonBO.getSessionUser().getUsrId(), 
		//			CommonEnums.OpType.ADD, "?????????????????????", agentInfo);
		
		// ??????????????????
		//this.logObj(BmEnums.FuncInfo._1000080000.getCode(), CommonEnums.OpType.ADD_REQUEST, agentInfo);
		
		genSign(record);
		getService().add(record);
		info("?????????(??????)????????????????????????: %s", record);
		return commonBO.buildSuccResp();
	}
	
	
	protected void genSign(AgentAccountInfo rec) {
		Map<String,String> map = BeanUtil.desc(rec, null, null);
		String sign= getSecSvc().signMap(TYPE.AgentAccountInfo, map, true);
		rec.setSysMac(sign);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		
		Pager<AgentAccountInfo> p = getService().selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_AGENT_ACC_INFO, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, AgentAccountInfo record) {
		this.buildAgentData(model, record);
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, AgentAccountInfo record) {
		this.buildAgentData(model, record);
		this.buildCommonData(model);
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(AgentAccountInfo record, String maxCardAmtDesc) {
		AssertUtil.objIsNull(record, "????????????.");
		AssertUtil.strIsBlank(record.getAgentCd(), "?????????????????????");
		AssertUtil.strIsBlank(record.getAccountBankCode(), "???????????????");
		AssertUtil.strIsBlank(record.getAccountNo(), "????????????");
		AssertUtil.strIsBlank(record.getAccountName(), "??????????????????");
		AssertUtil.strIsBlank(record.getAccountType(), "????????????????????????");
		
		this.info("?????????????????????(??????)????????????: %s", record);
		
		//record.setState(COMMON_STATE.YSE);
		record.setAccountBankName(BankNumCache.getBankName(record.getAccountBankCode()));
		if (!Utils.isEmpty(maxCardAmtDesc))
			record.setMaxCardAmt(Long.parseLong(toIntAmt(maxCardAmtDesc)));
		genSign(record);
		getService().update(record);
		
		info("?????????(??????)????????????????????????: %s", record);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult delete(AgentAccountInfo record) {
		AssertUtil.objIsNull(record, "????????????.");
		AssertUtil.strIsBlank(record.getAgentCd(), "?????????????????????");
		AssertUtil.strIsBlank(record.getAccountNo(), "????????????");
		AssertUtil.strIsBlank(record.getAccountType(), "????????????????????????");
		
		this.info("?????????????????????(??????)????????????: %s", record);
		
		
		getService().delete(record);
		
		info("?????????(??????)????????????????????????: %s", record);
		return commonBO.buildSuccResp();
	}
	
	private void buildCommonData(Model model) {
		model.addAttribute("bankCodeList", BankNumCache.getBankNumsList());
	}
	
	private void buildAgentData(Model model, AgentAccountInfoKey key) {
		AssertUtil.objIsNull(key, "????????????");
		
		AgentAccountInfo resQry = this.getService().selectByPrimaryKey(key);
		AssertUtil.objIsNull(resQry, "????????????: " + key);
		model.addAttribute("record", resQry);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_AGENT_ACC_INFO));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(resQry, BMConstants.PAGE_CONF_AGENT_ACC_INFO, ENTITY_TRANSFER));
	}
}
