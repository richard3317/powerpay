package com.icpay.payment.bm.web.controller.business;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.RiskEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.db.dao.mybatis.model.RiskThreshold;
import com.icpay.payment.db.service.IKeyGenService;
import com.icpay.payment.db.service.IRiskThresholdService;

@Controller
@RequestMapping("/riskThreshold")
public class RiskThresholdController extends BaseController {
	
	private static final String RESULT_BASE_URI = "riskThreshold";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String ruleTp = m.get("ruleTp");
			m.put("ruleTpDesc", EnumUtil.translate(RiskEnums.ThresholdRuleTp.class, ruleTp, true));
			
			// 如果是金额阀值，则转换成元
			if (RiskEnums.ThresholdRuleTp._01.getCode().equals(ruleTp)) {
				String threhold = m.get("threhold");
				m.put("threhold", String.valueOf(new BigDecimal(threhold).movePointLeft(2)));
			}
			
			String riskTp = m.get("riskTp");
			m.put("riskTpDesc", EnumUtil.translate(RiskEnums.RiskTp.class, riskTp, true));
			
			String result = m.get("result");
			m.put("resultDesc", EnumUtil.translate(RiskEnums.Result.class, result, true));
			
			String status = m.get("status");
			m.put("statusDesc", EnumUtil.translate(RiskEnums.Status.class, status, true));
			
			String intTransCd = m.get("intTransCd");
			m.put("intTransCdDesc", EnumUtil.translate(RiskEnums.ThresholdTxnTp.class, intTransCd, true));
		}
	};

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
		return super.toAdd(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addSubmit(RiskThreshold entity, String expiredTmStr, String threholdStr) {
		AssertUtil.objIsNull(entity, "entity is null.");
		AssertUtil.strIsBlank(expiredTmStr, "expiredTmStr is blank.");
		
		IKeyGenService keyGenService = this.getDBService(IKeyGenService.class);
		int key = keyGenService.genRiskKey();
		entity.setRuleId((long) key);
		entity.setExpiredTm(DateUtil.parseDate(expiredTmStr, DateUtil.DATE_FORMAT_17));
		if (RiskEnums.ThresholdRuleTp._01.getCode().equals(entity.getRuleTp())) {
			BigDecimal bd = new BigDecimal(threholdStr);
			entity.setThrehold(Long.valueOf(bd.movePointRight(2).toString()));
		} else {
			entity.setThrehold(Long.valueOf(threholdStr));
		}
		entity.setLastOperId(this.getSessionUsrId());
		IRiskThresholdService riskThresholdService = this.getDBService(IRiskThresholdService.class);
		riskThresholdService.add(entity);
		this.logObj(BmEnums.FuncInfo._7000030000.getCode(), CommonEnums.OpType.ADD, entity);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		IRiskThresholdService riskThresholdService = this.getDBService(IRiskThresholdService.class);
		Pager<RiskThreshold> p = riskThresholdService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_RISKTHRESHOLD, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, int ruleId) {
		IRiskThresholdService riskThresholdService = this.getDBService(IRiskThresholdService.class);
		RiskThreshold entity = riskThresholdService.selectByPrimaryKey(ruleId);
		AssertUtil.objIsNull(entity, "未找到规则信息:" + ruleId);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_RISKTHRESHOLD));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_RISKTHRESHOLD, ENTITY_TRANSFER));
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, int ruleId) {
		IRiskThresholdService riskThresholdService = this.getDBService(IRiskThresholdService.class);
		RiskThreshold entity = riskThresholdService.selectByPrimaryKey(ruleId);
		AssertUtil.objIsNull(entity, "未找到规则信息:" + ruleId);
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_RISKTHRESHOLD, ENTITY_TRANSFER));
		model.addAttribute("expiredTmStr", DateUtil.formatDate(entity.getExpiredTm(), DateUtil.DATE_FORMAT_17));
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(RiskThreshold entity, String expiredTmStr, String threholdStr) {
		AssertUtil.objIsNull(entity, "entity is null.");
		AssertUtil.strIsBlank(expiredTmStr, "expiredTmStr is blank.");
		
		Long ruleId =entity.getRuleId();
		IRiskThresholdService riskThresholdService = this.getDBService(IRiskThresholdService.class);
		RiskThreshold dbEntity = riskThresholdService.selectByPrimaryKey(Integer.parseInt(ruleId.toString()));
		AssertUtil.objIsNull(dbEntity, "未找到规则记录");
		entity.setExpiredTm(DateUtil.parseDate(expiredTmStr, DateUtil.DATE_FORMAT_17));
		entity.setStatus(dbEntity.getStatus()); // 修改页面不修改状态字段
		if (RiskEnums.ThresholdRuleTp._01.getCode().equals(dbEntity.getRuleTp())) {
			BigDecimal bd = new BigDecimal(threholdStr);
			entity.setThrehold(Long.valueOf(bd.movePointRight(2).toString()));
		} else {
			entity.setThrehold(Long.valueOf(threholdStr));
		}
		entity.setLastOperId(this.getSessionUsrId());
		riskThresholdService.update(entity);
		this.logObj(BmEnums.FuncInfo._7000030000.getCode(), CommonEnums.OpType.UPDATE, entity);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/enable.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult enable(int ruleId) {
		IRiskThresholdService riskThresholdService = this.getDBService(IRiskThresholdService.class);
		RiskThreshold entity = riskThresholdService.selectByPrimaryKey(ruleId);
		AssertUtil.objIsNull(entity, "未找到规则信息:" + ruleId);
		AssertUtil.strEquals(RiskEnums.Status._1.getCode(), entity.getStatus(), "规则已经是\"启用\"状态");
		entity.setStatus(RiskEnums.Status._1.getCode());
		entity.setLastOperId(this.getSessionUsrId());
		riskThresholdService.update(entity);
		this.logObj(BmEnums.FuncInfo._7000030000.getCode(), CommonEnums.OpType.UPDATE, entity);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/invalid.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult invalid(int ruleId) {
		IRiskThresholdService riskThresholdService = this.getDBService(IRiskThresholdService.class);
		RiskThreshold entity = riskThresholdService.selectByPrimaryKey(ruleId);
		AssertUtil.objIsNull(entity, "未找到规则信息:" + ruleId);
		AssertUtil.strNotEquals(RiskEnums.Status._1.getCode(), entity.getStatus(), "规则状态不是\"启用\"");
		entity.setStatus(RiskEnums.Status._2.getCode());
		entity.setLastOperId(this.getSessionUsrId());
		riskThresholdService.update(entity);
		this.logObj(BmEnums.FuncInfo._7000030000.getCode(), CommonEnums.OpType.UPDATE, entity);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult delete(int ruleId) {
		IRiskThresholdService riskThresholdService = this.getDBService(IRiskThresholdService.class);
		RiskThreshold entity = riskThresholdService.selectByPrimaryKey(ruleId);
		AssertUtil.objIsNull(entity, "未找到规则信息:" + ruleId);
		riskThresholdService.delete(ruleId);
		this.logObj(BmEnums.FuncInfo._7000030000.getCode(), CommonEnums.OpType.DELETE, entity);
		return commonBO.buildSuccResp();
	}
}
