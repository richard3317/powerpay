package com.icpay.payment.bm.web.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.constants.Constant.TxnCatalog;
import com.icpay.payment.common.constants.RspCd;
import com.icpay.payment.common.constants.TxnStateEnums;
import com.icpay.payment.common.entity.ColumnInfo;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.exception.ChnlBizException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.IOUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.service.client.MerParamsClient;
import com.opencsv.CSVReader;

public class LogCompareTool extends BaseTool{
	private static final String CSV = "csv";
	private Pager<Map<String, String>> transLogs;
	private Map<String, Object> summary;
	private String outputFileName;
	private MultipartFile uploadFile;
	private String charSet="UTF-8";
	private HttpServletResponse response;
	
	public LogCompareTool(Pager<Map<String, String>> transLogs, Map<String, Object> summary, String outputFileName,
			MultipartFile chnlLogFile, String charSet, HttpServletResponse response) {
		super();
		this.transLogs = transLogs;
		this.summary = summary;
		this.outputFileName = outputFileName;
		this.uploadFile = chnlLogFile;
		this.charSet = charSet;
		this.response = response;
	}
	
	private Map<String, List<String>> chnlLogs=null; 
	private List<List<String>> chnlDupLogs=null;
	private List<String> chnlHeaderRow=null;
	private List<XlsColumnInfo> chnlColumnList=null;
	
	//private Map<String, Row> chnlLogs=null; 
	//private Row chnlHeaderRow=null;
	
	public boolean proc() {
		if (uploadFile==null) return false;
		try {
			return doProc();
		} catch (Exception e) {
			this.error("对帐处理错误："+e.getMessage(), e);
			this.outputError("对帐处理错误："+e.getMessage(), e);
			return false;
		}
	}

	protected boolean doProc() throws Exception {
		initConfig();
		if (CSV.equals(this.ch_file_fmt.toLowerCase()))
			this.chnlLogs = parseFromUploadCsv();
		else
			this.chnlLogs = parseFromUploadExcel();
		int err=compareLogs();
		outputResults(err);
		return err==0;
	}
	
	
	/** 存放比对相同的纪录 */
	private List<Map<String,String>> resultForOK=new ArrayList<>();
	/** 存放比对状态不同的纪录 */
	private List<Map<String,String>> resultForNotMatch=new ArrayList<>();
	/** 存放渠道不存在的纪录 */
	private List<Map<String,String>> resultForNotInChnl=new ArrayList<>();
	/** 存放自身状态不一的纪录 */
	//private List<Map<String,String>> resultForStateUnknow=new ArrayList<>();
	
	protected void outputResults(int err) {
		createWorkbook();
		createPpaySheet("★比对结果不同_"+resultForNotMatch.size(), this.resultForNotMatch, "*比对结果不同*");
		createPpaySheet("★上游无记录_"+this.resultForNotInChnl.size(), this.resultForNotInChnl, "*上游无此记录*");
		createChnlSheet("★PPay无记录_"+(this.chnlLogs.size()+this.chnlDupLogs.size()), "*PPAY无此记录*");
		createPpaySheet("比对结果相同_"+this.resultForOK.size(), this.resultForOK, "OK");
		downloadWorkBook(this.xlsBook, this.outputFileName, DEFAULT_CHARSET, this.response);
	}
	
	private Workbook xlsBook;
	private Sheet curSheet;
	private CellStyle xlsStyleHeader;
	private CellStyle xlsStyleStr;
	private CellStyle xlsStyleNum;
	private int curRowIndex=0;
	
	protected Workbook createWorkbook() {
		if (xlsBook!=null) return xlsBook;
		
		xlsBook = new HSSFWorkbook();
		
		// 创建单元格的 显示样式
		xlsStyleHeader = xlsBook.createCellStyle();
		xlsStyleHeader.setAlignment(CellStyle.ALIGN_CENTER); // 水平方向上的对其方式
		xlsStyleHeader.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直方向上的对其方式
		
		xlsStyleStr = xlsBook.createCellStyle();
		xlsStyleStr.setAlignment(CellStyle.ALIGN_LEFT); // 水平方向上的对其方式
		xlsStyleStr.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直方向上的对其方式
		
		// 创建单元格的 显示样式
		xlsStyleNum = xlsBook.createCellStyle();
		xlsStyleNum.setAlignment(CellStyle.ALIGN_RIGHT);
		xlsStyleNum.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直方向上的对其方式
		xlsStyleNum.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		
		return xlsBook;
	}
	
	protected void outputError(String message, Throwable t) {
		createWorkbook();
		Sheet sheet = createSheet("★错误★");
		sheet.setColumnWidth(0, 35 * 800);
		curRowIndex=0;
		Row headerRow = this.createHeaderRow();
		Cell cell;
		cell = createCell(headerRow, 0, xlsStyleStr, "【错误信息】"+message);
		
		if (t!=null) {
			for (StackTraceElement et: t.getStackTrace()) {
				Row row = this.createRow();
				cell = createCell(row, 0, xlsStyleStr, et.toString());
			}
		}
		
		downloadWorkBook(this.xlsBook, this.outputFileName, DEFAULT_CHARSET, this.response);
	}
	
