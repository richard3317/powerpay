package com.icpay.payment.batch.task.settle;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.icpay.payment.common.exception.I18nBizException;
import com.icpay.payment.common.exception.I18nMsgSpec;
import com.icpay.payment.common.utils.Converter;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;

public class Test123 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		List<Map<String, Object>> javaBean = new ArrayList<Map<String, Object>>();
		Map<String, Object> javaMap1 = new HashMap<String, Object>();
		javaMap1.put("currCd", "156");
		javaMap1.put("unit", "0.1");
		javaMap1.put("sumTransFeeDelta", "200");
		javaBean.add(javaMap1);
		
		Map<String, Object> javaMap2 = new HashMap<String, Object>();
		javaMap2.put("currCd", "156");
		javaMap2.put("unit", "0.1");
		javaMap2.put("sumTransFeeDelta", "5000");
		javaBean.add(javaMap2);
		
		Map<String, Object> javaMap3 = new HashMap<String, Object>();
		javaMap3.put("currCd", "156");
		javaMap3.put("unit", "0.1");
		javaMap3.put("sumTransFeeDelta", "111");
		javaBean.add(javaMap3);
		
		Map<String, Object> javaMap4 = new HashMap<String, Object>();
		javaMap4.put("currCd", "156");
		javaMap4.put("unit", "0.1");
		javaMap4.put("sumTransFeeDelta", "300");
		javaBean.add(javaMap4);
		
		List<Map<String, Object>> currCdAndFee = new ArrayList<Map<String, Object>>();
		
		System.out.println(javaBean);
		
		for (int a = 0; a< javaBean.size(); a++) {
			Map<String, Object> javaMap = javaBean.get(a);
			//利潤加總
			if (!currCdAndFee.isEmpty()) {
				boolean hasFlag = false;
				int hasIndex = 0;
				for (int i = 0; i < currCdAndFee.size(); i++) {
					Map<String, Object> map = currCdAndFee.get(i);
					if (map.get("currCd").equals(javaMap.get("currCd"))) {
						hasFlag = true;
						hasIndex = i;
					}
				}
				if (hasFlag) {
					Map<String, Object> map = currCdAndFee.get(hasIndex);
					BigDecimal sum = new BigDecimal(map.get("sumTransFeeDelta").toString());
					BigDecimal fee = new BigDecimal(javaMap.get("sumTransFeeDelta").toString());
					sum = sum.add(fee);
					map.put("sumTransFeeDelta", sum);
				} else {
					Map<String, Object> currMap = new HashMap<String, Object>();
					currMap.put("currCd", javaMap.get("currCd"));
					currMap.put("unit", javaMap.get("unit"));
					currMap.put("sumTransFeeDelta", javaMap.get("sumTransFeeDelta"));
					currCdAndFee.add(currMap);
				}
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("currCd", javaMap.get("currCd"));
				map.put("unit", javaMap.get("unit"));
				map.put("sumTransFeeDelta", javaMap.get("sumTransFeeDelta"));
				currCdAndFee.add(map);
			}
		}
		
		System.out.println(currCdAndFee);
		*/
		
		/*
        //前一個月
		String startDate = "20210515";
        Calendar rightNow1 = Calendar.getInstance();
        rightNow1.setTime(DateUtil.parseDate8(startDate));//使用給定的 Date 設定此 Calendar 的時間。 
        rightNow1.add(Calendar.MONTH, -1);// 日期減1個月
        Date dt1 = rightNow1.getTime();//返回一個表示此 Calendar 時間值的 Date 物件。
        String today1 = DateUtil.date8(dt1);
        System.out.println(today1);
        String mon1 = getMonth(today1);
        System.out.println(mon1);
        
        //前二個月
        Calendar rightNow2 = Calendar.getInstance();
        rightNow2.setTime(DateUtil.parseDate8(startDate));//使用給定的 Date 設定此 Calendar 的時間。 
        rightNow2.add(Calendar.MONTH, -2);// 日期減1個月
        Date dt2 = rightNow2.getTime();//返回一個表示此 Calendar 時間值的 Date 物件。
        String today2 = DateUtil.date8(dt2);
        System.out.println(today2);
        String mon2 = getMonth(today2);
        System.out.println(mon2);
        */
		
		/*
		Date xx = DateUtil.parseDate("20210602", DateUtil.DATE_FORMAT_8);
		System.out.println(xx);
		String oo = DateUtil.formatDate(xx, DateUtil.DATE_FORMAT_10);
		System.out.println(oo);
        
		Date sTime = convertStartDateTime("20210602", "");
		Date eTime = convertEndDateTime("20210602", "");
		String startDate = DateUtil.formatDate(sTime, DateUtil.DATE_FORMAT_19);
		String endDate = DateUtil.formatDate(eTime, DateUtil.DATE_FORMAT_19);
		System.out.println(startDate);
		System.out.println(endDate);
		*/
		
		/*
		BigDecimal zhifuTransAt = new BigDecimal("10");
		System.out.println(zhifuTransAt);
		BigDecimal daifuTransAt = new BigDecimal("50");
		System.out.println(daifuTransAt);
		BigDecimal chongXiaoAt  = zhifuTransAt.subtract(daifuTransAt);
		System.out.println(chongXiaoAt.toString());
		*/
		
		/*
		if(chongXiaoAt.compareTo(BigDecimal.ZERO) == 0) {
			System.out.println(chongXiaoAt);
		} else {
			System.out.println(chongXiaoAt.negate());
		}
		*/
		
		List<String> values= Utils.newList(Utils.strSplit("123,456,", ","));
		System.out.println(values);
		
		String bm = "222";
		String mer = "12";
		if (bm.isEmpty() && mer.isEmpty()) {
			System.out.println("1111");
		} else {
			System.out.println("2222");
		}
		
	}
	
	protected static Date convertDateTime(String fmt, String dateTime) {
		try {
			return Converter.stringToDateTimeFmt(dateTime, fmt);
		} catch (ParseException e) {
			throw new I18nBizException(new I18nMsgSpec("zh_CN", "BaseService",null, "查询时，日期格式错误: '%s'", dateTime));
		}
	}
	
	protected static String strVal(Object obj) {
		if (obj==null) return "";
		return obj.toString();
	}
	
	protected static Date convertDateTime(String fmt, String date, String time, String suffix) {
		return convertDateTime(fmt, StringUtils.left(strVal(date)+strVal(time)+strVal(suffix), fmt.length()));
	}
	
	
	protected static Date convertStartDateTime(String date, String time) {
		return convertDateTime(DEFAULT_TIME_FORMAT, date, time, "000000");
	}

	protected static Date convertEndDateTime(String date, String time) {
		if (Utils.isEmpty(time)) 
			time = "235959.999";
		else
			time = StringUtils.left(time+"000000",6)+".000";
		
		return convertDateTime(DEFAULT_LONG_TIME_FORMAT, date, time, "");
	}	

	protected static final String DEFAULT_TIME_FORMAT="yyyyMMddHHmmss";
	protected static final String DEFAULT_LONG_TIME_FORMAT="yyyyMMddHHmmss.SSS";
	
	
	
	protected static String getMon(String... dates) {
		if (dates!=null)
			for(String d: dates) {
					return getMonth(d);
			}
		return getMonth();
	}
	
	public static String getMonth(String date) {
		return getMonth(date,4);
	}
	
	public static String getMonth() {
		return getMonth(new Date());
	}
	
	public static String getMonth(String src, int startIndex){
		String mon = getMonth(src, startIndex, startIndex+2);
		//if (mon==null)
		//	error(RspCd.Z_1001,"日期格式错误");
		return mon;
	}
	
	public static String getMonth(Date tm) {
		String dt = Converter.dateToString(tm);
		return getMonth(dt);
	}
	
	public static String getMonth(String src, int startIndex, int endIndex){
		if (StringUtil.isEmpty(src)) return null;
		if (src.length()<endIndex) return null;
		String month = src.substring(startIndex, endIndex);
		if (month.length()!=2) return null;
		if (!((month.compareTo("00")>0) && (month.compareTo("13")<0))) return null;
		return month;
	}

}
