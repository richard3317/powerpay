package com.icpay.payment.batch.task.transfer.chnl;

import java.text.ParseException;
import java.util.List;
import org.springframework.stereotype.Component;
import com.icpay.payment.batch.task.transfer.BalanceTransferBaseTask;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.constants.Constant.ACC_SUBTYPE;
import com.icpay.payment.common.data.model.BalanceTransferFotT1;
import com.icpay.payment.common.data.model.ChnlMchntInfo;
import com.icpay.payment.common.data.model.ChnlMchntSettlePolicy;
import com.icpay.payment.common.enums.CurrencyEnums.CurrType;
import com.icpay.payment.common.utils.BizUtils;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.service.ChnlMerAccService;
import com.icpay.payment.service.MerParams;

/**
 * TODO : 结转功能需改为多币别支持
 */
@Component("T1BalanceTransferChnlTask")
public class T1BalanceTransferChnlTask extends BalanceTransferBaseTask {

//	@Override
//	protected void doTask() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	protected String getTaskNm() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	@Override
	protected void doTask() {
		//查询渠道编号
		List<String> list = getChnlList();
			if(list != null && list.size() > 0) {
				for(String chnlId : list) {//循环渠道编号
					try {
						exe(chnlId);
					} catch (Exception e) {
						e.printStackTrace();
						this.error(e, "结转任务失败! "+e.getMessage());
					}
				}
			}
	}
	
	public void exe(String chnlId) throws NumberFormatException, ParseException {
		for (CurrType currency :CurrType.values())
			doTaskForCurrency(chnlId, currency);
	}

	private void doTaskForCurrency(String chnlId, CurrType currency) {
		boolean isHolidays =this.isHolidays();//是否是节假日
		
		//获取默认结转时间
		String defaultTaskTime = MerParams.getParam(chnlId,DEFAULT_MER,TRANSFER_CAT ,TRANSFER_T1TIME, "160000");
		String taskTime;
		String transMode="0"; //结转模式: 0=默认, 1=AB商户模式
		String mchntCd;
		String mchntSuffix;
			
		
		List<ChnlMchntSettlePolicy> merList = queryChnlMerSettlePolicy(chnlId, isHolidays, currency.getCode());
		if (merList==null) return;
		//debug("渠道[%s] T1馀额结转任务，共计 %s 个商户开始进行T1结转。。。", chnlId, merList.size());
		for (ChnlMchntSettlePolicy mp : merList) {
			taskTime = mp.getTransferTimeT1();
			if (Utils.isEmpty(taskTime)) taskTime = defaultTaskTime;
			if (! this.shouldRunTask(taskTime, mp.getChnlId(), mp.getChnlMchntCd())) continue; //非执行时间
			
			transMode = mp.getTransferMode();
			mchntSuffix= getMchntSuffix(mp);
			if (Utils.isEmpty(mchntSuffix))
				mchntSuffix = BizUtils.Default_BMchnt_Suffix;		
			mchntCd = mp.getChnlMchntCd();

			//查询需要T1结转的纪录
			BalanceTransferFotT1 rec =  gueryForT1Target(this.batchDt,  chnlId, mp.getChnlMchntCd(), currency.getCode());
			if (rec==null) continue;
			
			if ("1".equals(transMode) && (! mchntCd.endsWith(mchntSuffix))) { //结转模式: 0=默认, 1=AB商户模式
				transferAbMode(rec, mchntSuffix, currency);
			}
			else {
				transferDefault(rec, currency);
			}
			
//			String opType = Constant.OPERTYPE._52 ;
//			String mchnt = rec.getMchntCd();
//			String t1Amount =  ""+rec.getTargetT1Amt();
//			String memo = String.format("商戶:%s-%s, 结转金额: %s, 操作: %s", chnlId, mchnt, t1Amount, opType);
//			info(memo);
//			String taskResult="";
//			try {
//				this.chnlMerAccService().adjust(chnlId, mchnt, opType, t1Amount, this.getTaskNm(), memo);
//				taskResult="1";
//			} catch (Exception e) {
//				taskResult="2";
//				error(e, "T1结转任务发生失败! 商戶:%s-%s, 结转金额:%s, 操作:%s", chnlId, mchnt, t1Amount, opType);
//			}
//			//插入余额结转结果表
//			info("插入余额结转结果表，执行结果：" + taskResult);
//			updateBalanceTransferT1Result(chnlId, mchnt, t1Amount, taskResult, this.getTaskNm(), opType);
		}
	}
	
