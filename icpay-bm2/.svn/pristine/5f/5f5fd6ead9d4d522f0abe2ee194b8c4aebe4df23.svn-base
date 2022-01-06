package com.icpay.payment.bm.web.util.telegram;

import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.icpay.payment.db.dao.mybatis.model.TelegramMerchatid;
import com.icpay.payment.db.dao.mybatis.model.TxnLogView;

public class BigChickenBot extends TelegramLongPollingBot {

	public String getBotUsername() {
		return "bigchicken_bot";
	}

	public String getBotToken() {
		return "1441377038:AAH2eHp8we5yrmo8r1cv4z3d5UweoKRvo8A";
	}

	public void onUpdateReceived(Update update) {
		// System.out.println(update.getMessage().getText());

		String command = update.getMessage().getText();
		SendMessage message = new SendMessage();
		if (command.equals("/excuseme")) {
			message.setText("幹嘛");

		} else
		
			if (command.substring(0, 20).equals("/bind@bigchicken_bot")) {
				String mchntCd = command.substring(21,36);
				String chatId= update.getMessage().getChatId().toString();
				BotUtil botUtil = new BotUtil();
				String state =botUtil.setBind(mchntCd, chatId);
				message.setText(state);
				
			} else
			
		if (command.substring(0, 28).equals("/announcement@bigchicken_bot")&&update.getMessage().getFrom().getId().equals("1197289163")) {
					
					BotUtil botUtil = new BotUtil();
					List<TelegramMerchatid> telegramMerchatid=botUtil.sendAnnouncement();
					System.out.println(telegramMerchatid+"_____telegramMerchatid");
					message.setText("公告測試");
					  for(int i=0;i<telegramMerchatid.size();i++){
						  message.setChatId( telegramMerchatid.get(i).getChatId());
						  System.out.println("重點測試__"+telegramMerchatid.get(i).getChatId());
						  try {
								execute(message);
							} catch (TelegramApiException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				    	} 
					
					
				} else
					
		

		if (command.equals("/mylastname")) {
			message.setText(update.getMessage().getFrom().getLastName());

		} else

		if (command.substring(0, 22).equals("/select@bigchicken_bot")) {
			try {
			String transId = command.substring(23, 47);
			BotUtil botUtil = new BotUtil();
			System.out.println("transId"+transId);
			TxnLogView txnLogView = botUtil.getTxnState(transId);
			System.out.println("transId"+transId);
			if (txnLogView==null) {
				message.setText("查無此單");
			}else {
			String txnStateMsg = txnLogView.getTxnStateMsg();
			System.out.println("txnStateMsg"+txnStateMsg);
			message.setText(txnStateMsg);}
			}
			 catch (Exception e) {
				 message.setText("查詢錯誤");
				}

		} else

		if (command.equals("/bindcard@bigchicken_bot")) {
			message.setText("取现白名单卡\r\n" + "点选 提现->取现白名单卡\r\n" + "1.输入ALL绑定（表示允许所有卡号）\r\n"
					+ "2.输入指定银行卡（格式范例：张三|6227001215950083244)");

		} else

		if (command.equals("/calculatekey@bigchicken_bot")) {
			message.setText("密钥如何计算\r\n" + "点击进入http://xor.pw/ 计算密钥\r\n" + "开启商户后台点选安全设置获取密钥参数\r\n" + "\r\n"
					+ "计算方式：\r\n" + "第一隔填入：密钥a密钥b（顺序不可相反，中间不可空格或符号）\r\n" + "第二隔填入：安全密钥\r\n" + "\r\n"
					+ "*不足32位时于最前方补0补足32位数，最终结果为完整密钥。");

		} else {
			message.setText("請輸入正確指令or輸入:/ 選取指令");

		}

		System.out.println("getChat"+update.getMessage().getChat());
		
		System.out.println(update.getMessage().getFrom().getId());
		String[] a = {"1197289163","-268678324"};
//		message.setChatId(update.getMessage().getChatId());
//		for(int i=0;i<a.length;i++)
//					{
//					      System.out.println(a[i]);
//					      message.setChatId(a[i]);
//					      try {
//								execute(message);
//							} catch (TelegramApiException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//					}
		

	}public static void main(String[] args) {
		String[] a = {"1197289163","-268678324"};
//		message.setChatId(update.getMessage().getChatId());
		for(int i=0;i<a.length;i++)
					{
					      System.out.println(a[i]);
					}
	}
}
