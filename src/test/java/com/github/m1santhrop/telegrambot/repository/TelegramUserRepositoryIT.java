package com.github.m1santhrop.telegrambot.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.*;
import com.github.m1santhrop.telegrambot.repository.entity.GroupSub;
import com.github.m1santhrop.telegrambot.repository.entity.TelegramUser;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@DisplayName("Integration-level testing for TelegramUserRepository")
class TelegramUserRepositoryIT {

    @Autowired
    private TelegramUserRepository telegramUserRepository;
    
    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/telegram_users.sql"})
    @Test
    void shouldProperlyFindAllActiveUsers() {
        //when
        List<TelegramUser> users = telegramUserRepository.findByActive(true);

        //then
        Assertions.assertEquals(5, users.size());
    }
    
    @Sql(scripts = {"/sql/clearDbs.sql"})
    @Test
    void shouldProperlySaveTelegramUser() {
        //given
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId("1234567890");
        telegramUser.setActive(true);
        
        telegramUserRepository.save(telegramUser);

        //when
        Optional<TelegramUser> savedUser = telegramUserRepository.findById(telegramUser.getChatId());
        
        //then
        Assertions.assertTrue(savedUser.isPresent());
        Assertions.assertEquals(telegramUser, savedUser.get());
    }

    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/fiveGroupSubsForUser.sql"})
    @Test
    void shouldProperlyGetAllGroupSubsForUser() {
        //when
        Optional<TelegramUser> user = telegramUserRepository.findById("1");

        //then
        assertTrue(user.isPresent());
        List<GroupSub> groupSubs = user.get().getGroupSubs();
        for (int i = 0; i < groupSubs.size(); i++) {
            GroupSub groupSub = groupSubs.get(i);
            assertEquals(String.format("g%d", i + 1), groupSub.getTitle());
            assertEquals(i + 1, groupSub.getId());
            assertEquals(i + 1, groupSub.getLastArticleId());
        }
    }
}