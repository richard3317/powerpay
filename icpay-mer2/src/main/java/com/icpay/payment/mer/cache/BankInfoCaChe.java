package com.icpay.payment.mer.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.BankInfo;
import com.icpay.payment.db.dao.mybatis.model.BankNums;
import com.icpay.payment.db.service.IBankInfoService;
import com.icpay.payment.db.service.IBankNumsService;

public class BankInfoCaChe {

	private static final Logger logger = Logger.getLogger(BankInfoCaChe.class);
	private static final BankInfoCaChe INSTANCE = new BankInfoCaChe();
	
	private List<String> bankNameCache;
	private Map<String, List<BankInfo>> bankBranchCache = new HashMap<String, List<BankInfo>>();
	private List<BankNums> bankName;
	
	private BankInfoCaChe() {}
	
	public static List<String> getBankNameLst() {
		if (INSTANCE.bankNameCache == null) {
			synchronized (INSTANCE) {
				if (INSTANCE.bankNameCache == null) {
					logger.info("获取银行名称列表开始");
					IBankInfoService service = DBHessionServiceClient.getService(IBankInfoService.class);
					INSTANCE.bankNameCache = service.qryBankNmLst();
					logger.info("获取银行名称列表完成:" + INSTANCE.bankNameCache.size());
				}
			}
		}
		return INSTANCE.bankNameCache;
	}
	
	public static List<BankInfo> getBankBranchLst(String bankName) {
		AssertUtil.strIsBlank(bankName, "bankName is blank.");
		if (INSTANCE.bankBranchCache.get(bankName) == null) {
			synchronized (INSTANCE) {
				if (INSTANCE.bankBranchCache.get(bankName) == null) {
					logger.info("获取支行列表开始:" + bankName);
					IBankInfoService service = DBHessionServiceClient.getService(IBankInfoService.class);
					List<BankInfo> lst = service.qryBankListByBankName(bankName);
					INSTANCE.bankBranchCache.put(bankName, lst);
					logger.info("获取支行列表完成:" + bankName + "," + lst.size());
				}
			}
		}
		return INSTANCE.bankBranchCache.get(bankName);
	}
	
	public static List<BankNums> getBankCdLst() {
		if (INSTANCE.bankNameCache == null) {
			synchronized (INSTANCE) {
				if (INSTANCE.bankNameCache == null) {
					logger.info("获取银行联行号列表开始");
					IBankNumsService service = DBHessionServiceClient.getService(IBankNumsService.class);
					INSTANCE.bankName = service.qryBankNumLst();
					logger.info("获取银行联行号列表完成:" + INSTANCE.bankName.size());
				}
			}
		}
		return INSTANCE.bankName;
	}
	
	public static List<BankNums> getBankCdLstForWithdraw(List<String> uniqueChnlsId, String currCd) {
		if (INSTANCE.bankNameCache == null) {
			synchronized (INSTANCE) {
				if (INSTANCE.bankNameCache == null) {
					logger.info("获取银行联行号列表开始");
					IBankNumsService service = DBHessionServiceClient.getService(IBankNumsService.class);
					INSTANCE.bankName = service.qryBankNumLstByChnlsAndCurrCd(uniqueChnlsId, currCd);
					logger.info("获取银行联行号列表完成:" + INSTANCE.bankName.size());
				}
			}
		}
		return INSTANCE.bankName;
	}
}
