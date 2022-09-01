package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandName.ADD_GROUP_SUB;
import static com.github.m1santhrop.telegrambot.command.CommandName.DELETE_GROUP_SUB;
import static com.github.m1santhrop.telegrambot.command.CommandName.HELP;
import static com.github.m1santhrop.telegrambot.command.CommandName.LIST_GROUP_SUB;
import static com.github.m1santhrop.telegrambot.command.CommandName.START;
import static com.github.m1santhrop.telegrambot.command.CommandName.STAT;
import static com.github.m1santhrop.telegrambot.command.CommandName.STOP;
import static com.github.m1santhrop.telegrambot.command.CommandUtils.*;
import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static final String HELP_MESSAGE = String.format("✨<b>Дотупные команды</b>✨\n\n"
            + "<b>Начать\\закончить работу с ботом:</b>\n"
            + "%s - начать работу со мной\n"
            + "%s - приостановить работу со мной\n\n"
            + "<b>Работа с подписками на группу:</b>\n"
            + "%s - добавить подписку на группу\n"
            + "%s - отписаться от группы\n"
            + "%s - показать ваши текущие подписки\n\n"
            + "%s - получить помощь в работе со мной\n"
            + "%s - получить мою статистику использования\n",
        START.getName(), STOP.getName(), ADD_GROUP_SUB.getName(), DELETE_GROUP_SUB.getName(),
        LIST_GROUP_SUB.getName(), HELP.getName(), STAT.getName());

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(getChatId(update), HELP_MESSAGE);
    }
}
