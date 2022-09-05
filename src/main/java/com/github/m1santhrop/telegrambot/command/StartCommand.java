package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandUtils.getChatId;
import com.github.m1santhrop.telegrambot.repository.entity.TelegramUser;
import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import com.github.m1santhrop.telegrambot.service.TelegramUserService;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public static final String START_MESSAGE =
        "Привет. Я Telegram Bot. Я помогу тебе быть в курсе последних" + "\n"
            + "статей тех авторов, котрые тебе интересны. Я еще маленький и только учусь.";

    @Override
    public void execute(Update update) {
        Long chatId = getChatId(update);

        telegramUserService.findByChatId(chatId).ifPresentOrElse(
            telegramUser -> {
                telegramUser.setActive(true);
                telegramUserService.save(telegramUser);
            },
            () -> {
                TelegramUser newTelegramUser = new TelegramUser();
                newTelegramUser.setChatId(chatId);
                newTelegramUser.setActive(true);
                telegramUserService.save(newTelegramUser);
            });

        sendBotMessageService.sendMessage(chatId, START_MESSAGE);
    }
}

