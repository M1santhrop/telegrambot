package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandName.DELETE_GROUP_SUB;
import static com.github.m1santhrop.telegrambot.command.CommandUtils.getChatId;
import static com.github.m1santhrop.telegrambot.command.CommandUtils.getMessage;
import static com.github.m1santhrop.telegrambot.command.CommandUtils.sendGroupNotFound;
import static java.util.stream.Collectors.*;
import com.github.m1santhrop.telegrambot.exception.ExceptionSender;
import com.github.m1santhrop.telegrambot.repository.entity.GroupSub;
import com.github.m1santhrop.telegrambot.repository.entity.TelegramUser;
import com.github.m1santhrop.telegrambot.service.GroupSubService;
import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import com.github.m1santhrop.telegrambot.service.TelegramUserService;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public class DeleteGroupSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final GroupSubService groupSubService;
    private final TelegramUserService telegramUserService;

    public static final String EMPTY_GROUP_SUBS_MESSAGE = "Пока нет подписок на группы. Чтобы добавить подписку напиши /addGroupSub";
    public static final String DELETE_GROUP_SUBS_MESSAGE =
        "Чтобы удалить подписку на группу - передай комадну вместе с ID группы. \n" +
            "Например: /deleteGroupSub 16 \n\n" +
            "я подготовил список всех групп, на которые ты подписан) \n\n" +
            "ID группы - имя группы \n\n" +
            "%s";
    public static final String REMOVED_FROM_GROUP_MESSAGE = "Удалил подписку на группу: %s";
    public static final String NOT_SUBSCRUBED_GROUP_MESSAGE = "У вас нет группы с ID = %s";

    @Override
    public void execute(Update update) {
        String message = getMessage(update);
        String chatId = getChatId(update);
        if (message.equalsIgnoreCase(DELETE_GROUP_SUB.getName())) {
            sendGroupIdList(chatId);
            return;
        }
        String groupId = message.split("\\s")[1];
        if (StringUtils.isNumeric(groupId)) {
            Optional<GroupSub> groupSubOptional = groupSubService.findById(Integer.valueOf(groupId));
            if (groupSubOptional.isPresent()) {
                GroupSub groupSub = groupSubOptional.get();
                TelegramUser telegramUser = telegramUserService.findByChatId(chatId)
                    .orElseThrow(() -> ExceptionSender.throwNotFoundUserException(chatId));
                groupSub.getUsers().remove(telegramUser);
                groupSubService.save(groupSub);
                sendBotMessageService.sendMessage(chatId, String.format(REMOVED_FROM_GROUP_MESSAGE, groupSub.getTitle()));
            } else {
                sendBotMessageService.sendMessage(chatId, String.format(NOT_SUBSCRUBED_GROUP_MESSAGE, groupId));
            }
        } else {
            sendGroupNotFound(sendBotMessageService, chatId, groupId);
        }
    }

    private void sendGroupIdList(String chatId) {
        List<GroupSub> groupSubs = telegramUserService.findByChatId(chatId)
            .orElseThrow(() -> ExceptionSender.throwNotFoundUserException(chatId)).getGroupSubs();
        if (groupSubs.isEmpty()) {
            sendBotMessageService.sendMessage(chatId, EMPTY_GROUP_SUBS_MESSAGE);
        } else {
            String groupSubsString = groupSubs.stream()
                .sorted(Comparator.comparing(GroupSub::getId))
                .map(groupSub -> String.format("%s - %s %n", groupSub.getId(), groupSub.getTitle()))
                .collect(joining());

            sendBotMessageService.sendMessage(chatId,
                String.format(DELETE_GROUP_SUBS_MESSAGE, groupSubsString));
        }
    }
}
