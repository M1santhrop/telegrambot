package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandName.*;
import static com.github.m1santhrop.telegrambot.command.CommandUtils.getChatId;
import static com.github.m1santhrop.telegrambot.command.CommandUtils.getMessage;
import com.github.m1santhrop.telegrambot.javarushclient.JavaRushGroupClient;
import com.github.m1santhrop.telegrambot.javarushclient.dto.GroupDiscussionInfo;
import com.github.m1santhrop.telegrambot.javarushclient.dto.GroupInfo;
import com.github.m1santhrop.telegrambot.javarushclient.dto.GroupRequestArgs;
import com.github.m1santhrop.telegrambot.repository.entity.GroupSub;
import com.github.m1santhrop.telegrambot.service.GroupSubService;
import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public class AddGroupSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final GroupSubService groupSubService;
    private final JavaRushGroupClient javaRushGroupClient;

    public static final String GROUP_LIST_MESSAGE =
        "Чтобы подписаться на группу - передай комадну вместе с ID группы. \n" +
            "Например: /addGroupSub 16. \n\n" +
            "я подготовил список всех групп - выбирай какую хочешь :) \n\n" +
            "ID группы - имя группы \n\n" +
            "%s";
    public static final String GROUP_NOT_FOUND_MESSAGE = "Нет группы с ID = %s";

    @Override
    public void execute(Update update) {
        String message = getMessage(update);
        String chatId = getChatId(update);
        if (message.equalsIgnoreCase(ADD_GROUP_SUB.getName())) {
            sendGroupIdList(chatId);
            return;
        }
        String groupId = message.split("\\s")[1];
        if (StringUtils.isNumeric(groupId)) {
            GroupDiscussionInfo groupDiscussionInfo = javaRushGroupClient.getGroupById(
                Integer.valueOf(groupId));
            if (Objects.isNull(groupDiscussionInfo.getId())) {
                sendGroupNotFound(chatId, groupId);
            }
            GroupSub savedGroupSub = groupSubService.save(chatId, groupDiscussionInfo);
            sendBotMessageService.sendMessage(chatId, "Подписал на группу " + savedGroupSub.getTitle());
        } else {
            sendGroupNotFound(chatId, groupId);
        }
    }

    private void sendGroupIdList(String chatId) {
        String groupIds = javaRushGroupClient.getGroupList(GroupRequestArgs.builder().build())
            .stream()
            .sorted(Comparator.comparing(GroupInfo::getId))
            .map(groupInfo -> String.format("%s - %s %n", groupInfo.getId(), groupInfo.getTitle()))
            .collect(Collectors.joining());

        sendBotMessageService.sendMessage(chatId, String.format(GROUP_LIST_MESSAGE, groupIds));
    }

    private void sendGroupNotFound(String chatId, String groupId) {
        sendBotMessageService.sendMessage(chatId, String.format(GROUP_NOT_FOUND_MESSAGE, groupId));
    }
}
