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
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.FileUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.AgentAccCheckFile;
import com.icpay.payment.db.service.IAgentAccCheckFileService;

/**
 * 代理商对账文件管理
 * @author lijin
 *
 */
@Controller
@RequestMapping("/agentAccCheckFile")
public class AgentAccCheckFileController extends BaseController {

	private static final String RESULT_BASE_URI = "agentAccCheckFile";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String transAt = m.get("transAt");
			m.put("transAt", StringUtil.formateAmt(transAt));
			
			String settleAt = m.get("settleAt");
			m.put("settleAt", StringUtil.formateAmt(settleAt));
			
			String feeAt = m.get("feeAt");
			m.put("feeAt", StringUtil.formateAmt(feeAt));
		}
	};
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		IAgentAccCheckFileService service = this.getDBService(IAgentAccCheckFileService.class);
		Pager<AgentAccCheckFile> p = service.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_AGENTACCCHECKFILE, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, int seqId) {
		IAgentAccCheckFileService service = this.getDBService(IAgentAccCheckFileService.class);
		AgentAccCheckFile entity = service.selectByPrimaryKey(seqId);
		AssertUtil.objIsNull(entity, "未找到记录:" + seqId);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_AGENTACCCHECKFILE));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_AGENTACCCHECKFILE, ENTITY_TRANSFER));
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/down.do")
	public String down(int seqId, HttpServletResponse response) {
		IAgentAccCheckFileService service = this.getDBService(IAgentAccCheckFileService.class);
		AgentAccCheckFile entity = service.selectByPrimaryKey(seqId);
		AssertUtil.objIsNull(entity, "未找到记录:" + seqId);
		String filePath = entity.getFilePath();
		this.logText(BmEnums.FuncInfo._8000140000.getDesc(), 
				CommonEnums.OpType.DOWNLOAD_FILE, "下载代理商对账文件:" + entity.getFilePath());
		commonBO.downSettleFile(filePath, FileUtil.getFileName(filePath), Constant.UTF8, response);
		return null;
	}
}
