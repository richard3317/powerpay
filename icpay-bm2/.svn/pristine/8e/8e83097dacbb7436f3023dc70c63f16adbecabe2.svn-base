package com.icpay.payment.bm.bo;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.icpay.payment.bm.web.util.EntryUtil;
import com.icpay.payment.bm.web.util.ListUtils;
import com.icpay.payment.bm.web.util.MapToPojoUtil;
import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.enums.ProfitEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.IOUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountHour;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentProfitQueryBean;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentProfitQuerySummary;
import com.icpay.payment.db.dao.mybatis.model.modelExt.DailyProfitResultChnlTrans;
import com.icpay.payment.db.dao.mybatis.model.modelExt.DailyProfitResultEmployee;
import com.icpay.payment.db.dao.mybatis.model.modelExt.DailyProfitResultHuanbi;
import com.icpay.payment.db.dao.mybatis.model.modelExt.DailyProfitResultManager;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MchntProfitToMerBean;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MchntProfitToWeekBean;
import com.icpay.payment.db.dao.mybatis.model.modelExt.MchntTransHuanbiBean;

@Component("mchntProfitBO")
public class MchntProfitBO  extends BaseBO {

	/**
	 * 转换成浮点数金额
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
	
	/**
	 * 商户环比报表
	 * @param fileList
	 * @param fileName
	 * @param charSet
	 * @param response
	 */
	public void doMchntTransFile(List<Map<String, Object>> fileList,String fileName,String charSet, HttpServletResponse response) {
		//定义表头
		String[] title={"商户号","商户名","日交易额(元)","昨日交易额(元)","日环比"};
		//创建excel工作簿
		HSSFWorkbook workbook=new HSSFWorkbook();
		//创建工作表sheet
		HSSFSheet sheet=workbook.createSheet("小商户环比报表" );
		
		//创建单元格，并设置值表头 设置表头居中  
		HSSFCellStyle style = workbook.createCellStyle(); 
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平
		
		//创建第一行
		HSSFRow row=sheet.createRow(0);
		HSSFCell cell=null;
		//插入第一行数据的表头
		for(int i=0;i<title.length;i++){
		    cell=row.createCell(i);
		    cell.setCellValue(title[i]);
		    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		}
		
		//写入数据
		for (int i=0; i< fileList.size();i++){
		    HSSFRow nrow=sheet.createRow(i+1);
		    HSSFCell ncell=nrow.createCell(0);
		    ncell.setCellValue(fileList.get(i).get("mchntCd").toString());
		    ncell=nrow.createCell(1);
		    ncell.setCellValue(fileList.get(i).get("mchntCnNm").toString());
		    ncell=nrow.createCell(2);
		    ncell.setCellValue(fileList.get(i).get("txnAmtSum").toString());
		    ncell=nrow.createCell(3);
		    ncell.setCellValue(fileList.get(i).get("txnAmtSumOld").toString());
		    ncell=nrow.createCell(4);
		    ncell.setCellValue((fileList.get(i).get("txnAmtSumPercent").toString()));
		}
		downloadWorkBook(workbook, fileName, charSet, response);
	}
	
	public List<Map<String, Object>> genFileList(List<MchntTransHuanbiBean> list,List<MchntTransHuanbiBean> oldList,String settleDate) {
		return getHuanbiByMchntTrans(list,oldList,settleDate);
	}
	
