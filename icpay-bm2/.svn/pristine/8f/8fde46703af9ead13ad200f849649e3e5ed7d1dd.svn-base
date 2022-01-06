package com.icpay.payment.bm.web.util;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icpay.payment.common.constants.Names.MSG;
import com.icpay.payment.common.constants.RspCd;
import com.icpay.payment.common.exception.ChnlBizException;
import com.icpay.payment.common.utils.Converter;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.proxy.ServiceProxy;
import com.icpay.payment.service.HttpClientService;
import com.icpay.payment.service.api.HttpClientResponse;

public class BaseTool {
	
	protected String strVal(Object obj, String defVal) {
		if (obj==null) return defVal;
		return obj.toString();
	}
	
	protected String strVal(Object obj) {
		return strVal(obj,"");
	}
	
	
	
	private Logger logger = null;
	
	protected Logger getLogger() {
		if (logger==null) {
			logger = Logger.getLogger(this.getClass());
		}
		return logger;
	}

	protected String format(String fmt, Object...args) {
		return String.format(fmt, args);
	}

	protected void debug(String message) {
		getLogger().debug(getLogPrefix()+message);
	}

	protected void debug(String fmt, Object... args) {
		debug(String.format(fmt, args));
	}

	protected void info(String message) {
		getLogger().info(getLogPrefix()+message);
	}

	protected void info(String fmt, Object... args) {
		info(String.format(fmt, args));
	}

	protected void warn(String message) {
		getLogger().warn(getLogPrefix()+message);
	}

	protected void warn(String fmt, Object... args) {
		warn(String.format(fmt, args));
	}

	protected void error(String message) {
		getLogger().error(getLogPrefix()+message);
	}

	protected void error(String fmt, Object... args) {
		error(String.format(fmt, args));
	}

	protected void error(String message, Throwable t) {
		getLogger().error(getLogPrefix()+message, t);
	}

	protected void error(Throwable t, String fmt, Object... args) {
		error(String.format(fmt, args), t);
	}

	protected String getLogPrefix() {
		return "";
	}

	protected void throwError(String code, String msg) {
		error(format("[Error] %s (Code=%s);", msg, code));
		throw new ChnlBizException(code, msg);
	}
	
	protected void throwError(String code, String msg, Map<String, String> attach) {
		error(format("[Error] %s (Code=%s); Context: %s", msg, code, attach));
		throw new ChnlBizException(code, msg, attach);
	}
	
	protected void checkParam(Object param, String paramName, Map<String, String> attach) {
		if (Utils.isEmpty(param))
			throwError(RspCd.Z_1001, format("必填字段或参数'%s'缺失",paramName), attach);
	}
	
	protected void checkDataError(int count, Map<String, String> attach, String fmt, Object...args) {
		checkDataError(count, attach, String.format(fmt, args));
	}
	
	protected void checkDataError(int count, Map<String, String> attach, String msg) {
		if (count<=0) {
			throwError(RspCd._05, msg, attach);
		}
	}
	
	protected void checkQueryError(Object record, String msg) {
		if (record==null) {
			throwError(RspCd.Z_0019, msg);
		}
	}
	
	protected void checkQueryError(Object record, String fmt, Object...args) {
		checkQueryError(record, String.format(fmt, args));
	}
	
	protected boolean checkQueryResult(Map<String,String> resp, Object record, String fmt, Object...args) {
		try {
			checkQueryError(record, String.format(fmt, args));
			return true;
		} catch (ChnlBizException e) {
			resp.put(MSG.respCode, e.getErrorCode());
			resp.put(MSG.respMsg, e.getErrorMsg());
			return false;
		}
	}
	
	protected void checkParam(Object param, String fmt, Object...args) {
		if (Utils.isEmpty(param))
			throwError(RspCd.Z_1001, String.format(fmt, args));
	}
	
	protected void checkParam(Object param, String paramName) {
		checkParam(param, "缺失参数: %s", paramName);
	}
	
	protected boolean hasRecord(List<?> list) {
		return ((list!=null) && (list.size()>0));
	}
	
	protected boolean hasRecord(Object obj) {
		return (obj!=null);
	}
	
	protected static String toFloatAmt(String amt) {
		if (Utils.isEmpty(amt)) return "";
		//return new BigDecimal(amt).movePointLeft(2).toString();
		return StringUtil.formateAmt(amt);
	}
	
	protected static String toIntAmt(String amt) {
		if (Utils.isEmpty(amt)) return "";
		//return ""+ new BigDecimal(amt).movePointRight(2).longValue();
		return StringUtil.transferAmt(amt);
	}
	
	protected static Long toLongAmt(String amt) {
		if (Utils.isEmpty(amt)) return 0L;
		return Long.parseLong(StringUtil.transferAmt(amt));
	}
	
