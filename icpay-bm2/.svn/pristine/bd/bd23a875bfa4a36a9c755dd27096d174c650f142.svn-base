package com.icpay.payment.bm.web.controller.business;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.SettleEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.FileUtil;
import com.icpay.payment.db.dao.mybatis.model.AcctChkResultTotal;
import com.icpay.payment.db.service.IAcctChkResultTotalService;

@Controller
@RequestMapping("/acctChkTotal")
public class AcctChkTotalController extends BaseController {
	
	private static final String RESULT_BASE_URI = "acctChkTotal";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String chnlId = m.get("chnlId");
			m.put("chnlIdDesc", EnumUtil.translate(TxnEnums.ChnlId.class, chnlId, true));
			
			String checkResult = m.get("checkResult");
			m.put("checkResultDesc", EnumUtil.translate(SettleEnums.AcctResultTotalState.class, checkResult, true));
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
		IAcctChkResultTotalService service = this.getDBService(IAcctChkResultTotalService.class);
		Pager<AcctChkResultTotal> p = service.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_ACCTCHKRESULTTOTAL, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/down.do")
	public String down(int seqId, HttpServletResponse response) {
		IAcctChkResultTotalService service = this.getDBService(IAcctChkResultTotalService.class);
		AcctChkResultTotal entity = service.selectByPrimaryKey(seqId);
		AssertUtil.objIsNull(entity, "记录不存在:" + seqId);
		String filePath = entity.getFilePath();
		AssertUtil.strIsBlank(filePath, "退款文件路径为空");
		this.logText(BmEnums.FuncInfo._8000090000.getDesc(), 
				CommonEnums.OpType.DOWNLOAD_FILE, "下载退款文件:" + filePath);
		commonBO.downSettleFile(filePath, FileUtil.getFileName(filePath), Constant.UTF8, response);
		return null;
	}
}
