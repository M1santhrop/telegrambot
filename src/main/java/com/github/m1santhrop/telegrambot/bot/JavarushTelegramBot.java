package com.github.m1santhrop.telegrambot.bot;

import com.github.m1santhrop.telegrambot.command.CommandContainer;
import com.github.m1santhrop.telegrambot.javarushclient.JavaRushGroupClient;
import com.github.m1santhrop.telegrambot.service.GroupSubService;
import com.github.m1santhrop.telegrambot.service.SendBotMessageServiceImpl;
import com.github.m1santhrop.telegrambot.service.StatisticService;
import com.github.m1santhrop.telegrambot.service.TelegramUserService;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Slf4j
@Component
public class JavarushTelegramBot extends TelegramLongPollingBot {

    private static final String COMMAND_PREFIX = "/";

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private final CommandContainer commandContainer;

    @Autowired
    public JavarushTelegramBot(TelegramUserService telegramUserService,
        GroupSubService groupSubService, JavaRushGroupClient javaRushGroupClient,
        @Value("#{'${bot.admins}'.split(',')}") List<String> admins, StatisticService statisticService) {
        if (admins.size() == 1 && admins.get(0).equalsIgnoreCase(StringUtils.EMPTY)) {
            admins = Collections.emptyList();
        }
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this),
            telegramUserService, groupSubService, javaRushGroupClient, admins, statisticService);
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String userName = receiveUserName(update.getMessage().getFrom());
        log.info("Received message: \"{}\" from {}", update.getMessage().getText(), userName);

        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText().trim();
            if (text.startsWith(COMMAND_PREFIX)) {
                String commandName = text.split("\\s")[0].toLowerCase();
                commandContainer.findCommand(commandName, userName).execute(update);
            } else {
                commandContainer.findCommand("noCommand", userName).execute(update);
            }
        } else {
            commandContainer.findCommand("noText", userName).execute(update);
        }
    }

    private String receiveUserName(User user) {
        return (user.getUserName() == null) ? user.getLastName() + " " + user.getFirstName()
            : user.getUserName();
    }
}
