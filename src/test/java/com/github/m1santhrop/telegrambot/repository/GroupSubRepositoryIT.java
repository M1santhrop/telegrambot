package com.github.m1santhrop.telegrambot.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import com.github.m1santhrop.telegrambot.repository.entity.GroupSub;
import com.github.m1santhrop.telegrambot.repository.entity.TelegramUser;
import java.util.List;
import java.util.Optional;
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
@DisplayName("Integration-level testing for GroupSubRepository")
class GroupSubRepositoryIT {

    @Autowired
    private GroupSubRepository groupSubRepository;

    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/fiveUsersForGroupSub.sql"})
    @Test
    void shouldProperlyGetAllUsersForGroupSub() {
        //given
        final int id = 1;
        
        //when
        Optional<GroupSub> groupSubOptional = groupSubRepository.findById(id);
        
        //then
        assertTrue(groupSubOptional.isPresent());
        GroupSub groupSub = groupSubOptional.get();
        assertEquals(id, groupSub.getId());
        List<TelegramUser> users = groupSub.getUsers();
        for (int i = 0; i < users.size(); i++) {
            TelegramUser user = users.get(i);
            assertEquals(String.valueOf(i + 1), user.getChatId());
            assertTrue(user.isActive());
        }
    }
}