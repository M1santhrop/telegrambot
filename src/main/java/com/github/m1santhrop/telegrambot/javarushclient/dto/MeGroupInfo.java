package com.github.m1santhrop.telegrambot.javarushclient.dto;

import lombok.Data;

@Data
public class MeGroupInfo {

    private MeGroupInfoStatus status;
    private Integer userGroupId;
}
