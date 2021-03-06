package com.icpay.payment.bm.web.controller.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.bo.BusCheckBO;
import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.cache.RegionInfoCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant.AGENT_TYPE;
import com.icpay.payment.common.entity.AgentStCd;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.AjaxRespEnums;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.BusCheckTaskEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.ProfitEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.PwdUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.AgentInfo;
import com.icpay.payment.db.dao.mybatis.model.AgentProfitInfo;
import com.icpay.payment.db.service.IAgentInfoService;
import com.icpay.payment.db.service.IAgentProfitInfoService;

@Controller
@RequestMapping("/agent")
public class AgentController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(AgentController.class);
	
	private static final String RESULT_BASE_URI = "agent";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			
			String agentType = m.get("agentType");
			m.put("agentTypeDesc", EnumUtil.translate(ProfitEnums.AgentType.class, agentType, true));
			
			String areaCd = m.get("areaCd");
			m.put("areaCdDesc", RegionInfoCache.regionDesc(areaCd));
			
			String agentSt = m.get("agentSt");
			m.put("agentStDesc", EnumUtil.translate(CommonEnums.AgentSt.class, agentSt, true));
			
			String deposit = m.get("deposit");
			m.put("deposit", EnumUtil.translate(TxnEnums.CommonYesNo.class, deposit, true));
		}
	};
	
	@Autowired
	private BusCheckBO busCheckBO;

	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
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
	public @ResponseBody AjaxResult addSubmit(AgentInfo agentInfo, AgentStCd agentStCd) {
		AssertUtil.objIsNull(agentInfo, "agentInfo is null.");
		AssertUtil.strIsBlank(agentInfo.getAgentCnNm(), "???????????????");
		AssertUtil.strIsBlank(agentInfo.getAgentEnNm(), "???????????????");
		AssertUtil.strIsBlank(agentInfo.getAgentCnAbbr(), "??????????????????");
		AssertUtil.strIsBlank(agentInfo.getAgentEnAbbr(), "??????????????????");
		//AssertUtil.strIsBlank(agentInfo.getAreaCd(), "???????????????");
		AssertUtil.strIsBlank(agentInfo.getAgentType(), "?????????????????????");
		AssertUtil.ifTrue(AGENT_TYPE.SELF.equals(agentInfo.getAgentType()), "?????????????????????????????????");
		
		IAgentInfoService service = this.getDBService(IAgentInfoService.class);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("fullAgentCnNm", agentInfo.getAgentCnNm());
		List<AgentInfo> lst = service.select(qryParamMap);
		if (lst != null && lst.size() > 0) {
			throw new BizzException("??????????????????:" + agentInfo.getAgentCnNm());
		}
		
		agentInfo.setAgentSt(CommonEnums.AgentSt._1.getCode()); // ????????????????????????1-??????
		agentInfo.setStCd(agentStCd.toStCd());
		// ??????????????????
		busCheckBO.newTask(BusCheckTaskEnums.TaskTp._04, commonBO.getSessionUser().getUsrId(), 
					CommonEnums.OpType.ADD, "?????????????????????", agentInfo);
		// ??????????????????
		this.logObj(BmEnums.FuncInfo._1000080000.getCode(), CommonEnums.OpType.ADD_REQUEST, agentInfo);
		
		logger.info("????????????????????????????????????");
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		IAgentInfoService service = this.getDBService(IAgentInfoService.class);
		Pager<AgentInfo> p = service.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_AGENTINFO, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, String agentCd) {
		this.buildAgentData(model, agentCd);
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, String agentCd) {
		this.buildAgentData(model, agentCd);
		this.buildCommonData(model);
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(AgentInfo agentInfo) {
		AssertUtil.objIsNull(agentInfo, "agentInfo is null.");
		AssertUtil.strIsBlank(agentInfo.getAgentCnNm(), "???????????????");
		AssertUtil.strIsBlank(agentInfo.getAgentEnNm(), "???????????????");
		AssertUtil.strIsBlank(agentInfo.getAgentCnAbbr(), "??????????????????");
		AssertUtil.strIsBlank(agentInfo.getAgentEnAbbr(), "??????????????????");
		AssertUtil.strIsBlank(agentInfo.getAgentType(), "?????????????????????");
		AssertUtil.ifTrue(AGENT_TYPE.SELF.equals(agentInfo.getAgentType()), "?????????????????????????????????");		
		
		IAgentInfoService service = this.getDBService(IAgentInfoService.class);
		AgentInfo dbEntity = service.selectByPrimaryKey(agentInfo.getAgentCd());
		AssertUtil.objIsNull(dbEntity, "????????????????????????");
		
		// ??????????????????
		agentInfo.setAreaCd(dbEntity.getAreaCd());
		busCheckBO.newTask(BusCheckTaskEnums.TaskTp._04, commonBO.getSessionUser().getUsrId(), 
				CommonEnums.OpType.UPDATE, "?????????????????????", agentInfo);
		// ??????????????????
		this.logObj(BmEnums.FuncInfo._1000080000.getCode(), CommonEnums.OpType.UPDATE_REQUEST, agentInfo);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult delete(String agentCd) {
		AssertUtil.strIsBlank(agentCd, "agentCd is blank.");
		
		IAgentInfoService service = this.getDBService(IAgentInfoService.class);
		AgentInfo dbEntity = service.selectByPrimaryKey(agentCd);
		AssertUtil.objIsNull(dbEntity, "??????????????????");
		
		IAgentProfitInfoService profitService = this.getDBService(IAgentProfitInfoService.class);
		AgentProfitInfo profitInfo = profitService.selectByPrimaryKey(agentCd);
		if (profitInfo != null) {
			return commonBO.buildErrorResp("?????????????????????????????????");
		}
		
		// ??????????????????
		busCheckBO.newTask(BusCheckTaskEnums.TaskTp._04, commonBO.getSessionUser().getUsrId(), 
				CommonEnums.OpType.DELETE, "?????????????????????", dbEntity);
		// ??????????????????
		this.logObj(BmEnums.FuncInfo._1000080000.getCode(), CommonEnums.OpType.DELETE_REQUEST, dbEntity);
		return commonBO.buildSuccResp();
	}
	
	/**
	 * ????????????
	 */
	@RequestMapping(value = "/resetPwd.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult resetPwd(String agentCd) {
		AssertUtil.strIsBlank(agentCd, "agentCd is blank.");

		IAgentInfoService service = this.getDBService(IAgentInfoService.class);
		AgentInfo agentInfo = service.selectByPrimaryKey(agentCd);
		AssertUtil.objIsNull(agentInfo, "????????????????????????");
		String rondomPwd = StringUtil.randomPwd(10); // ????????????10?????????
		agentInfo.setPassword(PwdUtil.computeMD5Pwd(rondomPwd)); // ??????????????????
		service.update(agentInfo);
		// ??????????????????????????????????????????
		this.logObj(BmEnums.FuncInfo._9900030000.getDesc(), CommonEnums.OpType.UPDATE, agentInfo);
		return commonBO.buildAjaxResp(AjaxRespEnums.SUCC, rondomPwd);
	}
	
	private void buildCommonData(Model model) {
		model.addAttribute("provMap", RegionInfoCache.getProvMap());
	}
	
	private void buildAgentData(Model model, String agentCd) {
		AssertUtil.strIsBlank(agentCd, "agentCd is blank.");
		IAgentInfoService service = this.getDBService(IAgentInfoService.class);
		AgentInfo agent = service.selectByPrimaryKey(agentCd);
		AssertUtil.objIsNull(agent, "????????????????????????:" + agentCd);
		model.addAttribute("agent", agent);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_AGENTINFO));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(agent, BMConstants.PAGE_CONF_AGENTINFO, ENTITY_TRANSFER));
	}
}
