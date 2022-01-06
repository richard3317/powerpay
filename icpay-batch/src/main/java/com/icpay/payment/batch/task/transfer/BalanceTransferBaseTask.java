package com.icpay.payment.batch.task.transfer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import com.icpay.payment.batch.task.BatchTaskTemplate;
import com.icpay.payment.common.constants.RspCd;
import com.icpay.payment.common.data.dao.BalanceTransferFotT1Mapper;
import com.icpay.payment.common.data.dao.BalanceTransferLogMapper;
import com.icpay.payment.common.data.dao.ChnlMchntInfoMapper;
import com.icpay.payment.common.data.dao.ChnlMchntSettlePolicyMapper;
import com.icpay.payment.common.data.dao.MerSettlePolicyMapper;
import com.icpay.payment.common.data.model.BalanceTransferFotT1;
import com.icpay.payment.common.data.model.BalanceTransferFotT1Example;
import com.icpay.payment.common.data.model.BalanceTransferLog;
import com.icpay.payment.common.data.model.ChnlMchntInfo;
import com.icpay.payment.common.data.model.ChnlMchntInfoExample;
import com.icpay.payment.common.data.model.ChnlMchntInfoKey;
import com.icpay.payment.common.data.model.ChnlMchntSettlePolicy;
import com.icpay.payment.common.data.model.ChnlMchntSettlePolicyExample;
import com.icpay.payment.common.data.model.MerSettlePolicy;
import com.icpay.payment.common.data.model.MerSettlePolicyExample;
import com.icpay.payment.common.data.svc.DAO;
import com.icpay.payment.common.data.utils.TxnDataUtils;
import com.icpay.payment.common.exception.ChnlBizException;
import com.icpay.payment.common.utils.Converter;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.service.ChnlMerAccService;
import com.icpay.payment.service.MerParams;

public abstract class BalanceTransferBaseTask extends BatchTaskTemplate {
	
	
	/**
	 * MerParams D0/T1 结转参数分类
	 */
	public static final String TRANSFER_CAT="D0T1_transfer";
	/** 默认商户/不区分 */
	public static final String DEFAULT_MER="#DEFAULT#";
	/** D0结转时间 */
	public static final String TRANSFER_D0TIME="D0_transfer_time";
	/** T1结转时间 */
	public static final String TRANSFER_T1TIME="T1_transfer_time";
	/** T1结转时间 */
	public static final String TRANSFER_T1TIME_PRE="T1_transfer_time_pre";
	/** 时间误差 */
	public static final String TRANSFER_TIME_ERR="time_err";
	
	protected final Logger logger = Logger.getLogger(BalanceTransferBaseTask.class);
	
	
	/**
	 * 查询假期
	 */
	private static final String QUERY_HOLIDAYS ="SELECT t.`days` FROM `tbl_holidays` t WHERE t.`year`=? AND t.`month`=?;";
	
	
	//private static final String INSERT_TBL_BALANCE_TRANSFER_RESULT ="INSERT INTO tbl_balance_transfer_result (transfer_dt,mchnt_cd,transfer_at,trans_chnl,transfer_result,rec_crt_ts,rec_upd_ts,comments,operate_tp) VALUES (?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,?,?)";
	//private static final String UPDATE_TBL_BALANCE_TRANSFER_RESULT="UPDATE tbl_balance_transfer_result SET transfer_result=? ,rec_upd_ts= CURRENT_TIMESTAMP WHERE mchnt_cd=? AND transfer_dt=? AND trans_chnl=?";
	//private static final String UPDATE_MCHNT_ACCOUNT_INFO ="UPDATE tbl_mer_account_info  t SET t.`dayTxnAmt`=?,t.`dayTxnCount`=?,t.`dayTxnFee`=?,t.`dayWithdrawAmt`=?,t.`dayWithdrawCount`=?,t.`dayWithdrawFee`=? WHERE t.`mchnt_cd`=?";
	//private static final String UPDATE_CHNL_MCHNT_ACCOUNT_INFO ="UPDATE tbl_chnl_mchnt_account_info  t SET t.`dayTxnAmt`=?,t.`dayTxnCount`=?,t.`dayTxnFee`=?,t.`dayWithdrawAmt`=?,t.`dayWithdrawCount`=?,t.`dayWithdrawFee`=? WHERE t.`mchnt_cd`=?";
	
	
	ChnlMerAccService cmas=null;
	protected ChnlMerAccService chnlMerAccService(){
		if(cmas==null){
			cmas=ServiceProxy.getService(ChnlMerAccService.class);
		}
		return cmas;
	}
	
