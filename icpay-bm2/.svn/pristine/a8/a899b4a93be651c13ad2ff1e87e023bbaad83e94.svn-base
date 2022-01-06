package com.icpay.payment.bm.web.controller.business;

import java.util.Map;

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
import com.icpay.payment.common.enums.ProfitEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BizUtils;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.SettleProfitResult;
import com.icpay.payment.db.service.ISettleProfitResultService;

/**
 * 代理商清算分润结果管理
 * @author lijin
 *
 */
@Controller
@RequestMapping("/settleProfitResult")
public class SettleProfitResultController extends BaseController {

	private static final String RESULT_BASE_URI = "settleProfitResult";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String transAt = m.get("transAt");
			m.put("transAt", StringUtil.formateAmt(transAt));
			
			String settleAt = m.get("settleAt");
			m.put("settleAt", StringUtil.formateAmt(settleAt));
			
			String feeAt = m.get("feeAt");
			m.put("feeAt", StringUtil.formateAmt(feeAt));
			
			String profitAt = m.get("profitAt");
			m.put("profitAt", StringUtil.formateAmt(profitAt));
			
			String accNo = m.get("accNo");
			if (StringUtil.isBlank(accNo)) {
				m.put("accNoDesc", "");
			} else {
//				m.put("accNoDesc", accNo.length() > 7 ? StringUtil.mask(accNo, 4, accNo.length() - 6, '*') : accNo);
				m.put("accNoDesc", BizUtils.strMask(accNo, "*", 4, 0, 6));
			}
			
			String profitSt = m.get("profitSt");
			m.put("profitStDesc", EnumUtil.translate(ProfitEnums.ProfitResultState.class, profitSt, true));
		}
	};
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		ISettleProfitResultService service = this.getDBService(ISettleProfitResultService.class);
		Pager<SettleProfitResult> p = service.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_SETTLEPROFITRESULT, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, String transSeqId) {
		ISettleProfitResultService service = this.getDBService(ISettleProfitResultService.class);
		SettleProfitResult entity = service.selectByPrimaryKey(transSeqId);
		AssertUtil.objIsNull(entity, "未找到记录:" + transSeqId);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_SETTLEPROFITRESULT));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_SETTLEPROFITRESULT, ENTITY_TRANSFER));
		return super.toDetail(model, RESULT_BASE_URI);
	}
}
