package com.github.m1santhrop.telegrambot.command;

import com.github.m1santhrop.telegrambot.bot.JavarushTelegramBot;
import com.github.m1santhrop.telegrambot.repository.TelegramUserRepository;
import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import com.github.m1santhrop.telegrambot.service.SendBotMessageServiceImpl;
import com.github.m1santhrop.telegrambot.service.TelegramUserService;
import com.github.m1santhrop.telegrambot.service.TelegramUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

abstract class AbstractCommandTest {
    
    protected JavarushTelegramBot telegramBot = Mockito.mock(JavarushTelegramBot.class);
    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(telegramBot);
    protected TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
    
    @Test
    public void shouldProperlyExecuteCommand() throws TelegramApiException {
        //given
        Long chatId = 1234567824356L;
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(getCommandName());
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(getCommandMessage());
        sendMessage.enableHtml(true);
        
        //when
        Command command = getCommand();
        command.execute(update);
        
        //then
        Mockito.verify(telegramBot).execute(sendMessage);
    }

    abstract Command getCommand();
    
    abstract String getCommandName();
    
    abstract String getCommandMessage();
}
