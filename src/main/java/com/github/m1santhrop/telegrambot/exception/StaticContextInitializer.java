package com.github.m1santhrop.telegrambot.exception;

import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StaticContextInitializer {

    @Autowired
    private SendBotMessageService sendBotMessageService;

    @PostConstruct
    public void init() {
        ExceptionSender.setSendBotMessageService(sendBotMessageService);
    }
}
