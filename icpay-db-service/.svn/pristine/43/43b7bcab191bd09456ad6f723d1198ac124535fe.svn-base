package com.icpay.payment.db.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Service;

import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.mapper.I18nMsgsMapper;
import com.icpay.payment.db.dao.mybatis.model.I18nMsgs;
import com.icpay.payment.db.dao.mybatis.model.I18nMsgsExample;
import com.icpay.payment.db.dao.mybatis.model.I18nMsgsExample.Criteria;
import com.icpay.payment.db.service.I18nMessageService;
@Service("I18nMessageService")
public class I18nMessageServiceImpl extends BaseService implements I18nMessageService {
	public static String DEFAULT_LANG = "zh_CN";
	public static String PARAM_CAT="cat"; // 分類
	public static String PARAM_LANG = "lan" ;//語系
	public static String PARAM_CODE = "code";// 鍵值
	 
	public I18nMessageServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 复制整个列表
	 * 
	 * @param clazz         列表中的项目的类别
	 * @param srcList       原始列表
	 * @param includeFields 包含的属性名称(若为空值则表示复制所有来源对象的属性值)
	 * @return
	 */
	public List<Map<String, String>> cloneEntityMapList(List<?> srcList, String[] includeFields) {
		return cloneEntityMapList(srcList, includeFields, null, false);
	}

	public List<Map<String, String>> cloneEntityMapList(List<?> srcList, String[] includeFields, String[] ignoreFields,
			boolean ignoreSetError) {
		List<Map<String, String>> resList = new ArrayList<>();
		for (Object item : srcList) {
			try {
				// T dest = clazz.newInstance();
				Map<String, String> dest = BeanUtil.describe(item, ignoreFields, includeFields);
				resList.add(dest);
			} catch (Exception err) {
				err.printStackTrace();
				if (!ignoreSetError)
					throw new BizzException("复制实例失败", err);
			}
		}
		return resList;
	}
	
	@Override
	public String getMessage(String cat, String lang, String key) {
		I18nMsgsMapper svc = getMapper();
		I18nMsgsExample example = new I18nMsgsExample();
		I18nMsgsExample.Criteria c1 = example.createCriteria();
		c1.andCategoryEqualTo(cat).andLangEqualTo(lang).andCodeEqualTo(key);

		List<I18nMsgs> qryList = svc.selectByExample(example);
		example.clear();
		if (!this.hasRecord(qryList)) {
			I18nMsgsExample.Criteria c2 = example.createCriteria();
			c2.andCategoryEqualTo(cat).andLangEqualTo("zh-CN").andCodeEqualTo(key);
			List<I18nMsgs> qryList2 = svc.selectByExample(example);

			if (!this.hasRecord(qryList2)) {

				this.warn("[I18nMessageService][getMessage] 查无数据");
				throw new BizzException("查无数据");

			} else {
				List<Map<String, String>> resList2 = this.cloneEntityMapList(qryList2, new String[] { "value" });
				Map<String, String> oo = resList2.get(0);
				String value = oo.get("value");
				return value;
			}
		} else {

			List<Map<String, String>> resList = this.cloneEntityMapList(qryList, new String[] { "value" });
			System.out.println(resList);

			Map<String, String> oo = resList.get(0);
			String value = oo.get("value");
			return value;
		}
	}

	@Override
	public String getMessageWithDefault(String cat, String lang, String key, String defaultMsg) {
			I18nMsgsMapper svc = getMapper();
			I18nMsgsExample example = new I18nMsgsExample();
			I18nMsgsExample.Criteria c1 = example.createCriteria();
			c1.andCategoryEqualTo(cat).andLangEqualTo(lang).andCodeEqualTo(key);

			List<I18nMsgs> qryList = svc.selectByExample(example);
			example.clear();
			if (!this.hasRecord(qryList)) {
				I18nMsgsExample.Criteria c2 = example.createCriteria();
				c2.andCategoryEqualTo(cat).andLangEqualTo("zh_CN").andCodeEqualTo(key);
				List<I18nMsgs> qryList2 = svc.selectByExample(example);
				example.clear();
				if (!this.hasRecord(qryList2)) {
					I18nMsgs i18nMessage = new I18nMsgs();
					i18nMessage.setCategory(cat);
					i18nMessage.setCode(key);
					i18nMessage.setLang("zh_CN");
					i18nMessage.setValue(defaultMsg);
					i18nMessage.setMemo(defaultMsg);
					i18nMessage.setRecCrtTs((new Date()));
					i18nMessage.setRecUpdTs((new Date()));
					svc.insert(i18nMessage);
					List<I18nMsgs> qryList3 = svc.selectByExample(example);
					List<Map<String, String>> resList3 = this.cloneEntityMapList(qryList3, new String[] { "value" });
					Map<String, String> oo = resList3.get(0);
					String value = oo.get("value");
					
					if (lang.equals("zh_CN")) {
						return (value);
					} else {
						return (value+"___");
					}
					
					
			
				} else {
					List<Map<String, String>> resList2 = this.cloneEntityMapList(qryList2, new String[] { "value" });
					Map<String, String> oo = resList2.get(0);
					String value = oo.get("value");
					
					
					return (value+"___");
				}
			} else {

				List<Map<String, String>> resList = this.cloneEntityMapList(qryList, new String[] { "value" });
				System.out.println(resList);

				Map<String, String> oo = resList.get(0);
				String value = oo.get("value");
				return value;
			}

	}

	@Override
	public String queryAltLang(String lang) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected I18nMsgsMapper getMapper() {
		return this.getMapper(I18nMsgsMapper.class);
	}

	protected boolean hasRecord(List<?> list) {
		return ((list != null) && (list.size() > 0));
	}
	
	protected I18nMsgsExample buildQryExample(Map<String, String> qryParamMap) {
		I18nMsgsExample example = new I18nMsgsExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			
			String lang = qryParamMap.get(I18nMessageServiceImpl.PARAM_LANG);
			if (!StringUtil.isBlank(lang)) {
				c.andLangEqualTo(lang);
			}
			
			String cat = qryParamMap.get(I18nMessageServiceImpl.PARAM_CAT);
			if (!StringUtil.isBlank(cat)) {
				c.andCategoryEqualTo(cat);
			}
			
			String code = qryParamMap.get(I18nMessageServiceImpl.PARAM_CODE);
			if (!StringUtil.isBlank(code)) {
				c.andCodeEqualTo(code);
			}		}
		
		return example;
	}
	
}
