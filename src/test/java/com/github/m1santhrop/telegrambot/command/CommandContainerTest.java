package com.github.m1santhrop.telegrambot.command;

import static org.junit.jupiter.api.Assertions.*;
import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("Unit-level testing for CommandContainer")
class CommandContainerTest {
    
    private CommandContainer commandContainer;
    private SendBotMessageService sendBotMessageService;

    @BeforeEach
    void setUp() {
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        commandContainer = new CommandContainer(sendBotMessageService);
    }
    
    @Test
    public void shouldGetAllTheExistingCommands() {
        Arrays.stream(CommandName.values()).forEach(commandName -> {
            Command command = commandContainer.retrieveCommand(commandName.getCommandName());
            assertNotEquals(UnknownCommand.class, command.getClass());
        });
    }
    
    @Test
    public void shouldReturnUnknownCommand() {
        String unknownCommand = "/unknown";

        Command command = commandContainer.retrieveCommand(unknownCommand);
        
        assertEquals(UnknownCommand.class, command.getClass());
    }
}