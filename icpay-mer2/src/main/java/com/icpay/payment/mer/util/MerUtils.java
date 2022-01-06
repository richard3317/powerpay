package com.icpay.payment.mer.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.common.utils.WebUtil;


public class MerUtils {
	private static final Logger logger = Logger.getLogger(MerUtils.class);

	/**
	 * 校验银行卡卡号
	 * 
	 * @param cardId
	 * @return
	 */
	public static boolean checkBankCard(String cardId) {

		if (Utils.isEmpty(cardId)) return false;
		if("ALL".equals(cardId)) return true;
		//String accName = null;
		if (cardId.contains(WHITE_ITEM_SPLITOR))
			cardId = getAccNoInfo(cardId)[1];
		
		if (Utils.isEmpty(cardId)) return false;
		if (cardId.length()<=11) return false;
		
		boolean r = Utils.isMatch("[0-9]{12,24}", cardId);
		//if (!r) logger.info(String.format("银行卡 %s 校验不合法！", cardId));
		return r;
		
//		//logger.debug(String.format("检查卡号格式: '%s' ...", cardId));
//		char res = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
//		if (res == 'N') {
//			return false;
//		}
//		//logger.info("校验结果为：" + res);
//		String charJX = cardId.substring(cardId.length() - 1);// 存入不含校验位的卡号
//		//logger.info("银行卡的校验位为：" + charJX);
//		if (charJX.equals(String.valueOf(res))) {
//			//logger.info("银行卡合法！");
//			return true;
//		} else {
//			logger.info(String.format("银行卡 %s 校验不合法！", cardId));
//			return false;
//		}
	}
	
	/**
	 * 檢核戶名空白
	 * 
	 * @param accNameInfo
	 * @return
	 */
	public static boolean checkAccName(String accNameInfo) {

		if (Utils.isEmpty(accNameInfo)) return false;
		if("ALL".equals(accNameInfo)) return true;
		if (accNameInfo.contains(WHITE_ITEM_SPLITOR))
			accNameInfo = getAccNoInfo(accNameInfo)[0];
		if(accNameInfo.indexOf(" ")!=-1||accNameInfo.indexOf("　")!=-1 || accNameInfo.indexOf("\t")!=-1) {
			return false;
		}
		
		return true;
	}
	/**
	 * 校验银行卡卡号&检核户名空白
	 * 
	 * @param cardId
	 * @return
	 */
	public static List<String> checkBankCardList(String cardListData) {
		if (Utils.isEmpty(cardListData)) return null;
		String[] cardList = cardListData.replaceAll("[\\s,，]", ",").split("[,]");
		List<String> errList = new ArrayList<String>();
		for(String cardItem: cardList) {
			if (!checkBankCard(cardItem))
				errList.add(cardItem);
			if (!checkAccName(cardItem))
				errList.add(cardItem);
		}
		return errList;
	}
	

	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位 该校验的过程： 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
	 * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，则将其减去9），再求和。
	 * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
	 */
	public static char getBankCardCheckCode(String nonCheckCodeCardId) {
		if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0 || !nonCheckCodeCardId.matches("\\d+")
				|| nonCheckCodeCardId.trim().length() < 15 || nonCheckCodeCardId.trim().length() > 18) {
			// 如果传的数据不合法返回N
			System.out.println("银行卡号不合法！");
			return 'N';
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		// 执行luh算法
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) { // 偶数位处理
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}
	
	public static final String WHITE_ITEM_SPLITOR="|";
	
	/**
	 * 获取卡号白名单的项目内容 : accName|accNo
	 * @param accName
	 * @param accNo
	 * @return
	 */
	public static String accWhiteItem(String accName, String accNo) {
		if ("ALL".equals(accName)) return "ALL";
		if ("ALL".equals(accNo)) return "ALL";
		return StringUtil.trim(accName) +WHITE_ITEM_SPLITOR +StringUtil.trim(accNo);
	}
	
	/**
	 * 从卡号白名单项目获取 卡号及持卡人姓名
	 * @param accWhiteItem
	 * @return
	 */
	public static String[] getAccNoInfo(String accWhiteItem) {
		String[] res = new String[] {"",""};
		if (Utils.isEmpty(accWhiteItem)) return res;
		String[] items = accWhiteItem.split(String.format("\\%s", WHITE_ITEM_SPLITOR)); 
		int i=0;
		for(String item : items) {
			if (i<res.length) {
				if (! Utils.isEmpty(item))
					res[i] = StringUtil.trim(item);
			}
			i++;
		}
		return res;
	}
	
	/**
	 * 获取客户端IP
	 * @param req Http Request
	 * @return
	 */
	public static String getRemoteIp(HttpServletRequest req) {
		return WebUtil.getRemoteIp(req);
	}
	
	/**
	 * 获取客户端IP
	 * @param req Http Request
	 * @return
	 */
	public static String getRemoteIps(HttpServletRequest req) {
		return WebUtil.getRemoteIps(req);
	}
	
	/**
	 * 校验商户号
	 * 
	 * @param mchntCd
	 * @return
	 */
	public static List<String> checkMchntCdList(String mchntCdListData) {
		if (Utils.isEmpty(mchntCdListData)) return null;
		String[] mchntCdList = mchntCdListData.replaceAll("[\\s,，]", ",").split("[,]");
		List<String> errList = new ArrayList<String>();
		for(String mchntCdItem: mchntCdList) {
			if (!checkMchntCd(mchntCdItem))
				errList.add(mchntCdItem);
		}
		return errList;
	}
	
	/**
	 * 校验商户号
	 * 
	 * @param mchntCd
	 * @return
	 */
	public static boolean checkMchntCd(String mchntCd) {

		if (Utils.isEmpty(mchntCd)) return false;
		if (mchntCd.length() != 15) return false;
		boolean r = Utils.isMatch("[0-9]{15,15}", mchntCd);
		return r;
	}
	
	/**
	 * 从商户号白名单项目获取商户号
	 * @param mchntCdWhiteItem
	 * @return
	 */
	public static String[] getMchntCdInfo(String mchntCdWhiteItem) {
		String[] res = new String[] {"",""};
		if (Utils.isEmpty(mchntCdWhiteItem)) return res;
		String[] items = mchntCdWhiteItem.split(String.format("\\%s")); 
		int i=0;
		for(String item : items) {
			if (i<res.length) {
				if (! Utils.isEmpty(item))
					res[i] = StringUtil.trim(item);
			}
			i++;
		}
		return res;
	}
}
