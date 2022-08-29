package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandName.*;
import static com.github.m1santhrop.telegrambot.command.StatCommand.*;

class StatCommandTest extends AbstractCommandTest{

    @Override
    Command getCommand() {
        return new StatCommand(sendBotMessageService, telegramUserService);
    }

    @Override
    String getCommandName() {
        return STAT.getName();
    }

    @Override
    String getCommandMessage() {
        return String.format(STAT_MESSAGE, 0);
    }
}