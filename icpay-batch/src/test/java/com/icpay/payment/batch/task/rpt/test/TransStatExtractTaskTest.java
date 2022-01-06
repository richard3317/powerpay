package com.icpay.payment.batch.task.rpt.test;

import org.junit.Test;

import com.icpay.payment.batch.task.rpt.TransStatExtractTask;
import com.icpay.payment.batch.test.BaseTest;
import com.icpay.payment.common.enums.BatchEnums.RunningMode;

public class TransStatExtractTaskTest extends BaseTest {

	@Test
	public void testTransStatExtract() {
		TransStatExtractTask t = this.getBean("transStatExtractTask");
		t.exec("20150327", RunningMode._1);
	}
	
}
