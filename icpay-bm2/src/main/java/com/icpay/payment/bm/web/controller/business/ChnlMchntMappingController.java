package com.icpay.payment.bm.web.controller.business;

import java.util.Map;

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
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntMappingInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntMappingInfoKey;
import com.icpay.payment.db.service.IChnlMchntMappingInfoService;

@Controller
@RequestMapping("/chnlMchntMapping")
public class ChnlMchntMappingController extends BaseController {

	private static final String RESULT_BASE_URI = "chnlMchntMapping";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String chnlId = m.get("chnlId");
			m.put("chnlIdDesc", EnumUtil.translate(TxnEnums.ChnlId.class, chnlId, true));
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
		IChnlMchntMappingInfoService chnlMchntMappingInfoService = this.getDBService(IChnlMchntMappingInfoService.class);
		Pager<ChnlMchntMappingInfo> p = chnlMchntMappingInfoService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_CHNLMCHNTMAPPINGINFO, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(Model model) {
		return super.toAdd(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addSubmit(ChnlMchntMappingInfo entity) {
		IChnlMchntMappingInfoService chnlMchntMappingInfoService = this.getDBService(IChnlMchntMappingInfoService.class);
		entity.setLastOperId(this.getSessionUsrId());
		chnlMchntMappingInfoService.add(entity);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult delete(ChnlMchntMappingInfoKey key) {
		IChnlMchntMappingInfoService chnlMchntMappingInfoService = this.getDBService(IChnlMchntMappingInfoService.class);
		chnlMchntMappingInfoService.delete(key);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/cmt.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult cmt(ChnlMchntMappingInfoKey key, String comment) {
		AssertUtil.strIsBlank(key.getMchntCd(), "mchntCd is blank.");
		AssertUtil.strIsBlank(key.getChnlId(), "chnlId is blank.");
		AssertUtil.strIsBlank(key.getChnlMchntCd(), "chnlMchntCd is blank.");
		
		IChnlMchntMappingInfoService chnlMchntMappingInfoService = this.getDBService(IChnlMchntMappingInfoService.class);
		chnlMchntMappingInfoService.cmt(key, comment == null ? "" : comment, this.getSessionUsrId());
		return commonBO.buildSuccResp();
	}
}
