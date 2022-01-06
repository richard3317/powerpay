package com.icpay.payment.bm.web.util;

/**
 * 对账读写
 */

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.icpay.payment.common.enums.TxnEnums;
import com.icpay.payment.common.utils.EnumUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TransLogMapping;
import com.icpay.payment.db.dao.mybatis.model.modelExt.WithdrawLogMapping;

public class ExportUtil {

	/**
	 * 导出交易流水
	 */
	public static String writeTransLog(String path, List list, boolean flag) {
		Properties pro = Helper.loadProperties("systemData.properties", ExportUtil.class);
		File fileMk = new File(path);
		byte[] bom ={(byte) 0xEF,(byte) 0xBB,(byte) 0xBF};
		int len=bom.length;
		char [] arr=new char[len];
		for(int i=0; i<len; i++){
		  arr[i] = (char) bom[i];
		}
		deleteDir(fileMk);// 删除已有文件
		if (!fileMk.exists()) {
			fileMk.mkdirs();
		}
		Date date = new Date();
		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String rd = simple.format(date);
		String filePath ="/TransLogOrder" + rd + ".csv";
		File file = new File(path + filePath);

		FileWriter fw = null;
		int listSize = list.size();
		BigDecimal sumPayAmount = new BigDecimal(0);
		String downUrl = null;
		Long transAtSum = 0L;
		Long transFeeSum = 0L;
		try {
			if (file.exists() == false) {
				file.createNewFile();
			}
			fw = new FileWriter(file, flag);
			fw.write(arr);
			int line = 0;
			list.add(0, " 交易日期,交易时间,商户号,商户名称,商户订单号,交易流水号,交易类型,交易金额（元）," + "手续费（元）,交易状态,响应码,代理商代码,渠道,渠道商户号,联行号"
					+ ",平台订单号,渠道流水号,更新时间\n");
			for (Object item : list) {
				if (line == 0) {
					fw.write(item.toString());
					line = 1;
					continue;
				}

				String intTransCdDesc = EnumUtil.translate(TxnEnums.TxnType.class,
						((TransLogMapping) item).getIntTransCd(), true);
				String rspCdDesc=null;
				String rspCd = ((TransLogMapping) item).getRspCd();
				if (!StringUtil.isBlank(rspCd) && rspCd.length() > 2) {
					rspCdDesc=rspCd + "-" + ((TransLogMapping) item).getErrMsg();
				} else {
					rspCdDesc= rspCd;
				}

				String stateDesc = null;
				if ("00".equals(((TransLogMapping) item).getTxnState())) {
					stateDesc = "00-初始状态";
				} else if ("01".equals(((TransLogMapping) item).getTxnState())) {
					stateDesc = "01-交易处理中";
				} else if ("10".equals(((TransLogMapping) item).getTxnState())) {
					stateDesc = "10-交易成功";
				} else if ("20".equals(((TransLogMapping) item).getTxnState())) {
					stateDesc = "20-交易失败";
				} else {
					stateDesc = "30-其他";
				}
				Long transFee=((TransLogMapping) item).getTransFee();
				String transFeeDesc = StringUtil.formateAmt(transFee==null?"0":transFee.toString());

				String transChnl = EnumUtil.translate(TxnEnums.ChnlId.class, ((TransLogMapping) item).getTransChnl(), true);

				String transAtDesc = (new BigDecimal(((TransLogMapping) item).getTransAt()).movePointLeft(2))
						.toString();
				String dateString=ExportUtil.transformDateToString(((TransLogMapping) item).getRecUpdTs());
				fw.write(((TransLogMapping) item).getExtTransDt() + "," + ((TransLogMapping) item).getExtTransTm() + ","
						+ ((TransLogMapping) item).getMchntCd() + "," + ((TransLogMapping) item).getMchntCnAbbr() + ","
						+ ((TransLogMapping) item).getOrderId() + "," + ((TransLogMapping) item).getTransSeqId() + ","
						+ intTransCdDesc + "," + transAtDesc + "," + transFeeDesc + "," + stateDesc + "," + rspCdDesc
						+ "," + ((TransLogMapping) item).getAgent_cd() + "," + transChnl+ "," + ((TransLogMapping) item).getChnlMchntCd()
						+ "," + ((TransLogMapping) item).getBankNum() + "," + ((TransLogMapping) item).getChnlOrderId()
						+ "," + ((TransLogMapping) item).getChnlTransId() 
						+ "," + dateString
						+ "\n");

				transAtSum = transAtSum + ExportUtil.judegIsNull(((TransLogMapping) item).getTransAt(), 0L);
				transFeeSum = transFeeSum + ExportUtil.judegIsNull(((TransLogMapping) item).getTransFee(), 0L);
			}

			fw.write("交易总金额:," +StringUtil.formateAmt( transAtSum==null?"0": transAtSum.toString()) + " 元"+ "\n");
			fw.write("手续费:," +StringUtil.formateAmt( transFeeSum==null?"0": transFeeSum.toString())  + " 元");
			
			downUrl = pro.getProperty("downUrl.TransLogDown") + filePath;
			// downloadNet(downUrl, "D:\\export","\\order"+rd+".csv");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return downUrl;
	}

	/**
	 * 导出取现流水
	 */
	public static String writeWithdrawLog(String path, List list, boolean flag) {
		Properties pro = Helper.loadProperties("systemData.properties", ExportUtil.class);
		File fileMk = new File(path);
		byte[] bom ={(byte) 0xEF,(byte) 0xBB,(byte) 0xBF};
		int len=bom.length;
		char [] arr=new char[len];
		for(int i=0; i<len; i++){
		  arr[i] = (char) bom[i];
		}
		deleteDir(fileMk);// 删除已有文件
		if (!fileMk.exists()) {
			fileMk.mkdirs();
		}
		Date date = new Date();
		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String rd = simple.format(date);
		String filePath ="/WithdrawLogOrder" + rd + ".csv";
		File file = new File(path + filePath);

		FileWriter fw = null;
		int listSize = list.size();
		BigDecimal sumPayAmount = new BigDecimal(0);
		String downUrl = null;
		Long transAtSum = 0L;
		Long transFeeSum = 0L;
		try {
			if (file.exists() == false) {
				file.createNewFile();
			}
			fw = new FileWriter(file, flag);
			fw.write(arr);
			int line = 0;
			list.add(0, "取现日期,取现时间,前端商户号,商户名称,商户订单号,交易流水号,交易类型,取现金额（元）," + "手续费（元）,交易状态,卡哈,联行号,户名,渠道"
					+ ",平台订单号,渠道流水号\n");
			for (Object item : list) {
				if (line == 0) {
					fw.write(item.toString());
					line = 1;
					continue;
				}

				String intTransCdDesc = EnumUtil.translate(TxnEnums.TxnType.class,
						((WithdrawLogMapping) item).getIntTransCd(), true);
				String orderStateDesc = null;
				if ("00".equals(((WithdrawLogMapping) item).getOrderState())) {
					orderStateDesc = "00-初始状态";
				} else if ("01".equals(((WithdrawLogMapping) item).getOrderState())) {
					orderStateDesc = "01-交易处理中";
				} else if ("10".equals(((WithdrawLogMapping) item).getOrderState())) {
					orderStateDesc = "10-交易成功";
				} else if ("20".equals(((WithdrawLogMapping) item).getOrderState())) {
					orderStateDesc = "20-交易失败";
				} else {
					orderStateDesc = "30-其他";
				}
				Long transFee=((WithdrawLogMapping) item).getTransFee();
				String transFeeDesc = StringUtil.formateAmt(transFee==null?"0":transFee.toString());

				String transChnl = EnumUtil.translate(TxnEnums.ChnlId.class, ((WithdrawLogMapping) item).getTransChnl(), true);

				String transAmtDesc = (new BigDecimal(((WithdrawLogMapping) item).getTransAmt()).movePointLeft(2))
						.toString();

				
				fw.write(((WithdrawLogMapping) item).getExtTransDt() + "," + ((WithdrawLogMapping) item).getExtTransTm() + ","
						+ ((WithdrawLogMapping) item).getMchntCd() + "," + ((WithdrawLogMapping) item).getMchntCnAbbr() + ","
						+ ((WithdrawLogMapping) item).getOrderId() + "," + ((WithdrawLogMapping) item).getTransSeqId() + ","
						+ intTransCdDesc + "," + transAmtDesc + "," + transFeeDesc + "," + orderStateDesc 
						+ "," + ((WithdrawLogMapping) item).getAccNo() + "," +  ((WithdrawLogMapping) item).getBankNo() + "," + ((WithdrawLogMapping) item).getAccName()
						+ "," +transChnl + "," + ((WithdrawLogMapping) item).getChnlOrderId()
						+ "," + ((WithdrawLogMapping) item).getChnlTransId() 
						+ "\n");

				transAtSum = transAtSum + ExportUtil.judegIsNull(((WithdrawLogMapping) item).getTransAmt(), 0L);
				transFeeSum = transFeeSum + ExportUtil.judegIsNull(((WithdrawLogMapping) item).getTransFee(), 0L);
			}

			fw.write("交易总金额:," +StringUtil.formateAmt( transAtSum==null?"0": transAtSum.toString()) + " 元"+ "\n");
			fw.write("手续费:," +StringUtil.formateAmt( transFeeSum==null?"0": transFeeSum.toString())  + " 元");
			
			downUrl = pro.getProperty("downUrl.WithdrawLogDown") + filePath;
			// downloadNet(downUrl, "D:\\export","\\order"+rd+".csv");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return downUrl;
	}
	
	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	private static Long judegIsNull(Long start, Long result) {
		if (start == null) {
			start = result;
		}
		return start;
	}
	public static String transformDateToString(Date date) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sim.format(date);
	}
}
