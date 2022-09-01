package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandName.LIST_GROUP_SUB;
import static com.github.m1santhrop.telegrambot.command.ListGroupSubCommand.MESSAGE;
import static java.lang.Long.parseLong;
import static java.util.stream.Collectors.joining;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.github.m1santhrop.telegrambot.repository.entity.GroupSub;
import com.github.m1santhrop.telegrambot.repository.entity.TelegramUser;
import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import com.github.m1santhrop.telegrambot.service.TelegramUserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@DisplayName("Unit-level testing for ListGroupSubCommand")
class ListGroupSubCommandTest {

    private Command listGroupSubCommand;
    private final TelegramUserService telegramUserService = mock(TelegramUserService.class);
    private final SendBotMessageService sendBotMessageService = mock(SendBotMessageService.class);

    @BeforeEach
    void init() {
        listGroupSubCommand = new ListGroupSubCommand(telegramUserService, sendBotMessageService);
    }

    @Test
    void shouldProperlyShowsListGroupSub() {
        //given
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId("1");
        telegramUser.setActive(true);

        List<GroupSub> groupSubs = new ArrayList<>();
        groupSubs.add(buildGroupSub(1, "gr1"));
        groupSubs.add(buildGroupSub(2, "gr2"));
        groupSubs.add(buildGroupSub(3, "gr3"));
        telegramUser.setGroupSubs(groupSubs);

        Chat chat = new Chat();
        chat.setId(parseLong(telegramUser.getChatId()));
        Message message = new Message();
        message.setChat(chat);
        message.setText(LIST_GROUP_SUB.getName());
        Update update = new Update();
        update.setMessage(message);
        when(telegramUserService.findByChatId(telegramUser.getChatId())).thenReturn(
            Optional.of(telegramUser));

        String collectedString = telegramUser.getGroupSubs().stream()
            .map(groupSub -> String.format("ID : %s, Группа : %s %n", groupSub.getId(),
                groupSub.getTitle()))
            .collect(joining());

        //when
        listGroupSubCommand.execute(update);

        //then
        verify(sendBotMessageService).sendMessage(telegramUser.getChatId(),
            MESSAGE + collectedString);
    }

    private GroupSub buildGroupSub(Integer id, String title) {
        GroupSub groupSub = new GroupSub();
        groupSub.setId(id);
        groupSub.setTitle(title);
        return groupSub;
    }
}