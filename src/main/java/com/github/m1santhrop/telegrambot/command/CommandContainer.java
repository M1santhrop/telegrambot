package com.github.m1santhrop.telegrambot.command;

import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import com.google.common.collect.ImmutableMap;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService) {
        this.commandMap = ImmutableMap.<String, Command>builder()
            .put(CommandName.START.getName(), new StartCommand(sendBotMessageService))
            .put(CommandName.STOP.getName(), new StopCommand(sendBotMessageService))
            .put(CommandName.HELP.getName(), new HelpCommand(sendBotMessageService))
            .put(CommandName.NO.getName(), new NoCommand(sendBotMessageService))
            .build();

        this.unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandName) {
        return commandMap.getOrDefault(commandName, unknownCommand);
    }
}