	protected List<MerSettlePolicy>  queryMerSettlePolicy(boolean isHolidays, String currCd){
		MerSettlePolicyMapper dao = DAO.getMerSettlePolicyMapper();
		MerSettlePolicyExample example1 = new MerSettlePolicyExample();
		MerSettlePolicyExample example2 = new MerSettlePolicyExample();
		MerSettlePolicyExample.Criteria c1 = 
				example1.createCriteria().andBalanceTransferEqualTo("1").andSettlePeriodIn(toList("0","9")).andCurrCdEqualTo(currCd);
		MerSettlePolicyExample.Criteria c11 = 
				example1.or().andBalanceTransferT1EqualTo("1").andSettlePeriodIn(toList("0","9")).andCurrCdEqualTo(currCd);
		
		MerSettlePolicyExample.Criteria c2 = 
				example2.createCriteria().andBalanceTransferEqualTo("1").andSettlePeriodEqualTo("9").andCurrCdEqualTo(currCd);
		MerSettlePolicyExample.Criteria c21 =
				example2.or().andBalanceTransferT1EqualTo("1").andSettlePeriodEqualTo("9").andCurrCdEqualTo(currCd);
		
		if (!isHolidays)
			return dao.selectByExample(example1);
		else
			return dao.selectByExample(example2);
	}
	
	protected List<MerSettlePolicy>  queryMerSettlePolicyPre(boolean isHolidays, String currCd){
		MerSettlePolicyMapper dao = DAO.getMerSettlePolicyMapper();
		MerSettlePolicyExample example1 = new MerSettlePolicyExample();
		MerSettlePolicyExample example2 = new MerSettlePolicyExample();
		MerSettlePolicyExample.Criteria c1 = 
				example1.createCriteria()
				.andPreTransferTimeT1IsNotNull()
				.andPreTransferTimeT1GreaterThanOrEqualTo("0000")
				.andBalanceTransferEqualTo("1")
				.andSettlePeriodIn(toList("0","9"))
				.andCurrCdEqualTo(currCd);
		MerSettlePolicyExample.Criteria c11 = 
				example1.or().andBalanceTransferT1EqualTo("1").andSettlePeriodIn(toList("0","9")).andCurrCdEqualTo(currCd);
		
		MerSettlePolicyExample.Criteria c2 = 
				example2.createCriteria()
				.andPreTransferTimeT1IsNotNull()
				.andPreTransferTimeT1GreaterThanOrEqualTo("0000")
				.andBalanceTransferEqualTo("1")
				.andSettlePeriodEqualTo("9")
				.andCurrCdEqualTo(currCd);
		MerSettlePolicyExample.Criteria c21 =
				example2.or().andBalanceTransferT1EqualTo("1").andSettlePeriodEqualTo("9")
				.andCurrCdEqualTo(currCd);
		
		if (!isHolidays)
			return dao.selectByExample(example1);
		else
			return dao.selectByExample(example2);
	}

	
//	protected List<ChnlMchntInfo>  queryChnlMerSettlePolicy(String chnlId, boolean isHolidays){
//		ChnlMchntInfoMapper dao = DAO.getChnlMchntInfoMapper();
//		
//		ChnlMchntInfoExample example1 = new ChnlMchntInfoExample();
//		ChnlMchntInfoExample example2 = new ChnlMchntInfoExample();
//		
//		ChnlMchntInfoExample.Criteria c1 = 
//				example1.createCriteria().andBalanceTransferEqualTo("1").andSettlePeriodIn(toList("0","9"));
//		ChnlMchntInfoExample.Criteria c11 = 
//				example1.or().andBalanceTransferT1EqualTo("1").andSettlePeriodIn(toList("0","9"));
//		
//		
//		ChnlMchntInfoExample.Criteria c2 =
//				example2.createCriteria().andBalanceTransferEqualTo("1").andSettlePeriodEqualTo("9");
//		ChnlMchntInfoExample.Criteria c21 =
//				example2.or().andBalanceTransferT1EqualTo("1").andSettlePeriodEqualTo("9");
//				
//		if (!isHolidays)
//			return dao.selectByExample(example1);
//		else
//			return dao.selectByExample(example2);
//	}
	
