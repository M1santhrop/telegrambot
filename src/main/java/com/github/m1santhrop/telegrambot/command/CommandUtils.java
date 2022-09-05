package com.github.m1santhrop.telegrambot.command;

import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandUtils {

    public static final String GROUP_NOT_FOUND_MESSAGE = "Нет группы с ID = %s";
    
    private CommandUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getMessage(Update update) {
        return update.getMessage().getText();
    }
    
    public static Long getChatId(Update update) {
        return update.getMessage().getChatId();
    }

    public static void sendGroupNotFound(SendBotMessageService sendBotMessageService, Long chatId, String groupId) {
        sendBotMessageService.sendMessage(chatId, String.format(GROUP_NOT_FOUND_MESSAGE, groupId));
    }
}
