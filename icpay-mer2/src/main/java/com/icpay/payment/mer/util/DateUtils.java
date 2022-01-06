package com.icpay.payment.mer.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;

public class DateUtils {
	  public static final String DATE_FORMAT_8 = "yyyyMMdd";
	  public static final String DATE_FORMAT_10 = "yyyy-MM-dd";
	  public static final String DATE_FORMAT_14 = "yyyyMMddHHmmss";
	  public static final String DATE_FORMAT_15 = "yyyyMMdd HHmmss";
	  public static final String DATE_FORMAT_16 = "yyyy-MM-dd HH:mm";
	  public static final String DATE_FORMAT_17 = "yyyyMMdd HH:mm:ss";
	  public static final String DATE_FORMAT_19 = "yyyy-MM-dd HH:mm:ss";
	  public static final String TIME_STR_FMT_6 = "HHmmss";
	  
	  public static String timeToStr6(Date date)
	  {
	    AssertUtil.argIsNull(date, "日期为空");
	    DateFormat df = new SimpleDateFormat(TIME_STR_FMT_6);
	    return df.format(date);
	  }
	  
	  public static String dateToStr8(Date date)
	  {
	    AssertUtil.argIsNull(date, "日期为空");
	    return formatDate(date, DATE_FORMAT_8);
	  }
	  
	  public static String dateToStr14(Date date)
	  {
	    AssertUtil.argIsNull(date, "日期为空");
	    DateFormat df = new SimpleDateFormat(DATE_FORMAT_14);
	    return df.format(date);
	  }
	  
	  public static String dateToStr15(Date date)
	  {
	    AssertUtil.argIsNull(date, "日期为空");
	    return formatDate(date, DATE_FORMAT_15);
	  }
	  
	  public static String dateToStr17(Date date)
	  {
		  AssertUtil.argIsNull(date, "日期为空");
		  return formatDate(date, DATE_FORMAT_17);
	  }
	  
	  public static String dateToStr19(Date date)
	  {
	    AssertUtil.argIsNull(date, "日期为空");
	    return formatDate(date, DATE_FORMAT_19);
	  }
	  
	  public static String timeToStrMillionSeconds(Date date)
	  {
	    AssertUtil.argIsNull(date, "日期为空");
	    return String.valueOf(date.getTime());
	  }
	  
	  public static Date str8ToDate(String dateStr)
	  {
	    AssertUtil.strIsBlank(dateStr, "日期为空");
	    Date dt = new Date();
	    try
	    {
	      SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT_8);
	      dt = sf.parse(dateStr);
	    }
	    catch (Exception e)
	    {
	      throw new IllegalArgumentException("日期格式转换错误" + dateStr);
	    }
	    return dt;
	  }
	  
	  public static String formatDate(Date date, String format)
	  {
	    AssertUtil.objIsNull(date, "日期为空");
	    if (StringUtil.isEmpty(format))
	    {
	      DateFormat df = new SimpleDateFormat(DATE_FORMAT_10);
	      return df.format(date);
	    }
	    return new SimpleDateFormat(format).format(date);
	  }
	  
	  public static String now8()
	  {
	    return formatDate(new Date(), DATE_FORMAT_8);
	  }
	  
	  public static String now10()
	  {
		  return formatDate(new Date(), DATE_FORMAT_10);
	  }
	  
	  public static String now17()
	  {
	    return formatDate(new Date(), DATE_FORMAT_17);
	  }
	  
	  public static String now19()
	  {
	    return formatDate(new Date(), DATE_FORMAT_19);
	  }
	  
	  public static Date parseDate(String dateString, String format)
	  {
	    try
	    {
	      DateFormat df = new SimpleDateFormat(format);
	      return df.parse(dateString);
	    }
	    catch (ParseException e)
	    {
	      throw new BizzException(StringUtil.formatMessage("Parse date String \"{0}\" failed.", new Object[] { dateString }), e);
	    }
	  }
	  
	  /**
	   * 转换时间格式
	   * @param dateStr 时间字符串
	   * @param oldFormat  转换前时间 格式
	   * @param format  转换 后时间格式
	   * @return
	   */
	  public static String formatDateStr(String dateStr,String oldFormat,String format)
	  {
	    return formatDate(parseDate(dateStr,oldFormat),format);
	  }
	  
	  public static void main(String[] args)
	  {
	    System.out.println(formatDateStr("2019-08-09 12:00:29","yyy-MM-dd HH:mm:ss","HHmmss"));
//	    System.out.println(formatDate(new Date(),"yyy-MM-dd HH:mm:ss"));
	  }
}
