package com.icpay.payment.bm.web.util;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ListUtils {

	 /**
     * 对list的元素按照多个属性名称排序,
     * list元素的属性可以是数字（byte、short、int、long、float、double等，支持正数、负数、0）、char、String、java.util.Date
     * 
     * 
     * @param lsit
     * @param sortname
     *            list元素的属性名称
     * @param isAsc
     *            true升序，false降序
     */
    public static <E> void sort(List<E> list, final boolean isAsc, final String... sortnameArr) {
        Collections.sort(list, new Comparator<E>() {

            public int compare(E a, E b) {
                int ret = 0;
                try {
                    for (int i = 0; i < sortnameArr.length; i++) {
                        ret = ListUtils.compareObject(sortnameArr[i], isAsc, a, b);
                        if (0 != ret) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ret;
            }
        });
    }
    
    /**
     * 给list的每个属性都指定是升序还是降序
     * 
     * @param list
     * @param sortnameArr  参数数组
     * @param typeArr      每个属性对应的升降序数组， true升序，false降序
     */

    public static <E> void sort(List<E> list, final String[] sortnameArr, final boolean[] typeArr) {
        if (sortnameArr.length != typeArr.length) {
            throw new RuntimeException("属性数组元素个数和升降序数组元素个数不相等");
        }
        Collections.sort(list, new Comparator<E>() {
            public int compare(E a, E b) {
                int ret = 0;
                try {
                    for (int i = 0; i < sortnameArr.length; i++) {
                        ret = ListUtils.compareObject(sortnameArr[i], typeArr[i], a, b);
                        if (0 != ret) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ret;
            }
        });
    }

    /**
     * 对2个对象按照指定属性名称进行排序
     * 
     * @param sortname
     *            属性名称
     * @param isAsc
     *            true升序，false降序
     * @param a
     * @param b
     * @return
     * @throws Exception
     */
    private static <E> int compareObject(final String sortname, final boolean isAsc, E a, E b) throws Exception {
        int ret;
        Object value1 = ListUtils.forceGetFieldValue(a, sortname);
        Object value2 = ListUtils.forceGetFieldValue(b, sortname);
        String str1 = value1.toString();
        String str2 = value2.toString();
        if (value1 instanceof Number && value2 instanceof Number) {
            int maxlen = Math.max(str1.length(), str2.length());
            str1 = ListUtils.addZero2Str((Number) value1, maxlen);
            str2 = ListUtils.addZero2Str((Number) value2, maxlen);
        } else if (value1 instanceof Date && value2 instanceof Date) {
            long time1 = ((Date) value1).getTime();
            long time2 = ((Date) value2).getTime();
            int maxlen = Long.toString(Math.max(time1, time2)).length();
            str1 = ListUtils.addZero2Str(time1, maxlen);
            str2 = ListUtils.addZero2Str(time2, maxlen);
        }
        if (isAsc) {
            ret = str1.compareTo(str2);
        } else {
            ret = str2.compareTo(str1);
        }
        return ret;
    }

    /**
     * 给数字对象按照指定长度在左侧补0.
     * 
     * 使用案例: addZero2Str(11,4) 返回 "0011", addZero2Str(-18,6)返回 "-000018"
     * 
     * @param numObj
     *            数字对象
     * @param length
     *            指定的长度
     * @return
     */
    public static String addZero2Str(Number numObj, int length) {
        NumberFormat nf = NumberFormat.getInstance();
        // 设置是否使用分组
        nf.setGroupingUsed(false);
        // 设置最大整数位数
        nf.setMaximumIntegerDigits(length);
        // 设置最小整数位数
        nf.setMinimumIntegerDigits(length);
        return nf.format(numObj);
    }

    /**
     * 获取指定对象的指定属性值（去除private,protected的限制）
     * 
     * @param obj
     *            属性名称所在的对象
     * @param fieldName
     *            属性名称
     * @return
     * @throws Exception
     */
    public static Object forceGetFieldValue(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        Object object = null;
        boolean accessible = field.isAccessible();
        if (!accessible) {
            // 如果是private,protected修饰的属性，需要修改为可以访问的
            field.setAccessible(true);
            object = field.get(obj);
            // 还原private,protected属性的访问性质
            field.setAccessible(accessible);
            return object;
        }
        object = field.get(obj);
        return object;
    }
    
    /**
     * 对结果集进行排序，目前支持日期、字符串、各种整形、各种浮点型
     * @param result 结果集
     * @param order
     * @param orderType -1降序 1升序, 下面代码假设orderType为1
     * @return
     */
    public static <K, V> List<Map<K, V>> resultOrder(List<Map<K, V>> result,Integer orderType, final String... order  ){

        if(result == null || orderType == null){
            return result;
        }

        if(orderType != -1){
            orderType = 1;
        }
        
        final Integer oType = orderType;

        Collections.sort(result, new Comparator<Map<K, V>>() {

            @Override
            public int compare(Map<K, V> o1, Map<K, V> o2) {
            	for (int i = 0; i < order.length; i++) {
                Object obj1 = o1.get(order[0]);
                Object obj2 = o2.get(order[0]);

                if (obj1 == null) {
                    if(oType < 0){
                        return -oType;
                    }
                    return oType;
                }
                if (obj2 == null) {
                    if(oType < 0){
                        return oType;
                    }
                    return -oType;
                }

                if(obj1 instanceof Date){
                    //日期排序
                    Date date1 = (Date)obj1;
                    Date date2 = (Date)obj2;
                    return longCompare(oType, date1.getTime(), date2.getTime());
                }else if(obj1 instanceof String){
                    //字符串排序
                    String str1 = obj1.toString();
                    String str2 = obj2.toString();

                    if(str1.compareTo(str2) < 0){
                        return -oType;
                    }else if(str1.compareTo(str2) == 0){
                        return 0;
                    }else if(str1.compareTo(str2) > 0){
                        return oType;
                    }
                }else if(obj1 instanceof Double || obj1 instanceof Float){
                    //浮点型排序
                    return doubleCompare(oType, obj1, obj2);
                }else if(obj1 instanceof Long || obj1 instanceof Integer || obj1 instanceof Short || obj1 instanceof Byte){
                    //整数型排序
                    return longCompare(oType, obj1, obj2);
                }
            	}
                return 0;
            }
        });
        return result;
    }

    private static int longCompare(final Integer oType, Object obj1, Object obj2) {
        long d1 = Long.parseLong(obj1.toString());
        long d2 = Long.parseLong(obj2.toString());
        if(d1 < d2){
            return -oType;
        }else if(d1 == d2){
            //相同的是否进行交互
            return 0;
        }else if(d1 > d2){
            return oType;
        }
        return 0;
    }

    private static int doubleCompare(final Integer oType, Object obj1, Object obj2) {
        double d1 = Double.parseDouble(obj1.toString());
        double d2 = Double.parseDouble(obj2.toString());
        if(d1 < d2){
            return -oType;
        }else if(d1 == d2){
            return 0;
        }else if(d1 > d2){
            return oType;
        }
        return 0;
    }

}