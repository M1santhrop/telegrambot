package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandName.DELETE_GROUP_SUB;
import static com.github.m1santhrop.telegrambot.command.CommandUtils.GROUP_NOT_FOUND_MESSAGE;
import static com.github.m1santhrop.telegrambot.command.DeleteGroupSubCommand.DELETE_GROUP_SUBS_MESSAGE;
import static com.github.m1santhrop.telegrambot.command.DeleteGroupSubCommand.EMPTY_GROUP_SUBS_MESSAGE;
import static com.github.m1santhrop.telegrambot.command.DeleteGroupSubCommand.NOT_SUBSCRUBED_GROUP_MESSAGE;
import static com.github.m1santhrop.telegrambot.command.DeleteGroupSubCommand.REMOVED_FROM_GROUP_MESSAGE;
import static java.lang.Long.parseLong;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.github.m1santhrop.telegrambot.repository.entity.GroupSub;
import com.github.m1santhrop.telegrambot.repository.entity.TelegramUser;
import com.github.m1santhrop.telegrambot.service.GroupSubService;
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

@DisplayName("Unit-level testing for DeleteGroupSubCommand")
class DeleteGroupSubCommandTest {

    private Command deleteGroupSubCommand;
    private final SendBotMessageService sendBotMessageService = mock(SendBotMessageService.class);
    private final GroupSubService groupSubService = mock(GroupSubService.class);
    private final TelegramUserService telegramUserService = mock(TelegramUserService.class);

    @BeforeEach
    void init() {
        deleteGroupSubCommand = new DeleteGroupSubCommand(sendBotMessageService, groupSubService,
            telegramUserService);
    }

    @Test
    void shouldProperlyReturnEmptySubscriptionList() {
        //given
        String chatId = "1";
        Update update = buildUpdate(chatId, DELETE_GROUP_SUB.getName());

        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setGroupSubs(new ArrayList<>());
        when(telegramUserService.findByChatId(chatId)).thenReturn(Optional.of(telegramUser));

        //when
        deleteGroupSubCommand.execute(update);

        //then
        verify(sendBotMessageService).sendMessage(chatId, EMPTY_GROUP_SUBS_MESSAGE);
    }

    @Test
    void shouldProperlyReturnSubscriptionList() {
        //given
        String chatId = "1";
        Update update = buildUpdate(chatId, DELETE_GROUP_SUB.getName());

        TelegramUser telegramUser = new TelegramUser();
        GroupSub groupSub = new GroupSub();
        groupSub.setId(1);
        groupSub.setTitle("gr1");
        telegramUser.setGroupSubs(singletonList(groupSub));
        String expectedMessage = String.format(DELETE_GROUP_SUBS_MESSAGE, String.format("%s - %s %n", groupSub.getId(), groupSub.getTitle()));
        when(telegramUserService.findByChatId(chatId)).thenReturn(Optional.of(telegramUser));

        //when
        deleteGroupSubCommand.execute(update);

        //then
        verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }

    @Test
    void shouldRejectByInvalidGroupId() {
        //given
        String chatId = "1";
        String groupId = "abc";
        Update update = buildUpdate(chatId,
            String.format("%s %s", DELETE_GROUP_SUB.getName(), groupId));
        String expectedMessage = String.format(GROUP_NOT_FOUND_MESSAGE, groupId);

        TelegramUser telegramUser = new TelegramUser();
        GroupSub groupSub = new GroupSub();
        groupSub.setId(1);
        groupSub.setTitle("gr1");
        telegramUser.setGroupSubs(singletonList(groupSub));
        when(telegramUserService.findByChatId(chatId)).thenReturn(Optional.of(telegramUser));

        //when
        deleteGroupSubCommand.execute(update);

        //then
        verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }
    
    @Test
    void shouldProperlyDeleteByGroupId() {
        //given
        String chatId = "1";
        Integer groupId = 1;
        Update update = buildUpdate(chatId, String.format("%s %s", DELETE_GROUP_SUB, groupId));
        
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId(chatId);
        telegramUser.setActive(true);

        GroupSub groupSub = new GroupSub();
        groupSub.setId(groupId);
        groupSub.setTitle("gr1");
        List<TelegramUser> users = new ArrayList<>(List.of(telegramUser));
        groupSub.setUsers(users);
        telegramUser.setGroupSubs(singletonList(groupSub));

        String expectedMessage = String.format(REMOVED_FROM_GROUP_MESSAGE, groupSub.getTitle());

        when(groupSubService.findById(groupId)).thenReturn(Optional.of(groupSub));
        when(telegramUserService.findByChatId(chatId)).thenReturn(Optional.of(telegramUser));
        
        //when
        deleteGroupSubCommand.execute(update);
        
        //then
        users.remove(telegramUser);
        verify(groupSubService).save(groupSub);
        verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }

    @Test
    void shouldDoesNotExistByGroupId() {
        //given
        String chatId = "1";
        Integer groupId = 2;
        Update update = buildUpdate(chatId,
            String.format("%s %s", DELETE_GROUP_SUB.getName(), groupId));
        String expectedMessage = String.format(NOT_SUBSCRUBED_GROUP_MESSAGE, groupId);

        when(groupSubService.findById(groupId)).thenReturn(Optional.empty());

        //when
        deleteGroupSubCommand.execute(update);

        //then
        verify(groupSubService).findById(groupId);
        verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }

    private Update buildUpdate(String chatId, String text) {
        Update update = new Update();
        Chat chat = new Chat();
        chat.setId(parseLong(chatId));
        Message message = new Message();
        message.setChat(chat);
        message.setText(text);
        update.setMessage(message);
        return update;
    }
}