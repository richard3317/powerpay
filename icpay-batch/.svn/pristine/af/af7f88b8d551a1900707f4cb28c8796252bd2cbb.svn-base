package com.icpay.payment.batch.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.exolab.castor.types.DateTime;
import org.junit.Test;

import com.icpay.payment.common.enums.CurrencyEnums.CurrType;
import com.icpay.payment.common.utils.Converter;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.Utils;

public class SimpleTest extends BaseTest {
	
	@Test
	public void testParseDouble() {
		System.out.println(Double.parseDouble("0.1"));
		System.out.println(Double.parseDouble("10%"));
	}
	
	@Test
	public void testCurrenctType() {
		for (CurrType currency :CurrType.values())
			System.out.println(currency);
	}
	
	@Test
	public void testFuzzyDate() throws ParseException {
		
		
		System.out.println(DateUtil.fuzzyBatchDayD0("000000"));
		System.out.println(DateUtil.fuzzyBatchDayT1("003000"));
		System.out.println(DateUtil.fuzzyBatchDayT1("010000"));

		
//		System.out.println(DateUtil.fuzzyBatchDayD0("120000"));
//		System.out.println(DateUtil.fuzzyBatchDayD0("1200"));
//		
//		System.out.println(DateUtil.fuzzyBatchDayD0("210000"));
//		System.out.println(DateUtil.fuzzyBatchDayT1("123000"));

//		System.out.println(DateUtil.fuzzyBatchDayD0("220000"));
//		System.out.println(DateUtil.fuzzyBatchDayD0("2200"));
//		System.out.println(DateUtil.fuzzyBatchDayT1("220000"));
//		System.out.println(DateUtil.fuzzyBatchDayT1("2200"));
//		
//		System.out.println(DateUtil.fuzzyBatchDayD0(Converter.stringToDateTime("20181019230000"), "220000"));
//		System.out.println(DateUtil.fuzzyBatchDayT1(Converter.stringToDateTime("20181019230000"), "220000"));

	}
	
	@Test
	public void test_mulPercent() {
		println(mulPercent("1000","0.5"));
		println(mulPercent("","0.5"));
	}
	
	protected String mulPercent(String amt, String percent) {
		if (Utils.isEmpty(amt)) return "0";
		if (Utils.isEmpty(percent)) return "0";
		long iAmt = Long.parseLong(amt);
		Double p = Double.parseDouble(percent);
		Long r = (long) (iAmt * p);
		return r.toString(); 
	}

	@Test
	public void test1() {
		DateTime dt = new DateTime(new Date());
		int yy= dt.getYear();
		int mm = dt.getMonth();
		int dd = dt.getDay();
		int hr = dt.getHour();
		int nn= dt.getMinute();
		int sec= dt.getSeconds();
		println("%s = %s,%s,%s,  %s,%s,%s", dt, yy,mm,dd,hr,nn,sec);
	}
	
	@Test
	public void test2() throws ParseException {
		//Date date = new Date();   // given date
		Date date = Converter.stringToDateTime("20190208151430");
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date);   // assigns calendar to given date
		int yy= calendar.get(Calendar.YEAR);
		int mm= calendar.get(Calendar.MONTH);
		int dd= calendar.get(Calendar.DAY_OF_YEAR);
		int hr = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
		int nn= getMinutesOfDay(date);
		int sec= calendar.get(Calendar.SECOND);
		//calendar.get(Calendar.HOUR);        // gets hour in 12h format
		//calendar.get(Calendar.MONTH);       // gets month number, NOTE this is zero based!
		println("%s = %s,%s,%s,  %s,%s,%s", date, yy,mm,dd,hr,nn,sec);
	}
	
	/**
	 * 回传当日的第几分钟
	 * @param time
	 * @return
	 */
	protected int getMinutesOfDay(Date time) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(time);
		int hr = calendar.get(Calendar.HOUR_OF_DAY);
		int mn= calendar.get(Calendar.MINUTE);
		return hr*60+mn;
	}

}
