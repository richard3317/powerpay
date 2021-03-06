package com.icpay.payment.bm.web.controller.business;

import static com.icpay.payment.risk.BmUser.bmUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.icpay.payment.bm.bo.BusCheckBO;
import com.icpay.payment.bm.cache.ChnlMchntInfoCache;
import com.icpay.payment.bm.cache.MchntInfoCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.BusCheckTaskEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.TxnRouting;
import com.icpay.payment.db.dao.mybatis.model.TxnRoutingKey;
import com.icpay.payment.db.dao.mybatis.model.TxnRouting_Mapping;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.ITxnRoutingService;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;

public abstract class RouteBaseController extends BaseController {

	protected abstract String getResultBaseUri();

	@Autowired
	private BusCheckBO busCheckBO;

	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			
			String currCd = m.get("currCd");
			m.put("currCd", EnumUtil.translate(BmEnums.CurrCdType.class, currCd, true));
			
			String chnlId = m.get("chnlId");
			m.put("chnlIdDesc", EnumUtil.translate(TxnEnums.ChnlId.class, chnlId, true));
			
			String mchntCd=m.get("mchntCd");
			Map<String,String> mchnt = MchntInfoCache.getInstance().get(mchntCd);
			if (!Utils.isEmpty(mchnt)) {
				String mchntDesc = mchnt.get("mchntCnNm");
				m.put("mchntDesc", mchntDesc);
			}
			
			String chnlMchntCd=m.get("chnlMchntCd");
			Map<String,String> chnlMchnt = ChnlMchntInfoCache.getInstance().get(chnlId, chnlMchntCd);
			if (!Utils.isEmpty(chnlMchnt)) {
				String chnlMchntDesc = chnlMchnt.get("chnlMchntDesc");
				m.put("chnlMchntDesc", chnlMchntDesc);
			}

