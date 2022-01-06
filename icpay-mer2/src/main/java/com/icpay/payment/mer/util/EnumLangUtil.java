package com.icpay.payment.mer.util;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import com.icpay.payment.common.enums.AccEnums;
import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.DataDic;
import com.icpay.payment.db.dao.mybatis.model.DataDicKey;
import com.icpay.payment.db.service.IDataDicService;

public class EnumLangUtil {

//	public static Object[] getEnumConstants(Class clazz) {
//		Object[] ret = clazz.getEnumConstants();
//		if (ret==null) {
//			try {
//				ret = (Object[]) invokeStaticMethod(clazz, "values", null, true);
//			} catch (Exception e) {
//				e.printStackTrace();
//				ret=null;
//			}
//		}
//		return ret;
//	}
//
//	@SuppressWarnings("unchecked")
//	public static String translate(Class clazz, String code, boolean withCode) {
//		try {
//			Object[] enums = getEnumConstants(clazz);
//			for (Object e : enums) {
//				Method m = e.getClass().getMethod("getCode", new Class[]{});
//				String cd = (String) (""+m.invoke(e, new Object[]{}));
//				if (cd.equals(code)) {
//					Method m2 = e.getClass().getMethod("getDesc", new Class[]{});
//					Object val = m2.invoke(e, new Object[]{});
//					if (withCode) {
//						return val == null ? code : (code + "-" + String.valueOf(val));
//					} else {
//						return val == null ? code : String.valueOf(val);
//					}
//				}
//			}
//			return code;
//		} catch (Exception e) {
//			throw new BizzException("枚举值转义失败:" + code, e);
//		}
//	}
	
	static final String DEFAULT_DATACATALOG = "*";
	public static String translate(String lang, String dataTp, String code, boolean withCode) {
		try {
			String desc=null;
			String dataCatalog = DEFAULT_DATACATALOG;
			//String dataTp = clazz.getSimpleName();
			//String dataKey = code ;
			//TODO query service by (dataCatalog, dataTp, lang, code) ->  desc
			
			IDataDicService dataDicService = DBHessionServiceClient.getService(IDataDicService.class);
			DataDicKey key = new DataDicKey();
			key.setDataCatalog(DEFAULT_DATACATALOG);
			key.setDataTp(dataTp);
			key.setDataKey(code);
			key.setLang(lang);
			DataDic dataDic = dataDicService.selectByPrimaryKey(key);
			if (dataDic != null)
				desc = dataDic.getDataVal();
			else
				desc = code;
			
			return withCode ? String.format("%s-%s", code, desc) : desc;
		} catch (Exception e) {
			throw new BizzException("枚举值转义失败:" + code, e);
		}
	}
	
	public static String translate(String lang, Class clazz, String code, boolean withCode) {
		return translate(lang, clazz.getSimpleName(), code, withCode);
	}
	
//	@SuppressWarnings("unchecked")
//	public static <T> T parseEnumByCode(Class clazz, String code) {
//		AssertUtil.strIsBlank(code, "code is blank.");
//		try {
//			Object[] enums = getEnumConstants(clazz);
//			for (Object e : enums) {
//				Method m = e.getClass().getMethod("getCode", new Class[]{});
//				String cd = (String) (""+m.invoke(e, new Object[]{}));
//				if (cd.equals(code)) {
//					return (T) e;
//				}
//			}
//			throw new BizzException("未找到枚举类:" + code);
//		} catch (Exception e) {
//			throw new BizzException("枚举类查找失败:" + code, e);
//		}
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static <T> T parseEnumByCode(Class clazz, String code, T defaultValue) {
//		T ret=null;
//		try {
//			ret = parseEnumByCode(clazz, code);
//		} catch (Exception e) {
//			ret = defaultValue;
//		}
//		return ret;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static Map<String, String> enumMap(Class clazz, boolean withCode) {
//		Map<String, String> mp = new LinkedHashMap<String, String>();
//		try {
//			Object[] enums = getEnumConstants(clazz);
//			for (Object e : enums) {
//				Method m = e.getClass().getMethod("getCode", new Class[]{});
//				String cd = (String) (""+m.invoke(e, new Object[]{}));
//				Method m2 = e.getClass().getMethod("getDesc", new Class[]{});
//				Object val = m2.invoke(e, new Object[]{});
//				mp.put(cd, withCode ? cd + "-" + StringUtil.valueOf(val) : StringUtil.valueOf(val));
//			}
//		} catch (Exception e) {
//			throw new BizzException("枚举转map失败", e);
//		}
//		return mp;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static int parseIndx(Class clazz, String code) {
//		try {
//			Object[] enums = getEnumConstants(clazz);
//			for (int i = 0; i < enums.length; i ++) {
//				Object e = enums[i];
//				Method m = e.getClass().getMethod("getCode", new Class[]{});
//				String cd = (String) (""+m.invoke(e, new Object[]{}));
//				if (cd.equals(code)) {
//					return i;
//				}
//			}
//		} catch (Exception e) {
//			throw new BizzException("获取枚举序号失败:" + code, e);
//		}
//		return -1;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static Class findEnum(String enumPackage, String enumNm) {
//		AssertUtil.strIsBlank(enumPackage, "enumPackage is blank.");
//		AssertUtil.strIsBlank(enumNm, "enumNm is blank.");
//		
//		if (enumNm.contains(".")) {
//			enumNm = enumNm.replace(".", "$");
//		}
//		if (!enumPackage.endsWith(".")) {
//			enumPackage = enumPackage + ".";
//		}
//		
//		String fullNm = enumPackage + enumNm;
//		try {
//			return Class.forName(fullNm);
//		} catch (Exception e) {
//			throw new BizzException("查找枚举类失败:" + fullNm, e);
//		}
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static String buildCtrlRule(Class clazz, String[] vals, String ctrlRuleDftVal) {
//		if (vals == null || vals.length == 0) {
//			return ctrlRuleDftVal;
//		}
//		char[] cs = ctrlRuleDftVal.toCharArray();
//		for (String v : vals) {
//			int idx = parseIndx(TxnEnums.TxnType.class, v);
//			cs[idx] = '1';
//		}
//		return new String(cs);
//	}
//	
//	protected static Object invokeStaticMethod(Class ownerClass, String methodName, Object[] args, boolean noException) throws Exception {
//		
//		if (args==null)
//			args=new Object[0];
//		Class[] argsClass = new Class[args.length];
//
//		for (int i = 0, j = args.length; i < j; i++) {
//			argsClass[i] = args[i].getClass();
//		}
//
//		@SuppressWarnings("unchecked")
//		Method method = ownerClass.getMethod(methodName, argsClass);
//		if (method!=null)
//		    return method.invoke(null, args);
//		else {
//			if (noException) 
//				return null;
//			else 
//				throw new Exception("Invoke static method Error!");
//		}
//	}
//	
//	public static void main(String[] args) {
//		System.out.println(translate(AccEnums.AccOperTp.class, "40", true));
//		System.out.println(translate(AccEnums.AccOperTp.class, "40", false));
//		System.out.println(parseIndx(AccEnums.AccOperTp.class, "ZZ"));
//	}
}
