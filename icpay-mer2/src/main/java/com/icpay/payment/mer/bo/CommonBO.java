package com.icpay.payment.mer.bo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.ColumnInfo;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.BizUtils;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.IOUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountFlow;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.TransLog;
import com.icpay.payment.db.service.IMchntInfoService;
import com.icpay.payment.mer.cache.PageConfCache;
import com.icpay.payment.mer.session.SessionKeys;
import com.icpay.payment.mer.util.EnumLangUtil;
import com.icpay.payment.mer.util.I18nMsgUtils;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.service.FileService;

@Component("commonBO")
public class CommonBO extends BaseBO {

	public <T> Pager<Map<String, String>> transferPager(Pager<T> pager, String pageConfTp, IEntityTransfer transfer ,HttpServletRequest req) {

		AssertUtil.objIsNull(pager, "pager is null");
		AssertUtil.strIsBlank(pageConfTp, "pageConfTp is blank.");

		Pager<Map<String, String>> result = new Pager<Map<String, String>>();
		result.setPageNum(pager.getPageNum());
		result.setPageSize(pager.getPageSize());
		result.setTotal(pager.getTotal());
		List<Map<String, String>> resultLst = new ArrayList<Map<String, String>>();
		Set<String> containFields = PageConfCache.getContainedFields(pageConfTp);
		for (T o : pager.getResultList()) {
			Map<String, String> m = BeanUtil.desc(o, null, containFields);
			if (transfer != null) {
				transfer.transfer(m);
			}
			resultLst.add(m);
		}
		result.setResultList(resultLst);
		List<ColumnInfo> columnLst = PageConfCache.getColumnLst(pageConfTp, I18nMsgUtils.getLanguage(req));
		result.setColumnLst(columnLst);
		return result;
	}

	public <T> Pager<Map<String, String>> transferList(List<T> pager, String pageConfTp, IEntityTransfer transfer, HttpServletRequest req) {
		AssertUtil.objIsNull(pager, "pager is null");
		AssertUtil.strIsBlank(pageConfTp, "pageConfTp is blank.");

		Pager<Map<String, String>> result = new Pager<Map<String, String>>();
		// result.setPageNum(pager.getPageNum());
		// result.setPageSize(pager.getPageSize());
		// result.setTotal(pager.getTotal());
		List<Map<String, String>> resultLst = new ArrayList<Map<String, String>>();
		Set<String> containFields = PageConfCache.getContainedFields(pageConfTp);
		for (T o : pager) {
			Map<String, String> m = BeanUtil.desc(o, null, containFields);
			if (transfer != null) {
				transfer.transfer(m);
			}
			resultLst.add(m);
		}
		result.setResultList(resultLst);
		List<ColumnInfo> columnLst = PageConfCache.getColumnLst(pageConfTp , I18nMsgUtils.getLanguage(req));
		result.setColumnLst(columnLst);
		return result;
	}

	public <T> Map<String, String> transferEntity(T entity, String pageConfTp, IEntityTransfer transfer, String lang) {
		AssertUtil.objIsNull(entity, "entity is null");
		Set<String> containFields = PageConfCache.getContainedFields(pageConfTp);
		Map<String, String> m = BeanUtil.desc(entity, null, containFields);
		if (transfer != null) {
			transfer.transfer(m);
		}
		
		//?????????
		if(m.get("currCd") != null) { 
			m.put("currCdDesc", EnumLangUtil.translate(lang, "CurrType", m.get("currCd"), false));
		}
		
		if(m.get("intTransCd") != null) { 
			m.put("intTransCdDesc", EnumLangUtil.translate(lang, "TxnType", m.get("intTransCd"), false));
		}

		if(m.get("txnState") != null) { 
			m.put("txnStateMsg", EnumLangUtil.translate(lang, "TxnStatus", m.get("txnState"), false));
			m.put("txnStateDesc", EnumLangUtil.translate(lang, "TxnStatus", m.get("txnState"), false));
		}
		
		if(m.get("txnSrc") != null) { 
			m.put("txnSrcDesc", EnumLangUtil.translate(lang, "TxnSource", m.get("txnSrc"), true));
		}
		
		if(m.get("operateTp") != null) { 
			m.put("operateTpDesc", EnumLangUtil.translate(lang, "AccOperTp", m.get("operateTp"), false));
		}
		
		return m;
	}

