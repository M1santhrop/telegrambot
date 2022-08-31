package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.AddGroupSubCommand.GROUP_LIST_MESSAGE;
import static com.github.m1santhrop.telegrambot.command.CommandName.ADD_GROUP_SUB;

class AddGroupSubCommandTest extends AbstractCommandTest{

    @Override
    Command getCommand() {
        return new AddGroupSubCommand(sendBotMessageService, groupSubService, javaRushGroupClient);
    }

    @Override
    String getCommandName() {
        return ADD_GROUP_SUB.getName();
    }

    @Override
    String getCommandMessage() {
        return String.format(GROUP_LIST_MESSAGE, "");
    }
}