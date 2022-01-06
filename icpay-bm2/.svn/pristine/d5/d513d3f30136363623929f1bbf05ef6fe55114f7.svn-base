package com.icpay.payment.bm.web.util;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * list转map工具类，根据指定的字段分组
 *
 */
public class EntryUtil {

    private static Logger logger = Logger.getLogger(EntryUtil.class);
    /**
     *
     * 将list中的元素放到Map<K, List<V>> 以建立 key - List<value> 索引<p>
     *
     * @param list  List<V> 元素列表
     * @param keyFieldName String 元素的属性名称, 该属性的值作为索引key
     * @param <K> key类型
     * @param <V> value类型
     * @return Map<K, V> key - value 索引
     *
     *
     */
    @SuppressWarnings("unchecked")
	public static <K, V> Map<K, List<V>> makeEntityListMap(List<V> list, String keyFieldName) {
        Map<K, List<V>> map = new LinkedHashMap<K, List<V>>();
        if(list == null || list.size() == 0) {
            return map;
        }
        try {
            Method getter = getMethod(list.get(0).getClass(), keyFieldName, "get");
            for (V item : list) {
                K key = (K) getter.invoke(item);
                List<V> groupList = map.get(key);
                if (groupList == null) {
                    groupList = new ArrayList<V>();
                    map.put(key, groupList);
                }
                groupList.add(item);
            }
        } catch (Exception e) {
            logger.error("makeEntityListMap error list is " + list, e);
            return map;
        }
        return map;
    }
    
    @SuppressWarnings("unchecked")
	public static <K, V> Map<K, List<Map<K, V>>> makeListMap(List<Map<K, V>> list, String keyFieldName) {
    	Map<K, List<Map<K, V>>> map = new LinkedHashMap<K, List<Map<K, V>>>();
    	List<Map<K, V>> groupList = null;
    	if(list == null || list.size() == 0) {
    		return map;
    	}
    	try {
    		for (Map<K, V> item : list) {
    			for(K key : item.keySet()) {
    				if(keyFieldName.equals(key)) {
		    			if (map.get((K)item.get(key)) == null) {
		    				groupList = Lists.newArrayList();
		    			}
		    			groupList.add(item);
		    			map.put((K) item.get(key), groupList);
	    			}
    			}
    		}
    	} catch (Exception e) {
    		return map;
    	}
    	return map;
    }

    /**
     * 获取getter或setter
     */
    @SuppressWarnings("unchecked")
    private static Method getMethod(@SuppressWarnings("rawtypes") Class clazz, String fieldName,
                                    String methodPrefix) throws NoSuchMethodException {
        String first = fieldName.substring(0, 1);
        String getterName = methodPrefix + fieldName.replaceFirst(first, first.toUpperCase());
        return clazz.getMethod(getterName);
    }
}