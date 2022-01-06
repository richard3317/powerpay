package com.icpay.payment.bm.web.controller.business;

import static com.icpay.payment.risk.BmUser.bmUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.io.Util;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfoKey;
import com.icpay.payment.db.dao.mybatis.model.MchntInfoExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfo;


import com.icpay.payment.bm.cache.CashPoolInfoCache;
import com.icpay.payment.bm.cache.MchntInfoCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant.COMMON_STATE;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.CashPoolInfo;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfo;
import com.icpay.payment.db.dao.mybatis.model.MerAccountInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MerCashPoolInfo;
import com.icpay.payment.db.dao.mybatis.model.MerCashPoolInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MerCashPoolInfoKey;
import com.icpay.payment.db.dao.mybatis.model.TxnRouting;
import com.icpay.payment.db.service.ICashPoolInfoService;
import com.icpay.payment.db.service.IChnlCashPoolInfoService;
import com.icpay.payment.db.service.IMchntAccountService;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.IMerCashPoolInfoService;
import com.icpay.payment.risk.RISK;
import com.icpay.payment.risk.RiskEvent;

@Controller
@RequestMapping("/merCashPool")
public class MerCashPoolController extends BaseController {

	private static final Logger logger = Logger.getLogger(ChnlController.class);
	
	private static final String RESULT_BASE_URI = "merCashPool";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			
			String currCd = m.get("currCd");
			m.put("currCd", EnumUtil.translate(BmEnums.CurrCdType.class, currCd, true));
			
			String state = m.get("state");
			m.put("stateDesc", EnumUtil.translate(CommonEnums.RecSt.class, state, true));
			
			String mchntCd=m.get("mchntCd");
			
			Map<String,String> mchnt = MchntInfoCache.getInstance().get(mchntCd);
			if (!Utils.isEmpty(mchnt)) {
				String mchntDesc = mchnt.get("mchntCnNm");
				m.put("mchntDesc", mchntDesc);
			}
			
