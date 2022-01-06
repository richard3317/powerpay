package com.icpay.payment.bm.web.controller.system;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.DataDicCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.DataDic;
import com.icpay.payment.db.service.IDataDicService;

@Controller
@RequestMapping("/system/dataDic/")
public class DataDicController extends BaseController {

	private static final Logger logger = Logger.getLogger(DataDicController.class);
	
	private static final String RESULT_BASE_URI = "system/dataDic";
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		model.addAttribute("allDataTp", DataDicCache.getAllDataTp());
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		if (logger.isDebugEnabled()) {
			logger.debug("qry started...");
		}
		IDataDicService dataDicService = this.getDBService(IDataDicService.class);
		Pager<DataDic> p = dataDicService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_DATADIC, null));
	}
}
