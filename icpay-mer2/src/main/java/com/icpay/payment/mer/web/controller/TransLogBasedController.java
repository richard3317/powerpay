package com.icpay.payment.mer.web.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.exception.SystemException;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.TransLog;
import com.icpay.payment.mer.bo.TransBO;
import com.icpay.payment.service.api.HttpClientResponse;
import com.icpay.payment.service.client.InternalGatewayClient;

public class TransLogBasedController extends BaseController {
	
	protected void exportData(TransBO transBO, String mon, Map<String, String> qryParams, 
			IEntityTransfer transfer, String pageConfig, 
			String filePath, String fileName, String charSet,
			HttpServletResponse response, HttpServletRequest req) {
		List<TransLog> list = transBO.qryTransList(mon.substring(4,6), qryParams);
		Pager<Map<String, String>> pager = commonBO.transferList(list, pageConfig, transfer, req);
		pager = commonBO.transferResultListByLang(pager, req); //再将某些栏位的查询结果，做多国语的转换
		commonBO.exportAccflowExcel(pager,filePath, fileName, Constant.UTF8, response);
	}

	protected HttpClientResponse resendNotifyToMer(String txnId, String orderDate) {
		this.getLogger().info(String.format("[resendNotifyToMer] 补发下游通知， 参数: %s, %s; ", txnId, orderDate));
		if (Utils.isEmpty(orderDate)) {
			orderDate= (txnId+"010101").substring(4,6);
		}
		try {
			HttpClientResponse hr = InternalGatewayClient.resendNotifyToMer(txnId, orderDate, false);
			if (hr!=null)
				this.info("[resendNotifyToMer] 补发通知成功! 商户响应: [%s] %s", hr.getCode(), hr.getBody());
			else
				this.info("[resendNotifyToMer] 补发通知请求成功! 已推送后台背景发送！");
			return hr;
		} catch (SystemException e) {
			this.error(e.getMessage());
		}
		return null;
	}
	
	protected void checkNotifiUrl(String notifyUrl) {
		if (isNoopUrl(notifyUrl))
			throw new BizzException("商户平台发起的交易无法回调！");
		
		if (!isValidNotifiUrl(notifyUrl))
			throw new BizzException("不正确的回调地址！");
	}
	
	protected boolean isNoopUrl(String notifyUrl) {
		if (Utils.isEmpty(notifyUrl)) return true;
		if (Constant.isNoopUrl(notifyUrl)) return true;
		if (Constant.INTERNAL_IP.equals(notifyUrl)) return true;
		return false;
	}
	
	protected boolean isValidNotifiUrl(String notifyUrl) {
		if (Utils.isEmpty(notifyUrl)) return false;
		if (Constant.INTERNAL_IP.equals(notifyUrl)) return false;
		if (Constant.isNoopUrl(notifyUrl)) return false;
		try {
			URI uri = new URI(notifyUrl);
			if (!uri.isAbsolute()) return false;
			if ("127.0.0.1".equals(uri.getHost())) return false;
			if ("localhost".equals(uri.getHost())) return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
