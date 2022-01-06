package com.icpay.payment.bm.web.controller.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.bo.BusCheckBO;
import com.icpay.payment.bm.cache.ChnlMchntInfoCache;
import com.icpay.payment.bm.cache.MchntInfoCache;
import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant.COMMON_STATE;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.ProfitEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.AgentAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicy;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicyKey;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentForChnlMerEx;
import com.icpay.payment.db.service.IAgentBankAccsService;
import com.icpay.payment.db.service.IAgentProfitPolicyService;

@Controller
@RequestMapping("/agentProfitPolicy")
public class AgentProfitPolicyController extends BaseController {
	
	private static final String RESULT_BASE_URI = "agentProfitPolicy";
	
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			
			String intTransCd = m.get("intTransCd");
			if (!Utils.isEmpty(intTransCd))
				m.put("intTransCdDesc", EnumUtil.translate(ProfitEnums.ProfitTxnTp.class, intTransCd, true));
			
			String mchntCd=m.get("mchntCd");
			Map<String,String> mchnt = MchntInfoCache.getInstance().get(mchntCd);
			if (!Utils.isEmpty(mchnt)) {
				String mchntDesc = mchnt.get("mchntCnNm");
				m.put("mchntDesc", mchntDesc);
			}

			String chnlId = m.get("chnlId");
			if (!Utils.isEmpty(intTransCd))
				m.put("chnlIdDesc", EnumUtil.translate(TxnEnums.ChnlId.class, chnlId, true));
			
			String chnlMchntCd=m.get("chnlMchntCd");
			Map<String,String> chnlMchnt = ChnlMchntInfoCache.getInstance().get(chnlId, chnlMchntCd);
			if (!Utils.isEmpty(chnlMchnt)) {
				String chnlMchntDesc = chnlMchnt.get("chnlMchntDesc");
				m.put("chnlMchntDesc", chnlMchntDesc);
			}
			
			
			String state = m.get("state");
			if (!Utils.isEmpty(state))
				m.put("stateDesc", EnumUtil.translate(TxnEnums.CommonValid.class, state, true));
			
			String maxFee = m.get("maxFee");
			if (!Utils.isEmpty(maxFee))
				m.put("maxFeeDesc", toFloatAmt(maxFee));
			
