package com.icpay.payment.bm.web.controller.business;

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
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.ProfitEnums;
import com.icpay.payment.common.enums.SettleEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.FileUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.ProfitResFile;
import com.icpay.payment.db.service.IProfitResFileService;

/**
 * 代理商分润文件管理
 * @author lijin
 *
 */
@Controller
@RequestMapping("/profitResFile")
public class ProfitResFileController extends BaseController {

	private static final String RESULT_BASE_URI = "profitResFile";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String transAt = m.get("transAt");
			m.put("transAt", StringUtil.formateAmt(transAt));
			
			String profitAt = m.get("profitAt");
			m.put("profitAt", StringUtil.formateAmt(profitAt));
			
			String state = m.get("state");
			m.put("stateDesc", EnumUtil.translate(ProfitEnums.ProfitResState.class, state, true));
		}
	};
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		IProfitResFileService service = this.getDBService(IProfitResFileService.class);
		Pager<ProfitResFile> p = service.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_PROFITRESFILE, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, int seqId) {
		IProfitResFileService service = this.getDBService(IProfitResFileService.class);
		ProfitResFile entity = service.selectByPrimaryKey(seqId);
		AssertUtil.objIsNull(entity, "未找到记录:" + seqId);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_PROFITRESFILE));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_PROFITRESFILE, ENTITY_TRANSFER));
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/down.do")
	public String downRpt(int seqId, HttpServletResponse response) {
		IProfitResFileService service = this.getDBService(IProfitResFileService.class);
		ProfitResFile entity = service.selectByPrimaryKey(seqId);
		AssertUtil.objIsNull(entity, "未找到记录:" + seqId);
		String filePath = entity.getFilePath();
		this.logText(BmEnums.FuncInfo._8000130000.getDesc(), 
				CommonEnums.OpType.DOWNLOAD_FILE, "下载代理商划款文件:" + entity.getFilePath());
		commonBO.downSettleFile(filePath, FileUtil.getFileName(filePath), Constant.UTF8, response);
		return null;
	}
	
	@RequestMapping(value = "/confirm.do")
	public @ResponseBody AjaxResult confirm(Model model, int seqId) {
		IProfitResFileService service = this.getDBService(IProfitResFileService.class);
		ProfitResFile entity = service.selectByPrimaryKey(seqId);
		AssertUtil.objIsNull(entity, "未找到记录:" + seqId);
		AssertUtil.strEquals(SettleEnums.SettleResFileState._1.getCode(), entity.getState(), "状态已经是1-已划款");
		entity.setState(SettleEnums.SettleResFileState._1.getCode());
		entity.setLastOperId(this.getSessionUsrId());
		service.update(entity);
		this.logObj(BmEnums.FuncInfo._8000130000.getCode(), CommonEnums.OpType.CONFIRM, "确认已划款:" + seqId);
		return commonBO.buildSuccResp();
	}
}
