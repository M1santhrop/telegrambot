package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.UnknownCommand.UNKNOWN_MESSAGE;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Unit-level testing for UnknownCommand")
class UnknownCommandTest extends AbstractCommandTest {

    @Override
    Command getCommand() {
        return new UnknownCommand(sendBotMessageService);
    }

    @Override
    String getCommandName() {
        return "/unknown";
    }

    @Override
    String getCommandMessage() {
        return UNKNOWN_MESSAGE;
    }
}