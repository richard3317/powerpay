package com.icpay.payment.bm.web.controller.business;

import static com.icpay.payment.risk.BmUser.bmUser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icpay.payment.bm.cache.CashPoolInfoCache;
import com.icpay.payment.bm.cache.ChnlMchntInfoCache;
import com.icpay.payment.bm.cache.ParamCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Names.INTER_MSG;
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
import com.icpay.payment.db.dao.mybatis.model.CashPoolInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfoKey;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfoView;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountInfoKey;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoSub;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoSubKey;
import com.icpay.payment.db.service.ICashPoolInfoService;
import com.icpay.payment.db.service.IChnlCashPoolInfoService;
import com.icpay.payment.db.service.IChnlMchntAccountInfoService;
import com.icpay.payment.db.service.IChnlMchntInfoSubService;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;

@Controller
@RequestMapping("/chnlCashPool")
public class ChnlCashPoolController extends BaseController {

	private static final Logger logger = Logger.getLogger(ChnlController.class);
	
	private static final String RESULT_BASE_URI = "chnlCashPool";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String currCd = m.get("currCd");
			m.put("currCd", EnumUtil.translate(BmEnums.CurrCdType.class, currCd, true));
			
			String state = m.get("state");
			m.put("stateDesc", EnumUtil.translate(CommonEnums.RecSt.class, state, true));
			
			String chnlId = m.get("chnlId");
			m.put("chnlIdDesc", EnumUtil.translate(TxnEnums.ChnlId.class, chnlId, true));
			
			String chnlMchntCd=m.get("chnlMchntCd");
			Map<String,String> chnlMchnt = ChnlMchntInfoCache.getInstance().get(chnlId, chnlMchntCd);
			if (!Utils.isEmpty(chnlMchnt)) {
				String chnlMchntDesc = chnlMchnt.get("chnlMchntDesc");
				m.put("chnlMchntDesc", chnlMchntDesc);
			}
			
			String poolId = m.get("poolId");
			Map<String,String> pool = CashPoolInfoCache.getInstance().get(poolId);
			if (!Utils.isEmpty(pool)) {
				String poolDesc = pool.get("poolDesc");
				m.put("poolDesc", poolDesc);
			}
			
			String rowStyler = ParamCache.STYLE_DEFAULT; 
			
			m.put("wdStateDesc","默认");
			
			if("1".endsWith(m.get("wdState"))) {
				m.put("wdStateDesc","优先");
				if("1".endsWith(state))
					rowStyler=ParamCache.STYLE_CASH_POOL;
			}
			
			if (!Utils.isEmpty(rowStyler))
				m.put("rowStyler", rowStyler);
			
			String availableBalance = m.get("availableBalance");
			m.put("availableBalanceDesc", StringUtil.formateAmt(availableBalance));
			
			String txAmtMax = m.get("txAmtMax");
			m.put("txAmtMaxDesc", StringUtil.formateAmt(txAmtMax));
			String txAmtMin = m.get("txAmtMin");
			m.put("txAmtMinDesc", StringUtil.formateAmt(txAmtMin));
			
			String txDayMax = m.get("txDayMax");
			m.put("txDayMaxDesc", StringUtil.formateAmt(txDayMax));
			
