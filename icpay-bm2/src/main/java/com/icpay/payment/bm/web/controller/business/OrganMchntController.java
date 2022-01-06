package com.icpay.payment.bm.web.controller.business;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSON;
import com.icpay.payment.bm.bo.BusCheckBO;
import com.icpay.payment.bm.cache.MchntInfoCache;
import com.icpay.payment.bm.cache.OrganInfoCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.BusCheckTaskEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.CommonEnums.MchntSt;
import com.icpay.payment.common.enums.CommonEnums.MchntUserSt;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.PwdUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntUserInfoKey;
import com.icpay.payment.db.dao.mybatis.model.OrganInfo;
import com.icpay.payment.db.dao.mybatis.model.OrganMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.ViewOrganMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.modelExt.OrganMchntExtInfo;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.IMchntUserInfoService;
import com.icpay.payment.db.service.IOrganMchntInfoService;

@Controller
@RequestMapping("/organ")
public class OrganMchntController extends BaseController {

	private static final Logger logger = Logger.getLogger(ChnlController.class);
	
	private static final String RESULT_BASE_URI = "organ";
	public static String JG_ADMIN_USER = "superadmin";  //机构默认管理员
	public static String SU = "su";  //机构默认管理员用户
	
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String state = m.get("state");
			m.put("stateDesc", EnumUtil.translate(CommonEnums.ChnlSt.class, state, true));
			
//			String organId = m.get("organId");
//			Map<String,String> organ = OrganInfoCache.getInstance().get(organId);
//			if (!Utils.isEmpty(organ)) {
//				String organDesc = organ.get("organDesc");
//				m.put("organDesc", organDesc);
//			}
			
//			String mchntCd = m.get("mchntCd");
//			Map<String,String> mchnt = MchntInfoCache.getInstance().get(mchntCd);
//			if (!Utils.isEmpty(mchnt)) {
//				String mchntDesc = mchnt.get("mchntCnNm");
//				m.put("mchntCnNm", mchntDesc);
//			}
			
			
		}
	};
	
	@Autowired
	private BusCheckBO busCheckBO;
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		if (logger.isDebugEnabled()) {
			logger.debug("机构管理");
		}
		this.getOrgans(model);
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/backToManage.do", method = RequestMethod.GET)
	public String backToManage(Model model) {
		this.getOrgans(model);
		return super.toManage(model, true, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		if (logger.isDebugEnabled()) {
			logger.debug("qry 机构管理 started...");
		}
		IOrganMchntInfoService chnlService = this.getDBService(IOrganMchntInfoService.class);
		Pager<ViewOrganMchntInfo> p = chnlService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_ORGAN_MCHNT_INFO, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(Model model) {
		this.getOrgans(model);
		return super.toAdd(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addSubmit(OrganMchntExtInfo info, String mchntCdsList) {
//		AssertUtil.strIsBlank(info.getMchntCd(), "mchntCd is blank.");
		IOrganMchntInfoService service = this.getDBService(IOrganMchntInfoService.class);
		OrganInfo e = service.selectOrganInfoByOrganDesc(info.getOrganDesc());
		if(!Utils.isEmpty(e))
			info.setOrganId(Utils.isEmpty(info.getOrganId()) ? (Utils.isEmpty(e.getOrganId()) ? ""  : e.getOrganId())  : info.getOrganId());
		StringBuffer buf = new StringBuffer();
		if(!Utils.isEmpty(mchntCdsList)) {
			// 去重
			List<String> mchntList = Utils.strSplitToList(mchntCdsList, "\r\n", true);
			List<String> list = list_unique(mchntList);
			List<String> olist = new ArrayList<String>();
			for(int i=0 ;  i < list.size();i++ ) {
				String mchntCd =  list.get(i);
				OrganMchntInfo organ = service.selectMchntByMchnt(mchntCd);
				//去除商户号不正确/商户号无效的
				if(!checkMchntCd(mchntCd)) {
					buf.append(mchntCd).append(" 无效").append("\r\n");
				}else if(!Utils.isEmpty(info.getOrganId())) {// 如果机构已生成，检查商户号 是否已经 存在
					if(!Utils.isEmpty(organ) && !info.getOrganId().equals(organ.getOrganId())) {
						buf.append(mchntCd).append(" 已存在其他机构中").append("\r\n");
					}else if (!Utils.isEmpty(organ) && info.getOrganId().equals(organ.getOrganId())) {
						buf.append(mchntCd).append(" 已存在").append("\r\n");
					}else {
						olist.add(mchntCd);
					}
				}else if (!Utils.isEmpty(organ)) {
					buf.append(mchntCd).append(" 已存在其他机构中").append("\r\n");
				}else {
					olist.add(mchntCd);
				}
				if(Utils.isEmpty(list))
					break;
			}
			
			if(!Utils.isEmpty(buf)) {
				buf.append("\r\n").append("请更正后提交.");
				return commonBO.buildErrorResp(buf.toString());
			}
			String [] mchntCdArr = olist.toArray(new String[olist.size()]);
			info.setMchntCdStr(mchntCdArr);
			
			if((!Utils.isEmpty(info.getMchntCdStr()))) {
				info.setLastOperId(this.getSessionUsrId());
				info.setState(CommonEnums.ChnlSt._1.getCode());
				this.info(String.format("新增机构配置信息，操作员： %s 机构id：%s ", this.getSessionUsrId()  ,info.getOrganId()) );
				// 添加审核任务
				busCheckBO.newTask(BusCheckTaskEnums.TaskTp._07, commonBO.getSessionUser().getUsrId(), 
							CommonEnums.OpType.ADD, "新增机构信息", info);
						
				this.logObj(BmEnums.FuncInfo._1001010000.getDesc(), CommonEnums.OpType.ADD_REQUEST, info);
				return commonBO.buildSuccResp(buf.toString());
			}else {
				return commonBO.buildErrorResp(buf.toString());
			}
		}else if(!Utils.isEmpty(info.getOrganId())){
			return commonBO.buildErrorResp("机构已存在");
		}else {
			info.setLastOperId(this.getSessionUsrId());
			info.setState(CommonEnums.ChnlSt._1.getCode());
			this.info(String.format("新增机构配置信息，操作员： %s 机构id：%s ", this.getSessionUsrId()  ,info.getOrganId()) );
			// 添加审核任务
			busCheckBO.newTask(BusCheckTaskEnums.TaskTp._07, commonBO.getSessionUser().getUsrId(), 
						CommonEnums.OpType.ADD, "新增机构信息", info);
					
			this.logObj(BmEnums.FuncInfo._1001010000.getDesc(), CommonEnums.OpType.ADD_REQUEST, info);
			return commonBO.buildSuccResp();
		}
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult delete(OrganMchntExtInfo info,String organIdStr) {
//		info.setOrganIdStr());
		if(!Utils.isEmpty(organIdStr)) {
			String [] arr = organIdStr.split(";");
			List<OrganInfo> list = new ArrayList<OrganInfo>();
			for(String organId : arr) {
				OrganInfo o = new OrganInfo();
				o.setOrganDesc(OrganInfoCache.getInstance().getByOrganId(organId));
				o.setOrganId(organId);
				list.add(o);
			}
			info.setOrganIdList(list);
		}
		info.setLastOperId(this.getSessionUsrId());
		this.info(String.format("删除机构配置信息，操作员： %s , 机构编号：%s ", this.getSessionUsrId() ,Utils.isEmpty(organIdStr) ? info.getOrganId() : organIdStr));
		// 添加审核任务
		busCheckBO.newTask(BusCheckTaskEnums.TaskTp._07, commonBO.getSessionUser().getUsrId(), 
					CommonEnums.OpType.DELETE, "删除机构信息", info);
		
		this.logObj(BmEnums.FuncInfo._1001010000.getDesc(), CommonEnums.OpType.DELETE_REQUEST, info);
		return commonBO.buildSuccResp();
	}
	
	/**
	 * 查询全部
	 * @param organId
	 * @return
	 */
	public void getOrgans(Model model) {
		List<OrganInfo> list = OrganInfoCache.getInstance().getOrganInfoList();
		if(Utils.isEmpty(list))
			list = new ArrayList<OrganInfo>();
		model.addAttribute("organList" , list);
	}
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, String organId) {
		this.getOrgans(model);
		IOrganMchntInfoService service = this.getDBService(IOrganMchntInfoService.class);
//		OrganInfo key = new OrganInfo();
//		key.setMchntCd(mchntCd);
//		key.setOrganId(organId);
		OrganInfo entity = service.selectOrganInfoByOrganId(organId);
		AssertUtil.objIsNull(entity, "未找到机构配置信息");
//		model.addAttribute(BMConstants.ENTITY_RESULT,
//				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_ORGAN_MCHNT_INFO, ENTITY_TRANSFER));
		model.addAttribute("organId",organId);
		model.addAttribute("organDesc",entity.getOrganDesc());
		model.addAttribute("state",entity.getState());
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
//	public @ResponseBody AjaxResult editSubmit(OrganMchntInfo info) {
//		info.setLastOperId(this.getSessionUsrId());
//		this.info(String.format("更新机构配置，操作员： %s 机构id：%s ", this.getSessionUsrId() ,info.getOrganId()));
//		// 添加审核任务
//		busCheckBO.newTask(BusCheckTaskEnums.TaskTp._02, this.getSessionUsrId(), 
//					CommonEnums.OpType.UPDATE_REQUEST, "更新机构信息", info);
//		this.logObj(BmEnums.FuncInfo._1001010000.getDesc(), CommonEnums.OpType.UPDATE_REQUEST, info);
//		return commonBO.buildSuccResp();
//	}
	public @ResponseBody AjaxResult editSubmit(String organId,String organDesc,String state) {
		this.info(String.format("更新机构配置，操作员： %s 机构id：%s  机构名称 ： %s , 状态： %s", this.getSessionUsrId() ,organId , organDesc, state));
		IOrganMchntInfoService service = this.getDBService(IOrganMchntInfoService.class);
		OrganMchntExtInfo info = new OrganMchntExtInfo();
		info.setOrganId(organId);
		info.setOrganDesc(organDesc);
		info.setState(state);
		info.setLastOperId(this.getSessionUsrId());
		service.update(info);   // TODO   是否需要审核
		this.logObj(BmEnums.FuncInfo._1001010000.getDesc(), CommonEnums.OpType.UPDATE, info);
		//更新缓存
		OrganInfoCache.getInstance().needRefresh();
		
		OrganMchntInfo oinfo = new OrganMchntInfo();
		oinfo.setState(state);
		oinfo.setLastOperId(this.getSessionUsrId());
		
		Map<String,String> qryMap = new HashMap<String,String>();
		qryMap.put("organId", organId);
		service.updateOrganMchnt(oinfo, qryMap);
		return commonBO.buildSuccResp();
	}
	
	
//	@RequestMapping(value = "/checkMchntCd")
	public boolean checkMchntCd(String mchntCd) {
		IMchntInfoService mchntService = this.getDBService(IMchntInfoService.class);
		MchntInfo mchnt = mchntService.selectByPrimaryKey(mchntCd);
		if (mchnt == null) {
			return false;
		} else if (!CommonEnums.MchntSt._1.getCode().equals(mchnt.getMchntSt())) {
			return false;
		}
		return true;
	}
	
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, String organId) {
		IOrganMchntInfoService service = this.getDBService(IOrganMchntInfoService.class);
		OrganInfo entity = service.selectOrganInfoByOrganId(organId);
		AssertUtil.objIsNull(entity, "未找到机构信息:" + entity);
		model.addAttribute("entity", entity);
		
		List<OrganMchntInfo>  organMchntList = service.selectMchntByOrganId(organId);
		List<OrganMchntExtInfo> list = new ArrayList<OrganMchntExtInfo>();
		
		for(OrganMchntInfo info : organMchntList) {
			OrganMchntExtInfo extInfo = new OrganMchntExtInfo();
			extInfo.setMchntCd(info.getMchntCd());
			extInfo.setMchntCnNm(MchntInfoCache.getInstance().getMchntName(info.getMchntCd()));
			extInfo.setOrganId(info.getOrganId());
			list.add(extInfo);
		}
		model.addAttribute("organMchntList", list);
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value="/resetPwd.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult resetPwd(String organIdArr) {
		AssertUtil.strIsBlank(organIdArr, "重置数据为空" + organIdArr);
//		organIdArr = organIdArr.substring(0, organIdArr.length()-1);
		String [] organArr = organIdArr.split(",");
		String newPwd = "";
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		if(!Utils.isEmpty(organArr)) {
			for(String organId : organArr) {
				if(Utils.isEmpty(organId.trim()))
					continue;
				
				Map<String,String> map = new HashMap<String,String>();

				IMchntUserInfoService svc = this.getDBService(IMchntUserInfoService.class);
				MchntUserInfoKey key = new MchntUserInfoKey();
				key.setMchntCd(organId);
				key.setUserId(JG_ADMIN_USER);
				MchntUserInfo user = svc.selectByPrimaryKey(key);
				if(Utils.isEmpty(user)) {
	//				AssertUtil.objIsNull(user, "机构管理员不存在:" + organId);
					map.put("organId", organId);
					map.put("newPwd", "机构管理员不存在");
				}else {
					MchntUserInfo info = new MchntUserInfo();
					newPwd = StringUtil.randomPwd(10) ; ;
					info.setLoginPwd(PwdUtil.computeMD5Pwd(newPwd));
					info.setPwdUpdTs(DateUtil.now19());
					info.setLoginFailedCnt(0);
					info.setMchntCd(organId);
					info.setUserId(JG_ADMIN_USER);
					info.setUserRole(SU);
					info.setUserState(MchntUserSt._0.getCode());
					svc.updateByPrimaryKeySelective(info);
					
					map.put("organId", organId);
					map.put("newPwd", newPwd);
				}
				list.add(map);
			}
		}
		String json = JSON.toJSONString(list);
		// 记录操作日志
		this.logObj("机构基本信息密码修改", CommonEnums.OpType.UPDATE, json);
		return commonBO.buildSuccResp("respJson", json);
	}
	
	@RequestMapping(value="/resetGaCode.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult resetGaCode(String organIdArr) {
		AssertUtil.strIsBlank(organIdArr, "重置数据为空" + organIdArr);
//		organIdArr = organIdArr.substring(0, organIdArr.length()-1);
		String [] organArr = organIdArr.split(",");
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		if(!Utils.isEmpty(organArr)) {
			for(String organId : organArr) {
				if(Utils.isEmpty(organId.trim()))
					continue;
				
				Map<String,String> map = new HashMap<String,String>();
				
				IMchntUserInfoService svc = this.getDBService(IMchntUserInfoService.class);
				MchntUserInfoKey key = new MchntUserInfoKey();
				key.setMchntCd(organId);
				key.setUserId(JG_ADMIN_USER);
				MchntUserInfo user = svc.selectByPrimaryKey(key);
				if(Utils.isEmpty(user)) {
//				AssertUtil.objIsNull(user, "机构管理员不存在:" + organId);
					map.put("organId", organId + "机构管理员不存在");
				}else {
					MchntUserInfo info = new MchntUserInfo();
					info.setMchntCd(organId);
					info.setUserId(JG_ADMIN_USER);
					info.setUserRole(SU);
					info.setOtpSecret("");
					svc.updateByPrimaryKeySelective(info);
					
					map.put("organId", organId);
				}
				list.add(map);
			}
		}
		String json = JSON.toJSONString(list);
		// 记录操作日志
		this.logObj("机构基本信息谷歌验证码重置", CommonEnums.OpType.UPDATE, json);
		return commonBO.buildSuccResp("respJson", json);
	}

}
