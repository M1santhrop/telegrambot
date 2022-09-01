package com.github.m1santhrop.telegrambot.javarushclient;

import com.github.m1santhrop.telegrambot.javarushclient.dto.PostInfo;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Integration-level testing for JavaRushPostClient")
class JavaRushPostClientTest {

    private JavaRushPostClient javaRushPostClient = new JavaRushPostClientImpl("https://javarush.ru/api/1.0/rest");
    
    @Test
    void shouldProperlyGetNew15Posts() {
        
        //when
        List<PostInfo> newPosts = javaRushPostClient.findNewPosts(30, 1);

        //then
        Assertions.assertEquals(15, newPosts.size());
    }
}