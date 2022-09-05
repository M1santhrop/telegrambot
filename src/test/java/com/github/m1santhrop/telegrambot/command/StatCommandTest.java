package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandName.*;
import static com.github.m1santhrop.telegrambot.command.StatCommand.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.github.m1santhrop.telegrambot.dto.GroupStatDTO;
import com.github.m1santhrop.telegrambot.dto.StatisticDTO;
import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import com.github.m1santhrop.telegrambot.service.StatisticService;
import java.util.Collections;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@DisplayName("Unit-level testing for StatCommand")
class StatCommandTest {

    private SendBotMessageService sendBotMessageService = mock(SendBotMessageService.class);
    private StatisticService statisticService = mock(StatisticService.class);
    private Command statCommand;

    @BeforeEach
    void init() {
        statCommand = new StatCommand(sendBotMessageService, statisticService);
    }

    @Test
    void shouldProperlySendMessage() {
        //given
        Long chatId = 123L;
        GroupStatDTO groupStatDTO = new GroupStatDTO(1, "gr1", 1);
        StatisticDTO statisticDTO = new StatisticDTO(1, 1,
            Collections.singletonList(groupStatDTO), 2.5);
        Update update = buildUpdate(chatId, STAT.getName());

        String groupStatistics = statisticDTO.getGroupStatDTOs().stream().map(
                groupStat -> String.format("ID = %s (%s) - %s подписчиков", groupStat.getId(),
                    groupStat.getTitle(), groupStat.getActiveUserCount()))
            .collect(Collectors.joining("\n"));

        String expectedMessage = String.format(STAT_MESSAGE, statisticDTO.getActiveUserCount(),
            statisticDTO.getInactiveUserCount(), statisticDTO.getAverageGroupCountByUser(),
            groupStatistics);

        when(statisticService.countBotStatistic()).thenReturn(statisticDTO);

        //when
        statCommand.execute(update);

        //then
        verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }

    private Update buildUpdate(Long chatId, String text) {
        Update update = new Update();
        Chat chat = new Chat();
        chat.setId(chatId);
        Message message = new Message();
        message.setChat(chat);
        message.setText(text);
        update.setMessage(message);
        return update;
    }
}