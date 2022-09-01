package com.github.m1santhrop.telegrambot.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommandName {
    
    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    NOCOMMAND("nocommand"),
    NOTEXT("notext"),
    STAT("/stat"),
    ADD_GROUP_SUB("/addgroupsub"),
    LIST_GROUP_SUB("/listgroupsub"),
    DELETE_GROUP_SUB("/deletegroupsub");
    
    private String name;
}
