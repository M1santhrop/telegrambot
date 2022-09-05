package com.github.m1santhrop.telegrambot.exception;

import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import javax.ws.rs.NotFoundException;

public class ExceptionSender {

    private static final String NOT_FOUND_USER_MESSAGE = "Вас нет в базе данных. Зарегистрируйтесь в системе с помощью команды /start";

    private static SendBotMessageService sendBotMessageService;
    
    public static void setSendBotMessageService(SendBotMessageService sendBotMessageService) {
        ExceptionSender.sendBotMessageService = sendBotMessageService;
    }
    private ExceptionSender() {
    }
    
    public static NotFoundException throwNotFoundUserException(Long chatId) {
        sendBotMessageService.sendMessage(chatId, NOT_FOUND_USER_MESSAGE);
        return new NotFoundException(
            String.format("There is no user with this chatId = %s in the database", chatId));
    }
}
