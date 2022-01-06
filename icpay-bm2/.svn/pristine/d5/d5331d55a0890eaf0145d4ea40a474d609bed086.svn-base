package com.icpay.payment.bm.web.controller.business;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.icpay.payment.bm.bo.BusCheckBO;
import com.icpay.payment.bm.cache.MchntInfoCache;
import com.icpay.payment.bm.cache.MchntTpCache;
import com.icpay.payment.bm.cache.RegionInfoCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.MchntStCd;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.BusCheckTaskEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.SettleEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.Amount;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.AgentInfo;
import com.icpay.payment.db.dao.mybatis.model.BusCheckTask;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicy;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicyKey;
import com.icpay.payment.db.dao.mybatis.model.MerSettlePolicySub;
import com.icpay.payment.db.dao.mybatis.model.OrganInfo;
import com.icpay.payment.db.dao.mybatis.model.TransTypeGroup;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MchntInfoAndMerSettlePolicy;
import com.icpay.payment.db.dao.mybatis.model.modelExt.OrganMchntExtInfo;
import com.icpay.payment.db.service.IAgentInfoService;
import com.icpay.payment.db.service.IBusCheckTaskService;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.db.service.IMerSettlePolicyService;
import com.icpay.payment.db.service.IOrganMchntInfoService;
import com.icpay.payment.db.service.ITransTypeGroupService;

@Controller
@RequestMapping("/busCheck")
public class BusCheckController extends BaseController {

	private static final String RESULT_BASE_URI = "busCheck";
	private static final String CHNL_DFT_ALLOWED_REQ_SRC = "00";
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String taskTp = m.get("taskTp");
			m.put("taskTpDesc", EnumUtil.translate(BusCheckTaskEnums.TaskTp.class, taskTp, true));
			
			String opTp = m.get("opTp");
			m.put("opTpDesc", EnumUtil.translate(CommonEnums.OpType.class, opTp, true));
			
