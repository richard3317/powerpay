package com.icpay.payment.bm.bo;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.enums.CommonEnums;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.JsonUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.BmOperLog;
import com.icpay.payment.db.service.IBmOperLogService;

@Service("operLogBO")
public class OperLogBO {
	
	private static final Logger logger = Logger.getLogger(OperLogBO.class);
	private static final String OP_CONTENT_TP_0 = "0";

	public void log(CommonEnums.SysInfo sysInfo, String operInfo, String opFuncInfo, CommonEnums.OpType opTp, String opIp, String opContent) {
		AssertUtil.objIsNull(sysInfo, "所属系统为null");
		AssertUtil.strIsBlank(operInfo, "操作员信息为空");
		AssertUtil.objIsNull(opFuncInfo, "功能权限信息为空");
		AssertUtil.objIsNull(opTp, "操作类型为null");
		AssertUtil.strIsBlank(opIp, "操作员IP为空");
		AssertUtil.strIsBlank(opContent, "操作内容为空");
		
		try {
			BmOperLog log = new BmOperLog();
			log.setSysId(sysInfo.getCode());
			log.setOperInfo(operInfo);
			log.setOpFuncInfo(opFuncInfo);
			log.setOpTp(opTp.getCode());
			Date now = new Date();
			log.setOpDt(DateUtil.dateToStr8(now));
			log.setOpTm(DateUtil.dateToStr19(now));
			log.setOpIp(opIp);
			log.setOpContentTp(OP_CONTENT_TP_0);
			log.setOpContent(opContent);
			IBmOperLogService serivce = DBHessionServiceClient.getService(IBmOperLogService.class);
			serivce.add(log);
		} catch (Exception e) {
			String msg = StringUtil.concat("_", sysInfo.getCode(), operInfo, opFuncInfo, opTp.getCode(), opIp, opContent);
			logger.error("记录操作日志失败:" + StringUtil.concat("_", msg));
		}
	}
	
	public void log(CommonEnums.SysInfo sysInfo, String operInfo, String opFuncInfo, CommonEnums.OpType opTp, String opIp, Object opEntity) {
		AssertUtil.objIsNull(opEntity, "操作对象为null");
		this.log(sysInfo, operInfo, opFuncInfo, opTp, opIp, JsonUtil.toJson(opEntity));
	}
	
	public void log(CommonEnums.SysInfo sysInfo, String operInfo, String opFuncInfo, CommonEnums.OpType opTp, String opIp, 
			Object oldEntity, Object newEntity) {
		AssertUtil.objIsNull(oldEntity, "操作对象为null");
		AssertUtil.objIsNull(newEntity, "操作对象为null");
		Map<String, Object> m = new LinkedHashMap<String, Object>();
		m.put("oldObj", oldEntity);
		m.put("newObj", newEntity);
		this.log(sysInfo, operInfo, opFuncInfo, opTp, opIp, JsonUtil.toJson(m));
	}
}
