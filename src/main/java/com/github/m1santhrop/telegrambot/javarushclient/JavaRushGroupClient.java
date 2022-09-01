package com.github.m1santhrop.telegrambot.javarushclient;

import com.github.m1santhrop.telegrambot.javarushclient.dto.GroupDiscussionInfo;
import com.github.m1santhrop.telegrambot.javarushclient.dto.GroupInfo;
import com.github.m1santhrop.telegrambot.javarushclient.dto.GroupRequestArgs;
import java.util.List;

public interface JavaRushGroupClient {

    List<GroupInfo> getGroupList(GroupRequestArgs groupRequestArgs);

    List<GroupDiscussionInfo> getGroupDiscussionList(GroupRequestArgs groupRequestArgs);

    Integer getGroupCount(GroupRequestArgs groupRequestArgs);

    GroupDiscussionInfo getGroupById(Integer id);
    
    Integer findLastPostId(Integer groupSubId);
}
