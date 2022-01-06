package com.icpay.payment.bm.web.controller.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.db.dao.mybatis.model.RptInfo;
import com.icpay.payment.db.dao.mybatis.model.TransProfitReport;
import com.icpay.payment.db.service.IProfitRptInfoService;
import com.icpay.payment.db.service.IRptInfoService;

@Controller
@RequestMapping("/rpt")
public class RptController extends BaseController {
	

	private static final String RESULT_BASE_URI = "rpt";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			m.put("rptTpDesc", EnumUtil.translate(CommonEnums.RptTp.class, m.get("rptTp"), false));
		}
	};
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		IRptInfoService service = this.getDBService(IRptInfoService.class);
		Pager<RptInfo> p = service.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_RPTINFO, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/downRpt.do")
	public String downRpt(int seqId, HttpServletResponse response) {
		IRptInfoService service = this.getDBService(IRptInfoService.class);
		RptInfo entity = service.selectByPrimaryKey(seqId);
		AssertUtil.objIsNull(entity, "未找到记录:" + seqId);
		String rptPath = entity.getRptPath();
		this.logText(BmEnums.FuncInfo._9200010000.getDesc(), 
				CommonEnums.OpType.DOWNLOAD_FILE, "下载批量报表:" + entity.getRptNm());
		commonBO.downExcelFile(rptPath, entity.getRptNm() + ".xls", response);
		return null;
	}
	
	/**
	 * 跳到每日财报页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/manageProfitRpt.do", method = RequestMethod.GET)
	public String manageProfitRpt(Model model) {
		model.addAttribute("today", DateUtil.yesterday8());
		return RESULT_BASE_URI+"/profitReportManage";
	}
	/**
	 * 下载
	 * @param transDtBen
	 * @param transDtEnd
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/exportRpt.do",method = RequestMethod.GET)
	public String exportRpt(String startOperDt,String endOpDt, HttpServletResponse response) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("startOperDt", startOperDt);
		map.put("endOpDt", endOpDt);
		IProfitRptInfoService service = this.getDBService(IProfitRptInfoService.class);
		List<TransProfitReport> respList = service.selectProfit(map);
		String fileNm = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TRANS_PROFIT_EXPORT_FILENM) + "_" + DateUtil.now8() + ".xls";
		String filePath = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TRANS_PROFIT_FILE_PATH);
		commonBO.exportTransProfitExcel(respList,filePath, fileNm, Constant.UTF8,response);
		return null;
		
	}
}
