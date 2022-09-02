package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandName.STAT;
import static com.github.m1santhrop.telegrambot.command.CommandUtils.getChatId;
import com.github.m1santhrop.telegrambot.command.annotation.AdminCommand;
import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
@AdminCommand
public class AdminHelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static final String ADMIN_HELP_MESSAGE = String.format(
        "✨<b>Доступные команды админа</b>✨\n\n"
            + "<b>Получить статистику</b>\n"
            + "%s - статистика бота\n",
        STAT.getName());

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(getChatId(update), ADMIN_HELP_MESSAGE);
    }
}