	/***
	 * 导出 日报表，周报表
	 */
	public void exportTransProfitExcel(List list ,List beforeList,String settleDate ,String filePath, String fileName, String charSet,
			HttpServletResponse response) {
		//判断是下载什么文件
		String [] nameArray = fileName.split("-");
		if ("daily_trans_profit".equals(nameArray[0])) { //日报表
			doMchntProfitToMerFile(list, fileName, charSet, response);
		}else if("week_trans_profit".equals(nameArray[0])) { //周报表
			doMchntProfitToWeekFile(list,filePath, fileName, charSet, response);
		}else {//商户环比报表
			List<Map<String, Object>> fileList = genFileList(list,beforeList,settleDate);
			//写入文件中
			doMchntTransFile(fileList,fileName,charSet,response);
		}
	}
	/*
	 * 
	 * 写入商户财报文件
	 */
	@SuppressWarnings("deprecation")
	public void doMchntProfitToMerFile(List<MchntProfitToMerBean> list, String fileName, String charSet,
			HttpServletResponse response) {
			//定义表头
			String[] title={"渠道编号","渠道名称","商户号","商户名","日交易额(元)","日分润(元)","日环比","渠道日交易额(元)","渠道日分润(元)","渠道日环比"};
			//创建excel工作簿
			HSSFWorkbook workbook=new HSSFWorkbook();
			//创建工作表sheet
			HSSFSheet sheet=workbook.createSheet("每日财报" );
			
			//创建单元格，并设置值表头 设置表头居中  
			HSSFCellStyle style = workbook.createCellStyle(); 
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平
			
			//创建第一行
			HSSFRow row=sheet.createRow(0);
			HSSFCell cell=null;
			//插入第一行数据的表头
			for(int i=0;i<title.length;i++){
			    cell=row.createCell(i);
			    cell.setCellValue(title[i]);
			    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			}
			int count  = 0;
			//写入数据
			for (int i=0; i< list.size();i++){
			    HSSFRow nrow=sheet.createRow(i+1);
			    HSSFCell ncell=nrow.createCell(0);
			    ncell.setCellValue(list.get(i).getChnlId());
			    ncell=nrow.createCell(1);
			    ncell.setCellValue(list.get(i).getChnlDesc());
			    ncell=nrow.createCell(2);
			    ncell.setCellValue("*");
			    ncell=nrow.createCell(3);
			    ncell.setCellValue(list.get(i).getMchntCnNm());
			    ncell=nrow.createCell(4);
			    ncell.setCellValue((list.get(i).getTxnAmtSum()));
			    ncell=nrow.createCell(5);
			    ncell.setCellValue(list.get(i).getAgentProfit());
			    ncell=nrow.createCell(6);
			    ncell.setCellValue(list.get(i).getTxnAmtSumPercent());
			    
			    //合并列
			    if(i != list.size()-1 && !(list.get(i).getChnlId().equals(list.get(i+1).getChnlId()))) {
			    	 sheet.addMergedRegion(new CellRangeAddress(i+1-count, i+1, 7, 7));
			    	 sheet.addMergedRegion(new CellRangeAddress(i+1-count, i+1, 8, 8));
			    	 sheet.addMergedRegion(new CellRangeAddress(i+1-count, i+1, 9, 9));
			    	 ncell=nrow.createCell(7);
					 ncell.setCellValue(list.get(i).getChnlTxnAmtSum());
					 ncell=nrow.createCell(8);
					 ncell.setCellValue(list.get(i).getChnlAgentProfit());
					 ncell=nrow.createCell(9);
					 ncell.setCellValue(list.get(i).getChnlTxnAmtSumPercent());
					 count = 0;
			    }else if(i == list.size()-1){
			    	 sheet.addMergedRegion(new CellRangeAddress(i+1-count, i+1, 7, 7));
					 sheet.addMergedRegion(new CellRangeAddress(i+1-count, i+1, 8, 8));
				     sheet.addMergedRegion(new CellRangeAddress(i+1-count, i+1, 9, 9));
				     ncell=nrow.createCell(7);
					 ncell.setCellValue(list.get(i).getChnlTxnAmtSum());
					 ncell=nrow.createCell(8);
					 ncell.setCellValue(list.get(i).getChnlAgentProfit());
					 ncell=nrow.createCell(9);
					 ncell.setCellValue(list.get(i).getChnlTxnAmtSumPercent());
					 count = 0;
			    }else {
			    	 ncell=nrow.createCell(7);
					 ncell.setCellValue(list.get(i).getChnlTxnAmtSum());
					 ncell=nrow.createCell(8);
					 ncell.setCellValue(list.get(i).getChnlAgentProfit());
					 ncell=nrow.createCell(9);
					 ncell.setCellValue(list.get(i).getChnlTxnAmtSumPercent());
			    	count ++;
			    }
			}
			downloadWorkBook(workbook, fileName, charSet, response);
	}
	/**
	 * 周报表
	 * @param list
	 * @param fileName
	 * @param charSet
	 * @param response
	 */
	public void doMchntProfitToWeekFile(List<MchntProfitToWeekBean> list, String filePath, String fileName, String charSet,
			HttpServletResponse response) {
		//定义表头
		String[] title={"商户号","商户名","本周交易额(元)","上周交易额(元)","周环比"};
		//创建excel工作簿
		HSSFWorkbook workbook=new HSSFWorkbook();
		//创建工作表sheet
		HSSFSheet sheet=workbook.createSheet("每周财报");
		
		//创建单元格，并设置值表头 设置表头居中  
		HSSFCellStyle style = workbook.createCellStyle(); 
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平
		
		//创建第一行
		HSSFRow row=sheet.createRow(0);
		HSSFCell cell=null;
		//插入第一行数据的表头
		for(int i=0;i<title.length;i++){
		    cell=row.createCell(i);
		    cell.setCellValue(title[i]);
		    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		}
		//写入数据
		for (int i=0; i< list.size();i++){
		    HSSFRow nrow=sheet.createRow(i+1);
		    HSSFCell ncell=nrow.createCell(0);
		    ncell.setCellValue(list.get(i).getMchntCd());
		    ncell=nrow.createCell(1);
		    ncell.setCellValue(list.get(i).getMchntCnNm());
		    ncell=nrow.createCell(2);
		    ncell.setCellValue((list.get(i).getTxnAmtSum()));
		    ncell=nrow.createCell(3);
		    ncell.setCellValue((list.get(i).getTxnAmtSumOld()));
		    ncell=nrow.createCell(4);
		    ncell.setCellValue(list.get(i).getTxnAmtSumPercent());
		    
		}
		downloadWorkBook(workbook, fileName, charSet, response);
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
	
	public static void mkdirFileDir(String fileFullPath) {
		try {
			File file = new File(fileFullPath);
			file.getParentFile().mkdirs();
		} catch (Exception e) {
		}
	}
	
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
			throw new BizzException("下载文件失败:" + fileName, e);
		} finally {
			IOUtil.close(in);
			IOUtil.close(os);
		}
	}
	
	public static List<Map<String, Object>> getHuanbiByMchntTrans(List<MchntTransHuanbiBean> nowlist, List<MchntTransHuanbiBean> oldList,String settleDate){

		List<Map<String, Object>> queryResult = new ArrayList<Map<String,Object>>();
        Map<String,Object> preRecord = new HashMap<String,Object>();
        for (MchntTransHuanbiBean m : oldList) {
        	preRecord = MapToPojoUtil.bean2Map(m);
        	queryResult.add(preRecord);
        }
        Map<String,Object> record = new HashMap<String,Object>();
        for (MchntTransHuanbiBean m : nowlist) {
        	record = MapToPojoUtil.bean2Map(m);
        	queryResult.add(record);
        }

        //先按商户号和日期排序
        ListUtils.resultOrder(queryResult, 1 , "mchntCd", "settleDate");
        
        // 再按商户号分组
        Map<String, List<Map<String, Object>>> brand2ListMap = EntryUtil.makeListMap(queryResult,"mchntCd");
        /**
         *  每组中第一条肯定是昨日的 找到昨日的数目
         *  第二条记录是本日的 找到本日的数据
         *  计算环比 本期记录中添加环比
         *  同时删除上一条记录
          */
        List<Map<String, Object>> huanbiList = new ArrayList<Map<String, Object>>();
        
        for (String key : brand2ListMap.keySet()) {
        	List<Map<String, Object>> respList = new ArrayList<Map<String, Object>>(brand2ListMap.get(key));
            if (respList.size() > 1) {
                Map<String, Object> prevRecord = new HashMap<>(respList.get(0));
                Map<String, Object> currentRecord = new HashMap<>(respList.get(1));
                String txnAmtSum = (String) prevRecord.get("txnAmtSum");
                String txnAmtSumOld = (String) currentRecord.get("txnAmtSum");
                
                if(Utils.isEmpty(txnAmtSumOld) || Utils.isEmpty(txnAmtSum)) {
                	currentRecord.put("txnAmtSumPercent", "--");
                	currentRecord.put("txnAmtSumOld", Utils.isEmpty(txnAmtSumOld) ? "--" : toFloatAmt(txnAmtSumOld));
                	currentRecord.put("txnAmtSum", Utils.isEmpty(txnAmtSum) ? "--" : toFloatAmt(txnAmtSum));
                }else {
	                Integer prevCount = Integer.valueOf(prevRecord.get("txnAmtSum").toString());
	                Integer currentCount = Integer.valueOf(currentRecord.get("txnAmtSum").toString());
	
	                BigDecimal huanbi = BigDecimal.valueOf(currentCount).divide(BigDecimal.valueOf(prevCount), 2, RoundingMode.HALF_DOWN);
	                currentRecord.put("txnAmtSumPercent", huanbi.toString());
                	currentRecord.put("txnAmtSumOld", toFloatAmt(prevRecord.get("txnAmtSum").toString()));
                	currentRecord.put("txnAmtSum", toFloatAmt(currentRecord.get("txnAmtSum").toString()));
                }
                huanbiList.add(currentRecord);
                respList.remove(0);
                
            }else{
                // 当前日前有，上期没有
            	if(settleDate.equals(respList.get(0).get("settleDate"))) {
            		huanbiList.get(0).put("txnAmtSumPercent", "--");
	            	huanbiList.get(0).put("txnAmtSumOld", "--");
	            	huanbiList.get(0).put("txnAmtSum", Utils.isEmpty(respList.get(0).get("txnAmtSum")) ? "--" : toFloatAmt((String) respList.get(0).get("txnAmtSum")));
            	} else { // 上期有，当前没有
	            	huanbiList.get(0).put("txnAmtSumPercent", "--");
	            	huanbiList.get(0).put("txnAmtSumOld", Utils.isEmpty(respList.get(0).get("txnAmtSum")) ? "--" : toFloatAmt((String)respList.get(0).get("txnAmtSum")));
	            	huanbiList.get(0).put("txnAmtSum", "--");
            	}
            }
        }
        //放入map中
//        brand2ListMap = EntryUtil.makeListMap(huanbiList,"mchntCd");
        // 生成一个新的List 包含本期,上期记录，和环比
//        List<Map<String, List<Map<String, Object>>>> processedResult = new ArrayList<Map<String, List<Map<String, Object>>>>();
//        processedResult.add(brand2ListMap);
//        ListUtils.sort(processedResult, true, "mchntCd");
//        return processedResult ;
        return huanbiList;
	
	}
	
	/***
	 * 导出 主管报表，员工报表
	 */
	public void exportTransHuanbiExcel(List list ,List beforeList,String settleDate ,String filePath, String fileName, String charSet,
			HttpServletResponse response, String topTitle) {
		//判断是下载什么文件
		String [] nameArray = fileName.split("_");
		if ("huanbi".equals(nameArray[0])) { //渠道交易报表
			doHuanbiReportFile(list, fileName, charSet, response, topTitle);
		} else if ("manager".equals(nameArray[0])) {
			doManagerReportFile(list, fileName, charSet, response, topTitle);
		} else if ("employee".equals(nameArray[0])) {
			doEmployeeReportFile(list, fileName, charSet, response, topTitle);
		} else if ("chnltrans".equals(nameArray[0])) {
			doChnlTransReportFile(list, fileName, charSet, response, topTitle);
		}
	}
	
	/*
	 * 
	 * 写入商户交易环比报表文件
	 */
	@SuppressWarnings("deprecation")
	public void doHuanbiReportFile(List<DailyProfitResultHuanbi> list, String fileName, String charSet, HttpServletResponse response, String topTitle) {
		//定义表头
		String top = "商户环比报表_" + topTitle;
		String[] title={"商户名称","总交易额","总分润","上期交易额","商户交易额环比"};
		//创建excel工作簿
		HSSFWorkbook workbook=new HSSFWorkbook();
		//创建工作表sheet
		HSSFSheet sheet=workbook.createSheet("商户交易环比报表" );
		
		//创建单元格，并设置值表头 设置表头居中  
		HSSFCellStyle style = workbook.createCellStyle(); 
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平
		
		//创建第一行
		HSSFRow rowTop = sheet.createRow(0);
		HSSFRow row=sheet.createRow(1);
		HSSFCell cell=null;
		cell=rowTop.createCell(0);
		cell.setCellValue(top);
		
		//插入第一行数据的表头
		for(int i=0;i<title.length;i++){
		    cell=row.createCell(i);
		    cell.setCellValue(title[i]);
		    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		}
		//写入数据
		int dataCell = 2;
		for (int i=0; i< list.size();i++){
		    HSSFRow nrow=sheet.createRow(dataCell + i);
		    HSSFCell ncell=nrow.createCell(0);
		    ncell.setCellValue(list.get(i).getMchntCnNm());
		    ncell=nrow.createCell(1);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getMchntTxnAmtSum().toString()));
		    ncell=nrow.createCell(2);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getMchntAgentProfit().toString()));
		    ncell=nrow.createCell(3);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getMchntCtTxnAmtSum().toString()));
		    ncell=nrow.createCell(4);
		    ncell.setCellValue(list.get(i).getMchntHuanbi().toString());
		}
		downloadWorkBook(workbook, fileName, charSet, response);
	}
	
	/*
	 * 
	 * 写入各支付类型环比-主管文件
	 */
	@SuppressWarnings("deprecation")
	public void doManagerReportFile(List<DailyProfitResultManager> list, String fileName, String charSet, HttpServletResponse response, String topTitle) {
		//定义表头
		String top = "各支付类型环比报表_" + topTitle;
		String[] title={"交易类型","商户名称","总交易额","总分润","上期交易额","商户环比","各类支付交易额","各类支付分润","上期各类支付交易额","各类支付交易额环比"};
		//创建excel工作簿
		HSSFWorkbook workbook=new HSSFWorkbook();
		//创建工作表sheet
		HSSFSheet sheet=workbook.createSheet("各支付类型环比报表" );
		
		//创建单元格，并设置值表头 设置表头居中  
		HSSFCellStyle style = workbook.createCellStyle(); 
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平
		
		//创建第一行
		HSSFRow rowTop = sheet.createRow(0);
		HSSFRow row=sheet.createRow(1);
		HSSFCell cell=null;
		cell=rowTop.createCell(0);
		cell.setCellValue(top);
		
		//插入第一行数据的表头
		for(int i=0;i<title.length;i++){
		    cell=row.createCell(i);
		    cell.setCellValue(title[i]);
		    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		}
		//写入数据
		int dataCell = 2;
		for (int i=0; i< list.size();i++){
		    HSSFRow nrow=sheet.createRow(dataCell + i);
		    HSSFCell ncell=nrow.createCell(0);
		    ncell.setCellValue(EnumUtil.translate(ProfitEnums.ProfitTxnTp.class, list.get(i).getIntTransCd(), true));
		    ncell=nrow.createCell(1);
		    ncell.setCellValue(list.get(i).getMchntCnNm());
		    ncell=nrow.createCell(2);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getMchntTxnAmtSum().toString()));
		    ncell=nrow.createCell(3);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getMchntAgentProfit().toString()));
		    ncell=nrow.createCell(4);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getMchntCtTxnAmtSum().toString()));
		    ncell=nrow.createCell(5);
		    ncell.setCellValue(list.get(i).getMchntHuanbi().toString());
		    ncell=nrow.createCell(6);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getTransTxnAmtSum().toString()));
		    ncell=nrow.createCell(7);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getTransAgentProfit().toString()));
		    ncell=nrow.createCell(8);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getTransCtTxnAmtSum().toString()));
		    ncell=nrow.createCell(9);
		    ncell.setCellValue(list.get(i).getTransHuanbi().toString());
		}
		downloadWorkBook(workbook, fileName, charSet, response);
	}
	
	/*
	 * 
	 * 写入各支付类型环比-员工文件
	 */
	@SuppressWarnings("deprecation")
	public void doEmployeeReportFile(List<DailyProfitResultEmployee> list, String fileName, String charSet, HttpServletResponse response, String topTitle) {
		//定义表头
		String top = "各支付类型环比报表_" + topTitle;
		String[] title={"交易类型","商户名称","总交易额","上期交易额","商户环比","各类支付交易额","上期各类支付交易额","各类支付交易额环比"};
		//创建excel工作簿
		HSSFWorkbook workbook=new HSSFWorkbook();
		//创建工作表sheet
		HSSFSheet sheet=workbook.createSheet("各支付类型环比报表" );
		
		//创建单元格，并设置值表头 设置表头居中  
		HSSFCellStyle style = workbook.createCellStyle(); 
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平
		
		//创建第一行
		HSSFRow rowTop = sheet.createRow(0);
		HSSFRow row=sheet.createRow(1);
		HSSFCell cell=null;
		cell=rowTop.createCell(0);
		cell.setCellValue(top);
		
		//插入第一行数据的表头
		for(int i=0;i<title.length;i++){
		    cell=row.createCell(i);
		    cell.setCellValue(title[i]);
		    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		}
		//写入数据
		int dataCell = 2;
		for (int i=0; i< list.size();i++){
		    HSSFRow nrow=sheet.createRow(dataCell + i);
		    HSSFCell ncell=nrow.createCell(0);
		    ncell.setCellValue(EnumUtil.translate(ProfitEnums.ProfitTxnTp.class, list.get(i).getIntTransCd(), true));
		    ncell=nrow.createCell(1);
		    ncell.setCellValue(list.get(i).getMchntCnNm());
		    ncell=nrow.createCell(2);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getMchntTxnAmtSum().toString()));
		    ncell=nrow.createCell(3);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getMchntCtTxnAmtSum().toString()));
		    ncell=nrow.createCell(4);
		    ncell.setCellValue(list.get(i).getMchntHuanbi().toString());
		    ncell=nrow.createCell(5);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getTransTxnAmtSum().toString()));
		    ncell=nrow.createCell(6);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getTransCtTxnAmtSum().toString()));
		    ncell=nrow.createCell(7);
		    ncell.setCellValue(list.get(i).getTransHuanbi().toString());
		}
		downloadWorkBook(workbook, fileName, charSet, response);
	}
	
	/*
	 * 
	 * 写入渠道交易环比
	 */
	@SuppressWarnings("deprecation")
	public void doChnlTransReportFile(List<DailyProfitResultChnlTrans> list, String fileName, String charSet, HttpServletResponse response, String topTitle) {
		//定义表头
		String top = "渠道交易环比报表_" + topTitle;
		String[] title={"渠道名称","交易类型","子类交易总交易额","子类交易总分润","上期子类交易额","子类交易额环比","渠道总交易额","渠道总分润","渠道上期交易额","渠道交易额环比"};
		//创建excel工作簿
		HSSFWorkbook workbook=new HSSFWorkbook();
		//创建工作表sheet
		HSSFSheet sheet=workbook.createSheet("各支付类型环比报表" );
		
		//创建单元格，并设置值表头 设置表头居中  
		HSSFCellStyle style = workbook.createCellStyle(); 
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平
		
		//创建第一行
		HSSFRow rowTop = sheet.createRow(0);
		HSSFRow row=sheet.createRow(1);
		HSSFCell cell=null;
		cell=rowTop.createCell(0);
		cell.setCellValue(top);
		
		//插入第一行数据的表头
		for(int i=0;i<title.length;i++){
		    cell=row.createCell(i);
		    cell.setCellValue(title[i]);
		    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		}
		//写入数据
		int dataCell = 2;
		for (int i=0; i< list.size();i++){
		    HSSFRow nrow=sheet.createRow(dataCell + i);
		    HSSFCell ncell=nrow.createCell(0);
		    ncell.setCellValue(EnumUtil.translate(TxnEnums.ChnlId.class, list.get(i).getChnlId(), true));
		    ncell=nrow.createCell(1);
		    ncell.setCellValue(EnumUtil.translate(ProfitEnums.ProfitTxnTp.class, list.get(i).getIntTransCd(), true));
		    ncell=nrow.createCell(2);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getChnlTransTxnAmtSum().toString()));
		    ncell=nrow.createCell(3);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getChnlTransAgentProfit().toString()));
		    ncell=nrow.createCell(4);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getChnlTransCtTxnAmtSum().toString()));
		    ncell=nrow.createCell(5);
		    ncell.setCellValue(list.get(i).getChnlTransHuanbi().toString());
		    ncell=nrow.createCell(6);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getChnlTxnAmtSum().toString()));
		    ncell=nrow.createCell(7);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getChnlAgentProfit().toString()));
		    ncell=nrow.createCell(8);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getChnlCtTxnAmtSum().toString()));
		    ncell=nrow.createCell(9);
		    ncell.setCellValue(list.get(i).getChnlHuanbi().toString());
		}
		downloadWorkBook(workbook, fileName, charSet, response);
	}
	
	/*
	 * 
	 * 写入代理商分润数据报表文件
	 */
	@SuppressWarnings("deprecation")
	public void doAgentProfitQueryReportFile(List<AgentProfitQueryBean> list, String fileName, String charSet, HttpServletResponse response, String summary) {
		//定义表头
		String startDate = fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf("_") + 9);
		String endDate = fileName.substring(fileName.lastIndexOf("_") + 1, fileName.lastIndexOf("_") + 9);
		//設定日期格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
		//進行轉換
		String startDateNew = "";
		String endDateNew = "";
		try {
			Date sDate = sdf.parse(startDate);
			startDateNew = sdf2.format(sDate);
			Date eDate = sdf.parse(endDate);
			endDateNew = sdf2.format(eDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String top = "查询时间范围：" + startDateNew + "-" + endDateNew;
		String[] title={"代理商代码","代理商名称","交易量","分润","返点比例","交易類別","前端商戶號","前端商戶名","渠道","渠道商編","渠道商编名称"};
		//创建excel工作簿
		HSSFWorkbook workbook=new HSSFWorkbook();
		//创建工作表sheet
		HSSFSheet sheet=workbook.createSheet("代理商分润数据报表");
		
		//创建单元格，并设置值表头 设置表头居中  
		HSSFCellStyle style = workbook.createCellStyle(); 
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平
		
		//创建第一行
		HSSFRow rowTop = sheet.createRow(0);
		HSSFRow row=sheet.createRow(1);
		HSSFCell cell=null;
		cell=rowTop.createCell(0);
		cell.setCellValue(top);
		
		//插入第一行数据的表头
		for(int i=0;i<title.length;i++){
		    cell=row.createCell(i);
		    cell.setCellValue(title[i]);
		    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		}
		
		//写入数据
		int dataCell = 2;
		for (int i=0; i< list.size();i++){
			HSSFRow nrow=sheet.createRow(dataCell + i);
		    HSSFCell ncell=nrow.createCell(0);
		    ncell.setCellValue(list.get(i).getAgentCd());
		    ncell=nrow.createCell(1);
		    ncell.setCellValue(list.get(i).getAgentCnNm());
		    ncell=nrow.createCell(2);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getTransAt().toString()));
		    ncell=nrow.createCell(3);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getAgentProfit().toString()));
		    ncell=nrow.createCell(4);
		    ncell.setCellValue(list.get(i).getRate().toString());
		    ncell=nrow.createCell(5);
		    ncell.setCellValue(EnumUtil.translate(ProfitEnums.ProfitTxnTp.class, list.get(i).getIntTransCd(), true));
		    ncell=nrow.createCell(6);
		    ncell.setCellValue(list.get(i).getMchntCd());
		    ncell=nrow.createCell(7);
		    ncell.setCellValue(list.get(i).getMchntCnNm());
		    ncell=nrow.createCell(8);
		    ncell.setCellValue(EnumUtil.translate(TxnEnums.ChnlId.class, list.get(i).getChnlId(), true));
		    ncell=nrow.createCell(9);
		    ncell.setCellValue(list.get(i).getChnlMchntCd());
		    ncell=nrow.createCell(10);
		    ncell.setCellValue(list.get(i).getChnlMchntDesc());
		}
		
		//插入最后一行加总
		HSSFRow lrow = sheet.createRow(list.size()+2);
		HSSFCell lcell = lrow.createCell(0);
		lcell.setCellValue(summary);
		
		downloadWorkBook(workbook, fileName, charSet, response);
	}
	
	/*
	 * 
	 * 写入代理分润总表
	 */
	@SuppressWarnings("deprecation")
	public void doAgentProfitTotalReportFile(List<AgentProfitQuerySummary> list, String fileName, String charSet, HttpServletResponse response) {
		//定义表头
		String startDate = fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf("_") + 9);
		String endDate = fileName.substring(fileName.lastIndexOf("_") + 1, fileName.lastIndexOf("_") + 9);
		//設定日期格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
		//進行轉換
		String startDateNew = "";
		String endDateNew = "";
		try {
			Date sDate = sdf.parse(startDate);
			startDateNew = sdf2.format(sDate);
			Date eDate = sdf.parse(endDate);
			endDateNew = sdf2.format(eDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String top = "查询时间范围：" + startDateNew + "-" + endDateNew;
		String[] title={"代理商代码","代理商名称","交易量","分润金额"};
		//创建excel工作簿
		HSSFWorkbook workbook=new HSSFWorkbook();
		//创建工作表sheet
		HSSFSheet sheet=workbook.createSheet("代理分润总表");
		
		//创建单元格，并设置值表头 设置表头居中  
		HSSFCellStyle style = workbook.createCellStyle(); 
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平
		
		//创建第一行
		HSSFRow rowTop = sheet.createRow(0);
		HSSFRow row=sheet.createRow(1);
		HSSFCell cell=null;
		cell=rowTop.createCell(0);
		cell.setCellValue(top);
		
		//插入第一行数据的表头
		for(int i=0;i<title.length;i++){
		    cell=row.createCell(i);
		    cell.setCellValue(title[i]);
		    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		}
		
		//写入数据
		int dataCell = 2;
		for (int i=0; i< list.size();i++){
			HSSFRow nrow=sheet.createRow(dataCell + i);
		    HSSFCell ncell=nrow.createCell(0);
		    ncell.setCellValue(list.get(i).getAgentCd());
		    ncell=nrow.createCell(1);
		    ncell.setCellValue(list.get(i).getAgentCnNm());
		    ncell=nrow.createCell(2);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getSumTransAt().toString()));
		    ncell=nrow.createCell(3);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getSumAgentProfit().toString()));
		}
		
		downloadWorkBook(workbook, fileName, charSet, response);
	}
	
	/*
	 * 
	 * 写入代理分润总表
	 */
	@SuppressWarnings("deprecation")
	public void doChnlMchntAccountHourReportFile(List<ChnlMchntAccountHour> list, String fileName, String charSet, HttpServletResponse response) {
		//定义表头
		String startDate = fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf("_") + 9);
		String endDate = fileName.substring(fileName.lastIndexOf("_") + 1, fileName.lastIndexOf("_") + 9);
		//設定日期格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");
		//進行轉換
		String startDateNew = "";
		String endDateNew = "";
		try {
			Date sDate = sdf.parse(startDate);
			startDateNew = sdf2.format(sDate);
			Date eDate = sdf.parse(endDate);
			endDateNew = sdf2.format(eDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String top = "平台交易量报表 " + startDateNew + "-" + endDateNew;
		String[] title={"日期","时间","充值总额","代付总额","充值-代付","实际可代付余额","渠道D0可代付余额","保留余额","渠道D1余额","渠道冻结金额"};
		//创建excel工作簿
		HSSFWorkbook workbook=new HSSFWorkbook();
		//创建工作表sheet
		HSSFSheet sheet=workbook.createSheet("平台交易量报表");
		
		//创建单元格，并设置值表头 设置表头居中  
		HSSFCellStyle style = workbook.createCellStyle(); 
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平
		
		//创建第一行
		HSSFRow rowTop = sheet.createRow(0);
		HSSFRow row=sheet.createRow(1);
		HSSFCell cell=null;
		cell=rowTop.createCell(0);
		cell.setCellValue(top);
		
		//插入第一行数据的表头
		for(int i=0;i<title.length;i++){
		    cell=row.createCell(i);
		    cell.setCellValue(title[i]);
		    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		}
		
		//写入数据
		int dataCell = 2;
		for (int i=0; i< list.size();i++){
			HSSFRow nrow=sheet.createRow(dataCell + i);
			HSSFCell ncell=nrow.createCell(0);
			Date eDate = new Date();
			Date eHour = new Date();
			try {
				eDate = sdf.parse(list.get(i).getTransDt().toString());
				SimpleDateFormat hour = new SimpleDateFormat("HH");
				eHour = hour.parse(list.get(i).getTransHour().toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		    ncell.setCellValue(sdf2.format(eDate));
		    ncell=nrow.createCell(1);
		    ncell.setCellValue(sdf3.format(eHour));
		    ncell=nrow.createCell(2);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getTransAt().toString()));
		    ncell=nrow.createCell(3);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getWithdrawAt().toString()));
		    long trans = Long.parseLong(list.get(i).getTransAt().toString());
		    long withdraw = Long.parseLong(list.get(i).getWithdrawAt().toString());
		    String subtraction = String.valueOf(trans - withdraw);
		    ncell=nrow.createCell(4);
		    ncell.setCellValue(StringUtil.formateAmt(subtraction));
		    long availableBalance = Long.parseLong(list.get(i).getAvailableBalance().toString());
		    long obligatedBalance = Long.parseLong(list.get(i).getObligatedBalance().toString());
		    String realAvailableBalance = String.valueOf(availableBalance - obligatedBalance);
		    ncell=nrow.createCell(5);
		    ncell.setCellValue(StringUtil.formateAmt(realAvailableBalance));
		    ncell=nrow.createCell(6);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getAvailableBalance().toString()));
		    ncell=nrow.createCell(7);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getObligatedBalance().toString()));
		    ncell=nrow.createCell(8);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getFrozenT1Balance().toString()));
		    ncell=nrow.createCell(9);
		    ncell.setCellValue(StringUtil.formateAmt(list.get(i).getFrozenBalance().toString()));
		}
		
		downloadWorkBook(workbook, fileName, charSet, response);
	}
}
