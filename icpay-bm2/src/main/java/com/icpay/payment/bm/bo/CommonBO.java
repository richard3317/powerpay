package com.icpay.payment.bm.bo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.icpay.payment.bm.cache.PageConfCache;
import com.icpay.payment.bm.constants.BMConstants;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.AjaxResult;
import com.icpay.payment.common.entity.ColumnInfo;
import com.icpay.payment.common.entity.IEntityTransfer;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.AjaxRespEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.BizUtils;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.IOUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.TransProfitReport;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TransLogMapping;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.service.FileService;

@Component("commonBO")
public class CommonBO extends BaseBO {

	public <T> Pager<Map<String, String>> transferPager(Pager<T> pager, String pageConfTp, IEntityTransfer transfer) {

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
		List<ColumnInfo> columnLst = PageConfCache.getColumnLst(pageConfTp);
		result.setColumnLst(columnLst);
		return result;
	}
	
	public <T> Pager<Map<String, String>> transferList(List<T> pager,
			String pageConfTp,
			IEntityTransfer transfer) {
		
		AssertUtil.objIsNull(pager, "pager is null");
		AssertUtil.strIsBlank(pageConfTp, "pageConfTp is blank.");
		
		Pager<Map<String, String>> result = new Pager<Map<String,String>>();
		List<Map<String, String>> resultLst = new ArrayList<Map<String,String>>();
		Set<String> containFields = PageConfCache.getContainedFields(pageConfTp);
		for (T o : pager) {
			Map<String, String> m = BeanUtil.desc(o, null, containFields);
			if (transfer != null) {
				transfer.transfer(m);
			}
			resultLst.add(m);
		}
		result.setResultList(resultLst);
		List<ColumnInfo> columnLst = PageConfCache.getColumnLst(pageConfTp);
		result.setColumnLst(columnLst);
		return result;
	}
	

	public <T> Map<String, String> transferEntity(T entity, String pageConfTp, IEntityTransfer transfer) {
		AssertUtil.objIsNull(entity, "entity is null");
		//Set<String> containFields = PageConfCache.getContainedFields(pageConfTp);
		//Map<String, String> m = BeanUtil.desc(entity, null, containFields);
		Map<String, String> m = BeanUtil.desc(entity, null, null);
		if (transfer != null) {
			transfer.transfer(m);
		}
		return m;
	}

	public AjaxResult buildSuccResp() {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(AjaxRespEnums.SUCC.getRespCode());
		rst.setRespMsg(AjaxRespEnums.SUCC.getRespMsg());
		return rst;
	}

	public AjaxResult buildSuccResp(String msg) {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(AjaxRespEnums.SUCC.getRespCode());
		rst.setRespMsg(msg);
		return rst;
	}

	public AjaxResult buildSuccResp(String resultDataKey, Object resultData) {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(AjaxRespEnums.SUCC.getRespCode());
		rst.setRespMsg(AjaxRespEnums.SUCC.getRespMsg());
		if (!StringUtil.isEmpty(resultDataKey) && resultData != null) {
			rst.addResultData(resultDataKey, resultData);
		}
		return rst;
	}

	public AjaxResult buildAjaxResp(AjaxRespEnums ajaxResp) {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(ajaxResp.getRespCode());
		rst.setRespMsg(ajaxResp.getRespMsg());
		return rst;
	}

	public AjaxResult buildAjaxResp(AjaxRespEnums ajaxResp, String msg) {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(ajaxResp.getRespCode());
		if (StringUtil.isBlank(msg)) {
			rst.setRespMsg(ajaxResp.getRespMsg());
		} else {
			rst.setRespMsg(msg);
		}
		return rst;
	}

	public AjaxResult buildErrorResp() {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(AjaxRespEnums.ERROR.getRespCode());
		rst.setRespMsg(AjaxRespEnums.ERROR.getRespMsg());
		return rst;
	}

