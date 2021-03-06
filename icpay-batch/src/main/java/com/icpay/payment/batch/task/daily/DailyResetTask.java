package com.icpay.payment.batch.task.daily;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.BatchTaskTemplate;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.data.dao.ChnlMchntAccountInfoMapper;
import com.icpay.payment.common.data.dao.ChnlMchntInfoMapper;
import com.icpay.payment.common.data.dao.ChnlMchntSettlePolicyMapper;
import com.icpay.payment.common.data.dao.MerAccountInfoMapper;
import com.icpay.payment.common.data.dao.MerSettlePolicyMapper;
import com.icpay.payment.common.data.model.ChnlMchntAccountInfo;
import com.icpay.payment.common.data.model.ChnlMchntInfo;
import com.icpay.payment.common.data.model.ChnlMchntInfoExample;
import com.icpay.payment.common.data.model.ChnlMchntSettlePolicy;
import com.icpay.payment.common.data.model.MerAccountInfo;
import com.icpay.payment.common.data.model.MerSettlePolicy;
import com.icpay.payment.common.data.model.MerSettlePolicyExample;
import com.icpay.payment.common.data.svc.DAO;
import com.icpay.payment.common.data.utils.TxnDataUtils;
import com.icpay.payment.common.enums.CurrencyEnums.CurrType;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.service.ChnlMerAccService;
import com.icpay.payment.service.MerParams;

@Component("dailyResetTask")
public class DailyResetTask extends BatchTaskTemplate {
	
	/**
	 * MerParams D0/T1 结转参数分类
	 */
	public static final String TRANSFER_CAT="D0T1_transfer";
	/** 默认商户/不区分 */
	public static final String DEFAULT_MER="#DEFAULT#";
	/** D0结转时间 */
	public static final String TRANSFER_D0TIME="D0_transfer_time";
	/** 时间误差 */
	public static final String TRANSFER_TIME_ERR="time_err";
	
	private String defaultMerTaskTime="";
	
	
	protected boolean shouldRunTask(String chnlId, String targetTime) {
		//if (Utils.isEmpty(chnlId)) chnlId="00";
		//String timeErr = MerParams.getParam(chnlId,Consts.DEFAULT_MER, Consts.CAT_DAILY_RESET ,Consts.PNAME_TIME_ERR, "300");
		String timeErr = MerParams.getParam("00",DEFAULT_MER,TRANSFER_CAT ,TRANSFER_TIME_ERR, "600");
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
		//String taskTime = MerParams.getParam("00",Consts.DEFAULT_MER, Consts.CAT_DAILY_RESET , Consts.PNAME_TASK_TIME, "235000");
		defaultMerTaskTime = MerParams.getParam("00",DEFAULT_MER, TRANSFER_CAT , TRANSFER_D0TIME, "000000");
		//if (! this.shouldRunTask(taskTime)) return ;
		resetForChnlAccount();
		resetForMerAccount();
	}

	protected void resetForMerAccount() {
		MerSettlePolicyMapper dao = DAO.getMerSettlePolicyMapper();
		List<MerSettlePolicy> list= dao.selectByExample(null);
		
		if ((list==null) || (list.size()==0)) {
			debug("无任何商户符合重置条件!");
			return;
		}
		
		for(MerSettlePolicy mer: list) {
			String taskTime = mer.getTransferTime();
			if (Utils.isEmpty(taskTime))
				taskTime=defaultMerTaskTime;
			if (this.shouldRunTask("00",taskTime))
				resetForMerAccount(mer.getMchntCd(), mer.getCurrCd());
		}
	}
	
	protected void resetForChnlAccount() {
		ChnlMchntSettlePolicyMapper dao =DAO.getChnlMchntSettlePolicyMapper();
		List<ChnlMchntSettlePolicy> list= dao.selectByExample(null);
		
		if ((list==null) || (list.size()==0)) {
			debug("无任何渠道商户符合重置条件!");
			return;
		}
		
		for(ChnlMchntSettlePolicy mer: list) {
			String taskTime=mer.getTransferTime();
			if (Utils.isEmpty(taskTime))
				taskTime = MerParams.getParam(mer.getChnlId(), DEFAULT_MER, TRANSFER_CAT ,TRANSFER_D0TIME, "000000");;
			if (this.shouldRunTask(mer.getChnlId(),taskTime))
				resetForChnlAccount(mer.getChnlId(), mer.getChnlMchntCd(), mer.getCurrCd());
		}
	}

	

	/**
	 * @param mer
	 */
	protected String resetForMerAccount(String mchntCd, String currCd) {
		String chnlId = "00";
		String opType = Constant.OPERTYPE._90 ;
		String memo = String.format("商戶: %s, 币别: %s, 重置日累计额, 操作: %s", mchntCd, currCd, opType);
		String taskResult="2";
		try {
			this.adjust(chnlId, mchntCd, currCd, opType, this.getTaskNm(), memo);
			taskResult="1";
		} catch (Exception e) {
			taskResult="2";
			error(e, "重置归零作业失败! 商戶: %s, 币别: %s, 重置日累计额, 操作: %s", mchntCd, currCd, opType);
		}
		return taskResult;
	}
	
//	protected void adjust(String chnlId, String merId, String opType, String operator, String note) {
//		Object[] currs = EnumUtil.getEnumConstants(CurrType.class);
//		for(Object item: currs) {
//			CurrType curr = (CurrType) item;
//			this.chnlMerAccService().adjust(chnlId, merId, opType, "0", curr.getCode(), curr.getDefaultUnit(), operator, note);
//		}
//	}
	
	protected void adjust(String chnlId, String merId, String currCd, String opType, String operator, String note) {
		CurrType curr = EnumUtil.parseEnumByCode(CurrType.class, currCd);
		this.chnlMerAccService().adjust(chnlId, merId, opType, "0", curr.getCode(), curr.getDefaultUnit(), operator, note);
	}
	
	/**
	 * @param mer
	 */
	protected String resetForChnlAccount(String chnlId, String mchntCd, String currCd) {
		String opType = Constant.OPERTYPE._90 ;
		String memo = String.format("商戶: %s-%s, 币别: %s, 重置日累计额, 操作: %s", chnlId, mchntCd, currCd, opType);
		String taskResult="2";
		try {
			this.adjust(chnlId, mchntCd, currCd, opType, this.getTaskNm(), memo);
			taskResult="1";
		} catch (Exception e) {
			taskResult="2";
			error(e, "重置归零作业失败! 商戶:%s-%s, 币别: %s, 重置日累计额, 操作:%s",chnlId,  mchntCd, currCd, opType);
		}
		return taskResult;
	}
	
	@Override
	protected String getTaskNm() {
		return "重置商户日累计额";
	}
	
	ChnlMerAccService cmas=null;
	protected ChnlMerAccService chnlMerAccService(){
		if(cmas==null){
			cmas=ServiceProxy.getService(ChnlMerAccService.class);
		}
		return cmas;
	}	
}
