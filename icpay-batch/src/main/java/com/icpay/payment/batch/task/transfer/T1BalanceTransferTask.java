package com.icpay.payment.batch.task.transfer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.data.dao.ChnlMchntInfoMapper;
import com.icpay.payment.common.data.dao.MerSettlePolicyMapper;
import com.icpay.payment.common.data.model.BalanceTransferFotT1;
import com.icpay.payment.common.data.model.ChnlMchntInfo;
import com.icpay.payment.common.data.model.ChnlMchntInfoExample;
import com.icpay.payment.common.data.model.MerSettlePolicy;
import com.icpay.payment.common.data.model.MerSettlePolicyExample;
import com.icpay.payment.common.data.svc.DAO;
import com.icpay.payment.common.data.utils.TxnDataUtils;
import com.icpay.payment.common.enums.CurrencyEnums.CurrType;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.service.MerParams;

/**
 * TODO : 结转功能需改为多币别支持
 */
@Component("T1BalanceTransferTask")
public class T1BalanceTransferTask extends BalanceTransferBaseTask {

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
		String defaultTaskTime =
				MerParams.getParam("00",DEFAULT_MER, TRANSFER_CAT , TRANSFER_T1TIME, "160000");
		
		//CurrType currency=CurrType._156;
		for (CurrType currency :CurrType.values())
			doTaskForCurrency(defaultTaskTime, currency);
	}

	private void doTaskForCurrency(String defaultTaskTime, CurrType currency) {
		String taskTime;
		boolean isHolidays =this.isHolidays();//是否是节假日
		List<MerSettlePolicy> merList = queryMerSettlePolicy(isHolidays, currency.getCode());
		
		if (! TxnDataUtils.hasRecord(merList)) {
			info("渠道：%s 日期：%s 币别：%s， 任務： %s， 无任何商户符合執行条件! ", "00", this.batchDt, currency.getCode(), this.getTaskNm());
			return;
		}
		else {
			debug("渠道：%s 日期：%s 币别：%s， 任務：%s 開始。。。", "00", this.batchDt, currency.getCode(), this.getTaskNm());
		}
		
		//if (merList==null) return;
		//debug("T1馀额结转任务，共计 %s 个商户开始进行T1结转。。。", merList.size());
		
		for (MerSettlePolicy mp : merList) {
			taskTime = mp.getTransferTimeT1();
			if (Utils.isEmpty(taskTime)) taskTime = defaultTaskTime;
			if (! this.shouldRunTask(taskTime, "00", mp.getMchntCd())) continue; //非执行时间
			
			//查询需要T1结转的纪录
			BalanceTransferFotT1 rec =  gueryForT1Target(this.batchDt, "00", mp.getMchntCd(), currency.getCode());
			if (rec==null) continue;
			
			String opType = Constant.OPERTYPE._52 ;
			String chnlId = rec.getTransChnl();
			String mchnt = rec.getMchntCd();
			String t1Amount =  ""+rec.getTargetT1Amt();
			String memo = String.format("商戶: %s, 结转金额: %s, 操作: %s", mchnt, t1Amount, opType);
			info(memo);
			String taskResult="";
			try {
				this.chnlMerAccService().adjust(chnlId, mchnt, opType, t1Amount, currency.getCode(), currency.getDefaultUnit(), this.getTaskNm(), memo);
				taskResult="1";
			} catch (Exception e) {
				taskResult="2";
				error(e, "T1结转任务发生失败! 商戶:%s, 结转金额:%s, 操作:%s", mchnt, t1Amount, opType);
			}
			//插入余额结转结果表
			info("插入余额结转结果表，执行结果：" + taskResult);
			updateBalanceTransferT1Result(chnlId, mchnt, t1Amount, currency.getCode(), currency.getDefaultUnit(), taskResult, this.getTaskNm(), opType);
		}
	}

	@Override
	protected String getTaskNm() {
		return "商户T1结转任务";
	}
	
}

