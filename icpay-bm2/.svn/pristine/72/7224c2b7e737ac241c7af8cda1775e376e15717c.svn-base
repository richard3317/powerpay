package com.icpay.payment.bm.web.controller.business;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.Logger;
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
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.db.dao.mybatis.model.ChnlInfo;
import com.icpay.payment.db.service.IChnlInfoService;

@Controller
@RequestMapping("/chnl")
public class ChnlController extends BaseController {

	private static final Logger logger = Logger.getLogger(ChnlController.class);
	
	/// Robin Modified 20160113: {{
	//private static final BigDecimal MAX_TRANS_LIMIT = new BigDecimal("9999999999.99");
	//private static final BigDecimal MAX_DAY_TRANS_LIMIT = new BigDecimal("999999999999.99");
	private static final BigDecimal MAX_TRANS_LIMIT = new BigDecimal("9999999999");
	private static final BigDecimal MAX_DAY_TRANS_LIMIT = new BigDecimal("999999999999");
	/// }}
	
	private static final String RESULT_BASE_URI = "chnl";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String state = m.get("state");
			m.put("stateDesc", EnumUtil.translate(CommonEnums.ChnlSt.class, state, true));
			
			/*String feeLevel = m.get("feeLevel");
			m.put("feeLevelDesc", EnumUtil.translate(CommonEnums.ChnlFeeLevel.class, feeLevel, true));
			
			String transLimit = m.get("transLimit");
			m.put("transLimitDesc", String.valueOf(new BigDecimal(transLimit).movePointLeft(2)));
			
			String dayTransLimit = m.get("dayTransLimit");
			m.put("dayTransLimitDesc", String.valueOf(new BigDecimal(dayTransLimit).movePointLeft(2)));*/
		}
	};
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		if (logger.isDebugEnabled()) {
			logger.debug("渠道管理");
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
		if (logger.isDebugEnabled()) {
			logger.debug("qry mchnt started...");
		}
		IChnlInfoService chnlService = this.getDBService(IChnlInfoService.class);
		Pager<ChnlInfo> p = chnlService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_CHNLINFO, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(Model model) {
		return super.toAdd(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addSubmit(ChnlInfo chnlInfo, String[] chnlTxnTypes) {
		AssertUtil.strIsBlank(chnlInfo.getChnlId(), "chnlId is blank.");
		AssertUtil.strIsBlank(chnlInfo.getChnlDesc(), "chnlDesc is blank.");
		
		IChnlInfoService chnlService = this.getDBService(IChnlInfoService.class);
		ChnlInfo dbEntity = chnlService.selectByPrimaryKey(chnlInfo.getChnlId());
		if (dbEntity != null) {
			return commonBO.buildErrorResp("渠道号已存在");
		}
		
		/*String transLimit = chnlInfo.getTransLimit();
		AssertUtil.strIsBlank(transLimit, "transLimit");
		// 判断单笔限额是否超过最大值
		BigDecimal bd = new BigDecimal(transLimit);
		if (bd.compareTo(MAX_TRANS_LIMIT) > 0) {
			throw new BizzException("单笔限额不能超过" + MAX_TRANS_LIMIT.toString() + "元");
		}
		chnlInfo.setTransLimit(bd.movePointRight(2).toString());
		
		// 判断当日限额是否超过最大值
		String dayLimit = chnlInfo.getDayTransLimit();
		bd = new BigDecimal(dayLimit);
		if (bd.compareTo(MAX_DAY_TRANS_LIMIT) > 0) {
			throw new BizzException("当日累计限额不能超过" + MAX_DAY_TRANS_LIMIT.toString() + "元");
		}*/
		String operateConditions =  chnlInfo.getOperateConditions();
		chnlInfo.setOperateConditions(operateConditions.replace("/\\r\\n/g", "<br/>").replace("/\\n/g", "<br/>").replace("/\\s/g", " "));
		chnlInfo.setTransLimit("100");
		chnlInfo.setDayTransLimit("100");//CommonEnums.ChnlFeeLevel._10.getCode()
		chnlInfo.setFeeLevel(CommonEnums.ChnlFeeLevel._10.getCode());
		chnlInfo.setTransType(EnumUtil.buildCtrlRule(TxnEnums.TxnType.class, chnlTxnTypes, Constant.CHNL_DFT_TXN_TYPES));
		chnlInfo.setLastOperId(this.getSessionUsrId());
		chnlInfo.setState(CommonEnums.ChnlSt._1.getCode());
		chnlService.add(chnlInfo);
		this.logObj(BmEnums.FuncInfo._2000010000.getDesc(), CommonEnums.OpType.ADD, chnlInfo);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult delete(String chnlId) {
		IChnlInfoService chnlService = this.getDBService(IChnlInfoService.class);
		ChnlInfo entity = chnlService.selectByPrimaryKey(chnlId);
		AssertUtil.objIsNull(entity, "未找到渠道信息");
		chnlService.delete(chnlId);
		this.logObj(BmEnums.FuncInfo._2000010000.getDesc(), CommonEnums.OpType.DELETE, entity);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, String chnlId) {
		IChnlInfoService chnlService = this.getDBService(IChnlInfoService.class);
		ChnlInfo entity = chnlService.selectByPrimaryKey(chnlId);
		AssertUtil.objIsNull(entity, "未找到渠道信息");
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_CONF_CHNLINFO, ENTITY_TRANSFER));
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(ChnlInfo chnlInfo, String[] chnlTxnTypes) {
		
		/*String transLimit = chnlInfo.getTransLimit();
		AssertUtil.strIsBlank(transLimit, "transLimit");
		// 判断单笔限额是否超过最大值
		BigDecimal bd = new BigDecimal(transLimit);
		if (bd.compareTo(MAX_TRANS_LIMIT) > 0) {
			throw new BizzException("单笔限额不能超过" + MAX_TRANS_LIMIT.toString() + "元");
		}
		chnlInfo.setTransLimit(bd.movePointRight(2).toString());
		
		// 判断当日限额是否超过最大值
		String dayLimit = chnlInfo.getDayTransLimit();
		bd = new BigDecimal(dayLimit);
		if (bd.compareTo(MAX_DAY_TRANS_LIMIT) > 0) {
			throw new BizzException("当日累计限额不能超过" + MAX_DAY_TRANS_LIMIT.toString() + "元");
		}
		chnlInfo.setDayTransLimit(bd.movePointRight(2).toString());
		*/
		String operateConditions =  chnlInfo.getOperateConditions();
		chnlInfo.setOperateConditions(operateConditions.replace("/\\r\\n/g", "<br/>").replace("/\\n/g", "<br/>").replace("/\\s/g", " "));
		chnlInfo.setTransLimit("");
		chnlInfo.setDayTransLimit("");
		chnlInfo.setFeeLevel("");
		chnlInfo.setTransType(EnumUtil.buildCtrlRule(TxnEnums.TxnType.class, chnlTxnTypes, Constant.CHNL_DFT_TXN_TYPES));
		chnlInfo.setLastOperId(this.getSessionUsrId());
		IChnlInfoService chnlService = this.getDBService(IChnlInfoService.class);
		chnlService.update(chnlInfo);
		this.logObj(BmEnums.FuncInfo._2000010000.getDesc(), CommonEnums.OpType.UPDATE, chnlInfo);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, String chnlId) {
		AssertUtil.strIsBlank(chnlId, "chnlId is blank.");
		IChnlInfoService chnlService = this.getDBService(IChnlInfoService.class);
		ChnlInfo chnl = chnlService.selectByPrimaryKey(chnlId);
		AssertUtil.objIsNull(chnl, "未找到渠道信息:" + chnlId);
		model.addAttribute("chnl", chnl);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_CHNLINFO));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(chnl, BMConstants.PAGE_CONF_CHNLINFO, ENTITY_TRANSFER));
		return super.toDetail(model, RESULT_BASE_URI);
	}
}