	public AjaxResult buildErrorResp(String msg) {
		AjaxResult rst = new AjaxResult();
		rst.setRespCode(AjaxRespEnums.ERROR.getRespCode());
		if (StringUtil.isEmpty(msg)) {
			rst.setRespMsg(AjaxRespEnums.ERROR.getRespMsg());
		} else {
			rst.setRespMsg(msg);
		}
		return rst;
	}

	public String getParamMapKey(Class clazz) {
		AssertUtil.objIsNull(clazz, "clazz is null.");
		return clazz.getName() + BMConstants.QRY_PARAM_MAP_KEY;
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
	 * ?????? Excel
	 * @param wb Excel Workbook
	 * @param fileName ????????????
	 * @param charSet ?????????????????????UTF-8
	 * @param response HttpServletResponse
	 */
	public void downloadWorkBook(Workbook wb, String fileName, String charSet, HttpServletResponse response) {
		AssertUtil.objIsNull(wb, "Excel?????????????????????(null).");
		AssertUtil.strIsBlank(fileName, "fileName is blank.");

		if (StringUtil.isBlank(charSet)) {
			charSet = Constant.UTF8;
		}
		response.setCharacterEncoding(charSet);
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);

		OutputStream os = null;
		try {
			os = response.getOutputStream();
			wb.write(os);
		} catch (Exception e) {
			throw new BizzException("??????Excel????????????:" + fileName, e);
		} finally {
			IOUtil.close(os);
		}
	}

	/**
	 * ??????????????????????????????????????????
	 */
	public void downSettleFile(String filePath, String fileName, String charSet, HttpServletResponse response) {
		AssertUtil.strIsBlank(filePath, "filePath is blank.");
		AssertUtil.strIsBlank(fileName, "fileName is blank.");
		downFromRemoteFile(filePath, fileName, "0", charSet, response);
	}

	/**
	 * ??????????????????????????????????????????
	 */
	public void downExcelFile(String filePath, String fileName, HttpServletResponse response) {
		AssertUtil.strIsBlank(filePath, "filePath is blank.");
		AssertUtil.strIsBlank(fileName, "fileName is blank.");
		downFromRemoteFile(filePath, fileName, "1", null, response);
	}

