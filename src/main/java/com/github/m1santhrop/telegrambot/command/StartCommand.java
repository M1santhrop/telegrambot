package com.github.m1santhrop.telegrambot.command;

import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command {

    private SendBotMessageService sendBotMessageService;

    public static final String START_MESSAGE =
        "Привет. Я Telegram Bot. Я помогу тебе быть в курсе последних" + "\n"
            + "статей тех авторов, котрые тебе интересны. Я еще маленький и только учусь.";

    public StartCommand(
        SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}

