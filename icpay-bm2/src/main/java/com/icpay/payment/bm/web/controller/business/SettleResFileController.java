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
import com.icpay.payment.common.enums.SettleEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.FileUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.SettleResFile;
import com.icpay.payment.db.service.ISettleResFileService;

@Controller
@RequestMapping("/settleResFile")
public class SettleResFileController extends BaseController {

	private static final String RESULT_BASE_URI = "settleResFile";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String transAt = m.get("transAt");
			m.put("transAtDesc", StringUtil.formateAmt(transAt));
			
			String chnlId = m.get("chnlId");
			m.put("chnlIdDesc", EnumUtil.translate(TxnEnums.ChnlId.class, chnlId, true));
			
			String settleAt = m.get("settleAt");
			m.put("settleAtDesc", StringUtil.formateAmt(settleAt));
			
			String feeAt = m.get("feeAt");
			m.put("feeAtDesc", StringUtil.formateAmt(feeAt));
			
			String state = m.get("state");
			m.put("stateDesc", EnumUtil.translate(SettleEnums.SettleResFileState.class, state, true));
			
			String settleBt = m.get("settleBt");
			if ("0".equals(settleBt)) {
				m.put("settleBt", "T+n");
			} else {
				m.put("settleBt", "T+1 - " + settleBt);
			}
		}
	};
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		ISettleResFileService service = this.getDBService(ISettleResFileService.class);
		Pager<SettleResFile> p = service.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_SETTLERESFILE, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, int seqId) {
		ISettleResFileService service = this.getDBService(ISettleResFileService.class);
		SettleResFile entity = service.selectByPrimaryKey(seqId);
		AssertUtil.objIsNull(entity, "???????????????:" + seqId);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_SETTLERESFILE));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_SETTLERESFILE, ENTITY_TRANSFER));
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/confirm.do")
	public @ResponseBody AjaxResult confirm(Model model, int seqId) {
		ISettleResFileService service = this.getDBService(ISettleResFileService.class);
		SettleResFile entity = service.selectByPrimaryKey(seqId);
		AssertUtil.objIsNull(entity, "???????????????:" + seqId);
		AssertUtil.strEquals(SettleEnums.SettleResFileState._1.getCode(), entity.getState(), "???????????????1-?????????");
		entity.setState(SettleEnums.SettleResFileState._1.getCode());
		entity.setLastOperId(this.getSessionUsrId());
		service.update(entity);
		this.logObj(BmEnums.FuncInfo._8000080000.getCode(), CommonEnums.OpType.CONFIRM, "???????????????:" + seqId);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/down.do")
	public String down(int seqId, HttpServletResponse response) {
		ISettleResFileService service = this.getDBService(ISettleResFileService.class);
		SettleResFile entity = service.selectByPrimaryKey(seqId);
		AssertUtil.objIsNull(entity, "???????????????:" + seqId);
		String filePath = entity.getFilePath();
		this.logText(BmEnums.FuncInfo._8000080000.getDesc(), 
				CommonEnums.OpType.DOWNLOAD_FILE, "??????????????????:" + entity.getFilePath());
		commonBO.downSettleFile(filePath, FileUtil.getFileName(filePath), Constant.UTF8, response);
		return null;
	}
	
}
