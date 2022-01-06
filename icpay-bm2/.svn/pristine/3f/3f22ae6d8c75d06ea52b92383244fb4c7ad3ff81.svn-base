package com.icpay.payment.bm.web.controller.business;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.db.dao.mybatis.model.MchntTermInfo;
import com.icpay.payment.db.service.IMchntTermInfoService;

@Controller
@RequestMapping("/mchntTerm")
public class MchntTermController extends BaseController {

	private static final Logger logger = Logger.getLogger(MchntTermController.class);
	
	private static final String RESULT_BASE_URI = "mchntTerm";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String mchntSt = m.get("mchntSt");
			m.put("mchntStDesc", EnumUtil.translate(CommonEnums.MchntSt.class, mchntSt, false));
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
	public @ResponseBody AjaxResult addSubmit(MchntTermInfo mchntTerm) {
		mchntTerm.setLastOperId(this.getSessionUsrId());
		IMchntTermInfoService mchntTermService = this.getDBService(IMchntTermInfoService.class);
		mchntTermService.add(mchntTerm);
		this.logObj(BmEnums.FuncInfo._1000020000.getCode(), CommonEnums.OpType.ADD, mchntTerm);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		if (logger.isDebugEnabled()) {
			logger.debug("qry mchnt started...");
		}
		IMchntTermInfoService mchntTermService = this.getDBService(IMchntTermInfoService.class);
		Pager<MchntTermInfo> p = mchntTermService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_MCHNTTERMINFO, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult delete(String mchntCd, String termSn) {
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		AssertUtil.strIsBlank(termSn, "termSn is blank.");
		IMchntTermInfoService mchntTermService = this.getDBService(IMchntTermInfoService.class);
		MchntTermInfo entity = mchntTermService.selectByPrimarykey(mchntCd, termSn);
		AssertUtil.objIsNull(entity, "记录不存在");
		mchntTermService.delete(mchntCd, termSn);
		this.logObj(BmEnums.FuncInfo._1000020000.getCode(), CommonEnums.OpType.DELETE, entity);
		return commonBO.buildSuccResp();
	}
}
