package com.icpay.payment.batch.quartz.job;

import org.springframework.stereotype.Component;

import com.icpay.payment.batch.task.IBatchTask;
import com.icpay.payment.common.constants.Constant.INTER_MSG;
import com.icpay.payment.common.constants.Constant.MSG;
import com.icpay.payment.common.enums.BatchEnums;
import com.icpay.payment.common.utils.ApplicationContextUtil;
import com.icpay.payment.common.utils.DateUtil;

@Component("settleT0ResCreateJob")
public class SettleT0ResCreateJob extends BaseQuartzJob {
	
	@Override
	public void doProcess() {
		String settleBatch = String.valueOf(Integer.parseInt(DateUtil.now17()
				.substring(9, 11)) / 2);
		if (settleBatch.equals("0")) {
			settleBatch = "12";
		}

		String batchDt = DateUtil.now8();
		if (settleBatch.equals("12")) {
			batchDt = DateUtil.preDay(DateUtil.parseDate8(batchDt));
		}
		
		IBatchTask bo = ApplicationContextUtil.getBean("settleT0ResCreateTask");
		bo.setParam(MSG.settleDate,batchDt);
		bo.setParam(INTER_MSG.settleBatch,settleBatch);
		bo.exec(batchDt, BatchEnums.RunningMode._0);
	}

	@Override
	protected String getJobDesc() {
		return "T+0划款文件创建任务";
	}
	
}