	/**
	 * ??????????????????????????????????????????
	 * 
	 * @param filePath
	 * @param fileName
	 * @param response
	 */
	private void downFromRemoteFile(String filePath, String fileName, String fileTp, String charSet,
			HttpServletResponse response) {
		InputStream in = null;
		OutputStream os = null;
		try {
			FileService fs = ServiceProxy.getService(FileService.class);
			byte[] fileData = fs.getFile(filePath);
			in = new ByteArrayInputStream(fileData);
			os = response.getOutputStream();
			byte[] b = fs.getFile(filePath);

			if ("1".equals(fileTp)) {
				response.setContentType("application/vnd.ms-excel;charset=ISO-8859-1");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
			} else {
				if (StringUtil.isBlank(charSet)) {
					charSet = Constant.GBK;
				}
				response.setCharacterEncoding(charSet);
				response.setContentType("multipart/form-data");
				response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
			}

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

	/***
	 * ????????????????????????
	 */
	public void exportTransLogExcel(List<TransLogMapping> transList, String filePath, String fileName, String charSet,
			HttpServletResponse response) {
		// ??????excel??????
		Date date = new Date();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		//String rd = simple.format(date);
		// Long transAtSum = 0L;
		// Long transFeeSum = 0L;
		BigDecimal transAtSum = new BigDecimal(0);
		BigDecimal transFeeSum = new BigDecimal(0);
		try {
			Workbook wb = new HSSFWorkbook();
			// ???????????????
			Sheet sheet = wb.createSheet(fileName);
			String headers[] = new String[] { "????????????", "????????????", "?????????", "????????????", "???????????????", "???????????????", "????????????", "?????????????????????",
					"????????????????????????", "????????????", "?????????", "???????????????", "??????", "???????????????", "?????????", "???????????????", "???????????????", "????????????" };
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
				cell.setCellValue(headers[i]);
			}
			for (int i = 0; i < transList.size(); i++) {
				TransLogMapping transLog = transList.get(i);
				if (transLog != null) {
					String transDt = transLog.getExtTransDt();
					String transTm = transLog.getExtTransTm();
					String mchntCd = transLog.getMchntCd();
					String mchntNm = transLog.getMchntCnAbbr();
					String orderId = transLog.getOrderId();
					String transSeqId = transLog.getTransSeqId();

					BigDecimal transAt = new BigDecimal(transLog.getTransAt());
					String transAtDesc = transAt.movePointLeft(2).toString();
					BigDecimal transFee = null;
					String transFeeDesc = null;
					if (transLog.getTransFee() != null && transLog.getTransFee() != 0) {
						transFee = new BigDecimal(transLog.getTransFee());
						transFeeDesc = transFee.movePointLeft(2).toString();
					} else {
						transFeeDesc = "0.00";
					}
					String intTransCd = transLog.getIntTransCd();
					String intTransCdDesc = EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, true);

					String txnState = transLog.getTxnState();
					String stateDesc = null;
					if (StringUtil.isNotBlank(txnState)) {
						stateDesc = EnumUtil.translate(TxnEnums.TxnStatus.class, txnState, true);
					} else {
						stateDesc = "";
					}

					String respCd = transLog.getRspCd();
					String respDesc = null;
					if (!StringUtil.isBlank(respCd) && respCd.length() > 2) {
						respDesc = respCd + "-" + transLog.getErrMsg();
					} else {
						respDesc = respCd;
					}

					String agentCd = transLog.getAgent_cd();
					String chnlName = EnumUtil.translate(TxnEnums.ChnlId.class, transLog.getTransChnl(), true);
					String chnlMchntCd = transLog.getChnlMchntCd();
					String bankNum = transLog.getBankNum();
					String chnlOrderId = transLog.getChnlOrderId();
					String chnlTransId = transLog.getChnlTransId();
					String recUpdTs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(transLog.getRecUpdTs());

					Row row = sheet.createRow(i + 1);
					row.setHeight((short) (12.75 * 20)); // ???????????? ?????????20
					setRowValues(0, transDt, row);
					setRowValues(1, transTm, row);
					setRowValues(2, mchntCd, row);
					setRowValues(3, mchntNm, row);
					setRowValues(4, orderId, row);
					setRowValues(5, transSeqId, row);
					setRowValues(6, intTransCdDesc, row);
					setRowValues(7, transAtDesc, row);
					setRowValues(8, transFeeDesc, row);
					setRowValues(9, stateDesc, row);
					setRowValues(10, respDesc, row);
					setRowValues(11, agentCd, row);
					setRowValues(12, chnlName, row);
					setRowValues(13, chnlMchntCd, row);
					setRowValues(14, bankNum, row);
					setRowValues(15, chnlOrderId, row);
					setRowValues(16, chnlTransId, row);
					setRowValues(17, recUpdTs, row);

					// transAtSum = transAtSum + (transLog.getTransAt() == null ? 0L :
					// transLog.getTransAt());
					// transFeeSum = transFeeSum + (transLog.getTransFee() ==null ? 0L
					// :transLog.getTransFee());
					transAtSum = transAtSum
							.add(new BigDecimal((transLog.getTransAt() == null ? 0 : transLog.getTransAt())));
					transFeeSum = transFeeSum
							.add(new BigDecimal((transLog.getTransFee() == null ? 0 : transLog.getTransFee())));
				}
			}
			// ???????????????

			Row foot_row = sheet.createRow(transList.size() + 1);
			Cell cell = foot_row.createCell(0);
			cell.setCellStyle(style);
			cell.setCellValue("??????????????????");

			cell = foot_row.createCell(1);
			cell.setCellStyle(style);
			cell.setCellValue(transAtSum.movePointLeft(2).toString() + "???");

			// ???????????????
			Row foot_row1 = sheet.createRow(transList.size() + 2);
			Cell cell1 = foot_row1.createCell(0);
			cell1.setCellStyle(style);
			cell1.setCellValue("?????????????????????");

			cell1 = foot_row1.createCell(1);
			cell1.setCellStyle(style);
			cell1.setCellValue(transFeeSum.movePointLeft(2).toString() + "???");
			
			fileName = getUniqueFileName(fileName);
			mkdirFileDir(filePath + fileName);

			FileOutputStream fileOut = new FileOutputStream(filePath  + fileName);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			throw new BizzException("??????????????????:" + fileName, e);
		}
		downFile(filePath, fileName, charSet, response);
	}