	protected String getMchntSuffix(ChnlMchntSettlePolicy mp) {
		ChnlMchntInfoMapper dao =DAO.getChnlMchntInfoMapper();
		ChnlMchntInfo rec = dao.selectByPrimaryKey(new ChnlMchntInfoKey(mp.getChnlId(), mp.getChnlMchntCd()));
		if (rec==null) return null;
		return rec.getMchntSuffix();
	}

	
	
	protected List<ChnlMchntSettlePolicy>  queryChnlMerSettlePolicy(String chnlId, boolean isHolidays, String currCd){
		ChnlMchntSettlePolicyMapper dao = DAO.getChnlMchntSettlePolicyMapper();
		
		ChnlMchntSettlePolicyExample example1 = new ChnlMchntSettlePolicyExample();
		ChnlMchntSettlePolicyExample example2 = new ChnlMchntSettlePolicyExample();
		
		ChnlMchntSettlePolicyExample.Criteria c1 = 
				example1.createCriteria().andBalanceTransferEqualTo("1").andSettlePeriodIn(toList("0","9")).andCurrCdEqualTo(currCd);
		ChnlMchntSettlePolicyExample.Criteria c11 = 
				example1.or().andBalanceTransferT1EqualTo("1").andSettlePeriodIn(toList("0","9")).andCurrCdEqualTo(currCd);
		
		
		ChnlMchntSettlePolicyExample.Criteria c2 =
				example2.createCriteria().andBalanceTransferEqualTo("1").andSettlePeriodEqualTo("9").andCurrCdEqualTo(currCd);
		ChnlMchntSettlePolicyExample.Criteria c21 =
				example2.or().andBalanceTransferT1EqualTo("1").andSettlePeriodEqualTo("9").andCurrCdEqualTo(currCd);
				
		if (!isHolidays)
			return dao.selectByExample(example1);
		else
			return dao.selectByExample(example2);
	}	
	
	
	public BalanceTransferFotT1 gueryForT1Target(String dt, String chnlId, String merId, String currCd){
		if (Utils.isEmpty(chnlId))
			chnlId = "00";
		BalanceTransferFotT1Mapper dao = DAO.getBalanceTransferFotT1Mapper();
		BalanceTransferFotT1Example example = new BalanceTransferFotT1Example();
		example.createCriteria()
			.andTransferDtEqualTo(dt)
			.andTransChnlEqualTo(chnlId)
			.andMchntCdEqualTo(merId)
			.andCurrCdEqualTo(currCd)
			.andTargetT1AmtGreaterThan(0L);
		List<BalanceTransferFotT1>  list= dao.selectByExample(example);
		if (!TxnDataUtils.hasRecord(list)) return null;
		return list.get(0);
	}
	
//	public List<BalanceTransferFotT1> gueryForT1TargetsForChnlMer(){
//		BalanceTransferFotT1Mapper dao = DAO.getBalanceTransferFotT1Mapper();
//		BalanceTransferFotT1Example example = new BalanceTransferFotT1Example();
//		example.createCriteria()
//			.andTransChnlNotEqualTo("00")
//			.andTargetT1AmtGreaterThan(new BigDecimal(0));
//		return dao.selectByExample(example);
//	}
	
	/**
	 * 插入余额结转结果表
	 * @param chnlId
	 * @param chnlMchntCd
	 * @param transferAmt
	 * @param status 1=成功,2=失败
	 * @param comment
	 * @param opt
	 */
	public void insertBalanceTransferD0Result(final String chnlId, final String chnlMchntCd, final String transferAmt, final String currCd, final BigDecimal unit,final String result, String do_trans_t1,final String comment,final String opt){
		
		BalanceTransferLogMapper dao = DAO.getBalanceTransferLogMapper();
		
		BalanceTransferLog rec = new BalanceTransferLog();
		rec.setD0Comments(comment);
		rec.setD0OperateTp(opt);
		rec.setD0Result(result);
		rec.setD0TaskTime(new Date());
		rec.setD0TransferAt(Long.parseLong(transferAmt));
		rec.setUnit(unit);
		rec.setCurrCd(currCd);
		rec.setMchntCd(chnlMchntCd);
		rec.setTransChnl(chnlId);
		rec.setTransferDt(this.batchDt);
		rec.setRecUpdTs(new Date());
		
		//判斷是否T1結轉
		if (! ("1".equals(do_trans_t1))) rec.setT1Result("3");

		try {
			int r = dao.insertSelective(rec);
			if (r<=0)
				throw new ChnlBizException(RspCd.Z_5001, "插入馀额结转结果失败!");
		} catch (Exception err) {
			this.error(err, "插入馀额结转结果失败!");
		}
	}
	
