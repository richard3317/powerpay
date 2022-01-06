package com.icpay.payment.bm.web.controller.business;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.icpay.payment.bm.bo.TerminalBO;
import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.cache.ValidationConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.UUIDGenerator;
import com.icpay.payment.common.validate.ValidationHelper;
import com.icpay.payment.db.dao.mybatis.model.TermInfo;
import com.icpay.payment.db.service.ITermInfoService;

@Controller
@RequestMapping("/terminal")
public class TerminalController extends BaseController {

	private static final String RESULT_BASE_URI = "terminal";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String state = m.get("state");
			m.put("stateDesc", EnumUtil.translate(CommonEnums.RecSt.class, state, true));
		}
	};
	
	@Autowired
	private TerminalBO terminalBO;

	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/import.do", method = RequestMethod.GET)
	public String batchImport(Model model) {
		return RESULT_BASE_URI + "/import";
	}
	
	@RequestMapping(value = "/import/submit.do", method = RequestMethod.POST)
	public String batchImportSubmit(@RequestParam(value = "importFile", required = false) MultipartFile file, Model model) {
		this.logText(BmEnums.FuncInfo._1100010000.getDesc(), CommonEnums.OpType.UPLOAD_FILE, 
				"批量导入终端信息:" + file.getOriginalFilename());
		String batNo = UUIDGenerator.getUUID();
		Map<String, Object> resultMap = terminalBO.batchImport(file, batNo);
		commonBO.setSessionAttr(BMConstants.SESSION_KEY_TERM_IMPORT_RSLT_FILENM, 
				resultMap.get(BMConstants.SESSION_KEY_TERM_IMPORT_RSLT_FILENM));
		model.addAttribute("batNo", batNo);
		model.addAttribute("total", String.valueOf(resultMap.get("total")));
		model.addAttribute("succ", String.valueOf(resultMap.get("succ")));
		model.addAttribute("fail", String.valueOf(resultMap.get("fail")));
		return RESULT_BASE_URI + "/import_result";
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		ITermInfoService termService = this.getDBService(ITermInfoService.class);
		Pager<TermInfo> p = termService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_TERMINAL, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/downResult.do")
	public String downResult(HttpServletRequest request, HttpServletResponse response) {
		String resultFileNm = commonBO.getSessionAttr(BMConstants.SESSION_KEY_TERM_IMPORT_RSLT_FILENM);
		this.logText(BmEnums.FuncInfo._1100010000.getDesc(), CommonEnums.OpType.DOWNLOAD_FILE, 
				"下载终端导入结果文件:" + resultFileNm);
		commonBO.downFile(BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TERMINAL_IMPORT_FILE_PATH), resultFileNm, Constant.UTF8, response);
		return null;
	}
	
	@RequestMapping(value = "/export.do", method = RequestMethod.POST)
	@QryMethod
	public String export(Model model, String exportKey1, String exportKey2) {
		ValidationHelper.validate(exportKey1, 
				ValidationConfCache.getConfig(BMConstants.VALID_CONF_KEY_TERMINFO_EXPORT_KEY));
		ValidationHelper.validate(exportKey2, 
				ValidationConfCache.getConfig(BMConstants.VALID_CONF_KEY_TERMINFO_EXPORT_KEY));
		this.logText(BmEnums.FuncInfo._1100010000.getDesc(), 
				CommonEnums.OpType.BATCH_EXPORT, "导出终端信息，导出条件:" + this.getQryParamMap());
		int exportNum = terminalBO.export(exportKey1 + exportKey2, this.getQryParamMap());
		model.addAttribute("total", exportNum);
		return RESULT_BASE_URI + "/export_result";
	}
	
	@RequestMapping(value = "/downExport.do")
	public String downExport(HttpServletRequest request, HttpServletResponse response) {
		String resultFileNm = commonBO.getSessionAttr(BMConstants.SESSION_KEY_TERM_EXPORT_RSLT_FILENM);
		this.logText(BmEnums.FuncInfo._1100010000.getDesc(), CommonEnums.OpType.DOWNLOAD_FILE, 
				"下载导出结果文件:" + resultFileNm);
		commonBO.downFile(BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TERMINAL_EXPORT_FILE_PATH), resultFileNm, Constant.UTF8, response);
		return null;
	}
	
	@RequestMapping(value = "/downSample.do")
	public String downSample(HttpServletRequest request, HttpServletResponse response) {
		String sampleFileNm = BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TERMINAL_IMPORT_SAMPLE_FILE_NM);
		this.logText(BmEnums.FuncInfo._1100010000.getDesc(), CommonEnums.OpType.DOWNLOAD_FILE, 
				"下载终端信息导入示例文件:" + sampleFileNm);
		commonBO.downFile(BMConfigCache.getConfig(BMConstants.CONFIG_KEY_TERMINAL_IMPORT_FILE_PATH), sampleFileNm, Constant.UTF8, response);
		return null;
	}
}
