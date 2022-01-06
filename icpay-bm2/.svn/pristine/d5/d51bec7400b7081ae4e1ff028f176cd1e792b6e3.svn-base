package com.icpay.payment.bm.web.controller.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.BankInfoCaChe;
import com.icpay.payment.bm.cache.DataDicCache;
import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.cache.RegionInfoCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.SettleEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.enums.ProfitEnums.ProfitPeriod;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.AgentInfo;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitInfo;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicy;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitPolicyKey;
import com.icpay.payment.db.dao.mybatis.model.BankInfo;
import com.icpay.payment.db.dao.mybatis.model.RegionInfo;
import com.icpay.payment.db.service.IAgentInfoService;
import com.icpay.payment.db.service.IAgentProfitInfoService;
import com.icpay.payment.db.service.IAgentProfitPolicyService;
import com.icpay.payment.db.service.IBankInfoService;

@Controller
@RequestMapping("/agentProfit")
public class AgentProfitController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(AgentProfitController.class);
	
	private static final String RESULT_BASE_URI = "agentProfit";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String profitPeriod = m.get("profitPeriod");
			m.put("profitPeriodDesc", EnumUtil.translate(ProfitPeriod.class, profitPeriod, false));
		}
	};
	
	private static final IEntityTransfer PROFITPOLICY_ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String intTransCd = m.get("intTransCd");
			m.put("intTransCdDesc", EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, true));
			
			String maxFee = m.get("maxFee");
			if (!StringUtil.isBlank(maxFee)) {
				m.put("maxFee", StringUtil.formateAmt(maxFee));
			}
			
			String minFee = m.get("minFee");
			if (!StringUtil.isBlank(minFee)) {
				m.put("minFee", StringUtil.formateAmt(minFee));
			}
		}
	};

	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	@QryMethod
	public String manage(Model model, String agencCd) {
		if (!Utils.isEmpty(agencCd))
			model.addAttribute("agencCd", agencCd);
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/backToManage.do", method = RequestMethod.GET)
	public String backToManage(Model model) {
		return super.toManage(model, true, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(Model model) {
		this.buildCommonData(model);
		return super.toAdd(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addSubmit(AgentProfitInfo agentProfitInfo) {
		String agentCd = agentProfitInfo.getAgentCd();
		AssertUtil.strIsBlank(agentCd, "agentCd is blank.");
		IAgentInfoService agentService = this.getDBService(IAgentInfoService.class);
		AgentInfo agent = agentService.selectByPrimaryKey(agentCd);
		AssertUtil.objIsNull(agent, "无效的代理商:" + agentCd);
		
		IAgentProfitInfoService agentProfitService = this.getDBService(IAgentProfitInfoService.class);
		AgentProfitInfo dbAgentProfit = agentProfitService.selectByPrimaryKey(agentCd);
		AssertUtil.objIsNotNull(dbAgentProfit, "代理商分润信息已存在:" + agentCd);
		
		// 构建开户地址信息
		String accountAreaCode = agentProfitInfo.getAccountAreaCode();
		RegionInfo r = RegionInfoCache.getRegionInfo(accountAreaCode);
		AssertUtil.objIsNull(r, "地区码不存在：" + accountAreaCode);
		String provCd = r.getProvRegionCd();
		RegionInfo prov = RegionInfoCache.getRegionInfo(provCd);
		String cityCd = r.getCityRegionCd();
		RegionInfo city = RegionInfoCache.getRegionInfo(cityCd);
		String accountAreaInfo = prov.getRegionCnNm() + city.getRegionCnNm() + r.getRegionCnNm();
		agentProfitInfo.setAccountAreaInfo(accountAreaInfo);
		
		agentProfitInfo.setLastOperId(this.getSessionUsrId());
		logger.info("新增代理商分润信息开始:" + agentCd);
		agentProfitService.add(agentProfitInfo);
		logger.info("新增代理商分润信息完成:" + agentCd);
		
		this.logObj(BmEnums.FuncInfo._1000090000.getCode(), CommonEnums.OpType.ADD, agentProfitInfo);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		IAgentProfitInfoService service = this.getDBService(IAgentProfitInfoService.class);
		Pager<AgentProfitInfo> p = service.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_AGENTPROFITINFO, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, String agentCd) {
		this.buildAgentData(model, agentCd);
		IAgentProfitInfoService service = this.getDBService(IAgentProfitInfoService.class);
		AgentProfitInfo entity = service.selectByPrimaryKey(agentCd);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_AGENTPROFITINFO));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_AGENTPROFITINFO, ENTITY_TRANSFER));
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, String agentCd) {
		this.buildAgentData(model, agentCd);
		this.buildCommonData(model);
		
		IAgentProfitInfoService service = this.getDBService(IAgentProfitInfoService.class);
		AgentProfitInfo entity = service.selectByPrimaryKey(agentCd);
		AssertUtil.objIsNull(entity, "未找到代理商分润信息:" + agentCd);
		
		String accountAreaCode = entity.getAccountAreaCode();
		if (!StringUtil.isBlank(accountAreaCode)) {
			RegionInfo r = RegionInfoCache.getRegionInfo(accountAreaCode);
			AssertUtil.objIsNull(r, "地区码不合法：" + accountAreaCode);
			String provCd = r.getProvRegionCd();
			model.addAttribute("provCd", provCd);
			model.addAttribute("cityLst", RegionInfoCache.getCityLst(provCd));
			String cityCd = r.getCityRegionCd();
			if (RegionInfoCache.isDirectCity(provCd)) {
				model.addAttribute("cityCd", provCd);
				model.addAttribute("districtLst", RegionInfoCache.getDistrictLst(provCd));
			} else {
				model.addAttribute("cityCd", cityCd);
				model.addAttribute("districtLst", RegionInfoCache.getDistrictLst(cityCd));
			}
		}
		
		// 根据支行和联行号获取总行名称
		String bankBranchName = entity.getAccountBankName();
		String bankCd = entity.getAccountBankCode();
		if (!StringUtil.isBlank(bankBranchName) && !StringUtil.isBlank(bankCd)) {
			IBankInfoService bs = this.getDBService(IBankInfoService.class);
			BankInfo b = bs.qryBankInfoByBankBranchInfo(bankBranchName, bankCd);
			AssertUtil.objIsNull(b, "获取银行信息失败:" + bankBranchName);
			model.addAttribute("bankName", b.getBankName());
		}
		
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_AGENTPROFITINFO, ENTITY_TRANSFER));
		
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(AgentProfitInfo agentProfitInfo) {
		String agentCd = agentProfitInfo.getAgentCd();
		AssertUtil.strIsBlank(agentCd, "agentCd is blank.");
		logger.info("修改代理商分润信息开始:" + agentCd);
		IAgentProfitInfoService service = this.getDBService(IAgentProfitInfoService.class);
		AgentProfitInfo dbEntity = service.selectByPrimaryKey(agentCd);
		AssertUtil.objIsNull(dbEntity, "代理商分润信息不存在:" + agentCd);
		agentProfitInfo.setLastOperId(this.getSessionUsrId());
		
		// 构建开户地址信息
		String accountAreaCode = agentProfitInfo.getAccountAreaCode();
		RegionInfo r = RegionInfoCache.getRegionInfo(accountAreaCode);
		AssertUtil.objIsNull(r, "地区码不存在：" + accountAreaCode);
		String provCd = r.getProvRegionCd();
		RegionInfo prov = RegionInfoCache.getRegionInfo(provCd);
		String cityCd = r.getCityRegionCd();
		RegionInfo city = RegionInfoCache.getRegionInfo(cityCd);
		String accountAreaInfo = prov.getRegionCnNm() + city.getRegionCnNm() + r.getRegionCnNm();
		agentProfitInfo.setAccountAreaInfo(accountAreaInfo);
		
		service.update(agentProfitInfo);
		this.logObj(BmEnums.FuncInfo._1000090000.getCode(), CommonEnums.OpType.UPDATE, agentProfitInfo);
		logger.info("修改代理商分润信息完成:" + agentCd);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult delete(String agentCd) {
		AssertUtil.strIsBlank(agentCd, "agentCd is blank.");
		
		IAgentProfitInfoService service = this.getDBService(IAgentProfitInfoService.class);
		AgentProfitInfo dbEntity = service.selectByPrimaryKey(agentCd);
		AssertUtil.objIsNull(dbEntity, "代理商分润信息不存在");
		
		IAgentProfitPolicyService policyService = this.getDBService(IAgentProfitPolicyService.class);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("agentCd", agentCd);
		List<AgentProfitPolicy> lst = policyService.select(qryParamMap);
		if (lst != null && lst.size() > 0) {
			throw new BizzException("请先删除该代理商的分润策略");
		}
		
		service.delete(agentCd);
		
		// 记录操作日志
		this.logObj(BmEnums.FuncInfo._1000090000.getCode(), CommonEnums.OpType.DELETE, dbEntity);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/profitPolicy/manage.do", method = RequestMethod.GET)
	public String profitPolicyMng(Model model, String agentCd) {
		AssertUtil.strIsBlank(agentCd, "agentCd is blank.");
		IAgentProfitPolicyService service = this.getDBService(IAgentProfitPolicyService.class);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("agentCd", agentCd);
		List<AgentProfitPolicy> lst = service.select(qryParamMap);
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		for (AgentProfitPolicy p : lst) {
			Map<String, String> m = BeanUtil.desc(p, null, null);
			String intTransCd = p.getIntTransCd();
			m.put("intTransCdDesc", EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, true));
			
			String tradeType = p.getTradeType();
			m.put("tradeTypeDesc", DataDicCache.translate(Constant.DATA_DIC_DATA_TP.TRADE_TYPE, tradeType));
			
			String maxFee = m.get("maxFee");
			if (!StringUtil.isBlank(maxFee)) {
				m.put("maxFee", StringUtil.formateAmt(maxFee));
			}
			
			String minFee = m.get("minFee");
			if (!StringUtil.isBlank(minFee)) {
				m.put("minFee", StringUtil.formateAmt(minFee));
			}
			result.add(m);
		}
		model.addAttribute("agentCd", agentCd);
		model.addAttribute("result", result);
		return RESULT_BASE_URI + "/profitPolicy_mng";
	}
	
	@RequestMapping(value = "/profitPolicy/add.do", method = RequestMethod.GET)
	public String profitPolicyAdd(Model model, String agentCd) {
		AssertUtil.strIsBlank(agentCd, "agentCd is blank.");
		IAgentInfoService service = this.getDBService(IAgentInfoService.class);
		AgentInfo agent = service.selectByPrimaryKey(agentCd);
		AssertUtil.objIsNull(agent, "代理商不存在:" + agentCd);
		if (!CommonEnums.AgentSt._1.getCode().equals(agent.getAgentSt())) {
			throw new BizzException("代理商无效:" + agentCd);
		}
		model.addAttribute("agent", agent);
		return RESULT_BASE_URI + "/profitPolicy_add";
	}
	
	@RequestMapping(value = "/profitPolicy/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult profitPolicyAddSubmit(Model model, AgentProfitPolicy profitPolicy) {
		AssertUtil.objIsNull(profitPolicy, "待新增记录为null");
		profitPolicy.setTradeType("00");
		validateAgentProfitPolicy(profitPolicy);
		
		logger.info("新增分润策略开始");
		IAgentProfitPolicyService service = this.getDBService(IAgentProfitPolicyService.class);
		AgentProfitPolicy dbEntity = service.selectByPrimaryKey(profitPolicy);
		AssertUtil.objIsNotNull(dbEntity, "分润策略已存在");
		profitPolicy.setLastOperId(this.getSessionUsrId());
		setupProfitPolicyPriority(profitPolicy);
		service.add(profitPolicy);
		this.logObj(BmEnums.FuncInfo._1000090000.getCode(), CommonEnums.OpType.ADD, profitPolicy);
		logger.info("新增分润策略完成");
		return commonBO.buildSuccResp();
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
	
	@RequestMapping(value = "/profitPolicy/upd.do", method = RequestMethod.GET)
	public String profitPolicyUpd(Model model, String agentCd, String intTransCd, String mchntCd, String chnlId, String chnlMchntCd) {
		AssertUtil.strIsBlank(agentCd, "agentCd is blank.");
		AssertUtil.strIsBlank(intTransCd, "intTransCd is blank.");
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		AssertUtil.strIsBlank(chnlId, "chnlId is blank.");
		AssertUtil.strIsBlank(chnlMchntCd, "chnlMchntCd is blank.");
		
		IAgentProfitPolicyService service = this.getDBService(IAgentProfitPolicyService.class);
		AgentProfitPolicyKey key = new AgentProfitPolicyKey();
		key.setAgentCd(agentCd);
		key.setIntTransCd(intTransCd);
		key.setMchntCd(mchntCd);
		key.setChnlId(chnlId);
		key.setChnlMchntCd(chnlMchntCd);
		
		//key.setTradeType(tradeType);
		AgentProfitPolicy dbEntity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(dbEntity, "待更新记录不存在");
		
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(dbEntity, BMConstants.PAGE_CONF_AGENTPROFITPOLICY, PROFITPOLICY_ENTITY_TRANSFER));
		
		return RESULT_BASE_URI + "/profitPolicy_upd";
	}
	
	@RequestMapping(value = "/profitPolicy/upd/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult profitPolicyUpdSubmit(Model model, AgentProfitPolicy profitPolicy) {
		AssertUtil.objIsNull(profitPolicy, "待修改记录为null");
		validateAgentProfitPolicy(profitPolicy);
		
		logger.info("更新分润策略开始");
		IAgentProfitPolicyService service = this.getDBService(IAgentProfitPolicyService.class);
		AgentProfitPolicy dbEntity = service.selectByPrimaryKey(profitPolicy);
		AssertUtil.objIsNull(dbEntity, "待更新记录不存在");
		profitPolicy.setLastOperId(this.getSessionUsrId());
		setupProfitPolicyPriority(profitPolicy);
		service.update(profitPolicy);
		logger.info("更新分润策略完成");
		
		this.logObj(BmEnums.FuncInfo._1000050000.getCode(), CommonEnums.OpType.UPDATE, profitPolicy);
		
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/profitPolicy/delete.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult profitPolicyDelete(Model model, String agentCd, String intTransCd, String mchntCd, String chnlId, String chnlMchntCd) {
		AssertUtil.strIsBlank(agentCd, "agentCd is blank.");
		AssertUtil.strIsBlank(intTransCd, "intTransCd is blank.");
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		AssertUtil.strIsBlank(chnlId, "chnlId is blank.");
		AssertUtil.strIsBlank(chnlMchntCd, "chnlMchntCd is blank.");
		
		logger.info("删除分润策略开始");
		IAgentProfitPolicyService service = this.getDBService(IAgentProfitPolicyService.class);
		AgentProfitPolicyKey key = new AgentProfitPolicyKey();
		key.setAgentCd(agentCd);
		key.setIntTransCd(intTransCd);
		//key.setTradeType(tradeType);
		key.setMchntCd(mchntCd);
		key.setChnlId(chnlId);
		key.setChnlMchntCd(chnlMchntCd);
		
		AgentProfitPolicy dbEntity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(dbEntity, "待删除记录不存在");
		service.delete(key);
		this.logObj(BmEnums.FuncInfo._1000090000.getCode(), CommonEnums.OpType.DELETE, dbEntity);
		logger.info("删除分润策略完成");
		return commonBO.buildSuccResp();
	}
	
	private void buildCommonData(Model model) {
		model.addAttribute("provMap", RegionInfoCache.getProvMap());
		model.addAttribute("bankNameLst", BankInfoCaChe.getBankNameLst());
	}
	
	private void buildAgentData(Model model, String agentCd) {
		AssertUtil.strIsBlank(agentCd, "agentCd is blank.");
		IAgentInfoService service = this.getDBService(IAgentInfoService.class);
		AgentInfo agent = service.selectByPrimaryKey(agentCd);
		AssertUtil.objIsNull(agent, "未找到代理商信息:" + agentCd);
		model.addAttribute("agent", agent);
	}
	
	private void validateAgentProfitPolicy(AgentProfitPolicy agentProfitPolicy) {
		AssertUtil.strIsBlank(agentProfitPolicy.getAgentCd(), "agentCd is blank.");
		AssertUtil.strIsBlank(agentProfitPolicy.getIntTransCd(), "intTransCd is blank.");
		AssertUtil.strIsBlank(agentProfitPolicy.getTradeType(), "tradeType is blank.");
		String rate = agentProfitPolicy.getRate();
		AssertUtil.strIsBlank(rate, "请输入代理商扣率");
		BigDecimal bdRate = new BigDecimal(rate);
		String maxFee = agentProfitPolicy.getMaxFee();
		BigDecimal bdMax = null;
		if (!StringUtil.isBlank(maxFee)) {
			agentProfitPolicy.setMaxFee(StringUtil.transferAmt(maxFee));
			bdMax = (new BigDecimal(maxFee)).movePointRight(2);
		}
		String minFee = agentProfitPolicy.getMinFee();
		BigDecimal bdMin = null;
		if (!StringUtil.isBlank(minFee)) {
			agentProfitPolicy.setMinFee(StringUtil.transferAmt(minFee));
			bdMin = (new BigDecimal(minFee)).movePointRight(2);
		} 
		
		SettleEnums.SettleTxnType txnType = EnumUtil.parseEnumByCode(SettleEnums.SettleTxnType.class, agentProfitPolicy.getIntTransCd(), null);
		if (txnType==null) 
			throw new BizzException("不支持的交易类型");
		
		if (bdRate.compareTo(BigDecimal.ZERO) < 0) {
			throw new BizzException("正向交易扣率请输入正值");
		}
		if (bdMax != null && bdMax.compareTo(BigDecimal.ZERO) < 0) {
			throw new BizzException("正向交易封顶手续费请输入正值或0");
		}
		if (bdMin != null && bdMin.compareTo(BigDecimal.ZERO) < 0) {
			throw new BizzException("正向交易保底手续费请输入正值或0");
		}
		
		
//		if ( SettleEnums.SettleTxnType._0121.getCode().equals(agentProfitPolicy.getIntTransCd())//SettleEnums.SettleTxnType._0100.getCode().equals(agentProfitPolicy.getIntTransCd())
//			|| 	SettleEnums.SettleTxnType._0122.getCode().equals(agentProfitPolicy.getIntTransCd())
//			|| 	SettleEnums.SettleTxnType._0131.getCode().equals(agentProfitPolicy.getIntTransCd())
//			|| 	SettleEnums.SettleTxnType._0132.getCode().equals(agentProfitPolicy.getIntTransCd())
//			|| 	SettleEnums.SettleTxnType._0133.getCode().equals(agentProfitPolicy.getIntTransCd())
//			|| 	SettleEnums.SettleTxnType._0134.getCode().equals(agentProfitPolicy.getIntTransCd())
//			|| 	SettleEnums.SettleTxnType._0090.getCode().equals(agentProfitPolicy.getIntTransCd())
//			|| 	SettleEnums.SettleTxnType._0050.getCode().equals(agentProfitPolicy.getIntTransCd())
//			|| 	SettleEnums.SettleTxnType._5210.getCode().equals(agentProfitPolicy.getIntTransCd())
//			|| 	SettleEnums.SettleTxnType._5211.getCode().equals(agentProfitPolicy.getIntTransCd())
//			|| 	SettleEnums.SettleTxnType._0141.getCode().equals(agentProfitPolicy.getIntTransCd())
//			|| 	SettleEnums.SettleTxnType._0142.getCode().equals(agentProfitPolicy.getIntTransCd())
//			|| 	SettleEnums.SettleTxnType._0143.getCode().equals(agentProfitPolicy.getIntTransCd())
//			|| 	SettleEnums.SettleTxnType._0144.getCode().equals(agentProfitPolicy.getIntTransCd())
//				) {
//			if (bdRate.compareTo(BigDecimal.ZERO) < 0) {
//				throw new BizzException("正向交易扣率请输入正值");
//			}
//			if (bdMax != null && bdMax.compareTo(BigDecimal.ZERO) < 0) {
//				throw new BizzException("正向交易封顶手续费请输入正值或0");
//			}
//			if (bdMin != null && bdMin.compareTo(BigDecimal.ZERO) < 0) {
//				throw new BizzException("正向交易保底手续费请输入正值或0");
//			}
//		}
////		else if (SettleEnums.SettleTxnType._3100.getCode().equals(agentProfitPolicy.getIntTransCd())
////				|| SettleEnums.SettleTxnType._0400.getCode().equals(agentProfitPolicy.getIntTransCd())) {
////			if (bdRate.compareTo(BigDecimal.ZERO) > 0) {
////				throw new BizzException("反向交易扣率请输入负值");
////			}
////			if (bdMax != null && bdMax.compareTo(BigDecimal.ZERO) > 0) {
////				throw new BizzException("反向交易封顶手续费请输入负值或0");
////			}
////			if (bdMin != null && bdMin.compareTo(BigDecimal.ZERO) > 0) {
////				throw new BizzException("反向交易保底手续费请输入负值或0");
////			}
////		}
//		else {
//			throw new BizzException("不支持的交易类型");
//		}
		if (bdMax != null && bdMin != null) {
			if (bdMax.compareTo(bdMin) < 0) {
				throw new BizzException("封顶手续费不能低于保底手续费");
			}
		}
	}
}
