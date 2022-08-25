package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandName.*;
import static com.github.m1santhrop.telegrambot.command.StartCommand.START_MESSAGE;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Unit-level testing for StartCommand")
class StartCommandTest extends AbstractCommandTest {

    @Override
    Command getCommand() {
        return new StartCommand(sendBotMessageService);
    }

    @Override
    String getCommandName() {
        return START.getName();
    }

    @Override
    String getCommandMessage() {
        return START_MESSAGE;
    }
}