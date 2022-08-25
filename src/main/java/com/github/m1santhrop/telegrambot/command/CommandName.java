package com.github.m1santhrop.telegrambot.command;

public enum CommandName {
    
    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    NO("nocommand");
    
    private String name;

    CommandName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