	protected Cell createCell(Row row, int colIndex, CellStyle style, String text) {
		Cell cell;
		cell = row.createCell(colIndex);
		if (style!=null)
			cell.setCellStyle(style);
		cell.setCellValue(text);
		return cell;
	}
	

	
	protected Sheet createPpaySheet(String name, List<Map<String,String>> results, String defaultMsg) {
		// 创建工作表
		curSheet = xlsBook.createSheet(name);
		curRowIndex=0;
		Row header_row = createHeaderRow(curSheet,0); //curSheet.createRow(0);
		curRowIndex=1;
		
		Cell cell;
		int i = 0;
		for (ColumnInfo column : transLogs.getColumnLst()) {
			curSheet.setColumnWidth(i, 35 * Math.min(column.getWidth(), 120)); // 18*256
			cell = header_row.createCell(i);
			// 应用样式到 单元格上
			cell.setCellStyle(xlsStyleHeader);
			cell.setCellValue(column.getTitle());
			i++;
		}
		
		curSheet.setColumnWidth(i, 35 * 400);
		cell = header_row.createCell(i);
		cell.setCellStyle(xlsStyleHeader);
		cell.setCellValue("比对结果");
		
		SumHelper sumHelper = new SumHelper();
		int ci = 0;
		Row row;
		for(Map<String,String> dataRow : results) {
			String resMsg = dataRow.get(TxnLogFName.resultMsg);
			if (StringUtil.isBlank(resMsg))
				resMsg=defaultMsg;
			
			row = createRow(curSheet, curRowIndex);

			ci = 0;
			for (ColumnInfo column : transLogs.getColumnLst()) {
				if (Utils.isInSet(column.getField(), "transAmtDesc", "transFeeDesc", "transFeeChnlDesc", "transFeeDeltaDesc", "transAt", "transFee", "transFeeChnl", "transFeeDelta", "transAmt", "transAtDesc"))
				{
					sumHelper.addRow(curRowIndex, ci); //记录加总Cell
					setRowValueAsNumber(ci, dataRow.get(column.getField()), row, this.xlsStyleNum);
				}
				else {
					setRowValues(ci, dataRow.get(column.getField()), row, this.xlsStyleStr);
				}
				ci++;
			}
			
			//结果讯息栏位
			setRowValues(ci, resMsg, row, this.xlsStyleStr);
			
			curRowIndex++;
		}
		
		//自动加总
		row = createRow(curSheet, curRowIndex);
		ci = 0;
		for (ColumnInfo column : transLogs.getColumnLst()) {
			if (Utils.isInSet(column.getField(), "transAmtDesc", "transFeeDesc", "transFeeChnlDesc", "transFeeDeltaDesc", "transAt", "transFee", "transFeeChnl", "transFeeDelta", "transAmt", "transAtDesc"))
			{
				SumRef ref = sumHelper.getColSum(ci);
				if (ref!=null)
					setRowAsFormula(ci, ref, row, this.xlsStyleNum);
			}
			ci++;
		}
		
		//curSheet.setForceFormulaRecalculation(true);
		
		//自动宽度
		for(i=0; i<transLogs.getColumnLst().size(); i++)
			curSheet.autoSizeColumn(i, true);
		
		return curSheet;
	}
	
	protected Row createHeaderRow(Sheet sheet, int rowIndex) {
		Row row = sheet.createRow(rowIndex);
		row.setHeight((short) (12.75 * 28)); // 设置行高 基数为20
		return row;
	}
	
	protected Row createRow(Sheet sheet, int rowIndex) {
		Row row = sheet.createRow(rowIndex);
		row.setHeight((short) (12.75 * 24)); // 设置行高 基数为20
		return row;
	}
	protected Sheet createSheet(String name) {
		if (this.xlsBook==null)
			this.createWorkbook();
		this.curSheet = xlsBook.createSheet(name);
		this.curRowIndex=0;
		return this.curSheet;
	}
	protected Row createHeaderRow() {
		Row row = createHeaderRow(this.curSheet,this.curRowIndex);
		this.curRowIndex++;
		return row;
	}

	protected Row createRow() {
		Row row = createRow(this.curSheet,this.curRowIndex);
		this.curRowIndex++;
		return row;
	}
	
	
	protected Sheet createChnlSheet(String name, String defResMsg) {
		// 创建工作表
		this.createSheet(name);
		Row header_row = this.createHeaderRow();
		
		Cell cell;
		int i = 0;
		for (XlsColumnInfo column : chnlColumnList) {
			curSheet.setColumnWidth(i, column.getWidth()); // 18*256
			cell = createCell(header_row,i,xlsStyleHeader,column.getText());
			i++;
		}
		curSheet.setColumnWidth(i, 35 * 400);
		cell = createCell(header_row,i,xlsStyleHeader,"比对结果");
		
		//chnlLogs
		
		Row row;
		int ci = 0;
		SumHelper sumHelper = new SumHelper();
		
		for(String pk : chnlLogs.keySet()) {
			String resMsg = defResMsg;
			List<String> dataRow= chnlLogs.get(pk);
			row = this.createRow();
			ci = setChnlRowValues(row, dataRow, 0, sumHelper);
			setRowValues(ci, resMsg, row, this.xlsStyleStr);
		}
		
		//自动加总行
		row = this.createRow();
		SumRef ref = sumHelper.getColSum(ch_amt_cndx);
		if (ref!=null)
			setRowAsFormula(ch_amt_cndx, ref, row, this.xlsStyleNum);
		//curSheet.setForceFormulaRecalculation(true);
		
		for(i=0; i< chnlColumnList.size(); i++)
			curSheet.autoSizeColumn(i, true);
		
		
		if ((this.chnlDupLogs!=null) && (this.chnlDupLogs.size()>0)) {
			row = this.createRow(); //空一行
			row = this.createRow(); //Header
			cell = this.createCell(row, 0, this.xlsStyleStr, "★重复单号的记录★");
			for(List<String> dataRow: this.chnlDupLogs) {
				String resMsg = "**单号重复**";
				row = this.createRow();
				ci = setChnlRowValues(row, dataRow, 0, sumHelper);
				setRowValues(ci, resMsg, row, this.xlsStyleStr);
			}
		}

		return curSheet;
	}

	/**
	 * @param row
	 * @param dataRow
	 * @param sumHelper
	 * @return
	 */
	protected int setChnlRowValues(Row row, List<String> dataRow, int initColIndex, SumHelper sumHelper) {
		Cell cell;
		int ci=initColIndex;
		for(String cdata: dataRow) {
			XlsColumnInfo col=this.chnlColumnList.get(ci);
			if (this.ch_amt_cndx==ci) {
				sumHelper.addRow(curRowIndex-1, ci);
				Long chTransAmt = getChnlAmt(cdata);
				setRowValueAsNumber(ci, StringUtil.formateAmt(chTransAmt), row, this.xlsStyleNum);
			}
			else {
				cell = createCell(row,ci,xlsStyleStr,cdata);
			}
			ci++;
		}
		return ci;
	}

