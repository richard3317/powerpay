package com.icpay.payment.batch.task.settle;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.aliyun.openservices.shade.org.apache.commons.lang3.StringUtils;
import com.icpay.payment.batch.task.BatchTaskTemplate;
import com.icpay.payment.common.constants.RspCd;
import com.icpay.payment.common.constants.Constant.ACC_SUBTYPE;
import com.icpay.payment.common.data.svc.DAO;
import com.icpay.payment.common.data.svc.TransactionContext;
import com.icpay.payment.common.data.svc.impl.ChnlMerAccServiceImpl;
import com.icpay.payment.common.enums.BmEnums;
import com.icpay.payment.common.enums.SettleEnums.SettleState;
import com.icpay.payment.common.enums.TxnEnums.TxnStatus;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.Converter;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitResult;
import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitState;
import com.icpay.payment.db.dao.mybatis.model.TxnLogView;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TxnLogSysSettleProfit;
import com.icpay.payment.db.service.ISysSettleProfitResultService;
import com.icpay.payment.db.service.ISysSettleProfitStateService;
import com.icpay.payment.db.service.ITxnLogViewService;
import com.icpay.payment.service.ChnlMerAccService;
import com.icpay.payment.service.MerParams;

@Component("sysSettleProfitCreateTask")
public class SysSettleProfitCreateTask extends BatchTaskTemplate {
	
	public static final String LAST_OPER_ID = "SysSettleProfitResultService";
	public static final String CHNL_ID = "00";
	public static final String SYS_MCHNT_CD = "999000000000SYS";
	protected static final String DEFAULT_TIME_FORMAT="yyyyMMddHHmmss";
	protected static final String DEFAULT_LONG_TIME_FORMAT="yyyyMMddHHmmss.SSS";
	
