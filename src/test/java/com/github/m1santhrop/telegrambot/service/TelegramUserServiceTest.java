package com.github.m1santhrop.telegrambot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.github.m1santhrop.telegrambot.repository.TelegramUserRepository;
import com.github.m1santhrop.telegrambot.repository.entity.TelegramUser;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Unit-level testing for TelegramUserServiceImpl")
class TelegramUserServiceTest {

    private TelegramUserRepository telegramUserRepository;
    private TelegramUserService telegramUserService;

    @BeforeEach
    void setUp() {
        telegramUserRepository = mock(TelegramUserRepository.class);
        telegramUserService = new TelegramUserServiceImpl(telegramUserRepository);
    }

    @Test
    void shouldProperlySaveUser() {
        //given
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId(1L);
        telegramUser.setActive(true);

        //when
        telegramUserService.save(telegramUser);

        //then
        verify(telegramUserRepository, times(1)).save(telegramUser);
    }

    @Test
    void shouldProperlyFindAllActiveUsers() {
        //given
        List<TelegramUser> expectedUsers = new ArrayList<>();
        TelegramUser telegramUser1 = new TelegramUser();
        telegramUser1.setChatId(1L);
        telegramUser1.setActive(true);
        expectedUsers.add(telegramUser1);
        
        when(telegramUserRepository.findByActive(true)).thenReturn(expectedUsers);

        //when
        List<TelegramUser> users = telegramUserService.findByActive(true);
        
        //then
        assertEquals(expectedUsers, users);
    }

    @Test
    void shouldProperlyFindByChatId() {
        //given
        Long chatId = 1L;
        TelegramUser expectedTelegramUser = new TelegramUser();
        expectedTelegramUser.setChatId(chatId);
        expectedTelegramUser.setActive(true);
        
        when(telegramUserRepository.findById(chatId)).thenReturn(Optional.of(expectedTelegramUser));
        
        //when
        Optional<TelegramUser> user = telegramUserService.findByChatId(chatId);

        //then
        assertTrue(user.isPresent());
        assertEquals(expectedTelegramUser, user.get());
    }
}