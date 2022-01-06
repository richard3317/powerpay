package com.icpay.payment.db.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.db.dao.mybatis.mapper.ExtMapper.ProfitRptInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.TransProfitReport;
import com.icpay.payment.db.service.IProfitRptInfoService;

@Service("pofitRptInfoService")
public class ProfitRptInfoService extends BaseService implements IProfitRptInfoService {

	@Override
	public List<TransProfitReport> selectProfit(Map<String,String> map) {
		ProfitRptInfoMapper mapper = getMapper();
		List<TransProfitReport> respList = mapper.selectProfitRpt(map);
		return respList;
	}
	
	private ProfitRptInfoMapper getMapper() {
		return super.getMapper(ProfitRptInfoMapper.class);
	}

	
}