	protected void transferAbMode(BalanceTransferFotT1 rec, String mchntSuffix, CurrType currency) {
		String chnlId = rec.getTransChnl();
		
		//查询需要T1结转的纪录
		//String opType = Constant.OPERTYPE._52 ;
		String mchnt = rec.getMchntCd();
		String t1Amount = ""+rec.getTargetT1Amt();
		
//		String mchntSuffix= merInfo.getMchntSuffix();
//		if (Utils.isEmpty(mchntSuffix))
//			mchntSuffix = BizUtils.Default_BMchnt_Suffix;
		
		String mchntTo=mchnt+mchntSuffix;
		
		String memo = String.format("AB商戶:%s-%s/%s, 结转金额: %s", chnlId, mchnt, mchntTo, t1Amount);
		info(memo);
		String taskResult="";
		try {
			//this.chnlMerAccService().adjust(chnlId, mchnt, opType, t1Amount, this.getTaskNm(), memo);
			this.chnlMerAccService().transfer(chnlId, mchnt, ACC_SUBTYPE.B1, mchntTo, ACC_SUBTYPE.B0, t1Amount, currency.getCode(), currency.getDefaultUnit(), this.getTaskNm(), "AB商户结转");
			taskResult="1";
		} catch (Exception e) {
			taskResult="2";
			error(e, "T1结转任务发生失败! AB商戶:%s-%s/%s, 结转金额:%s, 操作:%s", chnlId, mchnt, mchntTo, t1Amount, "6x");
		}
		//插入余额结转结果表
		info("%s，执行结果：%s", memo, taskResult);
		updateBalanceTransferT1Result(chnlId, mchnt, t1Amount,currency.getCode(), currency.getDefaultUnit(), taskResult, this.getTaskNm(), "6x");
		updateBalanceTransferT1Result(chnlId, mchnt, t1Amount,currency.getCode(), currency.getDefaultUnit(), taskResult, String.format("[%s] %s", this.getTaskNm(), memo), "6x");
		
	}

	protected void transferDefault(BalanceTransferFotT1 rec, CurrType currency) {
		String chnlId = rec.getTransChnl();
		//查询需要T1结转的纪录
		String opType = Constant.OPERTYPE._52 ;
		String mchnt = rec.getMchntCd();
		String t1Amount =  ""+rec.getTargetT1Amt();
		String memo = String.format("商戶:%s-%s, 结转金额: %s, 操作: %s", chnlId, mchnt, t1Amount, opType);
		info(memo);
		String taskResult="";
		try {
			this.chnlMerAccService().adjust(chnlId, mchnt, opType, t1Amount,currency.getCode(), currency.getDefaultUnit(), this.getTaskNm(), memo);
			taskResult="1";
		} catch (Exception e) {
			taskResult="2";
			error(e, "T1结转任务发生失败! 商戶:%s-%s, 结转金额:%s, 操作:%s", chnlId, mchnt, t1Amount, opType);
		}
		//插入余额结转结果表
		//info("插入余额结转结果表，执行结果：" + taskResult);
		info("%s，执行结果：%s", memo, taskResult);
		updateBalanceTransferT1Result(chnlId, mchnt, t1Amount,currency.getCode(), currency.getDefaultUnit(), taskResult, String.format("[%s] %s", this.getTaskNm(), memo), opType);
	}
	
	@Override
	protected String getTaskNm() {
		return "渠道T1余额结转任务";
	}
	
	ChnlMerAccService cmas=null;
	protected ChnlMerAccService chnlMerAccService(){
		if(cmas==null){
			cmas=ServiceProxy.getService(ChnlMerAccService.class);
		}
		return cmas;
	}
	
}

