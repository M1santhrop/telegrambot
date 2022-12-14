package com.github.m1santhrop.telegrambot.service;

import com.github.m1santhrop.telegrambot.javarushclient.dto.GroupDiscussionInfo;
import com.github.m1santhrop.telegrambot.repository.entity.GroupSub;
import java.util.List;
import java.util.Optional;

public interface GroupSubService {
    GroupSub save(Long chatId, GroupDiscussionInfo groupDiscussionInfo);
    Optional<GroupSub> findById(Integer id);
    GroupSub save(GroupSub groupSub);
    List<GroupSub> findAll();
}
