package com.icpay.payment.bm.web.controller.business;

import static com.icpay.payment.risk.BmUser.bmUser;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.CacheManager;
import com.icpay.payment.bm.cache.CacheType;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.data.utils.SeqNums;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.db.dao.mybatis.model.CashPoolInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfo;
import com.icpay.payment.db.dao.mybatis.model.MerCashPoolInfo;
import com.icpay.payment.db.service.ICashPoolInfoService;
import com.icpay.payment.db.service.IChnlCashPoolInfoService;
import com.icpay.payment.db.service.IMerCashPoolInfoService;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;

@Controller
@RequestMapping("/cashPool")
public class CashPoolController extends BaseController {

	private static String PRE_CD = "ZJC";
	private static final Logger logger = Logger.getLogger(ChnlController.class);
	
	private static final String RESULT_BASE_URI = "cashPool";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String state = m.get("state");
			m.put("stateDesc", EnumUtil.translate(CommonEnums.RecSt.class, state, true));
			
			String currCd = m.get("currCd");
			m.put("currCd", EnumUtil.translate(BmEnums.CurrCdType.class, currCd, true));
			
			String intTransCd = m.get("intTransCd");
			m.put("intTransCd", EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, true));
		}
	};
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		if (logger.isDebugEnabled()) {
			logger.debug("资金池管理");
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
		ICashPoolInfoService chnlService = this.getDBService(ICashPoolInfoService.class);
		Pager<CashPoolInfo> p = chnlService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_CASH_POOL_INFO, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(Model model) {
		return super.toAdd(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addSubmit(CashPoolInfo poolInfo) {
//		AssertUtil.strIsBlank(poolInfo.getPoolId(), "poolId is blank.");
		AssertUtil.strIsBlank(poolInfo.getPoolDesc(), "poolDesc is blank.");
		AssertUtil.strIsBlank(poolInfo.getCurrCd(), "CurrCd is blank.");
		AssertUtil.strIsBlank(poolInfo.getIntTransCd(), "IntTransCd is blank.");
		
		//定义资金池id规则
		// zjc+8位年月日+001序号
		
		String time = DateUtil.dateToStr8(new Date());
		String prefix= (PRE_CD + time);
		String poolId = prefix+SeqNums.nextSeq(SeqNums.MER_ID);
		
		ICashPoolInfoService poolService = this.getDBService(ICashPoolInfoService.class);
		CashPoolInfo dbEntity = poolService.selectByPrimaryKey(poolId);
		if (dbEntity != null) {
			return commonBO.buildErrorResp("资金池ID已存在");
		}
		poolInfo.setPoolId(poolId);
		poolInfo.setLastOperId(this.getSessionUsrId());
		poolInfo.setState(CommonEnums.ChnlSt._1.getCode());
		poolInfo.setIntTransCd(poolInfo.getIntTransCd());
		poolService.add(poolInfo);
		this.info(String.format("新增资金池信息，操作员： %s 资金池id：%s ", this.getSessionUsrId()  ,poolInfo.getPoolId()));
		this.logObj(BmEnums.FuncInfo._2001000000.getDesc(), CommonEnums.OpType.ADD, poolInfo);

		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Cashpool_Add)
			.result(RISK.Result.Ok)
			.target(poolId+"|"+poolInfo.getPoolDesc()+"|"+EnumUtil.translate(BmEnums.CurrCdType.class, poolInfo.getCurrCd(), false)+"|"+poolInfo.getIntTransCd())
			.message(String.format("用户： %s, 新增资金池信息。资金池id： %s, 资金池名称： %s, 币别： %s, 交易类型： %s", commonBO.getSessionUser().getUsrId(), poolId, poolInfo.getPoolDesc(), EnumUtil.translate(BmEnums.CurrCdType.class, poolInfo.getCurrCd(), false), poolInfo.getIntTransCd()))
			.params("poolId",poolInfo.getPoolId())
			.params("poolDesc",poolInfo.getPoolDesc())
			.params("currCd",poolInfo.getCurrCd())
			.params("intTransCd",poolInfo.getIntTransCd())
			.params("state",poolInfo.getState())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		// 刷新缓存
		CacheManager.refreshCache(CacheType.CASH_POOL_CACHE.getCacheTp());
		
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult delete(String poolId) {
		ICashPoolInfoService chnlService = this.getDBService(ICashPoolInfoService.class);
		CashPoolInfo entity = chnlService.selectByPrimaryKey(poolId);
		AssertUtil.objIsNull(entity, "未找到资金池信息");
		chnlService.delete(poolId);
		this.info(String.format("删除资金池信息，操作员： %s 资金池id：%s ", this.getSessionUsrId()  ,poolId));
		this.logObj(BmEnums.FuncInfo._2001000000.getDesc(), CommonEnums.OpType.DELETE, entity);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Cashpool_Del)
			.result(RISK.Result.Ok)
			.target(poolId)
			.message(String.format("用户： %s, 删除资金池信息。资金池id： %s", commonBO.getSessionUser().getUsrId(), poolId))
			.params("poolId",poolId)
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		// 刷新缓存
		CacheManager.refreshCache(CacheType.CASH_POOL_CACHE.getCacheTp());
		
		// 同步删除资金池 后端配置
		IChnlCashPoolInfoService service = this.getDBService(IChnlCashPoolInfoService.class);
		List<ChnlCashPoolInfo> list = service.selectByPoolId(poolId);
		for(ChnlCashPoolInfo info : list) {
			service.delete(info);
		}
		
		IMerCashPoolInfoService svc = this.getDBService(IMerCashPoolInfoService.class);
		List<MerCashPoolInfo> merList = svc.selectByPoolId(poolId);
		for(MerCashPoolInfo info : merList) {
			svc.delete(info);
		}
		
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, String poolId) {
		ICashPoolInfoService chnlService = this.getDBService(ICashPoolInfoService.class);
		CashPoolInfo entity = chnlService.selectByPrimaryKey(poolId);
		AssertUtil.objIsNull(entity, "未找到资金池信息");
		Map<String, String> transfer = commonBO.transferEntity(entity, BMConstants.PAGE_CONF_CASH_POOL_INFO, ENTITY_TRANSFER);
		transfer.put("currCd", entity.getCurrCd());
		model.addAttribute(BMConstants.ENTITY_RESULT,transfer);
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(CashPoolInfo cashPoolInfo) {
		cashPoolInfo.setLastOperId(this.getSessionUsrId());
		ICashPoolInfoService poolService = this.getDBService(ICashPoolInfoService.class);
		poolService.update(cashPoolInfo);
		this.info(String.format("更新资金池信息，操作员： %s 资金池id：%s ", this.getSessionUsrId()  ,cashPoolInfo.getPoolId()));
		this.logObj(BmEnums.FuncInfo._2001000000.getDesc(), CommonEnums.OpType.UPDATE, cashPoolInfo);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Cashpool_Modify)
			.result(RISK.Result.Ok)
			.target(cashPoolInfo.getPoolId()+"|" + ("1".equals(cashPoolInfo.getState()) ? "有效" : "无效"))
			.message(String.format("用户： %s, 更新资金池信息。资金池id： %s, 状态： %s", commonBO.getSessionUser().getUsrId(), cashPoolInfo.getPoolId(), ("1".equals(cashPoolInfo.getState()) ? "有效" : "无效")))
			.params("poolId",cashPoolInfo.getPoolId())
			.params("state",cashPoolInfo.getState())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		// 刷新缓存
		CacheManager.refreshCache(CacheType.CASH_POOL_CACHE.getCacheTp());
				
		//  判断状态是由有效改到无效，联动更新后端资金池中配置的大商户和小商户的状态
		if(CommonEnums.ChnlSt._0.getCode().equals(cashPoolInfo.getState())) {
			IChnlCashPoolInfoService service = this.getDBService(IChnlCashPoolInfoService.class);
			List<ChnlCashPoolInfo> list = service.selectByPoolId(cashPoolInfo.getPoolId());
			for(ChnlCashPoolInfo info : list) {
				info.setState(CommonEnums.ChnlSt._0.getCode());
				service.update(info);
			}
			
			IMerCashPoolInfoService svc = this.getDBService(IMerCashPoolInfoService.class);
			List<MerCashPoolInfo> merList = svc.selectByPoolId(cashPoolInfo.getPoolId());
			for(MerCashPoolInfo info : merList) {
				info.setState(CommonEnums.ChnlSt._0.getCode());
				svc.updateByPrimaryKey(info);
			}
		}
		return commonBO.buildSuccResp();
	}
	
}