	/**
	 * ????????????
	 *
	 * @param filePath
	 * @param fileName
	 * @param response
	 */
	public void downFile(String filePath, String fileName, String charSet, HttpServletResponse response) {
		AssertUtil.strIsBlank(filePath, "filePath is blank.");
		AssertUtil.strIsBlank(fileName, "fileName is blank.");

		String fullPath = filePath + fileName;

		if (StringUtil.isBlank(charSet)) {
			charSet = Constant.GBK;
		}
		response.setCharacterEncoding(charSet);
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);

		InputStream in = null;
		OutputStream os = null;
		try {
			in = new FileInputStream(new File(fullPath));
			os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = in.read(b)) > 0) {
				os.write(b, 0, length);
			}
		} catch (Exception e) {
			throw new BizzException("??????????????????:" + fileName, e);
		} finally {
			IOUtil.close(in);
			IOUtil.close(os);
		}
	}

	/**
	 * ??????????????????????????????????????????
	 */
	public void downSettleFile(String filePath, String fileName, String charSet, HttpServletResponse response) {
		AssertUtil.strIsBlank(filePath, "filePath is blank.");
		AssertUtil.strIsBlank(fileName, "fileName is blank.");
		downFromRemoteFile(filePath, fileName, charSet, "multipart/form-data", response);
	}

	/**
	 * ??????????????????????????????????????????
	 *
	 * @param filePath
	 * @param fileName
	 * @param response
	 */
	private void downFromRemoteFile(String filePath, String fileName, String charSet, String contentType,
			HttpServletResponse response) {
		InputStream in = null;
		OutputStream os = null;
		try {
			FileService fs = ServiceProxy.getService(FileService.class);
			byte[] fileData = fs.getFile(filePath);
			in = new ByteArrayInputStream(fileData);
			os = response.getOutputStream();
			byte[] b = fs.getFile(filePath);
			int length;

			if (StringUtil.isBlank(charSet)) {
				charSet = Constant.GBK;
			}
			response.setCharacterEncoding(charSet);
			if (StringUtil.isBlank(contentType)) {
				response.setContentType("multipart/form-data");
			} else {
				response.setContentType(contentType);
			}
			response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);

			while ((length = in.read(b)) > 0) {
				os.write(b, 0, length);
			}
		} catch (Exception e) {
			throw new BizzException("??????????????????:" + fileName, e);
		} finally {
			IOUtil.close(in);
			IOUtil.close(os);
		}
	}

	/***
	 * ????????????????????????
	 */
	public void exportTransLogExcel(List<TransLog> transList, String filePath, String fileName, String charSet,
			HttpServletResponse response) {
		// ??????excel??????
		try {
			Workbook wb = new HSSFWorkbook();
			// ???????????????
			Sheet sheet = wb.createSheet(fileName);
			String headers[] = new String[] { "????????????", "????????????", "?????????", "??????", "????????????", "???????????????", "????????????" };
			Row header_row = sheet.createRow(0);
			header_row.setHeight((short) (12.75 * 20));
			// ?????????????????? ????????????
			CellStyle style = wb.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER); // ??????????????????????????????
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// ??????????????????????????????

			for (int i = 0; i < headers.length; i++) {
				// ???????????? ?????????256 ??????????????????
				sheet.setColumnWidth(i, 18 * 256);
				Cell cell = header_row.createCell(i);
				// ??????????????? ????????????
				cell.setCellStyle(style);
				cell.setCellValue(I18nMsgUtils.getMessageWithDefault(
						I18nMsgUtils.ICPAY_MER, 
						this.getUsedLang(),
						I18nMsgUtils.getKeyCombine("report", headers[i]),headers[i]));
			}
			for (int i = 0; i < transList.size(); i++) {
				TransLog transLog = transList.get(i);
				if (transLog != null) {
					String transDt = transLog.getExtTransDt() + " " + transLog.getExtTransTm();
					BigDecimal transAt = new BigDecimal(transLog.getTransAt());
					String transAtDesc = transAt.movePointLeft(2).toString();
					String accNo = transLog.getAccNo();
					String accNoDesc = "";
					if (StringUtil.isNotBlank(accNo)) {
						// accNoDesc = accNo.length() > 7 ? StringUtil.mask(accNo, 6, accNo.length() -
						// 4, '*') : accNo;
						accNoDesc = BizUtils.strMask(accNo, "*", 4, 0, 6);
					}

					BigDecimal transFee = null;
					String transFeeDesc = null;
					if (transLog.getTransFee() != null && !transLog.getTransFee().equals("")) {
						transFee = new BigDecimal(transLog.getTransFee());
						transFeeDesc = transFee.movePointLeft(2).toString();
					} else {
						transFeeDesc = "0.00";
					}
					String intTransCd = transLog.getIntTransCd();
					String intTransCdDesc = EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, true);
					// String rspCd = transLog.getRspCd();

					String txnState = transLog.getTxnState();
					String stateDesc = null;
					if (StringUtil.isNotBlank(txnState)) {
						stateDesc = EnumUtil.translate(TxnEnums.TxnStatus.class, txnState, true);
					} else {
						stateDesc = "";
					}
					// if (RspCd._00.equals(rspCd)) {
					// stateDesc = "??????";
					// } else if (StringUtil.isBlank(rspCd)) {
					// stateDesc = "??????";
					// } else {
					// stateDesc = "??????";
					// }

					Row row = sheet.createRow(i + 1);
					row.setHeight((short) (12.75 * 20)); // ???????????? ?????????20
					setRowValues(0, transDt, row);
					setRowValues(1, intTransCdDesc, row);
					setRowValues(2, transLog.getOrderId(), row);
					setRowValues(3, accNoDesc, row);
					setRowValues(4, transAtDesc, row);
					setRowValues(5, transFeeDesc, row);
					setRowValues(6, stateDesc, row);
					// setRowValues(7,transLog.getPayType(),row);
				}
			}

			mkdirFileDir(filePath + fileName);

			FileOutputStream fileOut = new FileOutputStream(filePath + fileName);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			throw new BizzException("??????????????????:" + fileName, e);
		}
		downFile(filePath, fileName, charSet, response);
	}

	/***
	 * ????????????????????????
	 */
	public void exportAccflowExcel(Pager<Map<String, String>> pager, String filePath, String fileName, String charSet,
			HttpServletResponse response) {
		// ??????excel??????
		try {
			Workbook wb = new HSSFWorkbook();
			// ???????????????
			Sheet sheet = wb.createSheet(fileName);
			Row header_row = sheet.createRow(0);
			header_row.setHeight((short) (12.75 * 20));
			// ?????????????????? ????????????
			CellStyle style = wb.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER); // ??????????????????????????????
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// ??????????????????????????????

			int i = 0;
			for (ColumnInfo column : pager.getColumnLst()) {
				sheet.setColumnWidth(i, 30 * column.getWidth()); // 18*256
				Cell cell = header_row.createCell(i);
				// ??????????????? ????????????
				cell.setCellStyle(style);
				cell.setCellValue(column.getTitle());
				i++;
			}

			i = 0;
			for (Map<String, String> data : pager.getResultList()) {
				// System.out.println("daraRow: "+data);

				Row row = sheet.createRow(i + 1);
				row.setHeight((short) (12.75 * 20)); // ???????????? ?????????20

				int ci = 0;
				for (ColumnInfo column : pager.getColumnLst()) {
					setRowValues(ci, data.get(column.getField()), row);
					ci++;
				}
				i++;
			}

			mkdirFileDir(filePath + fileName);
			FileOutputStream fileOut = new FileOutputStream(filePath + fileName);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			throw new BizzException("??????????????????:" + fileName, e);
		}
		downFile(filePath, fileName, charSet, response);
	}

	public void setRowValues(int count, String vlues, Row row) {
		Cell cell = row.createCell(count);
		cell.setCellValue(vlues);
	}

	private IMchntInfoService mchntSvc = null;

	public IMchntInfoService getMchntSvc() {
		if (mchntSvc == null) {
			mchntSvc = this.getDBService(IMchntInfoService.class);
		}
		return mchntSvc;
	}

	/**
	 * ????????????????????????????????????
	 *
	 * @param mchntInfo
	 * @return
	 */
	public boolean validateSecret(String mchntCd) {
		if (Utils.isEmpty(mchntCd)) return false;
		MchntInfo mchnt = getMchntSvc().selectByPrimaryKey(mchntCd);
		return validateSecret(mchnt);
	}

	/**
	 * ????????????????????????????????????
	 *
	 * @param mchnt
	 * @return
	 */
	protected boolean validateSecret(MchntInfo mchnt) {
		if (mchnt==null) return false;
		if (mchnt.getSecretInitState() == null || "".equals(mchnt.getSecretInitState())
				|| "0".equals(mchnt.getSecretInitState())) {
			return false;// ????????????????????????????????????????????????
		}
		return true;
	}

	public static void mkdirFileDir(String fileFullPath) {
		try {
			File file = new File(fileFullPath);
			file.getParentFile().mkdirs();
		} catch (Exception e) {
		}
	}

	public static String getFileExtension(String fileName) {
		// String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}

	public static String stripExtension(String str) {
		// Handle null case specially.
		if (str == null)
			return null;
		// Get position of last '.'.
		int pos = str.lastIndexOf(".");
		// If there wasn't any '.' just return the string as is.
		if (pos == -1)
			return str;
		// Otherwise return the string, up to the dot.
		return str.substring(0, pos);
	}

	public static String getUniqueFileName(String fileName) {
		// String fileNameWithOutExt = fileName.replaceFirst("[.][^.]+$", "");
		String fileNameWithOutExt = stripExtension(fileName);
		String fileExt = getFileExtension(fileName);
		String rnd = "" + System.currentTimeMillis() + "" + Utils.getRandomInt(1000, 9999);
		return fileNameWithOutExt + rnd + (Utils.isEmpty(fileExt) ? "" : "." + fileExt);
	}
	public String getUsedLang() {
		return this.getSessionData(SessionKeys.USED_LANG.getCode());
	}
	
	public Pager<Map<String, String>> transferResultListByLang(Pager<Map<String, String>> transferPager, HttpServletRequest req) {
		
		List<Map<String, String>> transferResultLsts = new ArrayList<Map<String, String>>(); //?????????????????????????????????list
		List<Map<String, String>> resultLsts = transferPager.getResultList(); //??????????????????list
		
		String lang = I18nMsgUtils.getLanguage(req);
		
		for (Map<String, String> resultLst : resultLsts) {
			//?????????????????????????????????
			if(resultLst.get("currCd") != null) { 
				resultLst.put("currCdDesc", EnumLangUtil.translate(lang, "CurrType", resultLst.get("currCd"), false));
			}
			
			if(resultLst.get("intTransCd") != null) { 
				resultLst.put("intTransCdDesc", EnumLangUtil.translate(lang, "TxnType", resultLst.get("intTransCd"), false));
			}

			if(resultLst.get("txnState") != null) { 
				resultLst.put("txnStateMsg", EnumLangUtil.translate(lang, "TxnStatus", resultLst.get("txnState"), false));
				resultLst.put("txnStateDesc", EnumLangUtil.translate(lang, "TxnStatus", resultLst.get("txnState"), false));
			}
			
			if(resultLst.get("txnSrc") != null) { 
				resultLst.put("txnSrcDesc", EnumLangUtil.translate(lang, "TxnSource", resultLst.get("txnSrc"), true));
			}
			
			if(resultLst.get("operateTp") != null) { 
				resultLst.put("operateTpDesc", EnumLangUtil.translate(lang, "AccOperTp", resultLst.get("operateTp"), false));
			}
			
			transferResultLsts.add(resultLst);			
		}
		transferPager.setResultList(transferResultLsts);

		return transferPager;
	}	
}
