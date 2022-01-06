package com.icpay.payment.bm.web.controller.business;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icpay.payment.bm.cache.BMConfigCache;
import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.bm.web.interceptor.QryMethod;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BizUtils;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.TransLog;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TransLogMapping;
import com.icpay.payment.db.service.ITransLogService;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.service.ChnlMerAccService;

@Deprecated
public abstract class TransLogControllerBase extends BaseController {
	
	protected abstract String getTransType();
	
	protected static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			BigDecimal transAt = new BigDecimal(m.get("transAt"));
			m.put("transAtDesc", transAt.movePointLeft(2).toString());

			//帐号加入隐码
			String accNo = m.get("accNo");
			if (StringUtil.isBlank(accNo)) {
				m.put("accNoDesc", "");
			} else {
				m.put("accNoDesc", BizUtils.strMask(accNo, "*", 4, 4, 6));
			}
			
			String accName = m.get("accName");
			if (StringUtil.isBlank(accName)) {
				m.put("accNameDesc", "");
			} else {
				m.put("accNameDesc", BizUtils.strMask(accName, "*", 1, 2, 0));
			}
			

			String intTransCd = m.get("intTransCd");
			m.put("intTransCdDesc", EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, true));

			String rspCd = m.get("rspCd");
			if (!Utils.isEmpty(rspCd)) {
				m.put("rspCdDesc", rspCd + "-" + m.get("errMsg"));
			} else {
				m.put("rspCdDesc", rspCd);
			}

			String txnState = m.get("txnState");
			
			if ("00".equals(txnState)) {
				m.put("stateDesc", "00-初始状态");
			} else if ("01".equals(txnState)) {
				m.put("stateDesc", "01-等待付款");
			} else if ("10".equals(txnState)) {
				m.put("stateDesc", "10-交易成功");
			} else if ("20".equals(txnState)) {
				m.put("stateDesc", "20-交易失败");
			} else {
				m.put("stateDesc", "30-其他");
			}
			
			//TODO Order State

			String transChnl = m.get("transChnl");
			m.put("transChnl", EnumUtil.translate(TxnEnums.ChnlId.class, transChnl, true));
			
			String transFee = m.get("transFee");
			m.put("transFeeDesc", StringUtil.formateAmt(transFee));

			String transFeeChnl = m.get("transFeeChnl");
			m.put("transFeeChnlDesc", StringUtil.formateAmt(transFeeChnl));

			//利润
			m.put("transFeeDeltaDesc", StringUtil.formateAmt(sub(transFee, transFeeChnl)));
			
			String settleType = m.get("settleType");
			if("0".equals(settleType)){
				m.put("settleTypeDesc","T+0" );
			}else if("1".equals(settleType)){
				m.put("settleTypeDesc","T+1" );
			}
		}
	};
	
	protected static Long add(Long a, Long b) {
		if (b==null) return a;
		if (a==null) return b;
		return a+b;
	}
	
	protected static Long sub(String a, String b) {
		return sub(parseLong(a), parseLong(b));
	}
	
	protected static Long sub(Long a, Long b) {
		if (b==null) return a;
		if (a==null) return b;
		return a-b;
	}
	
	protected static Long add(String a, String b) {
		return add(parseLong(a), parseLong(b));
	}
	
	protected static Long parseLong(String s) {
		if (Utils.isEmpty(s)) return null;
		return Long.parseLong(s);
	}
	
}
