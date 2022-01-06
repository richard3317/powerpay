package com.icpay.payment.bm.web.controller.business;

import java.math.BigDecimal;
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
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.modelExt.ChnlMchntDaily;
import com.icpay.payment.db.service.ITxnLogViewService;

@Controller
@RequestMapping("/chnlMchntDaily")
public class ChnlMchntDailyController extends BaseController {

	private static final String RESULT_BASE_URI = "chnlMchntDaily";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			//渠道编号
			String chnlId = m.get("chnlId");
			m.put("chnlIdDesc", EnumUtil.translate(TxnEnums.ChnlId.class, chnlId, true));
			
			//币别
			String currCd = m.get("currCd");
			m.put("currCdDesc", EnumUtil.translate(BmEnums.CurrCdType.class, currCd, true));
			
			//支付金额
			String zhifuTransAt = m.get("zhifuTransAt");
			m.put("zhifuTransAtDesc", StringUtil.formateAmt(zhifuTransAt));
			
			//代付金额
			String daifuTransAt = m.get("daifuTransAt");
			m.put("daifuTransAtDesc", StringUtil.formateAmt(daifuTransAt));
			
			//冲销金额
			BigDecimal zhifuTransAtB = new BigDecimal(zhifuTransAt);
			System.out.println(zhifuTransAt);
			BigDecimal daifuTransAtB = new BigDecimal(daifuTransAt);
			BigDecimal chongXiaoAt  = zhifuTransAtB.subtract(daifuTransAtB);
			m.put("chongXiaoAtDesc", chongXiaoAt.movePointLeft(2).toString());
			
			//冻结金额
			String frozenBalance = m.get("frozenBalance");
			m.put("frozenBalanceDesc", StringUtil.formateAmt(frozenBalance));
		}
	};
		
	/**
	 * 跳到页面
	 */
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manageProfitRpt(Model model) {
		model.addAttribute("today", DateUtil.yesterday8());
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	/**
	 * 查詢
	 */
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		Map<String, String> qryParamMap = this.getQryParamMap();
		String recUpdTs = qryParamMap.get("recUpdTs");
		AssertUtil.objIsNull(recUpdTs, "日期不得为空");
		String mon = getMonth(recUpdTs);
		ITxnLogViewService service = this.getDBService(ITxnLogViewService.class);
		Pager<ChnlMchntDaily> p = service.selectChnlMchntDailyByPage(pageNum, pageSize, mon, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_CHNL_MCHNT_DAILY, ENTITY_TRANSFER));
	}
	
	/**
	 * 導出
	 */
	@RequestMapping(value = "/exportRpt.do",method = RequestMethod.GET)
	public void exportRpt(Model model, HttpServletResponse response) {
		Map<String, String> qryParamMap = this.getQryParamMap();
		String recUpdTs = qryParamMap.get("recUpdTs");
		AssertUtil.objIsNull(recUpdTs, "日期不得为空");
		String mon = getMonth(recUpdTs);
		ITxnLogViewService service = this.getDBService(ITxnLogViewService.class);
		List<ChnlMchntDaily> respList = service.selectChnlMchntDaily(mon, this.getQryParamMap());
		Pager<Map<String, String>> pager = commonBO.transferList(respList, BMConstants.PAGE_CONF_CHNL_MCHNT_DAILY, ENTITY_TRANSFER);
		String fileNm = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TRANS_EXPORT_FILENM);
		commonBO.exportToExcel(pager, fileNm, Constant.UTF8, response);
	}
}