			String poolId = m.get("poolId");
			Map<String,String> pool = CashPoolInfoCache.getInstance().get(poolId);
			if (!Utils.isEmpty(pool)) {
				String poolDesc = pool.get("poolDesc");
				m.put("poolDesc", poolDesc);
			}
			
		}
	};
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		if (logger.isDebugEnabled()) {
			logger.debug("小商户资金池管理");
		}
		this.getCashPools(model);
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/backToManage.do", method = RequestMethod.GET)
	public String backToManage(Model model) {
		this.getCashPools(model);
		return super.toManage(model, true, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		if (logger.isDebugEnabled()) {
			logger.debug("qry 小商户资金池管理 started...");
		}
		IMerCashPoolInfoService chnlService = this.getDBService(IMerCashPoolInfoService.class);
		Pager<MerCashPoolInfo> p = chnlService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_MER_CASH_POOL_INFO, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(Model model) {
		this.getCashPoolsByValid(model);
		return super.toAdd(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/add/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult addSubmit(MerCashPoolInfo info) {
		AssertUtil.strIsBlank(info.getPoolId(), "poolId is blank.");
		AssertUtil.strIsBlank(info.getMchntCd(), "mchntCd is blank.");

		MerCashPoolInfoKey key = new MerCashPoolInfoKey();
		key.setMchntCd(info.getMchntCd());
		key.setPoolId(info.getPoolId());

		IMerCashPoolInfoService service = this.getDBService(IMerCashPoolInfoService.class);
		MerCashPoolInfo dbEntity = service.selectByPrimaryKey(key);
		if (dbEntity != null) {
			return commonBO.buildErrorResp("商户已配置该资金池");
		}
		
		info.setLastOperId(this.getSessionUsrId());
		info.setState(CommonEnums.ChnlSt._1.getCode());
		service.add(info);
		this.info(String.format("新增商户资金池配置信息，操作员： %s 资金池id：%s 商户号：%s", this.getSessionUsrId()  ,info.getPoolId() , info.getMchntCd()));
		this.logObj(BmEnums.FuncInfo._2001000000.getDesc(), CommonEnums.OpType.ADD, info);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Mer_Cashpool_Add)
			.result(RISK.Result.Ok)
			.target(info.getMchntCd()+"|"+info.getPoolId())
			.message(String.format("用户： %s, 新增商户资金池配置信息。商户号：%s, 资金池id： %s", commonBO.getSessionUser().getUsrId(), info.getMchntCd(), info.getPoolId()))
			.params("mchntCd", info.getMchntCd())
			.params("poolId", info.getPoolId())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult delete(MerCashPoolInfoKey key) {
		IMerCashPoolInfoService service = this.getDBService(IMerCashPoolInfoService.class);
//		MerCashPoolInfoKey key = new MerCashPoolInfoKey();
//		key.setMchntCd(mchntCd);
//		key.setPoolId(poolId);
		MerCashPoolInfo entity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(entity, "未找到资金池信息");
		service.delete(key);
		this.info(String.format("删除商户资金池配置信息，操作员： %s 资金池id：%s 商户号：%s", this.getSessionUsrId()  ,key.getPoolId() , key.getMchntCd()));
		this.logObj(BmEnums.FuncInfo._2001000000.getDesc(), CommonEnums.OpType.DELETE, key);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Mer_Cashpool_Del)
			.result(RISK.Result.Ok)
			.target(key.getMchntCd()+"|"+key.getPoolId())
			.message(String.format("用户： %s, 删除商户资金池配置信息。商户号：%s, 资金池id： %s", commonBO.getSessionUser().getUsrId(), key.getMchntCd(), key.getPoolId()))
			.params("mchntCd", key.getMchntCd())
			.params("poolId", key.getPoolId())
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();
	}
	
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Model model, String poolId,String mchntCd) {
		this.getCashPoolsByValid(model);
		IMerCashPoolInfoService service = this.getDBService(IMerCashPoolInfoService.class);
		MerCashPoolInfoKey key = new MerCashPoolInfoKey();
		key.setMchntCd(mchntCd);
		key.setPoolId(poolId);
		//從poolId取得幣別資訊
		ICashPoolInfoService poolService = this.getDBService(ICashPoolInfoService.class);
		CashPoolInfo pool = poolService.selectByPrimaryKey(poolId);
		
		MerCashPoolInfo entity = service.selectByPrimaryKey(key);
		AssertUtil.objIsNull(entity, "未找到资金池信息");
		
		Map<String, String> transfer = commonBO.transferEntity(entity, BMConstants.PAGE_CONF_MER_CASH_POOL_INFO, ENTITY_TRANSFER);
		if(pool!=null) {
			transfer.put("currCd", EnumUtil.translate(BmEnums.CurrCdType.class, pool.getCurrCd(), true));
		}
		model.addAttribute(BMConstants.ENTITY_RESULT,transfer);
		return super.toEdit(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/edit/submit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult editSubmit(String mchntCd, String poolId, String newPoolId,String state,String newState,String currCd) {
		if(poolId.endsWith(newPoolId)) {
			AssertUtil.strEquals(newState,state, "数据无修改，不允许更新！");
		}
		IMerCashPoolInfoService service = this.getDBService(IMerCashPoolInfoService.class);
		MerCashPoolInfoKey key = new MerCashPoolInfoKey();
		key.setMchntCd(mchntCd);
		key.setPoolId(newPoolId);

		MerCashPoolInfo entity = service.selectByPrimaryKey(key);
		if(entity != null)
			AssertUtil.strEquals(entity.getState(), newState, "商户已配置该资金池");
		
		Map <String,String> map = new HashMap<String, String>();
		map.put("mchntCd", mchntCd);
		map.put("poolId", poolId);
		
		MerCashPoolInfo info =new MerCashPoolInfo();
		info.setMchntCd(mchntCd);
		info.setState(newState);
		info.setPoolId(newPoolId);

		info.setLastOperId(this.getSessionUsrId());
		
		service.update(info,map);
		this.info(String.format("更新商户资金池配置信息，操作员： %s 资金池id：%s 商户号：%s", this.getSessionUsrId()  ,key.getPoolId() , key.getMchntCd()));
		this.logObj(BmEnums.FuncInfo._2001000000.getDesc(), CommonEnums.OpType.UPDATE, info);
		
		RiskEvent.bm()
			.who(bmUser(commonBO.getSessionUser().getUsrId()))
			.event(RISK.Event.Mer_Cashpool_Modify)
			.result(RISK.Result.Ok)
			.target(key.getMchntCd()+"|"+key.getPoolId()+"|"+ ("1".equals(newState) ? "有效" : "无效"))
			.message(String.format("用户： %s, 更新商户资金池配置信息。商户号：%s, 资金池id： %s, 状态： %s", commonBO.getSessionUser().getUsrId(), key.getMchntCd(), key.getPoolId(), ("1".equals(newState) ? "有效" : "无效")))
			.params("mchntCd", key.getMchntCd())
			.params("poolId", key.getPoolId())
			.params("state", newState)
			.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
			.submit();
		
		return commonBO.buildSuccResp();
	}
	
	/**
	 * 查询全部
	 * @param poolId
	 * @return
	 */
	public void getCashPools(Model model) {
		ICashPoolInfoService poolService = this.getDBService(ICashPoolInfoService.class);
		List<CashPoolInfo> poolList = poolService.getCashPoolInfo();
		model.addAttribute("poolList" , poolList);
	}
	
	public void getCashPoolsByValid(Model model) {
		ICashPoolInfoService poolService = this.getDBService(ICashPoolInfoService.class);
		List<CashPoolInfo> poolList = poolService.getAllCashPoolInfo();
		//将币别156转换成(156-人民币)给前端显示
		for(CashPoolInfo pool:poolList) {
			pool.setCurrCd(EnumUtil.translate(BmEnums.CurrCdType.class, pool.getCurrCd(), false));
		}
		model.addAttribute("poolList" , poolList);
	}
	
	
	@RequestMapping(value = "/selectMerCash.do", method = RequestMethod.GET)
	@QryMethod
	public @ResponseBody AjaxResult selectMerCash(String poolId,String mchntCd ) {
		IMerCashPoolInfoService service = this.getDBService(IMerCashPoolInfoService.class);
		//查询是否有配置其他资金池
		List<MerCashPoolInfo> list = service.select(mchntCd);
		if(!Utils.isEmpty(list)) {
			for(MerCashPoolInfo info : list ) {
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

	
	@RequestMapping(value = "/batchAdd.do", method = {RequestMethod.POST, RequestMethod.GET})
	@QryMethod
	public String batchAdd(Model model,String state ,  String mchntName, String mchntCd , String poolId ) {
		this.debug("QryParamMap: "+this.getQryParamMap());
		
		
		
		
		List<MerCashPoolInfo> list = queryUsedMerCashPoolinfoByParam(state, mchntName, mchntCd, poolId);
		
		this.debug("list: "+list);
		StringBuilder buf= new StringBuilder();
		if (list!=null) {
			for (MerCashPoolInfo tr: list) {
				int i = buf.indexOf(tr.getMchntCd());
				if (i<0)
					buf.append(tr.getMchntCd()).append("\r\n");
			}
		}
		model.addAttribute("mchntCdsList", buf.toString());
		this.getCashPoolsByValid(model);
		return this.toPage(model, RESULT_BASE_URI, "batchAdd");
	}

	private List<MerCashPoolInfo> queryUsedMerCashPoolinfoByParam(String state, String mchntName, String mchntCd,String poolId){
		
		IMerCashPoolInfoService merCashPoolInfoService = this.getDBService(IMerCashPoolInfoService.class);
		MerCashPoolInfoExample merCashPoolInfoExample = new MerCashPoolInfoExample();
		
		if(StringUtil.isNotBlank(poolId)) {
			merCashPoolInfoExample.createCriteria().andPoolIdEqualTo(poolId);
		}
		if(StringUtil.isNotBlank(state)) {
			merCashPoolInfoExample.createCriteria().andStateEqualTo(state);
		}
		if(StringUtil.isNotBlank(mchntName)) {
			merCashPoolInfoExample.createCriteria().andMchntCdLike(mchntName);
		}
		if(StringUtil.isNotBlank(mchntCd)) {
			merCashPoolInfoExample.createCriteria().andMchntCdEqualTo(mchntCd);
		}
		
		List<MerCashPoolInfo> list= merCashPoolInfoService.select(merCashPoolInfoExample);
		return list;
	}
	
	
	@RequestMapping(value = "/batchAddSubmit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult batchAddSubmit(Model model, MerCashPoolInfo merCashPoolInfo, String mchntCdsList, String poolId) {
		this.checkFuncRight("2000020013");
		
		AssertUtil.objIsNull(merCashPoolInfo, "merCashPoolInfo is null.");
		AssertUtil.ifTrue(Utils.isEmpty(mchntCdsList), "商戶清單不得為空值!" );
		AssertUtil.argIsBlank(poolId,  "资金池名称不得为空值!" );

		merCashPoolInfo.setLastOperId(this.getSessionUsrId());
		merCashPoolInfo.setRecCrtTs(new Date());
		merCashPoolInfo.setRecUpdTs(new Date());
		
		/*
		 * 1. 取得Model 商戶清單 : mchntCdsList
		 * 2. 取得資金池ID
		 * 3. 確認商戶清單有效商戶
		 * 4. 確認資金池狀態
		 * 5. 針對此批次進行新增
		 * */
		String msg = "";
		
		List<String> mchntCdList = Utils.strSplitToList(mchntCdsList, "\r\n", true);
		
		// 移除不存在数据库的MchntCd
		MerAccountInfoExample example = new MerAccountInfoExample();
		example.createCriteria().andMchntCdIn(mchntCdList);
		IMchntAccountService mchntAccounService = this.getDBService(IMchntAccountService.class);
		List<MerAccountInfo> merAccountExits= mchntAccounService.selectByExample(example);
		//因现行一个币别能多个商户账户，所以此逻辑先拔掉
//		if(merAccountExits.size() == mchntCdList.size()) {			
//		}else {
//			
//			// 移除不存在数据库的MchntCd
//			for(MerAccountInfo merAccountInfo:merAccountExits) {
//				// 移除前端傳來的MchntCd, 為了後面顯示ID不存在使用
//				if(mchntCdList.contains(merAccountInfo.getMchntCd())) {
//					mchntCdList.remove(merAccountInfo.getMchntCd());
//				}
//			}
//			
//			
//			
//			msg = String.format("批量新增完成，共新增 %s 笔记录! %s 资料不存在", merAccountExits.size() ,mchntCdList.toString() );
//		}
		
		// 移除已存在MchntCd & poolID 的数据
		// 此次更新的范围，已存在的数据
		IMerCashPoolInfoService merCashPoolInfoService= this.getDBService(IMerCashPoolInfoService.class);	

		MerCashPoolInfoExample query = new MerCashPoolInfoExample();
		query.createCriteria().andPoolIdEqualTo(poolId);
		query.createCriteria().andMchntCdIn(mchntCdList);
		List<MerCashPoolInfo> exitList = merCashPoolInfoService.select(query);
		if(exitList.size()>0) {
			// 移除已存在MchntCd & poolID 的数据
			for(MerCashPoolInfo merCashPoolInfoData:exitList) {
						// 移除前端傳來的MchntCd, 為了後面顯示ID不存在使用
					if(mchntCdList.contains(merCashPoolInfoData.getMchntCd())) {
						mchntCdList.remove(merCashPoolInfoData.getMchntCd());
					}
				}
			msg = String.format("批量新增完成，共新增 %s 笔记录! %s 资料已存在", exitList.size() ,exitList.toString() );

		}
		
		
		try {
			if(merAccountExits.size()==0) {
				msg = String.format("新增失败，资料异常! %s 资料不存在", mchntCdList.toString() );
				return commonBO.buildErrorResp(msg);
			}
				ICashPoolInfoService cashPoolInfoService = this.getDBService(ICashPoolInfoService.class);
				CashPoolInfo cashPoolInfo = cashPoolInfoService.selectByPrimaryKey(poolId);
		
				merCashPoolInfo.setState(cashPoolInfo.getState());
				merCashPoolInfo.setPoolId(cashPoolInfo.getPoolId());
				List<MerCashPoolInfo> merCashPoolInfoExitList= merCashPoolInfoService.selectByPoolId(poolId);
				
				for(MerCashPoolInfo merCashPoolInfoExit:merCashPoolInfoExitList) {
					if(mchntCdList.contains(merCashPoolInfoExit.getMchntCd())) {
						merCashPoolInfoExit.setState(COMMON_STATE.YSE);
						merCashPoolInfoExit.setLastOperId(this.getSessionUsrId());
						mchntCdList.remove(merCashPoolInfoExit.getMchntCd());
						merCashPoolInfoService.updateByPrimaryKey(merCashPoolInfoExit);
					}
				}
				
				int insetCount = merCashPoolInfoService.batchAdd(mchntCdList, merCashPoolInfo);
				msg = String.format("批量新增完成，共新增 %s 笔记录!", insetCount);
				
				RiskEvent.bm()
					.who(bmUser(commonBO.getSessionUser().getUsrId()))
					.event(RISK.Event.Mer_Cashpool_Batch_Add)
					.result(RISK.Result.Ok)
					.target(insetCount+"")
					.message(String.format("用户： %s, 批量新增完成，共新增 %s 笔记录!", commonBO.getSessionUser().getUsrId(), insetCount+""))
					.params("mchntCdsList", mchntCdsList)
					.params("poolId", poolId)
					.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
					.submit();

		}catch(Exception ex) {
			ex.printStackTrace();
			msg = String.format("批量新增异常，请联系技术人员( %s)", ex.getMessage());
			return commonBO.buildErrorResp(msg);
		}
		
		
		return commonBO.buildSuccResp(msg);

		
	}
	
	@RequestMapping(value = "/batchEdit.do", method = {RequestMethod.POST, RequestMethod.GET})
	@QryMethod	
	public String batchEdit(Model model) {
		this.debug("QryParamMap: "+this.getQryParamMap());
		this.getCashPoolsByValid( model);
		return this.toPage(model, RESULT_BASE_URI, "batchEdit");
	}
	
	@RequestMapping(value = "/batchEditSubmit.do", method = RequestMethod.POST)
	@QryMethod	
	public @ResponseBody AjaxResult batchEditSubmit(Model model, CashPoolInfo cashPoolInfo ) {
		this.debug("QryParamMap: "+this.getQryParamMap());
		this.checkFuncRight("2000020011");
		AssertUtil.objIsNull(cashPoolInfo, "cashPoolInfo is null.");
		AssertUtil.objIsNull(cashPoolInfo.getState(), "资金池状态不得為空值!");
		AssertUtil.objIsNull(cashPoolInfo.getPoolId(), "资金池不得為没选择!");
		
		String msg="资料未更新";
		int modified=0;

		// 1. 更新資金持狀態
		// 2. 更新資金池以下的商戶對應關系的狀態
		MerCashPoolInfo merCashPoolInfo = new MerCashPoolInfo();

		if(StringUtil.isNotEmpty(cashPoolInfo.getState())) {
			merCashPoolInfo.setState(cashPoolInfo.getState());
			modified++;
		}
		if(StringUtil.isNotEmpty( cashPoolInfo.getPoolId())) {
			merCashPoolInfo.setPoolId(cashPoolInfo.getPoolId());
			modified++;
		}

		
		int updateCount =0 ;

		if (modified>0) {
			
			//1.根據前端條件，弄出此次需更新的數據
			MerCashPoolInfoExample query = new MerCashPoolInfoExample();
			IMerCashPoolInfoService merCashPoolInfoService= this.getDBService(IMerCashPoolInfoService.class);	
			String state = this.getQryParamMap().get("state");
			String mchntName = this.getQryParamMap().get("mchntName");
			String mchntCd = this.getQryParamMap().get("mchntCd");
			String poolId = this.getQryParamMap().get("poolId");
			//此次更新的數據
			List<MerCashPoolInfo> list = queryUsedMerCashPoolinfoByParam(state, mchntName, mchntCd, poolId);
			

			//統計要更新的商戶號(不重覆)
			List<String> updateMchntCdList = new ArrayList<String>();
			for(MerCashPoolInfo info : list) {
				if(!updateMchntCdList.contains(info.getMchntCd())) {
					updateMchntCdList.add(info.getMchntCd());
				}
			}
			
			//2.另找出已存在的数据(有用這個PoolId的跟商戶號的其他資金池)
			query.createCriteria().andPoolIdEqualTo(cashPoolInfo.getPoolId()).andMchntCdIn(updateMchntCdList);
			List<MerCashPoolInfo> exitList = merCashPoolInfoService.select(query);
			
			// 更新时，需要确认是否有资料会有因为更新之后，导致资料重复。
			if(exitList.size()>0) {
				List<String> exitsMchntCdList = new ArrayList<String>();

				// 如果更新后数据，会有存在重复，已此方法更新
				for(MerCashPoolInfo info : exitList) {
					exitsMchntCdList.add(info.getMchntCd());
				}
				for(MerCashPoolInfo update:list) {
					//判斷變更後的數據，如果已經存在了就不更新
					if(!exitsMchntCdList.contains(update.getMchntCd())) {
						if(StringUtil.isNotEmpty( cashPoolInfo.getPoolId())) {
							update.setPoolId(cashPoolInfo.getPoolId());
						}
						if(StringUtil.isNotEmpty(cashPoolInfo.getState())) {
							update.setState(cashPoolInfo.getState());
						}
						update.setRecUpdTs(new Date());
						update.setLastOperId(this.getSessionUsrId());
						merCashPoolInfoService.updateByPrimaryKey(update);
						updateCount+=1;
					}
					//上面僅判斷商戶號跟poolId，這邊額外處理State變更
					if(StringUtil.isNotEmpty(cashPoolInfo.getState())) {
						update.setState(cashPoolInfo.getState());
						update.setRecUpdTs(new Date());
						update.setLastOperId(this.getSessionUsrId());
						merCashPoolInfoService.updateByPrimaryKey(update);
						updateCount+=1;
					}
				}
			}else {
				
				merCashPoolInfo.setRecUpdTs(new Date());
				merCashPoolInfo.setLastOperId(this.getSessionUsrId());
		
				updateCount = merCashPoolInfoService.batchUpdate(this.getQryParamMap(), merCashPoolInfo);
			}
			

			
			this.logObj("批量更新资金池", CommonEnums.OpType.UPDATE, cashPoolInfo);
			msg = String.format("更新完成，共更新 %s 笔记录!", updateCount);
			
			RiskEvent.bm()
				.who(bmUser(commonBO.getSessionUser().getUsrId()))
				.event(RISK.Event.Mer_Cashpool_Batch_Modify)
				.result(RISK.Result.Ok)
				.target(updateCount+"")
				.message(String.format("用户： %s, 批量更新完成，共更新 %s 笔记录!", commonBO.getSessionUser().getUsrId(), updateCount+""))
				.params(this.getQryParamMap())
				.params("poolId", cashPoolInfo.getPoolId())
				.params("state", cashPoolInfo.getState())
				.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
				.submit();
			
		}
		return commonBO.buildSuccResp(msg);
	}
	


	@RequestMapping(value = "/batchDelete.do", method = {RequestMethod.POST, RequestMethod.GET})
	@QryMethod
	public @ResponseBody AjaxResult batchDelete(Model model, CashPoolInfo cashPoolInfo ) {
		this.checkFuncRight("2000020012");
		
		String msg="资料未删除";
		try {
			IMerCashPoolInfoService merCashPoolInfoService= this.getDBService(IMerCashPoolInfoService.class);
			int delCount = merCashPoolInfoService.batchDelete(getQryParamMap());
			msg = String.format("批量删除完成，共删除 %s 笔记录!", delCount);
			
			RiskEvent.bm()
				.who(bmUser(commonBO.getSessionUser().getUsrId()))
				.event(RISK.Event.Mer_Cashpool_Batch_Del)
				.result(RISK.Result.Ok)
				.target(delCount+"")
				.message(String.format("用户： %s, 批量删除完成，共删除 %s 笔记录!", commonBO.getSessionUser().getUsrId(), delCount+""))
				.params(this.getQryParamMap())
				.params(INTER_MSG.reqIp, commonBO.getSessionUser().getLoginIp())
				.submit();
			
		}catch(Exception ex) {
			ex.printStackTrace();
			msg = String.format("资料删除异常，请联系技术人员( %s)", ex.getMessage());
			return commonBO.buildErrorResp(msg);
		}
		
		this.logObj("批量删除资金池", CommonEnums.OpType.DELETE, this.getQryParamMap());

		return commonBO.buildSuccResp(msg);
	}
	
	
	@RequestMapping(value = "/getPoolsByCurrCd.do", method = RequestMethod.GET)
	public @ResponseBody AjaxResult getChnlMchntsByCurrCd(Model model,String currCd) {
		List<CashPoolInfo> list = new ArrayList<>();
		
		ICashPoolInfoService poolService = this.getDBService(ICashPoolInfoService.class);
		List<CashPoolInfo> poolList = poolService.getAllCashPoolInfo();
		
		for(CashPoolInfo pool:poolList) {
			if(pool.getCurrCd().equals(currCd)) {
				list.add(pool);
			}
		}
	
		AjaxResult resp= commonBO.buildSuccResp("poolList", list);
		return resp;
	}
}
