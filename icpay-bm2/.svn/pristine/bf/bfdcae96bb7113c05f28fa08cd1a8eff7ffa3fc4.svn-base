package com.icpay.payment.bm.web.controller.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.BankNumCache;
import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant.COMMON_STATE;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.BankChnlMap;
import com.icpay.payment.db.dao.mybatis.model.BankChnlMapKey;
import com.icpay.payment.db.dao.mybatis.model.BankNums;
import com.icpay.payment.db.service.IBankChnlMapService;
import com.icpay.payment.db.service.IBankNumsService;

@Controller
@RequestMapping("/chnlBankMapping")
public class ChnlBankMappingController extends BaseController {
	
	private static final String RESULT_BASE_URI = "chnlBankMapping";
	
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String bankNum = m.get("bankNum");
			if (!Utils.isEmpty(bankNum))
				m.put("bankName", BankNumCache.getBankName(bankNum));
			
			String chnlId = m.get("chnlId");
			if (!Utils.isEmpty(chnlId))
				m.put("chnlDesc", EnumUtil.translate(TxnEnums.ChnlId.class, chnlId, true));
			
			String state = m.get("state");
			if (!Utils.isEmpty(state))
				m.put("stateDesc", EnumUtil.translate(TxnEnums.CommonValid.class, state, true));
			
