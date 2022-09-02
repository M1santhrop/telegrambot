package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.AdminHelpCommand.ADMIN_HELP_MESSAGE;
import static com.github.m1santhrop.telegrambot.command.CommandName.ADMIN_HELP;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Unit-level testing for AdminHelpCommand")
class AdminHelpCommandTest extends AbstractCommandTest{

    @Override
    Command getCommand() {
        return new AdminHelpCommand(sendBotMessageService);
    }

    @Override
    String getCommandName() {
        return ADMIN_HELP.getName();
    }

    @Override
    String getCommandMessage() {
        return ADMIN_HELP_MESSAGE;
    }
}