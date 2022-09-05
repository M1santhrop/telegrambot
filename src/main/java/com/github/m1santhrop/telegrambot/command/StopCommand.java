package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandUtils.getChatId;
import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import com.github.m1santhrop.telegrambot.service.TelegramUserService;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public class StopCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public static final String STOP_MESSAGE = "Деактивировал все ваши подписки \uD83D\uDE1F.";

    @Override
    public void execute(Update update) {
        Long chatId = getChatId(update);
        telegramUserService.findByChatId(chatId).ifPresent(telegramUser -> {
            telegramUser.setActive(false);
            telegramUserService.save(telegramUser);
        });
        sendBotMessageService.sendMessage(chatId, STOP_MESSAGE);
    }
}

