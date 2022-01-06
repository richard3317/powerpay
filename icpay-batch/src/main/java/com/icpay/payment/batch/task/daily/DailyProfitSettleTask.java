package com.icpay.payment.batch.task.daily;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.BatchTaskTemplate;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.data.dao.ChnlMchntAccountInfoMapper;
import com.icpay.payment.common.data.dao.ChnlMchntInfoMapper;
import com.icpay.payment.common.data.dao.MerAccountInfoMapper;
import com.icpay.payment.common.data.dao.MerSettlePolicyMapper;
import com.icpay.payment.common.data.model.ChnlMchntAccountInfo;
import com.icpay.payment.common.data.model.ChnlMchntInfo;
import com.icpay.payment.common.data.model.ChnlMchntInfoExample;
import com.icpay.payment.common.data.model.MerAccountInfo;
import com.icpay.payment.common.data.model.MerSettlePolicy;
import com.icpay.payment.common.data.model.MerSettlePolicyExample;
import com.icpay.payment.common.data.svc.DAO;
import com.icpay.payment.common.data.svc.ProfitWithdrawService;
import com.icpay.payment.common.data.utils.TxnDataUtils;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.service.ChnlMerAccService;
import com.icpay.payment.service.MerParams;
import com.icpay.payment.service.ProfitWithdrawServiceImpl;

@Component("dailyProfitSettleTask")
public class DailyProfitSettleTask extends BatchTaskTemplate {
	
	
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
		//String taskTime = MerParams.getParam("00",Consts.DEFAULT_MER, Consts.CAT_DAILY_RESET , Consts.PNAME_TASK_TIME, "235000");
		String taskTime = 
			MerParams.getParam("00", Consts.DEFAULT_MER, Consts.CAT_DAILY, Consts.PNAME_PROFIT_SETTLE_TASK_TIME, "050000");
		String preDate=DateUtil.yesterday8();
		if (shouldRunTask(taskTime)) {
			info(String.format("结算分润， 参数: settleDate=%s; taskTime=%s", preDate, taskTime));
			getSvc().settleProfit(preDate);
			info("结算分润成功结束");
			
//			info(String.format("结算打款数据， 参数: settleDate=%s; ", preDate));
//			getSvc().settleProfitWithdrawData(preDate);
//			info("结算打款数据成功结束");
		}
	}

	@Override
	protected String getTaskNm() {
		
		return "每日分润结算";
	}
	
	ProfitWithdrawService svc=null;
	protected ProfitWithdrawService getSvc(){
		if(svc==null){
			//svc=ServiceProxy.getService(ProfitWithdrawService.class);
			svc = new ProfitWithdrawServiceImpl();
		}
		return svc;
	}
}
