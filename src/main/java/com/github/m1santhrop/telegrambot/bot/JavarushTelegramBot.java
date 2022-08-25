package com.github.m1santhrop.telegrambot.bot;

import com.github.m1santhrop.telegrambot.command.CommandContainer;
import com.github.m1santhrop.telegrambot.service.SendBotMessageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
public class JavarushTelegramBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavarushTelegramBot.class);
    private static final String COMMAND_PREFIX = "/";

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private final CommandContainer commandContainer;

    public JavarushTelegramBot() {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this));
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
        LOGGER.info("Received message: \"{}\" from {}", update.getMessage().getText(), userName);
        
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText().trim();
            if (text.startsWith(COMMAND_PREFIX)) {
                String commandName = text.split("\\s")[0].toLowerCase();
                commandContainer.retrieveCommand(commandName).execute(update);
            } else {
                commandContainer.retrieveCommand("nocommand").execute(update);
            }
        }
    }

    private String receiveUserName(User user) {
        return (user.getUserName() == null) ? user.getLastName() + " " + user.getFirstName()
            : user.getUserName();
    }
}
