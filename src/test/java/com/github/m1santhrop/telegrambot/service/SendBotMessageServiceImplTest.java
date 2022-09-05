package com.github.m1santhrop.telegrambot.service;

import static org.mockito.Mockito.*;
import com.github.m1santhrop.telegrambot.bot.JavarushTelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@DisplayName("Unit-level testing for SendBotMessageServiceImpl")
class SendBotMessageServiceImplTest {
    
    private SendBotMessageService sendBotMessageService;
    private JavarushTelegramBot telegramBot;

    @BeforeEach
    void setUp() {
        telegramBot = mock(JavarushTelegramBot.class);
        sendBotMessageService = new SendBotMessageServiceImpl(telegramBot);
    }

    @Test
    void shouldProperlySendMessage() throws TelegramApiException {
        //given
        Long chatId = 1L;
        String message = "test_message";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        
        //when
        sendBotMessageService.sendMessage(chatId, message);
        
        //then
        Mockito.verify(telegramBot).execute(sendMessage);
    }
}