package com.github.m1santhrop.telegrambot.service;

import com.github.m1santhrop.telegrambot.bot.JavarushTelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

//@Service
public class SendBotMessageServiceImpl implements  SendBotMessageService{
    private static final Logger LOGGER = LoggerFactory.getLogger(SendBotMessageServiceImpl.class);
        
    private JavarushTelegramBot javarushTelegramBot;

//    @Autowired
    public SendBotMessageServiceImpl(
        JavarushTelegramBot javarushTelegramBot) {
        this.javarushTelegramBot = javarushTelegramBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.enableHtml(true);

        try {
            LOGGER.info("Message: \"{}\" sent to chat: {}", message, chatId);
            javarushTelegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