	protected static String DefaultExpectHttpRespCodePrefix="2;3;";
	/**
	 * 检查是否为预期的响应码
	 * @param respCode 响应码，如:"200","302","404"...
	 * @return 是否为预期的响应码
	 */
	protected boolean checkHttpRespCodePrefix(String respCode) {
		return checkHttpRespCodePrefix(respCode,DefaultExpectHttpRespCodePrefix);
	}
	/**
	 * 检查是否为预期的响应码
	 * 
	 * @param respCode
	 *            响应码，如:"200","302","404"...
	 * @return 是否为预期的响应码
	 */
	protected boolean checkHttpRespCodePrefix(String respCode, String expectPrefixs) {
		String prefixs = expectPrefixs + ";";
		String cp = ("" + respCode).substring(0, 1);
		return prefixs.contains(cp + ";");
	}	
	
	
	protected void responseHtml(HttpServletResponse resp, String statusCode, String msg) throws IOException {
		this.sendResponse(resp, "200", msg, "text/html", null);
	}
	
	/**
	 * 
	 * @param resp
	 * @param statusCode
	 * @param respText
	 * @param contentType
	 * @param encoding
	 * @throws IOException
	 */
	protected void sendResponse(HttpServletResponse resp, String statusCode, String respText, String contentType, String encoding) throws IOException {
		Integer iStatus=null;
		if (!Utils.isEmpty(statusCode))
			iStatus = Integer.parseInt(statusCode);
		this.sendResponse(resp, iStatus, respText, contentType, encoding);
	}
	
	/**
	 * 
	 * @param resp
	 * @param statusCode
	 * @param respText
	 * @param contentType
	 * @param encoding
	 * @throws IOException
	 */
	protected void sendResponse(HttpServletResponse resp, Integer statusCode, String respText, String contentType, String encoding) throws IOException {
		if (!Utils.isEmpty(statusCode))
			resp.setStatus(statusCode);
		
		if (Utils.isEmpty(encoding))
			encoding = "utf-8";

		if (Utils.isEmpty(contentType))
			contentType = "text/html; charset=" + encoding;

		resp.setContentType(contentType);
		
		this.debug("[sendResponse] statusCode: %s; contentType: %s; body: %s", statusCode, contentType, respText);
		
		ServletOutputStream sos = resp.getOutputStream();
		try {
			sos.write(respText.getBytes(encoding));
		} finally {
			sos.close();
		}
	}
	
	/**
	 * 将JSON对象转换成Map
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Map toMap(JSONObject json) {
		return json.toJavaObject(json, Map.class);
	}

	/**
	 * 将JSON内容转换成MAP
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Map toMap(String jsonStr) {
		return toMap(JSON.parseObject(jsonStr));
	}
	
	public HttpClientResponse httpPostByService(String reqUrl, Map<String, String> postData) throws IOException {
		HttpClientService svc = ServiceProxy.getService(HttpClientService.class);
		return svc.httpPostMap(reqUrl, postData, null, "UTF-8");
	}
	
	public HttpClientResponse httpPostByService(String reqUrl, String postData) throws IOException {
		HttpClientService svc = ServiceProxy.getService(HttpClientService.class);
		return svc.httpPost(reqUrl, postData, null, "UTF-8");
	}
	
	protected String getMonth(String... dateStrs ) {
		String dtStr= Converter.dateTimeToString(new Date());
		if (!Utils.isEmpty(dateStrs)) {
			for(String s: dateStrs) {
				if (!Utils.isEmpty(s) && (s.length()>=8)) {
					dtStr = s;
					break;
				}
			}
		}
		return dtStr.substring(4,6);
	}
	
	
	public static void mkdirFileDir(String fileFullPath) {
		try {
			File file = new File(fileFullPath);
			file.getParentFile().mkdirs();
		} catch (Exception e) {
		}
	}
	
	public static String getFileExtension(String fileName) {
        //String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
	
    public static String stripExtension (String str) {
        // Handle null case specially.
        if (str == null) return null;
        // Get position of last '.'.
        int pos = str.lastIndexOf(".");
        // If there wasn't any '.' just return the string as is.
        if (pos == -1) return str;
        // Otherwise return the string, up to the dot.
        return str.substring(0, pos);
    }
    
	public static String getUniqueFileName(String fileName) {
		//String fileNameWithOutExt = fileName.replaceFirst("[.][^.]+$", "");
		String fileNameWithOutExt = stripExtension(fileName);
		String fileExt = getFileExtension(fileName);
		String rnd = ""+System.currentTimeMillis()+""+Utils.getRandomInt(1000, 9999);
		return fileNameWithOutExt+rnd+(Utils.isEmpty(fileExt) ? "" : "."+fileExt);
	}

	/**
	 * 获取指定日期的前一天
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyyMMdd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
		return dayBefore;
	}
	
}
