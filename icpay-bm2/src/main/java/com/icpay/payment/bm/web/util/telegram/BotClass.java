package com.icpay.payment.bm.web.util.telegram;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class BotClass {

    public static  String startBot() {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {   
            telegramBotsApi.registerBot(new BigChickenBot());
            return "BotsStart";
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return "BotsStartFail";
        }
		
    }

}