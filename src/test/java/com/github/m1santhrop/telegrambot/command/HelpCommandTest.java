package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandName.*;
import static com.github.m1santhrop.telegrambot.command.HelpCommand.HELP_MESSAGE;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Unit-level testing for HelpCommand")
class HelpCommandTest extends AbstractCommandTest {

    @Override
    Command getCommand() {
        return new HelpCommand(sendBotMessageService);
    }

    @Override
    String getCommandName() {
        return HELP.getName();
    }

    @Override
    String getCommandMessage() {
        return HELP_MESSAGE;
    }
}