	/**
	 * 插入余额结转结果表
	 * @param chnlId
	 * @param chnlMchntCd
	 * @param transferAmt
	 * @param status 1=成功,2=失败
	 * @param comment
	 * @param opt
	 */
	public void updateBalanceTransferT1Result(final String chnlId, final String chnlMchntCd, final String transferAmt,final String currCd, final BigDecimal unit,final String result,final String comment,final String opt){
		
		BalanceTransferLogMapper dao = DAO.getBalanceTransferLogMapper();
		
		BalanceTransferLog rec = new BalanceTransferLog();
		rec.setT1Comments(comment);
		rec.setT1OperateTp(opt);
		rec.setT1Result(result);
		rec.setT1TaskTime(new Date());
		rec.setT1TransferAt(Long.parseLong(transferAmt));
		rec.setUnit(unit);
		rec.setCurrCd(currCd);
		rec.setMchntCd(chnlMchntCd);
		rec.setTransChnl(chnlId);
		rec.setTransferDt(this.batchDt);
		rec.setRecUpdTs(new Date());
		try {
			int r = dao.updateByPrimaryKeySelective(rec);
			if (r<=0)
				throw new ChnlBizException(RspCd.Z_5001, "更新馀额结转结果失败!");
		} catch (Exception err) {
			this.error(err, "更新馀额结转结果失败!");
		}
	}
	
