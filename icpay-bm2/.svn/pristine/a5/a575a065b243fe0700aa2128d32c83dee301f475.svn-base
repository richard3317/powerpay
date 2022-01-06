/**
 * 
 */
package com.icpay.payment.bm.web.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 无法归类的杂七杂八的方法
 * 
 * 
 *
 */
public class Helper {
	
	

	/**
	 * 读取配置文件
	 * @param propertiesName
	 * @param class
	 * @return
	 */
	public static Properties loadProperties(String propertiesName, Class<?> cls) {
		Properties prop = new Properties();
		try {
			prop.load(cls.getClassLoader().getResourceAsStream(propertiesName));// /加载属性列表
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	/**
     * url参数转换成Map
     * @param urlParams
     * @return
     */
    public static Map<String, Object> urlToMap(String urlParams) {
    	Map<String, Object> map = new TreeMap<String, Object>();
    	//Map<String,Object> map = new HashMap<String,Object>();
		String [] params = urlParams.split("&");
		for(String param : params){
			map.put(param.substring(0, param.indexOf("=")), param.substring(param.indexOf("=")+1,param.length()));
		}
        return map;  
    } 
    
  //返回值类型为Map<String, String>
    public static Map<String, String> getParameterStringMap(Map request) {
        Map<String, String[]> properties = request;//把请求参数封装到Map<String, String[]>中
        Map<String, String> returnMap = new HashMap<String, String>();
        String name = "";
        String value = "";
        for (Map.Entry<String, String[]> entry : properties.entrySet()) {
            name = entry.getKey();
            String[] values = entry.getValue();
            if (null == values || "".equals(values)) {
                value = "";
            } else if (values.length>1) {
                for (int i = 0; i < values.length; i++) { //用于请求参数中有多个相同名称
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = values[0];//用于请求参数中请求参数名唯一
            }
            try {
				returnMap.put(name, URLDecoder.decode(value,"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
        return returnMap;
    }
    
  //返回值类型为Map<String, Object>  不转化url
    public static Map<String, String> getParameterStringMap2(Map request) {
        Map<String, String[]> properties = request;//把请求参数封装到Map<String, String[]>中
        Map<String, String> returnMap = new HashMap<String, String>();
        String name = "";
        String value = "";
        for (Map.Entry<String, String[]> entry : properties.entrySet()) {
            name = entry.getKey();
            String[] values = entry.getValue();
            if (null == values) {
                value = "";
            } else if (values.length>1) {
                for (int i = 0; i < values.length; i++) { //用于请求参数中有多个相同名称
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = values[0];//用于请求参数中请求参数名唯一
            }
            returnMap.put(name, value);
            
        }
        return returnMap;
    }
    
    
  //返回值类型为Map<String, String>
    public static String getParameterString(Map request) {
        Map<String, String[]> properties = request;//把请求参数封装到Map<String, String[]>中
        Map<String, String> returnMap = new HashMap<String, String>();
        StringBuffer str = new StringBuffer();
        String name = "";
        String value = "";
        for (Map.Entry<String, String[]> entry : properties.entrySet()) {
            name = entry.getKey();
            String[] values = entry.getValue();
            if (null == values) {
                value = "";
            } else if (values.length>1) {
                for (int i = 0; i < values.length; i++) { //用于请求参数中有多个相同名称
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = values[0];//用于请求参数中请求参数名唯一
            }
            str.append(name+"="+value+"&");
        }
        String temp = str.toString();
        if(null == temp || "".equals(temp)){
        	return "";
        }else{
        	return temp.substring(0,temp.length()-1);
        }
    }
	
    /**
	 * 转换map请求 字符串
	 * 
	 * @param map
	 * @return
	 */
	public static String getMapString(Map<String, String> params) {
		StringBuffer str = new StringBuffer();
		String[] keys = params.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		for (String key : keys) {
			str.append(key+"=");
			str.append(params.get(key) == null ? "" : params.get(key));
			str.append("&");
//			str.append(key + "=" + params.get(key) == null ? "" : params.get(key) + "&");
		}
		String temp = str.toString();
		if (null == temp || "".equals(temp)) {
			return "";
		} else {
			return temp.substring(0, temp.length() - 1);
		}
	}
	
	/**
	 * 以&符号拼接的请求字符串转map
	 * @param data
	 * @return
	 */
	public static Map<String,String> getMapByStr(String data){
		if(null == data || "".equals(data)) {
			return null;
		}
		String[] strs = data.split("&");
		Map<String, String> strMap = new HashMap<String, String>();  
		for(String str : strs){  
			String[] temp = str.split("=");
			if(temp.length == 1) {
				strMap.put(str.split("=")[0], "");  
			}else {
				strMap.put(str.split("=")[0], str.split("=")[1] == null?"":str.split("=")[1]);  
			}
		}  
		return strMap;
	}
    
    public static void main(String[] args) {
		

    }
}
