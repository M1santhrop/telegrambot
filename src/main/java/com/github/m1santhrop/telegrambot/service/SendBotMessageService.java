package com.github.m1santhrop.telegrambot.service;

public interface SendBotMessageService {
    void sendMessage(String chatId, String message);
}
