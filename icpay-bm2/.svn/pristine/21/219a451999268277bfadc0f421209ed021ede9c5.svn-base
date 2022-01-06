package com.icpay.payment.bm.web.controller.business;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.ColumnInfo;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.SettleEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.FileUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.MerSettleTaskLog;
import com.icpay.payment.db.service.IMerSettleTaskLogService;

@Controller
@RequestMapping("/merSettleTask")
public class MerSettleTaskLogController extends BaseController {

	private static final String RESULT_BASE_URI = "merSettleTask";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String transAt = m.get("transAt");
			m.put("transAtDesc", StringUtil.formateAmt(transAt));
			
			String settleAt = m.get("settleAt");
			m.put("settleAtDesc", StringUtil.formateAmt(settleAt));
			
			String feeAt = m.get("feeAt");
			m.put("feeAtDesc", StringUtil.formateAmt(feeAt));
			
			String settlePeriod = m.get("settlePeriod");
			m.put("settlePeriodDesc", EnumUtil.translate(SettleEnums.SettlePeriod.class, settlePeriod, false));
			
			String state = m.get("state");
			m.put("stateDesc", EnumUtil.translate(SettleEnums.SettleTaskState.class, state, true));
		}
	};
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		IMerSettleTaskLogService service = this.getDBService(IMerSettleTaskLogService.class);
		Pager<MerSettleTaskLog> p = service.selectByPage(pageNum, pageSize, this.getQryParamMap());
		Pager<Map<String, String>> pagerResult = commonBO.transferPager(p, BMConstants.PAGE_CONF_MERSETTLETASKLOG, ENTITY_TRANSFER);
		List<ColumnInfo> lst = pagerResult.getColumnLst();
		for (ColumnInfo c : lst) {
			if (c.getField().equals("seqId")) {
				c.setCheckbox(true);
			}
		}
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, pagerResult);
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, int seqId) {
		IMerSettleTaskLogService service = this.getDBService(IMerSettleTaskLogService.class);
		MerSettleTaskLog entity = service.selectByPrimaryKey(seqId);
		AssertUtil.objIsNull(entity, "未找到记录:" + seqId);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_MERSETTLETASKLOG));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_MERSETTLETASKLOG, ENTITY_TRANSFER));
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/down.do")
	public String down(Model model, int seqId, HttpServletResponse response) {
		IMerSettleTaskLogService service = this.getDBService(IMerSettleTaskLogService.class);
		MerSettleTaskLog entity = service.selectByPrimaryKey(seqId);
		AssertUtil.objIsNull(entity, "未找到记录:" + seqId);
		AssertUtil.strNotEquals(entity.getState(), 
				SettleEnums.SettleTaskState._2.getCode(), "请选择状态为'已生成清算文件'的记录");
		String filePath = entity.getFilePath();
		this.logText(BmEnums.FuncInfo._8000070000.getDesc(), 
				CommonEnums.OpType.DOWNLOAD_FILE, "下载清算文件:" + filePath);
		commonBO.downSettleFile(filePath, FileUtil.getFileName(filePath), Constant.UTF8, response);
		return null;
	}
}