			String minFee = m.get("minFee");
			if (!Utils.isEmpty(maxFee))
				m.put("minFeeDesc", toFloatAmt(minFee));
		}
	};
	
	private IAgentProfitPolicyService svc = null;
	
	protected IAgentProfitPolicyService getService() {
		if (svc==null)
			svc=this.getDBService(IAgentProfitPolicyService.class);
		return svc;
	}
	
	protected AgentProfitPolicyKey getKeyEntity(AgentProfitPolicyKey record) {
		AgentProfitPolicyKey k = new AgentProfitPolicyKey();
		k.cloneFrom(record);
		return k;
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
	public @ResponseBody AjaxResult addSubmit(AgentProfitPolicy record, String minFeeDesc, String maxFeeDesc) {
		AssertUtil.objIsNull(record, "數據內容為空.");
		AssertUtil.strIsBlank(record.getAgentCd(), "代理商編號为空");
		AssertUtil.strIsBlank(record.getIntTransCd(), "交易類为空");
		AssertUtil.strIsBlank(record.getMchntCd(), "商戶号为空");
		AssertUtil.strIsBlank(record.getChnlId(), "渠道編號为空");
		AssertUtil.strIsBlank(record.getChnlMchntCd(), "渠道商編为空");
		AssertUtil.strIsBlank(record.getRate(), "費率設定为空");
		
		this.info("请求新增代理商(中人)分潤策略: %s", record);
		
		AgentProfitPolicy resQry=getService().selectByPrimaryKey(record);
		if (resQry!=null)
			throw new BizzException(String.format("分潤策略重复: %s" , this.getKeyEntity(record)));
		
		setupProfitPolicyPriority(record);
		record.setState(COMMON_STATE.YSE);
		if (!Utils.isEmpty(minFeeDesc))
			record.setMinFee(toIntAmt(minFeeDesc));		
		if (!Utils.isEmpty(maxFeeDesc))
			record.setMaxFee(toIntAmt(maxFeeDesc));		
		record.setLastOperId(this.getSessionUsrId());
		record.setTradeType("00");
		
		this.getService().add(record);
		
		this.logObj(BmEnums.FuncInfo._1000090000.getCode(), CommonEnums.OpType.ADD, record);
		info("分润策略已新增: "+ record);
		return commonBO.buildSuccResp();		
	}
	
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, AgentProfitPolicy record) {
		this.buildAgentData(model, record);
		this.buildCommonData(model);
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(AgentProfitPolicy record, String minFeeDesc, String maxFeeDesc) {
		AssertUtil.objIsNull(record, "數據內容為空.");
		AssertUtil.strIsBlank(record.getAgentCd(), "代理商編號为空");
		AssertUtil.strIsBlank(record.getIntTransCd(), "交易類为空");
		AssertUtil.strIsBlank(record.getMchntCd(), "商戶号为空");
		AssertUtil.strIsBlank(record.getChnlId(), "渠道編號为空");
		AssertUtil.strIsBlank(record.getChnlMchntCd(), "渠道商編为空");
		AssertUtil.strIsBlank(record.getRate(), "費率設定为空");
		
		this.info("请求更新代理商(中人)分潤策略: %s", record);
		
		setupProfitPolicyPriority(record);
		if (!Utils.isEmpty(minFeeDesc))
			record.setMinFee(toIntAmt(minFeeDesc));		
		if (!Utils.isEmpty(maxFeeDesc))
			record.setMaxFee(toIntAmt(maxFeeDesc));		
		
		record.setLastOperId(this.getSessionUsrId());
		
		this.getService().update(record);
		
		this.logObj(BmEnums.FuncInfo._1000090000.getCode(), CommonEnums.OpType.UPDATE, record);
		info("分润策略已更新: "+ record);
		return commonBO.buildSuccResp();
		
		
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult delete(AgentProfitPolicy record) {
		AssertUtil.objIsNull(record, "數據內容為空.");
		AssertUtil.strIsBlank(record.getAgentCd(), "代理商編號为空");
		AssertUtil.strIsBlank(record.getIntTransCd(), "交易類为空");
		AssertUtil.strIsBlank(record.getMchntCd(), "商戶号为空");
		AssertUtil.strIsBlank(record.getChnlId(), "渠道編號为空");
		AssertUtil.strIsBlank(record.getChnlMchntCd(), "渠道商編为空");
		//AssertUtil.strIsBlank(record.getRate(), "費率設定为空");
		
		this.info("请求删除代理商(中人)分潤策略: %s", record);
		
		getService().delete(record);
		
		this.logObj(BmEnums.FuncInfo._1000090000.getCode(), CommonEnums.OpType.DELETE, record);
		info("分润策略已刪除: "+ record);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		
		Pager<AgentProfitPolicy> p = getService().selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_AGENTPROFITPOLICY, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, AgentProfitPolicy record) {
		this.buildAgentData(model, record);
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	
	
	private void buildCommonData(Model model) {
		//model.addAttribute("bankCodeList", BankNumCache.getBankNumsList());
	}
	
	private void buildAgentData(Model model, AgentProfitPolicyKey key) {
		AssertUtil.objIsNull(key, "数据为空");
		
		AgentProfitPolicy resQry = this.getService().selectByPrimaryKey(key);
		AssertUtil.objIsNull(resQry, "查无数据: " + key);
		model.addAttribute("record", resQry);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_AGENTPROFITPOLICY));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(resQry, BMConstants.PAGE_CONF_AGENTPROFITPOLICY, ENTITY_TRANSFER));
	}
	
	/**
	 * 依据"*"设定的数量配置优先等级
	 * @param profitPolicy
	 */
	protected void setupProfitPolicyPriority(AgentProfitPolicy profitPolicy) {
		if (profitPolicy==null) return;
		int baseP=50;
		int w=50;
		if ("*".equals(profitPolicy.getChnlMchntCd())) w+=10;
		if ("*".equals(profitPolicy.getMchntCd())) w+=10;
		if ("*".equals(profitPolicy.getChnlId())) w+=30;
		if ("*".equals(profitPolicy.getIntTransCd())) w+=40;
		profitPolicy.setPriority((w>50 ? baseP+w : baseP));
	}
	
	@RequestMapping(value = "/getChnlMchnts.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult getChnlMchnts(String chnlId) {
		List<Map<String,String>> list = new ArrayList<>();
		list = ChnlMchntInfoCache.getInstance().getByChnlId(chnlId);
		AjaxResult resp= commonBO.buildSuccResp("chnlMchntList", list);
		return resp;
	}

	
	
}
