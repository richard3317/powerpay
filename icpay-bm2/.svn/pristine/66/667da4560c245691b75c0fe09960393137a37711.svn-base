package com.icpay.payment.bm.quartz.job;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.service.IMchntInfoService;

@Component("mchntExpiredDtCheckJob")
public class MchntExpiredDtCheckJob extends BaseQuartzJob {
	
	private static final Logger logger = Logger.getLogger(MchntExpiredDtCheckJob.class);

	@Override
	public void doProcess() {
		String today = DateUtil.now8();
		// 查找数据库中状态是有效，且失效日期小于当前日期的记录，并置为失效
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("mchntSt", CommonEnums.MchntSt._1.getCode());
		logger.info("失效日期检查截止日期:" + today);
		qryParamMap.put("expiredEndDt", today);
		IMchntInfoService mchntService = DBHessionServiceClient.getService(IMchntInfoService.class);
		List<MchntInfo> lst = mchntService.select(qryParamMap);
		logger.info("自动失效商户数:" + lst.size());
		Set<String> st = new LinkedHashSet<String>();
		for (MchntInfo m : lst) {
			try {
				// 将商户状态置为2-失效，并保存
				m.setMchntSt(CommonEnums.MchntSt._2.getCode());
				mchntService.update(m);
				st.add(m.getMchntCd());
			} catch (Exception e) {
				logger.error("更新商户状态出错:" + m.getMchntCd(), e);
			}
		}
		
		StringBuilder opContent = new StringBuilder();
		if (st.isEmpty()) {
			opContent.append("无已过失效日期的商户");
		} else {
			opContent.append("如下商户已过失效日期，被自动\"禁用\":" + StringUtil.concatSet(",", st));
		}
		
		// 记录操作日志
		operLogBO.log(CommonEnums.SysInfo.CONSOLE, "system", this.getJobDesc(), 
				CommonEnums.OpType.QUARTZ_JOB_RUNNING, "system", opContent.toString());
	}

	@Override
	protected String getJobDesc() {
		return "商户失效日期检查任务";
	}
	
}
