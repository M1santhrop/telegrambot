package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandName.*;
import static com.github.m1santhrop.telegrambot.command.StopCommand.*;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Unit-level testing for StopCommand")
class StopCommandTest extends AbstractCommandTest {

    @Override
    Command getCommand() {
        return new StopCommand(sendBotMessageService);
    }

    @Override
    String getCommandName() {
        return STOP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return STOP_MESSAGE;
    }
}