	protected String mulPercent(String amt, String percent) {
		if (Utils.isEmpty(amt)) return "0";
		if (Utils.isEmpty(percent)) return "0";
		long iAmt = Long.parseLong(amt);
		//Double p = Double.parseDouble(percent);
		Double p = Converter.strPercentToDouble(percent);
		if (p==null) return "0";
		Long r = (long) (iAmt * p);
		return r.toString(); 
	}

	
//	/**
//	 * 更新余额结转结果表
//	 * @param chnlId
//	 * @param chnlMchntCd
//	 */
//	public void updateBalanceTransferResult(final String chnlId,final String chnlMchntCd,final String status){
//		jdbcTemplate.update(new PreparedStatementCreator() {
//			public PreparedStatement createPreparedStatement(Connection conn)
//					throws SQLException {
//				PreparedStatement ps = conn.prepareStatement(UPDATE_TBL_BALANCE_TRANSFER_RESULT);
//				ps.setString(1, status);//成功
//				ps.setString(2, chnlMchntCd);
//				ps.setString(3, batchDt);
//				ps.setString(4, chnlId);
//				return ps;
//			}
//		});
//	}
	
//	/**
//	 * 计算出需要做结转的金额
//	 * @param isHolidays
//	 * @param transDt
//	 * @param mchntCd
//	 * @param chnlId
//	 * @return
//	 */
//	public BigDecimal extractT1(String transDt,String mchntCd,String chnlId) {
//		String mon = transDt.substring(4, 6);
//		String chnlMchntAccountFlowTbl = "tbl_chnl_mchnt_account_flow" + mon;
//		String sql ="SELECT SUM(tl.available_balance + tl.frozen_t1_balance) FROM " + chnlMchntAccountFlowTbl+" tl WHERE tl.SEQ_ID=(SELECT MAX(T.SEQ_ID) FROM  " + chnlMchntAccountFlowTbl 
//						+ "  t WHERE t.`MCHNT_CD` = '" + mchntCd + "'" +  " AND t.`CHNL_ID` = '" + chnlId +"')";
//		
//		BigDecimal T1Amt = this.jdbcTemplate.queryForObject(sql,BigDecimal.class);
//		return T1Amt;
//	}
	
	
//	/**
//	 * 计算出需要做结转的金额, TODO: Select max 無意義，等同於虛擬帳戶當前餘額
//	 * @param isHolidays
//	 * @param transDt
//	 * @param mchntCd
//	 * @param chnlId
//	 * @return
//	 */
//	public BigDecimal extractD0(String transDt,String mchntCd,String chnlId) {
//		String mon = transDt.substring(4, 6);
//		String chnlMchntAccountFlowTbl = "tbl_chnl_mchnt_account_flow" + mon;
//		String sql = " SELECT tl.available_balance"
//				+ " FROM " + chnlMchntAccountFlowTbl +" tl WHERE tl.SEQ_ID=(SELECT MAX(T.SEQ_ID) FROM  " + chnlMchntAccountFlowTbl 
//				+ "  t WHERE t.`MCHNT_CD` = '" + mchntCd + "'" +  " AND t.`CHNL_ID` = '" + chnlId +"')";
//		
//		BigDecimal T1Amt = this.jdbcTemplate.queryForObject(sql,BigDecimal.class);
//		return T1Amt;
//	}
	
//	/**
//	 * 清空账户日交易信息
//	 * @param dayTxnAmt
//	 * @param dayTxnCount
//	 * @param dayTxnFee
//	 * @param dayWithdrawAmt
//	 * @param dayWithdrawCount
//	 * @param dayWithdrawFee
//	 * @param mchntCd
//	 */
//	public void updateMchntAccountInfo(final String dayTxnAmt,final String dayTxnCount, final String dayTxnFee, final String dayWithdrawAmt, final String dayWithdrawCount,final String dayWithdrawFee,final String mchntCd){
//		jdbcTemplate.update(new PreparedStatementCreator() {
//			public PreparedStatement createPreparedStatement(Connection conn)
//					throws SQLException {
//				PreparedStatement ps = conn.prepareStatement(UPDATE_MCHNT_ACCOUNT_INFO);
//				ps.setString(1, dayTxnAmt);
//				ps.setString(2, dayTxnCount);
//				ps.setString(3, dayTxnFee);
//				ps.setString(4, dayWithdrawAmt);
//				ps.setString(5, dayWithdrawCount);
//				ps.setString(6, dayWithdrawFee);
//				ps.setString(7, mchntCd);
//				return ps;
//			
//			}
//		});
//	}
//	/**
//	 * 清空渠道账户日交易信息
//	 * @param dayTxnAmt
//	 * @param dayTxnCount
//	 * @param dayTxnFee
//	 * @param dayWithdrawAmt
//	 * @param dayWithdrawCount
//	 * @param dayWithdrawFee
//	 * @param mchntCd
//	 */
//	public void updateChnlMchntAccountInfo(final String dayTxnAmt,final String dayTxnCount, final String dayTxnFee, final String dayWithdrawAmt, final String dayWithdrawCount,final String dayWithdrawFee,final String mchntCd){
//		jdbcTemplate.update(new PreparedStatementCreator() {
//			public PreparedStatement createPreparedStatement(Connection conn)
//					throws SQLException {
//				PreparedStatement ps = conn.prepareStatement(UPDATE_CHNL_MCHNT_ACCOUNT_INFO);
//				ps.setString(1, dayTxnAmt);
//				ps.setString(2, dayTxnCount);
//				ps.setString(3, dayTxnFee);
//				ps.setString(4, dayWithdrawAmt);
//				ps.setString(5, dayWithdrawCount);
//				ps.setString(6, dayWithdrawFee);
//				ps.setString(7, mchntCd);
//				return ps;
//			
//			}
//		});
//	}
	
