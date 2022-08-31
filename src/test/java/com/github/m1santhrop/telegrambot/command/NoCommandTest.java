package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandName.*;
import static com.github.m1santhrop.telegrambot.command.NoCommand.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Unit-level testing for NoCommand")
class NoCommandTest extends AbstractCommandTest {

    @Override
    Command getCommand() {
        return new NoCommand(sendBotMessageService);
    }

    @Override
    String getCommandName() {
        return NOCOMMAND.getName();
    }

    @Override
    String getCommandMessage() {
        return NO_MESSAGE;
    }
}