	/**
	 * 下载 Excel
	 * @param wb Excel Workbook
	 * @param fileName 文件名称
	 * @param charSet 字符集，默认为UTF-8
	 * @param response HttpServletResponse
	 */
	public void downloadWorkBook(Workbook wb, String fileName, String charSet, HttpServletResponse response) {
		AssertUtil.objIsNull(wb, "Excel工作簿内容为空(null).");
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
			throw new BizzException("下载Excel文件失败:" + fileName, e);
		} finally {
			IOUtil.close(os);
		}
	}

	
	protected int compareLogs() {
		List<Map<String,String>> list = transLogs.getResultList();
		//this.checkParam(transLogs.getColumnLst(), "transLogs.getColumnLst()");
		int err=0;
		
		for(Map<String, String> dataRow : list) {
			String chnlId =  dataRow.get(TxnLogFName.transChnl);
			String chnlOrderId= dataRow.get(TxnLogFName.chnlOrderId);
			String chnlTransId= dataRow.get(TxnLogFName.chnlTransId);
			String pkey=chnlOrderId;
			if ("1".equals(this.ch_pkey_type))
				pkey=chnlTransId;
			String chnlMchntCd= dataRow.get(TxnLogFName.chnlMchntCd);
			String stransAmt= dataRow.get(TxnLogFName.transAt);
			Long transAmt=this.convertAmount(stransAmt, 0L);
			
			String txnState= dataRow.get(TxnLogFName.txnState);
			String txnStateDesc= dataRow.get(TxnLogFName.txnState);
			String orderState= dataRow.get(TxnLogFName.orderState);
			
			// 检查是否状态不一致
			String stateMark = "";
			String orderToTxnState = TxnStateEnums.OrderStateToTxnState.get(orderState);
			if (!Utils.isInSet(orderToTxnState, txnState, null, ""))
				stateMark = "*自身状态不一致*";
			
			String resMsg="";
			List<String> chnlRow = this.chnlLogs.get(pkey);
			
			if (chnlRow==null) { //渠道不存在此笔交易
				resMsg= "*上游无此交易*";
				err++;
				resultForNotInChnl.add(dataRow);
			}
			else { //两边皆有纪录
				
				//先移除
				this.chnlLogs.remove(pkey);
				resMsg="";
				
				if (!Utils.isEmpty(stateMark)) { //自身状态不一致
					resMsg = stateMark+" ";
					//resultForStateUnknow.add(dataRow);
				}
				//比对渠道状态
				String ch_amt =chnlRow.get(this.ch_amt_cndx); // this.getCellValue(chnlRow, this.ch_amt_cndx); 
				Long chTransAmt = getChnlAmt(ch_amt);
				if (!chTransAmt.equals(transAmt)) {
					resMsg = resMsg+"*金额不同* ";
				}
				String ch_state =  chnlRow.get(this.ch_state_cndx); //this.getCellValue(chnlRow, this.ch_state_cndx);
				String chTxnState = this.getChnlTxnState(ch_state);
				if(!(""+chTxnState).equals(txnState)) {
					resMsg = resMsg+"*交易状态不同* ";
				}
				
				if (! StringUtil.isBlank(resMsg)) {
					//resMsg = String.format("%s 单号:%s, 渠道交易金额:%s, 渠道交易状态:%s ", resMsg, pkey, StringUtil.formateAmt(chTransAmt), ch_state);
					resMsg = String.format("%s 单号:%s, {交易金额:%s, 上游交易金额:%s}, {交易状态:%s, 上游交易状态:%s(=%s)} ", resMsg, pkey, 
							StringUtil.formateAmt(transAmt), StringUtil.formateAmt(chTransAmt), 
							txnStateDesc, ch_state, chTxnState
							);
					err++;
					resultForNotMatch.add(dataRow);
				}
				else {
					resMsg = "(比对相同)";
					resultForOK.add(dataRow);
				}
			}
			dataRow.put(TxnLogFName.resultMsg, resMsg);
		} // end for
		err= err+this.chnlLogs.size(); //渠道存在但我们没有的纪录
		debug("[compareLogs] 错误笔数:%s", err);
		return err;
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
	
	public Cell setRowAsFormula(int count, SumRef ref, Row row, CellStyle style) {
		Cell cell = row.createCell(count, Cell.CELL_TYPE_FORMULA);
		try {
			//this.debug("公式源： %s",ref);
			String f = String.format("SUM(%s%s%s)", ref.start.getCellReference().formatAsString(), ":", ref.end.getCellReference().formatAsString());
			//this.debug("公式： %s",f);
			cell.setCellFormula(f);
			if (style!=null) cell.setCellStyle(style);
		} catch (Exception e) {
			this.error("设定公式错误: %s", e.getMessage());
		}
		return cell;
	}
	
	protected String getChRowMarkText(Row row) {
		//this.ch_row_end_mark_cndx;
		if (row==null) return "NULL";
		Cell cell=row.getCell(this.ch_row_end_mark_cndx, Row.RETURN_BLANK_AS_NULL);
		if (cell==null) return "NULL";
		String cellText = this.getCellText(cell, null);
		if (StringUtil.isBlank(cellText)) return "NULL";
		return cellText;
	}
	
	protected String getChRowMarkText(String[] row) {
		//this.ch_row_end_mark_cndx;
		if (row==null) return "NULL";
		if (this.ch_row_end_mark_cndx>=row.length) return "NULL";
		String cellText = row[this.ch_row_end_mark_cndx];
		if (StringUtil.isBlank(cellText)) return "NULL";
		return cellText;
	}
	
	protected boolean isEndRow(Row row) {
		String markText = getChRowMarkText(row);
		return (isEndRow(markText));
	}
	
	protected boolean isEndRow(String[] row) {
		String markText = getChRowMarkText(row);
		return (isEndRow(markText));
	}
	
	protected boolean isEndRow(String markText) {
		if (ch_row_end_mark_pattern==null) {
			return "NULL".equals(markText);
		}
		else {
			String mark = this.capture(this.ch_row_end_mark_pattern, markText);
			return !Utils.isEmpty(mark);
		}
	}
	
	
	protected String getChnlPKey(String s) {
		String pk = this.capture(this.ch_pkey_pattern, s);
		return pk;
	}

	protected String getChnlTxnState(String s) {
		String chState = this.capture(this.ch_state_pattern, s);
		String state = chnlStatusMap.get(chState);
		if (Utils.isEmpty(state))
			state  = chnlStatusMap.get("*");
		return state;
	}
	
	protected Long getChnlAmt(String chAmt) {
		String amt = this.capture(this.ch_amt_pattern, chAmt);
		if ("1".equals(this.ch_amt_unit)) 
			return convertFromYuanAmount(amt, 0L);
		else
			return convertAmount(amt, 0L);
	}
	
	protected Long convertAmount(String amt, Long defaultVal) {
		if (Utils.isEmpty(amt)) return defaultVal;
		try {
			BigDecimal a = new BigDecimal(amt);
			return a.longValue();
		} catch (Exception e) {
			this.error("查询时，金额格式错误: '%s'", amt);
			return defaultVal;
		}
	}
	protected Long convertFromYuanAmount(String amt, Long defaultVal) {
		if (Utils.isEmpty(amt)) return defaultVal;
		try {
			BigDecimal a = new BigDecimal(amt).multiply(new BigDecimal(100));
			return a.longValue();
		} catch (Exception e) {
			this.error("查询时，金额格式错误: '%s'", amt);
			//throw new BizzException(RspCd.Z_1001, String.format("查询时，金额格式错误: '%s'", amt));
			return defaultVal;
		}
	}
	
	/**
	 * 剖析上傳的渠道對帳文件
	 * @return
	 * @throws Exception
	 */
	protected Map<String, List<String>> parseFromUploadCsv() throws Exception {
		if (uploadFile==null) return null;
		String uploadFilename= uploadFile.getOriginalFilename();
		if (Utils.isEmpty(uploadFilename))  return null;
		
		Map<String, List<String>> chnlLogs= new TreeMap<>();
		this.chnlDupLogs= new ArrayList<>();
		chnlColumnList= new ArrayList<>();
		
		InputStream fis= uploadFile.getInputStream();
		try {
			try (
				Reader reader = new InputStreamReader(fis, this.ch_file_encoding);
				CSVReader csvReader = new CSVReader(reader);
			){
				int i=0;
				String[] row;
				while ((row = csvReader.readNext()) != null) {
					boolean rowAdded;
					if (i>=this.ch_row_start_rndx && (this.ch_row_start_rndx>=0)) {
						if (this.isEndRow(row))
							break;
						rowAdded=addChnlRow(chnlLogs, this.chnlDupLogs, row);
						if ((i<=1) && rowAdded) {
							setColumnType(chnlColumnList, row);
						}
					}
					else if (i>=0 && (i==(this.ch_row_start_rndx-1))) {
						chnlHeaderRow = rowToList(row);
						if (chnlHeaderRow!=null)
							setColumnTitle(chnlColumnList, row);
					}
					i++;	
				}
			}
			
		} finally {
			if (fis!=null) fis.close();
		}
		return chnlLogs;
	}
	
	/**
	 * 剖析上傳的渠道對帳文件
	 * @return
	 * @throws Exception
	 */
	protected Map<String, List<String>> parseFromUploadExcel() throws Exception {
		if (uploadFile==null) return null;
		String uploadFilename= uploadFile.getOriginalFilename();
		if (Utils.isEmpty(uploadFilename))  return null;
		
		Map<String, List<String>> chnlLogs= new TreeMap<>();
		this.chnlDupLogs= new ArrayList<>();

		chnlColumnList= new ArrayList<>();
		
		InputStream fis= uploadFile.getInputStream();
		
		try {
			Workbook wb = null;
			wb = WorkbookFactory.create(fis);
			
			Sheet sheet = wb.getSheetAt(this.ch_sheet_index);
			Iterator<Row> rows = sheet.rowIterator();
			int i=0;
			while (rows.hasNext()) {
				Row row= rows.next();
				boolean rowAdded;
				if (i>=this.ch_row_start_rndx && (this.ch_row_start_rndx>=0)) {
					if (this.isEndRow(row))
						break;
					rowAdded=addChnlRow(chnlLogs, this.chnlDupLogs, row);
					//if (!rowAdded) 
					//	break;
					if ((i<=1) && rowAdded) {
						setColumnType(chnlColumnList, sheet, row);
					}
				}
				else if (i>=0 && (i==(this.ch_row_start_rndx-1))) {
					chnlHeaderRow = rowToList(row);
					if (chnlHeaderRow!=null)
						setColumnTitle(chnlColumnList, row);
					//chnlHeaderRow = row;
				}
				i++;
			}
		} finally {
			if (fis!=null) fis.close();
		}
		
		return chnlLogs;
	}
	
	private void setColumnTitle(List<XlsColumnInfo> chnlColumnList, Row row) {
		Iterator<Cell> cells = row.cellIterator();
		int ci=0;
		while(cells.hasNext()) {
			Cell cell= cells.next();
			String cellVal = getCellText(cell, "");
			XlsColumnInfo c=getXlsColumn(chnlColumnList, ci);
			c.setText(cellVal);
			ci++;
		}
	}
	
	private void setColumnTitle(List<XlsColumnInfo> chnlColumnList, String[] row) {
		int ci=0;
		for(int i=0; i<row.length; i++) {
			XlsColumnInfo xlsCol=getXlsColumn(chnlColumnList, ci);
			String cv=strVal(row[ci]);
			xlsCol.setText(cv);
			ci++;
		}
	}
	
	
	private void setColumnType(List<XlsColumnInfo> chnlColumnList, Sheet sheet , Row row) {
		Iterator<Cell> cells = row.cellIterator();
		int ci=0;
		while(cells.hasNext()) {
			Cell cell= cells.next();
			XlsColumnInfo xlsCol=getXlsColumn(chnlColumnList, ci);
			xlsCol.setWidth(sheet.getColumnWidth(ci));
			if (cell!=null) {
				xlsCol.setCellStyle(cell.getCellStyle());
				xlsCol.setCellType(cell.getCellType());
			}
			ci++;
		}
	}
	
	private void setColumnType(List<XlsColumnInfo> chnlColumnList, String[] row) {
		//Cell.
		int ci=0;
		for(int i=0; i<row.length; i++) {
			XlsColumnInfo xlsCol=getXlsColumn(chnlColumnList, ci);
			String cv=strVal(row[ci]);
			xlsCol.setWidth(210*Math.min(cv.length(), 16));
			if (this.isMoneyType(cv)) {
				xlsCol.setCellType(Cell.CELL_TYPE_NUMERIC);
			}
			else {
				xlsCol.setCellType(Cell.CELL_TYPE_STRING);
			}
			ci++;
		}
	}

	

	protected XlsColumnInfo getXlsColumn(List<XlsColumnInfo> chnlColumnList, int index) {
		XlsColumnInfo c;
		while (index>=chnlColumnList.size()) {
			c= new XlsColumnInfo();
			chnlColumnList.add(c);
		}
		c= chnlColumnList.get(index);
		if (c==null) {
			c= new XlsColumnInfo();
			chnlColumnList.set(index, c);
		}
		return c;
	}

	private boolean addChnlRow(Map<String, List<String>> chnlLogs, List<List<String>> chnlDupLogs, Row row) {
		//int rn= row.getRowNum();
		List<String> record= rowToList(row);
		return addChnlRecord(chnlLogs, chnlDupLogs, record);
	}
	
	private boolean addChnlRow(Map<String, List<String>> chnlLogs, List<List<String>> chnlDupLogs, String[] row) {
		List<String> record= rowToList(row);
		return addChnlRecord(chnlLogs, chnlDupLogs, record);
	}

	/**
	 * @param chnlLogs
	 * @param record
	 * @return
	 */
	protected boolean addChnlRecord(Map<String, List<String>> chnlLogs, List<List<String>> chnlDupLogs, List<String> record) {
		//debug("[addChnlRow] chnlRow: ",list);
		if ((record==null) || (record.size()<=1)) 
			return false;
		//this.debug("add chnl record: ", record);

		if (filterChnlRecord(record)) {
			if (this.ch_pkey_cndx >= record.size())
				this.throwError(RspCd.Z_7015, "上游数据格式错误(index out of bound.)");
			
			String pkey= this.getChnlPKey(record.get(this.ch_pkey_cndx));
			this.debug("record added: %s", pkey);
			
			if (chnlLogs.containsKey(pkey)) {
				//this.throwError(RspCd.Z_7015, "上游交易记录重复: 平台流水号="+pkey);
				chnlDupLogs.add(record);
			}
			//debug("[addChnlRow] chnlRow has added as key: '%s'.", pkey);
			chnlLogs.put(pkey, record);
		}
		return true;
	}
	
	protected boolean filterChnlRecord(List<String> record) {
		if (record==null) return false;
		if (this.ch_filter_cndx<0) return true;
		if (this.ch_filter_pattern==null) return true;
		String val ="NULL";
		if (ch_filter_cndx<record.size())
			val = strVal(record.get(ch_filter_cndx), "NULL");
		if (Utils.isEmpty(val)) val = "NULL";
		//return this.find(this.ch_filter_pattern, val);
		return !Utils.isEmpty(this.capture(this.ch_filter_pattern, val));
	}
	
	protected void debug(String prefix, List list) {
		StringBuilder buf = new StringBuilder(strVal(prefix)).append("{");
		int r=0;
		if (list!=null)
			for(Object o:list) {
				buf.append(strVal(o)).append(", ");
				r++;
			}
		if (r>0) buf.delete(buf.length()-2, buf.length());
		buf.append("}");
		this.debug(buf.toString());
	}
	
	protected  List<String> rowToList(String[] row){
		List<String> list= new ArrayList<>();
		if (row==null) return list;
		for(int i=0; i<row.length; i++)
			list.add(row[i]);
		return list;
	}

	
	protected  List<String> rowToList(Row row){
		List<String> list= new ArrayList<>();
		Iterator<Cell> cells = row.cellIterator();
		int countOfNonEmpty=0;
		while(cells.hasNext()) {
			Cell cell= cells.next();
			String cellVal = getCellText(cell, "");
			if (!StringUtil.isBlank(cellVal)) countOfNonEmpty++;
			list.add(strVal(cellVal));
		}
		if (countOfNonEmpty>0)
			return list;
		else
			return null;
	}
	
	public static String getCellText(Cell cell, String defVal) {
		if (cell==null) return defVal;
		try {
			DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
			String s = formatter.formatCellValue(cell); //Returns the formatted value of a cell as a String regardless of the cell type.
			return s;
		} catch (Exception e) {
			return defVal;
		}
	}
	
	protected String getCellValue(Row row, int cellIndex) {
		if (row==null) return "";
		Cell cell = row.getCell(cellIndex);
		return getCellText(cell, "");
	}

	/** 渠道ID */
	private String channelId="";
	private TxnCatalog txnCat=TxnCatalog.CONSUME;
	private String transTypeCatlog="01";
	private int ch_sheet_index=0;
	
	protected static final String DEFAULT_CAT="ChnlLog.Compare";
	protected static final String DEFAULT_MER="#DEFAULT#";
	protected static final String DEFAULT_CHARSET="utf-8";
	public static final int REGEX_OPTION=Pattern.DOTALL|Pattern.UNICODE_CASE|Pattern.UNICODE_CHARACTER_CLASS;
	
	//private String curParamCat=DEFAULT_CAT;

	/** 是否支持 多重配置 */
	private boolean multi_config=false;

	/** 渠道文件格式: xls, xlsx, csv */
	private String ch_file_fmt="xls";
	/** 渠道文件編碼: UTF-8,GB2312 */
	private String ch_file_encoding="gb2312";
	
	/** 渠道文件 Row 起始*/
	private int ch_row_start_rndx=-1;
	
	/** 渠道文件 Row结束符号判断的栏位 */
	private int ch_row_end_mark_cndx=0;
	
	/** 渠道文件 Row结束符号判断的Regex */
	private String ch_row_end_mark_regex=""; //"范例: (NULL|结束行)"
	private Pattern ch_row_end_mark_pattern= null; 
	
	/** 渠道文件 主键字段 索引 */
	private int ch_pkey_cndx = -1;
	/** 渠道文件 金额字段 索引*/
	private int ch_amt_cndx = -1;
	/** 渠道文件 交易状态字段 索引*/
	private int ch_state_cndx = -1;
	
	/** 渠道文件 金额单位： 0=分, 1=元*/
	private String ch_amt_unit="0"; 
	
	/** 渠道文件 主鍵類型： 0=渠道訂單號，1=渠道系統流水號 */
	private String ch_pkey_type="0";
	
	/** 渠道文件 主键字段 Regex: '([0-9a-zA-Z]+)'  */
	private String ch_pkey_regex = "([0-9a-zA-Z]+)";
	private Pattern ch_pkey_pattern= null;
	
	/** 渠道文件 金额字段 Regex: '([0-9\.]+)'  */
	private String ch_amt_regex = "([0-9\\.]+)";
	private Pattern ch_amt_pattern= null;
	
	/** 渠道文件 交易状态字段 Regex: '(成功|失败|处理中)'  */
	private String ch_state_regex = "(成功|失败|处理中)";
	private Pattern ch_state_pattern= null;
	
	/** 渠道文件 交易状态对应 */
	private String ch_state_map = "成功=10;失败=20;*=01";
	
	/** 渠道文件记录过滤栏位索引 */
	private int ch_filter_cndx = -1;
	/** 渠道文件记录过滤栏位索引Regex,仅ch_filter_cndx>=0有效 */
	private String ch_filter_regex = "(成功)";
	private Pattern ch_filter_pattern= null;
	
	
	private Map<String,String> chnlStatusMap=null;
	//private Map<String,String> chnlSheetMap=null;
	
	private Map<String, Integer> columnIndex= null;
	protected int colIndexByName(String fieldName) {
		if (columnIndex==null) {
			int i=0;
			for (ColumnInfo column : transLogs.getColumnLst()) {
				columnIndex.put(column.getField(), i);
				i++;
			}
		}
		Integer ndx= columnIndex.get(fieldName);
		return ndx==null ? -1 : ndx;
	}
	
	static class TxnLogFName{
		public static final String  transChnl = "transChnl";
		public static final String  intTransCd = "intTransCd";
		public static final String  chnlMchntCd = "chnlMchntCd";
		public static final String  chnlOrderId = "chnlOrderId";
		public static final String  chnlTransId = "chnlTransId";
		public static final String  txnState = "txnState";
		public static final String  txnStateDesc = "txnStateDesc";
		public static final String  orderState = "orderState";
		public static final String  transSeqId = "transSeqId";
		public static final String  transAt = "transAt";
		public static final String  resultMsg = "resultMsg";
	}
	
	public String capture(Pattern p, String value) {
		if ((p==null) || (value==null)) return null;
		if (Utils.isEmpty(value)) return "";
		Matcher m=p.matcher(value);
		if (!m.find()) return "";
		return m.group(0);
	}
	
	public boolean match(Pattern p, String value) {
		if ((p==null) || (value==null)) return false;
		if (Utils.isEmpty(value)) return false;
		Matcher m=p.matcher(value);
		return m.matches();
	}

	public boolean find(Pattern p, String value) {
		if ((p==null) || (value==null)) return false;
		if (Utils.isEmpty(value)) return false;
		Matcher m=p.matcher(value);
		return m.find();
	}
	
	protected static Pattern MONEY_PATTERN= Pattern.compile("([0-9]+\\.[0-9][0-9])", REGEX_OPTION);
	public boolean isMoneyType(String s) {
		return this.find(MONEY_PATTERN, s);
	}

	private void debugConfig() {
		/** 是否支持 多重配置 */
		this.debug("multi_config= %s",multi_config);

		/** 渠道文件格式: xls, xlsx, csv */
		this.debug("ch_file_fmt= %s",ch_file_fmt);
		/** 渠道文件編碼: UTF-8,GB2312 */
		this.debug("ch_file_encoding= %s",ch_file_encoding);
		
		/** 渠道文件 Row 起始*/
		this.debug("ch_row_start_rndx= %s",ch_row_start_rndx);
		
		/** 渠道文件 Row结束符号判断的栏位 */
		this.debug("ch_row_end_mark_cndx= %s",ch_row_end_mark_cndx);
		
		/** 渠道文件 Row结束符号判断的Regex */
		this.debug("ch_row_end_mark_regex= %s",ch_row_end_mark_regex); //"范例: (NULL|结束行)"
		this.debug("ch_row_end_mark_pattern= %s",ch_row_end_mark_pattern); 
		
		/** 渠道文件 主键字段 索引 */
		this.debug("ch_pkey_cndx= %s",ch_pkey_cndx);
		/** 渠道文件 金额字段 索引*/
		this.debug("ch_amt_cndx= %s",ch_amt_cndx);
		/** 渠道文件 交易状态字段 索引*/
		this.debug("ch_state_cndx= %s",ch_state_cndx);
		
		/** 渠道文件 金额单位： 0=分, 1=元*/
		this.debug("ch_amt_unit= %s",ch_amt_unit); 
		
		/** 渠道文件 主鍵類型： 0=渠道訂單號，1=渠道系統流水號 */
		this.debug("ch_pkey_type= %s",ch_pkey_type);
		
		/** 渠道文件 主键字段 Regex: '([0-9a-zA-Z]+)'  */
		this.debug("ch_pkey_regex= %s",ch_pkey_regex);
		this.debug("ch_pkey_pattern= %s",ch_pkey_pattern);
		
		/** 渠道文件 金额字段 Regex: '([0-9\.]+)'  */
		this.debug("ch_amt_regex= %s",ch_amt_regex);
		this.debug("ch_amt_pattern= %s",ch_amt_pattern);
		
		/** 渠道文件 交易状态字段 Regex: '(成功|失败|处理中)'  */
		this.debug("ch_state_regex= %s",ch_state_regex);
		this.debug("ch_state_pattern= %s",ch_state_pattern);
		
		/** 渠道文件 交易状态对应 */
		this.debug("ch_state_map= %s",ch_state_map);
		
		/** 渠道文件记录过滤栏位索引 */
		this.debug("ch_filter_cndx= %s",ch_filter_cndx);
		/** 渠道文件记录过滤栏位索引Regex,仅ch_filter_cndx>=0有效 */
		this.debug("ch_filter_regex= %s",ch_filter_regex);
		this.debug("ch_filter_pattern= %s",ch_filter_pattern);		
	}
	
	private void initConfig() throws UnsupportedEncodingException {
		List<Map<String,String>> list = transLogs.getResultList();
		this.checkQueryError(list, "查无数据");
		this.checkDataError(list.size(), null, "查无数据");
		this.checkParam(transLogs.getColumnLst(), "transLogs.getColumnLst()");
		
		//获取 channleId
		Map<String, String> dataRow= list.get(0);
		channelId =  dataRow.get(TxnLogFName.transChnl);
		this.checkParam(channelId, "channelId");
		
		//获取 交易分类
		String intTransType=  dataRow.get(TxnLogFName.intTransCd);
		txnCat = Constant.getTxnCatalog(intTransType);
		
		//设定哪个 sheet
		this.ch_sheet_index=0;
		String ch_sheet_map= this.merDefaultParam("ch_sheet_map", "");
		this.ch_sheet_index = getSheetIndex(txnCat, ch_sheet_map);
		
		//设定是否多重配置
		this.multi_config=Boolean.parseBoolean(this.merDefaultParam("multi_config","false"));
		
		//设定过滤条件
		//ch_filter_cndx
		ch_filter_cndx = Integer.parseInt(this.merParam("ch_filter_cndx","-1"));
		ch_filter_regex = this.merParam("ch_filter_regex", "");
		if (! StringUtil.isBlank(ch_filter_regex))
			ch_filter_pattern = Pattern.compile(ch_filter_regex, REGEX_OPTION);
		
		ch_file_fmt = this.merParam("ch_file_fmt", ch_file_fmt);
		ch_file_encoding= this.merParam("ch_file_encoding", ch_file_encoding);
		ch_row_start_rndx = Integer.parseInt(this.merParam("ch_row_start_rndx","1"));
		
		ch_row_end_mark_cndx = Integer.parseInt(this.merParam("ch_row_end_mark_cndx","0"));
		ch_row_end_mark_regex = this.merParam("ch_row_end_mark_regex", ch_row_end_mark_regex);
		if (! StringUtil.isBlank(ch_row_end_mark_regex))
			ch_row_end_mark_pattern = Pattern.compile(ch_row_end_mark_regex, REGEX_OPTION);
		
		ch_pkey_cndx = Integer.parseInt(this.merParam("ch_pkey_cndx"));
		ch_amt_cndx = Integer.parseInt(this.merParam("ch_amt_cndx"));
		ch_state_cndx = Integer.parseInt(this.merParam("ch_state_cndx"));
		
		ch_amt_unit= this.merParam("ch_amt_unit", ch_amt_unit);
		ch_pkey_type= this.merParam("ch_pkey_type", ch_pkey_type);
		
		ch_pkey_regex= this.merParam("ch_pkey_regex", ch_pkey_regex);
		ch_pkey_pattern = Pattern.compile(ch_pkey_regex, REGEX_OPTION);
		
		ch_amt_regex= this.merParam("ch_amt_regex", ch_amt_regex);
		ch_amt_pattern = Pattern.compile(ch_amt_regex, REGEX_OPTION);
		
		ch_state_regex= this.merParam("ch_state_regex", ch_state_regex);
		ch_state_pattern = Pattern.compile(ch_state_regex, REGEX_OPTION);
		
		ch_state_map= this.merParam("ch_state_map", ch_state_map);
		chnlStatusMap = this.statusConfigToMap(ch_state_map, DEFAULT_CHARSET);
		
		debugConfig();
		
		if ((ch_row_start_rndx<0) || (ch_pkey_cndx<0))
			this.throwError(RspCd.Z_0502, String.format("读取配置文件错误", this.channelId));
	}

	/**
	 * @param txnCat
	 * @throws UnsupportedEncodingException
	 */
	protected int getSheetIndex(TxnCatalog txnCat, String ch_sheet_map) throws UnsupportedEncodingException {
		int sheetIndex=0;
		if (txnCat!=null) {
			this.transTypeCatlog = txnCat.getCode();
			if (!Utils.isEmpty(ch_sheet_map)) {
				Map<String,String> chnlSheetMap = this.statusConfigToMap(ch_sheet_map, DEFAULT_CHARSET);
				String sSheetNdx=chnlSheetMap.get(this.transTypeCatlog);
				if (Utils.isEmpty(sSheetNdx))
					sSheetNdx=chnlSheetMap.get("*");
				try {
					sheetIndex= Integer.parseInt(sSheetNdx);
				} catch (Exception e) {
				}
			}
		}
		return sheetIndex;
	}
	
	private static final String NOT_EXISTS="__NOT_EXISTS__";
	
	
	public String merDefaultParam(String paramId, String defaultVal)
	{
		return MerParamsClient.getParam(this.channelId, DEFAULT_MER, DEFAULT_CAT, paramId, defaultVal);
	}

	public String merDefaultParam(String paramId)
	{
		return MerParamsClient.getParam(this.channelId, DEFAULT_MER, DEFAULT_CAT, paramId, true);
	}
	
	public String merParam(String paramId, String defaultVal)
	{
		if ((!multi_config)||(txnCat.equals(TxnCatalog.CONSUME))) 
			return merDefaultParam(paramId, defaultVal);
		String paramCat= DEFAULT_CAT+"."+txnCat.getCode();
		String ret= MerParamsClient.getParam(this.channelId, DEFAULT_MER, paramCat, paramId, NOT_EXISTS);
		if (NOT_EXISTS.equals(ret))
			return merDefaultParam(paramId, defaultVal);
		else
			return ret;
	}

	public String merParam(String paramId)
	{
		if ((!multi_config)||(txnCat.equals(TxnCatalog.CONSUME))) 
			return merDefaultParam(paramId);
		String paramCat= DEFAULT_CAT+"."+txnCat.getCode();
		String ret= MerParamsClient.getParam(this.channelId, DEFAULT_MER, paramCat, paramId, NOT_EXISTS);
		//throw new ChnlBizException(RspCd.Z_1002, String.format("缺失参数 (channel=%s, merId=%s, catalog=%s, paramId=%s)", channel, merId, catalog, paramId));
		if (NOT_EXISTS.equals(ret)) {
			ret= merDefaultParam(paramId, NOT_EXISTS);
			if (NOT_EXISTS.equals(ret))
				throw new ChnlBizException(RspCd.Z_1002, String.format("缺失参数 (channel=%s, merId=%s, catalog=%s, paramId=%s)", this.channelId, DEFAULT_MER, paramCat, paramId));
		}
		return ret;
	}
	
//	public String merParam(String paramId, String defaultVal)
//	{
//		if ((!multi_config)||(txnCat.equals(TxnCatalog.CONSUME))) 
//			return merDefaultParam(paramId, defaultVal);
//		String paramCat= DEFAULT_CAT+"."+txnCat.getCode();
//		return MerParamsClient.getParam(this.channelId, DEFAULT_MER, paramCat, paramId, defaultVal);
//	}
//
//	public String merParam(String paramId)
//	{
//		if ((!multi_config)||(txnCat.equals(TxnCatalog.CONSUME))) 
//			return merDefaultParam(paramId);
//		String paramCat= DEFAULT_CAT+"."+txnCat.getCode();
//		return MerParamsClient.getParam(this.channelId, DEFAULT_MER, paramCat, paramId, true);
//	}
	
	protected Map<String, String> statusConfigToMap(String qryStr, String charset) throws UnsupportedEncodingException {
		Map<String, String> map=new LinkedHashMap<>();
		String[] pairs = qryStr.split(";");
		for (String pair : pairs) {
			if (!StringUtil.isBlank(pair)) {
			    int idx = pair.indexOf("=");
			    String key = idx > 0 ? pair.substring(0, idx) : pair;
			    String value = idx > 0 && pair.length() > idx + 1 ? pair.substring(idx + 1) : null;
			    map.put(key, value);
			}
		}
		return map;
	}
	
	@Override
	protected String getLogPrefix() {
		String chmsg= Utils.isEmpty(this.channelId) ? "" : String.format("[%s]", this.channelId);
		return "[LogCompareTool]"+chmsg;
	}
	
	
	public static class XlsColumnInfo implements Serializable {
		public XlsColumnInfo() {
		}
				
		public XlsColumnInfo(int cellType, CellStyle cellStyle, int width, String text) {
			super();
			this.cellType = cellType;
			this.cellStyle = cellStyle;
			this.width = width;
			this.text = text;
		}
		
		
		public int getCellType() {
			return cellType;
		}
		public void setCellType(int cellType) {
			this.cellType = cellType;
		}
		public CellStyle getCellStyle() {
			return cellStyle;
		}
		public void setCellStyle(CellStyle cellStyle) {
			this.cellStyle = cellStyle;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public CellStyle getNewCellStyle(Workbook wsBook, CellStyle defaultStyle) {
			if (newCellStyle==null) {
				if (cellStyle==null) return defaultStyle;
				newCellStyle = wsBook.createCellStyle();
				if (cellStyle!=null) {
					newCellStyle = cloneStyle(cellStyle, newCellStyle, defaultStyle);
				}
			}
			return newCellStyle;
		}
		
		protected CellStyle cloneStyle(CellStyle src, CellStyle dest, CellStyle defaultStyle) {
			if (dest==null) return null;
			if (src==null) src=defaultStyle;
			if (src==null) return dest;
			try {
				dest.cloneStyleFrom(src);
			} catch (Exception e) {
				if (src!=defaultStyle)
					try {
						dest.cloneStyleFrom(defaultStyle);
					} catch (Exception e2) {
					}
			}
			return dest;
		}

		private int cellType; 
		private CellStyle cellStyle;
		private CellStyle newCellStyle;
		private int width=100;
		private String text;
	}
	
	
	public static class Ref{
		public Ref() {
			super();
		}
		public Ref(int row, int col) {
			this();
			this.row = row;
			this.col = col;
		}
		
		public CellReference getCellReference() {
			return new CellReference(this.row, this.col, false, false);
		}
		
		public int getRow() {
			return row;
		}
		public void setRow(int row) {
			this.row = row;
		}
		public int getCol() {
			return col;
		}
		public void setCol(int col) {
			this.col = col;
		}
		public void set(int row, int col) {
			this.row=row;
			this.col=col;
		}
		private int row;
		private int col;
		@Override
		public String toString() {
			return String.format("(%s,%s)",row,col);
		}
		
	}
	
	public static class SumRef{
		@Override
		public String toString() {
			return String.format("(%s-%s)", start,end);
		}
		public SumRef() {
			this(new Ref(Integer.MAX_VALUE,Integer.MAX_VALUE), new Ref(0,0));
		}
		public SumRef(Ref start, Ref end) {
			super();
			this.start = start;
			this.end = end;
		}
		public SumRef(int row, int col) {
			this(new Ref(row,col), new Ref(row,col));
		}
		
		public void addRow(int r, int c) {
			if (r<start.getRow()) {
				start.set(r, c);
			}
			if (r>end.getRow()) {
				end.set(r,c);
			}
		}
		
		public void addCol(int r, int c) {
			if (c<start.getCol()) {
				start.set(r,c);
			}
			if (c>end.getCol()) {
				end.set(r,c);
			}
		}
		
		public Ref getStart() {
			return start;
		}
		public void setStart(Ref start) {
			this.start = start;
		}
		public Ref getEnd() {
			return end;
		}
		public void setEnd(Ref end) {
			this.end = end;
		}
		private Ref start=null;
		private Ref end=null;
	}
	
	public static class SumHelper{
		private Map<Integer, SumRef> colSum = new HashMap<>();
		private Map<Integer, SumRef> rowSum = new HashMap<>();
		
		public SumRef getColSum(int col) {
			return colSum.get(col);
		}
		
		public SumRef getRowSum(int col) {
			return rowSum.get(col);
		}
		
		public void addRow(int r, int c) {
			SumRef sref = colSum.get(c);
			if (sref==null) {
				sref=new SumRef(r,c);
				colSum.put(c, sref);
			}
			sref.addRow(r, c);
		}
		public void addCol(int r, int c) {
			SumRef sref = rowSum.get(r);
			if (sref==null) {
				sref=new SumRef(r,c);
				rowSum.put(r, sref);
			}
			sref.addCol(r, c);
		}
	}
	
}
