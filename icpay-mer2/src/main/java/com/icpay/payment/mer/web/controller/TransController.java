package com.icpay.payment.mer.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.model.GenericResult;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.constants.Constant.TxnSource;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.MerEnums.MerUserRole;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.enums.TxnEnums.TxnType;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.BizUtils;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.TransLog;
import com.icpay.payment.db.dao.mybatis.model.TransProof;
import com.icpay.payment.db.dao.mybatis.model.TxnLogView;
import com.icpay.payment.db.service.ITransProofService;
import com.icpay.payment.mer.bo.TransBO;
import com.icpay.payment.mer.cache.MerConfigCache;
import com.icpay.payment.mer.cache.PageConfCache;
import com.icpay.payment.mer.constants.MerConstants;
import com.icpay.payment.mer.util.I18nMsgUtils;
import com.icpay.payment.mer.web.interceptor.RolesSessionCheck;
import com.icpay.payment.service.api.HttpClientResponse;
import com.icpay.payment.service.oss.OssUtils;
import com.icpay.payment.service.oss.SimpleProgressListener;

@Controller
@RequestMapping("/trans")
public class TransController extends TransLogBasedController {
	
	private static final Logger logger = Logger.getLogger(TransController.class);
	private static final IEntityTransfer ENTITY_TRANSFER = new IEntityTransfer() {
		@Override
		public void transfer(Map<String, String> m) {
			BigDecimal transAt = new BigDecimal(m.get("transAt"));
			m.put("transAtDesc", transAt.movePointLeft(2).toString());
			
			BigDecimal transFee = null;
			if(StringUtil.isNotBlank(m.get("transFee"))) {
				 transFee = new BigDecimal(m.get("transFee"));
				 m.put("transFeeDesc", transFee.movePointLeft(2).toString());
			}else {
				m.put("transFeeDesc","0.00");
			}
			
			String accNo = m.get("accNo");
			if (StringUtil.isBlank(accNo)) {
				m.put("accNoDesc", "");
			} else {
//				m.put("accNoDesc", accNo.length() > 7 ? 
//						StringUtil.mask(accNo, 3, accNo.length() - 4, '*') : accNo);
				m.put("accNoDesc", BizUtils.strMask(accNo, "*", 4, 0, 6));
			}
			
			String intTransCd = m.get("intTransCd");
			m.put("intTransCdDesc", EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, false));
			
//			String payType = m.get("payType");
//			if (!StringUtil.isBlank(payType)) {
//				m.put("payTypeDesc", EnumUtil.translate(TxnEnums.PaymentType.class, payType, false));
//			}
			String rspCd = m.get("rspCd");
			if (!StringUtil.isBlank(rspCd)) {
				m.put("rspCdDesc", rspCd + "-" + m.get("errMsg"));
			} else {
				m.put("rspCdDesc", rspCd);
			}

			String txnState = m.get("txnState");
			if (!StringUtil.isBlank(txnState)) {
				m.put("txnStateMsg", EnumUtil.translate(TxnEnums.TxnStatus.class, txnState, false));
			}
			String extTransDt = m.get("extTransDt");
			String extTransTm = m.get("extTransTm");
			m.put("transTm", extTransDt + " " + extTransTm);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//日期原始格式  
			String origFormat = "yyyyMMdd HHmmss";  
			DateFormat format = new SimpleDateFormat(origFormat);
			Date date;
			try {
				date = format.parse(m.get("transTm"));
				m.put("transTm", sdf.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
				logger.error(e);
			}
			
			String txnSrc = m.get("txnSrc");
			m.put("txnSrcDesc", EnumUtil.translate(TxnSource.class, txnSrc, true));
			
		}
	};

	@Autowired
	private TransBO transBO;
	