	protected boolean shouldRunTask(String targetTime) {
		String timeErr = MerParams.getParam("00", Consts.DEFAULT_MER, Consts.CAT_DAILY, Consts.PNAME_TIME_ERR, "600");
		boolean r=false;
		try {
			r = Utils.isTimeEqualNow(targetTime, Integer.parseInt(timeErr));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	@Override
	protected void doTask() {
		String taskTime = MerParams.getParam("00", Consts.DEFAULT_MER, Consts.CAT_DAILY, Consts.PNAME_TASK_TIME_SYS_SETTLE_PROFIT, "050000");
		if (shouldRunTask(taskTime)) {
			//????????????
			String settleDate = DateUtil.yesterday8();
			
			//??????tbl_sys_settle_profit_state?????????????????????
			Date dt = DateUtil.str8ToDate(settleDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			cal.add(Calendar.MONTH,-1);
			Date date = cal.getTime();
			String oneMon = DateUtil.dateToStr8(date);
			List<SysSettleProfitState> delList = this.getSysStateDao().selectBeforeDate(oneMon);
			if (delList != null && !delList.isEmpty()) {
				for (int i = 0; i < delList.size(); i ++) {
					SysSettleProfitState ss = delList.get(i);
					String transSeqId = ss.getTransSeqId();
					this.getSysStateDao().deleteByPrimaryKey(transSeqId);
					logger.info(String.format("?????????????????? - ?????????????????????????????????: %s ????????????", oneMon));
				}
			}
			
			//????????????
			if (this.isExistsProfitSettleData(settleDate)) {
				logger.info(String.format("?????? %s ????????????????????????", settleDate));
			} else {
				//????????????
				String mon = settleDate.substring(4, 6);
				Date startDate = convertStartDateTime(settleDate, "");
				Date endDate = convertEndDateTime(settleDate, "");
				List<TxnLogSysSettleProfit> txnList = this.getTxnLogViewDao().selectSysSettleProfitSummary(mon, DateUtil.dateToStr19(startDate), DateUtil.dateToStr19(endDate));
				logger.info(String.format("?????? %s ???????????????????????? %s ???????????????", new Object[]{settleDate, txnList == null ? 0 : txnList.size()}));
				
				//????????????????????????tbl_sys_settle_profit_state?????????settle_state=1?????????
				Map<String, String> qryTransLogMap = new HashMap<String, String>();
				qryTransLogMap.put("startDate", settleDate);
				qryTransLogMap.put("endDate", settleDate);
				qryTransLogMap.put("timeQryMethod", BmEnums.TimeQryMethod.ByUpdateTs.getCode());
				qryTransLogMap.put("txnState", TxnStatus.OK.getCode());
				List<TxnLogView> logList = this.getTxnLogViewDao().select("", qryTransLogMap);
				if (logList != null && !logList.isEmpty()) {
					for (int i = 0; i < logList.size(); i ++) {
						TxnLogView tl = logList.get(i);
						String transSeqId = tl.getTransSeqId();
						SysSettleProfitState ss = this.getSysStateDao().selectByPrimaryKey(transSeqId);
						if (ss == null) {
							SysSettleProfitState record = new SysSettleProfitState();
							record.setTransSeqId(transSeqId);
							record.setSettleDate(settleDate);
							record.setSettleState(SettleState._1.getCode());
							record.setRecCrtTs(new Date());
							this.getSysStateDao().insertSelective(record);
							logger.info("?????????????????? - ???????????????????????????");
						} else {
							ss.setSettleState(SettleState._1.getCode());
							this.getSysStateDao().updateByPrimaryKeySelective(ss);
						}
					}
				}
				
				if (txnList != null && !txnList.isEmpty()) {
					List<Map<String, Object>> currCdAndFee = new ArrayList<Map<String, Object>>();
					//???????????????(tbl_sys_settle_profit_result)
					for (TxnLogSysSettleProfit txnLog : txnList) {
						String currCd = txnLog.getCurrCd();
						SysSettleProfitResult sysResult = new SysSettleProfitResult();
						sysResult.setSettleDate(settleDate);
						sysResult.setSiteId(txnLog.getSiteId());
						sysResult.setMchntCd(txnLog.getMchntCd());
						sysResult.setChnlId(txnLog.getTransChnl());
						sysResult.setChnlMchntCd(txnLog.getChnlMchntCd());
						sysResult.setCurrCd(currCd);
						sysResult.setProfitAmt(txnLog.getSumTransFeeDelta());
						sysResult.setComment("");
						sysResult.setLastOperId(LAST_OPER_ID);
						sysResult.setRecCrtTs(new Date());
						sysResult.setRecUpdTs(new Date());
						this.getSysResultDao().insertSelective(sysResult);
						logger.info(String.format("?????? %s ???????????????????????????:%s", settleDate, sysResult));
						
						//????????????
						if (!currCdAndFee.isEmpty()) {
							boolean hasFlag = false;
							int hasIndex = 0;
							for (int i = 0; i < currCdAndFee.size(); i++) {
								Map<String, Object> map = currCdAndFee.get(i);
								if (map.get("currCd").equals(currCd)) {
									hasFlag = true;
									hasIndex = i;
								}
							}
							if (hasFlag) {
								Map<String, Object> map = currCdAndFee.get(hasIndex);
								BigDecimal sum = new BigDecimal(map.get("sumTransFeeDelta").toString());
								BigDecimal fee = new BigDecimal(txnLog.getSumTransFeeDelta());
								sum = sum.add(fee);
								map.put("sumTransFeeDelta", sum);
							} else {
								Map<String, Object> currMap = new HashMap<String, Object>();
								currMap.put("currCd", currCd);
								currMap.put("unit", txnLog.getUnit());
								currMap.put("sumTransFeeDelta", txnLog.getSumTransFeeDelta());
								currCdAndFee.add(currMap);
							}
						} else {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("currCd", currCd);
							map.put("unit", txnLog.getUnit());
							map.put("sumTransFeeDelta", txnLog.getSumTransFeeDelta());
							currCdAndFee.add(map);
						}
						logger.info(String.format("?????? %s ???????????? %s ", settleDate, currCdAndFee));
					}
					
					//??????(?????????SYS??????) 
					TransactionContext tx = DAO.beginTrans();
					logger.info("Begin transaction ...");
					try {
						//????????????
						for (int i = 0; i < currCdAndFee.size(); i++) {
							Map<String, Object> currCdMap = currCdAndFee.get(i);
							String currCd = currCdMap.get("currCd").toString();
							BigDecimal unit = new BigDecimal(currCdMap.get("unit").toString());
							String sumTransFeeDelta = currCdMap.get("sumTransFeeDelta").toString();
							logger.info(String.format("?????? %s ??????????????????????????? %s ??????????????? %s (???)????????? %s", settleDate, SYS_MCHNT_CD, sumTransFeeDelta, currCd));
							String note = String.format("??????%s ?????????????????????????????????: %s (???)", settleDate, sumTransFeeDelta);//???????????????????????????
							this.chnlMerAccService().add(CHNL_ID, SYS_MCHNT_CD, ACC_SUBTYPE.B0, sumTransFeeDelta, currCd, unit, LAST_OPER_ID, note);
						}
						logger.info("Commit transaction ...");
						DAO.commit(tx);
						logger.info("?????????????????? - ????????????????????????");
					} catch (Exception e) {
						logger.info("Rollback transaction ...");
						logger.info(String.format("?????????????????????????????? %s", e));
						DAO.rollback(tx);
						
						//???????????????(tbl_sys_settle_profit_result)
						Map <String,String> sysResultMap = new HashMap<String,String>();
						sysResultMap.put("settleDate", settleDate);
						List<SysSettleProfitResult> resList = this.getSysResultDao().selectByExample(sysResultMap);
						if (resList != null && !resList.isEmpty()) {
							this.getSysResultDao().deleteByExample(sysResultMap);
						}
						
						//????????????settle_state??????9??????
						List<SysSettleProfitState> ssList = getSysStateDao().select(settleDate, SettleState._1.getCode());
						if (ssList != null && !ssList.isEmpty()) {
							for (int i = 0; i < ssList.size(); i ++) {
								SysSettleProfitState ss = ssList.get(i);
								ss.setSettleState(SettleState._9.getCode());
								this.getSysStateDao().updateByPrimaryKeySelective(ss);
							}
						}
						throw e;
					}
					
					//????????????settle_state??????2?????????
					List<SysSettleProfitState> ssList = getSysStateDao().select(settleDate, SettleState._1.getCode());
					if (ssList != null && !ssList.isEmpty()) {
						for (int i = 0; i < ssList.size(); i ++) {
							SysSettleProfitState ss = ssList.get(i);
							ss.setSettleState(SettleState._2.getCode());
							this.getSysStateDao().updateByPrimaryKeySelective(ss);
						}
					}
				} else {
					logger.info(String.format("?????????(tbl_sys_settle_profit_result)?????????????????? %s ????????????????????????", settleDate));
				}
				info("??????????????????????????????");
			}
		}
	}
	
	@Override
	protected String getTaskNm() {
		return "????????????????????????????????????";
	}
	
	protected <T> T getDBService(Class<T> clazz) {
		return DBHessionServiceClient.getService(clazz);
	}
	
	ITxnLogViewService txnLogViewDao = null;
	private ITxnLogViewService getTxnLogViewDao() {
		if (this.txnLogViewDao == null) {
			this.txnLogViewDao = getDBService(ITxnLogViewService.class);
		}
		return this.txnLogViewDao;
	}
	
	ISysSettleProfitResultService sysResultDao = null;
	private ISysSettleProfitResultService getSysResultDao() {
		if (this.sysResultDao == null) {
			this.sysResultDao = getDBService(ISysSettleProfitResultService.class);
		}
		return this.sysResultDao;
	}
	
	ISysSettleProfitStateService sysStateDao = null;
	private ISysSettleProfitStateService getSysStateDao() {
		if (this.sysStateDao == null) {
			this.sysStateDao = getDBService(ISysSettleProfitStateService.class);
		}
		return this.sysStateDao;
	}
	
	private ChnlMerAccService cmas = null;
	protected ChnlMerAccService chnlMerAccService() {
		if (this.cmas == null) {
			this.cmas = new ChnlMerAccServiceImpl();
		}
		return this.cmas;
	}
	
	public boolean isExistsProfitSettleData(String settleDate) {
		Map<String, String> qryTransLogMap = new HashMap<String, String>();
		qryTransLogMap.put("settleDate", settleDate);
		return this.getSysResultDao().countByExample(qryTransLogMap) > 0L;
	}
	
	protected Date convertStartDateTime(String date, String time) {
		return convertDateTime(DEFAULT_TIME_FORMAT, date, time, "000000");
	}

	protected Date convertEndDateTime(String date, String time) {
		if (Utils.isEmpty(time)) 
			time = "235959.999";
		else
			time = StringUtils.left(time+"000000",6)+".000";
		
		return convertDateTime(DEFAULT_LONG_TIME_FORMAT, date, time, "");
	}	
	
	protected Date convertDateTime(String fmt, String dateTime) {
		try {
			return Converter.stringToDateTimeFmt(dateTime, fmt);
		} catch (ParseException e) {
			logger.info("??????????????????????????????: '%s'" + dateTime);
			throw new BizzException(RspCd.Z_1001, String.format("??????????????????????????????: '%s'", dateTime));
		}
	}
	
	protected Date convertDateTime(String dateTime) {
		try {
			return Converter.stringToDateTime(dateTime);
		} catch (ParseException e) {
			logger.info("??????????????????????????????: '%s'" + dateTime);
			throw new BizzException(RspCd.Z_1001, String.format("??????????????????????????????: '%s'", dateTime));
		}
	}
	
	protected Date convertDateTime(String fmt, String date, String time, String suffix) {
		return convertDateTime(fmt, StringUtils.left(strVal(date)+strVal(time)+strVal(suffix), fmt.length()));
	}
	
	protected String strVal(Object obj) {
		if (obj==null) return "";
		return obj.toString();
	}
	
}

