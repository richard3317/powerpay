package com.icpay.payment.bm.web.util.telegram;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icpay.payment.bm.web.controller.BaseController;
import com.icpay.payment.db.dao.mybatis.model.TelegramMerchatid;
import com.icpay.payment.db.dao.mybatis.model.TelegramMerchatidExample;
import com.icpay.payment.db.dao.mybatis.model.TelegramMerchatidExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.TxnLogView;
import com.icpay.payment.db.service.ITelegramMerchatidService;
import com.icpay.payment.db.service.ITxnLogViewService;

public class BotUtil extends BaseController {

	public TxnLogView getTxnState(String transId) {
		ITxnLogViewService svc = this.getDBService(ITxnLogViewService.class);
		String mon = transId.substring(0, 8);
		System.out.println("mon" + mon);
		TxnLogView TxnLogView = svc.selectByPrimaryKey(transId, mon);
		System.out.println("TxnLogView" + TxnLogView);
		return TxnLogView;
	}

	public String setBind(String mchntCd, String chatId) {

		ITelegramMerchatidService svc = this.getDBService(ITelegramMerchatidService.class);
		TelegramMerchatid usrInfo = new TelegramMerchatid();
		System.out.println(mchntCd + "----mchntCd");
		System.out.println(chatId + "----chatId");
		usrInfo.setChatId(chatId);
		usrInfo.setMchntCd(mchntCd);
		usrInfo.setState("1");
		int ok = svc.add(usrInfo);
		if (ok == 1) {
			return "ok";
		} else {
			return "fail";
		}

	}

	public List<TelegramMerchatid> sendAnnouncement() {

		ITelegramMerchatidService svc = this.getDBService(ITelegramMerchatidService.class);
		TelegramMerchatidExample example = new TelegramMerchatidExample();
		Criteria cri = example.createCriteria();
		cri.andStateEqualTo("1");
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("state", "1");
		List<TelegramMerchatid> telegramMerchatid = svc.select(qryParamMap);
//		     for(int i=0;i<telegramMerchatid.size();i++){
//		    	   telegramMerchatid.get(i).getChatId();
//		    	} 
//	
		return telegramMerchatid;
	}

	public static void main(String[] args) {
		String a = "/announcement@bigchicken_bot";

		String transId = a.substring(0, 28);
		System.out.println(transId);

	}
}