			String intTransCd = m.get("intTransCd");
			if (!Utils.isEmpty(intTransCd))
				m.put("intTransCdDesc", EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, true));
			
			String currCd = m.get("currCd");
			if (!Utils.isEmpty(currCd))
				m.put("currCdDesc", EnumUtil.translate(BmEnums.CurrCdType.class, currCd, true));
		}
	};
	
	private IBankChnlMapService svc = null;
	
	protected IBankChnlMapService getService() {
		if (svc==null)
			svc=this.getDBService(IBankChnlMapService.class);
		return svc;
	}
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	@QryMethod
	public String manage(Model model, String agentCd) {
		this.buildCommonData(model);
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/backToManage.do", method = RequestMethod.GET)
	public String backToManage(Model model) {
		this.checkFuncRight("2000110000");
		this.buildCommonData(model);
		return super.toManage(model, true, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(Model model) {
		String chnlId = this.getQryParamMap().get("chnlId");
		if (!Utils.isEmpty(chnlId))
			model.addAttribute("chnlId", chnlId);		
		this.buildCommonData(model);
		return super.toAdd(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addSubmit(BankChnlMap record, String maxCardAmtDesc) {
		AssertUtil.objIsNull(record, "數據為空.");
		AssertUtil.strIsBlank(record.getIntTransCd(), "交易类型为空");
		AssertUtil.strIsBlank(record.getBankNum(), "银行编号为空");
		AssertUtil.strIsBlank(record.getChnlId(), "渠道编号为空");
		
		BankChnlMap resQry=getService().selectByPrimaryKey(record);
		if (resQry!=null)
			throw new BizzException(String.format("重复记录: %s" , record));
		record.setState(COMMON_STATE.YSE);
		
		if (Utils.isEmpty(record.getChnlBankName()) && !Utils.isEmpty(record.getChnlBankNum()) && ! "*".equals(record.getChnlBankNum())) {
			record.setChnlBankName(BankNumCache.getBankName(record.getBankNum()));
		}
		
		getService().add(record);
		debug("渠道银行编号新增成功: %s", record);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		
		Pager<BankChnlMap> p = getService().selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_BANK_CHNL_MAP, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, BankChnlMap record) {
		this.buildRecordData(model, record);
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, BankChnlMap record) {
		this.buildRecordData(model, record);
		this.buildCommonData(model);
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(BankChnlMap record, String maxCardAmtDesc) {
		AssertUtil.objIsNull(record, "數據為空.");
		AssertUtil.strIsBlank(record.getIntTransCd(), "交易类型为空");
		AssertUtil.strIsBlank(record.getBankNum(), "银行编号为空");
		AssertUtil.strIsBlank(record.getChnlId(), "渠道编号为空");
		
		if (Utils.isEmpty(record.getChnlBankName()) && !Utils.isEmpty(record.getChnlBankNum()) && ! "*".equals(record.getChnlBankNum())) {
			record.setChnlBankName(BankNumCache.getBankName(record.getBankNum()));
		}
		
		getService().update(record);
		
		debug("渠道银行编号修改成功: %s", record);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult delete(BankChnlMap record) {
		AssertUtil.objIsNull(record, "數據為空.");
		AssertUtil.strIsBlank(record.getIntTransCd(), "交易类型为空");
		AssertUtil.strIsBlank(record.getBankNum(), "银行编号为空");
		AssertUtil.strIsBlank(record.getChnlId(), "渠道编号为空");
		
		getService().delete(record);
		
		info("渠道银行编号删除成功: %s", record);
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/batchEdit.do", method = {RequestMethod.POST, RequestMethod.GET})
	@QryMethod	
	public String batchEdit(Model model) {
		Map<String,String> qm= this.getQryParamMap();
//		AssertUtil.objIsNull(qm.get("chnlId"), "查询条件必须包含渠道编号");
//		AssertUtil.objIsNull(qm.get("chnlMchntCd"), "查询条件必须包含渠道商户号");
		//AssertUtil.objIsNull(qm.get("intTransCd"), "查询条件必须包交易编号");

		model.addAttribute("queryParamInps", qryParamMapToHiddenInp());
//		return this.toPage(model, getResultBaseUri(), "batchEdit");
		
		return this.toPage(model, RESULT_BASE_URI, "batchEdit");
	}
	
	protected static final String HiddenFieldFmt="<input type=\"hidden\" name=\"_QRY_%s\" value=\"%s\">";
	protected String qryParamMapToHiddenInp() {
		Map<String, String> qryMap = this.getQryParamMap();
		if (qryMap==null) return "";
		StringBuilder buf= new StringBuilder();
		for (String key: qryMap.keySet()) {
			String val="";
//			try {
//				val = URLEncoder.encode(qryMap.get(key), "utf-8");
//			} catch (UnsupportedEncodingException e) {
//			}
			val = qryMap.get(key);
			buf.append(String.format(HiddenFieldFmt, key, val));
		}
		return buf.toString();
	}
	
	@RequestMapping(value = "/batchEditSubmit.do", method = RequestMethod.POST)
	@QryMethod	
	public @ResponseBody AjaxResult batchEditSubmit(Model model, BankChnlMap bcm) {
		this.checkFuncRight("2000110040");
		return batchEditSubmit(model, bcm, false);
	}
	
	public AjaxResult batchEditSubmit(Model model, BankChnlMap bcm, boolean checkMode) {
		int modified=0;
		BankChnlMap rec = new BankChnlMap();
		if (! Utils.isEmpty(bcm.getState())) {
			rec.setState(bcm.getState());
			modified++;
		}
//		if (! Utils.isEmpty(tr.getChnlId())) {
//			rec.setChnlId(tr.getChnlId());
//			modified++;
//		}
//		if (! Utils.isEmpty(tr.getChnlMchntCd())) {
//			rec.setChnlMchntCd(tr.getChnlMchntCd());
//			modified++;
//		}
		String msg="资料未更新";
		if (modified>0) {
//			rec.setLastOperId(this.getSessionUsrId());
			rec.setRecUpdTs(new Date());
			int cnt=this.getService().batchUpdate(this.getQryParamMap(), rec);
			Map<String ,String > bankChnlMap = new HashMap<String ,String >();
			bankChnlMap = this.getQryParamMap();
			bankChnlMap.put("updateStatus", rec.getState());
//			bankChnlMap.put("updateChnlId", rec.getChnlId());
//			bankChnlMap.put("updateChnlMchntCd", rec.getChnlMchntCd());
			this.logObj("批量修改银行编号状态", CommonEnums.OpType.UPDATE, bankChnlMap);
			
			msg = String.format("更新完成，共更新 %s 笔记录!", cnt);
			
		}
		return commonBO.buildSuccResp(msg);
	}
	
	private void buildCommonData(Model model) {
		model.addAttribute("bankCodeList", BankNumCache.getBankNumsList());
	}
	
	private void buildRecordData(Model model, BankChnlMapKey key) {
		AssertUtil.objIsNull(key, "数据为空");
		
		BankChnlMap resQry = this.getService().selectByPrimaryKey(key);
		AssertUtil.objIsNull(resQry, "查无数据: " + key);
		model.addAttribute("record", resQry);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_BANK_CHNL_MAP));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(resQry, BMConstants.PAGE_CONF_BANK_CHNL_MAP, ENTITY_TRANSFER));
	}
	
	/***
	 * 取得银行
	 * @return
	 */
	@RequestMapping(value = "/getChnlBanks.do", method = RequestMethod.GET)
	public  @ResponseBody AjaxResult getChnlBanks(String currCd) {
		List<BankNums> res = new ArrayList<BankNums>();
		IBankNumsService service = DBHessionServiceClient.getService(IBankNumsService.class);
		res = service.qryBankNumLstByCurrCd(currCd);
		return commonBO.buildSuccResp("bankNums", res);
	}
}