	@RequestMapping("/transMng")
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.PaymentUser,MerUserRole.SuperUser})
	public String transMng(Model model) {
//		model.addAttribute("today", DateUtil.now8());
		model.addAttribute("defaultStart", DateUtil.now8() + " 000000");
		model.addAttribute("defaultEnd", DateUtil.now8() + " 235959");
		String mchntCd =commonBO.getMchntCd();
		String secretFlag ="true";
		if(!commonBO.validateSecret(mchntCd)) {
			secretFlag ="false";
		}
		model.addAttribute("secretFlag", secretFlag);
		model.addAttribute("loginType", commonBO.getLoginType());
		model.addAttribute("payTypes", this.getPayTypes(mchntCd));//交易类型
		return "transMng";
	}
	/**
	 * 查询交易
	 */
	@RequestMapping("/transQry")
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.PaymentUser,MerUserRole.SuperUser})
	public @ResponseBody AjaxResult transQry(int pageNum, int pageSize, String startDt, String endDt,
			String startTm, String endTm, String transSeqId, String orderId, String transAt,
			String transState, String userId, String txnSrc, String transType, String currCd, HttpServletRequest req) {
//		AssertUtil.notMonStr(mon.substring(4));
		AssertUtil.strIsBlank(startDt, "startDt is blank.");
		AssertUtil.strIsBlank(startDt, "startDt is blank.");
		Map<String, String> qryParams = new HashMap<String, String>();
		qryParams.put("mchntCd", commonBO.getMchntCd());
		qryParams.put("transSeqId", transSeqId);
		qryParams.put("orderId", orderId);
		qryParams.put("transAt", transAt);
//		qryParams.put("startDate", startDt);
//		qryParams.put("endDate", endDt);
//		qryParams.put("startTime", startTm);//起始时间
//		qryParams.put("endTime", endTm);//结束时间
		String mon = StringUtil.left(startDt, 8);
		qryParams.put("startDate", StringUtil.left(startDt, 8));
		qryParams.put("endDate", StringUtil.left(endDt, 8));
		qryParams.put("startTime", StringUtil.right(startDt, 6));//起始时间
		qryParams.put("endTime", StringUtil.right(endDt, 6)); // 结束时间
		qryParams.put("transType", Utils.isEmpty(transType) ? "01":transType);//交易类型
		qryParams.put("txnState", transState);//交易状态
		qryParams.put("userId", userId);
		qryParams.put("txnSrc", txnSrc);//交易来源
		qryParams.put("currCd", currCd);//币别
		logger.info("交易查询开始:" + BeanUtil.desc(qryParams, null, null));
		Pager<TxnLogView> p = transBO.qryTrans(pageNum, pageSize, mon, qryParams);
		Pager<Map<String, String>> transferPager = commonBO.transferPager(p, MerConstants.PAGE_CONF_TXNLOGVIEW, ENTITY_TRANSFER,req);
		transferPager = commonBO.transferResultListByLang(transferPager, req); //再将某些栏位的查询结果，做多国语的转换
		return commonBO.buildSuccResp(MerConstants.PAGER_RESULT, transferPager);
	}
	/**
	 * 查询交易流水明细
	 */
	@RequestMapping("/transFlowDetail")
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.PaymentUser,MerUserRole.SuperUser})
	public String transFlowDetail(Model model, String transSeqId, String mon , HttpServletRequest req) {
//		AssertUtil.notMonStr(mon.substring(4));
		AssertUtil.strIsBlank(transSeqId, "transSeqId is blank.");
		AssertUtil.strIsBlank(mon, "mon is blank.");
//		mon = DateUtils.formatDateStr(mon,DateUtils.DATE_FORMAT_10, DateUtils.DATE_FORMAT_8);
		TxnLogView log = transBO.getTxnLogViewFlow(transSeqId, mon);
		AssertUtil.objIsNull(log, "未找到交易明细:" + transSeqId);
		
		//查询汇款凭证
		ITransProofService proofSvc = this.getService(ITransProofService.class);
		TransProof proof = proofSvc.selectByPrimaryKey(transSeqId);
		if ("0191".equals(log.getIntTransCd()) && "01".equals(log.getTxnState()) && proof == null) {//0191、交易状态处理中、未上传凭证
			model.addAttribute("proof", "noImage");
		} else {
			model.addAttribute("proof", "");
		}
		model.addAttribute(MerConstants.DETAIL_CONF_LST, 
				PageConfCache.getDetailConfLst(MerConstants.PAGE_CONF_TXNLOGVIEW,I18nMsgUtils.getLanguage(req)));
		model.addAttribute(MerConstants.ENTITY_RESULT, 
				commonBO.transferEntity(log, MerConstants.PAGE_CONF_TXNLOGVIEW, ENTITY_TRANSFER, I18nMsgUtils.getLanguage(req)));
		return "transFlowDetail";
	}
	
	/**
	 * 导出
	 */
	@RequestMapping("/export")  //导出
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.PaymentUser,MerUserRole.SuperUser})
	public @ResponseBody AjaxResult exportLog(String startDt, String endDt,String transType,
			String transSeqId, String orderId, String transAt ,String userId,String transState, String currCd, HttpServletResponse response, HttpServletRequest req) {
//		AssertUtil.notMonStr(mon.substring(4));
		AssertUtil.strIsBlank(startDt, "startDt is blank.");
		//AssertUtil.strIsBlank(startDt, "startDt is blank.");
		Map<String, String> qryParams = new HashMap<String, String>();
		String mchntCd = commonBO.getMchntCd();
		qryParams.put("mchntCd", mchntCd);
		qryParams.put("transSeqId", transSeqId);
		qryParams.put("orderId", orderId);
		qryParams.put("transAt", transAt);
//		qryParams.put("startExtTransDt", startDt);
//		qryParams.put("endExtTransDt", endDt);
		String mon = StringUtil.left(startDt, 8);
		qryParams.put("startExtTransDt", StringUtil.left(startDt, 8));
		qryParams.put("endExtTransDt", StringUtil.left(endDt, 8));
		qryParams.put("transState", transState);
		qryParams.put("userId", userId);
		qryParams.put("transType", Utils.isEmpty(transType) ? "01":transType);//交易类型
		qryParams.put("ifPay", "1");
		qryParams.put("currCd", currCd);
		logger.info("交易查询开始:" + BeanUtil.desc(qryParams, null, null));
		String fileNm = mchntCd+Utils.getRandomInt(1000, 9999)+MerConfigCache.getConfig(MerConstants.CONFIG_KEY_TRANS_EXPORT_FILENM);
		String filePath = MerConfigCache.getConfig(MerConstants.CONFIG_KEY_TRANSLOG_EXPORT_FILE_PATH);
		//List<TransLog> list = transBO.qryTransList(mon.substring(4), qryParams);
		//commonBO.exportTransLogExcel(list,filePath, fileNm, Constant.UTF8, response);
		this.exportData(transBO, mon, qryParams, ENTITY_TRANSFER, MerConstants.PAGE_CONF_TRANSLOGEXP, filePath, fileNm, Constant.UTF8, response, req);
		return null;
	}
	
	/**
	 * 重发通知
	 * @param transSeqId
	 * @param mon
	 * @param response
	 * @return
	 */
	@RequestMapping("/renotify.do")  
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.PaymentUser,MerUserRole.SuperUser})
	public @ResponseBody AjaxResult resendNotify(String transSeqId, String mon, HttpServletResponse response, HttpServletRequest request) {
		
        String lang = I18nMsgUtils.getLanguage(request);
		String msg="";
		try {
			AssertUtil.notMonStr(mon.substring(4,6));
			AssertUtil.strIsBlank(transSeqId, mappingI18n(this.getClass().getSimpleName(),"交易编号为空", lang));
			TransLog log = transBO.getTransFlow(transSeqId, mon.substring(4,6));
			AssertUtil.objIsNull(log, mappingI18n(this.getClass().getSimpleName(),"未找到交易明细:", lang)+ transSeqId);
			String notifyUrl = log.getMchntBackUrl();
			checkNotifiUrl(notifyUrl);
			
			HttpClientResponse hr = super.resendNotifyToMer(transSeqId, mon);
			if (hr!=null) {
				msg = String.format(mappingI18n(this.getClass().getSimpleName(),"交易 %s 补发通知成功!\n通知地址: '%s' \n商户平台响应: [%s] %s", lang), transSeqId, notifyUrl, hr.getCode(), hr.getBody());
				return commonBO.buildSuccResp(msg);
			}
			else {
				msg = String.format(mappingI18n(this.getClass().getSimpleName(),"交易 %s 补发通知异常! \n通知地址: '%s'", lang), transSeqId, notifyUrl);
				this.error("[resendNotify] "+msg);
			}
		} catch (Exception e) {
			msg = String.format(mappingI18n(this.getClass().getSimpleName(),"交易 %s 补发通知异常!\n%s", lang), transSeqId, mappingI18n(this.getClass().getSimpleName(), e.getMessage(), lang));
			this.error("[resendNotify] "+msg,e);
		}
		return commonBO.buildErrorResp(msg);
	}
	
	/**
	 * 加总
	 */
	@RequestMapping("/amtSum.do")
	public @ResponseBody JSONObject amtSum(String startDt, String endDt, String startTm, String endTm,
			String transSeqId, String orderId, String transAt ,String userId, String transState, String txnSrc, 
			String transType, String currCd) {
//		AssertUtil.notMonStr(mon.substring(4));
		AssertUtil.strIsBlank(startDt, "startDt is blank.");
		Map<String, String> qryParams = new HashMap<String, String>();
		qryParams.put("mchntCd", commonBO.getMchntCd());
		qryParams.put("transSeqId", transSeqId);
		qryParams.put("orderId", orderId);
		qryParams.put("userId", userId);
		qryParams.put("transAt", transAt);
//		qryParams.put("startDate", startDt);
//		qryParams.put("endDate", endDt);
//		qryParams.put("startTime", startTm);//起始时间
//		qryParams.put("endTime", endTm);//结束时间
		String mon = StringUtil.left(startDt, 8);
		qryParams.put("startDate", StringUtil.left(startDt, 8));
		qryParams.put("endDate", StringUtil.left(endDt, 8));
		qryParams.put("startTime", StringUtil.right(startDt, 6));//起始时间
		qryParams.put("endTime", StringUtil.right(endDt, 6)); // 结束时间
		qryParams.put("transType", Utils.isEmpty(transType) ? "01":transType);//交易类型
		qryParams.put("txnState", transState);//交易状态
		qryParams.put("txnSrc", txnSrc);//交易来源
		qryParams.put("currCd", currCd);//币别
		logger.info("交易加總:" + BeanUtil.desc(qryParams, null, null));
		return transBO.summary(qryParams, mon);
	}
	
	/**
	 * 取得交易类型
	 * @param mchntCd
	 * @return
	 */
	protected List<TxnType> getPayTypes(String mchntCd) {
		List<TxnType> getPayTypes = transBO.getPayTypes(mchntCd);
				
        //去重复出现在选单的交易类型
        List<TxnType> uniquePayTypes = new ArrayList<>();
        for (TxnType payTypes : getPayTypes) {
        	if (getPayTypes != null && getPayTypes.size() > 0) {
				if (!uniquePayTypes.contains(payTypes))
					uniquePayTypes.add(payTypes);
        	}
		}
		return uniquePayTypes;
	}
	
	/**
	 * 上传凭证
	 * @param transSeqId
	 * @param mon
	 * @param response
	 * @return
	 */
	@RequestMapping("/uploadImage.do")  
	@RolesSessionCheck(roles= {MerUserRole.WithdrawUser,MerUserRole.PaymentUser,MerUserRole.SuperUser})
	public @ResponseBody String uploadImage(Model model, MultipartFile uploadImage, String transSeqId, HttpServletRequest request, HttpServletResponse response) {

        String lang = I18nMsgUtils.getLanguage(request);
		String msg="";
		try {
			if (uploadImage.getSize() != 0) {
				if (checkFileSize(uploadImage.getSize(), 1, "M")) {//图片大小限制1MB
					uploadImage(uploadImage, transSeqId);
					msg = mappingI18n(this.getClass().getSimpleName(),"凭证上传成功", lang);
				} else {
					msg = mappingI18n(this.getClass().getSimpleName(),"图片大小限制1MB", lang);
				}
			} else {
				msg = mappingI18n(this.getClass().getSimpleName(),"未选择图片", lang);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		return msg;
	}
	
	public void uploadImage(MultipartFile uploadImage, String transSeqId) throws IOException {
		if(StringUtil.isNotBlank(transSeqId)) {
			String fileName = uploadImage.getOriginalFilename();
			int deputy = fileName.lastIndexOf(".");
			String contentType = fileName.substring(deputy + 1);
			byte[] img = loadImage(uploadImage);
			SimpleProgressListener listener = new SimpleProgressListener();
			String newFileName = "0191-proof-"+transSeqId+"."+contentType;
			GenericResult r = OssUtils.putImage(newFileName, img, listener);
			String uniqueName = OssUtils.getUniqueName(newFileName);
			int number = uniqueName.indexOf(".");
			String objectName = uniqueName.substring(0, number);
			ITransProofService service = this.getService(ITransProofService.class);
			TransProof transProof = new TransProof();
			transProof.setPlatformId("00000000");
			transProof.setTxnId(transSeqId);//交易流水号
			transProof.setProofType("0191");
			transProof.setFileDesc(newFileName);
			transProof.setContentType(contentType);//文件格式：如：jpg，png等
			transProof.setContentBase64(objectName);//文件内容base64
			transProof.setFileId(uniqueName);//文件ID
			service.add(transProof);
		}
	}
	
	private static byte[] loadImage(MultipartFile fname) throws IOException {
		InputStream fileIn = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			fileIn = fname.getInputStream();
			int c=-1;
			byte[] buf=new byte[1024];
			while (true) {
				c=fileIn.read(buf);
				if (c>0) {
					os.write(buf, 0, c);
				}
				else {
					break;
				}
			}
			return os.toByteArray();
		} finally {
			if (fileIn != null)
				fileIn.close();
		}
	}
	
	/**
	 * 判断文件大小
	 * @param len文件长度
	 * @param size限制大小
	 * @param unit限制单位（B,K,M,G）
	 * @return
	 */
    public static boolean checkFileSize(Long len, int size, String unit) {
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }
	
}