			String taskSt = m.get("taskSt");
			m.put("taskStDesc", EnumUtil.translate(BusCheckTaskEnums.TaskSt.class, taskSt, true));
		}
	};
	
	@Autowired
	private BusCheckBO busCheckBO;

	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		model.addAttribute("today", DateUtil.now8());
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/backToManage.do", method = RequestMethod.GET)
	public String backToManage(Model model) {
		model.addAttribute("today", DateUtil.now8());
		return super.toManage(model, true, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		IBusCheckTaskService busCheckService = this.getDBService(IBusCheckTaskService.class);
		Pager<BusCheckTask> p = busCheckService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_BUSCHECKTASK, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, int taskId) {
		IBusCheckTaskService busCheckService = this.getDBService(IBusCheckTaskService.class);
		BusCheckTask task = busCheckService.selectByPrimaryKey(taskId);
		AssertUtil.objIsNull(task, "未找到任务信息:" + task);
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(task, BMConstants.PAGE_CONF_BUSCHECKTASK, ENTITY_TRANSFER));
		Object contentObj = busCheckBO.restoreTaskContent(task.getTaskTp(), task.getContent());
		model.addAttribute("contentObj", contentObj);
		this.buildDetailData(model, task.getTaskTp(), task.getOpTp(), contentObj);
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/checkTask.do")
	public String checkTask(Model model, int taskId) {
		IBusCheckTaskService busCheckService = this.getDBService(IBusCheckTaskService.class);
		BusCheckTask task = busCheckService.selectByPrimaryKey(taskId);
		AssertUtil.objIsNull(task, "未找到任务信息:" + task);
		if (!task.getTaskSt().equals(BusCheckTaskEnums.TaskSt._0.getCode())
				&& !task.getTaskSt().equals(BusCheckTaskEnums.TaskSt._1.getCode())) {
			throw new BizzException("请选择状态为待处理或处理中的任务");
		}
		AssertUtil.strEquals(task.getOperId(), commonBO.getSessionUser().getUsrId(), "不能审核自己提交的任务");

		// 如果是处理中状态，则判断当前操作员是否是该任务的审核员
		if (BusCheckTaskEnums.TaskSt._1.getCode().equals(task.getTaskSt())
				&& !this.getSessionUsrId().equals(task.getCheckOperId())) {
			throw new BizzException("该任务已被其他操作员审核");
		}
		
		// 如果是待处理状态，则自动将任务置为处理中状态
		if (BusCheckTaskEnums.TaskSt._0.getCode().equals(task.getTaskSt())) {
			task.setCheckOperId(this.getSessionUsrId());
			task.setTaskSt(BusCheckTaskEnums.TaskSt._1.getCode());
			busCheckService.update(task);
		}
		
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(task, BMConstants.PAGE_CONF_BUSCHECKTASK, ENTITY_TRANSFER));
		
		Object contentObj = busCheckBO.restoreTaskContent(task.getTaskTp(), task.getContent());
		model.addAttribute("contentObj", contentObj);
		this.buildDetailData(model, task.getTaskTp(), task.getOpTp(), contentObj);
		
		logText(BmEnums.FuncInfo._9000010000.getDesc(), CommonEnums.OpType.CHECK_START, "开始处理审核任务，taskId=" + taskId);
		
		return RESULT_BASE_URI + "/checkTask";
	}
	
	/**
	 * 审核通过
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/pass.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult pass(int taskId, String checkerComments) {
		busCheckBO.pass(taskId, getSessionUsrId(), checkerComments);
		logText(BmEnums.FuncInfo._9000010000.getDesc(), CommonEnums.OpType.CHECK_PASS, "审核通过，taskId=" + taskId);
		return commonBO.buildSuccResp();
	}
	
	/**
	 * 审核拒绝
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/refuse.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult refuse(int taskId, String checkerComments) {
		busCheckBO.refuse(taskId, getSessionUsrId(), checkerComments);
		logText(BmEnums.FuncInfo._9000010000.getDesc(), CommonEnums.OpType.CHECK_REFUSE, "审核拒绝，taskId=" + taskId);
		return commonBO.buildSuccResp();
	}
	
	/**
	 * 撤销任务
	 */
	@RequestMapping(value = "/cancle.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult cancle(int taskId) {
		busCheckBO.cancle(taskId, getSessionUsrId());
		logText(BmEnums.FuncInfo._9000010000.getDesc(), CommonEnums.OpType.CHECK_CANCLE, "撤销任务，taskId=" + taskId);
		return commonBO.buildSuccResp();
	}

	private void buildDetailData(Model model, String taskTp, String opTp, Object contentObj) {
		// 按照任务类型构造转义数据
		if (BusCheckTaskEnums.TaskTp._01.getCode().equals(taskTp)) {
			MchntInfo m = (MchntInfo) contentObj;
			String mchntTpDesc = MchntTpCache.getMchntTpMap().get(m.getMchntTp());
			model.addAttribute("mchntTpDesc", StringUtil.isBlank(mchntTpDesc) ? m.getMchntTp() : m.getMchntTp() + "-" + mchntTpDesc);
			model.addAttribute("areaCdDesc", RegionInfoCache.regionDesc(m.getAreaCd()));
			model.addAttribute("transTpGroupDesc", m.getTransTypeGroupId());
			MchntStCd mchntStCd = new MchntStCd();
			if (CommonEnums.OpType.UPDATE.getCode().equals(opTp)) {
				IMchntInfoService mService = this.getDBService(IMchntInfoService.class);
				MchntInfo dbEntity = mService.selectByPrimaryKey(m.getMchntCd());
				mchntStCd.parseStCd(dbEntity.getStCd());
			} else {
				mchntStCd.parseStCd(m.getStCd());
			}
			model.addAttribute("mchntStCd", mchntStCd);
			ITransTypeGroupService service = this.getDBService(ITransTypeGroupService.class);
			List<TransTypeGroup> lst = service.select(null);
			for (TransTypeGroup g : lst) {
				if (g.getSeqId() == m.getTransTypeGroupId()) {
					model.addAttribute("transTpGroupDesc", g.getSeqId() + "-" + g.getGroupNm());
				}
			}
			
			String agentCd = m.getAgentCd();
			if (!StringUtil.isBlank(agentCd)) {
				IAgentInfoService agentService = this.getDBService(IAgentInfoService.class);
				AgentInfo agent = agentService.selectByPrimaryKey(agentCd);
				if (agent != null) {
					model.addAttribute("agentCnNm", agent.getAgentCnNm());
				}
			}
			
			//允许的充值来源
			if (Utils.isEmpty(m.getAllowedReqSrc())) {
				model.addAttribute("allowedReqSrc", CHNL_DFT_ALLOWED_REQ_SRC);
			} else if ("ALL".equals(m.getAllowedReqSrc())) {
				model.addAttribute("allowedReqSrc", "11");
			} else {
				String[] allowedReqSrc = m.getAllowedReqSrc().split(",");
				model.addAttribute("allowedReqSrc", buildCtrlRule(TxnEnums.AllowedReqSrc.class, allowedReqSrc, CHNL_DFT_ALLOWED_REQ_SRC));
			}
			//允许的代付来源
			if (Utils.isEmpty(m.getAllowedReqSrcWd())) {
				model.addAttribute("allowedReqSrcWd", CHNL_DFT_ALLOWED_REQ_SRC);
			} else if ("ALL".equals(m.getAllowedReqSrcWd())) {
				model.addAttribute("allowedReqSrcWd", "11");
			} else {
				String[] allowedReqSrcWd = m.getAllowedReqSrcWd().split(",");
				model.addAttribute("allowedReqSrcWd", buildCtrlRule(TxnEnums.AllowedReqSrc.class, allowedReqSrcWd, CHNL_DFT_ALLOWED_REQ_SRC));
			}
		}
		
		if (BusCheckTaskEnums.TaskTp._04.getCode().equals(taskTp)) {
			AgentInfo agent = (AgentInfo) contentObj;
			model.addAttribute("areaCdDesc", RegionInfoCache.regionDesc(agent.getAreaCd()));
		}
		
		/// 王允   added : {{
		
		//商户清算
		if (BusCheckTaskEnums.TaskTp._51.getCode().equals(taskTp)) {
			MerSettlePolicy m = (MerSettlePolicy) contentObj;
			
			IMerSettlePolicyService service = this.getDBService(IMerSettlePolicyService.class);
			MerSettlePolicyKey key = new MerSettlePolicyKey();
			key.setCurrCd(m.getCurrCd());
			key.setMchntCd(m.getMchntCd());
			MerSettlePolicy dbEntity = service.selectByPrimaryKey(key);
			//判断是否是新增的商户清算信息
			boolean flag=(dbEntity!=null);
			
			String settlePeriod =flag? dbEntity.getSettlePeriod():m.getSettlePeriod();
			model.addAttribute("settlePeriodDesc", EnumUtil.translate(SettleEnums.SettlePeriod.class, settlePeriod, false));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			model.addAttribute("recUpdTs", flag?sdf.format(dbEntity.getRecUpdTs()):"");
			if (SettleEnums.SettlePeriod._T0.getCode().equals(settlePeriod)) {
				String settleLimit =m.getSettleLimit();
				if (!StringUtil.isBlank(settleLimit)) {
					model.addAttribute("settleLimit", StringUtil.formateAmt(settleLimit));
				}
			}
		}
		
		//商户清算计费方式审核
		if (BusCheckTaskEnums.TaskTp._52.getCode().equals(taskTp)) {
			MerSettlePolicySub m = (MerSettlePolicySub) contentObj;
			Map<String, String> map = BeanUtil.desc(m, null, null);
			map.put("intTransCdDesc", 
					EnumUtil.translate(SettleEnums.SettleTxnType.class, m.getIntTransCd(), true));
			map.put("settleModeDesc", 
					EnumUtil.translate(SettleEnums.SettleMode.class, m.getSettleMode(), true));
			map.put("settleAlgorithmDesc", 
					translateAlgorithm(m.getSettleMode(), m.getSettleAlgorithm()));
			map.put("mchntCd", m.getMchntCd());
			
			model.addAttribute("map", map);
		}
		
		/// }}
		
		//商户基本信息、清算信息、支付方式审核
		if (BusCheckTaskEnums.TaskTp._05.getCode().equals(taskTp)) {
			MchntInfoAndMerSettlePolicy m = (MchntInfoAndMerSettlePolicy) contentObj;
			String mchntTpDesc = MchntTpCache.getMchntTpMap().get(m.getMchntTp());
			model.addAttribute("mchntTpDesc", StringUtil.isBlank(mchntTpDesc) ? m.getMchntTp() : m.getMchntTp() + "-" + mchntTpDesc);
			model.addAttribute("areaCdDesc", RegionInfoCache.regionDesc(m.getAreaCd()));
			model.addAttribute("transTpGroupDesc", m.getTransTypeGroupId());
			MchntStCd mchntStCd = new MchntStCd();
			if (CommonEnums.OpType.UPDATE.getCode().equals(opTp)) {
				IMchntInfoService mService = this.getDBService(IMchntInfoService.class);
				MchntInfo dbEntity = mService.selectByPrimaryKey(m.getMchntCd());
				mchntStCd.parseStCd(dbEntity.getStCd());
			} else {
				mchntStCd.parseStCd(m.getStCd());
			}
			model.addAttribute("mchntStCd", mchntStCd);
			ITransTypeGroupService service = this.getDBService(ITransTypeGroupService.class);
			List<TransTypeGroup> lst = service.select(null);
			for (TransTypeGroup g : lst) {
				if (g.getSeqId() == m.getTransTypeGroupId()) {
					model.addAttribute("transTpGroupDesc", g.getSeqId() + "-" + g.getGroupNm());
				}
			}
			//归属代理商
			String agentCd = m.getAgentCd();
			if (!StringUtil.isBlank(agentCd)) {
				IAgentInfoService agentService = this.getDBService(IAgentInfoService.class);
				AgentInfo agent = agentService.selectByPrimaryKey(agentCd);
				if (agent != null) {
					model.addAttribute("agentCnNm", agent.getAgentCnNm());
				}
			}
			//商戶状态
			String mchntSt = m.getMchntSt();
			String mchntStDesc = EnumUtil.translate(CommonEnums.MchntSt.class, mchntSt, false);
			model.addAttribute("mchntStDesc", mchntStDesc);
			//接入方式
			if (Utils.isEmpty(m.getApiType())) {
				model.addAttribute("apiType", CHNL_DFT_ALLOWED_REQ_SRC);
			} else if ("0".equals(m.getApiType())) {
				model.addAttribute("apiType", "10");
			} else if ("1".equals(m.getApiType())) {
				model.addAttribute("apiType", "01");
			} else if (m.getApiType().length() > 1) {
				model.addAttribute("apiType", "11");
			}
			//允许的充值来源
			if (Utils.isEmpty(m.getAllowedReqSrc())) {
				model.addAttribute("allowedReqSrc", CHNL_DFT_ALLOWED_REQ_SRC);
			} else if ("ALL".equals(m.getAllowedReqSrc())) {
				model.addAttribute("allowedReqSrc", "11");
			} else {
				String[] allowedReqSrc = m.getAllowedReqSrc().split(",");
				model.addAttribute("allowedReqSrc", buildCtrlRule(TxnEnums.AllowedReqSrc.class, allowedReqSrc, CHNL_DFT_ALLOWED_REQ_SRC));
			}
			//允许的代付来源
			if (Utils.isEmpty(m.getAllowedReqSrcWd())) {
				model.addAttribute("allowedReqSrcWd", CHNL_DFT_ALLOWED_REQ_SRC);
			} else if ("ALL".equals(m.getAllowedReqSrcWd())) {
				model.addAttribute("allowedReqSrcWd", "11");
			} else {
				String[] allowedReqSrcWd = m.getAllowedReqSrcWd().split(",");
				model.addAttribute("allowedReqSrcWd", buildCtrlRule(TxnEnums.AllowedReqSrc.class, allowedReqSrcWd, CHNL_DFT_ALLOWED_REQ_SRC));
			}

			//清算周期配置
			model.addAttribute("settleResult", buildMerSettlePolicyLst(m.getMerSettlePolicy()));
			//支付方式配置
			model.addAttribute("result", buildAlgorithmLst(m.getMerSettlePolicySub()));
			
			if (!StringUtil.isBlank(m.getOrganId())) {
				IOrganMchntInfoService oService = this.getDBService(IOrganMchntInfoService.class);
				OrganInfo organ = oService.selectOrganInfoByOrganId(m.getOrganId());
				if (organ != null) {
					model.addAttribute("organId", organ.getOrganId());
					model.addAttribute("organDesc", organ.getOrganDesc());
				}
			}
			
			//站点
			String siteId = m.getSiteId();
			String siteIdDesc = EnumUtil.translate(CommonEnums.Site.class, siteId, false);
			model.addAttribute("siteIdDesc", siteIdDesc);
		}
		
		//新开户流程：商户基本信息审核
		if (BusCheckTaskEnums.TaskTp._06.getCode().equals(taskTp)) {
			MchntInfoAndMerSettlePolicy m = (MchntInfoAndMerSettlePolicy) contentObj;
			String mchntTpDesc = MchntTpCache.getMchntTpMap().get(m.getMchntTp());
			model.addAttribute("mchntTpDesc", StringUtil.isBlank(mchntTpDesc) ? m.getMchntTp() : m.getMchntTp() + "-" + mchntTpDesc);
			model.addAttribute("areaCdDesc", RegionInfoCache.regionDesc(m.getAreaCd()));
			model.addAttribute("transTpGroupDesc", m.getTransTypeGroupId());
			MchntStCd mchntStCd = new MchntStCd();
			if (CommonEnums.OpType.UPDATE.getCode().equals(opTp)) {
				IMchntInfoService mService = this.getDBService(IMchntInfoService.class);
				MchntInfo dbEntity = mService.selectByPrimaryKey(m.getMchntCd());
				mchntStCd.parseStCd(dbEntity.getStCd());
			} else {
				mchntStCd.parseStCd(m.getStCd());
			}
			model.addAttribute("mchntStCd", mchntStCd);
			ITransTypeGroupService service = this.getDBService(ITransTypeGroupService.class);
			List<TransTypeGroup> lst = service.select(null);
			for (TransTypeGroup g : lst) {
				if (g.getSeqId() == m.getTransTypeGroupId()) {
					model.addAttribute("transTpGroupDesc", g.getSeqId() + "-" + g.getGroupNm());
				}
			}
			//归属代理商
			String agentCd = m.getAgentCd();
			if (!StringUtil.isBlank(agentCd)) {
				IAgentInfoService agentService = this.getDBService(IAgentInfoService.class);
				AgentInfo agent = agentService.selectByPrimaryKey(agentCd);
				if (agent != null) {
					model.addAttribute("agentCnNm", agent.getAgentCnNm());
				}
			}
			//商戶状态
			String mchntSt = m.getMchntSt();
			String mchntStDesc = EnumUtil.translate(CommonEnums.MchntSt.class, mchntSt, false);
			model.addAttribute("mchntStDesc", mchntStDesc);
			//接入方式
			if (Utils.isEmpty(m.getApiType())) {
				model.addAttribute("apiType", CHNL_DFT_ALLOWED_REQ_SRC);
			} else if ("0".equals(m.getApiType())) {
				model.addAttribute("apiType", "10");
			} else if ("1".equals(m.getApiType())) {
				model.addAttribute("apiType", "01");
			} else if (m.getApiType().length() > 1) {
				model.addAttribute("apiType", "11");
			}
			//允许的充值来源
			if (Utils.isEmpty(m.getAllowedReqSrc())) {
				model.addAttribute("allowedReqSrc", CHNL_DFT_ALLOWED_REQ_SRC);
			} else if ("ALL".equals(m.getAllowedReqSrc())) {
				model.addAttribute("allowedReqSrc", "11");
			} else {
				String[] allowedReqSrc = m.getAllowedReqSrc().split(",");
				model.addAttribute("allowedReqSrc", buildCtrlRule(TxnEnums.AllowedReqSrc.class, allowedReqSrc, CHNL_DFT_ALLOWED_REQ_SRC));
			}
			//允许的代付来源
			if (Utils.isEmpty(m.getAllowedReqSrcWd())) {
				model.addAttribute("allowedReqSrcWd", CHNL_DFT_ALLOWED_REQ_SRC);
			} else if ("ALL".equals(m.getAllowedReqSrcWd())) {
				model.addAttribute("allowedReqSrcWd", "11");
			} else {
				String[] allowedReqSrcWd = m.getAllowedReqSrcWd().split(",");
				model.addAttribute("allowedReqSrcWd", buildCtrlRule(TxnEnums.AllowedReqSrc.class, allowedReqSrcWd, CHNL_DFT_ALLOWED_REQ_SRC));
			}
			
			if (!StringUtil.isBlank(m.getOrganId())) {
				IOrganMchntInfoService oService = this.getDBService(IOrganMchntInfoService.class);
				OrganInfo organ = oService.selectOrganInfoByOrganId(m.getOrganId());
				if (organ != null) {
					model.addAttribute("organId", organ.getOrganId());
					model.addAttribute("organDesc", organ.getOrganDesc());
				}
			}
		}
		if (BusCheckTaskEnums.TaskTp._07.getCode().equals(taskTp)) {
			OrganMchntExtInfo m = (OrganMchntExtInfo) contentObj;
			List<String> list = new ArrayList<String>();
			if (!Utils.isEmpty(m.getMchntCdStr())) {
				for(String mchntCd : m.getMchntCdStr()) {
					String mchntNm = MchntInfoCache.getMchntName(mchntCd);
					list.add(mchntCd +"|"+ mchntNm);
				}
			}else if(!Utils.isEmpty(m.getMchntCd())){
				String mchntNm = MchntInfoCache.getMchntName(m.getMchntCd());
				list.add(m.getMchntCd() +"|"+ mchntNm);
			}
			model.addAttribute("mchntCdList", list);
			
		}
	}
	
	/// 王允 added : {{
	
	/**
	 * 商户清算计费方式的审核，wangyun
	 * @param settleMode
	 * @param algorithm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected String translateAlgorithm(String settleMode, String algorithm) {
		StringBuilder desc = new StringBuilder();
		Map<String, String> m = JsonUtil.fromJson(algorithm, Map.class);
		if (SettleEnums.SettleMode._1.getCode().equals(settleMode)) {
			desc.append("单笔手续费金额:" + new BigDecimal(m.get(Constant.SETTLE_AlG_KEY.fixFee)).movePointLeft(2) + "元");
		} else if (SettleEnums.SettleMode._2.getCode().equals(settleMode)) {
			desc.append("扣率:" + m.get(Constant.SETTLE_AlG_KEY.fixRate));
			if (!StringUtil.isBlank(m.get(Constant.SETTLE_AlG_KEY.minFee))) {
//				BigDecimal bd = new BigDecimal(m.get(Constant.SETTLE_AlG_KEY.minFee));
				desc.append("<br/>保底手续费:" + m.get(Constant.SETTLE_AlG_KEY.minFee) + "元");
			}
			if (!StringUtil.isBlank(m.get(Constant.SETTLE_AlG_KEY.maxFee))) {
//				BigDecimal bd = new BigDecimal(m.get(Constant.SETTLE_AlG_KEY.maxFee));
				desc.append("<br/>封顶手续费:" + m.get(Constant.SETTLE_AlG_KEY.maxFee) + "元");
			}
		} else if (SettleEnums.SettleMode._3.getCode().equals(settleMode)
				|| SettleEnums.SettleMode._5.getCode().equals(settleMode)) {
			
			for (int i = 1; i < 6; i ++) {
				String rangeFromKey = Constant.SETTLE_AlG_KEY.rangeFrom + i;
				String rangeToKey = Constant.SETTLE_AlG_KEY.rangeTo + i;
				String rateKey = Constant.SETTLE_AlG_KEY.rate + i;
				
				if (!StringUtil.isBlank(m.get(rangeFromKey))
						&& !StringUtil.isBlank(m.get(rangeToKey))
						&& !StringUtil.isBlank(m.get(rateKey))) {
					if (i > 1) {
						desc.append("<br/>");
					}
					BigDecimal bd1 = new BigDecimal(m.get(rangeFromKey));
					BigDecimal bd2 = new BigDecimal(m.get(rangeToKey));
					desc.append("金额区间" + i + ":" + bd1.movePointLeft(2).toString() + 
							"元至" + bd2.movePointLeft(2).toString() + "元扣率为" + m.get(rateKey));
				}
			}
			
			if (!StringUtil.isBlank(m.get(Constant.SETTLE_AlG_KEY.minFee))) {
				BigDecimal bd = new BigDecimal(m.get(Constant.SETTLE_AlG_KEY.minFee));
				desc.append("<br/>保底手续费:" + String.valueOf(bd.movePointLeft(2)) + "元");
			}
			if (!StringUtil.isBlank(m.get(Constant.SETTLE_AlG_KEY.maxFee))) {
				BigDecimal bd = new BigDecimal(m.get(Constant.SETTLE_AlG_KEY.maxFee));
				desc.append("<br/>封顶手续费:" + String.valueOf(bd.movePointLeft(2)) + "元");
			}
		} else if (SettleEnums.SettleMode._4.getCode().equals(settleMode)) {
			for (int i = 1; i < 6; i ++) {
				String rangeFromKey = Constant.SETTLE_AlG_KEY.rangeFrom + i;
				String rangeToKey = Constant.SETTLE_AlG_KEY.rangeTo + i;
				String rateKey = Constant.SETTLE_AlG_KEY.rate + i;
				
				if (!StringUtil.isBlank(m.get(rangeFromKey))
						&& !StringUtil.isBlank(m.get(rangeToKey))
						&& !StringUtil.isBlank(m.get(rateKey))) {
					if (i > 1) {
						desc.append("<br/>");
					}
					desc.append("笔数区间" + i + ":" + m.get(rangeFromKey) + 
							"笔至" + m.get(rangeToKey) + "笔扣率为" + m.get(rateKey));
				}
			}
			
			if (!StringUtil.isBlank(m.get(Constant.SETTLE_AlG_KEY.minFee))) {
				BigDecimal bd = new BigDecimal(m.get(Constant.SETTLE_AlG_KEY.minFee));
				desc.append("<br/>保底手续费:" + String.valueOf(bd.movePointLeft(2)) + "元");
			}
			if (!StringUtil.isBlank(m.get(Constant.SETTLE_AlG_KEY.maxFee))) {
				BigDecimal bd = new BigDecimal(m.get(Constant.SETTLE_AlG_KEY.maxFee));
				desc.append("<br/>封顶手续费:" + String.valueOf(bd.movePointLeft(2)) + "元");
			}
		} else {
			throw new BizzException("不支持的计费方式:" + settleMode);
		}
		return desc.toString();
	}
	
	public static String buildCtrlRule(Class clazz, String[] values, String ctrlRuleDftVal) {
		if ((values == null) || (values.length == 0)) {
			return ctrlRuleDftVal;
		} 
		char[] cs = ctrlRuleDftVal.toCharArray();
		for (String v : values) {
			if (!"04".equals(v)) {
				int idx = EnumUtil.parseIndx(TxnEnums.AllowedReqSrc.class, v);
				cs[idx] = '1';
			}
		}
		return new String(cs);
	}
	
	private List buildAlgorithmLst(List<MerSettlePolicySub> lst) {
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		for (MerSettlePolicySub mps : lst) {
			Map<String, String> m = BeanUtil.desc(mps, null, null);
			m.put("intTransCdDesc", 
					EnumUtil.translate(SettleEnums.SettleTxnType.class, mps.getIntTransCd(), true));
			//币别
			m.put("currCdDesc", 
					EnumUtil.translate(BmEnums.CurrCdType.class, mps.getCurrCd(), true));
			
			m.put("settleModeDesc", 
					EnumUtil.translate(SettleEnums.SettleMode.class, mps.getSettleMode(), true));
			m.put("txT0Percent", mps.getTxT0Percent());
			String settleAlgorithm=mps.getSettleAlgorithm();
			Map<String, String> map = JSONObject.parseObject(settleAlgorithm, new TypeReference<Map<String, String>>() {
			});
			map.put("minFee", amtTranfer(mps.getCurrCd(), map.get("minFee")));
			map.put("fixRate",map.get("fixRate"));
			map.put("maxFee", amtTranfer(mps.getCurrCd(), map.get("maxFee")));
			JSONObject Json = (JSONObject) JSON.toJSON(map);
			settleAlgorithm = Json.toJSONString();
			m.put("settleAlgorithmDesc", 
					translateAlgorithm(mps.getSettleMode(),settleAlgorithm));
			m.put("settleAlgorithmLimit", translateAlgorithmLimit(mps));
			result.add(m);
		}
		return result;
	}
	
	private String translateAlgorithmLimit(MerSettlePolicySub mps) {
		StringBuilder desc = new StringBuilder();
		String format="单笔下限: %s, 单笔上限: %s (元)<br/>单卡上限: %s, 单日上限: %s (元)<br/>交易时段: %s";
		if (mps!=null) {
			desc.append(String.format(format, 
					amtTranfer(mps.getCurrCd(), mps.getTxAmtMin()), amtTranfer(mps.getCurrCd(), mps.getTxAmtMax()),
					amtTranfer(mps.getCurrCd(), mps.getTxCardDayMax()), amtTranfer(mps.getCurrCd(), mps.getTxDayMax()),
					mps.getTxTimeLimit()
					));
		}
		return desc.toString();
	}
	
	private List buildMerSettlePolicyLst(List<MerSettlePolicy> lst) {
		List<Map<String, String>> settleResult = new ArrayList<Map<String,String>>();
		for (MerSettlePolicy msp : lst) {
			Map<String, String> m = BeanUtil.desc(msp, null, null);

			//币别
			m.put("currCdDesc", 
					EnumUtil.translate(BmEnums.CurrCdType.class, msp.getCurrCd(), true));
			
			//清算周期
			m.put("settlePeriodDesc", 
					EnumUtil.translate(SettleEnums.SettlePeriod.class, msp.getSettlePeriod(), false));
			//D0结转
			m.put("balanceTransferDesc", 
					EnumUtil.translate(TxnEnums.CommonYesNo.class, msp.getBalanceTransfer(), false));
			//T1反还
			m.put("balanceTransferT1Desc", 
					EnumUtil.translate(TxnEnums.CommonYesNo.class, msp.getBalanceTransferT1(), false));

			settleResult.add(m);
		}
		return settleResult;
	}
	
	/*
	 * 金额单位转换(元)
	 */
	public String amtTranfer(String currCd, String fieldAmt) {
		BigDecimal srcAmt = Amount.create(currCd).defaultUnit().amount(fieldAmt).toRegularUnit().getAmountValue();
		String targetAmt = srcAmt.stripTrailingZeros().toPlainString(); //去小数点多余的0
		return targetAmt;
	}
}
