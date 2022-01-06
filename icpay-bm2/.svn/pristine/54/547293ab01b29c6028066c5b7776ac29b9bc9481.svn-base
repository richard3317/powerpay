package com.icpay.payment.bm.web.controller.system;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.AuthDataCache;
import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.cache.ValidationConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.AjaxRespEnums;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.PwdUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.validate.ValidationHelper;
import com.icpay.payment.db.dao.mybatis.model.BmRoleInfo;
import com.icpay.payment.db.dao.mybatis.model.BmUserInfo;
import com.icpay.payment.db.service.IBmUserService;

@Controller
@RequestMapping("/system/bmUser/")
public class BmUserController extends BaseController {

	private static final String RESULT_BASE_URI = "system/bmUser";
	
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String roleId = m.get("roleId");
			String usrId = m.get("usrId");
			if (BMConfigCache.getSuperAdmin().equals(usrId)) {
				m.put("roleDesc", "超级管理员（不可删除）");
			} else {
				BmRoleInfo br = AuthDataCache.getInstance().getRoleInfo(Integer.valueOf(roleId));
				m.put("roleDesc", br != null ? br.getRoleId() + "-" + br.getRoleNm() : roleId);
			}
			
			String usrSt = m.get("usrSt");
			m.put("usrStDesc", EnumUtil.translate(CommonEnums.RecSt.class, usrSt, true));
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
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		IBmUserService bmUserService = this.getDBService(IBmUserService.class);
		Pager<BmUserInfo> p = bmUserService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_BMUSERINFO, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(Model model) {
		return super.toAdd(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addSubmit(BmUserInfo usrInfo, 
			String loginPwd1, String loginPwd2) {
		AssertUtil.objIsNull(usrInfo, "usrInfo is null.");
		ValidationHelper.validate(loginPwd1, 
				ValidationConfCache.getConfig(BMConstants.VALID_CONF_KEY_BMUSER_PWD));
		ValidationHelper.validate(loginPwd2, 
				ValidationConfCache.getConfig(BMConstants.VALID_CONF_KEY_BMUSER_PWD));
		AssertUtil.strNotEquals(loginPwd1, loginPwd2, "两次密码输入不一致");
		usrInfo.setPassword(PwdUtil.computeMD5Pwd(loginPwd1));
		IBmUserService bmUserService = this.getDBService(IBmUserService.class);
		bmUserService.add(usrInfo);
		this.logObj(BmEnums.FuncInfo._9900030000.getCode(), CommonEnums.OpType.ADD, usrInfo);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, String usrId) {
		IBmUserService bmUserService = this.getDBService(IBmUserService.class);
		BmUserInfo entity = bmUserService.selectByPrimaryKey(usrId);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_BMUSERINFO));
		model.addAttribute(BMConstants.ENTITY_RESULT, 
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_BMUSERINFO, ENTITY_TRANSFER));
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/chngPwd.do", method = RequestMethod.GET)
	public String chngPwd() {
		return RESULT_BASE_URI + "/chngPwd";
	}
	
