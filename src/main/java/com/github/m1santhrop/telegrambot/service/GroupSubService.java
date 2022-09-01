package com.github.m1santhrop.telegrambot.service;

import com.github.m1santhrop.telegrambot.javarushclient.dto.GroupDiscussionInfo;
import com.github.m1santhrop.telegrambot.repository.entity.GroupSub;
import java.util.Optional;

public interface GroupSubService {
    GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo);
    Optional<GroupSub> findById(Integer id);
    GroupSub save(GroupSub groupSub);
}
