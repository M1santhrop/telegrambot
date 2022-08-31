package com.github.m1santhrop.telegrambot.javarushclient;

import com.github.m1santhrop.telegrambot.javarushclient.dto.GroupDiscussionInfo;
import com.github.m1santhrop.telegrambot.javarushclient.dto.GroupInfo;
import com.github.m1santhrop.telegrambot.javarushclient.dto.GroupRequestArgs;
import java.util.List;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JavaRushGroupClientImpl implements JavaRushGroupClient {

    private final String javarushApiGroupPath;

    @Autowired
    public JavaRushGroupClientImpl(@Value("${javarush.api.path}") String javarushApiGroupPath) {
        this.javarushApiGroupPath = javarushApiGroupPath + "/groups";
    }

    @Override
    public List<GroupInfo> getGroupList(GroupRequestArgs groupRequestArgs) {
        return Unirest.get(javarushApiGroupPath)
            .queryString(groupRequestArgs.populateQueries())
            .asObject(new GenericType<List<GroupInfo>>() {
            })
            .getBody();
    }

    @Override
    public List<GroupDiscussionInfo> getGroupDiscussionList(GroupRequestArgs groupRequestArgs) {
        return Unirest.get(javarushApiGroupPath)
            .queryString(groupRequestArgs.populateQueries())
            .asObject(new GenericType<List<GroupDiscussionInfo>>() {
            })
            .getBody();
    }

    @Override
    public Integer getGroupCount(GroupRequestArgs groupRequestArgs) {
        return Integer.valueOf(Unirest.get(String.format("%s/count", javarushApiGroupPath))
            .queryString(groupRequestArgs.populateQueries())
            .asString()
            .getBody());
    }

    @Override
    public GroupDiscussionInfo getGroupById(Integer id) {
        return Unirest.get(String.format("%s/group%s", javarushApiGroupPath, id.toString()))
            .asObject(GroupDiscussionInfo.class)
            .getBody();
    }
}