	protected boolean shouldRunTask(String targetTime) {
		if (Utils.isEmpty(targetTime)) return false;
		if (this.isManualTrigger()) return true;
		String timeErr = MerParams.getParam("00",DEFAULT_MER,TRANSFER_CAT ,TRANSFER_TIME_ERR, "600");
		boolean r=false;
		try {
			r = Utils.isTimeEqualNow(targetTime, Integer.parseInt(timeErr));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	protected boolean shouldRunTask(String targetTime, String chnlId, String mchntCd) {
		if (Utils.isEmpty(targetTime)) return false;
		if (this.isManualTrigger()) return true;
		String timeErr = MerParams.getParam("00",DEFAULT_MER,TRANSFER_CAT ,TRANSFER_TIME_ERR, "600");
		boolean r=false;
		try {
			r = Utils.isTimeEqualNow(targetTime, Integer.parseInt(timeErr));
		} catch (Exception e) {
			logger.debug(String.format("商户(%s-%s)结转时间格式错误:  时间值: '%s', 允许误差: '%s' ;", chnlId, mchntCd, targetTime, timeErr), e); //每隔5分钟会执行一次，小心LOG太大
			System.out.println(String.format("商户(%s-%s)结转时间格式错误:  时间值: '%s', 允许误差: '%s' ;", chnlId, mchntCd, targetTime, timeErr));
			e.printStackTrace();
		}
		return r;
	}
	
	/**
	 * 获取指定时间前一日的日期
	 * @param time
	 * @return
	 */
	protected String getLastDay(Date time) {
		if (time==null) time = new Date();
		Date tm = new Date(time.getTime()-86400000L);
		return getDate(tm);
	}
	
//	protected String getLastDate() {
//		return getLastDay(getNow());
//	}
	
	protected String getDate(Date time) {
		return Converter.dateTimeToString(time);
	}
	
//	protected String getDate() {
//		return getDate(getNow());
//	}
	
//	protected Date tmNow = null;
//	
//	protected Date setNow() {
//		this.tmNow = new Date();
//		return tmNow;
//	}
//	
//	protected Date getNow() {
//		if (tmNow==null)
//			setNow();
//		return tmNow;
//	}
	
//	private Logger log = null;
//	
//	protected Logger getLogger() {
//		if (log==null) {
//			log = Logger.getLogger(this.getClass());
//		}
//		return log;
//	}
	
	protected boolean isHolidays(Date nowDate) {
		if (Utils.isEmpty(nowDate))
			throw new ChnlBizException(RspCd.Z_1003, "日期格式錯誤!");
		
		String nowStr = DateUtil.formatDate(nowDate,"yyyy-MM-dd");
		String [] arry = nowStr.split("-");
		String days = jdbcTemplate.queryForObject(QUERY_HOLIDAYS, new Object[]{arry[0],arry[1]}, String.class)+",";
		
		if (Utils.isEmpty(days))
			throw new ChnlBizException(RspCd.Z_1003, "節假日日期配置錯誤!");
		
		return (!Utils.isEmpty(days)) && (days.contains(arry[2]+","));
	}
	
	protected boolean isHolidays() {
		return this.isHolidays(this.batchTime);
	}

	private static final String QUERY_CHNL_ID = "SELECT t.chnl_id from tbl_chnl_info t";

	protected List<String> getChnlList(){
		List<String> list = jdbcTemplate.queryForList(QUERY_CHNL_ID, String.class);
		return list;
	}
	
	////////////////////////////////////////////////////////////////////////
	// Utils
	
//	private Logger log = null;
//	
//	protected Logger getLogger() {
//		if (log==null) {
//			log = Logger.getLogger(this.getClass());
//		}
//		return log;
//	}
//	
//	protected String logPrefix() {
//		return format("[%s]",this.getTaskNm());
//	}
//	
//	protected String format(String fmt, Object...args) {
//		return String.format(fmt, args);
//	}
//	
//	protected void debug(String fmt, Object...args) {
//		debug(String.format(fmt, args));
//	}
//	
//	protected void debug(String message) {
//		getLogger().debug(logPrefix() + message);
//	}
//	
//	protected void info(String fmt, Object...args) {
//		info(String.format(fmt, args));
//	}
//	
//	protected void info(String message) {
//		getLogger().info(logPrefix() + message);
//	}
//	
//	protected void warn(String fmt, Object...args) {
//		warn(String.format(fmt, args));
//	}
//	
//	protected void warn(String message) {
//		getLogger().warn(logPrefix() + message);
//	}
//	
//	protected void error(String fmt, Object...args) {
//		error(String.format(fmt, args));
//	}
//	
//	protected void error(String message) {
//		getLogger().error(logPrefix() + message);
//	}
//	
//	protected void error(Throwable err,  String message) {
//		getLogger().error(logPrefix() + message, err);
//	}
//	
//	protected void error(Throwable err, String fmt, Object...args) {
//		error(err, String.format(fmt, args));
//	}
	
//	protected List<String> toList(String... items){
//		ArrayList<String> list = new ArrayList<>();
//		for(String item : items) {
//			list.add(item);
//		}
//		return list;
//	}
	
	protected Long add(String a, String b) {
		if (Utils.isEmpty(a)|| Utils.isEmpty(b)) return null;
		Long ia=Long.parseLong(a);
		Long ib=Long.parseLong(b);
		return ia+ib;
	}

	protected String strVal(Object obj) {
		if (obj==null) return "";
		return obj.toString();
	}
	
}
