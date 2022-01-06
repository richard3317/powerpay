package com.icpay.payment.bm.web.controller.business;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.bo.MchntProfitBO;
import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.DailyProfitResultView;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MchntProfitToMerBean;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MchntProfitToWeekBean;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MchntTransHuanbiBean;
import com.icpay.payment.db.service.IDailyProfitResultViewService;

@Controller
@RequestMapping("/dailyProfitResultByChnl")
public class DailyProfitResultByChnlController extends BaseController {

	private static final String RESULT_BASE_URI = "dailyProfitResultByChnl";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String chnlId = m.get("chnlId");
			if (!Utils.isEmpty(chnlId))
				m.put("chnlIdDesc", EnumUtil.translate(TxnEnums.ChnlId.class, chnlId, true));
			
			/*String chnlMchntCd=m.get("chnlMchntCd");
			Map<String,String> chnlMchnt = ChnlMchntInfoCache.getInstance().get(chnlId, chnlMchntCd);
			if (!Utils.isEmpty(chnlMchnt)) {
				String chnlMchntDesc = chnlMchnt.get("chnlMchntDesc");
				m.put("chnlMchntDesc", chnlMchntDesc);
			}*/
			
			String txnAmtSum = m.get("txnAmtSum");
			m.put("txnAmtSumDesc", StringUtil.formateAmt(txnAmtSum));
			String agentProfit = m.get("agentProfit");
			m.put("agentProfitDesc", StringUtil.formateAmt(agentProfit));
			
		}
	};
		
	/**
	 * 跳到每日财报页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manageProfitRpt(Model model) {
		model.addAttribute("today", DateUtil.yesterday8());
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
/*		IProfitRptInfoService service = this.getDBService(IProfitRptInfoService.class);
		Pager<TransProfitReport> p = service.selectProfitPager(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_DAILYPROFITRESULTBYCHNL, ENTITY_TRANSFER));
*/		IDailyProfitResultViewService service = this.getDBService(IDailyProfitResultViewService.class);
		Pager<DailyProfitResultView> p = service.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_DAILYPROFITRESULTBYCHNL, ENTITY_TRANSFER));
	}
	
	@Autowired
	protected MchntProfitBO mchntProfitBO;
	/**
	 * 下载
	 * @param transDtBen
	 * @param transDtEnd
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/exportRpt.do",method = RequestMethod.GET)
	public void exportRpt(Model model, HttpServletResponse response) {
		IDailyProfitResultViewService service = this.getDBService(IDailyProfitResultViewService.class);
		List<MchntProfitToMerBean> respList = service.selectMchntProfit(this.getQryParamMap());
		String fileNm = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TRANS_PROFIT_EXPORT_FILENM) + "-" + DateUtil.now8() + ".xls";
		String filePath = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TRANS_PROFIT_FILE_PATH);
		mchntProfitBO.exportTransProfitExcel(respList,null,null,filePath, fileNm, Constant.UTF8,response);
		
//		String filename = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TRANS_PROFIT_EXPORT_FILENM)+ "_" + DateUtil.now8() + ".xls";
//		export(model, BMConstants.PAGE_CONF_DAILYPROFITRESULTBYCHNL, this.getQryParamMap(), filename, response);
	}
	
	protected AjaxResult export(Model model, String pageConfTp, Map<String, String> qryParamMap, String filename, HttpServletResponse response) {
		this.debug("[export] 每日财报导出, qryParamMap: %s", qryParamMap);
		IDailyProfitResultViewService service = this.getDBService(IDailyProfitResultViewService.class);
		List<DailyProfitResultView> list = service.select(qryParamMap);
		Pager<Map<String, String>> pager = commonBO.transferList(list, pageConfTp, ENTITY_TRANSFER);
		try {
			commonBO.exportToExcel(pager,null, filename, Constant.UTF8, response);
			return null;
		} catch (Exception e) {
			return commonBO.buildErrorResp(e.getMessage());
		}
	}
	

	@RequestMapping(value = "/exportWeekRpt.do",method = RequestMethod.GET)
	public void exportWeekRpt(Model model, HttpServletResponse response) {
		IDailyProfitResultViewService service = this.getDBService(IDailyProfitResultViewService.class);
		List<MchntProfitToWeekBean> respList = service.selectWeek();
		String fileNm = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TRANS_PROFIT_WEEK_FILENM) + "-" + DateUtil.now8() + ".xls";
		String filePath = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TRANS_PROFIT_FILE_PATH);
		mchntProfitBO.exportTransProfitExcel(respList,null,null,filePath, fileNm, Constant.UTF8,response);
	}
	
	@RequestMapping(value = "/exportHuanbiRpt.do",method = RequestMethod.GET)
	public void exportHuanbiRpt(Model model, HttpServletResponse response) {
		IDailyProfitResultViewService service = this.getDBService(IDailyProfitResultViewService.class);
		String settleDate = this.getQryParamMap().get("settleDate");
		List<MchntTransHuanbiBean> respList = service.selectHuanbi(this.getQryParamMap());
		Map<String,String> map = Utils.mergerMaps();
		map.put("settleDate", super.getSpecifiedDayBefore(settleDate)); //前一天的记录
		List<MchntTransHuanbiBean> beforeRespList = service.selectHuanbi(map);
		
		String fileNm = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TRANS_HUANBI_FILENM) + "_" + DateUtil.now8() + ".xls";
		String filePath = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TRANS_PROFIT_FILE_PATH);
		mchntProfitBO.exportTransProfitExcel(respList,beforeRespList,settleDate,filePath, fileNm, Constant.UTF8,response);
	}
}