	/***
	 * ??????quxian????????????
	 */
	public void exportWithdrawLogExcel(List<TransLogMapping> transList, String filePath, String fileName,
			String charSet, HttpServletResponse response) {
		// ??????excel??????
		Date date = new Date();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		//String rd = simple.format(date);
		BigDecimal transAtSum = new BigDecimal(0);
		BigDecimal transFeeSum = new BigDecimal(0);
		try {
			Workbook wb = new HSSFWorkbook();
			// ???????????????
			Sheet sheet = wb.createSheet(fileName);
			String headers[] = new String[] { "????????????", "????????????", "???????????????", "????????????", "???????????????", "???????????????", "????????????", "?????????????????????",
					"????????????????????????", "????????????", "??????", "?????????", "??????", "??????", "???????????????", "???????????????" };
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
				cell.setCellValue(headers[i]);
			}
			for (int i = 0; i < transList.size(); i++) {
				TransLogMapping transLog = transList.get(i);
				if (transLog != null) {
					String transDt = transLog.getExtTransDt();
					String transTm = transLog.getExtTransTm();
					String mchntCd = transLog.getMchntCd();
					String mchntNm = transLog.getMchntCnAbbr();
					String orderId = transLog.getOrderId();
					String transSeqId = transLog.getTransSeqId();

					BigDecimal transAt = new BigDecimal(transLog.getTransAt());
					String transAtDesc = transAt.movePointLeft(2).toString();
					BigDecimal transFee = null;
					String transFeeDesc = null;
					if (transLog.getTransFee() != null && StringUtils.isNotBlank(transLog.getTransFee().toString())) {
						transFee = new BigDecimal(transLog.getTransFee());
						transFeeDesc = transFee.movePointLeft(2).toString();
					} else {
						transFeeDesc = "0.00";
					}
					String intTransCd = transLog.getIntTransCd();
					String intTransCdDesc = EnumUtil.translate(TxnEnums.TxnType.class, intTransCd, true);

					String txnState = transLog.getTxnState();
					String stateDesc = null;
					if (StringUtil.isNotBlank(txnState)) {
						stateDesc = EnumUtil.translate(TxnEnums.TxnStatus.class, txnState, true);
					} else {
						stateDesc = "";
					}

					String chnlName = EnumUtil.translate(TxnEnums.ChnlId.class, transLog.getTransChnl(), true);
					String accNo = transLog.getAccNo();
					String accNoDesc = null;
					if (StringUtil.isBlank(accNo)) {
						accNoDesc = "";
					} else {
						// accNoDesc = accNo.length() > 7 ? StringUtil.mask(accNo, 4, accNo.length() -
						// 6, '*') : accNo;
						accNoDesc = BizUtils.strMask(accNo, "*", 4, 0, 6);
					}
					String bankNum = transLog.getBankNum();
					String chnlOrderId = transLog.getChnlOrderId();
					String chnlTransId = transLog.getChnlTransId();
					String accName = transLog.getAccName();

					Row row = sheet.createRow(i + 1);
					row.setHeight((short) (12.75 * 20)); // ???????????? ?????????20
					setRowValues(0, transDt, row);
					setRowValues(1, transTm, row);
					setRowValues(2, mchntCd, row);
					setRowValues(3, mchntNm, row);
					setRowValues(4, orderId, row);
					setRowValues(5, transSeqId, row);
					setRowValues(6, intTransCdDesc, row);
					setRowValues(7, transAtDesc, row);
					setRowValues(8, transFeeDesc, row);
					setRowValues(9, stateDesc, row);
					setRowValues(10, accNoDesc, row);
					setRowValues(11, bankNum, row);
					setRowValues(12, accName, row);
					setRowValues(13, chnlName, row);
					setRowValues(14, chnlOrderId, row);
					setRowValues(15, chnlTransId, row);

					transAtSum = transAtSum
							.add(new BigDecimal((transLog.getTransAt() == null ? 0 : transLog.getTransAt())));
					transFeeSum = transFeeSum
							.add(new BigDecimal((transLog.getTransFee() == null ? 0 : transLog.getTransFee())));
				}
			}

			// ???????????????

			Row foot_row = sheet.createRow(transList.size() + 1);
			Cell cell = foot_row.createCell(0);
			cell.setCellStyle(style);
			cell.setCellValue("??????????????????");

			cell = foot_row.createCell(1);
			cell.setCellStyle(style);
			cell.setCellValue(transAtSum.movePointLeft(2).toString() + "???");

			// ???????????????
			Row foot_row1 = sheet.createRow(transList.size() + 2);
			Cell cell1 = foot_row1.createCell(0);
			cell1.setCellStyle(style);
			cell1.setCellValue("?????????????????????");

			// ???????????????
			cell1 = foot_row1.createCell(1);
			cell1.setCellStyle(style);
			cell1.setCellValue(transFeeSum.movePointLeft(2).toString() + "???");
			
			fileName = getUniqueFileName(fileName);
			mkdirFileDir(filePath + fileName);

			FileOutputStream fileOut = new FileOutputStream(filePath+ fileName);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			throw new BizzException("??????????????????:" + fileName, e);
		}
		downFile(filePath, fileName, charSet, response);
	}

	public Cell setRowValues(int count, String vlues, Row row) {
		return setRowValues(count, vlues, row, null);
	}
	
	public Cell setRowValues(int count, String vlues, Row row, CellStyle style) {
		//Cell cell = row.createCell(count, Cell.CELL_TYPE_STRING);
		Cell cell = row.createCell(count);
		if (style!=null) cell.setCellStyle(style);	
		cell.setCellValue(vlues);
		return cell;
	}
	
