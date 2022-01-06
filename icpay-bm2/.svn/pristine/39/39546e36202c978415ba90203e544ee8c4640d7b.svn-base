package com.icpay.payment.bm.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.client.DBHessionServiceClient;
import com.icpay.payment.db.dao.mybatis.model.BankNums;
import com.icpay.payment.db.service.IBankNumsService;

public class BankNumCache extends CacheBase implements ICache{

	private static final Logger logger = Logger.getLogger(BankNumCache.class);
	private static final BankNumCache INSTANCE = new BankNumCache();
	
	public static BankNumCache getInstance() {
		return INSTANCE;
	}
	
	private Map<String, BankNums> cache = null;
	private List<BankNums> list = null;
	
	private BankNumCache() {}
	
	public static List<BankNums>  getBankNumsList() {
		if (INSTANCE.list == null) {
			synchronized (INSTANCE) {
				if (INSTANCE.list == null) {
					INSTANCE.init();
				}
			}
		}
		return INSTANCE.list;
	}
	
	public static String getBankName(String bankCode) {
		getBankNumsList();
		if (INSTANCE.cache==null) return null;
		BankNums item= INSTANCE.cache.get(bankCode);
		if (item==null) return null;
		return item.getBankName();
	}

	@Override
	public void init() {
		logger.info("获取银行编号列表开始");
		IBankNumsService service = DBHessionServiceClient.getService(IBankNumsService.class);
		List<BankNums> list = service.qryBankNumLst();
		if (!Utils.isEmpty(list)) {
			Map<String, BankNums> cache = new HashMap<>();
			for(BankNums item: list) 
				cache.put(item.getBankNum(), item);
			synchronized (INSTANCE) {
				INSTANCE.list = list;
				INSTANCE.cache=cache;
			}
			logger.info("获取银行编号列表完成:" + INSTANCE.list.size());
		}
	}

	@Override
	public void refresh() {
		init();
	}

	@Override
	public void clear() {
		synchronized (INSTANCE) {
			if (cache!=null)
				cache.clear();
			if (list!=null)
				list.clear();
			cache=null;
			list=null;
		}
	}
	

}
