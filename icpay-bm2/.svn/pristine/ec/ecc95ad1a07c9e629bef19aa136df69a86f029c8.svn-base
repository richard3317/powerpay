package com.icpay.payment.bm.web.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONUtils {
	public static String abc = "{\"data\":{\"date\":1547776076067,\"orderId\":\"O19011809552941\",\"totalPrice\":50.00,\"payPage\":\"https://cntpay.io/payment.html?orderId=V2M5S0JPaHFUWE14SEUzcVZ5UjduQT09\",\"referenceCode\":\"456227\",\"pays\":[{\"payType\":\"0\",\"openBank\":\"\",\"cardId\":\"626\",\"payUrl\":\"HTTPS://QR.ALIPAY.COM/FKX06175ASCIDTRMAMK936\",\"subbranch\":\"\",\"userName\":\"董云峰\",\"payName\":\"dyfwanghua@163.com\"}]},\"resultCode\":\"0000\",\"resultMsg\":\"下单成功\"}";
//	public static String abc = "{\"head\":{\"mchid\":\"2018\",\"tradeno\":\"5643023200001681\",\"notifyurl\":\"http://testenv.chongshengwei.cn:9090/icpay-gateway-onl/txnNotify/56/5210/do\",\"timestamp\":\"1548837288259\"},\"body\":[{\"bankcity\":\"深圳市\",\"orderno\":\"5643023200001681\",\"purpose\":\"代付\",\"name\":\"姬喜喜\",\"bankprovince\":\"广东省\",\"bankname\":\"建设银行广州行\",\"sumamount\":\"1000\",\"cardno\":\"6217001210055609190\"}]}";

	public static void main(String[] args) {
		System.out.println(parseJSON2Map(abc));
	}

	/**
	 * 多层json转成map
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public static Map<String, Object> parseJSON2Map(String jsonStr){    
        Map<String, Object> map = new HashMap<String, Object>();    
        JSONObject json = JSONObject.parseObject(jsonStr);
        for(Object k : json.keySet()){
            Object v = json.get(k);     
            if(v instanceof JSONObject){
            	for(Object vk : ((JSONObject) v).keySet()){
                    Object vv = ((JSONObject) v).get(vk);
                    if(vv instanceof JSONArray){
                        Iterator it = ((JSONArray)vv).iterator();
                        Map jsonMap = new HashMap<>();
		                while(it.hasNext()){
		                    Object json2 = it.next();
		                   jsonMap = parseJSON2Map(json2.toString());
		                }
		                map.putAll(jsonMap);
                    }else {    
                        map.put(vk.toString(), vv);
                    }  
            	}
            }else if(v instanceof JSONArray){
                Iterator it = ((JSONArray)v).iterator();
                Map jsonMap = new HashMap<>();
                while(it.hasNext()){
                    Object json2 = it.next();
                   jsonMap = parseJSON2Map(json2.toString());
                }
                map.putAll(jsonMap);
            }else {    
                map.put(k.toString(), v);    
            }    
        }    
        return map;    
    }
	
}
