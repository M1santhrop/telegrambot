package com.github.m1santhrop.telegrambot.dto;

import java.util.List;
import lombok.Data;

@Data
public class StatisticDTO {
    private final Integer activeUserCount;
    private final Integer inactiveUserCount;
    private final List<GroupStatDTO> groupStatDTOs;
    private final Double averageGroupCountByUser;
}
