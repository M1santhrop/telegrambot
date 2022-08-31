package com.github.m1santhrop.telegrambot.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandUtils {
    
    private CommandUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getMessage(Update update) {
        return update.getMessage().getText();
    }
    
    public static String getChatId(Update update) {
        return update.getMessage().getChatId().toString();
    }
}
