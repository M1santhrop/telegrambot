package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandName.*;
import com.github.m1santhrop.telegrambot.javarushclient.JavaRushGroupClient;
import com.github.m1santhrop.telegrambot.service.GroupSubService;
import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import com.github.m1santhrop.telegrambot.service.TelegramUserService;
import com.google.common.collect.ImmutableMap;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService,
        TelegramUserService telegramUserService, GroupSubService groupSubService,
        JavaRushGroupClient javaRushGroupClient) {
        this.commandMap = ImmutableMap.<String, Command>builder()
            .put(START.getName(), new StartCommand(sendBotMessageService, telegramUserService))
            .put(STOP.getName(), new StopCommand(sendBotMessageService, telegramUserService))
            .put(HELP.getName(), new HelpCommand(sendBotMessageService))
            .put(NOCOMMAND.getName(), new NoCommand(sendBotMessageService))
            .put(NOTEXT.getName(), new NoTextCommand(sendBotMessageService))
            .put(STAT.getName(), new StatCommand(sendBotMessageService, telegramUserService))
            .put(ADD_GROUP_SUB.getName(), new AddGroupSubCommand(sendBotMessageService, groupSubService, javaRushGroupClient))
            .put(LIST_GROUP_SUB.getName(), new ListGroupSubCommand(telegramUserService, sendBotMessageService))
            .build();

        this.unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandName) {
        return commandMap.getOrDefault(commandName, unknownCommand);
    }
}
