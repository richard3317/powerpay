package com.icpay.payment.batch.task.rpt;

import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ReportDataSource implements JRDataSource {

	private List<Map<String, Object>> data;
	private int index;
	private int count;

	public ReportDataSource(List<Map<String, Object>> data) {
		this.data = data;
		this.index = -1;
		this.count = data.size();
	}

	public Object getFieldValue(JRField field) throws JRException {
		Map<String, Object> row = data.get(index);
		return row.get(field.getName());
	}

	public boolean next() throws JRException {
		if (this.index >= this.count - 1)
			return false;
		else {
			this.index++;
			return true;
		}
	}
}