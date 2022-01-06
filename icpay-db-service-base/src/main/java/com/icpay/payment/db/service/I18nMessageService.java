package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.db.dao.mybatis.model.I18nMsgs;
import com.icpay.payment.db.dao.mybatis.model.I18nMsgsExample;

/**
 * i18n 多國語系訊息服務
 * @author robin
 *
 */
public interface I18nMessageService {
	
	/**
	 * 獲取國際化訊息。若指定語系不存在相關信息則默認回傳替代語系(每個語系可以於系統設定替代語系，例如: 'zh-TW'->'zh-CN','th'->'en-US')
	 * @param cat 分類
	 * @param lang 語系
	 * @param key 鍵值
	 * @return
	 */
	String getMessage(String cat, String lang, String key);
	
	/**
	 * 獲取國際化訊息，允許默認值。若指定語系不存在則回傳替代語系，若仍不存在則使用默認值。<br/>
	 * 使用範例：<br/>
	 * String msg = svc.getMessageWithDefault('app','th','welcome','歡迎');
	 * @param cat 分類
	 * @param lang 語系
	 * @param key 鍵值
	 * @param defaultMsg 若擷取的鍵值不存在，則使用此默認值當做回傳。
	 * @return 回傳指定語言的訊息
	 */
	String getMessageWithDefault(String cat, String lang, String key, String defaultMsg);
	
	/**
	 * 查詢替代語系 (目前保留尚未使用)
	 * @param lang
	 * @return
	 */
	String queryAltLang(String lang);
	
	
}
