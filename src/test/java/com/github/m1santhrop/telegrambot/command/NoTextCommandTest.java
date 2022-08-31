package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandName.NOTEXT;
import static com.github.m1santhrop.telegrambot.command.NoTextCommand.NO_TEXT_MESSAGE;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Unit-level testing for NoTextCommand")
class NoTextCommandTest extends AbstractCommandTest {

    @Override
    Command getCommand() {
        return new NoTextCommand(sendBotMessageService);
    }

    @Override
    String getCommandName() {
        return NOTEXT.getName();
    }

    @Override
    String getCommandMessage() {
        return NO_TEXT_MESSAGE;
    }
}