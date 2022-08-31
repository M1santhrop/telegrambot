package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandUtils.getChatId;
import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public class NoCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static final String NO_MESSAGE = "Я поддерживаю команды, начинающиеся со слеша(/).\n"
        + "Чтобы посмотреть список команд введите /help";

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(getChatId(update), NO_MESSAGE);
    }
}
