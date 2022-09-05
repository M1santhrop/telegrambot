package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandName.*;
import static java.util.Objects.*;
import com.github.m1santhrop.telegrambot.command.annotation.AdminCommand;
import com.github.m1santhrop.telegrambot.javarushclient.JavaRushGroupClient;
import com.github.m1santhrop.telegrambot.service.GroupSubService;
import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import com.github.m1santhrop.telegrambot.service.StatisticService;
import com.github.m1santhrop.telegrambot.service.TelegramUserService;
import com.google.common.collect.ImmutableMap;
import java.util.List;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;
    private final List<String> admins;

    public CommandContainer(SendBotMessageService sendBotMessageService,
        TelegramUserService telegramUserService, GroupSubService groupSubService,
        JavaRushGroupClient javaRushGroupClient, List<String> admins, StatisticService statisticService) {
        this.commandMap = ImmutableMap.<String, Command>builder()
            .put(START.getName(), new StartCommand(sendBotMessageService, telegramUserService))
            .put(STOP.getName(), new StopCommand(sendBotMessageService, telegramUserService))
            .put(HELP.getName(), new HelpCommand(sendBotMessageService))
            .put(NOCOMMAND.getName(), new NoCommand(sendBotMessageService))
            .put(NOTEXT.getName(), new NoTextCommand(sendBotMessageService))
            .put(STAT.getName(), new StatCommand(sendBotMessageService, statisticService))
            .put(ADD_GROUP_SUB.getName(), new AddGroupSubCommand(sendBotMessageService, groupSubService, javaRushGroupClient))
            .put(LIST_GROUP_SUB.getName(), new ListGroupSubCommand(telegramUserService, sendBotMessageService))
            .put(DELETE_GROUP_SUB.getName(), new DeleteGroupSubCommand(sendBotMessageService, groupSubService, telegramUserService))
            .put(ADMIN_HELP.getName(), new AdminHelpCommand(sendBotMessageService))
            .build();

        this.unknownCommand = new UnknownCommand(sendBotMessageService);
        this.admins = admins;
    }

    public Command findCommand(String commandName, String username) {
        Command command = commandMap.getOrDefault(commandName, unknownCommand);
        if (isAdminCommand(command)) {
            if (admins.contains(username)) {
                return command;
            } else {
                return unknownCommand;
            }
        }
        return command;
    }

    private boolean isAdminCommand(Command command) {
        return nonNull(command) && nonNull(command.getClass().getAnnotation(AdminCommand.class));
        
    }
}
