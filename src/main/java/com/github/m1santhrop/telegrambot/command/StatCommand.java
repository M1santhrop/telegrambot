package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandUtils.getChatId;
import com.github.m1santhrop.telegrambot.command.annotation.AdminCommand;
import com.github.m1santhrop.telegrambot.dto.StatisticDTO;
import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import com.github.m1santhrop.telegrambot.service.StatisticService;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
@AdminCommand
public class StatCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final StatisticService statisticService;

    public static final String STAT_MESSAGE = "✨<b>Подготовил статистику</b>✨\n" +
        "- Количество активных пользователей: %s\n" +
        "- Количество неактивных пользователей: %s\n" +
        "- Среднее количество групп на одного пользователя: %s\n\n" +
        "<b>Информация по активным группам</b>:\n" +
        "%s";

    @Override
    public void execute(Update update) {
        StatisticDTO statisticDTO = statisticService.countBotStatistic();

        String collectedGroups = statisticDTO.getGroupStatDTOs().stream()
            .map(
                groupStatDTO -> String.format("ID = %s (%s) - %s подписчиков", groupStatDTO.getId(),
                    groupStatDTO.getTitle(), groupStatDTO.getActiveUserCount()))
            .collect(Collectors.joining("\n"));

        sendBotMessageService.sendMessage(getChatId(update),
            String.format(STAT_MESSAGE, statisticDTO.getActiveUserCount(),
                statisticDTO.getInactiveUserCount(), statisticDTO.getAverageGroupCountByUser(),
                collectedGroups));
    }
}
