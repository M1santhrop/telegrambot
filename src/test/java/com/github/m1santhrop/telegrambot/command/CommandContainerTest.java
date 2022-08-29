package com.github.m1santhrop.telegrambot.command;

import static org.junit.jupiter.api.Assertions.*;
import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import com.github.m1santhrop.telegrambot.service.TelegramUserService;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("Unit-level testing for CommandContainer")
class CommandContainerTest {
    
    private CommandContainer commandContainer;
    private SendBotMessageService sendBotMessageService;
    private TelegramUserService telegramUserService;

    @BeforeEach
    void setUp() {
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        telegramUserService = Mockito.mock(TelegramUserService.class);
        commandContainer = new CommandContainer(sendBotMessageService, telegramUserService);
    }
    
    @Test
    void shouldGetAllTheExistingCommands() {
        Arrays.stream(CommandName.values()).forEach(commandName -> {
            Command command = commandContainer.retrieveCommand(commandName.getName());
            assertNotEquals(UnknownCommand.class, command.getClass());
        });
    }
    
    @Test
    void shouldReturnUnknownCommand() {
        String unknownCommand = "/unknown";

        Command command = commandContainer.retrieveCommand(unknownCommand);
        
        assertEquals(UnknownCommand.class, command.getClass());
    }
}