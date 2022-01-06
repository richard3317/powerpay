package com.icpay.payment.bm.web.controller.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.BankNumCache;
import com.icpay.payment.bm.cache.ChnlMchntInfoCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.OffPayBank;
import com.icpay.payment.db.service.IOffPayBankService;

@Controller
@RequestMapping("/offPayBank")
public class OffPayBankController extends BaseController {

	private static final Logger logger = Logger.getLogger(OffPayBankController.class);
	private static final String RESULT_BASE_URI = "offPayBank";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			
			String chnlId = m.get("chnlId");
			m.put("chnlIdDesc", EnumUtil.translate(TxnEnums.ChnlId.class, chnlId, true));
			
			String chnlMchntCd = m.get("chnlMchntCd");
			Map<String,String> chnlMchnt = ChnlMchntInfoCache.getInstance().get(chnlId, chnlMchntCd);
			if (!Utils.isEmpty(chnlMchnt)) {
				String chnlMchntDesc = chnlMchnt.get("chnlMchntDesc");
				m.put("chnlMchntDesc", chnlMchntDesc);
			}

			String state = m.get("state");
			m.put("stateDesc", EnumUtil.translate(CommonEnums.ChnlSt.class, state, true));
		}
	};
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		if (logger.isDebugEnabled()) {
			logger.debug("电汇收款卡管理");
		}
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/backToManage.do", method = RequestMethod.GET)
	public String backToManage(Model model) {
		return super.toManage(model, true, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		IOffPayBankService service = this.getDBService(IOffPayBankService.class);
		Pager<OffPayBank> p = service.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_OFF_PAY_BANK, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(Model model) {
		this.buildCommonData(model);
		return super.toAdd(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addSubmit(OffPayBank offPayBank) {
		String accNo = offPayBank.getAccNo();
		AssertUtil.strIsBlank(accNo, "银行卡号不能为空");
		String catalog = offPayBank.getCatalog();
		if (StringUtil.isBlank(catalog)) {
			catalog = "DEFAULT";
			offPayBank.setCatalog(catalog);
		}
		IOffPayBankService service = this.getDBService(IOffPayBankService.class);
		OffPayBank dbEntity = service.selectByPrimaryKey(catalog, accNo);
		if (dbEntity != null) {
			return commonBO.buildErrorResp("此银行卡号信息已存在");
		}
		offPayBank.setLastOperId(this.getSessionUsrId());
		service.add(offPayBank);
		this.logObj(BmEnums.FuncInfo._6001030000.getDesc(), CommonEnums.OpType.ADD, offPayBank);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult delete(String catalog, String accNo) {
		if (StringUtil.isBlank(catalog)) {
			catalog = "DEFAULT";
		}
		IOffPayBankService service = this.getDBService(IOffPayBankService.class);
		OffPayBank entity = service.selectByPrimaryKey(catalog, accNo);
		AssertUtil.objIsNull(entity, "未找到银行卡号");
		service.delete(catalog, accNo);
		this.logObj(BmEnums.FuncInfo._6001030000.getDesc(), CommonEnums.OpType.DELETE, entity);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, String catalog, String accNo) {
		IOffPayBankService service = this.getDBService(IOffPayBankService.class);
		AssertUtil.strIsBlank(accNo, "银行卡号不能为空");
		if (StringUtil.isBlank(catalog)) {
			catalog = "DEFAULT";
		}
		OffPayBank dbEntity = service.selectByPrimaryKey(catalog, accNo);
		AssertUtil.objIsNull(dbEntity, "未找到渠道信息");
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(dbEntity, BMConstants.PAGE_OFF_PAY_BANK, ENTITY_TRANSFER));
		this.buildCommonData(model);
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(OffPayBank offPayBank) {
		String accNo = offPayBank.getAccNo();
		AssertUtil.strIsBlank(accNo, "银行卡号不能为空");
		String catalog = offPayBank.getCatalog();
		if (StringUtil.isBlank(catalog)) {
			catalog = "DEFAULT";
			offPayBank.setCatalog(catalog);
		}
		IOffPayBankService service = this.getDBService(IOffPayBankService.class);
		offPayBank.setLastOperId(this.getSessionUsrId());
		service.update(offPayBank);
		this.logObj(BmEnums.FuncInfo._6001030000.getDesc(), CommonEnums.OpType.UPDATE, offPayBank);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/getChnlMchnts.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult getChnlMchnts(String chnlId) {
		List<Map<String,String>> list = new ArrayList<>();
		list = ChnlMchntInfoCache.getInstance().getByChnlId(chnlId);
		AjaxResult resp= commonBO.buildSuccResp("chnlMchntList", list);
		return resp;
	}
	
	private void buildCommonData(Model model) {
		model.addAttribute("bankCodeList", BankNumCache.getBankNumsList());
	}
}
