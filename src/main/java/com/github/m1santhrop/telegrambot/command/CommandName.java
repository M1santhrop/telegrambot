package com.github.m1santhrop.telegrambot.command;

public enum CommandName {
    
    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    NO("nocommand");
    
    private String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
