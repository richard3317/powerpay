package com.icpay.payment.bm.web.controller.system;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.AuthDataCache;
import com.icpay.payment.bm.cache.CacheManager;
import com.icpay.payment.bm.cache.CacheType;
import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.db.dao.mybatis.model.BmRoleInfo;
import com.icpay.payment.db.service.IBmRoleService;

@Controller
@RequestMapping("/system/bmRole/")
public class BmRoleController extends BaseController {

	private static final String RESULT_BASE_URI = "system/bmRole";
	
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
	public @ResponseBody AjaxResult addSubmit(BmRoleInfo roleInfo) {
		IBmRoleService bmRoleService = this.getDBService(IBmRoleService.class);
		bmRoleService.add(roleInfo);
		CacheManager.refreshCache(CacheType.AUTH_DATA_CACHE.getCacheTp());
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, int roleId) {
		IBmRoleService bmRoleService = this.getDBService(IBmRoleService.class);
		BmRoleInfo entity = bmRoleService.selectByRoleId(roleId);
		model.addAttribute(BMConstants.ENTITY_RESULT, entity);
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(BmRoleInfo roleInfo) {
		IBmRoleService bmRoleService = this.getDBService(IBmRoleService.class);
		bmRoleService.update(roleInfo);
		CacheManager.refreshCache(CacheType.AUTH_DATA_CACHE.getCacheTp());
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/roleFunc.do", method = RequestMethod.GET)
	public String roleFunc(int roleId, Model model) {
		IBmRoleService bmRoleService = this.getDBService(IBmRoleService.class);
		BmRoleInfo role = bmRoleService.selectByRoleId(roleId);
		AssertUtil.objIsNull(role, "角色不存在:" + roleId);
		String funcTreeData = AuthDataCache.getInstance().funcTreeData(bmRoleService.selectRoleFunc(roleId));
		model.addAttribute("role", role);
		model.addAttribute("funcTreeData", funcTreeData);
		return RESULT_BASE_URI + "/roleFunc";
	}
	
	@RequestMapping(value = "/roleFunc/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult roleFuncSubmit(int roleId, String[] funcCds) {
		Set<String> funcSet = new HashSet<String>();
		for (String funcCd : funcCds) {
			funcSet.add(funcCd);
		}
		IBmRoleService bmRoleService = this.getDBService(IBmRoleService.class);
		bmRoleService.saveRoleFunc(roleId, funcSet);
		CacheManager.refreshCache(CacheType.AUTH_DATA_CACHE.getCacheTp());
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult delete(int roleId) {
		IBmRoleService bmRoleService = this.getDBService(IBmRoleService.class);
		bmRoleService.delete(roleId);
		CacheManager.refreshCache(CacheType.AUTH_DATA_CACHE.getCacheTp());
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		IBmRoleService bmRoleService = this.getDBService(IBmRoleService.class);
		Pager<BmRoleInfo> p = bmRoleService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_BMROLEINFO, null));
	}
	
	@RequestMapping(value = "/detail.do", method = RequestMethod.GET)
	public String detail(Model model, int roleId) {
		IBmRoleService bmRoleService = this.getDBService(IBmRoleService.class);
		BmRoleInfo entity = bmRoleService.selectByRoleId(roleId);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_BMROLEINFO));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_BMROLEINFO, null));
		String funcTreeData = AuthDataCache.getInstance().funcTreeData(bmRoleService.selectRoleFunc(roleId));
		model.addAttribute("funcTreeData", funcTreeData);
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/copy.do", method = RequestMethod.GET)
	public String copy(Model model, int roleId) {
		IBmRoleService bmRoleService = this.getDBService(IBmRoleService.class);
		BmRoleInfo role = bmRoleService.selectByRoleId(roleId);
		AssertUtil.objIsNull(role, "角色不存在:" + roleId);
		model.addAttribute("copyRole", role);		
		return super.toPage(model, RESULT_BASE_URI, "copy");
	}
	
	@RequestMapping(value = "/copy/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addCopySubmit(BmRoleInfo roleInfo, int copyRoleId) {
		
		AssertUtil.argIsBlank(roleInfo.getRoleNm(), "角色名称不可为空");
		
		int currentRoleId = 0;
		IBmRoleService bmRoleService = this.getDBService(IBmRoleService.class);
		List<BmRoleInfo> BmRoleInfo = bmRoleService.queryRoleId(roleInfo.getRoleNm());
		if (BmRoleInfo.size() == 0) {
			bmRoleService.add(roleInfo);
			BmRoleInfo = bmRoleService.queryRoleId(roleInfo.getRoleNm());
			if (BmRoleInfo.size() == 1) {
				for (BmRoleInfo roleId : BmRoleInfo) {
					currentRoleId = roleId.getRoleId();
				}
				Set<String> rolefuncSet = new HashSet<String>(); 
				rolefuncSet = bmRoleService.selectRoleFunc(copyRoleId);
				bmRoleService.insertRoleFunc(currentRoleId, rolefuncSet);
				CacheManager.refreshCache(CacheType.AUTH_DATA_CACHE.getCacheTp());
			}
		} else {
			AssertUtil.objIsNotNull(BmRoleInfo, "角色名称已存在");
		}
		return commonBO.buildSuccResp();
	}
}
