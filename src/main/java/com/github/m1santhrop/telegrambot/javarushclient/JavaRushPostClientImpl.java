package com.github.m1santhrop.telegrambot.javarushclient;

import com.github.m1santhrop.telegrambot.javarushclient.dto.PostInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JavaRushPostClientImpl implements JavaRushPostClient {
    
    private static final Integer DEFAULT_POSTS_LIMIT = 15;

    private final String javarushApiPostPath;

    public JavaRushPostClientImpl(@Value("${javarush.api.path}") String javarushApiPath) {
        this.javarushApiPostPath = javarushApiPath + "/posts";
    }

    @Override
    public List<PostInfo> findNewPosts(Integer groupId, Integer lastPostId) {
        List<PostInfo> lastPostsByGroup = Unirest.get(javarushApiPostPath)
            .queryString("order", "NEW")
            .queryString("groupKid", groupId)
            .queryString("limit", DEFAULT_POSTS_LIMIT)
            .asObject(new GenericType<List<PostInfo>>() {
            })
            .getBody();
        List<PostInfo> newPosts = new ArrayList<>();
        for (PostInfo postInfo : lastPostsByGroup) {
            if (Objects.equals(postInfo.getId(), lastPostId)) {
                return newPosts;
            }
            newPosts.add(postInfo);
        }
        return newPosts;
    }
}
