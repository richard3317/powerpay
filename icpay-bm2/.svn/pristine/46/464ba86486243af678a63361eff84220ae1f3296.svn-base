package com.icpay.payment.bm.web.controller.business;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.db.dao.mybatis.model.TransTypeGroup;
import com.icpay.payment.db.service.ITransTypeGroupService;

@Controller
@RequestMapping("/transTypeGroup")
public class TransTypeGroupController extends BaseController {

	private static final Logger logger = Logger.getLogger(TransTypeGroupController.class);
	
	private static final String RESULT_BASE_URI = "transTypeGroup";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
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
	public @ResponseBody AjaxResult addSubmit(String groupNm, String[] txnTypes) {
		AssertUtil.strIsBlank(groupNm, "groupNm is blank.");
		TransTypeGroup entity = new TransTypeGroup();
		entity.setGroupNm(groupNm);
		entity.setTransType(EnumUtil.buildCtrlRule(TxnEnums.TxnType.class, txnTypes, Constant.CHNL_DFT_TXN_TYPES));
		entity.setLastOperId(this.getSessionUsrId());
		ITransTypeGroupService service = this.getDBService(ITransTypeGroupService.class);
		service.add(entity);
		this.logObj(BmEnums.FuncInfo._1000060000.getCode(), CommonEnums.OpType.ADD, entity);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		ITransTypeGroupService service = this.getDBService(ITransTypeGroupService.class);
		Pager<TransTypeGroup> p = service.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT,
				commonBO.transferPager(p, BMConstants.PAGE_CONF_TRANSTYPEGROUP, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, int seqId) {
		ITransTypeGroupService service = this.getDBService(ITransTypeGroupService.class);
		TransTypeGroup entity = service.selectByPrimaryKey(seqId);
		AssertUtil.objIsNull(entity, "未找到记录:" + seqId);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_TRANSTYPEGROUP));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_TRANSTYPEGROUP, ENTITY_TRANSFER));
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, int seqId) {
		ITransTypeGroupService service = this.getDBService(ITransTypeGroupService.class);
		TransTypeGroup entity = service.selectByPrimaryKey(seqId);
		AssertUtil.objIsNull(entity, "未找到记录:" + seqId);
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_TRANSTYPEGROUP, ENTITY_TRANSFER));
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(TransTypeGroup entity, String[] txnTypes) {
		logger.info("修改交易权限组信息开始:" + entity.getSeqId());
		entity.setTransType(EnumUtil.buildCtrlRule(TxnEnums.TxnType.class, txnTypes, Constant.CHNL_DFT_TXN_TYPES));
		entity.setLastOperId(this.getSessionUsrId());
		ITransTypeGroupService service = this.getDBService(ITransTypeGroupService.class);
		service.update(entity);
		this.logObj(BmEnums.FuncInfo._1000060000.getCode(), CommonEnums.OpType.UPDATE, entity);
		logger.info("修改交易权限组信息完成:" + entity.getSeqId());
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult delete(int seqId) {
		logger.info("删除交易权限组信息开始:" + seqId);
		ITransTypeGroupService service = this.getDBService(ITransTypeGroupService.class);
		TransTypeGroup dbEntity = service.selectByPrimaryKey(seqId);
		AssertUtil.objIsNull(dbEntity, "未找到记录:" + seqId);
		service.delete(seqId);
		this.logObj(BmEnums.FuncInfo._1000050000.getCode(), CommonEnums.OpType.DELETE, dbEntity);
		logger.info("删除交易权限组信息完成:" + seqId);
		return commonBO.buildSuccResp();
	}
	
}
