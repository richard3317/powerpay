package com.icpay.payment.batch.task.acctChk.qianhai;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

import com.icpay.payment.batch.cache.BatchConfigCache;
import com.icpay.payment.batch.constants.BatchConstants;
import com.icpay.payment.batch.task.acctChk.ChnlFileChkBaseTask;
import com.icpay.payment.common.constants.Constant.CHANNEL;
import com.icpay.payment.common.exception.BizzException;

@Component("QianhaiFileChkTask")
public class QianhaiFileChkTask extends ChnlFileChkBaseTask {

	@Override
	protected void doTask() {
		try {
			deleteChnlSettleInfo(CHANNEL._11);
			String basePath = BatchConfigCache.getConfig(BatchConstants.CONF_KEY_QIANHAI_CHK_FILE_PATH);
			
			//按交易日期命名文件夹的
			File srcFileDir=new File(basePath+batchDt);
			File[] srcFileArr=srcFileDir.listFiles();
			if(srcFileArr==null||srcFileArr.length==0){
				logger.info("src_file not exists!batchDt="+batchDt);
				throw new BizzException("src_file not exists!batchDt="+batchDt);
			}
			for(int f=0;f<srcFileArr.length;f++){
				FileInputStream fis=new FileInputStream(srcFileArr[f]);
				HSSFWorkbook workbook = new HSSFWorkbook(fis);
				HSSFSheet sheet=workbook.getSheetAt(0);
				HSSFRow row = sheet.getRow(0);
				int txnNum = 0;
				BigDecimal totalTxnAmt = new BigDecimal(0);
				BigDecimal totalFee = new BigDecimal(0);
				//从第二行开始读取数据						
				for(int rowInx=1;rowInx<=sheet.getLastRowNum();rowInx++){
					row=sheet.getRow(rowInx);
					HSSFCell cell = row.getCell((short)1);
//					cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
					String cellVal=readCellValue(cell);
					BigDecimal txnFlag=new BigDecimal(-1);
					//如果不是消费交易，则交易金额为负数
					if(cellVal!=null){
						if(cellVal.equals("消费")){
							txnFlag=new BigDecimal(1);
						}
					}
					cell = row.getCell((short)5);
					cellVal=readCellValue(cell);
					
					//交易成功才计算交易金额和手续费
					if(cellVal!=null&&cellVal.equals("成功")){
						cell = row.getCell((short)3);
						cellVal=readCellValue(cell);
						if(cellVal!=null&&!cellVal.equalsIgnoreCase("")){
							totalTxnAmt=totalTxnAmt.add(txnFlag.multiply(new BigDecimal(cellVal)));
						}
						//交易手续费可能为空
						cell = row.getCell((short)4);
						if(cell!=null){
							cellVal=readCellValue(cell);
							if(cellVal!=null&&!cellVal.equals("")){
								totalFee=totalFee.add(txnFlag.multiply(new BigDecimal(cellVal)));
							}
						}									
					}
					txnNum++;																			
				}
				fis.close();
				insertChnlSettleInfo(srcFileArr[f].getName(),txnNum,totalTxnAmt.movePointRight(2),totalFee.movePointRight(2),srcFileArr[f].getAbsolutePath(),CHANNEL._11,"QianhaiFileChkTask");
			}
			
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected String getTaskNm() {
		return "下载钱海渠道对账文件";
	}
	
	private String readCellValue(HSSFCell cell){
		String cellValue="";
		if(cell.getCellType()==0){
			cellValue=String.valueOf(cell.getNumericCellValue());
		}
		else if(cell.getCellType()==1){
			cellValue=cell.getStringCellValue();
		}
		return cellValue;
	}
	
}

