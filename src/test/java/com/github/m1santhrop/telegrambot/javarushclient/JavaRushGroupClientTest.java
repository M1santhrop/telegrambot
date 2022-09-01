package com.github.m1santhrop.telegrambot.javarushclient;

import static com.github.m1santhrop.telegrambot.javarushclient.dto.GroupInfoType.*;
import static org.junit.jupiter.api.Assertions.*;
import com.github.m1santhrop.telegrambot.javarushclient.dto.GroupDiscussionInfo;
import com.github.m1santhrop.telegrambot.javarushclient.dto.GroupInfo;
import com.github.m1santhrop.telegrambot.javarushclient.dto.GroupRequestArgs;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Integration-level testing for JavaRushGroupClient")
class JavaRushGroupClientTest {

    private final JavaRushGroupClient javaRushGroupClient = new JavaRushGroupClientImpl(
        "https://javarush.ru/api/1.0/rest");

    @Test
    void shouldProperlyGetGroupsWithEmptyArgs() {
        //given
        GroupRequestArgs groupRequestArgs = GroupRequestArgs.builder().build();

        //when
        List<GroupInfo> groupList = javaRushGroupClient.getGroupList(groupRequestArgs);

        //then
        assertNotNull(groupList);
        assertFalse(groupList.isEmpty());
    }

    @Test
    void shouldProperlyGetGroupsWithOffsetAndLimit() {
        //given
        GroupRequestArgs groupRequestArgs = GroupRequestArgs.builder().offset(1).limit(3).build();

        //when
        List<GroupInfo> groupList = javaRushGroupClient.getGroupList(
            groupRequestArgs);

        //then
        assertNotNull(groupList);
        assertEquals(3, groupList.size());
    }

    @Test
    void shouldProperlyGetGroupDiscussionsWithEmptyArgs() {
        //given
        GroupRequestArgs groupRequestArgs = GroupRequestArgs.builder().build();

        //when
        List<GroupDiscussionInfo> groupList = javaRushGroupClient.getGroupDiscussionList(
            groupRequestArgs);

        //then
        assertNotNull(groupList);
        assertFalse(groupList.isEmpty());
    }

    @Test
    void shouldProperlyGetGroupDiscussionsWithOffsetAndLimit() {
        //given
        GroupRequestArgs groupRequestArgs = GroupRequestArgs.builder().offset(1).limit(3).build();

        //when
        List<GroupDiscussionInfo> groupList = javaRushGroupClient.getGroupDiscussionList(
            groupRequestArgs);

        //then
        assertNotNull(groupList);
        assertEquals(3, groupList.size());
    }

    @Test
    void shouldProperlyGetGroupCount() {
        //given
        GroupRequestArgs groupRequestArgs = GroupRequestArgs.builder().build();

        //when
        Integer groupCount = javaRushGroupClient.getGroupCount(groupRequestArgs);

        //then
        assertEquals(32, groupCount);
    }

    @Test
    void shouldProperlyGetGroupTECHCount() {
        //given
        GroupRequestArgs groupRequestArgs = GroupRequestArgs.builder().type(TECH).build();

        //when
        Integer groupCount = javaRushGroupClient.getGroupCount(groupRequestArgs);

        //then
        assertEquals(7, groupCount);
    }
    
    @Test
    void shouldProperlyGetGroupById() {
        //given
        Integer id = 1;
        
        //when
        GroupDiscussionInfo group = javaRushGroupClient.getGroupById(id);
        
        //then
        assertNotNull(group);
        assertEquals(id, group.getId());
        assertEquals("javarush", group.getKey());
        assertEquals(COMPANY, group.getType());
    }

    @Test
    void shouldProperlyFindLastPostId() {
        //given
        Integer id = 1;
        
        //when
        Integer lastPostId = javaRushGroupClient.findLastPostId(id);
        
        //then
        assertTrue(lastPostId > 0);
    }
}