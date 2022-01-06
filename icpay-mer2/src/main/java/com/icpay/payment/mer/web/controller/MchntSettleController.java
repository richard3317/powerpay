package com.icpay.payment.mer.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.SettleEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.FileUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.MerSettleTaskLog;
import com.icpay.payment.mer.bo.MchntSettleBO;
import com.icpay.payment.mer.constants.MerConstants;
import com.icpay.payment.mer.util.I18nMsgUtils;

@Controller
@RequestMapping("/mchntSettle")
public class MchntSettleController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(MchntSettleController.class);
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			String transAt = m.get("transAt");
			m.put("transAtDesc", StringUtil.formateAmt(transAt));
			
			String settleAt = m.get("settleAt");
			m.put("settleAtDesc", StringUtil.formateAmt(settleAt));
			
			String feeAt = m.get("feeAt");
			m.put("feeAtDesc", StringUtil.formateAmt(feeAt));
		}
	};

	@Autowired
	private MchntSettleBO mchntSettleBO;
	
	@RequestMapping(value="/settleMng", method=RequestMethod.GET)
	public String acctMng(Model model) {
		return "settleMng";
	}

	@RequestMapping(value="/qrySettleLog", method=RequestMethod.POST)
	public @ResponseBody AjaxResult qrySettleLog(int pageNum, int pageSize, 
			String settleDt, String settleBt,HttpServletRequest req) {
		logger.info("查询商户清算记录开始..");
		Map<String, String> qryParams = new HashMap<String, String>();
		qryParams.put("mchntCd", commonBO.getMchntCd());
		qryParams.put("stateLst", SettleEnums.SettleTaskState._2.getCode() + "," + SettleEnums.SettleTaskState._3.getCode()); // 只查询已经生成清算文件的记录,2或3
		qryParams.put("settleDt", settleDt);
		qryParams.put("settleBt", settleBt);
		Pager<MerSettleTaskLog> pager = mchntSettleBO.qrySettleLogLst(pageNum, pageSize, qryParams);
		logger.info("查询商户清算记录完成..");
		return commonBO.buildSuccResp(MerConstants.PAGER_RESULT, 
				commonBO.transferPager(pager, MerConstants.PAGE_CONF_MERSETTLETASKLOG, ENTITY_TRANSFER , req));
	}
	
	@RequestMapping(value="/downStlFile", method=RequestMethod.GET)
	public String downStlFile(Model model, int seqId, HttpServletResponse response, HttpServletRequest req) {
        String lang = I18nMsgUtils.getLanguage(req);
		MerSettleTaskLog log = mchntSettleBO.getSettleTaskLog(seqId);
		AssertUtil.objIsNull(log, mappingI18n(this.getClass().getSimpleName(),"清算记录不存在：", lang) + seqId);
		AssertUtil.strNotEquals(log.getMchntCd(), commonBO.getMchntCd(),mappingI18n(this.getClass().getSimpleName(), "该记录不属于当前商户:", lang)+ seqId);
		if (!SettleEnums.SettleTaskState._2.getCode().equals(log.getState())
				&& !SettleEnums.SettleTaskState._3.getCode().equals(log.getState())) {
			throw new BizzException(mappingI18n(this.getClass().getSimpleName(),"改清算记录状态有误", lang));
		}
		String filePath = log.getFilePath();
		logger.info("下载清算文件开始:" + filePath);
		commonBO.downSettleFile(filePath, FileUtil.getFileName(filePath), Constant.UTF8, response);
		logger.info("下载清算文件完成:" + filePath);
		return null;
	}
}
