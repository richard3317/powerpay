package com.icpay.payment.mer.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.icpay.payment.db.service.IMerParamsService;
import com.icpay.payment.db.dao.mybatis.model.MerParams;

@Component("merParamsBo")
public class MerParamsBo extends BaseBO {
	
	
	public List<MerParams> getSysParams() {
		IMerParamsService sysParam = this.getDBService(IMerParamsService.class);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("mchnt_cd", "#LAN#");
		List<MerParams>  sysParamList = sysParam.select(qryParamMap);
		return sysParamList;
	}
}