//	public Cell setRowValueAsNumber(int count, String value, Row row, CellStyle style) {
//		Cell cell = row.createCell(count, Cell.CELL_TYPE_NUMERIC);
//		cell.setCellValue(value);
//		if (style!=null) cell.setCellStyle(style);	
//		return cell;
//	}
//	
//	public Cell setRowValueAsNumber(int count, Long value, Row row, CellStyle style) {
//		Cell cell = row.createCell(count, Cell.CELL_TYPE_NUMERIC);
//		cell.setCellValue(value);
//		if (style!=null) cell.setCellStyle(style);	
//		return cell;
//	}
//	
//	public Cell setRowValueAsNumber(int count, Double value, Row row, CellStyle style) {
//		Cell cell = row.createCell(count, Cell.CELL_TYPE_NUMERIC);
//		cell.setCellValue(value);
//		if (style!=null) cell.setCellStyle(style);	
//		return cell;
//	}
	
	public Cell setRowValueAsNumber(int count, String value, Row row, CellStyle style) {
		return setRowValueAsNumber(count, new BigDecimal(value).doubleValue(), row, style);
	}
	
	public Cell setRowValueAsNumber(int count, Long value, Row row, CellStyle style) {
		return setRowValueAsNumber(count, new BigDecimal(value).doubleValue(), row, style);
	}
	
	public Cell setRowValueAsNumber(int count, Double value, Row row, CellStyle style) {
		Cell cell = row.createCell(count, Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		if (style!=null) cell.setCellStyle(style);	
		return cell;
	}
	
//	/***
//	 * ????????????????????????
//	 */
//	public void exportToExcel(Pager<Map<String, String>> pager, String filePath, String fileName, String charSet, HttpServletResponse response) {
//		// ??????excel??????
//		try {
//			Workbook wb = new HSSFWorkbook();
//			// ???????????????
//			Sheet sheet = wb.createSheet(fileName);
//			Row header_row = sheet.createRow(0);
//			header_row.setHeight((short) (12.75 * 20));
//			// ?????????????????? ????????????
//			CellStyle style = wb.createCellStyle();
//			style.setAlignment(CellStyle.ALIGN_CENTER); // ??????????????????????????????
//			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// ??????????????????????????????
//			
//			int i = 0;
//			for (ColumnInfo column : pager.getColumnLst()) {
//				sheet.setColumnWidth(i, 30 * column.getWidth()); // 18*256
//				Cell cell = header_row.createCell(i);
//				// ??????????????? ????????????
//				cell.setCellStyle(style);
//				cell.setCellValue(column.getTitle());
//				i++;
//			}
//
//			i=0;
//			for (Map<String, String> data : pager.getResultList()) {
//				//System.out.println("daraRow: "+data);
//				
//				Row row = sheet.createRow(i + 1);
//				row.setHeight((short) (12.75 * 20)); // ???????????? ?????????20
//
//				int ci = 0;
//				for (ColumnInfo column : pager.getColumnLst()) {
//					setRowValues(ci, data.get(column.getField()), row);
//					ci++;
//				}
//				i++;
//			}
//			fileName = getUniqueFileName(fileName);
//			mkdirFileDir(filePath + fileName);
//			FileOutputStream fileOut = new FileOutputStream(filePath + fileName);
//			wb.write(fileOut);
//			fileOut.close();
//		} catch (Exception e) {
//			throw new BizzException("??????????????????:" + fileName, e);
//		}
//		downFile(filePath, fileName, charSet, response);
//	}
	
	/**
	 * ????????????????????????
	 * @param pager ????????????????????? Pager
	 * @param fileName ????????????
	 * @param charSet ?????????
	 * @param response
	 */
	public void exportToExcel(Pager<Map<String, String>> pager, String fileName, String charSet, HttpServletResponse response) {
		exportToExcel(pager, null, fileName, charSet, response);
	}
	
//	public void compareChnlLogToExcel(Pager<Map<String, String>> pager, Map<String, Object> summary, String fileName, String charSet, MultipartFile chnlLogFile, HttpServletResponse response) {
//		
//	}
	/***
	 * ????????????????????????
	 */
	public void exportToExcel(Pager<Map<String, String>> pager, Map<String, Object> summary, String fileName, String charSet, HttpServletResponse response) {
		// ??????excel??????
		try {
			Workbook wb = new HSSFWorkbook();
			// ???????????????
			Sheet sheet = wb.createSheet(fileName);
			
			// ?????????????????? ????????????
			CellStyle styleHeader = wb.createCellStyle();
			styleHeader.setAlignment(CellStyle.ALIGN_CENTER); // ??????????????????????????????
			styleHeader.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// ??????????????????????????????
			
			CellStyle styleText = wb.createCellStyle();
			styleText.setAlignment(CellStyle.ALIGN_LEFT); // ??????????????????????????????
			styleText.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// ??????????????????????????????
			
			// ?????????????????? ????????????
			CellStyle styleNum = wb.createCellStyle();
			styleNum.setAlignment(CellStyle.ALIGN_RIGHT);
			styleNum.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// ??????????????????????????????
			styleNum.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
			
			Row header_row = sheet.createRow(0);
			header_row.setHeight((short) (12.75 * 24));
			
			int i = 0;
			for (ColumnInfo column : pager.getColumnLst()) {
				sheet.setColumnWidth(i, 35 * column.getWidth()); // 18*256
				Cell cell = header_row.createCell(i);
				// ??????????????? ????????????
				cell.setCellStyle(styleHeader);
				cell.setCellValue(column.getTitle());
				i++;
			}
			
			int ri=0;
			for (Map<String, String> data : pager.getResultList()) {
				//System.out.println("daraRow: "+data);
				
				Row row = sheet.createRow(ri + 1);
				row.setHeight((short) (12.75 * 22)); // ???????????? ?????????20

				int ci = 0;
				for (ColumnInfo column : pager.getColumnLst()) {
					if (Utils.isInSet(column.getField(), 
							"transAmtDesc", "transFeeDesc", "transFeeChnlDesc", "transFeeDeltaDesc", 
							"transAt", "transFee", "transFeeChnl", "transFeeDelta", "transAmt", "transAtDesc",
							"txnAmt","txnAmtDesc","txnFee","txnFeeDesc","apAmtTotal","apAmtTotalDesc","apAmt","apAmtDesc",
							"txAmtMaxDesc","txAmtMinDesc","txCardDayMaxDesc","txDayMaxDesc"
							))
					{
						setRowValueAsNumber(ci, data.get(column.getField()), row, styleNum);
					}
					else {
						setRowValues(ci, data.get(column.getField()), row, styleText);
					}
					ci++;
				}
				ri++;
			}
			
			for(i=0; i<pager.getColumnLst().size(); i++)
				sheet.autoSizeColumn(i, true);
			
			if (!Utils.isEmpty(summary)) {
				ri++; //?????????
				for(String field: summary.keySet()) {
					Row row = sheet.createRow(ri + 1);
					row.setHeight((short) (12.75 * 24)); // ???????????? ?????????20
					setRowValues(0, field, row);
					setRowValueAsNumber(2, strVal(summary.get(field)), row, styleNum);
					ri++;
				}
			}
			
			fileName = getUniqueFileName(fileName);
			downloadWorkBook(wb, fileName, charSet, response);
		} catch (Exception e) {
			throw new BizzException("??????????????????:" + fileName, e);
		}
	}
	
	public static String strVal(Object obj, String defaultVal) {
		if (obj==null) return defaultVal;
		return obj.toString();
	}
	
	public static String strVal(Object obj) {
		return strVal(obj,"");
	}
	
	
	
	public static void mkdirFileDir(String fileFullPath) {
		try {
			File file = new File(fileFullPath);
			file.getParentFile().mkdirs();
		} catch (Exception e) {
		}
	}
	
	public static String getFileExtension(String fileName) {
        //String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
	
    public static String stripExtension (String str) {
        // Handle null case specially.
        if (str == null) return null;
        // Get position of last '.'.
        int pos = str.lastIndexOf(".");
        // If there wasn't any '.' just return the string as is.
        if (pos == -1) return str;
        // Otherwise return the string, up to the dot.
        return str.substring(0, pos);
    }
	
	public static String getUniqueFileName(String fileName) {
		//String fileNameWithOutExt = fileName.replaceFirst("[.][^.]+$", "");
		String fileNameWithOutExt = stripExtension(fileName);
		String fileExt = getFileExtension(fileName);
		String rnd = ""+System.currentTimeMillis()+""+Utils.getRandomInt(1000, 9999);
		return fileNameWithOutExt+rnd+(Utils.isEmpty(fileExt) ? "" : "."+fileExt);
	}
	
	/***
	 * ????????????????????????
	 */
	public void exportTransProfitExcel(List<TransProfitReport> respList, String filePath, String fileName, String charSet,
			HttpServletResponse response) {
				//????????????
				String[] title={"????????????","????????????","???????????????","???????????????","???????????????","???????????????","????????????(???)","???????????????(???)","????????????"/*,"???????????????(???)","??????????????????(???)"*/};
				//??????excel?????????
				HSSFWorkbook workbook=new HSSFWorkbook();
				//???????????????sheet
				HSSFSheet sheet=workbook.createSheet("????????????");
				
				//???????????????????????????????????? ?????????????????? ??
				HSSFCellStyle style = workbook.createCellStyle(); 
				style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//??????
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//??????
				
				//???????????????
				HSSFRow row=sheet.createRow(0);
				HSSFCell cell=null;
				//??????????????????????????????
				for(int i=0;i<title.length;i++){
				    cell=row.createCell(i);
				    cell.setCellValue(title[i]);
				    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}
				/*int transSum  = 0;
				int profitSum  = 0;
				int count  = 0;*/
				//????????????
				for (int i=0; i< respList.size();i++){
				    HSSFRow nrow=sheet.createRow(i+1);
				    HSSFCell ncell=nrow.createCell(0);
				    ncell.setCellValue(respList.get(i).getChnlId());
				    ncell=nrow.createCell(1);
				    ncell.setCellValue(respList.get(i).getChnlName());
				    ncell=nrow.createCell(2);
				    ncell.setCellValue(respList.get(i).getChnlMchntCd());
				    ncell=nrow.createCell(3);
				    ncell.setCellValue(respList.get(i).getChnlMchntName());
				    ncell=nrow.createCell(4);
				    ncell.setCellValue(respList.get(i).getMchntCd());
				    ncell=nrow.createCell(5);
				    ncell.setCellValue(respList.get(i).getMchntName());
				    ncell=nrow.createCell(6);
				    ncell.setCellValue(toFloatAmt(respList.get(i).getTransAt()));
				    ncell=nrow.createCell(7);
				    ncell.setCellValue(toFloatAmt(respList.get(i).getProfit()));
				    /*ncell=nrow.createCell(8);
				    ncell.setCellValue(respList.get(i).getAgentCd());
				    ncell=nrow.createCell(9);
				    ncell.setCellValue(respList.get(i).getAgentName());*/
				    ncell=nrow.createCell(8);
				    ncell.setCellValue(respList.get(i).getSettDate());
				    
				   /* transSum = transSum + Integer.parseInt(respList.get(i).getTransAt());
				    profitSum = profitSum + Integer.parseInt(respList.get(i).getProfit());
				    
				    //?????????
				    if(i+1 != respList.size() && !respList.get(i).getChnlId().equals(respList.get(i+1).getChnlId())) {
				    	sheet.addMergedRegion(new CellRangeAddress(i+1-count, i+1, 11, 11));
				    	 ncell=nrow.createCell(11);
						 ncell.setCellValue(toFloatAmt(Integer.toString(transSum)));
						 sheet.addMergedRegion(new CellRangeAddress(i+1-count, i+1, 12, 12));
						 ncell=nrow.createCell(12);
						 ncell.setCellValue(toFloatAmt(Integer.toString(profitSum)));
						 transSum = 0;
						 profitSum = 0;
						 count = i;
				    }else if(i+1 == respList.size()){
				    	sheet.addMergedRegion(new CellRangeAddress(i+1-count, i+1, 11, 11));
				    	 ncell=nrow.createCell(11);
						 ncell.setCellValue(toFloatAmt(respList.get(i).getTransAt()));
						 sheet.addMergedRegion(new CellRangeAddress(i+1-count, i+1, 12, 12));
						 ncell=nrow.createCell(12);
						 ncell.setCellValue(toFloatAmt(respList.get(i).getProfit()));
						 transSum = 0;
						 profitSum = 0;
						 count = i;
				    }else {
				    	count ++;
				    }*/
				}
				
				mkdirFileDir(filePath + fileName);
				try {
					FileOutputStream fout = new FileOutputStream(filePath + fileName); 
					workbook.write(fout); 
					fout.close();
				} catch (IOException e) {
				    e.printStackTrace();
				}
				downFile(filePath, fileName, charSet, response);
	}
	
	/**
	 * ????????????????????????
	 * @param sAmt
	 * @return
	 */
	public static String toFloatAmt(String sAmt) {
		if (sAmt==null) return null;
		String fAmt=null;
		if (!Utils.isEmpty(sAmt)) {
			try {
				Long amt = Long.parseLong(sAmt);
				fAmt = String.format("%.2f", (amt/100.0F));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fAmt;
	}
}