			String intTransCd = m.get("intTransCd");
			m.put("intTransCdDesc", EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, true));

			String status = m.get("status");
			if("0".equals(status)){
				m.put("statusDesc",status+"-??????" );
			}else if("1".equals(status)){
				m.put("statusDesc",status+"-??????" );
			}
			
			String txAmtMin = m.get("txAmtMin");
			m.put("txAmtMinDesc", StringUtil.formateAmt(txAmtMin));
			
			String txAmtMax = m.get("txAmtMax");
			m.put("txAmtMaxDesc", StringUtil.formateAmt(txAmtMax));
			
			String txDayMax = m.get("txDayMax");
			m.put("txDayMaxDesc", StringUtil.formateAmt(txDayMax));
			
			String txCardDayMax = m.get("txCardDayMax");
			m.put("txCardDayMaxDesc", StringUtil.formateAmt(txCardDayMax));
			
			//??????
			String siteId = selectSiteIdByMchntCd(mchntCd);
			m.put("siteId", EnumUtil.translate(CommonEnums.Site.class, siteId, true));
		}
	};

	public String manage(Model model) {
		ChnlMchntInfoCache.getInstance().setFilterEffect(false);
		ChnlMchntInfoCache.getInstance().init();
		return super.toManage(model, false, getResultBaseUri());
	}

	public String backToManage(Model model) {
		ChnlMchntInfoCache.getInstance().setFilterEffect(false);
		ChnlMchntInfoCache.getInstance().init();
		return super.toManage(model, true, getResultBaseUri());
	}

	public AjaxResult qry(int pageNum, int pageSize, int showMore) {
		Map<String,String> qryParams = new HashMap<String,String>();
		qryParams = this.getQryParamMap();

		if(showMore==1)
		{
			if(qryParams.containsKey("chnlMchntCd")) {
				qryParams.put("chnlMchntCdAssoc", qryParams.get("chnlMchntCd"));
				qryParams.put("chnlMchntCd", "");
			}
		}
		Pager<TxnRouting_Mapping> p = this.routeSvc().selectByPage(pageNum, pageSize, qryParams);
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT,
				commonBO.transferPager(p, BMConstants.PAGE_TXN_ROUTING_MAPPING, ENTITY_TRANSFER));
	}

	public String add(Model model) {
		ChnlMchntInfoCache.getInstance().setFilterEffect(true);
		ChnlMchntInfoCache.getInstance().init();
		return super.toAdd(model, getResultBaseUri());
	}

	public AjaxResult addSubmit(TxnRouting tr, boolean checkMode) {
		AssertUtil.objIsNull(tr, "txnRouting is null.");
		AssertUtil.objIsNull(tr.getMchntCd(), "????????????????????????!");
		AssertUtil.objIsNull(tr.getChnlId(), "?????????????????????!");
		AssertUtil.objIsNull(tr.getChnlMchntCd(), "??????????????????????????????!");
		AssertUtil.objIsNull(tr.getIntTransCd(), "???????????????????????????!");
		AssertUtil.argIsBlank(tr.getCurrCd(), "?????????????????????!");
		//
		IMchntInfoService mchntService = this.getDBService(IMchntInfoService.class);
		MchntInfo mchnt = mchntService.selectByPrimaryKey(tr.getMchntCd());
		AssertUtil.objIsNull(mchnt, "???????????????:" + tr.getMchntCd());
		//
		tr.setLastOperId(this.getSessionUsrId());
		tr.setRecCrtTs(new Date());
		tr.setRecUpdTs(new Date());
		tr.setCurrCd(tr.getCurrCd());
		
		TxnRouting dbEntity = routeSvc().selectByPrimaryKey(tr);
		AssertUtil.objIsNotNull(dbEntity, "????????????????????????????????????");
		
		String msg = "";
		if (checkMode) {
			busCheckBO.newTask(BusCheckTaskEnums.TaskTp._03, this.getSessionUsrId(), 
					CommonEnums.OpType.ADD, "??????????????????", tr);
			msg = "???????????????????????????????????????";
			this.logObj(BmEnums.FuncInfo._2000020000.getDesc(), CommonEnums.OpType.ADD_REQUEST, tr);
		}
		else {
			this.routeSvc().add(tr);
			
			RiskEvent.bm()
				.who(bmUser(commonBO.getSessionUser().getUsrId()))
				.event(RISK.Event.Route_Add)
				.result(RISK.Result.Ok)
				.target(tr.getMchntCd()+"|"+tr.getChnlId()+"|"+tr.getChnlMchntCd()+"|"+ tr.getIntTransCd()+"|"+EnumUtil.translate(BmEnums.CurrCdType.class, tr.getCurrCd(), false)+"|"+ ("1".equals(tr.getStatus()) ? "??????" : "??????"))
				.message(String.format("????????? %s, ?????????????????????????????????????????? %s, ????????? %s, ?????????????????? %s, ????????? %s, ????????? %s", commonBO.getSessionUser().getUsrId(), tr.getMchntCd(), tr.getChnlId(), tr.getChnlMchntCd(), EnumUtil.translate(BmEnums.CurrCdType.class, tr.getCurrCd(), false), ("1".equals(tr.getStatus()) ? "??????" : "??????")))
				.params("mchntCd", tr.getMchntCd())
				.params("chnlId", tr.getChnlId())
				.params("chnlMchntCd", tr.getChnlMchntCd())
				.params("intTransCd", tr.getIntTransCd())
				.params("currCd", tr.getCurrCd())
				.params("status", tr.getStatus())
				.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
				.submit();
			
			msg = "??????????????????????????????";
			this.logObj(BmEnums.FuncInfo._2000020000.getDesc(), CommonEnums.OpType.ADD, tr);
		}
		return commonBO.buildSuccResp(msg);
	}

	public String edit(Model model, String intTransCd, String mchntCd, String chnlId, String chnlMchntCd, String currCd) {
		TxnRoutingKey key = new TxnRoutingKey();
		key.setChnlId(chnlId);
		key.setChnlMchntCd(chnlMchntCd);
		key.setIntTransCd(intTransCd);
		key.setMchntCd(mchntCd);
		key.setCurrCd(currCd.substring(0,3));
		TxnRouting entity = routeSvc().selectByPrimaryKey(key);
		AssertUtil.objIsNull(entity, "?????????????????????");
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(entity, BMConstants.PAGE_TXN_ROUTING, ENTITY_TRANSFER));

		return super.toEdit(model, getResultBaseUri());
	}

	public AjaxResult editSubmit(Model model, TxnRouting tr, boolean checkMode) {
		AssertUtil.objIsNull(tr, "routInfo is null.");
		AssertUtil.objIsNull(tr.getMchntCd(), "????????????????????????!");
		AssertUtil.objIsNull(tr.getChnlId(), "?????????????????????!");
		AssertUtil.objIsNull(tr.getChnlMchntCd(), "??????????????????????????????!");
		AssertUtil.objIsNull(tr.getIntTransCd(), "???????????????????????????!");
		AssertUtil.objIsNull(tr.getCurrCd(), "?????????????????????!");
		tr.setLastOperId(this.getSessionUsrId());
		tr.setRecUpdTs(new Date());
		tr.setCurrCd(tr.getCurrCd().substring(0,3));
		TxnRouting dbEntity = routeSvc().selectByPrimaryKey(tr);
		AssertUtil.objIsNull(dbEntity, "?????????????????????");
		
		String msg="";
		if (checkMode) {
			busCheckBO.newTask(BusCheckTaskEnums.TaskTp._03, this.getSessionUsrId(), 
					CommonEnums.OpType.UPDATE, "??????????????????", tr);
			this.logObj(BmEnums.FuncInfo._2000020000.getDesc(), CommonEnums.OpType.UPDATE_REQUEST, tr);
			msg = "???????????????????????????????????????";
		}
		else {
			this.routeSvc().update(tr);
			
			RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Route_Modify)
			.result(RISK.Result.Ok)
			.target(tr.getMchntCd()+"|"+tr.getChnlId()+"|"+tr.getChnlMchntCd()+"|"+ tr.getIntTransCd()+"|"+EnumUtil.translate(BmEnums.CurrCdType.class, tr.getCurrCd(), false)+"|"+ tr.getPriority() + "|" + ("1".equals(tr.getStatus()) ? "??????" : "??????"))
			.message(String.format("????????? %s, ?????????????????????????????????????????? %s, ????????? %s, ?????????????????? %s, ????????? %s, ???????????? %s, ????????? %s", commonBO.getSessionUser().getUsrId(), tr.getMchntCd(), tr.getChnlId(), tr.getChnlMchntCd(), EnumUtil.translate(BmEnums.CurrCdType.class, tr.getCurrCd(), false), tr.getPriority(), ("1".equals(tr.getStatus()) ? "??????" : "??????")))
			.params("mchntCd", tr.getMchntCd())
			.params("chnlId", tr.getChnlId())
			.params("chnlMchntCd", tr.getChnlMchntCd())
			.params("intTransCd", tr.getIntTransCd())
			.params("currCd", tr.getCurrCd())
			.params("priority", tr.getPriority())
			.params("status", tr.getStatus())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
			
			this.logObj(BmEnums.FuncInfo._2000020000.getDesc(), CommonEnums.OpType.UPDATE, tr);
			msg = "??????????????????????????????";
		}
		return commonBO.buildSuccResp(msg);
	}
	
	public String batchAdd(Model model) {
		ChnlMchntInfoCache.getInstance().setFilterEffect(true);
		ChnlMchntInfoCache.getInstance().init();
		List<TxnRouting> list=this.routeSvc().select(this.getQryParamMap());
		this.debug("list: "+list);
		StringBuilder buf= new StringBuilder();
		if (list!=null) {
			for (TxnRouting tr: list) {
				int i = buf.indexOf(tr.getMchntCd());
				if (i<0)
					buf.append(tr.getMchntCd()).append("\r\n");
			}
		}
		model.addAttribute("mchntCdsList", buf.toString());
		this.debug("mchntCdsList: "+buf.toString());
		return this.toPage(model, getResultBaseUri(), "batchAdd");
	}
	
	public AjaxResult batchAddSubmit(Model model, TxnRouting tr, String mchntCdsList, boolean checkMode) {
		AssertUtil.objIsNull(tr, "txnRouting is null.");
		AssertUtil.objIsNull(mchntCdsList, "????????????????????????!");
		AssertUtil.objIsNull(tr.getChnlId(), "?????????????????????!");
		AssertUtil.objIsNull(tr.getChnlMchntCd(), "??????????????????????????????!");
		AssertUtil.objIsNull(tr.getIntTransCd(), "???????????????????????????!");
		AssertUtil.argIsBlank(tr.getCurrCd(), "?????????????????????!");
		tr.setLastOperId(this.getSessionUsrId());
		tr.setRecCrtTs(new Date());
		tr.setRecUpdTs(new Date());
		tr.setCurrCd(tr.getCurrCd());
		String msg = "";
		int cnt=this.routeSvc().batchAdd(mchntCdsList, tr);
		msg = String.format("?????????????????????????????? %s ?????????!", cnt);
		
		List<String> mchntCdList = Utils.strSplitToList(mchntCdsList, "\r\n", true);	
		List<TxnRouting> routList = new ArrayList<TxnRouting>();
		for(String mchntCd: mchntCdList) {
			tr.setMchntCd(mchntCd);
			routList.add(tr);
		}
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Route_Batch_Add)
			.result(RISK.Result.Ok)
			.target(cnt+"")
			.message(String.format("????????? %s, ?????????????????????????????? %s ?????????!", commonBO.getSessionUser().getUsrId(), cnt+""))
			.params("mchntCdsList", mchntCdsList)
			.params("chnlId", tr.getChnlId())
			.params("chnlMchntCd", tr.getChnlMchntCd())
			.params("intTransCd", tr.getIntTransCd())
			.params("currCd", tr.getCurrCd())
			.params("priority", tr.getPriority())
			.params("status", tr.getStatus())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		this.logObj("??????????????????", CommonEnums.OpType.ADD, routList);
		return commonBO.buildSuccResp(msg);
	}
	
	public String batchEdit(Model model) {
		Map<String,String> qm= this.getQryParamMap();
		AssertUtil.objIsNull(qm.get("chnlId"), "????????????????????????????????????");
		AssertUtil.objIsNull(qm.get("chnlMchntCd"), "???????????????????????????????????????");
		//AssertUtil.objIsNull(qm.get("intTransCd"), "?????????????????????????????????");

		model.addAttribute("queryParamInps", qryParamMapToHiddenInp());
		return this.toPage(model, getResultBaseUri(), "batchEdit");
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

	public AjaxResult batchEditSubmit(Model model, TxnRouting tr, boolean checkMode) {
		int modified=0;
		TxnRouting rec = new TxnRouting();
		if (tr.getPriority()!=null) {
			rec.setPriority(tr.getPriority());
			modified++;
		}
		if (! Utils.isEmpty(tr.getStatus())) {
			rec.setStatus(tr.getStatus());
			modified++;
		}
		if (! Utils.isEmpty(tr.getChnlId())) {
			rec.setChnlId(tr.getChnlId());
			modified++;
		}
		if (! Utils.isEmpty(tr.getChnlMchntCd())) {
			rec.setChnlMchntCd(tr.getChnlMchntCd());
			modified++;
		}
		String msg="???????????????";
		if (modified>0) {
			rec.setLastOperId(this.getSessionUsrId());
			rec.setRecUpdTs(new Date());
			int cnt=this.routeSvc().batchUpdate(this.getQryParamMap(), rec);
			Map<String ,String > routMap = new HashMap<String ,String >();
			routMap = this.getQryParamMap();
			routMap.put("updateStatus", rec.getStatus());
			routMap.put("updateChnlId", rec.getChnlId());
			routMap.put("updateChnlMchntCd", rec.getChnlMchntCd());
			if (!Utils.isEmpty(rec.getPriority()))
				routMap.put("updatePriority", rec.getPriority().toString());
			this.logObj("??????????????????", CommonEnums.OpType.UPDATE, routMap);
			
			msg = String.format("???????????????????????? %s ?????????!", cnt);
			
			RiskEvent.bm()
				.who(bmUser(commonBO.getSessionUser().getUsrId()))
				.event(RISK.Event.Route_Batch_Modify)
				.result(RISK.Result.Ok)
				.target(cnt + "")
				.message(String.format("????????? %s, ?????????????????????????????? %s ?????????!", commonBO.getSessionUser().getUsrId(), cnt+""))
				.params(routMap)
				.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
				.submit();
			
		}
		return commonBO.buildSuccResp(msg);
	}

	public AjaxResult delete(String mchntCd, String intTransCd, String chnlId, String chnlMchntCd, String currCd, boolean checkMode) {
		AssertUtil.objIsNull(mchntCd, "????????????????????????!");
		AssertUtil.objIsNull(chnlId, "?????????????????????!");
		AssertUtil.objIsNull(chnlMchntCd, "??????????????????????????????!");
		AssertUtil.objIsNull(intTransCd, "???????????????????????????!");
		AssertUtil.objIsNull(currCd, "?????????????????????!");
		String[] currCdSplit = currCd.split("-");
		currCd = currCdSplit[0];
		
		TxnRoutingKey key = new TxnRoutingKey();
		key.setChnlId(chnlId);
		key.setChnlMchntCd(chnlMchntCd);
		key.setIntTransCd(intTransCd);
		key.setMchntCd(mchntCd);
		key.setCurrCd(currCd);
		TxnRouting dbEntity = routeSvc().selectByPrimaryKey(key);
		AssertUtil.objIsNull(dbEntity, "?????????????????????");
		
		
		String msg="";
		if (checkMode) {
			busCheckBO.newTask(BusCheckTaskEnums.TaskTp._03, this.getSessionUsrId(), 
					CommonEnums.OpType.DELETE, "??????????????????", dbEntity);
			this.logObj(BmEnums.FuncInfo._2000020000.getDesc(), CommonEnums.OpType.DELETE_REQUEST, dbEntity);
			msg = "???????????????????????????????????????";
		}
		else {
			this.routeSvc().delete(dbEntity);
			
			RiskEvent.bm()
				.who(bmUser(commonBO.getSessionUser().getUsrId()))
				.event(RISK.Event.Route_Del)
				.result(RISK.Result.Ok)
				.target(mchntCd+"|"+chnlId+"|"+chnlMchntCd+"|"+ intTransCd+"|"+EnumUtil.translate(BmEnums.CurrCdType.class, currCd, false))
				.message(String.format("????????? %s, ?????????????????????????????????????????? %s, ????????? %s, ?????????????????? %s, ????????? %s", commonBO.getSessionUser().getUsrId(), mchntCd, chnlId, chnlMchntCd, EnumUtil.translate(BmEnums.CurrCdType.class, currCd, false)))
				.params("mchntCd", mchntCd)
				.params("chnlId", chnlId)
				.params("chnlMchntCd", chnlMchntCd)
				.params("intTransCd", intTransCd)
				.params("currCd", currCd)
				.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
				.submit();
			
			this.logObj(BmEnums.FuncInfo._2000020000.getDesc(), CommonEnums.OpType.DELETE, dbEntity);
			msg = "??????????????????????????????";
		}		
		
		return commonBO.buildSuccResp(msg);
	}
	
	public AjaxResult batchDelete(Model model, boolean checkMode) {
		String msg="???????????????";
		int cnt=this.routeSvc().batchDelete(this.getQryParamMap());
		if (cnt>0) {
			msg = String.format("???????????????????????? %s????????????!", cnt);
			
			RiskEvent.bm()
				.who(bmUser(commonBO.getSessionUser().getUsrId()))
				.event(RISK.Event.Route_Batch_Del)
				.result(RISK.Result.Ok)
				.target(cnt + "")
				.message(String.format("????????? %s, ???????????????????????????????????? %s????????????!???", commonBO.getSessionUser().getUsrId(), cnt+""))
				.params(this.getQryParamMap())
				.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
				.submit();
		}
		
		this.logObj("??????????????????", CommonEnums.OpType.DELETE, this.getQryParamMap());
		return commonBO.buildSuccResp(msg);
	}
	
	
	ITxnRoutingService service = null;
	protected ITxnRoutingService routeSvc() {
		if (service==null)
			service = DBHessionServiceClient.getService(ITxnRoutingService.class);
		return service;
	}
	
	public AjaxResult getChnlMchnts(String chnlId) {
		List<Map<String,String>> list = new ArrayList<>();
		list = ChnlMchntInfoCache.getInstance().getByChnlId(chnlId);
		AjaxResult resp= commonBO.buildSuccResp("chnlMchntList", list);
		return resp;
	}
	
	protected AjaxResult export(Model model, String pageConfTp, Map<String, String> qryParamMap, String filename, HttpServletResponse response) {
		
		this.debug("[export] ????????????, this.getQryParamMap(): %s", qryParamMap);
		ITxnRoutingService svc = this.getDBService(ITxnRoutingService.class);
		List<TxnRouting> list = svc.select(qryParamMap);
		Pager<TxnRouting_Mapping> p = svc.selectByPage(1, list.size(), qryParamMap);
		Pager<Map<String, String>> pager = commonBO.transferList(p.getResultList(), pageConfTp, ENTITY_TRANSFER);
		try {
			commonBO.exportToExcel(pager,null , filename, Constant.UTF8, response);
			return null;
		} catch (Exception e) {
			return commonBO.buildErrorResp(e.getMessage());
		}
	}
	
	private static String selectSiteIdByMchntCd(String mchntCd) {
		String siteId = "";
		IMchntInfoService service = DBHessionServiceClient.getService(IMchntInfoService.class);
		MchntInfo mchntInfo = service.selectByPrimaryKey(mchntCd);
		if (!Utils.isEmpty(mchntInfo)) {
			siteId = mchntInfo.getSiteId();
		}
		return siteId;
	}

}
