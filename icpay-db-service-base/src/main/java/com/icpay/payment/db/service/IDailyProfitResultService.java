package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.DailyProfitResult;

public interface IDailyProfitResultService {

	public Pager<DailyProfitResult> selectByPage(int pageNum, int pageSize,Map<String, String> qryParamMap);
	public List<DailyProfitResult> select(Map<String, String> qryParamMap);
	
}
