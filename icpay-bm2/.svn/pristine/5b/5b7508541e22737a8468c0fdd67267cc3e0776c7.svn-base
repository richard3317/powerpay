package test.simple;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.servlet.jsp.tagext.TryCatchFinally;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class TestsForExcel {

	@Test
	public void test() throws Exception {
		procExcel("/Users/robin/Documents/_Work/Power2017/requirements/渠道对帐报表/优先/支付-28-9999033-20180906.xlsx");
		procExcel("/Users/robin/Documents/_Work/Power2017/requirements/渠道对帐报表/优先/代付-41-SHID20180626064-20180906.xls");
		procExcel("/Users/robin/Documents/_Work/Power2017/requirements/渠道对帐报表/支付-35-ZSWNDTFDIRWK@20180828134153-20180906.csv");
	}

	/**
	 * @param fn
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidFormatException 
	 */
	protected void procExcel(String fn) throws FileNotFoundException, IOException, InvalidFormatException {
		System.out.println("[procExcel] "+fn);
		String ext=getFileExtension(fn);

		InputStream myxls = new FileInputStream(fn);
		try {
			//POIFSFileSystem fs = new POIFSFileSystem(myxls);
			Workbook wb = null;
			wb = WorkbookFactory.create(myxls);
			Sheet sheet = wb.getSheetAt(0);
			Iterator<Row> rows = sheet.rowIterator();
			while (rows.hasNext()) {
				Row row= rows.next();
				process(row);
			}
		} finally {
			myxls.close();
		}
	}
	
	protected Workbook openWorkbook(String ext, InputStream myxls) throws IOException {
		Workbook wb = null;
		if ("xls".equalsIgnoreCase(ext))
			wb = new HSSFWorkbook(myxls);
		else if ("xlsx".equalsIgnoreCase(ext))
			wb = new XSSFWorkbook(myxls);
		return wb;
	}
	
	public static String getFileExtension(String fileName) {
        //String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
	
	private void process(Row row) {
		StringBuilder buf= new StringBuilder();
		//System.out.println("Row #" + row.getRowNum());
		buf.append("Row #").append(row.getRowNum()).append(": ");
		Iterator<Cell> cells = row.cellIterator();
		while(cells.hasNext()) {
			Cell cell= cells.next();
			process(buf,cell);
		}
		System.out.println(buf);
	}

	private StringBuilder process(StringBuilder buf, Cell cell) {
		//cell.getNumericCellValue()
		String cellVal= cell.getStringCellValue();
		buf.append(cellVal).append(" ");
		return buf;
	}

}