	@RequestMapping(value = "/chngPwd/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult chngPwdSubmit(String oldPwd, String loginPwd1, String loginPwd2) {
		ValidationHelper.validate(oldPwd, 
				ValidationConfCache.getConfig(BMConstants.VALID_CONF_KEY_BMUSER_PWD));
		ValidationHelper.validate(loginPwd1, 
				ValidationConfCache.getConfig(BMConstants.VALID_CONF_KEY_BMUSER_PWD));
		ValidationHelper.validate(loginPwd2, 
				ValidationConfCache.getConfig(BMConstants.VALID_CONF_KEY_BMUSER_PWD));

		IBmUserService bmUserService = this.getDBService(IBmUserService.class);
		AssertUtil.strNotEquals(loginPwd1, loginPwd2, "两次密码输入不一致");
		String usrId = commonBO.getSessionUser().getUsrId();
		BmUserInfo u = bmUserService.selectByPrimaryKey(usrId);
		AssertUtil.strNotEquals(PwdUtil.computeMD5Pwd(oldPwd), u.getPassword(), "原密码输入错误");
		u.setPassword(PwdUtil.computeMD5Pwd(loginPwd1));
		bmUserService.update(u, true);
		
		this.logText(BmEnums.FuncInfo._9900030000.getDesc(), 
				CommonEnums.OpType.UPDATE, "用户修改登录密码:" + usrId);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult delete(String usrId) {
		AssertUtil.strIsBlank(usrId, "usrId is blank.");
		AssertUtil.strEquals(BMConfigCache.getSuperAdmin(), usrId, "不能删除超级管理员");

		IBmUserService bmUserService = this.getDBService(IBmUserService.class);
		BmUserInfo bu = bmUserService.selectByPrimaryKey(usrId);
		AssertUtil.objIsNull(bu, "未找到用户信息");
		bmUserService.delete(usrId);
		this.logObj(BmEnums.FuncInfo._9900030000.getCode(), CommonEnums.OpType.DELETE, bu);
		return commonBO.buildSuccResp();
	}
	
	/**
	 * 启用
	 */
	@RequestMapping(value = "/enable.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult enable(String usrId) {
		AssertUtil.strIsBlank(usrId, "usrId is blank.");
		AssertUtil.strEquals(BMConfigCache.getSuperAdmin(), usrId, "不能操作超级管理员");

		IBmUserService bmUserService = this.getDBService(IBmUserService.class);
		BmUserInfo bu = bmUserService.selectByPrimaryKey(usrId);
		AssertUtil.objIsNull(bu, "未找到用户信息");
		AssertUtil.strEquals(bu.getUsrSt(), CommonEnums.RecSt.VALID.getCode(), "该用户已经是有效状态");
		bu.setUsrSt(CommonEnums.RecSt.VALID.getCode());
		bmUserService.update(bu, false);
		this.logObj(BmEnums.FuncInfo._9900030000.getCode(), CommonEnums.OpType.UPDATE, bu);
		return commonBO.buildSuccResp();
	}
	
	/**
	 * 禁用
	 */
	@RequestMapping(value = "/disable.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult disable(String usrId) {
		AssertUtil.strIsBlank(usrId, "usrId is blank.");
		AssertUtil.strEquals(BMConfigCache.getSuperAdmin(), usrId, "不能操作超级管理员");

		IBmUserService bmUserService = this.getDBService(IBmUserService.class);
		BmUserInfo dbEntity = bmUserService.selectByPrimaryKey(usrId);
		AssertUtil.objIsNull(dbEntity, "未找到用户信息");
		AssertUtil.strEquals(dbEntity.getUsrSt(), CommonEnums.RecSt.INVALID.getCode(), "该用户已经是无效状态");
		dbEntity.setUsrSt(CommonEnums.RecSt.INVALID.getCode());
		bmUserService.update(dbEntity, false);
		this.logObj(BmEnums.FuncInfo._9900030000.getCode(), CommonEnums.OpType.UPDATE, dbEntity);
		return commonBO.buildSuccResp();
	}
	
	/**
	 * 重置密码
	 */
	@RequestMapping(value = "/resetPwd.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult resetPwd(String usrId) {
		AssertUtil.strIsBlank(usrId, "usrId is blank.");
		AssertUtil.strEquals(BMConfigCache.getSuperAdmin(), usrId, "不能操作超级管理员");

		IBmUserService bmUserService = this.getDBService(IBmUserService.class);
		BmUserInfo bu = bmUserService.selectByPrimaryKey(usrId);
		AssertUtil.objIsNull(bu, "未找到用户信息");
		String rondomPwd = StringUtil.randomPwd(10); // 随机生成10位密码
		bu.setPassword(PwdUtil.computeMD5Pwd(rondomPwd)); // 保存至数据库
		bmUserService.update(bu, true);
		// 将自动生成的密码传回页面显示
		this.logObj(BmEnums.FuncInfo._9900030000.getCode(), CommonEnums.OpType.UPDATE, bu);
		return commonBO.buildAjaxResp(AjaxRespEnums.SUCC, rondomPwd);
	}
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, String usrId) {
		IBmUserService bmUserService = this.getDBService(IBmUserService.class);
		BmUserInfo entity = bmUserService.selectByPrimaryKey(usrId);
		AssertUtil.objIsNull(entity, "未找到用户信息:" + usrId);
		AssertUtil.strEquals(BMConfigCache.getSuperAdmin(), entity.getUsrId(), "不能更新超级管理员");
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_BMUSERINFO, ENTITY_TRANSFER));
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(BmUserInfo entity) {
		AssertUtil.objIsNull(entity, "entity is null.");
		AssertUtil.strIsBlank(entity.getUsrId(), "usrId is blank.");
		AssertUtil.strEquals(BMConfigCache.getSuperAdmin(), entity.getUsrId(), "不能更新超级管理员");
		
		IBmUserService bmUserService = this.getDBService(IBmUserService.class);
		// 更新页面不修改密码
		BmUserInfo bu = bmUserService.selectByPrimaryKey(entity.getUsrId());
		AssertUtil.objIsNull(bu, "未找到用户信息");
		entity.setUsrSt(bu.getUsrSt()); // 更新页面不修改用户状态
		bmUserService.update(entity, false);
		// 记录操作日志
		this.logObj(BmEnums.FuncInfo._9900030000.getCode(), CommonEnums.OpType.UPDATE, entity);
		return commonBO.buildSuccResp();
	}
	
	@Override
	protected void preparePageData(Model model, String pageTp) {
		if (PAGE_TP_ADD.equals(pageTp) 
				|| PAGE_TP_MANAGE.equals(pageTp)
				|| PAGE_TP_EDIT.equals(pageTp)) {
			model.addAttribute("super_admin", BMConfigCache.getSuperAdmin());
			model.addAttribute("roleMap", buildRoleMap());
		}
	}
	
	private Map<String, String> buildRoleMap() {
		Map<Integer, BmRoleInfo> m = AuthDataCache.getInstance().getRoleInfoMap();
		Map<String, String> roleNmMap = new LinkedHashMap<String, String>();
		for (Integer roleId : m.keySet()) {
			roleNmMap.put(String.valueOf(roleId), m.get(roleId).getRoleNm());
		}
		return roleNmMap;
	}
}
