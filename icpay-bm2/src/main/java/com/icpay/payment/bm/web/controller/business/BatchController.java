package com.icpay.payment.bm.web.controller.business;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.AjaxRespEnums;
import com.icpay.payment.common.enums.BatchEnums;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.HttpClient;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.common.utils.HttpClient.OpenFailureException;
import com.icpay.payment.db.dao.mybatis.model.BatchTaskExecLog;
import com.icpay.payment.db.service.IBatchTaskExecLogService;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/batch")
public class BatchController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(BatchController.class);

	private static final String RESULT_BASE_URI = "batch";
	
	private static final Map<String, String> STAT_TASK_MAP = new LinkedHashMap<String, String>();
	static {
		STAT_TASK_MAP.put("transStatExtractTask", "交易统计中间信息抽取任务");
		STAT_TASK_MAP.put("mchntTransDailyReport", "商户交易情况日报任务");
		STAT_TASK_MAP.put("chnlTransDailyReport", "渠道交易情况日报任务");
		STAT_TASK_MAP.put("chnlMchntTransDailyReport", "渠道商户交易情况统计日报任务");
	}
	
	private Map<String, String> chnlFileChkTaskMap = new LinkedHashMap<String, String>() {
		{
			/*this.put(TxnEnums.ChnlId._01.getCode(), "TVPayFileChkTask"); // 银视通对账文件检查任务
			this.put(TxnEnums.ChnlId._03.getCode(), "HongdaFileChkTask"); // 弘达对账文件检查任务
			this.put(TxnEnums.ChnlId._04.getCode(), "HaikeFileChkTask"); // 海科对账文件检查任务
			this.put(TxnEnums.ChnlId._05.getCode(), "HaikeOfflineFileChkTask"); // 海科线下对账文件检查任务
			*/
			this.put(TxnEnums.ChnlId._11.getCode(), "QianhaiFileChkTask"); // 钱海对账文件检查任务
		}
	};
	private Map<String, String> chnlWithdrawFileChkTaskMap = new LinkedHashMap<String, String>() {
		{
			this.put(TxnEnums.ChnlId._11.getCode(), "QianhaiWithdrawFileChkTask"); // 钱海取现对账文件检查任务
		}
	};
	private Map<String, String> chnlAccChkTaskMap = new LinkedHashMap<String, String>() {
		{
			/*this.put(TxnEnums.ChnlId._01.getCode(), "TVPayAccChkTask"); // 银视通对账文件检查任务
			this.put(TxnEnums.ChnlId._03.getCode(), "HongdaAccChkTask"); // 弘达对账文件检查任务
			this.put(TxnEnums.ChnlId._04.getCode(), "HaikeAccChkTask"); // 海科对账文件检查任务
			this.put(TxnEnums.ChnlId._05.getCode(), "HaikeOfflineAccChkTask"); // 海科线下对账文件检查任务
			*/
			this.put(TxnEnums.ChnlId._11.getCode(), "QianhaiAccChkTask");   //钱海对账文件检查任务
		}
	};
	
	private Map<String, String> chnlWithdrawChkTaskMap = new LinkedHashMap<String, String>() {
		{
			this.put(TxnEnums.ChnlId._11.getCode(), "QianhaiWithdrawChkTask");   //钱海取现对账文件检查任务
		}
	};
	
	private Map<String, String> chnlT1BalanceTransferTaskMap = new LinkedHashMap<String, String>() {
		{
			this.put(TxnEnums.ChnlId._11.getCode(), "QianhaiT1BalanceTransferChnlTask");   //钱海
		}
	};
	private Map<String, String> chnlD0BalanceTransferTaskMap = new LinkedHashMap<String, String>() {
		{
			this.put(TxnEnums.ChnlId._11.getCode(), "QianhaiD0BalanceTransferChnlTask");   //钱海
		}
	};
	
	private Map<String, String> balanceTransferTaskMap = new LinkedHashMap<String, String>() {
		{
			this.put("T1BalanceTransferTask","T1余额结转");   //
			this.put("D0BalanceTransferTask","D0余额结转");   //
		}
	};
	
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String runningMode = m.get("runningMode");
			m.put("runningModeDesc", EnumUtil.translate(BatchEnums.RunningMode.class, runningMode, true));
			
			String status = m.get("status");
			m.put("statusDesc", EnumUtil.translate(BatchEnums.RunningStatus.class, status, true));
		}
	};
	
	@RequestMapping(value = "/manage.do", method = RequestMethod.GET)
	public String manage(Model model) {
		return super.toManage(model, false, RESULT_BASE_URI);
	}
	
	@RequestMapping(value = "/runSettleTask.do", method = RequestMethod.GET)
	public String runSettleTask(Model model) {
		model.addAttribute("chnlFileChkTaskMap", chnlFileChkTaskMap);
		model.addAttribute("chnlAccChkTaskMap", chnlAccChkTaskMap);
		return RESULT_BASE_URI + "/runSettleTask";
	}
	
	@RequestMapping(value = "/qry.do", method = RequestMethod.POST)
	@QryMethod
	public @ResponseBody AjaxResult qry(int pageNum, int pageSize) {
		IBatchTaskExecLogService batchExecLogService = this.getDBService(IBatchTaskExecLogService.class);
		Pager<BatchTaskExecLog> p = batchExecLogService.selectByPage(pageNum, pageSize, this.getQryParamMap());
		return commonBO.buildSuccResp(BMConstants.PAGER_RESULT, 
				commonBO.transferPager(p, BMConstants.PAGE_CONF_BATCHTASKEXECLOG, ENTITY_TRANSFER));
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model, String logId) {
		IBatchTaskExecLogService batchExecLogService = this.getDBService(IBatchTaskExecLogService.class);
		BatchTaskExecLog execLog = batchExecLogService.selectByPrimaryKey(logId);
		AssertUtil.objIsNull(execLog, "未找到记录:" + logId);
		model.addAttribute(BMConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConf(BMConstants.PAGE_CONF_BATCHTASKEXECLOG));
		model.addAttribute(BMConstants.ENTITY_RESULT,
				commonBO.transferEntity(execLog, BMConstants.PAGE_CONF_BATCHTASKEXECLOG, ENTITY_TRANSFER));
		return super.toDetail(model, RESULT_BASE_URI);
	}
	
	/**
	 * 批量任务-内部对账
	 * @param batchDt
	 * @return
	 */
	@RequestMapping(value = "/internalAccCheck.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult internalAccCheck(String batchDt) {
		AssertUtil.notDt8Str(batchDt);
		this.logText(BmEnums.FuncInfo._8000060000.getDesc(), 
				CommonEnums.OpType.RUN_BATCH, "手动拉起内部对账批量任务_" + batchDt);
		
		String taskNm = "internalAccCheckTask";
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("taskNm", taskNm);
		postData.put("batchDt", batchDt);
		return runBatchJob(taskNm, postData);
	}
	
	/**
	 * 批量任务-执行对账数据抽取任务
	 * @param batchDt
	 * @return
	 */
	@RequestMapping(value = "/dataExtract.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult dataExtract(String batchDt) {
		AssertUtil.notDt8Str(batchDt);
		this.logText(BmEnums.FuncInfo._8000060000.getDesc(), 
				CommonEnums.OpType.RUN_BATCH, "手动拉起对账数据抽取任务_" + batchDt);
		
		String taskNm = "dataExtractTask";
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("taskNm", taskNm);
		postData.put("batchDt", batchDt);
		return runBatchJob(taskNm, postData);
	}
	
	/**
	 * 批量任务-渠道对账文件检查及虚拟商户清算记录创建任务
	 * @param batchDt
	 * @return
	 */
	@RequestMapping(value = "/chnlFileChk.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult chnlFileChk(String batchDt, String chnlId) {
		AssertUtil.notDt8Str(batchDt);
		this.logText(BmEnums.FuncInfo._8000060000.getDesc(), 
				CommonEnums.OpType.RUN_BATCH, "手动拉起银视通对账文件检查及虚拟商户清算记录创建任务_" + batchDt);
		
		String taskNm = chnlFileChkTaskMap.get(chnlId);
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("taskNm", taskNm);
		postData.put("batchDt", batchDt);
		return runBatchJob(taskNm, postData);
	}
	
	/**
	 * 批量任务-渠道对账文件检查及虚拟商户清算记录创建任务
	 * @param batchDt
	 * @return
	 */
	@RequestMapping(value = "/chnlWithdrawFileChk.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult chnlWithdrawFileChk(String batchDt, String chnlId) {
		AssertUtil.notDt8Str(batchDt);
		this.logText(BmEnums.FuncInfo._8000060000.getDesc(), 
				CommonEnums.OpType.RUN_BATCH, "手动拉起银视通对账文件检查及虚拟商户清算记录创建任务_" + batchDt);
		
		String taskNm = chnlWithdrawFileChkTaskMap.get(chnlId);
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("taskNm", taskNm);
		postData.put("batchDt", batchDt);
		return runBatchJob(taskNm, postData);
	}
	
	/**
	 * 批量任务-银视通对账任务
	 * 业务人员，查询虚拟商户清算记录（一日内多条），确认无误后（修改记录状态），手动拉起本任务
	 * @param batchDt
	 * @return
	 */
	@RequestMapping(value = "/chnlAccChk.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult chnlAccChk(String batchDt,String chnlId) {
		AssertUtil.notDt8Str(batchDt);
		this.logText(BmEnums.FuncInfo._8000060000.getDesc(), 
				CommonEnums.OpType.RUN_BATCH, "手动拉起银视通对账任务_" + batchDt);
		
		String taskNm = chnlAccChkTaskMap.get(chnlId);
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("taskNm", taskNm);
		postData.put("batchDt", batchDt);
		return runBatchJob(taskNm, postData);
	}
	
	/**
	 * 批量任务-
	 * 业务人员，查询虚拟商户清算记录（一日内多条），确认无误后（修改记录状态），手动拉起本任务
	 * @param batchDt
	 * @return
	 */
	@RequestMapping(value = "/chnlWithdrawChk.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult chnlWithdrawChk(String batchDt,String chnlId) {
		AssertUtil.notDt8Str(batchDt);
		this.logText(BmEnums.FuncInfo._8000060000.getDesc(), 
				CommonEnums.OpType.RUN_BATCH, "手动拉起取现对账任务_" + batchDt);
		
		String taskNm = chnlWithdrawChkTaskMap.get(chnlId);
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("taskNm", taskNm);
		postData.put("batchDt", batchDt);
		return runBatchJob(taskNm, postData);
	}
	
	/**
	 * 批量任务-创建T+0清算记录任务
	 * @param settleDate
	 * @param settleBatch
	 * @return
	 */
	@RequestMapping(value = "/settleT0LogCreate.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult settleT0LogCreate(String batchDt, String settleBatch) {
		AssertUtil.notDt8Str(batchDt);
		String batchNo = String.valueOf(parseBatch(settleBatch));
		this.logText(BmEnums.FuncInfo._8000060000.getDesc(), 
				CommonEnums.OpType.RUN_BATCH, "手动拉起创建T+0清算记录任务_" + batchDt + "_" + settleBatch);
		
		String taskNm = "settleT0LogCreateTask";
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("taskNm", taskNm);
		postData.put("batchDt", batchDt);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put(Constant.MSG.settleDate, batchDt);
		paramMap.put(Constant.INTER_MSG.settleBatch, batchNo);
		postData.put("paramStr", JsonUtil.toJson(paramMap));
		return runBatchJob(taskNm, postData);
	}
	
	/**
	 * 批量任务-执行T+0清算
	 * @param settleDate
	 * @param settleBatch
	 * @return
	 */
	@RequestMapping(value = "/settleT0Proc.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult settleT0Proc(String batchDt, String settleBatch) {
		AssertUtil.notDt8Str(batchDt);
		String batchNo = String.valueOf(parseBatch(settleBatch));
		this.logText(BmEnums.FuncInfo._8000060000.getDesc(), 
				CommonEnums.OpType.RUN_BATCH, "手动拉起执行T+0清算任务_" + batchDt + "_" + settleBatch);
		
		String taskNm = "settleT0ProcTask";
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("taskNm", taskNm);
		postData.put("batchDt", batchDt);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put(Constant.MSG.settleDate, batchDt);
		paramMap.put(Constant.INTER_MSG.settleBatch, batchNo);
		postData.put("paramStr", JsonUtil.toJson(paramMap));
		return runBatchJob(taskNm, postData);
	}
	
	/**
	 * 批量任务-T+0划款文件生成
	 * @param settleDate
	 * @param settleBatch
	 * @return
	 */
	@RequestMapping(value = "/settleT0ResCreate.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult settleT0ResCreate(String batchDt, String settleBatch) {
		AssertUtil.notDt8Str(batchDt);
		String batchNo = String.valueOf(parseBatch(settleBatch));
		this.logText(BmEnums.FuncInfo._8000060000.getDesc(), 
				CommonEnums.OpType.RUN_BATCH, "手动拉起T+0划款文件生成任务_" + batchDt + "_" + settleBatch);
		
		String taskNm = "settleT0ResCreateTask";
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("taskNm", taskNm);
		postData.put("batchDt", batchDt);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put(Constant.MSG.settleDate, batchDt);
		paramMap.put(Constant.INTER_MSG.settleBatch, batchNo);
		postData.put("paramStr", JsonUtil.toJson(paramMap));
		return runBatchJob(taskNm, postData);
	}
	
	/**
	 * 批量任务-创建T+n清算记录
	 * @param batchDt
	 * @return
	 */
	@RequestMapping(value = "/settleTnLogCreate.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult settleTnLogCreate(String batchDt) {
		AssertUtil.notDt8Str(batchDt);
		this.logText(BmEnums.FuncInfo._8000060000.getDesc(), 
				CommonEnums.OpType.RUN_BATCH, "手动拉起创建T+n清算记录任务_" + batchDt);
		
		String taskNm = "settleTnLogCreateTask";
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("taskNm", taskNm);
		postData.put("batchDt", batchDt);
		return runBatchJob(taskNm, postData);
	}
	
	/**
	 * 批量任务-执行T+n清算任务
	 * @param batchDt
	 * @param mchntCd
	 * @return
	 */
	@RequestMapping(value = "/settleTnProc.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult settleTnProc(String batchDt, String mchntCd) {
		AssertUtil.notDt8Str(batchDt);
		AssertUtil.strIsBlank(mchntCd, "mchntCd is blank.");
		this.logText(BmEnums.FuncInfo._8000060000.getDesc(), 
				CommonEnums.OpType.RUN_BATCH, "手动拉起T+n清算任务_" + batchDt);
		
		String taskNm = "settleTnProcTask";
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("taskNm", taskNm);
		postData.put("batchDt", batchDt);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put(Constant.MSG.merId, mchntCd);
		postData.put("paramStr", JsonUtil.toJson(paramMap));
		return runBatchJob(taskNm, postData);
	}
	
	/**
	 * 批量任务-执行代理商分润文件生成任务
	 * @param batchDt
	 * @return
	 */
	@RequestMapping(value = "/agentProfitLogCreateTask.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult agentProfitLogCreateTask(String batchDt) {
		AssertUtil.notDt8Str(batchDt);
		this.logText(BmEnums.FuncInfo._8000060000.getDesc(), 
				CommonEnums.OpType.RUN_BATCH, "手动拉起代理商分润文件生成任务_" + batchDt);
		
		String taskNm = "agentProfitLogCreateTask";
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("taskNm", taskNm);
		postData.put("batchDt", batchDt);
		return runBatchJob(taskNm, postData);
	}
	
	/**
	 * 批量任务-执行代理商分润划款文件生成任务
	 * @param batchDt
	 * @return
	 */
	@RequestMapping(value = "/agentProfitResCreateTask.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult agentProfitResCreateTask(String batchDt) {
		AssertUtil.notDt8Str(batchDt);
		this.logText(BmEnums.FuncInfo._8000060000.getDesc(), 
				CommonEnums.OpType.RUN_BATCH, "手动拉起代理商分润划款文件生成任务_" + batchDt);
		
		String taskNm = "agentProfitResCreateTask";
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("taskNm", taskNm);
		postData.put("batchDt", batchDt);
		return runBatchJob(taskNm, postData);
	}
	
	/**
	 * 批量任务-执行代理商对账文件生成任务
	 * @param batchDt
	 * @return
	 */
	@RequestMapping(value = "/agentCheckFileCreateTask.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult agentCheckFileCreateTask(String batchDt) {
		AssertUtil.notDt8Str(batchDt);
		this.logText(BmEnums.FuncInfo._8000060000.getDesc(), 
				CommonEnums.OpType.RUN_BATCH, "手动拉起代理商对账文件生成任务_" + batchDt);
		
		String taskNm = "agentCheckFileCreateTask";
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("taskNm", taskNm);
		postData.put("batchDt", batchDt);
		return runBatchJob(taskNm, postData);
	}
	
	/**
	 * 批量任务-T+n划款文件生成
	 * @param batchDt
	 * @return
	 */
	@RequestMapping(value = "/settleTnResCreate.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult settleTnResCreate(String batchDt) {
		AssertUtil.notDt8Str(batchDt);
		this.logText(BmEnums.FuncInfo._8000060000.getDesc(), 
				CommonEnums.OpType.RUN_BATCH, "手动拉起T+n划款文件生成任务_" + batchDt);
		
		String taskNm = "settleTnResCreateTask";
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("taskNm", taskNm);
		postData.put("batchDt", batchDt);
		return runBatchJob(taskNm, postData);
	}
	
	@RequestMapping(value = "/runStatTask.do", method = RequestMethod.GET)
	public String runStatTask(Model model) {
		model.addAttribute("taskMap", STAT_TASK_MAP);
		return RESULT_BASE_URI + "/runStatTask";
	}
	
	@RequestMapping(value = "/runStatTaskSubmit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult runTaskSubmit(String taskNm, String batchDt) {
		AssertUtil.strIsBlank(taskNm, "taskNm is blank.");
		AssertUtil.notDt8Str(batchDt);
		this.logText(BmEnums.FuncInfo._9200020000.getDesc(), 
				CommonEnums.OpType.RUN_BATCH, "手动拉起统计分析批量任务:" + taskNm + "_" + batchDt);
		
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("batchDt", batchDt);
		postData.put("taskNm", taskNm);
		return runBatchJob(taskNm, postData);
	}
	
	private AjaxResult runBatchJob(String taskNm, Map<String, String> postData) {
		String postDataStr = JsonUtil.toJson(postData);
		logger.info("手动拉起批量任务开始:" + taskNm + "_" + postDataStr);
		HttpClient hc = null;
		try{
			String runTaskUrl = BMConfigCache.getConfig("batch_runTask_url");
			hc = new HttpClient(runTaskUrl);
			hc.open();
			hc.httpPost(postData);
			if ("200".equals(hc.getResCode())) {
				String result = hc.getResBody();
				AjaxResult ar = JsonUtil.fromJson(result, AjaxResult.class);
				if (ar.getRespCode().equals(AjaxRespEnums.SUCC.getRespCode())) {
					return commonBO.buildSuccResp();
				} else {
					logger.error("批量任务执行失败:" + ar.getRespMsg());
					return commonBO.buildErrorResp(ar.getRespMsg());
				}
			}
			logger.error("手动拉起批量任务失败:" + taskNm + "_" + postDataStr + "_" + hc.getResCode());
			return commonBO.buildErrorResp("手动拉起批量任务失败:" + taskNm);
		} catch (Exception e) {
			logger.error("手动拉起批量任务失败:" + taskNm + "_" + postDataStr, e);
			if (e instanceof OpenFailureException) {
				return commonBO.buildErrorResp("通知批量子系统失败，请检查网络");
			} else {
				return commonBO.buildErrorResp("任务处理中，请查询批量任务日志");
			}
		} finally {
			if (hc != null) {
				hc.close();
			}
		}
	}
	
	private int parseBatch(String settleBatch) {
		AssertUtil.strIsBlank(settleBatch, "settleBatch is blank.");
		int b = Integer.parseInt(settleBatch);
		if (b < 1 || b > 12) {
			throw new BizzException("批次号只能为1-12");
		}
		return b;
	}
	
	/**
	 * 余额结转
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/balanceTransferTask.do", method = RequestMethod.GET)
	public String balanceTransferTask(Model model) {
		model.addAttribute("taskMap", balanceTransferTaskMap);
		return RESULT_BASE_URI + "/balanceTransferTask";
	}
	
	/**
	 * 余额结转
	 * @param taskNm
	 * @param batchDt
	 * @return
	 */
	@RequestMapping(value = "/balanceTransferTaskSubmit.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult balanceTransferTaskSubmit(String taskNm, String batchDt) {
		AssertUtil.strIsBlank(taskNm, "taskNm is blank.");
		AssertUtil.notDt8Str(batchDt);
//		this.logText(BmEnums.FuncInfo._8000150000.getDesc(), 
//				CommonEnums.OpType.RUN_BATCH, "手动拉起余额结转任务_" + batchDt);
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("batchDt", batchDt);
		postData.put("taskNm", taskNm);
		return runBatchJob(taskNm, postData);
	}
	
	@RequestMapping(value = "/chnlD0BalanceTransferTask.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult chnlD0BalanceTransferTask(String batchDt, String chnlId) {
		AssertUtil.notDt8Str(batchDt);
//		this.logText(BmEnums.FuncInfo._8000160000.getDesc(), 
//				CommonEnums.OpType.RUN_BATCH, "手动拉起渠道D0余额结转任务_" + batchDt);
		String taskNm = chnlD0BalanceTransferTaskMap.get(chnlId);
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("taskNm", taskNm);
		postData.put("batchDt", batchDt);
		return runBatchJob(taskNm, postData);
	}
	
	@RequestMapping(value = "/chnlT1BalanceTransferTask.do", method = RequestMethod.POST)
	public @ResponseBody AjaxResult chnlT1BalanceTransferTask(String batchDt, String chnlId) {
		AssertUtil.notDt8Str(batchDt);
//		this.logText(BmEnums.FuncInfo._8000170000.getDesc(), 
//				CommonEnums.OpType.RUN_BATCH, "手动拉起渠道T1余额结转任务_" + batchDt);
		String taskNm = chnlT1BalanceTransferTaskMap.get(chnlId);
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("taskNm", taskNm);
		postData.put("batchDt", batchDt);
		return runBatchJob(taskNm, postData);
	}
}
