package com.github.m1santhrop.telegrambot.service;

import com.github.m1santhrop.telegrambot.javarushclient.JavaRushPostClient;
import com.github.m1santhrop.telegrambot.javarushclient.dto.PostInfo;
import com.github.m1santhrop.telegrambot.repository.entity.GroupSub;
import com.github.m1santhrop.telegrambot.repository.entity.TelegramUser;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindNewPostServiceImpl implements FindNewPostService {

    private static final String JAVARUSH_WEB_POST_FORMAT = "https://javarush.ru/groups/posts/%s";
    private static final String NEW_POST_MESSAGE =
        "✨Вышла новая статья <b>%s</b> в группе <b>%s</b>.✨\n\n" +
            "<b>Описание:</b> %s\n\n" +
            "<b>Ссылка:</b> %s\n";

    private final GroupSubService groupSubService;
    private final JavaRushPostClient javaRushPostClient;
    private final SendBotMessageService sendBotMessageService;

    @Autowired
    public FindNewPostServiceImpl(
        GroupSubService groupSubService,
        JavaRushPostClient javaRushPostClient,
        SendBotMessageService sendBotMessageService) {
        this.groupSubService = groupSubService;
        this.javaRushPostClient = javaRushPostClient;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void findNewPosts() {
        groupSubService.findAll().forEach(groupSub -> {
            List<PostInfo> newPosts = javaRushPostClient.findNewPosts(groupSub.getId(),
                groupSub.getLastPostId());

            setNewPostId(groupSub, newPosts);

            notifySubscribersAboutNewPosts(groupSub, newPosts);
        });
    }

    private void setNewPostId(GroupSub groupSub, List<PostInfo> newPosts) {
        newPosts.stream()
            .mapToInt(PostInfo::getId)
            .max()
            .ifPresent(maxPostId -> {
                groupSub.setLastPostId(maxPostId);
                groupSubService.save(groupSub);
            });
    }

    private void notifySubscribersAboutNewPosts(GroupSub groupSub, List<PostInfo> newPosts) {
        Collections.reverse(newPosts);

        List<String> messagesWithNewPosts = newPosts.stream()
            .map(postInfo -> String.format(NEW_POST_MESSAGE, postInfo.getTitle(),
                groupSub.getTitle(), postInfo.getDescription(), getPostUrl(postInfo.getKey())))
            .collect(Collectors.toList());

        groupSub.getUsers().stream()
            .filter(TelegramUser::isActive)
            .forEach(telegramUser -> sendBotMessageService.sendMessage(telegramUser.getChatId(),
                messagesWithNewPosts));
    }

    private String getPostUrl(String key) {
        return String.format(JAVARUSH_WEB_POST_FORMAT, key);
    }
}
