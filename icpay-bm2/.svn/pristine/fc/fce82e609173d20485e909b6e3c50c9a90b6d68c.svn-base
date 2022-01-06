package com.icpay.payment.bm.web.controller.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.icpay.payment.bm.bo.MchntProfitBO;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountHour;
import com.icpay.payment.db.service.IChnlMchntAccountHourService;

@Controller
@RequestMapping("/chnlMchntAccountHour")
public class ChnlMchntAccountHourController extends BaseController {

	private static final String RESULT_BASE_URI = "chnlMchntAccountHour";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
		}
	};
	
	/**
	 * 跳转页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manageProfitRpt(Model model) {
		model.addAttribute("today", DateUtil.yesterday8());
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@Autowired
	protected MchntProfitBO mchntProfitBO;
	
	/**
	 * 导出报表
	 */
	@RequestMapping(value = "/exportRpt.do",method = RequestMethod.GET)
	public void exportTotalRpt(String qryStartDate, String qryEndDate, HttpServletResponse response) {
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("startDate", qryStartDate);
		qryParamMap.put("endDate", qryEndDate);
		IChnlMchntAccountHourService service = this.getDBService(IChnlMchntAccountHourService.class);
		List<ChnlMchntAccountHour> respList = service.selectByDate(qryParamMap);
		if (respList != null && !respList.isEmpty()) {
			String fileName = "chnlMchntAccountHour_" + qryStartDate + "_" + qryEndDate + ".xls";
			mchntProfitBO.doChnlMchntAccountHourReportFile(respList, fileName, Constant.UTF8, response);
		} else {
			AssertUtil.ifTrue(true, "查无资料");
		}
	}
	
}
