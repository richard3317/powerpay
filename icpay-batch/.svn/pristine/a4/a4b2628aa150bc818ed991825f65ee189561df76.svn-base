package com.icpay.payment.batch.task.rpt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import com.icpay.payment.batch.cache.BatchConfigCache;
import com.icpay.payment.batch.task.BatchTaskTemplate;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.RptInfo;
import com.icpay.payment.db.service.IRptInfoService;

public abstract class BaseDailyReportTask extends BatchTaskTemplate {

	protected String reportDt8; // 8位报表日期 - yyyyMMdd
	
	private Map<String, Object> params;
	private List<Map<String, Object>> dataList;
	
	@Override
	protected void doTask() {
		try {
			if (params != null) {
				params.clear();
			}
			if (dataList != null) {
				dataList.clear();
			}
			
			params = new HashMap<String, Object>();
			dataList = new ArrayList<Map<String,Object>>();
			
			// 初始化报表生成用到的信息
			initRptData();
			
			// 获取统计数据并生成报表
			createReport();
		} catch (Exception e) {
			logger.error("生成报表【" + this.getReportNm() + "】失败", e);
			throw new BizzException("生成报表【" + this.getReportNm() + "】失败");
		}

	}
	
	/**
	 * 生成Excel报表
	 * @param templateFileNm 模板文件名
	 * @param reportFileNm   报表文件名
	 * @param params
	 * @param dataList
	 * @throws Exception
	 */
	protected void createReport() throws Exception {
		
		// 生成Excel报表文件
		String rptFilePath = createExcel();
		
		IRptInfoService service = DBHessionServiceClient.getService(IRptInfoService.class);
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("rptDt", batchDt);
		param.put("rptNm", this.getReportNm());
		List<RptInfo> lst = service.select(param);
		// 如果数据库中已经有该报表信息，则不重复插入
		if (lst.size() > 0) {
			logger.info("报表信息已存在");
			return;
		}
		
		// 构造报表信息
		RptInfo rptInfo = new RptInfo();
		rptInfo.setInsTp("0"); // 所属机构类型
		rptInfo.setInsCd("00000000"); // 所属机构代码
		rptInfo.setRptDt(batchDt); // 报表日期
		rptInfo.setRptTp("1"); // 报表类型： 1-日报
		rptInfo.setRptNm(this.getReportNm());
		rptInfo.setRptPath(rptFilePath);
		
		// 保存报表信息
		service.add(rptInfo);
	}
	
	/**
	 * 初始化报表数据
	 * @throws Exception
	 */
	protected abstract void initRptData();
	
	/**
	 * 模板名称
	 * @return
	 * @throws Exception
	 */
	protected abstract String getJasperNm();
	
	/**
	 * 报表名称
	 * @return
	 * @throws Exception
	 */
	protected abstract String getReportNm();
	
	/**
	 * 
	 * @param templateFileNm
	 * @param reportFileNm
	 * @param rptDate
	 * @param params
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	private String createExcel() throws Exception {

		AssertUtil.strIsBlank(this.getJasperNm(), "模板名不能为空");
		String fullTemplateFileNm = BatchConfigCache.getConfig("jasper_root_dir") + this.getJasperNm();
		String reportFileFullPath = getExcelRptFullPath();
		
		logger.info("报表【" + this.getReportNm() + "】生成开始，报表日期为【" + batchDt + "】");
		
		JasperReport fillTemp = (JasperReport) JRLoader.loadObjectFromFile(fullTemplateFileNm);
//		JasperReport fillTemp = (JasperReport) JRLoader.loadObjectFromLocation(fullTemplateFileNm);
		JasperPrint jasperPrint = JasperFillManager.fillReport(fillTemp, params, new ReportDataSource(dataList));
		JRXlsExporter jrxe = new JRXlsExporter();
		jrxe.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		jrxe.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, reportFileFullPath);
		jrxe.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
		jrxe.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		jrxe.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		jrxe.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		jrxe.exportReport();
		
		logger.info("报表【" + this.getReportNm() + "】已生成，报表路径为【" + reportFileFullPath + "】");
		return reportFileFullPath;
	}
	
	private String getExcelRptFullPath() {
		// 按报表日期每天一个目录
		File rptDir = new File(BatchConfigCache.getConfig("report_root_dir") + batchDt);
		if (!rptDir.exists()) {
			rptDir.mkdirs();
		}
		return rptDir.getAbsolutePath() + File.separator + this.getReportNm() + ".xls";
	}
	
	protected void addParam(String key, String val) {
		params.put(key, val);
	}
	
	protected void addData(Map<String, Object> data) {
		dataList.add(data);
	}
}
