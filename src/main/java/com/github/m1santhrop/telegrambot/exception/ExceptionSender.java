package com.github.m1santhrop.telegrambot.exception;

import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import javax.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExceptionSender {

    private static final String NOT_FOUND_USER_MESSAGE = "Вас нет в базе данных. Зарегистрируйтесь в системе с помощью команды /start";
    
    private static SendBotMessageService sendBotMessageService;

    @Autowired
    public ExceptionSender(
        SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    public static NotFoundException throwNotFoundUserException(String chatId) {
        sendBotMessageService.sendMessage(chatId, NOT_FOUND_USER_MESSAGE);
        return new NotFoundException(String.format("There is no user with this chatId = %s in the database", chatId));
    }

}
