package com.github.m1santhrop.telegrambot.service;

import com.github.m1santhrop.telegrambot.dto.GroupStatDTO;
import com.github.m1santhrop.telegrambot.dto.StatisticDTO;
import com.github.m1santhrop.telegrambot.repository.entity.TelegramUser;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticServiceImpl implements StatisticService {

    private final TelegramUserService telegramUserService;
    private final GroupSubService groupSubService;

    @Autowired
    public StatisticServiceImpl(
        TelegramUserService telegramUserService,
        GroupSubService groupSubService) {
        this.telegramUserService = telegramUserService;
        this.groupSubService = groupSubService;
    }

    @Override
    public StatisticDTO countBotStatistic() {
        List<TelegramUser> activeUsers = telegramUserService.findByActive(true);
        List<TelegramUser> inactiveUsers = telegramUserService.findByActive(false);

        return new StatisticDTO(
            activeUsers.size(),
            inactiveUsers.size(),
            calculateGroupStatDTOs(),
            calculateAverageGroupCountByUser(activeUsers)
        );
    }

    private List<GroupStatDTO> calculateGroupStatDTOs() {
        return groupSubService.findAll().stream()
            .filter(groupSub -> !groupSub.getUsers().isEmpty())
            .map(groupSub -> {
                int count = (int) groupSub.getUsers().stream()
                    .filter(TelegramUser::isActive)
                    .count();
                return new GroupStatDTO(groupSub.getId(), groupSub.getTitle(), count);
            }).collect(Collectors.toList());
    }

    private double calculateAverageGroupCountByUser(List<TelegramUser> activeUsers) {
        double sumOfGroupSubs = activeUsers.stream()
            .mapToInt(telegramUser -> telegramUser.getGroupSubs().size())
            .sum();
        return sumOfGroupSubs / activeUsers.size();
    }
}
