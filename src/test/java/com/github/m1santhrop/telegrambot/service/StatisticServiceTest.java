package com.github.m1santhrop.telegrambot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.github.m1santhrop.telegrambot.dto.StatisticDTO;
import com.github.m1santhrop.telegrambot.repository.entity.GroupSub;
import com.github.m1santhrop.telegrambot.repository.entity.TelegramUser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Unit-level testing for StatisticServiceImpl")
class StatisticServiceTest {
    
    private final TelegramUserService telegramUserService = mock(TelegramUserService.class);
    private final GroupSubService groupSubService = mock(GroupSubService.class);
    private StatisticService statisticService;
    
    @BeforeEach
    void init() {
        statisticService = new StatisticServiceImpl(telegramUserService, groupSubService);
    }
    
    @Test
    void shouldProperlySendStatDTO() {
        //given
        TelegramUser activeUser = new TelegramUser();
        activeUser.setChatId(1L);
        activeUser.setActive(true);
        List<TelegramUser> activeUsers = Collections.singletonList(activeUser);
        when(telegramUserService.findByActive(true)).thenReturn(activeUsers);
        
        TelegramUser inactiveUser = new TelegramUser();
        inactiveUser.setChatId(2L);
        inactiveUser.setActive(false);
        when(telegramUserService.findByActive(false)).thenReturn(Collections.singletonList(inactiveUser));

        GroupSub groupSub1 = buildGroupSub(1, "gr1", activeUsers);
        GroupSub groupSub2 = buildGroupSub(2, "gr2", activeUsers);
        GroupSub groupSub3 = buildGroupSub(3, "gr3", activeUsers);
        List<GroupSub> groupSubs = new ArrayList<>(Arrays.asList(groupSub1, groupSub2, groupSub3));
        activeUser.setGroupSubs(groupSubs);
        when(groupSubService.findAll()).thenReturn(groupSubs);
        
        //when
        StatisticDTO statisticDTO = statisticService.countBotStatistic();
        
        //then
        assertNotNull(statisticDTO);
        assertEquals(1, statisticDTO.getActiveUserCount());
        assertEquals(1, statisticDTO.getInactiveUserCount());
        assertEquals(3, statisticDTO.getGroupStatDTOs().size());
        assertEquals(3.0, statisticDTO.getAverageGroupCountByUser());
    }
    
    private GroupSub buildGroupSub(Integer id, String title, List<TelegramUser> users) {
        GroupSub groupSub = new GroupSub();
        groupSub.setId(1);
        groupSub.setTitle("gr1");
        groupSub.setUsers(users);
        return groupSub;
    }
}