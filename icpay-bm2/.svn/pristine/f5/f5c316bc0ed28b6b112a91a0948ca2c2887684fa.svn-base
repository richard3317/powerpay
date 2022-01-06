package com.icpay.payment.bm.web.util;

import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.enums.AjaxRespEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.exception.ChnlBizException;

public abstract class AjaxMethod {
	
	abstract void onExecute();
	
	public AjaxResult run() {
		AjaxResult rst = new AjaxResult();
		try {
			onExecute();
			rst.setRespCode(AjaxRespEnums.SUCC.getRespCode());
			rst.setRespMsg(AjaxRespEnums.SUCC.getRespMsg());
			return rst;
		} catch (ChnlBizException e) {
			rst.setRespCode(e.getErrorCode());
			rst.setRespMsg(e.getErrorMsg());
			return rst;
		} catch (BizzException e) {
			rst.setRespCode(e.getErrorCode());
			rst.setRespMsg(e.getErrorMsg());
			return rst;
		} catch (Exception e) {
			rst.setRespCode(AjaxRespEnums.ERROR.getRespCode());
			rst.setRespMsg(e.getMessage());
			return rst;
		}
	}
	
}