			//20201104新增【实际可取现金额】字段
			BigDecimal obligatedBalance = new BigDecimal(m.get("obligatedBalance")!=null ?m.get("obligatedBalance"):"0");
			BigDecimal availableBalanceBd = new BigDecimal(m.get("availableBalance")!=null ?m.get("availableBalance"):"0");
			BigDecimal realAvailableBalance  = availableBalanceBd.subtract(obligatedBalance);
			if(realAvailableBalance.compareTo(BigDecimal.ZERO) > 0) {
				m.put("realAvailableBalanceDesc", realAvailableBalance.movePointLeft(2).toString());
			} else {
				m.put("realAvailableBalanceDesc", "0.00");
			}
			
//			String chnlMchntCd = m.get("chnlMchntCd");
//			Map<String,String> chnlMchnt = ChnlMchntInfoCache.getInstance().get(chnlMchntCd);
//			if (!Utils.isEmpty(chnlMchnt)) {
//				String chnlMchntDesc = chnlMchnt.get("mchntCnNm");
//				m.put("chnlMchntDesc", chnlMchntDesc);
//			}
//			
//			String poolId = m.get("poolId");
//			Map<String,String> pool = CashPoolInfoCache.getInstance().get(poolId);
//			if (!Utils.isEmpty(pool)) {
//				String poolDesc = pool.get("poolDesc");
//				m.put("poolDesc", poolDesc);
//			}
			
		}
	};
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		if (logger.isDebugEnabled()) {
			logger.debug("资金池管理");
		}
		this.getCashPools(model);
		this.getChnlMchnts(model);
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/backToManage.do", method = RequestMethod.GET)
	public String backToManage(Model model) {
		this.getCashPools(model);
		this.getChnlMchnts(model);
		return super.toManage(model, true, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		if (logger.isDebugEnabled()) {
			logger.debug("qry 资金池配置管理 started...");
		}
		IChnlCashPoolInfoService chnlService = this.getDBService(IChnlCashPoolInfoService.class);
		Pager<ChnlCashPoolInfoView> p = chnlService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_CHNL_CASH_POOL_INFO, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(Model model) {
		this.getCashPoolsByValid(model);
		this.getChnlMchnts(model);

		return super.toAdd(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addSubmit(ChnlCashPoolInfo info) {
		AssertUtil.strIsBlank(info.getPoolId(), "poolId is blank.");
		AssertUtil.strIsBlank(info.getChnlMchntCd(), "chnlMchntCd is blank.");
		
		ChnlCashPoolInfoKey key = new ChnlCashPoolInfoKey();
		key.setChnlMchntCd(info.getChnlMchntCd());
		key.setPoolId(info.getPoolId());
		key.setChnlId(info.getChnlId());

		IChnlCashPoolInfoService service = this.getDBService(IChnlCashPoolInfoService.class);
		ChnlCashPoolInfo dbEntity = service.selectByPrimaryKey(key);
		if (dbEntity != null) {
			return commonBO.buildErrorResp("商户已配置该资金池");
		}
//		if("0".endsWith(info.getWdState())) {
//			info.setPriority(100);  //默认优先级有100
//		}else {
//			info.setPriority(10);  //优先
//		}
		info.setWdState("0");  //原出款狀態先預設默認
		info.setPriority(info.getPriority());  //优先級
		info.setWeight(100); //权重默认100
		info.setLastOperId(this.getSessionUsrId());
		info.setState(CommonEnums.ChnlSt._1.getCode());
		this.info(String.format("新增资金池配置信息，操作员： %s 渠道商户号：%s 资金池id：%s 渠道编号：%s", this.getSessionUsrId() ,info.getChnlMchntCd() ,info.getPoolId() , info.getChnlId()));
		service.add(info);
		this.logObj(BmEnums.FuncInfo._2001000000.getDesc(), CommonEnums.OpType.ADD, info);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Chnl_Cashpool_Add)
			.result(RISK.Result.Ok)
			.target(info.getPoolId()+"|"+info.getChnlId()+"|"+info.getChnlMchntCd())
			.message(String.format("用户： %s, 新增资金池配置信息。资金池id： %s, 渠道编号：%s, 渠道商户号：%s", commonBO.getSessionUser().getUsrId(), info.getPoolId(), info.getChnlId(), info.getChnlMchntCd()))
			.params("poolId",info.getPoolId())
			.params("chnlId",info.getChnlId())
			.params("chnlMchntCd",info.getChnlMchntCd())
			.params("priority",info.getPriority())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult delete(ChnlCashPoolInfoKey key) {
		IChnlCashPoolInfoService service = this.getDBService(IChnlCashPoolInfoService.class);
		ChnlCashPoolInfo entity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(entity, "未找到资金池信息");
		this.info(String.format("删除资金池配置信息，操作员： %s 渠道商户号：%s 资金池id：%s 渠道编号：%s", this.getSessionUsrId() ,key.getChnlMchntCd() ,key.getPoolId() , key.getChnlId()));
		service.delete(key);
		this.logObj(BmEnums.FuncInfo._2001000000.getDesc(), CommonEnums.OpType.DELETE, entity);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Chnl_Cashpool_Del)
			.result(RISK.Result.Ok)
			.target(key.getPoolId()+"|"+key.getChnlId()+"|"+key.getChnlMchntCd())
			.message(String.format("用户： %s, 删除资金池配置信息。资金池id： %s, 渠道编号：%s, 渠道商户号：%s", commonBO.getSessionUser().getUsrId(), key.getPoolId(), key.getChnlId(), key.getChnlMchntCd()))
			.params("poolId",key.getPoolId())
			.params("chnlId",key.getChnlId())
			.params("chnlMchntCd",key.getChnlMchntCd())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/enable.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult enable(Model model, String poolId,String chnlMchntCd , String chnlId,String currCd) {
		IChnlCashPoolInfoService service = this.getDBService(IChnlCashPoolInfoService.class);
		ChnlCashPoolInfoKey key = new ChnlCashPoolInfoKey();
		key.setChnlMchntCd(chnlMchntCd);
		key.setPoolId(poolId);
		key.setChnlId(chnlId);
		//前端带过来，会是币别-币别中文，需转换
//		key.setCurrCd(currCd.substring(0,3));
		ChnlCashPoolInfo entity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(entity, "未找到资金池配置信息");
		
		ChnlCashPoolInfo info = new ChnlCashPoolInfo();
		info.setChnlId(chnlId);
		info.setChnlMchntCd(chnlMchntCd);
		info.setPoolId(poolId);
//		info.setCurrCd(currCd.substring(0,3));
		info.setState(CommonEnums.ChnlSt._1.getCode()); //设置状态为有效
		info.setLastOperId(this.getSessionUsrId());
		this.info(String.format("更新资金池配置为可用，操作员： %s 渠道商户号：%s 资金池id：%s 渠道编号：%s", this.getSessionUsrId() ,key.getChnlMchntCd() ,key.getPoolId() , key.getChnlId()));
		service.update(info);
		this.logObj(BmEnums.FuncInfo._2001000000.getDesc(), CommonEnums.OpType.UPDATE, info);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Chnl_Cashpool_Enable)
			.result(RISK.Result.Ok)
			.target(key.getPoolId()+"|"+key.getChnlId()+"|"+key.getChnlMchntCd())
			.message(String.format("用户： %s, 更新资金池配置为可用。资金池id： %s, 渠道编号：%s, 渠道商户号：%s", commonBO.getSessionUser().getUsrId(), key.getPoolId(), key.getChnlId(), key.getChnlMchntCd()))
			.params("poolId",key.getPoolId())
			.params("chnlId",key.getChnlId())
			.params("chnlMchntCd",key.getChnlMchntCd())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/disable.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult unable(String chnlMchntCd, String poolId, String chnlId,String currCd) {
		IChnlCashPoolInfoService service = this.getDBService(IChnlCashPoolInfoService.class);
		ChnlCashPoolInfoKey key = new ChnlCashPoolInfoKey();
		key.setChnlMchntCd(chnlMchntCd);
		key.setPoolId(poolId);
		key.setChnlId(chnlId);
//		key.setCurrCd(currCd.substring(0,3));
		ChnlCashPoolInfo entity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(entity, "未找到资金池配置信息");
		
		ChnlCashPoolInfo info = new ChnlCashPoolInfo();
		info.setChnlId(chnlId);
		info.setChnlMchntCd(chnlMchntCd);
		info.setPoolId(poolId);
//		info.setCurrCd(currCd.substring(0,3));
		info.setState(CommonEnums.ChnlSt._0.getCode()); //设置状态为无效
		info.setLastOperId(this.getSessionUsrId());
		this.info(String.format("更新资金池配置为不可用，操作员： %s 渠道商户号：%s 资金池id：%s 渠道编号：%s", this.getSessionUsrId() ,key.getChnlMchntCd() ,key.getPoolId() , key.getChnlId()));
		service.update(info);
		this.logObj(BmEnums.FuncInfo._2001000000.getDesc(), CommonEnums.OpType.UPDATE, info);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Chnl_Cashpool_Disable)
			.result(RISK.Result.Ok)
			.target(key.getPoolId()+"|"+key.getChnlId()+"|"+key.getChnlMchntCd())
			.message(String.format("用户： %s, 更新资金池配置为不可用。资金池id： %s, 渠道编号：%s, 渠道商户号：%s", commonBO.getSessionUser().getUsrId(), key.getPoolId(), key.getChnlId(), key.getChnlMchntCd()))
			.params("poolId",key.getPoolId())
			.params("chnlId",key.getChnlId())
			.params("chnlMchntCd",key.getChnlMchntCd())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();
	}
	
	
	@RequestMapping(value = "/priorityModify.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult priorityModify(String chnlMchntCd, String poolId, String chnlId ,String wdState,String currCd) {
		IChnlCashPoolInfoService service = this.getDBService(IChnlCashPoolInfoService.class);
		ChnlCashPoolInfoKey key = new ChnlCashPoolInfoKey();
		key.setChnlMchntCd(chnlMchntCd);
		key.setPoolId(poolId);
		key.setChnlId(chnlId);
		//前端带过来，会是币别-币别中文，需转换
//		key.setCurrCd(currCd.substring(0,3));
		ChnlCashPoolInfo entity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(entity, "未找到资金池配置信息");
		
		ChnlCashPoolInfo info = new ChnlCashPoolInfo();
		info.setChnlId(chnlId);
		info.setChnlMchntCd(chnlMchntCd);
		info.setPoolId(poolId);
//		info.setCurrCd(currCd.substring(0,3));
		info.setWdState(wdState); // 优先
		if("0".endsWith(wdState)) {
			info.setPriority(100);  // 
		}else {
			info.setPriority(10);  // 优先级默认为10
		}
		info.setState(CommonEnums.ChnlSt._1.getCode());
		
		info.setLastOperId(this.getSessionUsrId());
		this.info(String.format("更新资金池配置优先级，操作员： %s 渠道商户号：%s 资金池id：%s 渠道编号：%s 优先级： %s", this.getSessionUsrId() ,key.getChnlMchntCd() ,key.getPoolId() , key.getChnlId() , info.getPriority()));
		service.update(info);
		this.logObj(BmEnums.FuncInfo._2001000000.getDesc(), CommonEnums.OpType.UPDATE, info);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Chnl_Cashpool_Modify)
			.result(RISK.Result.Ok)
			.target(poolId+"|"+chnlId+"|"+chnlMchntCd+"|"+info.getPriority())
			.message(String.format("用户： %s, 更新资金池配置优先级。资金池id： %s, 渠道编号：%s, 渠道商户号：%s, 优先级： %s", commonBO.getSessionUser().getUsrId(), key.getPoolId(), key.getChnlId(), key.getChnlMchntCd(), info.getPriority()))
			.params("poolId",key.getPoolId())
			.params("chnlId",key.getChnlId())
			.params("chnlMchntCd",key.getChnlMchntCd())
			.params("wdState", wdState)
			.params("priority", info.getPriority())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();
	}

	@RequestMapping(value = "/amtSum.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody JSONObject amtSum(Model model, String mon) {
		model.addAttribute("qryParamMap", this.getQryParamMap());
		return summary(this.getQryParamMap(), mon);
	}

	public JSONObject summary(Map<String, String> qryParamMap, String mon) {
		this.debug("[IChnlCashPoolInfoService][summary] qryParamMap: %s", qryParamMap);
		IChnlCashPoolInfoService svc = this.getDBService(IChnlCashPoolInfoService.class);
		String sum = svc.selectSummaryByChnl(qryParamMap);
		Map<String, Object> map = new HashMap<String, Object>();
		if (sum != null) {
			map.put("sumAvailableBalance", StringUtil.formateAmt(sum));// 可代付总额
		}else {
			map.put("sumAvailableBalance", "0");
		}
		JSONObject result = (JSONObject) JSON.toJSON(map);
		return result;
	}

	/**
	 * 查询全部
	 * @param poolId
	 * @return
	 */
	public void getCashPools(Model model) {
		ICashPoolInfoService poolService = this.getDBService(ICashPoolInfoService.class);
		List<CashPoolInfo> poolList = poolService.getCashPoolInfo();
		model.addAttribute("poolList", poolList);
	}

	public void getCashPoolsByValid(Model model) {
		ICashPoolInfoService poolService = this.getDBService(ICashPoolInfoService.class);
		List<CashPoolInfo> poolList = poolService.getAllCashPoolInfo();
		for(CashPoolInfo pool:poolList) {
			pool.setCurrCd(EnumUtil.translate(BmEnums.CurrCdType.class, pool.getCurrCd(), false));
		}
		model.addAttribute("poolList" , poolList);
	}
	
	public void getChnlMchnts(Model model) {
		model.addAttribute("chnlMchntList" , ChnlMchntInfoCache.getInstance().getChnlMchntInfoList());
	}
	
	
	@RequestMapping(value = "/selectChnlCash.do", method = RequestMethod.GET)
	@QryMethod
	public @ResponseBody AjaxResult selectChnlCash(String poolId,String chnlMchntCd,String currCd ) {
		IChnlCashPoolInfoService service = this.getDBService(IChnlCashPoolInfoService.class);
		//查询是否有配置其他资金池
		List<ChnlCashPoolInfo> list = service.select(chnlMchntCd);
		if(!Utils.isEmpty(list)) {
			for(ChnlCashPoolInfo info : list ) {
				if(poolId.equals(info.getPoolId())) {
//					return commonBO.buildSuccResp("EXISTS");
					return commonBO.buildErrorResp("商户已配置该资金池");
				}
			}
			return commonBO.buildSuccResp("OTHER");
		}else {
			return commonBO.buildSuccResp();

		}
			
	}
	
	@RequestMapping(value = "/getChnlMchnts.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult getChnlMchnts(String chnlId,String poolId) {
		List<Map<String,String>> list = new ArrayList<>();
		list = ChnlMchntInfoCache.getInstance().getByChnlId(chnlId);
		AjaxResult resp= commonBO.buildSuccResp("chnlMchntList", list);
		return resp;
	}
	@RequestMapping(value = "/getChnls.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult getChnls() {
		//重新捞渠道列表，丢给前端option显示
		List<Map<String,String>> list = new ArrayList<>();
		
		Map<String, String> m = EnumUtil.enumMap(EnumUtil.findEnum("com.icpay.payment.common.enums", "TxnEnums.ChnlId"), Boolean.valueOf(true));
		
		for( String map:m.keySet()) {
			Map<String,String> maps = new HashMap<String,String>();
			maps.put("chnl", map);
			maps.put("chnlDesc", m.get(map));
			list.add(maps);
		}
		
		AjaxResult resp= commonBO.buildSuccResp("chnlsList", list);
		return resp;
	}
	
	@RequestMapping(value = "/getChnlMchntsByCurrCd.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult getChnlMchntsByCurrCd(String chnlId,String poolId) {
		//先取得你选择的资金池币别
		ICashPoolInfoService service = this.getDBService(ICashPoolInfoService.class);
		List<CashPoolInfo> poolList = service.getAllCashPoolInfo();
		String poolCurrCd = "";
		String poolIntTransCd = ""; //該資金池的交易方式，目前預設5210
		for(CashPoolInfo pool:poolList) {
			if(pool.getPoolId().equals(poolId)) {
				poolCurrCd = pool.getCurrCd();
				poolIntTransCd = pool.getIntTransCd();
			}
		}
		//取得币别之后，让渠道商户号以此币别为主，去秀在前端
		List<Map<String,String>> list = new ArrayList<>();
		//取得该渠道的所有商户号
		List<Map<String, String>> ChnlMchntlist = ChnlMchntInfoCache.getInstance().getByChnlId(chnlId);
		
		if(StringUtil.isNotBlank(poolCurrCd)) {
			//商户号再去判断，渠道商户账户有没有该币别，有的话就捞出来
			for(Map<String, String> val :ChnlMchntlist) {
				IChnlMchntAccountInfoService chnlService = this.getDBService(IChnlMchntAccountInfoService.class);
				ChnlMchntAccountInfoKey key = new ChnlMchntAccountInfoKey();
				key.setChnlId(chnlId);
				key.setCurrCd(poolCurrCd); //判斷有沒有資金池的幣別
				key.setMchntCd(val.get("chnlMchntCd"));
				ChnlMchntAccountInfo entity = chnlService.selectByPrimaryKey(key);
				//有撈到該幣別商戶帳戶，需再判斷是否有該幣別的交易方式
				if(entity != null) {
					IChnlMchntInfoSubService chnlMchntInfoSubService = this.getDBService(IChnlMchntInfoSubService.class);
					ChnlMchntInfoSubKey subKey= new ChnlMchntInfoSubKey();
					subKey.setChnlId(chnlId);
					subKey.setChnlMchntCd(entity.getMchntCd());
					subKey.setCurrCd(poolCurrCd);
					subKey.setIntTransCd(poolIntTransCd);
					ChnlMchntInfoSub subResult = chnlMchntInfoSubService.selectByPrimaryKey(subKey);
					//都有撈到的話，再加入前端選項中
					if(subResult!=null) {
						list.add(val);
					}
				}
			}
		}else {
			list.addAll(ChnlMchntlist);
		}
		
		
		AjaxResult resp= commonBO.buildSuccResp("chnlMchntList", list);
		return resp;
	}
	
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, String poolId,String chnlMchntCd,String chnlId,String currCd) {
		this.getCashPools(model);
		IChnlCashPoolInfoService service = this.getDBService(IChnlCashPoolInfoService.class);
		ChnlCashPoolInfoKey key = new ChnlCashPoolInfoKey();
		key.setChnlMchntCd(chnlMchntCd);
		key.setPoolId(poolId);
		key.setChnlId(chnlId);
//		key.setCurrCd(currCd.substring(0,3));
		ChnlCashPoolInfo entity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(entity, "未找到资金池配置信息");
		Map<String, String> transfer = commonBO.transferEntity(entity, BMConstants.PAGE_CONF_CHNL_CASH_POOL_INFO, ENTITY_TRANSFER);
		//将资金池的币别带入修改页面显示
		transfer.put("poolCurrCd", currCd);
		model.addAttribute(BMConstants.ENTITY_RESULT,transfer);
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(String currCd ,String chnlId , String chnlMchntCd, String poolId, String txTimeLimit,String priority) {
		IChnlCashPoolInfoService service = this.getDBService(IChnlCashPoolInfoService.class);
		ChnlCashPoolInfoKey key = new ChnlCashPoolInfoKey();
		key.setChnlMchntCd(chnlMchntCd);
		key.setPoolId(poolId);
		key.setChnlId(chnlId);
//		key.setCurrCd(currCd.substring(0,3));
		ChnlCashPoolInfo entity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(entity, "未找到资金池配置信息");
		
		entity.setPriority(new Integer(priority));
		entity.setTxTimeLimit("".equals(txTimeLimit) ? "ALL" : txTimeLimit);
		entity.setLastOperId(this.getSessionUsrId());
		this.info(String.format("更新资金池配置，操作员： %s 渠道商户号：%s 资金池id：%s 渠道编号：%s 优先级： %s 优先时段： %s", this.getSessionUsrId() ,key.getChnlMchntCd() ,key.getPoolId() , key.getChnlId() , priority, entity.getTxTimeLimit()));
		service.update(entity);
		this.logObj(BmEnums.FuncInfo._2001000000.getDesc(), CommonEnums.OpType.UPDATE, entity);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Chnl_Cashpool_Modify)
			.result(RISK.Result.Ok)
			.target(poolId+"|"+chnlId+"|"+chnlMchntCd+"|"+entity.getTxTimeLimit())
			.message(String.format("用户： %s, 更新资金池配置。资金池id： %s, 渠道编号：%s, 渠道商户号：%s, 优先级： %s, 优先时段： %s", commonBO.getSessionUser().getUsrId(), key.getPoolId(), key.getChnlId(), key.getChnlMchntCd(), priority, entity.getTxTimeLimit()))
			.params("poolId",key.getPoolId())
			.params("chnlId",key.getChnlId())
			.params("chnlMchntCd",key.getChnlMchntCd())
			.params("priority", priority)
			.params("txTimeLimit", entity.getTxTimeLimit())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();
	}
	
}
