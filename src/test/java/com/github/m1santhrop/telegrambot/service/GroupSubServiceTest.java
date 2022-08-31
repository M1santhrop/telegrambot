package com.github.m1santhrop.telegrambot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.github.m1santhrop.telegrambot.javarushclient.dto.GroupDiscussionInfo;
import com.github.m1santhrop.telegrambot.repository.GroupSubRepository;
import com.github.m1santhrop.telegrambot.repository.entity.GroupSub;
import com.github.m1santhrop.telegrambot.repository.entity.TelegramUser;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("Unit-level testing for GroupSubServiceImpl")
class GroupSubServiceTest {

    private GroupSubService groupSubService;
    private GroupSubRepository groupSubRepository;
    private TelegramUser newTelegramUser;

    private static final String CHAT_ID = "1";

    @BeforeEach
    void init() {
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        groupSubRepository = Mockito.mock(GroupSubRepository.class);
        groupSubService = new GroupSubServiceImpl(groupSubRepository, telegramUserService);
        
        newTelegramUser = new TelegramUser();
        newTelegramUser.setChatId(CHAT_ID);
        newTelegramUser.setActive(true);
        
        when(telegramUserService.findByChatId(CHAT_ID)).thenReturn(Optional.of(newTelegramUser));
    }
    
    @Test
    void shouldProperlySaveGroup() {
        //given
        GroupDiscussionInfo groupDiscussionInfo = new GroupDiscussionInfo();
        groupDiscussionInfo.setId(1);
        groupDiscussionInfo.setTitle("g1");

        GroupSub expectedGroupSub = new GroupSub();
        expectedGroupSub.setId(groupDiscussionInfo.getId());
        expectedGroupSub.setTitle(groupDiscussionInfo.getTitle());
        expectedGroupSub.addUser(newTelegramUser);
        
        //when
        groupSubService.save(CHAT_ID, groupDiscussionInfo);
        
        //then
        verify(groupSubRepository).save(expectedGroupSub);
    }
    
    @Test
    void shouldProperlyAddUserToExistingGroup() {
        //given
        TelegramUser oldTelegramUser = new TelegramUser();
        oldTelegramUser.setChatId("2");
        oldTelegramUser.setActive(true);
        
        GroupDiscussionInfo groupDiscussionInfo = new GroupDiscussionInfo();
        groupDiscussionInfo.setId(1);
        groupDiscussionInfo.setTitle("g1");
        
        GroupSub groupSub = new GroupSub();
        groupSub.setId(groupDiscussionInfo.getId());
        groupSub.setTitle(groupDiscussionInfo.getTitle());
        groupSub.addUser(oldTelegramUser);
        
        when(groupSubRepository.findById(groupDiscussionInfo.getId())).thenReturn(Optional.of(groupSub));
        
        //when
        groupSubService.save(CHAT_ID, groupDiscussionInfo);
        
        //then
        verify(groupSubRepository).findById(groupDiscussionInfo.getId());
        verify(groupSubRepository).save(groupSub);
    }
}