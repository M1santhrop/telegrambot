package com.github.m1santhrop.telegrambot.service;

import com.github.m1santhrop.telegrambot.javarushclient.dto.GroupDiscussionInfo;
import com.github.m1santhrop.telegrambot.repository.GroupSubRepository;
import com.github.m1santhrop.telegrambot.repository.entity.GroupSub;
import com.github.m1santhrop.telegrambot.repository.entity.TelegramUser;
import java.util.Optional;
import javax.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupSubServiceImpl implements GroupSubService {

    private GroupSubRepository groupSubRepository;
    private TelegramUserService telegramUserService;

    @Autowired
    public GroupSubServiceImpl(
        GroupSubRepository groupSubRepository,
        TelegramUserService telegramUserService) {
        this.groupSubRepository = groupSubRepository;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo) {
        TelegramUser telegramUser = telegramUserService.findByChatId(chatId)
            .orElseThrow(NotFoundException::new);

        Optional<GroupSub> groupSubFromDB = groupSubRepository.findById(
            groupDiscussionInfo.getId());
        GroupSub groupSub;
        if (groupSubFromDB.isPresent()) {
            groupSub = groupSubFromDB.get();
            Optional<TelegramUser> first = groupSub.getUsers()
                .stream()
                .filter(user -> user.getChatId().equalsIgnoreCase(chatId))
                .findFirst();
            if (first.isEmpty()) {
                groupSub.addUser(telegramUser);
            }
        } else {
            groupSub = new GroupSub();
            groupSub.setId(groupDiscussionInfo.getId());
            groupSub.setTitle(groupDiscussionInfo.getTitle());
            groupSub.addUser(telegramUser);
        }
        return groupSubRepository.save(groupSub);
    }

    @Override
    public Optional<GroupSub> findById(Integer id) {
        return groupSubRepository.findById(id);
    }

    @Override
    public GroupSub save(GroupSub groupSub) {
        return groupSubRepository.save(groupSub);
    }
}
