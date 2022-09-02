package com.github.m1santhrop.telegrambot.command;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.github.m1santhrop.telegrambot.javarushclient.JavaRushGroupClient;
import com.github.m1santhrop.telegrambot.service.GroupSubService;
import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import com.github.m1santhrop.telegrambot.service.StatisticService;
import com.github.m1santhrop.telegrambot.service.TelegramUserService;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("Unit-level testing for CommandContainer")
class CommandContainerTest {

    private CommandContainer commandContainer;
    private SendBotMessageService sendBotMessageService = mock(SendBotMessageService.class);
    private TelegramUserService telegramUserService = mock(TelegramUserService.class);
    private GroupSubService groupSubService = mock(GroupSubService.class);
    private JavaRushGroupClient javaRushGroupClient = mock(JavaRushGroupClient.class);
    private StatisticService statisticService = mock(StatisticService.class);

    @BeforeEach
    void setUp() {
        commandContainer = new CommandContainer(sendBotMessageService, telegramUserService,
            groupSubService, javaRushGroupClient, Collections.singletonList("dmitryTimofeevDev"),
            statisticService);
    }

    @Test
    void shouldGetAllTheExistingCommands() {
        Arrays.stream(CommandName.values()).forEach(commandName -> {
            Command command = commandContainer.retrieveCommand(commandName.getName(),
                "dmitryTimofeevDev");
            assertNotEquals(UnknownCommand.class, command.getClass());
        });
    }

    @Test
    void shouldReturnUnknownCommand() {
        String unknownCommand = "/unknown";

        Command command = commandContainer.retrieveCommand(unknownCommand, "dmitryTimofeevDev");

        assertEquals(UnknownCommand.class, command.getClass());
    }
}