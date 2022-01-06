package com.icpay.payment.bm.web.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.CacheManager;
import com.icpay.payment.bm.cache.CacheType;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.AssertUtil;

@Controller
@RequestMapping("/system/cache/")
public class CacheController extends BaseController {
	
	private static final String RESULT_BASE_URI = "system/cache";

	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		model.addAttribute("cacheTpMap", CacheType.getMap());
		return RESULT_BASE_URI + "/manage";
	}
	
	@RequestMapping(value = "/refresh.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult refresh(String cacheTp) {
		AssertUtil.strIsBlank(cacheTp, "cacheTp is blank.");
		if (cacheTp.equals("ALL")) {
			CacheManager.refreshAllCaches();
		} else {
			CacheManager.refreshCache(cacheTp);
		}
		this.logText(BmEnums.FuncInfo._9900050000.getCode(), 
				CommonEnums.OpType.REFRESH_CACHE, 
				"刷新缓存:" + (cacheTp.equals("ALL") ? "全部" : CacheType.getCacheType(cacheTp).getCacheTpDesc()));
		return commonBO.buildSuccResp();
	}
}
