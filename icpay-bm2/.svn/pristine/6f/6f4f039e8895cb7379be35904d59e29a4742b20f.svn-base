package com.icpay.payment.bm.web.controller.system;

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
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.db.dao.mybatis.model.BmOperLog;
import com.icpay.payment.db.service.IBmOperLogService;

@Controller
@RequestMapping("/system/bmOperLog/")
public class BmOperLogController extends BaseController {

	private static final Logger logger = Logger.getLogger(BmOperLogController.class);
	
	private static final String RESULT_BASE_URI = "system/bmOperLog";
	
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String opTp = m.get("opTp");
			m.put("opTpDesc", EnumUtil.translate(CommonEnums.OpType.class, opTp, true));
			
			String opFuncInfo = m.get("opFuncInfo");
			m.put("opFuncInfoDesc", EnumUtil.translate(BmEnums.FuncInfo.class, opFuncInfo, true));
		}
	};
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		model.addAttribute("today", DateUtil.now8());
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		if (logger.isDebugEnabled()) {
			logger.debug("查询操作日志");
		}
		IBmOperLogService bmOperLogService = this.getDBService(IBmOperLogService.class);
		Pager<BmOperLog> p = bmOperLogService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_BMOPERLOG, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, int logId) {
		if (logger.isDebugEnabled()) {
			logger.debug("查看操作日志详情");
		}
		IBmOperLogService bmOperLogService = this.getDBService(IBmOperLogService.class);
		BmOperLog log = bmOperLogService.selectByPrimaryKey(logId);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_BMOPERLOG));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(log, BMConstants.PAGE_CONF_BMOPERLOG, ENTITY_TRANSFER));
		return super.toDetail(model, RESULT_BASE_URI);
	}
}
