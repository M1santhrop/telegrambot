package com.github.m1santhrop.telegrambot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.github.m1santhrop.telegrambot.javarushclient.JavaRushGroupClient;
import com.github.m1santhrop.telegrambot.javarushclient.dto.GroupDiscussionInfo;
import com.github.m1santhrop.telegrambot.repository.GroupSubRepository;
import com.github.m1santhrop.telegrambot.repository.entity.GroupSub;
import com.github.m1santhrop.telegrambot.repository.entity.TelegramUser;
import java.util.ArrayList;
import java.util.List;
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
    private static final Integer GROUP_ID = 10;
    private static final Integer LAST_POST_ID = 123;

    @BeforeEach
    void init() {
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        JavaRushGroupClient javaRushGroupClient = Mockito.mock(JavaRushGroupClient.class);
        groupSubRepository = Mockito.mock(GroupSubRepository.class);
        groupSubService = new GroupSubServiceImpl(groupSubRepository, telegramUserService, javaRushGroupClient);
        
        newTelegramUser = new TelegramUser();
        newTelegramUser.setChatId(CHAT_ID);
        newTelegramUser.setActive(true);
        
        when(telegramUserService.findByChatId(CHAT_ID)).thenReturn(Optional.of(newTelegramUser));
        when(javaRushGroupClient.findLastPostId(GROUP_ID)).thenReturn(LAST_POST_ID);
    }
    
    @Test
    void shouldProperlySaveGroup() {
        //given
        GroupDiscussionInfo groupDiscussionInfo = new GroupDiscussionInfo();
        groupDiscussionInfo.setId(GROUP_ID);
        groupDiscussionInfo.setTitle("g1");

        GroupSub expectedGroupSub = new GroupSub();
        expectedGroupSub.setId(groupDiscussionInfo.getId());
        expectedGroupSub.setTitle(groupDiscussionInfo.getTitle());
        expectedGroupSub.setLastArticleId(LAST_POST_ID);
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
        groupDiscussionInfo.setId(GROUP_ID);
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
    
    @Test
    void shouldProperlyFindGroupSubById(){
        //given
        GroupSub groupSub = new GroupSub();
        groupSub.setId(GROUP_ID);
        groupSub.setTitle("gr1");
        
        when(groupSubRepository.findById(groupSub.getId())).thenReturn(Optional.of(groupSub));
        
        //when
        Optional<GroupSub> actualGroupSub = groupSubService.findById(groupSub.getId());

        //then
        assertTrue(actualGroupSub.isPresent());
        assertEquals(groupSub, actualGroupSub.get());
        verify(groupSubRepository).findById(groupSub.getId());
    }

    @Test
    void shouldProperlySaveGroupSub(){
        //given
        GroupSub groupSub = new GroupSub();
        groupSub.setId(GROUP_ID);
        groupSub.setTitle("gr1");
        
        when(groupSubRepository.save(groupSub)).thenReturn(groupSub);
        
        //when
        GroupSub saved = groupSubService.save(groupSub);
        
        //then
        assertEquals(groupSub, saved);
        verify(groupSubRepository).save(groupSub);
    }

    @Test
    void shouldProperlyFindAllGroupSubs(){
        //given
        List<GroupSub> groupSubs = new ArrayList<>();
        groupSubs.add(buildGroupSub(1, "gr1"));
        groupSubs.add(buildGroupSub(2, "gr2"));
        groupSubs.add(buildGroupSub(3, "gr3"));
        
        when(groupSubRepository.findAll()).thenReturn(groupSubs);
        
        //when
        List<GroupSub> actualGroupSubs = groupSubService.findAll();
        
        //then
        assertEquals(groupSubs.size(), actualGroupSubs.size());
        verify(groupSubRepository).findAll();
    }
    
    private GroupSub buildGroupSub(Integer id, String title) {
        GroupSub groupSub = new GroupSub();
        groupSub.setId(id);
        groupSub.setTitle(title);
        return groupSub;
    }
}