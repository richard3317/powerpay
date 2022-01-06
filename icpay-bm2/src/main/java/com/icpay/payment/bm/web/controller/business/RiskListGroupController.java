package com.icpay.payment.bm.web.controller.business;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.cache.RiskGroupInfoCache;
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
import com.icpay.payment.db.dao.mybatis.model.RiskListGroup;
import com.icpay.payment.db.service.IKeyGenService;
import com.icpay.payment.db.service.IRiskListGroupService;

@Controller
@RequestMapping("/riskListGroup")
public class RiskListGroupController extends BaseController {

	private static final Logger logger = Logger.getLogger(RiskListGroupController.class);
	
	private static final String RESULT_BASE_URI = "riskListGroup";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String groupTp = m.get("groupTp");
			m.put("groupTpDesc", EnumUtil.translate(RiskEnums.GroupTp.class, groupTp, true));
			
			String listTp = m.get("listTp");
			m.put("listTpDesc", EnumUtil.translate(RiskEnums.ListTp.class, listTp, true));
			
			String status = m.get("status");
			m.put("statusDesc", EnumUtil.translate(RiskEnums.Status.class, status, true));
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
	public @ResponseBody AjaxResult addSubmit(RiskListGroup entity) {
		IRiskListGroupService riskListGroupService = this.getDBService(IRiskListGroupService.class);
		IKeyGenService keyGenService = this.getDBService(IKeyGenService.class);
		int groupId = keyGenService.genRiskKey();
		entity.setGroupId(groupId);
		entity.setLastOperId(this.getSessionUsrId());
		riskListGroupService.add(entity);
		RiskGroupInfoCache.getInstance().needRefresh();
		this.logObj(BmEnums.FuncInfo._7000010000.getCode(), CommonEnums.OpType.ADD, entity);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		if (logger.isDebugEnabled()) {
			logger.debug("qry mchnt started...");
		}
		IRiskListGroupService riskListGroupService = this.getDBService(IRiskListGroupService.class);
		Pager<RiskListGroup> p = riskListGroupService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_RISKLISTGROUP, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, int groupId) {
		IRiskListGroupService riskListGroupService = this.getDBService(IRiskListGroupService.class);
		RiskListGroup entity = riskListGroupService.selectByPrimaryKey(groupId);
		AssertUtil.objIsNull(entity, "未找到名单组信息:" + groupId);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_RISKLISTGROUP));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_RISKLISTGROUP, ENTITY_TRANSFER));
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, int groupId) {
		IRiskListGroupService riskListGroupService = this.getDBService(IRiskListGroupService.class);
		RiskListGroup entity = riskListGroupService.selectByPrimaryKey(groupId);
		AssertUtil.objIsNull(entity, "未找到名单组信息:" + groupId);
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_RISKLISTGROUP, ENTITY_TRANSFER));
		model.addAttribute("expiredTmStr", DateUtil.formatDate(entity.getExpiredTm(), DateUtil.DATE_FORMAT_17));
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(RiskListGroup entity, String expiredTmStr) {
		AssertUtil.objIsNull(entity, "entity is null.");
		AssertUtil.strIsBlank(expiredTmStr, "expiredTmStr is blank.");
		
		IRiskListGroupService riskListGroupService = this.getDBService(IRiskListGroupService.class);
		int groupId = entity.getGroupId();
		RiskListGroup dbEntity = riskListGroupService.selectByPrimaryKey(groupId);
		AssertUtil.objIsNull(dbEntity, "未找到名单组信息");
		entity.setExpiredTm(DateUtil.parseDate(expiredTmStr, DateUtil.DATE_FORMAT_17));
		entity.setStatus(dbEntity.getStatus()); // 修改页面不修改状态字段
		entity.setLastOperId(this.getSessionUsrId());
		riskListGroupService.update(entity);
		RiskGroupInfoCache.getInstance().needRefresh();
		this.logObj(BmEnums.FuncInfo._7000010000.getCode(), CommonEnums.OpType.UPDATE, entity);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/enable.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult enable(int groupId) {
		IRiskListGroupService riskListGroupService = this.getDBService(IRiskListGroupService.class);
		RiskListGroup entity = riskListGroupService.selectByPrimaryKey(groupId);
		AssertUtil.objIsNull(entity, "未找到名单组信息:" + groupId);
		AssertUtil.strEquals(RiskEnums.Status._1.getCode(), entity.getStatus(), "名单组信息已经是\"启用\"状态");
		entity.setStatus(RiskEnums.Status._1.getCode());
		entity.setLastOperId(this.getSessionUsrId());
		riskListGroupService.update(entity);
		this.logObj(BmEnums.FuncInfo._7000010000.getCode(), CommonEnums.OpType.UPDATE, entity);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/invalid.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult invalid(int groupId) {
		IRiskListGroupService riskListGroupService = this.getDBService(IRiskListGroupService.class);
		RiskListGroup entity = riskListGroupService.selectByPrimaryKey(groupId);
		AssertUtil.objIsNull(entity, "未找到名单组信息:" + groupId);
		AssertUtil.strNotEquals(RiskEnums.Status._1.getCode(), entity.getStatus(), "规则状态不是\"启用\"");
		entity.setStatus(RiskEnums.Status._2.getCode());
		entity.setLastOperId(this.getSessionUsrId());
		riskListGroupService.update(entity);
		this.logObj(BmEnums.FuncInfo._7000010000.getCode(), CommonEnums.OpType.UPDATE, entity);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult delete(int groupId) {
		IRiskListGroupService riskListGroupService = this.getDBService(IRiskListGroupService.class);
		RiskListGroup entity = riskListGroupService.selectByPrimaryKey(groupId);
		AssertUtil.objIsNull(entity, "未找到名单组信息:" + groupId);
		riskListGroupService.delete(groupId);
		RiskGroupInfoCache.getInstance().needRefresh();
		this.logObj(BmEnums.FuncInfo._7000010000.getCode(), CommonEnums.OpType.DELETE, entity);
		return commonBO.buildSuccResp();
	}
}
